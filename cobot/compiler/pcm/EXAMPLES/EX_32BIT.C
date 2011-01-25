/////////////////////////////////////////////////////////////////////////
////                         EX_32BIT.C                              ////
////                                                                 ////
////  This program shows input, output and standard operations with  ////
////  32 bit long numbers.  I/O is RS232.                            ////
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
#include <MATH.C>

void print_long32 (struct long32 *input);

void main() {
   struct long32  first, second, working;
   char s[20];

   while (TRUE) {
      printf ("\r\n\r\nEnter the first number: ");
      get_string (s,20);
      atol32 (s,&first);

      printf ("\r\nEnter the second number: ");
      get_string (s,20);
      atol32 (s,&second);

      printf ("\r\n\r\nA: ");
      print_long32 (&first);

      printf ("\r\nB: ");
      print_long32 (&second);

      working.hi = first.hi;
      working.lo = first.lo;
      add32 (&working, &second);
      printf ("\r\na +  b = ");
      print_long32 (&working);

      working.hi = first.hi;
      working.lo = first.lo;
      sub32 (&working, &second);
      printf ("\r\na -  b = ");
      print_long32 (&working);

      working.hi = first.hi;
      working.lo = first.lo;
      mul32 (&working, &second);
      printf ("\r\na *  b = ");
      print_long32 (&working);

      working.hi = first.hi;
      working.lo = first.lo;
      div32 (&working, &second);
      printf ("\r\na /  b = ");
      print_long32 (&working);

      rem32 (&first, &second, &working);
      printf ("\r\na modulus  b = ");
      print_long32 (&working);

   }
}

void print_long32 (struct long32 *input) {
   byte i;
   struct long32 divisor, digit, temp, value;

   divisor.hi = 0x3B9A;
   divisor.lo = 0xCA00;
   value.hi = input->hi;
   value.lo = input->lo;
   for(i=0;i<10;++i) {
      digit = value;
      div32 (&digit,&divisor);
      temp = digit;
      mul32 (&temp,&divisor);
      sub32 (&value, &temp);
      putc(digit.lo+'0');
      temp.hi = 0;
      temp.lo = 0x000A;
      div32 (&divisor, &temp);
   }
}


