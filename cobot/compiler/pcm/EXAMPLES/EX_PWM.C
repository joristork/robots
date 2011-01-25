/////////////////////////////////////////////////////////////////////////
////                           EX_PWM.C                              ////
////                                                                 ////
////  This program will show how to use the built in PIC PWM.        ////
////  The program takes an analog input and uses the digital         ////
////  value to set the duty cycle.  The frequency is set by          ////
////  the user over the RS-232.                                      ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17, 12 to 18                    ////
////      Connect a scope to pin 3 (C2)                              ////
////      Connect 9 to 15 (pot)                                      ////
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

#use Delay(Clock=10000000)

#use RS232(Baud=9600,Xmit=PIN_C6,Rcv=PIN_C7,brgh1ok)

main() {
   char selection;
   byte value;


   printf("\r\nFrequency:\r\n");
   printf("    1) 19.5 khz\r\n");
   printf("    2) 4.9 khz\r\n");
   printf("    3) 1.2 khz\r\n");

   do {
     selection=getc();
   } while((selection<'1')||(selection>'3'));


   setup_ccp1(CCP_PWM);   // Configure CCP1 as a PWM

                    // The cycle time will be (1/clock)*4*t2div*(period+1)
	            // In this program clock=10000000 and period=127 (below)
                    // For the three possible selections the cycle time is:
		    //     (1/10000000)*4*1*128 =  51.2 us or 19.5 khz
		    //     (1/10000000)*4*4*128 = 204.8 us or 4.9 khz
		    //     (1/10000000)*4*16*128= 819.2 us or 1.2 khz

   switch(selection) {
     case '1' : setup_timer_2(T2_DIV_BY_1, 127, 1);
                break;
     case '2' : setup_timer_2(T2_DIV_BY_4, 127, 1);
                break;
     case '3' : setup_timer_2(T2_DIV_BY_16, 127, 1);
                break;
   }



  setup_port_a(ALL_ANALOG);
  setup_adc(adc_clock_internal);
  set_adc_channel( 0 );
  printf("%c\r\n",selection);

  while( TRUE ) {
    value=read_adc();

    printf("%2X\r",value);

    set_pwm1_duty(value);          // This sets the time the pulse is
                                   // high each cycle.  We use the A/D
                                   // input to make a easy demo.
                                   // the high time will be:
                                   //  if value is LONG INT:
				   //    value*(1/clock)*t2div
                                   //  if value is INT:
                                   //    value*4*(1/clock)*t2div
				   // for example a value of 30 and t2div
                                   // of 1 the high time is 12us
                                   // WARNING:  A value to high or low will
                                   //           prevent the output from
                                   //           changing.
  }

}
