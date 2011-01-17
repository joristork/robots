Web of Things Demos

Author: Vipul Gupta (last updated Oct 27, 2010)

--------
OVERVIEW
--------

The Web of Things is a vision where everyday devices (like home appliances,
health and energy monitoring devices, sensors etc) are fully integrated 
into the Web by reusing the its architectural styles (REST), and 
well-known technologies (URL, HTTP).

     "The main idea of REST is to design applications which implement
      their functionality completely as a set of URI-addressable 
      resources, with HTTP being the [uniform] access method for 
      interacting with them. In such an application, there is no 
      need for any special interface, the application fully blends 
      into the Web ..."       
                                -- Eric Wilde, Putting things to REST

This directory contains a collection of demos implementing parts of 
this vision on the Sun SPOTs. This demo is just a start. There are 
lots of interesting new functionality coming. Stay tuned!

CHTTPlib (under lib):

    A small footprint library for parsing and otherwise manipulating
    HTTP messages. A unique aspect of this library is its support for
    Compressed HTTP as described in 
    
    http://tools.ietf.org/html/draft-frank-6lowapp-chopan-00

WoTlib-common, WoTlib-device, WoTlib-host (under lib):

    Code to create a "nano" application server on the SPOTs and desktop
    computers. Think of this server as a stripped down version of server
    software such as Apache Tomcat or GlassFish. The nano app server hosts
    multiple "WebApplications" created by an application developer. Each
    "WebApplication" can be registered to handle HTTP requests directed 
    at a specific portion of the URL subspace, e.g. an application called
    MemoryInfo may be registered with the top level URL "/mem" and it will
    be responsible for HTTP requests to "/mem" as well as /mem/free and
    /mem/total.

    A distinguishing aspect of the NanoAppServer is its ability to process
    HTTP requests over different channels. Traditional application servers
    typically run over TCP, bind to a specific port and handle HTTP
    requests sent to that TCP port. The NanoAppServer can sit atop 
    multiple TCP as well as UDP connections (note that compressed HTTP
    often results in messages small enough to be carried inside a single 
    UDP datagram) and even a Reverse-HTTP connection (as described in 
    http://tools.ietf.org/html/draft-lentczner-rhttp-00). The 
    ability to handle HTTP requests over an RHTTP connection implies that
    the NanoAppServer could be running behind a firewall/NAT and still be
    accessible via an RHTTP gateway. We run such a gateway (from 
    Yaler.org) at sensor.network.com on port 1234.

    Another interesting feature of the NanoAppServer is that it 
    automatically furnishes meta-information about itself under 
    /.well-known (see RFC 5785, http://www.rfc-editor.org/rfc/
    rfc5785.txt), e.g. registered webapplications are listed under
    /.well-known/r and one can even search for specific webapplications,
    e.g. doing a GET on /.well-known/r?sh=s  will return the web
    application which has registered "s" as a short URL where it 
    listens for requests.

WoTServer-onDesktop:

    This is a standalone host application that shows the nano app server
    running on a desktop. It does not require SPOTs or a basestation. 
    Please refer to the README.TXT file included in the 
    WoTServer-onDesktop folder for additional details about this 
    application. 

RESTfulSPOTs:

    WoTGateway-onDesktop is a host application that interacts with
    neigboring SPOTs running WoTServer-onSPOT to make the 
    sensors/actuators and other services on these SPOTs accessible from
    anywhere on the Internet.

    WoTServer-onSPOT is a SPOT application that makes various sensors/
    actuators and other services on the SPOT accessible via a RESTful 
    API, e.g. one can access the light reading on a SPOT from anywhere 
    on the Internet simply by accessing a URL of the form

        http://server:port/<path-to-SPOT-device>/light 

    Or get the amount of free memory on it by doing an HTTP GET on 

        http://server:port/<path-to-SPOT-device>/mem/free

    Or change the color of LEDs to yellow by doing an HTTP PUT of   
    "[255,255,0]" to

    http://server:port/<path-to-SPOT-device>/leds/

    Complete documentation on the supported RESTful API is part of
    the meta information available at  
       http://server:port/<path-to-SPOT-device>/.well-known/r

NOTE:
=====

If you do not have Sun SPOT devices, you can still experiment with this 
code using the emulator. Specific instructions are included below but 
please refer to the emulator tutorial first if you are unfamiliar with 
the overall process of deploying and running code on virtual SPOTs.
   
---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Build the libraries -- Compressed HTTP library, WoTlib-common, 
   WoTlib-device and WoTlib-host
  
    % ant build-libs


2. Build and deploy WoTServer-onSPOT on Sun SPOTs.

   [Follow the instructions in 2(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    2(b) if you are using emulated SPOTs.]
   
    (a) Go into the RESTfulSPOTs/WoTServer-onSPOT directory, connect
        a free-range SPOT to a USB port and execute:

	% ant deploy

       [NOTE: If you will be using a large number of SPOTs in close 
        proximity, you should also configure them as ENDNODEs by 
        additionally executing the following command in the 
        WoTServer-onSPOT directory.

        % ant set-system-property -Dkey=spot.mesh.routing.enable -Dvalue=ENDNODE
   
       This will minimize broadcast traffic.]

   Turn off the SPOTs after code deployment. Repeat the above for 
   each free-range SPOT you wish to involve in the demo.

   (b) Go into the RESTfulSPOTs/WoTServer-onSPOT directory, connect a basestation
       to the desktop, and execute:

       % ant solarium

       This will start up the Solarium tool that includes the SPOT
       emulator. 

          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop up.
              Navigate to the WoTServer-onSPOT subdirectory 
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
              MIDlet ..." and select the "WoTServer" application.

3. Connect a basestation to the desktop (if you haven't done so already in 2(b))
   and run the gateway

    % cd RESTfulSPOTs/WoTGateway-onDesktop; ant host-run

   If you already have Solarium running, you can start the gateway application
   by choosing "SPOT Host Apps" -> "Run É" This will bring up a file chooser
   window. Navigate to the RESTfulSPOTs directory, select WoTGateway-onDesktop
   and click "Use this directory".

   One of the first things the gateway will output is its URL where you
   should point your browser. The URL will look like 

    http://<hostNameOrAddr>:8888/

   where hostNameOrAddr is the hostname or IP address of the desktop
   where the gateway is running.

4. Point a browser to this URL. At first you will see an empty list of 
   devices but as you turn on the SPOTs running WoTServer-onSPOT, they will 
   automatically be discovered and added to this list. After a SPOT 
   shows up on this list, you can start interacting with available 
   services in a RESTful manner.

   For example, to get the light readings on a SPOT whose address is
   0014.4F01.0000.0B0B, access the URL

       http://<hostNameOrAddr>:8888/spot-0B0B/light

   Cover up the light sensor with your hand and you should see a 
   change in the reading when you access the above URL again.
   [For an emulated SPOT, use the sensor panel to change the light reading.]

   To change the color of LEDs on this SPOT to red, do a PUT of 
   "[255,0,0]" at http://<hostNameOrAddr>:8888/spot-0B0B/leds. The 
   following line shows how to do this with curl. 
 
  The main page (whose URL is printed out by the Gateway on startup)
  includes a link "Dashboard". Clicking on this link will display a 
  page where live light readings are obtained periodically
  from each SPOT the gateway knows about. Each known SPOT is represented
  by a small graphic showing its Id, name (value assigned to the 
  property spot.name), battery level, last heard time, ratio of 
  successful to total requests, recent light reading and 
  LED setting. You can change the name (for real SPOTs only) or LED 
  setting by accessing a drop-down menu which is revealed when you 
  hover over a SPOT's Id.

  NOTE:  This demo was used as the basis for a hands-on lab at JavaOne 2010
  as described at http://blogs.sun.com/vipul/entry/the_web_of_things_and

---------------
WHAT TO DO NEXT
---------------
1. Even if you are behind a firewall or a NAT (as is almost always the 
   case on a home network), you can still enable access HTTP-based 
   interaction with your SPOTs from anywhere on the Internet using 
   reverse HTTP. Reverse HTTP requires a relay to be running on the 
   open Internet (outside the firewall) and we often
   have one running at port 1234 on sensor.network.com. 

   Make sure make sure that the reverse HTTP gateway is running by 
   pointing your browser to http://sensor.network.com:1234. 
   A "Gateway Time Out" message confirms that the gateway is 
   running. Next, look for a couple of lines in Main.java (under   
   RESTfulSPOTs/WoTGateway-onDesktop/src/org/sunspotworld/demo) 
   with "gw-xxxx" (around line 77), replace xxxx with the last four 
   hex digits of your basestation address and uncomment them.

   Now if you restart the gateway (step 3 in previous section), your 
   SPOTs will be accessible as:

    http://sensor.network.com:8888/gw-10E0/spot-0B0B

   (here we assume that the last four hex digits of 
   the basestation are 10E0 and those of the SPOT are 0B0B)

   For example, pointing the browser to 
             http://sensor.network.com:8888/gw-10E0/spot-0B0B/light
   will display the light reading on your SPOT. You can have remote   
   friends/relatives try this too even if you are behind a NAT/firewall.

2. Implement a new subclass of WebApplication, called AccelSensor.java, 
   inside WoTServer-onSPOT to handle the "/accel" URL subspace such that
   one may access the 3-axis accelerometer readings on the SPOT as 
    follows:

   (i) HTTP GET on http://server:port/<path-to-SPOT-device>/accel 
       should return a JSON string with accelerometer readings on all 
       three axes, e.g.

   {
      x: 0.000012,
      y: 0.00109,
      z: -0.99201
   }

   
    (ii) HTTP GET on http://server:port/<path-to-SPOT-device>/accel/x 
         should return a single float value corresponding to the X-axis 
         reading. Do the same for y and z.

3. Add authentication to PUTs on /leds. To do this, look at the 
   isAuthorized method in HOLLEDController.java and uncomment the code
   that enforces HTTP Basic authentication for PUTs.

   After you rebuild and deploy this code, you will no longer be
   able to change the LED colors unless you authenticate.
   The following cURL command shows how to specify the authorized user
   name and password. Here we assume the user is "Ali Baba" and
   the password is "open sesame".

  % curl --request PUT --dump-header "hdrs.txt" --data "[255,0,0]" --user "Ali Baba:open sesame" --basic "http://<hostNameOrAddr>:8888/spot-0B0B/leds/


---------------
TROUBLESHOOTING
---------------

  Make sure you have followed the instructions correctly, e.g. be sure
  to replace the address of the gateway and SPOT with correct values 
  for your setup.

  You may choose to disable auto-discovery of Sun SPOTs to debug any
  networking related issues. Auto-discovery on the free-range SPOTs 
  is controlled by a boolean variable AUTO_DISCOVERY in 
  WoTConstants.java in WoTServer-onSPOT. Set it to false, recompile 
  WoTServer-onSPOT and redeploy to the SPOTs. With auto-discovery
  turned off on the SPOTs, you'll need to hardcode the SPOT Ids you 
  want the gateway to know about. This is done by specifying the last 
  four hex digits of each SPOT in a comma-separated list and passing 
  that as arguments to the host application (WoTGateway-onDesktop). 
  For eample, to hardcode two SPOTs with IEEE addresses of
  0014.4F01.0000.1C8D and 0014.4F01.0000.3A0F, edit the main.args 
  line in build.properties in WoTGateway-onDesktop to read

      main.args=-spots 1C8D,3A0F

  Post any outstanding issues to the Sun SPOT forums at

          https://www.sunspotworld.com/forums

  You'll need to sign up for an account at sunspotworld.com if you don't 
  have one already. Include a detailed description of the problem and 
  steps needed to replicate it when you ask for help.


