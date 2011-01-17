/*
 * Created on Jun -8, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 * 
 * Simple robot used for pathfinding in the maze.
 *
 */

package javaBot.maze;

/**
 * @version $Revision: 1.1 $ last changed Feb 17, 2006
 */

import javaBot.GraphicalRepresentation;
import javaBot.Location;
import javaBot.Robot;
import javaBot.Simulator;
import javaBot.World;

public class MazeRobot extends Robot
{
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/jobot.gif");
	private static final int	DIAMETER_IN_IMAGE	= 100;

	public static final int		EAST				= 0;
	public static final int		WEST				= 1;
	public static final int		NORTH				= 2;
	public static final int		SOUTH				= 3;

	private Location			fromPos;
	private Location			targetPos;

	/**
	 * Creates a new MazeRobot object.
	 *
	 * @param name 
	 * @param positionX 
	 * @param positionY F
	 */
	public MazeRobot(String name, double positionX, double positionY)
	{
		super(name, 1.0, positionX, positionY, 0.18, 0.5);

		fromPos = new Location(0, 0);
		targetPos = new Location(0, 0);
	}

	/* (non-Javadoc)
	 * @see javaBot.PhysicalObject#createGraphicalRepresentation()
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE, true);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param x 
	 * @param y 
	 */
	public void goToCell(int x, int y)
	{
		location.setX((x * World.GRID_SIZE) + (World.GRID_SIZE / 2));
		location.setY(((Maze.ROWS - y) * World.GRID_SIZE) - (World.GRID_SIZE / 2));
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param x 
	 * @param y 
	 * @param d 
	 */
	public void goToCell(int x, int y, int d)
	{
		location.setX((x * World.GRID_SIZE) + (World.GRID_SIZE / 2));
		location.setY(((Maze.ROWS - y) * World.GRID_SIZE) - (World.GRID_SIZE / 2));
		rotation = Math.toRadians(d);
	}

	/**
	 * cause we only update the robot several times a second we could pass our
	 * destination therefore we check if al ready there or even further
	 *
	 * @return returns true if we're past our destination in the y and x
	 *         dimension
	 */
	public boolean moveReady()
	{
		boolean test = false;

		/*was x bigger or smaller then we want?
		 if (fromPos.getX() < targetPos.getX()) {
		 //is it bigger now?
		 test = fromPos.getX() >= targetPos.getX();
		 } else {
		 //is it smaller now
		 test = fromPos.getX() < targetPos.getX();
		 }

		 //did x change?
		 if (test) {
		 if (fromPos.getY() < targetPos.getY()) {
		 test = fromPos.getY() >= targetPos.getY();
		 } else {
		 test = fromPos.getY() < targetPos.getY();
		 }
		 } else {
		 test = false;
		 }*/
		if ((Math.abs(fromPos.getX() - targetPos.getX()) < 0.005)
				&& (Math.abs(fromPos.getY() - targetPos.getY()) < 0.005))
		{
			fromPos.setX(targetPos.getX());
			fromPos.setY(targetPos.getY());
			test = true;
		}

		//Debug.printDebug("[" + fromPos.getX() + "," + fromPos.getY() + "] >= [" + targetPos.getX() + "," + targetPos.getY() + "]");
		return test;
	}

	/**
	 * Method to move robot to another cell
	 *
	 * @param x 
	 * @param y 
	 * @param v 
	 * @param d 
	 */
	public void moveToCell(int x, int y, double v, int d)
	{
		int angle;

		//Debug.printDebug("Moving " + x + " " + y + " " + v + " " + d);
		switch (d)
		// Use the direction as a force factor
		{
			case EAST: // Forward - Right
				setDrivingVelocityX(v); // Angle = 90
				setDrivingVelocityY(0);
				angle = 90;

				break;

			case WEST: // Backward - Left
				setDrivingVelocityX(-v); // angle = 270;
				setDrivingVelocityY(0);
				angle = 270;

				break;

			case NORTH: // Up - North
				setDrivingVelocityX(0); // angle = 0;
				setDrivingVelocityY(-v);
				angle = 0;

				break;

			case SOUTH: // Down - South
				setDrivingVelocityX(0); // angle = 180;
				setDrivingVelocityY(v);
				angle = 180;

				break;

			default:
				angle = d;

				break;
		}

		//calculates what the target position is in pixels given the square in the maze
		targetPos.setX((x * World.GRID_SIZE) + (World.GRID_SIZE / 2));
		targetPos.setY(World.HEIGHT - ((y * World.GRID_SIZE) + (World.GRID_SIZE / 2)));
		fromPos = location;
		rotation = Math.toRadians(angle);
	}
}
