/*
 * Created on Aug 13, 2004
 * The MapReaderbehavior (dip=6-7) class sits on the base behavior class
 * @author James Caska
 */
package javaBot.Junior.Dance;

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
	private String	map;
	private int		index;

	/**
	 * Creates a new MapReaderJrBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param tick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 * @param initMap TODO PARAM: DOCUMENT ME!
	 */
	public MapReaderBehavior(JobotBaseController initJoBot, PeriodicTimer tick,
			int servicePeriod, String initMap)
	{
		super(initJoBot, tick, servicePeriod);
		map = initMap;
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		PortIO.setPin((index & 1) == 1, 3, PortIO.PORTB);

		int vl = (byte) map.charAt(index++);
		int vr = (byte) map.charAt(index++);

		//int omega = (byte) map.charAt(index++);
		//System.out.println("vx = " + String.valueOf(vx) + " vy = " + String.valueOf(vy) + " omega = " + String.valueOf(omega));
		if (index >= map.length())
		{
			index = 0;
		}

		getJoBot().drive(vl, vr);
	}
}
