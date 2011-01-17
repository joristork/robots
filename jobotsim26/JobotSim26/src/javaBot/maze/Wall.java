/*
 * Created on Jun 8, 2004
 */
package javaBot.maze;

import java.util.Random;

/**
 * A border of a Cell and is used to create a maze. Each cell has four borders
 * that are shared with adjacent cells. In a matrix of 10x5 cells there are
 * 10x4+95=85 walls. For the outer walls of the field, not walls are
 * generated. So the numbering goes as follows:  - Horizontal - x=0-9 and
 * y=1-4 - Vertical   - x=1-9 and y=0-4  The wallArray first contains all
 * horizontal walls, followed by all vertical walls. The variable HORVERT is
 * the number of horizontal walls, above this number the vertical walls are
 * found.
 */
public class Wall
{
	public static int		SIZE	= 0;
	public static int		HORVERT	= 0;
	public static int		X1;
	public static int		Y1;

	private static Wall[]	wallArray;
	private static Wall		dummyWall;
	private static Random	r;

	private int				x;
	private int				y;
	private int				index;
	private boolean			visited	= false;
	private boolean			up		= true;

	/**
	 * Creates a new Wall object.
	 *
	 * @param x TODO PARAM: DOCUMENT ME!
	 * @param y TODO PARAM: DOCUMENT ME!
	 * @param i TODO PARAM: DOCUMENT ME!
	 */
	public Wall(int x, int y, int i)
	{
		this.x = x;
		this.y = y;
		index = i;
		visited = false;
		up = true;
		wallArray[i] = this;
		dummyWall = new Wall();
		dummyWall.index = -1;
	}

	/**
	 * Creates a new Wall object.
	 */
	public Wall()
	{
		up = true;
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
		r = new Random();

		Wall theWall;
		X1 = x;
		Y1 = y;
		SIZE = (x * (y - 1)) + ((x - 1) * y);
		wallArray = new Wall[SIZE];
		i = 0;

		// Horizontal x=0-9 y=1-4 (assuming 10x5 matrix)
		for (k = 1; k < y; k++)
		{
			for (j = 0; j < x; j++)
			{
				theWall = new Wall(j, k, i);
				i++;
			}
		}

		HORVERT = i;

		// Vertical x=1-9 y=0-4 (assuming 10x5 matrix)
		for (k = 0; k < y; k++)
		{
			for (j = 1; j < x; j++)
			{
				theWall = new Wall(j, k, i);
				i++;
			}
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Wall getWall()
	{
		Wall aWall = null;
		boolean done = false;
		int i = 0;

		while (!done)
		{
			aWall = wallArray[r.nextInt(SIZE)];

			if (!aWall.visited)
			{
				aWall.visited = true;
				done = true;
			}
			else
			{
				i++;
				aWall = null;

				if (i > SIZE)
				{
					done = true;
				}
			}
		}

		return aWall;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param i TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Wall getWall(int i)
	{
		return wallArray[i];
	}

	/**
	 * Retrieves the wall at the given position need to calculate where the
	 * requested wall is located
	 *
	 * @param x TODO PARAM: DOCUMENT ME!
	 * @param y TODO PARAM: DOCUMENT ME!
	 * @param hor TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Wall getWall(int x, int y, boolean hor)
	{
		Wall theWall = null;
		int index = 0;

		if (hor)
		{
			if ((x < 0) || (x >= X1))
			{
				return dummyWall;
			}

			if ((y <= 0) || (y >= Y1))
			{
				return dummyWall;
			}

			index = ((y - 1) * X1) + x;
		}
		else
		{
			if ((x <= 0) || (x >= X1))
			{
				return dummyWall;
			}

			if ((y < 0) || (y >= Y1))
			{
				return dummyWall;
			}

			index = HORVERT + ((y * (X1 - 1)) + (x - 1));
		}

		theWall = wallArray[index];

		return theWall;
	}

	/**
	 * Retrieves the wall at the given cell position. 0=Top, 1=Bottom, 2=Left,
	 * 3=Right
	 *
	 * @param cellX TODO PARAM: DOCUMENT ME!
	 * @param cellY TODO PARAM: DOCUMENT ME!
	 * @param i TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static Wall getWall(int cellX, int cellY, int i)
	{
		switch (i)
		{
			case 0:
				return getWall(cellX, cellY - 1, true);

			case 1:
				return getWall(cellX, cellY + 1, true);

			case 2:
				return getWall(cellX - 1, cellY, false);

			case 3:
				return getWall(cellX + 1, cellY, false);
		}

		return null;
	}

	/**
	 * @return isWallHor
	 */
	public boolean isWallHor()
	{
		return index < HORVERT;
	}

	/**
	 * The given wall returns the cells that are connected by this wall
	 *
	 * @return theCells
	 */
	public int[] getCells()
	{
		int[] theCells = new int[2];
		int newX = x;
		int newY = y;

		if (index >= HORVERT)
		{
			// Vertical x and x-1
			newX = x - 1;
		}
		else
		{
			// Horizontal y and y-1
			newY = y - 1;
		}

		Cell theCell = Cell.getCell(x, y);
		Cell newCell = Cell.getCell(newX, newY);
		theCells[0] = theCell.getIndex();
		theCells[1] = newCell.getIndex();

		return theCells;
	}

	/**
	 * @return x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x The x to set.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y The y to set.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @param index The index to set.
	 */
	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * @return up
	 */
	public boolean isUp()
	{
		return up;
	}

	/**
	 * @param up The up to set.
	 */
	public void setUp(boolean up)
	{
		this.up = up;
	}
}
