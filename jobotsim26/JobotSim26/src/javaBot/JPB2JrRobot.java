package javaBot;

/**
 * Ver 0.0 - 01-06-2004 Ver 0.1 - 03-07-2004     -     Implemented Actors to
 * display servos     Included DoCommand webservice interfaces (preliminary)
 * Modified ADC output to integer value     Reversed direction of servo
 * motors Ver 0.2 - 01-08-2004 [JC]     Added commands as static variables
 * Integrated icd with Reset/Program/Run commands     Remoted simmode -
 * UVMRobot acts as an avatar to the real-robot. See note on avatar Ver 0.3 -
 * 14-08-2004        Added the WebService implementation and changed the
 * interface to better reflect what is available on the real robot agent Ver
 * 0.4 - 16-12-2005        Jobot Junior version with contact sensors Avatar
 * When running as an avatar the simulation is a representation of the
 * real-robot and run side by side. All commands are passed through to the
 * real robot. The difference between the way the two run will be the
 * differences in the world stimulus they receive. One way to handle this is
 * to read the actual sensor values off the real device and plug them into the
 * simulation.
 */
import org.openVBB.interfaces.IopenVBBRTI;
import org.openVBB.robotKit.controllers.JoBotJPB2Controller;
import org.openVBB.robotKit.interfaces.Units;
import org.openVBB.sim.rti.OpenVBBRTIImpl;

/**
 * [JC] The JrJpb2ProxyRobot implements a Robot 'brain' using the openVBB uVM
 * simulator. The JoBotJPB2Controller is a wrapper for an openVBB circuit
 * simulation using the openVBB simulator with wiring as per the JPB
 * controller with electical connections provided as per the JPB
 * specification. The UVMRobot can also act as an avatar for a real robot. In
 * particular the UVMRobot implements the same AgentWebService interface that
 * is available on the remote Agent robot. This means it looks like the remote
 * robot to applications accessing it.
 *
 * @author James Caska The UVMRobot proxy implements the  [JC] September 15,
 *         2005   Separated JPB2Robot into implementation of UVMRobot which
 *         gives better decoupling of the services available to each UVMRobot
 *         and the specific robot implementation provided by the instance of
 *         the JPB2Robot -- later will probably need JPB2Jnr robot etc
 */
public class JPB2JrRobot extends UVMRobot
{
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/jobotjr.gif");
	private static final String	CONFIG_XML			= Simulator
															.getRelativePath("./Junior/joBotJr.xml");
	private static final int	DIAMETER_IN_IMAGE	= 100;

	public static final double	BASE_RADIUS			= 0.090;
	public static final double	WHEEL_RADIUS		= 0.045;
	public static final int		RED_LED				= 0;
	public static final int		YELLOW_LED			= 1;
	public static final int		GREEN_LED			= 2;
	public static final int		BLUE_LED			= 3;
	
	//protected double distanceFromWall = 1.8;
	
	/** Not Documented yet */
	JoBotServoMotor[]			servoMotors;

