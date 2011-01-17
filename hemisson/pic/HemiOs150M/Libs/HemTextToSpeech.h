//-------------------------------------------------------------------------------//
//-                   HemTextToSpeech Lib. 1.2                                  -//
//-									        -//
//-  Copyright (C) Alexandre Colot, K-Team S.A. 2002                            -//
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
//-									        -//
//-									        -//
//-                               __  __  ________			        -//
//- K-Team S.A.                  |  |/  /|__    __|___  _____  ___  ___         -//
//- Chemin de Vuasset, CP 111    |     / __ |  | _____|/  _  \|   \/   |        -//
//- 1028 Preverenges             |  |  \    |  | ____|/  /_\  |        |        -//
//- Switzerland                  |__|\__\   |__|______|_/   \_|__|\/|__|        -//
//- alexandre.colot@k-team.com   tel:+41 21 802 5472 fax:+41 21 802 5471        -//
//-									        -//
//-------------------------------------------------------------------------------//

#define HEMTEXTTOSPEECH_I2C_ADDRESS 0xC4
char Speech_Speed = 0x05;                                                        // Speed : 0 (slowest) -> 3 (fastest)
char Speech_Pitch = 0x06;                                                        // Pitch :  0 (highest) -> 7 (lowest)
char Volume = 0x00;                                                              // Volume : 0 (loudest) -> 7 (quietest)

void HemTextToSpeech_Init(void)
{
	delay_ms(5000);
}

//----------------------------------------------------------------//
//-                Settings Functions                            -//
void HemTextToSpeech_Speed( char value )
{
   	Speech_Speed = value;
}

void HemTextToSpeech_Pitch( char value )
{
   	Speech_Pitch = value;
}

void HemTextToSpeech_Volume( char value)
{
	Volume = value;
}
char HemTextToSpeech_Version( void )
{
      	char Software_Version = 0x00;
      	i2c_start();                                                               	// I2C Start Sequence
      	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS);                                    	// HemTextToSpeech I2C Address
      	i2c_write(0x01);                                                           	// Software Version Register
      	i2c_start();
      	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS + 1 );                               	// HemTextToSpeech I2C Read Address
      	Software_Version = i2c_read(0);
      	i2c_stop();                                                                	// I2C Stop Sequence
      	return Software_version;
}

void HemTextToSpeech_Free( void )
{
      	char Speaking_Completed = 0xFF;
      	while(Speaking_Completed == 0xFF)
      	{
         	i2c_start();                                                            // I2C Start Sequence
         	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS);                                 // HemTextToSpeech I2C Address
         	i2c_write(0x00);                             	    			// Command Register
         	i2c_start();
         	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS + 1 );                       	// HemTextToSpeech I2C Read Address
         	Speaking_Completed = i2c_read(0);
         	i2c_stop();
      	}                                                                          	// I2C Stop Sequence
}

//----------------------------------------------------------------//
//-                Speak Functions                               -//

void HemTextToSpeech_Speak(char sentence[], int nbr)
{
      	int i;
	HemTextToSpeech_Free();
      	i2c_start();                                                               	// I2C Start Sequence
      	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS);                                    	// HemTextToSpeech I2C Address
      	i2c_write(0x00);                                                           	// HemTextToSpeech Command Register
      	i2c_write(0x00);                                                           	// HemTextToSpeech NOP Command
      	i2c_write(Volume);                                                           	// Volume (Max.)
      	i2c_write(Speech_Speed);                                                   	// Speech Speed
      	i2c_write(Speech_Pitch);                                                   	// Speech Pitch
      	for(i=0;i<nbr;i++)
      	{
         	i2c_write(sentence[i]);
      	}
      	i2c_write(0x00);                                                           	// NULL
      	i2c_stop();                                                                	// I2C Stop Sequence
      	HemTextToSpeech_Free();
      	i2c_start();                                                               	// I2C Start Sequence
      	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS);                                    	// HemTextToSpeech I2C Address
      	i2c_write(0x00);                                                           	// HemTextToSpeech Command Register
      	i2c_write(0x40);                                                           	// HemTextToSpeech Speack Command
      	i2c_stop();  
}

void HemTextToSpeech_Speak_PreDef( char nbr )
{
      	HemTextToSpeech_Free();
      	i2c_start();                                                               	// I2C Start Sequence
      	i2c_write(HEMTEXTTOSPEECH_I2C_ADDRESS);                                    	// HemTextToSpeech I2C Address
      	i2c_write(0x00);                                                           	// HemTextToSpeech Command Register
      	i2c_write(nbr);                                                            	// HemTextToSpeech NOP Command
      	i2c_stop();                                                                	// I2C Stop Sequence
}

