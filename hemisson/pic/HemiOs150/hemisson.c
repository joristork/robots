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
/*!   \file hemisson.c
      \brief The Os functions, all functions are called through an interrupt
*/
////////////////////////////////////////////////////////////////////////////////

//----------------------------------------------------------------//
//-                Conversion Function                           -//
char chartohex( char valuetoconvert )
{
	char convertedval;
   	if( valuetoconvert >= 'A' )
   	{
      		convertedval = valuetoconvert-'A'+10;
   	}	
   	else
   	{
      		convertedval = valuetoconvert -'0';
   	}
   	return convertedval;
}

//----------------------------------------------------------------//
//-                Robot Initialisation                          -//
void __hemisson_init(void)
{
	__PwmMotLeft = 0;
   	__PwmMotRight = 0;

   	__IR_Light[ 0 ] = 0;
   	__IR_Light[ 1 ] = 0;
   	__IR_Light[ 2 ] = 0;
   	__IR_Light[ 3 ] = 0;
   	__IR_Light[ 4 ] = 0;
   	__IR_Light[ 5 ] = 0;
   	__IR_Light[ 6 ] = 0;
   	__IR_Light[ 7 ] = 0;
   	__IR_Proximity[ 0 ] = 0;
   	__IR_Proximity[ 1 ] = 0;
   	__IR_Proximity[ 2 ] = 0;
   	__IR_Proximity[ 3 ] = 0;
   	__IR_Proximity[ 4 ] = 0;
   	__IR_Proximity[ 5 ] = 0;
   	__IR_Proximity[ 6 ] = 0;
   	__IR_Proximity[ 7 ] = 0;

   	__Switchs[ 0 ] = 0;
   	__Switchs[ 1 ] = 0;
   	__Switchs[ 2 ] = 0;
   	__Switchs[ 3 ] = 0;

   	//setup_spi(FALSE);
   	setup_psp(PSP_DISABLED);
   	setup_counters(RTCC_INTERNAL,RTCC_DIV_2);
   	setup_timer_1(T1_DISABLED);
   	setup_timer_2(T2_DISABLED,0,1);
   	setup_ccp1(CCP_OFF);
	setup_ccp2(CCP_OFF);

	// I/O's
	set_tris_a(INIT_TRISA);
	set_tris_b(INIT_TRISB);
	set_tris_c(INIT_TRISC);
	set_tris_d(INIT_TRISD);
	set_tris_e(INIT_TRISE);

	output_a(INIT_PORTA);
	output_b(INIT_PORTB);
	output_c(INIT_PORTC);
	output_d(INIT_PORTD);
	output_e(INIT_PORTE);

	port_b_pullups(false);

	// Analog Port
	setup_adc_ports(ALL_ANALOG);
	setup_adc(ADC_CLOCK_INTERNAL);

	// Timer 2 for TV Remote RC5 Decode
	setup_timer_2(T2_DISABLED,0xBB,3);
	set_timer2(0);

	// Timer 0 for Scheduler
	setup_timer_0 (RTCC_DIV_4);
	set_timer0(0);
   	
	#ifdef firmware
	// Timer 1 for Webots Timer
   	setup_timer_1(T1_INTERNAL|T1_DIV_BY_8);
   	set_timer1(0);
	#endif
	
	// Welcome Message
	delay_ms(5);			// Delay for HemRadio Module
	printf("HemiOS V%d.%d , K-Team S.A. ( Alexandre Colot )  " , HEMIOS_VERSION , HEMIOS_REVISION);
	printf(__DATE__);
	printf(" @ ");
	printf(__TIME__);
	printf("\r\n");
	printf("E-Mail : info@hemisson.com\r\n\r\n");

	// Interrupts
	enable_interrupts(INT_TIMER0);
	enable_interrupts(INT_RB);
	if( __Enable_RS232_Control == 1 )
	{
		enable_interrupts(INT_RDA);
	}
	enable_interrupts(GLOBAL);
}

