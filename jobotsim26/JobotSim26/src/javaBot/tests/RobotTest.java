package javaBot.tests;

import javaBot.JPBRobot;
import javaBot.RCJobot;
import javaBot.Sensor;
import javaBot.World;

import junit.framework.TestCase;

public class RobotTest extends TestCase
{
	String[]	sensors;

	public RobotTest(String arg0)
	{
		super(arg0);

		sensors = new String[] {"ReflectionSensor", "ContactSensor", "FieldSensor", "IRSensor"};

	}

	protected void setUp() throws Exception
	{
		// wat hier staat wordt voorafgaand aan elke test uitgevoerd		
	}

	/*
	 * Test for a robot (in this case InnerRCJobot) if it's possible to initiate
	 * all the sensors and tests if the angles can be properly set and get.
	 */
	public void testAddAllSensors() throws Exception
	{
		InnerRCJobot robot = new InnerRCJobot();

		for (int i = 0; i < sensors.length; i++)
		{
			String sensor = sensors[i];

			// Test addSensor with correct class, existing sensor
			Sensor addSensorReflection = robot.addSensor(sensor, 0.072, 270);
			assertTrue("Creating a " + sensor + " with method addSensor succeeded",
					(addSensorReflection.getName().equals(sensor)));

			// tests for the createdSensor if the angles can be correctly set
			// and retreived
			double retreivedAngle = addSensorReflection.getAngle();
			double expectedAngle = robot.degreesToRadians(270);
			assertTrue("Sensor angle has been properly set", (retreivedAngle == expectedAngle));
		}
	}

	/*
	 * Tests if it's possible to add an Agent likt it's a sensor with the added
	 * reflection method to create sensors
	 */
	public void testAddingWrongSensor()
	{
		InnerRCJobot robot = new InnerRCJobot();

			String sensor = sensors[0]; // reflectionSensor
			Sensor addSensorReflection = robot.addSensor("RescueAgent", 0.072, 270);
			
			if( addSensorReflection == null)
			{
				assertFalse(
						"Creating a 'RescueAgent' like it is a sensor, with method addSensor succeeded, so Not OK", false);
			}
			else
			{
				//this may NOT happen!
				assertFalse(
						"Creating a 'RescueAgent' like it is a sensor, with method addSensor succeeded, so Not OK",
						true);
			}
	}

	/*
	 * Tests the function to convert degrees into radians.
	 */
	public void testDegreesToRadians()
	{
		JPBRobot robot = new JPBRobot("testDegreesToRadians", 1.0, 1.0);

		// test data, hardcoded
		double degree = 270;
		double rad = (9 / 6.0) * Math.PI;

		double result = robot.degreesToRadians(degree);

		// there is no function for 2 doubles
		assertTrue("Degrees to Radians succesfull", (result == rad));
	}
	
//	/** Disabled for "Field Boundaries" function*/	
//	public void testLeftOutTheFieldCollides() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(true);
//		inner.setDiameter(2.01);	
//		double[] expected = {1.0, 2.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(0.0, 2.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testRightOutTheFieldCollides() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(true);
//		inner.setDiameter(2.01);	
//		double[] expected = {3.0, 2.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(4.0, 2.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testBottomOutTheFieldCollides() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(true);
//		inner.setDiameter(2.01);	
//		double[] expected = {3.0, 1.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(3.0, 0.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testTopOutTheFieldCollides() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(true);
//		inner.setDiameter(2.01);	
//		double[] expected = {3.0, 4.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(3.0, 5.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testInTheFieldCollides() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(true);
//		inner.setDiameter(2.01);	
//		double[] expected = {2.0, 2.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(2.0, 2.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testLeftOutTheField() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(false);
//		inner.setDiameter(2.01);	
//		double[] expected = {3.9, 2.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(0.0, 2.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testRightOutTheField() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(false);
//		inner.setDiameter(2.01);	
//		double[] expected = {0.1, 2.0};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(4.0, 2.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testBottomOutTheField() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(false);
//		inner.setDiameter(2.01);	
//		double[] expected = {3.0, 4.9};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(3.0, 0.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}
//	
//	public void testTopOutTheField() {
//		World.HEIGHT = 5;
//		World.WIDTH = 4;
//		
//		InnerRCJobot inner = new InnerRCJobot();
//		inner.setCollideWithBoundaries(false);
//		inner.setDiameter(2.01);	
//		double[] expected = {3.0, 0.1};		
//		double[] actual = inner.getCoordinatesAccordingToBoundary(3.0, 5.0);		
//		assertEquals(arrayToString(expected),arrayToString(actual));
//	}		
	
	private String arrayToString(double[] doubleArray) {
		String result = "";
		
		for(int i = 0; i < doubleArray.length; i++)
		{
			if (i == doubleArray.length - 1)
			{
				result += doubleArray[i];
			}
			else 
			{
				result += doubleArray[i] + ",";
			}
		}	
		return result;	
	}
	

	/**
	 * These Inner class is build only for test purposes. The abstract robot
	 * class has a protected addSensor method, with this InnerClass we make it
	 * visible for our tests without modifying the actually abstract Robot Class
	 */
	private class InnerRCJobot extends RCJobot
	{
		
		public InnerRCJobot()
		{
			super("test", 1.0, 1.0);
		}

		public Sensor addSensor(String sensorClassName, double radius, double position)
		{
			Sensor sensor = null;

			try
			{
				sensor = super.addSensor(sensorClassName, radius, position);
			}
			catch (Exception e)
			{
				// When not succeeded, just return null;
			}

			return sensor;
		}
		
		// Zet de diameter op een bepaalde waarde, zodat je gemakkelijk kan testen.
		// diameter komt uit physicalObject
		public void setDiameter(double diameter) {
			super.diameter = diameter;
		}
		
		public double[] getCoordinatesAccordingToBoundary(double x, double y) {
			return super.getCoordinatesAccordingToBoundary(x, y);
		}
		
//		/** Disabled for "Field Boundaries" function*/	
//		public void setCollideWithBoundaries(boolean collide) {
//			super.setCollideWithBoundaries(collide);
//		}

	}

}
