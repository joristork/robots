/*
 * Created on Feb 14, 2006 Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 */
package javaBot;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $  last changed Feb 14, 2006
 */
public class TachoSensor extends Sensor
{
	/**
	 * Creates a new FieldSensor object.
	 *
	 * @param position The position relative to the robot the sensor placed on
	 * @param angle The angle relative to the robot under which the sensor
	 *        placed
	 */
	public TachoSensor(Location position, double angle)
	{
		// create sensor with position, angle and
		// default value 0
		super("TachoSensor", position, angle, 0);
	}
}
