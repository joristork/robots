package javaBot.Junior;

import com.muvium.UVMRunnable;
import com.muvium.apt.PeriodicTimer;
import com.muvium.apt.PeripheralFactory;
import com.muvium.apt.TimerEvent;
import com.muvium.apt.TimerListener;
import com.muvium.io.PortIO;

/**
 * The joBot Junior version uses only two motors and has between zero and eight
 * sensors.  
 * The possible sensors are: 
 * 0 - Touch sensor left and 
 * 1 - Touch sensor right 
 * 2 - Ground sensor left and 
 * 3 - Ground sensor right 
 * 4 - IR sensor left and 
 * 5 - IR sensor right 
 * The motors are connected as follows: 
 * 0 - Motor left 
 * 1 - Motor right  
 * 
 * UVMDemo is a behavioral demonstration which incorporates various behaviors which 
 * are set by current state. The current state can be set either remotely using the WebService or
 * by DIP switch which will override it. The current behavior will continue to
 * execute until it changes manually.  A possible next step would be to
 * include an intelligent supervisor which  determines which behavior should
 * be presently executing.
 *
 * @author Peter van Lith, James Caska 
 * Ver 1.0 - 01-10-2005 First version copied from UVMDemo
 * Ver 2.0 - 10-11-2006 New Jobot Jr Frame version
 */

// Made changes to all modules and to RobotGUI, TachoSensor
// ToDo: Vaststellen karakteristiek IR sensoren, afstand en sensorhoek
//       en gevoeligheid daglicht
// ToDo: Vaststellen karakteristiek fieldsensor, kleuren en gevoeligheid daglicht
// ToDo: Vaststellen karakteristiek motoren, lineariteit en snelheid
// ToDo: Controle juistheid formules en implementatie voor 2 en 3 wielen
// ToDo: Controle juiste implementatie snelheden en afmetingen robots

public class UVMDemo extends UVMRunnable implements AgentWebService, TimerListener
{
	/**
	 * Resources Section String resources are statically and so can be very
	 * useful for creating large string resources to store lookup tables etc
	 */
	private String testMacro = 
		"\u0000\u0064\u0000" + //   0 , 100 , 0 
		"\u0032\u0056\u0000" + //  50 ,  86 , 0 
		"\u0064\u0000\u0000" + // 100 ,   0 , 0
		"\u0056\uFFCE\u0000" + //  86 , -50 , 0
		"\u0032\uFFAA\u0000" + //  50 , -86 , 0
		"\u0000\uFF9C\u0000" + //   0 ,-100 , 0
		"\uFFCE\uFFAA\u0000" + // -50 , -86 , 0
		"\uFFAA\uFFCE\u0000" + // -86 , -50 , 0
		"\uFF9C\u0000\u0000" + //-100 ,   0 , 0
		"\uFFAA\u0032\u0000" + // -86 ,  50 , 0
		"\uFFCE\u0056\u0000";	// -50 ,  86 , 0
	
	private JobotBaseController	joBot;
	private PeriodicTimer			stateModelTick;
	private PeriodicTimer			behaviorServiceTick;
	private Behavior				currentBehavior;

	//State Model definitions
	public static final int			STATE_IDLE				= 0;
	public static final int			STATE_CALIBRATE			= 1;  // Shows values of all sensors
	public static final int			STATE_TEST				= 2;  // Makes a standard moving pattern
	public static final int			STATE_DRIVE				= 3;
	public static final int			STATE_FIND_BALL			= 4;
	public static final int			STATE_DRIBBLE_BALL		= 5;
	public static final int			STATE_AVOID_OBSTACLE	= 6;
	public static final int			STATE_GOTO_WHITE		= 7;
	public static final int			STATE_GOTO_BLACK		= 8;
	public static final int			STATE_GOTO_CENTER		= 9;
	public static final int			STATE_SHOOT_BALL		= 10;
	
	/**
	 * Dip Switch Settings  The DIP Switch sets the mode and can be set either
	 * manually or remotely. The currentDIP setting is updated either by a
	 * webService call OR by changing the DIP value.
	 */
	private int						currentState		= -1;
	private int						previousState		= -1;

	/**
	 * WebService Interfaces WebServices are remotely accessible Remote Method
	 * Invocation. The AgentWebService is a WSDL ( Web Service Description
	 * Language) Interface which defines the following methods which are
	 * exposed by muvium as WebService Methods     
	 * public int getSensor(int sensor); 
	 * public int getState();     
	 * public void setState(int dip);
	 * public void vector(int vx, int vy, int omega);
	 * public void drive(int vx, int vy, int vz);
	 */
	
	/**
	 * [WebService]
	 *
	 * @param vx TODO PARAM: DOCUMENT ME!
	 * @param vy TODO PARAM: DOCUMENT ME!
	 * @param omega TODO PARAM: DOCUMENT ME!
	 */
	public void vector(int vx, int vy, int omega)
	{
		joBot.drive(vx, vy);
	}

