/*
 * Created on Aug 13, 2004
 * The ChaseBallbehavior (4) class sits on the Behavior base class
 * ChaseBall uses the special IR sensor, located at sensor position 0
 * It also uses sensors 1 and 2 to avoid obstacles
 * Please note that the standard sensors are changed in this example
 * When no IR sensor is present, the robot randomly scans for a ball
 * @author Peter van Lith
 */
package javaBot.Junior;

/**
 * The ChaseBall behavior is a state machine that tries to keep the robot at a
 * fixed distance from the ball, using the IR Sensor 
 * It consists of several functions, working together: 
 * - FollowBall 
 * - Calibrate (Should store info in global variable) 
 * - ScanForBall 
 * - AvoidObstacle 
 * - AvoidBall 
 * - ReadGreyScale
 */
import com.muvium.apt.PeriodicTimer;

public class DribbleBallBehavior extends Behavior {
	
    public DribbleBallBehavior(JobotBaseController initJoBot,
        PeriodicTimer initServiceTick, int servicePeriod) {
        super(initJoBot, initServiceTick, servicePeriod);
    }

    public void doBehavior() {
        int s0 = 0;
        int s1 = 0;
        int vy = 0; // forward/backward
        int vr = 0; // positive is clockwise

        final int AMBIENT_LIGHT = 200;
        final int READING_TO_CONTROL = 200;

        // Find the ball
        s0 = getJoBot().getSensor(3) - AMBIENT_LIGHT; // Read the single IR sensor

        getJoBot().drive(50, vy);
        getJoBot().setStatusLeds(false, s0 > 0, s1 > 0, false);
    }
}
