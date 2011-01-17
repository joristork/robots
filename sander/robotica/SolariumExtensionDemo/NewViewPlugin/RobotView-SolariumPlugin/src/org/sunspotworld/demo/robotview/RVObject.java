/*
 * Copyright 2009 Sun Microsystems, Inc.
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
 */

package org.sunspotworld.demo.robotview;

import org.sunspotworld.demo.robotview.RobotWorld.Beacon;

import com.sun.spot.solarium.gui.views.IUIObject;
import com.sun.spot.solarium.spotworld.participants.EmulatedSunSPOT;
import com.sun.spot.solarium.spotworld.participants.IEmulatorExtension;
import com.sun.spot.solarium.spotworld.virtualobjects.IVirtualObject;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 * Node for the Robot View.
 *
 * Handles updating the model of the iRobot Create as it moves about
 * the RobotWorld.
 *
 * @author Ron Goldman
 */
public class RVObject implements IUIObject, IEmulatorExtension {

    public static final double MM_TO_SCREEN = 0.47272727273;
    private static final int offsetX = (int)(2 / MM_TO_SCREEN);
    private static final int offsetY = (int)(2 / MM_TO_SCREEN);
    private static double width;
    private static BufferedImage robotImage = null;

    private RobotView robotView;
    private EmulatedSunSPOT spot; // the virtual SPOT controlling this Create
    private String address;

    // position & heading
    private double x = 0.0;
    private double y = 0.0;
    private double heading = 0.0;

    // motion values
    private int velocity = 0;
    private int radius = 0;
    private int velocityLeft  = 0;
    private int velocityRight = 0;
    private double deltaV = 0.0;
    private double deltaHeading = 0.0;
    private double distanceTraveled = 0.0;
    private double angleTraveled = 0.0;

    // geometric shape of robot
    private Ellipse2D shape;
    private Arc2D leftBumper, rightBumper;

    // sensors
    private boolean bumpLeft = false;
    private boolean bumpRight = false;
    private boolean wallDetected = false;
    private int wallValue = 0;
    private static final int WALL_THRESHOLD_HIGH = 96;
    private static final int WALL_THRESHOLD_LOW  = 92;

    private static final int BEACON_X  = 100;
    private static final int BEACON_O  = 101;
    private static final int NO_BEACON = 255;
    private int beacon = NO_BEACON;
    
    private boolean selected = false;


    public RVObject(EmulatedSunSPOT obj, String address) {
        super();
        if (robotImage == null) {
            readImages();
        }
        spot = obj;
        spot.registerExtension("Create", this);
        this.address = address;
        shape = new Ellipse2D.Double(offsetX, offsetY, width, width);
        leftBumper  = new Arc2D.Double(offsetX, offsetY, width, width, 175.0, -130.0, Arc2D.CHORD);
        rightBumper = new Arc2D.Double(offsetX, offsetY, width, width, 135.0, -130.0, Arc2D.CHORD);
    }

    private void readImages() {
        try {
            URL url = getClass().getResource("/com/sun/spot/solarium/views/robotview/images/Create-SPOT.png");
            robotImage = ImageIO.read(url);
            width = robotImage.getWidth(null) / MM_TO_SCREEN - 2 * offsetX;
        } catch (Exception ex) {
            System.out.println("Failed to load Robot View images! " + ex);
            ex.printStackTrace();
            robotImage = null;
        }
    }

    /**
     * All the feedback sent to the virtual SPOT in the Emulator goes
     * through this method.
     *
     * All of the commands from the virtual SPOT arrive in the handleSpotMessage()
     * method defined below.
     *
     * @param msg information to update emulator's model
     */
    private void sendCmd(String msg) {
        spot.sendCmd("Xtend Create " + msg);
    }

    public synchronized void driveCommand(int velocity, int radius) {
        this.radius = radius;
        this.velocity = velocity;
        int r = radius & 0xFFFF;
        if (r == 0x8000 || r == 0x7fff) {   // move in a straight line
            velocityLeft = velocity;
            velocityRight = velocity;
            deltaV = velocity / 20.0;       // distance traveled in 1/20 second
            deltaHeading = 0.0;
        } else if (r == 0xffff) {           // rotate in place clockwise
            velocityLeft = velocity;
            velocityRight = -velocity;
            deltaHeading = 360.0 * (velocity / 20.0) / (260.0 * Math.PI);
            deltaV = 0.0;
        } else if (r == 0x0001) {           // rotate in place counter-clockwise
            velocityLeft = -velocity;
            velocityRight = velocity;
            deltaHeading = -360.0 * (velocity / 20.0) / (260.0 * Math.PI);
            deltaV = 0.0;
        } else {
            deltaV = velocity / 20.0;       // distance traveled in 1/20 second
            double theta = (velocity / 20.0) / (2.0 * radius * Math.PI);  // % arc traveled
            deltaHeading = -360.0 * theta;
            velocityLeft  = (int)((2.0 * (radius - 130.0) * Math.PI) * theta);
            velocityRight = (int)((2.0 * (radius + 130.0) * Math.PI) * theta);
        }
        sendCmd("DrivingDirect " + velocityRight + " " + velocityLeft);

    }

