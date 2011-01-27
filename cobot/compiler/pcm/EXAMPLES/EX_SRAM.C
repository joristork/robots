/////////////////////////////////////////////////////////////////////////
////                          EX_SRAM.C                              ////
////                                                                 ////
////  This program uses the PCF8570.C SRAM driver to read and write  ////
////  to an external (2 wire) serial SRAM.                           ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18.                ////
////              PCF8570      Protoboard                            ////
////                  1            27   gnd                          ////
////                  2            27   gnd                          ////
////                  3            27   gnd                          ////
////                  4            27   gnd                          ////
////                  5            54   B7                           ////
////                  6            53   B6                           ////
////                  7            27   gnd                          ////
////                  8            28   +5V                          ////
////                                                                 ////
//// The 68HC68R1.C or 68HC68R2.C drivers may be used in place of    ////
//// the PCF8570.C driver.                                           ////
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
#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)
#else
#include <16C74.H>
#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)
#endif

#fuses   HS,NOPROTECT,NOWDT

#include <INPUT.C>

#include <PCF8570.C>


main() {

   byte value,cmd;
   byte address;

   do {
      do {
         printf("\r\nRead or Write: ");
         cmd=getc();
         cmd=toupper(cmd);
         putc(cmd);
      } while ( (cmd!='R') && (cmd!='W') );

      printf("\n\rLocation: ");

      address = gethex();

      if(cmd=='R')
         printf("\r\nValue: %X\r\n",READ_EXT_SRAM( address ) );

      if(cmd=='W') {
         printf("\r\nNew value: ");
         value = gethex();
         printf("\n\r");
         WRITE_EXT_SRAM( address, value );
      }
   } while (TRUE);

}
