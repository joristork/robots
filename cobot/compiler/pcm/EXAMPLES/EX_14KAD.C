//////////////////////////////////////////////////////////////////
////                          EX_14000.C                      ////
////                                                          ////
////  This program displays the filtered and calibrated A/D   ////
////  samples from any analog input pin (chosen by user)      ////
////  over the RS-232 interface.                              ////
////  The process is repeated forever.                        ////
////                                                          ////
////  Configure the CCS prototype card as follows:            ////
////                                                          ////
////      Connect 0.033 uF capacitor between 48 and 27.       ////
////      Insert jumpers from: 11 to 8, 12 to 7               ////
////      Use the #9 POT to vary the voltage.                 ////
////      For A/D sampling, insert a jumper from 9 to:        ////
////               15       for        RA0/AN0                ////
////               16                  RA1/AN1                ////
////               17                  RA2/AN2                ////
////               18                  RA3/AN3                ////
////                1                  RC0/REFA               ////
////               53                  RD3/REFB               ////
////               36                  RD4/AN4                ////
////               37                  RD5/AN5                ////
////               38                  RD6/AN6                ////
////               39                  RD7/AN7                ////
//////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <14000.h>

///#include <cal9717n.h>  // Put your calibration constants here

#fuses   HS,NOPROTECT,NOWDT
#use delay(clock=10000000)
#use rs232(baud=9600,xmit=PIN_C6,rcv=PIN_C7)

#include <14KCAL.c>


main() {

   int source;
   float res;

   setup_adc_ports(ALL_ANALOG);
   setup_adc(CURRENT_34);

   delay_ms(2000);

   do {
      printf("\r\nChoose analog input channel:\r\n");
      printf("\r\n0) RA0/AN0  1) RA1/AN1   2) RA2/AN2  3) RA3/AN3");
      printf("\r\n7) IntTemp  8) RC0/REFA  9) RD3/REFB");
      printf("\r\nA) RD4/AN4  B) RD5/AN5   C) RD6/AN6  D) RD7/AN7");

      source = toupper(getc());
      if (source >= 'A')
         source = source - 'A' + 10;
      else
         source = source - '0';

      res = READ_CALIBRATED_AD(source);
      printf("\r\n\nCalibrated A/D = %E\n", res);

   } while(TRUE);
}
