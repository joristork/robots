/*
 * Created on Aug 13, 2004
 * FleeBehavior (dip=5) moves in the opposite direction
 * of an obstacle detected by a sensor
 * The behavior base class sits on the behavior base class
 * @author James Caska
 */
package javaBot.JrTacho;

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
public class BraitenbergBehavior extends Behavior
{
	private int	dip	= 0;

	/**
	 * Creates a new BraitenbergJrBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 * @param dipValue TODO PARAM: DOCUMENT ME!
	 */
	public BraitenbergBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod, int dipValue)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		dip = dipValue;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param value TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private int scale(int value)
	{
		if (value > 100)
		{
			return 100;
		}
		else
		{
			return value;
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int s0 = 0;
		int s1 = 0;
		int vx = 0;
		int vy = 0;

		s0 = getJoBot().getSensor(0);
		s1 = getJoBot().getSensor(1);

		if (dip == 0)
		{
			vx = s0;
			vy = s1;
		}

		if (dip == 1)
		{
			vx = s1;
			vy = s0;
		}

		if (dip == 2)
		{
			vx = -s0;
			vy = -s1;
		}

		if (dip == 3)
		{
			vx = -s1;
			vy = -s0;
		}

		getJoBot().setStatusLeds(false, vx == 0, vy == 0);
		getJoBot().drive(vx, vy);
	}
}
