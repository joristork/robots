/////////////////////////////////////////////////////////////////////////
////                          EX_INTEE.C                             ////
////                                                                 ////
////  This program will read and write to the '83 or '84 internal    ////
////  EEPROM.                                                        ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18.                ////
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

#include <16C84.H>
#fuses  HS,NOWDT,NOPROTECT

#use delay(clock=4000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)

#include <INPUT.C>


main() {

   byte i,j,address,value;

   do {
      printf("\r\n\nEEPROM:\r\n");         // Display contents of entire
      for(i=0; i<=3; ++i) {                // EEPROM in hex
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
