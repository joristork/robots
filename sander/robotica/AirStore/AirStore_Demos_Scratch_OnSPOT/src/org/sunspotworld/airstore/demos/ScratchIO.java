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

package org.sunspotworld.airstore.demos;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.Utils;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.midlet.MIDletStateChangeException;

import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.KeyNotFoundException;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;

/**
 *  ScratchIO.java
 */

public class ScratchIO extends javax.microedition.midlet.MIDlet implements IAirStoreListener {

    String[] scratchVarNames = {"ledX", "ledLevel" };

    private ISwitch sw0, sw1;
    private IAccelerometer3D accelerometer;
    private ILightSensor lightSensor;
    private ITemperatureInput temperature;
    private ITriColorLED[] leds;
    private String scratchID;

    private Ball ball; //A moveable light on the LEDs.

    private int spotDelta = 1; //Threshold for reporting sensor reading changes.

    protected void startApp() throws MIDletStateChangeException {
        accelerometer = (IAccelerometer3D)Resources.lookup(IAccelerometer3D.class);
        lightSensor = (ILightSensor)Resources.lookup(ILightSensor.class);
        temperature  = (ITemperatureInput)Resources.lookup(ITemperatureInput.class);
        leds = ((ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class)).toArray();
        sw0  = (ISwitch)Resources.lookup(ISwitch.class, "SW1");
        sw1  = (ISwitch)Resources.lookup(ISwitch.class, "SW2");

        ball = new Ball();
        scratchID = System.getProperty("spot.scratch.scratchID");

        for (int i = 0; i < leds.length; i++) { leds[i].setOn(); }

        if(scratchID == null) scratchID = "";
        AirStore.put("ScratchIO", "started for scratchID ="+ scratchID);

        startSW0Thread();
        startSW1Thread();

        /**
         * The empty template matches any record, so this SPOT will
         * be notified of every record that arrives in AirStore.
         * See notifyPut() below...
         **/
        AirStore.addListener(this, new RecordTemplate()); 

        int aX=0, aY=0, aZ=0, aTotal=0;
        int temp = 0;
        int light = 0;

        int oldAx=0, oldAy=0, oldAz=0, oldATotal=0;
        int oldTemp = 0;
        int oldLight = 0;
        while (true) {
            try {
                aX =  clip((int) Math.abs(50.0 + (accelerometer.getAccelX() * 50.0)));
                aY =  clip((int) Math.abs(50.0 + (accelerometer.getAccelY() * 50.0)));
                aZ =  clip((int) Math.abs(50.0 + (accelerometer.getAccelZ() * 50.0)));
                aTotal  =  clip((int) Math.abs(50.0 + (accelerometer.getAccel()  * 50.0)));
                temp = clip((int)temperature.getFahrenheit());
                light = clip(lightSensor.getAverageValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            spotDelta = AirStore.getInt("spotDelta", 1);

            if(Math.abs(aX - oldAx) > spotDelta) {
                AirStore.put("aX" + scratchID, aX);
                oldAx = aX;
            }
            if(Math.abs(aY - oldAy) > spotDelta) {
                AirStore.put("aY" + scratchID, aY);
                oldAy = aY;
            }
            if(Math.abs(aZ - oldAz) > spotDelta) {
                AirStore.put("aZ" + scratchID, aZ);
                oldAz = aZ;
            }
            if(Math.abs(aTotal - oldATotal) > spotDelta) {
                AirStore.put("aTotal" + scratchID, aTotal);
                oldATotal = aTotal;
            }
            if(Math.abs(light - oldLight) > spotDelta) {
                AirStore.put("light" + scratchID, light);
                oldLight = light;
            }
            if(Math.abs(temp - oldTemp) > spotDelta) {
                AirStore.put("temp" + scratchID, temp);
                oldTemp = temp;
            }
            Utils.sleep(AirStore.getInt("spotPollMS", 500));
        }
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg) throws MIDletStateChangeException {
    }

    private void startSW0Thread() {
        (new Thread(){ public void run(){ while(true){
            sw0.waitForChange();
            int closed  = sw0.isClosed() ? 100 : 0;
            AirStore.put("sw0" + scratchID, closed);
        }}}).start();
    }

    private void startSW1Thread() {
        (new Thread(){ public void run(){ while(true){
            sw1.waitForChange();
            int closed  = sw1.isClosed() ? 100 : 0;
            AirStore.put("sw1" + scratchID, closed);
        }}}).start();
    }

    public int clip(int val){
        return Math.max(0, Math.min(100, val));
    }

     /**
     * Because this SPOT is listening to AirStore for every Record that is stored
     * (addListener(...) was called in startApp() above)
     * this method will get called when a record arrives.
     * @param r
     */
    public void notifyPut(Record r) {
        /*
         *  Example of looking for a record with a particaulr variable in it
         *  in this case, ledX
         */
        int pos = r.getInt("ledX", -1);  //The -1 is the "if the key ledX is absent" value.
        if (pos != -1) {
            ball.setX(pos / 100.0 * 7.0);
            ball.display(leds);
        }
    }

    public void notifyTake(Record arg0) {
    }

    public void notifyReplace(Record arg0, Record arg1) {
        notifyPut(arg1);
    }
}
