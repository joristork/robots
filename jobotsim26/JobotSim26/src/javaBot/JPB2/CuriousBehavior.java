/*
 * Created on Aug 13, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * FleeBehavior (dip=5) moves in the opposite direction
 * of an obstacle detected by a sensor
 * The behavior base class sits on the behavior base class
 */
package javaBot.JPB2;

import com.muvium.apt.PeriodicTimer;

/**
 * CuriousBehavior reads all sensors and determines which one
 * seed an object closest.
 * It then makes sure the robot moves towards that object
 * and stops when it reaches a distance of about 10 cm.
 *
 */
public class CuriousBehavior extends Behavior
{
	public CuriousBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	public void doBehavior()
	{
		int s0 = 0;
		int s1 = 0;
		int s2 = 0;
		int vx = 0;
		int vy = 0;
		int vz = 0;

		s0 = getJoBot().getSensor(0);
		s1 = getJoBot().getSensor(1);
		s2 = getJoBot().getSensor(2);

		if ((s0 > 20) && (s0 < 400) && (s0 > s1) && (s0 > s2))
		{
			s0 = 100;
			vx = 0;
			vy = -s0;
			vz = s0;
		}

		if ((s1 > 20) && (s1 < 400) && (s1 > s0) && (s1 > s2))
		{
			s1 = 100;
			vx = s1;
			vy = 0;
			vz = -s1;
		}

		if ((s2 > 20) && (s2 < 400) && (s2 > s0) && (s2 > s1))
		{
			s2 = 100;
			vx = -s2;
			vy = s2;
			vz = 0;
		}

		getJoBot().setStatusLeds(vx == 0, vy == 0, vz == 0);
		getJoBot().drive(vx, vy, vz);
	}
}
