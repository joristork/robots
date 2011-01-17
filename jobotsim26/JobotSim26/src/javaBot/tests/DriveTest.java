/*
 * Created on March 24, 2004 
 * Copyright: (c) 2006 
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */

package javaBot.tests;

/**
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */

import junit.framework.Assert;
import junit.framework.TestCase;

public class DriveTest extends TestCase
{
	private JobotTestController	a;
	private int					max	= 156;

	/**
	 * Constructor for AgentTest.
	 * 
	 * @param arg0
	 */
	public DriveTest(String arg0)
	{
		super(arg0);
	}

	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(DriveTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		a = new JobotTestController(max);
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testScale()
	{
		int i;
		i = a.scale(-100);
		Assert.assertEquals("0 scale(-100)", 1, i);
		i = a.scale(-80);
		Assert.assertEquals("1 scale(-80)", 16, i);
		i = a.scale(-60);
		Assert.assertEquals("2 scale(-60)", 32, i);
		i = a.scale(-40);
		Assert.assertEquals("3 scale(-40)", 47, i);
		i = a.scale(-20);
		Assert.assertEquals("4 scale(-20)", 63, i);
		i = a.scale(0);
		Assert.assertEquals("5 scale(0)", 78, i);
		i = a.scale(20);
		Assert.assertEquals("6 scale(20)", 93, i);
		i = a.scale(40);
		Assert.assertEquals("7 scale(40)", 109, i);
		i = a.scale(60);
		Assert.assertEquals("8 scale(60)", 124, i);
		i = a.scale(80);
		Assert.assertEquals("9 scale(80)", 140, i);
		i = a.scale(100);
		Assert.assertEquals("10 scale(100)", 155, i);
	}

	// To test this part, change updateServos() in
	// the Drive code to updateServos(max);
	public void testDrive()
	{
		a.drive(0, 0, 0);
		Assert.assertEquals("0 Drive(0,0,0", 78, a.getServo(0));
		Assert.assertEquals("1 Drive(0,0,0", 78, a.getServo(1));
		Assert.assertEquals("2 Drive(0,0,0", 78, a.getServo(2));
		a.drive(100, 0, 0);
		Assert.assertEquals("0 Drive(100,0,0", 100, a.getServo(0));
		Assert.assertEquals("1 Drive(100,0,0", 78, a.getServo(1));
		Assert.assertEquals("2 Drive(100,0,0", 78, a.getServo(2));
		a.drive(0, 100, 0);
		Assert.assertEquals("0 Drive(0,100,0", 78, a.getServo(0));
		Assert.assertEquals("1 Drive(0,100,0", 100, a.getServo(1));
		Assert.assertEquals("2 Drive(0,100,0", 78, a.getServo(2));
		a.drive(0, 0, 100);
		Assert.assertEquals("0 Drive(0,0,100", 78, a.getServo(0));
		Assert.assertEquals("1 Drive(0,0,100", 78, a.getServo(1));
		Assert.assertEquals("2 Drive(0,0,100", 100, a.getServo(2));
		a.drive(5, -2, 5);
		Assert.assertEquals("0 Drive(5,-2,5", 81, a.getServo(0));
		Assert.assertEquals("1 Drive(5,-2,5", 77, a.getServo(1));
		Assert.assertEquals("2 Drive(5,-2,5", 81, a.getServo(2));
	}

	public void testVelToPos()
	{
		int i = 0;
		i = a.velToPos(-100);
		Assert.assertEquals("0 velToPos(-100)", 0, i); // -75
		i = a.velToPos(-90);
		Assert.assertEquals("1 velToPos(-90)", 16, i); // -59
		i = a.velToPos(-80);
		Assert.assertEquals("2 velToPos(-80)", 29, i); // -46
		i = a.velToPos(-70);
		Assert.assertEquals("3 velToPos(-70)", 40, i); // -35
		i = a.velToPos(-60);
		Assert.assertEquals("4 velToPos(-60)", 49, i); // -26
		i = a.velToPos(-50);
		Assert.assertEquals("5 velToPos(-50)", 56, i); // -19
		i = a.velToPos(-40);
		Assert.assertEquals("6 velToPos(-40)", 62, i); // -13
		i = a.velToPos(-30);
		Assert.assertEquals("7 velToPos(-30)", 67, i); // -8
		i = a.velToPos(-20);
		Assert.assertEquals("8 velToPos(-20)", 70, i); // -5
		i = a.velToPos(-10);
		Assert.assertEquals("9 velToPos(-10)", 73, i); // -2
		i = a.velToPos(0);
		Assert.assertEquals("10 velToPos(0)", 75, i); // Middle
		// 62
		i = a.velToPos(10);
		Assert.assertEquals("11 velToPos(10)", 77, i); // 2
		i = a.velToPos(20);
		Assert.assertEquals("12 velToPos(20)", 79, i); // 4
		i = a.velToPos(30);
		Assert.assertEquals("13 velToPos(30)", 81, i); // 6
		i = a.velToPos(40);
		Assert.assertEquals("14 velToPos(40)", 84, i); // 9
		i = a.velToPos(50);
		Assert.assertEquals("15 velToPos(50)", 87, i); // 12
		i = a.velToPos(60);
		Assert.assertEquals("16 velToPos(60)", 91, i); // 16
		i = a.velToPos(70);
		Assert.assertEquals("17 velToPos(70)", 97, i); // 22
		i = a.velToPos(80);
		Assert.assertEquals("18 velToPos(80)", 104, i); // 29
		i = a.velToPos(90);
		Assert.assertEquals("19 velToPos(90)", 112, i); // 37
		i = a.velToPos(100);
		Assert.assertEquals("20 velToPos(100)", 124, i); // 49
	}
}
