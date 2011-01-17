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

public class WallTest extends TestCase
{
	/**
	 * Constructor for WallTest.
	 * 
	 * @param arg0
	 */
	public WallTest(String arg0)
	{
		super(arg0);
	}

	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(WallTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		Wall.init(10, 5);
		Cell.init(10, 5);
		Assert.assertEquals("Init", 85, Wall.SIZE);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/*
	 * Test for Wall getWall(int)
	 */
	public void testGetWallint()
	{
		Wall aWall = null;
		// First test horizontal
		int X = 0;
		aWall = Wall.getWall(X + 0);
		Assert.assertEquals("getWall(0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0) HY", 1, aWall.getY());
		Assert.assertEquals("getWall(0) Up", true, aWall.isUp());
		aWall = Wall.getWall(X + 1);
		Assert.assertEquals("getWall(1) HX", 1, aWall.getX());
		Assert.assertEquals("getWall(1) HY", 1, aWall.getY());
		aWall = Wall.getWall(X + 9);
		Assert.assertEquals("getWall(9) HX", 9, aWall.getX());
		Assert.assertEquals("getWall(9) HY", 1, aWall.getY());
		aWall = Wall.getWall(X + 10);
		Assert.assertEquals("getWall(10) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(10) HY", 2, aWall.getY());
		aWall = Wall.getWall(X + 39);
		Assert.assertEquals("getWall(39) HX", 9, aWall.getX());
		Assert.assertEquals("getWall(39) HY", 4, aWall.getY());
		// Now test vertical - start at 40
		X = 40;
		aWall = Wall.getWall(X + 0);
		Assert.assertEquals("getWall(0) VX", 1, aWall.getX());
		Assert.assertEquals("getWall(0) VY", 0, aWall.getY());
		Assert.assertEquals("getWall(0) Up", true, aWall.isUp());
		aWall = Wall.getWall(X + 1);
		Assert.assertEquals("getWall(1) VX", 2, aWall.getX());
		Assert.assertEquals("getWall(1) VY", 0, aWall.getY());
		aWall = Wall.getWall(X + 8);
		Assert.assertEquals("getWall(8) VX", 9, aWall.getX());
		Assert.assertEquals("getWall(8) VY", 0, aWall.getY());
		aWall = Wall.getWall(X + 9);
		Assert.assertEquals("getWall(9) VX", 1, aWall.getX());
		Assert.assertEquals("getWall(9) VY", 1, aWall.getY());
		aWall = Wall.getWall(X + 44);
		Assert.assertEquals("getWall(44) VX", 9, aWall.getX());
		Assert.assertEquals("getWall(44) VY", 4, aWall.getY());
	}

	/*
	 * Test for Wall getWall(int, int, boolean)
	 */
	public void testGetWallintintboolean()
	{
		Wall aWall = null;
		// First dummy tests
		aWall = Wall.getWall(-1, 0, true);
		Assert.assertEquals("getWall(-1,0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(-1,0) HY", 0, aWall.getY());
		aWall = Wall.getWall(10, 0, true);
		Assert.assertEquals("getWall(10,0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(10,0) HY", 0, aWall.getY());
		aWall = Wall.getWall(0, 0, true);
		Assert.assertEquals("getWall(0,0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,0) HY", 0, aWall.getY());
		aWall = Wall.getWall(0, 5, true);
		Assert.assertEquals("getWall(0,5) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,5) HY", 0, aWall.getY());
		aWall = Wall.getWall(0, 0, false);
		Assert.assertEquals("getWall(0,0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,0) HY", 0, aWall.getY());
		aWall = Wall.getWall(1, 10, false);
		Assert.assertEquals("getWall(1,10) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(1,10) HY", 0, aWall.getY());
		aWall = Wall.getWall(1, -1, false);
		Assert.assertEquals("getWall(1,-1) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(1,-1) HY", 0, aWall.getY());
		aWall = Wall.getWall(1, 5, false);
		Assert.assertEquals("getWall(1,5) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(1,5) HY", 0, aWall.getY());

		// First test horizontal
		aWall = Wall.getWall(0, 1, true);
		Assert.assertEquals("getWall(0,1) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,1) HY", 1, aWall.getY());
		aWall = Wall.getWall(1, 1, true);
		Assert.assertEquals("getWall(1,1) HX", 1, aWall.getX());
		Assert.assertEquals("getWall(1,1) HY", 1, aWall.getY());
		aWall = Wall.getWall(9, 1, true);
		Assert.assertEquals("getWall(9,1) HX", 9, aWall.getX());
		Assert.assertEquals("getWall(9,1) HY", 1, aWall.getY());
		aWall = Wall.getWall(0, 2, true);
		Assert.assertEquals("getWall(0,2) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,2) HY", 2, aWall.getY());
		aWall = Wall.getWall(9, 4, true);
		Assert.assertEquals("getWall(9,4) HX", 9, aWall.getX());
		Assert.assertEquals("getWall(9,4) HY", 4, aWall.getY());

		// Now test vertical - start at 40
		aWall = Wall.getWall(1, 0, false);
		Assert.assertEquals("getWall(1,0) VX", 1, aWall.getX());
		Assert.assertEquals("getWall(1,0) VY", 0, aWall.getY());
		aWall = Wall.getWall(2, 0, false);
		Assert.assertEquals("getWall(2,0) VX", 2, aWall.getX());
		Assert.assertEquals("getWall(2,0) VY", 0, aWall.getY());
		aWall = Wall.getWall(9, 0, false);
		Assert.assertEquals("getWall(9,0) VX", 9, aWall.getX());
		Assert.assertEquals("getWall(9,0) VY", 0, aWall.getY());
		aWall = Wall.getWall(1, 1, false);
		Assert.assertEquals("getWall(1,1) VX", 1, aWall.getX());
		Assert.assertEquals("getWall(1,1) VY", 1, aWall.getY());
		aWall = Wall.getWall(9, 4, false);
		Assert.assertEquals("getWall(9,4) VX", 9, aWall.getX());
		Assert.assertEquals("getWall(9,4) VY", 4, aWall.getY());
	}

	/*
	 * Test for Wall getWall(int, int, int)
	 */
	public void testGetWallintintint()
	{
		Wall aWall = null;
		// Top
		aWall = Wall.getWall(0, 1, 0);
		Assert.assertEquals("getWall(0,1,0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,1,0) HY", 0, aWall.getY());
		aWall = Wall.getWall(0, 4, 0);
		Assert.assertEquals("getWall(0,4,0) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,4,0) HY", 3, aWall.getY());

		// Bottom
		aWall = Wall.getWall(0, 0, 1);
		Assert.assertEquals("getWall(0,0,1) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,0,1) HY", 1, aWall.getY());
		aWall = Wall.getWall(0, 3, 1);
		Assert.assertEquals("getWall(0,3,1) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(0,3,1) HY", 4, aWall.getY());

		// Left
		aWall = Wall.getWall(1, 0, 2);
		Assert.assertEquals("getWall(1,0,2) HX", 0, aWall.getX());
		Assert.assertEquals("getWall(1,0,2) HY", 0, aWall.getY());
		aWall = Wall.getWall(2, 0, 2);
		Assert.assertEquals("getWall(2,0,2) HX", 1, aWall.getX());
		Assert.assertEquals("getWall(2,0,2) HY", 0, aWall.getY());

		// Right
		aWall = Wall.getWall(0, 0, 3);
		Assert.assertEquals("getWall(0,0,3) HX", 1, aWall.getX());
		Assert.assertEquals("getWall(0,0,3) HY", 0, aWall.getY());
		aWall = Wall.getWall(8, 0, 3);
		Assert.assertEquals("getWall(8,0,3) HX", 9, aWall.getX());
		Assert.assertEquals("getWall(8,0,3) HY", 0, aWall.getY());

		// Middle
		aWall = Wall.getWall(5, 2, 0);
		Assert.assertEquals("getWall(5,2,0) HX", 5, aWall.getX());
		Assert.assertEquals("getWall(5,2,0) HY", 1, aWall.getY());
		aWall = Wall.getWall(5, 2, 1);
		Assert.assertEquals("getWall(5,2,1) HX", 5, aWall.getX());
		Assert.assertEquals("getWall(5,2,1) HY", 3, aWall.getY());
		aWall = Wall.getWall(5, 2, 2);
		Assert.assertEquals("getWall(5,2,2) HX", 4, aWall.getX());
		Assert.assertEquals("getWall(5,2,2) HY", 2, aWall.getY());
		aWall = Wall.getWall(5, 2, 3);
		Assert.assertEquals("getWall(5,2,3) HX", 6, aWall.getX());
		Assert.assertEquals("getWall(5,2,3) HY", 2, aWall.getY());
	}

	/*
	 * Test for Wall getWall()
	 */
	public void testGetWall()
	{
		Wall aWall = null;
		aWall = Wall.getWall();
		//		Assert.assertEquals("getWall() X", 0, aWall.X);
		//		Assert.assertEquals("getWall() Y", 0, aWall.Y);
		Assert.assertTrue("1-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("1-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("2-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("2-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("3-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("3-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("4-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("4-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("5-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("5-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("6-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("6-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("7-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("7-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
		aWall = Wall.getWall();
		Assert.assertTrue("8-getWall() X", aWall.getX() >= 0 && aWall.getX() <= 9);
		Assert.assertTrue("8-getWall() Y", aWall.getY() >= 0 && aWall.getY() <= 5);
	}

	public void testGetCells()
	{
		int[] cells;
		Wall aWall = null;
		// Dummy
		aWall = Wall.getWall(0, 0, true);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(0,0) HL", 2, cells.length);
		Assert.assertEquals("getCells(0,0) HI", -1, aWall.getIndex());
		Assert.assertEquals("getCells(0,0) HA", 0, cells[0]);
		Assert.assertEquals("getCells(0,0) HB", 0, cells[1]);
		// Horizontal
		aWall = Wall.getWall(0, 1, true);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(0,1) HI", 0, aWall.getIndex());
		Assert.assertEquals("getCells(0,1) HA", 10, cells[0]);
		Assert.assertEquals("getCells(0,1) HB", 0, cells[1]);
		aWall = Wall.getWall(0, 4, true);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(0,4) HI", 30, aWall.getIndex());
		Assert.assertEquals("getCells(0,4) HA", 40, cells[0]);
		Assert.assertEquals("getCells(0,4) HB", 30, cells[1]);
		aWall = Wall.getWall(9, 1, true);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(9,1) HI", 9, aWall.getIndex());
		Assert.assertEquals("getCells(9,1) HA", 19, cells[0]);
		Assert.assertEquals("getCells(9,1) HB", 9, cells[1]);
		aWall = Wall.getWall(9, 4, true);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(9,4) HI", 39, aWall.getIndex());
		Assert.assertEquals("getCells(9,4) HA", 49, cells[0]);
		Assert.assertEquals("getCells(9,4) HB", 39, cells[1]);
		// Vertical
		aWall = Wall.getWall(1, 0, false);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(1,0) HI", 40, aWall.getIndex());
		Assert.assertEquals("getCells(1,0) VA", 1, cells[0]);
		Assert.assertEquals("getCells(1,0) VB", 0, cells[1]);
		aWall = Wall.getWall(1, 4, false);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(1,4) HI", 76, aWall.getIndex());
		Assert.assertEquals("getCells(1,4) VA", 41, cells[0]);
		Assert.assertEquals("getCells(1,4) VB", 40, cells[1]);
		aWall = Wall.getWall(9, 0, false);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(9,0) HI", 48, aWall.getIndex());
		Assert.assertEquals("getCells(9,0) VA", 9, cells[0]);
		Assert.assertEquals("getCells(9,0) VB", 8, cells[1]);
		aWall = Wall.getWall(9, 4, false);
		cells = aWall.getCells();
		Assert.assertEquals("getCells(9,4) HI", 84, aWall.getIndex());
		Assert.assertEquals("getCells(9,4) VA", 49, cells[0]);
		Assert.assertEquals("getCells(9,4) VB", 48, cells[1]);
	}
}
