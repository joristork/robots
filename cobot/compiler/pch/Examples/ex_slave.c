///////////////////////////////////////////////////////////////////////////
////                         EX_SLAVE.C                                ////
////                                                                   ////
////  This program uses the PIC in I2C slave mode to emulate the       ////
////  24LC01 EEPROM. You can write to addresses 00h to 0Fh with it.    ////
////                                                                   ////
////  This program is to be used in conjunction with the ex_extee.c    ////
////  sample.  Use the "#include <2402.C>" or "#include <2401.c>".     ////
////  Only 16 bytes of address space are implemented, however.         ////
////                                                                   ////
////  If using a compiler version before 2.639 add "*0x14 = 0x3E;" to  ////
////  the begining of main(), and add "NOFORCE_SW" as the last         ////
////  parameter in the #use i2c directive.                             ////
////                                                                   ////
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

#use i2c(SLAVE, SDA=PIN_C4, SCL=PIN_C3, address=0xa0)

typedef enum {NOTHING, CONTROL_READ,
              ADDRESS_READ, READ_COMMAND_READ} I2C_STATE;

I2C_STATE fState;
byte address, buffer[0x10];


#INT_SSP
void ssp_interupt ()
{
   byte incoming;

   if (i2c_poll() == FALSE) {
      if (fState == ADDRESS_READ) {  //i2c_poll() returns false on the
         i2c_write (buffer[address]);//interupt receiving the second
         fState = NOTHING;           //command byte for random read operation
      }
   }
   else {
      incoming = i2c_read();

      if (fState == NOTHING){
         fState = CONTROL_READ;
      }
      else if (fState == CONTROL_READ) {
         address = incoming;
         fState = ADDRESS_READ;
      }
      else if (fState == ADDRESS_READ) {
         buffer[address] = incoming;
         fState = NOTHING;
      }
   }
}


void main ()
{
   int i;

   fState = NOTHING;
   address = 0x00;
   for (i=0;i<0x10;i++)
      buffer[i] = 0x00;

   enable_interrupts(GLOBAL);
   enable_interrupts(INT_SSP);

   while (TRUE) {}
}
