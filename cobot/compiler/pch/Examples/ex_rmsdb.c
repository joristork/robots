/////////////////////////////////////////////////////////////////////////
////                            EX_RMSDB.C                           ////
////                                                                 ////
////  This program displays the RMS voltage and the dB level of the  ////
////  input voltage.  NOTE: The input voltage waveform must be       ////
////  between 0V and 5V.                                             ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Connect pin 16 to the voltage source.                       ////
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
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18c452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#include <math.h>


main() {
   const long NUM_DATA_POINTS = 3000;
   long i;
   int value;
   float voltage;

   printf("Sampling:\n");

   setup_port_a( ALL_ANALOG );
   setup_adc( ADC_CLOCK_INTERNAL );
   set_adc_channel( 1 );

   while(TRUE)
   {
      voltage = 0;
      for(i=0; i<NUM_DATA_POINTS; i++)
      {
         value = Read_ADC();
         voltage += (float)value*(float)value;
      }
      voltage /=2601.0;
      voltage = sqrt(voltage/(NUM_DATA_POINTS));
      printf("\nInput =  %f V     %f dB\n", voltage, 20*log10(voltage));
   }
}
