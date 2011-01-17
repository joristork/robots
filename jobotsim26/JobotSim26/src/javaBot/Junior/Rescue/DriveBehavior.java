/*
 * Created on Aug 13, 2004
 * DriveTestJrBehavior (dip=5) drives the robot in a straight
 * line at various speeds
 */
package javaBot.Junior.Rescue;

import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision$
 * last changed 20-02-2006
 *
 * TODO CLASS: DOCUMENT ME! 
 */
public class DriveBehavior extends Behavior
{
	private JobotBaseController	joBot;
	private int						state	= 0;
	private int						count	= 0;
	private int						vl		= 0;
	private int						vr		= 0;

	/**
	 * Creates a new DriveBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public DriveBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		joBot = initJoBot;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int sl = joBot.getSensor(0);           // Get the bumpSensors
		int sr = joBot.getSensor(1);

		if (state == 0)
		{
			if ((sl > 100) && (sr > 100))
			{
				vl = -100;
				vr = -15; //-95
				state = 1;
				System.out.println("DriveState = Back");
			}
			else if (sl > 100)
			{
				vl = -100;
				vr = -10; // -50
				state = 1;
				System.out.println("DriveState = Back");
			}
			else if (sr > 100)
			{
				vl = -10; //-50
				vr = -100;
				state = 1;
				System.out.println("DriveState = Back");
			}
			else
			{
				vl = 100;
				vr = 100;
			}
		}
		else
		{
			// Make sure we drive away for some time
			count++;

			if (count == 2)
			{
				state = 0;
				count = 0;
				vl = 100;
				vr = 100;
				System.out.println("DriveState = Forward");
				joBot.setState(-1);     // kill this task
			}
		}

		joBot.setStatusLeds(sl > 100, sr > 100, false);
		joBot.drive(vl, vr);
	}
}
