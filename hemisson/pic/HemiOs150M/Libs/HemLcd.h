//-------------------------------------------------------------------------------//
//-                             HemLCD Lib. 1.1                                 -//
//-									        -//
//-  Copyright (C) Alexandre Colot, K-Team S.A. 2003                            -//
//-  This library is free software; you can redistribute it and/or              -//
//-  modify it under the terms of the GNU Lesser General Public                 -//
//-  License as published by the Free Software Foundation; either               -//
//-  version 2.1 of the License, or any later version.                          -//
//- 										-//
//-  This library is distributed in the hope that it will be useful,		-//
//-  but WITHOUT ANY WARRANTY; without even the implied warranty of		-//
//-  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU          -//
//-  Lesser General Public License for more details.                            -//
//-                                                                             -//
//-  You should have received a copy of the GNU Lesser General Public           -//
//-  License along with this library; if not, write to the Free Software        -//
//-  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  -//
//-										-//
//-										-//
//-                               __  __  ________				-//
//- K-Team S.A.                  |  |/  /|__    __|___  _____  ___  ___         -//
//- Chemin de Vuasset, CP 111    |     / __ |  | _____|/  _  \|   \/   |        -//
//- 1028 Preverenges             |  |  \    |  | ____|/  /_\  |        |        -//
//- Switzerland                  |__|\__\   |__|______|_/   \_|__|\/|__|        -//
//- alexandre.colot@k-team.com   tel:+41 21 802 5472 fax:+41 21 802 5471        -//
//-										-//
//-------------------------------------------------------------------------------//

#include <string.h>

#define HEMLCD_I2C_ADDRESS 0xA0

int1 SW1,SW2,SW3;
char Line1[12] = "";
char Line2[12] = "";

char HemLCD_Init(void)
{
  delay_ms(4000);
}

char HemLCD_Read_Version( void )
{
   unsigned char value = 0x00;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x00);                                            // Firmware version Register
   i2c_start();
   i2c_write(HEMLCD_I2C_ADDRESS + 1 );                         // HemLcd I2C Read Address
   value = i2c_read(0);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
   return value;
}

void HemLCD_Set_Backlight( unsigned char value )
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x40);                                            // BackLight Register
   i2c_write(value);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

void HemLCD_Set_Contrast( unsigned char value )
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x41);                                            // Contrast Register
   i2c_write(value);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

void HemLCD_Clear_Screen( void )
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x30);                                            // Clear Screen Register
   i2c_write(0x00);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

void HemLCD_Clear_Line1( void )
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x11);                                            // Clear Line1 Register
   i2c_write(0x00);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

void HemLCD_Clear_Line2( void )
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x21);                                            // Clear Line2 Register
   i2c_write(0x00);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

void HemLCD_read_Interruptors( void )
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x10);                                            // Read Interruptors Register
   i2c_start();
   i2c_write(HEMLCD_I2C_ADDRESS + 1 );                         // HemLcd I2C Read Address
   SW1 = i2c_read();
   SW2 = i2c_read();
   SW3 = i2c_read(0);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

void HemLCD_Line1_Left(char* Data)
{
   unsigned char char_count = 0;
   unsigned char tmp = 0;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x10);                                            // Line1 Left Register
   tmp = strlen(Data);
   i2c_write(tmp);
   for(char_count=0;char_count<tmp;char_count++)
   {
      i2c_write(*Data);
      Data++;
   }
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(10);
}

void HemLCD_Line1_Centered(char* Data)
{
   unsigned char char_count = 0;
   unsigned char tmp = 0;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x12);                                            // Line1 Center Register
   tmp = strlen(Data);
   i2c_write(tmp);                                               
   for(char_count=0;char_count<tmp;char_count++)
   {
      i2c_write(*Data);
      Data++;
   }
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(10);
}

void HemLCD_Line1_Right(char* Data)
{
   unsigned char char_count = 0;
   unsigned char tmp = 0;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x13);                                            // Line1 Right Register
   tmp = strlen(Data);
   i2c_write(tmp);                                               
   for(char_count=0;char_count<tmp;char_count++)
   {
      i2c_write(*Data);
      Data++;
   }
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(10);
}

void HemLCD_Line2_Left(char* Data)
{
   unsigned char char_count = 0;
   unsigned char tmp = 0;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x20);                                            // Line2 Left Register
   tmp = strlen(Data);
   i2c_write(tmp); 
   for(char_count=0;char_count<tmp;char_count++)
   {
      i2c_write(*Data);
      Data++;
   }
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(10);                                                 // I2C Stop Sequence
}

void HemLCD_Line2_Centered(char* Data)
{
   unsigned char char_count = 0;
   unsigned char tmp = 0;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x22);                                            // Line2 Center Register
   tmp = strlen(Data);
   i2c_write(tmp);                                                
   for(char_count=0;char_count<tmp;char_count++)
   {
      i2c_write(*Data);
      Data++;
   }
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(10);
}

void HemLCD_Line2_Right(char* Data)
{
   unsigned char char_count = 0;
   unsigned char tmp = 0;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMLCD_I2C_ADDRESS);                              // HemLcd I2C Address
   i2c_write(0x23);                                            // Line2 Right Register
   tmp = strlen(Data);
   i2c_write(tmp); 
   for(char_count=0;char_count<tmp;char_count++)
   {
      i2c_write(*Data);
      Data++;
   }
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(10);                                                // I2C Stop Sequence
}


