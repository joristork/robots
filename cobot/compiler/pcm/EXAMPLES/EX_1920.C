/////////////////////////////////////////////////////////////////////////
////                             EX_1920.C                           ////
////                                                                 ////
////  This program interfaces a Dallas DS1920 touch memory device.   ////
////  It will display the temperature reading of the device it       ////
////  "touches" with pin B0.                                         ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 8 to 11, 7 to 12                      ////
////      Connect pin B0 (47) through a 4.7K resistor to 5V (28)     ////
////      Connect the touch device to ground and then "Touch" to B0  ////
////                                                                 ////
////  The device requires 2 seconds of contact to charge enough power////
////  for the temperature conversion.                                ////
////                                                                 ////
////////////////////////////////////////////////////////////////////////////
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

#fuses   HS,NOPROTECT,NOWDT

#include <touch.c>
#include <string.h>
#include <stdlib.h>

main() {
   byte buffer[2];

   printf("\r\nTemperature requires atleast 2 seconds of contact.");
   printf("\r\nWaiting for a touch device...\r\n");

   while (TRUE) {
     if(touch_present()) {
        touch_write_byte(0xCC);
        touch_write_byte (0x44);
        output_high(TOUCH_PIN);
        delay_ms(2000);

        touch_present();
        touch_write_byte(0xCC);
        touch_write_byte (0xBE);
        buffer[0] = touch_read_byte();
        buffer[1] = touch_read_byte();

        printf ("\r\nTemperature: %c%3.1f C", (buffer[1])?'-':' ', (float)buffer[0]/2);
        delay_ms (1000);
     }
  }
}
