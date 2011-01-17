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
import org.sunspotworld.demo.simspot.SimSPOT;
import java.util.Vector;

/**
 * A simmulated 1-D accelrometer, that keeps a history of motion in order to 
 * report on acceleration. Note unlike the actual accelerometers, these must be turned on individually
 * as the performance burden of collecting histories could be significant on some platforms. Consequently,
 * you'll see a special call to turn on these accelermoters in any simulation start up code that uses them.
 * (I.e., that call is not necessary for the real accelerometers.)
 * @author randy
 */
public class SimAccelerometer extends Resource {
    
    static public final int X_ORIENTATION = 0;
    static public final int Y_ORIENTATION = 1;
    static public final int Z_ORIENTATION = 2;
    private SimSPOT motionSource;
    private boolean on = false;
    private int sampleRate = 5;   // in mSec.
    private int orientation;
    DoubleStackFL xH, yH, vxH, vyH, accelH, thetaH, dtH;  // histories for finding acceleration;
    double accel; //Most recent value averaged out of the history, though made public as an int.
    double g, accelConv;  //gravity and unit conversion factor.
    long time;
    
    /** Creates a new instance of SimAccelerometer */
    public SimAccelerometer() {
        init();
    }
    
    public void init(){
        g = 188.0;
        accelConv = 2000.0;
        xH = new DoubleStackFL();
        yH = new DoubleStackFL();
        vxH = new DoubleStackFL();
        vyH = new DoubleStackFL();
        accelH = new DoubleStackFL();
        thetaH = new DoubleStackFL();
        dtH = new DoubleStackFL();
        // initialize the histories
        for (int i = 0; i < 10; i++){
            xH.push(0.0);
            yH.push(0.0);
            vxH.push(0.0);
            vyH.push(0.0);
            accelH.push(0.0);
            thetaH.push(0.0);
            dtH.push( 10.0 );  //value someone arbitrary, as all is 0.0
        }
        accel = 0.0 ;
    }
        
    public int getValue(){
        //return (int) (472.0 + 188.0 * Math.sin(motionSource.getTheta()));
        return (int)(accel + 472.0);
    }
    
    public SimSPOT getMotionSource() {
        return motionSource;
    }
    
    public void setMotionSource(SimSPOT motionSource) {
        this.motionSource = motionSource;
    }
    
    public boolean isOn() {
        return on;
    }
    
    public void setOn(boolean b) {
        if(on && b) return;
        this.on = b;
        if(on) startMotionTrackingThread();
    }
    
    public void startMotionTrackingThread(){
        time = System.currentTimeMillis(); //to initialize it for first use
        Runnable r = new Runnable(){
            public void run(){
                while(on){
                    if(motionSource != null){
                        extractSampleFromSource();
                        updateAccel();
                    }
                    try {
                        Thread.sleep(sampleRate);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                };
            };
        };
        (new Thread(r)).start();
    }
    
    public void extractSampleFromSource(){
        double theta = motionSource.getTheta();
        double pX = motionSource.getX();
        double pY = motionSource.getY();
        
        long newTime = System.currentTimeMillis();
        double dt = Math.max(1.0, (double)(newTime - time));
        time = newTime;
        dtH.pushRm0(dt);
        
        double oldX  = xH.peekDouble();
        double oldY  = yH.peekDouble();
        double oldVx = vxH.peekDouble();
        double oldVy = vyH.peekDouble();
        double vX = (pX - oldX) /dt;
        double vY = (pY - oldY) /dt;
        double aXt =     accelConv * (vX - oldVx) / dt;
        double aYt = g + accelConv * (vY - oldVy) / dt;
        
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        double at = 0.0; //instantaneous acceleration
        if(orientation == SimAccelerometer.X_ORIENTATION)
            at =  - aXt*cos + aYt*sin;
        if(orientation == SimAccelerometer.Y_ORIENTATION)
            at =    aXt*sin + aYt*cos;
        //Note z accelerations currently not implemented, stuck at 0.0
        thetaH.pushRm0(theta);
        xH.pushRm0(pX);
        yH.pushRm0(pY);
        vxH.pushRm0(vX);
        vyH.pushRm0(vY);
        accelH.pushRm0(at);
    };
    
    public void updateAccel(){
        double a = 0.0;
        double t = 0.0;
        double dt = 0.0;
        for(int i = 0; i < accelH.size(); i++){
            dt = dtH.doubleAt(i);
            a = a + accelH.doubleAt(i) * dt;
            t = t + dt;
        }
        accel = a / t;
        //System.out.println(a + "     dt = " + dtH.peekDouble() + " " + t + "   ");
    }
    
    public int getSampleRate(){
        return sampleRate;
    }
    
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public int getOrientation() {
        return orientation;
    }
    
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    
    public void beXOrientation(){
        setOrientation(SimAccelerometer.X_ORIENTATION);
    }
    
    public void beYOrientation(){
        setOrientation(SimAccelerometer.Y_ORIENTATION);
    }
    
    public void beZOrientation(){
        setOrientation(SimAccelerometer.Z_ORIENTATION);
    }

}

class DoubleStackFL extends Vector {
    
    
    public void push(double x){
        addElement(new Double(x));
    }
    
    synchronized public void pushRm0(double x){
        if(! isEmpty()) removeElementAt(0);
        addElement(new Double(x));
    }
    
    public double peekDouble(){
        return ((Double)elementAt(size() - 1)).doubleValue();
    }
    
    public double doubleAt(int i){
        return ((Double)super.elementAt(i)).doubleValue();
    }
}