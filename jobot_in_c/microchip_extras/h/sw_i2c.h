#ifndef __SWI2C16_H
#define __SWI2C16_H
/******************************************************************************
 // *                   SW_I2C PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		sw_i2c.h
 * Dependencies:    	See include below
 * Processor:       		PIC18
 * Compiler:        		MCC18
 * Company:         		Microchip Technology, Inc.
 *
 * Software License Agreement
 * The software supplied herewith by Microchip Technology Incorporated
 * (the “Company”) for its PICmicro® Microcontroller is intended and
 * supplied to you, the Company’s customer, for use solely and
 * exclusively on Microchip PICmicro Microcontroller products. The
 * software is owned by the Company and/or its supplier, and is
 * protected under applicable copyright laws. All rights are reserved.
 * Any use in violation of the foregoing restrictions may subject the
 * user to criminal sanctions under applicable laws, as well as to
 * civil liability for the breach of the terms and conditions of this
 * license.
 *
 * THIS SOFTWARE IS PROVIDED IN AN “AS IS” CONDITION. NO WARRANTIES,
 * WHETHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT NOT LIMITED
 * TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE APPLY TO THIS SOFTWARE. THE COMPANY SHALL NOT,
 * IN ANY CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL OR
 * CONSEQUENTIAL DAMAGES, FOR ANY REASON WHATSOEVER.
 *****************************************************************************/
 
#include <pconfig.h>

/* PIC18 software I2C interface header */



// USER NEEDS TO DEFINE DATA AND CLOCK PINS. RESISTORS ARE REQUIRED ON 
// DATA AND CLOCK PINS. 

#if defined(SW_I2C_IO_V1)

#define  DATA_LOW   TRISBbits.TRISB4 = 0; // define macro for data pin output
#define  DATA_HI    TRISBbits.TRISB4 = 1; // define macro for data pin input
#define  DATA_LAT   LATBbits.LATB4        // define macro for data pin latch
#define  DATA_PIN   PORTBbits.RB4         // define macro for data pin

#define  CLOCK_LOW  TRISBbits.TRISB3 = 0; // define macro for clock pin output
#define  CLOCK_HI   TRISBbits.TRISB3 = 1; // define macro for clock pin input
#define  SCLK_LAT   LATBbits.LATB3        // define macro for clock pin latch
#define  SCLK_PIN   PORTBbits.RB3         // define macro for clock pin

#elif defined(SW_I2C_IO_V2) 

#define  DATA_LOW   TRISBbits.TRISB0 = 0; // define macro for data pin output
#define  DATA_HI    TRISBbits.TRISB0 = 1; // define macro for data pin input
#define  DATA_LAT   LATBbits.LATB0        // define macro for data pin latch
#define  DATA_PIN   PORTBbits.RB0         // define macro for data pin

#define  CLOCK_LOW  TRISBbits.TRISB1 = 0; // define macro for clock pin output
#define  CLOCK_HI   TRISBbits.TRISB1 = 1; // define macro for clock pin input
#define  SCLK_LAT   LATBbits.LATB1        // define macro for clock pin latch
#define  SCLK_PIN   PORTBbits.RB1         // define macro for clock pin

#elif defined(SW_I2C_IO_V3)
#define  DATA_LOW   TRISBbits.TRISB5 = 0; // define macro for data pin output
#define  DATA_HI    TRISBbits.TRISB5 = 1; // define macro for data pin input
#define  DATA_LAT   LATBbits.LATB5        // define macro for data pin latch
#define  DATA_PIN   PORTBbits.RB5         // define macro for data pin

#define  CLOCK_LOW  TRISBbits.TRISB4 = 0; // define macro for clock pin output
#define  CLOCK_HI   TRISBbits.TRISB4 = 1; // define macro for clock pin input
#define  SCLK_LAT   LATBbits.LATB4        // define macro for clock pin latch
#define  SCLK_PIN   PORTBbits.RB4         // define macro for clock pin	  

#else

#define  DATA_LOW   TRISCbits.TRISC4 = 0; // define macro for data pin output
#define  DATA_HI    TRISCbits.TRISC4 = 1; // define macro for data pin input
#define  DATA_LAT   LATCbits.LATC4        // define macro for data pin latch
#define  DATA_PIN   PORTCbits.RC4         // define macro for data pin

#define  CLOCK_LOW  TRISCbits.TRISC3 = 0; // define macro for clock pin output
#define  CLOCK_HI   TRISCbits.TRISC3 = 1; // define macro for clock pin input
#define  SCLK_LAT   LATCbits.LATC3        // define macro for clock pin latch
#define  SCLK_PIN   PORTCbits.RC3         // define macro for clock pin
#endif

/*****   FUNCTION PROTOTYPES FOR PIC18CXXX   *****/
void SWStopI2C ( void );
void SWStartI2C ( void );
void SWRestartI2C ( void );
void SWStopI2C ( void );

signed char SWAckI2C( void );
signed char Clock_test( void );
unsigned int SWReadI2C( void );
signed char SWWriteI2C( auto unsigned char data_out );
signed char SWGetsI2C( auto unsigned char *rdptr, auto unsigned char length );
signed char SWPutsI2C( auto unsigned char *wrptr );

/**************************************************************************
Macro       : SWPutcI2C

Description : macro is identical to SWWriteI2C,#define to SWWriteI2C in sw_i2c.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  SWPutcI2C    SWWriteI2C

/**************************************************************************
Macro       : SWGetcI2C

Description : macro is identical to SWReadI2C,#define to SWReadI2C in sw_i2c.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  SWGetcI2C    SWReadI2C

/**************************************************************************
Macro       : SWNotAckI2C

Description : macro is identical to SWAckI2C,#define to SWAckI2C in sw_i2c.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  SWNotAckI2C  SWAckI2C

#endif
