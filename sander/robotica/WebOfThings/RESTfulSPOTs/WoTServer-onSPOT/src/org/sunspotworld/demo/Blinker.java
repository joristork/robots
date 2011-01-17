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
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.Utils;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;

/**
 *
 * @author vgupta
 */
public class Blinker extends WebApplication {
    private ITriColorLEDArray myLEDs;
    private String rgbStr;

    public Blinker(String str) {
        super(str);
    }
    
    public void init() {
        this.myLEDs = (ITriColorLEDArray)
            Resources.lookup(ITriColorLEDArray.class);
        for (int i = 0; i < myLEDs.size(); i++) {
            myLEDs.getLED(i).setRGB(0,0,0);
            myLEDs.getLED(i).setOff();                // turn off all LEDs
        }
    }

    public synchronized HttpResponse processRequest(HttpRequest request) throws IOException {
        HttpResponse response = new HttpResponse();

        if (request.getMethod().equalsIgnoreCase("POST")) {
            if (request.getBody() == null) {
                rgbStr = "255,0,0";
            } else {
                rgbStr = new String(request.getBody());
            }
            
            new Thread(new Runnable() {
                public void run() {
                    int[] savedRed = new int[myLEDs.size()];
                    int[] savedGreen = new int[myLEDs.size()];
                    int[] savedBlue = new int[myLEDs.size()];
                    boolean[] isOn = new boolean[myLEDs.size()];

                    // Save LED state
                    for (int i = 0; i < myLEDs.size(); i++) {
                        savedRed[i] = myLEDs.getLED(i).getRed();
                        savedGreen[i] = myLEDs.getLED(i).getGreen();
                        savedBlue[i] = myLEDs.getLED(i).getBlue();
                        isOn[i] = myLEDs.getLED(i).isOn();
                    }
                    for (int j = 0; j < 3; j++) {
                        for (int i = 0; i < myLEDs.size(); i++) {
                            try {
                                MyUtils.setColor(myLEDs.getLED(i), rgbStr);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        Utils.sleep(500);
                        for (int i = 0; i < myLEDs.size(); i++) {
                            myLEDs.getLED(i).setOff();
                        }
                        Utils.sleep(500);
                    }
                    
                    // Restore LED state
                    for (int i = 0; i < myLEDs.size(); i++) {
                        myLEDs.getLED(i).setRGB(savedRed[i], savedGreen[i], savedBlue[i]);
                        savedBlue[i] = myLEDs.getLED(i).getBlue();
                        if (isOn[i])
                            myLEDs.getLED(i).setOn();
                        else
                            myLEDs.getLED(i).setOff();
                    }
                }
            }).start();

            response.setStatus(HttpResponse.SC_OK);
            return response;
        } else {
            response.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
            response.setHeader("Allow", "POST");
            response.setHeader("Cache-Control", "max-age=300"); // this will never change

            return response;
        }
    }
}
