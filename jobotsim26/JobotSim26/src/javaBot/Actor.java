/*
 * Created on Jun 18, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 */
package javaBot;

/**
 * @version $Revision: 1.1 
 * $ last changed Feb 17, 2006
 */
public abstract class Actor
{
	//Under wich angle is this actor positioned
	private double		angle;

	//What is the relative position of the actor on the robot
	private Location	position;

	//current value
	private double		value;

	//Just for GUI purposes
	private String		name;

	//Default Constructor
	Actor()
	{
	}

	//Constructor: Creates a new Actor object under specified angle and postion
	Actor(String name, Location position, double angle)
	{
		this.name = name;
		this.position = position;
		this.angle = angle;
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
	public double getValue()
	{
		return value;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param angle TODO PARAM: param description
	 */
	public void setAngle(double angle)
	{
		this.angle = angle;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param value TODO PARAM: param description
	 */
	public void setValue(double value)
	{
		this.value = value;
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
	 * @param location TODO PARAM: param description
	 */
	public void setPosition(Location location)
	{
		position = location;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void reset()
	{
		value = 0;
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
	
	/**
	 * 
	 * @return The speed of the actor in percentage
	 */
	public double getPercentageSpeed()
	{
		return getValue();
	}
}
