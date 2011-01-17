//-----------------------------------------------------------------------
//
// Filename 	:	main.c                                            
// Revision 	:	1.0                                                   
// Created  	:	08-06-2006                                            
// Project  	:	Optical mouse sensor for Hemisson                                              
// Device		:	PIC16F877                                          
// Development	:	MPLAB / CCS PCM 
// Tab setting	:	3                                                
//                                                                   
// Author		:	E.H. Steffens
// Department	:	Instituut voor Informatica, Faculteit NWI
// Copyright	:	Universiteit van Amsterdam
// 
//	Description	:	Serial communication ADNS-2051 optical mouse sensor                        
//-----------------------------------------------------------------------
#device PIC16F877 *=16 ADC=8	// Use 16 bit pointer for ram address > 255 and ADC = 8 bits resolution

// -- Global Header files (Must be placed at beginning of file)
#include "16F877.H"

// --------------------
// -- Device options --
// --------------------

// -- Fuses --
#fuses HS,NOPROTECT,NOWDT

// -- Processor clock --
#use delay(clock=20000000) 

// -- RS232 communication options --
#use rs232(baud=115200, xmit=PIN_C6, rcv=PIN_C7,  bits=8)

// -- IO ports --
#use fast_io(a)
#use fast_io(b)

#use fast_io(d)
#use fast_io(e)

// -- Bootloader Protection --
#org 0x1F00, 0x1FFF {}                                                             

// -- Local Header files --
#include "constants.h"
#include "ADNS2051.h"

// -- Source File includes --
#include "uart_int.c"
#include "hemisson_functions.c"
#include "adns2051.c"
#include "IRleds.c"

// -- Globals --
int adns2051[0x12]; // shadow of ADNS2051 registers
long teller;
signed int16 posX, posY;
signed int DeltaX, DeltaY;
signed int lastX[DELTA_LOG_SIZE], lastY[DELTA_LOG_SIZE];
int teller1;

#use fast_io(c) 
//-----------------------------------------------------------//
//                  Internal Interrupts                     -//
//-----------------------------------------------------------//
#INT_TIMER0
void Scheduler_Interrupt(void)                                                   	
{ 
// Internal Task Manager, Interrupt every 204.8 us                                                                               		// Sensors Powered during 400us, refreshed every 40ms

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

// Other Tasks executed every 52.43 milliseconds
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
				__Sensors_Refreshed_Flag = 1;
		}
		break;

		case 100 :
		/* Check if border line is crossed */
			if(FreeZone == TRUE){	 
				CheckGroundSensors();
			}
		break;
	}

// Task Counter
	__SchedulerTask++;

}

//-----------------------------------------------------------------------
//	timer1_handler()
//-----------------------------------------------------------------------
//
#int_timer1
void timer1_handler() 
{
/* Set interrupt rate */
   set_timer1(65535 - 62500);  // 0.1 sec 

/* Increment interrupt counter */
   teller++;

/* Check if there was a motion */
	adns2051[MOTION] = read_ADNS2051(MOTION);
	
	if( (adns2051[MOTION] & MOTION_OCCURED) ){
	/* Read ADNS2051 delta X and Y registers */
		DeltaX = read_ADNS2051(DELTA_X);
		DeltaY = read_ADNS2051(DELTA_Y);
	
	/* Log delta's */
		lastX[teller1] = DeltaX;
		lastY[teller1] = DeltaY;

		teller1++;
		if(teller1 == DELTA_LOG_SIZE){
			teller1=0;
		}

	/* Calculate new position */
		posX = posX + DeltaX;	
		posY = posY + DeltaY;
		
	/* Goto position ? */
		//GotoPosY
		//GotoPosX
	}	
}

	
//-----------------------------------------------------------------------
//	main()
//-----------------------------------------------------------------------
//

