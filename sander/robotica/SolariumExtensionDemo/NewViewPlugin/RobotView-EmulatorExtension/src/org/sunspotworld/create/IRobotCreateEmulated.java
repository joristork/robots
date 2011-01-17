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

package org.sunspotworld.create;

import com.sun.spot.emulator.Emulator;
import com.sun.spot.emulator.IEmulatorExtension;
import com.sun.spot.peripheral.SpotFatalException;
import com.sun.spot.util.Utils;
import java.util.Vector;

/**
 * Class that provides a high level interface to the iRobot series of
 * Roomba/Create robots.
 * 
 * Stripped down version for this demo. See the iRobotCreateDemo for the full API.
 *
 * The names of most of the sensors are derived mostly from the
 * "Create Open Interface_v2.pdf" document found on the http://www.irobot.com
 * site. Recommend reading this document in order to get a better understanding
 * of how to work with the robot.
 */
class IRobotCreateEmulated implements IRobotCreate, IEmulatorExtension {

    private boolean initialized = false;
    private Emulator emulator;
    private final Object angleLock = new Integer(0);
    private final Object distanceLock = new Integer(0);
    private final Object headingLock = new Integer(0);
    private final Object wallValueLock = new Integer(0);

    private int driveVelocity;
    private int driveRadius;
    private int driveVelocityLeft;
    private int driveVelocityRight;

    private int lastAngle;
    private int lastDistance;
    private int accumulatedAngle = 0;
    private int accumulatedDistance = 0;
    private boolean bumpLeft;
    private boolean bumpRight;
    private boolean wallDetected;
    private int wallValue;
    private Vector bumpListeners = new Vector();
    private Vector wallListeners = new Vector();
    private int infrared = INFRARED_BYTE_NONE;

    // some additions to the standard Create sensors
    private int heading;

    
    /**
     * Create a new instance to control the iRobot Create.
     */
    public IRobotCreateEmulated() {
        emulator = Emulator.getInstance();
        emulator.registerExtension("Create", this);
    }

    private void sendReply(String msg) {
        init();
        emulator.sendReply("Xtend Create " + msg);
    }

    private void init() {
        if (!initialized) {
            initialized = true;
            sendReply("Hello");
        }
    }

    /**
     * Register a new bump listener
     *
     * @param listener who to notify
     */
    public void addIBumpListener(IBumpListener listener) {
        if (!bumpListeners.contains(listener)) {
            bumpListeners.addElement(listener);
        }
    }

    /**
     * Remove a bump listener
     *
     * @param listener who to no longer notify
     */
    public void removeIBumpListener(IBumpListener listener) {
        bumpListeners.removeElement(listener);
    }

    /**
     * Returns an array of all the bump listeners registered on this Create.
     *
     * @return all of the bump listeners or an empty array if none are currently registered.
     */
    public IBumpListener[] getIBumpListeners() {
        IBumpListener[] list = new IBumpListener[bumpListeners.size()];
        for (int i = 0; i < bumpListeners.size(); i++) {
            list[i] = (IBumpListener)bumpListeners.elementAt(i);
        }
        return list;
    }

    /**
     * Register a new wall listener
     *
     * @param listener who to notify
     */
    public void addIWallListener(IWallListener listener) {
        if (!wallListeners.contains(listener)) {
            wallListeners.addElement(listener);
        }
    }

    /**
     * Remove a wall listener
     *
     * @param listener who to no longer notify
     */
    public void removeIWallListener(IWallListener listener) {
        wallListeners.removeElement(listener);
    }

    /**
     * Returns an array of all the wall listeners registered on this Create.
     *
     * @return all of the wall listeners or an empty array if none are currently registered.
     */
    public IWallListener[] getIWallListeners() {
        IWallListener[] list = new IWallListener[wallListeners.size()];
        for (int i = 0; i < wallListeners.size(); i++) {
            list[i] = (IWallListener)wallListeners.elementAt(i);
        }
        return list;
    }

