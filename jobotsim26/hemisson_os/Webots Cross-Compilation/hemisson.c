/*-----------------------------------------------------------------------*/
/*- Hemisson Webots cross-compilation example                           -*/
/*-                                                                     -*/
/*- This program can be compiled for Webots (with GCC or Visual C++) or -*/
/*- for the Hemisson robot with the HemiOS from http://www.hemisson.com -*/
/*- using CCS C compiler from http://www.ccsinfo.com                    -*/
/*-                                                                     -*/
/*- Copyright (c) 2003 Cyberbotics Sarl. All rights reserved            -*/
/*- Author: Olivier Michel, Cyberbotics                                 -*/
/*- Contact: Olivier.Michel@cyberbotics.com                             -*/
/*-                                                                     -*/
/*- Cyberbotics Sarl                                                    -*/
/*- Chemin de Vuasset, CP 98                                            -*/
/*- CH-1028 Preverenges                                                 -*/
/*- Switzerland                                                         -*/
/*- Phone/Fax: +41 21 693 86 24                                         -*/
/*-----------------------------------------------------------------------*/

#include <device/robot.h>
#include <device/differential_wheels.h>
#include <device/distance_sensor.h>
#include <device/light_sensor.h>
#include <device/led.h>

#define TIME_STEP 64

static DeviceTag ds0,ds1,ds2; /* pointer to distance sensors */

void reset() {
  /* it is necessary to use the HEMISSON_* constants as device names to
   * be able to cross-compile for the real Hemisson robot */
  ds0 = robot_get_device(HEMISSON_DS0); /* front right */
  ds1 = robot_get_device(HEMISSON_DS1); /* front left */
  ds2 = robot_get_device(HEMISSON_DS2); /* front */
}

int main() {
  int left_speed=0,right_speed=0;
  unsigned int ds0_value,ds1_value,ds2_value;
  
  robot_live(reset); /* Hemisson Initialisation */
  distance_sensor_enable(ds0,TIME_STEP);
  distance_sensor_enable(ds1,TIME_STEP);
  distance_sensor_enable(ds2,TIME_STEP);
  for(;;) {
    robot_step(TIME_STEP);
    ds0_value = distance_sensor_get_value(ds0);
    ds1_value = distance_sensor_get_value(ds1);
    ds2_value = distance_sensor_get_value(ds2);
    if (ds0_value>10 || ds1_value>10 || ds2_value>10) {
      left_speed=0;
      right_speed=0;
    } else {
      left_speed=10;
      right_speed=10;
    }
    differential_wheels_set_speed(left_speed,right_speed);
  }
  return 0;
}