void main() {
	int i;
	int pixel_data, pixel_address;

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

	posX = 0;
	posY = 0;

	DeltaX = 0;
	DeltaY = 0;

	teller = 0;
	teller1 = 0;

	for(i=0;i<DELTA_LOG_SIZE;i++){
		lastX[i] = 0;
		lastY[i] = 0;
	}

// Initialise GroundSensors section
	IR_trigger = 0;
	IR_threshold = THRESHOLD;
	IR_armed = FALSE;
	FreeZone = TRUE;

// Init 16F877 modules	
	setup_spi(FALSE);
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

// Init ADNS-2051 pins
	output_high(SDIO);
	output_high(SCLK);
	output_low(PD);	

// Analog Port
	setup_adc_ports(ALL_ANALOG);
	setup_adc(ADC_CLOCK_INTERNAL);

// Timer 0 for Scheduler
	setup_timer_0 (RTCC_DIV_4);
	set_timer0(0);

// Timer 1 for mouse sensor 
	setup_timer_1(T1_INTERNAL | T1_DIV_BY_4);
	set_timer1(0);
   	
// Interrupts
	enable_interrupts(INT_TIMER0);
	enable_interrupts(INT_TIMER1);
	enable_interrupts(INT_RDA);
	enable_interrupts(GLOBAL);
	
	delay_ms(10);

// Print alive message
	printf("ADNS-2051M ");
	printf(__DATE__);
	printf(" @ ");
	printf(__TIME__);
	printf("\r\n");

// Stop Hemisson
	hemisson_set_speed(0,0);
	hemisson_delay_s(1);

// Calibrate Ground sensors  (Sensors required to be above Free Zone)
	CalibrateGroundSensors();	
	
// Begin command loop
	do{
// --------------------------------------------------------------------------------		 
// -- 'UART receive command' task manager
// --------------------------------------------------------------------------------
	if(command_in == TRUE){ 

		switch(serial_in[0]){
			case READ_PRODUCT_ID :
			// 
				adns2051[PRODUCT_ID] = read_ADNS2051(PRODUCT_ID);
				printf("Product_id = %x\n\r", adns2051[PRODUCT_ID]);
			break;
			
			case READ_PRODUCT_VERSION :
			// 
				adns2051[REVISION_ID] = read_ADNS2051(REVISION_ID);
				printf("Product_version = %x\n\r", adns2051[REVISION_ID]);
			break;
			
			case READ_MOTION :
			// 
				adns2051[MOTION] = read_ADNS2051(MOTION);
				printf("Motion = %x\n\r", adns2051[MOTION]);
			break;

			case READ_DELTA_X :
			// 
				adns2051[DELTA_X] = read_ADNS2051(DELTA_X);
				printf("Delta_x = %x\n\r", adns2051[DELTA_X]);
				adns2051[DELTA_Y] = read_ADNS2051(DELTA_Y);
				printf("Delta_y = %x\n\r", adns2051[DELTA_Y]);
				adns2051[SURFACE_QUALITY] = read_ADNS2051(SURFACE_QUALITY);
				printf("SURFACE_QUALITY = %x\n\r", adns2051[SURFACE_QUALITY]);
			break;
				
			case READ_AVERAGE_PIXEL :
			// 
				adns2051[AVERAGE_PIXEL] = read_ADNS2051(AVERAGE_PIXEL);
				printf("AVERAGE_PIXEL = %x\n\r", adns2051[AVERAGE_PIXEL]);
				adns2051[MAXIMUM_PIXEL] = read_ADNS2051(MAXIMUM_PIXEL);
				printf("MAXIMUM_PIXEL = %x\n\r", adns2051[MAXIMUM_PIXEL]);
				adns2051[ONFIG_BITS] = read_ADNS2051(ONFIG_BITS);
				printf("ONFIG_BITS = %x\n\r", adns2051[ONFIG_BITS]);
			break;
				
			case READ_SHUTTER :
			// 
				adns2051[SHUTTER_UPPER] = read_ADNS2051(SHUTTER_UPPER);
				printf("SHUTTER_UPPER = %x\n\r", adns2051[SHUTTER_UPPER]);
				adns2051[SHUTTER_LOWER] = read_ADNS2051(SHUTTER_LOWER);
				printf("SHUTTER_LOWER = %x\n\r", adns2051[SHUTTER_LOWER]);
			break;

			case READ_FRAME_PERIOD :
			// 
				adns2051[FRAME_PERIOD_UPPER] = read_ADNS2051(FRAME_PERIOD_UPPER);
				printf("FRAME_PERIOD_UPPER = %x\n\r", adns2051[FRAME_PERIOD_UPPER]);
				adns2051[FRAME_PERIOD_LOWER] = read_ADNS2051(FRAME_PERIOD_LOWER);
				printf("FRAME_PERIOD_LOWER = %x\n\r", adns2051[FRAME_PERIOD_LOWER]);
			break;

			case READ_PIXEL_DUMP :
				write_adns2051(ONFIG_BITS, PIX_DUMP);
				printf("SOD\n\r");
				// Read the pixel map
				for(i=0; i<255; i++){
					do{
						pixel_data = read_adns2051(DATA_OUT_LOWER);
					}while (pixel_data & 0x80);
	
					pixel_address = read_adns2051(DATA_OUT_UPPER); 
					printf("Pixel = %x, %x\n\r", pixel_address, pixel_data); 
				}
				printf("EOD\n\r");
				
				write_adns2051(ONFIG_BITS, LED_MODE_ON);
			
			break;

			case RESET_ADNS2051 :
				write_adns2051(ONFIG_BITS, RESET);
				printf("ADNS2051 reset\n\r");
			break;
	
			case READ_POSITION :
				printf("Pos X,Y : %ld %ld @ %lu\n\r", posX, posY, teller);
			break;

			case READ_POS_BUFFER :
				for(i=0;i<DELTA_LOG_SIZE;i++){
					disable_interrupts(INT_TIMER1);
					printf("X,Y : %d,%d\n\r", lastX[i], lastY[i]);
					enable_interrupts(INT_TIMER1);
				}
			break;

			case RESET_POSITION :
				posX = 0;
				posY = 0;
			break;
			
		// - Recalibrate Ground sensors
			case '1' :
				CalibrateGroundSensors();
			break;
			
		// - Read Ground sensors
			case '2' :
				ReadGroundSensors();
			break;
		
		// - Right turn over 90 degrees
			case '3' :
				Right90();
			break;

		// - Left turn over 90 degrees
			case '4' :
				Left90();
			break;

		// - Reset to Freezone state	  
			case '5' :
				FreeZone = TRUE;
				IR_trigger = 0;
				IR_armed = TRUE;
			break;

		// Go forward for x seconds	   	
			case '6' :
				Forward();
			break;

		// Go reverse for x seconds
			case '7' :
				Reverse();
			break;

		// Set trigger level for IR sensors
			case '8' :
				IR_threshold = chartohex(serial_in[2]);
			break;

		// Go reverse for a half block
			case '9' :
				HalfReverse();
			break;

			default:
				break;
		}
	command_in = FALSE;
	printf("%c\n\r", serial_in[0]);
	}

/* Check if border line is crossed */
//	if(FreeZone == TRUE){	 
//		CheckGroundSensors();
//	}


  }while(TRUE);
}// -- end of command loop --



