package javaBot.sensors;

import java.util.Enumeration;
import javaBot.sensors.ws.interfaces.ISensorSim;
import javaBot.sensors.ws.proxyImpls.ISensorSimProxy;
import javaBot.sensors.ws.proxyImpls.ISensorUvmProxy;

/**
 * The similator is used to both represent and retrive data for certain
 * scenarios
 */

//TODO: Field SIZE must be collected by server and set to check boundaries.
public class SensorDataGetter extends Thread implements ISensorSim
{
	private SensorMap	mySensorMap;

	/**
	 * Creates a new SensorDataGetter object.
	 *
	 * @param sensorMap the sensorMap with all sensors
	 * @see SensorMap
	 */
	public SensorDataGetter(SensorMap sensorMap)
	{
		mySensorMap = sensorMap;
	}

	/**
	 * Generate random simulated Sensor data
	 *
	 * @param sensor - int the sensor number
	 *
	 * @return String the random databytes as String
	 */
	public String getSensorValue(int sensor)
	{
		int i;
		double val;
		char[] bytes = new char[128];
		String aStr;

		for (i = 0; i < 128; i++)
		{
			val = 255.0 * Math.random();
			bytes[i] = (char) val;
		}

		aStr = String.copyValueOf(bytes);

		return aStr;
	}

	/**
	 * Access the Sensor data through the  interface
	 *
	 * @param sensorId int number of the sensor
	 * @param type TODO PARAM: DOCUMENT ME!
	 *
	 * @return byte[] the sensordata
	 */
	public byte[] getSensorData(int sensorId, int type)
	{
		SensorDescription sensor = mySensorMap.getSensor(sensorId);
		String value = "";
		byte[] valArr = new byte[128];

		if (sensor.active)
		{
			Object sensorProxy = sensor.getProxy();

			if (sensorProxy instanceof ISensorSimProxy)
			{
				value = ((ISensorSimProxy) sensorProxy).getSensorValue(type);
				valArr = value.getBytes();
				System.out.println("Getting Simulated Sensor:" + sensor.myDeviceIp + ":"
						+ sensor.myGatewayPort + " (" + sensor.myId + ")=" + value);
			}
			else if (sensorProxy instanceof ISensorUvmProxy)
			{
				valArr = ((ISensorUvmProxy) sensorProxy).getSample(type);
				System.out.println("Getting Uvm Sensor:" + sensor.myDeviceIp + ":"
						+ sensor.myGatewayPort + " (" + sensor.myId + ")=" + value);
			}
			else
			{
				System.out.println("Unknown Sensor:" + sensor.myDeviceIp + ":"
						+ sensor.myGatewayPort + " (" + sensor.myId + ")");
			}
		}
		else
		{
			System.out.println("Sensor " + sensorId + " not activated");
		}

		return valArr;
	}

	/**
	 * Get name of this sensorDataGetter
	 *
	 * @param s1 not used?
	 *
	 * @return String "aSensor" is always returned
	 */
	public String getName(String s1)
	{
		return "aSensor";
	}

	/**
	 * Enumerate the sensormap, extract data from all active sensors and discard this data
	 *
	 * @param type some sensortype value (?)
	 */
	public void scanSensors(int type)
	{
		for (Enumeration e = mySensorMap.getSensors().elements(); e.hasMoreElements();)
		{
			SensorDescription sensor = (SensorDescription) e.nextElement();

			if (sensor.active)
			{
				getSensorData(sensor.myId, type);
			}
		}
	}

	/**
	 * the never ending loop of the thread for the webservice
	 * 
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (Exception e)
			{}

			scanSensors(1);
		}
	}
}
