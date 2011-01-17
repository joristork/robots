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

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class DateApp extends WebApplication {
    private int i = 0;

    @Override
    public void init() {
    }

    public DateApp(String propStr) {
        super(propStr);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) throws IOException {

        HttpResponse resp = new HttpResponse();

        // If the request method is anything other than a GET, respond
        // with Method Not Allowed
        if (!request.getMethod().equalsIgnoreCase("GET")) {
            resp.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
            resp.setHeader("Allow", "GET");
            return resp;
        }

        i++;
        resp.setStatus(HttpResponse.SC_OK);
        resp.setHeader("Content-Type", "text/html");
        String respStr = "<html>Welcome</html>Request# " + i + "<br/>" +
                        "Current time is " +
                DateFormat.getTimeInstance().format(new Date());
        resp.setBody(respStr.getBytes());

        return resp;
    }

}
