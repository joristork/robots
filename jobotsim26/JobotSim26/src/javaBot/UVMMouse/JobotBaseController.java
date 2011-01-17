/*
 * Created on Aug 13, 2004
 * Controller for an OmniDirectional Robot Base
 * Provides the basic functionality for every joBot.
 * When creating a new application, make sure an instance
 * of this class is created, that the robot can inherit from.
 * Ver 0.5 - 07-09-2004 Modified wheel base
 *
 */
package javaBot.UVMMouse;

import javaBot.Debug;
import javaBot.JPB2.ServoLinearizator;
import com.muvium.apt.ADCReader;
import com.muvium.apt.MultiServoController;
import com.muvium.apt.PeripheralFactory;
import com.muvium.driver.opticalMouse.Agilent2051;
import com.muvium.io.PortIO;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 * 
 * @version $Revision$ last changed 20-02-2006
 * 
 * TODO CLASS: DOCUMENT ME!
 */
public class JobotBaseController {
	private static final byte ledRED = (byte) 0x04;
	private static final byte ledYEL = (byte) 0x05;
	private static final byte ledGRN = (byte) 0x06;
	private static final byte ledBLU = (byte) 0x07;
	ToneGenerator tonePlayer;
	MultiServoController servoDirect;
	int servoMax;
	int servoMid;
	int reporting = 0;
	ADCReader adc;
	Agilent2051 mouse;

