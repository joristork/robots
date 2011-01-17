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

package org.sunspotworld.demo.simspot.simsensorboard.io;

import com.sun.spot.resources.Resource;
import com.sun.spot.resources.transducers.Accelerometer3DEvent;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.IMeasurementRange;
import com.sun.spot.resources.transducers.IMeasurementRangeListener;
import com.sun.spot.resources.transducers.SensorEvent;
import java.io.IOException;

/**
 * A simluation accelerometer that consists simply of three simulated 1D accelerometers.
 * @author randy
 */
public class SimAccelerometer3D extends Resource implements IAccelerometer3D, IMeasurementRange {
    private SimAccelerometer x;
    private SimAccelerometer y;
    private SimAccelerometer z;
    
    private static final double ZERO_OFFSET = 472.0;
    private static final double GAIN = 188.0;
    
    /**
     *   Creates a new instance of SimAccelerometer3D.
     *   Creates 3 1-D simulated acceleromteres, each to handle one axis.
     */
    public SimAccelerometer3D() {
        SimAccelerometer a = new SimAccelerometer();
        a.beXOrientation();
        setXAxis(a);
        a = new SimAccelerometer();
        a.beYOrientation();
        setYAxis(a);
        a = new SimAccelerometer();
        a.beZOrientation();
        setZAxis(a);
    }
        
    public void setXAxis(SimAccelerometer x) {
        this.x = x;
    }
     
    public void setYAxis(SimAccelerometer y) {
        this.y = y;
    }
     
    public void setZAxis(SimAccelerometer z) {
        this.z = z;
    }

    public SimAccelerometer getXAxis() {
        return x;
    }

    public SimAccelerometer getYAxis() {
        return y;
    }

    public SimAccelerometer getZAxis() {
        return z;
    }
    
    public double getAccelX() {
        return (x.getValue() - ZERO_OFFSET) / GAIN;
    }
    public double getAccelY() {
        return (x.getValue() - ZERO_OFFSET) / GAIN;
    }
    public double getAccelZ() {
        return (x.getValue() - ZERO_OFFSET) / GAIN;
    }
    public double getAccel() {
        double x = getAccelX();
        double y = getAccelY();
        double z = getAccelZ();
        return Math.sqrt(x*x + y*y + z*z);
    }

    public double[] getAccelValues() {
        return new double[]{getAccelX(), getAccelY(), getAccelZ()};
    }

    public double getTiltX() {
        return Math.asin(getAccelX() / getAccel());
    }
    public double getTiltY() {
        return Math.asin(getAccelY() / getAccel());
    }
    public double getTiltZ() {
        return Math.asin(getAccelZ() / getAccel());
    }

    public double getAccel(int i) {
        if(i == 1) return getAccelX();
        if(i == 2) return getAccelY();
        if(i == 3) return getAccelZ();
        throw new RuntimeException("Cannot currently accelerate along t axis or other higher dimensions");
    }

    public double getTilt(int i) {
        if(i == 1) return getTiltX();
        if(i == 2) return getTiltY();
        if(i == 3) return getTiltZ();
        throw new RuntimeException("Cannot currently tilt along t axis or other higher dimensions");
    }

    public int getNumberRanges() {
        return 2;
    };

    public int getCurrentRange() {
        return 0;
    }

    public void setCurrentRange(int range) {

    }

    public double getMinValue(int index) {
        return -getMaxValue(index);
    }

    public double getMaxValue(int index) {
        return (index == 0) ? 2 : 6;
    }

    public double getResolution(int index) {
        return (index == 0) ? (4.0 / 1024.0) : (12.0 / 1024.0);
    }

    public double getAccuracy(int index) {
        return 0.1;
    }

    public double getMinValue() {
        return getMinValue(0);
    }

    public double getMaxValue() {
        return getMaxValue(0);
    }

    public double getResolution() {
        return getResolution(0);
    }

    public double getAccuracy() {
        return getAccuracy(0);
    }

    public String getDescription() {
        return "Sim accelerometer";
    }

    public double getMaxSamplingRate() {
        return 0;
    }

    public SensorEvent createSensorEvent() {
        SensorEvent evt = new Accelerometer3DEvent(this);
        return evt;
    }

    public void saveEventState(SensorEvent evt) {
        ((Accelerometer3DEvent) evt).setAccels(getAccelX(), getAccelY(), getAccelZ());
    }

    public void addIMeasurementRangeListener(IMeasurementRangeListener il) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeIMeasurementRangeListener(IMeasurementRangeListener il) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IMeasurementRangeListener[] getIMeasurementRangeListeners() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}