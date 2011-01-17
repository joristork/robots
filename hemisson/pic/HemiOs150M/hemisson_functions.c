//--------------------------------------------------------------------------------//
//-                   HemiOs ( Hemisson Operating System )                       -//
//										 -//
//-  Copyright (C) Alexandre Colot, K-Team S.A. 2002                             -//
//-  This library is free software; you can redistribute it and/or               -//
//-  modify it under the terms of the GNU Lesser General Public                  -//
//-  License as published by the Free Software Foundation; either                -//
//-  version 2.1 of the License, or any later version.                           -//
//-                                                                              -//
//-  This library is distributed in the hope that it will be useful,             -//
//-  but WITHOUT ANY WARRANTY; without even the implied warranty of              -//
//-  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU           -//
//-  Lesser General Public License for more details.                             -//
//-                                                                              -//
//-  You should have received a copy of the GNU Lesser General Public            -//
//-  License along with this library; if not, write to the Free Software         -//
//-  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA   -//
//-                                                                              -//
//-                               __  __  ________                               -//
//- K-Team S.A.                  |  |/  /|__    __|___  _____  ___  ___          -//
//- Chemin de Vuasset, CP 111    |     / __ |  | _____|/  _  \|   \/   |         -//
//- 1028 Preverenges             |  |  \    |  | ____|/  /_\  |        |         -//
//- Switzerland                  |__|\__\   |__|______|_/   \_|__|\/|__|         -//
//- alexandre.colot@k-team.com   tel:+41 21 802 5472 fax:+41 21 802 5471         -//
//-                                                                              -//
//--------------------------------------------------------------------------------//


////////////////////////////////////////////////////////////////////////////////
void hemisson_config_rs232_control(int1 Bit)
{
   	__Enable_RS232_Control=Bit;
}


int1 hemisson_flag_sensors_refreshed(void)
{
   	return __Sensors_Refreshed_Flag;
}

int1 hemisson_flag_rs232_filtering(void)
{
   	return __Enable_RS232_Control;
}

int1 hemisson_get_switch_state(char Switch_Number)
{
   	return __Switchs[Switch_Number];
}

void hemisson_set_speed(signed int8 LeftSpeed,signed int8 RightSpeed)
{
   	__PwmMotLeft = LeftSpeed;
   	__PwmMotRight = RightSpeed;
}

void hemisson_beep(int1 State)
{
   	output_bit(PIN_D4,State);
}

void hemisson_delay_s(int Delay)
{
   	int i;
   	for(i = 0; i< Delay; i++)
   	{
		delay_ms(1000);
   	}
}

void hemisson_delay_ms(int Delay)
{
	delay_ms(Delay);
}

unsigned int32 hemisson_get_time(void)
{
	return __TimeTip;
}

void hemisson_set_time(unsigned int32 Time)
{
	__TimeTip = Time;
}




