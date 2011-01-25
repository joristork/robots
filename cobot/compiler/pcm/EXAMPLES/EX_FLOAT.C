/////////////////////////////////////////////////////////////////////////
////                          EX_FLOAT.C                             ////
////                                                                 ////
////  This program shows input, output and standard operations with  ////
////  floating piont numbers.  I/O is RS232.                         ////
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

#include <16c74.H>
#use delay(clock=10000000)
#use rs232(baud=9600 ,xmit=PIN_c6,rcv=PIN_c7)

#include <input.c>
#include <stdlib.h>


float get_float() {
  char s[20];
  float f;

  get_string(s,20);
  f=atof(s);
  return(f);
}


void main() {
	long int l;
        float a, b;

	while(true) {
	 printf("\r\nEnter first number: ");
         a=get_float();

         printf("\r\nEnter second number: ");
	 b=get_float();

         printf("\r\n\nA= %E\r\n", a);
         printf("B= %E\r\n", b);

         printf("\r\na + b = %E", a + b);
         printf("\r\na - b = %E", a - b);
         printf("\r\na * b = %E", a * b);
         printf("\r\na / b = %E\r\n", a / b);
	 if(a <= b)
            printf("a<=b, ");
         else
            printf("a>b, ");
         if(a < b)
            printf("a<b, ");
         else
            printf("a>=b, ");
	 if(a == b)
            printf("a==b\r\n");
         else
            printf("a!=b\r\n");
	 l = (long int)a;
         printf("\r\n(long)a = %lu, ", l);
         a = (float)l;
         printf("\r\n(float)(long)a = %E\r\n", a);
       }
}
