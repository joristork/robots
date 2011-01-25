///////////////////////////////////////////////////////////////////////////
////                         EX_TEMP.C                                 ////
////                                                                   ////
////   Reads temperature using the DS1621 and sends it over the RS232  ////
////                                                                   ////
////                                                                   ////
///////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C74.H>
#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)

#include <ds1621.c>

main() {
   byte value;

   init_temp();

   do {
      value = read_temp();
      printf("%u\r\n",value);
      delay_ms(1000);
   } while (TRUE);
}
