///////////////////////////////////////////////////////////////////////////
////   Library for a MicroChip 24LC04B                                 ////
////                                                                   ////
////   init_ext_eeprom();    Call before the other functions are used  ////
////                                                                   ////
////   write_ext_eeprom(a, d);  Write the byte d to the address a      ////
////                                                                   ////
////   d = read_ext_eeprom(a);   Read the byte d from the address a    ////
////                                                                   ////
////   The main program may define eeprom_sda                          ////
////   and eeprom_scl to override the defaults below.                  ////
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

#ifndef EEPROM_SDA

#define EEPROM_SDA  PIN_B7
#define EEPROM_SCL  PIN_B6

#endif

#use i2c(master,sda=EEPROM_SDA, scl=EEPROM_SCL)

#define EEPROM_ADDRESS long int
#define EEPROM_SIZE    512

void init_ext_eeprom() {
   output_float(eeprom_scl);
   output_float(eeprom_sda);
}


void write_ext_eeprom(long int address, byte data) {

   i2c_start();
   i2c_write((0xa0|(byte)(address>>7))&0xfe);
   i2c_write(address);
   i2c_write(data);
   i2c_stop();
   delay_ms(11);
}


byte read_ext_eeprom(long int address) {
   byte data;

   i2c_start();
   i2c_write((0xa0|(byte)(address>>7))&0xfe);
   i2c_write(address);
   i2c_start();
   i2c_write((0xa0|(byte)(address>>7))|1);
   data=i2c_read(0);
   i2c_stop();
   return(data);
}

