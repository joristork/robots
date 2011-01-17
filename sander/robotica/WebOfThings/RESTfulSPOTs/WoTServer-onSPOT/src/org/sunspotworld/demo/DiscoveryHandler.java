/*
 * Copyright (c) 2009, Sun Microsystems
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the Sun Microsystems nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.sunspotworld.demo;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import com.sun.spot.ipv6.IPUtils;
import com.sun.spot.peripheral.IDeepSleepListener;
import com.sun.spot.peripheral.TimeoutException;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import java.util.Date;
import javax.microedition.io.Connector;
import java.util.Vector;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;



/**
 *
 * @author vgupta
 */
public class DiscoveryHandler extends Thread implements IDeepSleepListener {
    private static long TIMEOUT_INTERVAL = 3000;

    boolean done = false;
    UDPConnection conn = null;
    UDPDatagram dg = null;
    Vector gwAddrs = new Vector();
    private static GatewayInfo gwCurrent = null;
    GatewayInfo gwCandidate = null;

    public static String getCurrentGatewayAddr() {
       if (gwCurrent == null)
           return "";
       else
           return gwCurrent.getAddr();
    }

    DiscoveryHandler() {
        try {
            conn = (UDPConnection) Connector.open("udp://:" +
                    WoTConstants.DISCOVERY_PORT);
            conn.setTimeout(TIMEOUT_INTERVAL);
            dg = (UDPDatagram) conn.newDatagram(conn.getMaximumLength());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        GatewayInfo gi = null;
        long currentTime = 0L;
        long lastTime = 0L;
        
        if (conn == null) {
            System.err.println("No available connection for discovery thread");
            return;
        }

//        (new Thread() {
//            public void run() {
//                GatewayInfo gi = null;
//                long currentTime = 0L;
//
//                System.out.println("Starting expiry thread for device");
//                while (!done) {
//                    try {
//                        Thread.sleep(10000);
//                        gwCandidate = null;
//                        currentTime = System.currentTimeMillis();
//                        for (int i = 0; i < gwAddrs.size();) {
//                            gi = (GatewayInfo) gwAddrs.elementAt(i);
//                            if ((gi != null) &&
//                                    (currentTime > gi.getExpirationTime())) {
//                                if ((gwCurrent != null) && gwCurrent.equals(gi)) {
//                                    System.out.println("*** Removing current gateway ***");
//                                    gwCurrent = null;
//                                }
//                                gwAddrs.removeElementAt(i);
//                            } else {
//                                gwCandidate = gi;
//                                i++;
//                            }
//                        }
//
//                        if ((gwCurrent == null) && (gwCandidate != null)) {
//                            gwCurrent = gwCandidate;
//                            System.out.println("*** Found new gateway ***\n" + gwCurrent);
//                        }
//
//                        printState();
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        }).start();

