SignAndVerify 

Author: Vipul Gupta (last updated Apr 28, 2009)

--------
OVERVIEW
--------

This demo application uses the Sun SPOT crypto functionality 
(implemented in lib/SSL_device.jar) to digitally sign data
and verify that signature.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------
To build and run the demo, follow these steps.

1. In the directory containing this README file, execute

      % ant deploy run

2. This will produce output like:

     [java] 
     [java] Squawk VM Starting (red-090414a)...
     [java] SignAndVerify: This demo shows how to use digital signatures on a Sun SPOT.
     [java] Generating public/private key pair ...
     [java] Signing data ...
     [java] Verifying signature on unmodified data (this should succeed) ... successful
     [java] Verifying signature on modified data (this should fail) ... failed
     [java] Done.

---------------
WHAT TO DO NEXT
---------------

  - Try using a different signature algorithm (you will need to look at the 
    javadoc included in CryptoDemos/doc
