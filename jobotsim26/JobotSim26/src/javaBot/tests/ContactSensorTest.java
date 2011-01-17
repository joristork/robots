/*
 * Created on Mar 7, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.tests;


import javaBot.ContactSensor;
import javaBot.Line;
import javaBot.Location;
import junit.framework.TestCase;

public class ContactSensorTest extends TestCase
{
	ContactSensor cSensor = new ContactSensor(new Location(0,0), 0.0, 0.0);
	
	/* ContactSensor.GetValue using negative values */
	public void testGetValueNegative()
	{
		assertEquals(0, this.cSensor.convertDistanceToValue(-1023));
		assertEquals(0, this.cSensor.convertDistanceToValue(-10));
		assertEquals(0, this.cSensor.convertDistanceToValue(-1));
	}
	
	/* ContactSensor.GetValue using a zero value */
	public void testGetValueZero()
	{
		assertEquals(1023, this.cSensor.convertDistanceToValue(0));				
	}
	
	/* ContactSensor.GetValue using positive values */
	public void testGetValuePositive()
	{
		assertEquals(0, this.cSensor.convertDistanceToValue(1));
		assertEquals(0, this.cSensor.convertDistanceToValue(10));
		assertEquals(0, this.cSensor.convertDistanceToValue(1023));
	}
	
	/* ContactSensor.GetValue using unrounded values */
	public void testGetValueRoundOff()
	{
		assertEquals(1023, this.cSensor.convertDistanceToValue(0.01));
		assertEquals(0, this.cSensor.convertDistanceToValue(0.1));
		assertEquals(0, this.cSensor.convertDistanceToValue(1.1));
	}
	
	/* ContactSensor.SensorLine, checking if line angles are the same */
	public void testSensorLineAngleEqual()
	{
		assertEquals(0, this.cSensor.sensorLine(0.0, 0.0, 0.0).getAngle(), 0);
		assertEquals(0, this.cSensor.sensorLine(0.0, 0.0, 1.0).getAngle(), 1.0);		
	}
	
	/* ContactSensor.SensorLine checking if sensor line lengths are the same */
	public void testSensorLineDistanceEqual()
	{
		assertEquals(0, this.cSensor.sensorLine(0.0, 0.0, 0.0).getLength(), 0.1);
		
		this.cSensor.setMaxDist(1);
		assertEquals(0, this.cSensor.sensorLine(0.0, 0.0, 0.0).getLength(), this.cSensor.getMaxDist());		
	}
}
