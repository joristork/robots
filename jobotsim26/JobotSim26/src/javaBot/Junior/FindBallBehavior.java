package javaBot.Junior;

import com.muvium.apt.PeriodicTimer;

/**
 * FindBallBehavior
 *
 * @author Peter van Lith 
 * Rotates around its axis to locate the ball. When the
 * ball is detected, it will drive straight to is and stop in front
 * If not found, it will move to another place on the field in a random
 * manner
 */

// ToDo: During bump continue to look for the ball

public class FindBallBehavior extends Behavior
{
	private JobotBaseController	joBot;
	private int	state	= 0; // 0=follow, 1=find
	private int	count	= 0;
	private int	vl		= 0;
	private int	vr		= 0;
	private int ballSeenLeft = 0;
	final static int AMBIENT = 300;
	final static int NEARBY = 400; 
	final static int BALLSEEN = AMBIENT;


	public FindBallBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		joBot = initJoBot;
	}

	public void doBehavior()
	{
		int ballDetected = 0;

		int sl = joBot.getSensor(0); // Get the bumper sensors
		int sr = joBot.getSensor(1);
		int bl = joBot.getSensor(4); // Get the ball sensors
		int br = joBot.getSensor(5);

		if ((bl > BALLSEEN) && (br > BALLSEEN))
		{
			vl = 100; 
			vr = 100; 
			ballDetected = 1;
			ballSeenLeft = 0;
		}
		else if (bl > BALLSEEN)
		{
			vl = 90; 
			vr = 100;
			ballDetected = 1;
			ballSeenLeft = 1;
		}
		else if (br > BALLSEEN)
		{
			vl = 100; 
			vr = 90;
			ballDetected = 1;
			ballSeenLeft = 0;
		}
		if (ballDetected == 1)
		{
			state = 0;
			count = 0;
		}

		if (((sl > NEARBY) || (sr > NEARBY)) && ballDetected == 0)
		{ 
			joBot.setState(UVMDemo.STATE_DRIVE);      // Goto Drive behavior
//			System.out.println("State = Drive");
			return ;
		}

		if (state == 0)       		// Following ball
		{
			if (ballDetected == 1)
			{
//				if (count == 0)
//				System.out.println("State = Follow");
			}
			else	
			{
				if (ballSeenLeft == 1)
				{
					vl = -50;
					vr = 50;
					
				}
				else
				{
					vl = 50;
					vr = -50;
				}
//				if (count == 0)
//				System.out.println("State = Search");
				count++;

				if (count > 30)
				{
					count = 0;
					state = 1;
//					System.out.println("State = Move");
				}
			}
		}
		else
		{
			// Move to another place for some time
			//			count = (int) Math.random();
			vl = 100;
			vr = 100;
			count++;

			if (count > 30)
			{
				state = 0;
				count = 0;
			}
		}

		joBot.setStatusLeds(sl > NEARBY, sr > NEARBY, ballDetected == 1);
		joBot.drive(vl, vr);
	}
}
