/////////////////////////////////////////////////////////////////////////
////                          EX_DEC.C                               ////
////                                                                 ////
////  This program shows how to display a LONG INT in  a fixed       ////
////  point format.                                                  ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 8 and 12 to 7.                  ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C74.H>
#fuses  HS,NOWDT,NOPROTECT

#use delay(clock=4000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7,brgh1ok)

#include <INPUT.C>

void print_fp_2(long int value) {
   byte i,digit;
   long int divisor;

   divisor=10000;
   for(i=0;i<5;++i) {
      digit = value/divisor;
      value-=digit*divisor;
      divisor/=10;
      putc(digit+'0');
      if(i==2)
        putc('.');
   }
}

void print_as_volts(long int value) {    // This function shows how to
   byte i,digit;                         // display a fixed point number.
   long int divisor;                     // In this example we assume that
                                         // 0xffff is 5 volts

   divisor=13107;         // 65535 / 5
   for(i=0;i<5;++i) {
      digit = value/divisor;
      value-=digit*divisor;
      divisor/=10;
      putc(digit+'0');
      if(i==0)
        putc('.');
   }
}



main() {
   long int value;

   do {
      printf("\r\n\nHex value: ");
      value=gethex();
      value=value<<8|gethex();

      printf("\r\nAs dollers:  ");
      print_fp_2(value);

      printf("\r\nAs Volts:  ");
      print_as_volts(value);

   } while (TRUE);

}
