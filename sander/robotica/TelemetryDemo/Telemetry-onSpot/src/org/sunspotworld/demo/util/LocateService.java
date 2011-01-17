/*
 * Copyright (c) 2007-2010 Sun Microsystems, Inc.
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
 **/

package org.sunspotworld.demo.util;

import com.sun.spot.peripheral.Spot;
import com.sun.spot.peripheral.ChannelBusyException;
import com.sun.spot.peripheral.TimeoutException;
import com.sun.spot.peripheral.radio.RadioPolicy;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;

import java.io.IOException;
import java.util.Random;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;


/**
 * Helper class to handle locating a remote service. 
 *<p>
 * Broadcasts a service request periodically and listens for a response.
 * When found calls back to report the IEEE address where the service can be found.
 *
 * @author Ron Goldman<br>
 * Date: July 31, 2007
 *
 * @see LocateServiceListener
 */
public class LocateService {
    
    private static final int DEFAULT_HOPS = 2;
    private long ourMacAddress;
    private long serviceAddress;
    private LocateServiceListener listener = null;
    private long checkInterval;
    private String port;
    private byte requestCmd, replyCmd;
    private int numHops;
    private Random random;
    private ITriColorLED led = null;
    private Thread thread = null;
    private boolean checking = false;
    
    /**
     * Creates a new instance of LocateService.
     *
     * @param listener class to callback when the service is found
     * @param port the port to broadcast & listen on
     * @param checkInterval how long to wait between checking
     * @param requestCmd the command requesting a connection with the service
     * @param replyCmd the command the service replys with to indicate it is available
     */
    public LocateService(LocateServiceListener listener, String port, long checkInterval,
                         byte requestCmd, byte replyCmd) {
        init(listener, port, checkInterval, requestCmd, replyCmd, DEFAULT_HOPS);
    }
    
    /**
     * Creates a new instance of LocateService.
     *
     * @param listener class to callback when the service is found
     * @param port the port to broadcast & listen on
     * @param checkInterval how long to wait between checking
     * @param requestCmd the command requesting a connection with the service
     * @param replyCmd the command the service replys with to indicate it is available
     * @param numHops the number of hops the broadcast command should traverse
     */
    public LocateService(LocateServiceListener listener, String port, long checkInterval,
                         byte requestCmd, byte replyCmd, int numHops) {
        init(listener, port, checkInterval, requestCmd, replyCmd, numHops);
    }

    private void init(LocateServiceListener listener, String port, long checkInterval,
                      byte requestCmd, byte replyCmd, int numHops) {
        this.listener = listener;
        this.port = port;
        this.checkInterval = checkInterval;
        this.requestCmd = requestCmd;
        this.replyCmd = replyCmd;
        this.numHops = numHops;
        
        ourMacAddress = Spot.getInstance().getRadioPolicyManager().getIEEEAddress();
        random = new Random(ourMacAddress);
    }
 
    /**
     * Specify an LED to use to display status of search.
     *
     * @param led the LED to use to show the search status
     */
    public void setStatusLed(ITriColorLED led) {
        this.led = led;
    }

    /**
     * Start searching for a service
     */
    public void start() {
        checking = true;
        thread = new Thread() {
            public void run() {
                clientLoop();
            }
        };
        thread.start();
    }
    

    /**
     * Stop searching for a service
     */
    public void stop() {
        checking = false;
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }
    
    /**
     * Try to locate a display server. Broadcast a service request packet and
     * listen for a reply from host. Timeout if no reply received.
     *
     * @param txConn the broadcast radiogram connection to use to send packets
     * @param xdg the packet to use for broadcasting the request
     * @param rcvConn the server radiogram connection to use to receive a reply
     * @param rdg the packet to use for receiving the reply
     *
     * @return true if a display server was located
     */
    private boolean locateDisplayServer (DatagramConnection txConn, Datagram xdg,
                                         RadiogramConnection rcvConn, Datagram rdg) {
        boolean result = false;
        try {
            xdg.reset();
            xdg.writeByte(requestCmd);                    // packet type = locate service request
            int retry = 0;
            while (retry < 5) {
                try {
                    txConn.send(xdg);                     // broadcast locate display server request
                    break;
                } catch (ChannelBusyException ex) {
                    retry++;
                    Utils.sleep(random.nextInt(10) + 2);  // wait a random amount before retrying
                }
            }
            try {
                while (checking) {                     // loop until we either get a good reply or timeout
                    rdg.reset();
                    rcvConn.receive(rdg);              // wait until we receive a request
                    if (rdg.readByte() == replyCmd) {  // type of packet
                        long replyAddress = rdg.readLong();
                        if (replyAddress == ourMacAddress) {
                            String addr = rdg.getAddress();
                            IEEEAddress ieeeAddr = new IEEEAddress(addr);
                            serviceAddress = ieeeAddr.asLong();
                            result = true;
                        }
                    }
                }
            } catch (TimeoutException ex) { /* ignore - just return false */ }
        } catch (IOException ex)  { /* also ignore - just return false */ }

        return result;
    }

    /**
     * Internal loop to locate a remote display service and report its IEEE address back.
     */
    private void clientLoop () {
        try {
            RadiogramConnection txConn = null;
            RadiogramConnection rcvConn = null;
            Utils.sleep(200);  // wait a bit to give any previously running instance a chance to exit
            // this outer loop is for retrying if there is an exception
            while (checking && thread == Thread.currentThread()) {
                try {
                    txConn = (RadiogramConnection)Connector.open("radiogram://broadcast:" + port);
                    txConn.setMaxBroadcastHops(numHops);
                    Datagram xdg = txConn.newDatagram(20);
                    rcvConn = (RadiogramConnection)Connector.open("radiogram://:" + port);
                    rcvConn.setTimeout(300);    // timeout in 300 msec - so receive() will not deep sleep
                    rcvConn.setRadioPolicy(RadioPolicy.AUTOMATIC);  // but allow deep sleep other times
                    Radiogram rdg = (Radiogram)rcvConn.newDatagram(20);
                    
                    // loop to locate a remote print server
                    while (checking && thread == Thread.currentThread()) {
                        int tries = 0;
                        boolean found = false;
                        if (led != null) {
                            led.setRGB(60,40,0);                // Yellow = looking for display server
                            led.setOn();
                        }
                        do {
                            found = locateDisplayServer(txConn, xdg, rcvConn, rdg);
                            Utils.sleep(20);  // wait 20 msecs
                            ++tries;
                        } while (checking && !found && tries < 5);
                        if (led != null) {
                            led.setOff();
                        }
                        if (found) {
                            checking = false;
                            listener.serviceLocated(serviceAddress);    //report success
                            break;
                        } else {
                            if (led != null) {
                                led.setRGB(80,0,0);             // Red = still looking for service
                                led.setOn();
                            }
                            if (checking) {
                                Utils.sleep(checkInterval);     // wait a while before looking again
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error trying to locate remote display server: " + ex.toString());
                    ex.printStackTrace();
                } finally {
                    try {
                        if (led != null) {
                            led.setOff();
                        }
                        txConn.close();
                        if (rcvConn != null) {
                            rcvConn.close();
                            rcvConn = null;
                        }
                    } catch (IOException ex) { /* ignore */ }
                }
            }
        } finally {
            thread = null;
        }
    }

}