//---------------------------------------------------------------//
//-                        IR Sensors                           -//
void __hemisson_refresh_sensors( int zone )
{
	switch( zone )
	{
		case FrontZone :
			set_adc_channel( FrontLeft );
			delay_us( 12 );
			__IR_Light[ FrontLeft ] = read_adc();
			set_adc_channel( FrontRight );
			delay_us( 12 );
			__IR_Light[ FrontRight ] = read_adc();
			set_adc_channel( Front );
			delay_us( 12 );
			__IR_Light[ Front ] = read_adc();
			output_high( PIN_B2 );
			delay_us( 300 );
			__IR_Proximity[ Front ] = __IR_Light[ Front ] - read_adc();
			set_adc_channel( FrontLeft );
			delay_us( 12 );
			__IR_Proximity[ FrontLeft ] = __IR_Light[ FrontLeft ] - read_adc();
			set_adc_channel( FrontRight );
			delay_us( 12 );
			__IR_Proximity[ FrontRight ] = __IR_Light[ FrontRight ] - read_adc();
			output_low( PIN_B2 );
			break;
		case RearZone :
			set_adc_channel(Left);
			delay_us(12);
			__IR_Light[Left]=read_adc();
			set_adc_channel(Right);
			delay_us(12);
			__IR_Light[Right]= read_adc();
			set_adc_channel(Rear);
			delay_us(12);
			__IR_Light[Rear]= read_adc();
			output_high(PIN_B3);
			delay_us(300);
			__IR_Proximity[Rear]=__IR_Light[Rear]- read_adc();
			set_adc_channel(Left);
			delay_us(12);
			__IR_Proximity[Left]=__IR_Light[Left]- read_adc();
			set_adc_channel(Right);
			delay_us(12);
			__IR_Proximity[Right]=__IR_Light[Right]- read_adc();
			output_low(PIN_B3);
			break;
		case GroundZone :
			set_adc_channel( GroundLeft );
			delay_us( 12 );
			__IR_Light[ GroundLeft ] = read_adc();
			set_adc_channel( GroundRight );
			delay_us( 12 );
			__IR_Light[ GroundRight ] = read_adc();
			output_high( PIN_B1 );
			delay_us( 300 );
			__IR_Proximity[ GroundRight ] = __IR_Light[ GroundRight ] - read_adc();
			set_adc_channel( GroundLeft );
			delay_us( 12 );
			__IR_Proximity[ GroundLeft ] = __IR_Light[ GroundRight ] - read_adc();
			output_low( PIN_B1 );
			break;
	}
	delay_ms( 5 );
}

//---------------------------------------------------------------//
//-                        TV Remote                            -//

// TV Remote Control
void __TV_Remote_Control( void )
{
	switch( __TV_DATA )
	{
		case 1 :
			__PwmMotLeft = 5;
			__PwmMotRight = 10;
			break;
		case 2 :
			__PwmMotLeft = 10;
			__PwmMotRight = 10;
			break;
		case 3 :
			__PwmMotLeft = 10;
			__PwmMotRight = 5;
			break;
		case 4:
			__PwmMotLeft = -10;
			__PwmMotRight = 10;
			break;
		case 5 :
			__PwmMotLeft = 0;
			__PwmMotRight = 0;
			break;
		case 6 :
			__PwmMotLeft = 10;
			__PwmMotRight = -10;
			break;
		case 7 :
			__PwmMotLeft = -5;
			__PwmMotRight = -10;
			break;
		case 8:
			__PwmMotLeft = -10;
			__PwmMotRight = -10;
			break;
		case 9 :
			__PwmMotLeft = -10;
			__PwmMotRight = -5;
			break;
		case 12 :   			// Touche On/Off
			break;
		case 16 :   			// Touche Son +
			break;
		case 17 :   			// Touche Son -
			break;
	}
}


//-----------------------------------------------------------//
//                  Internal Interrupts                     -//

#INT_RB
void __TV_Remote_Interrupt(void)
{
	if( __Auto_Refresh_TV_Remote == 1 )
	{
		delay_us(150);									// only a glitch
		if(input(PIN_B4)== 0)
		{
			__TV_Counter=0;
			__StartBit1=!input(PIN_B4);
			__TV_Counter++;
			disable_interrupts(INT_RB);
			setup_timer_2(T2_DIV_BY_16,0xBB,3);                                     // Interrupt every 1.780 ms
			set_timer2(0);
			enable_interrupts(INT_TIMER2);
		}
	}
}

