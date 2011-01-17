/*
 * Created on Jun 8, 2004
 */
package javaBot.maze;

import javaBot.Debug;
import javaBot.Location;
import javaBot.World;

/**
 * Representation of one maze cell. It has an index specifying the relative
 * position and an associated X,Y coordinate. The walls specify on what sides
 * the cell is open.
 */
public class Cell
{
	public static int		SIZE		= 0;
	public static int		X1;
	public static int		Y1;

	private static Cell[]	cellArray;

	private int				x;
	private int				y;
	private int				index;
	private int				prevCell	= 0;
	private int				nextCell	= 0;
	private boolean			visited		= false;


	/**
	 * Creates a new Cell object.
	 *
	 * @param x TODO PARAM: DOCUMENT ME!
	 * @param y TODO PARAM: DOCUMENT ME!
	 * @param i TODO PARAM: DOCUMENT ME!
	 */
	public Cell(int x, int y, int i)
	{
		this.x = x;
		this.y = y;
		index = i;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param x TODO PARAM: param description
	 * @param y TODO PARAM: param description
	 */
	public static void init(int x, int y)
	{
		int i;
		int j;
		int k;
		Cell theCell;
		X1 = x;
		Y1 = y;
		SIZE = x * y;
		cellArray = new Cell[SIZE];
		i = 0;

		for (k = 0; k < y; k++)
		{
			for (j = 0; j < x; j++)
			{
				theCell = new Cell(j, k, i);
				cellArray[i] = theCell;
				i++;
			}
		}
	}

	/**
	 * Get a cell based on it's index in the array
	 *
	 * @param i TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Cell getCell(int i)
	{
		return cellArray[i];
	}

	/**
	 * Get a cell based on it's location in the x,y grid
	 *
	 * @param x TODO PARAM: DOCUMENT ME!
	 * @param y TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Cell getCell(int x, int y)
	{
		if ((x < 0) || (x > X1))
		{
			x = 0;
		}

		if ((y < 0) || (y > Y1))
		{
			y = 0;
		}

		int i = (y * X1) + x;

		return cellArray[i];
	}

	/**
	 * Get a cell based on it's name
	 *
	 * @param name TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Cell getCell(String name)
	{
		int index = Integer.parseInt(name.substring(1));

		return cellArray[index];
	}

	/**
	 * Returns the walls associated with this cell
	 *
	 * @param i TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int[] getWalls(int i)
	{
		int[] walls = new int[4];
		walls[0] = Wall.getWall(x, y, 0).getIndex();
		walls[1] = Wall.getWall(x, y, 1).getIndex();
		walls[2] = Wall.getWall(x, y, 2).getIndex();
		walls[3] = Wall.getWall(x, y, 3).getIndex();

		return walls;
	}

	/**
	 * Get the index of the cell on a given position
	 *
	 * @param location TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static int getCellIndex(Location location)
	{
		return Cell.getCell((int) (location.getX() / World.GRID_SIZE),
				(int) ((World.HEIGHT - location.getY()) / World.GRID_SIZE)).index;
	}

	/**
	 * Returns the walls that are actually around a cell
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean[] getWallsAroundCell()
	{
		try
		{
			boolean[] walls = new boolean[4];

			// top (Y+2 since the getwall seems to be off..)            
			walls[0] = Wall.getWall(x, y + 2, 0).isUp();

			// bottom
			walls[1] = Wall.getWall(x, y - 1, 1).isUp();

			// left
			walls[2] = Wall.getWall(x + 1, y, 2).isUp();

			// right
			walls[3] = Wall.getWall(x, y, 3).isUp();

			return walls;
		}
		catch (Exception e)
		{
			Debug.printError("Error in getWallsAroundCell(): " + e.toString());

			return null;
		}
	}

	/**
	 * Get the status of a wall (up/down)
	 *
	 * @param i TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getWallUp(int i)
	{
		int[] walls = getWalls(i);

		return Wall.getWall(walls[i]).isUp();
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String toString()
	{
		return "Cell with coordinates: (" + x + "," + y + ")";
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getPrevCell()
	{
		return prevCell;
	}

	public void setPrevCell(int prevCell)
	{
		this.prevCell = prevCell;
	}

	public int getNextCell()
	{
		return nextCell;
	}

	public void setNextCell(int nextCell)
	{
		this.nextCell = nextCell;
	}

	public boolean isVisited()
	{
		return visited;
	}

	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
}