    public synchronized void driveDirectCommand(int rightWheelVelocity, int leftWheelVelocity) {
        velocityRight = rightWheelVelocity;
        velocityLeft  = leftWheelVelocity;
        double vel = (velocityRight + velocityLeft) / 2.0;
        velocity = (int) vel;
        deltaV = vel / 20.0;                 // distance traveled in 1/20 second
        if (velocityRight == velocityLeft) {
            deltaHeading = 0.0;
            radius = 0x8000;
        } else if (velocity == 0.0) {        // i.e. velocityRight == -velocityLeft
            deltaHeading = 360.0 * (velocityLeft / 20.0) / (260.0 * Math.PI);
            if (velocityLeft >= 0) {
                radius = 0x0001;             // rotate in place counter-clockwise
            } else {
                radius = 0xffff;             // rotate in place clockwise
            }
        } else {
            if (velocityLeft == 0.0) {
                radius = 130;
            } else {
                radius = -(int)(260.0 / (1.0 - (((double)velocityRight) / velocityLeft)) - 130.0);
            }
            deltaHeading = -360.0 * deltaV / (2.0 * radius * Math.PI);
        }
        sendCmd("Driving " + radius + " " + velocity);
    }

    public void resetRobot() {
        driveDirectCommand(0,0);
    }

    public synchronized boolean action(RobotWorld world, Vector<RVObject> robots) {
        boolean moved = false;
        if (deltaV != 0.0) {
            int backoff = 9;
            double oldX = x;
            double oldY = y;
            double chordHeading = Math.toRadians(heading + deltaHeading);
            double dx =  deltaV * Math.sin(chordHeading);
            double dy = -deltaV * Math.cos(chordHeading);
            moveBy(dx, dy);
            boolean newLeft  = false;
            boolean newRight = false;
            for (Wall w : world.getWalls()) {
                Rectangle2D wall = w.getShape();
                if (shape.intersects(wall)) {
                    newRight = rightBumper.intersects(wall);
                    newLeft  = leftBumper.intersects(wall);
                    for (; backoff > 0; backoff--) {
                        moveBy(-dx / 10.0, -dy / 10.0);
                        if (!shape.intersects(w.getShape())) {
                            break;
                        }
                    }
                    if (backoff <= 0) {
                        setPosition(oldX, oldY);
                        setBumpers(newRight, newLeft);
                        return false;               // no change in position
                    }
                }
            }
            for (RVObject r : robots) {
                if (r != this && Point2D.distance(x, y, r.getX(), r.getY()) <= width) {
                    double theta = -Math.toDegrees(Math.atan2(r.getY() - y, r.getX() - x));
                    newRight = rightBumper.containsAngle(theta);
                    newLeft  = leftBumper.containsAngle(theta);
// for now don't cause other robot to register a bump
// as there's no good way to reset the bumper if the other robot is not moving
//                    boolean otherRight = r.rightBumper.containsAngle(theta + 180) || r.bumpRight;
//                    boolean otherLeft  = r.leftBumper.containsAngle(theta + 180)  || r.bumpLeft;
//                    r.setBumpers(otherRight, otherLeft);
                    for (; backoff > 0; backoff--) {
                        moveBy(-dx / 10.0, -dy / 10.0);
                        if (Point2D.distance(x, y, r.getX(), r.getY()) > width) {
                            break;
                        }
                    }
                    if (backoff <= 0) {
                        setPosition(oldX, oldY);
                        setBumpers(newRight, newLeft);
                        return false;               // no change in position
                    }
                }
            }
            moved = true;
            distanceTraveled += deltaV * (backoff + 1.0) / 10.0;
            angleTraveled += deltaHeading;
            setHeading(heading + deltaHeading);
            setBumpers(newRight, newLeft);
        } else {
            moved = deltaHeading != 0.0;
            angleTraveled += deltaHeading;
            setHeading(heading + deltaHeading);
            if (bumpLeft || bumpRight) {
                boolean newRight = false;
                boolean newLeft = false;
                double angle = Math.toRadians(heading);
                double dx =  10 * Math.sin(angle);
                double dy = -10 * Math.cos(angle);
                moveBy(dx, dy);    // check bumpers by moving forward a bit
                for (Wall w : world.getWalls()) {
                    Rectangle2D wall = w.getShape();
                    newRight |= rightBumper.intersects(wall);
                    newLeft  |= leftBumper.intersects(wall);
                }
                for (RVObject r : robots) {
                    if (r != this && Point2D.distance(x, y, r.getX(), r.getY()) <= width) {
                        double theta = -Math.toDegrees(Math.atan2(r.getY() - y, r.getX() - x));
                        newRight |= rightBumper.containsAngle(theta);
                        newLeft  |= leftBumper.containsAngle(theta);
                    }
                }
                moveBy(-dx, -dy);    // restore position
                setBumpers(newRight, newLeft);
            }
        }
        computeWallValue(world, robots);
        beaconCheck(world);
        return moved;
    }

