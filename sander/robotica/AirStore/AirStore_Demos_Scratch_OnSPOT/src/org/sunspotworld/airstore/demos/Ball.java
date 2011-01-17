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


import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.squawk.util.StringTokenizer;

/**
 * A ball can be one of three colors, keeps track of velocity and position, and can display itself on an array of LEDs.
 * It caches the current and previous LED index for erasing and as an efficiency in displaying.
 * It can also encode and decode itself to and from Strings (presumably to be transmitted or received over the radio).
 * @author randy
 */
public class Ball {

    /**
     *  colorKey is logical OR of RED, GREEN, BLUE
     */
    public static final int RED=1;

    public static final int GREEN=2;

    public static final int BLUE=4;

    /**
     * One of the three integer values: <CODE>RED</CODE>, <CODE>GREEN</CODE>, or <CODE>BLUE</CODE>
     */
    private int colorKey;

    /**
     * How bright the ball appears when displaye.
     */
    private int brightness;

    /**
     * In general the ball shows up on two LEDs, partly on one, partly on the next, with varying brightness.
     * This index tracks which one is currently the left most one. The ball may also show up on LED indexed at ledIndex + 1.
     */
    private int ledIndex;

    /**
     * Tracks where the ball was displayed last taime. In general the ball shows up on two LEDs, partly on one, partly on the next, with varying brightness.
     * This index tracks which one was the left most one last time. The ball may habe also showed up on LED indexed at lastLEDIndex + 1.
     */
    private int lastLEDIndex;

    /**
     * The veolicty of the ball, roughly in units of LEDs / msec.
     */
    private double v  = 0.0;
    /**
     * Position of the ball, in units of LEDs.
     */
    private double x  = 3.5;   //initial position is about halfway along the LEDs.

    /**
     * Position as last animation frame, an optimization for collsion detection
     */
    private double oldX = 3.5;

    /** Creates a new instance of Ball */
    public Ball() {
        init();
    }

    /**
     * initialize a new  instance
     */
    public void init(){
        ledIndex = (int)x;
        setColorKey(BLUE);
        setBrightness(100);
    }

    /**
     * Encode the state of this ball as a string. State should be everything that is need to reconstrauct it, space separated.
     * Also see newFromSerializationString().
     * @return A string encoding the state of this ball.
     */
    public String serializationString(){
        return "" + getX() + " " + getV() + " " + getColorKey() + " " + getBrightness();
    }

    /**
     * Decode the state of this ball from the given Strings, which have been parsed into pieces.
     * Also see newFromSerializationString().
     * @param strs An array of strings, from which the ball can be reconstructed
     * @return The resulting new ball.
     */
    public static Ball newFromSerializationStrings(String[] strs){
        Ball b = new Ball();

        b.setX(Double.parseDouble(strs[1]));
        b.setV(Double.parseDouble(strs[2]));
        b.setColorKey(Integer.parseInt(strs[3]));
        b.setBrightness(Integer.parseInt(strs[4]));

        return b;
    }


