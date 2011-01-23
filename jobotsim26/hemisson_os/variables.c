//--------------------------------------------------------------------------------//
//-                   HemiOs ( Hemisson Operating System )                       -//
//-                                                                              -//
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
/*!   \file variables.c
      \brief Definition of all global functions
*/
////////////////////////////////////////////////////////////////////////////////

//- Motors
signed int8 __PwmMotLeft = 0;                                                    // 0 : Stop ; 9 : Max Speed
signed int8 __PwmMotRight = 0;                                                   // 0 : Stop ; 9 : Max Speed
unsigned char __PwmCounter = 0;

//- Configuration Bits
int1 __Auto_Refresh_Sensors = 1;                                                 // 1 : Auto   ; 0 : Manual
int1 __Auto_Refresh_TV_Remote = 1;                                               // 1 : Auto   ; 0 : Never
int1 __Enable_RS232_Control = 1;                                                 // 1 : Enable ; 0 : Disable
int1 __Enable_TV_Remote_Control = 1;                                             // 1 : Enable ; 0 : Disable

//- Flags
int1 __Sensors_Refreshed_Flag = 0;                                               // 1 : Sensors refreshed
int1 __TV_Data_Available = 0;

//- RS232
char __SerialBuffer[ __SERIAL_BUFFER_SIZE ];
#locate __SerialBuffer = 0x01A0
char __SerialCounter = 0;

//- TV Remote Control
int1 __StartBit1 = 0;
int1 __StartBit2 = 0;
int1 __ToggleBit = 0;
int1 __SystemBit4 = 0;
int1 __SystemBit3 = 0;
int1 __SystemBit2 = 0;
int1 __SystemBit1 = 0;
int1 __SystemBit0 = 0;
int1 __DataBit5 = 0;
int1 __DataBit4 = 0;
int1 __DataBit3 = 0;
int1 __DataBit2 = 0;
int1 __DataBit1 = 0;
int1 __DataBit0 = 0;
int __TV_DATA = 0;
int __TV_Counter = 0;
int __BeepMem = 0;

//- Time
unsigned int32 __TimeTip = 0;
char __TimeTipDivider = 0;

//- Irs Sensors
unsigned char __IR_Light[ 8 ];
#locate __IR_Light = 0x01D2
unsigned char __IR_Proximity[ 8 ];				// 0 : nothing detected ; 255 : obstacle near Hemisson
#locate __IR_Proximity = 0x01DA

//- Switchs
char __Switchs[ 4 ];
#locate __Switchs = 0x01E2

//- Scheduler
unsigned char __SchedulerTask = 0;
int __Divider = 0;

//- TRIS
#locate TRISB = 0x0086




