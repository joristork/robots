package javaBot;

import javaBot.UVM.ServoLinearizator;

import org.openVBB.robotKit.interfaces.ServoPulseListener;

/**
 * Ver 0.1 - 03-07-2004 -     Included Name for servo display
 */
/**
 * THe openVBB simulator generates actual signals from the uVM controller and
 * so we must look from the perspective of the signals that the motor
 * receives.  The motor contains two calibrations  Signal => Servo Position
 * Signal => Motor Velocity  In the RobotKit these components are abstracted
 * but for this model we join them into the JoBotServoMotor class  The JoBot
 * simulator converts ServoPostion => Motor Velocity.
 * Please note that we need to know the servo type.
 * Default is the S3003, curently only this and the S148 are supported
 *
 * @author James Caska
 */
public class JoBotServoMotor extends Actor implements ServoPulseListener
{
	private static final double	DEFAULT_MIN_PULSE	= 1.0 / 1000;			//default = 1 milliseconds
	private static final double	DEFAULT_MAX_PULSE	= 2.0 / 1000;			//default = 2 milliseconds
	private static final double	DEFAULT_MAX_SPEED	= 2 * Math.PI;			//default = 1 rotation per second
	private static final int	DEFAULT_RESOLUTION	= 256;					//default = 256 steps ( 8 bits )
	private double				maxPulse			= DEFAULT_MAX_PULSE;
	private double				maxSpeed			= DEFAULT_MAX_SPEED;
	private double				minPulse			= DEFAULT_MIN_PULSE;
	private double				servoPulseWidth;
	private int					resolution			= DEFAULT_RESOLUTION;
	private int					servoPosition;
	private String				servoType			= "S3003";

	/**
	 * Creates a new JoBotServoMotor object.
	 *
	 * @param initSpeed initial speed
	 * @param initResolution initial resolution
	 * @param initMinPulse intitial min pulse
	 * @param initMaxPulse initial max pulse
	 */
	public JoBotServoMotor(double initSpeed, int initResolution, 
			               double initMinPulse, double initMaxPulse,
			               String initType)
	{
		setValue(initSpeed);
		resolution = initResolution;
		minPulse = initMinPulse;
		maxPulse = initMaxPulse;
		if (servoType.equalsIgnoreCase("S3003") ||
			servoType.equalsIgnoreCase("S148"))	
			servoType = initType;
		else
			servoType = "S3003";
		setName("Servo");
	}

	/**
	 * Reproduces the Servo Position from the Signal from the known parameters
	 * of the ServoSignal generator
	 *
	 * @param pulseWidth
	 * @param range
	 * @param minPosition
	 *
	 * @return servo position within the specified range.
	 */
	public int getServoPosition(double pulseWidth, int range, int minPosition)
	{
		if (pulseWidth <= minPulse)
		{
			return 0;
		}
		else if (pulseWidth >= maxPulse)
		{
			return resolution - 1;
		}
		else
		{
			double pulseRange = maxPulse - minPulse;
			double unit = pulseRange / range;
			int position = (int) ((pulseWidth - minPulse) / unit) + minPosition;
			return position;
		}
	}

	/**
	 * Gets the current servo position
	 *
	 * @return servo position
	 */
	public int getServoPosition()
	{
		return servoPosition;
	}

	/**
	 * Gets the calibrated velocity of the Servo Motor  Note: Ideally the
	 * ServoMotor speed would be calibrated against a very high resolution
	 * servo signal. This would lead to a larger range of use against a larger
	 * number of signal generators.   In this instance we scale the signal to
	 * the full calibration  range.
	 *
	 * @param pulseWidth
	 *
	 * @return
	 */
	public double getServoVelocity(double pulseWidth)
	{
		double speed = 0.0;
		
		if (pulseWidth <= minPulse)
		{
			speed = -maxSpeed;
		}
		else if (pulseWidth >= maxPulse)
		{
			speed = maxSpeed;
		}
		else
		{
			double pulseRange = maxPulse - minPulse;
			
			/* Below we calculate the percentage of the power output from the servo.
			 * The servo motor can create a power between minPulse and maxPulse (pulseRange).
			 * pulseWidth is the current power output.
			 * Using the pulseWidth and the pulseRange we can calculate the percentage of 
			 * power of the servo motor.
			 */
			double powerPercentage = (((pulseWidth - minPulse) / (0.5 * pulseRange)) - 1) * 100;
			
			/* The speed the servo motor generates is not lineair to the powerPercentage 
			 * Therefore we calculate the percentage of speed in reality using the ServoLineairizator
			 */
			int lineairPercentage = ServoLinearizator.convertServoSpeedToPower((int)Math.round(powerPercentage));
			speed = (lineairPercentage * maxSpeed) / 100;
		}		
		return speed;
	}

	/**
	 * Gets the current servo velocity
	 *
	 * @return servo velocity
	 * TODO: this function is never used?!
	 */
	public double getServoVelocity()
	{
		return getValue();
	}

	/**
	 * @return The speed in percentage.
	 */
	public double getPercentageSpeed()
	{
		double value = super.getValue();
		value = (value / DEFAULT_MAX_SPEED ) * 100.0;
		
		return Math.abs( value );
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pulseWidth
	 * @param channel
	 */
	public void servoPulseChanged(double pulseWidth, int channel)
	{	
		servoPulseWidth = pulseWidth;
		servoPosition = getServoPosition(pulseWidth, 200, -100);
		setValue(getServoVelocity(pulseWidth));	
	}
	
	/**l
	 * Used for unit-test PercentageSpeedTest.java
	 * 
	 * @return The max speed of this servo
	 */
	public double getMaxSpeed()
	{
		return (DEFAULT_MAX_SPEED);
	}
}
