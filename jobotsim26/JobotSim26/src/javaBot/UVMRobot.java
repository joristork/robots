package javaBot;

/**
 *	Ver 0.0 - 01-06-2004
 * 	Ver 0.1 - 03-07-2004 	- 	Implemented Actors to display servos
 * 								Included DoCommand webservice interfaces (preliminary)
 * 								Modified ADC output to integer value
 * 								Reversed direction of servo motors
 *  Ver 0.2 - 01-08-2004 [JC] 	Added commands as static variables
 * 								Integrated icd with Reset/Program/Run commands
 * 								Remoted simmode - UVMRobot acts as an avatar to the real-robot. See note on avatar
 *  Ver 0.3 - 14-08-2004		Added the WebService implementation and changed the interface
 * 							    to better reflect what is available on the real robot agent
 *  Ver 0.4	- 15-Sept-2005	    [JC] Split UVMRobot into an abstract class with the base services and
 * 							    create JPBRobot and JPB2Robot as specific implementations
 * 
 * Avatar
 * 
 * When running as an avatar the simulation is a representation of the real-robot and run side by
 * side. All commands are passed through to the real robot. The difference between the way the
 * two run will be the differences in the world stimulus they receive. One way to handle this is
 * to read the actual sensor values off the real device and plug them into the simulation.
 */

import javaBot.agents.AgentWebService;

import org.openVBB.robotKit.controllers.JoBotJPBController;
import org.openVBB.robotKit.interfaces.ADCSampleListener;
import org.openVBB.robotKit.interfaces.DigitalOutputPinListener;
import org.openVBB.robotKit.interfaces.StepTimeListener;
import org.openVBB.sim.rti.OpenVBBRTIImpl;

import com.muvium.dev.icd.UVMICD;
import com.muvium.web.services.protocols.HttpGetClientProtocol;

/**
 * [JC] The UVMProxyRobot implements a Robot 'brain' using the openVBB uVM
 * simulator. The JoBotJPBController is a wrapper for an openVBB circuit
 * simulation using the openVBB simulator with wiring as per the JPB
 * controller with electical connections provided as per the JPB
 * specification.
 *
 * The UVMRobot can also act as an avatar for a real robot. In particular the
 * UVMRobot implements the same AgentWebService interface that is available
 * on the remote Agent robot. This means it looks like the remote robot
 * to applications accessing it. 
 *
 * @author James Caska The UVMRobot proxy implements the
 */
