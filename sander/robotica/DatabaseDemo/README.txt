Sun SPOT Database Demo - Version 1.0

Author: Vipul Gupta (last updated May 27, 2008)

--------
OVERVIEW
--------

This demo application stores periodic sensor readings measured
on one or more Sun SPOTs into a database and runs a few simple 
SQL queries on the stored data. It consists of three parts:

  - DatabaseDemo-onSPOT:

       This application runs on a Sun SPOT periodically 
       sampling the built-in light sensor and broadcasting
       those readings over the radio.

  - DatabaseDemo-onDesktop:

       This application runs on a host computer to which a basestation
       has been attached [NOTE: a basestation is not required if you'll
       be running the DatabaseDemo-onSPOT portion of the code entirely 
       on emulated SPOTs]. It listens for the sensor sample broadcasts
       and stores them in a database. After a user-specified duration,
       the application stops collecting data and runs SQL queries to 
       analyze the data, e.g. computing the max, min, and average 
       sensor reading.

   - Database:

       The demo code is written for mySQL, a popular open-source
       database but should work with other similar databases
       after appropriate modifications to DatabaseDemoHostApplication.java

       NOTE: 
          The database code is not included here. Instructions for
          downloading and installing mySQL can be found at
             http://www.sun.com/software/products/mysql/getit.jsp

NOTE:
=====

If you do not have Sun SPOT devices, you can still experiment with this code using the emulator. Specific instructions are included below but
please refer to the emulator tutorial first if you are unfamiliar with 
the overall process of deploying and running code on virtual SPOTs.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. Set up your database

   This demo makes the following assumptions about your mySQL
   installtion. 
   
   NOTE: These are the default settings for a fresh mySQL installation.

      (i) mySQL has been installed on the local host (same host
          on which you plan to run DatabaseDemo-onDesktop)
          at the default port (3306) 

     (ii) mySQL has a database called Test that can be manipulated 
          using the default administrator settings (empty password 
          for user 'root'). 

   During testing, we used mySQL version 5.0.51 and the JDBC driver
   for that is available in the jdbc-driver sub-directory. If you end 
   up using a different version, be sure to edit the build.properties 
   file so the JAR file containing the appropriate JDBC driver is included
   in the user.classpath property. 

   Before proceeding further, check that your database is set up 
   correctly. We use the mysql command line program to do a few
   simple operations on the database. The output from one such session
   is included at the end. You could try something similar.


2. Compile and deploy code to Sun SPOTs.

   [Follow the instructions in 2(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    2(b) if you are using emulated SPOTs.]

   (a) Go into the DatabaseDemo-onSPOT directory, connect a SPOT to 
       your host using a USB cable and execute:

       ant deploy

       This will compile the sensor sampling application and load it. 
       Disconnect the SPOT and reset it. After the SPOT finishes rebooting,
       it'll enter a loop where it reads the light sensor, broadcasts that 
       reading and goes to sleep for six seconds before the next sample.
       The SPOT flashes the rightmost LED to visually indicate a sampling
       event. You might notice the SPOT going into deep sleep between
       consecutive samples if it isn't running any other threads that 
       need to use the CPU, radio or other resources.

   (b) Go into the DatabaseDemo-onSPOT directory and execute:

       ant solarium

       This will start up the Solairum tool that includes the SPOT
       emulator. 
          i.  Create a virtual SPOT by choosing Emulator -> New Virtual
              SPOT.
         ii.  Right click on the virtual SPOT, choose "Deploy a MIDlet 
              bundle ..." from the menu. A file chooser window will pop up.
              Navigate to the DatabaseDemo-onSPOT subdirectory and select
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
       broadcasts that reading and goes to sleep for six seconds before 
       the next sample. These readings will be displayed in the window
       created in step (iii) above. The SPOT flashes the rightmost LED 
       to visually indicate a sampling event. You can change the light
       sensor values read by a virtual SPOT by manipulating the slider
       marked "Light Sensor" (under the "Enviro" tab) on its sensor 
       panel.

   Repeat this step on as many SPOTs as you want to collect samples on.

3. Run the host application

   [Follow the instructions in 3(a) if you are experimenting with
    this demo on real SPOTs. Otherwise, follow the instructions in
    3(b) if you are using emulated SPOTs.]

   (a) Go into the DatabaseDemo-onDesktop directory, connect a basestation
       to your host using a USB cable and execute:

       ant host-run

   (b) Go into the DatabaseDemo-onDesktop directory and execute:

   ant host-run -Dbasestation.shared=true -Dbasestation.not.required=true 

   This will start the host application that collects sample readings
   over the radio and stores them in mySQL. Each sample reading includes
   the Id (an IEEE address) of the sending SPOT, the timestamp when the
   sample was collected and the sensor reading. After about one minute
   (this interval can be modified in the program), the application
   will stop collecting data and run four simple SQL queries on the
   stored information. The first query, prints out all the entries in 
   the database sorted by increasing timestamp and grouped by SPOT Id.
   The remaining queries print the maximum, minimum and average sensor
   reading across all samples.

---------------
WHAT TO DO NEXT
---------------

   - Try out some more complex SQL queries, e.g. figure out how
     many samples had a sensor reading greater than the average.

   - Try to collect and store multiple sensor readings, e.g. modify
     the demo to collect/store/analyze temperature readings in
     addition to the light readings. 

     Let the demo run overnight and see if you can correlate changes
     in the light/temperature sensor readings readings with the onset
     of day/night. Increase the sleep period between samples to make 
     the battery last longer between consecutive charges.
     This only applies if you are using real (not emulated) SPOTs.

   - Share any other cool enhancements you implement with the
     Sun SPOT community at 
               http://www.sunspotworld.com/forums

---------------
TROUBLESHOOTING
---------------

   - If you see Exceptions of the kind "setUp caught ...", make sure
     that you have a working mySQL installation. See the next section
     on some simple commands to try.

   - Please report any other issues you encounter at:

          http://www.sunspotworld.com/forums

---------------------------------------------
SAMPLE INTERACTION WITH MYSQL INSTALLATION
---------------------------------------------

   These instructions are for a UNIX installation. The location
   of the mysql binary on a windows host would be different.

   % /usr/local/mysql/bin/mysql

   Welcome to the MySQL monitor.  Commands end with ; or \g.
   Your MySQL connection id is 15
   Server version: 5.0.51a MySQL Community Server (GPL)

   Type 'help;' or '\h' for help. Type '\c' to clear the buffer.

   mysql> use Test
   Reading table information for completion of table and column names
   You can turn off this feature to get a quicker startup with -A

   Database changed

   mysql> create table junk (field1 char(5), field2 smallint);
   Query OK, 0 rows affected (0.02 sec)

   mysql> describe junk;
       +--------+-------------+------+-----+---------+-------+
       | Field  | Type        | Null | Key | Default | Extra |
       +--------+-------------+------+-----+---------+-------+
       | field1 | char(5)     | YES  |     | NULL    |       | 
       | field2 | smallint(6) | YES  |     | NULL    |       | 
       +--------+-------------+------+-----+---------+-------+
       2 rows in set (0.16 sec)

  mysql> insert into junk (field1, field2) values ('abc', 0);
  Query OK, 1 row affected (0.01 sec)

  mysql> select * from junk;
  +--------+--------+
  | field1 | field2 |
  +--------+--------+
  | abc    |      0 | 
  +--------+--------+
  1 row in set (0.00 sec)

  mysql> drop table junk;
  Query OK, 0 rows affected (0.01 sec)

  mysql> describe junk;
  ERROR 1146 (42S02): Table 'test.junk' doesn't exist

  mysql> quit
  Bye
       
