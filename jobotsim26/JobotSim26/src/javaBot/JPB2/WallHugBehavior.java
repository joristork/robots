/*
 * Created on Aug 13, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * The WallHugbehavior (8) class sits on the Behavior base class
 * WallHug tries to keep the robot at a fixed distance from the wall
 * It uses just one sensor for this.
 * An improved version of this behavior could use all sensors and
 * first try to find out which side is closest to the wall
 *
 * The WallHug behavior is a state machine that tries to keep the robot
 * at a fixed distance from the wall, using only two sensors
 */
package javaBot.JPB2;

import com.muvium.apt.PeriodicTimer;

/**
 * WallHugBehavior is aa simple wall follower using two sensor.
 * We read the two sensors facing the wall and determine the sum and the difference.
 * The difference controls the angle the robot makes against the wall and
 * by feeding the difference into the vectorDrive it actually balances itself
 * against the wall using the robot's rotation.
 * The sum of the sensors determines how far we are from the wall.
 * This number is fed into the Y component of the vectorDrive and thus
 * controls the distance to the wall.
 *
 */
public class WallHugBehavior extends Behavior
{
	/**
	 * Creates a new WallHugBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public WallHugBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int s1 = 0;
		int s2 = 0;
		int vy = 0; // forward/backward
		int vr = 0; // positive is clockwise

		final int READING_TO_CONTROL = 200;

		// Find minumum distance
		s1 = getJoBot().getSensor(1) - READING_TO_CONTROL;
		s2 = getJoBot().getSensor(2) - READING_TO_CONTROL;
		vy = (s2 + s1);
		vr = (s2 - s1);

		if (vy > 50)
		{
			vy = 50;
		}

		if (vy < -50)
		{
			vy = -50;
		}

		if (vr > 50)
		{
			vr = 50;
		}

		if (vr < -50)
		{
			vr = -50;
		}

		getJoBot().vectorDrive(50, vy, -vr);
		getJoBot().setStatusLeds(false, getJoBot().getSensor(2) > READING_TO_CONTROL,
				getJoBot().getSensor(1) > READING_TO_CONTROL);
	}
}
