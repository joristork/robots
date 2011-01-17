package javaBot.tests;

import javaBot.UVM.ServoLinearizator;
import junit.framework.TestCase;

public class ServoLinearizatorTest extends TestCase
{
	
	public void testConvertPowerToServoSpeedMaxPower()
	{
		assertEquals(34, ServoLinearizator.convertPowerToServoSpeed(100));
	}

	public void testConvertPowerToServoSpeedNegative()
	{
		assertEquals(-9, ServoLinearizator.convertPowerToServoSpeed(-50));
	}

	public void testConvertPowerToServoSpeedPositive()
	{
		assertEquals(9, ServoLinearizator.convertPowerToServoSpeed(50));
	}

	public void testConvertPowerToServoSpeedZero()
	{
		assertEquals(1, ServoLinearizator.convertPowerToServoSpeed(0));
	}

	public void testConvertPowerToServoSpeedInterpolate()
	{
		assertEquals(6, ServoLinearizator.convertPowerToServoSpeed(30));
		assertEquals(10, ServoLinearizator.convertPowerToServoSpeed(60));
		assertEquals(13, ServoLinearizator.convertPowerToServoSpeed(85));
		assertEquals(26, ServoLinearizator.convertPowerToServoSpeed(95));
		assertEquals(3, ServoLinearizator.convertPowerToServoSpeed(10));
	}

	public void testConvertPowerToServoSpeedMaximum()
	{
		assertEquals(34, ServoLinearizator.convertPowerToServoSpeed(100));
	}

	public void testConvertServoSpeedToPowerNegative()
	{
		assertEquals(-50, ServoLinearizator.convertServoSpeedToPower(-9));
	}

	public void testConvertServoSpeedToPowerPositive()
	{
		assertEquals(50, ServoLinearizator.convertServoSpeedToPower(9));
	}

	public void testConvertServoSpeedToPowerResultsInZero()
	{
		assertEquals(0, ServoLinearizator.convertServoSpeedToPower(1));
	}
	
	public void testConvertServoSpeedToPowerZero()
	{
		assertEquals(0, ServoLinearizator.convertServoSpeedToPower(0));
	}

	public void testConvertServoSpeedToPowerInterpolate()
	{
		assertEquals(29, ServoLinearizator.convertServoSpeedToPower(6));
		assertEquals(50, ServoLinearizator.convertServoSpeedToPower(9));
		assertEquals(8, ServoLinearizator.convertServoSpeedToPower(3));
	}

	public void testConvertServoSpeedToPowerMaximum()
	{
		assertEquals(100, ServoLinearizator.convertServoSpeedToPower(34));
	}
}
