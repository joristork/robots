/*
 * Created on 24-feb-2006 Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.tests;

import javaBot.DistanceSensor;
import junit.framework.Assert;
import junit.framework.TestCase;

public class DistanceSensorTest extends TestCase
{

	int				belowTwenty[]	= {
			0,
			151,
			1,
			160,
			2,
			190,
			3,
			215,
			4,
			316,
			5,
			369,
			6,
			392,
			7,
			472,
			8,
			507,
			9,
			503,
			10,
			472,
			11,
			450,
			12,
			420,
			13,
			391,
			14,
			365,
			15,
			340,
			16,
			321,
			17,
			312,
			18,
			305,
			19,
			290,
			20,
			270						};
	DistanceSensor	ds				= new DistanceSensor(new javaBot.Location(0, 0), 0.0);

	/*
	 * Test method for
	 * 'javaBot.ds.convertDistanceToValue(double)'
	 */
	public void testConvertDistanceToValueNegative()
	{
		Assert.assertEquals(-1, ds.convertDistanceToValue(-1));
		Assert.assertEquals(-1, ds.convertDistanceToValue(-2));
		Assert.assertEquals(-1, ds.convertDistanceToValue(-100));
	}

	/*
	 * Test method for
	 * 'javaBot.ds.convertDistanceToValue(double)'
	 */
	public void testConvertDistanceToValueRoundOff()
	{
		Assert.assertEquals(110, ds.convertDistanceToValue(0.45));
		Assert.assertEquals(110, ds.convertDistanceToValue(0.454));
		Assert.assertEquals(108, ds.convertDistanceToValue(0.458));
	}

	/*
	 * Test method for
	 * 'javaBot.ds.convertDistanceToValue(double)'
	 */
	public void testConvertDistanceToValueGraphBend()
	{
		// Value goes up
		Assert.assertEquals(392, ds.convertDistanceToValue(0.06));
		Assert.assertEquals(472, ds.convertDistanceToValue(0.07));
		Assert.assertEquals(507, ds.convertDistanceToValue(0.08));
		// Value goes down (bend occured in graph
		// between 8 and 9 centimers)
		Assert.assertEquals(503, ds.convertDistanceToValue(0.09));
		Assert.assertEquals(472, ds.convertDistanceToValue(0.10));
		Assert.assertEquals(450, ds.convertDistanceToValue(0.11));
	}

	/*
	 * Test method for
	 * 'javaBot.ds.convertDistanceToValue(double)'
	 */
	public void testConvertDistanceToValueBelow21CM()
	{
		for (int i = 0; i < belowTwenty.length; i += 2)
		{
			Assert.assertEquals(belowTwenty[i + 1], ds
					.convertDistanceToValue(belowTwenty[i] / 100.0));
		}
	}

	/*
	 * Test method for
	 * 'javaBot.ds.convertDistanceToValue(double)'
	 */
	public void testConvertDistanceToValueBAbove20CMBelow80CM()
	{
		// Just take some samples at the beginning
		Assert.assertEquals(260, ds.convertDistanceToValue(0.21));
		Assert.assertEquals(250, ds.convertDistanceToValue(0.22));
		Assert.assertEquals(240, ds.convertDistanceToValue(0.23));
		Assert.assertEquals(230, ds.convertDistanceToValue(0.24));
		Assert.assertEquals(220, ds.convertDistanceToValue(0.25));
		// Just take some samples in the middle
		Assert.assertEquals(155, ds.convertDistanceToValue(0.34));
		Assert.assertEquals(120, ds.convertDistanceToValue(0.42));
		Assert.assertEquals(105, ds.convertDistanceToValue(0.47));
		Assert.assertEquals(88, ds.convertDistanceToValue(0.54));
		Assert.assertEquals(75, ds.convertDistanceToValue(0.65));
		Assert.assertEquals(71, ds.convertDistanceToValue(0.69));
		Assert.assertEquals(70, ds.convertDistanceToValue(0.70));
	}

	/*
	 * Test method for
	 * 'javaBot.ds.convertDistanceToValue(double)'
	 */
	public void testConvertDistanceToValueAbove79CM()
	{
		Assert.assertEquals(0, ds.convertDistanceToValue(0.8));
		Assert.assertEquals(0, ds.convertDistanceToValue(0.9));
		Assert.assertEquals(0, ds.convertDistanceToValue(1.2));
	}
}
