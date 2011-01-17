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
package javaBot.UVMSpeech;

import com.muvium.apt.PeriodicTimer;
import com.muvium.apt.TimerEvent;
import com.muvium.apt.TimerListener;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */
public abstract class Behavior implements TimerListener
{
	private JobotBaseController	joBot;
	private PeriodicTimer		serviceTick;
	
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
			e.printStackTrace();
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

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the joBot.
	 */
	public JobotBaseController getJoBot()
	{
		return joBot;
	}
}
