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

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.resources.Resource;
import com.sun.spot.service.IService;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Simple helper class to monitor a radiogram connection and redirect packets to
 * handlers that have registered an interest in that packet type.
 * The contents of the first byte of the packet determines its type.
 *<p>
 * Implements the com.sun.spot.service.IService interface.
 *
 * @author arshan & Ron Goldman<br>
 * Date: August 1, 2007
 *
 * @see PacketHandler
 * @see PacketTransmitter
 * @see com.sun.spot.service.IService
 */
public class PacketReceiver extends Resource implements IService {
    
    private Vector[] registeredHandlers; // this is an array of vectors
    private int status = STOPPED;
    private Thread thread = null;
    private String name = "Command Dispatcher";
    private RadiogramConnection rcvConn = null;
    
    /**
     * Creates a new instance of a PacketReceiver.
     *
     * @param conn the radiogram connection to receive packets from
     */
    public PacketReceiver(RadiogramConnection conn) {
        registeredHandlers = new Vector[256];
        for (int x = 0 ; x < 256 ; x++) {
            registeredHandlers[x] = new Vector();
        }
        rcvConn = conn;
    }
    
    /**
     * Register a handler for some packet type. Packets of this type will be
     * passed to the class via its handlePacket() callback method.
     *
     * @param handler the class to register
     * @param type the command to dispatch to this handler
     */
    public synchronized void registerHandler(PacketHandler handler, byte type){
        if (!registeredHandlers[type].contains(handler)) {
            registeredHandlers[type].addElement(handler);
        }        
    }
    
    /**
     * Unregister a handler for some packet type.
     *
     * @param handler the class to unregister
     * @param type the command to not dispatch to this handler
     */
    public synchronized void unregisterHandler(PacketHandler handler , byte type){
        registeredHandlers[type].removeElement(handler);
    }

    /**
     * Unregister a handler from all packet types.
     *
     * @param handler the class to unregister
     */
    public synchronized void unregisterHandler(PacketHandler handler){
        for (int i = 0; i < 256; i++) {
            registeredHandlers[i].removeElement(handler);
        }        
    }
    
    /**
     * Main loop of the packet receiver.  Receive packets and dispatch them to 
     * all handlers that have asked to see that type of packet.
     */
    private void receiverLoop() {
        try {
            Radiogram rdg = (Radiogram)rcvConn.newDatagram(rcvConn.getMaximumLength());
            
            // continually receive the next packet
            status = RUNNING;
            
            while (status == RUNNING && thread == Thread.currentThread()) {
                try {
                    rcvConn.receive(rdg);
                    byte packetType = rdg.readByte();
                    synchronized (this) { // we don't allow new handlers to be added in this section
                        Vector handlers = registeredHandlers[packetType];
                        if (handlers.size() == 0) {
                            System.out.println("ignoring packet type: " + packetType);
                        } else {
                            for (Enumeration e = handlers.elements() ; e.hasMoreElements() ;) {
                                try {
                                    rdg.resetRead();
                                    rdg.readByte();
                                    ((PacketHandler)e.nextElement()).handlePacket(packetType,rdg);
                                } catch (Exception ex) {            // don't let an error kill the server!!!
                                    System.out.println("Error handling packet of type " + packetType + ": " + ex);
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (InterruptedIOException iex) {
                    System.out.println("Packet receiver " + name + ": " + iex);
                    break;
                } catch (IOException ex) {
                    /* ignore */
                    System.out.println("Error in packet receiver " + name + ": " + ex);
                    // XXX should we try to recover?
                }
            }
        } catch (Exception e) {
            System.out.println("Fatal error in packet receiver " + name + ": " + e);
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
     * Stop packet receiver service if running.
     *
     * @return true if will stop service
     */
    public boolean stop() {
        if (status != STOPPED) {
            status = STOPPING;
        }
        System.out.println("Stopping packet receiver: " + name);
        return true;
    }
    
    /**
     * Start packet receiver service running.
     *
     * @return true if will start service
     */
    public boolean start() {
        if (status == STOPPED || status == STOPPING) {
            status = STARTING;
            thread = new Thread() {
                public void run() {
                    receiverLoop();
                }
            };
            thread.setPriority(Thread.MAX_PRIORITY - 3);
            thread.start();
            System.out.println("Starting packet receiver: " + name);
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
