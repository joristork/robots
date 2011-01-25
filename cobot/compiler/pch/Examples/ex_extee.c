/////////////////////////////////////////////////////////////////////////
////                          EX_EXTEE.C                             ////
////                                                                 ////
////  This program uses the 24xx or 93xx external EEPROM drivers to  ////
////  read and write to an external serial EEPROM.                   ////
////                                                                 ////
////  Change the #include <9356.C> to any of the other drivers to    ////
////  test other parts.  Note each driver defines EEPROM_ADDRESS     ////
////  indicate 8 or 16 bit addresses.                                ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     EEPROM connections will vary depending on the device,       ////
////     for the 93C56 use the 9356.C driver and the default         ////
////     connections:                                                ////
////               9356 pin    Protoboard                            ////
////                  1            54   B7                           ////
////                  2            53   B6                           ////
////                  3            52   B5                           ////
////                  4            51   B4                           ////
////                  5            27   gnd                          ////
////                  6            27   gnd                          ////
////                  7            27   gnd                          ////
////                  8            28   +5V                          ////
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

#include <input.c>
#include <2402.c>


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
