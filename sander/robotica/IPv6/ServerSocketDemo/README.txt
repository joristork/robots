ServerSocket Demo

Author: Pete St. Pierre
--------
OVERVIEW
--------

This demo code provides an example of how to use server sockets to 
create a server that can handle multiple incoming TCP connections.
1.     KnockKnockServer-onSPOT:

       This application runs on a Sun SPOT and accepts client requests
       from a KnockKnock client.  

2(a)   KnockKnockClient-onDesktop:

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

   (a) Go into the KnockKnockServer-onSPOT directory, connect a SPOT to 
       your host using a USB cable and execute:

       ant deploy

       This will compile the sensor sampling application and load it. 
       Disconnect the SPOT and reset it. After the SPOT finishes rebooting,
       it'll enter a loop where it waits for incoming connections.

   (b) Go into the KnockKnockServer-onSPOT directory and execute:

       ant solarium

       This will start up the Solarium tool that includes the SPOT
       emulator. 
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop up.
              Navigate to the KnockKnockServer-onSPOT subdirectory and select
              the "build.xml" file. This will automatically cause the 
              application to be compiled and loaded into the virtual SPOT.
        iii.  Right click on the virtual SPOT, choose "Display application
              output" -> "in Internal Frame" to open a window where
              output from the application will be displayed.

2. Run the host application

   [Follow the instructions in 2(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    2(b) if you are using emulated SPOTs.]

   (a) The KnockKnockClient-onDesktop program requires 1 argument, the address
       of the server it will connect to and receive data. For those new
       to IPv6, a basic explaination of IPv6 addressing can be found in
       the SDK Developer's Guide.

       Go into the KnockKnockClient-onDesktop directory, connect a basestation
       to your host using a USB cable and execute:

       ant host-run -Dmain.args=<target>
       
       where <target is the address of the node you wish to query.  For 
       example:

       ant host-run -Dmain.args=fe80::8192:4f0f:0000:1001

   (b) Go into the KnockKnockClient-onDesktop directory and execute the above 
       command with the addition of arguments for shared basestation and 
       basestation not required:

   ant host-run -Dmain.args=<target>
          -Dbasestation.shared=true -Dbasestation.not.required=true 

       such as:
   ant host-run -Dmain.args=fe80::8192:4f0f:0000:1001 
          -Dbasestation.shared=true -Dbasestation.not.required=true

   This will start the host application that collects a sample reading
   over the radio and displays it. 

