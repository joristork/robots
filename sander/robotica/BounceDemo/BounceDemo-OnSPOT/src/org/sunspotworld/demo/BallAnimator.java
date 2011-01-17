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

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.util.Utils;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

/**
 * Responsible for moving the balls on this device, and informing the client if and when they "fall off" one end.
 * @author randy
 */
public class BallAnimator {
    /**
     * The LEDs upn which to display.
     */
    private ITriColorLED[] leds;
    /**
     * The object that needs to be notified when a ball escapes from an "open" end.
     */
    private SPOTBounce client;
    /**
     * The collection of balls being managed by this animator.
     */
    Vector balls = new Vector();
    /**
     * The device from which accelertion readings are taken.
     */
    private IAccelerometer3D accelerometer;

    /**
     * Sued for beeping, swooshing, etc. on devices with a built in speaker (eg. Rev 8).
     */
    private IToneGenerator soundDevice;

    /**
     * The time step increment in the animation.
     */
    private double dt; //time step, units of milliseconds.
    /**
     * The constant of friction (multiplies the speed to generate a force against the motion.)
     */
    double friction;

    /**
     * Conversion factor to change acceleration from "as read"
     * to units of LEDs / msec / msec, assuming 3 LEDs/cm
     * This theory is close but looks a little "fast" so there is the
     * extra empirical factor 0.6 )
     */
    double conv = 980 * 3.0 * 0.001 * 0.001 *  0.6;

    boolean debug = false;

    /**
     * Creates a new instance of BallAnimator
     */
    public BallAnimator() {
        init();
    }

    /**
     * initialize a new instance
     */
    public void init(){
        soundDevice = (IToneGenerator) Resources.lookup(IToneGenerator.class);
        setDt(10);
        friction = 0.0005;
    }

    /**
     * How namy balls are being managed?
     * @return the number of balls being managed.
     */
    public int getNumBalls(){
        return balls.size();
    }

    /**
     * add a new ballto those being managed.
     * @param b is the ball to add.
     */
    synchronized public void addBall(Ball b, boolean playSoundIfAvailable){
        balls.addElement(b);
        if(playSoundIfAvailable){
            makeUnwhooshSound();
        }
        sortAndReport();
    }

    /**
     * Color the first ball
     * @param key or'ed combo of Ball.RED, Ball.BLUe, Ball.GREEN
     */
    synchronized public void colorFirstBall(int key){
        Ball b;
        if(balls.size() == 0) return; else b = (Ball) balls.elementAt(0);
        b.eraseFromDisplay(leds);
        b.setColorKey(key);
        b.display(leds);
    }

    /**
     * The main call for making the balls change position (and velocity)
     * appropriately. Takes care of bouncing off the end or of notifiying the
     * client it a ball has "fallen" off an "open" end.
     */
    public void doKinematics(){
        double aX = 0.0;
        try {
            aX = accelerometer.getAccelX() * conv;
        } catch (IOException ex) {
            aX = 0.0;
            ex.printStackTrace();
        }

        for (int i = 0; i < balls.size(); i++) {
            Ball ball = (Ball) balls.elementAt(i);
            double v = ball.getV();
            double x = ball.getX();

            // standard Physics 101 here ...
            aX = aX - friction * v;           //diminish accel by friction
            v = v + aX * dt;
            x = x + v * dt + 0.5 * aX * dt * dt;

            /**
             * Add end points stickiness. This makes the ball hang up a little
             * at the left and right end. So if you tip it slowly yu might notice
             * the ball stick rather than bounces off the end. This "feature"
             * keeps the ball from flittering back and forth over the
             * radio between two "connected" SPOTs that are tipped
             * towards each other. Istead of flittering, the ball tends
             * to settle down and stay on one SPOT or the other, thanks to
             * this end-point stickiness.
             **/
            if(x > 6.99 && Math.abs(v) < 0.019) {
                x =7.0;
                v =0.0;
            }
            if(x <0.01  && Math.abs(v) < 0.019) {
                x =0.0;
                v =0.0;
            }

            ball.setX(x);
            ball.setV(v);

            synchronized(client){
                if(x < 0.0){
                    if(client.isLeftEndOpen()){
                        ball.eraseFromDisplay(leds);
                        ball.setV(Math.abs(v));
                        ball.setX(Math.min(7.0, -x));
                        client.ballOffEnd(ball, true);  //true indicates left end,
                        makeWhooshSound();
                        balls.removeElement(ball);
                    } else  {
                        makeBounceSound();
                        ball.setV(-v);
                        ball.setX(Math.min(7.0, - x));
                    }
                }
            }

            synchronized(client){
                if(x > 7.0){
                    if(client.isRightEndOpen()){
                        ball.eraseFromDisplay(leds);
                        ball.setV(Math.abs(v));
                        ball.setX(Math.min(7.0, x - 7.0));
                        client.ballOffEnd(ball, false);  //false indicates right end.
                        makeWhooshSound();
                        balls.removeElement(ball);
                    } else {
                        makeBounceSound();
                        ball.setV(-v);
                        double excess = x - 7.0;
                        ball.setX(Math.max(0.0, 7.0 - excess));
                    }
                }
            }
        }

        // Now test if there are any balls to merge together.
        Vector bls = sortAndReport();
        if(bls.size() != 0) mergeBalls(bls);
    }

