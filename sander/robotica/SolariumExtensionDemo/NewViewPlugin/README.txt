Sun SPOT Solarium New View Demo - Version 1.0

Author: Ron Goldman 

--------
OVERVIEW
--------

This demo shows how to add a new view to Solarium and also how to 
extend the Sun SPOT Emulator so that it interacts with the new view. 

The demo consists of three parts:

1.     RobotView-SolariumPlugin:

       This project creates a jar file that when plugged into Solarium
       makes a new MyRobot View. This new view maintains a model of the
       world that includes one or more iRobot Creates and walls.

2      RobotView-EmulatorExtension:

       This project creates a jar file to extend the Emulator to be
       able to handle emulating an iRobot Create and to interact with
       the robot model in the Robot View plugin. The jar file is used
       as a library by SPOT applications to issue commands to the
       emulated Create.

3      CreateDemoApp:

       This simple demo bounces an emulated Create robot off the walls
       in the new Robot View in Solarium using the Emulator extensions.


---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Create the MyRobotView.jar file

   Go into the RobotView-SolariumPlugin directory, and execute the 
   command:

       ant host-compile

   This will compile the necessary files and create a new
   MyRobotView.jar file in the <SDK>/lib/SPOTWorld/views/ 
   directory where Solarium will find it.

2. Create the myCreate_host.jar library to extend the Emulator

   Go into the RobotView-EmulatorExtension directory, and execute the 
   command:

       ant jar-app

   This will compile the necessary files and create a new 
   myCreate_host.jar library file in the <SDK>/lib/ directory where
   it can be used to compile SPOT Robot applications.

3. Run Solarium

   Go into the CreateDemoApp directory and execute:

       ant solarium

   Solarium will start up and be ready to display the new MyRobot view
   and run emulated SPOT Robot applications.

4. Create a new Robot View

   From the View menu select "My Robot View". Click the "-" button to
   zoom out so the entire room is visible.

5. Create an emulated robot

   Push the "Add robot" button. Notice that a new Create robot is
   displayed in the Robot View. Also notice that a new virtual SPOT
   is also created and displayed in the SPOT View.

6. Deploy the SPOT application

   Right click on the virtual SPOT, choose "Deploy a MIDlet bundle ..."
   from the menu. A file chooser window will pop up. Navigate to the
   CreateDemoApp directory and select the "build.xml" file. This
   will automatically cause the application to be compiled and loaded 
   into the virtual SPOT.

7. Run the application

   Right click again on the virtual SPOT, choose "Run MIDlet ..." and
   select the "IRobotCreateDemo" application. The SPOT application
   will command the Create to move forward until it bumps into the
   top wall of the room. It will then turn 180 degrees, locate the
   other walls, and finally move to the center of the room. You can
   push the "Reset" button to reposition the Create to the starting
   location, or you can drag it around with the mouse.


--------------------------
EMULATOR ARCHITECTURE NOTE
--------------------------

Each virtual SPOT has its own emulator, consisting of a Squawk VM that
sends & receives messages with a Solarium instance of the EmulatedSunSPOT
class. To extend the emulator requires two classes: one in Solarium and
one running in the Squawk VM. The Solarium class, e.g. RVObject, needs
to register itself with the EmulatedSunSPOT instance, and the Squawk-side
class, e.g. IRobotCreateEmulated, needs to register with the Emulator
instance.


-------------------------------
FILES NEEDED TO EXTEND SOLARIUM
-------------------------------

(all files are in the RobotView-SolariumPlugin project)

RobotView.java defines the new view, including its GUI. Has a dedicated
thread running to update the model every 50 milliseconds.

RVObject.java represents an iRobot Create as a node in the Robot View.
It handles updating the model of the iRobot Create as it moves about the 
RobotWorld. It interacts with the corresponding virtual SPOT in the Emulator.
It implements the com.sun.spot.solarium.spotworld.participants.IEmulatorExtension 
interface, which consists of the method void handleSpotMessage(String[] token) 
that the EmulatedSunSPOT instance will call to pass commands to the 
Create model. To send values/events back to the SPOT application 
running in the Emulator this class calls the EmulatedSunSPOT method 
void sendCmd(String msg).

RobotWorld.java a simple model consisting of Walls & Beacons.

Wall.java model of a simple rectangular object: position, size & color.

The images folder contains the graphic to use when displaying an iRobot
Create.

The build.xml file has a special target to create the jar file to plug
into Solarium. The jar file's manifest includes attributes that tell 
Solarium the name of the view and the class that implements the view.


-----------------------------------
FILES NEEDED TO EXTEND THE EMULATOR
-----------------------------------

(all files are in the RobotView-EmulatorExtension project)

IRobotCreateEmulated.java registers itself as an Emulator extension. It
implements the com.sun.spot.emulator.IEmulatorExtension interface, which
consists of the method void doCommand(String[] token) that the Emulator
will call to pass values/events to the running SPOT application. To send
values/commands this class calls the Emulator method void sendReply(String msg).

The other files in this project: IRobotCreate.java, Create.java, 
IBumpListener.java & IWallListener.java define the Create API that a 
SPOT application uses to control an emulated iRobot Create.
