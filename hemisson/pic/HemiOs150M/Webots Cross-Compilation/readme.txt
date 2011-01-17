Using Webots API with the Hemisson robot

1. Advantages:

Write once, compile and run on both Webots simulation and on the real Hemisson robot.

2. Requirements:

Webots 4.0 (version STD, EDU or PRO) or ealier. http://www.cyberbotics.com
Price: CHF 300.

CCS C: PCW compiler from Custom Computer Services, Inc. http://www.ccsinfo.com
Discount available using you Hemisson serial number at http://ccsinfo.com/cgi-bin/order.cgi?hemisson
Price: USD 100.

3. Setup:

In Webots, open the world hemisson.wbt which uses the controller produced by controllers/hemisson/hemisson.c
In CCS C, use the Hemisson.PJT project which uses hemisson.c (the same file as above) to control the robot.
Once compiled, use Hemisson Uploader to upload the resulting hemisson.HEX file onto the real robot.



15-MAY-03
Olivier Michel - Olivier.Michel@cyberbotics.com
Cyberbotics