#INT_TIMER0
void Scheduler_Interrupt(void)                                                   		// Internal Task Manager, Interrupt every 200 us
{                                                                                		// Sensors Powered during 400us, refreshed every 40ms
	// Time function
	__TimeTipDivider++;
	if(__TimeTipDivider == 5)
	{
		__TimeTipDivider = 0;
		__TimeTip++;                                                               	// __TimeTip incremented every 1 ms (counter up to 4294967296 ms = 1193 hours ...)
	}
	// Motor Task, PWM freq 300 Hz
	if( __PwmMotLeft >= 0 )                                                       		// Left Motor
	{
		if( ( 15 - __PwmMotLeft + __PwmCounter ) >= 15 )
		{
			output_bit( PIN_D0 , 0 );
			output_bit( PIN_D1 , 0 );
		}
		else
		{
			output_bit( PIN_D0 , 1 );
			output_bit( PIN_D1 , 0 );
		}
	}
	else
	{
		if( ( 15 - ( - __PwmMotLeft ) + __PwmCounter ) >= 15 )
		{
			output_bit( PIN_D0 , 0 );
			output_bit( PIN_D1 , 0 );
		}
		else
		{
			output_bit( PIN_D0 , 0 );
			output_bit( PIN_D1 , 1 );
		}
	}
	if( __PwmMotRight >= 0 )                                                      		// Right Motor
	{
		if( ( 15 - __PwmMotRight + __PwmCounter ) >= 15 )
		{
			output_bit( PIN_D2 , 0 );
			output_bit( PIN_D3 , 0 );
		}
		else
		{
			output_bit( PIN_D2 , 1 );
			output_bit( PIN_D3 , 0 );
		}
	}
	else
	{
		if( ( 15 - ( - __PwmMotRight ) + __PwmCounter ) >= 15 )
		{
			output_bit( PIN_D2 , 0 );
			output_bit( PIN_D3 , 0 );
		}
		else
		{
			output_bit( PIN_D2 , 0 );
			output_bit( PIN_D3 , 1 );
		}
	}
	__PwmCounter++;                                                               		// Counter
	if( __PwmCounter > 15 )
	{
		__PwmCounter = 0;
	}

	// Other Tasks
	switch( __SchedulerTask )
	{
		case 1 :                                                                   	// Brightness measurement zone 1
			if( __Auto_Refresh_Sensors == 1 )
			{
				set_adc_channel( GroundLeft );
				delay_us( 12 );
				__IR_Light[ GroundLeft ] = read_adc();
				set_adc_channel( GroundRight );
				delay_us( 12 );
				__IR_Light[ GroundRight ] = read_adc();
				output_high( PIN_B1 );
			}
			break;
		case 3 :                                                                   	// Proximity measurement zone 1
			if( __Auto_Refresh_Sensors == 1 )
			{
				__IR_Proximity[ GroundRight ] = __IR_Light[ GroundRight ] - read_adc();
				set_adc_channel( GroundLeft );
				delay_us( 12 );
				__IR_Proximity[ GroundLeft ] = __IR_Light[ GroundRight ] - read_adc();
				output_low( PIN_B1 );
			}
			break;
		case 10 :                                                                  	// Brightness measurement zone 2
			if( __Auto_Refresh_Sensors == 1 )
			{
				set_adc_channel( FrontLeft );
				delay_us( 12 );
				__IR_Light[ FrontLeft ] = read_adc();
				set_adc_channel( FrontRight );
				delay_us( 12 );
				__IR_Light[ FrontRight ] = read_adc();
				set_adc_channel( Front );
				delay_us( 12 );
				__IR_Light[ Front ] = read_adc();
				output_high( PIN_B2 );
			}
			break;
		case 12 :                                                                 	// Proximity measurement zone 2
			if( __Auto_Refresh_Sensors == 1 )
			{
				__IR_Proximity[ Front ] = __IR_Light[ Front ] - read_adc();
				set_adc_channel( FrontLeft );
				delay_us( 12 );
				__IR_Proximity[ FrontLeft ] = __IR_Light[ FrontLeft ] - read_adc();
				set_adc_channel( FrontRight );
				delay_us( 12 );
				__IR_Proximity[ FrontRight ] = __IR_Light[ FrontRight ] - read_adc();
				output_low( PIN_B2 );
			}
			break;
		case 15 :
			if(__Divider==0)                                                        // Powered during 4ms every 450ms
			{
				output_bit( PIN_D7 , 1 );
			}
			break;
		case 20 :                                                                  	// Brightness measurement zone 3
			if( __Auto_Refresh_Sensors == 1 )
			{
				set_adc_channel(Left);
				delay_us(12);
				__IR_Light[Left]=read_adc();
				set_adc_channel(Right);
				delay_us(12);
				__IR_Light[Right]= read_adc();
				set_adc_channel(Rear);
				delay_us(12);
				__IR_Light[Rear]= read_adc();
				output_high(PIN_B3);
			}
			break;
		case 22 :                                                                 	// Proximity measurement zone 3
			if( __Auto_Refresh_Sensors == 1 )
			{
				__IR_Proximity[Rear]=__IR_Light[Rear]- read_adc();
				set_adc_channel(Left);
				delay_us(12);
				__IR_Proximity[Left]=__IR_Light[Left]- read_adc();
				set_adc_channel(Right);
				delay_us(12);
				__IR_Proximity[Right]=__IR_Light[Right]- read_adc();
				output_low(PIN_B3);
				__Sensors_Refreshed_Flag = 1;
			}
			break;
		case 25 :                                                                  	// Refresh Switchs
			__Switchs[0]=!input(PIN_C0);
			__Switchs[1]=!input(PIN_C1);
			__Switchs[2]=!input(PIN_C2);
			__Switchs[3]=!input(PIN_C5);
			break;
		case 35 :
			if(__Divider==0)
			{
				output_bit( PIN_D7 , 0 );
			}
			break;
		case 100 :
			hemisson_task1();
			break;
		case 120 :
			hemisson_task2();
			break;
		case 140 :
			hemisson_task3();
			break;
		case 160 :
			hemisson_task4();
			break;
		case 200 :                                                                 	// Every 40 ms
			__SchedulerTask=0;
			__Divider++;
			if(__Divider>10)
				__Divider=0;
			break;
	}
	__SchedulerTask++;                                                            		// Task Counter
}

