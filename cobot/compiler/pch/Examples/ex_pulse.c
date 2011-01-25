/////////////////////////////////////////////////////////////////////////
////                           EX_PULSE.C                            ////
////                                                                 ////
////  This program uses the RTCC (timer0) to time a single pulse     ////
////  input to the PIC.                                              ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Connect pulse generator to 48                               ////
////     See additional connections below.                           ////
////                                                                 ////
////  This example will work with the PCB, PCM and PCH compilers.    ////
////  The following conditional compilation lines are used to        ////
////  include a valid device for each compiler.  Change the device,  ////
////  clock and RS232 pins for your hardware if needed.              ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,2001 Custom Computer Services         ////
//// This source code may only be used by licensed users of the CCS  ////
//// C compiler.  This source code may only be distributed to other  ////
//// licensed users of the CCS C compiler.  No other use,            ////
//// reproduction or distribution is permitted without written       ////
//// permission.  Derivative programs created using this software    ////
//// in object code form are not restricted in any way.              ////
/////////////////////////////////////////////////////////////////////////


#if defined(__PCB__)
#include <16c56.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCM__)
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

#include <ctype.h>


char get_scale() {
   char scale;

   do {
     printf("\n\rPress S for short or L for long: ");
     scale = getc();
     scale = toupper(scale);
   } while ( (scale!='S') && (scale!='L') );

   return(scale);
}

void wait_for_low_to_high() {

   while(input(PIN_B1)) ;       /* if it's high, wait for a low */

   delay_us(3);                 /* account for fall time */

   while(!input(PIN_B1));       /* wait for signal to go high */
}

void wait_for_low() {

   delay_us(3);                 /* account for rise time */

   while(input(PIN_B1));        /* wait for signal to go high */
}


main() {

   char scale;
   byte time;

   do {
      scale = get_scale();

      if(scale=='S')
         setup_counters( RTCC_INTERNAL, RTCC_DIV_64 );
      else
         setup_counters( RTCC_INTERNAL, RTCC_DIV_256 );

      printf("\n\rWaiting...\n\r");

      wait_for_low_to_high();
      set_rtcc(0);
      wait_for_low();
      time = get_rtcc();

      printf("Counter value: %2X\n\n\r", time);

   } while (TRUE);
}
