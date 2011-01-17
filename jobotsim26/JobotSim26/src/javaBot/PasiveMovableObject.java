/*
 * Created on Jun 8, 2004
 */
package javaBot;

/**
 * Abstract class all passive movable objects should extend, i.e. objects that
 * move but not by their own power.
 */
public abstract class PasiveMovableObject extends MovableObject
{
	/**
	 * Creates an object with given parameters
	 *
	 * @param name name of the object
	 * @param friction how high is the friction
	 * @param positionX where is it in the world
	 * @param positionY where is it in the world
	 * @param diameter how big is it
	 * @param mass how heavy is it  Speed, rotation and acceleration are put to
	 *        zero by default
	 */
	public PasiveMovableObject(String name, double friction, double positionX, double positionY,
			double diameter, double mass)
	{
		super(name, friction, positionX, positionY, diameter, mass);
	}

	/**
	 * Calulate were the object is in the new frame based on it's motion
	 *
	 * @param elapsed Time since last rendered frame in milliseconds
	 */
	public void update(double elapsed)
	{
		super.update(elapsed);

		double v = Math.sqrt((this.getVelocityX() * this.getVelocityX())
				+ (this.getVelocityY() * this.getVelocityY()));
		double dv = -mass * this.getFriction() * v * elapsed;

		/*
		 * A check if the velocity equals 0 is being made to prevent the
		 * dvx and dvy values to be set to NaN, due to a division by zero,
		 * when it should be 0.0.
		 */
		double dvx = (v > 0) ? ((dv * this.getVelocityX()) / v) : 0.0;
		double dvy = (v > 0) ? ((dv * this.getVelocityY()) / v) : 0.0;

		/*
		 * Do not allow the direction of the velocity to change,
		 * because of sampling
		 */
		this
				.setVelocityX((Math.abs(this.getVelocityX()) > Math.abs(dvx)) ? (this
						.getVelocityX() + dvx) : 0.0);
		this
				.setVelocityY((Math.abs(this.getVelocityY()) > Math.abs(dvy)) ? (this
						.getVelocityY() + dvy) : 0.0);
	}
}
