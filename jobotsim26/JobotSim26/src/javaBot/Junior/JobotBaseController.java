/*
 * Created on Aug 13, 2004 Controller for an OmniDirectional Robot Base Provides
 * the basic functionality for every joBot. When creating a new application,
 * make sure an instance of this class is created, that the robot can inherit
 * from. Ver 0.5 - 07-09-2004 Modified wheel base
 *
 */
// ToD0: Simulate tacho counts on wheels
package javaBot.Junior;

import javaBot.UVM.ServoLinearizator;
import com.muvium.apt.ADCReader;
import com.muvium.apt.MultiServoController;
import com.muvium.apt.PeripheralFactory;
import com.muvium.io.PortIO;

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
public class JobotBaseController
{
	public static final byte		LED_RED		= (byte) 0x04;
	public static final byte		LED_YELLOW	= (byte) 0x05;
	public static final byte		LED_GREEN	= (byte) 0x06;
	public static final byte		LED_BLUE	= (byte) 0x07;

	private ToneGenerator			tonePlayer;
	private MultiServoController	servoDirect;
	private int						servoMax;
	private int						servoMid;
	private ADCReader				adc;
	private UVMDemo					robot;
	private int 					reporting = 0;
	
	private boolean wl = false;
	private boolean wr = false;
	private int wlc = 0;
	private int wrc = 0;

	/**
	 * Creates a new JobotJrBaseController object.
	 *
	 * @param factory TODO PARAM: DOCUMENT ME!
	 * @param theDemo TODO PARAM: DOCUMENT ME!
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
		robot = theDemo;
		tonePlayer = new ToneGenerator(factory);

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
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void heartBeat()
	{
		PortIO.toggleOutputPin(LED_RED, PortIO.PORTE);
	}

	public void setStatusLeds(boolean yel, boolean grn, boolean blu)
	{
		PortIO.setOutputPin(yel, LED_YELLOW, PortIO.PORTE);
		PortIO.setOutputPin(grn, LED_GREEN, PortIO.PORTE);
		PortIO.setOutputPin(blu, LED_BLUE, PortIO.PORTE);
	}

	public void setStatusLeds(boolean red, boolean yel, boolean grn, boolean blu)
	{
		PortIO.setOutputPin(red, LED_RED, PortIO.PORTE);
		PortIO.setOutputPin(yel, LED_YELLOW, PortIO.PORTE);
		PortIO.setOutputPin(grn, LED_GREEN, PortIO.PORTE);
		PortIO.setOutputPin(blu, LED_BLUE, PortIO.PORTE);
	}

	public void setLed(int led, boolean state)
	{
		PortIO.setOutputPin(state, led, PortIO.PORTE);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param value TODO PARAM: param description
	 * @param servo TODO PARAM: param description
	 */
	public void setServo(int value, int servo)
	{
		servoDirect.setPosition(value, servo);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getSensor(int sensor)
	{
		adc.setChannel(sensor);
		int i = adc.getSample();
		return i;
	}

	/**
	 * getSwitch
	 * Is only used on digital inputs.
	 * Currently no digital input sensors exist.
	 */
	
	public int getSwitch(int sw) {
		boolean on;
		on = PortIO.getInputPin(sw, PortIO.PORTD);
		if (on)
			return 1;
		else
			return 0;
	}

	/*
	 * Checks if the wheel encoder is going from black to white If so, bump the
	 * counter.
	 */
	public void getTacho()
	{
		if (getSensor(6) > 300)
		{
			if (!wl)
			{
				wl = true;
				wlc++;
			}
		}
		else
		{
			wl = false;
		}

		if (robot.getCurrentState() == UVMDemo.STATE_CALIBRATE)
		{
			setLed(LED_RED, wl);
		}

		if (getSensor(7) > 200)
		{
			if (!wr)
			{
				wr = true;
				wrc++;
			}
		}
		else
		{
			wr = false;
		}

		if (robot.getCurrentState() == UVMDemo.STATE_CALIBRATE)
		{
			setLed(LED_BLUE, wr);
		}
	}

	//	When we reach the second mark, calculate the speed per wheel
	public void calcTacho()
	{
		wlc = 0; // Reset the wheel counters
		wrc = 0;
	}

	/*
	 * Drive directly drives the servo's
	 */
	public void drive(int x, int y)
	{
		servoDirect.setPosition(scaleToServoSpeed(x), 0);
		servoDirect.setPosition(scaleToServoSpeed(0-y), 1);
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

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param toneId TODO PARAM: param description
	 */
	public void tone(int toneId)
	{
		tonePlayer.playTone(toneId);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param state TODO PARAM: param description
	 */
	public void setState(int state)
	{
		robot.setState(state);
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
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getWlc()
	{
		return wlc;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getWrc()
	{
		return wrc;
	}
	
}
