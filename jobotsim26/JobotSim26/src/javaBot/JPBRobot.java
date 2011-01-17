package javaBot;

/**
 * Ver 0.0 - 01-06-2004 Ver 0.1 - 03-07-2004     -     Implemented Actors to
 * display servos     Included DoCommand webservice interfaces (preliminary)
 * Modified ADC output to integer value     Reversed direction of servo
 * motors Ver 0.2 - 01-08-2004 [JC]     Added commands as static variables
 * Integrated icd with Reset/Program/Run commands     Remoted simmode -
 * UVMRobot acts as an avatar to the real-robot. See note on avatar Ver 0.3 -
 * 14-08-2004        Added the WebService implementation and changed the
 * interface to better reflect what is available on the real robot agent
 * Avatar  When running as an avatar the simulation is a representation of the
 * real-robot and run side by side. All commands are passed through to the
 * real robot. The difference between the way the two run will be the
 * differences in the world stimulus they receive. One way to handle this is
 * to read the actual sensor values off the real device and plug them into the
 * simulation.
 */
import org.openVBB.interfaces.IopenVBBRTI;
import org.openVBB.robotKit.controllers.JoBotJPBController;
import org.openVBB.robotKit.interfaces.Units;
import org.openVBB.sim.rti.OpenVBBRTIImpl;

/**
 * [JC] The UVMProxyRobot implements a Robot 'brain' using the openVBB uVM
 * simulator. The JoBotJPBController is a wrapper for an openVBB circuit
 * simulation using the openVBB simulator with wiring as per the JPB
 * controller with electical connections provided as per the JPB
 * specification. The UVMRobot can also act as an avatar for a real robot. In
 * particular the UVMRobot implements the same AgentWebService interface that
 * is available on the remote Agent robot. This means it looks like the remote
 * robot to applications accessing it.
 *
 * @author James Caska The UVMRobot proxy implements the
 */
public class JPBRobot extends UVMRobot
{
	private static final String	IMAGE_FILE			= Simulator.getRelativePath("./resources/jobot.gif");
	private static final String	CONFIG_XML			= Simulator.getRelativePath("./UVM/joBot.xml");
	private static final int	DIAMETER_IN_IMAGE	= 100;

	public static final double	BASE_RADIUS			= 0.072;
	public static final double	WHEEL_RADIUS		= 0.020;

	/** Represents the leds in the led array */
	public static final int		RED_LED				= 0;
	public static final int		YELLOW_LED			= 1;
	public static final int		GREEN_LED			= 2;

	//	private int[] values= new int[5];

	/** Not Documented yet */
	JoBotServoMotor[]			servoMotors;

	/**
	 * Creates a new UVMRobot instance
	 *
	 * @param name
	 * @param positionX
	 * @param positionY
	 */
	public JPBRobot(String name, double positionX, double positionY)
	{
		/*
		 * friction = 7.0
		 * diameter = 2.0
		 * mass = 3.0
		 */
		super(name, 7.0, positionX, positionY, BASE_RADIUS * 2, 3);

		try
		{
			// Create the sensors
			setSensors(new Sensor[5]);
	
			// addSensor parameters: sensorClassName, radius, position [, direction]
			getSensors()[0] = addSensor("ReflectionSensor", BASE_RADIUS, 270);
			getSensors()[1] = addSensor("ReflectionSensor", BASE_RADIUS, 30);
			getSensors()[2] = addSensor("ReflectionSensor", BASE_RADIUS, 150);
			getSensors()[3] = addSensor("IRSensor", BASE_RADIUS, 270, 90);
			getSensors()[4] = addSensor("FieldSensor", BASE_RADIUS, 270);
		}
		catch( ReflectionException e )
		{
			e.printStackTrace();
		}
		
		// Create the leds
		setLeds(new Led[3]);
		getLeds()[GREEN_LED] = new Led(java.awt.Color.GREEN, 1);
		getLeds()[RED_LED] = new Led(java.awt.Color.RED, 1);
		getLeds()[YELLOW_LED] = new Led(java.awt.Color.YELLOW, 1);

		loadApp(CONFIG_XML);
		vbbRTI.start();

		if (servoMotors != null)
		{
			setActors(new Actor[3]);
			getActors()[0] = servoMotors[0];
			getActors()[1] = servoMotors[1];
			getActors()[2] = servoMotors[2];
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
		if (vout == false)
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

		controller = new JoBotJPBController(this, .1, (IopenVBBRTI) vbbRTI, configXML);

		/**
		 * Register JoBotServoMotors as ServoPulseListeners
		 */
		servoMotors = new JoBotServoMotor[3];

		for (int i = 0; i < servoMotors.length; i++)
		{
			servoMotors[i] = new JoBotServoMotor(0.0, 
								156, 1 * Units.MILLISECONDS, 2 * Units.MILLISECONDS,
								"S3003");
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

		/**
		 * Register DigitalOutputListeners to read update the LED values
		 */
		controller.registerDigitalOutputListener(this, RED_LED);
		controller.registerDigitalOutputListener(this, YELLOW_LED);
		controller.registerDigitalOutputListener(this, GREEN_LED);
		controller.setPortBDIP(15); // Initialize the DIP settings
	}

	/* (non-Javadoc)
	 * @see javaBot.MovableObject#update(double)
	 * Please note that the standard futaba servo's rotate in the opposite
	 * direction compared to the HiTec servos.
	 */
	public void updatePosition(double elapsed)
	{
		double w0 = servoMotors[0].getValue();
		double w1 = servoMotors[1].getValue();
		double w2 = servoMotors[2].getValue();

		if (servoMotors != null)
		{
			/*
			 * rvx and rvy are the x and y components of the
			 * driving velocity relative to the robot's orientation
			 */
			double rvx = (WHEEL_RADIUS / 3.) * ((-2 * w0) + w1 + w2);
			double rvy = (WHEEL_RADIUS / Math.sqrt(3.)) * (-w1 + w2);
			setDrivingVelocityX(0 - ((rvx * Math.cos(rotation)) - (rvy * Math.sin(rotation))));
			setDrivingVelocityY(0 - ((rvx * Math.sin(rotation)) + (rvy * Math.cos(rotation))));
			this.setRotationSpeed(0 - ((WHEEL_RADIUS / (3. * BASE_RADIUS)) * (w0 + w1 + w2)));
		}
	}

	/**
	 * Get a led's on/off status
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getGreenLed()
	{
		return (getLeds()[GREEN_LED].getValue() == 0.0);
	}

	/**
	 * Get a led's on/off status
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getRedLed()
	{
		return (getLeds()[RED_LED].getValue() == 0.0);
	}

	/**
	 * Get a led's on/off status
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getYellowLed()
	{
		return (getLeds()[YELLOW_LED].getValue() == 0.0);
	}

	/**
	 * Get a led's on/off status
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getBlueLed()
	{
		return false;
	}
}
