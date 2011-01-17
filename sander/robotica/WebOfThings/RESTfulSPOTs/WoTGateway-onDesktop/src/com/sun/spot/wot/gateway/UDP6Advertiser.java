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
package com.sun.spot.wot.gateway;

import com.sun.spot.io.j2me.udp.UDPConnection;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 *
 * @author vgupta
 */
public class UDP6Advertiser extends Thread {
    private UDPConnection rCon = null;
    private Datagram dg = null;
    private boolean done = false;
    byte[] advertisementBytes;
    private int period;
    private long lastSent = 0;
    private boolean initOk = false;
    private Date d = new Date();
    
    public UDP6Advertiser(byte[] advertisementBytes, int period) throws Exception {
        if (advertisementBytes != null) {
            this.advertisementBytes = new byte[advertisementBytes.length];
            System.arraycopy(advertisementBytes, 0, this.advertisementBytes, 0,
                    advertisementBytes.length);
        }
        this.period = period;
        try {
            rCon = (UDPConnection) Connector.open("udp://" + "broadcast" + ":" +
                    //"[fe80:0:0:0:0214:4f01:0000:5317]:" +
                    Constants.UDP6_ADVPORT);
            dg = rCon.newDatagram(rCon.getMaximumLength());
            //dg.reset(); /// why do I need this??
            dg.write(advertisementBytes, 0, advertisementBytes.length);
            initOk = true;
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
            e.printStackTrace();
            System.exit(1);
        }

        if (!initOk) {
            System.out.println("UDPAdvertiser initialization failed");
        } else {
            System.out.println("UDPAdvertiser started (will broadcast on port " +
                    Constants.UDP6_ADVPORT + ")");
        }
    }

    public void markDone() {
        done = true;
    }

    public void sendUnsolicitedAdv() {
        try {
            rCon.send(dg);
            lastSent = System.currentTimeMillis();
            d.setTime(lastSent);
            System.out.println(d.toString() + ": Sent unsolicited UDP advertisement on port " +
                    Constants.UDP6_ADVPORT);
        } catch (IOException ex) {
            System.err.println("Caught " + ex);
            ex.printStackTrace();
            Logger.getLogger(UDP6Advertiser.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            try {
                while (!done) {
                    try {
                        if (System.currentTimeMillis() > (lastSent + period)) {
                            sendUnsolicitedAdv();
                        }
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                        Logger.getLogger(UDP6Advertiser.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Shutting down UDP Advertiser");

            } finally {
                rCon.close();
            }
        } catch (IOException ex) {
            System.err.println("Caught " + ex + " in UDP6Advertiser run");
            ex.printStackTrace();
        }
    }
}
