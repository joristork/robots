/*
 * Created on Aug 13, 2006 
 * Copyright: (c) 2006 
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 *
 * The ChaseBallbehavior (4) class sits on the Behavior base class
 * ChaseBall uses the special IR sensor, located at sensor position 0
 * It also uses sensors 1 and 2 to avoid obstacles
 * Please note that the standard sensors are changed in this example
 * When no IR sensor is present, the robot randomly scans for a ball
 * 
 * The ChaseBall behavior is a state machine that tries to keep the robot
 * at a fixed distance from the ball, using the IR Sensor
 * It consists of several functions, working together:
 * - FollowBall
 * - Calibrate (Should store info in global variable)
 * - ScanForBall
 * - AvoidObstacle
 * - AvoidBall
 * - ReadGreyScale
 */
package javaBot.UVMSoccer;

import javaBot.JPB2.Behavior;
import javaBot.JPB2.JobotBaseController;

import com.muvium.apt.PeriodicTimer;

/**
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */

public class ChaseBallBehaviorIR extends Behavior
{
	public ChaseBallBehaviorIR(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	public void doBehavior()
	{
		int s0 = 0;
		int s1 = 0;
		int s2 = 0;
		// int vx = 50; // positive = rightwards
		int vy = 0; // forward/backward
		int vr = 0; // positive is clockwise

		final int AMBIENT_LIGHT = 200;
		final int READING_TO_CONTROL = 200;

		// Find the ball
		s0 = getJoBot().getSensor(3) - AMBIENT_LIGHT; // Read the single IR
		// sensor
		// Find minumum distance
		s1 = getJoBot().getSensor(1) - READING_TO_CONTROL;
		s2 = getJoBot().getSensor(2) - READING_TO_CONTROL;
		vy = (s2 + s1);
		vr = (s2 - s1);

		if (vy > 50)
			vy = 50;
		if (vy < -50)
			vy = -50;
		if (vr > 50)
			vr = 50;
		if (vr < -50)
			vr = -50;
		getJoBot().vectorDrive(50, vy, -vr);
		getJoBot().setStatusLeds(s0 > 0, s2 > 0, s1 > 0);
	}
}