/*
 * SensorSampler.java
 *
 * Copyright (c) 2008-2010 Sun Microsystems, Inc.
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
 */

package org.sunspotworld.demo;

import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.util.Utils;
import java.util.Calendar;
import java.util.Date;
import javax.microedition.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * This application is the 'on SPOT' portion of the DatabaseDemo. It
 * periodically samples a sensor value on the SPOT and transmits it to
 * a desktop application (the 'on Desktop' portion of the DatabaseDemo)
 * for storage in a database.
 *
 * @author: Vipul Gupta
 */
public class SensorSampler extends MIDlet {
    private static final int DATA_SINK_PORT = 67;
    
    protected void startApp() throws MIDletStateChangeException {
        RadiogramConnection rCon = null;
        Datagram dg = null;
        String ourAddress = System.getProperty("IEEE_ADDRESS");
        long now = 0L;
        int reading = 0;
        Calendar cal = Calendar.getInstance();
        String ts = null;
        ILightSensor lightSensor = (ILightSensor)Resources.lookup(ILightSensor.class);
        ITriColorLED led = (ITriColorLED)Resources.lookup(ITriColorLED.class, "LED7");
        
        System.out.println("Starting sensor sampler application on " +
                ourAddress + " ...");

	// Listen for downloads/commands over USB connection
	new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        try {
            // Open up a broadcast connection at the data sink port
            // where the 'on Desktop' portion of this demo is listening
            rCon = (RadiogramConnection) Connector.open(
                    "radiogram://broadcast:" + DATA_SINK_PORT);
            dg = rCon.newDatagram(rCon.getMaximumLength());
        } catch (Exception e) {
            System.err.println("Caught " + e +
                    " in connection initialization.");
            notifyDestroyed();
        }
        
        while (true) {
            try {
                // Get the current time and sensor reading
                now = System.currentTimeMillis();
                reading = lightSensor.getValue();
                
                // Flash an LED to indicate a sampling event
                led.setRGB(255, 255, 255);
                led.setOn();
                Utils.sleep(50);
                led.setOff();
                
                cal.setTime(new Date(now));
                ts = cal.get(Calendar.YEAR) + "-" +
                        (1 + cal.get(Calendar.MONTH)) + "-" +
                        cal.get(Calendar.DAY_OF_MONTH) + " " +
                        cal.get(Calendar.HOUR_OF_DAY) + ":" +
                        cal.get(Calendar.MINUTE) + ":" +
                        cal.get(Calendar.SECOND);
                
                System.out.println("Reading at " + ts + " is " + reading);
                
                // Package our identifier, timestamp and sensor reading
                // into a radio datagram and send it.
                dg.reset();
                dg.writeUTF(ourAddress);
                dg.writeUTF(ts);
                dg.writeInt(reading);
                rCon.send(dg);
                
                // Go to sleep to conserve battery
                Utils.sleep(6000 - (System.currentTimeMillis() - now));
            } catch (Exception e) {
                System.err.println("Caught " + e +
                        " while collecting/sending sensor sample.");
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
