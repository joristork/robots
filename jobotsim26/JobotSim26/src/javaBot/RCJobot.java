package javaBot;

/**
 * Ver 0.0 - 08-06-2004 Ver 0.1 - 03-07-2004 -     Modified IR sensor position
 * Included constructor with positioning 05-07-2004    -    Included black
 * jobot version
 */
/**
 * Basic virtual robot class with three wheels, three distance sensors, one IR
 * sensor and one fieldsensor.
 */
public class RCJobot extends Robot
{
	/** Represents the red led in the led array */
	public static final int		RED_LED				= 0;
	/** Represents the yellow led in the led array */
	public static final int		YELLOW_LED			= 1;
	/** Represents the green led in the led array */
	public static final int		GREEN_LED			= 2;

	public static final double	BASE_RADIUS			= 0.090;
	public static final double	WHEEL_RADIUS		= 0.020;

	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/jobotblack.gif");
	private static final int	DIAMETER_IN_IMAGE	= 100;

	/**
	 * Creates a new RCJobot object.
	 *
	 * @param name TODO PARAM: DOCUMENT ME!
	 * @param positionX TODO PARAM: DOCUMENT ME!
	 * @param positionY TODO PARAM: DOCUMENT ME!
	 */
	public RCJobot(String name, double positionX, double positionY)
	{
		/*
		 * friction = 7.0
		 * diameter = 2.0
		 * mass = 3.0
		 */
		super(name, 7.0, positionX, positionY, BASE_RADIUS * 2, 3);

		// Create the actors
		// Make sure vector drive in the agent has got these wheel angles
		double[] wheelAngles = {(6 / 6.) * Math.PI, (10 / 6.) * Math.PI, (2 / 6.) * Math.PI};
		setActors(new Actor[3]);
		getActors()[0] = new Wheel(WHEEL_RADIUS, new Location(BASE_RADIUS
				* Math.cos(wheelAngles[0] - (Math.PI / 2)), BASE_RADIUS
				* Math.sin(wheelAngles[0] - (Math.PI / 2))), wheelAngles[0]);
		getActors()[1] = new Wheel(WHEEL_RADIUS, new Location(BASE_RADIUS
				* Math.cos(wheelAngles[1] - (Math.PI / 2)), BASE_RADIUS
				* Math.sin(wheelAngles[1] - (Math.PI / 2))), wheelAngles[1]);
		getActors()[2] = new Wheel(WHEEL_RADIUS, new Location(BASE_RADIUS
				* Math.cos(wheelAngles[2] - (Math.PI / 2)), BASE_RADIUS
				* Math.sin(wheelAngles[2] - (Math.PI / 2))), wheelAngles[2]);

		// Create the sensors
		setSensors(new Sensor[5]);

		try
		{
			// addSensor parameters: sensorClassName, radius, position [, direction]
			getSensors()[0] = addSensor("ReflectionSensor", BASE_RADIUS, 270);
			getSensors()[1] = addSensor("ReflectionSensor", BASE_RADIUS, 150);
			getSensors()[2] = addSensor("ReflectionSensor", BASE_RADIUS, 30);
			getSensors()[3] = addSensor("IRSensor", WHEEL_RADIUS, 180, 90);
			getSensors()[4] = addSensor("FieldSensor", BASE_RADIUS, 270, 0);			
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
		if ((getActors() != null) && (getActors().length == 3))
		{
			double[] a = new double[3];
			double[] w = new double[3];
			double[] fx = new double[3];
			double[] fy = new double[3];

			for (int i = 0; i < 3; i++)
			{
				// Angle of the wheel's rolling direction
				a[i] = getActors()[i].getAngle() + rotation;

				// x component of the wheel's direction
				fx[i] = Math.cos(a[i]);

				// y component of the wheel's rolling direction
				fy[i] = Math.sin(a[i]);

				// Angular velocity of the wheel
				w[i] = getActors()[i].getValue();
			}

			double div = (fx[2] * (fy[0] - fy[1])) + (fx[0] * (fy[1] - fy[2]))
					+ (fx[1] * (-fy[0] + fy[2]));
			setDrivingVelocityX((WHEEL_RADIUS * (((fy[1] - fy[2]) * w[0])
					+ ((-fy[0] + fy[2]) * w[1]) + ((fy[0] - fy[1]) * w[2])))
					/ div);
			setDrivingVelocityY((WHEEL_RADIUS * (((fx[1] - fx[2]) * w[0])
					+ ((-fx[0] + fx[2]) * w[1]) + ((fx[0] - fx[1]) * w[2])))
					/ -div);
			this
					.setRotationSpeed((WHEEL_RADIUS * ((((-fx[2] * fy[1]) + (fx[1] * fy[2])) * w[0])
							+ (((fx[2] * fy[0]) - (fx[0] * fy[2])) * w[1]) + (((-fx[1] * fy[0]) + (fx[0] * fy[1])) * w[2])))
							/ (BASE_RADIUS * div));
		}
	}
}
