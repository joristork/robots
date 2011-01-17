Library Extension Sample Code.

  This code illustrates how to add user code to the Sun SPOT library.
  Please refer to the section titled "Using library suites" in the
  "Sun SPOT Developers Guide" for details.

  Follow these instructions to use this sample code:

1. Open the addertest project and choose "Build Project" from the
   project menu. This attempt should fail with the errors:
      "package adder does not exist"
      "cannot find symbol Adder"
   indicating that there is no Adder class in the default library.

2. Open the adderlib project in Netbeans and choose "Build library
   jar file" to create a jar file containing the library extension.
   You can check the correct execution of this command by looking in
   your SDK installation, where you should now find adderlib_rt.jar in
   the lib directory. The name of the .jar file is controlled from the
   file build.properties in the root directory of the adderlib directory.

   Note: if you are not using NetBeans then from the command line "cd"
   to the adderlib project directory and do "ant jar-app".

3. Manually edit the .sunspot.properties file in your user root folder.
   Change the property "spot.library.name" from transducerlib to adderlib:

   spot.library.name=adderlib

   Then add the following property definition (all on one line):

   spot.library.addin.jars=${sunspot.lib}/adderlib_rt.jar${path.separator}${sunspot.lib}/multihop_common.jar${path.separator}${sunspot.lib}/transducer_device.jar

4. In the adderlib project in Netbeans choose "Build library suite" to
   create a new version of the SPOT library, named adderlib, that
   includes the new Adder class. Deploy this library to a Sun SPOT by
   choosing "Flash library to Sun SPOT".

   Note: if you are not using NetBeans then from the command line, cd to
   the adderlib project directory and do "ant library" followed by
   "ant flashlibrary".
    
5. Back in the addertest project, choose "Build Project + Deploy to 
   Sun SPOT" followed by "Run Project"

6. If all goes well the addertest program will print
       2 plus 2 is 4
       CRC of 2,3,4 is 7992

7. To cleanup restore your .sunspot.properties file so that 

   spot.library.name=transducerlib

   and remove the line defining the "spot.library.addin.jars" property.
   Then in the adderlib project in Netbeans choose "Flash library to Sun
   SPOT" to write the original transducerlib suite to your SPOT.

