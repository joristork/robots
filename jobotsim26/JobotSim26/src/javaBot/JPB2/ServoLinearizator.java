/*
 * Created on Mar 6, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * The servos on JoBot are modified to allow speed and direction control.
 * Becahse of the hysteresis of the control loop, the behavior is not linear.
 * This routine linearizes the incoming value so the motor turns as fast
 * as we have indicated.
 * Please note that there are several different servo's in use and that each type
 * has its own characteristics.
 * Make sure you are using the proper version for your robot.
 * Currently the JoBot is using the Futaba S3003 servo.
 */
package javaBot.JPB2;

public class ServoLinearizator
{
	/**
	 * Conversion table for non linearity of servo motor
	 */
	private static int[] speedPowerTable = new int[] {
		1, 0, 
		4, 13,
		5, 25,
		8, 38,
		9, 50,
		10, 63,
		11, 75,
		14, 88,
		34, 100
	};
	
	private static int absolutePower (int power) {
		int absolutePower = power;
		if (power < 0) absolutePower = -power;
		return absolutePower;
	}
	
	/**
	 * Calculates from the power percentage the non-linear servo speed
	 * @param power Percentage between -100 to 100
	 * @return The non lineair speed of the servo motor
	 */
	public static int convertPowerToServoSpeed(int power)
	{
		int servoPowerIndex = 1;
		
		// Determine the index in the speedRotationsTable of the upper boundary of power
		while(speedPowerTable[servoPowerIndex] <= absolutePower(power) && servoPowerIndex < speedPowerTable.length-1)
			servoPowerIndex+=2;
		
		// Calculate between the values in the table using the upper and lower boundary
		int percentageAbove = 100 * (absolutePower(power) - speedPowerTable[servoPowerIndex - 2]) 
		/ (speedPowerTable[servoPowerIndex] - speedPowerTable[servoPowerIndex - 2]);
		
		// Interpolate
		int servoPower = ((100 - percentageAbove) * speedPowerTable[servoPowerIndex - 3] +
		                 percentageAbove * speedPowerTable[servoPowerIndex - 1]);
		
		/* Muvium controller can't use doubles, work-around for this below.
		 * We look if servoPower >= .50, for example 150, 250, 361, 399. If it is, the number
		 * would normally be rounded to 2, 3, 4, 4 respectively instead of 1, 2, 3, 3. */
		int roundingCorrection = servoPower % 100 >= 50 ? 1 : 0;
		
		// For negative and positive powers the linearity is the same
		if (power < 0)
			return -(servoPower / 100) + roundingCorrection;
		else
			return (servoPower / 100) + roundingCorrection;
	}
	
	/**
	 * This function converts the given power ratio to a speed using the speed table
	 * @param speed	-	desired speed in % between -100 and +100
	 * @return The lineair power of the servo motor
	 */
	public static int convertServoSpeedToPower(int speed)
	{
		int absolutePower = absolutePower(speed);
		int servoPowerIndex = 2;
		
		if(absolutePower < speedPowerTable[0])
			absolutePower = speedPowerTable[0];
		
		// Determine the index in the speedRotationsTable of the upper boundary of power
		while(speedPowerTable[servoPowerIndex] <= absolutePower && servoPowerIndex < speedPowerTable.length-2)
			servoPowerIndex+=2;
		
		// Calculate between the values in the table using the upper and lower boundary
		int percentageAbove = 100 * (absolutePower - speedPowerTable[servoPowerIndex - 2]) 
		/ (speedPowerTable[servoPowerIndex] - speedPowerTable[servoPowerIndex - 2]);
		
		// Interpolate, for negative and positive powers the linearity is the same
		if (speed < 0)
			return -((100 - percentageAbove) * speedPowerTable[servoPowerIndex - 1] +
					percentageAbove * speedPowerTable[servoPowerIndex + 1]) / 100;
		else
			return ((100 - percentageAbove) * speedPowerTable[servoPowerIndex - 1] +
					percentageAbove * speedPowerTable[servoPowerIndex + 1]) / 100;
	}
}
