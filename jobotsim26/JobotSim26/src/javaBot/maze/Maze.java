/*
 * Created on Jun 08, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 */
package javaBot.maze;

import java.awt.Color;
import java.awt.Graphics;

import javaBot.Debug;
import javaBot.GraphicalRepresentation;
import javaBot.Line;
import javaBot.Location;
import javaBot.MovableObject;
import javaBot.NonMovableObject;
import javaBot.ReflectionSensor;
import javaBot.Robot;
import javaBot.Simulator;
import javaBot.World;
import weiss.nonstandard.DisjointSets;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ last changed Feb 17, 2006  Creates on entry a new maze structure. A maze consists of a number of cells, located on an x/y grid. Each cell consists of 4 grid cells and has four walls. When two adjacent cells do not have a wall between them, they are connected. Maze is a meta object for the maze structure (i.e. the walls and the cells) this way the world can call de standard methods for a nonmovable object in one call instead of a single call to each wall of the maze.
 */
public class Maze extends NonMovableObject
{
	//Height of the maze in cells
	public static int		ROWS;

	//Width of the maze in cells
	public static int		COLUMNS;
	
	private Cell			theCell;
	private DisjointSets	theSets;
	private MazePath		thePath;
	private Wall			theWall;
	private Wall[]			lastWalls			= new Wall[3];
	private float			scaleX;
	private float			scaleY;
	private int				transX;
	private int				transY;
	private double			wallBounceFactor	= 1;

	/**
	 * Construct a maze object
	 */
	public Maze()
	{
		super("Maze");
		location = new Location(World.WIDTH / 2, World.HEIGHT / 2);
		ROWS = (int) (World.HEIGHT / World.GRID_SIZE);
		COLUMNS = (int) (World.WIDTH / World.GRID_SIZE);
		initMaze();
		generateMaze();
		findPath();
	}

