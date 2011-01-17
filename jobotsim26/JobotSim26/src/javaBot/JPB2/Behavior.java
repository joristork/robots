/*
 * Created on Aug 13, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * The Behavior base class defines the methods that need to be
 * implemented in every behavior. The stop and timer functions
 * are defined as part of the base class.
 */
package javaBot.JPB2;

import com.muvium.apt.PeriodicTimer;
import com.muvium.apt.TimerEvent;
import com.muvium.apt.TimerListener;

/**
 * Bahavior is the base class for all behaviors.
 * A behavior needs to implement the doBehavior method.
 * This method receives controle every servicetick.
 * The period of this tick is defined when creating a new
 * instance of this class, usually on UVMDemo.
 */
public abstract class Behavior implements TimerListener
{
	private JobotBaseController	joBot;
	private PeriodicTimer		serviceTick;
	private int					count;

	/**
	 * Creates a new Behavior object.
	 *
	 * @param initJoBot 		- Is the basecontroller used by the robot
	 * @param initServiceTick 	- Is the timer that controls the initiation of a serviceTick
	 * @param period 			- Determines how often the method is invoked.
	 */
	public Behavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick, int period)
	{
		serviceTick = initServiceTick;
		joBot = initJoBot;
		serviceTick.setPeriod(period);

		try
		{
			serviceTick.addTimerListener(this);
			serviceTick.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method needs to be implemented by each behavior.
	 * It gets control when a serviceTick is generated.
	 */
	public abstract void doBehavior();

	/**
	 * This timer generates the clock interrupts that generate
	 * the serviceTick
	 *
	 * @param e - This timerEvent is not used by the behavior
	 */
	public void Timer(TimerEvent e)
	{
		doBehavior();
	}

	/**
	 * Stop is used to stop the current behavior.
	 */
	public void stop()
	{
		serviceTick.removeTimerListener(this);
		serviceTick.stop();
		serviceTick = null;
	}

	/**
	 * getJobot returns the BaseController
	 *
	 * @return Returns the joBot Base Controller
	 */
	public JobotBaseController getJoBot()
	{
		return joBot;
	}
}
