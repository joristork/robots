/*
 * Created on Aug 13, 2004
 * The MapReaderbehavior (dip=6-7) class sits on the base behavior class
 * @author James Caska
 */
package javaBot.agents;

import com.muvium.apt.PeriodicTimer;
import com.muvium.io.PortIO;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision$
 * last changed 20-02-2006
 *
 * TODO CLASS: DOCUMENT ME! 
 */
public class MapReaderBehavior extends Behavior
{
	String	map;
	int		index;
	boolean	vector;

	/**
	 * Creates a new MapReaderBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param tick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 * @param initMap TODO PARAM: DOCUMENT ME!
	 * @param vectorDrive TODO PARAM: DOCUMENT ME!
	 */
	public MapReaderBehavior(JobotBaseController initJoBot, PeriodicTimer tick, int servicePeriod,
			String initMap, boolean vectorDrive)
	{
		super(initJoBot, tick, servicePeriod);
		map = initMap;
		vector = vectorDrive;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		PortIO.setPin((index & 1) == 1, 3, PortIO.PORTB);

		int vx = (byte) map.charAt(index++);
		int vy = (byte) map.charAt(index++);
		int omega = (byte) map.charAt(index++);

		//System.out.println("vx = " + String.valueOf(vx) + " vy = " + String.valueOf(vy) + " omega = " + String.valueOf(omega));
		if (index >= map.length())
		{
			index = 0;
		}

		if (vector)
		{
			joBot.vectorDrive(vx, vy, omega);
		}
		else
		{
			joBot.drive(vx, vy, omega);
		}
	}
}