	/**
	 * Handles a collision of the maze's walls with a movable object
	 *
	 * @param object the colliding movable object
	 */
	public void collideWith(MovableObject object)
	{
		if (object.getLocation().getX() > 0)
		{
			// Get the cell at my position
			Cell c = Cell.getCell(Cell.getCellIndex(object.getLocation()));
			Cell d;
			Location l = new Location(object.getLocation().getX() / World.GRID_SIZE,
					(int) ((World.HEIGHT - object.getLocation().getY()) / World.GRID_SIZE));
			Location intersection;
			Location difference;
			double factor;

			//Debug.printDebug(c.index + "" + r.getLocation().toString());
			boolean[] walls = c.getWallsAroundCell();
			boolean[] otherWalls;
			Line[] wall = new Line[4];

			wall[0] = new Line(new Location(c.getX() * World.GRID_SIZE, World.HEIGHT
					- ((c.getY() + 1) * World.GRID_SIZE)), 0, World.GRID_SIZE);

			wall[0] = new Line(new Location(c.getX() * World.GRID_SIZE, World.HEIGHT
					- ((c.getY() + 1) * World.GRID_SIZE)), 0, World.GRID_SIZE);

			wall[1] = new Line(new Location(c.getX() * World.GRID_SIZE, World.HEIGHT
					- (c.getY() * World.GRID_SIZE)), 0, World.GRID_SIZE);

			wall[2] = new Line(new Location(c.getX() * World.GRID_SIZE, World.HEIGHT
					- ((c.getY() + 1) * World.GRID_SIZE)), Math.PI / 2.0, World.GRID_SIZE);

			wall[3] = new Line(new Location((c.getX() + 1) * World.GRID_SIZE, World.HEIGHT
					- ((c.getY() + 1) * World.GRID_SIZE)), Math.PI / 2.0, World.GRID_SIZE);

			if (walls[0])
			{
				if (wall[0].distancePointToLine(object.getLocation()) < (object.diameter / 2))
				{
					//Debug.printDebug("collided with maze");
					object.getLocation().setY(wall[0].getP().getY() + (object.diameter / 2));
					object.setVelocityY(-object.getVelocityY() * wallBounceFactor);
					object.setAccelerationY(object.getAccelerationY() * -wallBounceFactor);
				}
			}
			else
			{
				if (!walls[2])
				{
					if (((l.getX() + 1) < COLUMNS) && ((l.getY() - 1) >= 0))
					{
						//check for collision with left under corner
						d = Cell.getCell((int) l.getX() + 1, (int) l.getY() - 1);
						otherWalls = d.getWallsAroundCell();

						if (otherWalls[1] || otherWalls[3])
						{
							//Location
							intersection = wall[0].intersect(wall[2]);

							if (intersection.distanceTo(object.getLocation()) < (object.diameter / 2))
							{
								Debug.printDebug("We have a collision in the left under corner");
								difference = object.getLocation().substract(intersection);
								factor = object.diameter
										/ Math.sqrt((difference.getX() + difference.getY())
												* (difference.getX() + difference.getY()));
								difference.setX(difference.getX() * factor);
								difference.setY(difference.getY() * factor);
								object.setLocation(intersection.add(difference));
								object.setVelocityX(-object.getVelocityX());
								object.setVelocityY(-object.getVelocityY());
							}
						}
					}
				}

				if (!walls[3])
				{
					if (((l.getX() + 1) < COLUMNS) && ((l.getY() + 1) < ROWS))
					{
						d = Cell.getCell((int) l.getX() + 1, (int) l.getY() + 1);
						otherWalls = d.getWallsAroundCell();

						if (otherWalls[1] || otherWalls[2])
						{
							intersection = wall[0].intersect(wall[3]);

							if (intersection.distanceTo(object.getLocation()) < (object.diameter / 2))
							{
								Debug.printDebug("We have a collision in the right under corner");
								difference = object.getLocation().substract(intersection);
								factor = object.diameter
										/ Math.sqrt((difference.getX() + difference.getY())
												* (difference.getX() + difference.getY()));
								difference.setX(difference.getX() * factor);
								difference.setY(difference.getY() * factor);
								object.setLocation(intersection.add(difference));
								object.setVelocityX(-object.getVelocityX());
								object.setVelocityY(-object.getVelocityY());
							}
						}
					}
				}
			}

			if (walls[1])
			{
				if (wall[1].distancePointToLine(object.getLocation()) < (object.diameter / 2))
				{
					//Debug.printDebug("collided with maze");
					object.getLocation().setY(wall[1].getP().getY() - (object.diameter / 2));
					object.setVelocityY(-object.getVelocityY() * wallBounceFactor);
					object.setAccelerationY(object.getAccelerationY() * -wallBounceFactor);
				}
			}
			else
			{
				if (!walls[2])
				{
					//check for collision with left under corner
					if (((l.getX() - 1) >= 0) && ((l.getY() - 1) >= 0))
					{
						d = Cell.getCell((int) l.getX() - 1, (int) l.getY() - 1);
						otherWalls = d.getWallsAroundCell();

						if (otherWalls[0] || otherWalls[3])
						{
							intersection = wall[1].intersect(wall[2]);

							if (intersection.distanceTo(object.getLocation()) < (object.diameter / 2))
							{
								Debug.printDebug("We have a collision in the left upper corner");
								difference = object.getLocation().substract(intersection);
								factor = object.diameter
										/ Math.sqrt((difference.getX() + difference.getY())
												* (difference.getX() + difference.getY()));
								difference.setX(difference.getX() * factor);
								difference.setY(difference.getY() * factor);
								object.setLocation(intersection.add(difference));
								object.setVelocityX(-object.getVelocityX());
								object.setVelocityY(-object.getVelocityY());
							}
						}
					}
				}

				if (!walls[3])
				{
					if (((l.getX() - 1) >= 0) && ((l.getY() + 1) < ROWS))
					{
						d = Cell.getCell((int) l.getX() - 1, (int) l.getY() + 1);
						otherWalls = d.getWallsAroundCell();

						if (otherWalls[0] || otherWalls[2])
						{
							intersection = wall[1].intersect(wall[3]);

							if (intersection.distanceTo(object.getLocation()) < (object.diameter / 2))
							{
								Debug.printDebug("We have a collision in the right upper corner");
								difference = object.getLocation().substract(intersection);
								factor = object.diameter
										/ Math.sqrt((difference.getX() + difference.getY())
												* (difference.getX() + difference.getY()));
								difference.setX(difference.getX() * factor);
								difference.setY(difference.getY() * factor);
								object.setLocation(intersection.add(difference));
								object.setVelocityX(-object.getVelocityX());
								object.setVelocityY(-object.getVelocityY());
							}
						}
					}
				}
			}

			if (walls[2])
			{
				if (wall[2].distancePointToLine(object.getLocation()) < (object.diameter / 2))
				{
					//Debug.printDebug("collided with maze");
					object.getLocation().setX(wall[2].getP().getX() + (object.diameter / 2));
					object.setVelocityX(-object.getVelocityX() * wallBounceFactor);
					object.setAccelerationX(object.getAccelerationX() * -wallBounceFactor);
				}
			}

			if (walls[3])
			{
				if (wall[3].distancePointToLine(object.getLocation()) < (object.diameter / 2))
				{
					//Debug.printDebug("collided with maze");
					object.getLocation().setX(wall[3].getP().getX() - (object.diameter / 2));
					object.setVelocityX(-object.getVelocityX() * wallBounceFactor);
					object.setAccelerationX(object.getAccelerationX() * -wallBounceFactor);
				}
			}
		}
	}

