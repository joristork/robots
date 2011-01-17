This directory contains some simple examples of how to use the radio and 
some of the basic sensors of the Sun SPOT to help people learn how to 
program their SPOTs.


AccelerometerSampleCode:

Simple use of the accelerometer to measure tilt of the Sun SPOT.

GPIOToneGeneratorSampleCode:

Illustrates use of the GPIO pin coupled with special driver software
to implement a tone generator.

I2CSampleCode:

Example code demonstrating use of I2C sensors as described in the I2CeDEMOAppNote.pdf Application Note (in the doc/AppNotes folder).

LEDSampleCode:

Simple LED demo to show how to blink an LED on & off, move the lit LED
from left to right, and pulse an LED from dim to bright.

LibraryExtensionSampleCode:

This code illustrates how to add user code to the Sun SPOT library.

RadioBroadcastSampleCode:

This simple demo shows you how to use the radio to broadcast
some data to any listening SPOT(s).

RadioInputStreamSampleCode:

This example shows how to use the basic radio functionality.

SunSpotApplicationTemplate:

Template project for a Sun SPOT application.

SunSpotHostApplicationTemplate:

Template project for a Sun SPOT host application.

SwitchesSampleCode:

This app illustrates both a simple use of waiting for a switch to 
change state, and a call back style of using the switches, as in the 
listener-notifier pattern used for conventional UI devices like the 
mouse buttons.

TemperatureSensorSampleCode:

Illustrates the use of the on board temperature Sensor by printing out
the temeperature level reading in various units.



NOTE:
=====

If you do not have Sun SPOT devices, you can still experiment with this
code using the emulator. Specific instructions are included below but
please refer to the emulator tutorial first if you are unfamiliar with 
the overall process of deploying and running code on virtual SPOTs.

---------------------------------------
GENERAL STEPS FOR BUILDING AND RUNNING THE CODE SAMPLES
---------------------------------------

1. Look at the source code to see what the sample is supposed to do.

2. Compile and deploy code to Sun SPOTs.

   [Follow the instructions in 2(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    2(b) if you are using emulated SPOTs.]

   (a) Go into the specific code sample's directory, connect a SPOT to 
       your host using a USB cable and execute:

       ant deploy run

       This will compile the application, load and start it running. 

   (b) Go into the specific code sample's directory and execute:

       ant solarium

       This will start up the Solarium tool that includes the SPOT
       emulator. 
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop up.
              Navigate to the specific code sample's directory and select
              the "build.xml" file. This will automatically cause the 
              application to be compiled and loaded into the virtual SPOT.
        iii.  Right click on the virtual SPOT, choose "Display application
              output" -> "in Internal Frame" to open a window where
              output from the application will be displayed.
         iv.  Right click on the virtual SPOT, choose "Display sensor
              panel" to open up a multi-tabbed panel that allows you
              to manipulate various sensor readings experienced by the
              virtual SPOT.
          v.  Right click again on the virtual SPOT, choose "Run 
              MIDlet ..." and select the just loaded application.

       The SPOT will run the code sample. You can change the sensor value
       read by the application by manipulating the sliders and buttons in
       the virtual SPOT's sensor panel.

       Once you are running Solarium you can switch to another code sample
       or application by right clicking on the virtual SPOT and choosing
       "Deploy a MIDlet bundle ..." from the menu. Navigate to the directory
       containing the new project and select the "build.xml" file.

