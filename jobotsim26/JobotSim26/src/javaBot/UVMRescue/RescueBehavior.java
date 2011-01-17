/*
 * RescueBehavior.java
 * Een programma dat een jobot bestuurt zodat de jobot
 * de zwarte lijn op het rescuefield volgt naar het moeras en dan
 * een slachtoffer redt. De jobot gebruikt twee sensoren en 
 * neemt de gele afkorting wel.
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
 * Dit module wordt niet meer gebruikt en dient alleen als voorbeeld
 * totdat het niewe, state based module klaar is.
 * 
 */

package javaBot.UVMRescue;

import javaBot.Sensor;

import com.muvium.apt.*;

public class RescueBehavior extends Behavior {     
	int state = 0;	//De status van de jobot
	int count = 0;	//Een teller
	
	public RescueBehavior(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}
	
	public void doBehavior() {
		int dS1 = joBot.getSensor(0); //"linker" afstandssensor
		int dS2 = joBot.getSensor(1); //"rechter" afstandssensor
		int fS1 = joBot.getSensor(2); //"linker" fieldsensor
	    int fS2 = joBot.getSensor(3); //"rechter" fieldsensor
        
        if(state == 0){                //Het eerste stuk zwarte weg
            if(fS1 > 350 || fS2 > 350) {   //Als een van de fieldsensoren
                //geel ziet ben je bij de afkorting
//                state = 1;    //Ga naar de volgende state
//                System.out.println("State set to 1");
//                count = 100;   //zet een teller
               //Pas als de teller is afgelopen reageert de jobot weer op 
                //het zien van zwart. Dit is zodat hij niet aan het begin 
                //van de afkorting denkt dat hij bij het eind van de 
                //afkorting is.
            }
            else if(fS1 > 100 && fS2 > 100) {  //als beide sensoren groen zien
            	                               //zit je nog goed op de weg
                joBot.vectorDrive(0,-100,0);    //Ga gewoon rechtdoor
            }
            else if(fS1 < 100) { //Als de linker fieldsensor zwart ziet
                if(fS2 < 100) {  //Als de rechter ook zwart ziet
                    System.out.println("Error! Both sensors are sensing black!");
                }
                joBot.drive(-100,-100,-100);  //Draai naar links
//                sleep(300);
//                joBot.vectorDrive(0,-100,0);   //Ga weer rechtdoor
//                sleep(100);
            }
            else if(fS2 < 100) {  //als de rechter fieldsensor zwart ziet
                joBot.drive(100,100,100);  //draai naar rechts
//                sleep(300);
//                joBot.vectorDrive(0,-100,0);  //ga weer rechtdoor
//                sleep(100);
            }
        }
        else if(state == 1) {  //De afkorting
            if(count > 0) {    //De teller laten aflopen
                count--;
            }
            //Als een van de fieldsensoren zwart ziet
            //en de teller staat op 0
            if((fS1 < 100 || fS2 < 100) && count == 0) {
                state = 2;  //dan ben je dus aan het eind van de afkorting
                count = 100; //weer een teller, net zoals eerder.
                System.out.println("State set to 2");            }
            else if(fS1 < 350 && fS2 < 350) { //Als de sensoren groen zien
                joBot.vectorDrive(0,-100,0);  //ga gewoon rechtdoor
            }
            else if(fS1 > 350) { //Als de linker fieldsensor geel ziet
                if(fS2 > 350) {  //Als de rechter ook geel ziet
                    System.out.println("Error! Both sensors are sensing "+
                    		"yellow in state 1!");
                }
                joBot.drive(-100,-100,-100); //Draai naar links
                sleep(20);		//We draaien een kleinere hoek 
                //omdat er in de afkorting alleen heel flauwe bochten zitten
                joBot.vectorDrive(0,-100,0);  //Ga weer rechtdoor
                sleep(60);
            }
            else if(fS2 > 350) { //Hetzelfde voor de rechtersensor
                joBot.drive(100,100,100);
                sleep(20);
                joBot.vectorDrive(0,-100,0);
                sleep(60);
            }
        }
        else if(state == 2) {  //Het tweede stuk zwarte weg
        	if(count > 0) {  //De teller verlagen
        		count--;
        	}
            if((fS1 > 350 || fS2 > 350) && count == 0) {   
            	//Als een van de fieldsensoren geel ziet 
            	//en de teller is al afgelopen dan ben je bij het moeras
                state = 3;    //Ga naar de volgende state
                System.out.println("State set to 3");                joBot.vectorDrive(0,100,0); //Rijdt een stukje rechtdoor het
                sleep(2000);        //moeras in
            }
            else if(fS1 > 100 && fS2 > 100) {  //als beide sensoren groen zien
            	                               //zit je nog goed op de weg
                joBot.vectorDrive(0,-100,0);    //Ga gewoon rechtdoor
            }
            else if(fS1 < 100) { //Als de linker fieldsensor zwart ziet
                if(fS2 < 100) {  //Als de rechter ook zwart ziet
                    System.out.println("Error! Both sensors are sensing black!");
                }
                joBot.drive(100,100,100);  //Draai naar links
                sleep(40);     //Een grotere hoek dan bij het eerste gedeelte
                //want er zitten scherpere bochten in het tweede gedeelte
                joBot.vectorDrive(0,-100,0);   //Ga weer rechtdoor
                sleep(40);
            }
            else if(fS2 < 100) {  //Hetzelfde voor de rechterfieldsensor
                joBot.drive(-100,-100,-100);  //draai naar rechts
                sleep(40);
                joBot.vectorDrive(0,-100,0);  //ga weer rechtdoor
                sleep(40);
            }
        }
        else if(state == 3) {
            if(dS1 > 5) {   //Als je het slachtoffer met de linker 
            	  //afstandssensor ziet
                joBot.vectorDrive(100,0,0); //Rijd naar links
                //totdat je groen ziet
                while(fS1 > 400 && fS2 > 400) {
                    sleep(1);
                    fS1 = joBot.getSensor(2);
                    fS2 = joBot.getSensor(3);
                }
                joBot.vectorDrive(-100,0,0); //Rijd een stukje terug
                sleep(6000);
            }
            else if(dS2 > 5) { //Hetzelfde voor de rechter afstandssensor
                joBot.vectorDrive(-100,0,0);
                while(fS1 > 400 && fS2 > 400) {
                    sleep(1);
                    fS1 = joBot.getSensor(2);
                    fS2 = joBot.getSensor(3);
                }
                joBot.vectorDrive(100,0,0);
                sleep(6000);
            }
            else if(fS1 < 400 || fS2 < 400) { //Als je met een van de 
            	//fieldsensoren groen ziet
                joBot.drive(100, 100, 100);  //ga draaien
                count = 100;    //Count is in aantal ms/10
                //De while staat gelijk aan sleep(count*10)
                //met het verschil dat hij stopt als hij een 
                //slachtoffer ziet
                while(dS1 < 5 && dS2 < 5 && count >= 0) {
                    dS1 = joBot.getSensor(0);
                    dS2 = joBot.getSensor(1);
                    sleep(10);
                    count--;
                }
                joBot.vectorDrive(0,-100,0);  //Ga weer naar voren rijden
                count = 50;    //Count is in aantal ms/10
                //De while staat weer gelijk aan sleep(count*10)
                while(dS1 < 5 && dS2 < 5 && count >= 0) {
                    dS1 = joBot.getSensor(0);
                    dS2 = joBot.getSensor(1);
                    sleep(10);
                    count--;
                }
            }
            else {   //Rijd gewoon rechtdoor
                joBot.vectorDrive(0,-100,0);
            }
        }
        else {
            System.out.println("Error! State out of range!");
        }
    }
	
    void sleep(long mil) {
        try{
            java.lang.Thread.sleep(mil);
        }catch(Exception e) {
        }
    }
}