/////////////////////////////////////////////////////////////////////////
////                             EX_SQW.C                            ////
////                                                                 ////
////  This program displays a message over the RS-232 and waits for  ////
////  any keypress to continue.  The program will then begin a 1khz  ////
////  square wave over I/O pin B0.                                   ////
////                                                                 ////
////  Comment out the printf's and getc to eliminate the RS232 and   ////
////  just output a square wave.                                     ////
////                                                                 ////
////  Change both delay_us to delay_ms to make the frequency 1 hz.   ////
////  This will be more visable on a LED.                            ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Insert jumpers from 42 to 47.                               ////
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


#if defined(__PCM__)
#include <16c74.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 1

#elif defined(__PCB__)
#include <16c56.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif


main() {

   printf("Press any key to begin\n\r");
   getc();
   printf("1 khz signal activated\n\r");

   while (TRUE) {
     output_high(PIN_B0);
     delay_us(500);
     output_low(PIN_B0);
     delay_us(500);
   }
}
