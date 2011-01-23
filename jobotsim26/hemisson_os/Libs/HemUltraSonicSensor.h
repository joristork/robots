//--------------------------------------------------------------------------------//
//-                   HemUltraSonicSensor Lib V1.2                               -//
//-																							         -//
//-  Copyright (C) Alexandre Colot, K-Team S.A. 2002                             -//
//-  This library is free software; you can redistribute it and/or               -//
//-  modify it under the terms of the GNU Lesser General Public                  -//
//-  License as published by the Free Software Foundation; either                -//
//-  version 2.1 of the License, or any later version.                           -//
//- 																										-//
//-  This library is distributed in the hope that it will be useful,					-//
//-  but WITHOUT ANY WARRANTY; without even the implied warranty of					-//
//-  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU           -//
//-  Lesser General Public License for more details.                             -//
//-                                                                              -//
//-  You should have received a copy of the GNU Lesser General Public            -//
//-  License along with this library; if not, write to the Free Software         -//
//-  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA   -//
//-																							         -//
//-																							         -//
//-                               __  __  ________					                  -//
//- K-Team S.A.                  |  |/  /|__    __|___  _____  ___  ___          -//
//- Chemin de Vuasset, CP 111    |     / __ |  | _____|/  _  \|   \/   |         -//
//- 1028 Preverenges             |  |  \    |  | ____|/  /_\  |        |         -//
//- Switzerland                  |__|\__\   |__|______|_/   \_|__|\/|__|         -//
//- alexandre.colot@k-team.com   tel:+41 21 802 5472 fax:+41 21 802 5471         -//
//-																							         -//
//--------------------------------------------------------------------------------//

#define I2C_Address 0xE0

void HemUltrason_Init(void)
{
   delay_ms(500);
}

char HemUltraSon_Read_Version( void )
{
   char __version = 0x00;
   i2c_start();
   i2c_write(0xE0);                                                              //SRF08 I2C Address
   i2c_write(0x00);
   i2c_start();
   i2c_write(0xE1);                                                              //SRF08 I2C Address
   __version = i2c_read(0);
   i2c_stop();
   return __version;
}

char HemUltraSon_Read_Brightness( void )
{
   char __brightness = 0x00;
   i2c_start();
   i2c_write(0xE0);                                                              //SRF08 I2C Address
   i2c_write(0x01);
   i2c_start();
   i2c_write(0xE1);                                                              //SRF08 I2C Address
   __brightness = i2c_read(0);
   i2c_stop();
   return __brightness;
}

void HemUltraSon_Start_Measure( void )
{
   i2c_start();
   i2c_write(0xE0);                                                              // SRF08 I2C Address
   i2c_write(0x00);                                                              // Command Register
   i2c_write(0x51);                                                              // Measure in millimeters
   i2c_stop();
   delay_ms(65);                                                                 // Wait for data available
}

unsigned int16 HemUltraSon_Read_Values( char Echo )
{
   unsigned int __highvalue = 0x00;
   unsigned int __lowvalue = 0x00;
   unsigned int16 __Result = 0x00;
   i2c_start();
   i2c_write(0xE0);                                                              //SRF08 I2C Address
   i2c_write(0x02+Echo+Echo);
   i2c_start();
   i2c_write(0xE1);                                                              //SRF08 I2C Address
   __highvalue = i2c_read(0);
   i2c_stop();
   i2c_start();
   i2c_write(0xE0);                                                              //SRF08 I2C Address
   i2c_write(0x03+Echo+Echo);
   i2c_start();
   i2c_write(0xE1);                                                              //SRF08 I2C Address
   __lowvalue = i2c_read(0);
   i2c_stop();
   __Result = (__highvalue<<8) + __lowvalue;
   return __Result;
}

void HemUltraSon_Init_Range_Register( unsigned char Value )
{
   i2c_start();
   i2c_write(0xE0);                                                              // SRF08 I2C Address
   i2c_write(0x02);                                                              // Range register ((Value*43mm)+43mm)
   i2c_write(Value);                                                             // ((Value*43mm)+43mm)
   i2c_stop();
}
