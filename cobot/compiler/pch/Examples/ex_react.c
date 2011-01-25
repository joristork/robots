/////////////////////////////////////////////////////////////////////////
////                           EX_REACT.C                            ////
////                                                                 ////
////  This program will show how to use the built in CCP to find the ////
////  reaction time of an external event.                            ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Make the following connections:                             ////
////        W117SIP-6 Relay    Protoboard                            ////
////              1               3 C2 (also use a pull-up resistor) ////
////              2              28 +5V                              ////
////              3              48 B1                               ////
////              4              27 Gnd                              ////
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
#include <16c77.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#define RELAY_SET PIN_B1


main() {

   setup_timer_1(T1_INTERNAL);
   output_low(RELAY_SET);

   while(TRUE)
   {
      printf("\nHit any key to turn on relay and begin timing\n");
      getc();

      setup_ccp1(CCP_CAPTURE_RE);
      CCP_1=0;
      set_timer1(0);
      output_high(RELAY_SET);
      delay_ms(50);
      printf("Time for relay to turn on:  %lu us\r\n",CCP_1/5);

      delay_ms(100);

      setup_ccp1(CCP_CAPTURE_FE);
      CCP_1=0;
      set_timer1(0);
      output_low(RELAY_SET);
      delay_ms(50);
      printf("Time for relay to turn off:  %lu us\r\n",CCP_1/5);

      delay_ms(100);
   }
}



