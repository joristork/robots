/////////////////////////////////////////////////////////////////////////
////                         EX_EXPIO.C                               ////
////                                                                  ////
////     This program shows how to use the 74165.C and 74595.C        ////
////  libraries for extended input and output.                        ////
////                                                                  ////
////     When button 1 is pushed, LED 1 will toggle green.  Button    ////
////  2 will toggle LED 2.  However, when both buttons are pushed,    ////
////  LED 3 will toggle green.                                        ////
////                                                                  ////
////  Configure the CCS prototype card as follows:                    ////
////               74165 pin    Protoboard                            ////
////                  11           40   Button 1                      ////
////                  12           41   Button 2                      ////
////                  13           27   gnd                           ////
////                  14           27   gnd                           ////
////                  15           27   gnd                           ////
////                  3            27   gnd                           ////
////                  4            27   gnd                           ////
////                  5            27   gnd                           ////
////                  6            27   gnd                           ////
////                  8            27   gnd                           ////
////                  16           28   +5V                           ////
////                  9            52   B5                            ////
////                  1            50   B3                            ////
////                  2            51   B4                            ////
////                                                                  ////
////               74595 pin    Protoboard                            ////
////                  15           42   LED 1                         ////
////                  1            43   LED 2                         ////
////                  2            44   LED 3                         ////
////                  3            27   gnd                           ////
////                  4            27   gnd                           ////
////                  5            27   gnd                           ////
////                  6            27   gnd                           ////
////                  7            27   gnd                           ////
////                  8            27   gnd                           ////
////                  13           27   gnd                           ////
////                  16           28   +5V                           ////
////                  10           28   +5V                           ////
////                  14           49   B2                            ////
////                  11           48   B1                            ////
////                  12           47   B0                            ////
//////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services          ////
//// This source code may only be used by licensed users of the CCS C ////
//// compiler.  This source code may only be distributed to other     ////
//// licensed users of the CCS C compiler.  No other use, reproduction////
//// or distribution is permitted without written permission.         ////
//// Derivative programs created using this software in object code   ////
//// form are not restricted in any way.                              ////
//////////////////////////////////////////////////////////////////////////

#ifdef __PCB__
#include <16C56.H>
#else
#include <16C74.H>
#endif

#include <74595.C>
#include <74165.C>

main() {
   byte data;

   do {
      read_expanded_inputs (&data);
      
      data |= 0xF8;                           //Force the unused input bits on
      data -= (!(data&0x01)&!(data&0x02))<<2; //Turn on bit 2 it both inputs are
                                              //toggled
      write_expanded_outputs (&data);
   } while (TRUE);
}
