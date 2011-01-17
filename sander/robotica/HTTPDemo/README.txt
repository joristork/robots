Sun SPOT HTTP Demo - Version 1.1

Author: Vipul Gupta (last updated Nov 11, 2010)

--------
OVERVIEW
--------

This demo application uses the Sun SPOT's built-in HTTP networking
capability to interact with the PostBin service (see
http://www.postbin.org).

The application uses HTTP POSTs to post the light and temperature
readings to http://www.postbin.org/yvkuby whenever Switch 2 is
pressed.

The SPOT will blink its LED array in a saw-tooth pattern while it
is attempting to post its sensor readings. The LEDs will turn green
if the post is successful and red otherwise. Successful posts can
be accessed by pointing your browser at the same URL:
      http://www.postbin.org/yvkuby

NOTE:
=====

If you do not have Sun SPOT devices, you can still experiment with
this code using the emulator. Specific instructions are included below
but please refer to the emulator tutorial first if you are unfamiliar
with the overall process of deploying and running code on virtual SPOTs.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------
To build and run the demo, follow these steps.

1. Compiling and loading the application on a SPOT:

   [Follow the instructions in 1(a) if you have real SPOTs. Otherwise,
   follow the instructions in 1(b) if you are using emulated SPOTs.]

   (a) In the directory containing this README file, execute

       ant deploy

       This will compile the application and load it. Disconnect
       the SPOT. *DO NOT* reset it just yet.

    (b) In the directory containing this README file, execute

        ant solarium

        to start up the Solarium tool which also includes the SPOT
        emulator.
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet
              bundle ..." from the menu. A file chooser window will pop
              up. Navigate to the HTTPDemo subdirectory and select
              the "build.xml" file. This will automatically cause the
              application to be compiled and loaded into the virtual SPOT.
        iii.  Right click on the virtual SPOT, choose "Display application
              output" -> "in Internal Frame" to open a window where
              output from the application will be displayed.
         iv.  Right click on the virtual SPOT, choose "Display sensor
              panel" to open up a multi-tabbed panel that allows you
              to manipulate various sensor readings experienced by the
              virtual SPOT.

2. Starting a socket proxy:

   [Skip this step if you are using emulated SPOTs. Emulated SPOTs run
    on a host computer and can establish direct HTTP connections to
    Internet hosts without the need for a proxy.]

   HTTP networking requires the use of a basestation and a
   special host application called the socket-proxy that
   acts as a relay between a TCP/IP connection (e.g., with
   Twitter.com in this case) and a radiostream connection
   to a SPOT.

   Attach a basestation to your host with a USB cable and execute

   ant socket-proxy-gui

   Wait for a GUI window to open, and check the box labelled
   "I/O". This will enable logging of the actual data sent
   over the HTTP connection.

   Press the start button (in the GUI window) to start the socket-proxy.

3. Starting the HTTPDemo application:

   [Follow the instructions in 3(a) if you have real SPOTs. Otherwise,
   follow the instructions in 3(b) if you are using emulated SPOTs.]

   (a) Now reset the SPOT you used in Step 1 and monitor the "Proxy Log"
       in the GUI window from Step 2.

       Press switch 2 on the SPOT to cause it to post its sensor
       readings. If the POST is successful, all LEDs will turn green
       and you can verify the successful POST by pointing your browser
       to http://www.postbin.org/yvkuby and matching the address in the
       last post against that of your own SPOT.


   (b) Now start the application by right clicking on the virtual SPOT and
       selecting "Run MIDlet" -> "HTTPDemo".

       Press switch 2 on the SPOT to cause it to post its sensor
       readings. If the POST is successful, all LEDs will turn green
       and you can verify the successful POST by pointing your browser
       to http://www.postbin.org/yvkuby and matching the address in the
       last post against that of your own SPOT.

---------------
WHAT TO DO NEXT
---------------

  - Try changing the message that gets posted (the code is in the
    switchPressed method of inside src/org/sunspotworld/demo/
    MySwitchListener.java), e.g. you could get creative and use the
    accelerometer readings to post the SPOTs orientation, e.g.
    laying on its back or standing up or laying face down.

  - If you do not wish to have POSTs from your SPOTs intermingled
    with those from other people's SPOTs, you can get your own
    POSTBIN URL by clicking the "Make a PostBin" button at
    http://www.postbin.org/ and modifying the POST-URL property in
    resources/META-INF/MANIFEST.MF to point to your URL.

---------------
TROUBLESHOOTING
---------------

  - This demo requires a working Internet connection. Make sure
    your host is connected to the Internet and can access
    		http://www.postbin.org/yvkuby
    directly *without* going through a proxy.

    If you happen to be behind a firewall and need to use a proxy
    to reach postbin.org, add the following line to
    resources/META-INF/MANIFEST.MF (see the section on "Configuring
    the http protocol" inside the Sun SPOT developer's Guide)

    com.sun.squawk.io.j2me.http.Protocol-HttpProxy: <proxyaddress>:<port>


  - If you have a working Internet connection but are seeing errors like
    "Attempt to open connection twice for ...", try restarting the demo.
    You should never see this problem on an emulator. Here is the exact
    sequence of steps you should use:

    a. turn off the SPOT running the HTTPDemo
    b. terminate the socket proxy host application
    c. restart the socket proxy host application (i.e. repeat Step 2)
       and wait until it is running again
    d. turn on the SPOT to start the HTTPDemo

  - If you are still encountering issues, please post them to the
    forums at http://www.sunspotworld.com/forums



