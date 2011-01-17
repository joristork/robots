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
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.utils.Base64;

/**
 *
 * @author vgupta
 */
public class LEDController extends WebApplication {
    private ITriColorLEDArray myLEDs = null;

    public void init() {
        System.out.println("LEDController init called ...");
        myLEDs = (ITriColorLEDArray)
            Resources.lookup(ITriColorLEDArray.class);
        for (int i = 0; i < myLEDs.size(); i++) {
            myLEDs.getLED(i).setRGB(0,0,0);
            myLEDs.getLED(i).setOff();                // turn off all LEDs
        }
    }

    public LEDController(String str) {
        super(str);
    }
    
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        if (myLEDs == null) init();
        
        if (request.getPathInfo().equalsIgnoreCase("/") ||
                request.getPathInfo().equalsIgnoreCase("")) {
            serveAllLEDs(request, response);
            return response;
        }

        int ledNum = Integer.parseInt(request.getPathInfo().substring(1));
        serveLED(ledNum, request, response);

        return response;
    }

    private void serveAllLEDs(HttpRequest httpRequest, HttpResponse httpResponse) {        
        if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < myLEDs.size(); i++) {
                sb.append(i + "," + 
                        myLEDs.getLED(i).getRed() + "," +
                        myLEDs.getLED(i).getGreen() + "," +
                        myLEDs.getLED(i).getBlue() + "\n");
            }

            httpResponse.setStatus(HttpResponse.SC_OK);
            httpResponse.setHeader("Content-Type", "text/plain");
            httpResponse.setBody(sb.toString().getBytes(), 0, sb.length());
            return;
        }

        if (httpRequest.getMethod().equalsIgnoreCase("PUT")) {
            byte[] requestBody_b = httpRequest.getBody();
            if (requestBody_b == null) {
                System.err.println("Request body is null");
                httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
                return;
            }

            String requestBody_s = new String(requestBody_b);
            StringTokenizer stLines = new StringTokenizer(requestBody_s, "\n");
            while (stLines.hasMoreTokens()) {
                String line = stLines.nextToken();
                int idx = line.indexOf(",");

                if (idx < 0) {
                    httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
                    return;
                } else {
                    try {
                        int ledNum = Integer.parseInt(line.substring(0, idx));
                        String rgb = line.substring(idx + 1);
                        MyUtils.setColor(myLEDs.getLED(ledNum), rgb);
                    } catch (NumberFormatException nfe) {
                        httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
                        httpResponse.setBody(("Bad led index " + line.substring(0, idx)).getBytes());
                        return;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
                        return;
                    }
                }
            }

            httpResponse.setStatus(HttpResponse.SC_OK);
            return;
        }

        httpResponse.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
        httpResponse.setHeader("Allow", "GET,PUT");

        return;
    }

    private void serveLED(int ledNum, HttpRequest httpRequest,
            HttpResponse httpResponse) {

        if (7 < ledNum || 0 > ledNum) {
            httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
            httpResponse.setBody(("Invalid LED number " + ledNum).getBytes());
            return;
        }

        if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
            StringBuffer sb = new StringBuffer();
            sb.append(myLEDs.getLED(ledNum).getRed() + "," +
                    myLEDs.getLED(ledNum).getGreen() + "," +
                    myLEDs.getLED(ledNum).getBlue());

            httpResponse.setStatus(HttpResponse.SC_OK);
            httpResponse.setHeader("Content-Type", "text/plain");
            httpResponse.setBody(sb.toString().getBytes(), 0, sb.length());
            return;
        }

        if (httpRequest.getMethod().equalsIgnoreCase("PUT")) {
            byte[] requestBody_b = httpRequest.getBody();

            if (requestBody_b == null) {
                System.err.println("Request body is null");
                httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
                return;
            }

            try {
                MyUtils.setColor(myLEDs.getLED(ledNum),
                        new String(requestBody_b));
                httpResponse.setStatus(HttpResponse.SC_OK);
            } catch (IOException ioe) {
                System.out.println("Error: " + ioe.getMessage());
                httpResponse.setStatus(HttpResponse.SC_BAD_REQUEST);
            }
            return;
        }

        httpResponse.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
        httpResponse.setHeader("Allow", "GET,PUT");
        return;
    }
    
    public boolean isAuthorized(HttpRequest req) {
        String auth = req.getHeader("Authorization");

        return ((auth != null) &&
                auth.equalsIgnoreCase("Basic " + Base64.encode("Aladdin:open sesame")));
    }
}
