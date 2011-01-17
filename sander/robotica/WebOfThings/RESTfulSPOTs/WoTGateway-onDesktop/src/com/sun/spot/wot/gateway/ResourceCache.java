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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class ResourceCache {
    HashMap cache = new HashMap();

    public synchronized boolean insert(HttpRequest request, HttpResponse response) {
        // XXX Automatically assign an Age tag ???
        if (!request.getMethod().equalsIgnoreCase("GET") ||
                request.getQueryString() != null) {
            System.err.println("Did not insert request, response in cache!");
            return false;
        }
        
        ResourceCacheEntry rce = new ResourceCacheEntry(request, response);

        cache.put(request.getPathInfo(), rce);
        //System.out.println("Inserting into resource cache:\n" + rce);
        return true;
    }

    public synchronized HttpResponse getCachedResponse(HttpRequest request) {
        HttpResponse result = null;

        if (!request.getMethod().equalsIgnoreCase("GET") ||
                request.getQueryString() != null) {
            System.err.println("Warning: Cannot use resource cache " +
                    "for this request");
            return result;
        }

        ResourceCacheEntry rce = (ResourceCacheEntry) cache.get(request.getPathInfo());
        if (rce != null) {
            if (rce.isExpired()) {
//                System.out.println("Resource Cache Entry has expired");
                cache.remove(request.getPathInfo());
            } else {
                result = rce.getAcceptableResponse(request);
            }
        }

        return result;
    }

    public synchronized String getSummary() {
        StringBuffer sb = new StringBuffer();
        int size = cache.size();

        if (size == 0) {
            sb.append("empty");
        } else {
            sb.append(size + " entries");
        }
        
        return sb.toString();
    }

    public synchronized String toString() {
        StringBuffer sb = new StringBuffer();
        Set keys = cache.keySet();
        Iterator i = keys.iterator();
        
        while (i.hasNext()) {
            ResourceCacheEntry rce = (ResourceCacheEntry) cache.get(i.next());
            sb.append(rce.toString());
        }

        return sb.toString();
    }
}
