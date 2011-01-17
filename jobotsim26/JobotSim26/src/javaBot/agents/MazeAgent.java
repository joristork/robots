/*
 * Created on Jun 9, 2004
 * @author bastiaan
 *
 */
package javaBot.agents;

import javaBot.Debug;
import javaBot.ReflectionSensor;
import javaBot.Robot;
import javaBot.World;
import javaBot.maze.Cell;
import javaBot.maze.MazeRobot;

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
public class MazeAgent extends JobotAgent
{
	/**
	 * Constructor
	 */
	public MazeAgent()
	{
		super();
	}

	/**
	 * Default constructor
	 *
	 * @param robot TODO PARAM: DOCUMENT ME!
	 */
	public MazeAgent(Robot robot)
	{
		super(robot);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			/*
			 * Started the Mazeagent with a sleep of 500ms to allow the application to build up the cells.
			 */
			sleep(500);
			Cell.getCell(0, 0);
		}
		catch (Exception e)
		{
			Debug.printError("Please start a maze first");

			return;
		}

		if (getRobot() instanceof MazeRobot)
		{
			Cell theCell = Cell.getCell(0, 0);
			Cell nextCell = null;
			setRunning(true);
			((MazeRobot) getRobot()).goToCell(0, 0);

			while (isRunning())
			{
				heartbeat();
				nextCell = moveToNextCell(theCell);
				waitForPosition();
				theCell = nextCell;

				if (theCell.getIndex() == 0)
				{
					setRunning(false);
				}
			}
		}
		else
		{
			getRobot().getLocation().setX(World.GRID_SIZE / 2);
			getRobot().getLocation().setY(World.HEIGHT - (World.GRID_SIZE / 2));

			getRobot().rotation -= (Math.PI / 2);
			wallHug();
		}

		Debug.printInfo("Ended follow maze");
		getRobot().setVelocityX(0);
		getRobot().setVelocityY(0);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param theCell TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Cell moveToNextCell(Cell theCell)
	{
		int direction = 0;
		Cell nextCell;
		int next;
		next = theCell.getNextCell();
		nextCell = Cell.getCell(next);

		if (theCell.getX() == nextCell.getX()) // Check up or down
		{
			if ((theCell.getY() - nextCell.getY()) == 1)
			{
				direction = MazeRobot.SOUTH; // Down
			}
			else
			{
				direction = MazeRobot.NORTH;
			}
		}
		else
		{
			if ((theCell.getX() - nextCell.getX()) == 1)
			{ // Now check left or right
				direction = MazeRobot.WEST;
			}
			else
			{
				direction = MazeRobot.EAST;
			}
		}

		if (next > 0)
		{
			//Debug.printDebug(
			//"Going to cell C" + next + " " + nextCell.X + "," + nextCell.Y);
			((MazeRobot) getRobot()).moveToCell(nextCell.getX(), nextCell.getY(), 0.1, direction);
		}

		return nextCell;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public synchronized void waitForPosition()
	{
		if (!checkInterrupt())
		{
			while (!((MazeRobot) getRobot()).moveReady())
			{
				try
				{
					sleep(1);
				}
				catch (Exception e)
				{}
			}
		}
	}

	// Wallhug follows the walls of a maze to find a way out
	private void wallHug()
	{
		heartbeat();

		try
		{
			sleep(500);
			ReflectionSensor rs = (ReflectionSensor) getRobot().getSensors()[0];
			if ((rs.getValue() == 0)
					|| (rs.convertValueToDistance(rs.getValue()) > 0.17))
			{
				vectorDrive(0, -0.1, 0);
				sleep(3000);
				vectorDrive(0, 0, -Math.PI / 2);

				sleep(1000);
			}
			else
			{
				vectorDrive(0, 0, Math.PI / 2);

				sleep(1000);
			}

			vectorDrive(0, 0, 0);

			// Set the agent straight, this is obviously a cheat..
			getRobot().setRotationSpeed(0);
			getRobot().rotation = (Math.PI / 2) * (Math.round(getRobot().rotation / (Math.PI / 2)));

			sleep(100);
			wallHug();
		}
		catch (Exception e)
		{
			Debug.printDebug(e.toString());
		}
	}

	// Used by wallhug
	private void tryLeft()
	{
		try
		{
			vectorDrive(0, 0, Math.PI / 2);
			sleep(1000);
			vectorDrive(0, 0, 0);

			sleep(100);
			ReflectionSensor rs = (ReflectionSensor) getRobot().getSensors()[0];
			if (rs.getValue() == 0
					|| (rs.convertValueToDistance(rs.getValue()) > 0.17))
			{
				vectorDrive(0, -0.1, 0);
				sleep(3000);
				vectorDrive(0, 0, 0);
				wallHug();
			}
			else
			{
				tryLeft();
			}

			// Set the agent straight, this is obviously a cheat..
			getRobot().setRotationSpeed(0);
			getRobot().rotation = (Math.PI / 2) * (Math.round(getRobot().rotation / (Math.PI / 2)));
			sleep(200);
		}
		catch (Exception e)
		{
			Debug.printDebug(e.toString());
		}
	}

	/**
	 * check if there has been a stop commando, don't really now how to
	 * implement cause who is processing the command agent or robot
	 *
	 * @return boolean stop command given
	 */
	public boolean checkInterrupt()
	{
		return false;
	}
}
