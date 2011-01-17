package javaBot.tests;

import java.awt.Color;

import javaBot.Led;
import junit.framework.Assert;
import junit.framework.TestCase;

public class LedTest extends TestCase
{
	private Led testLed;
	
	public void setUp()
	{
		testLed = new Led(java.awt.Color.RED);
	}	

	/*
	 * Test method for 'javaBot.Led.setValue(double)'
	 */
	public void testSetValueFraction() 
	{
		testLed.setValue(0.5);
		Assert.assertEquals(1.0, testLed.getValue(), 0.0);
		testLed.setValue(0.25);
		Assert.assertEquals(1.0, testLed.getValue(), 0.0);		
	}
	
	/*
	 * Test method for 'javaBot.Led.setValue(double)'
	 */
	public void testSetValueNegative() 
	{
		testLed.setValue(-1.0);
		Assert.assertEquals(0.0, testLed.getValue(), 0.0);
		testLed.setValue(-0.1);
		Assert.assertEquals(0.0, testLed.getValue(), 0.0);		
	}
	
	/*
	 * Test method for 'javaBot.Led.setValue(double)'
	 */
	public void testSetValueLargerThenOne() 
	{
		testLed.setValue(2.0);
		Assert.assertEquals(1.0, testLed.getValue(), 0.0);
		testLed.setValue(4.5);
		Assert.assertEquals(1.0, testLed.getValue(), 0.0);		
	}	
	
	/*
	 * Test method for 'javaBot.Led.setValue(double)'
	 */
	public void testSetValueNormal()
	{
		testLed.setValue(1.0);
		Assert.assertEquals(1.0, testLed.getValue(), 0.0);
		testLed.setValue(0.0);
		Assert.assertEquals(0.0, testLed.getValue(), 0.0);
	}

	/*
	 * Test method for 'javaBot.Led.Led(Color, int)'
	 */
	public void testLedColorInt()
	{
		Led testLed1 = new Led(java.awt.Color.RED, 0);
		Led testLed2 = new Led(java.awt.Color.RED, 1);
		Led testLed3 = new Led(java.awt.Color.RED, 2);
		Led testLed4 = new Led(java.awt.Color.RED, -1);
		Assert.assertEquals(0.0, testLed1.getValue(), 0.0);
		Assert.assertEquals(1.0, testLed2.getValue(), 0.0);
		Assert.assertEquals(1.0, testLed3.getValue(), 0.0);
		Assert.assertEquals(0.0, testLed4.getValue(), 0.0);
	}

	/*
	 * Test method for 'javaBot.Led.Led(Color)'
	 */
	public void testLedColorValue()
	{
		Led testLed = new Led(java.awt.Color.RED);
		assertTrue(testLed.getValue() == 1.0);
	}
	
	/*
	 * Test method for 'javaBot.Led.Led(Color)'
	 */
	public void testLedColor()
	{
		Led testLed = new Led(Color.RED);
		assertEquals(testLed.getColor(), Color.RED);
	}

}
