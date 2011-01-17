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
package javaBot.Junior;

import com.muvium.apt.PeriodicTimer;

/**
 * CalibrateBehavior tests the various sensons on board
 * It reads all sensors and displays the sensor values.
 * 
 * Sensor Levels:
 * Power 9v (9) 580=9.0v, 480=7.5v Ratio is 6.4
 * Power 5v (8) 560=5.5v, 250=2.5v Ratio is 9.6
 * Make sure that the 9v level does not get under 7.5 volts
 * The 5v supply should not get under 2.5 volts
 * 
 * Sensor	L Low	L High	R Low	R High
 * Dist	   
 * Ground
 * IR
 */

public class CalibrateBehavior extends Behavior
{
	int state = 0;
	
	public CalibrateBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	public void doBehavior()
	{
		int i = -1;
		int s = 0;
		int s1 = 0;

		if (state == 0) {
			System.out.println("UVMdemo Jr V2.0");
			state = 1;
		}
		
		//		Retrieve the first four sensor values and 
		//		determine the highest value
		
//		s = getJoBot().getSensor(0);
//		s1 = getJoBot().getSensor(1);		
		// activate this code only if you want to show the highest value
//		if (s1 > s)
//		{
//			s = s1;
//			i = 1;
//		}
//
//		s1 = getJoBot().getSensor(2);
//		if ((s1 / 1) > s)
//		{
//			s = s1;
//			i = 2;
//		}
//
//		s1 = getJoBot().getSensor(3);
//		if ((s1 / 1) > s)
//		{
//			s = s1;
//			i = 3;
//		}
//
//		s1 = getJoBot().getSensor(4);
//		if (s1 > s)
//		{
//			s = s1;
//			i = 4;
//		}

		s1 = getJoBot().getSensor(9); // 9v
		System.out.print("V1=");
		System.out.print(s1*10/64);
		s1 = getJoBot().getSensor(8); // 6v
		System.out.print(" V2=");
		System.out.print(s1*10/95);

		System.out.print(" DL=");
		s1 = getJoBot().getSensor(0);
		if (s1 > 300) i = 0;
		System.out.print(s1);
		
		System.out.print(" DR=");
		s1 = getJoBot().getSensor(1);
		if (s1 > 300) i = 1;
		System.out.print(s1);
		
		System.out.print(" FL=");
		s1 = getJoBot().getSensor(2);
		if (s1 > 300) i = 2;
		System.out.print(s1);
		
		System.out.print(" FR=");
		s1 = getJoBot().getSensor(3);
		if (s1 > 300) i = 3;
		System.out.print(s1);
		
		System.out.print(" IL=");
		s1 = getJoBot().getSensor(4);
		if (s1 > 300) i = 4;
		System.out.print(s1);
		
		System.out.print(" IR=");
		s1 = getJoBot().getSensor(5);
		if (s1 > 300) i = 5;
		System.out.print(s1);
		
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
		getJoBot().setLed(JobotBaseController.LED_YELLOW, i == 0 || i == 2 || i ==4); 	// Show sensor left detected
		getJoBot().setLed(JobotBaseController.LED_GREEN, i == 1 || i == 3 || i == 5);	// Show sensor right detected
		getJoBot().setLed(JobotBaseController.LED_BLUE, i == 4 || i == 5); 				// Ball detected
		getJoBot().drive(0, 0);
	}
}
