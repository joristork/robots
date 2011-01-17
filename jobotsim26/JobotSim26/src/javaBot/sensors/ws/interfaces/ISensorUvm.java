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
 * 
 * @author Peter van Lith
 */
public interface ISensorUvm extends WSDL
{

	public int getSensorValue(int sensor);

	public byte[] getSample(int sensor);

	public int getState();

	public void setState(int dip);
}
