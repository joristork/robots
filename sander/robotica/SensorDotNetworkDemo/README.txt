Sun SPOT SensorDotNetwork Demo (last update 2009-11-02)

Author: Vipul Gupta

--------
OVERVIEW
--------

This demo application sends periodic sensor readings measured on one
or more Sun SPOTs to a web-based service called sensor.network
where that data can be easily searched, shared, visualized and 
analyzed.

Using this demo requires an account on sensor.network. Please visit 
http://sensor.network.com/ to sign up and/or learn more about 
the service.

1.     SensorDotNetworkDemo-onSPOT:

       This application runs on a Sun SPOT periodically sampling
       built-in sensors (light, temperature) and its internal state
       (battery level, voltage on USB port, uptime, time spent in
       shallow sleep, time in deep sleep, deep sleep count) and 
       broadcasting those readings over the radio. 

2      SensorDotNetworkDemo-onDesktop:

       This application runs on a host computer to which a basestation
       has been attached. It listens for the sensor sample broadcasts
       and posts them to sensor.network using a web-based API.
       Interaction with sensor.network requires an APIKey for
       authentication which may be obtained by signing up for an 
       account at http://sensor.network.com/.

NOTE:
=====

If you do not have Sun SPOT devices, you can still experiment with this 
code using the emulator. Specific instructions are included below but 
please refer to the emulator tutorial first if you are unfamiliar with 
the overall process of deploying and running code on virtual SPOTs.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Compile and deploy code to Sun SPOTs.

   [Follow the instructions in 1(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    1(b) if you are using emulated SPOTs.]

   (a) Go into the SensorDotNetworkDemo-onSPOT directory, connect a SPOT to 
       your host using a USB cable and execute:

       ant deploy

       This will compile the sensor sampling application and load it. 
       Disconnect the SPOT and reset it. After the SPOT finishes rebooting,
       it'll enter a loop where it reads its internal state and sensors, 
       broadcasts that reading and goes to sleep for fifteen minutes 
       before the next sample. The SPOT flashes the rightmost LED to 
       visually indicate a sampling event. You should notice the SPOT 
       going into deep sleep between consecutive samples if it isn't 
       connected to a USB port and isn't running any other threads that 
       need to use the CPU, radio or other resources.

       Unless you are running other applications on the SPOT concurrently,
       you'll want to make sure that the SPOT spends almost the entire period
       between consecutive samples in deep sleep. Configure the SPOT as an
       "end-node" (as opposed to a router) by using the following command:

       % ant set-system-property -Dkey=spot.mesh.routing.enable -Dvalue=ENDNODE

       and restart the SPOT. This will disable "routing" functionality on the 
       SPOT, i.e. this SPOT will no longer participate in forwarding traffic for
       other SPOTs in its radio range, but it will also keep the SPOT from waking
       up every 20 seconds (or more frequently) for routing related chores. This 
       should enhance the battery life significantly.


   (b) Go into the SensorDotNetworkDemo-onSPOT directory and execute:

       ant solarium

       This will start up the Solarium tool that includes the SPOT
       emulator. 
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop up.
              Navigate to the SensorDotNetworkDemo-onSPOT subdirectory 
              and select the "build.xml" file. This will automatically 
              cause the application to be compiled and loaded into the 
              virtual SPOT.
        iii.  Right click on the virtual SPOT, choose "Display application
              output" -> "in Internal Frame" to open a window where
              output from the application will be displayed.
         iv.  Right click on the virtual SPOT, choose "Display sensor
              panel" to open up a multi-tabbed panel that allows you
              to manipulate various sensor readings experienced by the
              virtual SPOT.
          v.  Right click again on the virtual SPOT, choose "Run 
              MIDlet ..." and select the "ExtendedSensorSampler" 
              application.

       The SPOT will enter a loop where it reads its sensors and internal
       state and broadcasts that reading and goes to sleep for fifteen
       minutes before the next sample. These readings will be displayed 
       in the window created in step (iii) above. The SPOT flashes the 
       rightmost LED to visually indicate a sampling event. You can 
       change the light sensor values read by a virtual SPOT by 
       manipulating the slider marked "Light Sensor" (under the 
       "Enviro" tab) on its sensor panel.

   Repeat this step on as many SPOTs as you want to collect samples on.

2. Run the host application

   As part of signing up at sensor.network, you'll be assigned a unique
   API key -- a base-64 encoded string that serves as a passphrase for
   authenticating yourself. Edit build.properties and replace 
   "YourAPIKeyHere" with the contents of your API key.

   [Follow the instructions in 2(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    2(b) if you are using emulated SPOTs.]

   (a) Go into the SensorDotNetworkDemo-onDesktop directory, connect a basestation
       to your host using a USB cable and execute:

       ant host-run

   (b) Go into the SensorDotNetworkDemo-onDesktop directory and execute:

   ant host-run -Dbasestation.shared=true -Dbasestation.not.required=true 

   This will start the host application that collects sample readings
   over the radio and sends them for archival at sensor.network.com. 
   Each sample reading includes the Id (an IEEE address) of the 
   sending SPOT, the timestamp when the sample was collected and a
   bunch of sensor/internal state readings. 

3. Log in to http://sensor.network.com/ and search for your datastreams
   tagged "SNDemo" on the "Explore Datastreams" page. There should be one
   datastream for each Sun SPOT running the SensorDotNetworkDemo-onSPOT 
   application. After the demo has been running for a bit (remember that
   the default sampling period is 15 minutes), you should 
   start seeing activity in the dashboard view. You can create 
   visualizations of the data collected by visiting the "Create 
   Visualization" page. Many of the visualizations featured on 
   sensor.network can be embedded in your own blog or webpage and 
   continue to get updated automatically as new data comes in for 
   those datastreams at sensor.network. We call this feature "LivePlot". 

---------------
WHAT TO DO NEXT
---------------

   - Let the demo run overnight and see if you can correlate changes
     in the light/temperature sensor readings readings with the onset
     of day/night. Increase the sleep period between samples to make 
     the battery last longer between consecutive charges by editing
     the SAMPLE_PERIOD variable in *both* ExtendedSensorSampler.java
     and SensorDotNetworkHostApplication.java. This only
     applies if you are using real (not emulated) SPOTs.      

   - If you were to create a scatterplot visualization to study the 
     correlation between light and temperature readings or between
     deep sleep count and deep sleep time, what would you expect the plot
     to look like? Create that visualization at sensor.network and 
     see if it matches your expectation.

   - Add the ability to monitor free memory and see if you can tell
     all the instances when garbage collection kicks in by plotting
     free memory against time.
 
