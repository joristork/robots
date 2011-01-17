/*
 * Created on March 06, 2004 
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

public class AgentTest extends TestCase
{
	private JobotTestController	a;
	private int					max	= 156;

	/**
	 * Constructor for AgentTest.
	 * 
	 * @param arg0
	 */
	public AgentTest(String arg0)
	{
		super(arg0);
	}

	public static void main(String[] args)
	{
		junit.awtui.TestRunner.run(AgentTest.class);
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

	public void testVector_drive()
	{
		// 4 directions
		a.vectorDrive(0, 0, 0);
		Assert.assertEquals("0 VectorDrive(0,0,0)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,0,0)", 0, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,0,0)", 0, a.getServo(2));
		a.vectorDrive(100, 0, 0);
		Assert.assertEquals("0 VectorDrive(100,0,0)", -100, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(100,0,0)", 50, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(100,0,0)", 50, a.getServo(2));
		a.vectorDrive(0, 100, 0);
		Assert.assertEquals("0 VectorDrive(0,100,0)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,0)", -87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,0)", 87, a.getServo(2));
		a.vectorDrive(0, 0, 100);
		Assert.assertEquals("0 VectorDrive(0,0,100)", 72, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,0,100)", 72, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,0,100)", 72, a.getServo(2));

		// Gyrate
		//    0 100 0
		//   50 86 0
		//  100 0 0
		//   86 -50 0
		//   50 -86 0
		//    0, -100 0
		//  -50, -86, 0
		//  -86, -50, 0
		// -100, 0, 0
		//  -86, 50, 0
		//  -50, 86, 0

		//    0 100 0
		a.vectorDrive(0, 100, 0);
		Assert.assertEquals("0 VectorDrive(0,100,0)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,0)", -87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,0)", 87, a.getServo(2));
		//   50 86 0
		a.vectorDrive(50, 87, 0);
		Assert.assertEquals("0 VectorDrive(50,87,0)", -50, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(50,87,0)", -50, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(50,87,0)", 100, a.getServo(2));
		//  100 0 0
		a.vectorDrive(100, 0, 0);
		Assert.assertEquals("0 VectorDrive(100,0,0)", -100, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(100,0,0)", 50, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(100,0,0)", 50, a.getServo(2));
		//   86 -50 0
		a.vectorDrive(87, -50, 0);
		Assert.assertEquals("0 VectorDrive(87,-50,0)", -87, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(87,-50,0)", 87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(87,-50,0)", 0, a.getServo(2));
		//   50 -86 0
		a.vectorDrive(50, -87, 0);
		Assert.assertEquals("0 VectorDrive(50,-87,0)", -50, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(50,-87,0)", 100, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(50,-87,0)", -50, a.getServo(2));
		//    0, -100 0
		a.vectorDrive(0, -100, 0);
		Assert.assertEquals("0 VectorDrive(0,-100,0)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,-100,0)", 87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,-100,0)", -87, a.getServo(2));
		//  -50, -86, 0
		a.vectorDrive(-50, -87, 0);
		Assert.assertEquals("0 VectorDrive(-50,-87,0)", 50, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-50,-87,0)", 50, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-50,-87,0)", -100, a.getServo(2));
		//  -86, -50, 0
		a.vectorDrive(-87, -50, 0);
		Assert.assertEquals("0 VectorDrive(-87,-50,0)", 87, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-87,-50,0)", 0, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-87,-50,0)", -87, a.getServo(2));
		// -100, 0, 0
		a.vectorDrive(-100, 0, 0);
		Assert.assertEquals("0 VectorDrive(-100,0,0)", 100, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-100,0,0)", -50, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-100,0,0)", -50, a.getServo(2));
		//  -86, 50, 0
		a.vectorDrive(-87, 50, 0);
		Assert.assertEquals("0 VectorDrive(-87,50,0)", 87, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-87,50,0)", -87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-87,50,0)", 0, a.getServo(2));
		//  -50, 86, 0
		a.vectorDrive(-50, 87, 0);
		Assert.assertEquals("0 VectorDrive(-50,87,0)", 50, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-50,87,0)", -100, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-50,87,0)", 50, a.getServo(2));

		a.vectorDrive(-100, 0, 0);
		Assert.assertEquals("0 VectorDrive(-100,0,0)", 100, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-100,0,0)", -50, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-100,0,0)", -50, a.getServo(2));
		a.vectorDrive(0, -100, 0);
		Assert.assertEquals("0 VectorDrive(0,-100,0)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,-100,0)", 87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,-100,0)", -87, a.getServo(2));
		a.vectorDrive(0, 0, -100);
		Assert.assertEquals("0 VectorDrive(0,0,-100)", -72, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,0,-100)", -72, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,0,-100)", -72, a.getServo(2));

		a.vectorDrive(50, 50, 0);
		Assert.assertEquals("0 VectorDrive(50,50,0)", -50, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(50,50,0)", -18, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(50,50,0)", 68, a.getServo(2));
		a.vectorDrive(0, 100, 100);
		Assert.assertEquals("0 VectorDrive(0,100,100)", 72, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,100)", -15, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,100)", 100, a.getServo(2));
		a.vectorDrive(100, 0, 100);
		Assert.assertEquals("0 VectorDrive(100,0,100)", -28, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(100,0,100)", 100, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(100,0,100)", 100, a.getServo(2));
		a.vectorDrive(50, 50, 100);
		Assert.assertEquals("0 VectorDrive(50,50,100)", 22, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(50,50,100)", 53, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(50,50,100)", 100, a.getServo(2));

		a.vectorDrive(-50, -50, 0);
		Assert.assertEquals("0 VectorDrive(-50,-50,0)", 50, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-50,-50,0)", 18, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-50,-50,0)", -68, a.getServo(2));
		a.vectorDrive(0, -100, -100);
		Assert.assertEquals("0 VectorDrive(0,-100,-100)", -72, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,-100,-100)", 15, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,-100,-100)", -100, a.getServo(2));
		a.vectorDrive(-100, 0, -100);
		Assert.assertEquals("0 VectorDrive(-100,0,-100)", 28, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-100,0,-100)", -100, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-100,0,-100)", -100, a.getServo(2));
		a.vectorDrive(-50, -50, -100);
		Assert.assertEquals("0 VectorDrive(-50,-50,-100)", -22, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(-50,-50,-100)", -53, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(-50,-50,-100)", -100, a.getServo(2));

		// Wall hug
		a.vectorDrive(0, 100, 0);
		Assert.assertEquals("0 VectorDrive(0,100,0)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,0)", -87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,0)", 87, a.getServo(2));
		a.vectorDrive(0, 100, 1);
		Assert.assertEquals("0 VectorDrive(0,100,1)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,1)", -86, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,1)", 87, a.getServo(2));
		a.vectorDrive(0, 100, 15);
		Assert.assertEquals("0 VectorDrive(0,100,15)", 10, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,15)", -76, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,15)", 97, a.getServo(2));
		a.vectorDrive(0, 100, 30);
		Assert.assertEquals("0 VectorDrive(0,100,30)", 21, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,30)", -65, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,30)", 100, a.getServo(2));
		a.vectorDrive(0, 100, 60);
		Assert.assertEquals("0 VectorDrive(0,100,60)", 43, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,60)", -43, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,60)", 100, a.getServo(2));

		a.vectorDrive(0, 100, -1);
		Assert.assertEquals("0 VectorDrive(0,100,-1)", 0, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,-1)", -87, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,-1)", 86, a.getServo(2));
		a.vectorDrive(0, 100, -15);
		Assert.assertEquals("0 VectorDrive(0,100,-15)", -10, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,-15)", -97, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,-15)", 76, a.getServo(2));
		a.vectorDrive(0, 100, -30);
		Assert.assertEquals("0 VectorDrive(0,100,-30)", -21, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,-30)", -100, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,-30)", 65, a.getServo(2));
		a.vectorDrive(0, 100, -60);
		Assert.assertEquals("0 VectorDrive(0,100,-60)", -43, a.getServo(0));
		Assert.assertEquals("1 VectorDrive(0,100,-60)", -100, a.getServo(1));
		Assert.assertEquals("2 VectorDrive(0,100,-60)", 43, a.getServo(2));
	}
}
