//--------------------------------------------------------------------------------//
//-                             HemGenIO Lib. 1.3                                -//
//-																							         -//
//-  Copyright (C) Alexandre Colot, K-Team S.A. 2003                             -//
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

#define HEMGENIO_I2C_ADDRESS 0xD0

void HemGenIO_Init( void )
{
  delay_ms(1000);
}

char HemGenIO_Read_Version( void )
{
   unsigned char value = 0x00;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMGENIO_I2C_ADDRESS);                            // I2C Address
   i2c_write(0x00);                                            // Firmware version Register
   i2c_start();
   i2c_write(HEMGENIO_I2C_ADDRESS + 1 );                       // I2C Read Address
   value = i2c_read(0);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
   return value;
}

int1 HemGenIO_Read_Digital(char input)
{
   int1 state;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMGENIO_I2C_ADDRESS);                            // I2C Address
   switch(input)
   {
      case 0 :
      i2c_write(0x20);                                         // Register Adress
      break;
      case 1:
      i2c_write(0x21);                                         // Register Adress
      break;
      case 2 :
      i2c_write(0x22);                                         // Register Adress
      break;
      case 3 :
      i2c_write(0x23);                                         // Register Adress
      break;
      case 4 :
      i2c_write(0x24);                                         // Register Adress
      break;
      case 5 :
      i2c_write(0x25);                                         // Register Adress
      break;
      case 6 :
      i2c_write(0x26);                                         // Register Adress
      break;
      case 7 :
      i2c_write(0x27);                                         // Register Adress
      break;
      case 8 :
      i2c_write(0x28);                                         // Register Adress
      break;
      case 9 :
      i2c_write(0x29);                                         // Register Adress
      break;
      case 10 :
      i2c_write(0x30);                                         // Register Adress
      break;
      case 11 :
      i2c_write(0x31);                                         // Register Adress
      break;
   }
   i2c_start();
   i2c_write(HEMGENIO_I2C_ADDRESS + 1 );                       // I2C Read Address
   state = i2c_read(0);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
   return state;
}

void HemGenIO_Write_Digital(char input, int1 state)
{
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMGENIO_I2C_ADDRESS);                            // I2C Address
   switch(input)
   {
      case 0 :
      i2c_write(0x20);                                         // Register Adress
      break;
      case 1:
      i2c_write(0x21);                                         // Register Adress
      break;
      case 2 :
      i2c_write(0x22);                                         // Register Adress
      break;
      case 3 :
      i2c_write(0x23);                                         // Register Adress
      break;
      case 4 :
      i2c_write(0x24);                                         // Register Adress
      break;
      case 5 :
      i2c_write(0x25);                                         // Register Adress
      break;
      case 6 :
      i2c_write(0x26);                                         // Register Adress
      break;
      case 7 :
      i2c_write(0x27);                                         // Register Adress
      break;
      case 8 :
      i2c_write(0x28);                                         // Register Adress
      break;
      case 9 :
      i2c_write(0x29);                                         // Register Adress
      break;
      case 10 :
      i2c_write(0x30);                                         // Register Adress
      break;
      case 11 :
      i2c_write(0x31);                                         // Register Adress
      break;
   }
   i2c_write(state);                                           // Data
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
}

unsigned char HemGenIO_Read_Analog(char input)
{
   unsigned char state;
   i2c_start();                                                // I2C Start Sequence
   i2c_write(HEMGENIO_I2C_ADDRESS);                            // I2C Address
   switch(input)
   {
      case 0 :
      i2c_write(0x10);                                         // Register Adress
      break;
      case 1:
      i2c_write(0x11);                                         // Register Adress
      break;
      case 2 :
      i2c_write(0x12);                                         // Register Adress
      break;
      case 3 :
      i2c_write(0x13);                                         // Register Adress
      break;
      case 4 :
      i2c_write(0x14);                                         // Register Adress
      break;
   }
   i2c_start();
   i2c_write(HEMGENIO_I2C_ADDRESS + 1 );                       // I2C Read Address
   state = i2c_read(0);
   i2c_stop();                                                 // I2C Stop Sequence
   delay_ms(1);
   return state;
}

