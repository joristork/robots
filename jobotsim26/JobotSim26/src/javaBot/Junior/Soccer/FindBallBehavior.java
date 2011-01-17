package javaBot.Junior.Soccer;

import com.muvium.apt.PeriodicTimer;

/**
 * FindBallBehavior
 *
 * @author Peter van Lith Rotates around its axis to locate the ball. When the
 *         ball is detected, it will drive straight to is and stop in front If
 *         not found, it will move to another place on the field in a random
 *         manner
 */
public class FindBallBehavior extends Behavior
{
	private JobotBaseController	joBot;
	private int						state	= 0;
	private int						count	= 0;
	private int						vl		= 0;
	private int						vr		= 0;

	/**
	 * Creates a new FindBallBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public FindBallBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
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
		joBot.drive(15, -15); // 80, -80

		int bl = joBot.getSensor(0); // Get the bumper sensors
		int br = joBot.getSensor(1);
		int sl = joBot.getSensor(2); // Get the ball sensors
		int sr = joBot.getSensor(3);

		if (state == 0) // Searching
		{
			if ((bl > 100) || (br > 100))
			{ // Did we bump
				joBot.setState(3); // Goto Drive behavior
			}

			if ((sl > 100) && (sr > 100))
			{
				vl = 50; // 80
				vr = 50; // 80
			}
			else if (sl > 100)
			{
				vl = 50; // 80
				vr = 10; // 60
			}
			else if (sr > 100)
			{
				vl = 10; // 60
				vr = 50; // 80
			}
			else
			{
				count++;

				if (count > 50)
				{
					count = 0;
					state = 1;
					System.out.println("State = Move");
				}
			}
		}
		else
		{
			// Move to another place for some time
			//			count = (int) Math.random();
			count++;

			if (count >= 50)
			{
				state = 0;
				count = 0;
				vl = 50;
				vr = -50;
				System.out.println("State = Find");
			}
			else
			{
				vl = 100;
				vr = 100;
			}
		}

		joBot.setStatusLeds(false, sl > 100, sr > 100, false);
		joBot.drive(vl, vr);
	}
}
