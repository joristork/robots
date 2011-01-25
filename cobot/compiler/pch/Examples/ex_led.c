/////////////////////////////////////////////////////////////////////////
////                           EX_LED.C                              ////
////                                                                 ////
////  This program shows how to drive a two digit LED using input    ////
////  from an RS-232 port.                                           ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     LED seg f    Pin 47 (B0)                                    ////
////     LED seg a    Pin 48 (B1)                                    ////
////     LED seg e    Pin 49 (B2)                                    ////
////     LED seg c    Pin 50 (B3)                                    ////
////     LED seg dp   Pin 51 (B4)                                    ////
////     LED seg d    Pin 52 (B5)                                    ////
////     LED seg b    Pin 53 (B6)                                    ////
////     LED seg g    Pin 54 (B7)                                    ////
////     LED Anode 1  Pin 16 (A0)                                    ////
////     LED Anode 2  Pin 15 (A1)                                    ////
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




#if defined(__PCH__)
#include "C:\pch\Devices\18f452.h"
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif


#byte port_b=6


void wait() {           // This function waits for either ~2ms or until a
  int countdown;        // event happens (in this case a rs232 character)

  countdown=200;
  while((--countdown!=0)&&!kbhit())
    delay_us(10);
}


void display( char one, char two) {
   output_high(PIN_B1);
   wait();
   output_low(PIN_B1);
   output_high(PIN_B3);
   wait();
   output_low(PIN_B3);
}


void main() {
  char pos1,pos2;

  set_tris_b(0);
  port_b=0;

  pos1='0';
  pos2='1';

  while(TRUE) {
    char c;
    display(pos1,pos2);
    if(c = getc()) {
       printf("%c \n\r", c);
    }
  }

}
