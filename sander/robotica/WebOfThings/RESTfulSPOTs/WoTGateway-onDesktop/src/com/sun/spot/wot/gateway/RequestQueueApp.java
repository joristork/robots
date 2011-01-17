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

import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class RequestQueueApp extends WebApplication {

    public RequestQueueApp() {
    }

    public RequestQueueApp(String string) {
        super(string);
    }
    
    @Override
    public void init() {
        System.out.println("Started RequestQueue manager ...");
    }

    @Override
    public HttpResponse processRequest(HttpRequest req) {
        int idx = -1;
        String pathInfo = req.getPathInfo();
        HttpResponse res = new HttpResponse();

        if ((pathInfo == null) || ((idx = pathInfo.indexOf("/", 1)) < 1)) {
            res.setStatus(HttpResponse.SC_BAD_REQUEST);
        } else {
            String deviceId = pathInfo.substring(1, idx);
            String reqId = pathInfo.substring(idx + 1);
//            System.out.println("Device Id is " + deviceId +
//                    " in RequestQueueServlet, requestId=" + reqId);
            Device dev = DeviceManager.findByName(deviceId);
            if (dev == null) {
//                System.out.println("Did not find device " + deviceId);
                res.setStatus(HttpResponse.SC_NOT_FOUND);
            } else {

//                System.out.println("Handling status check for request <" +
//                        reqId + ">");
                RequestQueueEntry rqe = dev.getRequestQueue().findRequest(reqId);
                if (rqe == null) {
//                    System.out.println("Did not find request " + reqId +
//                            " for device " + deviceId);
                    //res.setStatus(HttpResponse.SC_NOT_FOUND);
                    // Show all requests ...
                    res.setStatus(HttpResponse.SC_OK);
                    res.setHeader("Content-Type", "text/plain");
                    byte[] body = ("Request queue for device " + deviceId +
                            "\n===========================\n" +
                            dev.getRequestQueue().toString()).getBytes();
                    res.setBody(body);
                } else {
//                    System.out.println("Found request " + reqId +
//                            " for device " + deviceId);
                    res.setStatus(HttpResponse.SC_OK);
                    res.setHeader("Content-Type", "text/plain");
                    byte[] body = rqe.toString().getBytes();
                    res.setBody(body);
                }
            }
        }

        return res;
    }
}
