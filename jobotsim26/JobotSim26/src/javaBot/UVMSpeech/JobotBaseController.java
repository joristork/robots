/*
 * Created on Aug 13, 2004
 * Controller for an OmniDirectional Robot Base
 * Provides the basic functionality for every joBot.
 * When creating a new application, make sure an instance
 * of this class is created, that the robot can inherit from.
 *  Ver 0.5 - 07-09-2004 Modified wheel base
 *
 */
package javaBot.UVMSpeech;

import com.muvium.apt.ADCReader;
import com.muvium.apt.MultiServoController;
import com.muvium.apt.PeripheralFactory;
import com.muvium.io.PortIO;
import com.muvium.driver.opticalMouse.Agilent2051;

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
	private static final byte	ledRED	= (byte) 0x04;
	private static final byte	ledYEL	= (byte) 0x05;
	private static final byte	ledGRN	= (byte) 0x06;
	private static final byte	ledBLU	= (byte) 0x07;
	ToneGenerator				tonePlayer;
	MultiServoController		servoDirect;
	int							servoMax;
	int							servoMid;
	private UVMDemo				robot;
	ADCReader					adc;
	Agilent2051 				mouse;
	int							prev;				// Previous position	
	int							fs;					// Field Sensor
	int							calSpeed = 0;		// Calibrated speed
	// Calibrated color 0=black, 1=green, 2=yellow, 3=white	
	int[]						calColor = new int[] {10,512,768,1020};
	public static final int    	BLACK = 0;
	public static final int    	GREEN = 1;
	public static final int    	YELLOW = 2;
	public static final int    	WHITE = 3;
	
//  Public variables
	int							speed = 0;			// Current speed
	int							pos = 0;			// Current position on field
	int							color = 0;				
	int							reporting = 1;      // Turn reporting on/off

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
		System.out.print("Servo=");
		System.out.println(servoMax);
		adc = factory.createADCReader(0, ADCReader.READ_INT);
		robot = theDemo;
		tonePlayer = new ToneGenerator(factory);
		mouse = new Agilent2051();

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
		PortIO.toggleOutputPin(ledRED, PortIO.PORTE);
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
		PortIO.setOutputPin(red, ledRED, PortIO.PORTE);
		PortIO.setOutputPin(yel, ledYEL, PortIO.PORTE);
		PortIO.setOutputPin(grn, ledGRN, PortIO.PORTE);
		PortIO.setOutputPin(false, ledBLU, PortIO.PORTE);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param red TODO PARAM: param description
	 * @param yel TODO PARAM: param description
	 * @param grn TODO PARAM: param description
	 * @param blu TODO PARAM: param description
	 */
	public void setStatusLeds(boolean red, boolean yel, boolean grn, boolean blu)
	{
		PortIO.setOutputPin(red, ledRED, PortIO.PORTE);
		PortIO.setOutputPin(yel, ledYEL, PortIO.PORTE);
		PortIO.setOutputPin(grn, ledGRN, PortIO.PORTE);
		PortIO.setOutputPin(blu, ledBLU, PortIO.PORTE);
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
		int by = 85 * vy;

		servoDirect.setPosition(scaleToServoSpeed(((-100 * vx) + bb) / 100), 0);
		servoDirect.setPosition(scaleToServoSpeed(((bx - by) + bb) / 100), 1);
		servoDirect.setPosition(scaleToServoSpeed(((bx + by) + bb) / 100), 2);
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
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param toneId TODO PARAM: param description
	 */
	public void tone(int toneId)
	{
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
//		resetSensor();
		mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 25);           //Pixel Dump 800 dpi
		for (int i = 0; i < 256; i++)
		{
			int address = mouse.getRegister(Agilent2051.A2051_DATA_OUT_UPPER);
			int data = mouse.getRegister(Agilent2051.A2051_DATA_OUT_LOWER);
			int inc = 0;
			while ((data & 0x80) != 0)
			{
				data = mouse.getRegister(Agilent2051.A2051_DATA_OUT_LOWER);
				inc++;
			}
			if (i < 10) {
				String s = String.valueOf(address) + "," + String.valueOf(data) + ",";
				System.out.print(s);
			}
		}
		System.out.println();
	}
	
	public void mouseTrack() {
		int x = 0;
		int y = 0;
//		if (mouse.hasMoved())
//		{
			x += mouse.getDX();
			y += mouse.getDY();

			System.out.print("x = ");
			System.out.print(x);
			System.out.print(" y = ");
			System.out.print(y);
			System.out.print("\r\n");
//		}
	}
		
	/**
	 * Reset the configuration register for the
	 * Agilent 2051.
	 *
	 */
	public void resetSensor() {
		mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 0x19);
	}	
	
	/**
	 * GetState
	 * This function montors the robot's state by checking the
	 * position, direction of travel, speed etc. It determines:
	 * speed - current speed in grayscale increments.
	 *         a positive value is towards white
	 *         a negative value is towards black
	 *         the absolute value is the speed
	 * pos   - current position on the field in %
	 * 		   0 = black
	 *         100 = white
	 */
	
	public void calcState() {	
		fs = getSensor(5);
		calcDirection();
		calcPosition();
		calcColor();
		calcStates();	
		reportState();
	}
	
	/**
	 * Constantly monitor in what direction the robot is driving
	 * This is an example of determining both direction and speed
	 * from the field sensor
	 */
	private void calcDirection() {
		speed = prev - fs;
		prev = fs;	
		if (speed != 0)
			pos = (int) (fs - calColor[BLACK]) * 100 / calColor[WHITE];
	}
	
	/**
	 * CalcPosition calculates the robot's current position
	 * and orientation, based on the grayscale.
	 * Position is calculated in a percentage of field length
	 * X = current position in gray scale
	 * Y = estimated position using heuristics
	 * Z = orientation based on direction of travel
	 */
	
	private void calcPosition() {
		
	}
	
	/**
	 * Using the reading of the grayscale of fieldsensor
	 * a determination is made of the current color under 
	 * the field sensor
	 * This is done using the calibration values stored
	 * during initial calibration
	 *
	 */
	private void calcColor() {
		
	}
	
	/**
	 * calcStates will estimate the robot's states 
	 * It will try to determine the following
	 * heading - calculated based on motorspeed versus real speed
	 * stuck   - when motor speed is nonzero and real speed is zero
	 * wall    - when a wall is detected
	 * ball    - when ball is spotted
	 */
	private void calcStates() {
		
	}
	
	/**
	 * setCalibratedValue takes a sensor reading and 
	 * classifies this as a certain color based on previous readings
	 * We first need to establish black and white
	 * 0 = black
	 * 1 = green
	 * 2 = yellow
	 * 3 = white
	 */
	public void setCalibratedValue (int index, int value) {
		calColor[index] = value;
	}
	
	/**
	 * reportState shows the current calculated states
	 * when the reporting is turned on
	 *
	 */
	public void reportState () {
		String s = "";
		if (reporting > 0) {
			if (speed > 1 || speed < -1)
				s += " Speed=" + speed + " Pos=" + pos;
			if (s != "")
				System.out.println(s);
		}
	}
	
	public void reportState (int level) {
		reporting = level;
	}
	
	public int abs(int value) {
		if (value < 0)
			return 0 - value;
		return value;
	}
	
	public int max(int f1, int f2) {
		if (f1 > f2)
			return f1;
		return f2;
	}
	
	
}
