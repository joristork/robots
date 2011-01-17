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
 * TemperatureSensorSampleCode.java
 *
 * Illustrates use of the on board temperature Sensor. 
 * Note the temperature is the reading from inside the ADC chip
 * on the Sun SPOT sensor board. Because it is internal to that chip
 * it will not quickly change with the air temperature. 
 * Furthermore, it will be sensitive to the power disipation of 
 * the ADC chip, which may vary depending on the application.
 *
 * This app prints out the temeperature level reading in various units.
 *
 * You can find the ADC chip on the first released SunSPOTs demo sensor
 * board at the right edge of the board. It is the middle of the three chips.
 *  
 * author: Randy Smith  
 * date: August 2, 2006 
 */ 

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.IMeasurementInfo;

import com.sun.spot.util.Utils;
import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
 
public class TemperatureSensorSampleCode extends MIDlet {   
    private ITemperatureInput tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);

    /**
     * Main application loop.
     * IOException is thrown by reading the acclerometer, temperature, or light.
     */
    private void run() throws IOException {
        
        // If you run the project from NetBeans on the host or with "ant run" on a command line,
        // and if the USB is connected, you will see System.out text output.

        System.out.println("Temperature device an instance of " + tempSensor.getClass());
        System.out.println(" located: " + tempSensor.getTagValue("location"));
        if (tempSensor instanceof IMeasurementInfo) {
            System.out.println(" with range: " + ((IMeasurementInfo)tempSensor).getMinValue() + " C" +
                                        " to " + ((IMeasurementInfo)tempSensor).getMaxValue() + " C");
        }
        
        while (true) { 
            double tempC = tempSensor.getCelsius();      // Temperature in Celcius.
            double tempF = tempSensor.getFahrenheit();   // Temperature in Farenheight
            
            System.out.println("temperature: " + tempC  + " C = " + tempF  + " F");
            
            Utils.sleep(1000);                           // Like Thread.sleep() without the exception.
        }
    }

   
    /**
     * startApp() is the MIDlet call that starts the application.
     */
    protected void startApp() throws MIDletStateChangeException { 
	// Listen for downloads/commands over USB connection
	new com.sun.spot.service.BootloaderListenerService().getInstance().start();
        try {
            run();
        } catch (IOException ex) { // A problem in reading the sensors. 
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
