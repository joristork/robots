/*
 * Created on Aug 13, 2004
 * The YoYoBehavior (9-10) class sits on the Behavior base class
 * WallHug tries to keep the robot at a fixed distance from the wall
 * It uses just one sensor for this.
 * An improved version of this behavior could use all sensors and
 * first try to find out which side is closest to the wall
 * @author James Caska
 */
package javaBot.UVM;

import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class YoYoBehavior extends Behavior
{
	int		velocity;
	int		acceleration;
	boolean	directionNorthSouth;

	/**
	 * Creates a new YoYoBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 * @param initAcceleration TODO PARAM: DOCUMENT ME!
	 * @param initNorthSouth TODO PARAM: DOCUMENT ME!
	 */
	public YoYoBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod, int initAcceleration, boolean initNorthSouth)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		acceleration = initAcceleration;
		directionNorthSouth = initNorthSouth;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
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

		//System.out.println("velocity = " + String.valueOf( velocity ) + " acceleration = " + String.valueOf(acceleration));
		if (directionNorthSouth)
		{
			joBot.vectorDrive(velocity, 0, 0);
		}
		else
		{
			joBot.vectorDrive(0, velocity, 0);
		}
	}
}
