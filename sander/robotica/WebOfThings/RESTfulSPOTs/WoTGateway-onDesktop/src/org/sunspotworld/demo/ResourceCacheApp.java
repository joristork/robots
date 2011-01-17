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
import java.io.IOException;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class ResourceCacheApp extends WebApplication {

    public ResourceCacheApp() {
    }

    ResourceCacheApp(String string) {
        super(string);
    }

    @Override
    public void init() {
        System.out.println("Started ResourceCache manager ...");
    }

    @Override
    public HttpResponse processRequest(HttpRequest req) throws IOException {
        int idx = -1;
        String pathInfo = req.getPathInfo();
        HttpResponse res = new HttpResponse();

        if ((pathInfo == null) || ((idx = pathInfo.indexOf("/", 1)) < 1)) {
            res.setStatus(HttpResponse.SC_BAD_REQUEST);
        } else {
            String deviceId = pathInfo.substring(1, idx);
            Device dev = DeviceManager.findByName(deviceId);
            if (dev == null) {
                //System.out.println("Did not find device " + deviceId);
                res.setStatus(HttpResponse.SC_NOT_FOUND);
            } else {
                // Show all resources ...
                res.setStatus(HttpResponse.SC_OK);
                res.setHeader("Content-Type", "text/plain");
                byte[] body = ("Resource Cache for device " + deviceId +
                        "\n===========================\n" +
                        dev.getResourceCache().toString()).getBytes();
                res.setBody(body);
            }
        }

        return res;
    }
}
