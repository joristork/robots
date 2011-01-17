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

package org.sunspotworld.demo.simspot.simsensorboard.led;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.SensorEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;
import javax.swing.JComponent;

/**
 * A Simulated tri-colored LED. Actually draws on the screen.
 * @author randy
 */
public class SimTriColorLED extends JComponent implements ITriColorLED  {
    
    private int r,g,b;
    private boolean on = false;
    
    public SimTriColorLED() { 
        setRGB(0,0,0);
    }
    
    public Dimension getPreferredSize(){
        return new Dimension(30,30);
    }
    
    public void setPosition(int x, int y){
        setBounds(x,y, getPreferredSize().width, getPreferredSize().height);
    }
    
    public void paintComponent(Graphics gr){
        int r2, g2, b2;
        Graphics2D gr2 = (Graphics2D)gr;
        if (isOn()) {
            int[] rgbS = getRGBForScreen();
            r2 = rgbS[0];
            g2 = rgbS[1];
            b2 = rgbS[2];
        } else {
            r2 = 0;
            g2 = 0;
            b2 = 0;
        }
        gr.setColor(new Color(r2, g2, b2));
        gr.fillRect(12,12,getWidth()-24, getHeight()-24);
    }
    
    public void paintLensFlare(Graphics gr){
        if(!isOn()) return;
        Graphics2D gr2 = (Graphics2D)gr;
        int[] rgbS = getRGBForScreen();
        int r2 = rgbS[0];
        int g2 = rgbS[1];
        int b2 = rgbS[2];
        gr.setColor(new Color(r2, g2, b2)); 
        Composite comp = gr2.getComposite();
        int rule = ((AlphaComposite)comp).getRule();
        float opacity = (r2 + g2 + b2) / (3.0f * 255.0f ) * 0.4f;
        gr2.setComposite(AlphaComposite.getInstance(rule, opacity));
        gr2.fillOval(0,0,getWidth(),getHeight());
        if( (r + b + g) > 20 ) {
            gr2.setComposite(AlphaComposite.getInstance(rule, 1.0f));
            gr2.drawLine(0,0,getWidth(),getHeight());
            gr2.drawLine(getWidth(),0,0,getHeight());
        }
    }
    
    /**
     * This "undoes" the linearization appropriae for the LED filaments. Hence we 
     * use a 2/3 poewr to cancel out the 3/2 power. called by the Sun SPOT code. 
     * In addition, some small offset seems to be appropriate for small valuse of RBG.
     */
    public int[] getRGBForScreen(){
        int[] v = new int[3];
        int r2, g2, b2; 
        if(r + g + b != 0){
            double r1 =  Math.pow(r / 255.0, 0.6777) * 255.0;
            double g1 =  Math.pow(g / 255.0, 0.6777) * 255.0;
            double b1 =  Math.pow(b / 255.0, 0.6777) * 255.0;
            r2 = (int) (4.0 * r1 +       g1 +       b1 + 0.5);
            g2 = (int) (      r1 + 4.0 * g1 +       b1 + 0.5);
            b2 = (int) (      r1 +       g1 + 4.0 * b1 + 0.5);
            r2 = Math.min(255, r2);
            g2 = Math.min(255, g2);
            b2 = Math.min(255, b2); 
        } else {
            r2 = g2 = b2 = 0;
        }
        v[0] = r2;
        v[1] = g2;
        v[2] = b2;
        return v;
    }
    
    public void setRGB(int i, int i0, int i1) {
        r = i;
        g = i0;
        b = i1;
        repaint();
    }
    
    public void setColor(LEDColor ledRgb) {
        setRGB(ledRgb.red(),ledRgb.green(),ledRgb.blue());
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
    
    public LEDColor getColor() {
        return new LEDColor(r,g,b);
    }
    
    public void setOn() {
        on = true;
    }
    
    public void setOff() {
        on = false;
    }
    
    public void setOn(boolean b) {
        on = b;
    }

    public boolean isOn() {
        return on; 
    }
    
    public String getDescription() {
        return "Tri-color LED";
    }

    public double getMaxSamplingRate() {
        return 0;
    }

    // Resource-related methods

    private Vector tags = new Vector();

    /**
     * Get the array of tags associated with this resource.
     *
     * @return the array of tags associated with this resource.
     */
    public String[] getTags() {
        String [] a = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            a[i] = (String)tags.elementAt(i);
        }
        return a;
    }

    /**
     * Add a new tag to describe this resource.
     *
     * @param tag the new tag to add
     */
    public void addTag(String tag) {
        tags.addElement(tag);
    }

    /**
     * Remove an existing tag describing this resource.
     *
     * @param tag the tag to remove
     */
    public void removeTag(String tag) {
        tags.removeElement(tag);
    }

    /**
     * Check if the specified tag is associated with this resource.
     *
     * @param tag the tag to check
     * @return true if the tag is associated with this resource, false otherwise.
     */
    public boolean hasTag(String tag) {
        boolean result = false;
        if (tag != null) {
            for (int i = 0; i < tags.size(); i++) {
                if (tag.equalsIgnoreCase((String) tags.elementAt(i))) {
                    result = true;
                    break;
                }
            }
        } else {
            result = true;
        }
        return result;
    }

  /**
     * Treat each tag as being "key=value" and return the value of the first tag
     * with the specified key. Return null if no tag has the specified key.
     *
     * @param key the string to match the key
     * @return the tag value matching the specified key or null if none
     */
    public String getTagValue(String key) {
        String result = null;
        if (key != null) {
            String keyPlusEquals = key + "=";
            for (int i = 0; i < tags.size(); i++) {
                String tag = (String) tags.elementAt(i);
                if (tag.startsWith(keyPlusEquals)) {
                    result = tag.substring(keyPlusEquals.length());
                    break;
                }
            }
        }
        return result;
    }

    public SensorEvent createSensorEvent() {
        return new SensorEvent(this);
    }

    public void saveEventState(SensorEvent evt) {
        // nothing to do
    }

}