    /**
     * Set how often the bump sensors are checked in order to notify any IBumpListener's.
     * <br>
     * Note: period is ignored by Emulator.
     *
     * @param period time in milliseconds between checks. Must be greater than 15 msec.
     */
    public void setIBumpListenerPeriod(long period) { }


    /**
     * Set how often the wall sensor is checked in order to notify any IWallListener's.
     * <br>
     * Note: period is ignored by Emulator.
     *
     * @param period time in milliseconds between checks. Must be greater than 15 msec.
     */
    public void setIWallListenerPeriod(long period) { }


    /* Status & Sensor readings from Solarium to virtual SPOT
     *
     * Driving velocity radius                      [as ints]
     * DrivingDirect velocityRight velocityLeft     [as ints]
     * Bump right left                              [as booleans]
     * Wall val                                     [as boolean]
     * Distance distance                            [as int]
     * Angle angle                                  [as int]
     * Heading heading                              [as int]
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
     * Respond to a message from Solarium. Not for use in user programs.
     *
     * @param token array of tokens in message
     */
    public void doCommand(String[] token) {
        init();
        int len = token.length;
        if (len < 2) return;

        if (token[2].equalsIgnoreCase("Driving") && len >= 4) {           // Driving velocity radius
            driveVelocity = Integer.parseInt(token[3]);
            driveRadius = Integer.parseInt(token[4]);
        } else if (token[2].equalsIgnoreCase("DrivingDirect") && len >= 4) { // DrivingDirect velocityRight velocityLeft
            driveVelocityRight = Integer.parseInt(token[3]);
            driveVelocityLeft = Integer.parseInt(token[4]);
        } else if (token[2].equalsIgnoreCase("Bump") && len >= 4) {       // Bump right left
            final boolean bumpRightOld = bumpRight;
            final boolean bumpLeftOld = bumpLeft;
            bumpRight = "true".equalsIgnoreCase(token[3]);
            bumpLeft  = "true".equalsIgnoreCase(token[4]);
            if (bumpRightOld != bumpRight || bumpLeftOld != bumpLeft) {
                // notify any bump listeners
                for (int i = 0; i < bumpListeners.size(); i++) {
                    final IBumpListener l = (IBumpListener) bumpListeners.elementAt(i);
                    new Thread("User bump event thread") {
                        public void run() {
                            l.bumpEvent(bumpRight, bumpLeft, bumpRightOld, bumpLeftOld);
                        }
                    }.start();
                }
            }
        } else if (token[2].equalsIgnoreCase("Wall") && len >= 3) {       // Wall val
            boolean wallOld = wallDetected;
            wallDetected  = "true".equalsIgnoreCase(token[3]);
            if (wallOld != wallDetected) {
                // notify any wall listeners
                for (int i = 0; i < wallListeners.size(); i++) {
                    final IWallListener l = (IWallListener) wallListeners.elementAt(i);
                    new Thread("User wall event thread") {
                        public void run() {
                            l.wallEvent(wallDetected);
                        }
                    }.start();
                }
            }
        } else if (token[2].equalsIgnoreCase("Distance") && len >= 3) {   // Distance distance
            lastDistance = Integer.parseInt(token[3]);
            accumulatedDistance += lastDistance;
            synchronized(distanceLock) {
                distanceLock.notifyAll();
            }
        } else if (token[2].equalsIgnoreCase("Angle") && len >= 3) {      // Angle angle
            lastAngle = Integer.parseInt(token[3]);
            accumulatedAngle += lastAngle;
            synchronized(angleLock) {
                angleLock.notifyAll();
            }
            Thread.yield();
        } else if (token[2].equalsIgnoreCase("Heading") && len >= 3) {    // Heading heading
            heading = Integer.parseInt(token[3]);
            synchronized(headingLock) {
                headingLock.notifyAll();
            }
        } else if (token[2].equalsIgnoreCase("WallValue") && len >= 3) {  // WallValue val
            wallValue = Integer.parseInt(token[3]);
            synchronized(wallValueLock) {
                wallValueLock.notifyAll();
            }
        } else if (token[2].equalsIgnoreCase("Infrared") && len >= 3) {  // Infrared val
            infrared = Integer.parseInt(token[3]);
        }
    }


