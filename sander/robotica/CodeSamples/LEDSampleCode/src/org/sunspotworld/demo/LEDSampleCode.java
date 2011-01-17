/*
 * Copyright (c) 2006-2010 Sun Microsystems, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 **/

package org.sunspotworld.demo;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.util.Utils;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Sample code snippet to show how to use the tricolor LEDs
 * on the Sun SPOT General Purpose Sensor Board.
 *
 * @author Ron Goldman<br>
 * date: August 14, 2006 
 */
public class LEDSampleCode extends MIDlet {

    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);

    /**
     * Simple LED demo that repeats forever.
     *
     * First, blink the leftmost LED on & off 5 times. 
     * Second, have a moving lit LED sweep from left to right.
     * Third, pulse one LED from dim to bright, repeat 3 times.
     */
    public void demoLEDs() {
        leds.setOff();                // turn off all LEDs
       
        while (true) {

            // first demo - blink LED 0 on & off 5 times
            leds.getLED(0).setColor(LEDColor.BLUE); // set it to one of the predefined colors
            for (int i = 0; i < 5; i++ ) {
                leds.getLED(0).setOn();
                Utils.sleep(250);                   // on for 1/4 second
                leds.getLED(0).setOff();
                Utils.sleep(750);                   // off for 3/4 second
            }

            // second demo - move the lit LED - go from LED 0 to the last (LED 7)
            for (int i = 0; i < leds.size(); i++) {
                leds.getLED(i).setColor(LEDColor.MAGENTA);
                leds.getLED(i).setOn();
                Utils.sleep(200);                   // on for 1/5 second
                leds.getLED(i).setOff();
            }

            // third demo - pulse LED 3 so it gets brighter - do so 3 times
            for (int i = 0; i < 3; i++) {
                leds.getLED(3).setRGB(0, 0, 0);	    // start it off dim
                leds.getLED(3).setOn();
                Utils.sleep(100);
                for (int j = 0; j < 255; j += 5) {
                    leds.getLED(3).setRGB(j, 0, 0); // make it get brighter red
                    Utils.sleep(50);	            // change every 1/20 second
                }
            }
            leds.getLED(3).setOff();
        }
    }

    /**
     * MIDlet call to start our application.
     */
    protected void startApp() throws MIDletStateChangeException {
        // Listen for downloads/commands over USB connection
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();
        demoLEDs();
    }

    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * @param unconditional If true the MIDlet must cleanup and release all resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        leds.setOff();
    }
}
