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

package com.sun.spot.wot;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import java.io.IOException;
import java.util.Date;
import javax.microedition.io.Connector;
import javax.microedition.io.DatagramConnection;
import com.sun.spot.wot.http.EncodingException;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class UDP6Handler extends Thread {
    DatagramConnection conn = null;
    NanoAppServer nas = null;
    int maxlen = 0;
    int port = 0;

    public UDP6Handler(int port, NanoAppServer nas) throws IOException {
        this.nas = nas;
        this.port = port;

        conn = (UDPConnection) Connector.open("udp://:" + port);
        maxlen = conn.getMaximumLength();
    }

    public void run() {
        UDPDatagram dg = null;
        boolean done = false;
        byte[] reqBytes = null;
        byte[] resBytes = null;
        HttpRequest req = null;
        HttpResponse res = null;
        Date d = new Date();

        System.out.println(d.toString() + ": Starting UDP6 handler on port " +
                port + "...");
        while (!done) {
            try {
                req = null;
                res = new HttpResponse();
                dg = (UDPDatagram) conn.newDatagram(maxlen);
                dg.reset();
                try {
                    conn.receive(dg);
                    d.setTime(System.currentTimeMillis());
//                    srcAddr = dg.getSrcAddress();
//                    srcPort = dg.getSrcPort();
                    reqBytes = dg.getData();
                    req = HttpRequest.parse(reqBytes, 0, reqBytes.length);
                    System.out.println(d.toString() + ": Received " +
                            req.getMethod() + " " + req.getPathInfo() +
                            ((req.getQueryString() == null) ?
                                "":
                                "?"+req.getQueryString()) + " over UDP from "
                            + dg.getAddress() + ", port " + dg.getSrcPort());
                } catch (Exception ex) {
                    // If there is a problem reading the request, return BAD_INPUT
                    d.setTime(System.currentTimeMillis());
                    System.err.println(d.toString() +
                            ": Caught " + ex + " in UDP6Handler");
                    ex.printStackTrace();
                    req = null;
                    res.setStatus(HttpResponse.SC_BAD_REQUEST);
                }

                if (req != null) {
                    res = nas.getResponse(req);
                }

                resBytes = new byte[maxlen];
                int wlen = res.toByteArray((req == null) ? false :
                    req.isCompressed(), resBytes, 0);
                UDPDatagram ndg = (UDPDatagram) conn.newDatagram(maxlen);
                ndg.reset();
                
                //ndg.setDstAddress(dg.getSrcAddress());
                //ndg.setDstPort(dg.getSrcPort());
                ndg.setAddress(dg); // Sets both address and port
                ndg.write(resBytes, 0, wlen);
                d.setTime(System.currentTimeMillis());
                System.out.println(d.toString() + ": Sent code " + 
                        res.getStatus() + " over UDP to " + dg.getAddress());
                conn.send(ndg);
            } catch (EncodingException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
