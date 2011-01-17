/*
 * Created on 18-jun-2004
 *
 */
package javaBot.UVM;

import javaBot.Jobot;
import javaBot.Robot;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public abstract class JobotAgent extends Agent
{
	// Used for heartbeat
	private double	lastBeat	= 0;

	/**
	 * Creates a new JobotAgent object.
	 */
	protected JobotAgent()
	{
		super();
	}

	/**
	 * Creates a new JobotAgent object.
	 *
	 * @param robot TODO PARAM: DOCUMENT ME!
	 */
	protected JobotAgent(Robot robot)
	{
		super(robot);
	}

	//totalElapsed += elapsed;
	//if (totalElapsed >= 0.5) {
	//    if (leds[RED_LED].getValue() > 0.0)
	//        leds[RED_LED].setValue(0.0);
	//    else
	//        leds[RED_LED].setValue(1.0);
	//    totalElapsed = totalElapsed -0.5;
	//}

	/**
	 * Initial vector drive
	 *
	 * @param vx TODO PARAM: DOCUMENT ME!
	 * @param vy TODO PARAM: DOCUMENT ME!
	 * @param omega TODO PARAM: DOCUMENT ME!
	 */
	public void vectorDrive(double vx, double vy, double omega)
	{
		double[] a = {(6 / 6.) * Math.PI, (10 / 6.) * Math.PI, (2 / 6.) * Math.PI};
		double[] fx = new double[3];
		double[] fy = new double[3];

		for (int i = 0; i < 3; i++)
		{
			// x component of the wheel's direction
			fx[i] = Math.cos(a[i]);

			// y component of the wheel's rolling direction
			fy[i] = Math.sin(a[i]);
		}

		double w0 = ((fx[0] * vx) + (fy[0] * vy) + (Jobot.BASE_RADIUS * omega))
				/ Jobot.WHEEL_RADIUS;
		double w1 = ((fx[1] * vx) + (fy[1] * vy) + (Jobot.BASE_RADIUS * omega))
				/ Jobot.WHEEL_RADIUS;
		double w2 = ((fx[2] * vx) + (fy[2] * vy) + (Jobot.BASE_RADIUS * omega))
				/ Jobot.WHEEL_RADIUS;

		if ((getRobot().getActors() != null) && (getRobot().getActors().length == 3))
		{
			getRobot().getActors()[0].setValue(w0);
			getRobot().getActors()[1].setValue(w1);
			getRobot().getActors()[2].setValue(w2);
		}
	}

	/**
	 * This run function only takes care of the heartbeat. So if a heartbeat is
	 * required, make sure to call heartbeat() in the run method of the agent.
	 */
	protected void heartbeat()
	{
		if ((System.currentTimeMillis() - lastBeat) > 500)
		{
			if (getRobot().getLeds().length > 0)
			{
				getRobot().getLeds()[0].setValue(1 - getRobot().getLeds()[0].getValue());
			}
		}

		lastBeat = System.currentTimeMillis();
	}
}
