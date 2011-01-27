///////////////////////////////////////////////////////////////////////////
////   Library for a 24C128 serial EEPROM                              ////
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

#ifndef EEPROM_SDA

#define EEPROM_SDA  PIN_C4
#define EEPROM_SCL  PIN_C3

#endif

#define hi(x)  (*(&x+1))

#use i2c(master,sda=EEPROM_SDA, scl=EEPROM_SCL)

#define EEPROM_ADDRESS long int
#define EEPROM_SIZE   16384

void init_ext_eeprom()
{
    int i;

   output_float(eeprom_scl);
   output_float(eeprom_sda);
   delay_us(4);
   for (i=0;i<3;i++) {
     i2c_start();
     i2c_write(0xa0);
     i2c_write(0xff);
     i2c_write(0xff);
     if(i==2)
       i2c_write(0x2);
     else
       i2c_write(0x2+i);
     i2c_stop();
  }
}


void write_ext_eeprom(long int address, byte data)
{
   i2c_start();
   i2c_write(((0xa0)|(hi(address)>>5))&0xfe);
   i2c_write(hi(address)&0x3f);
   i2c_write(address);
   i2c_write(data);
   i2c_stop();
   delay_ms(10);
}


byte read_ext_eeprom(long int address) {
   byte data;

   i2c_start();
   i2c_write(((0xa0)|(hi(address)>>5))&0xfe);
   i2c_write(hi(address)&0x3f);
   i2c_write(address);
   i2c_start();
   i2c_write((0xa1)|(hi(address)>>5));
   data=i2c_read(0);
   i2c_stop();
   return(data);
}

