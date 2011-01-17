Sun SPOT Solarium Virtual Object Demo - Version 1.0

Author: Ron Goldman 

--------
OVERVIEW
--------

This demo application builds on the SendDataDemo that sends periodic
sensor readings measured on one or more Sun SPOTs to an your laptop 
or PC to display the values. Instead of running a special host 
application to gather the data, for this demo we modify Solarium to
recognize a SPOT running the SendDataDemo and to be able to display
the data in a Solarium window. 

This is done by defining a new Solarium "virtual object" to represent
a SPOT running the SendDataDemo. This code is then plugged into 
Solarium.

The demo consists of two parts:

1.     SendDataDemo-onSPOT:

       This application runs on a Sun SPOT periodically sampling
       the built-in light sensor and broadcasting those readings
       over the radio. This is the same code as in the SendDataDemo
       with two modifications: (1) it now notifies the OTA command
       server that this SPOT has a special subtype, and (2) the
       SPOT stays awake for 1 minute so Solarium can discover it.

2      SendDataDemo-inSolarium:

       This project creates a jar file that can be added to Solarium
       so that Solarium will recognize a SPOT running the 
       SendDataDemo and then graphically display any sensor samples
       broadcast by the SPOT.


NOTE:
=====

This demo requires a real, physical Sun SPOT and basestation. It will
not work using the emulator and a virtual SPOT.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Create the SendDataDemo jar file

   Go into the SendDataDemo-inSolarium directory, and execute the 
   command:

       ant host-compile

   This will compile the necessary files, create a new SendDataVO.jar
   file, and then copy the jar file to <SDK>/lib/SPOTWorld/virtualObjects 
   directory where Solarium will then find it.

2. Run Solarium

   Go into the SendDataDemo-inSolarium directory, connect a basestation
   to your host using a USB cable and execute:

       ant solarium

   Solarium will start up and be ready to discover SPOTs.

3. Compile and deploy code to Sun SPOTs.

   Go into the SendDataDemo-onSPOT directory, connect a SPOT to your host
   computer using a USB cable and execute:

       ant deploy

   This will compile the sensor sampling application and load it. 
   Disconnect the SPOT and reset it. After the SPOT finishes rebooting,
   it'll enter a loop where it reads the light sensor, broadcasts that 
   reading and goes to sleep for ten seconds before the next sample.
   The SPOT flashes the rightmost LED to visually indicate a sampling
   event. You should notice the SPOT going into deep sleep between
   consecutive samples if it isn't running any other threads that 
   need to use the CPU, radio or other resources.

   Note that the SPOT will stay awake for the first minute so that it
   can be discovered by Solarium. After you reset the SPOT use the 
   Solarium command "Discover Sun SPOTs" in the "Discovery" menu. 
   When the SPOT responds Solarium will display it. Note how the SPOT
   is shown in Solarium with two green lines. If you right click on
   the SPOT a menu of commands will be displayed. Note how this is 
   different from the regular SPOT commands in Solarium. Choose the 
   first item to "Show Send Data Graph". Solarium will then display a
   graph of the sensor readings to the left of the SPOT.

   Repeat this step on as many SPOTs as you want to collect samples on.


-------------------------------
FILES NEEDED TO EXTEND SOLARIUM
-------------------------------

SendDataSpot.java defines the new virtual object. It extends the 
regular SPOT virtual object by redefining the menu of commands.

The PVSendDataSpot.java and TVSendDataSpot.java define how to display
a SendData Spot in the two dimensional SPOT View and the tree-based
Inspector View. PVSendDataSpot.java also specifies the "Show Send Data 
Graph" command.

SendDataPanel.java takes care of opening a radio connection to the 
real SPOT, listening for light sensor values, and then displaying
them in a graph.

The images folder contains the graphic to use when displaying a SendData
Spot.

The build.xml file has a target to create the jar file to plug into
Solarium. The jar file's manifest includes attributes that tell Solarium
what type/subtype of object this file applies to.

