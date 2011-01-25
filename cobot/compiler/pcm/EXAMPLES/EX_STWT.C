/////////////////////////////////////////////////////////////////////////
////                           EX_STWT.C                             ////
////                                                                 ////
////  This program uses the RTCC (timer0) and interrupts to keep a   ////
////  real time seconds counter.  A simple stop watch function is    ////
////  then implemented.                                              ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18.                ////
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

#include <16C84.H>

#fuses HS,NOWDT,NOPROTECT

#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)


#define INTS_PER_SECOND 76     // (20000000/(4*256*256))

byte seconds;      // A running seconds counter
byte int_count;    // Number of interrupts left before a second has elapsed


#int_rtcc                          // This function is called every time
clock_isr() {                      // the RTCC (timer0) overflows (255->0).
                                   // For this program this is apx 76 times
    if(--int_count==0) {           // per second.
      ++seconds;
      int_count=INTS_PER_SECOND;
    }

}


main() {

   byte start;

   int_count=INTS_PER_SECOND;
   set_rtcc(0);
   setup_counters( RTCC_INTERNAL, RTCC_DIV_256);
   enable_interrupts(RTCC_ZERO);
   enable_interrupts(GLOBAL);

   do {

      printf("Press any key to begin.\n\r");
      getc();
      start=seconds;
      printf("Press any key to stop.\n\r");
      getc();
      printf("%u seconds.\n\r",seconds-start);

   } while (TRUE);

}
