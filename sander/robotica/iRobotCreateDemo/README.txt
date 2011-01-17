Sun SPOT iRobot Create Demo - Version 1.0

Author: Ron Goldman 

--------
OVERVIEW
--------

This directory contains a simple demo to bounce an emulated Create robot
off the walls in the Robot View in Solarium.

It uses a special library, <SDK>/lib/create_host.jar, that contains
methods to control the Create robot in the Emulator in Solarium.
Documentation for the library is in the CreateAPIjavadoc directory.

Note: Another library, create_device.jar, can be used to control a real
iRobot Create with a real Sun SPOT. Since both libraries use the same 
API, the same SPOT application can be run in the Emulator and on a
real Create.


---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Start up Solarium

   Go into the iRobotCreateDemo directory and execute:

       ant solarium

   This will start up the Solarium tool that includes the SPOT Robot
   emulator. 

2. Open the Robot View

   From the View menu select "Robot View". Click the "-" button to
   zoom out so the entire room is visible.

3. Create an emulated robot

   Push the "Add robot" button. Notice that a new Create robot is
   displayed in the Robot View. Also notice that a new virtual SPOT
   is also created and displayed in the SPOT View.

4. Deploy the SPOT application

   Right click on the virtual SPOT, choose "Deploy a MIDlet bundle ..."
   from the menu. A file chooser window will pop up. Navigate to the
   iRobotCreateDemo directory and select the "build.xml" file. This
   will automatically cause the application to be compiled and loaded 
   into the virtual SPOT.

5. Run the application

   Right click again on the virtual SPOT, choose "Run MIDlet ..." and
   select the "IRobotCreateDemo" application. The SPOT application
   will command the Create to move forward until it bumps into the
   top wall of the room. It will then turn 180 degrees, locate the
   other walls, and finally move to the center of the room. You can
   push the "Reset" button to reposition the Create to the starting
   location, or you can drag it around with the mouse.

6. Write your own Create robot programs

   You can modify the source code to make the Create robot do whatever
   you wish. See if you can program the Create to navigate through the
   Maze or Obstacle course. Each of those views includes an 'X' as a
   starting point and a 'O' as an ending point. The robot includes
   a sensor that allows it to sense when it is over one of these marks.

