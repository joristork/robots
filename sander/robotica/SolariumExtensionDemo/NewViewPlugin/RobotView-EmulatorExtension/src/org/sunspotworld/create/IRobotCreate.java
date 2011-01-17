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

/**
 * Interface that provides a high level interface to the iRobot series of
 * Roomba/Create robots.
 *
 * Stripped down version for this demo. See the iRobotCreateDemo for the full API.
 * 
 * The names of most of the sensors are derived mostly from the
 * "Create Open Interface_v2.pdf" document found on the http://www.irobot.com
 * site. Recommend reading this document in order to get a better understanding
 * of how to work with the robot.
 */
public interface IRobotCreate {

    public static final int DRIVE_STRAIGHT = 0x8000;
    public static final int DRIVE_TURN_IN_PLACE_CLOCKWISE = 0xFFFF;
    public static final int DRIVE_TURN_IN_PLACE_COUNTER_CLOCKWISE = 0x0001;

    public static final int DRIVE_MAX_VELOCITY = 500;
    public static final int DRIVE_MAX_RADIUS = 2000;

    public static final int INFRARED_BYTE_BEACON_X = 100;
    public static final int INFRARED_BYTE_BEACON_O = 101;
    public static final int INFRARED_BYTE_NONE = 255;

    /******************************************************************
     *
     * Motion related commands
     *
     ******************************************************************/
        
    /**
     * This command controls Create's drive wheels. It takes two arguments. The first
     * specifies the average velocity of the drive wheels in millimeters
     * per second (mm/s). The next specifies the radius in millimeters at which
     * the Create will turn. The longer radii make the Create drive straighter,
     * while the shorter radii make the Create turn more. The radius is measured
     * from the center of the turning circle to the center of the Create.
     * A Drive command with a positive velocity
     * and a positive radius makes the Create drive forward while turning toward the
     * left. A negative radius makes the Create turn toward the right. Special cases
     * for the radius make the Create turn in place or drive straight, as specified
     * below. A negative velocity makes the Create drive backward. <p>
     * NOTE: Internal and environmental restrictions may prevent the Create from
     * accurately carrying out some drive commands. For example, it may not be
     * possible for the Create to drive at full speed in an arc with a large radius
     * of curvature.
     * <ul>
     * <li> Available in modes: Safe or Full
     * <li> Changes mode to: No Change
     * </ul>
     * <p>
     * Special values for Radius:
     * <ul>
     * <li> Straight = 32768 or 32767 = hex 8000 or 7FFF or DRIVE_STRAIGHT
     * <li> Turn in place clockwise = hex FFFF or DRIVE_TURN_IN_PLACE_CLOCKWISE
     * <li> Turn in place counter-clockwise = hex 0001 or DRIVE_TURN_IN_PLACE_COUNTER_CLOCKWISE
     * </ul>
     * 
     * @param velocity  -500 - 500 mm/s
     * @param radius   -2000 - 2000 mm, or special cases
     */
    void drive(int velocity, int radius);
    
    /**
     * This command lets you control the forward and backward motion of the Create's
     * drive wheels independently. It takes two arguments. The first
     * specifies the velocity of the right wheel in millimeters per
     * second (mm/s). The next specifies the velocity of the left wheel.
     * A positive velocity makes that wheel drive forward, while a negative
     * velocity makes it drive backward.
     * 
     * @param rightVelocity  Right wheel velocity (-500 - 500 mm/s)
     * @param leftVelocity   Left wheel velocity (-500 - 500 mm/s)
     */
    void driveDirect(int rightVelocity, int leftVelocity);
    
    /**
     * Send a driveDirect command with 0, 0 for each wheel, stopping the Create.
     */
    void stop();

    /**
     * Return the total angle the Create has rotated since this instance was
     * created. This accumulation is done every time the angle sensor value is
     * read from the Create. This value is not totally accurate but can be a
     * general indication of what angle the Create is at.
     * 
     * @return the total angle the Create has rotated in degrees
     */
    int getAccumulatedAngle();

    /**
     * Return the total distance the Create has travelled since this instance
     * was created. Not totally accurate, and is accumulated every time the
     * distance sensor value is read from the Create.
     * 
     * @return the total distance the Create has rotated in millimeters
     */
    int getAccumulatedDistance();

    /**
     * The angle in degrees that iRobot Create has turned since the angle was
     * last requested. Counter-clockwise angles are positive and clockwise
     * angles are negative. If the value is not polled frequently enough, it is
     * capped at its minimum or maximum. <p>
     * Range: -32768 - 32767 <p>
     * NOTE: Create uses wheel encoders to measure distance and angle. If the
     * wheels slip, the actual distance or angle traveled may differ from
     * Create's measurements.
     * 
     * @return the angle the Create has turned since last requested in degrees
     */
    int getAngle();

    /**
     * The distance that Create has traveled in millimeters since the distance
     * it was last requested is sent as a signed 16-bit value, high byte first.
     * This is the same as the sum of the distance traveled by both wheels
     * divided by two. Positive values indicate travel in the forward direction;
     * negative values indicate travel in the reverse direction. If the value is
     * not polled frequently enough, it is capped at its minimum or maximum. <p>
     * Range: -32768 - 32767
     * 
     * @return the distance the Create has traveled since last requested in millimeters
     */
    int getDistance();
    
    /**
     * The velocity most recently requested with a Drive command is returned. <p>
     * Range: -500 - 500 mm/s
     *
     * @return velocity set with last motion command, in mm/sec
     */
    int getRequestedVelocity();

    /**
     * The radius most recently requested with a Drive command is returned. <p>
     * Range: -32768 - 32767 mm
     * 
     * @return radius set with last motion command, in mm
     */
    int getRequestedRadius();