    /**
     * Draw this in the color indicated by <CODE>colorKey</CODE>, and in the brightness indicated
     * by <CODE>brightness</CODE> at the position indicated by <CODE>x</CODE>. Note the ball will
     * usually appear partly on one LED and partly on the next, deplending on the fraction of the
     * way x is between two integer values.
     * @param leds The array of leds upon which to display.
     */
    public void display(ITriColorLED[] leds){
        /*
         * Light up the two LEDs appropriately, so it appears proportinaltly
         * on one with the rest of the light on the next.
         * NOTE LEAVE RED ALONE.
         */
        lastLEDIndex = ledIndex;    // Keep track of the previously used LEDs.
        double whole = Math.floor(x);
        double rem = x - whole;     // The fraction of the way between LEDs
        ledIndex = Math.min(7,(int)whole);

        ITriColorLED led1; //utility variables
        ITriColorLED led2;
        // If the two LEDs involved are different than last time, blank them out.
        if(ledIndex != lastLEDIndex) {
            led1 = leds[lastLEDIndex];
            led2 = leds[Math.min(7,lastLEDIndex + 1)];
            if((getColorKey() & RED) != 0){
                led1.setRGB(0, led1.getGreen(), led1.getBlue());
                led2.setRGB(0, led2.getGreen(), led2.getBlue());
            }
            if((getColorKey() & GREEN) != 0) {
                led1.setRGB(led1.getRed(), 0 ,led1.getBlue());
                led2.setRGB(led2.getRed(), 0, led2.getBlue());
            }
            if((getColorKey() & BLUE) != 0) {
                led1.setRGB(led1.getRed(), led1.getGreen() ,0);
                led2.setRGB(led2.getRed(), led2.getGreen(), 0);
            }
        }
        double lbfL = linearBrightnessForLED(1.0 - rem);
        led1 = leds[ledIndex];
        if((getColorKey() & RED) != 0){
            int newR = (int) (getBrightness() * lbfL);
            if(newR != led1.getRed()) led1.setRGB(newR, led1.getGreen(), led1.getBlue());
        }
        if((getColorKey() & GREEN) != 0){
            int newG = (int) (getBrightness() * lbfL);
            if(newG != led1.getGreen()) led1.setRGB(led1.getRed(), newG, led1.getBlue());
        }
        if((getColorKey() & BLUE) != 0){
            int newB = (int) (getBrightness() * lbfL);
            if(newB != led1.getBlue()) led1.setRGB(led1.getRed(), led1.getGreen(), newB);
        }
        if(ledIndex < 7){
            led2 = leds[ledIndex + 1];
            lbfL = linearBrightnessForLED(rem);
            if((getColorKey() & RED) != 0){
                ;
                int newR = (int) (getBrightness() * lbfL);
                if(newR != led2.getRed())   led2.setRGB(newR, led2.getGreen(), led2.getBlue());
            }
            if((getColorKey() & GREEN) != 0){
                int newG = (int) (getBrightness() * lbfL);
                if(newG != led2.getGreen()) led2.setRGB(led2.getRed(), newG, led2.getBlue());
            }
            if((getColorKey() & BLUE) != 0){
                int newB = (int) (getBrightness() * lbfL);
                if(newB != led2.getBlue())  led2.setRGB(led2.getRed(), led2.getGreen(), newB );
            }
        }
    }


    /**
     * A utility function that calculates a linear brightness. This formula, involving the 3/2 power, was determined empiraically.
     * Note, this function may only be only appropriate for one particular way of drving the LEDs.
     * @param x The desired proportion of full brightness
     * @return The actual value to pass in to the <CODE>setRGB()</CODE> type calls to acheive the desired brightness,
     */
    static public double linearBrightnessForLED(double x){
        return  x * Math.sqrt(x);
    }

    /**
     * Use the stored state <CODE>ledIndex</CODE> to erase self from the display.
     * @param leds The leds from which to erase this ball.
     */
    public void eraseFromDisplay(ITriColorLED[] leds){
        ITriColorLED led1 = leds[ledIndex];
        ITriColorLED led2 = leds[Math.min(7,ledIndex + 1)];
        if((getColorKey() & RED) != 0){
            led1.setRGB(0, led1.getGreen(), led1.getBlue());
            led2.setRGB(0, led2.getGreen(), led2.getBlue());
        }
        if((getColorKey() & GREEN) != 0) {
            led1.setRGB(led1.getRed(), 0 ,led1.getBlue());
            led2.setRGB(led2.getRed(), 0, led2.getBlue());
        }
        if((getColorKey() & BLUE) != 0) {
            led1.setRGB(led1.getRed(), led1.getGreen() ,0);
            led2.setRGB(led2.getRed(), led2.getGreen(), 0);
        }
    }

    /**
     * Returns the ball velocity
     * @return The current velocity in units of LEDs / msec.
     */
    public double getV() {
        return v;
    }

    /**
     * Set the ball velocity.
     * @param v The new value for velocity
     */
    public void setV(double v) {
        this.v = v;
    }

    /**
     * Returns the current position
     * @return The current position in units of LEDs.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the current position
     * @param x The desired new position in units of LEDs
     */
    public void setX(double x2) {
        oldX = x;
        this.x = x2;
    }

    /**
     *
     */
    public void mixInColorKey(int ck){
        setColorKey(getColorKey() | ck);
    }
    /**
     * Gets the current color key (RED, GREEN, or BLUE)
     * @return The current <CODE>colorKey</CODE>.
     */
    public int getColorKey() {
        return colorKey;
    }

    /**
     * Set the current color key to <CODE>RED</CODE>, <CODE>GREEN</CODE>, or <CODE>BLUE</CODE>
     * @param colorKey One of <CODE>RED</CODE>, <CODE>GREEN</CODE>, or <CODE>BLUE</CODE>
     */
    public void setColorKey(int colorKey) {
        this.colorKey = colorKey;
    }

    /**
     * Returns the current brightness
     * @return The current brightness, from 0-255.
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Sets the current brightness
     * @param brightness The new value for brightness, from 0-255.
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public double getOldX() {
        return oldX;
    }

}