	/**
	 * [WebService]
	 *
	 * @param vx TODO PARAM: DOCUMENT ME!
	 * @param vy TODO PARAM: DOCUMENT ME!
	 * @param vz TODO PARAM: DOCUMENT ME!
	 */
	public void drive(int vx, int vy, int vz)
	{
		joBot.drive(vx, vy);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param vl TODO PARAM: param description
	 * @param vr TODO PARAM: param description
	 */
	public void drive(int vl, int vr)
	{
		joBot.drive(vl, vr);
	}

	/**
	 * [WebService] getSensor returns the current ADC sensor reading of the
	 * <code>sensor</code> sensor which represents what the robot currently
	 * sees from this sensor
	 *
	 * @param sensor TODO PARAM: DOCUMENT ME!
	 *
	 * @return ADC value 0..1023  [WebService]
	 */
	public int getSensor(int sensor)
	{
		return joBot.getSensor(sensor);
	}

	/**
	 * [WebService] getDIP returns the current value of the robot DIP switch
	 * allowing the a remote representation of the Robot to updated
	 *
	 * @return dipValue 0..15 which is the current mode of the robot
	 *         [WebService]
	 */
	public int getState()
	{
		return currentState;
	}

	/**
	 * [WebService] setState overrides the currentDIP value which allows the
	 * robot mode  to be set remotely
	 *
	 * @param newState - value 0..15 which sets the current mode of the robot
	 */
	public void setState(int newState)
	{
		int state = 0;
		if (newState == -1)
			state = previousState;
		else
			state = newState;
		processState(state);
//		System.out.print("state = ");
//		System.out.println(String.valueOf(state));
	}

	/**
	 * [WebService] reportState sets the reporting level in the robot
	 * This allows the remote machine to define if test output is generated
	 */
	public void reportState(int level)
	{
		joBot.reportState(level);
	}

	/**
	 * HeartBeat and check for manual state change using physical DIP switches
	 *
	 * @param e TODO PARAM: DOCUMENT ME!
	 */
	public void Timer(TimerEvent e)
	{
		// Display a HeartBeat so we know things are still running
			joBot.heartBeat();

			// Check to see if the DIP Switch has changed
			int dip = (PortIO.getPort(PortIO.PORTB) >> 4) ^ 0x0F;

			if (dip != previousState)
			{
				setState(dip);
				previousState = dip;
//				System.out.print("dip = ");
//				System.out.println(String.valueOf(dip));
			}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param dipValue TODO PARAM: param description
	 */
	public void processState(int dipValue)
	{
		currentState = dipValue;

		if (currentBehavior != null)
		{
			currentBehavior.stop();
			currentBehavior = null;
		}

		switch (dipValue)
		{
			case STATE_IDLE:
				joBot.setStatusLeds(false, false, false, false);
				drive(0, 0);
				break;
			case STATE_CALIBRATE: // 1
				currentBehavior = new CalibrateBehavior(joBot, behaviorServiceTick, 1000);
				break;
			case STATE_TEST: // 2
				currentBehavior = new MapReaderBehavior(joBot, behaviorServiceTick, 1000,
						testMacro);
				break;
			case STATE_DRIVE: // 3
				currentBehavior = new DriveBehavior(joBot, behaviorServiceTick, 100);
				break;
			case STATE_FIND_BALL: // 4
				currentBehavior = new FindBallBehavior(joBot, behaviorServiceTick, 100);
				break;
			case STATE_DRIBBLE_BALL: // 5
				currentBehavior = new DribbleBallBehavior(joBot, behaviorServiceTick, 100);
				break;
			default:
				drive(0, 0);
				break;
		}
	}

	public void run()
	{
		try
		{
			/**
			 * Setup the DIP Switch
			 */
			PortIO.setTris(0xF0, PortIO.PORTB);
			PortIO.setProperty(PortIO.PORTB, PortIO.PROPERTY_PORT_PULLUP_ON);
			PortIO.setTris(0x0F, PortIO.PORTE);
			joBot = new JobotBaseController(getPeripheralFactory(), this);
			joBot.setStatusLeds(false, false, false, false);
			drive(0, 0);
			joBot.tone(0);

			/**
			 * The stateModel determines the current behavior and refreshes the
			 * status lights
			 */
			stateModelTick = getPeripheralFactory().createPeriodicTimer(this, 500,
					PeripheralFactory.EVENT_PRIORITY_BACKGROUND);
			behaviorServiceTick = getPeripheralFactory().createPeriodicTimer(null, 1000,
					PeripheralFactory.EVENT_PRIORITY_URGENT);
			previousState = -1;

			try
			{
				stateModelTick.start();
			}
			catch (Exception e)
			{}

			while (true)
			{
				Thread.sleep(100);
			}
		}
		catch (Exception e)
		{
			joBot.setStatusLeds(true, true, true, true);
		}
	}

	public int getCurrentState()
	{
		return currentState;
	}
}
