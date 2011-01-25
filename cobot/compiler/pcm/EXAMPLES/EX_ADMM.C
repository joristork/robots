/////////////////////////////////////////////////////////////////////////
////                          EX_ADMM.C                              ////
////                                                                 ////
////  This program displays the min and max of 30 A/D samples over   ////
////  the RS-232 interface.  The process is repeated forever.        ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////      Insert jumpers from: 11 to 8, 12 to 7 and 9 to 15          ////
////      Use the #9 POT to vary the voltage.                        ////
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
#fuses   HS,NOPROTECT,NOWDT

#use delay(clock=10000000)
#use rs232(baud=9600,xmit=PIN_C6,rcv=PIN_C7)

main() {

   int i,value,min,max;

   printf("Sampling:");

   setup_port_a( ALL_ANALOG );
   setup_adc( ADC_CLOCK_INTERNAL );
   set_adc_channel( 0 );

   do {
      min=255;
      max=0;
      for(i=0;i<=30;++i) {
         delay_ms(100);
         value = Read_ADC();
         if(value<min)
            min=value;
         if(value>max)
            max=value;
      }
      printf("\n\rMin: %2X  Max: %2X\r\n",min,max);

   } while (TRUE);

}
