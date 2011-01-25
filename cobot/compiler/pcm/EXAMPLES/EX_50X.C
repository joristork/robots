/////////////////////////////////////////////////////////////////////////
////                             EX_50x.C                            ////
////  This program shows how to use the pins on a PIC12C508/9 for    ////
////  general I/O.  When run, the program will run up and down the   ////
////  LEDs.  If you hold down the push button, the program will      ////
////  reverse its direction.                                         ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////               12C508 pin    Protoboard                          ////
////                  1           29   +5                            ////
////                  2           46   LED 5                         ////
////                  3           45   LED 4                         ////
////                  4           40   Push Button                   ////
////                  5           44   LED 3                         ////
////                  6           43   LED 2                         ////
////                  7           42   LED 1                         ////
////                  8           27   gnd                           ////
////                                                                 ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <12C508.H>

#fuses INTRC, NOWDT, NOPROTECT, NOMCLR
#use delay(clock=10000000)

void cycle_forward (void)
{
   output_low (PIN_B5);
   output_high (PIN_B0);
   delay_ms (70);
   output_low (PIN_B0);
   output_high (PIN_B1);
   delay_ms (70);
   output_low (PIN_B1);
   output_high (PIN_B2);
   delay_ms (70);
   output_low (PIN_B2);
   output_high (PIN_B4);
   delay_ms (70);
   output_low (PIN_B4);
   output_high (PIN_B5);
   delay_ms (70);
}

void cycle_backward (void)
{
   output_low (PIN_B0);
   output_high (PIN_B5);
   delay_ms (70);
   output_low (PIN_B5);
   output_high (PIN_B4);
   delay_ms (70);
   output_low (PIN_B4);
   output_high (PIN_B2);
   delay_ms (70);
   output_low (PIN_B2);
   output_high (PIN_B1);
   delay_ms (70);
   output_low (PIN_B1);
   output_high (PIN_B0);
   delay_ms (70);
}

main() {
   setup_counters (RTCC_INTERNAL, DISABLE_WAKEUP_ON_CHANGE );
                               // See .h file for other special
                               // options for setup_counters

   while (TRUE) {
      if (!input (PIN_B3)) 
	 cycle_forward ();
      else
	 cycle_backward ();
   }
}

