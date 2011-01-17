
package javaBot.UVMRescue;

import javaBot.UVM.ServoLinearizator;

import com.muvium.apt.ADCReader;
import com.muvium.apt.MultiServoController;
import com.muvium.apt.PeripheralFactory;
import com.muvium.driver.opticalMouse.Agilent2051;
import com.muvium.io.PortIO;

/**
 * Controller for an OmniDirectional Robot Base
 * Provides the basic functionality for every joBot.
 * When creating a new application, make sure an instance
 * of this class is created, that the robot can inherit from.
 */

// ToDo: Implement functions outside controller for simulator use only

public class JobotBaseController
{
	private static final byte	ledRED	= (byte) 0x01;
	private static final byte	ledYEL	= (byte) 0x02;
	private static final byte	ledGRN	= (byte) 0x03;
	int[]						calColor = new int[] {80,200,500,800};
	public static final int    	BLACK = 0;
	public static final int    	GREEN = 1;
	public static final int    	YELLOW = 2;
	public static final int    	WHITE = 3;
	int							reporting = 0;      // Turn reporting on/off
	int							color = -1;			// Track the color
	int							pos = 0;			// Track the position
	
	MultiServoController		servoDirect;
	int							servoMax;
	int							servoMid;
	ADCReader					adc;
	Agilent2051					mouse;
	
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
	
//  Setup of controller for unit test only
	public JobotBaseController() {		
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

		return adc.getSample();
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
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sw TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getSwitch(int sw)
	{
		boolean on;
		on = PortIO.getInputPin(sw, PortIO.PORTD);

		if (on)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String getCoordinates()
	{
		int x = mouse.getDX();
		int y = mouse.getDY();

		System.out.print("x = ");
		System.out.print(x);
		System.out.print(" y = ");
		System.out.print(y);
		System.out.print("\r\n");

		return x + " " + y;
	}

	//	public int velToPosNew(int value) {
	//		int p = value;
	//		p= ((40 * value) / 100) - 13;
	//		p= ((p * value) / 100) + 22;
	//		p= ((p * value) / 100) + 128;
	//		p = scale(p);
	//		return p;
	//	}
	
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
	
	public int getRangeValue (int index, int range) {
		return calColor[index] - range;
	}
	
	public int getCalibratedValue(int index) {
		return calColor[index];	}
	
	public boolean isColor(int value, int index) {
		int aColor = getColor(value);
		return aColor == index;
	}


	/**
	 * getColor determines the color range based on the given value
	 * calColor = new int[] {80,200,500,800};
	 * Unknown 	- < 0
	 * Black	- 0 - 179
	 * Green	- 180 - 479
	 * Yellow	- 480 - 779
	 * White	- >= 780
	 */
	public int getColor (int value) {
		color = -1;
		if (value < 0) 
			color = -1;
		if (value >= 0 && value < getRangeValue(GREEN, 20))
			color = BLACK;
		if (value >= getRangeValue(GREEN, 20) && value < getRangeValue(YELLOW, 20))
			color = GREEN;
		if (value >= getRangeValue(YELLOW, 20) && value < getRangeValue(WHITE, 20))
			color = YELLOW;
		if (value >= getRangeValue(WHITE, 20))
			color = WHITE;
		return color;
	}
	
	public String getColorName (int value) {
		String sColor = "";
		int aColor = getColor(value);
		switch (aColor) {
			case BLACK:
				sColor = "Black";
				break;
			case GREEN:
				sColor = "Green";
				break;
			case YELLOW:
				sColor = "Yellow";
				break;
			case WHITE:
				sColor = "White";
				break;
			default:
				sColor = "Unknown";
				break;
		}
		return sColor;
	}
	
	/**
	 * reportState shows the current calculated states
	 * when the reporting is turned on
	 *
	 */
	public void reportState () {
		String s = "";
		String sColor = getColorName(color);
		if (reporting > 0) {
			s += " Color=" + sColor;
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
