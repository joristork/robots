/*
 * Created on Feb 14, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * The Calibratebehavior (dip=1) class sits on the base Behavior class
 * It keeps the servos to their neutral position
 * Reads the sensors and sets the status leds to
 * indicate whisch sensor is read
 * To ease testing it reacts only to close objects
 *
 */
package javaBot.JPB2;

import com.muvium.apt.PeriodicTimer;

/**
 * The Calibration behavior reads each of the three distancesensors.
 * It then determines which of the sensors returns the highest value.
 * A threshold of 128 is used, which makes that the robot only reacts
 * in a relatively small area so you are not bothered with
 * sensitive signals.
 * When the Test switch is on, the wheels spin with a speed
 * proportional to the distance of the object and a tone is heard.
 * Also the red, yellow and green indicators are used to show 
 * which sensor reads something.
 * This is used to test both the sensors and the motors
 */
public class CalTestBehavior extends Behavior
{
	boolean drive = false;
	
	/**
	 * Creates a new CalibrateBehavior object.
	 *
	 * @param initJoBot 		- is the Base Controller
	 * @param initServiceTick 	- Is the serviceTick object
	 * @param servicePeriod 	- Is the timer interval
	 * @param test				- This switch is on when we run in drive mode
	 */
	public CalTestBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod, boolean test)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		drive = test;
	}

	public void doBehavior()
	{
		int i = 0;
		int s;
		int s1;
		String str = "";

		// Retrieve the first three sensor values and determine the highest
		// value
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

		s1 = getJoBot().getSensor(9); // 9v
		System.out.print("V1=");
		System.out.print(s1);
		s1 = getJoBot().getSensor(8); // 6v
		System.out.print(" V2=");
		System.out.print(s1);
		System.out.print(" ");

		if (s > 128)
		{
			System.out.print("S");
			System.out.print(i);
			System.out.print("=");
			System.out.println(s);
			getJoBot().tone(i);
			// Show distance detected
			getJoBot().setStatusLeds(i == 0, i == 2, i == 1, i > 2);
		}
		else
		{
			// Reset lamps if no input read
			System.out.println();
			getJoBot().setStatusLeds(false, false, false);
		}
		if (drive)
			getJoBot().drive(s-128, s-128, s-128);
		else getJoBot().drive(0, 0, 0);
	}
}
