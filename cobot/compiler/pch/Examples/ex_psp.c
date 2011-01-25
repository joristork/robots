/////////////////////////////////////////////////////////////////////////
////                           EX_PSP.C                              ////
////                                                                 ////
////  This program demonstrates how the parallel slave port works.   ////
////  This example allows the user to print text from a PC to a PIC. ////
////  To run this example correctly, an IBM parallel printer cable   ////
////  is needed, with the connections shown below.  After installing ////
////  the generic text printer in Windows, text can be printed to    ////
////  the PIC using a text editor.  The text will be displayed via   ////
////  the RS-232.                                                    ////
////                                                                 ////
////  This example also shows how to use banks 2 and 3 in 4 bank     ////
////  parts without going to 16 bit pointers (saving ROM).           ////
////                                                                 ////
////  Configure the CCS prototype card as follows:                   ////
////     Connect pin 23 to 27 and pin 21 to 28.                      ////
////     Connect one end of the parrallel printer cable to the PC.   ////
////     Also make the following connections:                        ////
////          IBM parallel                                           ////
////           cable pin     Protoboard                              ////
////               1           22   E1                               ////
////               2           32   D0                               ////
////               3           33   D1                               ////
////               4           34   D2                               ////
////               5           35   D3                               ////
////               6           36   D4                               ////
////               7           37   D5                               ////
////               8           38   D6                               ////
////               9           39   D7                               ////
////              11            1   C0                               ////
////              12           27   gnd                              ////
////              13           28   +5V                              ////
////              15           28   +5V                              ////
////              18           27   gnd                              ////
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
#error  For PIC18 change read/write _bank to use a buffer
#endif

#define BUSY_LINE    PIN_C0
#define BUFFER_SIZE  96


int next_in = 0;
int next_out = 0;
short data_lost = TRUE;

#int_psp
void psp_isr()  {

   if(psp_overflow())
     data_lost=TRUE;

   if(psp_input_full()) {
      write_bank(2, next_in++, input_D());
      if(next_in == BUFFER_SIZE)
         next_in = 0;
      if(next_in == next_out)
         output_high(BUSY_LINE);
   }
}


main() {

   setup_adc_ports(NO_ANALOGS);
   setup_psp(PSP_ENABLED);
   enable_interrupts(GLOBAL);
   enable_interrupts(INT_PSP);

   output_low(BUSY_LINE);

   printf("Waiting for print data... \r\n\n");

   while(true)
   {
      if(next_in!=next_out)
      {
         putc(read_bank(2,next_out));
         if(++next_out==BUFFER_SIZE)
            next_out=0;
         if(data_lost) {
            printf("\r\nData Lost!!!\r\n");
            data_lost = FALSE;
         }
         output_low(BUSY_LINE);
      }
   }
}

