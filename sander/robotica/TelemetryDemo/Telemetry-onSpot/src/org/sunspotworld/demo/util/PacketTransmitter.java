/*
 * Copyright (c) 2007 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.sunspotworld.demo.util;

import com.sun.spot.peripheral.ChannelBusyException;
import com.sun.spot.peripheral.NoAckException;
import com.sun.spot.peripheral.radio.*;
import com.sun.spot.util.*;
import com.sun.spot.service.IService;
import com.sun.spot.io.j2me.radiogram.*;

import com.sun.spot.resources.Resource;
import java.util.Random;
import java.io.*;
import javax.microedition.io.*;

/**
 * Simple transmit loop to pull packets off of a xmitQueue and send them.
 * After the packet is sent it is put on a freeQueue. 
 *<p>
 * Classes wanting to send a packet should get one by calling newDataPacket(),
 * passing in the packet type as an argument.
 *<p>
 * Implements the com.sun.spot.service.IService interface.
 *
 * @author Ron Goldman<br>
 * Date: August 1, 2007
 *
 * @see PacketReceiver
 * @see com.sun.spot.service.IService
 */
public class PacketTransmitter extends Resource implements IService {
    
    /** How many bytes fit in each radiogram packet, including the packet type byte. */
    private static final int MAX_PAYLOAD_SIZE = 
            RadioPacket.MIN_PAYLOAD_LENGTH - (LowPanHeader.MAX_UNFRAG_HEADER_LENGTH + 1);
    /** packet type size = one byte */
    private static final int HEADER_SIZE = 1;           // packet type
    /** Number of bytes of data that will fit in a single packet, not including the header */
    public static final int SINGLE_PACKET_PAYLOAD_SIZE = MAX_PAYLOAD_SIZE - HEADER_SIZE;

    /**
     * How many Radiograms should be initially placed on the freeQueue.
     */
    private static final int PREALLOCATE = 100;
    
    private RadiogramConnection txConn;
    private Queue xmitQueue;
    private Queue freeQueue;
    private int status = STOPPED;
    private Thread thread = null;
    private String name = "Xmitter";
    private int payloadSize = MAX_PAYLOAD_SIZE;     // default to single packet

    /**
     * Create a new transmit loop thread.
     *
     * @param conn the RadiogramConnection to send packets over
     */
    public PacketTransmitter(RadiogramConnection conn) {
        txConn = conn;
        init();
    }
    
    /**
     * Create a new transmit loop thread and use (multi-packet) datagrams.
     *
     * @param conn the RadiogramConnection to send packets over
     * @param payloadSize how big do outgoing packets need to be
     */
    public PacketTransmitter(RadiogramConnection conn, int payloadSize) {
        try {
            if (payloadSize > conn.getMaximumLength()) {
                throw new IllegalArgumentException("Payload size can not exceed " + conn.getMaximumLength());
            }
        } catch (IOException ex) {
            // should never happen - ignore
        }
        txConn = conn;
        this.payloadSize = payloadSize;
        init();
    }
    
    /**
     * Initialize the free & transmit queues. Preallocate datagrams.
     */
    private void init() {
        xmitQueue = new Queue();
        freeQueue = new Queue();
        try {
            for (int i = 0; i < PREALLOCATE; i++) {
                freeQueue.put(txConn.newDatagram(payloadSize));
            }
        } catch (IOException e) {
        }
    }
    
    /**
     * Write the common header into a Radiogram.
     *
     * @param rdg the Radiogram to write the header info into
     * @param type the type of data packet to send
     */
    public void writeHeader(Radiogram rdg, byte type) {
        try {
            rdg.reset();
            rdg.writeByte(type);
        } catch (IOException ex) {
            System.out.println("Error writing header: " + ex);
        }
    }
    
    /**
     * Get a free data packet and write its header.
     *
     * @param type the type of data packet to send
     * @return the data packet with its header setup
     */
    public Radiogram newDataPacket(byte type) {
        for (int i = 0; i < 5; i++) {
            try {
                Radiogram dg = null;
                if (freeQueue.isEmpty()) {
                    dg = (Radiogram)txConn.newDatagram(MAX_PAYLOAD_SIZE);
                } else {
                    dg = (Radiogram)freeQueue.get();
                }
                writeHeader(dg, type);
                return dg;
            } catch (IOException ex) {
                // retry, let dg be reclaimed as garbage
            }
        }
        return null;        // probably should throw some exception
    }
    
