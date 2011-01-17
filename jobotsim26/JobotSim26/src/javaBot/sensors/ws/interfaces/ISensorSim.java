/*
 * @(#)IGenericSensor.java Created on Mar 9, 2005
 *
 * Copyright 2003 to 2005 James Caska, muvium.com . All Rights Reserved
 */

package javaBot.sensors.ws.interfaces;

import com.muvium.web.WSDL;

/**
 * <p>
 * <b>NOTES:</b><br>
 * <ul>
 * <li>Version Mar 9, 2005 ( Created ) </li>
 * </ul>
 * 
 * @author James Caska
 * @see 
 */
public interface ISensorSim extends WSDL
{

	public String getSensorValue(int sensorId);

	public String getName(String name);
}
