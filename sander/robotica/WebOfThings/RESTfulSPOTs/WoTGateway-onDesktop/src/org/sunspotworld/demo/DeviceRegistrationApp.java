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

import com.sun.spot.wot.gateway.Constants;
import com.sun.spot.wot.gateway.Device;
import com.sun.spot.wot.gateway.DeviceForwarder;
import com.sun.spot.wot.gateway.DeviceManager;
import com.sun.spot.wot.gateway.Utils;
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
public class DeviceRegistrationApp extends WebApplication {

    DeviceRegistrationApp(String string) {
        super(string);
    }

    @Override
    public void init() {
        Device device = null;
        String spotlist = getProperty("spots");
        String[] hardcodedDeviceIds = null;

        System.out.println("Started DeviceRegistrationApp ...");

        // Insert any hardcoded devices here ...
        if ((spotlist != null) && !spotlist.equalsIgnoreCase("null")) {
            hardcodedDeviceIds = spotlist.split(",");

            for (int i = 0; i < hardcodedDeviceIds.length; i++) {
                if (hardcodedDeviceIds[i].equalsIgnoreCase("xxxx")) {
                    System.err.println("Error: Oops! Looks like you forgot to specify your device Id in build.properties?");
                    System.exit(-1);
                }
                device = new Device("spot-" + hardcodedDeviceIds[i],
                        DeviceForwarder.Type.UDP6, true,
                        "[fe80:0:0:0:0214:4f01:0000:" + hardcodedDeviceIds[i] +
                        "]:8888", 600000);
                DeviceManager.addDevice(device);

            }
        }
    }

    @Override
    public HttpResponse processRequest(HttpRequest req) throws IOException {
        byte[] body = null;
        String line = null;
        HttpResponse res = new HttpResponse();
        String name = "";
        DeviceForwarder.Type ft = DeviceForwarder.Type.UDP6;
        boolean compression = true;
        int lifetime = Constants.DEFAULT_REG_LIFETIME; // duration in sec for which this registration should not expire
        String addr = "";

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
                    } else if (tokens[0].equalsIgnoreCase("addr")) {
                        addr = tokens[1];
                    } else if (tokens[0].equalsIgnoreCase("compression")) {
                        compression = tokens[1].toLowerCase().equals("true");
                    } else if (tokens[0].equalsIgnoreCase("ctype")) {
                        ft = (tokens[1].toLowerCase().startsWith("tcp") ?
                            DeviceForwarder.Type.TCP6 :
                            DeviceForwarder.Type.UDP6);
                    } else if (tokens[0].equalsIgnoreCase("lifetime")) {
                        lifetime = Integer.parseInt(tokens[1]);
                    } else {
                        System.out.println("Ignoring line <" + line + ">");
                    }
                }
            }

            if (name.equals("") || addr.equals("")) {
                res.setStatus(HttpResponse.SC_BAD_REQUEST);
            } else {
                // XXX Name conflict handling is quite simplistic for now ...
                Device device = DeviceManager.findByName(name);
                if (device != null) {
                    device.setLastHeardTime(System.currentTimeMillis());
                    device.setInteractionType(ft);
                    device.setUsesCompression(compression);
                    device.setAddress(addr);
                    res.setStatus(HttpResponse.SC_SEE_OTHER);
                } else {
                    device = new Device(name, ft, compression, addr,
                        lifetime);
                    DeviceManager.addDevice(device);
                    res.setStatus(HttpResponse.SC_CREATED);
                }
                
                res.setHeader("Location", Utils.getBaseURL() + "/" +
                        // "dev/" +
                        name);
            }
        }

        return res;
    }
}
