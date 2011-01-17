/*
 * Created on Feb 17, 2005 TODO To change the
 * template for this generated file go to Window -
 * Preferences - Java - Code Style - Code
 * Templates
 */
package javaBot;

/**
 * Distance sensor
 */
public class DistanceShortSensor extends DistanceSensor
{
	public final static int	BOUND_ERROR		= -1;

	protected double		maxDist			= 0.3;
	protected static int	keyValuePairs[]	= {
			0,151,
			1,160,
			2,190,
			3,215,
			4,316,
			5,369,
			6,392,
			7,472,
			8,507,
			9,503,
			10,472,
			11,450,
			12,420,
			13,391,
			14,365,
			15,340,
			16,321,
			17,312,
			18,305,
			19,290,
			20,270,
			25,220,
			30,0	
			};
	/**
	 * keeps a pre-calculated table of values per
	 * 0-30 cm to speed up lookups
	 */
	protected static int[]	cmValueCache	= loadValueTable();

	// Default constructor:
	public DistanceShortSensor(Location position, double angle)
	{
		// create sensor with position, angle and
		// default value 0
		super("DistanceShortSensor", position, angle, 0);
	}

	/**
	 * @return array of interpolated values to be
	 *         used as cache. Index is cm, value
	 *         is sensorvalue.
	 */
	protected static int[] loadValueTable()
	{
		cmValueCache = new int[31];
		for (int i = 0; i < 31; i++)
			cmValueCache[i] = 0;
		// 0-20 cm
		for (int i = 0; i < keyValuePairs.length / 2; i++)
		{
			cmValueCache[keyValuePairs[i * 2]] = keyValuePairs[i * 2 + 1];
		}
		// 21-25 cm: Modulo 5 because of the way
		// the keypairs are arranged
		// between 20 and 30 cm
		for (int distance = 21; distance < 30; distance++)
		{
			int lowBoundIndex = (distance - (distance % 5));
			int upBoundIndex = lowBoundIndex + 5;
			cmValueCache[distance] = interpolateLinear(cmValueCache[lowBoundIndex], lowBoundIndex,
					cmValueCache[upBoundIndex], upBoundIndex, distance);
		}
		return cmValueCache;
	}

	/** used by children of DistanceSensor */
	protected static int getTableValue(int cm)
	{
		if (cm < 0 || cm > 30)
			return BOUND_ERROR;
		else
			return cmValueCache[cm];
	}

	/* Variable ’distance’ in CM. */
	protected static int getValue(int distance)
	{
		if (distance < 0)
		{
			return BOUND_ERROR;
		}
		else if (distance < 30)
		{
			return cmValueCache[distance];
		}
		else
		{
			return cmValueCache[30];
		}
	}

	/**
	 * Create a line going through the sensor
	 * under the correct angle.
	 * 
	 * @param posX
	 *        x position of the robot the sensor
	 *        attached to
	 * @param posY
	 *        y position of the robot the sensor
	 *        attached to
	 * @param rotationRobot
	 *        oriantation of the robot
	 * @return The line of sight of the sensor at
	 *         the maximum sight length
	 */
	public Line sensorLine(double posX, double posY, double rotationRobot)
	{
		double diam = Math.sqrt(Math.pow(getPosition().getX(), 2)
				+ Math.pow(getPosition().getY(), 2));
		Location point = new Location(posX + Math.cos(getAngle() + rotationRobot) * diam, posY
				+ Math.sin(getAngle() + rotationRobot) * diam);
		return new Line(point, getAngle() + rotationRobot, maxDist);
	}
	
	/*
	 * Note: from 0 - 3 CM the measuring is
	 * imprecise.
	 */
	public int getDistanceInCM(int value)
	{
		int tableValue, last_i = 8, lastTableValue = getValue(last_i);
		/* Search the table for the best key: */
		for (int i = last_i; i <= 30; i++)
		{
			tableValue = getValue(i);
			if (tableValue <= value)
			{
				if (tableValue == lastTableValue)
					return last_i; // otherwise
									// divide by
									// zero in
									// next
									// statement
				else
					return last_i + (i - last_i) * (value - lastTableValue)
							/ (tableValue - lastTableValue);
			}
			lastTableValue = tableValue;
			last_i = i;
		}
		return 30;
	}
}