    /**
     * Routine to put a data packet onto the free list.
     *
     * @param dg the Datagram to free
     */
    public void free(Datagram dg) {
        freeQueue.put(dg);
    }

    /**
     * Routine to queue a data packet for later sending.
     *
     * @param dg the Datagram to send
     */
    public void send(Datagram dg) {
        xmitQueue.put(dg);
    }

    /**
     * Routine to immediately send a data packet. Used by senders who need to know
     * when the packet was actually sent or who want to reuse the Datagram.
     *
     * @param dg the Datagram to send
     * @throws IOException
     */
    public void immediateSend(Datagram dg) throws IOException {
        txConn.send(dg);
    }

    /**
     * The main transmit loop.
     */
    private void transmitLoop() {
        status = RUNNING;
        Random random = new Random();
        while (status == RUNNING && thread == Thread.currentThread()) {
            Datagram xdg = (Datagram) xmitQueue.get();
            if (xdg != null) {
                try {
                    for (int tries = 1; tries < 5; tries++) {
                        try {
                            txConn.send(xdg);     // normally takes 9-12 msec, but can take 70+ msec
                            break;
                        } catch (NoAckException ne) {
                            System.err.println("No Ack: " + ne.toString());
                        } catch (ChannelBusyException be) {
                            System.err.println("Busy Channel: " + be.toString());
                        }
                        Utils.sleep(tries * (3 + random.nextInt(10)));    // backoff by random time
                    }
                    Thread.yield();
                } catch (IOException ie) {
                    System.err.println("IO exception: " + ie.toString());
                } catch (Exception e) {
                    System.err.println("SENDER problem " + e);
                } finally {
                    freeQueue.put(xdg);
                }
            }
        }
        if (thread == Thread.currentThread()) {
            status = STOPPED;
        }
    }


    ////////////////////////////////
    //
    // IService defined methods
    //
    ////////////////////////////////

    /**
     * Stop service running.
     *
     * @return true if will stop service
     */
    public boolean stop() {
        if (status != STOPPED) {
            status = STOPPING;
            System.out.println("Stopping xmitter: " + name);
        }
        return true;
    }

    /**
     * Start service running.
     *
     * @return true if will start service
     */
    public boolean start() {
        if (status == STOPPED || status == STOPPING) {
            while (!xmitQueue.isEmpty()) {
                Object obj = xmitQueue.get();
                if (obj != null) {
                    freeQueue.put(obj);
                }
            }
            status = STARTING;
            thread = new Thread() {
                public void run() {
                    transmitLoop();
                }
            };
            thread.setPriority(Thread.MAX_PRIORITY - 2);
            thread.start();
            System.out.println("Starting xmitter: " + name);
        }
        return true;
    }

    /**
     * Pause the service, and return whether successful.
     *
     * Since there is no particular state associated with this service
     * then pause() can be implemented by calling stop().
     *
     * @return true if the service was successfully paused
     */
    public boolean pause() {
        return stop();
    }

    /**
     * Resume the service, and return whether successful.
     *
     * Since there was no particular state associated with this service
     * then resume() can be implemented by calling start().
     *
     * @return true if the service was successfully resumed
     */
    public boolean resume() {
        return start();
    }

    /**
     * Return service name
     *
     * @return the name of this service
     */
    public String getServiceName() {
        return name;
    }

    /**
     * Assign a name to this service.
     *
     * @param who the new name for this service
     */
    public void setServiceName(String who) {
        if (who != null) {
            name = who;
        }
    }

    /**
     * Return if service is currently running.
     *
     * @return true if currently running
     */
    public boolean isRunning() {
        return status == RUNNING;
    }

    /**
     * Return current service status.
     *
     * @return current service status: STOPPED, STARTING, RUNNING, or STOPPING.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Return whether service is started automatically on reboot.
     *
     * @return false as this service is never started automatically on reboot
     */
    public boolean getEnabled() {
        return false;
    }

    /**
     * Enable/disable whether service is started automatically. Noop for us.
     *
     * @param enable ignored
     */
    public void setEnabled(boolean enable) {
        // ignore
    }    
    
}
