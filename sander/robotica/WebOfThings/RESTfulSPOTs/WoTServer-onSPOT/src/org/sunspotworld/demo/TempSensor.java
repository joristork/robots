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

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITemperatureInput;
import java.io.IOException;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;

/**
 *
 * @author vgupta
 */
public class TempSensor extends WebApplication {

    public void init() {};

    public TempSensor(String str) {
        super(str);
    }
    
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        String tempReadingStr = null;

        if (request.getMethod().equalsIgnoreCase("GET")) {
            ITemperatureInput tempSensor = null;
            tempSensor =  (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
            tempReadingStr = "" + tempSensor.getCelsius();

            response.setStatus(HttpResponse.SC_OK);
            response.setHeader("Content-Type", "text/plain");
            response.setHeader("Cache-Control", "max-age=60");
            response.setBody(tempReadingStr.getBytes());

            return response;
        } else {
            response.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
            response.setHeader("Allow", "GET");

            return response;
        }
    }
}
