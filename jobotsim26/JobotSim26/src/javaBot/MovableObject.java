/*
 * Created on Jun 04, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Abstract class all movable objects should extend, includes fields and methods for the
 * implementation fo physical laws in the virtual world.
 */
package javaBot;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ last changed Feb 17, 2006
 */
public abstract class MovableObject extends PhysicalObject
{
	/**
	 * Resembles the constant factor with [Ns/m] as unit, to be multiplied with
	 * the object's velocity to calculate the frictional force.
	 */
	private double	friction;

	/** Speed in the x direction */
	private double	velocityX;

	/** Speed in the y direction */
	private double	velocityY;

	/** Acceleration in the x dimension in m/s^2 */
	private double	accelerationX;

	/** Acceleration in the y dimension in m/s^2 */
	private double	accelerationY;

	/** Rotation speed of the object, clockwise is positive in radions/second */
	private double	rotationSpeed;

	/**
	 * Models the elasticity of an object as a double between 0 and 1 An
	 * elasticity factor set to 0 means all energy is absorbed during a
	 * collision. Elasticity factor 1 means no energy at all is absorbed.
	 * (This currently only works with collisions with the surrounding walls)
	 */
	private double	elasticityFactor	= 1;

	/**
	 * Creates a new MovableObject object with default properties.
	 *
	 * @param name name of the object
	 */
	public MovableObject(String name)
	{
		super(name);
		friction = 0;
		location = new Location(0, 0);
		diameter = 1.;
		mass = 1.;
		accelerationX = 0;
		accelerationY = 0;
		rotation = 0;
		velocityX = 0;
		velocityY = 0;
	}

	/**
	 * Creates a new MovableObject object.
	 *
	 * @param name name of the object
	 * @param friction how high is the friction
	 * @param locationX where is it in the world
	 * @param locationY where is it in the world
	 * @param diameter how big is it
	 * @param mass how heavy is it  Speed, rotation and acceleration are put to
	 *        zero by default
	 */
	public MovableObject(String name, double friction, double locationX, double locationY,
			double diameter, double mass)
	{
		super(name);
		this.friction = friction;
		this.location = new Location(locationX, locationY);
		this.diameter = diameter;
		this.mass = mass;
		accelerationX = 0;
		accelerationY = 0;
		rotation = 0;
		velocityX = 0;
		velocityY = 0;
	}

	/**
	 * Calulate were the object is in the new frame based on it's motion
	 *
	 * @param elapsed Time since last rendered frame in milliseconds
	 */
	public void update(double elapsed)
	{
		double v = Math.sqrt((velocityX * velocityX) + (velocityY * velocityY));
		double dv = -mass * friction * v * elapsed;

		/*
		 * A check if the velocity equals 0 is being made to prevent the
		 * dvx and dvy values to be set to NaN, due to a division by zero,
		 * when it should be 0.0.
		 */
		double dvx = (v > 0) ? ((dv * velocityX) / v) : 0.0;
		double dvy = (v > 0) ? ((dv * velocityY) / v) : 0.0;

		/*
		 * Do not allow the direction of the velocity to change,
		 * because of sampling
		 */
		velocityX = (Math.abs(velocityX) > Math.abs(dvx)) ? (velocityX + dvx) : 0.0;
		velocityY = (Math.abs(velocityY) > Math.abs(dvy)) ? (velocityY + dvy) : 0.0;

		/*
		 * Set new location and rotation
		 */
		double newX = location.getX() + (elapsed * velocityX);
		double newY = location.getY() + (elapsed * velocityY);

		/*
		 * When the object is about to pass the surrounding walls
		 * put it back inside
		 *
		 * to get the new location the diameter is divided bij slightly
		 * more than 2.0 to trigger a collision with the surrounding wall
		 */
		if (newX < 0)
		{
			newX = 0 + (diameter / 2.01);
		}

		if (newY < 0)
		{
			newY = 0 + (diameter / 2.01);
		}

		if (newX > World.WIDTH)
		{
			newX = World.WIDTH - (diameter / 2.01);
		}

		if (newY > World.HEIGHT)
		{
			newY = World.HEIGHT - (diameter / 2.01);
		}

		location.setX(newX);
		location.setY(newY);
		rotation += (rotationSpeed * elapsed);
	}

	/**
	 * Give sensory information to the distance sensors on a robot
	 *
	 * @param r TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		double[] returnvalue = r.newSensorValues();

		// For each sensor on the robot, do:
		for (int i = 0; i < r.getSensors().length; i++)
		{
			// If it is a Distance sensor
			if (r.getSensors()[i] instanceof DistanceSensor)
			{
				double d = r.getSensors()[i].getDefaultValue();

				// Get the sensor and create the sensorline
				DistanceSensor ds = ((DistanceSensor) r.getSensors()[i]);
				Line sensorLine = ds.sensorLine(r.getLocation().getX(), r.getLocation().getY(),
						r.rotation);

				// Get the distance from the sensor to the object
				double dist = sensorLine.distancePointToLine(this.location);

				if (dist < (diameter / 2))
				{
					d = ds.convertDistanceToValue(new Line(r.location, location)
							.getLength()
							- (diameter / 2) - (r.diameter / 2));
				}

				// If the current object is closer to the sensor than the
				// current reading, overwrite it.
				if ((d > 0) && (d > returnvalue[i]))
				{
					returnvalue[i] = d;
				}
			}
		}

		return returnvalue;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the velocityX.
	 */
	public double getVelocityX()
	{
		return velocityX;
	}

	/**
	 * Sets the velocity to the specified value for robots it also sets the
	 * sliding velocity. To be used when objects are dragged or collide.
	 *
	 * @param v velocity in x direction
	 */
	public void setVelocityX(double v)
	{
		velocityX = v;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the velocityY.
	 */
	public double getVelocityY()
	{
		return velocityY;
	}

	/**
	 * Sets the velocity to the specified value for robots it also sets the
	 * sliding velocity. To be used when objects are dragged or collide.
	 *
	 * @param v velocity in y direction
	 */
	public void setVelocityY(double v)
	{
		velocityY = v;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the accelerationX.
	 */
	public double getAccelerationX()
	{
		return accelerationX;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param accelerationX The accelerationX to set.
	 */
	public void setAccelerationX(double accelerationX)
	{
		this.accelerationX = accelerationX;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the accelerationY.
	 */
	public double getAccelerationY()
	{
		return accelerationY;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param accelerationY The accelerationY to set.
	 */
	public void setAccelerationY(double accelerationY)
	{
		this.accelerationY = accelerationY;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the elasticityFactor.
	 */
	public double getElasticityFactor()
	{
		return elasticityFactor;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param elasticityFactor The elasticityFactor to set.
	 */
	public void setElasticityFactor(double elasticityFactor)
	{
		this.elasticityFactor = elasticityFactor;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the friction in a Ns/m unit.
	 */
	public double getFriction()
	{
		return friction;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param friction The friction to set, in Ns/m.
	 */
	public void setFriction(double friction)
	{
		this.friction = friction;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the rotationSpeed.
	 */
	public double getRotationSpeed()
	{
		return rotationSpeed;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param rotationSpeed The rotationSpeed to set.
	 */
	public void setRotationSpeed(double rotationSpeed)
	{
		this.rotationSpeed = rotationSpeed;
	}
}
