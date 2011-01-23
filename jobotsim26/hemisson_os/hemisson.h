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

#device PIC16F877 *=16 ADC=8

//#define firmware								 // Comment this line to build HemiOs

#include "16f877.h"

#fuses HS,NOWDT,NOPROTECT,NOPUT,NOBROWNOUT,NOLVP,WRT,NOCPD                       // Configuration Bits
#use delay(clock=20000000)                                                       // Clock @ 20 MHz
#use rs232(baud=115200,parity=N,xmit=PIN_C6,rcv=PIN_C7)                          // Serial Configuration
#use i2c( master , sda = PIN_C4 , scl = PIN_C3, FORCE_HW, SLOW  )                // I2C Master Configuration

#use fast_io(a)
#use fast_io(b)
#use fast_io(c)
#use fast_io(d)
#use fast_io(e)

#include "constants.h"
#include "variables.c"
#include "hemisson_task1.c"
#include "hemisson_task2.c"
#include "hemisson_task3.c"
#include "hemisson_task4.c"
#ifdef firmware
#include "firmware_hemisson.c"
#endif
#include "hemisson.c"

#org 0x1F00, 0x1FFF {}                                                             // Bootloader Protection

////////////////////////////////////////////////////////////////////////////////
/*!   \file hemisson.h
      \brief This file is the library of all high level functions that can be used when programming Hemisson
*/
////////////////////////////////////////////////////////////////////////////////

/**
   * \defgroup Configuration_Functions
   */
