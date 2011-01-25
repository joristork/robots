/////////////////////////////////////////////////////////////////////////
////                           EX_STWT.C                             ////
////                                                                 ////
////  This program uses the RTCC (timer0) and interrupts to keep a   ////
////  real time seconds counter.  A simple stop watch function is    ////
////  then implemented.                                              ////
////                                                                 ////
////  Configure the CCS prototype card as described below.           ////
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
   enable_interrupts(INT_RTCC);
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
