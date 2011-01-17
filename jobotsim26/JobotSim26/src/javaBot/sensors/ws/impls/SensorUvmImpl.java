/*
 * @(#)GenericSensorSimImpl.java Created on Mar 9, 2005
 *
 * Copyright 2003 to 2005 James Caska, muvium.com . All Rights Reserved
 */

package javaBot.sensors.ws.impls;

import javaBot.sensors.ws.interfaces.ISensorUvm;
import javaBot.sensors.ws.proxyImpls.ISensorUvmProxy;

;
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
public class SensorUvmImpl implements ISensorUvm
{

	ISensorUvmProxy	mySimulatorProxy;
	int				mySensorId;

	public SensorUvmImpl(int sensorId, String simulatorProxy, String simulatorPort,
			String simulatorIp)
	{

		mySimulatorProxy = new ISensorUvmProxy(simulatorProxy + ":" + simulatorPort + "/http://"
				+ simulatorIp);
		mySensorId = sensorId;
	}

	public int getSensorValue(int sensorId)
	{
		return mySimulatorProxy.getSensorValue(mySensorId);
	}

	public byte[] getSample(int sensorId)
	{
		return mySimulatorProxy.getSample(mySensorId);
	}

	public int getState()
	{
		return mySimulatorProxy.getState();
	}

	public void setState(int param)
	{
		mySimulatorProxy.setState(param);
	}

}
