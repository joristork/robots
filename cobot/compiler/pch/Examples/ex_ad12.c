/////////////////////////////////////////////////////////////////////////
////                          EX_AD12.C                              ////
////                                                                 ////
////  This program will read two A/D channels and display the        ////
////  results as both a voltage and raw hex number over the RS-232.  ////
////  A reading is taken every second. This can be done using either ////
////  the LTC1298 chip or the MCP3208 chip.                          ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////                                                                 ////
////     Using the LTC1298:                                          ////
////     LTC1298    Protoboard                                       ////
////         1           50  B3                                      ////
////         2            9  analog-1                                ////
////         3           10  analog-2                                ////
////         4           27  gnd                                     ////
////         5           49  B2                                      ////
////         6           48  B1                                      ////
////         7           47  B0                                      ////
////         8           28  +5V                                     ////
////                                                                 ////
////     Using the MCP3208:                                          ////
////     MCP3208    Protoboard                                       ////
////         1            9  analog-1                                ////
////         2           10  analog-2                                ////
////         3..8        Not Connected                               ////
////         9           27  gnd                                     ////
////        10           50  B3                                      ////
////        11           49  B2                                      ////
////        12           48  B1                                      ////
////        13           47  B0                                      ////
////        14           27  gnd                                     ////
////        15           28  +5V                                     ////
////        16           28  +5V                                     ////
////                                                                 ////
////     See additional connections below.                           ////
////                                                                 ////
////  Using the MCP3208 requires changing the #include <LTC1298.C>   ////
////  to #include <MCP3208.C>                                        ////
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
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#include <ltc1298.c>


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
