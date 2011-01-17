/*
 * Created on Aug 13, 2004
 * Controller for an OmniDirectional Robot Base
 * Provides the basic functionality for every joBot.
 * When creating a new application, make sure an instance
 * of this class is created, that the robot can inherit from.
 *  Ver 0.5 - 07-09-2004 Modified wheel base
 *
 */
package javaBot.agents;

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
	private static final byte	ledRED	= (byte) 0x01;
	private static final byte	ledYEL	= (byte) 0x02;
	private static final byte	ledGRN	= (byte) 0x03;
	MultiServoController		servoDirect;
	int							servoMax;
	int							servoMid;
	ADCReader					adc;

	/**
	 * Creates a new JobotBaseController object.
	 *
	 * @param factory TODO PARAM: DOCUMENT ME!
	 */
	public JobotBaseController(PeripheralFactory factory)
	{
		servoDirect = factory
				.createMultiServoController(MultiServoController.IMPLEMENTATION_DIRECT);
		servoMax = servoDirect.getMaxPosition();
		servoMid = servoMax >> 1;
		adc = factory.createADCReader(0, ADCReader.READ_INT);

		try
		{
			servoDirect.start();
		}
		catch (Exception e)
		{
			System.out.println("Failed!");
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void heartBeat()
	{
		PortIO.toggleOutputPin(ledRED, PortIO.PORTB);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param red TODO PARAM: param description
	 * @param yel TODO PARAM: param description
	 * @param grn TODO PARAM: param description
	 */
	public void setStatusLeds(boolean red, boolean yel, boolean grn)
	{
		PortIO.setOutputPin(!red, ledRED, PortIO.PORTB);
		PortIO.setOutputPin(!yel, ledYEL, PortIO.PORTB);
		PortIO.setOutputPin(!grn, ledGRN, PortIO.PORTB);
	}

	//	public int getServo(int servo) {
	//		return servoDirect.getPosition(servo);
	//	}
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

		return adc.getSample();
	}

	/*
	 * Drive directly drives the servo's
	 */
	public void drive(int x, int y, int z)
	{
		servoDirect.setPosition(scale(x), 0);
		servoDirect.setPosition(scale(y), 1);
		servoDirect.setPosition(scale(z), 2);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param val TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int scale(int val)
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

		return ((value * servoMid) / 100) + servoMid;
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

		servoDirect.setPosition(velToPos(((-100 * vx) + bb) / 100), 0);
		servoDirect.setPosition(velToPos(((bx - by) + bb) / 100), 1);
		servoDirect.setPosition(velToPos(((bx + by) + bb) / 100), 2);
	}

	/*
	 * This function converts a "velocity" into an absolute
	 * servo position which roughly corresponds to that
	 * velocity.  (Servos are not linear.)
	 * The current version is not using this facility
	 */
	public int velToPos(int value)
	{
		int p = value;
		p = scale(p);

		return p;
	}

	//	public int velToPosNew(int value) {
	//		int p = value;
	//		p= ((40 * value) / 100) - 13;
	//		p= ((p * value) / 100) + 22;
	//		p= ((p * value) / 100) + 128;
	//		p = scale(p);
	//		return p;
	//	}
}
