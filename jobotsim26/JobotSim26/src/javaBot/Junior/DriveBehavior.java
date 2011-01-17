/*
 * Created on Aug 13, 2004
 * DriveTestJrBehavior (dip=5) drives the robot in a straight
 * line at various speeds
 */
package javaBot.Junior;

import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Dirves forward until an obstacle is detected.
 * It steers away from the obstacle and then continues
 */
public class DriveBehavior extends Behavior
{
	private JobotBaseController	joBot;
	private int	state	= 0;
	private int	count	= 0;
	private int	vl		= 0;
	private int	vr		= 0;
    final   int NEARBY = 400;   

	public DriveBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		joBot = initJoBot;
	}

	public void doBehavior()
	{
		int sl = joBot.getSensor(0);           // Get the bumpSensors
		int sr = joBot.getSensor(1);

		if (state == 0)
		{
			if ((sl > NEARBY) && (sr > NEARBY))
			{
				vl = -100;
				vr = -10; 
				state = 1;
//				System.out.println("DriveState = Back");
			}
			else if (sl > NEARBY)
			{
				vl = -10;
				vr = -100;
				state = 1;
//				System.out.println("DriveState = Bac L");
			}
			else if (sr > NEARBY)
			{
				vl = -100; 
				vr = -10;
				state = 1;
//				System.out.println("DriveState = Back R");
			}
			else
			{
				vl = 100;
				vr = 100;
			}
		}
		else
		{
			count++;
			// Get away from obstacle
			if (state == 1)
			{
				if (count == 10)
				{
					state = 2;
					count = 0;
					vl = 100;
					vr = 100;
//					System.out.println("DriveState = Away");
				}
			}
			else
				// Make sure we drive away for some time
				if (count == 15)
				{
					state = 0;
					count = 0;
//					System.out.println("DriveState = Forward");
					joBot.setState(-1);     // kill this task
				}
		}

		joBot.setStatusLeds(sl > NEARBY, sr > NEARBY, false);
		joBot.drive(vl, vr);
	}
}
