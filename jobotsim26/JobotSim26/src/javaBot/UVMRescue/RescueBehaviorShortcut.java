/*
 * RescueBehaviorStart.java
 * This module contains the behavior for the first leg of the field
 * from start until the yellow shortcut,
 * It ignores this and carries on over the entire field at a fast
 * speed. Modify this behavior to also handle the sharper corners 
 * later on.
 * 
 * Peter van Lith - 27-03-2006
 * 
 */

package javaBot.UVMRescue;

import com.muvium.apt.*;

public class RescueBehaviorShortcut extends Behavior {     
	
	public RescueBehaviorShortcut(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}
	
	public void doBehavior() {
		int fSl = joBot.getSensor(2); //"left" fieldsensor
		int fSr = joBot.getSensor(3); //"right" fieldsensor
		
//		if(fSl > 350 || fSr > 350) {   //Als een van de fieldsensoren
			//geel ziet ben je bij de afkorting
//			state = 1;    //Ga naar de volgende state
//			System.out.println("State set to 1");
//			count = 100;   //zet een teller
			//Pas als de teller is afgelopen reageert de jobot weer op 
			//het zien van zwart. Dit is zodat hij niet aan het begin 
			//van de afkorting denkt dat hij bij het eind van de 
			//afkorting is.
//		}
		if(fSl > 100 && fSr > 100) {  //If both sensors see green
			//you are still on the road
			joBot.vectorDrive(0,-100,0);    //Carry on
			System.out.print("Straight ");
		}
		else if(fSl < 100) { //If left sensor sees black
			if(fSr < 100) {  //And right one also
				System.out.println("Error! Both sensors are sensing black!");
			}
			joBot.drive(-100,-100,-100);  //Turn left
			System.out.print("Turn Left ");
		}
		else if(fSr < 100) {  //If right sensor sees black
			joBot.drive(100,100,100);  //Turn right
			System.out.print("Turn Right ");
		}

		System.out.print("L=");
		System.out.print(fSl);
		System.out.print(" R=");
		System.out.println(fSr);
	}

}