#INT_TIMER2
void RC5_Decoding_Interrupt(void)
{
	switch(__TV_Counter)
	{
		case 1 :
			__StartBit2 = !input(PIN_B4);
			if(__StartBit2 != 1)
			{
				setup_timer_2(T2_DISABLED,0xBB,3);
				disable_interrupts(INT_TIMER2);
				enable_interrupts(INT_RB);
			}
			break;
		case 2 :
			__ToggleBit=!input(PIN_B4);
			break;
		case 3 :
			__SystemBit4 = !input( PIN_B4 );
			if( __SystemBit4 != 0 )
			{
				setup_timer_2(T2_DISABLED,0xBB,3);
				disable_interrupts(INT_TIMER2);
				enable_interrupts( INT_RB );
			}
			break;
		case 4 :
			__SystemBit3 = !input( PIN_B4 );
			if( __SystemBit3 != 0 )
			{
				setup_timer_2(T2_DISABLED,0xBB,3);
				disable_interrupts(INT_TIMER2);
				enable_interrupts( INT_RB );
			}
			break;
		case 5 :
			__SystemBit2 = !input( PIN_B4 );
			if( __SystemBit2 != 0 )
			{
				setup_timer_2(T2_DISABLED,0xBB,3);
				disable_interrupts(INT_TIMER2);
				enable_interrupts( INT_RB );
			}
			break;
		case 6 :
			__SystemBit1 = !input( PIN_B4 );
			if( __SystemBit1 != 0 )
			{
				setup_timer_2(T2_DISABLED,0xBB,3);
				disable_interrupts(INT_TIMER2);
				enable_interrupts( INT_RB );
			}
			break;
		case 7 :
			__SystemBit0 = !input( PIN_B4 );
			if( __SystemBit0 != 0 )
			{
				setup_timer_2(T2_DISABLED,0xBB,3);
				disable_interrupts(INT_TIMER2);
				enable_interrupts( INT_RB );
			}	
			break;
		case 8 :
			__DataBit5 = !input( PIN_B4 );
			break;
		case 9 :
			__DataBit4 = !input( PIN_B4 );
			break;
		case 10 :
			__DataBit3 = !input( PIN_B4 );
			break;
		case 11 :
			__DataBit2 = !input( PIN_B4 );
			break;
		case 12 :
			__DataBit1 = !input( PIN_B4 );
			break;
		case 13 :
			__DataBit0 = !input( PIN_B4 );
			__TV_DATA = ( __DataBit5 << 5 ) | ( __DataBit4 << 4 ) | ( __DataBit3 << 3 ) | ( __DataBit2 << 2 ) | ( __DataBit1 << 1 ) | __DataBit0 ;
			if( __Enable_TV_Remote_Control == 1 )
			{
				__TV_Remote_Control();
			}
			__TV_Data_Available = 1;                                                	// Just a Flag
			setup_timer_2(T2_DISABLED,0xBB,3);
			disable_interrupts(INT_TIMER2);
			enable_interrupts( INT_RB );
			break;
	}
	__TV_Counter++;
}

