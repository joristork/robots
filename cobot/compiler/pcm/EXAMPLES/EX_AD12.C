/////////////////////////////////////////////////////////////////////////
////                          EX_AD12.C                              ////
////                                                                 ////
////  This program will read both A/D channels and display the       ////
////  results as both a voltage and raw hex number over the RS-232.  ////
////  A reading is taken every second.                               ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 17, 12 to 18                    ////
////      LTC1298    Protoboard                                      ////
////         1           50  B3                                      ////
////         2            9  analog-1                                ////
////         3           10  analog-2                                ////
////         4           27  gnd                                     ////
////         5           49  B2                                      ////
////         6           48  B1                                      ////
////         7           47  B0                                      ////
////         8           28  +5V                                     ////
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

#ifdef __PCB__
#include <16C56.H>
#else
#include <16C84.H>
#endif

#fuses   HS,NOPROTECT,NOWDT

#use delay(clock=10000000)
#use rs232(baud=9600, xmit=PIN_A3, rcv=PIN_A2)

#include <LTC1298.C>



void display_data( long int data ) {
     char volt_string[6];

     convert_to_volts( data, volt_string );
     printf(volt_string);
     printf(" (%4lX)",data);
}


main() {
   long int value;

   adc_init();

   printf("Sampling:\r\n");

   do {
      delay_ms(1000);

      value = read_analog(0);
      printf("\n\rCh0: ");
      display_data( value );

      value = read_analog(1);
      printf("   Ch1: ");
      display_data( value );

   } while (TRUE);

}
