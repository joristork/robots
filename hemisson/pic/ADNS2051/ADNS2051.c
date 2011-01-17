//-----------------------------------------------------------------------
//
// Filename 	:	ADNS2051.c                                            
// Revision 	:	1.0                                                   
// Created  	:	03-05-2006                                            
// Project  	:	Optical mouse sensor for Hemisson                                              
// Device		:	PIC16F876                                          
// Development	:	MPLAB / CCS PCM 
// Tab setting	:	3                                                
//                                                                   
// Author		:	E.H. Steffens
// Department	:	Instituut voor Informatica, Faculteit NWI
// Copyright	:	Universiteit van Amsterdam
// 
//	Description	:	Serial communication ADNS-2051 optical mouse sensor                        
//-----------------------------------------------------------------------

	
//#include <16F876.H>
#include <16F877.H>
#include <CTYPE.H>
#include "ADNS2051.h"

// Globals
int adns2051[0x12]; // shadow of ADNS2051 registers
long teller;
signed int16 posX, posY;
signed int DeltaX, DeltaY;
signed int lastX[DELTA_LOG_SIZE], lastY[DELTA_LOG_SIZE];
int teller1;

// Fuses
#fuses   HS,NOPROTECT,NOWDT

// Inform the compiler the clock frequency is 8 MHz
//#use delay(clock=8000000) 
#use delay(clock=20000000) 

// Setup the RS232 communication
#use rs232(baud=115200, xmit=PIN_C6, rcv=PIN_C7,  bits=8)

// Bootloader Protection
#org 0x1F00, 0x1FFF {}                                                             


//-----------------------------------------------------------------------
//	load_command(command)
//-----------------------------------------------------------------------
//
void load_command(int j )
{
	int i;
	int command;

	command = j;
	
// Load command
	for(i=0; i < COMMAND_BITS; i++){
		
	// Leading edge of Program clock
		output_low(SCLK);
		
	// put here code to shift command bits out on program data pin
		output_bit(SDIO, shift_left(&command, 1, 0));
		
	// Trailling edge of program clock (data is clocked in ADNS-2051)
		output_high(SCLK);
	
	// Delay
		delay_us(10);
	}
		
}
// -- end of load_command --

//-----------------------------------------------------------------------
//	load_data(int data)
//-----------------------------------------------------------------------
//
void load_data(int data_in)
{
	int i;
	int data;

	data = data_in;

// Load data
	for(i=0; i < DATA_BITS; i++){

	// Leading edge of Program clock
		output_low(SCLK);
		
	// put here code to shift data out on SDIO
		output_bit(SDIO, shift_left(&data, 1, 0 ) );
		
	// Trailling edge of clock (data is clocked in ADNS-2051)
		output_high(SCLK);
		
	// Delay
		delay_us(10);
}
}

//-----------------------------------------------------------------------
//	read_data()
//-----------------------------------------------------------------------
//
long read_data()
{
	int i;
	int data;
	
// Tri-state the SDIO pin
	output_float(SDIO);

// Minium delay between address and reading data
	delay_us(100);
	
// Read data
	for(i=0; i < DATA_BITS; i++){

	// Leading edge of serial clock (data is clocked out ADNS-2051)
		output_low(SCLK);
		
	// Some extra delay before read
		delay_us(10);
		
	// put here code to shift data in on B7
		shift_left(&data, 1, input(SDIO) );
		
	// Trailling edge of serial clock 
		output_high(SCLK);
	}
		return data;
}


//-----------------------------------------------------------------------
//	read_ADNS2051()
//-----------------------------------------------------------------------
//
int read_ADNS2051(int address)
{
	int data;

// Load reister address
	load_command(address);
	data = read_data();
	
	return data;
}
// -- end of read_ADNS2051() --

//-----------------------------------------------------------------------
//	write_ADNS2051()
//-----------------------------------------------------------------------
//
void write_ADNS2051(int address, int data)
{
// Load reister address
	load_command(WRITE | address);
	load_data(data);
	
}
// -- end of write_ADNS2051() --

//-----------------------------------------------------------------------
//	convert()
//-----------------------------------------------------------------------
//
char convert(char c)
{
	if( c >= '0' && c <= '9'){
		return (c - 0x30);
	}
	else if( c >='A' && c <= 'Z'){
		return (c - 0x37);
	}
	else{
		return (c - 0x57);
	}

}// -- end of convert() --

//-----------------------------------------------------------------------
//	timer1_handler()
//-----------------------------------------------------------------------
//
#int_timer1
void timer1_handler() 
{
/* Set interrupt rate */
   set_timer1(65535 - 25000);  // 0.1 sec 

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
	}	
}


//-----------------------------------------------------------------------
//	main()
//-----------------------------------------------------------------------
//

void main() {
	char command_in;
	int i;
	int pixel_data, pixel_address;
	
// Initialisation
	setup_timer_1(T1_INTERNAL | T1_DIV_BY_8);
	enable_interrupts(INT_TIMER1);
	enable_interrupts(GLOBAL);

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
	
	delay_ms(10);

// Init ADNS-2051 pins
	output_high(SDIO);
	output_high(SCLK);
	output_low(PD);	
	
// Print alive message
	printf("ADNS-2051 ");
	printf(__DATE__);
	printf(" @ ");
	printf(__TIME__);
	printf("\r\n");

	
// Begin command loop
	do{

//	Wait for PC to start communication
		command_in = getch() & 0x7F; 

		switch(command_in){
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
		
				
			
			default:
				break;
		}

  }while(TRUE);
}// -- end of command loop --



