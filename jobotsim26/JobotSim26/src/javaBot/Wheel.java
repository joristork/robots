/*
 * Created on Jun 18, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Power level        % Speed
 *         1                        0
 *        4                        12.5
 *        5                        25
 *        8                        37.5
 *        9                        50
 *        10                        62.5
 *        11                        75
 *        14                        87.5
 *        34                        100
 *        100                        100
 */
package javaBot;

/**
 * Logic for a robot wheel
 *
 */
public class Wheel extends Actor
{
	private double	radius;
	//The maximum speed of the wheel
	private static final double MAX_WHEEL_SPEED = 50.0;


	/**
	 * Constructor for the wheel
	 *
	 * @param radius The radius of the wheel
	 * @param position Location of the wheel on a robot
	 * @param angle The direction the wheel is pointed at
	 */
	public Wheel(double radius, Location position, double angle)
	{
		this.radius = radius;
		setPosition(position);
		setAngle(angle);
		setName("Wheel");
	}

	/**
	 *
	 * @return Returns the radius
	 */
	public double getRadius()
	{
		return radius;
	}
	
	/**
	 * 
	 * @return The percentage of the speed, 0 - 100%
	 */
	public double getPercentageSpeed()
	{
//		negative speed is backward speed, so must also be positive
		double speed = Math.abs( ( super.getValue() / MAX_WHEEL_SPEED ) * 100.0 );
		return speed;
	}
	
	/**
	 * Used for unit-test PercentageSpeedTest.java
	 * 
	 * @return The max speed of this wheel
	 */
	public double getMaxSpeed()
	{
		return (MAX_WHEEL_SPEED);
	}
}
