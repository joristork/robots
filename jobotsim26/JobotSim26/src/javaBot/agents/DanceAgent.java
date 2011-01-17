/*
 * Created on Jun 28, 2005
 */
package javaBot.agents;

import javaBot.Debug;
import javaBot.Robot;

/**
 * DOCUMENT ME!
 *
 * @author Seth  The BallLover agent wants to go as close as possible to an IR
 *         emitting source
 */
public class DanceAgent extends JobotAgent
{
	final int				AMBIENT_LIGHT		= 200;
	final int				READING_TO_CONTROL	= 200;

	public static final int	STH					= 240;
	public static final int	STHM				= 1360;
	public static final int	DSP					= 20;

	/**
	 * Creates a new DanceAgent object.
	 */
	public DanceAgent()
	{
		super();
	}

	/**
	 * construct a new BallLover
	 *
	 * @param robot TODO PARAM: DOCUMENT ME!
	 */
	public DanceAgent(Robot robot)
	{
		super(robot);
	}

	/**
	 * start the thread
	 */
	public void run()
	{
		setRunning(true);

		if (getRobot() == null)
		{
			return;
		}

		try
		{
			while (isRunning())
			{
				vectorDrive(1, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(-1, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, -1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, -1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 0, 3);
				sleep(450);
				vectorDrive(0, 0, 0);
				vectorDrive(-1, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(1, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, -1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, -1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 0, -3);
				sleep(450);
				vectorDrive(0, 0, 0);

				vectorDrive(1, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, 1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(-1, 0, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
				vectorDrive(0, -1, 0);
				sleep(500);
				vectorDrive(0, 0, 0);
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error in BallLover: " + e.toString());
		}
	}
}
