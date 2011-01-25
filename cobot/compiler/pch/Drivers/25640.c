///////////////////////////////////////////////////////////////////////////
////   Library for a MicroChip 25C160                                 ////
////                                                                   ////
////   init_ext_eeprom();    Call before the other functions are used  ////
////                                                                   ////
////   write_ext_eeprom(a, d);  Write the byte d to the address a      ////
////                                                                   ////
////   d = read_ext_eeprom(a);   Read the byte d from the address a    ////
////                                                                   ////
////   The main program may define eeprom_select, eeprom_di, eeprom_do ////
////   and eeprom_clk to override the defaults below.                  ////
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

#ifndef EEPROM_SELECT

#define EEPROM_SELECT PIN_B1
#define EEPROM_CLK    PIN_B0
#define EEPROM_DI     PIN_B5
#define EEPROM_DO     PIN_B4

#endif

#define EEPROM_ADDRESS long int
#define EEPROM_SIZE    8192

void init_ext_eeprom() {
   output_high(EEPROM_SELECT);
   output_low(EEPROM_DI);
   output_low(EEPROM_CLK);
 }


void write_ext_eeprom(EEPROM_ADDRESS address, byte data) {
   byte cmd[4];
   byte i;
   byte wren;
   byte rdsr;
   wren=0x06;
   rdsr=0x05;
   cmd[0]=data;
   cmd[1]=address;
   cmd[2]=(address>>8);
   cmd[3]=0x02;
  
   output_low(EEPROM_SELECT);
   for(i=0;i<8;i++)
   {
      output_bit(EEPROM_DI, shift_left(&wren,1,0));
      output_high(EEPROM_CLK);
      output_low(EEPROM_CLK);
   }
   output_high(EEPROM_SELECT);
   output_low(EEPROM_SELECT);
   for(i=0;i<32;i++)
   {
      output_bit(EEPROM_DI, shift_left(cmd,4,0));  
      output_high(EEPROM_CLK);
      output_low(EEPROM_CLK);
   } 
   output_high(EEPROM_SELECT);
   delay_ms(6);
}

byte read_ext_eeprom(EEPROM_ADDRESS address) {
   byte cmd[3];
   byte i,data;
   cmd[0]=address;
   cmd[1]=(address>>8);
   cmd[2]=0x03;
   output_low(EEPROM_SELECT);
   for(i=0;i<24;i++)
   {
      output_bit(EEPROM_DI, shift_left(cmd,3,0));
      output_high(EEPROM_CLK);
      output_low(EEPROM_CLK);
   }
   for(i=0;i<8;i++)
   {
      shift_left(&data,1,input(EEPROM_DO));
      output_high(EEPROM_CLK);
      output_low(EEPROM_CLK);  

   }
   output_high(EEPROM_SELECT);
   return(data);
}
