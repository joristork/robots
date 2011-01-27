/////////////////////////////////////////////////////////////////////////
////                            EX_SPEED.C                           ////
////                                                                 ////
////  This example uses two photo diodes (PN334PA-ND) to time the    ////
////  motion of an  object across the diodes.  By entering in the    ////
////  distance between the diodes, the program can calculate the     ////
////  speed of the object in miles per hour.  NOTE:  Wrapping the    ////
////  diodes in paper to make a tube will greatly help channel the   ////
////  detectors and will improve the results.                        ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Jumper from pin 27 to short end of both photo diodes        ////
////     Jumper from pin 17 to long end of left photo diode          ////
////     Jumper from pin 18 to long end of right photo diode         ////
////     See additional connections below.                           ////
////                                                                 ////
////  This example will work with the PCM and PCH compilers.  The    ////
////  following conditional compilation lines are used to include a  ////
////  valid device for each compiler.  Change the device, clock and  ////
////  RS232 pins for your hardware if needed.                        ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,2001 Custom Computer Services         ////
//// This source code may only be used by licensed users of the CCS  ////
//// C compiler.  This source code may only be distributed to other  ////
//// licensed users of the CCS C compiler.  No other use,            ////
//// reproduction or distribution is permitted without written       ////
//// permission.  Derivative programs created using this software    ////
//// in object code form are not restricted in any way.              ////
/////////////////////////////////////////////////////////////////////////


#if defined(__PCM__)
#include <16c74.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#include <stdlib.h>

// The calibration offset is how much lower the A/D converter must get below
// the reference value in order for it to trigger.  1 = 0.0195V
#define CALIBRATION_OFFSET    2


main()   {
   int ref1,ref2;
   long time;
   float distance,mph;
   char s_dist[6];


   printf("\n\n\nThis program times movement across the two sensors.\n");
   printf("Note that this must take place in less than 800ms.\n");

   // Initial setup
   setup_adc_ports(ALL_ANALOG);
   setup_adc(ADC_CLOCK_INTERNAL);
   setup_timer_1(T1_INTERNAL|T1_DIV_BY_8);

   printf("\nType in the distance in inches between the two sensors and push enter.\n");
   gets(s_dist);

   // get distance between sensors
   distance = atof(s_dist);
   printf("\ndistance = %f\n",distance);

   while(TRUE)
   {
      // Calibrate the sensors
      printf("\nCalibrating the sensors, please don't cover them!\n");

      set_adc_channel(2);
      delay_ms(500);
      ref2=read_adc();

      set_adc_channel(1);
      delay_ms(1);
      ref1=read_adc();

      printf("Calibration done.\n");
      printf("Sweep your hand from the left to the right across the sensors.\n");

      set_adc_channel(2);
      delay_ms(1);
      
      // wait for the first sensor to be triggered
      while(read_adc()>(ref2-CALIBRATION_OFFSET))
         delay_us(5);
      set_timer1(0);                      // start counting
      set_adc_channel(1);                 // switch to other sensor
      delay_us(10);

      // wait for the second sensor to be triggered
      while(read_adc()>(ref1-CALIBRATION_OFFSET))
         delay_us(5);
      time=get_timer1();                  // get the time

      mph = 0.000009929*(float)time/3600.0;  // calculate the time in hours
      mph = distance/(63360.0*mph);

      printf("Your speed was %3.2f mph.\n",mph);
      
      delay_ms(1000);                     // wait until diodes are uncovered again
   }
}


