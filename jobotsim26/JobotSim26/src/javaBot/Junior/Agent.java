package javaBot.Junior;

import javaBot.Debug;
import javaBot.Robot;

/**
 * Abstract class for the creation of agents that should run in the simulator.
 * An agent is the brains of a robot it reads te sensors from the robot and
 * sets it's actuators accordingly.
 */
public abstract class Agent extends Thread
{
	/** What robot this agent is linked to */
	private Robot	robot;
	private boolean	running	= false;

	/** Set this object's name */
	private String	name	= "abstract agent";

	/**
	 * Default Constructor
	 */
	protected Agent()
	{
	}

	/**
	 * Constructor
	 *
	 * @param robot robot The robot the agent is linked to.
	 */
	protected Agent(Robot robot)
	{
		this.robot = robot;
		Debug.printDebug("Initialised " + toString());
	}

	/**
	 * Gets a string representation of this agent.
	 *
	 * @return String Representation of this agent
	 */
	public String toString()
	{
		return "Agent " + name + " using " + robot.name;
	}

	/**
	 * Method to stop agent, EACH AGENT SHOULD IMPLEMENT A CHECK TO SEE IF
	 * (robot == null)
	 */
	public void kill()
	{
		Debug.printDebug("Attempting to kill agent " + name
				+ " by setting the reference to it's robot to null");
		running = false;
	}
}
