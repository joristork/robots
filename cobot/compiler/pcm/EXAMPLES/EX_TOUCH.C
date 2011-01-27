/////////////////////////////////////////////////////////////////////////
////                             EX_TOUCH.C                          ////
////                                                                 ////
////  This program interfaces a Dallas DS1991 touch memory device.   ////
////  It will display the family code and ID of any device that      ////
////  "touch"es pin B0.                                              ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17, 12 to 18                    ////
////      Connect pin B0 (47) through a 4.7K resistor to 5V (28)     ////
////      Connect the touch device to ground and then "Touch" to B0  ////
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

#ifdef __PCB__
#include <16C56.H>
#else
#include <16C84.H>
#endif

#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)

#fuses   HS,NOPROTECT,NOWDT

#include <touch.c>


main() {
   byte buffer[8];
   byte i;

   printf("\r\nWaiting for a touch device...\r\n");
   while (TRUE) {
     while(!touch_present()) ;
     delay_ms(200);
     if(touch_present()) {
        touch_write_byte(0x33);
        for(i=0;i<8;++i)
           buffer[i]=touch_read_byte();
        printf("Family: %2X  ID: ",buffer[0]);
        for(i=6;i!=0;--i)
          printf("%2X",buffer[i]);
        printf("\r\n");
        delay_ms(1000);
     }
  }
}
