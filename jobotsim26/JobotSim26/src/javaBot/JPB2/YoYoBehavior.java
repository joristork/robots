/*
 * Created on Aug 13, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * The YoYoBehavior (9-10) class sits on the Behavior base class
 * WallHug tries to keep the robot at a fixed distance from the wall
 * It uses just one sensor for this.
 * An improved version of this behavior could use all sensors and
 * first try to find out which side is closest to the wall
 */
package javaBot.JPB2;

import com.muvium.apt.PeriodicTimer;

/**
 * This simple behavior makes the robot swing back an forth along the X or the Y axis
 * The direction switch determines on which axis we are swinging.
 *
 */
public class YoYoBehavior extends Behavior
{
	private int		velocity;
	private int		acceleration;
	private boolean	directionNorthSouth;

	public YoYoBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod, int initAcceleration, boolean initNorthSouth)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		acceleration = initAcceleration;
		directionNorthSouth = initNorthSouth;
	}

	public void doBehavior()
	{
		velocity += acceleration;

		if (velocity > 100)
		{
			velocity = 100;
			acceleration = -acceleration;
		}
		else if (velocity < -100)
		{
			velocity = -100;
			acceleration = -acceleration;
		}

		//System.out.println("velocity = " + String.valueOf( velocity ) + "
		// acceleration = " + String.valueOf(acceleration));
		if (directionNorthSouth)
		{
			getJoBot().vectorDrive(velocity, 0, 0);
		}
		else
		{
			getJoBot().vectorDrive(0, velocity, 0);
		}
	}
}
