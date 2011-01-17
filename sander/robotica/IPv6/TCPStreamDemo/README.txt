Sun SPOT TCP/IP Demo - Version 1.0

Author: Pete St. Pierre
--------
OVERVIEW
--------

This demo application sends periodic sensor readings measured on one
or more Sun SPOTs to an application on your laptop or PC that displays
the values. It consists of two parts:

1.     TCPServer-onSPOT:

       This application runs on a Sun SPOT and accepts client requests for
       light and temperature readings.  The server also counts the number
       of connections that have been made, along with the IPv6 address of
       the last connection.

2(a)   TCPClient-onDesktop:

       This application runs on a host computer to which a basestation
       has been attached [NOTE: a basestation is not required if you'll
       be running the TCPServer-onSPOT portion of the code entirely 
       on emulated SPOTs]. The application takes one argument which is
       the address of the server.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Compile and deploy code to Sun SPOTs.

   [Follow the instructions in 1(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    1(b) if you are using emulated SPOTs.]

   (a) Go into the TCPServer-onSPOT directory, connect a SPOT to 
       your host using a USB cable and execute:

       ant deploy

       This will compile the sensor sampling application and load it. 
       Disconnect the SPOT and reset it. After the SPOT finishes rebooting,
       it'll enter a loop where it waits for incoming connections.
       The SPOT will display a row of blue LEDs when waiting for connections.
       While procesing a request, the LEDs are set to red. 

   (b) Go into the TCPServer-onSPOT directory and execute:

       ant solarium

       This will start up the Solarium tool that includes the SPOT
       emulator. 
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop up.
              Navigate to the TCPServer-onSPOT subdirectory and select
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
              MIDlet ..." and select the "SensorSampler" application.

       The SPOT will enter a loop where it reads the light sensor, 
       broadcasts that reading and goes to sleep for ten seconds before 
       the next sample. These readings will be displayed in the window
       created in step (iii) above. The SPOT flashes the rightmost LED 
       to visually indicate a sampling event. You can change the light
       sensor values read by a virtual SPOT by manipulating the slider
       marked "Light Sensor" (under the "Enviro" tab) on its sensor 
       panel.

   Repeat this step on as many SPOTs as you want to collect samples on.

2. Run the host application

   [Follow the instructions in 2(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    2(b) if you are using emulated SPOTs.]

   (a) The TCPClient-onDesktop program requires 1 argument, the address
       of the server it will connect to and receive data. For those new
       to IPv6, a basic explaination of IPv6 addressing can be found in
       the SDK Developer's Guide.

       Go into the TCPClient-onDesktop directory, connect a basestation
       to your host using a USB cable and execute:

       ant host-run -Dmain.args=<target>
       
       where <target is the address of the node you wish to query.  For 
       example:

       ant host-run -Dmain.args=fe80::8192:4f0f:0000:1001

   (b) Go into the TCPClient-onDesktop directory and execute the above command
       with the addition of arguments for shared basestation and basestation
       not required:

   ant host-run -Dmain.args=<target>
          -Dbasestation.shared=true -Dbasestation.not.required=true 

       such as:
   ant host-run -Dmain.args=fe80::8192:4f0f:0000:1001 
          -Dbasestation.shared=true -Dbasestation.not.required=true

   This will start the host application that collects a sample reading
   over the radio and displays it. 


---------------
WHAT TO DO NEXT
---------------

   - Try to collect and display multiple sensor readings, e.g. modify
     the demo to collect/display temperature readings in addition to
     the light readings. 

     Let the demo run overnight and see if you can correlate changes
     in the light/temperature sensor readings readings with the onset
     of day/night. Increase the sleep period between samples to make 
     the battery last longer between consecutive charges. This only
     applies if you are using real (not emulated) SPOTs. For the
     graphical display you will need a way to modify the scale of the
     X (time) access.

