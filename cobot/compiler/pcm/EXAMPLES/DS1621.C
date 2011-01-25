///////////////////////////////////////////////////////////////////////////
////   Library for a Dallas 1621 Temperature chip                      ////
////                                                                   ////
////   init_temp();          Call before the other functions are used  ////
////                                                                   ////
////   d = read_temp();      Read the temerature in degrees (0-255)    ////
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


#use i2c(master,sda=PIN_B7, scl=PIN_B6)


void temp_config(byte data) {

   i2c_start();
   i2c_write(0x90);
   i2c_write(0xac);
   i2c_write(data);
   i2c_stop();
}


void init_temp() {
   output_high(PIN_B7);
   output_high(PIN_B6);
   i2c_start();
   i2c_write(0x90);
   i2c_write(0xee);
   i2c_stop();
   temp_config(8);
}


byte read_temp() {        ////// Returns degrees F (0-255)
   byte datah,datal;
   long data;

   i2c_start();
   i2c_write(0x90);
   i2c_write(0xaa);
   i2c_start();
   i2c_write(0x91);
   datah=i2c_read();
   datal=i2c_read(0);
   i2c_stop();
   data=datah;
   data=data*9;
   if((datal&0x80)!=0)
     data=data+4;
   data=(data/5)+32;
   datal=data;
   return(datal);
}



