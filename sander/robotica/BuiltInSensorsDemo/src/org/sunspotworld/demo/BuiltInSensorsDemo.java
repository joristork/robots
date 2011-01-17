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

/*
 * BuiltInSensorsDemo.java
 *
 * Illustrates use of the on-board sensors.
 * Intended as a starting template for building your own app.
 * For radio, see the RadioStrength demo.
 *
 *    This app waits for you to hold down SW1. While down, LED1 will
 *    Show green if shaken left to right.
 *    Once the button is released, it will show the temperature
 *    on LED1 (either red or blue), and the light level on LED2 in green.
 *
 * author: Randy Smith  
 * date: August 2, 2006 
 */ 

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ILightSensor; 
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.Utils;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
 
public class BuiltInSensorsDemo extends MIDlet {
    private ILightSensor lightSensor     = (ILightSensor)      Resources.lookup(ILightSensor.class);
    private ITemperatureInput tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
    private IAccelerometer3D accel       = (IAccelerometer3D)  Resources.lookup(IAccelerometer3D.class);
    private ITriColorLEDArray leds       = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    private ISwitch sw1                  = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
    
    /**
     * Main application loop.
     * IOException is thrown by reading the acclerometer, temperature, or light.
     */
    private void run()throws IOException {  
        for(int i = 0; i < leds.size(); i++){
            leds.getLED(i).setOn();                    // Enable this LED
            leds.getLED(i).setRGB(0,0,0);              // Set it to black
        }
        leds.getLED(0).setColor(LEDColor.TURQUOISE);   // See LEDColor for more predefined colors.
        
        /*
         * Wait for user to hold down SW1. Indicate left-right shaking in green light 
         * on the first LED
         */
        if(sw1.isOpen()) sw1.waitForChange();         // If it is open, wait for SW1 to close...
        while(sw1.isClosed()){                        // ...and while closed register shaking.
            double lateralShake = accel.getAccelX();  // acceleration measured in G's: [-2 : +2].
            leds.getLED(0).setRGB(0, (int) (lateralShake * lateralShake * 60.0), 0);  // [0 : 240]
            Utils.sleep(100);                         //Like Thread.sleep(..) but catches and ignores an InterruptedExceptionn.
        }
        
        /*
         * Now that the user has released SW1, we indicate ADC chip temperature on LED[0],
         * and light level on LED[1]. 
         */
        while(true){
            // heatIndication is scaled so that reaching 20 degrees away from 72 gives the maximum LED brightness (255)
            int heatIndication = (int) ((tempSensor.getFahrenheit() - 72.0) * 255.0 / 20);
            if(heatIndication > 0){
                leds.getLED(0).setRGB(heatIndication, 0, 0);      //above 72 degrees (room temp) in red
            } else {
                leds.getLED(0).setRGB(0, 0, - heatIndication);    //below 72 degrees (room temp) in blue
            }
            
            int lightIndication = lightSensor.getValue();         //ranges from 0 - 740
            leds.getLED(1).setRGB(0, lightIndication / 3, 0);     //Set LED green, will range from 0 - 246
            Utils.sleep(200); 
        }
    }

   
    /**
     * The rest is boiler plate code, for Java ME compliance
     *
     * startApp() is the MIDlet call that starts the application.
     */
    protected void startApp() throws MIDletStateChangeException { 
        BootloaderListenerService.getInstance().start();       // Listen for downloads/commands over USB connection
        try {
            run();
        } catch (IOException ex) {              // A problem in reading the sensors. 
            ex.printStackTrace();
        }
    }

    /**
     * This will never be called by the Squawk VM.
     */
    protected void pauseApp() { 
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * I.e. if startApp throws any exception other than MIDletStateChangeException,
     * if the isolate running the MIDlet is killed with Isolate.exit(), or
     * if VM.stopVM() is called.
     * 
     * It is not called if MIDlet.notifyDestroyed() was called.
     */
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException { 
    }

}