    /**
     * The velocity most recently requested with a Drive command is returned. <p>
     * Range: -500 - 500 mm/s
     *
     * @return left wheel velocity set with last motion command, in mm/sec
     */
    int getRequestedLeftVelocity();

    /**
     * The right wheel velocity most recently requested with a Drive Direct
     * command is returned. <p>
     * Range: -500 - 500 mm/s
     * 
     * @return right wheel velocity set with last motion command, in mm/sec
     */
    int getRequestedRightVelocity();

    /**
     * The current heading of the Create in degrees.
     * This is an addition to the normal Create sensors and is what might be returned
     * from a gyroscope or compass sensor.<p>
     * Range: 0 - 359 <p>
     * NOTE: This method is not available on a real Create.
     *
     * @return current heading of the Create in degrees
     */
    int getHeading();


    /**
     * The state of the wall sensor, true if there is a wall.
     *
     * @return true if there is a wall to right of Create
     */
    boolean isWall();

    /**
     * The strength of the wall sensor's signal is returned. <p>
     * Range: 0-4095
     * 
     * @return strength of the wall sensor's signal
     */
    int getWallSignal();

    /**
     * Return true if the left bumper is pressed.
     * 
     * @return true if the left bumper is pressed
     */
    boolean isBumpLeft();

    /**
     * Return true if the right bumper is pressed.
     * 
     * @return true if the right bumper is pressed
     */
    boolean isBumpRight();

    /**
     * Cause the Java execution to pause until the Create has travelled to a
     * delta of degrees.  This command assumes that the robot is already
     * moving/turning already.  If not, then this command will never return,
     * as this method will not return until the robot has rotated degrees.
     * The state of the Create will not be affected, this means that if you expect
     * the robot to stop after waiting degrees of turn, then you will need to send
     * the Create the stop command.
     * <p>
     * <code>
     * create.driveDirect(100,-100);<br>
     * waitAngle(90);<br>
     * create.stop()
     * </code>
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param degrees
     */
    void waitAngle(int degrees);

    /**
     * Cause the Java execution to pause until the Create has travelled to a
     * delta of degrees or the specified timeout has expired.
     * The state of the Create will not be affected, this means that if you expect
     * the robot to stop after waiting degrees of turn, then you will need to send
     * the Create the stop command.
     * <p>
     * <code>
     * create.driveDirect(100,-100);<br>
     * waitAngle(90);<br>
     * create.stop()
     * </code>
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param degrees
     * @param timeout length of time to wait in milliseconds
     */
    void waitAngle(int degrees, long timeout) throws InterruptedException;

    /**
     * Pause Java execution until the Create has travelled mms.
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param mms distance in millimeters
     */
    void waitDistance(int mms);

    /**
     * Pause Java execution until the Create has travelled mms or the specified timeout has expired.
     * <p>
     * Note: this method samples the Create every 50ms.
     *
     * @param mms distance in millimeters
     * @param timeout length of time to wait in milliseconds
     */
    void waitDistance(int mms, long timeout) throws InterruptedException;

    /**
     * Pause Java execution until one of the Create bump sensors is true.
     */
    void waitBump();

    /**
     * Pause Java execution until one of the Create bump sensors is true
     * or the specified timeout has expired.
     *
     * @param timeout length of time to wait in milliseconds
     */
    void waitBump(long timeout) throws InterruptedException;

    /**
     * Pause Java execution until neither of the Create bump sensors is true.
     */
    void waitNoBump();

    /**
     * Pause Java execution until neither of the Create bump sensors is true
     * or the specified timeout has expired.
     *
     * @param timeout length of time to wait in milliseconds
     */
    void waitNoBump(long timeout) throws InterruptedException;

    /**
     * This value identifies the IR byte currently being received by iRobot
     * Create. A value of 255 indicates that no IR byte is being received. These
     * bytes include those sent by the Roomba Remote, the Home Base, Create
     * robots using the Send IR command, and user-created devices. <p>
     * Range: 0 - 255
     *
     * @return One of IRobotCreateConstants.INFRARED_BYTE_*
     */
    int getInfraredByte();


    /******************************************************************
     *
     * Listener commands
     *
     ******************************************************************/

    /**
     * Register a new bump listener
     *
     * @param listener who to notify
     */
    void addIBumpListener(IBumpListener listener);

    /**
     * Remove a bump listener
     *
     * @param listener who to no longer notify
     */
    void removeIBumpListener(IBumpListener listener);

    /**
     * Returns an array of all the bump listeners registered on this Create.
     *
     * @return all of the bump listeners or an empty array if none are currently registered.
     */
    IBumpListener[] getIBumpListeners();

    /**
     * Register a new wall listener
     *
     * @param listener who to notify
     */
    void addIWallListener(IWallListener listener);

    /**
     * Remove a wall listener
     *
     * @param listener who to no longer notify
     */
    void removeIWallListener(IWallListener listener);

    /**
     * Returns an array of all the wall listeners registered on this Create.
     *
     * @return all of the wall listeners or an empty array if none are currently registered.
     */
    IWallListener[] getIWallListeners();

    /**
     * Set how often the bump sensors are checked in order to notify any IBumpListener's.
     * <p>
     * Note: period is ignored by Emulator.
     *
     * @param period time in milliseconds between checks. Must be greater than 15 msec.
     */
    void setIBumpListenerPeriod(long period);


    /**
     * Set how often the wall sensor is checked in order to notify any IWallListener's.
     * <p>
     * Note: period is ignored by Emulator.
     *
     * @param period time in milliseconds between checks. Must be greater than 15 msec.
     */
    void setIWallListenerPeriod(long period);

}