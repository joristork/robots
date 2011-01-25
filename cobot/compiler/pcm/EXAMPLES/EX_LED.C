/////////////////////////////////////////////////////////////////////////
////                           EX_LED.C                              ////
////                                                                 ////
////  This program shows how to drive a two digit LED using input    ////
////  from an RS-232 port.                                           ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17 and 12 to 18.                ////
////      LED seg f    Pin 47 (B0)                                   ////
////      LED seg a    Pin 48 (B1)                                   ////
////      LED seg e    Pin 49 (B2)                                   ////
////      LED seg c    Pin 50 (B3)                                   ////
////      LED seg dp   Pin 51 (B4)                                   ////
////      LED seg d    Pin 52 (B5)                                   ////
////      LED seg b    Pin 53 (B6)                                   ////
////      LED seg g    Pin 54 (B7)                                   ////
////      LED Anode 1  Pin 16 (A0)                                   ////
////      LED Anode 2  Pin 15 (A1)                                   ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////
#include <16c56.h>
#fuses xt,nowdt,noprotect

#use delay(clock=20000000)
#use rs232(baud=1200,xmit=PIN_A3,rcv=PIN_A2)

#byte port_b=6

byte CONST LED_MAP[10] = {0x90,0xb7,0x19,0x15,0x36,0x54,0x50,0xb5,0,0x24};


void wait() {           // This function waits for either ~2ms or until a
  int countdown;        // event happens (in this case a rs232 character)

  countdown=200;
  while((--countdown!=0)&&!kbhit())
    delay_us(10);
}

void display_segs(char c) {
  if((c>'9')||(C<'0'))
    port_b=0xff;
  else
    port_b=LED_MAP[c-'0'];
}


void display( char one, char two) {
   display_segs(one);
   output_high(PIN_A0);
   wait();
   output_low(PIN_A0);
   display_segs(two);
   output_high(PIN_A1);
   wait();
   output_low(PIN_A1);
}


void main() {
  char pos1,pos2;

  set_tris_b(0);
  port_b=0;

  pos1='0';
  pos2='1';

  while(TRUE) {
    display(pos1,pos2);
    if(kbhit()) {
       pos1=pos2;
       pos2=getc();
    }
  }

}
