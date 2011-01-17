/*
 * Created on Aug 13, 2004
 * The nehavior base class defines the methods that need to be
 * implemented in every behavior. The stop and timer functions
 * are defined as part of the base class.
 *
 * @author James Caska
 */
package javaBot.agents;

/**
 * The MapReader Behavior is a state machine that reads either a drive or a
 * vector 'map' and every period changes the values of the drivers to
 * represent these values
 */
import com.muvium.apt.PeriodicTimer;
import com.muvium.apt.TimerEvent;
import com.muvium.apt.TimerListener;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision$
 * last changed 20-02-2006
 *
 * TODO CLASS: DOCUMENT ME! 
 */
public abstract class Behavior implements TimerListener
{
	JobotBaseController	joBot;
	PeriodicTimer		serviceTick;
	int					count;

	/**
	 * Creates a new Behavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param period TODO PARAM: DOCUMENT ME!
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
			//Failed to start
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public abstract void doBehavior();

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param e TODO PARAM: param description
	 */
	public void Timer(TimerEvent e)
	{
		doBehavior();
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void stop()
	{
		serviceTick.removeTimerListener(this);
		serviceTick.stop();
		serviceTick = null;
	}
}
