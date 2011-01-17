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
public class CalibrateWheelBehavior extends Behavior
{
	private int	count	= 0;

	/**
	 * Creates a new CalibrateJrBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public CalibrateWheelBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
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

		count++;
		if (count >= 100)
		{
			count = 0;
		}

		//		Retrieve the first two sensor values and 
		//		determine the highest value
		s = getJoBot().getSensor(0);
		s1 = getJoBot().getSensor(1);

		if (s1 > s)
		{
			s = s1;
			i = 1;
		}

		s1 = getJoBot().getSensor(2);

		if (s1 > s)
		{
			s = s1;
			i = 2;
		}

		s1 = getJoBot().getSensor(4);

		if (s1 > s)
		{
			s = s1;
			i = 4;
		}

		s1 = getJoBot().getSensor(9); // 9v
		System.out.print("V1=");
		System.out.print(s1);
		s1 = getJoBot().getSensor(8); // 6v
		System.out.print(" V2=");
		System.out.print(s1);
		System.out.print(" C=");
		System.out.print(count);
//		System.out.print(" L=");
//		System.out.print(getJoBot().getWlc());
//		System.out.print(" R=");
//		System.out.print(getJoBot().getWrc());

//		s1 = getJoBot().getSensor(4);
//		System.out.print(s1);
//		s1 = getJoBot().getSensor(5);
//		System.out.print(s1);
		if (s > 0)
		{
			System.out.print(" ");
			System.out.print("S");
			System.out.print(i);
			System.out.print("=");
			System.out.println(s);
		}
		else
		{
			System.out.println();
		}

		getJoBot().calcTacho(); // Clear counters
		getJoBot().setLed(JobotBaseController.LED_YELLOW, i == 0); // Show sensor detected
		getJoBot().setLed(JobotBaseController.LED_GREEN, i == 1);
		getJoBot().setLed(JobotBaseController.LED_BLUE, i == 1);
		getJoBot().drive(count, count);
	}
}
