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

import com.sun.spot.ipv6.tcp.Socket;
import com.sun.spot.peripheral.ota.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class SPOTRHTTPHandler extends Thread {
    private int MAX_HEADER_SIZE = 1024;
    private URL url = null;
    private Socket sock = null;
    private NanoAppServer ar = null;

    public SPOTRHTTPHandler(String url, NanoAppServer ar) {
        this.url = new URL(url);
        this.ar = ar;
    }

    public void run() {
        HttpRequest req = new HttpRequest();
        HttpRequest innerReq = new HttpRequest();
        HttpResponse res = new HttpResponse();
        HttpResponse innerRes = new HttpResponse();
        InputStream is = null;
        OutputStream os = null;
        byte[] buf = null;
        SocketConnection sc = null;

        System.out.println("Starting SPOTRHTTP handler ... ");
        while (true) {
            try {
                Thread.sleep(2000);
                // Open up an HTTP connection to the URL
                sc = (SocketConnection)
                        Connector.open("socket://" + "sensor.network.com:1234");
//                        url.getAddress() + ":" +
//                        url.getPort());

                is = sc.openInputStream();
                os = sc.openOutputStream();

                System.out.println("Connected to " + "sensor.network.com:1234");
//                        url.getAddress() + ":" +
//                        url.getPort());

//                req.setMethod("POST");
//                req.setPathInfo(url.getAddress());
//                req.setHeader("Upgrade", "PTTH/1.0");
//                req.setHeader("Connection", "Upgrade");
//                req.setHeader("Host", url.getAddress());
//                buflen = req.toByteArray(false, buf, 0);
//                System.out.println("Sent request\n" + req);
//                os.write(buf, 0, buflen);

                String reqStr = "POST " + "/warn" + " HTTP/1.1\r\n" +
                        "Upgrade: PTTH/1.0\r\n" +
                        "Connection: Upgrade\r\n" +
                        "Host: " + url.getAddress() + "\r\n\r\n";
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
                            innerRes = ar.getResponse(innerReq);
                            haveInnerReq = true;
                        } catch (Exception e) {
                            System.out.println("Could not parse inner request");
                            e.printStackTrace();
                            innerRes.setStatus(HttpResponse.SC_BAD_REQUEST);
                        }

                        buf = new byte[(res.getBody() == null? 0:res.getBody().length) +
                                MAX_HEADER_SIZE];
                        int wlen = innerRes.toByteArray((haveInnerReq &&
                            innerReq.isCompressed()), buf, 0);

                        System.out.println("Inner response is:\n" + innerRes);
                        os.write(buf, 0, wlen);
                        os.flush();
//                    }
                }
            } catch (Exception ex) {
                System.err.println("Caught " + ex);
                ex.printStackTrace();
            } finally {

                try {
                    System.out.println("Closing reversehttp connection to " +
                            "sensor.network.com:1234"); //127.0.0.1:8080");
//                            url.getAddress() + ":" + url.getPort());
                    if (is != null) is.close();
                    if (os != null) os.close();
                    if (sc != null) sc.close();
//                    sock.close();
                } catch (IOException ex) {
                    System.err.println("Caught " + ex);
                    ex.printStackTrace();
                }
            }
        }
    }
}
