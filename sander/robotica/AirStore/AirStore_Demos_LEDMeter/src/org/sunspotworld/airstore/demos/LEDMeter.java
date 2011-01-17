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
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.Utils;
import java.util.Vector;
import javax.microedition.midlet.MIDletStateChangeException;

import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.KeyNotFoundException;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;

/**
 * LEDMeter Demo
 *
 * @author randy
 */
public class LEDMeter extends javax.microedition.midlet.MIDlet implements IAirStoreListener {

    private ITriColorLEDArray leds;   //Array of LEDs.

    private String ledMeteredVariableName = "x";   // The name of the variable to be displayed.
    private int value = 50;                        // The value to be displayed. 0<value<100. 50 by default.

    protected void startApp() throws MIDletStateChangeException {
        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        leds.setOn();

        AirStore.put(getHelpStringRecord());
        AirStore.put("LEDMeteredVariableName", "x");
        AirStore.put("x", 50);

        AirStore.addListener(this, new RecordTemplate()); //the empty RecordTemplate means Listen to everything
    }

    //***** Three methods implementing the IAirStoreListener Interface *******//

    public void notifyPut(Record r) {
        //The record may have a new LEDMeteredVariableName. If not use the existing one.
        ledMeteredVariableName = r.getString("LEDMeteredVariableName", ledMeteredVariableName);
        //The record may have a new value for ledMeteredVariableName. If not use the existing value.
        value = r.getInt( ledMeteredVariableName, value);
        display(value);
    }

    public void notifyTake(Record arg0) {
    }

    public void notifyReplace(Record out, Record in) {
        notifyTake(out);
        notifyPut(in);
    }

    /**
     * Method to display the argument 0<x<100 on the leds.
     * @param x
     */
    public void display(int x) {
        int firstBlackLED = (int) ((x / 100.0) * leds.size());  //Range of values is 0 - 100.

        for (int i = 0; i < leds.size(); i++) {
            if (i < firstBlackLED) {
                leds.getLED(i).setRGB(255, 0, 0);
            } else {
                leds.getLED(i).setRGB(0, 0, 0);
            }
        }
    }

    /**
     * This record is placed into AirStore at startup.
     * @return
     */
    public Record getHelpStringRecord(){
        Record r = new Record();
        r.set("LEDMeter Help (1)", "LEDMeter monitors whatever variable is stored in LEDMeteredVariableName");
        r.set("LEDMeter Help (2)", "That variable range should be 0-100");
        return r;
    }

    //***** Required by the MIDlet interface *******//
    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg) throws MIDletStateChangeException {
    }
}
