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

import com.sun.spot.peripheral.Spot;
import com.sun.spot.util.Properties;
import com.sun.squawk.VM;
import com.sun.squawk.util.StringTokenizer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;

/**
 *
 * @author vgupta
 */
public class PropertiesManager extends WebApplication {
    public void init() {};

    public PropertiesManager(String str) {
        super(str);
    }
    
    public synchronized HttpResponse processRequest(HttpRequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        String path = request.getPathInfo();
        StringBuffer sb = null;

        if (request.getMethod().equalsIgnoreCase("GET")) {
            if (path.equals("/") || path.equals("")) {
                response.setBody(getPropertiesResponse(null).toString().getBytes());
            } else {
                sb = getPropertiesResponse(path.substring(1));
                if (sb == null) {
                    response.setStatus(HttpResponse.SC_NOT_FOUND);
                    response.setHeader("Cache-Control", "max-age=10");
                    return response;
                } else {
                    response.setBody(sb.toString().getBytes());
                }
            }
            
            response.setStatus(HttpResponse.SC_OK);
            response.setHeader("Content-Type", "text/plain");
            response.setHeader("Cache-Control", "max-age=10");
            return response;
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            if (!path.equals("/") && !path.equals("")) {
                response.setStatus(HttpResponse.SC_BAD_REQUEST);
                return response;
            } else {
                Properties oldProps = Spot.getInstance().getPersistentProperties();
                Properties newProps = new Properties();
                newProps.load(new ByteArrayInputStream(request.getBody()));
                Enumeration newKeys = newProps.keys();
                while (newKeys.hasMoreElements()) {
                    String newKey = (String) newKeys.nextElement();
                    oldProps.setProperty(newKey,
                            newProps.getProperty(newKey));
                }

                Spot.getInstance().storeProperties(oldProps);

                response.setBody(getPropertiesResponse(null).toString().getBytes());
                response.setStatus(HttpResponse.SC_OK);
                response.setHeader("Cache-Control", "max-age=10");
                return response;
            }
        } else if (request.getMethod().equalsIgnoreCase("DELETE")) {
            if (path.equals("/") || path.equals("")) {
                response.setStatus(HttpResponse.SC_BAD_REQUEST);
                return response;
            } else {
                Properties oldProps = Spot.getInstance().getPersistentProperties();
                if (oldProps.containsKey(path.substring(1))) {
                    oldProps.remove(path.substring(1));
                    Spot.getInstance().storeProperties(oldProps);
                    response.setBody(getPropertiesResponse(null).toString().getBytes());
                    response.setStatus(HttpResponse.SC_OK);
                    response.setHeader("Content-Type", "text/plain");
                    response.setHeader("Cache-Control", "max-age=10");
                    return response;
                } else {
                    response.setStatus(HttpResponse.SC_NOT_FOUND);
                    response.setHeader("Cache-Control", "max-age=10");
                    return response;
                }
            }
        } else {
            response.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
            response.setHeader("Allow", "GET, DELETE, POST");

            return response;
        }
    }

    private StringBuffer getPropertiesResponse(String key) {
        Properties prop = Spot.getInstance().getPersistentProperties();
        StringBuffer sb = new StringBuffer();
        String value;

        if (key != null) {
            if ((value = prop.getProperty(key)) == null)
                return null;
            else
                sb.append("\"" + value + "\"");

            return sb;
        }

        sb.append("{\n");
        if (prop != null) {
            for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
                key = (String) e.nextElement();
                if (key == null) continue;
                value = prop.getProperty(key);
                sb.append("\t\"" + key + "\": \"" + value + "\",\n");
            }
        }

        sb.append("}\n");
        return sb;
    }
}
