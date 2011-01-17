package javaBot.UVM;

import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class SoccerBehavior extends Behavior
{
	public static final int	AMBIENT_LIGHT		= 200;
	public static final int	READING_TO_CONTROL	= 200;
	public static final int	BALL_IN_SIGHT		= 0;
	private boolean			ballInSight;
	private int				s0					= 0;
	private int				s1					= 0;
	private int				s2					= 0;
	private int				vx					= 50;	// positive = rightwards
	private int				vy					= 0;	// forward/backward
	private int				vr					= 0;	// positive is clockwise		
	public static final int	STH					= 240;
	public static final int	STHM				= 1360;
	public static final int	DSP					= 20;
	private int				shootCount			= 0;
	private boolean			shooting;

	/**
	 * Creates a new SoccerBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public SoccerBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		findBall();

		System.out.println(joBot.getSensor(3));
		gotoBall();

		//circleBall();
	}

	// A Findball.java
	public void findBall()
	{
		if (joBot.getSensor(3) == BALL_IN_SIGHT)
		{
			ballInSight = false;
			joBot.setStatusLeds(false, true, false);
			joBot.drive(100, 100, 100);
		}
		else
		{
			ballInSight = true;
			joBot.setStatusLeds(false, false, true);
			joBot.drive(0, 0, 0);
		}
	}

	// B Gotoball.java
	public void gotoBall()
	{
		if (joBot.getSensor(3) == 0)
		{
			ballInSight = false;
		}
		else
		{
			joBot.drive(50, 0, -50);
		}
	}

	// C Avoidcollision.java
	public void avoidCollision()
	{
		s0 = joBot.getSensor(0);
		s1 = joBot.getSensor(1);
		s2 = joBot.getSensor(2);

		if ((s0 > STH) && (s0 < STHM) && (s1 > STH) && (s1 < STHM) && (s2 > STH) && (s2 < STHM))
		{
			joBot.drive(DSP, DSP, DSP);
		}
		else if ((s0 > STH) && (s0 < STHM) && (s1 > STH) && (s1 < STHM))
		{
			joBot.drive(-DSP, DSP, 0);
		}
		else if ((s0 > STH) && (s0 < STHM) && (s2 > STH) && (s2 < STHM))
		{
			joBot.drive(DSP, 0, -DSP);
		}
		else if ((s1 > STH) && (s1 < STHM) && (s2 > STH) && (s2 < STHM))
		{
			joBot.drive(0, -DSP, DSP);
		}
		else if ((s0 > STH) && (s0 < STHM))
		{
			joBot.drive(0, DSP, -DSP);
		}
		else if ((s1 > STH) && (s1 < STHM))
		{
			joBot.drive(-DSP, 0, DSP);
		}
		else if ((s2 > STH) && (s2 < STHM))
		{
			joBot.drive(DSP, -DSP, 0);
		}
		else
		{
			joBot.drive(0, 0, 0);
		}
	}

	// D Circle.java
	public void circleBall()
	{
		if (joBot.getSensor(1) <= 12)
		{
			joBot.drive(50, -100, 50);
		}
		else
		{
			joBot.drive(-100, -100, -100);
		}
	}

	// E Shoot.java
	public void shoot()
	{
		if (shootCount < 10)
		{
			joBot.drive(-100, 0, 100);
		}
		else if (shootCount < 40)
		{
			joBot.drive(100, 0, -100);
		}
		else
		{
			shootCount = 0;
			shooting = false;
			joBot.drive(0, 0, 0);
		}

		shootCount++;
	}
}
