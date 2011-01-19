#ifndef __SW_SPI_H
#define __SW_SPI_H
/******************************************************************************
 // *                   SW_SPI PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		sw_spi.h
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


/* PIC18 Software SPI library header
 *
 * To use the software spi routines, the user must define
 * the port and tris register for each of the CS, DIN, DOUT,
 * and SCK pins.  The SPI mode must also be defined (MODE0,
 * MODE1, MODE2, MODE3).
 *
 * Define the port and pin for each of the software SPI pins
 *  - Chip select pin CS must have a port and tris definition.
 *  - Data in pin DIN must have a port and tris definition.
 *  - Data out pin DOUT must have a port and tris definition.
 *  - Clock pin SCK must have a port and tris definition.
 */

#if defined (SW_SPI_IO_V1)
#define SW_CS_PIN         PORTCbits.RC3      // Chip Select
#define TRIS_SW_CS_PIN    TRISCbits.TRISC3
#define SW_DIN_PIN        PORTCbits.RC4     // Data in
#define TRIS_SW_DIN_PIN   TRISCbits.TRISC4
#define SW_DOUT_PIN       PORTCbits.RC6    // Data out
#define TRIS_SW_DOUT_PIN  TRISCbits.TRISC6
#define SW_SCK_PIN        PORTCbits.RC5     // Clock
#define TRIS_SW_SCK_PIN   TRISCbits.TRISC5
 
#else
#define SW_CS_PIN         PORTBbits.RB2      // Chip Select
#define TRIS_SW_CS_PIN    TRISBbits.TRISB2
#define SW_DIN_PIN        PORTBbits.RB3     // Data in
#define TRIS_SW_DIN_PIN   TRISBbits.TRISB3
#define SW_DOUT_PIN       PORTBbits.RB7    // Data out
#define TRIS_SW_DOUT_PIN  TRISBbits.TRISB7
#define SW_SCK_PIN        PORTBbits.RB6     // Clock
#define TRIS_SW_SCK_PIN   TRISBbits.TRISB6
#endif

// Define the mode for software SPI
// Refer to the SPI module for PIC17C756 for definitions of CKP and CKE
// Only one mode can be uncommented, otherwise the software will not work

#ifndef MODE0
#define MODE0  		// Setting for SPI bus Mode 0,0
#endif

//#define MODE1  		// Setting for SPI bus Mode 0,1
//#define MODE2  		// Setting for SPI bus Mode 1,0
//#define MODE3  		// Setting for SPI bus Mode 1,1



void OpenSWSPI(void);
#define SWOpenSPI OpenSWSPI

char WriteSWSPI( char output);
#define SWWriteSPI WriteSWSPI

void SetCSSWSPI(void);
#define SWSetCSSPI SetCSSWSPI

void ClearCSSWSPI(void);
#define SWClearCSSPI ClearCSSWSPI

/**************************************************************************
Macro       : putcSWSPI

Description : macro is identical to WriteSWSPI,#define to WriteSWSPI in sw_spi.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define putcSWSPI WriteSWSPI

/**************************************************************************
Macro       : SWputcSPI

Description : macro is identical to WriteSWSPI,#define to WriteSWSPI in sw_spi.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define SWputcSPI putcSWSPI

#endif /* __SW_SPI_H */
