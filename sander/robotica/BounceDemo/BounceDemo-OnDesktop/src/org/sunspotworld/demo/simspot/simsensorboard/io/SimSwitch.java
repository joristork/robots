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
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.SensorEvent;
import com.sun.spot.resources.transducers.SwitchEvent;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * A simulated push button like the one on the Sun SPOT device, but reads the mouse. 
 * Rather than listen for mouse events directly, it is simpler to have the class SimSPOT do this,
 * as the rotation of the simulated spot makes handling mouse events complex otherwise.
 * @author randy
 */
public class SimSwitch extends Resource implements ISwitch  {
    
    private int x = 0;
    private int y = 0;
    private int width = 22;
    private int height = 14;  
    
    private Vector switchListeners = new Vector();
    
    boolean closed;

    int pollingMS;
    private String name = "";
    /**
     * Creates a new instance of SimSwitch
     */
    public SimSwitch() {
        init();
        startNotifyThread();
    }

    public void init(){
        pollingMS = 50; 
        closed = false; 
    }
    
    public boolean isOpen() {
        return ! closed;
    }

    public boolean isClosed() {
        return closed;
    }
    
    public void startNotifyThread(){
        final ISwitch switchThis = this;
        Runnable r = new Runnable(){
            public void run(){
                
                while(true){
                    waitForChange();
                    SwitchEvent evt = (SwitchEvent)createSensorEvent();
                    saveEventState(evt);
                    for (int i = 0; i < switchListeners.size(); i++) {
                        ISwitchListener sl = (ISwitchListener) (switchListeners.elementAt(i));
                        if(isClosed()) sl.switchPressed(evt);
                        else sl.switchReleased(evt);
                    }
                }
            }
        };
        (new Thread(r)).start();
    }
    
    
    public Thread createChangeWatcherThread(){
        Runnable r = new Runnable(){
            public void run(){
                boolean wasClosed = closed;
                while(wasClosed == closed){
                    wasClosed = closed;
                    try {
                        Thread.sleep(pollingMS);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            };
        };
        Thread t = new Thread(r);
        t.start();
        return t;
    }
     
    public void waitForChange() {
        try {
            createChangeWatcherThread().join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){ 
        if(closed){
            Graphics2D g2 = (Graphics2D) g;
            Composite cSave = g2.getComposite();
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F);
            g2.setComposite(ac);
            g2.setColor(Color.black);
            g2.fillRect(0,0,getWidth(),getHeight());
            g2.setComposite(cSave);
        }
    }

    public void mousePressed(MouseEvent e) { 
        closed = true;   
    }

    public void mouseReleased(MouseEvent e) {
        closed = false;   
    }

    public void setBounds(int x0, int y0, int w, int h){
        x=x0;
        y=y0;
        width = w;
        height = h;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addISwitchListener(ISwitchListener who) {
        switchListeners.add(who);
    }

    public void removeISwitchListener(ISwitchListener who) {
        switchListeners.remove(who);
    }

    public ISwitchListener[] getISwitchListeners() {
        ISwitchListener[] result = new ISwitchListener[switchListeners.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (ISwitchListener) (switchListeners.elementAt(i));
        }
        return result;
    }

    public String getDescription() {
        return "Switch";
    }

    public double getMaxSamplingRate() {
        return 0;
    }

    public SensorEvent createSensorEvent() {
        SensorEvent evt = new SwitchEvent(this);
        return evt;
    }

    public void saveEventState(SensorEvent evt) {
        ((SwitchEvent)evt).setPressed(isClosed());
    }

}
