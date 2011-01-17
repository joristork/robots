/*
 * @(#)GenericSensorSimImpl.java Created on Mar 9, 2005
 *
 * Copyright 2003 to 2005 James Caska, muvium.com . All Rights Reserved
 */

package javaBot.sensors.ws.impls;

import javaBot.sensors.ws.interfaces.ISensorSim;
import javaBot.sensors.ws.proxyImpls.ISensorSimProxy;

/**
 * The GenericSensorSimImpl implements the IGenericSensor but when invoked it
 * looks up it's value from the simulator via a WebService call
 * 
 * The SensorId
 * 
 * <p>
 * <b>NOTES:</b><br>
 * <ul>
 * <li>Version Mar 9, 2005 ( Created ) </li>
 * </ul>
 * 
 * @author James Caska
 * @see 
 */
public class SensorSimImpl implements ISensorSim
{

	ISensorSimProxy	mySimulatorProxy;
	int				mySensorId;

	public SensorSimImpl(int sensorId, String simulatorProxy, String simulatorPort,
			String simulatorIp)
	{

		mySimulatorProxy = new ISensorSimProxy(simulatorProxy + ":" + simulatorPort + "/http://"
				+ simulatorIp);
		mySensorId = sensorId;
	}

	public String getSensorValue(int sensorId)
	{
		return mySimulatorProxy.getSensorValue(mySensorId);
	}

	public String getName(String name)
	{
		return name + " - Remote Sensor Id = " + String.valueOf(mySensorId);
	}

}
