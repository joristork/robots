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
///////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <16C74.H>
#fuses NOPROTECT,HS,NOWDT

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
