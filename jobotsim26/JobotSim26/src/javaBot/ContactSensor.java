/*
 * Created on Feb 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package javaBot;


/**
 * Contact Sensor: if distance <4 cm returns 1023, else 0 as sensorvalue
 * Must be an instance of DistanceSensor (see SurroundingWalls.java)
 * TODO: get rid of the static methods
 * TODO: the methods here are never used because SurroundingWals uses DistanceSensor static calls. 
 * So why have a ContactSensor then...
 * @see javaBot.SurroundingWalls#giveSensoryInformationTo(Robot)
 * 
 * Please note that a contactsensor is actually a modified distance sensor
 * that has a fixed distance of about 8 cm.
 * It is handled as an analog sensor but returns a reversed value
 * It returns 0 when an object is detected
 */
public class ContactSensor extends DistanceSensor
{
	protected double				maxDist				= 0.20;

	//protected static int			keyValuePairs[]		= {0, 1023, 5, 0};
	public static final int BOUND_ERROR = -1;

	//	 Default constructor:
	public ContactSensor(Location position, double angle, double direction)
	{
		//	 create sensor with position, angle and default value 0
		super("ContactSensor", position, angle, direction);
	}
	
	public ContactSensor(Location position, double angle)
	{
		//	 create sensor with position, angle and default value 0
		super("ContactSensor", position, angle, 0);
	}

	/**
	 * Return the correspondig sensor value for a given distance
	 * @param distance distance in cm
	 * @return int sensorvalue 0 or 1023 or -1 if out-of-bounds argument
	 */
	protected static int getValue(int distance)
	{
		int i = 0;
		if (distance < 0)    // may happen if dist is too large
		{
			i = 0; 
		}
		else if (distance <= 15)
		{
			i = 112;           //getTableValue(distance);
		}
		else if (distance > 15)
		{
			i = 0;       //getTableValue(5);
		}
//		Debug.printInfo("ContactSensor getValue for distance="+distance + " =" + i);
		return i;
	}
		
	/** 
	 * Convert the distance reading to a (real) sensor value (the actual sensors)
	 * don't return a distance reading but a value <code>0 &lt; val &lt; 512</code> 
	 */
	public int convertDistanceToValue(double dist)
	{
		int i = 0;
		// Debug.printInfo("ContactSensor.convertDistanceToValue for dist ="+ dist);
		if (Double.isInfinite(dist)) {
			i = 0;
		} else {
			i =  ContactSensor.getValue((int) Math.round(100.0 * dist));
		}
		return i;
	}
	
	/** 
	 * Convert a (real) sensor value to a distance reading. Not applicable for contactsensor.
	 */
	public double convertValueToDistance(double val)
	{
		return (double) this.getDistanceInCM((int) val) / 100.0;
	}
	
	/**
	 *  Contactsensor: this method should not be used for ContactSensors
	 */
	public int getDistanceInCM(int value)
	{
		//Debug.printInfo("ContactSensor.getDistanceInCM called for value ="+value);
		if (value == 1023) return 0;
		return 8;
	}
	
	/**
	 * Create a line going through the sensor under the correct angle.
	 *
	 * @param posX x position of the robot the sensor attached to
	 * @param posY y position of the robot the sensor attached to
	 * @param rotationRobot oriantation of the robot
	 *
	 * @return The line of sight of the sensor at the maximum sight length
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
	 * Gets the Max distance of the sensorline.
	 */
	public double getMaxDist()
	{
		return this.maxDist;
	}
	
	/**
	 * Sets the Max distance of the sensorline.
	 *
	 * @param value The value of the max distance to set.
	 */
	public void setMaxDist(double value)
	{
		this.maxDist = value;
	}
}

