/////////////////////////////////////////////////////////////////////////
////                          EX_STISR.C                             ////
////                                                                 ////
////  This program shows how to implement an interrupt service       ////
////  routine to buffer up outgoing serial data.                     ////
////                                                                 ////
////  Configure the CCS prototype card as described below.           ////
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


#define T_BUFFER_SIZE 64
byte t_buffer[T_BUFFER_SIZE];
byte t_next_in = 0;
byte t_next_out = 0;


#int_tbe
void serial_isr() {
   putc(t_buffer[t_next_out]);
   t_next_out=(t_next_out+1) % T_BUFFER_SIZE;
   if(t_next_in==t_next_out)
     disable_interrupts(int_tbe);
}

void bputc(char c) {
   short restart;
   int ni;

   restart=t_next_in==t_next_out;
   t_buffer[t_next_in]=c;
   ni=(t_next_in+1) % T_BUFFER_SIZE;
   while(ni==t_next_out);
   t_next_in=ni;
   if(restart)
     enable_interrupts(int_tbe);
}



main() {

   enable_interrupts(global);

   printf(bputc,"\r\n\Running...\r\n");

   do {
      delay_ms(2000);
      printf(bputc,"This is buffered data\r\n");
   } while (TRUE);

}

