A series of sample MIDlets to demonstrate the SPOT Emulator.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Start up Solarium (e.g. from the command line: ant solarium)
2. Create a virtual SPOT by choosing Emulator -> New Virtual SPOT.
3. Right click on the virtual SPOT and choose "Deploy a MIDlet 
   bundle ..." from the menu. A file chooser window will pop up.
   Navigate to the EmulatorDemo subdirectory and select the
   "build.xml" file. This will automatically cause the application to
   be compiled and loaded into the virtual SPOT.
4. Right click on the virtual SPOT, choose "Display application
   output" -> "in Internal Frame" to open a window where output
   from the application will be displayed.
5. Right click on the virtual SPOT, choose "Display sensor panel"
   to open up a multi-tabbed panel that allows you to manipulate
   various sensor readings experienced by the virtual SPOT.
6. Right click again on the virtual SPOT, choose "Run MIDlet ..."
   and select the application you wish to run.


--------------
Sample MIDlets
--------------


SensorChecker
-------------

This MIDlet demonstrates how the Virtual Sensor Panel can be used 
in the SPOT World Emulator to interact with a virtual SPOT.
Use the Virtual SPOT's pop-up menu to display the sensor panel.

The LEDs on the virtual SPOT display the value read from one
of the SPOT's sensors.

There are four different modes:

 1. Display the light sensor reading in white
 2. Display the temperature sensor reading in red.
 3. Display the analog input A0 in green.
 4. Display the Z acceleration in blue.

As you move the slider for the current sensor you will
see the LED display change.

Pushing the left switch (SW1) advances to the next mode.
The current mode is shown by setting one of H0-H3 to high.

Also D0 is set as an output and the application sets it
to mirror the value that D1 is set to.


Sawtooth
--------

A very simple MIDlet to turn on the LEDs one by one,
each brighter than the previous until they are all on.
Then all the LEDs are turned off and the cycle repeats,
unless SW2 is pressed.


Count
-----

A simple MIDlet that uses the left switch (SW1) to set the color of 
the LEDs and the right switch (SW2) to count in binary in the LEDs.


BroadcastCount
--------------

A simple MIDlet that uses the left switch (SW1) to broadcast a message
to set the color of the LEDs of any receiving SPOTs and the right 
switch (SW2) to count in binary in its LEDs.

Messages received from the other SPOTs control the LEDs of this SPOT.

If a shared basestation is available then a real, physical SPOT can 
interact with the virtual SPOTs via the radio.


UnicastCount
------------

A simple MIDlet that pairs two SPOTs so each controls the others LEDs.
The left switch (SW1) sends a message to set the color of the LEDs of
its paired SPOT and the right switch (SW2) to count in binary in its LEDs.

Messages received from the other SPOT controls the LEDs of this SPOT.

Even addresses pair with smaller odd addresses, & odd addresses pair
with larger even addresses

