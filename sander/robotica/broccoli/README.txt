Demo of the Sun SPOT "eDemoBoard" built-in devices (light detector, temperature, 
accelrometer, LEDs, switches) - Version 1.0

/*
 * BuiltInSensorsDemo.java
 *
 * Illustrates use of the on-board sensors.
 * Intended as a good starting point for building your own app.
 * For radio, see the RadioStrenth demo.
 *
 *    This app waits for you to hold down SW1. While down, LED1 will
 *    show green if shaken left to right.
 *    Once the button is released, it will show the temperature
 *    on LED1 (either red or blue), and the light level on LED2 in green.
 *
 * author: Randy Smith  
 * date: August 2, 2006 
 */


To build and run the demo, plug in a SPOT to the USB, and either

  1] From within NetBeans: use the right button (control click on a Mac) to pop up the
     project's context menu and select "Build Project + Deploy to Sun SPOT". Then unplug the SPOT
     (if you wish) and click the SPOT's Control Button to start the app.

or

  2] From within a command line shell: type

        ant deploy 

     Then unplug the SPOT (if you wish) and click the SPOT's Control Button to start the app.

