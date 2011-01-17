/*
 * Created on Aug 13, 2004
 * The ChaseBallbehavior (4) class sits on the Behavior base class
 * ChaseBall uses the special IR sensor, located at sensor position 0
 * It also uses sensors 1 and 2 to avoid obstacles
 * Please note that the standard sensors are changed in this example
 * When no IR sensor is present, the robot randomly scans for a ball
 * @author Peter van Lith
 */
package javaBot.Junior.Rescue;

/**
 * The ChaseBall behavior is a state machine that tries to keep the robot at a
 * fixed distance from the ball, using the IR Sensor It consists of several
 * functions, working together: - FollowBall - Calibrate (Should store info in
 * global variable) - ScanForBall - AvoidObstacle - AvoidBall - ReadGreyScale
 */
import com.muvium.apt.PeriodicTimer;


/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class ChaseBallBehavior extends Behavior {
    /**
     * Creates a new ChaseBallBehavior object.
     *
     * @param initJoBot TODO PARAM: DOCUMENT ME!
     * @param initServiceTick TODO PARAM: DOCUMENT ME!
     * @param servicePeriod TODO PARAM: DOCUMENT ME!
     */
    public ChaseBallBehavior(JobotBaseController initJoBot,
        PeriodicTimer initServiceTick, int servicePeriod) {
        super(initJoBot, initServiceTick, servicePeriod);
    }

    /**
     * TODO METHOD: DOCUMENT ME!
     */
    public void doBehavior() {
        int s0 = 0;
        int s1 = 0;
        int vy = 0; // forward/backward
        int vr = 0; // positive is clockwise

        final int AMBIENT_LIGHT = 200;
        final int READING_TO_CONTROL = 200;

        // Find the ball
        s0 = getJoBot().getSensor(3) - AMBIENT_LIGHT; // Read the single IR sensor

        // Find minumum distance
        s1 = getJoBot().getSensor(1) - READING_TO_CONTROL;
        vy = (s0 + s1);
        vr = (s0 - s1);

        if (vy > 50) {
            vy = 50;
        }

        if (vy < -50) {
            vy = -50;
        }

        if (vr > 50) {
            vr = 50;
        }

        if (vr < -50) {
            vr = -50;
        }

        getJoBot().drive(50, vy);
        getJoBot().setStatusLeds(false, s0 > 0, s1 > 0, false);
    }
}