    private void beaconCheck(RobotWorld world) {
        int val = NO_BEACON;
        for (Beacon b : world.getBeacons()) {
            if (Point2D.distance(x, y, b.x, b.y) <= (3 * width / 4)) {
                val = (b.chr == 'X') ? BEACON_X : BEACON_O;
            }
        }
        setBeaconValue(val);
    }

    public Line2D getSensorLine(){
        double angle = Math.toRadians(heading + 60.0);
        double sensorX = (width / 2) * (1 + Math.sin(angle));
        double sensorY = (width / 2) * (1 - Math.cos(angle));
        angle = Math.toRadians(heading + 90.0);
        double dX =  Math.sin(angle);
        double dY = -Math.cos(angle);
        double endX = sensorX + 100 * dX;
        double endY = sensorY + 100 * dY;
        return new Line2D.Double(MM_TO_SCREEN * sensorX, MM_TO_SCREEN * sensorY, MM_TO_SCREEN * endX, MM_TO_SCREEN *endY);
    }

    private void computeWallValue(RobotWorld world, Vector<RVObject> robots) {
        double angle = Math.toRadians(heading + 60.0);
        double sensorX = x + width * Math.sin(angle) / 2;
        double sensorY = y - width * Math.cos(angle) / 2;
        angle = Math.toRadians(heading + 90.0);
        double dX =  Math.sin(angle);
        double dY = -Math.cos(angle);
        double endX = sensorX + 100 * dX;
        double endY = sensorY + 100 * dY;
        int wallDistance = 101;
        for (Wall w : world.getWalls()) {
            Rectangle2D wall = w.getShape();
            if (wall.intersectsLine(sensorX, sensorY, endX, endY)) {
                double sx = sensorX;
                double sy = sensorY;
                // just use brute force to find distance to wall
                for (int i = 1; i <= 100; i++) {
                    sx += dX;
                    sy += dY;
                    if (wall.intersectsLine(sensorX, sensorY, sx, sy)) {
                        if (wallDistance > i) {
                            wallDistance = i;
                        }
                        break;
                    }
                }
            }
        }
        for (RVObject r : robots) {
            if (r != this && Point2D.distance(x, y, r.getX(), r.getY()) <= (width + 50)) {
                double sx = sensorX;
                double sy = sensorY;
                // just use brute force to find distance to other robot
                for (int i = 1; i <= 100; i++) {
                    sx += dX;
                    sy += dY;
                    if (r.getShape().contains(sx, sy)) {
                        if (wallDistance > i) {
                            wallDistance = i;
                        }
                        break;
                    }
                }
            }
        }
        if (wallDistance < 101) {
            int newWallValue = 0;
            // ignore angle between surface and wall beam
            if (wallDistance >= 35) {
                // close enough fit for now - from manually fitting a curve to some actual values
                newWallValue = (int)((wallDistance - 107.0) * (wallDistance - 107.0) / 14.0);
            } else {
                newWallValue = (int)(wallDistance * wallDistance / 3.4);
            }
            setWallValue(newWallValue);
        } else {
            setWallValue(0);
        }
    }

    private void setWallValue(int val) {
        if (wallValue != val) {
            wallValue = val;
            if ((wallDetected && wallValue < WALL_THRESHOLD_LOW) ||
                (!wallDetected && wallValue > WALL_THRESHOLD_HIGH)) {
                wallDetected = !wallDetected;
                sendCmd("Wall " + wallDetected);  // notify virtual SPOT
            }
        }
    }

    private void setBeaconValue(int val) {
        if (beacon != val) {
            beacon = val;
            sendCmd("Infrared " + beacon);  // notify virtual SPOT
        }
    }

