/////////////////////////////////////////////////////////////////////////
////                          EX_WAKUP.C                             ////
////                                                                 ////
////  This example shows how to use the sleep function.  When the    ////
////  button is pushed, the processor goes into sleep mode.  When    ////
////  the button is released, the processor wakes up and continues   ////
////  counting.                                                      ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Jumper from pin 40 to 47.                                   ////
////     See additional connections below.                           ////
////                                                                 ////
////  This example will work with the PCM and PCH compilers.  The    ////
////  following conditional compilation lines are used to include a  ////
////  valid device for each compiler.  Change the device, clock and  ////
////  RS232 pins for your hardware if needed.                        ////
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
#include <16c77.h>
#fuses HS,WDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9200, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18f452.h>
#fuses HS,NOPROTECT
#use delay(clock=4000000)
#use rs232(baud=9200, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

// global flag to send processor into sleep mode
short sleep_mode;

// external interrupt when button pushed and released
#INT_EXT
void ext_isr() {
static short button_pressed=FALSE;

   if(!button_pressed)        // if button action and was not pressed
   {
      button_pressed=TRUE;    // the button is now down
      sleep_mode=TRUE;        // activate sleep
      printf("The processor is now sleeping.\r");
      ext_int_edge(L_TO_H);   // change so interrupts on release
   }
   else                       // if button action and was pressed
   {
      button_pressed=FALSE;   // the button is now up
      sleep_mode=FALSE;       // reset sleep flag
      ext_int_edge(H_TO_L);   // change so interrupts on press
   }
   if(!input(PIN_B0))         // keep button action sychronized wth button flag
      button_pressed=TRUE;
   delay_ms(100);             // debounce button
}

// main program that increments counter every second unless sleeping
main()   {
   long counter;

   sleep_mode=FALSE;          // init sleep flag

   ext_int_edge(H_TO_L);      // init interrupt triggering for button press
   enable_interrupts(INT_EXT);// turn on interrupts
   enable_interrupts(GLOBAL);

   printf("\n\n");

   counter=0;                 // reset the counter
   while(TRUE)
   {
      if(sleep_mode)          // if sleep flag set
         sleep();             // make processor sleep
      printf("The count value is:  %5ld     \r",counter);
      counter++;              // display count value and increment
      delay_ms(1000);         // every second
   }
}
