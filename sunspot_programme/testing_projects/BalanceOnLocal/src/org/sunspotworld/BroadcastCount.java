/*
 * Copyright (c) 2010 Oracle.
 * Copyright (c) 2007 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this soft
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copiesware and associated documentation
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.ITriColorLEDArray;


import java.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * A simple MIDlet that uses the left switch (SW1) to broadcast a message
 * to set the color of the LEDs of any receiving SPOTs and the right 
 * switch (SW2) to count in binary in its LEDs.
 *
 * Messages received from the other SPOTs control the LEDs of this SPOT.
 */
public class BroadcastCount extends MIDlet {

    private static final int CHANGE_COLOR = 1;
    private static final int CHANGE_COUNT = 2;

    private ITriColorLEDArray leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class);
    IAccelerometer3D accelerometer  = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    double rot = 1;

    private void showCount(int count) {
        leds.setOff();
        if(count != 0){
            if(count > 0){
                for (int i = 4; i < count + 4; i++) {
                        leds.getLED(i).setColor(LEDColor.RED);
                        leds.getLED(i).setOn();
                }
            }else if(count < 0){
                for (int i = 0; i < Math.abs(count); i++) {
                        leds.getLED(3 - i).setColor(LEDColor.RED);
                        leds.getLED(3 - i).setOn();
                }
            }
        }
    }
    
    protected void startApp() throws MIDletStateChangeException {
        System.out.println("Broadcast Counter MIDlet");
        start();
        
        //read_package();
    }
    protected void start(){
        while(true){
            try{
                int rottest = 0;
                leds.getLED(Math.abs(7)).setColor(LEDColor.WHITE);
                leds.getLED(Math.abs(7)).setOn();
                leds.getLED(Math.abs(0)).setColor(LEDColor.WHITE);
                leds.getLED(Math.abs(0)).setOn();
                rot = accelerometer.getTiltX();
                if(Math.abs(rot) > 0.01){
                    rottest = (int)(rot / 0.392699082);
                }

                showCount(rottest);
            } catch (IOException ex) {
                    System.out.println("Error receiving packet: " + ex);
                    ex.printStackTrace();
            }
        }
    }

    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // Only called if startApp throws any exception other than MIDletStateChangeException
    }


}
