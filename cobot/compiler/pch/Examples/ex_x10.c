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
////     Connect B0 to TW523 pin 1                                   ////
////             B1              3                                   ////
////             B2              4                                   ////
////             GND             2                                   ////
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
