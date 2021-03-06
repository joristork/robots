/*
 * Created on Aug 13, 2004 
 * The Calibratebehavior (dip=1) class sits on the base Behavior class
 * It keeps the servos to their neutral position 
 * Reads the sensors and sets the status leds to
 * indicate whisch sensor is read
 * To ease testing it reacts only to close objects
 * @author Peter van Lith
 *  
 */
package javaBot.UVM;

import com.muvium.apt.*;

public class IdleBehavior extends Behavior {

	public IdleBehavior(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}

	public void doBehavior() {
		joBot.setStatusLeds(false, false, false);				
		joBot.drive(0, 0, 0);
	}
}