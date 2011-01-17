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


#define INIT_TRISA            0x2F	// b0010 1111
#define INIT_PORTA            0x10	// b0001 0000

#define INIT_TRISB            0x30	// bXX11 000X
#define INIT_PORTB            0x00	// b0000 0000

#define INIT_TRISC            0xA7	// b1010 0111
#define INIT_PORTC            0x00	// b0001 1000

#define INIT_TRISD            0x00	// b0000 0000
#define INIT_PORTD            0x00	// b0000 0000

#define INIT_TRISE            0x0F	// b0000 1111
#define INIT_PORTE            0x00	// b0000 0000

#define __SERIAL_BUFFER_SIZE  20 //80

#define FRONT                 2
#define FRONTLEFT             1
#define FRONTRIGHT            0
#define LEFT                  4
#define RIGHT                 5
#define REAR                  3
#define GROUNDLEFT            6
#define GROUNDRIGHT           7
#define FRONTZONE             0
#define REARZONE              1
#define GROUNDZONE            2

#define ENABLE                1
#define DISABLE               0
#define FAST                  1
#define NORMAL                0
#define MANUAL                0
#define REFRESH               1
#define ON                    1
#define OFF                   0

#define HEMIOS_VERSION        1
#define HEMIOS_REVISION       50

// Global variables

/* Line following */
unsigned int GroundRightFree, GroundLeftFree;
unsigned int IR_trigger;
unsigned int IR_threshold;
short FreeZone;
short IR_armed;

//- Motors
signed int8 __PwmMotLeft = 0;                                                    // 0 : Stop ; 9 : Max Speed
signed int8 __PwmMotRight = 0;                                                   // 0 : Stop ; 9 : Max Speed
unsigned char __PwmCounter = 0;

//- Configuration Bits
int1 __Auto_Refresh_Sensors = 1;                                                 // 1 : Auto   ; 0 : Manual
int1 __Enable_RS232_Control = 1;                                                 // 1 : Enable ; 0 : Disable

//- Flags
int1 __Sensors_Refreshed_Flag = 0;                                               // 1 : Sensors refreshed
int __BeepMem = 0;

//- Time
unsigned int32 __TimeTip = 0;
char __TimeTipDivider = 0;

//- Irs Sensors
unsigned char __IR_Light[ 8 ];
unsigned char __IR_Proximity[ 8 ];				// 0 : nothing detected ; 255 : obstacle near Hemisson

//- Switchs
char __Switchs[ 4 ];

//- Scheduler
unsigned char __SchedulerTask = 0;
int __Divider = 0;


