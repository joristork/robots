/* ChaseBallBehaviorIR.java
 * Een simpele routine die een jobot een bal laat volgen
 * Als hij de bal niet ziet gaat hij rondjes draaien
 * Als hij hem wel ziet gaat hij naar de bal toe en geeft hem een duw
 * Deze versie gebruikt de IR sensor om een RoboCup Junior bal te vinden
 * 
 * Floor Sietsma
 * 0418803
 * fsietsma@science.uva.nl
 * 
 * Steven Klein
 * 0597872
 * sklein@science.uva.nl
 * 
 * Universiteit van Amsterdam
 * Bij het project Robotica en Systemen
 * Woensdag 1 februari 2006
 * 
 */

package javaBot.UVM;
import com.muvium.apt.*;

public class ChaseBallBehaviorIR extends Behavior {
	boolean ballInSight;	//Een variabele die zegt of de infraroodsensor op 
	                        //dit moment iets ziet
	int s0 = 0;		//Vier variabelen voor vier sensoren
	int s1 = 0;
	int s2 = 0;
	int s3 = 0;

	public ChaseBallBehaviorIR(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/*
	 * doBehavior wordt continu uitgevoerd en opnieuw aangeroepen
	 */
	public void doBehavior() {
		s0 = joBot.getSensor(0);	//De drie reflectie/afstandssensoren
		s1 = joBot.getSensor(1);
		s2 = joBot.getSensor(2);
		s3 = joBot.getSensor(3);	//De infraroodsensor
		findBall();
		gotoBall();
	}
	
	/* findBall laat de jobot rondjes draaien net zo lang tot hij
	 * de bal gevonden heeft
	 */
	public void findBall() {
		if (s3 == 0) {		//Als je de bal niet ziet
			ballInSight = false;
			joBot.setStatusLeds(false, true, false);
			joBot.drive(100,100,100);	//Draai een rondje 
			                            //tot je een ander signaal krijgt
		} else if(ballInSight == false){	//Als je de bal ziet en je zag hem 
											//net nog niet
			ballInSight = true;
			joBot.setStatusLeds(false, false, true);
			joBot.drive(100, 100, 100);		//Draai nog even door zodat de bal
										//niet aan de rand van het bereik ligt
			sleep(20000/s3);			//Hoe lang is gerelateerd aan de afstand
			joBot.drive(0, 0, 0);		//tot de bal
		}else if(ballInSight == true){  //Als je de bal ziet en je zag hem net 
										//ook al
			joBot.drive(0, 0, 0);		//Doe niks. In gotoBall gaat hij naar
			joBot.setStatusLeds(false, false, true); //de bal toe.
		}
	}
	
	/*Ga naar de bal toe als je hem ziet
	 * 
	 */
	public void gotoBall() {
		if (joBot.getSensor(3) < 100) {	//Als je hem niet ziet
			ballInSight = false;		//zet ballInSight op false
		} else {						//Als je hem wel ziet
			joBot.vectorDrive(0, -100, 0);	//Ga globaal in de goede richting
			//Je weet niet waar de bal precies is, alleen dat hij ergens in je 
			//bereik is dus nauwkeuriger naar de bal rijden is niet mogelijk			
		}
	}

	/*
	 * Sleep
	 */
	void sleep(long mil) {
		try{
			java.lang.Thread.sleep(mil);
		}catch(Exception e) {
		}
	}
}

