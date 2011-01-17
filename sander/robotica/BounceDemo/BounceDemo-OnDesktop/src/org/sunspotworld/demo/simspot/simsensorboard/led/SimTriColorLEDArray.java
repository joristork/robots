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

package org.sunspotworld.demo.simspot.simsensorboard.led;

import com.sun.spot.resources.transducers.ITransducer;
import com.sun.spot.resources.CompositeResource;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;

/**
 *
 * @author randy
 */
public class SimTriColorLEDArray extends CompositeResource implements ITriColorLEDArray {

    private SimTriColorLED[] simLeds = new SimTriColorLED[8];
    
    public SimTriColorLEDArray(){
        for(int i = 0; i < simLeds.length; i++){
            simLeds[i] = new SimTriColorLED();
        }
    }

    public int size() {
        return 8;
    }

    public ITriColorLED getLED(int arg0) {
        return simLeds[arg0];
    }

    /**
     * Return all the LEDs in an array
     *
     * @return the LEDs in an array
     */
    public ITriColorLED[] toArray() {
        ITriColorLED[] leds = new ITriColorLED[size()];
        for (int i = 0; i < leds.length; i++) {
            leds[i] = getLED(i);
        }
        return leds;
    }

    public void setOn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOff() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOn(boolean on) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRGB(int redRGB, int greenRGB, int blueRGB) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setColor(LEDColor clr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(ITransducer t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(ITransducer t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOn(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
