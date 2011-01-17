/*
 * Copyright (c) 2006-2010 Sun Microsystems, Inc.
 * Copyright (c) 2010 Oracle
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
 * LightSensorSampleCode.java
 *
 * Illustrates use of the on board light Sensor.
 * <p>
 * This app prints out the light level reading every 5 seconds.
 * It also creates a Condition to check every second
 * if the light sensor reading ever drops close to zero.
 * <p>
 * You can find the light sensor between the two switches (SW1 & SW2)
 * on the top of the new rev8 Sun SPOT demo board or just below SW1
 * on the older rev6 board.
 * 
 * author: Randy Smith  
 * date: August 2, 2006
 * modified: August 19, 2010
 */ 

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.IMeasurementInfo;
import com.sun.spot.resources.transducers.LightSensorEvent;
import com.sun.spot.resources.transducers.SensorEvent;
import com.sun.spot.util.Utils;
import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
 
public class LightSensorSampleCode extends MIDlet {

    private void initAndRun() throws IOException {
        ILightSensor lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);

        final double min = ((IMeasurementInfo)lightSensor).getMinValue();
        final double max = ((IMeasurementInfo)lightSensor).getMaxValue();
        System.out.println("Light sensor values range from " + min + " to " + max);

        // define the callback
        IConditionListener lightListener = new IConditionListener() {
            public void conditionMet(SensorEvent evt, Condition condition) {
                int val = ((LightSensorEvent) evt).getValue();
                System.out.println("Gosh... it's " + 
                        ((val > max / 2) ? "really bright" : "dark") +
                        "!  Light value = " + val);
            }
        };

        // define the condition - check light sensor every 1 second
        Condition checkLightLevel = new Condition(lightSensor, lightListener, 1 * 1000) {
            public boolean isMet(SensorEvent evt) {
                int val = ((LightSensorEvent) evt).getValue();
                if (val <= 10 || val > (max - 100)) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        checkLightLevel.start();    // start monitoring the condition

        while (true) {
            int lightLevel = lightSensor.getAverageValue();     // average in case fluorescent light

            // If you run the project from the host with the USB connected,
            // you will see this text output...
            System.out.println("LightSensor.getValue() = " + lightLevel);;
            Utils.sleep(5000);       // Like Thread.sleep() without the exception.
        }
    }


    /**
     * startApp() is the MIDlet call that starts the application.
     */
    protected void startApp() throws MIDletStateChangeException { 
        // Listen for downloads/commands over USB connection
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        try {
            initAndRun();
        } catch (IOException ex) { //A problem in reading the sensors. 
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
     * @param unconditional If true the MIDlet must cleanup and release all resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

}
