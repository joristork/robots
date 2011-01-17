/*
 * Created on Jun 21, 2005
 */
package javaBot.agents;

import javaBot.Debug;
import javaBot.FieldSensor;
import javaBot.Robot;
import javaBot.Sensor;

/**
 * DOCUMENT ME!
 *
 * @author A.J. Nieuwenhuizen  TODO: Make this work using the Peter van Lith
 *         tactics.
 */
public class RescueAgent2 extends JobotAgent
{
	/**
	 * Creates a new RescueAgent2 object.
	 */
	public RescueAgent2()
	{
		super();
	}

	/**
	 * construct a new RescueAgent
	 *
	 * @param robot TODO PARAM: DOCUMENT ME!
	 */
	public RescueAgent2(Robot robot)
	{
		super(robot);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 */
	public void goUp(Sensor sensor)
	{
		int val;
		double spd = 0.2;

		try
		{
			val = (int) sensor.getValue();
			vectorDrive(0, spd, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			vectorDrive(spd, 0, 0);
			sleep(1);
			vectorDrive(0, spd, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			val = (int) sensor.getValue();

			if (val < 150)
			{
				vectorDrive(spd, 0, 0);
				sleep(1);
				vectorDrive(0, 0, 0);
			}
			else
			{
				while (val > 150)
				{
					vectorDrive(-spd, 0, 0);
					sleep(1);
					vectorDrive(0, 0, 0);
					val = (int) sensor.getValue();
				}
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error in RescueAgent2: " + e.toString());
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 */
	public void goDown(Sensor sensor)
	{
		int val;
		double spd = 0.4;

		try
		{
			val = (int) sensor.getValue();
			vectorDrive(0, -spd, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			vectorDrive(-spd, 0, 0);
			sleep(1);
			vectorDrive(0, -spd, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			val = (int) sensor.getValue();

			if (val < 150)
			{
				vectorDrive(-spd, 0, 0);
				sleep(1);
				vectorDrive(0, 0, 0);
			}
			else
			{
				while (val > 150)
				{
					vectorDrive(spd, 0, 0);
					sleep(1);
					vectorDrive(0, 0, 0);
					val = (int) sensor.getValue();
				}
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error in RescueAgent2: " + e.toString());
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 */
	public void goRight(Sensor sensor)
	{
		int val;
		double spd = 0.4;

		try
		{
			val = (int) sensor.getValue();
			vectorDrive(spd, 0, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			vectorDrive(0, -spd, 0);
			sleep(1);
			vectorDrive(spd, 0, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			val = (int) sensor.getValue();

			if (val < 150)
			{
				vectorDrive(0, -spd, 0);
				sleep(1);
				vectorDrive(0, 0, 0);
			}
			else
			{
				while (val > 150)
				{
					vectorDrive(0, spd, 0);
					sleep(1);
					vectorDrive(0, 0, 0);
					val = (int) sensor.getValue();
				}
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error in RescueAgent2: " + e.toString());
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 */
	public void goLeft(Sensor sensor)
	{
		int val;
		double spd = 0.4;

		try
		{
			val = (int) sensor.getValue();
			vectorDrive(-spd, 0, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			vectorDrive(0, spd, 0);
			sleep(1);
			vectorDrive(-spd, 0, 0);
			sleep(1);
			vectorDrive(0, 0, 0);
			val = (int) sensor.getValue();

			if (val < 150)
			{
				vectorDrive(0, spd, 0);
				sleep(1);
				vectorDrive(0, 0, 0);
			}
			else
			{
				while (val > 150)
				{
					vectorDrive(0, -spd, 0);
					sleep(1);
					vectorDrive(0, 0, 0);
					val = (int) sensor.getValue();
				}
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error in RescueAgent2: " + e.toString());
		}
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
			Sensor sensor = getRobot().getSensors()[3];
			int val = 0;

			for (int i = 0; i < getRobot().getSensors().length; i++)
			{
				if (getRobot().getSensors()[i] instanceof FieldSensor)
				{
					sensor = getRobot().getSensors()[i];
				}
			}

			while (isRunning())
			{
				goUp(sensor);
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error in RescueAgent2: " + e.toString());
		}
	}
}
