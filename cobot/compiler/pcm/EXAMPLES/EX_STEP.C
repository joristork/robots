/////////////////////////////////////////////////////////////////////////
////                        EX_STEP.C                                ////
////                                                                 ////
////  This program interfaces to a stepper motor.  The program will  ////
////  use the RS-232 interface to either control the motor with a    ////
////  analog input, a switch input or by RS-232 command.             ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17, 12 to 18                    ////
////      Connect stepper motor to pins 47-50 (B0-B3)                ////
////      Conenct 40 to 54 (pushbutton)                              ////
////      Connect 9 to 15 (pot)                                      ////
////                                                                 ////
////  For a 40 pin part such as the 16C74 add jumpers from           ////
////  8 to 11, 7 to 12, and change the #USE RS232 to:                ////
////      #use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)             ////
/////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C71.H>

#use Delay(Clock=20000000)

#use RS232(Baud=9600,Xmit=PIN_A3,Rcv=PIN_A2)

#fuses   HS,NOPROTECT,NOWDT

#include <input.c>

#byte port_b = 6

#define FOUR_PHASE TRUE

#ifdef FOUR_PHASE

byte const POSITIONS[4] = {0b0101,
                           0b1001,
                           0b1010,
                           0b0110};
#else
byte const POSITIONS[8] = {0b0101,
                           0b0001,
                           0b1001,
                           0b1000,
                           0b1010,
                           0b0010,
                           0b0110,
                           0b0100};
#endif


drive_stepper(byte speed, char dir, byte steps) {
   static byte stepper_state = 0;
   byte i;

   for(i=0;i<steps;++i) {
     delay_ms(speed);
     set_tris_b(0xf0);
     port_b = POSITIONS[ stepper_state ];
     if(dir!='R')
       stepper_state=(stepper_state+1)&(sizeof(POSITIONS)-1);
     else
       stepper_state=(stepper_state-1)&(sizeof(POSITIONS)-1);
   }
}

use_pot() {
  byte value;

  setup_adc(adc_clock_internal);
  set_adc_channel( 1 );
  printf("\r\n");

  while( TRUE ) {
    value=read_adc();
    printf("%2X\r",value);
    if(value<0x80)
       drive_stepper(value,'R',8);
    else if(value>0x80)
       drive_stepper(128-(value-128),'F',8);
  }

}


use_switch(byte speed, char dir) {

   byte steps;

   printf("\n\rSteps per press: ");
   steps = gethex();

   while(true) {
      while(input(PIN_B7)) ;
      drive_stepper(speed,dir,steps);
      while(!input(PIN_B7)) ;
      delay_ms(100);
   }
}


main() {

   byte speed,steps;
   char dir;

   setup_port_a(RA0_RA1_ANALOG);

   while (TRUE) {
       printf("\n\rSpeed (hex): ");
       speed = gethex();

       if(speed==0)
          use_pot();

       printf("\n\rDirection (F,R): ");
       dir=getc()|0x20;
       putc(dir);

       printf("\n\rSteps (hex): ");
       steps = gethex();

       if(steps==0)
          use_switch(speed,dir);

       drive_stepper(speed,dir,steps);
   }

}
