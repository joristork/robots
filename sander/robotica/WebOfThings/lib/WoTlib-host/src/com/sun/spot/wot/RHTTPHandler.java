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
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class RHTTPHandler extends Thread {
    private int MAX_HEADER_SIZE = 1024;
    private URL url = null;
    private int prefixLen = 0; // This is the URL prefix that gets stripped away
    private Socket sock = null;
    private NanoAppServer ar = null;

    public RHTTPHandler(String url, NanoAppServer ar) {
        try {
            this.url = new URL(url);
            this.ar = ar;
        } catch (MalformedURLException ex) {
            Logger.getLogger(RHTTPHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RHTTPHandler(String url, int stripLen, NanoAppServer ar) {
        try {
            this.url = new URL(url);
            this.ar = ar;
            this.prefixLen = (stripLen > 0) ? stripLen : 0;
        } catch (MalformedURLException ex) {
            Logger.getLogger(RHTTPHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        HttpRequest req = new HttpRequest();
        HttpRequest innerReq = new HttpRequest();
        HttpResponse res = new HttpResponse();
        HttpResponse innerRes = new HttpResponse();
        InputStream is = null;
        OutputStream os = null;
        byte[] buf = null;

        while (true) {
            try {
                //Thread.sleep(2000);
                // Open up an HTTP connection to the URL
                System.out.println("Starting RHTTP handler for URL " + url);
                sock = new Socket(url.getHost(), url.getPort());
                is = sock.getInputStream();
                os = sock.getOutputStream();

                System.out.println("Connected to " + url.getHost() + ":" +
                        url.getPort());

//                req.setMethod("POST");
//                req.setPathInfo(url.getPath());
//                req.setHeader("Upgrade", "PTTH/1.0");
//                req.setHeader("Connection", "Upgrade");
//                req.setHeader("Host", url.getHost());
//                buflen = req.toByteArray(false, buf, 0);
//                System.out.println("Sent request\n" + req);
//                os.write(buf, 0, buflen);

                String reqStr = "POST " + url.getPath() + " HTTP/1.1\r\n" +
                        "Upgrade: PTTH/1.0\r\n" +
                        "Connection: Upgrade\r\n" +
                        "Host: " + url.getHost() + "\r\n\r\n";
                System.out.println("Sending request\n" + reqStr);
                os.write(reqStr.getBytes());

                os.flush();
                System.out.println("Waiting for response ...");
                res.parse(is);
                System.out.println("Got response\n" + res);
                if (res.getStatus() == HttpResponse.SC_SWITCHING_PROTOCOLS) {
//                    byte[] tunneledReqBytes = res.getBody();
//                    if (tunneledReqBytes != null) {
//                        innerReq = HttpRequest.parse(tunneledReqBytes,
//                                0, tunneledReqBytes.length);
//                        System.out.println("Inner request is\n" + innerReq);
                        boolean haveInnerReq = false;
                        try {
                            innerReq.parse(is);
                            //System.out.println("Inner req is\n" + innerReq);
                            // Strip off prefix in the request URI
                            innerReq.setPathInfo(
                                    innerReq.getPathInfo().substring(prefixLen));
                            if (innerReq.getPathInfo().equals("")) {
                                innerReq.setPathInfo("/");
                            }

                            innerRes = ar.getResponse(innerReq);
                            haveInnerReq = true;
                        } catch (Exception e) {
                            System.out.println("Could not parse inner request");
                            e.printStackTrace();
                            innerRes.setStatus(HttpResponse.SC_BAD_REQUEST);
                        }

                        // Force connection close
                        innerRes.setHeader("Connection", "close");
                        buf = new byte[(innerRes.getBody() == null ? 0: innerRes.getBody().length) +
                                MAX_HEADER_SIZE];
                        int wlen = innerRes.toByteArray((haveInnerReq &&
                            innerReq.isCompressed()), buf, 0);

                        System.out.println("Inner response is:\n" + innerRes);
                        os.write(buf, 0, wlen);
                        os.flush();
//                    }
                }
            } catch (ConnectException ex) {
                // We had a connection exception so wait a while before retrying
                System.err.println("Caught " + ex);
                ex.printStackTrace();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(RHTTPHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (Exception ex) {
                System.err.println("Caught " + ex);
                ex.printStackTrace();
            } finally {
                try {
                    System.out.println("Closing reversehttp connection to " +
                            url.getHost() + ":" + url.getPort());
                    if (sock != null) sock.close();
                } catch (IOException ex) {
                    Logger.getLogger(RHTTPHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
