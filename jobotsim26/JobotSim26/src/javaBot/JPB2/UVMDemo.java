package javaBot.JPB2;

import com.muvium.UVMRunnable;
import com.muvium.apt.PeriodicTimer;
import com.muvium.apt.PeripheralFactory;
import com.muvium.apt.TimerEvent;
import com.muvium.apt.TimerListener;
import com.muvium.io.PortIO;

/**
 * When viewed from the top, the servo and sensor assignments are: 
 * Servo Assignment Sensor Assignment 
 *    0 					1 2 
 *   2 1 					 0 
 *    C 					 C 
 *    
 * C is the location of the JPB viewed from the top 
 * Wheel movement is as follows: 
 * 		100 = turn clockwise (right) 
 *  	-100 = turn counterclockwise (left)
 * UVMDemo is a behavioral demonstration which incorporates various 
 * behaviors which are set by current state. The current state can be set 
 * either remotely using the WebService or by the DIP switch which will 
 * override it. The current behavior will continue to execute until it 
 * changes manually. 
 * A possible next step would be to include an intelligent supervisor 
 * which determines which behavior should presently be executing.
 * 
 * @author Peter van Lith, James Caska 
 * 	Ver 0.5 - 07-09-2004 Modified wheel base
 *  Ver 0.6 - 21-09-2004 Modified messages and compiler interface 
 *  Ver 0.7 - 24-09-2004 Fixed vector drive 
 *  Ver 0.8 - 04-10-2004 Included NewBehavior for students 
 *  Ver 0.9 - 30-12-2004 Included new wall follower code and new Flee behavior 
 *  Ver 1.0 - 01-09-2005 JPB2 Version
 *  Ver 1.1 - 29-04-2006 Included servo linearizer and chase ball
 *  Ver 1.2 - 27-07-2006 Included reporting facilities (27%)
 */

// ToDo: Gyrate does not work properly
// ToDo: replace all output statements with reporting functions

public class UVMDemo extends UVMRunnable implements AgentWebService, TimerListener
{
	/**
	 * Resources Section String resources are statically and so can be very
	 * useful for creating large string resources to store lookup tables etc
	 */
	private String	gyrateMacro	= 
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
	private String	testMacro = 
		"\u0000\u0000\u0000" + //   0 , 0 , 0
		"\u0064\u0064\u0064" + //   100 ,  100 ,  100
		"\uFF9C\uFF9c\uFF9C" + //  -100 , -100 , -100
		"\u0000\u0000\u0000" + //     0 ,    0 ,    0
		"\u0064\u0000\u0000" + //   100 ,    0 ,    0
		"\u0000\u0064\u0000" + //     0 ,  100 ,    0
		"\u0000\u0000\u0064" + //     0 ,    0 ,  100
		"\uFF9C\u0000\u0000" + //  -100 ,    0 ,    0
		"\u0000\uFF9C\u0000" + //     0 , -100 ,    0
		"\u0000\u0000\uFF9C" + //     0 ,    0 , -100
		"\u0000\u0000\u0000";	//     0 ,    0 ,    0
	
	public JobotBaseController	joBot;
	private PeriodicTimer		stateModelTick;
	private PeriodicTimer		behaviorServiceTick;
	private Behavior			currentBehavior;

	//State Model definitions
	public static final int		STATE_IDLE				= 0;
	public static final int		STATE_CALIBRATE			= 1;
	public static final int		STATE_CAL_TEST			= 2;
	public static final int		STATE_TEST				= 3;
	public static final int		STATE_CURIOUS			= 4;
	public static final int		STATE_FLEE				= 5;
	public static final int		STATE_GYRATE_VECTOR		= 6;
	public static final int		STATE_GYRATE_DRIVE		= 7;
	public static final int		STATE_WALL_HUG			= 8;
	public static final int		STATE_YOYO_NORTH_SOUTH	= 9;
	public static final int		STATE_YOYO_EAST_WEST	= 10;
	public static final int		STATE_CHASE_BALL		= 11;

	/**
	 * Dip Switch Settings  The DIP Switch sets the mode and can be set either
	 * manually or remotely. The currentDIP setting is updated either by a
	 * webService call OR by changing the DIP value.
	 */
	private int					currentState			= -1;
	private int					previousState			= -1;

	/**
	 * WebService Interfaces WebServices are remotely accessible Remote Method
	 * Invocation. The AgentWebService is a WSDL ( Web Service Description
	 * Language) Interface which defines the following methods which are
	 * exposed by muvium as WebService Method     
	 * 
	 * public int getSensor(int sensor); 
	 * public int getState();     
	 * public void setState(int dip);    
	 * public void reportState(int level); 
	 * public void vector(int vx, int vy, int omega);
	 * public void drive(int vx, int vy, int vz);
	 */
	
