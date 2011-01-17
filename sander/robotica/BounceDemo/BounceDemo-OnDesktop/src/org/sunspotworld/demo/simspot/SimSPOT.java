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

package org.sunspotworld.demo.simspot; 

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimSwitch;
import org.sunspotworld.demo.simspot.simsensorboard.led.SimTriColorLED;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimAccelerometer;
import org.sunspotworld.demo.simspot.simsensorboard.io.SimAccelerometer3D;
import org.sunspotworld.demo.simspot.simsensorboard.led.SimTriColorLEDArray;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

/**
 * A Simulated Sun SPOT. Note becase it can turn left or right, the switches are
 * handled explicitly here. One might transform the mouse coordinates, then pass them
 * on to swtiches if they were JComponents, but that turned out to be more complex than handling
 * it by hand here, and having the simulated switches be rather dumb objects.
 * @author randy
 */
public class SimSPOT extends JComponent implements
        MouseListener, MouseMotionListener {
    
    private int xLEDStart;
    private int yLEDStart;
    private Point sw1Org = new Point(81, 88);
    private Point sw2Org = new Point(128, 88);
    private SimSwitch[] switches;
    private double theta = 0.0;  //angle at which tilted
    double pX, pY;               // For storing exact location
    double dragOffsetX, dragOffsetY;
    boolean isDragging;
    Image img;
    public static String imageFileName = "images/SPOT9LftLight2.75inDKDK.gif";
    
    /**
     * Creates a new instance of SimSPOT
     */
    public SimSPOT() {
        init();
    }
    
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height){
        return false;//the required information has been acquired.
    }

    public void placeSimLEDs(){
        SimTriColorLEDArray leds = (SimTriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        for(int i = 0; i < leds.size(); i++){
            SimTriColorLED led = (SimTriColorLED) leds.getLED(i);
            int x = getXLEDStart() + i * 10;
            int y = getYLEDStart();
            led.setPosition(x , y);
            add(led);
        }
    }

    public void connectWithSimAccelerometer(){
        SimAccelerometer3D accel3D = (SimAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
        ((SimAccelerometer)(accel3D.getXAxis())).setMotionSource(this);
        ((SimAccelerometer)(accel3D.getYAxis())).setMotionSource(this);
        ((SimAccelerometer)(accel3D.getZAxis())).setMotionSource(this);
    }

    public void placeAndInitSwitches() {
        SimSwitch s1 = (SimSwitch) Resources.lookup(ISwitch.class, "SW1");
        SimSwitch s2 = (SimSwitch) Resources.lookup(ISwitch.class, "SW2");
        switches = new SimSwitch[2];
        switches[0] = s1;
        switches[1] = s2;
        s1.setBounds(getSw1Org().x, getSw1Org().y, s1.getWidth(), s1.getHeight());
        s2.setBounds(getSw2Org().x, getSw2Org().y, s2.getWidth(), s2.getHeight());
    }
    
    public void init(){
        //URL name = SimSPOT.class.getResource(SimSPOT.imageFileName);
        URL name = SimSPOT.class.getResource(SimSPOT.imageFileName);
        img = new ImageIcon(name).getImage();
        setXLEDStart(11 + ((int)(getPreferredSize().getWidth()) - img.getWidth(this)) / 2);
        setYLEDStart(48 + ((int)(getPreferredSize().getHeight()) - img.getHeight(this)) / 2); //(getHeight() - img.getHeight(this))/2;
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        placeSimLEDs();
        connectWithSimAccelerometer();
        placeAndInitSwitches();
    }

    synchronized public void setPosition(double x, double y, double newTheta){
        pX = x;
        pY = y;
        int w,h;
        if(getWidth()  == 0) w = (int)(getPreferredSize().getWidth()) ; else w = getWidth();
        if(getHeight() == 0) h = (int)(getPreferredSize().getHeight()); else h = getHeight();
        setBounds((int)(x + 0.5),(int)(y + 0.5), (int)w, (int)h);
        double dTheta = newTheta - getTheta();
        double sindt = Math.sin(dTheta);
        double cosdt = Math.cos(dTheta);
        double xC = w /2.0;
        double yC = h /2.0;
        double rx = (dragOffsetX - xC) * cosdt - (dragOffsetY - yC) * sindt;
        double ry = (dragOffsetX - xC) * sindt + (dragOffsetY - yC) * cosdt;
        dragOffsetX = rx + xC;
        dragOffsetY = ry + yC;
        setTheta(newTheta);
    }
    
    public Dimension getPreferredSize(){
        double hypot = Math.sqrt(img.getWidth(this)*img.getWidth(this) + img.getHeight(this)*img.getHeight(this) );
        return new Dimension((int)hypot + 1 , (int)hypot + 1);
    }
    
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(img,
                (getWidth() - img.getWidth(this)) / 2,
                (getHeight() - img.getHeight(this))/2,
                img.getWidth(this),
                img.getHeight(this),
                this);
        for (int i = 0; i < switches.length; i++) {
            g2.translate(switches[i].getX(), switches[i].getY());
            switches[i].paintComponent(g2);
            g2.translate(- switches[i].getX(), - switches[i].getY());
        }
    }
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform t = g2.getTransform();
        t.rotate(getTheta(), getWidth()/2.0, getHeight()/2.0);
        g2.setTransform(t);
        super.paint(g2);
        Component[] comps = getComponents();
        for (int i = 0; i < comps.length; i++) {
            Component c = comps[i];
            if(c instanceof SimTriColorLED){
                SimTriColorLED s = (SimTriColorLED) c;
                g.translate(s.getX(), s.getY());
                s.paintLensFlare(g);
                g.translate(-s.getX(), -s.getY());
            }
        }
    }
    
    public void paintComponentOLD(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        //g2.setColor(new Color( 200, 200, 200));
        //g2.fillRect(0,0,getWidth(), getHeight());
        g2.setColor(Color.blue);
        double rX = getWidth() *Math.cos(getTheta())/2;
        double rY = getHeight()*Math.sin(getTheta())/2;
        g2.drawLine((int)(getWidth()/2 - rX), (int)(getHeight()/2 -rY), (int)(getWidth()/2 + rX) , (int)(getHeight() / 2 + rY) );
        g2.fillRect((int)dragOffsetX - 1, (int)dragOffsetY -1, 2, 2);
    }
    
    public void mouseClicked(MouseEvent mouseEvent) {
    }
    
    public void mousePressed(MouseEvent mouseEvent) {
        if(dispatchMousePress(mouseEvent)) {
            repaint();
            return;
        }
        isDragging = true;
        dragOffsetX = (double)mouseEvent.getX();
        dragOffsetY = (double)mouseEvent.getY();
        repaint();
    }
    
    public void mouseReleased(MouseEvent mouseEvent) {
        if(dispatchMouseRelease(mouseEvent)){
            repaint();
            return;
        }
        isDragging = false;
    }
    
    public void mouseEntered(MouseEvent mouseEvent) {
    }
    
    public void mouseExited(MouseEvent mouseEvent) {
    }
    
    synchronized public void  mouseDragged(MouseEvent mouseEvent) {
        if(! isDragging) return;
        double dMx = mouseEvent.getX() - dragOffsetX;
        double dMy = mouseEvent.getY() - dragOffsetY;
        double xC = (getParent().getWidth()  - getWidth())  / 2.0;
        double yC = (getParent().getHeight() - getHeight())/ 2.0;
        double xL = getX() + dMx;
        double yL = getY() + dMy;
        double t = - (xL - xC)*(yL - yC) / 200.0 / 200.0 * 0.8;
        
        setPosition(xL, yL, t);
        repaint();
    }
    
    
    synchronized public void  mouseDraggedViscous(MouseEvent mouseEvent) {
        double viscosity = 0.002; //a number that govenrs the rate of spin of this object as it is dragged around the screen.
        //viscosity = 0.0;
        double dMx = mouseEvent.getX() - dragOffsetX;
        double dMy = mouseEvent.getY() - dragOffsetY;
        double xC = getWidth()  / 2.0;
        double yC = getHeight() / 2.0;
        double rX = dragOffsetX - xC;
        double rY = dragOffsetY - yC;
        double torque;
        if(dMx==0.0 && dMy==0.0)
            torque = 0.0;
        else
            torque = (rX * dMy - rY * dMx)/Math.sqrt(dMx*dMx + dMy*dMy);
        double dTheta = viscosity * torque; //This model only applies in the limit of high viscosity w.r.t free rotation kinematics.
        // System.out.println(dTheta);
        double cos = Math.cos(dTheta);
        double sin = Math.sin(dTheta);
        double dx = (1 - cos)* rX  -   sin    * rY + dMx;
        double dy =    sin   * rX  + (1 - cos)* rY + dMy;
        setPosition(pX + dx, pY + dy, getTheta() + dTheta);
        repaint();
    }
    
    public void mouseMoved(MouseEvent mouseEvent) {
    }
    
    public double getTheta() {
        return theta;
    }
    
    synchronized public void setTheta(double t) {
        double dt = t - theta;
        double sindt = Math.sin(dt);
        double cosdt = Math.cos(dt);
        double xC = getWidth() /2.0;
        double yC = getHeight()/2.0;
        double x = (dragOffsetX - xC) * cosdt - (dragOffsetY - yC) * sindt;
        double y = (dragOffsetX - xC) * sindt + (dragOffsetY - yC) * cosdt;
        dragOffsetX = x + xC;
        dragOffsetY = y + yC;
        theta = t;
    }
    
    public int getXLEDStart() {
        return xLEDStart;
    }
    
    public void setXLEDStart(int xLEDStart) {
        this.xLEDStart = xLEDStart;
    }
    
    public int getYLEDStart() {
        return yLEDStart;
    }
    
    public void setYLEDStart(int yLEDStart) {
        this.yLEDStart = yLEDStart;
    }
    
    /*
     * If transformed coordinates fall within component bounds, ask that component to
     * handle the event and return true. Else return false.
     */
    boolean dispatchMousePress(MouseEvent mouseEvent) {
        Point.Double p = unrotated(mouseEvent.getX(), mouseEvent.getY());
        for (int i = 0; i < switches.length; i++) {
            int orgX = switches[i].getX();
            int orgY = switches[i].getY();
            int cornerX = switches[i].getWidth() + orgX;
            int cornerY = switches[i].getHeight() + orgY;
            if((orgX <= p.x) && (orgY <= p.y) && (cornerX > p.x) && (cornerY > p.y)){
                switches[i].mousePressed(mouseEvent);
                return true;
            }
        }
        return false;
    }
    
    boolean dispatchMouseRelease(MouseEvent mouseEvent) {
         /*  Simply look for a closed switch.
          *  This catches the case of a relase when the mouse has "slid off" the button.
          */
        for (int i = 0; i < switches.length; i++) {
            if(switches[i].isClosed()){
                switches[i].mouseReleased(mouseEvent);
                return true;
            }
        }
        return false;
    }
    
    public Point.Double unrotated(int x, int y){
        double sinT = Math.sin(theta);
        double cosT = Math.cos(theta);
        double xC = getWidth() /2.0;
        double yC = getHeight()/2.0;
        double dX =   (x - xC) * cosT + (y - yC) * sinT;
        double dY = - (x - xC) * sinT + (y - yC) * cosT;
        return new Point.Double(xC + dX, yC + dY);
    }
    
    public Point getSw2Org() {
        return sw2Org;
    }
    
    public void setSw2Org(Point sw2Org) {
        this.sw2Org = sw2Org;
    }
    
    public Point getSw1Org() {
        return sw1Org;
    }

}

