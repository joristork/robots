/*
 * Created on Aug 13, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * The MapReaderbehavior (dip=6-7) class sits on the base behavior class
 */
package javaBot.JPB2;

/**
 */
import com.muvium.apt.PeriodicTimer;
import com.muvium.io.PortIO;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * MapReaderBehavior takes an incoming string and treats each set of 3 characters
 * as a setting for each of the motors.
 * This allows programming complicated movements from a simple string.
 * Each step advances the pointer into the string.
 */
public class MapReaderBehavior extends Behavior
{
	private String	map;
	private int		index;
	private boolean	vector;

	/**
	 * Creates a new MapReaderBehavior object.
	 *
	 * @param initJoBot		- Jobot Base Controller
	 * @param tick			- Timer that generates the ticks
	 * @param servicePeriod	- Frequency of the service Tick in ms per tick interval
	 * @param initMap		- the map used in defining the movements
	 * @param vectorDrive	- switch used to determine if we use drive or vectorDrive
	 */
	public MapReaderBehavior(JobotBaseController initJoBot, PeriodicTimer tick, int servicePeriod,
			String initMap, boolean vectorDrive)
	{
		super(initJoBot, tick, servicePeriod);
		map = initMap;
		vector = vectorDrive;
	}

	public void doBehavior()
	{
		PortIO.setPin((index & 1) == 1, 3, PortIO.PORTB);

		int vx = (byte) map.charAt(index++);
		int vy = (byte) map.charAt(index++);
		int omega = (byte) map.charAt(index++);

		//System.out.println("vx = " + String.valueOf(vx) + " vy = " +
		//String.valueOf(vy) + " omega = " + String.valueOf(omega));
		if (index >= map.length())
		{
			index = 0;
		}

		if (vector)
		{
			getJoBot().vectorDrive(vx, vy, omega);
		}
		else
		{
			getJoBot().drive(vx, vy, omega);
		}
	}
}
