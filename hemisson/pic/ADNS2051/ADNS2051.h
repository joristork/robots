///////////////////////////////////////////////////////////////////////////
// Filename 	:	ADSN2051.h                                           
// Revision 	:	1.0                                                   
// Created  	:	03-05-2006                                            
// Project  	:	Optical mouse sensor for Hemisson                                              
// Device		:	PIC16F876                                         
// Development	:	MPLAB/CCSC 
// Tab setting	:	3                                                
//                                                                   
// Author		:	E.H. Steffens
// Department	:	Instituut voor Informatica, Faculteit NWI
// Copyright	:	Universiteit van Amsterdam
// 
//	Description	:	Serial communication ADNS-2051 optical mouse sensor                        
/////////////////////////////////////////////////////////////////////////
#define VERSION							0x1

// ADNS2051 registers address
#define	PRODUCT_ID						0x00
#define	REVISION_ID			 			0x01
#define	MOTION				 			0x02
#define	DELTA_X				 			0x03
#define	DELTA_Y							0x04
#define	SURFACE_QUALITY				0x05
#define	AVERAGE_PIXEL					0x06
#define	MAXIMUM_PIXEL		 			0x07
#define	ONFIG_BITS						0X0A
#define	DATA_OUT_LOWER					0x0C
#define	DATA_OUT_UPPER					0x0D
#define	SHUTTER_LOWER					0x0E
#define	SHUTTER_UPPER					0x0F
#define	FRAME_PERIOD_LOWER			0x10
#define	FRAME_PERIOD_UPPER			0x11

// ADNS2051 register bits
#define	PIX_DUMP							0x08
#define	LED_MODE_ON						0x40
#define	RESET								0x80

#define MOTION_OCCURED					0x80

#define	WRITE								0x80


// Communications defines
#define	READ_PRODUCT_ID				'A'
#define	READ_PRODUCT_VERSION			'B'
#define	READ_MOTION						'C'
#define	READ_DELTA_X					'D'
#define	READ_AVERAGE_PIXEL			'E'
#define	READ_CONFIG_BITS				'F'
#define	READ_SHUTTER					'G'
#define	READ_FRAME_PERIOD				'H'
#define	READ_PIXEL_DUMP				'I'
#define	READ_POSITION					'J'
#define	READ_POS_BUFFER				'K'
#define	RESET_ADNS2051					'R'



// Other defines
#define	COMMAND_BITS					8
#define	DATA_BITS						8
#define	DELTA_LOG_SIZE					20

// ADNS2051 - PIC connections
//#define	SCLK								PIN_A1	// ADNS-2051 Serial clock input
//#define	SDIO								PIN_A2	// ADNS-2051 Data Input/Ouput
//#define	PD									PIN_A3	// ADNS-2051 Power Down input

#define	SCLK								PIN_C3	// ADNS-2051 Serial clock input
#define	SDIO								PIN_C4	// ADNS-2051 Data Input/Ouput
#define	PD									PIN_B0	// ADNS-2051 Power Down input

