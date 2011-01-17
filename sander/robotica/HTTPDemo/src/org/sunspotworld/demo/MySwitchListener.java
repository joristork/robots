/*
 * Copyright (c) 2008-2010, Sun Microsystems. All rights reserved.
 * Copyright 2010 Oracle. All Rights Reserved.
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
 * Neither the name of Sun Microsystems nor Oracle nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
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
import com.sun.spot.resources.transducers.ILightSensor;

import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author vgupta
 */
public class MySwitchListener implements ISwitchListener {
    private static final int INACTIVE = 0;
    private static final int CONNECTING = 1;
    private static final int COMPLETED = 2;
    private static final int IOERROR = 3;
    private static final int PROTOCOLERROR = 4;

    private static int POSTstatus = INACTIVE;

    private String postURL = null;
    private ILightSensor lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    private ITemperatureInput tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
    private long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
    int lightVal = 0;
    int tempVal = 0;

    public MySwitchListener(String postURL) {
        this.postURL = postURL;
    }

    public void switchPressed(SwitchEvent evt) {
        try {
            lightVal = lightSensor.getValue();
            tempVal = (int) (tempSensor.getCelsius() * 100);
            String msg = IEEEAddress.toDottedHex(ourAddr) +
                    ": Light reading is " + lightVal +
                    " and temperature is " + (float) (tempVal * 1.0 / 100) +
                    " C.";
            postMessage(postURL, msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void switchReleased(SwitchEvent evt) {
    }

    public static void postMessage(String postURL, String msg) {
        HttpConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        long starttime = 0;
        String resp = null;
        ProgressDisplayThread displayProg = null;

        System.out.println("Posting: <" + msg + "> to " + postURL);

        try {
            POSTstatus = CONNECTING;
            displayProg = new ProgressDisplayThread();
            displayProg.start();
            starttime = System.currentTimeMillis();
            conn = (HttpConnection) Connector.open(postURL);
            conn.setRequestMethod(HttpConnection.POST);
            conn.setRequestProperty("Connection", "close");

            out = conn.openOutputStream();
            out.write((msg + "'\n").getBytes());
            out.flush();

            in = conn.openInputStream();
            resp = conn.getResponseMessage();
            if (resp.equalsIgnoreCase("OK") || resp.equalsIgnoreCase("CREATED")) {
                POSTstatus = COMPLETED;
            } else {
                POSTstatus = PROTOCOLERROR;
            }
        } catch (Exception ex) {
            POSTstatus = IOERROR;
            resp = ex.getMessage();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException ex) {
                resp = ex.getMessage();
            }
        }

        displayProg.markDone(POSTstatus == COMPLETED);
        if (POSTstatus != COMPLETED) {
            System.out.println("Posting failed: " + resp);
        } else {
            System.out.println("Total time to post " +
                "(including connection set up): " +
                (System.currentTimeMillis() - starttime) + " ms");
        }
        System.out.flush();
    }
}

class ProgressDisplayThread extends Thread {
    private boolean done = false;

    private ITriColorLEDArray myLEDs =
            (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);

    public void run() {
        while (!done) {
            for (int i = 0; !done && i < myLEDs.size(); i++) {
                myLEDs.getLED(i).setRGB(0, 0, 0);
                myLEDs.getLED(i).setOff();
            }
            for (int i = 0; !done && i < myLEDs.size(); i++) {
                myLEDs.getLED(i).setRGB(50, 50, 50);
                myLEDs.getLED(i).setOn();
                Utils.sleep(500);
            }
        }
    }

    public void markDone(boolean status) {
        done = true;
        for (int i = 0; i < myLEDs.size(); i++) {
            if (status) { // post was successful
                myLEDs.getLED(i).setRGB(0, 50, 0);
            } else { // post failed
                myLEDs.getLED(i).setRGB(50, 0, 0);
            }
            myLEDs.getLED(i).setOn();
        }
        Utils.sleep(1000);
    }
}