	/**
	 * Creates a new JobotBaseController object.
	 * 
	 * @param factory
	 *            TODO PARAM: DOCUMENT ME!
	 */
	public JobotBaseController(PeripheralFactory factory) {
		servoDirect = factory
				.createMultiServoController(MultiServoController.IMPLEMENTATION_DIRECT);
		servoMax = servoDirect.getMaxPosition();
		servoMid = servoMax >> 1;
		System.out.print("Servo=");
		System.out.println(servoMax);
		adc = factory.createADCReader(0, ADCReader.READ_INT);
		mouse = new Agilent2051();
		tonePlayer = new ToneGenerator(factory);
		try {
			servoDirect.start();
		} catch (Exception e) {
			System.out.println("Failed!");
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void heartBeat() {
		PortIO.toggleOutputPin(ledRED, PortIO.PORTE);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 * 
	 * @param red
	 *            TODO PARAM: param description
	 * @param yel
	 *            TODO PARAM: param description
	 * @param grn
	 *            TODO PARAM: param description
	 */
	public void setStatusLeds(boolean red, boolean yel, boolean grn) {
		PortIO.setOutputPin(red, ledRED, PortIO.PORTE);
		PortIO.setOutputPin(yel, ledYEL, PortIO.PORTE);
		PortIO.setOutputPin(grn, ledGRN, PortIO.PORTE);
		PortIO.setOutputPin(false, ledBLU, PortIO.PORTE);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 * 
	 * @param red
	 *            TODO PARAM: param description
	 * @param yel
	 *            TODO PARAM: param description
	 * @param grn
	 *            TODO PARAM: param description
	 * @param blu
	 *            TODO PARAM: param description
	 */
	public void setStatusLeds(boolean red, boolean yel, boolean grn, boolean blu) {
		PortIO.setOutputPin(red, ledRED, PortIO.PORTE);
		PortIO.setOutputPin(yel, ledYEL, PortIO.PORTE);
		PortIO.setOutputPin(grn, ledGRN, PortIO.PORTE);
		PortIO.setOutputPin(blu, ledBLU, PortIO.PORTE);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 * 
	 * @param value
	 *            TODO PARAM: param description
	 * @param servo
	 *            TODO PARAM: param description
	 */
	public void setServo(int value, int servo) {
		servoDirect.setPosition(value, servo);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 * 
	 * @param sensor
	 *            TODO PARAM: param description
	 * 
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getSensor(int sensor) {
		adc.setChannel(sensor);
		int i = adc.getSample();
		if (i == 1023) {
			i = 0;
		}
		return i;
	}

	/*
	 * Drive directly drives the servo's
	 */
	public void drive(int x, int y, int z) {
		servoDirect.setPosition(scaleToServoSpeed(x), 0);
		servoDirect.setPosition(scaleToServoSpeed(y), 1);
		servoDirect.setPosition(scaleToServoSpeed(z), 2);
	}

	/**
	 * Checks the boundary's and makes sure the preconditions are met.
	 * 
	 * @param val
	 *            Percentage between -100 to 100
	 * @return The non lineair speed of the servo motor
	 */
	public int scaleToServoSpeed(int val) {
		int value = val;
		if (val >= 100) {
			value = 99;
		}
		if (val <= -100) {
			value = -99;
		}
		int servoValue = ServoLinearizator.convertPowerToServoSpeed(value);
		int servoCalculated = servoValue * servoMid;
		int roundCorrection = 0;
		/*
		 * Because the muvium controller cannot use doubles, the formula in the
		 * return statement of this method, servoCalculated / 100 can result in
		 * a double value, losing the rounding precision. To correct this, we
		 * see if the servoCalculated value is a number with the last two digits >
		 * 50. If it is, add 1 to the result. Do the opposite for negative
		 * values.
		 * 
		 * Examples: 167 ===> 67 is larger than 50 ==> result is 2 (167/100 + 1)
		 * 410 ===> 10 is smaller than 50 ==> result is 4 (410/100) - 310 ==>
		 * -10 is larger than -50 ==> result is -3 (-310/100) -193 ==> -93 is
		 * smaller than -50 => result is -2 (-193/100 - 1)
		 */
		if (servoValue > 0 && servoCalculated % 100 >= 50)
			roundCorrection = 1;
		else if (servoCalculated % 100 < -50)
			roundCorrection = -1;
		return (int) (((servoCalculated / 100) + servoMid) + roundCorrection);
	}

	/*
	 * If vx=0 and vy=0, and omega=100 the robot will rotate counterclockwise at
	 * 32 roughly its maximum speed. If vx=0 and vy=100 and omega=0, the robot
	 * will go "north" at roughly its maximum speed.
	 */
	public void vectorDrive(int vx, int vy, int omega) {
		int bb = 72 * omega;
		int bx = 50 * vx;
		int by = 87 * vy;
		servoDirect.setPosition(scaleToServoSpeed(((-100 * vx) + bb) / 100), 0);
		servoDirect.setPosition(scaleToServoSpeed(((bx - by) + bb) / 100), 1);
		servoDirect.setPosition(scaleToServoSpeed(((bx + by) + bb) / 100), 2);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 * 
	 * @param toneId
	 *            TODO PARAM: param description
	 */
	public void tone(int toneId) {
		tonePlayer.playTone(toneId);
	}

	/** 
	 * Do a pixeldump.
	 * 
	 * @return the pixeldump values
	 */
	public byte[] readPixels() {
		byte[] pixels = new byte[256];
		mouse.readPixels(pixels);
		return pixels;
	}
	
	public void pixelDump() {
		resetSensor(0x90);
		mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 0x19); //Pixel Dump 800 dpi
		for (int i = 0; i < 256; i++)
		{
			int address = mouse.getRegister(Agilent2051.A2051_DATA_OUT_UPPER);
			int data = mouse.getRegister(Agilent2051.A2051_DATA_OUT_LOWER);
			while ((data & 0x80) != 0)
			{
				data = mouse.getRegister(Agilent2051.A2051_DATA_OUT_LOWER);
			}
			if (i < 10) {
				String s = String.valueOf(address) + "," + String.valueOf(data) + ",";
				System.out.print(s);
			}
		}
		System.out.println();
		mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 0x10); // End of dump      
	}
	
	public void mouseTrack() {
		int x = 0;
		int y = 0;
//		if (mouse.hasMoved())
//		{
			x = mouse.getDX();
			y = mouse.getDY();
			System.out.print("X=");
			System.out.print(x);
			System.out.print(",Y=");
			System.out.print(y);
			System.out.print("\r\n");
//		}
	}
		
	/**
	 * Reset the configuration register for the
	 * Agilent 2051.
	 * Note that pin RB3 is used to connect the PD signal
	 * which causes a hard reset
	 */
	public void resetSensor(int status) {
		resetSensor();
		mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, status);
	}	
	
	public void resetSensor() {
		for (int i=0; i<10000; i++) {
			PortIO.setOutputPin(true, 3, PortIO.PORTB);
		}
		for (int i=0; i<100; i++) {
			PortIO.setOutputPin(false, 3, PortIO.PORTB);
		}
	}	
	
	public int getRegister(byte reg) {
		return mouse.getRegister(reg);
	}
	
	public void writeRegister(byte reg, int value) {
		mouse.writeReg(reg, value);
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
//			if (robot.getState() == robot.STATE_CALIBRATE)
				s += " V1" + v1 + " V2=" + v2;
			if (s != "")
				System.out.println(s);
		}
	}
	
	public void reportState (int level) {
		reporting = level;
	}
	
	public void sleep (int ms) {
		try
		{
			Thread.sleep(1000);
		}
		catch (Exception e)
		{}
	}
}
