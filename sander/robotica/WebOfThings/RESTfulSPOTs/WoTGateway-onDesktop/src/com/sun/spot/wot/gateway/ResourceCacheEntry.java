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

import java.util.Date;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class ResourceCacheEntry {
    private HttpRequest request;
    private HttpResponse response;
    private long responseGenerationTime = -1;
    private long responseExpirationTime = -1;

    public ResourceCacheEntry(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
        // Chose the latest generation time?
        this.responseGenerationTime = response.getTime();
        if (response.getTime() < (System.currentTimeMillis() -
                response.getAge()*1000)) {
            this.responseGenerationTime = System.currentTimeMillis() - 
                    (response.getAge()*1000);
        }
        
        this.responseExpirationTime = System.currentTimeMillis() +
                (response.getMaxAge() * 1000);
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() > responseExpirationTime);
    }

    public HttpResponse getAcceptableResponse(HttpRequest request) {
        // XXX This can't be as simple ... see HTTP document on computing cache times
        long responseAge = 0L;
        long now = System.currentTimeMillis();
        long remainingValidity = 0L;
        long requestAge = 0L;

        requestAge = request.getMaxAge() * 1000;
        if (requestAge <= 0) {
            // This is taken as no max age specified
            if (responseExpirationTime < now) return null;
        } else {
            if (responseGenerationTime < (now - requestAge)) return null;
        }

//        System.out.println("Found fresh enough response " +
//                    "max-age in request is " + request.getMaxAge() +
//                    ", response generated at " +
//                    new Date(responseGenerationTime) +
//                    ", good until " + new Date(responseExpirationTime) +
//                    ", current time " + new Date());
        // recompute age and max-age ...
        responseAge = now - responseGenerationTime;
        remainingValidity = responseExpirationTime - now;
        response.setHeader("Age", (responseAge / 1000) + "");
        response.setHeader("Cache-Control", "max-age=" +
                (remainingValidity / 1000));
        return response;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();

        result.append("            URI:" + request.getPathInfo() + " " +
                ((System.currentTimeMillis() > responseExpirationTime) ?
                    " ***Expired***" : "") + "\n" +
                "\tGeneration time: " + new Date(responseGenerationTime) + "\n" +
                "\tExpiration time: " + new Date(responseExpirationTime));
        result.append("\n---------------------------\n");

        return result.toString();
    }
}
