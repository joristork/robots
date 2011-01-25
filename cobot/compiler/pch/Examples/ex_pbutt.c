/////////////////////////////////////////////////////////////////////////
////                          EX_PBUTT.C                             ////
////  This example shows how to manage the port B interrupt on pin   ////  
////  change.                                                        ////
////                                                                 ////
////  This example will work with the PCB, PCM and PCH compilers.    ////
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
#fuses HS,NOWDT,NOPROTECT,NOLVP
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCM__)
#include <16F877.h>
//#device ICD=TRUE
#fuses HS,NOWDT,NOPROTECT,NOLVP
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT,NOVLP
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#define LOWTOHIGH TRUE
#define HIGHTOLOW FALSE
#byte PORTB=0x06

short int dbutton4,dbutton5,dbutton6,dbutton7;

#int_rb
void detect_rb_change() {
   int current;
   static int last=0;

   set_tris_b(0xF0);
   current=PORTB;

   #if LOWTOHIGH
   if ((!bit_test(last,4))&&(bit_test(current,4))) {dbutton4=1;}
   if ((!bit_test(last,5))&&(bit_test(current,5))) {dbutton5=1;}
   if ((!bit_test(last,6))&&(bit_test(current,6))) {dbutton6=1;}
   if ((!bit_test(last,7))&&(bit_test(current,7))) {dbutton7=1;}
   #elif HIGHTOLOW
   if ((!bit_test(current,4))&&(bit_test(last,4))) {dbutton4=1;}
   if ((!bit_test(current,5))&&(bit_test(last,5))) {dbutton5=1;}
   if ((!bit_test(current,6))&&(bit_test(last,6))) {dbutton6=1;}
   if ((!bit_test(current,7))&&(bit_test(last,7))) {dbutton7=1;}
   #endif

   last=current;
}

void clear_delta() {
   dbutton4=0;
   dbutton5=0;
   dbutton6=0;
   dbutton7=0;
}

void main() {
   clear_delta();

   enable_interrupts(INT_RB);
   enable_interrupts(GLOBAL);

   output_low(PIN_B0);
   while (TRUE) {
      if(dbutton4) {
        output_high(PIN_B0);
        dbutton4=FALSE;
      }
      if(dbutton5) {
        output_low(PIN_B0);
        dbutton5=FALSE;
      }
   }
}
