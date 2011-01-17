package javaBot.agents;

import javaBot.Debug;
import javaBot.Robot;

/**
 * Abstract class for the creation of agents that should run
 * in the simulator. An agent is the brains of a robot it reads te sensors from
 * the robot and sets it's actuators accordingly.
 */
/**
 * @version $Revision: 1.1 $
 * last changed Mar 6, 2006
 * 
 */
public abstract class Agent extends Thread
{
	//What robot this agent is linked to 
	private Robot	robot;

	//thread loop will end and die if running is false
	private boolean	running	= false;

	/** 
	 * Constructor 
	 */
	protected Agent()
	{
	}

	/** 
	 * Constructor
	 */
	protected Agent(Robot robot)
	{
		this.setRobot(robot);

		// Give the robot a reference to me        
		robot.setAgent(this);

		Debug.printDebug("Initialised " + toString());
	}

	/** 
	 * Method to stop agent, EACH AGENT SHOULD IMPLEMENT A CHECK TO SEE IF (robot == null)
	 * <br>[JD] setting robot=null leads to nullpexceptions in agent thread so don't do it.
	 * Setting running=false will kill the thread anyway. But each agent should of course check the running field.
	 */
	public void kill()
	{
		Debug.printDebug("Stopping agent " + this.getClass().getName()
				+ " by setting running=false");
		setRunning(false);
	}

	/**
	 * @param robot The robot to set.
	 */
	public void setRobot(Robot robot)
	{
		this.robot = robot;
	}

	/**
	 * @return Returns the robot.
	 */
	public Robot getRobot()
	{
		return robot;
	}

	/**
	 * @param running The running to set.
	 */
	public void setRunning(boolean running)
	{
		this.running = running;
	}

	/**
	 * @return Returns the running.
	 */
	public boolean isRunning()
	{
		return running;
	}

}
