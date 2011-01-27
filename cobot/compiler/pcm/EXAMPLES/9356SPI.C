///////////////////////////////////////////////////////////////////////////
////   Library for a MicroChip 93C56 configured for a x8 org           ////
////   Uses hardware SSP unit.                                         ////
////                                                                   ////
////   init_ext_eeprom();    Call before the other functions are used  ////
////                                                                   ////
////   write_ext_eeprom(a, d);  Write the byte d to the address a      ////
////                                                                   ////
////   d = read_ext_eeprom(a);   Read the byte d from the address a    ////
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

#define EEPROM_SELECT PIN_B0
#define EEPROM_DI     PIN_C5
#define EEPROM_DO     PIN_C4
#define EEPROM_CLK    PIN_C3

#define EEPROM_ADDRESS byte
#define EEPROM_SIZE    256


void init_ext_eeprom() {
   short int i;

   output_low(EEPROM_DI);
   output_low(EEPROM_CLK);
   output_low(EEPROM_SELECT);
   i=input(EEPROM_DO);

   setup_spi(SPI_MASTER | SPI_H_TO_L | SPI_CLK_DIV_16);

   output_high(EEPROM_SELECT);
   spi_write(0x9);
   spi_write(0x80);
   output_low(EEPROM_SELECT);
}


void write_ext_eeprom(EEPROM_ADDRESS address, byte data) {

   output_high(EEPROM_SELECT);
   spi_write(0xa);
   spi_write(address);
   spi_write(data);
   output_low(EEPROM_SELECT);
   delay_ms(11);
}


byte read_ext_eeprom(EEPROM_ADDRESS address) {
   byte data;

   rotate_left(&address,1);
   output_high(EEPROM_SELECT);
   spi_write(0x18|(address&1));
   spi_write(address);
   data=spi_read(0);
   output_low(EEPROM_SELECT);

   return(data);
}

