package javaBot.UVM;

import javaBot.Debug;
import javaBot.Robot;

/**
 * Abstract class for the creation of agents that should run
 * in the simulator. An agent is the brains of a robot it reads te sensors from
 * the robot and sets it's actuators accordingly.
 */
public abstract class Agent extends Thread
{

	//What robot this agent is linked to
	private Robot	robot;

	//thread loop will end and die if running is false
	private boolean	running	= false;

	//Set this object's name 
	private String	name	= "abstract agent";

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
		Debug.printDebug("Initialised " + toString());
	}

	/**
	 * Gets a string representation of this agent.
	 * @return String representation of this agent
	 */
	public String toString()
	{
		return "Agent " + getAgentName() + " using " + getRobot().name;
	}

	/** 
	 * Method to stop agent, EACH AGENT SHOULD IMPLEMENT A CHECK TO SEE IF (robot == null) 
	 */
	public void kill()
	{
		Debug.printDebug("Attempting to kill agent " + getAgentName()
				+ " by setting the reference to it's robot to null");
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

	/**
	 * @param name The name to set.
	 */
	public void setAgentName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the name.
	 */
	public String getAgentName()
	{
		return name;
	}
}
