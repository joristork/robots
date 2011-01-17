/*
 * Created on Aug 13, 2004
 * The MapReaderbehavior (dip=6-7) class sits on the base behavior class
 * @author James Caska
 */
package javaBot.Junior;

import com.muvium.apt.PeriodicTimer;
import com.muvium.io.PortIO;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Takes a string as input parameter.
 * Each value sets the L and R servo and makes a
 * moving pattern that way.
 */
public class MapReaderBehavior extends Behavior
{
	private String	map;
	private int		index;

	public MapReaderBehavior(JobotBaseController initJoBot, PeriodicTimer tick,
			int servicePeriod, String initMap)
	{
		super(initJoBot, tick, servicePeriod);
		map = initMap;
	}

	public void doBehavior()
	{
		PortIO.setPin((index & 1) == 1, 3, PortIO.PORTB);

		int vl = (byte) map.charAt(index++);
		int vr = (byte) map.charAt(index++);
		int vo = (byte) map.charAt(index++);    // Curently not used, could generate tone or light

		//System.out.println("vx = " + String.valueOf(vx) + " vy = " + String.valueOf(vy) + " omega = " + String.valueOf(omega));
		if (index >= map.length())
		{
			index = 0;
		}

		getJoBot().drive(vl, vr);
	}
}
