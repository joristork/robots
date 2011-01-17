/*
 * Created on Aug 13, 2004
 * The Calibratebehavior (dip=1) class sits on the base Behavior class
 * It keeps the servos to their neutral position
 * Reads the sensors and sets the status leds to
 * indicate whisch sensor is read
 * To ease testing it reacts only to close objects
 * @author Peter van Lith
 *
 */
package javaBot.agents;

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
public class CalibrateBehavior extends Behavior
{
	/**
	 * Creates a new CalibrateBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public CalibrateBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int i = 0;
		int s;
		int s1;

		// 		Retrieve the first three sensor values and 
		//		determine the highest value
		s = joBot.getSensor(0);
		s1 = joBot.getSensor(1);

		if (s1 > s)
		{
			s = s1;
			i = 1;
		}

		s1 = joBot.getSensor(2);

		if (s1 > s)
		{
			s = s1;
			i = 2;
		}

		// 		Include this only when IR sensor is present
		//		s1 = joBot.getSensor(3); 
		//		s = 0;
		//		if (s1 > s)
		//		   {s = s1; i=3;}
		// 		Include this only when Floor sensor is present
		//		s1 = joBot.getSensor(4); 
		//		s = 0
		//		if (s1 > s)
		//		   {s = s1; i=4;}
		if (s > 128)
		{
			System.out.print("S");
			System.out.print(i);
			System.out.print("=");
			System.out.println(s);

			//			if (i == 3)
			//				joBot.setStatusLeds(false, true, true); 		// Show IR detected
			//			else	
			joBot.setStatusLeds(i == 0, i == 2, i == 1); // Show distance detected
		}
		else
		{ // Reset lamps if no input read
			joBot.setStatusLeds(false, false, false);
		}

		joBot.drive(0, 0, 0);
	}
}