    /******************************************************************
     *
     * Motion related commands
     *
     ******************************************************************/
        
    /**
     * This command controls Create’s drive wheels. It takes four data bytes,
     * interpreted as two 16-bit signed values using two’s complement. The first
     * two bytes specify the average velocity of the drive wheels in millimeters
     * per second (mm/s), with the high byte being sent first. The next two
     * bytes specify the radius in millimeters at which Create will turn. The
     * longer radii make Create drive straighter, while the shorter radii make
     * Create turn more. The radius is measured from the center of the turning
     * circle to the center of Create. A Drive command with a positive velocity
     * and a positive radius makes Create drive forward while turning toward the
     * left. A negative radius makes Create turn toward the right. Special cases
     * for the radius make Create turn in place or drive straight, as specified
     * below. A negative velocity makes Create drive backward. <br>
     * NOTE: Internal and environmental restrictions may prevent Create from
     * accurately carrying out some drive commands. For example, it may not be
     * possible for Create to drive at full speed in an arc with a large radius
     * of curvature. <cr> • Available in modes: Safe or Full <cr> • Changes mode
     * to: No Change <cr> • Drive data byte 2: Radius () Special cases: Straight
     * = 32768 or 32767 = hex 8000 or 7FFF Turn in place clockwise = hex FFFF
     * Turn in place counter-clockwise = hex 0001
     * 
     * @param velocity
     *            -500 – 500 mm/s
     * @param radius
     *            -2000 – 2000 mm, special cases
     *            DRIVE_STRAIGHT,
     *            DRIVE_TURN_IN_PLACE_CLOCKWISE,
     *            DRIVE_TURN_IN_PLACE_COUNTER_CLOCKWISE
     */
    public void drive(int velocity, int radius) {
        sendReply("Drive " + velocity + " " + radius);
        driveVelocity = velocity;
        driveRadius = radius;
    }
    
    /**
     * This command lets you control the forward and backward motion of Create’s
     * drive wheels independently. It takes four data bytes, which are
     * interpreted as two 16-bit signed values using two’s complement. The first
     * two bytes specify the velocity of the right wheel in millimeters per
     * second (mm/s), with the high byte sent first. The next two bytes specify
     * the velocity of the left wheel, in the same format. A positive velocity
     * makes that wheel drive forward, while a negative velocity makes it drive
     * backward. <br>
     * • Available in modes: Safe or Full <br>
     * • Changes mode to: No Change
     * 
     * @param rightVelocity
     *            Right wheel velocity (-500 – 500 mm/s)
     * @param leftVelocity
     *            Left wheel velocity (-500 – 500 mm/s)
     */
    public void driveDirect(int rightVelocity, int leftVelocity) {
        sendReply("DriveDirect " + rightVelocity + " " + leftVelocity);
        driveVelocityRight = rightVelocity;
        driveVelocityLeft  = leftVelocity;
    }
    
    /**
     * Send a driveDirect command with 0, 0 for each wheel, stopping the Create.
     */
    public void stop() {
        driveDirect(0, 0);
        driveVelocityRight = 0;
        driveVelocityLeft  = 0;
        driveVelocity = 0;
    }

    /**
     * Return the current angle the Create has rotated since this instance was
     * created. This accumulation is done every time the angle sensor value is
     * read from the Create. This value is not totally accurate but can be a
     * general indication of what angle the Create is at.
     * 
     * @return
     */
    public int getAccumulatedAngle() {
        getAngle();
        return accumulatedAngle;
    }

    /**
     * Return the total distance the Create has travelled since this instance
     * was created. Not totally accurate, and is accumulated every time the
     * distance sensor value is read from the Create.
     * 
     * @return
     */
    public int getAccumulatedDistance() {
        getDistance();
        return accumulatedDistance;
    }

