/*
 * Created on Jun 19, 2006 
 *
 * @version 1.2
 *
 */
package javaBot.UVMSpeech;

import com.muvium.apt.PeriodicTimer;

/**
 * @version 1.3 last changed Jun 28, 2006
 */

public class AttackBehavior extends Behavior
{
//	private JobotBaseController	joBot;
	
	private final int S0 	= 0;			// Front reflection sensor
	private final int IRR 	= 3;			// Left IR sensor
	private final int IRL 	= 4;			// Right IR sensor
	private final int FLOOR = 5;			// Floor sensor
	
	// These numbers can be changed depending on surrounding lights at the location
	private final int AMBIENT_IR 	= 0;
	private final int AMBIENT_FLOOR = 0;
	private final int AMBIENT_LIGHT = 0;
	
	private final int STRAIGHT 	= 10;
	private final int CLOSE 	= 700;
	private final int COLOR 	= 500;
	private final int MOVE 		= 11;
	
	private int count 		= 0;		// the number of times a behavior has been called
	private int hold 		= 0;		// the number of times a behavior is used
	private boolean where 	= true;		// 
	
	public AttackBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	public void doBehavior()
	{	
		System.out.println(hold + " + " + count);
		
		if(gotTheBall() && closeToGoal() && hold == 0)
		{
			kickBall();
		}
		
		if(seeLeft() && hold == 0 && count == 0)
		{
			where = true;
			goLeft();
		}

		if(seeRight() && hold == 0 && count == 0)
		{
			where = false;
			goRight();
		}
		
		if(gotTheBall() && !closeToGoal() && count == 0 || hold > 0)
		{	
			if(where){
				if(hold < MOVE)
				{
					goLeft();
					hold += 1;
				}
				else
				{
					if(hold < MOVE)
					{
						goRight();
						hold += 1;
					}
					else
						hold = 0;
				}
			}
			else {
				if(hold < MOVE)
				{
					goRight();
					hold += 1;
				}
				else
				{	
					if(hold < MOVE)
					{
						goLeft();
						hold += 1;
					}
					else
						hold = 0;
				}
			}
		}
		
		if(seeTheBall() && hold == 0 && count == 0)
		{
			getJoBot().vectorDrive(0, 100, 0);
			getJoBot().setStatusLeds(true, true, true);
		}
	}

/***********************************************************************
	Submethodes.
***********************************************************************/
	
	
	// this methode makes the robot move to the left
	public void goLeft()
	{
		getJoBot().vectorDrive(0, 100, 50);
		getJoBot().setStatusLeds(false, true, false);
	}
	
	// this methode makes the robot move to the right
	public void goRight()
	{
		getJoBot().vectorDrive(0, 100, -50);
		getJoBot().setStatusLeds(false, false, true);
	}

	// this methode makes a forward thrust move.
	public void kickBall()
	{
		if (count < MOVE)
		{
			getJoBot().vectorDrive(0, 100, 0);
			getJoBot().setStatusLeds(true, true, true);
			count += 1;
		}
		else
		{	
			if (count < MOVE)
			{
				getJoBot().vectorDrive(0, 0, 0);
				count += 1;
			}
			else
				count = 0;
		}
	}
	
	// this methode returns true if robot is close to the goal of the opponent
	public boolean closeToGoal()
	{
		int fs	= getJoBot().getSensor(FLOOR) - AMBIENT_FLOOR;	// Read floor sensor - the ambient light
		if (fs > COLOR)
			return true;
		else
			return false;
	}
	
	// this methode returns true if the the ball is close to the robot
	public boolean gotTheBall()
	{
		int irl	= getJoBot().getSensor(IRL) - AMBIENT_IR;		// Read left IR sensor - the ambient light
		int irr	= getJoBot().getSensor(IRR) - AMBIENT_IR;		// Read right IR sensor - the ambient light
		int ir = irl - irr;
		if (abso(ir) < STRAIGHT && seeStraightAhead() && irl > CLOSE && irr > CLOSE)
			return true;
		else
			return false;
	}

	// this methode returns true if the reflection sensor in front of the robot sees the ball
	public boolean seeStraightAhead()
	{
		int s0 = getJoBot().getSensor(S0) - AMBIENT_LIGHT;		// Read sensor 0 - the ambient light
		if (s0 > 0 )
			return true;
		else
			return false;
	}
	
	// this methode returns true if the IR sensors see the ball
	public boolean seeTheBall()
	{
		int irl	= getJoBot().getSensor(IRL) - AMBIENT_IR;		// Read left IR sensor - the ambient light
		int irr	= getJoBot().getSensor(IRR) - AMBIENT_IR;		// Read right IR sensor - the ambient light
		int ir = irl - irr;
		if (abso(ir) < STRAIGHT)
			return true;
		else
			return false;
	}
	
	// this methode returns true if the left IR sensor sees the ball and the right doesn't
	public boolean seeLeft()
	{
		int irl	= getJoBot().getSensor(IRL) - AMBIENT_IR;		// Read left IR sensor - the ambient light
		int irr	= getJoBot().getSensor(IRR) - AMBIENT_IR;		// Read right IR sensor - the ambient light
		if (irl > 0 && irr == 0)
			return true;
		else
			return false;
	}

	// this methode returns true if the right IR sensor sees the ball and the left doesn't
	public boolean seeRight()
	{
		int irr	= getJoBot().getSensor(IRR) - AMBIENT_IR;		// Read right IR sensor - the ambient light
		int irl	= getJoBot().getSensor(IRL) - AMBIENT_IR;		// Read leftt IR sensor - the ambient light
		if (irr > 0 && irl == 0)
			return true;
		else
			return false;
	}
	
	// this methode calculates the absolute value of an integer
	public int abso(int x)
	{
		if(x < 0)
			return (x *= -1);
		else
			return x;
	}
}
