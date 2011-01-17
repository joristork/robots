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

import com.sun.spot.ipv6.tcp.ServerSocket;
import com.sun.spot.ipv6.tcp.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.utils.PrettyPrint;
import java.util.Date;


/**
 *
 * @author vgupta
 */
public class TCP6Handler extends Thread {
    NanoAppServer nas = null;
    int port = 0;
    ServerSocket ss = null;

    public TCP6Handler(int port, NanoAppServer nas) throws IOException {
        this.port = port;
        this.nas = nas;
        ss = new ServerSocket(port);
    }

    public void run() {
        boolean done = false;

        System.out.println("Starting TCP6 handler on port " + port + "...");

        while (!done) {
            try {
                new Socket6Handler((Socket) ss.accept(), nas).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Shutting down TCP6 handler on port " + port);

        try {
            ss.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class Socket6Handler extends Thread {
    private static int MAX_RESPONSE_SIZE = 2048;

    Socket sock = null;
    NanoAppServer nas = null;

    public Socket6Handler(Socket sock, NanoAppServer nas) {
        this.sock = sock;
        this.nas = nas;
    }

    public void run() {
        InputStream is = null;
        OutputStream os = null;
        byte[] writeBuf = new byte[MAX_RESPONSE_SIZE];
        boolean done = false;
        Date d = new Date();

        try {
            is = sock.getInputStream();
            os = sock.getOutputStream();

            while (!done) {
                HttpRequest req = new HttpRequest();
                HttpResponse res = new HttpResponse();

                try {
                    req.parse(is);
                    d.setTime(System.currentTimeMillis());
                    System.out.println(d.toString() + ": Received " +
                            req.getMethod() + " " + req.getPathInfo() +
                            ((req.getQueryString() == null) ?
                                "":
                                "?"+req.getQueryString()) + " over TCP from " +
                            sock.getRemoteAddress() + ", port " + 
                            sock.getRemotePort());
                } catch (Exception ex) {
                    // If there is a problem reading the request, return BAD_INPUT
                    System.err.println("Caught " + ex + " in TCP6Handler");
                    ex.printStackTrace();
                    req = null;
                    done = true;
                    res.setStatus(HttpResponse.SC_BAD_REQUEST);
                }

                if (req != null) {
                    res = nas.getResponse(req);

                    String connHdr = req.getHeader("Connection");
                    if ((connHdr != null) &&
                            connHdr.equalsIgnoreCase("close")) {
                        done = true;
                        res.setHeader("Connection", "close");
                    }
                }

                int wlen = res.toByteArray((req == null) ?
                    false : req.isCompressed(),
                    writeBuf, 0);
                
                d.setTime(System.currentTimeMillis());
                System.out.println(d.toString() + ": Sent code " + 
                        res.getStatus() + " over TCP to " + 
                        sock.getRemoteAddress());

                os.write(writeBuf, 0, wlen);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sock != null) {
                try {
                    System.out.println("Closing TCP6 connection ...");
                    sock.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
