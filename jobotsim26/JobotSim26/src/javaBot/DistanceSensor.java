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
public class DistanceSensor extends Sensor
{
	public final static int	BOUND_ERROR		= -1;

	protected double		maxDist			= 0.8;
	protected static int	keyValuePairs[]	= {
			0, 151,
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
			30,178,
			35,149,
			40,126,
			45,110,
			50,98,
			55,86,
			60,80,
			65,75,
			70,70,
			75,35, // TODO: Check if this sensorvalue
				// is correct for a distance of 75
				// cm
			80,0		
			};
	
	/**
	 * keeps a pre-calculated table of values per
	 * 0-80 cm to speed up lookups
	 */
	protected static int[]	cmValueCache	= loadValueTable();

	/**
	 * Creates a new DistanceSensor object under
	 * specified angle and postion
	 */
	public DistanceSensor(String name, Location position, double angle, double defaultValue)
	{
		setName(name);
		setPosition(position);
		setAngle(angle);
		setDefaultValue(defaultValue);
		setValue(defaultValue);
	}

	// Default constructor:
	public DistanceSensor(Location position, double angle)
	{
		// create sensor with position, angle and
		// default value 0
		super("DistanceSensor", position, angle, 0);
	}

	/**
	 * @return array of interpolated values to be
	 *         used as cache. Index is cm, value
	 *         is sensorvalue.
	 */
	protected static int[] loadValueTable()
	{
		cmValueCache = new int[81];
		for (int i = 0; i < 81; i++)
			cmValueCache[i] = 0;
		// 0-20 cm
		for (int i = 0; i < keyValuePairs.length / 2; i++)
		{
			cmValueCache[keyValuePairs[i * 2]] = keyValuePairs[i * 2 + 1];
		}
		// 21-70 cm: Modulo 5 because of the way
		// the keypairs are arranged
		// between 20 and 80 cm
		for (int distance = 21; distance < 80; distance++)
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
		if (cm < 0 || cm > 80)
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
		else if (distance < 80)
		{
			return cmValueCache[distance];
		}
		else
		{
			return cmValueCache[80];
		}
	}

	public static int interpolateLinear(int lowBound, int lowBoundIndex, int upBound,
			int upBoundIndex, int index)
	{
		// Out of bound, just return -1
		if ((index > upBoundIndex) || (index < lowBoundIndex))
		{
			return BOUND_ERROR;
		}
		else
		{
			double boundDifference = (upBound - lowBound);
			// Calculate the range of values that
			// are reached between these upper and
			// lowerbound
			double indexDifference = (upBoundIndex - lowBoundIndex);
			// Calculate the value of one step
			// from lowerboundindex to upperbound
			double indexFraction = boundDifference / indexDifference;
			// Calculate delta
			double deltaValue = (index - lowBoundIndex) * indexFraction;
			// Now calculate the value of index
			double indexValue = lowBound + deltaValue;
			return (int) Math.round(indexValue);
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

	/**
	 * Convert the distance reading to a (real)
	 * sensor value (the actual sensors) don't
	 * return a distance reading but a value
	 * <code>0 &lt; val &lt; 512</code>
	 */
	public int convertDistanceToValue(double dist)
	{
		return getValue((int) Math.round(100.0 * dist));
	}

	/**
	 * Convert a (real) sensor value to a distance
	 * reading
	 */
	public double convertValueToDistance(double val)
	{
		return (double) getDistanceInCM((int) val) / 100.0;
	}

	/*
	 * Note: from 0 - 8 CM the measuring is
	 * imprecise.
	 */
	public int getDistanceInCM(int value)
	{
		int tableValue, last_i = 8, lastTableValue = getValue(last_i);
		/* Search the table for the best key: */
		for (int i = last_i; i <= 70; i++)
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
		return 80;
	}

	/**
	 * Gets the Max distance of the sensorline.
	 */
	public double getMaxDist()
	{
		return this.maxDist;
	}

	/**
	 * Sets the Max distance of the sensorline.
	 * 
	 * @param value
	 *        The value of the max distance to
	 *        set.
	 */
	public void setMaxDist(double value)
	{
		this.maxDist = value;
	}
}