	/**
	 * Creates a new UVMRobot instance
	 *
	 * @param name
	 * @param positionX
	 * @param positionY
	 */
	public JPB2JrRobot(String name, double positionX, double positionY)
	{
		/*
		 * friction = 7.0
		 * diameter = 2.0
		 * mass = 3.0
		 */
		super(name, 7.0, positionX, positionY, BASE_RADIUS * 2, 3);

		try
		{
			// If > 1.8, Jobot will get stuck to the wall with his butt
			// If < 1.8, you will see a space between the Jobot and the walls.
			this.distanceFromWallX = 1.8;
			this.distanceFromWallY = 1.8;
			// Create the sensors
			setSensors(new Sensor[6]);
	
			// addSensor parameters: sensorClassName, radius, position [, direction]
			getSensors()[0] = addSensor("DistanceShortSensor", BASE_RADIUS, 150, 110);  	// Left
			getSensors()[1] = addSensor("DistanceShortSensor", BASE_RADIUS, 30, 70);		// Right
			getSensors()[2] = addSensor("FieldSensor", BASE_RADIUS, 60);					// Left / Middle
			getSensors()[3] = addSensor("FieldSensor", BASE_RADIUS, 120);					// Right
			getSensors()[4] = addSensor("IRSensor", BASE_RADIUS, 300, 120);					// Left
			getSensors()[5] = addSensor("IRSensor", BASE_RADIUS, 240, 60);					// Right
		}
		catch( ReflectionException e )
		{
			e.printStackTrace();
		}
		// Create the leds
		setLeds(new Led[4]);
		getLeds()[GREEN_LED] = new Led(java.awt.Color.GREEN, 0);
		getLeds()[RED_LED] = new Led(java.awt.Color.RED, 0);
		getLeds()[YELLOW_LED] = new Led(java.awt.Color.YELLOW, 0);
		getLeds()[BLUE_LED] = new Led(java.awt.Color.BLUE, 0);

		loadApp(CONFIG_XML);
		vbbRTI.start();

		if (servoMotors != null)
		{
			setActors(new Actor[2]);
			getActors()[0] = servoMotors[0];
			getActors()[1] = servoMotors[1];
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	protected String getConfigXMLDoc()
	{
		return CONFIG_XML;
	}

	/**
	 * This returns the sensor values
	 *
	 * @param channel DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public double getADCSample(int channel)
	{
		if (getSensors() == null)
		{
			System.out.println("Sample requested by null sensorValues");

			return 0;
		}

		// if (channel == 3) Debug.printDebug("Reading channel " + channel);
		return getSensors()[channel].getValue() / 1000;
	}

	/* (non-Javadoc)
	 * @see javaBot.PhysicalObject#createGraphicalRepresentation()
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE, true);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param vout DOCUMENT ME!
	 * @param pinId DOCUMENT ME!
	 */
	public void digitalOutputPinChanged(boolean vout, int pinId)
	{
		if (vout == true)
		{
			getLeds()[pinId].setValue(1.0);
		}
		else
		{
			getLeds()[pinId].setValue(0.0);
		}
	}

	/**
	 * Loads the application
	 *
	 * @param configXML
	 */
	public void loadApp(String configXML)
	{
		System.out.println("Loading from config file: " + configXML);

		/**
		 * If there is an existing simulation RTI then we need to rip it up and
		 * release the resources associated with the RTI so we can start over
		 */
		if (vbbRTI != null)
		{
			vbbRTI.ripUp();
		}

		vbbRTI = new OpenVBBRTIImpl();

		controller = new JoBotJPB2Controller(this, .1, (IopenVBBRTI) vbbRTI, configXML);

		/**
		 * Register JoBotServoMotors as ServoPulseListeners
		 */
		servoMotors = new JoBotServoMotor[2];

		for (int i = 0; i < servoMotors.length; i++)
		{
			servoMotors[i] = new JoBotServoMotor(0.0, 156, 
								1 * Units.MILLISECONDS, 2 * Units.MILLISECONDS,
								"S148");
			controller.registerServoPulseListener(servoMotors[i], i);
		}

		/**
		 * Register ADCSampleListeners to read the sensors
		 */
		controller.registerADCSampleListener(this, 0);
		controller.registerADCSampleListener(this, 1);
		controller.registerADCSampleListener(this, 2);
		controller.registerADCSampleListener(this, 3);
		controller.registerADCSampleListener(this, 4);
		controller.registerADCSampleListener(this, 5);

		/**
		 * Register DigitalOutputListeners to read update the LED values
		 */
		controller.registerDigitalOutputListener(this, RED_LED);
		controller.registerDigitalOutputListener(this, YELLOW_LED);
		controller.registerDigitalOutputListener(this, GREEN_LED);
		controller.registerDigitalOutputListener(this, BLUE_LED);
		controller.setPortBDIP(15); // Initialize the DIP settings
	}

	/* (non-Javadoc)
	 * @see javaBot.MovableObject#update(double)
	 * To make sure the user does not have to remember the motors are
	 * driven as if they are both running forward.
	 * Here the command for the right wheel is reversed to reflect
	 * the fact that both wheels run in opposite directions.
	 */
	public void updatePosition(double elapsed)
	{
		double wl = 0 - servoMotors[0].getValue();
		double wr = servoMotors[1].getValue();

		if (servoMotors != null)
		{
			double rvl = (WHEEL_RADIUS * wl) / 3.24 * Math.PI;
			double rvr = (WHEEL_RADIUS * wr) / 3.24 * Math.PI;
			double rv = (rvl + rvr) / 2;
			double rr = (rvl - rvr) / BASE_RADIUS;
			setDrivingVelocityX(rv * Math.sin(rotation));
			setDrivingVelocityY(0 - (rv * Math.cos(rotation)));
			this.setRotationSpeed(rr);
		}
	}

	/**
	 * Get a led's on/off status
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getGreenLed()
	{
		return (getLeds()[GREEN_LED].getValue() != 0);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getRedLed()
	{
		return (getLeds()[RED_LED].getValue() != 0);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getYellowLed()
	{
		return (getLeds()[YELLOW_LED].getValue() != 0);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getBlueLed()
	{
		return (getLeds()[BLUE_LED].getValue() != 0);
	}
}
