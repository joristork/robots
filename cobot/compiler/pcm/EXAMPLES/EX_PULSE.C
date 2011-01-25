/////////////////////////////////////////////////////////////////////////
////                           EX_PULSE.C                            ////
////                                                                 ////
////  This program uses the RTCC (timer0) to time a single pulse     ////
////  input to the PIC.                                              ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18                 ////
////      Connect pulse generator to 48                              ////
////                                                                 ////
////  For a 40 pin part such as the 16C74 add jumpers from           ////
////  8 to 11, 7 to 12, and change the #USE RS232 to:                ////
////      #use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)             ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#ifdef __PCB__
#include <16C56.H>
#else
#include <16C84.H>
#endif

#fuses   HS,NOPROTECT,NOWDT

#include <CTYPE.H>

#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)


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
