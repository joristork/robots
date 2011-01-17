/*
 * Created on June 28, 2006
 * Copyright: (c) 2006
 * Company: UvA
 *
 * @version $Revision: 1.2 by nout $
 *
 * Calibrate is used before the soccer game begins.
 * Set the robot to his own goal, start the calibrate-program.
 * Wait 2 seconds, than move the robot to the middle of the field.
 * Wait another 2 seconds and finally put the robot at the 
 * target goal and wait 2 seconds.
 * 
 * This sets CALIBRATED_ values in UVMDemo: CALIBRATED_COLOR_WHITE, 
 * CALIBRATED_COLOR_BLACK, CALIBRATED_COLOR_MID (sensor values of 
 * white/black/mid field) and CALIBRATED_SIDE (what side the robot is).
 */
package javaBot.UVMSoccer;

import com.muvium.apt.PeriodicTimer;

/**
 * Calibrate the bot before the game begings
 *
 * 1.2 $ last changed June 28, 2006
 */

// ToDo: Calibrate speed to define what speed is associated with power

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
		joBot = initJoBot;
	}
	
	JobotBaseController joBot = null;
	private int subState = 0;
	private int subCount = 0;
	private int s0 = 0;
	private int s1 = 0;
	private int s2 = 0;
	private int fs = 0;
	private int dify = 0;	
	private int totSpeed = 0;
	
	/**
	 * The auto calibration puts the robot in the black side
	 * and then slowly drives to the other side.
	 * It uses its sensors to detect the end of the field
	 * While driving it takes snapshots of the field values
	 * A number of sub-states are used:
	 * 0 - Starting, put on black
	 * 1 - First reading is taken on black
	 * 2 - Driving to white
	 * 3 - Turning around
	 * 4 - Driving to black
	 * 5 - 9 Measuring speed at 10, 20, 40, 80 and 100 power
	 * 10 - Stop calibration
	 */ 
	
	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		s0 = joBot.getSensor(0);
		s1 = joBot.getSensor(1);
		s2 = joBot.getSensor(2);
		fs = joBot.getSensor(5);
		dify = s1 - s2;
		
		switch (subState) {
			case 0:			// Initialize
				System.out.println("Put robot in black goal, sensor 0 facing white goal.");
				System.out.println("When robot is ready, put ball in front of sensor 0.");
				System.out.println("Make sure ball is our of the wya while driving.");
				subState = 1;
				break;
			case 1:			// Wait for robot to be placed
				if (s1 > 300 && s2 > 300) {
					if (s0 > 200) {
						if (fs > 120)
							System.out.println("Robot is placed at wrong side");
						else {
							subState = 2;
							System.out.println("Robot is positioned on black");
							System.out.println("BLACK=" + fs + " stored");							
							System.out.println("Now driving towards white");
							joBot.setCalibratedValue(joBot.BLACK, fs);
							joBot.vectorDrive(0, 80,0);						}
					}
				}
				break;
			case 2:
				joBot.vectorDrive(dify, 80, 0);
				if (s0 > 480) {
					joBot.drive(0,0,0);
					System.out.println("reached white end");
					System.out.println("WHITE=" + fs + " stored");
					joBot.setCalibratedValue(joBot.WHITE, fs);
					System.out.println("Turning around");
					subState = 3;
				}
				break;
			case 3:
				joBot.vectorDrive(0,0,100);
				subState = 4;
				break;
			case 4:
				if (s0 < 50 && dify < 5) {
					System.out.println("Turned OK");
					joBot.drive(0,0,0);
					joBot.vectorDrive(0, 80,0);
					System.out.println("Driving back to black while measuring speed");
//					joBot.reportState(1);
					System.out.print("SPEED 10=");
					totSpeed = 0;
					subCount = 0;
					subState = 5;
				}
				break;
			case 5:
				measureSpeed (10, 20, 5);
				break;
			case 6:
				measureSpeed (20, 40, 5);
				break;
			case 7:
				measureSpeed (40, 80, 5);
				break;
			case 8:
				measureSpeed (80, 100, 5);
				break;
			case 9:
				if (s0 > 480) {
					System.out.println(totSpeed/subCount);
					System.out.println("reached black end");
					subState = 10;
				}
				subCount++;
				totSpeed += joBot.speed;
				joBot.vectorDrive(dify, 100, 0);
				break;
			case 10:
				System.out.println("Calibration complete");
				joBot.drive(0,0,0);
				subState = -1;
				break;			
		}
	}
	
	private void measureSpeed(int power, int next, int maxCount) {
		if (subCount > maxCount) {
			System.out.println(totSpeed/subCount);
			totSpeed = 0;
			subCount = 0;
			System.out.print("SPEED " + next + "=");
			subState++;
		}
		else {
			joBot.vectorDrive(dify, power,0);
			totSpeed += joBot.speed;
			subCount++;
		}
	}
}
