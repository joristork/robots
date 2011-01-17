/*
 * Created on Jun 07, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Abstract class for creation of various types of sensors. The sensor class doesn't actually perform any
 * calculations, the actual work has to be done by the PhysicalObject subclasses by overriding the <code>giveSensoryInformationTo(Robot r)</code>.
 * Keep in mind that sensors are placed under a certain angle relative to the robot and at a certain position relative to the robot.
 *
 **/
package javaBot;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ last changed Feb 20, 2006
 */
public abstract class Sensor
{
	//Just for GUI purposes
	private String		name;

	//If the sensor has no object in range what value should it read?
	private double		defaultValue;

	//current value the sensor returns
	private double		value;

	//What is the relative position of the sensor on the robot 
	private Location	position;

	//nder what angle does this sensor look 
	private double		angle;
	
//	maximum sensor value
	protected int maxSensorValue = 1023;

	/**
	 * Creates a new Sensor object.
	 */
	Sensor()
	{
	}

	/**
	 * Creates a new Sensor object under specified angle and postion
	 *
	 * @param name TODO PARAM: DOCUMENT ME!
	 * @param position TODO PARAM: DOCUMENT ME!
	 * @param angle TODO PARAM: DOCUMENT ME!
	 * @param defaultValue TODO PARAM: DOCUMENT ME!
	 */
	Sensor(String name, Location position, double angle, double defaultValue)
	{
		this.name = name;
		this.position = position;
		this.angle = angle;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param value TODO PARAM: param description
	 */
	public void setValue(double value)
	{
		// System.out.println("Setting " + this.name + " to " + value);
		if (this.name.equals("ContactSensor"))
			this.value = 112 - value; // The contactsensor gives an inverted value back
		else
			this.value = value;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getAngle()
	{
		return angle;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Location getPosition()
	{
		return position;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param d TODO PARAM: param description
	 */
	public void setAngle(double d)
	{
		angle = d;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param d TODO PARAM: param description
	 */
	public void setDefaultValue(double d)
	{
		defaultValue = d;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param location TODO PARAM: param description
	 */
	public void setPosition(Location location)
	{
		position = location;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param robotLoc
	 *
	 * @return
	 */
	public Location getPositionInWorld(Location robotLoc)
	{
		return robotLoc.add(position);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public int getMaxValue()
	{
		return maxSensorValue;
	}
}