    private void setBumpers(boolean right, boolean left) {
        boolean changed = (bumpLeft != left) || (bumpRight != right);
        bumpLeft  = left;
        bumpRight = right;
        if (changed) {
            sendCmd("Bump " + right + " " + left);  // notify virtual SPOT
        }
    }

    public String toString() {
        return "[Robot] " + ": " + address;
    }

    public String getAddress() {
        return address;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        double posX = x - width / 2 + offsetX;
        double posY = y - width / 2 + offsetY;
        shape.setFrame(posX, posY, width, width);
        leftBumper.setFrame(posX, posY, width, width);
        rightBumper.setFrame(posX, posY, width, width);
    }

    public void moveBy(double dx, double dy) {
        setPosition(x + dx, y + dy);
    }

    public Ellipse2D getShape() {
        return shape;
    }

    public Arc2D getRightBumper() {
        return rightBumper;
    }

    public Arc2D getLeftBumper() {
        return leftBumper;
    }

    public boolean ptInRobot(int x, int y) {
        return shape.contains(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double newH) {
        heading = newH;
        while (heading >= 360.0) {
            heading -= 360.0;
        }
        while (heading < 0.0) {
            heading += 360.0;
        }
        leftBumper.setAngleStart(175.0 - heading);
        rightBumper.setAngleStart(135.0 - heading);
    }

    public void turnBy(double dH) {
        setHeading(heading + dH);
    }

    public Image getImage() {
        return robotImage;
    }
    
    /* Status & Sensor readings from Solarium to virtual SPOT
     *
     * Bump right left                              [as booleans]
     * Distance distance                            [as int]
     * Angle angle                                  [as int]
     * Driving velocity radius                      [as ints]
     * DrivingDirect velocityRight velocityLeft     [as ints]
     * Heading heading                              [as int]
     * Wall val                                     [as boolean]
     * WallValue val                                [as int]
     * Infrared val                                 [as int]
     *
     * Commands from virtual SPOT to Solarium
     *
     * Hello
     * Drive velocity radius                        [as ints]
     * DriveDirect velocityRight velocityLeft       [as ints]
     * GetDistance
     * GetAngle
     * GetHeading
     * GetWallValue
     *
     */

    /**
     * Respond to a message from the virtual SPOT (IEmulatorExtension)
     *
     * @param token array of tokens in message
     */
    public void handleSpotMessage(String[] token) {
        int len = token.length;
        if (len < 2) return;

        if (token[2].equalsIgnoreCase("Drive") && len >= 4) {              // Drive velocity radius
            int v = Integer.parseInt(token[3]);
            int r = Integer.parseInt(token[4]);
            driveCommand(v, r);
        } else if (token[2].equalsIgnoreCase("DriveDirect") && len >= 4) { // DriveDirect velocityRight velocityLeft
            int vr = Integer.parseInt(token[3]);
            int vl = Integer.parseInt(token[4]);
            driveDirectCommand(vr, vl);
        } else if (token[2].equalsIgnoreCase("GetDistance")) {             // GetDistance
            int d = (int)distanceTraveled;
            sendCmd("Distance " + d);
            distanceTraveled -= d;
        } else if (token[2].equalsIgnoreCase("GetAngle")) {                // GetAngle
            int a = (int)angleTraveled;
            sendCmd("Angle " + a);
            angleTraveled -= a;
        } else if (token[2].equalsIgnoreCase("GetHeading")) {              // GetHeading
            sendCmd("Heading " + (int)heading);
        } else if (token[2].equalsIgnoreCase("GetWallValue")) {            // GetWallValue
            sendCmd("WallValue " + wallValue);
        } else if (token[2].equalsIgnoreCase("Hello")) {                   // Hello
            // send current state to virtual SPOT
            sendCmd("Wall " + wallDetected);
            sendCmd("Bump " + bumpRight + " " + bumpLeft);
            sendCmd("Infrared " + beacon);
        }
    }


    /***************************************************
     * 
     * Solarium-related methods: IUIObject, etc.
     *
     ***************************************************/

    public IVirtualObject getVirtualObject() {
        return spot;
    }

    public EmulatedSunSPOT getEmulatedSunSPOT() {
        return spot;
    }

    public RobotView getView(){
        return getRobotView();
    }

    public void setRobotView(RobotView view) {
        robotView = view;
    }

    public RobotView getRobotView() {
        return robotView;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public void delete() {
        robotView.removeNode(this);
    }

    public void add(IUIObject obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(IUIObject obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setVirtualObject(IVirtualObject obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void tellUser(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getStringFromUser(String prompt, String defaultValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getUserConfirmation(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void noteState(Object obj) {
        // ignore
    }

}
