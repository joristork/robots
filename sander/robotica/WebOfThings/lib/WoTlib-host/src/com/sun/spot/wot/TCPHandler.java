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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 *
 * @author vgupta
 */
public class TCPHandler extends Thread {
    NanoAppServer nas = null;
    int port = 0;
    ServerSocket ss = null;

    public TCPHandler(int port, NanoAppServer nas) throws IOException {
        this.port = port;
        this.nas = nas;
        ss = new ServerSocket(port);
    }

    public void run() {
        boolean done = false;
        HttpRequest req = null;
        HttpResponse res = null;
        System.out.println("Starting TCP handler on port " + port + "...");

        while (!done) {
            try {
                new SocketHandler((Socket) ss.accept(), nas).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Shutting down TCP handler on port " + port);

        try {
            ss.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class SocketHandler extends Thread {
    private static int MAX_HEADER_SIZE = 1024;

    Socket sock = null;
    NanoAppServer nas = null;

    public SocketHandler(Socket sock, NanoAppServer nas) {
        this.sock = sock;
        this.nas = nas;
    }

    public void run() {
        InputStream is = null;
        OutputStream os = null;
        byte[] writeBuf = null;
        int i = 0;
        boolean done = false;
        Date d = new Date();

        try {
            d.setTime(System.currentTimeMillis());
//            System.out.println(d.toString() + ": Received TCP connection from " +
//                    sock.getInetAddress().getHostAddress());
            is = sock.getInputStream();
            os = sock.getOutputStream();

            while (!done) {
                HttpRequest req = new HttpRequest();
                HttpResponse res = new HttpResponse();

                try {
                    d.setTime(System.currentTimeMillis());
                    req.parse(is);
                    d.setTime(System.currentTimeMillis());
                    System.out.println(d.toString() + ": Received " +
                            req.getMethod() + " " + req.getPathInfo() +
                            ((req.getQueryString() == null) ?
                                "":
                                "?"+req.getQueryString()) + " over TCP from " +
                            ((InetSocketAddress) sock.getRemoteSocketAddress()).getAddress().getHostAddress() +
                            ", port " + sock.getPort());
                } catch (Exception ex) {
                    // If there is a problem reading the request, return BAD_INPUT
                    System.out.println("Caught " + ex);
                    req = null;
                    done = true;
                    res.setStatus(HttpResponse.SC_BAD_REQUEST);
                }

                if (req != null) {
                    res = nas.getResponse(req);

                    String connHdr = req.getHeader("Connection");
                    if (true || // Always force connection close
                            ((connHdr != null) &&
                            connHdr.equalsIgnoreCase("close"))) {
                        done = true;
                        res.setHeader("Connection", "close");
                    }
                }

                writeBuf = new byte[((res.getBody() == null) ? 0:res.getBody().length) +
                        MAX_HEADER_SIZE];
                int wlen = res.toByteArray((req == null) ?
                    false : req.isCompressed(),
                    writeBuf, 0);
                
                d.setTime(System.currentTimeMillis());
                System.out.println(d.toString() + ": Sent code " +
                        res.getStatus() + " over TCP to " +
                        sock.getRemoteSocketAddress());
                os.write(writeBuf, 0, wlen);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sock != null) {
                try {
//                    System.out.println("Closing TCP connection from " +
//                            sock.getInetAddress().toString());
                    sock.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