    /**
     * The angle in degrees that iRobot Create has turned since the angle was
     * last requested. Counter-clockwise angles are positive and clockwise
     * angles are negative. If the value is not polled frequently enough, it is
     * capped at its minimum or maximum. <br>
     * Range: -32768 – 32767 <br>
     * NOTE: Create uses wheel encoders to measure distance and angle. If the
     * wheels slip, the actual distance or angle traveled may differ from
     * Create’s measurements.
     * 
     * @return
     */
    public int getAngle() {
        synchronized(angleLock) {
            sendReply("GetAngle");
            try {
                angleLock.wait();
            } catch (InterruptedException ex) {
                // just continue
            }
        }
        return lastAngle;
    }

    /**
     * The distance that Create has traveled in millimeters since the distance
     * it was last requested is sent as a signed 16-bit value, high byte first.
     * This is the same as the sum of the distance traveled by both wheels
     * divided by two. Positive values indicate travel in the forward direction;
     * negative values indicate travel in the reverse direction. If the value is
     * not polled frequently enough, it is capped at its minimum or maximum. <br>
     * Range: -32768 – 32767
     * 
     * @return
     */
    public int getDistance() {
        synchronized(distanceLock) {
            sendReply("GetDistance");
            try {
                distanceLock.wait();
            } catch (InterruptedException ex) {
                // just continue
            }
        }
        return lastDistance;
    }
    
    /**
     * The velocity most recently requested with a Drive command is returned. <br>
     * Range: -500 - 500 mm/s
     *
     * @return
     */
    public int getRequestedVelocity() {
        return driveVelocity;
    }

    /**
     * The radius most recently requested with a Drive command is returned. <br>
     * Range: -32768 - 32767 mm
     * 
     * @return
     */
    public int getRequestedRadius() {
        return driveRadius;
    }

    /**
     * The velocity most recently requested with a Drive command is returned. <br>
     * Range: -500 - 500 mm/s
     *
     * @return
     */
    public int getRequestedLeftVelocity() {
        return driveVelocityLeft;
    }

    /**
     * The right wheel velocity most recently requested with a Drive Direct
     * command is returned. <br>
     * Range: -500 - 500 mm/s
     * 
     * @return
     */
    public int getRequestedRightVelocity() {
        return driveVelocityRight;
    }

    /**
     * The current heading of the Create in degrees.
     * This is an addition to the normal Create sensors and is what might be returned
     * from a gyroscope or compass sensor.<br>
     * Range: 0 - 359 <br>
     * NOTE: This method is not available on a real Create.
     *
     * @return
     */
    public int getHeading() {
        synchronized(headingLock) {
            sendReply("GetHeading");
            try {
                headingLock.wait();
            } catch (InterruptedException ex) {
                // just continue
            }
        }
        return heading;
    }


    /**
     * The state of the wall sensor, true if there is a wall.
     *
     * @return
     */
    public boolean isWall() {
        return wallDetected;
    }

    /**
     * The strength of the wall sensor’s signal is returned. <br>
     * Range: 0-4095
     * 
     * @return
     */
    public int getWallSignal() {
        synchronized(wallValueLock) {
            sendReply("GetWallValue");
            try {
                wallValueLock.wait();
            } catch (InterruptedException ex) {
                // just continue
            }
        }
        return wallValue;
    }


    /**
     * Return true if the left bumper is pressed.
     * 
     * @return
     */
    public boolean isBumpLeft() {
        return bumpLeft;
    }

    /**
     * Return true if the right bumper is pressed.
     * 
     * @return
     */
    public boolean isBumpRight() {
        return bumpRight;
    }

