/*
 * Created on March 23, 2004 
 * Copyright: (c) 2006 
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */

package javaBot.tests;

/**
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */

import javaBot.maze.Cell;
import javaBot.maze.Wall;
import junit.framework.Assert;
import junit.framework.TestCase;

public class CellTest extends TestCase
{
	/**
	 * Constructor for CellTest.
	 * 
	 * @param arg0
	 */
	public CellTest(String arg0)
	{
		super(arg0);
	}

	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(CellTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		Cell.init(10, 5);
		Wall.init(10, 5);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/*
	 * Test for Cell getCell(int)
	 */
	public void testGetCellint()
	{
		Cell aCell = null;
		aCell = Cell.getCell(0);
		Assert.assertEquals("getCell(0) X", 0, aCell.getX());
		Assert.assertEquals("getCell(0) Y", 0, aCell.getY());
		aCell = Cell.getCell(1);
		Assert.assertEquals("getCell(1) X", 1, aCell.getX());
		Assert.assertEquals("getCell(1) Y", 0, aCell.getY());
		aCell = Cell.getCell(10);
		Assert.assertEquals("getCell(10) X", 0, aCell.getX());
		Assert.assertEquals("getCell(10) Y", 1, aCell.getY());
		aCell = Cell.getCell(49);
		Assert.assertEquals("getCell(10) X", 9, aCell.getX());
		Assert.assertEquals("getCell(10) Y", 4, aCell.getY());
	}

	/*
	 * Test for Cell getCell(int, int)
	 */
	public void testGetCellintint()
	{
		Cell aCell = null;
		aCell = Cell.getCell(0);
		Assert.assertEquals("getCell(0,0) X", 0, aCell.getX());
		Assert.assertEquals("getCell(0,0) Y", 0, aCell.getY());
		aCell = Cell.getCell(1);
		Assert.assertEquals("getCell(0,1) X", 1, aCell.getX());
		Assert.assertEquals("getCell(0,1) Y", 0, aCell.getY());
		aCell = Cell.getCell(10);
		Assert.assertEquals("getCell(10,0) X", 0, aCell.getX());
		Assert.assertEquals("getCell(10,0) Y", 1, aCell.getY());
		aCell = Cell.getCell(49);
		Assert.assertEquals("getCell(10,5) X", 9, aCell.getX());
		Assert.assertEquals("getCell(10,5) Y", 4, aCell.getY());
	}

	public void testGetWalls()
	{
		int[] theWalls;
		Wall aWall = null;
		Cell aCell = Cell.getCell(5, 2);
		theWalls = aCell.getWalls(0);
		Assert.assertEquals("getWalls(5,2)", 4, theWalls.length);
		aWall = Wall.getWall(theWalls[0]);
		Assert.assertEquals("getWalls(5,2,0) X", 5, aWall.getX());
		Assert.assertEquals("getWalls(5,2,0) Y", 1, aWall.getY());
		aWall = Wall.getWall(theWalls[1]);
		Assert.assertEquals("getWalls(5,2,1) X", 5, aWall.getX());
		Assert.assertEquals("getWalls(5,2,1) Y", 3, aWall.getY());
		aWall = Wall.getWall(theWalls[2]);
		Assert.assertEquals("getWalls(5,2,2) X", 4, aWall.getX());
		Assert.assertEquals("getWalls(5,2,2) Y", 2, aWall.getY());
		aWall = Wall.getWall(theWalls[3]);
		Assert.assertEquals("getWalls(5,2,3) X", 6, aWall.getX());
		Assert.assertEquals("getWalls(5,2,3) Y", 2, aWall.getY());
	}

	public void testGetWallUp()
	{
		boolean wallUp;
		Cell aCell = Cell.getCell(5, 2);
		wallUp = aCell.getWallUp(0);
		Assert.assertEquals("getWallUp(5,2,0)", true, wallUp);
		wallUp = aCell.getWallUp(1);
		Assert.assertEquals("getWallUp(5,2,1)", true, wallUp);
		wallUp = aCell.getWallUp(2);
		Assert.assertEquals("getWallUp(5,2,2)", true, wallUp);
		wallUp = aCell.getWallUp(3);
		Assert.assertEquals("getWallUp(5,2,3)", true, wallUp);

	}

}
