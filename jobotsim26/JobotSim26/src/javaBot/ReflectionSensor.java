/*
 * Created on Feb 17, 2005 TODO To change the
 * template for this generated file go to Window -
 * Preferences - Java - Code Style - Code
 * Templates
 */
package javaBot;

/**
 * The reflection sensor is a kind of
 * distancesensor
 */
public class ReflectionSensor extends DistanceSensor
{
	/**
	 * Creates a reflection sensor
	 * 
	 * @param position
	 *        the position on the robot
	 * @param angle
	 *        The angle where the sensor is
	 *        pointing at
	 */
	public ReflectionSensor(Location position, double angle)
	{
		// create sensor with position, angle and
		// default value 0
		super("ReflectionSensor", position, angle, 0);
		
		//sets the maximum sensor value
		maxSensorValue = 507;
	}

	/**
	 * Comment: From 0 - 8 CM the measuring is
	 * imprecise. <br>
	 * [jd] TODO: values >506 will now return 7
	 * instead of divide by zero except. Is that
	 * correct?
	 * 
	 * @param value
	 * @return
	 */
	public int getDistanceInCM(int value)
	{
		int tableValue, last_i = 8, lastTableValue = getValue(last_i);
		if (value > 506)
			return 7; // prevent division by
						// zero
		if (value <= 0)
			return 80;
		/* Search the table for the best key: */
		for (int i = last_i; i <= 70; i++)
		{
			tableValue = getValue(i);
			if (tableValue <= value) // stop
										// search
										// when
										// found
										// tablevalue
										// smaller
										// than
										// value
			{
				if (tableValue == lastTableValue)
					return last_i;
				else
					return last_i + (i - last_i) * (value - lastTableValue)
							/ (tableValue - lastTableValue);
			}
			lastTableValue = tableValue;
			last_i = i;
		}
		return 80;
	}

	/**
	 * Convert a (real) sensor value to a distance
	 * reading in meters
	 */
	public double convertValueToDistance(double val)
	{
		return (double) getDistanceInCM((int) val) / 100.0;
	}

}
