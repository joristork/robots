SPOTWebDemo

Author: Vipul Gupta (last updated May 4, 2009)

--------
OVERVIEW
--------

SPOTWebDemo is a host application that starts up a simple web 
server and lets remote users interact with a collection of SPOTs
in the vicinty of the attached basestation using a standard
web browser. Authorized remote users can monitor the state of
sensors, applications and other system statistics. They can also
install, start, pause, resume, stop and remove applications.

NOTE: This demo cannot be used with virtual or emulated SPOTs.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. First, make sure that the SPOTs you wish to interact with are running
   the same version of the SDK under which this host application will run.
   One way to accomplish this is to attach each of these SPOTs to the
   host computer via a USB cable and run the 'ant upgrade' command.

2. After the SPOTs have been upgraded, attach a basestation to the host
   computer and execute 

      % ant host-run

   This should produce output that looks like the following

     [java] *************************************************
     [java] Launching jetty on port 8080 ...
     [java] After the server is running, point your browser to
     [java] 	http://localhost:8080/index.jsp
     [java] Log in as demo/demo when prompted.
     [java] *************************************************

   Make sure that no other application is using TCP port 8080 on the 
   host computer. If the port is in use, SPOTWebDemo will display an
   error and exit.

3. Next, point a standard web browser (Firefox, Safari etc) at the
   URL shown, i.e. http://localhost:8080/index.jsp. You can even
   run the browser on a host other than the one where SPOTWebDemo is
   running. In the latter case, replace "localhost" with the IP address
   or hostname of the host running SPOTWebDemo.

4. The front page will prompt you for a username and password. Use "demo"
   (without the quotes) for both the username and the password. This will
   bring up a Google map mashup showing discovered free-range SPOTs in the
   vicinity of the basestation. 

   Click on the icon of a discovered SPOT to start interacting with it. The
   FAQs link has a question "I see a map and at least one SPOT without the
   'caution' triangle. Now what do I do?" the answer to which lists some of
   the interactions you can try, e.g. assigning or altering the name and/or
   location of a SPOT, deploying applications and managing their life cycle,
   etc.


---------------
TROUBLESHOOTING
---------------

  - On some platforms (e.g. Vista and Ubuntu), you may encounter an error
    "Unable to compile class for JSP" when trying to access a URL ending in
    .jsp. This indicates that, for some reason, the system was unable to find
    javac which is needed to compile Java Server Pages (JSPs). On Ubuntu, I
    was able to get around the problem by locating tools.jar (this is typically
    found in the lib directory of the JDK installation) and adding its full
    path to the user.classpath definition inside build.properties. For example,
    since my JDK was installed in /home/sunspot/jdk1.6.0_07, I edited
    build.properties to include the full path to tools.jar as the first element
    in the definition of user.classpath:

       user.classpath=/home/sunspot/jdk1.6.0_07/lib/tools.jar:lib/jsp-2.0/slf4j-simple-1.3 ...

    If you figure out an elegant solution to this issue, please post it on the
    forums (see below) so we can include it in a future release.

  - Refer to the FAQs page (a link is shown on the left in your browser window
    while you are interacting with SPOTWebDemo) for troubleshooting tips.

  - You may also post feedback or problem reports to the Sun SPOT forums
    at http://www.sunspotworld.com/forums


