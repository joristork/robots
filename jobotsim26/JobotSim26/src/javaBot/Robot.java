/*
 * Created on Jun 8, 2004
 */
package javaBot;

import java.lang.reflect.InvocationTargetException;
import javaBot.agents.Agent;

/**
 * Abstract class that should be extended by all robots in the simulator
 *
 * @author bastiaan
 */
public abstract class Robot extends MovableObject implements IDragable
{
	protected double distanceFromWallX = 2.01; 
	protected double distanceFromWallY = 2.01;
	private static final double	ROBOT_SENSOR_OFFSET		= 0.1;
	private static final double	HALF_DEGREE			= 180.0;
	private static final String	PACKAGE_NAME		= "javaBot";

	/** An array of sensors */
	private Sensor[]			sensors				= new Sensor[0];

	/** An array of actors */
	private Actor[]				actors				= new Actor[0];

	/** Array of leds representing their on/off status */
	private Led[]				leds				= new Led[0];
	protected int				dipSwitchSetting	= 0;

	/** What agent am I linked to? */
	private Agent				agent;

	private double				drivingVelocityX	= 0;
	private double				drivingVelocityY	= 0;
	private double				slidingVelocityX	= 0;
	private double				slidingVelocityY	= 0;
	
	/** Enable/Disable collision with field boundaries */
	//private boolean				collideWithBoundaries	= true;


	/**
	 * Creates a new Robot object.
	 *
	 * @param name
	 * @param friction
	 * @param positionX
	 * @param positionY
	 * @param diameter
	 * @param mass
	 */
	protected Robot(String name, double friction, double positionX, double positionY,
			double diameter, double mass)
	{
		super(name, friction, positionX, positionY, diameter, mass);
		setElasticityFactor(0.95);
	}

	/**
	 * Add sensor to robot (direction is assumed to be the same as position)
	 *
	 * @param sensorClassName the classname of the sensor
	 * @param radius the sensor's range from the center of the robot
	 * @param position the location of the sensor on the robot
	 *
	 * @return Sensor
	 *
	 * @throws Exception If the sensorClass doesn't exist
	 */
	protected Sensor addSensor(String sensorClassName, double radius, double position)
			throws ReflectionException
	{
		return addSensor(sensorClassName, radius, position, position);
	}

	/**
	 * Add sensor to robot
	 *
	 * @param sensorClassName the classname of the sensor
	 * @param radius the sensor's range from the center of the robot
	 * @param position the location of the sensor on the robot
	 * @param direction the aim of the sensor
	 *
	 * @return Sensor
	 *
	 * @throws Exception If the sensorClass doesn't exist
	 */
	protected Sensor addSensor(String sensorClassName, double radius, double position,
			double direction) throws ReflectionException
	{
		// convert degrees to radians
		double radianPosition = degreesToRadians(position);
		double radianDirection = degreesToRadians(direction);

		// position of the sensor
		Location location = new Location(
				radius * Math.cos(radianPosition), 
				radius * Math.sin(radianPosition));

		Sensor sensor = null;

		try
		{
			//			 types and values passed to constructor via reflection
			Class[] classParm = new Class[] {Class.forName(PACKAGE_NAME + ".Location"), Double.TYPE};
			Object[] objectParm = new Object[] {location, new Double(radianDirection)};

			//			 initialize class
			Class cl = Class.forName(PACKAGE_NAME + "." + sensorClassName);

			//create the instance
			java.lang.reflect.Constructor co = cl.getConstructor(classParm);
			sensor = (Sensor) co.newInstance(objectParm);

			//sorry :)
		}
		catch (ClassNotFoundException e)
		{
			throw new ReflectionException(e, this.getClass().getName());
		}
		catch (InstantiationException e)
		{
			throw new ReflectionException(e, this.getClass().getName());
		}
		catch (IllegalAccessException e)
		{
			throw new ReflectionException(e, this.getClass().getName());
		}
		catch (InvocationTargetException e)
		{
			throw new ReflectionException(e, this.getClass().getName());
		}
		catch (NoSuchMethodException e)
		{
			throw new ReflectionException(e, this.getClass().getName());
		}

		return sensor;
	}

	/**
	 * Converts degrees to Radians
	 *
	 * @param degrees
	 *
	 * @return double Radians
	 */
	public double degreesToRadians(double degrees)
	{
		double radians = degrees * (Math.PI / HALF_DEGREE);

		return radians;
	}

	/**
	 * sets both velocity and sliding velocity to the specified value. To be
	 * used when objects are dragged or collide.
	 *
	 * @param dipValue the sliding velocity in the x direction
	 */

	//public void setVelocityX(double v) {
	//    velocityX = v;
	//    slidingVelocityX = v;
	//} TODO: Remove?
	//[JC] DipSwitch -- Overridden by UVMRobot
	public void setState(int dipValue)
	{
		dipSwitchSetting = dipValue;
	}

	/**
	 * sets both velocity and sliding velocity to the specified value. To be
	 * used when objects are dragged or collide.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */

	//public void setVelocityY(double v) {
	//    velocityY = v;
	//    slidingVelocityY = v;
	//} TODO: Remove?
	/**
	 * Create a new array of doubles containing the default sensor values
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] newSensorValues()
	{
		double[] values = new double[getSensors().length];

		for (int i = 0; i < getSensors().length; i++)
		{
			values[i] = getSensors()[i].getDefaultValue();
		}

		return values;
	}

	/**
	 * Reset all values to 0
	 */
	public void reset()
	{
		Debug.printInfo("Resetting robot");

		for (int i = 0; i < getActors().length; i++)
		{
			getActors()[i].reset();
		}

		this.setVelocityX(0);
		this.setVelocityY(0);

		drivingVelocityX = 0;
		drivingVelocityY = 0;

		rotation = 0;
		this.setRotationSpeed(0);
	}