        System.out.println(MyUtils.getTimeStamp() + "Starting discovery " +
                "thread for device on port " + WoTConstants.DISCOVERY_PORT);
        while (!done) {         
            try {
                //Thread.sleep(5000);
                dg.reset();
                conn.receive(dg);
//                System.out.println(MyUtils.getTimeStamp() + "Received datagram from " +
//                        dg.getAddress() + " ...");
                parseAdvertisement(dg.getData());
                printState();
            } catch (TimeoutException ex) {
//                System.out.print(".");
            } catch (Exception ex) {
                System.err.println(MyUtils.getTimeStamp() + "Caught " + ex +
                        " in DiscoveryThread run.");
                ex.printStackTrace();
            }

            // Go through the list and remove expired entries
            currentTime = System.currentTimeMillis();

            if (currentTime >= lastTime + 
                    (WoTConstants.DISCOVERY_PERIOD * 1000)) {
//                System.out.println(MyUtils.getTimeStamp() + "Checking ...");
                lastTime = currentTime;
                gwCandidate = null;
                for (int i = 0; i < gwAddrs.size();) {
                    gi = (GatewayInfo) gwAddrs.elementAt(i);
                    if ((gi != null) &&
                            (currentTime > gi.getExpirationTime())) {
                        if ((gwCurrent != null) && gwCurrent.equals(gi)) {
                            System.out.println(MyUtils.getTimeStamp() +
                                    "*** Removing current gateway ***");
                            gwCurrent = null;
                        }
                        gwAddrs.removeElementAt(i);
                    } else {
                        gwCandidate = gi;
                        i++;
                    }
                }

                if ((gwCurrent == null) && (gwCandidate != null)) {
                    gwCurrent = gwCandidate;
                    System.out.println(MyUtils.getTimeStamp() +
                            "*** Found new gateway ***\n" + gwCurrent);
                }

                if (gwCurrent != null) {
                    // keep renewing periodically
                    try {
                        HttpResponse resp =
                                sendRegistration(gwCurrent.getAddr());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                printState();
            } else {
//                System.out.print("*");
            }           
        }

        System.out.println(MyUtils.getTimeStamp() + "Discovery thread stopped.");
    }

    public void stop() {
        System.out.println(MyUtils.getTimeStamp() + "Stopping discovery thread ...");
        done = true;
    }

    private synchronized void parseAdvertisement(byte[] data) {
        int idx = 0;
        int last = 0;
        GatewayInfo gw = null;
        long now = System.currentTimeMillis();
        int ttl = 0;
        int pos = -1;

        // We assume that the first byte is the TTL in seconds
        ttl = data[idx++] & 0xff;
        last = 1;
        while (true) {
            while ((idx < data.length) && (data[idx] != 0x00)) idx++;
            if (idx >= data.length) break;
            String svcAddr = new String(data, last, idx - last);
            idx++;
            last = idx;

            // Filter out gateways we are not interested in ...
            if (!svcAddr.startsWith("udp://[")) continue;
            gw = new GatewayInfo(svcAddr, now, ttl);            
            if ((pos = gwAddrs.indexOf(gw)) != -1) {
                ((GatewayInfo) gwAddrs.elementAt(pos)).updateTimes(now, ttl);
            } else {
                gwAddrs.addElement(gw);
            }
        }
    }

    public synchronized void printState() {
        System.out.println(MyUtils.getTimeStamp() + "Gateway list has " +
            gwAddrs.size() + " entries:");
        for (int i = 0; i < gwAddrs.size(); i++) {
            System.out.println("------------------");
            System.out.println(gwAddrs.elementAt(i));
        }
        if (gwAddrs.size() > 0) System.out.println("------------------");
    }

    private HttpResponse sendRegistration(String connStr) throws IOException {
        // Create a registration request ...
        HttpRequest req = new HttpRequest();
        HttpClient httpClient = new HttpClient(connStr,
                WoTConstants.USES_COMPRESSION);

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        String name = WoTConstants.SPOT_PREFIX +
                IEEEAddress.toDottedHex(ourAddr).substring(15);
        String conType = WoTConstants.SVC_CONNECTION_TYPE;
        String addr = "[" + IPUtils.ieeeToIPv6Address(
                IEEEAddress.toDottedHex(ourAddr)).toString() + "]" +
                ":" + WoTConstants.TCP_UDP_SVCPORT;

        String reqBody = "name " + name + "\n" + "ctype " + conType + "\n" +
                "addr " + addr + "\n" + "compression " +
                (WoTConstants.USES_COMPRESSION ? "true" : "false") + "\n" +
                "lifetime " + WoTConstants.REG_LIFETIME;

        req.setMethod("POST");
        req.setPathInfo("/rg");
        req.setBody(reqBody.getBytes());
        HttpResponse res = httpClient.sendData(req);
        httpClient.shutDown();
        System.out.println(MyUtils.getTimeStamp() + 
                "Registration response from " + gwCurrent.getAddr() + " is: " +
                res.getStatus() + " (" +
                res.getStatusString(res.getStatus()) + ")");
        return res;
    }

    public void awakeFromDeepSleep() {
        ITriColorLEDArray myLEDs;

        myLEDs = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        
        
        if (gwCurrent != null) {
            myLEDs.getLED(3).setRGB(0, 255, 0);
            myLEDs.getLED(3).setOn();
            Utils.sleep(100);
            myLEDs.getLED(3).setOff();
            Utils.sleep(100);
            myLEDs.getLED(5).setRGB(0, 255, 0);
            myLEDs.getLED(5).setOn();
            Utils.sleep(100);
            myLEDs.getLED(5).setOff();
            Utils.sleep(100);
            myLEDs.getLED(3).setOn();
            try {
                HttpResponse resp =
                        sendRegistration(gwCurrent.getAddr());
            } catch (IOException ioe) {
                myLEDs.getLED(0).setRGB(0, 0, 255);
                ioe.printStackTrace();
            }
        } else {
            myLEDs.getLED(4).setRGB(255, 0, 0);
            myLEDs.getLED(4).setOn();
            Utils.sleep(100);
            myLEDs.getLED(4).setOff();
            Utils.sleep(100);
            myLEDs.getLED(4).setOn();
            Utils.sleep(100);
        }


        Utils.sleep(50);
        myLEDs.getLED(0).setRGB(0, 0, 0);
        myLEDs.getLED(4).setRGB(0, 0, 0);
        myLEDs.getLED(4).setOff();
    }
}

class GatewayInfo {
    private String addr = "";
    private long lastHeard = 0; // UNIX epoch time
    private long ttl = 0; // in seconds

    GatewayInfo(String addr, long lastHeard, int ttl) {
        this.addr = addr;
        this.lastHeard = lastHeard;
        this.ttl = ttl;
    }

    public void updateTimes(long lastHeard, int ttl) {
        this.lastHeard = lastHeard;
        this.ttl = ttl;
    }

    public long getExpirationTime() {
        return lastHeard + (ttl * 1000);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GatewayInfo other = (GatewayInfo) obj;
        if ((this.getAddr() == null) ? (other.getAddr() != null) : !this.addr.equalsIgnoreCase(other.addr)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getAddr().toLowerCase().hashCode();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("Addr: " + getAddr() + "\n");
        sb.append("Heard: " + new Date(lastHeard) + "\n");
        sb.append("TTL: " + ttl + "\n");
        return sb.toString();
    }

    /**
     * @return the addr
     */
    public String getAddr() {
        return addr;
    }
}

