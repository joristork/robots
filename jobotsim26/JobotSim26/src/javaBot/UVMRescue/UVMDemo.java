package javaBot.UVMRescue;

import com.muvium.UVMRunnable;
import com.muvium.apt.PeriodicTimer;
import com.muvium.apt.PeripheralFactory;
import com.muvium.apt.TimerEvent;
import com.muvium.apt.TimerListener;
import com.muvium.io.PortIO;

/**
  * RescueBehavior.java
 * Een programma dat een jobot bestuurt zodat de jobot
 * de zwarte lijn op het rescuefield volgt naar het moeras en dan
 * een slachtoffer redt. De jobot gebruikt twee sensoren en 
 * neemt de gele afkorting wel.
 * 
 * Floor Sietsma
 * 0418803
 * fsietsma@science.uva.nl
 * 
 * Steven Klein
 * 0597872
 * sklein@science.uva.nl
 * 
 * Universiteit van Amsterdam
 * Bij het project Robotica en Systemen
 * Woensdag 1 februari 2006
 */

public class UVMDemo extends UVMRunnable implements AgentWebService, TimerListener
{
	private JobotBaseController	joBot;
	private PeriodicTimer		stateModelTick;
	private PeriodicTimer		behaviorServiceTick;
	private Behavior			currentBehavior;

	//State Model definitions
	public static final int		STATE_IDLE				= 0;
	public static final int		STATE_CALIBRATE			= 1;
	public static final int		STATE_RESCUE_START		= 2;
	public static final int		STATE_RESCUE_SHORTCUT	= 3;
	
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
	 * exposed by muvium as WebService Methods     public int getSensor(int
	 * sensor); public int     getState();     public void setState(int dip);
	 * public void vector(int vx, int vy, int omega); public void drive(int
	 * vx, int vy, int vz);
	 *
	 * @param vx TODO PARAM: DOCUMENT ME!
	 * @param vy TODO PARAM: DOCUMENT ME!
	 * @param omega TODO PARAM: DOCUMENT ME!
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
		joBot.vectorDrive(vx, vy, omega);
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
		joBot.drive(vx, vy, vz);
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
	 * @param dipValue - value 0..15 which sets the current mode of the robot
	 */
	public void setState(int dipValue)
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
				joBot.setStatusLeds(false, false, false);
				drive(0, 0, 0);

				break;

			case STATE_CALIBRATE:
				currentBehavior = new CalibrateBehavior(joBot, behaviorServiceTick, 500);
				break;

			case STATE_RESCUE_START:
				currentBehavior = new RescueBehaviorStart(joBot, behaviorServiceTick, 50);
				break;

			case STATE_RESCUE_SHORTCUT:
				currentBehavior = new RescueBehaviorShortcut(joBot, behaviorServiceTick, 50);
				break;

			default:

				//	PortIO.setPort((dipValue ^ 0x03) << 2, PortIO.PORTB);
				joBot.setStatusLeds((dipValue & 0x04) != 0, (dipValue & 0x02) != 0,
						(dipValue & 0x01) != 0);
				drive(0, 0, 0);
				break;
		}
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
			System.out.print("dip = ");
			System.out.println(String.valueOf(dip));
			setState(dip);
			previousState = dip;
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void run()
	{
		System.out.println("JPBRescueDemo V0.0");

		try
		{
			/**
			 * Setup the DIP Switch
			 */
			PortIO.setTris(0xF0, PortIO.PORTB);
			PortIO.setProperty(PortIO.PORTB, PortIO.PROPERTY_PORT_PULLUP_ON);
			joBot = new JobotBaseController(getPeripheralFactory());
			joBot.setStatusLeds(false, false, false);
			drive(0, 0, 0);

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
			joBot.setStatusLeds(true, true, true);
			System.out.println("Error");
		}
	}
}