public abstract class UVMRobot extends Robot implements StepTimeListener, ADCSampleListener,
		DigitalOutputPinListener, AgentWebService
{
	/**
	 * Commands for the remote device
	 */
	public static final int		COMMAND_NULL_PARAMETER	= -1;
	public static final int		COMMAND_START			= 1;
	public static final int		COMMAND_STOP			= 2;
	public static final int		COMMAND_SENSOR			= 3;
	public static final int		COMMAND_VECTORDRIVE		= 4;
	public static final int		COMMAND_DRIVE			= 5;
	public static final int		COMMAND_ACTION			= 6;
	public static final int		COMMAND_GETSTATE		= 7;
	public static final int		COMMAND_REPORTSTATE		= 8;
	
	private static final String	UVMDK_PATH	= "C:\\eclipse3\\eclipse\\plugins\\uvmeclipse";

	/** Not Documented yet */
	HttpGetClientProtocol		invoker;

	/** Not Documented yet */
	JoBotJPBController			controller;

	/** Not Documented yet */
	OpenVBBRTIImpl				vbbRTI;

	UVMICD						icd;    //The ICD is the gateway to the real-device for programming and invoking webServices

	/**
	 * Creates a new UVMRobot instance
	 *
	 * @param name
	 * @param positionX
	 * @param positionY
	 * @param simulateMode - the XML configuration file
	 */
	public UVMRobot(String name, double friction, double positionX, double positionY,
			double diameter, double mass)
	{
		super(name, friction, positionX, positionY, diameter, mass);
	}

	/* (non-Javadoc)
	 * @see javaBot.PhysicalObject#createGraphicalRepresentation()
	 */
	public abstract GraphicalRepresentation createGraphicalRepresentation();

	public abstract void loadApp(String configXML);

	/** Get a led's on/off status */
	public abstract boolean getGreenLed();

	/** Get a led's on/off status */
	public abstract boolean getRedLed();

	/** Get a led's on/off status */
	public abstract boolean getYellowLed();

	/** Get a led's on/off status */
	public abstract boolean getBlueLed();

	//THe ConfigXML document contains for each muvium project the static configurations information
	//including the classfile to load into the container, the wiring configurations and implementation
	//information required for the launching of the project
	protected abstract String getConfigXMLDoc();

	public abstract void updatePosition(double elapsed);

	public void update(double elapsed)
	{
		super.update(elapsed);
		updatePosition(elapsed);
	}

	public void startAgent()
	{
		Debug.printInfo(System.getProperty("Starting load agent"));
		// TODO : Implement stop() method in OpenVBBRTIImpl
		// the method stop from type java.lang.Thread is depricated
		//vbbRTI.stop();
		loadApp(getConfigXMLDoc());
		vbbRTI.start();
	}

	public void suspendAgent()
	{
		vbbRTI.suspendRTI();
	}

	public void resumeAgent()
	{
		vbbRTI.resumeRTI();
	}

	/**
	 * Resets the real-agent and hooks up to the real-robot
	 * 
	 * THe procedure is
	 * 
	 * Reset 	- 	Resetting the device will instantiate the robot and attempt to put it into bootmode
	 * 		   		When using softboot this will happen automatically. When using hardboot a physical
	 * 				reset is required
	 * 
	 * Program 	-	Once in bootmode the real-robot can be programmed with the current Agent
	 * 				or if the agent is already the most recent then there is no need to program it
	 * 
	 * Run		- 	Runns the real-robot.
	 * 			
	 */
	public boolean resetRealAgent(boolean hardBoot)
	{

		if (icd == null)
		{
			icd = new UVMICD(UVMDK_PATH, getConfigXMLDoc());
		}

		int bootmode = UVMICD.BOOTMODE_SOFTBOOT;
		if (hardBoot)
		{
			bootmode = UVMICD.BOOTMODE_HARDBOOT;
		}

		if (icd.connect())
		{
			return icd.bootmode(bootmode);
		}
		else
		{
			return false;
		}
	}

	public void runRealAgent()
	{
		if (icd != null)
		{
			icd.run();
		}
	}

	public void programRealRobot()
	{
		if (icd != null)
		{
			icd.program();
		}
	}

	/**
	 * Normally would use a proxy interface but this method is more generic
	 *
	 * @param methodName
	 * @param params
	 *
	 * @return int return value command
	 */
	public int invokeRemoteMethod(String methodName, int[] params)
	{
		//invoker = new HttpGetClientProtocol(
		return 0;
	}

	/**
	 * Run and report to the agent
	 */
	public void run()
	{
		vbbRTI.start(); //Starts the openVBB thread
	}

	public void stop()
	{
		vbbRTI.stop();
	}

	/**
	 * TimeStep is the synchronisation clock coming from the openVBB simulation
	 * This is NOT used by the current simulator
	 * 
	 * NOTE: This is used to implement a simulated real-time system. This is a
	 * recommended next step for the Jobot simulation system because it allows
	 * more flexibility for the simulation model.
	 * 
	 * Presently the jobot simulator is a Real-Real time system which means 
	 * it gets it's time step from the actual real-time. The problem with this
	 * is when you have sections of the simulation which take more processing
	 * time to simulate than real time and so the result is large time steps
	 * which are 'jerky' and result in reduced accuracy. Simulated real-time
	 * on the other hand produces a computed time-step. When the simulation
	 * runs faster than real-time it can slow itself down to real-time but
	 * when it is slower then the system remains accurate. At present it is
	 * assumed the system runs in real-time but when this doesn't hold true
	 * ie if the periodicTImers are too fast then the simulation can break down
	 * Using the time-step to synchronise the simulation is the solution to 
	 * this problem.
	 *
	 * @param timeStep DOCUMENT ME!
	 * @param totalElapsedTime DOCUMENT ME!
	 */
	public void timeStep(double timeStep, double totalElapsedTime)
	{
	}

	public void setPortB(int value)
	{
		controller.setPortBDIP(value ^ 0x0F);
	}

	/*******************************************************************
	 * WebServices Interface
	 *******************************************************************
	 * 
	 * The UVMRobot is the proxy to the remote readSensor webService.
	 * THis allows an external application to invoke the exact same
	 * method from this ProxyRobot using the exact same interface
	 * as the one that is actually on the remote device. This is
	 * why it is called a proxy - this interfaced object for all
	 * intents and purposes is a teleporting window to the remote
	 * device. The WebServices infrastructure implements the teleporter
	 */

	/**
	 * [WebService] 
	 */
	public int getSensor(int sensor)
	{
		return webServiceI("getSensor", new Object[] {new Integer(sensor)});
	}

	/**
	 * [WebService] 
	 */
	public int getState()
	{
		return webServiceI("getState", new Object[] {});
	}

	/**
	 * [WebService] 
	 */
	public void setState(int dipSetting)
	{
		webServiceV("setState", new Object[] {new Integer(dipSetting)});
	}

	/**
	 * [WebService] 
	 */
	public void reportState(int level)
	{
		webServiceV("reportState", new Object[] {new Integer(level)});
	}

	/**
	 * [WebService] 
	 */
	public void vector(int vx, int vy, int omega)
	{
		webServiceV("vector", new Object[] {new Integer(vx), new Integer(vy), new Integer(omega)});
	}

	/**
	 * [WebService] 
	 */
	public void drive(int x, int y, int z)
	{
		webServiceV("drive", new Object[] {new Integer(x), new Integer(y), new Integer(z)});
	}

	private void webServiceV(String method, Object[] params)
	{
		controller.invokeWebServiceV(method, params);

		if (icd != null)
		{
			String signature = "(";
			for (int i = 0; i < params.length; i++)
			{
				signature += "I";
			}
			signature += ")V";

			try
			{
				icd.invokeRemoteMethod(method, signature, params);
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}

	/**
	 * If the real robot is available then the values are read from the 
	 * real-robot else they are read from the simulated robot
	 * 
	 * @param method
	 * @param params
	 * @return
	 */
	private int webServiceI(String method, Object[] params)
	{

		if (icd != null)
		{
			String signature = "(";
			for (int i = 0; i < params.length; i++)
			{
				signature += "I";
			}
			signature += ")I";

			try
			{
				return ((Integer) icd.invokeRemoteMethod(method, signature, params)).intValue();
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
				return -1;
			}
		}
		else
		{
			return controller.invokeWebServiceI(method, params);
		}
	}

	/**
	 * This returns the sensor values
	 *
	 * @param channel DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */

	public double getADCSample(int channel)
	{
		if (getSensors() == null)
		{
			System.out.println("Sample requested by null sensorValues");
			return 0;
		}
		// if (channel == 3) Debug.printDebug("Reading channel " + channel);
		return getSensors()[channel].getValue() / 1000;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param vout DOCUMENT ME!
	 * @param pinId DOCUMENT ME!
	 */
	public void digitalOutputPinChanged(boolean vout, int pinId)
	{
		if (vout == false)
			getLeds()[pinId].setValue(1.0);
		else
			getLeds()[pinId].setValue(0.0);
	}

}
