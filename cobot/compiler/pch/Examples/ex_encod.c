/////////////////////////////////////////////////////////////////////////
////                          EX_ENCODE.C                            ////
////                                                                 ////
////  This program connects to a 2 bit optical encoder.  The encoder ////
////  input is on pins A0 and A1.  A number is input on port B.      ////
////  The software will output a square wave on pin A3 representing  ////
////  the encoder RPM divided by the number on port B.  If there     ////
////  is an error from the encoder pin A2 is made high.              ////
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


#if defined(__PCM__)
#include <16c64.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 1

#elif defined(__PCB__)
#include <16c56.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

byte const sequence[4] = {0,2,3,1};
byte seq_index;
byte error_count;

void resync() {
   int value;

   if(error_count==4)
      output_high(pin_a2);
   else
     error_count++;

   value=input_a() & 3;
   if(value==sequence[0])
     seq_index=0;
   else if (value==sequence[1])
     seq_index=1;
   else if (value==sequence[2])
     seq_index=2;
   else if (value==sequence[3])
     seq_index=3;
}

main() {
   short output;
   long  count,max_count;

   error_count=0;
   resync();

   do {
      while((input_a() & 3)==sequence[seq_index]) ;
      seq_index=(seq_index+1)&3;
      if ((input_a() & 3)!=sequence[seq_index])
        resync();
      else {
         if(error_count==0)
           output_low(pin_a2);
         else
           --error_count;
         max_count=input_b()>>2;
         count++;
         if(count>max_count) {
           output=!output;
           output_bit(pin_a3,output);
           count=0;
         }
      }
   } while (true);
}

