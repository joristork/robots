package javaBot.sensors;

/*
 * @(#)SensorServiceDescription.java Created on Mar 9, 2005
 *
 * Copyright 2003 to 2005 James Caska, muvium.com . All Rights Reserved
 */
import javaBot.sensors.ws.impls.SensorSimImpl;
import javaBot.sensors.ws.impls.SensorUvmImpl;
import javaBot.sensors.ws.proxyImpls.ISensorSimProxy;
import javaBot.sensors.ws.proxyImpls.ISensorUvmProxy;

/**
 * <p>
 * <b>NOTES:</b><br>
 * 
 * <ul>
 * <li>
 * Version Mar 9, 2005 ( Created )
 * </li>
 * </ul>
 * </p>
 *
 * @author James Caska
 *
 * @see
 */
public class SensorDescription
{
	private static final int	INTERFACE_UNKNOWN		= 0;
	private static final int	INTERFACE_SIM_SENSOR	= 1;
	private static final int	INTERFACE_UVM_SENSOR	= 2;
	int							myId;
	String						myGatewayIp;
	String						myGatewayPort;
	String						myDeviceIp;
	int							myServiceInterface;
	String						myServiceType;
	Object						myProxy;
	Object						myService;
	boolean						active;

	/**
	 * Creates a new SensorDescription object.
	 *
	 * @param id TODO PARAM: DOCUMENT ME!
	 * @param gatewayIp TODO PARAM: DOCUMENT ME!
	 * @param gatewayPort TODO PARAM: DOCUMENT ME!
	 * @param deviceIp TODO PARAM: DOCUMENT ME!
	 * @param serviceInterface TODO PARAM: DOCUMENT ME!
	 * @param serviceType TODO PARAM: DOCUMENT ME!
	 */
	SensorDescription(int id, String gatewayIp, String gatewayPort, String deviceIp,
			String serviceInterface, String serviceType)
	{
		myId = id;
		myGatewayIp = gatewayIp;
		myGatewayPort = gatewayPort;
		myDeviceIp = deviceIp;
		myServiceInterface = getInterfaceType(serviceInterface);
		myServiceType = serviceType;
		myProxy = createProxy();
		active = false;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String getIpAddress()
	{
		return myDeviceIp;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private String createProxyFormatedDeviceIpAddress()
	{
		return myGatewayIp + ":" + myGatewayPort + "/http://" + myDeviceIp;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param interfacName TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private int getInterfaceType(String interfacName)
	{
		if (interfacName.equals("ISimSensor"))
		{
			return INTERFACE_SIM_SENSOR;
		}

		if (interfacName.equals("IUvmSensor"))
		{
			return INTERFACE_UVM_SENSOR;
		}

		return INTERFACE_UNKNOWN;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param gatewayIp TODO PARAM: param description
	 * @param gatewayPort TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean matchGateway(String gatewayIp, String gatewayPort)
	{
		return gatewayIp.equals(myGatewayIp) && gatewayPort.equals(myGatewayPort);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param value TODO PARAM: param description
	 */
	public void setActive(boolean value)
	{
		active = value;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean getActive()
	{
		return active;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String key()
	{
		return "S" + myId;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public int getId()
	{
		return myId;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Object getProxy()
	{
		return myProxy;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param aProxy TODO PARAM: param description
	 */
	public void setProxy(Object aProxy)
	{
		myProxy = aProxy;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private Object createProxy()
	{
		switch (myServiceInterface)
		{
			case INTERFACE_SIM_SENSOR:
				return new ISensorSimProxy(createProxyFormatedDeviceIpAddress());

			case INTERFACE_UVM_SENSOR:
				return new ISensorUvmProxy(createProxyFormatedDeviceIpAddress());
		}

		return null;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param simulatorProxy TODO PARAM: param description
	 * @param simulatorPort TODO PARAM: param description
	 * @param simulatorIp TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Object createService(String simulatorProxy, String simulatorPort, String simulatorIp)
	{
		switch (myServiceInterface)
		{
			case INTERFACE_SIM_SENSOR:
				return new SensorSimImpl(myId, simulatorProxy, simulatorPort, simulatorIp);

			case INTERFACE_UVM_SENSOR:
				return new SensorUvmImpl(myId, simulatorProxy, simulatorPort, simulatorIp);
		}

		return null;
	}
}