#separate
__default()
{
	printf( "Unknown Command ?!\r\n" );
}

#separate
__1()
{
	printf("1,");
	switch(__SerialBuffer[ 2 ])
	{
		case '0' :
			set_tris_b(TRISB|0x01);	
			printf("%d\r\n",input(PIN_B0));
			break;
		case '1' :
			set_tris_b(TRISB|0x40);
			printf("%d\r\n",input(PIN_B6));
			break;
		case '2' :
			set_tris_b(TRISB|0x80);	
			printf("%d\r\n",input(PIN_B7));
			break;
		default:
			__default();
			break;
	}
}

#separate
__2()
{
	printf("2\r\n");
	switch(__SerialBuffer[ 2 ])
	{
		case '0' :
			set_tris_b(TRISB & 0xFE);
			output_bit(PIN_B0,__SerialBuffer[ 4 ] - '0');
			break;
		case '1' :
			set_tris_b(TRISB & 0xBF);
			output_bit(PIN_B6,__SerialBuffer[ 4 ] - '0');
			break;
		case '2' :
			set_tris_b(TRISB & 0x7F);
			output_bit(PIN_B7,__SerialBuffer[ 4 ] - '0');
			break;
		default :
			__default();
			break;
	}

}

#separate
__Z()
{
	printf("z\r\n");
	reset_cpu();
}

#separate
__B()
{
	printf( "b,HemiOS_v_%d.%d\r\n" , HEMIOS_VERSION , HEMIOS_REVISION );
}

#separate
__E()
{
	printf("e,%02d,%02d\r\n",__PwmMotLeft,__PwmMotRight);
}

#separate
__J()
{
	unsigned char __i;
	unsigned char __TempVal;
	printf("j");
	for(__i=3;__i<254;__i=__i+2)
	{
		i2c_start();
		if(i2c_write(__i-1)==0)
		{
			i2c_write(0x00);  		// Version Register
			i2c_start();
			i2c_write(__i);
			__TempVal=i2c_read(0);
			i2c_stop();
			printf(",%2x",__i-1);
		}
		else
		{
			i2c_stop();
		}
	}
	printf("\r\n");
}

#separate
__T()
{
	printf( "t,%03u\r\n" , __TV_DATA );
}

