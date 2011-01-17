Sun SPOT Telemetry Demo - Version 2.0

Author: Ron Goldman
June 9, 2006,  revised: August 1, 2007 & July 25, 2008
revised: May 27, 2008 (added emulator instructions - vipul)
revised: August 1, 2010 for version 2.0

--------
OVERVIEW
--------

This is a demo application that provides the basic framework for
collecting data on a remote SPOT and passing it over the radio to 
a host application that can record and display it. It was written
with the hope that it could be easily modified by people developing
new SPOT applications.

The framework shows how the remote SPOT and the host application can
locate each other by the use of broadcast packets. A point-to-point
radio connection is then established and used to pass commands and
replies between the host application and the remote SPOT.

The SPOT application consists of two Java classes: one to handle the
radio connection to the host application, and the second to read the
accelerometer and send the data to the host application.

To simplify our work we make use of a number of utility helper classes: 
 * LocateService to locate a remote service (on a host)
 * PacketReceiver to receive commands from the host application and 
      dispatch them to whatever classes registered to handle the command
 * PacketTransmitter handles sending reply packets back to the host
 * PeriodicTask provides for running a task, such as taking samples, 
      at a regular interval using the timer/counter hardware.
 
The host commands and replies are defined in the PacketTypes class.

The SPOT uses its LEDs to display its status as follows:

LED 0:
    * Red = running, but not connected to host
    * Green = connected to host display server 

LED 1:
    * Yellow = looking for host display server
    * Blue = calibrating accelerometer
    * Red blink = responding to a ping request
    * Green = sending accelerometer values using 2G scale
    * Blue-green = sending accelerometer values using 6G scale 

Pushing switch 1 on the SPOT will cause it to disconnect any
current connection to the host and try to reconnect.

The host application consists of four main Java classes: one to
listen for connections from any SPOTs, one to then handle the
radio connection to a specific remote SPOT, one to display the
data collected, and the third to manage the GUI.

The host display server lets you collect up to ten minutes of
accelerometer readings from the SPOT. It will ignore initial zero
values, so you won't see anything until you move the SPOT. In
addition to displaying the x, y and z forces, you can also display 
the magnitude of their vector sum (|a|). You can view the raw data 
or smooth it using either a boxcar or triangle filter. You can also 
change the width of the filter window, that is how many samples are 
used to filter each point.

The host server display also allows the collected data to be saved 
to a file, or a previously collected file to be viewed. The data 
can also be printed out. A sample file of collected accelerometer
readings is included: accel-sample.data for your viewing pleasure.

Javadoc can be found in Telemetry-onSpot/doc/index.html and 
Telemetry-onDesktop/doc/index.html


Here is how the 3 axis of the accelerometer map to the Sun SPOT:

                                  Z  X
      ________________            | /
     / / *           /|           |/
    /_/_*___________/ |       ----. --- Y 
    |_|  Sun SPOT   | /          /|
      |_____________|/          / |


---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Setup a SPOT to report on its accelerometer:

   [Follow the instructions in 1(a) if you have real SPOTs. Otherwise,
   follow the instructions in 1(b) if you are using emulated SPOTs.]

   (a) Starting in the directory containing this README file, execute

            % cd Telemetry-onSpot
            % ant deploy run

   (b) Starting in the directory containing this README file, execute

            % cd Telemetry-onSpot
            % ant solarium

        to start up the Solarium tool which also includes the SPOT
        emulator. 
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop 
              up. Navigate to the Telemetry-onSPOT subdirectory and 
              select the "build.xml" file. This will automatically 
              cause the application to be compiled and loaded into the
              virtual SPOT.
        iii.  Right click on the virtual SPOT, choose "Display application
              output" -> "in Internal Frame" to open a window where
              output from the application will be displayed.
         iv.  Right click on the virtual SPOT, choose "Display sensor
              panel" to open up a multi-tabbed panel that allows you
              to manipulate various sensor readings experienced by the
              virtual SPOT. Select the tab marked "Accel".
          v.  Start the application by right clicking on the virtual 
              SPOT and selecting "Run MIDlet" -> "TelemetryMain".

2. Launch the host-side application to gather and plot accelerometer
   readings obtained from the SPOT:

   [Follow the instructions in 2(a) if you have real SPOTs. Otherwise,
   follow the instructions in 2(b) if you are using emulated SPOTs.]

       (a) Go into the Telemetry-onDesktop directory, connect a 
           basestation to your host computer via a USB cable and 
           execute 'ant host-run'

            % cd ../Telemetry-onDesktop
            % ant host-run

       (b) Go into the Telemetry-onDesktop directory and execute:

            % ant host-run -Dbasestation.shared=true

            (note that the build.properties file has already set the
             property basestation.not.required to true)
           

    This will open a graphical window. Wait until the host application
    has connected to the SPOT. Click the "Collect Data" button
    to start plotting accelerometer readings from the SPOT.

    As you move the SPOT (for a virtual SPOT this requires moving the X, Y,
    and Z sliders in the "accel" tab of the sensor panel) you'll see a 
    dynamically updated plot of the accelerometer readings along all three 
    axis (X in green, Y in blue, Z in red and the total acceleration in 
    orange). 
