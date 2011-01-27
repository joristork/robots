/////////////////////////////////////////////////////////////////////////
////                             EX_SQW.C                            ////
////                                                                 ////
////  This program displays a message over the RS-232 and waits for  ////
////  any keypress to continue.  The program will then begin a 1khz  ////
////  square wave over I/O pin B0.                                   ////
////                                                                 ////
////  Change both delay_us to delay_ms to make the frequency 1 hz.   ////
////  This will be more visable on a LED.                            ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17, 12 to 18 and 42 to 47       ////
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

#fuses HS,NOWDT,NOPROTECT

#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)


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
