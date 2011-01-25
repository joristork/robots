/////////////////////////////////////////////////////////////////////////
////                             EX_X10.C                            ////
////                                                                 ////
////  This program interfaces a X10 TW523 unit to RS-232.  This      ////
////  program will accept and send three character codes of the      ////
////  form xyy where x is A-P and yy is 00-1F.                       ////
////  Key codes 00-0F are translated to the key number.              ////
////                                                                 ////
////  A * is sent to indicate transmition was aborted due to         ////
////  a collision.  A > is sent when reception begins to reduce      ////
////  the chance of attempting to transmit during reception.         ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17, 12 to 18.                   ////
////      Connect B0 to TW523 pin 1                                  ////
////              B1              3                                  ////
////              B2              4                                  ////
////              GND             2                                  ////
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

#include <16C84.H>

#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)

#fuses   HS,NOPROTECT,NOWDT

#include <x10.c>
#include <input.c>


main() {
   char house_code;
   byte key_code;

   printf("Online\n\r");

   while (TRUE) {

      if(kbhit()) {
        house_code = getc();
        if((house_code>='A') && (house_code<='P')) {
          putc(house_code);
          key_code=gethex();
          x10_write(house_code,key_code);
          x10_write(house_code,key_code);
        }
      }

      if(x10_data_ready()) {
        putc('>');
        x10_read(&house_code,&key_code);
        printf("%c%2X",house_code,key_code);
      }
   }
}