	/**
	 * Return a new representation of the maze
	 *
	 * @return JComponent for the GUI
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new MazeGraphicalRepresentation(this);
	}

	/**
	 * DrawCell calculates the position on the screen of the walls of a single
	 * cell. If the wall is 'on' then the wall is drawn, otherwise it is left
	 * blank.
	 *
	 * @param g TODO PARAM: DOCUMENT ME!
	 * @param theCell TODO PARAM: DOCUMENT ME!
	 * @param scaleX TODO PARAM: DOCUMENT ME!
	 * @param scaleY TODO PARAM: DOCUMENT ME!
	 * @param transX TODO PARAM: DOCUMENT ME!
	 * @param transY TODO PARAM: DOCUMENT ME!
	 */
	public void drawCell(Graphics g, Cell theCell, float scaleX, float scaleY, int transX,
			int transY)
	{
		int x1;
		int x2;
		int y1;
		int y2;
		x1 = (int) ((theCell.getX() * World.GRID_SIZE) / scaleX) + transX;
		x2 = (int) ((((theCell.getX() * World.GRID_SIZE) + World.GRID_SIZE) / scaleX) + transX);
		y1 = (int) ((theCell.getY() * World.GRID_SIZE) / scaleY) + transY;
		y2 = (int) ((((theCell.getY() * World.GRID_SIZE) + World.GRID_SIZE) / scaleY) + transY);

		if (theCell.getWallUp(0))
		{
			g.drawLine(x1, y1, x2, y1); // Top
		}

		if (theCell.getWallUp(1))
		{
			g.drawLine(x1, y2, x2, y2); // Bottom
		}

		if (theCell.getWallUp(2))
		{
			g.drawLine(x1, y1, x1, y2); // Left
		}

		if (theCell.getWallUp(3))
		{
			g.drawLine(x2, y1, x2, y2); // Right
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param g TODO PARAM: param description
	 * @param scaleX TODO PARAM: param description
	 * @param scaleY TODO PARAM: param description
	 * @param transX TODO PARAM: param description
	 * @param transY TODO PARAM: param description
	 */
	public void drawMaze(Graphics g, float scaleX, float scaleY, int transX, int transY)
	{
		g.setColor(new Color(0f, 0f, 0f));

		Wall theWall = null;

		for (int i = 0; i < Wall.SIZE; i++)
		{
			theWall = Wall.getWall(i);
			drawWall(g, theWall, scaleX, scaleY, transX, transY);
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param g TODO PARAM: param description
	 * @param scaleX TODO PARAM: param description
	 * @param scaleY TODO PARAM: param description
	 * @param transX TODO PARAM: param description
	 * @param transY TODO PARAM: param description
	 */
	public void drawMazeCell(Graphics g, float scaleX, float scaleY, int transX, int transY)
	{
		g.setColor(new Color(0f, 0f, 0f));

		for (int i = 0; i < Cell.SIZE; i++)
		{
			theCell = Cell.getCell(i);
			drawCell(g, theCell, scaleX, scaleY, transX, transY);
		}
	}

	/**
	 * Show the generated path =
	 *
	 * @param g TODO PARAM: DOCUMENT ME!
	 * @param scaleX TODO PARAM: DOCUMENT ME!
	 * @param scaleY TODO PARAM: DOCUMENT ME!
	 * @param transX TODO PARAM: DOCUMENT ME!
	 * @param transY TODO PARAM: DOCUMENT ME!
	 */
	public void drawPath(Graphics g, float scaleX, float scaleY, int transX, int transY)
	{
		int i;
		int p;
		boolean done = false;
		Cell theCell = Cell.getCell(((Maze.ROWS * Maze.COLUMNS) - 1));
		paintCell(g, theCell, scaleX, scaleY, transX, transY);

		while (!done)
		{
			p = theCell.getPrevCell(); // Get previous one
			i = theCell.getIndex(); // Get index this one
			theCell = Cell.getCell(p);
			theCell.setNextCell(i); // Pointer to next one in previous

			if (p == 0)
			{
				done = true;
			}

			paintCell(g, theCell, scaleX, scaleY, transX, transY);
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param g TODO PARAM: param description
	 * @param scaleX TODO PARAM: param description
	 * @param scaleY TODO PARAM: param description
	 * @param transX TODO PARAM: param description
	 * @param transY TODO PARAM: param description
	 */
	public void drawSets(Graphics g, float scaleX, float scaleY, int transX, int transY)
	{
		int theEntry = theSets.find(0);
		int thePath = 0;

		for (int i = 0; i < Cell.SIZE; i++)
		{
			thePath = theSets.find(i);
			theCell = Cell.getCell(i);

			if (theCell.isVisited() && (thePath == theEntry))
			{
				paintSet(g, theCell, scaleX, scaleY, transX, transY);
			}
		}
	}

	/**
	 * DrawWall calculates the position on the screen of the given wall. If the
	 * wall is 'on' the wall is drawn, otherwise it is left blank
	 *
	 * @param g TODO PARAM: DOCUMENT ME!
	 * @param theWall TODO PARAM: DOCUMENT ME!
	 * @param scaleX TODO PARAM: DOCUMENT ME!
	 * @param scaleY TODO PARAM: DOCUMENT ME!
	 * @param transX TODO PARAM: DOCUMENT ME!
	 * @param transY TODO PARAM: DOCUMENT ME!
	 */
	public void drawWall(Graphics g, Wall theWall, float scaleX, float scaleY, int transX,
			int transY)
	{
		int x1;
		int x2;
		int y1;
		int y2;
		int X;
		int Y;
		X = theWall.getX();
		Y = theWall.getY();
		x1 = (int) ((X * World.GRID_SIZE) / scaleX) + transX;
		x2 = (int) (((X + 1) * World.GRID_SIZE) / scaleX) + transX;
		y1 = (int) ((Y * World.GRID_SIZE) / scaleY) + transY;
		y2 = (int) (((Y + 1) * World.GRID_SIZE) / scaleY) + transY;

		if (theWall.isUp())
		{
			if (theWall.isWallHor())
			{
				g.drawLine(x1, y1, x2, y1);
			}
			else
			{
				g.drawLine(x1, y1, x1, y2);
			}
		}
	}

	/**
	 * findPath calls the MazePath object to find a path through the maze
	 */
	public void findPath()
	{
		thePath.processPath();
	}

	/**
	 * Calculate what a robot would see on it's sensor concerning the maze
	 *
	 * @param r The robot we want to calculate the sensor values for
	 *
	 * @return an array containing all the sensor values
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		//Cell robotCell = Cell.getCell(Cell.getCellIndex(r.getLocation()));
		Cell currentCell;
		int prevCrossedLine;
		boolean foundWall = false;
		boolean transitFound = false;
		Line[] wall = new Line[4];
		Line sensorLine;
		ReflectionSensor sensor;
		double distance;
		double[] returnValue = new double[r.getSensors().length];
		boolean[] walls;

		//calculate the value for each sensor
		for (int sens = 0; sens < r.getSensors().length; sens++)
		{
			//prevcrossed line is the wall number we last crossed since we din't cross a line yet we put it on 4
			prevCrossedLine = 4;

			//we want to know for sure that distance has a value so by difault we set it to infinity which is equal to not seeing anything
			distance = Double.POSITIVE_INFINITY;
			foundWall = false;

			if (r.getSensors()[sens] instanceof ReflectionSensor)
			{
				sensor = ((ReflectionSensor) r.getSensors()[sens]);
				sensorLine = sensor.sensorLine(r.getLocation().getX(), r.getLocation().getY(),
						r.rotation);

				//set the current cell to the location the sensor is in
				currentCell = Cell.getCell(Cell.getCellIndex(sensor.getPositionInWorld(r
						.getLocation())));

				while (!foundWall)
				{
					//make a line for every wall surrounding the cell
					wall[0] = new Line(new Location(currentCell.getX() * World.GRID_SIZE,
							World.HEIGHT - ((currentCell.getY() + 1) * World.GRID_SIZE)), 0,
							World.GRID_SIZE);

					wall[1] = new Line(new Location(currentCell.getX() * World.GRID_SIZE,
							World.HEIGHT - (currentCell.getY() * World.GRID_SIZE)), 0,
							World.GRID_SIZE);

					wall[2] = new Line(new Location(currentCell.getX() * World.GRID_SIZE,
							World.HEIGHT - ((currentCell.getY() + 1) * World.GRID_SIZE)),
							Math.PI / 2.0, World.GRID_SIZE);

					wall[3] = new Line(new Location((currentCell.getX() + 1) * World.GRID_SIZE,
							World.HEIGHT - ((currentCell.getY() + 1) * World.GRID_SIZE)),
							Math.PI / 2.0, World.GRID_SIZE);

					//an array of booleans indicating which wall's are up and wich are down
					walls = currentCell.getWallsAroundCell();

					//we put this on true when we found a transit from this square to an other
					transitFound = false;

					for (int wallNumber = 0; wallNumber <= 3; wallNumber++)
					{
						//we haven't found a wall in range of the sensor yet and we din't found a transit to an other sqaure yet
						if (!foundWall && !transitFound)
						{
							//if the wall we want to check now is lying against the wall we last past we don't want to look at it
							if (lieAgainstEachOther(wallNumber, prevCrossedLine))
							{
								distance = Double.POSITIVE_INFINITY;
							}
							else
							{
								//distance is the distance bew
								distance = sensorLine.intersectDist2(wall[wallNumber]);
							}

							if (distance != Double.POSITIVE_INFINITY)
							{
								transitFound = true;

								if (walls[wallNumber])
								{
									foundWall = true;
									returnValue[sens] = sensor.convertDistanceToValue(distance);

									if ((currentCell.getWalls(1)[wallNumber] >= 0) && (sens == 0))
									{
										if (!Wall.getWall(currentCell.getWalls(1)[wallNumber])
												.equals(lastWalls[sens]))
										{
											lastWalls[sens] = Wall
													.getWall(currentCell.getWalls(1)[wallNumber]);
											Debug.printDebug("I see a wall with sensor: "
													+ Integer.toString(sens) + " it has number: "
													+ Integer.toString(wallNumber)
													+ " and is of cell: " + currentCell.toString()
													+ "the distance is: "
													+ Double.toString(distance));
										}
									}

									//                                    break;
								}
								else
								{
									prevCrossedLine = wallNumber;

									switch (wallNumber)
									{
										case 0: //bottom

											if (currentCell.getY() < (COLUMNS - 1))
											{
												currentCell = Cell.getCell(currentCell.getX(),
														currentCell.getY() + 1);
											}
											else
											{
												distance = Double.POSITIVE_INFINITY;
												foundWall = true;
											}

											break;

										case 1: //Top

											if (currentCell.getY() > 0)
											{
												currentCell = Cell.getCell(currentCell.getX(),
														currentCell.getY() - 1);
											}
											else
											{
												distance = Double.POSITIVE_INFINITY;
												foundWall = true;
											}

											break;

										case 2: //left

											if (currentCell.getX() > 0)
											{
												currentCell = Cell.getCell(currentCell.getX() - 1,
														currentCell.getY());
											}
											else
											{
												distance = Double.POSITIVE_INFINITY;
												foundWall = true;
											}

											break;

										case 3: //right

											if (currentCell.getX() < (ROWS - 1))
											{
												currentCell = Cell.getCell(currentCell.getX() + 1,
														currentCell.getY());
											}
											else
											{
												distance = Double.POSITIVE_INFINITY;
												foundWall = true;
											}

											break;

										default:
											Debug.printDebug("Default wall something did go wrong");
									} //end of switch statement
								} //end of if statement which tests if a wall is up or down
							} //end of if statement wich tests if the distance != infinity
						} //end of if(!foundwall&&!foundtransit)
					} //end of for loop over the 4 walls

					if (distance == Double.POSITIVE_INFINITY)
					{
						foundWall = true;
					}
				} //end of while loop (!foundWall)
			} //end of check if this is a distance sensor
		} //end of for loop over all sensors

		return returnValue;
	}

	/**
	 * pad a string S with a SIZE of N with char C on the left (True) or on the
	 * right(false)
	 *
	 * @param s TODO PARAM: DOCUMENT ME!
	 * @param n TODO PARAM: DOCUMENT ME!
	 * @param c TODO PARAM: DOCUMENT ME!
	 * @param paddingLeft TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public synchronized String paddingString(String s, int n, char c, boolean paddingLeft)
	{
		StringBuffer str = new StringBuffer(s);
		int strLength = str.length();

		if ((n > 0) && (n > strLength))
		{
			for (int i = 0; i <= n; i++)
			{
				if (paddingLeft)
				{
					if (i < (n - strLength))
					{
						str.insert(0, c);
					}
				}
				else
				{
					if (i > strLength)
					{
						str.append(c);
					}
				}
			}
		}

		return str.toString();
	}

	/**
	 * paintCell calculates the position on the screen of a single cell. It
	 * then paints the inside of the wall a certain color and then loops
	 * through the connected cells in the path and paints these too
	 *
	 * @param g TODO PARAM: DOCUMENT ME!
	 * @param theCell TODO PARAM: DOCUMENT ME!
	 * @param scaleX TODO PARAM: DOCUMENT ME!
	 * @param scaleY TODO PARAM: DOCUMENT ME!
	 * @param transX TODO PARAM: DOCUMENT ME!
	 * @param transY TODO PARAM: DOCUMENT ME!
	 */
	public void paintCell(Graphics g, Cell theCell, float scaleX, float scaleY, int transX,
			int transY)
	{
		int x1;
		int y1;
		int width;
		int height;

		x1 = (int) ((((theCell.getX() * World.GRID_SIZE) + 0.04) / scaleX) + transX);
		width = (int) ((World.GRID_SIZE - 0.07) / scaleX);
		y1 = (int) (((((theCell.getY()) * World.GRID_SIZE) + 0.04) / scaleY) + transY);
		height = (int) ((World.GRID_SIZE - 0.07) / (scaleY));
		g.setColor(new Color(0.9f, 0.9f, 0.9f));
		g.fillRect(x1, y1, width, height);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param g TODO PARAM: param description
	 * @param theCell TODO PARAM: param description
	 * @param scaleX TODO PARAM: param description
	 * @param scaleY TODO PARAM: param description
	 * @param transX TODO PARAM: param description
	 * @param transY TODO PARAM: param description
	 */
	public void paintSet(Graphics g, Cell theCell, float scaleX, float scaleY, int transX,
			int transY)
	{
		int x2;
		int y2;
		int theSet;
		x2 = (int) ((((theCell.getX() * World.GRID_SIZE) + 40) / scaleX) + transX);
		COLUMNS = (int) ((World.GRID_SIZE - 25) / scaleX);
		y2 = (int) (((((theCell.getY() + 1) * World.GRID_SIZE) - 120) / scaleY) + transY);
		ROWS = (int) ((World.GRID_SIZE - 25) / (0 - scaleY));
		theSet = theSets.find(theCell.getIndex());

		String s = "" + theSet;
		g.setColor(new Color(0f, 0f, 0f));
		g.drawString(s, x2, y2);
	}

	/**
	 * Student Code checkDone and generateMaze are to be programmed by the
	 * students.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	/**
	 * In order to check if we are done we check if the first cell is in the
	 * same set as the last cell. Remove this comment in student version
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private boolean checkDone()
	{
		int first = theSets.find(0);
		int last = theSets.find(Cell.SIZE - 1);

		return first == last;
	}

	/**
	 * GenerateMaze creates a maze from scratch and  keeps an array with all
	 * visited walls. Remove code in student version
	 */
	private void generateMaze()
	{
		boolean done = false;
		int i = 0;

		// Student code
		int setA;
		int setB;
		int A;
		int B;
		Cell cellA;
		Cell cellB;
		int[] adjCells;
		int wallUp;

		// Student code
		Debug.printInfo("Generating Maze...");

		try
		{
			while (!done)
			{
				// Student start
				theWall = Wall.getWall(); // Get a random wall

				if (theWall == null)
				{
					Debug.printError("Error in generateMaze(): randomizer");
					done = true;
				}
				else
				{
					wallUp = 1;
					adjCells = theWall.getCells(); // Get adjacent cells
					A = adjCells[0]; // Into A and B
					B = adjCells[1];
					setA = theSets.find(A);
					setB = theSets.find(B);
					cellA = Cell.getCell(A);
					cellB = Cell.getCell(B);
					cellA.setVisited(true); // Show they are visited
					cellB.setVisited(true);

					if (setA != setB) // If not connected
					{
						theWall.setUp(false); // Knock down the wall						
						theSets.union(setA, setB); // Union of both cells
						thePath.addEdge("C" + A, "C" + B, 1);
						thePath.addEdge("C" + B, "C" + A, 1);
						setA = theSets.find(A); // For printing only
						setB = theSets.find(B);
						wallUp = 0;
					}

					// Student end
					// Debug info

					/*System.out.println(printTab("Wall=" + theWall.index) +
					 printTab(" A=" + adjCells[0] + "," + setA) +
					 printTab(" B=" + adjCells[1] + "," + setB) +
					 printTab(" X=" + setA + "," + setB + "," + wallUp) +
					 printTab(" C" + A + " and C" + B + " connected"));*/
				}

				if (checkDone())
				{
					Debug.printInfo("Maze succeeded");
					done = true;
				}
				else
				{
					if (i++ > Wall.SIZE) // If we visited all walls
					{
						Debug.printInfo("Maze incomplete");
						done = true;
					}
				}
			}
		}
		catch (Exception e)
		{
			Debug.printError("Error during Maze gen: " + e.toString());
		}
	}

	/**
	 * Initialize the maze object
	 */
	private void initMaze()
	{
		Debug.printInfo("Initializing the maze");
		Cell.init(COLUMNS, ROWS);
		Wall.init(COLUMNS, ROWS);
		theSets = new DisjointSets(Cell.SIZE);
		thePath = new MazePath();
		Debug.printDebug("Init Maze " + COLUMNS + "x" + ROWS);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param wallNumber TODO PARAM: param description
	 * @param prevCrossedLine TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private boolean lieAgainstEachOther(int wallNumber, int prevCrossedLine)
	{
		switch (wallNumber)
		{
			case 0:
				return prevCrossedLine == 1;

			case 1:
				return prevCrossedLine == 0;

			case 2:
				return prevCrossedLine == 3;

			case 3:
				return prevCrossedLine == 2;
		}

		return false;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param s TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private String printTab(String s)
	{
		return paddingString(s, 10, ' ', false);
	}
}


/**
 * Visual representation of the maze
 */
class MazeGraphicalRepresentation extends GraphicalRepresentation
{
	private Maze	maze;
	private double	scale	= Simulator.pixelsPerMeter;

	/**
	 * Creates a new MazeGraphicalRepresentation object.
	 *
	 * @param maze The maze to create a representation for
	 */
	public MazeGraphicalRepresentation(Maze maze)
	{
		super(maze);
		this.maze = maze;
		this.layer = Simulator.MAZE_LAYER;

		double width = Maze.COLUMNS * World.GRID_SIZE;
		double height = Maze.ROWS * World.GRID_SIZE;
		setSize(Simulator.meterToPixel(width), Simulator.meterToPixel(height));
		setLocation(0, 0);
		setOpaque(false);
		setVisible(true);
	}

	// Draw the maze on the JComponent
	public void paintComponent(Graphics g)
	{
		maze.drawMaze(g, (float) (1 / scale), (float) (1 / scale), 0, 0);
		maze.drawPath(g, (float) (1 / scale), (float) (1 / scale), 0, 0);
	}
}
