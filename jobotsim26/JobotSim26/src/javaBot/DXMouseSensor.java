/*
 * Created on Jun 22, 2006
 */
package javaBot;

/**
 * Simulates the optical mouse sensor on the robot to determine the last
 * location change on the playingfield in horizontal direction. Also holds the
 * sum of the total position change since the last sensor reset.
 */
public class DXMouseSensor extends Sensor {
	private int[] dxy = new int[2];

	private double lastX;

	/**
	 * Creates a new DXMouseSensor object.
	 * 
	 * @param position
	 *            The position relative to the robot the sensor placed on
	 * @param angle
	 *            The angle relative to the robot under which the sensor placed
	 */
	public DXMouseSensor(Location position, double angle) {
		// create sensor with position, angle and default value 0
		super("DXMouseSensor", position, angle, 0);
		lastX = position.getX();
		dxy[0] = 0;
		dxy[1] = 0;
	}

	public double getLastLocation() {
		return lastX;
	}

	public void setLastLocation(double loc) {
		lastX = loc;
	}

	/**
	 * Clears the travelled distance and returns its former value
	 */
	public int[] resetSensor() {
		int[] temp = (int[]) dxy.clone();
		dxy[0] = 0;
		dxy[1] = 0;
		return temp;
	}

	public int[] getDistance() {
		return dxy;
	}

	public void setDistance(int nx, int ny) {
		dxy[0] += nx;
		dxy[1] += ny;
	}
}
