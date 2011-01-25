/////////////////////////////////////////////////////////////////////////
////                          EX_SRAM.C                              ////
////                                                                 ////
////  This program uses the PCF8570.C SRAM driver to read and write  ////
////  to an external (2 wire) serial SRAM.                           ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Insert jumpers from:                                        ////
////           PCF8570      Protoboard                               ////
////               1            27   gnd                             ////
////               2            27   gnd                             ////
////               3            27   gnd                             ////
////               4            27   gnd                             ////
////               5            54   B7                              ////
////               6            53   B6                              ////
////               7            27   gnd                             ////
////               8            28   +5V                             ////
////     See additional connections below.                           ////
////                                                                 ////
////  The 68HC68R1.C or 68HC68R2.C drivers may be used in place of   ////
////  the PCF8570.C driver.                                          ////
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


#if defined(__PCB__)
#include <16c56.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCM__)
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

#include <input.c>
#include <pcf8570.c>


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
