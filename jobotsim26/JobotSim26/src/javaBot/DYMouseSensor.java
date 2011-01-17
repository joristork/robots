/*
 * Created on Jun 22, 2006
 */
package javaBot;

/**
 * Simulates the optical mouse sensor on the robot to determine the last
 * location change of the robot on the playingfield in vertical direction.
 */
public class DYMouseSensor extends Sensor
{    
    private double lastY;
    
	/**
	 * Creates a new DYMouseSensor object.
	 *
	 * @param position The position relative to the robot the sensor placed on
	 * @param angle The angle relative to the robot under which the sensor
	 *        placed
	 */
	public DYMouseSensor(Location position, double angle)
	{
		// create sensor with position, angle and default value 0
		super("DYMouseSensor", position, angle, 0);
		lastY = position.getY();
	}
	
	/**
	 * Returns the vertical position of the robot on the field
	 * 
	 * @return vertical position in metres
	 */
	public double getLastLocation(){
	    return lastY;
	}
	
	/**
	 * Sets the vertical position of the robot on the field
	 * 
	 * @param loc, vertical position in metres
	 */
	public void setLastLocation(double loc){
	    lastY = loc;
	}
}
