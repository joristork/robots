/*
 * Created on Mar 6, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.Junior.Rescue;

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
	
	/**
	 * Calculates from the power percentage the non-linear servo speed
	 * @param power Percentage between -100 to 100
	 * @return The non lineair speed of the servo motor
	 */
	public static int convertPowerToServoSpeed(int power)
	{
		int absolutePower = Math.abs(power);
		int servoPowerIndex = 1;
		
		// Determine the index in the speedRotationsTable of the upper boundary of power
		while(speedPowerTable[servoPowerIndex] <= absolutePower && servoPowerIndex < speedPowerTable.length-1)
			servoPowerIndex+=2;
		
		// Calculate between the values in the table using the upper and lower boundary
		int percentageAbove = 100 
		                       * (absolutePower - speedPowerTable[servoPowerIndex - 2]) 
		                       / (speedPowerTable[servoPowerIndex] - speedPowerTable[servoPowerIndex - 2]);
		int percentageBelow = 100 - percentageAbove;
		
		// Interpolate
		int servoPower = (percentageBelow * speedPowerTable[servoPowerIndex - 3] 
                              +
		                 percentageAbove * speedPowerTable[servoPowerIndex - 1]);
		
		/* Muvium controller can't use doubles, work-around for this below.
		 * We look if servoPower >= .50, for example 150, 250, 361, 399. If it is, the number
		 * would normally be rounded to 2, 3, 4, 4 respectively instead of 1, 2, 3, 3. */
		int roundingCorrection = servoPower % 100 >= 50 ? 1 : 0;
		servoPower = (servoPower / 100) + roundingCorrection;
		
		// For negative and positive powers the linearity is the same
		if (power < 0)
			return -servoPower;
		else
			return servoPower;
	}
	
	/**
	 * TODO
	 * @param speed TODO
	 * @return The lineair power of the servo motor
	 */
	public static int convertServoSpeedToPower(int speed)
	{
		int absolutePower = Math.abs(speed);
		int servoPowerIndex = 2;
		
		if(absolutePower < speedPowerTable[0])
			absolutePower = speedPowerTable[0];
		
		// Determine the index in the speedRotationsTable of the upper boundary of power
		while(speedPowerTable[servoPowerIndex] <= absolutePower && servoPowerIndex < speedPowerTable.length-2)
			servoPowerIndex+=2;
				
		// Calculate between the values in the table using the upper and lower boundary
		int percentageAbove = 100 
		                         * (absolutePower - speedPowerTable[servoPowerIndex - 2]) 
		                         / (speedPowerTable[servoPowerIndex] - speedPowerTable[servoPowerIndex - 2]);
		int percentageBelow = 100 - percentageAbove;
		
		// Interpolate
		int servoPower = (percentageBelow * speedPowerTable[servoPowerIndex - 1] 
                              +
		                 percentageAbove * speedPowerTable[servoPowerIndex + 1])
		                  / 100;
		
		// For negative and positive powers the linearity is the same
		if (speed < 0)
			return -servoPower;
		else
			return servoPower;
	}
}
