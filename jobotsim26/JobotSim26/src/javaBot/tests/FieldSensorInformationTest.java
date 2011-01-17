package javaBot.tests;

import javaBot.FieldSensor;
import javaBot.FieldSensorInformation;
import javaBot.GraphicalRepresentation;
import javaBot.IRSensor;
import javaBot.Location;
import javaBot.RescueField;
import javaBot.Robot;
import javaBot.Sensor;
import javaBot.World;
import junit.framework.TestCase;

public class FieldSensorInformationTest extends TestCase
{
	private FieldSensorInformation	field;
	private DummyBot				dummyBot;
	private Sensor[]				sensorConfiguration;
	private final double ZERO = 0.0;

	public void setUp()
	{
		field = new RescueField();
		dummyBot = new DummyBot();
	}
	
	public void testGiveSensoryInformationWithNoSensor()
	{
		sensorConfiguration = new Sensor[0];
		dummyBot.setSensors(sensorConfiguration);

		double[] sensorValues = field.giveSensoryInformationTo(dummyBot);

		assertTrue(sensorValues != null);
		assertEquals(sensorValues.length, 0);

	}
	
	/**
	 * TODO 
	 * Controle of de array ook daadwerkelijk waarden bevat.
	 * (assertTrue uitcommentariëren).
	 */

	public void testGiveSensoryInformationWithOneSensor()
	{
		sensorConfiguration = new Sensor[1];
		sensorConfiguration[0] = new FieldSensor(new Location(0, 0), 0);
		dummyBot.setSensors(sensorConfiguration);

		double[] sensorValues = field.giveSensoryInformationTo(dummyBot);
		
		assertTrue(sensorValues != null);
		assertEquals(sensorValues.length, 1);		
		//assertTrue(sensorValues[0] > ZERO);
	}

	public void testGiveSensoryInformationWithTwoSensors()
	{
		sensorConfiguration = new Sensor[2];
		sensorConfiguration[0] = new FieldSensor(new Location(0, 0), 0);
		sensorConfiguration[1] = new FieldSensor(new Location(0, 0), 0);
		dummyBot.setSensors(sensorConfiguration);

		double[] sensorValues = field.giveSensoryInformationTo(dummyBot);

		assertTrue(sensorValues != null);
		assertEquals(sensorValues.length, 2);
		//assertTrue(sensorValues[0] > ZERO);
		//assertTrue(sensorValues[1] > ZERO);
	}

	public void testGiveSensoryInformationWithTwoDifferentSensors()
	{
		sensorConfiguration = new Sensor[2];
		sensorConfiguration[0] = new IRSensor(new Location(0, 0), 0);
		sensorConfiguration[1] = new FieldSensor(new Location(0, 0), 0);
		dummyBot.setSensors(sensorConfiguration);

		double[] sensorValues = field.giveSensoryInformationTo(dummyBot);

		assertTrue(sensorValues != null);
		assertEquals(sensorValues.length, 2);
		assertTrue(sensorValues[0] == ZERO);
		//assertTrue(sensorValues[1] > ZERO);
	}

	class DummyBot extends Robot
	{

		public static final double	BASE_RADIUS	= 0.090;

		/** Constructors */
		public DummyBot()
		{
			super("DummyBot", 7.0, World.WIDTH / 2, World.HEIGHT / 2, 2 * BASE_RADIUS, 2);
		}

		public GraphicalRepresentation createGraphicalRepresentation()
		{
			return null;
		}

		public void setSensors(Sensor[] s)
		{
			super.setSensors(s);
		}

	}
}
