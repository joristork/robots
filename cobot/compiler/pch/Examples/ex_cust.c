/////////////////////////////////////////////////////////////////////////
////                             EX_CUST.C                           ////
////                                                                 ////
//// This program shows how to customize the compiler operation.     ////
//// The following are shown:                                        ////
////       - Case sensitivity is turned on (normally its off)        ////
////       - short, int and long are 8,16,32 bits (instead of 1,8,16)////
////       - RAM is zeroed out before main starts                    ////
////       - Each function is identified as inline or separate       ////
////       - In some areas the automatic TRIState manipulation       ////
////         is disabled to save time                                ////
////       - Registers from 20h to 2Fh are excluded from C use       ////
////       - #pragma is used on non-standard C pre-processor lines   ////
////         (not required)                                          ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
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

#pragma case
#pragma type short=8,int=16,long=32
#define version 0x0001

#if defined(__pcm__)
#include <16C77.H>
#pragma fuses HS,NOWDT,NOPROTECT
#pragma use delay(clock=20000000)
#pragma use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 1
#define compiler __pcm__

#elif defined(__pcb__)
#include <16C56.H>
#pragma fuses HS,NOWDT,NOPROTECT
#pragma use delay(clock=20000000)
#pragma use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18
#define compiler __pcb__

#elif defined(__pch__)
#include <18C452.H>
#pragma fuses HS,NOPROTECT
#pragma use delay(clock=20000000)
#pragma use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#define compiler __pch__
#endif

#pragma id version
#reserve 0x20:0x2f

int CYCLES;    // Global cycles (note upper case)

#inline
void pulse_B0() {
#pragma use fast_io(B)
      output_low(PIN_B0);
      delay_ms(100);
      output_high(PIN_B0);
      delay_ms(100);
#pragma use standard_io(B)
}


#separate
void send_pulses() {
   int cycles;  // Local cycles (note lower case)

   for(cycles=CYCLES;cycles!=0;cycles--) {
      pulse_B0();
   }
}


#pragma zero_ram
main() {

   output_low(PIN_B0);
   output_low(PIN_B1);

   printf("Firmware version is %4X\r\n",version);
   printf("Compiled on %s with compiler version %s\r\n",__date__,compiler);

   printf("Pulsing B0 and B1...");
   for(CYCLES=100;CYCLES<=300;CYCLES+=100) {
      printf("\r\nMajor cycle #%c...",(CYCLES/100)+'0');
      output_low(PIN_B1);
      output_high(PIN_B1);
      send_pulses();
   }
   printf("\r\nAll Done.");

   delay_ms(3);  // Allow UART to finish
}
