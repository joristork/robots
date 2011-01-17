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
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.SwitchEvent;
//import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;
import java.util.Random;

/**
 *
 * @author vgupta
 */
public class SwitchListener implements ISwitchListener {
    Random rand = new Random();
    private ITriColorLEDArray myLEDs = null;
    private int idx = 0;
    private LEDColor[] ledcolor = {
            LEDColor.RED,
            LEDColor.WHITE,
            LEDColor.BLUE,
            LEDColor.GREEN,
            LEDColor.CYAN,
            LEDColor.YELLOW,
            LEDColor.MAGENTA
    };

    public SwitchListener() {
        System.out.println("Creating new switchlistener ...");
        myLEDs = (ITriColorLEDArray)
            Resources.lookup(ITriColorLEDArray.class);
    }

    
//    private HttpResponse sendCheckIn(String connStr) throws IOException {
//        // Create a registration request ...
//        HttpRequest req = new HttpRequest();
//        HttpClient httpClient = new HttpClient(connStr,
//                WoTConstants.USES_COMPRESSION);
//
//        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
//        String name = WoTConstants.SPOT_PREFIX +
//                IEEEAddress.toDottedHex(ourAddr).substring(15);
//
//        req.setMethod("POST");
//        req.setPathInfo("/ci");
////        req.setHeader("Name", name);
////        req.setHeader("Awake-Time", "" + 10);
////        req.setHeader("Sleep-Time", "" + 60);
//        String reqBody = "name " + name + "\n" + "awake-time " + 10 + "\n" +
//                "sleep-time " + 60 + "\n";
//        req.setBody(reqBody.getBytes());
//
//        System.out.println("Sent check-in request: " + req);
//        HttpResponse res = httpClient.sendData(req);
//        System.out.println("Got check-in response: " + res);
//        return res;
//    }
//
//    public void switchPressed(SwitchEvent evt) {
//        System.out.println("Switch pressed, will send check-in message");
//        String gwAddr = DiscoveryHandler.getCurrentGatewayAddr();
//
//        if (gwAddr.equalsIgnoreCase("")) {
//            System.out.println("No current gateway!!!");
//            return;
//        }
//
//        try {
//            System.out.println("Sending check-in to " +
//                    DiscoveryHandler.getCurrentGatewayAddr());
//            HttpResponse resp =
//                    sendCheckIn(DiscoveryHandler.getCurrentGatewayAddr());
//            System.out.println("Check-in response is: " +
//                    resp.getStatus() + "(" +
//                    HttpResponse.getStatusString(resp.getStatus()) + ")");
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

    public void switchPressed(SwitchEvent evt) {
        idx = (idx + 1) % (ledcolor.length + 1);
        
        for (int i = 0; i < myLEDs.size(); i++) {
            if (idx >  0) {
                myLEDs.getLED(i).setColor(ledcolor[idx - 1]);
                myLEDs.getLED(i).setOn();
            } else {
                myLEDs.getLED(i).setRGB(0, 0, 0);
                myLEDs.getLED(i).setOff();
            }
        }
    }

    public void switchReleased(SwitchEvent evt) {
    }
}
