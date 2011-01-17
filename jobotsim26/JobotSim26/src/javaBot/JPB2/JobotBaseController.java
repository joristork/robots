/*
 * Created on Aug 13, 2004
 * Controller for an OmniDirectional Robot Base
 * Provides the basic functionality for every joBot.
 * When creating a new application, make sure an instance
 * of this class is created, that the robot can inherit from.
 *  Ver 0.5 - 07-09-2004 Modified wheel base
 *
 */
package javaBot.JPB2;

import javaBot.JPB2.ServoLinearizator;
import com.muvium.apt.ADCReader;
import com.muvium.apt.MultiServoController;
import com.muvium.apt.PeripheralFactory;
import com.muvium.io.PortIO;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * The BaseController contains all basic commands that a robot
 * needs to read its sensors, control its motors and uses its
 * output like LEDs and the speaker.
 */
public class JobotBaseController
{
	public static final byte	ledRED	= (byte) 0x04;
	public static final byte	ledYEL	= (byte) 0x05;
	public static final byte	ledGRN	= (byte) 0x06;
	public static final byte	ledBLU	= (byte) 0x07;
	ToneGenerator				tonePlayer;
	MultiServoController		servoDirect;
	int							servoMax;
	int							servoMid;
	ADCReader					adc;
	int							reporting = 0;					
	private UVMDemo				robot;
	
	/**
	 * Creates a new JobotBaseController object.
	 *
	 * @param factory	- The factory that helps making the peripheral interfaces
	 * @param theDemo	- Provides a pointer to the UVMDemo object so we can access the
	 * 					  web-services and robot object.
	 */
	public JobotBaseController(PeripheralFactory factory, UVMDemo theDemo)
	{
		servoDirect = factory
				.createMultiServoController(MultiServoController.IMPLEMENTATION_DIRECT);
		servoMax = servoDirect.getMaxPosition();
		servoMid = servoMax >> 1;
//		System.out.print("Servo=");
//		System.out.println(servoMax);
		adc = factory.createADCReader(0, ADCReader.READ_INT);
		tonePlayer = new ToneGenerator(factory);
		robot = theDemo;
		try
		{
			servoDirect.start();
		}
		catch (Exception e)
		{
//			System.out.println("Failed!");
		}
	}

	/**
	 * This toggles the red LED to indicate the heartbeat.
	 * A heartbeat is shown so we know the program is still alive.
	 */
	public void heartBeat()
	{
		PortIO.toggleOutputPin(ledRED, PortIO.PORTE);
	}
	
	/**
	 * setStatusLights turns on/off the status leds
	 */

	public void setStatusLeds(boolean yel, boolean grn, boolean blu)
	{
		PortIO.setOutputPin(yel, ledYEL, PortIO.PORTE);
		PortIO.setOutputPin(grn, ledGRN, PortIO.PORTE);
		PortIO.setOutputPin(blu, ledBLU, PortIO.PORTE);
	}

	public void setStatusLeds(boolean red, boolean yel, boolean grn, boolean blu)
	{
		PortIO.setOutputPin(red, ledRED, PortIO.PORTE);
		PortIO.setOutputPin(yel, ledYEL, PortIO.PORTE);
		PortIO.setOutputPin(grn, ledGRN, PortIO.PORTE);
		PortIO.setOutputPin(blu, ledBLU, PortIO.PORTE);
	}

	public void setLed(int led, boolean state)
	{
		PortIO.setOutputPin(state, led, PortIO.PORTE);
	}

	/**
	 * Sets the speed and direction of the servo
	 *
	 * @param value	- sets the servo speed. 0 = stop, 
	 * 				  -100 and +100 are full speed forward and backward.
	 * @param servo	- Is the servo number 0, 1 or 2
	 */
	public void setServo(int value, int servo)
	{
		servoDirect.setPosition(value, servo);
	}

