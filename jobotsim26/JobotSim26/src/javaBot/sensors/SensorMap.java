package javaBot.sensors;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Central Map of Sensors Data (
 * @see
 */
public class SensorMap
{
	Vector		mySensors	= new Vector();
	Hashtable	myLookup	= new Hashtable();

	/**
	 * Creates a new SensorMap object.
	 */
	public SensorMap()
	{

	}

	/**
	 * 
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Vector getSensors()
	{
		return mySensors;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensorId TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public SensorDescription getSensor(int sensorId)
	{
		return (SensorDescription) myLookup.get("S" + sensorId);
	}

	// Please note that this function activates sensors.
	// Deactivation is not done anywhere currently
	public void activateSensors(String gatewayIp, String gatewayPort)
	{
		for (Enumeration e = mySensors.elements(); e.hasMoreElements();)
		{
			SensorDescription sensor = (SensorDescription) e.nextElement();

			if (sensor.matchGateway(gatewayIp, gatewayPort))
			{
				sensor.setActive(true);
			}
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param gatewayIp TODO PARAM: param description
	 * @param gatewayPort TODO PARAM: param description
	 * @param deviceIp TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public static String getKey(String gatewayIp, String gatewayPort, String deviceIp)
	{
		return gatewayIp + gatewayPort + deviceIp;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param gatewayIp TODO PARAM: param description
	 * @param gatewayPort TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Vector getGatewaySensors(String gatewayIp, String gatewayPort)
	{
		Vector sensors = new Vector();

		for (Enumeration e = mySensors.elements(); e.hasMoreElements();)
		{
			SensorDescription sensor = (SensorDescription) e.nextElement();

			if (sensor.matchGateway(gatewayIp, gatewayPort))
			{
				sensors.add(sensor);
			}
		}

		return sensors;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensor TODO PARAM: param description
	 */
	public void addSensor(SensorDescription sensor)
	{
		mySensors.add(sensor);
		myLookup.put(sensor.key(), sensor);
	}
}
