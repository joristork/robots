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

//----------------------------------------------------------------//
//-                Conversion Function                           -//
// -------------------------------------------------------------- //
char chartohex( char valuetoconvert )
{
	char convertedval;
   	if( valuetoconvert >= 'A' ){
      		convertedval = valuetoconvert-'A'+10;
   	}	
   	else{
      		convertedval = valuetoconvert -'0';
   	}
   	return convertedval;
}

//----------------------------------------------------------------//
//-                Robot Initialisation                          -//
// -------------------------------------------------------------- //
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
	
// Setup_spi(FALSE);
	setup_psp(PSP_DISABLED);
	setup_counters(RTCC_INTERNAL,RTCC_DIV_2);
 	setup_timer_1(T1_DISABLED);
 	setup_timer_2(T2_DISABLED,0,1);
 	setup_ccp1(CCP_OFF);
	setup_ccp2(CCP_OFF);

// I/O's
	set_tris_a(INIT_TRISA);
	set_tris_b(INIT_TRISB);
//	set_tris_c(INIT_TRISC);
	set_tris_d(INIT_TRISD);
	set_tris_e(INIT_TRISE);

	output_a(INIT_PORTA);
	output_b(INIT_PORTB);
//	output_c(INIT_PORTC);
	output_d(INIT_PORTD);
	output_e(INIT_PORTE);

	port_b_pullups(false);

// Analog Port
	setup_adc_ports(ALL_ANALOG);
	setup_adc(ADC_CLOCK_INTERNAL);

// Timer 0 for Scheduler
	setup_timer_0 (RTCC_DIV_4);
	set_timer0(0);
   	
// Welcome Message
	delay_ms(5);			// Delay for HemRadio Module
	printf("HemiOS V%d.%dM , " , HEMIOS_VERSION , HEMIOS_REVISION);
	printf(__DATE__);
	printf(" @ ");
	printf(__TIME__);
	printf("\r\n");

// Interrupts
	enable_interrupts(INT_TIMER0);
	enable_interrupts(INT_TIMER1);
	enable_interrupts(INT_RDA);
	enable_interrupts(GLOBAL);
}

//---------------------------------------------------------------//
//-                        IR Sensors                           -//
//---------------------------------------------------------------//
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


