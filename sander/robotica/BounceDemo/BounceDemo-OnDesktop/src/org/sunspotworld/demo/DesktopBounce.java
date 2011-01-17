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

import com.sun.spot.peripheral.ISpot;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.util.Utils;

import org.sunspotworld.demo.simspot.SimDisplayPanel;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimAccelerometer;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimAccelerometer3D;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimSwitch;
import org.sunspotworld.demo.simspot.simsensorboard.led.SimTriColorLEDArray;

import java.io.IOException;
import org.sunspotworld.demo.simspot.SimSPOT;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimToneGenerator;

/**
 * The main class for running a simulated Ectoplasmic Bouncing Ball Demo/
 *
 * Important to note: Because this class inherits from SPOTBounce, it
 * actually runs code that also runs on the Sun SPOT. It is therefore important 
 * that the companion code be in the expected place, as a peer to this project's 
 * directories.
 *
 * Here we add a main() method to open a window,
 * replace all the necessary real transducers with simulated ones, 
 * and overrides initialize() to turn on one simulated accelerometer. 
 * Other than that this class runs inherited code in the companion
 * project for the Sun SPOT device.
 *
 * @author randy
 */
public class DesktopBounce extends SPOTBounce {
 
    @Override
     public void initialize(){
        ((SimAccelerometer3D)(Resources.lookup(IAccelerometer3D.class))).getXAxis().setOn(true);
        super.initialize();
    }

    public static void replaceRealWithSimulatedResources(){
        Resources.remove(Resources.lookup(IToneGenerator.class));
        Resources.remove(Resources.lookup(IAccelerometer3D.class));
        Resources.remove(Resources.lookup(ITriColorLEDArray.class));
        Resources.remove(Resources.lookup(ISwitch.class, "SW1"));
        Resources.remove(Resources.lookup(ISwitch.class, "SW2"));

        Resources.add(new SimToneGenerator());
        Resources.add(new SimAccelerometer3D());
        Resources.add(new SimTriColorLEDArray());
        SimSwitch s1 = new SimSwitch();
        s1.addTag("SW1");
        Resources.add(s1);
        SimSwitch s2 = new SimSwitch();
        s2.addTag("SW2");
        Resources.add(s2);
    }
    
    public static void main(String[] args) {
                replaceRealWithSimulatedResources();
                SimDisplayPanel sdp = new SimDisplayPanel();
                sdp.initBaseStation();
                sdp.openInWindow();
                try {
                    (new DesktopBounce()).start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }
}