	/**
	 * [WebService] vector calls the vectorDrive function
	 *
	 * @param vx	- speed and direction in X axis
	 * @param vy	- speed and direction in Y axis
	 * @param omega	- rotation of the robot
	 */
	public void vector(int vx, int vy, int omega)
	{
		joBot.vectorDrive(vx, vy, omega);
	}

	/**
	 * [WebService] drive calls the drive function which directly controls each motor
	 *
	 * @param vx	- speed and direction of motor 0
	 * @param vy	- speed and direction of motor 1
	 * @param vz	- speed and direction of motor 2
	 */
	public void drive(int vx, int vy, int vz)
	{
		joBot.drive(vx, vy, vz);
	}

	/**
	 * [WebService] getSensor returns the current ADC sensor reading of the
	 * <code>sensor</code> sensor which represents what the robot currently
	 * sees from this sensor
	 *
	 * @param sensor	- sensor number corresponding to sensor definition in 
	 * 					  javaBot.JPB2Robot moduel
	 *
	 * @return ADC value 0..1023  [WebService]
	 */
	public int getSensor(int sensor)
	{
		return joBot.getSensor(sensor);
	}

	/**
	 * [WebService] getState returns the current value of the robot DIP switch
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
	 * [WebService] reportState sets the reporting level in the robot
	 * This allows the remote machine to define if test output is generated
	 */
	public void reportState(int level)
	{
		joBot.reportState(level);
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
		{
			state = previousState;
		}
		else
		{
			state = newState;
		}

//		System.out.print("state = ");
//		System.out.println(String.valueOf(state));
		processState(state);
	}

	/**
	 * HeartBeat and check for manual state change using physical DIP switches
	 *
	 * @param e TimerEvent is not used here
	 */
	public void Timer(TimerEvent e)
	{
		// Display a HeartBeat so we know things are still running
		joBot.heartBeat();

		// Check to see if the DIP Switch has changed
		int dip = (PortIO.getPort(PortIO.PORTB) >> 4) ^ 0x0F;
		
		if (dip != previousState)
		{
//			System.out.print("dip = ");
//			System.out.println(String.valueOf(dip));
			setState(dip);
			previousState = dip;
		}
	}

	/**
	 * [WebService] setState overrides the currentDIP value which allows the
	 * robot mode to be set remotely
	 *
	 * @param newState - value 0..15 which sets the current mode of the robot
	 */
	public void processState(int newState)
	{
		currentState = newState;

		if (currentBehavior != null)
		{
			currentBehavior.stop();
			currentBehavior = null;
		}

		switch (newState)
		{
			case STATE_IDLE:
				sleep(500);
				drive (0, 0, 0);
				break;
			case STATE_CALIBRATE:
				currentBehavior = new CalibrateBehavior(joBot, behaviorServiceTick, 1000, 
						newState == STATE_CAL_TEST);
				break;
			case STATE_CAL_TEST:
				currentBehavior = new CalTestBehavior(joBot, behaviorServiceTick, 50,
						newState == STATE_CAL_TEST);
				break;
			case STATE_TEST:
				currentBehavior = new MapReaderBehavior(joBot, behaviorServiceTick, 1000,
						testMacro, newState == STATE_GYRATE_VECTOR);
				break;
			case STATE_CURIOUS:
				currentBehavior = new CuriousBehavior(joBot, behaviorServiceTick, 50);
				break;
			case STATE_FLEE:
				currentBehavior = new FleeBehavior(joBot, behaviorServiceTick, 50);
				break;
			case STATE_WALL_HUG:
				currentBehavior = new WallHugBehavior(joBot, behaviorServiceTick, 50);
				break;
			case STATE_CHASE_BALL :
				currentBehavior = new ChaseBallBehavior(joBot, behaviorServiceTick, 50);
			break;		
			case STATE_GYRATE_VECTOR :
			case STATE_GYRATE_DRIVE :
		 		currentBehavior = new MapReaderBehavior(joBot, 
		 				behaviorServiceTick, 1000, gyrateMacro,
		 				newState == STATE_GYRATE_VECTOR);
		 		break;
			case STATE_YOYO_NORTH_SOUTH :
			case STATE_YOYO_EAST_WEST :
		 		currentBehavior = new YoYoBehavior(joBot,
		 				behaviorServiceTick, 1000, 5,
		 				newState == STATE_YOYO_NORTH_SOUTH);
		 		break;		
			default:
				joBot.setStatusLeds((newState & 0x04) != 0, (newState & 0x02) != 0,
						(newState & 0x01) != 0);
				drive(0, 0, 0);
				break;
		}
	}

	/**
	 * This is the program's mainline
	 */
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
			drive(0, 0, 0);
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
//			System.out.println("Error");
		}
	}
}
