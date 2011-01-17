HostQuerySpotsDemo

Author: Ron Goldman (last updated June 24, 2010)

--------
OVERVIEW
--------

HostQuerySpotsDemo is a host application that demonstrates how to locate
nearby SPOTs and then send them commands over-the-air (OTA). It makes
use of the spotclient library that is also used by both Solarium and
ant commands.

NOTE: This demo cannot be used with virtual or emulated SPOTs.

---------------------------------------
STEPS FOR BUILDING AND RUNNING THE DEMO
---------------------------------------

1. First, make sure that the SPOTs you wish to interact with are running.

2. Attach a basestation to the host computer and execute 

      % ant host-run

   This will run the HostQuerySpotsDemo and print out a list of nearby
   SPOTs, blink their LEDs, and print out their power statistics,
   e.g. their battery level.