	/* (non-Javadoc)
	 * @see javaBot.MovableObject#update(double)
	 */
	public void update(double elapsed)
	{
		double sv = Math.sqrt((slidingVelocityX * slidingVelocityX)
				+ (slidingVelocityY * slidingVelocityY));
		double dsv = -mass * this.getFriction() * sv * elapsed;

		/*
		 * A check if the velocity equals 0 is being made to prevent the
		 * dvx and dvy values to be set to NaN, due to a division by zero,
		 * when it should be 0.0.
		 */
		double dsvx = (sv > 0) ? ((dsv * slidingVelocityX) / sv) : 0.0;
		double dsvy = (sv > 0) ? ((dsv * slidingVelocityY) / sv) : 0.0;

		/*
		 * Do not allow the direction of the velocity to change,
		 * because of sampling
		 */
		slidingVelocityX = (Math.abs(slidingVelocityX) > Math.abs(dsvx)) ? (slidingVelocityX + dsvx)
				: 0.0;
		slidingVelocityY = (Math.abs(slidingVelocityY) > Math.abs(dsvy)) ? (slidingVelocityY + dsvy)
				: 0.0;

		this.setVelocityX(drivingVelocityX + slidingVelocityX);
		this.setVelocityY(drivingVelocityY + slidingVelocityY);

		/*
		 * Set new location and rotation
		 */
		double newX = location.getX() + (elapsed * this.getVelocityX());
		double newY = location.getY() + (elapsed * this.getVelocityY());

		double[] result = getCoordinatesAccordingToBoundary(newX, newY);
		
		location.setX(result[0]);
		location.setY(result[1]);
		rotation += (this.getRotationSpeed() * elapsed);
	}
	
	/*
	 * The option "Field Boundaries" in the menu controls the behaviour of
	 * the robot when the object is about to pass the surrounding walls. The
	 * robot will either be put back inside the field or leave the field to
	 * enter it on the opposite side. 
	 * 
	 * The ROBOT_SENSOR_OFFSET is the distance between the part of the robot that 
	 * actually hits the wall and the sensor.
	 */
	protected double[] getCoordinatesAccordingToBoundary(double xCoordinate, double yCoordinate) 
	{
//		/** Disabled for "Field Boundaries" function*/	
//		if (collideWithBoundaries)
//		{
			if (xCoordinate < ROBOT_SENSOR_OFFSET)
			{
				xCoordinate = 0 + (diameter / distanceFromWallX);
			}
			
			if (yCoordinate < ROBOT_SENSOR_OFFSET)
			{
				yCoordinate = 0 + (diameter / distanceFromWallY);
			}			
			if (xCoordinate > World.WIDTH - ROBOT_SENSOR_OFFSET)
			{
				xCoordinate = World.WIDTH - (diameter / distanceFromWallX);
			}

			if (yCoordinate > World.HEIGHT - ROBOT_SENSOR_OFFSET)
			{
				yCoordinate = World.HEIGHT - (diameter / distanceFromWallY);
			}
//		//		/** Disabled for "Field Boundaries" function*/	
//		}
//		else
//		{
//			if (xCoordinate < ROBOT_SENSOR_OFFSET)
//			{
//				xCoordinate = World.WIDTH - ROBOT_SENSOR_OFFSET;
//			}
//
//			if (yCoordinate < ROBOT_SENSOR_OFFSET)
//			{
//				yCoordinate = World.HEIGHT - ROBOT_SENSOR_OFFSET;
//			}
//
//			if (xCoordinate > World.WIDTH - ROBOT_SENSOR_OFFSET)
//			{
//				xCoordinate = ROBOT_SENSOR_OFFSET;
//			}
//
//			if (yCoordinate > World.HEIGHT - ROBOT_SENSOR_OFFSET)
//			{
//				yCoordinate = ROBOT_SENSOR_OFFSET;
//			}
//		}
	
		double[] coordinates = new double[2];
		coordinates[0] = xCoordinate;
		coordinates[1] = yCoordinate;
		return coordinates;
	}

	public double getDrivingVelocityX()
	{
		return drivingVelocityX;
	}

	public void setDrivingVelocityX(double drivingVelocityX)
	{
		this.drivingVelocityX = drivingVelocityX;
	}

	public double getDrivingVelocityY()
	{
		return drivingVelocityY;
	}

	public void setDrivingVelocityY(double drivingVelocityY)
	{
		this.drivingVelocityY = drivingVelocityY;
	}

	public Agent getAgent()
	{
		return agent;
	}

	public void setAgent(Agent agent)
	{
		this.agent = agent;
	}

	public void setSensors(Sensor[] sensors)
	{
		this.sensors = sensors;
	}

	public Sensor[] getSensors()
	{
		return sensors;
	}

	public void setActors(Actor[] actors)
	{
		this.actors = actors;
	}

	public Actor[] getActors()
	{
		return actors;
	}

	public void setLeds(Led[] leds)
	{
		this.leds = leds;
	}

	public Led[] getLeds()
	{
		return leds;
	}
	
//	/** Disabled for "Field Boundaries" function*/	
//	public boolean isCollideWithBoundaries()
//	{
//		return collideWithBoundaries;
//	}
//
//	public void setCollideWithBoundaries(boolean collideWithBoundaries)
//	{
//		this.collideWithBoundaries = collideWithBoundaries;
//	}

}