    /**
     * Cause the Java execution to pause until the Create has travelled to a
     * delta of degrees.  This command assumes that the robot is already
     * moving/turning already.  If not, then this command will never return,
     * as this method will not return until the robot has rotated degrees.
     * The state of the create will not be affected, this means that if you expect
     * the robot to stop after waiting degrees of turn, then you will need to send
     * the create the stop command.
     *
     * <code>
     * create.driveDirect(100,-100);
     * waitAngle(90);
     * create.stop()
     * </code>
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param degrees
     */
    public void waitAngle(int degrees) {
        int deg = Math.abs(degrees);
        int startingAngle = getAccumulatedAngle();
        while (Math.abs(getAccumulatedAngle() - startingAngle) < deg) {
            Utils.sleep(50);
        }
    }

    /**
     * Cause the Java execution to pause until the Create has travelled to a
     * delta of degrees or the specified timeout has expired.
     * The state of the create will not be affected, this means that if you expect
     * the robot to stop after waiting degrees of turn, then you will need to send
     * the create the stop command.
     *
     * <code>
     * create.driveDirect(100,-100);
     * waitAngle(90);
     * create.stop()
     * </code>
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param degrees
     * @param timeout length of time to wait in milliseconds
     */
    public void waitAngle(int degrees, long timeout) throws InterruptedException {
        int deg = Math.abs(degrees);
        long startTime = System.currentTimeMillis();
        int startingAngle = getAccumulatedAngle();
        while (Math.abs(getAccumulatedAngle() - startingAngle) < deg) {
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                throw new InterruptedException("Timeout waiting for angle to travel " + deg +
                        " degrees - currently " + Math.abs(accumulatedAngle - startingAngle) + " degrees");
            }
            Utils.sleep(50);
        }
    }

    /**
     * Pause Java execution until the Create has travelled mms.
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param mms distance in millimeters
     */
    public void waitDistance(int mms) {
        int startingDistance = getAccumulatedDistance();
        while (Math.abs(getAccumulatedDistance() - startingDistance) < mms) {
            Utils.sleep(50);
        }
    }

    /**
     * Pause Java execution until the Create has travelled mms or the specified timeout has expired.
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param mms distance in millimeters
     * @param timeout length of time to wait in milliseconds
     */
    public void waitDistance(int mms, long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int startingDistance = getAccumulatedDistance();
        while (Math.abs(getAccumulatedDistance() - startingDistance) < mms) {
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                throw new InterruptedException("Timeout waiting for distance to travel " + mms +
                        " mm - currently " + (accumulatedDistance - startingDistance) + " mm");
            }
            Utils.sleep(50);
        }
    }

    /**
     * Pause Java execution until one of the Create bump sensors is true.
     */
    public void waitBump() {
        while (!bumpLeft && !bumpRight) {
            Utils.sleep(50);
        }
    }

    /**
     * Pause Java execution until one of the Create bump sensors is true
     * or the specified timeout has expired.
     *
     * @param timeout length of time to wait in milliseconds
     */
    public void waitBump(long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (!bumpLeft && !bumpRight) {
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                throw new InterruptedException("Timeout waiting for bump sensor.");
            }
            Utils.sleep(50);
        }
    }

    /**
     * Pause Java execution until neither of the Create bump sensors is true.
     */
    public void waitNoBump() {
        while (bumpLeft || bumpRight) {
            Utils.sleep(50);
        }
    }

    /**
     * Pause Java execution until neither of the Create bump sensors is true
     * or the specified timeout has expired.
     *
     * @param timeout length of time to wait in milliseconds
     */
    public void waitNoBump(long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (bumpLeft || bumpRight) {
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                throw new InterruptedException("Timeout waiting for bump sensor.");
            }
            Utils.sleep(50);
        }
    }


    /**
     * This value identifies the IR byte currently being received by iRobot
     * Create. A value of 255 indicates that no IR byte is being received. These
     * bytes include those sent by the Roomba Remote, the Home Base, Create
     * robots using the Send IR command, and user-created devices. <br>
     * Range: 0 – 255
     *
     * @return One of IRobotCreateConstants.INFRARED_BYTE_*
     */
    public int getInfraredByte() {
        return infrared;
    }

}