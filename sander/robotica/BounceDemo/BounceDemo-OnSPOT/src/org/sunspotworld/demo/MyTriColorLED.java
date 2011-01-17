/*
 * Copyright (c) 2007-2010 Sun Microsystems, Inc.
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

/*
 * MyTriColorLED.java
 *
 * Created on September 26, 2007, 4:15 PM
 */

package org.sunspotworld.demo;

import com.sun.spot.resources.Resource;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.SensorEvent;

/**
 *
 * @author vgupta, randy
 */
public class MyTriColorLED extends Resource implements ITriColorLED {
    private static MyTriColorLED[] myleds = null;
    
    ITriColorLED realLED;
    int idx;
    int r, g, b;
    boolean on;
    
    /** Creates a new instance of MyTriColorLED */
    public MyTriColorLED(int index, ITriColorLED realLED) {
        this.realLED = realLED;
        idx = index;
        r = 0; 
        g = 0;
        b = 0;
        on = false;
    }

    public static ITriColorLED[] getLEDs(ITriColorLEDArray leds) {
        if (myleds != null) return myleds;
        if (leds == null) return new MyTriColorLED[0];
        
        myleds = new MyTriColorLED[leds.size()];
        for (int i = 0; i < leds.size(); i++) {
            myleds[i] = new MyTriColorLED(i, leds.getLED(i));
        }
        return myleds;
    }
    
    public void setRGB(int redRGB, int greenRGB, int blueRGB) {
        r = redRGB;
        g = greenRGB;
        b = blueRGB;
        setOn(on);
    }

    public void setColor(LEDColor clr) {
        r = clr.red();
        g = clr.green();
        b = clr.blue();
        setOn(on);
    }

    public LEDColor getColor() {
        return new LEDColor(r, g, b);
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public void setOn() {
        setOn(true);
    }

    public void setOff() {
        setOn(false);
    }

    public void setOn(boolean on) { 
        this.on = on;
        if (this.on) {
            realLED.setRGB(r, g, b);
            realLED.setOn();
        } else {
            realLED.setOff();
        }
    }

    public boolean isOn() {
        return on;
    }
    
    public String getDescription() {
        return "Emulated TriColor LED";
    }

    public double getMaxSamplingRate() {
        return 0;
    }

    public SensorEvent createSensorEvent() {
        return new SensorEvent(this);
    }

    public void saveEventState(SensorEvent evt) {
        // nothing to do
    }

}
