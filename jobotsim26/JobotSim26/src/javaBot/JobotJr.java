package javaBot;

/**
 * Ver 0.0 - 08-06-2004 Ver 0.1 - 03-07-2004 -     Modified IR sensor position
 * Included constructor with positioning Ver 0.4 - 30-1202004 -    Included IR
 * sensor
 */
/**
 * Basic virtual robot class with three wheels, three distance sensors, one IR
 * sensor and one fieldsensor.
 */
public class JobotJr extends Robot
{
	/** Represents the red led in the led array */
	public static final int		RED_LED				= 0;

	/** Represents the yellow led in the led array */
	public static final int		YELLOW_LED			= 1;

	/** Represents the green led in the led array */
	public static final int		GREEN_LED			= 2;

	public static final double	BASE_RADIUS			= 0.090;
	public static final double	WHEEL_RADIUS		= 0.045;
	
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/jobotjr.gif");
	private static final int	DIAMETER_IN_IMAGE	= 100;
	
	private static final double	FRICTION = 7.0;
	private static final double	DIAMETER = 2.0;
	private static final double	MASS = 3.0;

	/**
	 * Creates a new JobotJr object.
	 *
	 * @param name Name of the JobotJr
	 * @param positionX The absolute x-as location in the world
	 * @param positionY The absolute y-as location in the world
	 */
	public JobotJr(String name, double positionX, double positionY)
	{
		super(name, FRICTION, positionX, positionY, BASE_RADIUS * DIAMETER, MASS);

		// Create the actors
		// Make sure vector drive in the agent has got these wheel angles
		double[] wheelAngles = {(6 / 6.) * Math.PI, (12 / 6.) * Math.PI};
		setActors(new Actor[2]);
		getActors()[0] = new Wheel(WHEEL_RADIUS, new Location(BASE_RADIUS
				* Math.cos(wheelAngles[0] - (Math.PI / 2)), BASE_RADIUS
				* Math.sin(wheelAngles[0] - (Math.PI / 2))), wheelAngles[0]);
		getActors()[1] = new Wheel(WHEEL_RADIUS, new Location(BASE_RADIUS
				* Math.cos(wheelAngles[1] - (Math.PI / 2)), BASE_RADIUS
				* Math.sin(wheelAngles[1] - (Math.PI / 2))), wheelAngles[1]);

		try
		{
			// Create the sensors
			setSensors(new Sensor[5]);
	
			// addSensor parameters: sensorClassName, radius, position [, direction]
			getSensors()[0] = addSensor("DistanceShortSensor", BASE_RADIUS, 150, 110);  	// Left
			getSensors()[1] = addSensor("DistanceShortSensor", BASE_RADIUS, 30, 70);		// Right
			getSensors()[2] = addSensor("FieldSensor", BASE_RADIUS, 60);					// Left / Middle
			getSensors()[3] = addSensor("FieldSensor", BASE_RADIUS, 120);					// Right
			getSensors()[4] = addSensor("IRSensor", BASE_RADIUS, 300, 120);					// Left
			getSensors()[5] = addSensor("IRSensor", BASE_RADIUS, 240, 60);					// Right

//			getSensors()[0] = addSensor("ContactSensor", BASE_RADIUS, 30);
//			getSensors()[1] = addSensor("ContactSensor", BASE_RADIUS, 150);
//			getSensors()[2] = addSensor("FieldSensor", BASE_RADIUS, 150, 0);
//			getSensors()[3] = addSensor("IRSensor", BASE_RADIUS, 30, 90);
//			getSensors()[4] = addSensor("IRSensor", BASE_RADIUS, 150, 90);
//			getSensors()[5] = addSensor("IRSensor", BASE_RADIUS, 300, 120);		
		}
		
		catch( ReflectionException e )
		{
			e.printStackTrace();
		}
		// Create the leds
		setLeds(new Led[3]);
		getLeds()[GREEN_LED] = new Led(java.awt.Color.GREEN);
		getLeds()[RED_LED] = new Led(java.awt.Color.RED);
		getLeds()[YELLOW_LED] = new Led(java.awt.Color.YELLOW);
	}

	/**
	 * createGraphicalRepresentation() gives the GUI a JComponent to represent
	 * the MovableObject with. It's location within the GUI in pixel values
	 * will be set by the GUI itself.
	 *
	 * @return The JComponent representing the MovableObject in the GUI
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE, true);
	}

	/**
	 * Update the actors and the heartbeat
	 *
	 * @param elapsed TODO PARAM: DOCUMENT ME!
	 */
	public void update(double elapsed)
	{
		super.update(elapsed);

		/**
		 * Calculate wantedVelocity from rotational speeds of the wheels
		 */
		if ((getActors() != null) && (getActors().length == 2))
		{
			double[] w = new double[2];

			// Angular velocity of the wheel in radians
			w[0] = getActors()[0].getValue();
			w[1] = getActors()[1].getValue();
			setDrivingVelocityX((WHEEL_RADIUS * w[0]) / 3.14 * Math.PI);
			setDrivingVelocityY((WHEEL_RADIUS * w[1]) / 3.14 * Math.PI);
			this.setRotationSpeed(WHEEL_RADIUS * BASE_RADIUS * 0);
		}
	}
}
