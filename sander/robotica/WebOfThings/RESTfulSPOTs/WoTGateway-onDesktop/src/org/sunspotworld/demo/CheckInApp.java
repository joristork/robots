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

import com.sun.spot.wot.gateway.Device;
import com.sun.spot.wot.gateway.DeviceManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;


/**
 *
 * @author vgupta
 */
public class CheckInApp extends WebApplication {

    CheckInApp(String string) {
        super(string);
    }

    @Override
    public void init() {
        System.out.println("Started CheckInApp ...");
    }

//    private int parseIntFromHeader(HttpRequest req, String hdrName) {
//        int val = -1;
//        try {
//            val = Integer.parseInt(req.getHeader(hdrName));
//        } catch (Exception e) {
//            System.err.println("Caught " + e + " in parseIntFromHeader " +
//                    "when parsing " + req.getHeader(hdrName));
//        }
//        return val;
//    }
//
//    public HttpResponse processRequest(HttpRequest req) {
//        HttpResponse res = new HttpResponse();
//        String name = "";
//        int awakeDuration = -1;
//        int sleepDuration = -1;
//        System.out.println("Handling checkin request ...");
//
//        name = req.getHeader("name");
//        awakeDuration = parseIntFromHeader(req, "awake-time");
//        sleepDuration = parseIntFromHeader(req, "sleep-time");
//        if ((awakeDuration == -1) && (sleepDuration == -1)) {
//            res.setStatus(HttpResponse.SC_BAD_REQUEST);
//        } else {
//            Device dev = DeviceManager.findByName(name);
//            if (dev == null) {
//                System.out.println("Did not find device " + name);
//                res.setStatus(HttpResponse.SC_NOT_FOUND);
//            } else {
//                dev.handleCheckIn(awakeDuration, sleepDuration);
//                res.setStatus(HttpResponse.SC_OK);
//            }
//        }
//
//        return res;
//    }

    private int parseIntFromStr(String intStr) {
        int val = -1;
        try {
            val = Integer.parseInt(intStr);
        } catch (Exception e) {
            System.err.println("Caught " + e + " in parseIntFromStr " +
                    "when parsing " + intStr);
        }
        return val;
    }

    public HttpResponse processRequest(HttpRequest req) throws IOException {
        HttpResponse res = new HttpResponse();
        String name = "";
        int awakeDuration = -1;
        int sleepDuration = -1;
        byte[] body = null;
        String line = null;

//        System.out.println("Handling checkin request ...");
        
        if (!req.getMethod().equalsIgnoreCase("POST")) {
            res.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
            res.setHeader("Allow", "POST");
        } else if ((body = req.getBody()) == null) {
            res.setStatus(HttpResponse.SC_BAD_REQUEST);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new ByteArrayInputStream(body, 0, body.length)));

            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length != 2) {
                    System.out.println("Ignoring line <" + line + ">");
                } else {
                    if (tokens[0].equalsIgnoreCase("name")) {
                        name = tokens[1];
                    } else if (tokens[0].equalsIgnoreCase("awake-time")) {
                        awakeDuration = parseIntFromStr(tokens[1].trim());
                    } else if (tokens[0].equalsIgnoreCase("sleep-time")) {
                        sleepDuration = parseIntFromStr(tokens[1].trim());
                    } else {
                        System.out.println("Ignoring line <" + line + ">");
                    }
                }
            }


            if (name.equals("") || (awakeDuration == -1) ||
                    (sleepDuration == -1)) {
                res.setStatus(HttpResponse.SC_BAD_REQUEST);
            } else {
                Device dev = DeviceManager.findByName(name);
                if (dev == null) {
//                    System.out.println("Did not find device " + name);
                    res.setStatus(HttpResponse.SC_NOT_FOUND);
                } else {
                    dev.handleCheckIn(awakeDuration, sleepDuration);
                    res.setStatus(HttpResponse.SC_OK);
                }
            }
        }
        return res;
    }
}
