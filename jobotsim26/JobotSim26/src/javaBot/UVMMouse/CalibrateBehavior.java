/*
* Created on Feb 14, 2006
* Copyright: (c) 2006
* Company: Dancing Bear Software
*
* @version $Revision: 1.1 $
*
* The Calibratebehavior (dip=1) class sits on the base Behavior class
* It keeps the servos to their neutral position
* Reads the sensors and sets the status leds to
* indicate which sensor is read
* To ease testing it reacts only to close objects
*
*/
package javaBot.UVMMouse;
import com.muvium.apt.PeriodicTimer;
/**
* DOCUMENT ME!
*
* @version $Revision: 1.1 $ last changed Feb 14, 2006
*/
public class CalibrateBehavior extends Behavior{
private boolean firstRun = true;
private int[] dist = {0,0};
/**
* Creates a new CalibrateBehavior object.
*
* @param initJoBot
* TODO PARAM: DOCUMENT ME!
28
* @param initServiceTick
* TODO PARAM: DOCUMENT ME!
* @param servicePeriod
* TODO PARAM: DOCUMENT ME!
*/
public CalibrateBehavior(JobotBaseController initJoBot,
PeriodicTimer initServiceTick,int servicePeriod){
super(initJoBot, initServiceTick, servicePeriod);
getJoBot().resetSensor();
}
/**
* Implements the behaviour. Can be considered as an infinite loop.
*/
public void doBehavior(){
dist[0] += getJoBot().getSensor(5);
dist[1] += getJoBot().getSensor(6);
System.out.print(dist[0]+","+dist[1]+" ");
/* Code below is for real robot
byte[] pixels;
int i = 0;
int j = 0;
getJoBot().resetSensor(); // Reset the mouse sensor
pixels = getJoBot().readPixels();
System.out.print("Pix=");
for (i = 0; i < pixels.length; i++){
j = pixels[i];
if (i < 10){
System.out.print(j); // dump pixels on serial line
System.out.print(",");
}
}
System.out.print(" X=");
System.out.print(getJoBot().mouse.getDX());
System.out.print(",Y=");
System.out.print(getJoBot().mouse.getDY());
System.out.println();
if (pixels[0] >= 0){
// Show distance detected
getJoBot().setStatusLeds(false, false, true);
}else{
// Reset lamps if no input read
getJoBot().setStatusLeds(false, false, false);
}
*/
}
}