//-----------------------------------------------------------//
//                  Internal Interrupts                     -//
//-----------------------------------------------------------//
#INT_TIMER0
void Scheduler_Interrupt(void)                                                   	
{ 
// Internal Task Manager, Interrupt every 200 us                                                                               		// Sensors Powered during 400us, refreshed every 40ms

// Time function
// __TimeTip incremented every 1 ms (counter up to 4294967296 ms = 1193 hours ...)
	__TimeTipDivider++;

	if(__TimeTipDivider == 5){
		__TimeTipDivider = 0;
		__TimeTip++;                                                               	
	}

// Motor Task, PWM freq 300 Hz
	if(FreeZone == FALSE && IR_armed == TRUE){
	// Stop both motors
		__PwmMotLeft = 0;		
		__PwmMotRight = 0;
	// De-arm trigger
		IR_armed = FALSE;
	}

// Left Motor
	if( __PwmMotLeft >= 0 ){
		if( ( 15 - __PwmMotLeft + __PwmCounter ) >= 15 ){
			output_bit( PIN_D0 , 0 );
			output_bit( PIN_D1 , 0 );
		}
		else{
			output_bit( PIN_D0 , 1 );
			output_bit( PIN_D1 , 0 );
		}
	}
	else{
		if( ( 15 - ( - __PwmMotLeft ) + __PwmCounter ) >= 15 ){
			output_bit( PIN_D0 , 0 );
			output_bit( PIN_D1 , 0 );
		}
		else{
			output_bit( PIN_D0 , 0 );
			output_bit( PIN_D1 , 1 );
		}
	}

// Right Motor
	if( __PwmMotRight >= 0 ){
		if( ( 15 - __PwmMotRight + __PwmCounter ) >= 15 ){
			output_bit( PIN_D2 , 0 );
			output_bit( PIN_D3 , 0 );
		}
		else{
			output_bit( PIN_D2 , 1 );
			output_bit( PIN_D3 , 0 );
		}
	}
	else{
		if( ( 15 - ( - __PwmMotRight ) + __PwmCounter ) >= 15 ){
			output_bit( PIN_D2 , 0 );
			output_bit( PIN_D3 , 0 );
		}
		else{
			output_bit( PIN_D2 , 0 );
			output_bit( PIN_D3 , 1 );
		}
	}

// Counter
	__PwmCounter++;                                                               		
	if( __PwmCounter > 15 ){
		__PwmCounter = 0;
	}

// Other Tasks
	switch( __SchedulerTask )	{
	
	// Brightness measurement zone 1
		case 1 :                                                                   	
			if( __Auto_Refresh_Sensors == 1 ){
				set_adc_channel( GroundLeft );
				delay_us( 12 );
				__IR_Light[ GroundLeft ] = read_adc();
				set_adc_channel( GroundRight );
				delay_us( 12 );
				__IR_Light[ GroundRight ] = read_adc();
				output_high( PIN_B1 );
			}
		break;
	
	// Proximity measurement zone 1	
		case 3 :                                                                   
			if( __Auto_Refresh_Sensors == 1 ){
				__IR_Proximity[ GroundRight ] = __IR_Light[ GroundRight ] - read_adc();
				set_adc_channel( GroundLeft );
				delay_us( 12 );
				__IR_Proximity[ GroundLeft ] = __IR_Light[ GroundRight ] - read_adc();
				output_low( PIN_B1 );
			}
		break;

	// Brightness measurement zone 2
		case 10 :                                                                  	
			if( __Auto_Refresh_Sensors == 1 ){
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

	// Proximity measurement zone 2
		case 12 :                                                                 	
			if( __Auto_Refresh_Sensors == 1 ){
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
	
	// Powered during 4ms every 450ms
		case 15 :
			if(__Divider==0){
				output_bit( PIN_D7 , 1 );
			}
			break;
		
	// Brightness measurement zone 3
		case 20 :                                                                  	
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
		
	// Proximity measurement zone 3
		case 22 :                                                                 	
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
	
	// Refresh Switches
		case 25 :                                                                  	
			__Switchs[0]=!input(PIN_C0);
			__Switchs[1]=!input(PIN_C1);
			__Switchs[2]=!input(PIN_C2);
			__Switchs[3]=!input(PIN_C5);
		break;
		
		case 35 :
			if(__Divider==0){
				output_bit( PIN_D7 , 0 );
			}
		break;
		
		case 100 :
		//hemisson_task1();
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
	
	// Every 40 ms
		case 200 :                                                                 	
			__SchedulerTask=0;
			__Divider++;
			if(__Divider>10)
				__Divider=0;
		break;
	}

// Task Counter
	__SchedulerTask++;
}

#separate
void __default(void)
{
	printf( "Unknown Command ?!\r\n" );
}



#INT_RDA
void Serial_Interrupt(void)
{
	unsigned char __memaddress = 0;
	unsigned char __rcvalue = 0;
	unsigned char __TempVal;
	unsigned char __i;

// Read while data available
	while(kbhit()){
		__SerialBuffer[ __SerialCounter ] = getc();

// To discard buffer overflow
		if(__SerialCounter < ( __SERIAL_BUFFER_SIZE-1 )){                          	
			__SerialCounter++;
		}
	}

	if( __SerialBuffer[ __SerialCounter-1 ] == 13 ){                               		// '\n'
		switch( __SerialBuffer[ 0 ] ){

// HemiOs Version
			case 'B' :                                                             
				printf( "b,HemiOS_v_%d.%d\r\n" , HEMIOS_VERSION , HEMIOS_REVISION );
			break;
	
// Set Motors Speed
			case 'D' :                                                             
				if( __SerialBuffer[2] == '-' ){
					__PwmMotLeft = - ( __SerialBuffer[3] - '0' );
					
					if( __SerialBuffer[5] == '-' ){
						__PwmMotRight = - ( __SerialBuffer[6] - '0' );
					}
					else{
						__PwmMotRight = __SerialBuffer[5] - '0';
					}
				}
				else{
					__PwmMotLeft = __SerialBuffer[2] - '0';
					
					if( __SerialBuffer[4] == '-' ){
						__PwmMotRight = - ( __SerialBuffer[5] - '0' );
					}
					else{
						__PwmMotRight = __SerialBuffer[4] - '0';
					}
				}
				printf( "d\r\n" );
			break;

// Read Motors Speed
			case 'E' :                                                             	
				printf("e,%02d,%02d\r\n",__PwmMotLeft,__PwmMotRight);
			break;

// Buzzer
			case 'H' :								
				output_bit( PIN_D4 , __SerialBuffer[ 2 ] - '0' );
				printf( "h\r\n" );
			break;

// Read switches
			case 'I' :								
				printf( "i,%d,%d,%d,%d\r\n" , __Switchs[ 0 ] , __Switchs[ 1 ] , __Switchs[ 2 ] , __Switchs[ 3 ] );
			break;


// Set the Leds
			case 'L' :								
				output_bit( PIN_D7 , __SerialBuffer[2] - '0' );
				output_bit( PIN_D5 , __SerialBuffer[4] - '0' );
				output_bit( PIN_D6 , __SerialBuffer[6] - '0' );
				output_bit( PIN_A4 , !(__SerialBuffer[8] - '0') );
				printf( "l\r\n" );
			break;

// Get Brightness Zone Sensors
			case 'M' :								
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

// Get all Proximity Sensors
			case 'N' :     								
				printf( "n,%03u,%03u,%03u,%03u,%03u,%03u,%03u,%03u\r\n" , 
						__IR_Proximity[ Front ] , __IR_Proximity[ FrontRight ] ,
						__IR_Proximity[ FrontLeft ] , __IR_Proximity[ Right] , __IR_Proximity[ Left] , __IR_Proximity[ Rear ] ,
						__IR_Proximity[ GroundRight ] , __IR_Proximity[ GroundLeft ] );
			break;

// Get all Brightness Sensors
			case 'O' :								
				printf( "o,%03u,%03u,%03u,%03u,%03u,%03u,%03u,%03u\r\n" , 
						__IR_Light[ Front ] , __IR_Light[ FrontRight ] ,
						__IR_Light[ FrontLeft ] , __IR_Light[ Right] , __IR_Light[ Left] , __IR_Light[ Rear ] ,
						__IR_Light[ GroundRight ] , __IR_Light[ GroundLeft ] );
			break;

// Get Proximity Zone Sensors
			case 'P' :								
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

// Optical mouse commands
		case 'V' :	
			info_ADNS2051(__SerialBuffer[1]);
			//command_in = 0xA;
		break;

// Reset
			case 'Z' :	
				printf("z\r\n");
				reset_cpu();;
			break;

// To check when sensors has been refreshed
			case '!' :	
				printf("!%c\r\n",__Sensors_Refreshed_Flag);
				if(__Sensors_Refreshed_Flag == 1){
					__Sensors_Refreshed_Flag = 0;
				}
			break;

			case '0' :
				command_in = 0;
			break;
				
			case '3' :	// 
				command_in = 3;
			break;

			case '4' :	// 
				command_in = 4;
			break;

			case '5' :	// 
				command_in = 5;
			break;

			case '6' :	// 
				command_in = 6;
			break;

			case '7' :	// 
				command_in = 7;
			break;

			case '8' :	// 
				command_in = 8;
			break;

			case '9' :	// 
				command_in = 9;
			break;

// Unknown message command
			default :	
					__default();
			break;
				
			
		}
		__SerialCounter = 0;
	}
}











