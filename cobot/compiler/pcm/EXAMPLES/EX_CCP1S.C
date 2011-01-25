////////////////////////////////////////////////////////////////////////////
////                           EX_CCP1S.C                               ////
////                                                                    ////
////  This program will show how to use the built in CCP to             ////
////  generate a single pulse of a predefined time in response          ////
////  to a pushbutton (40).                                             ////
////                                                                    ////
////  Configure the CCS prototype card as follows:                      ////
////      Connect a scope to pin 3 (C2)                                 ////
////      Connect 40 (button) to 47 (B0)                                ////
////////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C74.H>

#use Delay(Clock=20000000)

main() {

   setup_ccp1(CCP_COMPARE_CLR_ON_MATCH);   // Configure CCP1 in COMPARE mode
   setup_timer_1(T1_INTERNAL);             // Set up timer to instruction clk

   while(TRUE) {

       while(input(PIN_B0)) ;    // Wait for keypress


       setup_ccp1(CCP_COMPARE_SET_ON_MATCH); // Configure CCP1 to set
       CCP_1=0;                              // C2 high now
       set_timer1(0);

       CCP_1 = 500;                        // Set high time limit
                                           // to 100 us
                                           // limit is time/(clock/4)
                                           // 500 = .0001*(20000000/4)

       setup_ccp1(CCP_COMPARE_CLR_ON_MATCH); // Configure CCP1 in COMPARE
                                             // mode and to pull pin C2
                                             // low on a match with timer1

       delay_ms(1000);           // Debounce - Permit only one pulse per sec
   }

}
