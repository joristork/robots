/////////////////////////////////////////////////////////////////////////
////                         EX_TGETC.C                              ////
////                                                                 ////
////  This program echoes the key presses of the user and times-     ////
////  out after a specified amount of time.                          ////
////                                                                 ////
////  Configure the CCS prototype card as described below.           ////
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
#include <16f877.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#include <input.c>

#define  KEYHIT_DELAY   500     // in milliseconds


char timed_getc() {
   long timeout;
   char retval;

   timeout=0;
   while(!kbhit() && (++timeout< (KEYHIT_DELAY*100)))
      delay_us(10);
   if(kbhit())
      retval = getc();
   else
      retval = 0;
   return(retval);
}

void main()
{
   int status;
   char value;

   while(TRUE)
   {
      status=1;
      printf("\nStart typing:\n");
      while(!kbhit());

      while(status==1)
      {
         value=timed_getc();
         if(value==0)
            status=0;
         else
         {
            status=1;
            putc(value);
         }
      }
      printf("\nToo slow!\n");
   }
}