#INT_RDA
void Serial_Interrupt(void)
{
	unsigned char __memaddress = 0;
	unsigned char __rcvalue = 0;
	unsigned char __TempVal;
	unsigned char __i;

	while(kbhit())                                                                		// Read while data available
	{
		__SerialBuffer[ __SerialCounter ] = getc();
		if (__SerialCounter < ( __SERIAL_BUFFER_SIZE-1 ))                          	// To discard buffer overflow
		{
			__SerialCounter++;
		}
	}
	if( __SerialBuffer[ __SerialCounter-1 ] == 13 )                               		// '\n'
	{
		//delay_ms(6);                                                             	// Only for HemBasicStamp
		switch( __SerialBuffer[ 0 ] )
		{
			//case 'A' : Unused
			case 'B' :                                                             // HemiOs Version
				__B();
				break;
			//case 'C' : Unused
			case 'D' :                                                             // Set Motors Speed
				if( __SerialBuffer[2] == '-' )
				{
					__PwmMotLeft = - ( __SerialBuffer[3] - '0' );
					if( __SerialBuffer[5] == '-' )
					{
						__PwmMotRight = - ( __SerialBuffer[6] - '0' );
					}
					else
					{
						__PwmMotRight = __SerialBuffer[5] - '0';
					}
				}
				else
				{
					__PwmMotLeft = __SerialBuffer[2] - '0';
					if( __SerialBuffer[4] == '-' )
					{
						__PwmMotRight = - ( __SerialBuffer[5] - '0' );
					}
					else
					{
						__PwmMotRight = __SerialBuffer[4] - '0';
					}
				}
				printf( "d\r\n" );
				break;
			case 'E' :                                                             	// Read Motors Speed
				__E();
				break;
			//case 'F' : Unused
			//case 'G' : Unused
			case 'H' :								// Buzzer
				output_bit( PIN_D4 , __SerialBuffer[ 2 ] - '0' );
				printf( "h\r\n" );
				break;
			case 'I' :								// Read switches
				printf( "i,%d,%d,%d,%d\r\n" , __Switchs[ 0 ] , __Switchs[ 1 ] , __Switchs[ 2 ] , __Switchs[ 3 ] );
				break;
			case 'J' :								// Scan I2C Bus
				__J();
				break;
			//case 'K' : Unused
			case 'L' :								// Set the Leds
				output_bit( PIN_D7 , __SerialBuffer[2] - '0' );
				output_bit( PIN_D5 , __SerialBuffer[4] - '0' );
				output_bit( PIN_D6 , __SerialBuffer[6] - '0' );
				output_bit( PIN_A4 , !(__SerialBuffer[8] - '0') );
				printf( "l\r\n" );
				break;
			case 'M' :								// Get Brightness Zone Sensors
				switch(__SerialBuffer[2])
				{
					case '0' :  // Front Zone
						printf( "m,%03u,%03u,%03u\r\n",__IR_Light[ Front ],__IR_Light[ FrontRight ],__IR_Light[ FrontLeft ] );
						break;
					case '1' :  // Rear Zone
						printf( "m,%03u,%03u,%03u\r\n",__IR_Light[ Right],__IR_Light[ Left],__IR_Light[ Rear ] );
						break;
					case '2' :  // Ground Zone
						printf( "m,%03u,%03u\r\n",__IR_Light[ GroundRight ],__IR_Light[ GroundLeft ]);
						break;
				}
				break;
			case 'N' :     								// Get all Proximity Sensors
				printf( "n,%03u,%03u,%03u,%03u,%03u,%03u,%03u,%03u\r\n" , __IR_Proximity[ Front ] , __IR_Proximity[ FrontRight ] ,
						__IR_Proximity[ FrontLeft ] , __IR_Proximity[ Right] , __IR_Proximity[ Left] , __IR_Proximity[ Rear ] ,
						__IR_Proximity[ GroundRight ] , __IR_Proximity[ GroundLeft ] );
				break;
			case 'O' :								// Get all Brightness Sensors
				printf( "o,%03u,%03u,%03u,%03u,%03u,%03u,%03u,%03u\r\n" , __IR_Light[ Front ] , __IR_Light[ FrontRight ] ,
						__IR_Light[ FrontLeft ] , __IR_Light[ Right] , __IR_Light[ Left] , __IR_Light[ Rear ] ,
						__IR_Light[ GroundRight ] , __IR_Light[ GroundLeft ] );
				break;
			case 'P' :								// Get Proximity Zone Sensors
				switch(__SerialBuffer[2])
				{
					case '0' :  // Front Zone
						printf( "p,%03u,%03u,%03u\r\n",__IR_Proximity[ Front ],__IR_Proximity[ FrontRight ],__IR_Proximity[ FrontLeft ] );
						break;
					case '1' :  // Rear Zone
						printf( "p,%03u,%03u,%03u\r\n",__IR_Proximity[ Right],__IR_Proximity[ Left],__IR_Proximity[ Rear ] );
						break;
					case '2' :  // Ground Zone
						printf( "p,%03u,%03u\r\n",__IR_Proximity[ GroundRight ],__IR_Proximity[ GroundLeft ]);
						break;
				}
				break;
			#ifdef firmware
			case 'Q' : //Reserved for Webots-Hemisson
				__Q();
				break;
			#endif
			case 'R' :								// Read I2C
				__memaddress = chartohex(__SerialBuffer[2]);
				__memaddress = (__memaddress<<4) + chartohex(__SerialBuffer[3]);
				i2c_start();
				i2c_write( __memaddress );                                      // Writing Address of the module
				__rcvalue = chartohex(__SerialBuffer[5]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[6]);
				i2c_write( __rcvalue );                                         // Writing Address of the register
				__rcvalue = chartohex(__SerialBuffer[8]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[9]);
				i2c_start();
				i2c_write( __memaddress + 1 );                                  // Writing Address of the module in Read Mode
				printf("r",__TempVal);
				while(__rcvalue > 1)
				{
					__TempVal = i2c_read();
					printf(",%03u",__TempVal);
					__rcvalue--;
				}
				__TempVal = i2c_read(0);
				printf(",%03u",__TempVal);
				i2c_stop();
				printf("\r\n");
				break;
			#ifdef firmware
			case 'S' : //Reserved for Webots-Hemisson
				__S();
				break;
			#endif
			case 'T' :								// Read TV Remote Sensor
			__T();
			break;
			#ifdef firmware
			case 'U' : //Reserved for Webots-Hemisson
				__U();
				break;
			#endif
			//case 'V' : Unused
			case 'W' :								// Write I2C
				__rcvalue = chartohex(__SerialBuffer[2]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[3]);
				i2c_start();
				i2c_write( __rcvalue );                                         // Writing Address of the module
				__rcvalue = chartohex(__SerialBuffer[5]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[6]);
				i2c_write( __rcvalue );                                         // Writing Address of the register
				__rcvalue = chartohex(__SerialBuffer[8]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[9]);
				i2c_write( __rcvalue );                                         // Writing Data in the register
				i2c_stop();
				printf("w\r\n");
				break;
			#ifdef firmware
			case 'X' : //Reserved for Webots-Hemisson
				__X();
				break;
			#endif
			case 'Y' :                                                        	// Write I2C (Pointer Method)
				__rcvalue = chartohex(__SerialBuffer[2]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[3]);
				i2c_start();
				i2c_write( __rcvalue );                                           // Writing Address of the module
				__rcvalue = chartohex(__SerialBuffer[5]);
				__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[6]);
				i2c_write( __rcvalue );                                           // Writing Address of the register
				__TempVal = chartohex(__SerialBuffer[8]);
				__TempVal = (__TempVal<<4) + chartohex(__SerialBuffer[9]);        // Number of Data to Write
				i2c_write( __TempVal );                                           // Writing Number of Data
				for(__i=0;__i<__TempVal;__i++)
				{
					__rcvalue = chartohex(__SerialBuffer[11+__i+__i+__i]);
					__rcvalue = (__rcvalue<<4) + chartohex(__SerialBuffer[12+__i+__i+__i]);
					i2c_write( __rcvalue );                                        // Writing Data in the register
					//delay_ms(1);						   // Needed for module ???
				}
				i2c_stop();
				printf("y\r\n");
				break;
			case 'Z' :	// Reset
				__Z();
				break;
			case '&' :	// Fast binary read
				printf("&%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\r\n",__PwmMotLeft,__PwmMotRight ,__IR_Proximity[ Front ] , __IR_Proximity[ FrontRight ] ,
						__IR_Proximity[ FrontLeft ] , __IR_Proximity[ Right] , __IR_Proximity[ Left] , __IR_Proximity[ Rear ] ,
						__IR_Proximity[ GroundRight ] , __IR_Proximity[ GroundLeft ] , __IR_Light[ Front ] , __IR_Light[ FrontRight ] ,
						__IR_Light[ FrontLeft ] , __IR_Light[ Right] , __IR_Light[ Left] , __IR_Light[ Rear ] ,
						__IR_Light[ GroundRight ] , __IR_Light[ GroundLeft ] , (__Switchs[ 0 ]|(__Switchs[ 1 ]<<1)|(__Switchs[ 2 ]<<2)|(__Switchs[ 3 ]<<3)) ,
						__TV_DATA , HEMIOS_VERSION, HEMIOS_REVISION );
				break;
			case '*' :	// Fast binary write
				__PwmMotLeft = __SerialBuffer[1];
				__PwmMotRight = __SerialBuffer[2];
				output_bit( PIN_D7 , __SerialBuffer[3]&0x01 );
				output_bit( PIN_D5 , __SerialBuffer[3]&0x02 );
				output_bit( PIN_D6 , __SerialBuffer[3]&0x04 );
				output_bit( PIN_A4 , !(__SerialBuffer[3]&0x08) );
				printf("*\r\n");
				break;
			case '!' :	// To check when sensors has been refreshed
				printf("!%c\r\n",__Sensors_Refreshed_Flag);
				if(__Sensors_Refreshed_Flag == 1)
				{
					__Sensors_Refreshed_Flag = 0;
				}
				break;
			case '1' :     // Read RB0,RB6,RB7
				__1();
				break;
			case '2' :	// Write RB0,RB6,RB7
				__2();
				break;
			default :	// Unknown message command
				__default();  
				break;
		}
		__SerialCounter = 0;
	}
}











