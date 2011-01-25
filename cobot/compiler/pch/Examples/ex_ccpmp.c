/////////////////////////////////////////////////////////////////////////
////                           EX_CCPMP.C                            ////
////                                                                 ////
////  This program will show how to use the built in CCP to          ////
////  measure a pulse width.                                         ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Connect a pulse generator to pin 3 (C2) and pin 2 (C1)      ////
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


long rise,fall,pulse_width;

#int_ccp2
void isr() {
   rise = CCP_1;
   fall = CCP_2;

   pulse_width = fall - rise;     // CCP_1 is the time the pulse went high
}                                 // CCP_2 is the time the pulse went low
                                  // pulse_width/(clock/4) is the time

                                  // In order for this to work the ISR
                                  // overhead must be less than the
                                  // low time.  For this program the
                                  // overhead is 45 instructions.  The
                                  // low time must then be at least
                                  // 9 us.

main() {

   printf("\r\nHigh time (sampled every second):\r\n");
   setup_ccp1(CCP_CAPTURE_RE);    // Configure CCP1 to capture rise
   setup_ccp2(CCP_CAPTURE_FE);    // Configure CCP2 to capture fall
   setup_timer_1(T1_INTERNAL);    // Start timer 1

   enable_interrupts(INT_CCP2);   // Setup interrupt on falling edge
   enable_interrupts(GLOBAL);

   while(TRUE) {
      delay_ms(1000);
      printf("\r%lu us      ", pulse_width/5 );
   }

}
