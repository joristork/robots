package javaBot.tests;

import javaBot.Ball;
import javaBot.Line;
import javaBot.Location;
import javaBot.SoccerWalls;
import javaBot.World;
import junit.framework.TestCase;

public class SoccerWallsTest extends TestCase
{
	private SoccerWalls	soccerWalls;
	private Ball		ball;
	private Line[]		wall;
	private double		radius;

	public void setUp()
	{
		World.WIDTH = 5.1;
		World.HEIGHT = 3.0;
		soccerWalls = new SoccerWalls("Field", 3.0);
		ball = new Ball();
		wall = soccerWalls.getWall();
		radius = (ball.diameter) / 2;
		
	}
	
	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testLeftHorizontalWallsInBounds()
	{
		ball.setLocation(new Location(1.0, 2.5));
		assertFalse(soccerWalls.isObjectOutField(ball, wall[7], 7, radius));
	}

	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testLeftHorizontalWallsOutBounds()
	{
		ball.setLocation(new Location(0.1, 2.5));
		assertTrue(soccerWalls.isObjectOutField(ball, wall[7], 7, radius));
	}
	
	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testRightHorizontalWallsInBounds()
	{
		ball.setLocation(new Location(4, 0.5));
		assertFalse(soccerWalls.isObjectOutField(ball, wall[8], 8, radius));
	}

	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testRightHorizontalWallsOutBounds()
	{
		ball.setLocation(new Location(4.9, 0.1 ));
		assertTrue(soccerWalls.isObjectOutField(ball, wall[8], 8, radius));
	}

	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testLeftVerticalWallsInBounds()
	{
		ball.setLocation(new Location(1, 2.5));
		assertFalse(soccerWalls.isObjectOutField(ball, wall[3], 3, radius));
	}
	
	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testLeftVerticalWallsOutBounds()
	{
		ball.setLocation(new Location(0.2, 2.5));
		assertTrue(soccerWalls.isObjectOutField(ball, wall[3], 3, radius));
	}
	
	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testLeftVerticalWallsInBoundsBehindLeftGoal()
	{
		//Wall 4 is different from all other vertical walls
		ball.setLocation(new Location(1, 1.5));
		assertFalse(soccerWalls.isObjectOutField(ball, wall[4], 4, radius));
	}
	
	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testRightVerticalWallsInBounds()
	{
		ball.setLocation(new Location(4.0, 1));
		assertFalse(soccerWalls.isObjectOutField(ball, wall[0], 0, radius));
	}
	
	/*
	 * Test method for 'javaBot.SoccerWalls.isObjectOutField'
	 */
	public void testRightVerticalWallsOutBounds()
	{
		ball.setLocation(new Location(4.9, 0.1));
		assertTrue(soccerWalls.isObjectOutField(ball, wall[0], 0, radius));
	}
}
