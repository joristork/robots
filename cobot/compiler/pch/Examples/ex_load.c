/////////////////////////////////////////////////////////////////////////
////                          EX_LOAD.C                              ////
////                                                                 ////
////  This program uses the loader driver to show how you can        ////
////  read and write to an internal program memory.                  ////
////                                                                 ////
////  The LOADER.C will take an Intel 8-bit Hex file over RS232      ////
////  and modify the flash program memory with the new code.         ////
////                                                                 ////
////  After each good line, the loader sends an ACK character.  The  ////
////  driver uses XON/XOFF flow control.  Also, any buffer on the PC ////
////  UART must be turned off, or to its lowest setting, otherwise it////
////  will miss data.                                                ////
////                                                                 ////
////  A program such as Hyper-Term may be used to simply transmit the////
////  .HEX file output from the compiler.                            ////
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
#include <16f877.h>
#fuses HS,NOWDT,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7, ERRORS)  // Jumpers: 8 to 11, 7 to 12

#elif defined(__PCH__)
#include <18f452.h>
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7, ERRORS)  // Jumpers: 8 to 11, 7 to 12
#endif

#include <loader.c>


void main()
{
   //clears out the rs232 buffer
   delay_ms (1000);
   while (kbhit())
      getc();

   do {
      printf("\r\nSoftware Version A!\r\n");
      printf("Press L to download new software.\r\n");
   } while (getc()!='L');
   
   load_program ();
}
