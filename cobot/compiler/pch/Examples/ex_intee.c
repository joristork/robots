/////////////////////////////////////////////////////////////////////////
////                          EX_INTEE.C                             ////
////                                                                 ////
////  This program will read and write to the '83 or '84 internal    ////
////  EEPROM.                                                        ////
////                                                                 ////
////  Configure the CCS prototype card as described below.           ////
////                                                                 ////
////  This example will work with the PCM and PCH compilers.  The    ////
////  following conditional compilation lines are used to include a  ////
////  valid device for each compiler.  Change the device, clock and  ////
////  RS232 pins for your hardware if needed.                        ////
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
#include <16c84.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#include <input.c>


main() {

   byte i,j,address,value;

   do {
      printf("\r\n\nEEPROM:\r\n");              // Display contents of entire
      for(i=0; i<=3; ++i) {                     // EEPROM in hex
         for(j=0; j<=15; ++j) {
            printf( "%2x ", read_eeprom( i*16+j ) );
         }
         printf("\n\r");
      }
      printf("\r\nLocation to change: ");
      address = gethex();
      printf("\r\nNew value: ");
      value = gethex();

      write_eeprom( address, value );

   } while (TRUE);

}