    /**
     * Remove the balls in the vector bs from this SPOT.
     * Create a new ball that is in some sense a combination of
     * the balls in bs.
     *
     **/
    public void mergeBalls(Vector bs){
        Ball newBall = new Ball(); //Merge the balls into this new ball.
        double x = 0.0;            // the new ball's position
        double v = 0.0;            // the new ball's velocity'
        int ck = 0;                // the new color key.
        //...now determine approprite values for v,x, and ck
        for (int i = 0; i < bs.size(); i++) {
            Ball b = (Ball) bs.elementAt(i);
            x = x + b.getX();
            v = v + b.getV();
            ck = ck | b.getColorKey();
            balls.removeElement(bs.elementAt(i));
            b.eraseFromDisplay(leds);
        }
        x = x / bs.size();
        v = v / bs.size();

        Random r = new Random();
        flash(x);              // Make the display flash at the point the new ball appears.
        // Make a random slorching sound
        makeMergeSound();
        newBall.setX(x);
        newBall.setV(v);
        newBall.setColorKey(ck);
        balls.addElement(newBall);
        sortAndReport();       //Just to resort the ball vector
    }

    /**
     * Operates on the vector of SPOT balls to BOTH
     * 1] sort them into increasing order, and
     * 2] report a vector of balls to merge. The merge
     * criteria are that the balls pass through each other
     * (or occupy the same position) at low relative speed.
     * Finding the balls out of order (and therefore in
     * need of sorting) is taken as indicating
     * they have passed through each other.
     *
     **/
    public Vector sortAndReport(){
        Vector r = new Vector();  //The reported vector of balls to merge.
        if ( balls.size() <= 1 ) return r;
        for (int i = 1; i < balls.size(); i++) {
            Ball bi = (Ball) balls.elementAt(i);
            double x = bi.getX();
            int j = i-1;
            /* note we want equal positions to count as a reported case */
            Ball bj = ((Ball) balls.elementAt(j));
            //Is ball j (bj) in the right place?
            while( (j >= 0) && (x <= bj.getX())){
                //Ball #j  (bj) is out of place.
                //Must have passed ball #i, so maybe report this case for merging
                //Test velcotiy difference.
                if( Math.abs(bj.getV() - bi.getV()) < 0.03){
                    r.addElement(bi);
                    r.addElement(bj);
                }
                //do the sort
                balls.removeElement(bi);
                balls.insertElementAt(bi, j);
                j = j-1;
            }
        }
        return r;
    }

    /**
     * Cause all the balls to display.
     */
    public void display(){
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = (Ball) balls.elementAt(i);
            ball.display(leds);
        }
    }

    /**
     * Make a bright, brief flash of the LEDs at point x.
     **/
    synchronized public void flash(double x){
        int[] r = new int[leds.length];
        int[] g = new int[leds.length];
        int[] b = new int[leds.length];
        int ledMin = Math.min( leds.length, Math.max(0, (int) (x - 1.75)));
        int ledMax = Math.min( leds.length, Math.max(0, (int) (x + 2.75)));
        for (int i = 0; i < leds.length; i++) {
            r[i] = leds[i].getRed();
            g[i] = leds[i].getGreen();
            b[i] = leds[i].getBlue();
        }
        for (int i = ledMin; i < ledMax; i++) {
            leds[i].setRGB(255,255,255);
        }
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < leds.length; i++) {
            leds[i].setRGB(r[i], g[i], b[i]);
        }
    }

    public void makeBounceSound(){
        if (soundDevice != null) {
            soundDevice.startTone(5000.0);
            Utils.sleep(4);
            soundDevice.stopTone();
        }
    }

    public void makeWhooshSound(){
        if (soundDevice != null) {
            for (double f = 220; f <= 3520; f *= 2) {
                soundDevice.startTone(f, 0);
            }
            soundDevice.stopTone();
        }
    }

    public void makeUnwhooshSound(){
        if (soundDevice != null) {
            for (double f = 3520; f >= 220; f /= 2) {
                soundDevice.startTone(f, 0);
            }
            soundDevice.stopTone();
        }
    }

    public void makeMergeSound(){
        if (soundDevice != null) {
            soundDevice.startTone(180.0);
            Utils.sleep(50);
            soundDevice.stopTone();
        }
    }

    /**
     * accessor for the accelerometer
     * @return The accelerometer object used by this animator.
     */
    public IAccelerometer3D getAccelerometer() {
        return accelerometer;
    }

    /**
     * Set the accelerometer used by this animator.
     * @param accelerometer the new accelerometer for this animator
     */
    public void setAccelerometer(IAccelerometer3D accelerometer) {
        this.accelerometer = accelerometer;
    }

    /**
     * Accessor returning the client
     * @return the client who is using this animator to move balls, and who receives notification when a ball falls off an open end.
     */
    public SPOTBounce getClient() {
        return client;
    }

    /**
     * set the client
     * @param client The client who uses this animator to manage the ball motion, and who gets notified if a ball falls off an open end.
     */
    public void setClient(SPOTBounce client) {
        this.client = client;
    }

    /**
     * Accessor for the leds
     * @return The leds used b this animator
     */
    public ITriColorLED[] getLeds() {
        return leds;
    }

    /**
     * Set the LED array.
     * @param leds The new array of LEDs used by this animator.
     */
    public void setLeds(ITriColorLED[] leds) {
        this.leds = leds;
    }

    /**
     * accessthe time increment
     * @return the time increment used by the animator.
     */
    public double getDt() {
        return dt;
    }

    /**
     * set the time increment
     * @param dt the new value of time increment for use in the animations.
     */
    public void setDt(double dt) {
        this.dt = dt;
    }
}
