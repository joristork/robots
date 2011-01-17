/*
 * Created on Aug 13, 2004
 * FleeBehavior (dip=5) moves in the opposite direction
 * of an obstacle detected by a sensor
 * The behavior base class sits on the behavior base class
 * @author James Caska
 */
package javaBot.UVM;

import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class CuriousBehavior extends Behavior
{
	/**
	 * Creates a new CuriousBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public CuriousBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int s0 = 0;
		int s1 = 0;
		int s2 = 0;
		int vx = 0;
		int vy = 0;
		int vz = 0;

		s0 = joBot.getSensor(0);
		s1 = joBot.getSensor(1);
		s2 = joBot.getSensor(2);

		if ((s0 > 50) && (s0 < 400) && (s0 > s1) && (s0 > s2))
		{
			s0 = 100;
			vx = 0;
			vy = -s0;
			vz = s0;
		}

		if ((s1 > 50) && (s1 < 400) && (s1 > s0) && (s1 > s2))
		{
			s1 = 100;
			vx = s1;
			vy = 0;
			vz = -s1;
		}

		if ((s2 > 50) && (s2 < 400) && (s2 > s0) && (s2 > s1))
		{
			s2 = 100;
			vx = -s2;
			vy = s2;
			vz = 0;
		}

		joBot.setStatusLeds(vx == 0, vy == 0, vz == 0);
		joBot.drive(vx, vy, vz);
	}
}
