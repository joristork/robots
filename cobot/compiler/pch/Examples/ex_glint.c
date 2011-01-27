/////////////////////////////////////////////////////////////////////////
////                         EX_GLINT.C                              ////
////                                                                 ////
////  This example shows how to use the global interrupt to execute  ////
////  interrupts quickly and efficently.  It also shows how to use   ////
////  the #asm and #endasm directives.                               ////
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
#fuses HS,WDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)  // Jumpers: 11 to 17, 12 to 18

#elif defined(__PCM__)
#include <16c77.h>
#fuses HS,WDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9200, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9200, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

long counter;

int save_w;
#locate save_w=0x7f
int save_status;
#locate save_status=0x20

#byte status = 3
#bit zero_flag = status.2
#bit t0if = 0xb.2

#INT_GLOBAL
void isr()  {
   #asm
   //store current state of processor
   MOVWF save_w
   SWAPF status,W
   BCF   status,5
   BCF   status,6
   MOVWF save_status
   // Save anything else your code may change
   // You need to do something with PCLATH if you have GOTOs
  
   // remember to check to see what interrupt fired if using more than one!!

   // code for isr
   BCF   t0if
   INCF  counter,F
   BTFSC zero_flag
   INCF  (&counter+1),F

   // restore processor and return from interrupt
   SWAPF save_status,W
   MOVWF status
   SWAPF save_w,F
   SWAPF save_w,W
   #endasm
}

main()   {

   printf("\nStarting the counter.\n\n");
   counter = 0;

   setup_counters(RTCC_INTERNAL,RTCC_DIV_32);
   enable_interrupts(INT_RTCC);
   enable_interrupts(GLOBAL);

   while(TRUE)
   {
      printf("The counter value is currently:  %5lu\r", counter);
      delay_ms(1000);
   }
}
