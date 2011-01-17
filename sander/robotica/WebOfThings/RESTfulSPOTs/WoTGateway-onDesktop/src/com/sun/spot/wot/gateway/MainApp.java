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

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class MainApp extends WebApplication {
    private int i = 0;
    private String ourIPaddrAndPort = "localhost" + ":" +
            Constants.TCP_SVCPORT;

    public MainApp(String string) {
        super(string);
    }
    
    @Override
    public void init() {       
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            ourIPaddrAndPort = address + ":" + Constants.TCP_SVCPORT;
        } catch (Exception e) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        Utils.setBaseURL("http://" + ourIPaddrAndPort);
        System.out.println("Gateway started on URL http://" + ourIPaddrAndPort);
    }
    
    private String extractAddress(String uri) {
        int idx = uri.indexOf("/", 1);

        if (idx < 0) {
            idx = uri.length();
        }

        return uri.substring(1, idx);
    }

    public HttpResponse processRequest(HttpRequest req) {
        HttpResponse res = new HttpResponse();

        // Check if this is a request for the base URL ...
        if (req.getPathInfo().equalsIgnoreCase("/") ||
                req.getPathInfo().equals("")) {
            res.setStatus(HttpResponse.SC_MOVED_PERMANENTLY);
            res.setHeader("Location", "/doc/index.html");

            return res;
        }

        if (req.getPathInfo().equalsIgnoreCase("/spots")) {
            res.setStatus(HttpResponse.SC_OK);
            res.setHeader("Content-Type", "text/plain");
            String devList = DeviceManager.getDeviceList("text");
            res.setHeader("Content-Length", "" + devList.getBytes().length);
            res.setBody(devList.getBytes());
            return res;
        }

        // Check if this is a request to list all available devices ...
        if (req.getPathInfo().equalsIgnoreCase("/devices.json")) {
            res.setStatus(HttpResponse.SC_OK);
            res.setHeader("Content-Type", "text/plain");
            String devList = DeviceManager.getDeviceList("JSON");
            res.setHeader("Content-Length", "" + devList.getBytes().length);
            res.setBody(devList.getBytes());
            return res;
        }

        // Check if this is a request to list all available devices ...
        if (req.getPathInfo().equalsIgnoreCase("/devices.html")) {
            res.setStatus(HttpResponse.SC_OK);
            res.setHeader("Content-Type", "text/html");
            String devList = DeviceManager.getDeviceList("HTML");
            res.setHeader("Content-Length", "" + devList.getBytes().length);
            res.setBody(devList.getBytes());
            return res;
        }


        // Figure out the specific device this request is for
        String deviceId = extractAddress(req.getPathInfo());
        Device dev = DeviceManager.findByName(deviceId);

        // If we don't know of the device, report a "Not Found" error .
        if (dev == null) {
//            System.out.println("Did not find device " + deviceId);
            res.setStatus(HttpResponse.SC_NOT_FOUND);
            return res;
        }

        // We have a known device ... prepare request for forwarding or
        // cache look up
        req.removeFirstPathSegment(); // Remove device name

        // Check the cache first ...
        res = dev.getResourceCache().getCachedResponse(req);
        if (res != null) {
//            System.out.println("Returning cached response ...\n" +
//                    res.toString());
            return res;
        } else {
//            System.out.println("Nothing suitable in cache ...\n");
            res = new HttpResponse();
        }

        // Couldn't satisfy request from cache ...
        if (dev.isInaccessible()) {
            int tmp = (int) (1 + (dev.getRemainingInaccessibleDuration()/1000));
//            System.out.println("Device is inaccessible for " + tmp + " sec");
            res.setStatus(HttpResponse.SC_GATEWAY_TIMEOUT);
            res.setHeader("Retry-After", "" + tmp);
        } else if (dev.isSleeping()) {
//            System.out.println("Device is sleeping ...");
            // Queue the request and return an "Accepted" response
            String reqId = null;
            if ((reqId = dev.getRequestQueue().insertRequest(req)) != null) {
                // Added request to queue ...
//                System.out.println("Added request [id=" + reqId +
//                        "] to queue for device " + dev.getId() + "...");
                String relativeURL = "/rq/" + dev.getId() +
                        "/" + reqId;
                String locationURL = "http://" + ourIPaddrAndPort + relativeURL;
                res.setStatus(HttpResponse.SC_ACCEPTED);
                res.setHeader("Location", locationURL);
                res.setHeader("Retry-After", "" +
                        (2 + dev.getRemainingSleepDuration()/1000));
                res.setHeader("Content-Type", "text/html");
                String bodyText = "<html><body>Request has been queued for later " +
                        "processing. <a href=\"" + relativeURL +
                        "\">Check Status</a></body></html>\r\n";
                res.setBody(bodyText.getBytes());
            } else {
                // The queue is full ...
//                System.out.println("Could not add request to queue ...");
                res.setStatus(HttpResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            // If device is awake, relay the request and resulting response
//            System.out.println("Device is awake ...");
            res = dev.getDeviceResponse(req);
        }

        return res;
    }
}
