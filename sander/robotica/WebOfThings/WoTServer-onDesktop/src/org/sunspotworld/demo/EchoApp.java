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
import java.util.Random;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
class EchoApp extends WebApplication {
    private int i = 0;
    private Random rand = new Random();

    @Override
    public void init() {
    }

    public EchoApp(String propStr) {
        super(propStr);
    }
    
    @Override
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        
        HttpResponse resp = new HttpResponse();


        // This code adds a random delay and can be used to verify that
        // the server handles multiple requests concurrently
        //long val = (long) ((15000.0 * rand.nextInt())/Integer.MAX_VALUE);
        //if (val < 0) val = 0 - val;
        //System.out.println("Sleeping for " + val + "ms");
        //Utils.sleep(val);
 
        i++;
        resp.setStatus(HttpResponse.SC_OK);
        resp.setHeader("Content-Type", "text/html");
        String respStr = "<html>Welcome</html>Request# " + i + "<br/>" +
                "Your request was <br/>" + request.toString();
        resp.setBody(respStr.getBytes());

        return resp;
    }
}
