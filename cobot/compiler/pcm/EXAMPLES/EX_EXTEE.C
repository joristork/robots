/////////////////////////////////////////////////////////////////////////
////                          EX_EXTEE.C                             ////
////                                                                 ////
////  This program uses the 24xx or 93xx external EEPROM drivers to  ////
////  read and write to an external serial EEPROM.                z  ////
////                                                                 ////
////  Change the #include <9356.C> to any of the other drivers to    ////
////  test other parts.  Note each driver defines EEPROM_ADDRESS     ////
////  indicate 8 or 16 bit addresses.                                ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18.                ////
////      EEPROM connections will vary depending on the device,      ////
////      for the 93C56 use the 9356.C driver and the default        ////
////      connections:                                               ////
////               9356 pin    Protoboard                            ////
////                  1            54   B7                           ////
////                  2            53   B6                           ////
////                  3            52   B5                           ////
////                  4            51   B4                           ////
////                  5            27   gnd                          ////
////                  6            27   gnd                          ////
////                  7            27   gnd                          ////
////                  8            28   +5V                          ////
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

#include <INPUT.C>

#include <2402.C>


main() {

   byte value,cmd;
   EEPROM_ADDRESS address;

   init_ext_eeprom();

   do {
      do {
         printf("\r\nRead or Write: ");
         cmd=getc();
         cmd=toupper(cmd);
         putc(cmd);
      } while ( (cmd!='R') && (cmd!='W') );

      printf("\n\rLocation: ");

#if sizeof(EEPROM_ADDRESS)==1
      address = gethex();
#else
#if EEPROM_SIZE>0xfff
      address = gethex();
#else
      address = gethex1();
#endif
      address = (address<<8)+gethex();
#endif

      if(cmd=='R')
         printf("\r\nValue: %X\r\n",READ_EXT_EEPROM( address ) );

      if(cmd=='W') {
         printf("\r\nNew value: ");
         value = gethex();
         printf("\n\r");
         WRITE_EXT_EEPROM( address, value );
      }
   } while (TRUE);

}