/*@{*/
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_init(void)
      \brief Hemisson Initialisation. This function initialise all Hemisson peripherals
      \param None
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_init(void)
{
	__hemisson_init();
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_config_auto_refresh_sensors(int1 Bit)
      \brief Set the refresh mode of all IRs sensors
      \param int1 Manual or Refresh (Default)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_config_auto_refresh_sensors(int1 Bit)
{
   	__Auto_Refresh_Sensors=Bit;
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_config_auto_refresh_tv_remote(int1 Bit)
      \brief Configure the refresh mode of the TV remote receiver
      \param int1 Manual or Refresh(Default)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_config_auto_refresh_tv_remote(int1 Bit)
{
   	__Auto_Refresh_TV_Remote=Bit;
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_config_rs232_control(int1 Bit)
      \brief Configure the Serial Remote Control
      \param int1 Disable or Enable (Default)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_config_rs232_control(int1 Bit)
{
   	__Enable_RS232_Control=Bit;
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_config_tv_remote_control(int1 Bit)
      \brief Configure the TV Remote Control
      \param int1 Disable or Enable (Default)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_config_tv_remote_control(int1 Bit)
{
   	__Enable_TV_Remote_Control=Bit;
}
/*@}*/

/**
   * \defgroup Flags
   */
/*@{*/
////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_flag_sensors_refreshed(void)
      \brief Test if IRs sensors have been refreshed
      \param None
      \retval int1 1 if sensors have been refreshed
      \warning You must then call hemisson_flag_sensors_reset() to clear the flag
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_flag_sensors_refreshed(void)
{
   	return __Sensors_Refreshed_Flag;
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_flag_sensors_reset(void)
      \brief Clear IRs sensors refreshed flag
      \param None
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_flag_sensors_reset(void)
{
   	__Sensors_Refreshed_Flag = 0;
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_flag_rs232_filtering(void)
      \brief Test if Serial Remote Control is active or not
      \param None
      \retval int1 1 if active otherwise 0
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_flag_rs232_filtering(void)
{
   	return __Enable_RS232_Control;
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_flag_tv_data_refreshed(void)
      \brief Test if Tv Data have been refreshed
      \param None
      \retval int1 1 if active otherwise 0
      \warning You must then call hemisson_flag_tv_data_reset() to clear the flag
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_flag_tv_data_refreshed(void)
{
   	return __TV_Data_Available;
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_flag_tv_data_reset(void)
      \brief Clear TV Data flag
      \param None
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_flag_tv_data_reset(void)
{
   	__TV_Data_Available = 0;
}
/*@}*/

/**
   * \defgroup Peripheral_Access_Functions
   */
/*@{*/
////////////////////////////////////////////////////////////////////////////////
/*!   \fn unsigned char hemisson_get_proximity(char Sensor)
      \brief Get proximity value of one IR sensor
      \param char Front, FrontLeft, FrontRight, Left, Right, Rear, GroundLeft, GroundRight
      \retval unsigned_char Proximity Value (0 when nothing)
*/
////////////////////////////////////////////////////////////////////////////////
unsigned char hemisson_get_proximity(char Sensor)
{
   	return __IR_Proximity[Sensor];
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn unsigned char hemisson_get_brightness(char Sensor)
      \brief Get brightness value of one IR sensor
      \param char Front, FrontLeft, FrontRight, Left, Right, Rear, GroundLeft, GroundRight
      \retval unsigned_char Brightness Value (0 when lot of light)
*/
////////////////////////////////////////////////////////////////////////////////
unsigned char hemisson_get_brightness(char Sensor)
{
   	return __IR_Light[Sensor];
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_get_switch_state(char Switch_Number)
      \brief Get Switch State
      \param char Number of the switch (0,1,2 or 3)
      \retval int1 Position of the switch (0 or 1)
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_get_switch_state(char Switch_Number)
{
   	return __Switchs[Switch_Number];
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn char hemisson_get_tv_data(void)
      \brief Get latest data from the TV remote receiver
      \param None
      \retval char Byte that have been received
*/
////////////////////////////////////////////////////////////////////////////////
char hemisson_get_tv_data(void)
{
   	return __TV_DATA;
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_set_speed(signed int8 Left, signed int8 Right)
      \brief Set speed of each motor
      \param signed_int8 Speed of motor (from -9 to 9, 0 = Stop)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_set_speed(signed int8 LeftSpeed,signed int8 RightSpeed)
{
   	__PwmMotLeft = LeftSpeed;
   	__PwmMotRight = RightSpeed;
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_beep(int1 State)
      \brief Set the buzzer State
      \param int1 State (0=Off,1=On)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_beep(int1 State)
{
   	output_bit(PIN_D4,State);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_led_frontleft(int1 State)
      \brief Set the FrontLeft Led State
      \param int1 State (0=Off,1=On)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_led_frontleft(int1 State)
{
   	output_bit(PIN_D6,State);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_led_frontright(int1 State)
      \brief Set the FrontRight Led State
      \param int1 State (0=Off,1=On)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_led_frontright(int1 State)
{
   	output_bit(PIN_A4,!State);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_led_pgmexec(int1 State)
      \brief Set the PgmExec Led State
      \param int1 State (0=Off,1=On)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_led_pgmexec(int1 State)
{
   	output_bit(PIN_D5,State);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_led_onoff(int1 State)
      \brief Set the OnOff Led State
      \param int1 State (0=Off,1=On)
      \retval None
      \warning This Led is controlled in background by the Scheduler
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_led_onoff(int1 State)
{
   	output_bit(PIN_D7,State);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_manual_refresh_sensors(char Zone)
      \brief Manualy refresh a zone
      \param char Zone (FrontZone,GroundZone,RearZone)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_manual_refresh_sensors(char Zone)
{
   	__hemisson_refresh_sensors(Zone);
}
/*@}*/

/**
   * \defgroup Time_Functions
   */
/*@{*/
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_delay_s(int Delay)
      \brief This function stop program execution (but not the interrupts) during the defined time in second
      \param Delay 1 to 255
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_delay_s(int Delay)
{
   	int i;
   	for(i = 0; i< Delay; i++)
   	{
		delay_ms(1000);
   	}
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_delay_ms(int Delay)
      \brief This function stop program execution (but not the interrupts) during the defined time in millisecond
      \param Delay 1 to 255
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_delay_ms(int Delay)
{
	delay_ms(Delay);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_delay_us(int Delay)
      \brief This function stop program execution (but not the interrupts) during the defined time in microsecond
      \param Delay 1 to 255
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_delay_us(int Delay)
{
	delay_us(Delay);
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn unsigned int32 hemisson_get_time(void)
      \brief This function return a 32 bits value which is increased every ms
      \param None
      \retval unsigned int32 time value
*/
////////////////////////////////////////////////////////////////////////////////
unsigned int32 hemisson_get_time(void)
{
	return __TimeTip;
}

////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_set_time(unsigned int32 time)
      \brief This function allow user to set the current time value
      \param unsigned int32 time
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_set_time(unsigned int32 Time)
{
	__TimeTip = Time;
}

/*@}*/


/**
   * \defgroup External_Access
   */
/*@{*/
// External Access
////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_ext_read_PINB0(void)
      \brief Read input PORTB.0
      \param None
      \retval int1 input value (0 or 1)
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_ext_read_PINB0(void)
{	
	set_tris_b(TRISB|0x01);	
	return input(PIN_B0);
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_ext_read_PINB6(void)
      \brief Read input PORTB.6
      \param None
      \retval int1 input value (0 or 1)
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_ext_read_PINB6(void)
{
	set_tris_b(TRISB|0x40);
	return input(PIN_B6);
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn int1 hemisson_ext_read_PINB7(void)
      \brief Read input PORTB.7
      \param None
      \retval int1 input value (0 or 1)
*/
////////////////////////////////////////////////////////////////////////////////
int1 hemisson_ext_read_PINB7(void)
{
	set_tris_b(TRISB|0x80);
	return input(PIN_B7);
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_ext_write_PINB0(int1 Bit)
      \brief Write output PORTB.0
      \param int1 Value to write (0 or 1)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_ext_write_PINB0(int1 Bit)
{
	set_tris_b(TRISB & 0xFE);
	output_bit(PIN_B0,bit);
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_ext_write_PINB6(int1 Bit)
      \brief Write output PORTB.6
      \param int1 Value to write (0 or 1)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_ext_write_PINB6(int1 Bit)
{
	set_tris_b(TRISB & 0xBF);
	output_bit(PIN_B6,bit);
}
////////////////////////////////////////////////////////////////////////////////
/*!   \fn void hemisson_ext_write_PINB7(int1 Bit)
      \brief Write output PORTB.7
      \param int1 Value to write (0 or 1)
      \retval None
*/
////////////////////////////////////////////////////////////////////////////////
void hemisson_ext_write_PINB7(int1 Bit)
{
	set_tris_b(TRISB & 0x7F);
	output_bit(PIN_B7,bit);
}
/*@}*/




