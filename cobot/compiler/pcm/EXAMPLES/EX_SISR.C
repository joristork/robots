////////////////////////////////////////////////////////////////////////////
////                          EX_SISR.C                                 ////
////                                                                    ////
////  This program shows how to implement a interrupt service           ////
////  routine to buffer up incomming serial data.                       ////
////                                                                    ////
////  If the PIC does not have an RDA interrupt pin B0 may be used      ////
////  with the INT_EXT.                                                 ////
////                                                                    ////
////  Configure the CCS prototype card as follows:                      ////
////      Insert jumpers from: 11 to 8 and 12 to 7.                     ////
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
#fuses  HS,NOWDT,NOPROTECT

#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)


#define BUFFER_SIZE 32
byte buffer[BUFFER_SIZE];
byte next_in = 0;
byte next_out = 0;


#int_rda
void serial_isr() {
   int t;

   buffer[next_in]=getc();
   t=next_in;
   next_in=(next_in+1) % BUFFER_SIZE;
   if(next_in==next_out)
     next_in=t;           // Buffer full !!
}

#define bkbhit (next_in!=next_out)

byte bgetc() {
   byte c;

   while(!bkbhit) ;
   c=buffer[next_out];
   next_out=(next_out+1) % BUFFER_SIZE;
   return(c);
}



main() {

   enable_interrupts(global);
   enable_interrupts(int_rda);

   printf("\r\n\Running...\r\n");

               // The program will delay for 10 seconds and then display
               // any data that came in during the 10 second delay

   do {
      delay_ms(10000);
      printf("\r\nBuffered data => ");
      while(bkbhit)
        putc( bgetc() );
   } while (TRUE);

}
