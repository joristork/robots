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
package javaBot.Junior.Rescue;

import com.muvium.apt.PeriodicTimer;

/**
 * CalibrateBehavior tests the various sensons on board
 * It reads all sensors and shows the sensor with the highest value.
 * Note that the infrared contact sensors return an inverted value
 * S0 - IR Left
 * S1 - Contact Right
 * S2 - IR Left
 * 
 */
public class CalibrateBehavior extends Behavior
{
	/**
	 * Creates a new CalibrateJrBehavior object.
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

	public void doBehavior()
	{
		int i = 0;
		int s = 0;
		int s1 = 0;

		//		Retrieve the first two sensor values and 
		//		determine the highest value
//		s = getJoBot().getSensor(0);
//		s1 = getJoBot().getSensor(1);
//		if (s1 > s)
//		{
//			s = s1;
//			i = 1;
//		}

		s1 = getJoBot().getSensor(2);
		if ((s1 / 1) > s)
		{
			s = s1;
			i = 2;
		}

		s1 = getJoBot().getSensor(3);
		if ((s1 / 1) > s)
		{
			s = s1;
			i = 3;
		}

//		s1 = getJoBot().getSensor(4);
//		if (s1 > s)
//		{
//			s = s1;
//			i = 4;
//		}

		s1 = getJoBot().getSensor(9); // 9v
		System.out.print("V1=");
		System.out.print(s1);
		s1 = getJoBot().getSensor(8); // 6v
		System.out.print(" V2=");
		System.out.print(s1);

		System.out.print(" L=");
		s1 = getJoBot().getSensor(2);
		System.out.print(s1);
		System.out.print(" R=");
		s = getJoBot().getSensor(3);
		System.out.print(s);
		
//		if (s > 0)
//		{
//			System.out.print(" ");
//			System.out.print("S");
//			System.out.print(i);
//			System.out.print("=");
//			System.out.println(s);
//		}
//		else
		{
			System.out.println();
		}
		getJoBot().setLed(JobotBaseController.LED_YELLOW, i == 0 || i == 2); // Show sensor detected
		getJoBot().setLed(JobotBaseController.LED_GREEN, i == 1 || i == 3);
		getJoBot().setLed(JobotBaseController.LED_BLUE, i > 1 || i == 4);
		getJoBot().drive(0, 0);
	}
}
