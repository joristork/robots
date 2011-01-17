package javaBot.sensors;

import java.util.Enumeration;
import java.util.Vector;
import javaBot.sensors.ws.proxyImpls.ISensorUvmProxy;

import com.muvium.net.socketImpls.SlipSocketFactory;
import com.muvium.web.services.gateway.RPCServer;
import com.muvium.web.services.protocols.WebRequest;

/**
 * Version     0.0 28-01-2006 Created from reference app This simulator and
 * sensor interface allows the main program to interface directly with a
 * muVium device and read sensors, connected to a microcontroller. All
 * communication is done through a WebService interface, that simplifies the
 * communication to a Remote Procedure Call. The simulator is included to
 * allow generating random values that may be read as if a real device is
 * attached, allowing tests.
 *
 * @author James Caska and Peter van Lith
 */
public class SensorServer
{
	public static final String	SIMULATOR_PROXY_IP	= "127.0.0.1";	// Proxy
	public static final String	SIMULATOR_PORT		= "8300";
	public static final String	SIMULATOR_IP		= "10.1.1.1";
	public static final String	SIMULATOR_INTPORT	= "9000";		// Simulator interface
	public static final String	SIMULATOR_DEV_IP	= "10.1.1.1";
	public static final String	SIMULATOR_PORT_SENSOR_1		= "8301";
	public static final String	SIMULATOR_PORT_SENSOR_2		= "8302";
	public static final String	SIMULATOR_PORT_SENSOR_3		= "101";

	public boolean				sim;								// This variable controls real or simulated sensors
	private SensorDataGetter	simData;

	/**
	 * Creates a new SensorServer object.
	 *
	 * @param simulated boolean true if sensors are simulated by simulator webservices
	 */
	public SensorServer(boolean simulated)
	{
		System.out.println("Starting sensor server 2.0");
		sim = simulated;

		SensorMap sensors = new SensorMap();
		// simulated sensors on localhost port 8300, 8301. Note: Linux ports <1000 are privileged ports
		sensors.addSensor(new SensorDescription(1, "127.0.0.1", SIMULATOR_PORT_SENSOR_1, "10.1.1.7", "ISimSensor", "sim"));
		sensors.addSensor(new SensorDescription(2, "127.0.0.1", SIMULATOR_PORT_SENSOR_2, "10.1.1.7", "ISimSensor", "sim"));
		// uvm sensors
		sensors.addSensor(new SensorDescription(3, "127.0.0.1", SIMULATOR_PORT_SENSOR_3, "10.1.1.10", "IUvmSensor", "uvm"));
		simData = new SensorDataGetter(sensors);

		if (!sim)
		{
			addRealMuviumRPCServer(sensors);
		}
		else
		{
			//Create the WebService enabled Simulator on 10.1.1.1 port Port80 gateway
			RPCServer simulatorServer = new RPCServer(SIMULATOR_PORT);
			simulatorServer.addWebService(SIMULATOR_IP, simData);
			createSimulatedSensor(sensors, SIMULATOR_PORT_SENSOR_1); // Add two simulated sensors
			createSimulatedSensor(sensors, SIMULATOR_PORT_SENSOR_2);
			simulatorServer.start();
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensorMap TODO PARAM: param description
	 */
	private static void addRealMuviumRPCServer(SensorMap sensorMap)
	{
		try
		{
			WebRequest.setSocketImplFactory(new SlipSocketFactory(1, 57600, true));
		}
		catch (Exception e)
		{}

		ISensorUvmProxy remoteProxy = new ISensorUvmProxy("10.1.1.10");
		SensorDescription sensor = sensorMap.getSensor(3);
		sensor.setProxy(remoteProxy);
		sensor.setActive(true);

		//		RPCServer realUVMServer = new RPCServer("101", 1, 57600);
		//You can setup multiple addresses, for example you can have a network of muvium devices
		//programmed with different addresses listening to a multi-drop serial/radio/infrared network
		//WebServices is request/respond so there are no collisions.
		//		sensorMap.activateSensors("127.0.0.1", "101");
		//		realUVMServer.addWebService( "10.1.1.10" ); //Add a forwarding service for 10.1.1.10 
		//		realUVMServer.start();              
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param sensorMap TODO PARAM: param description
	 * @param port TODO PARAM: param description
	 */
	private static void createSimulatedSensor(SensorMap sensorMap, String port)
	{
		RPCServer sensorPort = new RPCServer(port);
		sensorMap.activateSensors("127.0.0.1", port);

		Vector sensors = sensorMap.getGatewaySensors("127.0.0.1", port);

		for (Enumeration e = sensors.elements(); e.hasMoreElements();)
		{
			SensorDescription sensor = (SensorDescription) e.nextElement();
			sensorPort.addWebService(sensor.getIpAddress(), sensor.createService(
					SIMULATOR_PROXY_IP, SIMULATOR_PORT, SIMULATOR_IP));
		}

		sensorPort.run();
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param type TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public byte[] getSensorData(int type)
	{
		if (sim)
		{
			return simData.getSensorData(1, type); // Get simulated sensor 1
		}
		else
		{
			return simData.getSensorData(3, type); // Get real sensor on 3
		}
	}
}
