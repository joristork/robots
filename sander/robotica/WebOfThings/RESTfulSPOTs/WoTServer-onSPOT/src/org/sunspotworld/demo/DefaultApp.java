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

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;

/**
 *
 * @author vgupta
 */
class DefaultApp extends WebApplication {
    private String ourId = IEEEAddress.toDottedHex(RadioFactory.getRadioPolicyManager().getIEEEAddress()).substring(15);

    public void init() {};

    public DefaultApp(String props) {
        super(props);
    }

    public HttpResponse processRequest(HttpRequest req) throws IOException {
        HttpResponse resp = new HttpResponse();

        String body = "<html>" +
                "<a href=\"/spot-" + ourId +"/.well-known/r\">" +
                "Rsrcs" + "</a>" +
//                ".<p>" +
//                "<a href=\"http://sensor.network.com/spotservices/index.jsp\">" +
//                "Full documentation</a>." +
                "</html>";
                resp.setStatus(HttpResponse.SC_OK);
        resp.setHeader("Content-Type", "text/html");
        resp.setBody(body.getBytes());

//        XXX Using this has the drawback of causing the browser to retrieve
//        a much longer HTML page with the list of services from .well-known
//        resp.setStatus(HttpResponse.SC_MOVED_TEMPORARILY);
//        resp.setHeader("Location", "/spot-" + ourId +"/.well-known/r");
//        resp.setBody("See .well-known/r\n".getBytes());
        return resp;
    }
}
