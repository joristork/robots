/*
 * Created on Aug 13, 2004 Copyright: (c) 2006 Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 * 
 * Controller for an OmniDirectional Robot Base Provides the basic functionality
 * for every joBot. When creating a new application, make sure an instance of
 * this class is created, that the robot can inherit from. Ver 0.5 - 07-09-2004
 * Modified wheel base
 */

package javaBot.tests;

/**
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */

import com.muvium.io.PortIO;

public class JobotTestController
{
	private static final byte	LED_RED	= (byte) 0x01;
	private static final byte	LED_YEL	= (byte) 0x02;
	private static final byte	LED_GRN	= (byte) 0x03;
	private int					servoMax;
	private int					servoMid;
	private int[]				servos	= new int[3];
	private int[]				sensors	= new int[3];

	public JobotTestController(int max)
	{
		servoMax = max;
		servoMid = servoMax >> 1;
		servos[0] = 0;
		servos[1] = 0;
		servos[2] = 0;
	}

	public void heartBeat()
	{
		PortIO.toggleOutputPin(LED_RED, PortIO.PORTB);
	}

	public void setStatusLeds(boolean red, boolean green, boolean blue)
	{
		PortIO.setOutputPin(!red, LED_RED, PortIO.PORTB);
		PortIO.setOutputPin(!green, LED_YEL, PortIO.PORTB);
		PortIO.setOutputPin(!blue, LED_GRN, PortIO.PORTB);
	}

	public int getServo(int servo)
	{
		return servos[servo];
	}

	public void setServo(int value, int servo)
	{
		// Set servo value but do not scale
		servos[servo] = max(value);
	}

	public int getSensor(int sensor)
	{
		return sensors[sensor];
	}

	/*
	 * Drive directly drives the servo's
	 */
	public void drive(int x, int y, int z)
	{
		setServo(scale(x), 0);
		setServo(scale(y), 1);
		setServo(scale(z), 2);
	}

	public int scale(int val)
	{
		int value = val;
		if (val >= 100)
			value = 99;
		if (val <= -100)
			value = -99;
		return value * servoMid / 100 + servoMid;
	}

	public int max(int val)
	{
		int value = val;
		if (val >= 100)
			value = 100;
		if (val <= -100)
			value = -100;
		return value;
	}

	/*
	 * If vx=0 and vy=0, and omega=100 the robot will rotate counterclockwise at
	 * roughly its maximum speed. If vx=0 and vy=100 and omega=0, the robot will
	 * go "north" at roughly its maximum speed.
	 */
	public void vectorDrive(int vx, int vy, int omega)
	{
		int bb = 72 * omega;
		int bx = 50 * vx;
		int by = 87 * vy;

		setServo(((-100 * vx) + bb) / 100, 0);
		setServo(((bx - by) + bb) / 100, 1);
		setServo(((bx + by) + bb) / 100, 2);
	}

	/*
	 * This function converts a "velocity" into an absolute servo position which
	 * roughly corresponds to that velocity. (Servos are not linear.) The
	 * current version is not using this facility yet
	 */
	public int velToPos(int omega)
	{
		int p = omega;
		p = ((40 * omega) / 100) - 13;
		p = ((p * omega) / 100) + 22;
		p = ((p * omega) / 100) + 75;
		return p;
	}
}