	/**
	 * Reads the value of the given sensor.
	 * The maximum value of 1023 is never returned but is set to 0.
	 * 
	 * @param sensor	- Sensor number corresponding to the sensors 
	 * 					  as defined in the robot module.
	 * 					  In this case javaBot.JPB2Robot.
	 */
	public int getSensor(int sensor)
	{
		adc.setChannel(sensor);

		int i = adc.getSample();

		if (i == 1023)
		{
			i = 0;
		}

		return i;
	}

	/*
	 * Drive directly drives the servo's
	 */
	public void drive(int x, int y, int z)
	{
		servoDirect.setPosition(scaleToServoSpeed(x), 0);
		servoDirect.setPosition(scaleToServoSpeed(y), 1);
		servoDirect.setPosition(scaleToServoSpeed(z), 2);
	}

	/**
	 * Checks the boundary's and makes sure the preconditions are met.
	 * @param val Percentage between -100 to 100
	 * @return The non lineair speed of the servo motor
	 */
	public int scaleToServoSpeed(int val)
	{
		int value = val;

		if (val >= 100)
		{
			value = 99;
		}

		if (val <= -100)
		{
			value = -99;
		}

		int servoValue = ServoLinearizator.convertPowerToServoSpeed(value);
		int servoCalculated = servoValue * servoMid;
		int roundCorrection = 0;
		
		/* Because the muvium controller cannot use doubles, the formula in the return statement
		 * of this method, servoCalculated / 100 can result in a double value, losing the rounding
		 * precision. To correct this, we see if the servoCalculated value is a number with the last
		 * two digits > 50. If it is, add 1 to the result. Do the opposite for negative values.
		 * 
		 *  Examples:
		 *  167 ===> 67 is larger than 50	==> result is 2 (167/100 + 1)
		 *  410 ===> 10 is smaller than 50	==> result is 4 (410/100)
		 *  -310 ==> -10 is larger than -50 ==>	result is -3 (-310/100)
		 *  -193 ==> -93 is smaller than -50 =>	result is -2 (-193/100 - 1) 
		 */
		if (servoValue > 0 && servoCalculated % 100 >= 50)
			roundCorrection = 1;
		else if (servoCalculated % 100 < -50)
			roundCorrection = -1;
				
		return (int)(((servoCalculated / 100) + servoMid) + roundCorrection);		
	}

	/*
	 * If vx=0 and vy=0, and omega=100 the robot will rotate
	 * counterclockwise at roughly its maximum speed.  If vx=0 and
	 * vy=100 and omega=0, the robot will go "north" at roughly
	 * its maximum speed.
	 */
	public void vectorDrive(int vx, int vy, int omega)
	{
		int bb = 72 * omega;
		int bx = 50 * vx;
		int by = 87 * vy;

		servoDirect.setPosition(scaleToServoSpeed(((-100 * vx) + bb) / 100), 0);
		servoDirect.setPosition(scaleToServoSpeed(((bx - by) + bb) / 100), 1);
		servoDirect.setPosition(scaleToServoSpeed(((bx + by) + bb) / 100), 2);
	}

	/**
	 * Tone generates a fixed tone.
	 * Since we do not have a frequency generator, the tone is given
	 * here by 0 to stop and 1 thru 9 to generate a scale
	 *
	 * @param toneId the note to play
	 */
	public void tone(int toneId)
	{
		tonePlayer.playTone(toneId);
	}
	
	/**
	 * reportState shows the current calculated states
	 * when the reporting is turned on
	 * This is used to communicate with the simulator
	 * when the robot is online.
	 * The code underneath is not yet functional.
	 */
	public void reportState () {
		String s = "";
		int v1 = 0;
		int v2 = 0;
		if (reporting > 0) {
			if (robot.getState() == robot.STATE_CALIBRATE)
				s += " V1" + v1 + " V2=" + v2;
			if (s != "")
				System.out.println(s);
		}
	}
	
	public void reportState (int level) {
		reporting = level;
	}
	
	/**
	 * setState is used to dynamically alter the robot state
	 * from a hebavior.
	 *
	 * @param state - the new state of the robot.
	 */
	public void setState(int state)
	{
		robot.setState(state);
	}


	
}
