#ifndef __MWIRE_H
#define __MWIRE_H
/******************************************************************************
 // *                   MWIRE PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		mwire.h
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

/* In the following SSPx stands for SSP, SSP1 and SSP2 */

/* SSPxSTAT REGISTER */
#define   MODE_01       1              //Setting for SPI bus Mode 0,1 
#define   MODE_11       3              //Setting for SPI bus Mode 1,1

/* SSPxCON1 REGISTER */
#define   SSPENB        0x20           // Enable serial port and configures SCK, SDO, SDI

#define   MWIRE_FOSC_4        0x10     // clock = Fosc/4
#define   MWIRE_FOSC_16       0x11     // clock = Fosc/16
#define   MWIRE_FOSC_64       0x12     // clock = Fosc/64
#define   MWIRE_FOSC_TMR2     0x13     // clock = TMR2 output/2

/* microwire interface definitions */
#if defined(MWIRE_IO_V1)	

#define   MW1_DO        PORTCbits.RC7        // microwire DO to 18Cxxx
#define   MW1_DO_TRIS   TRISCbits.TRISC7
#define   MW1_DI        PORTBbits.RB0        // microwire DI to 18Cxxx
#define   MW1_DI_TRIS   TRISBbits.TRISB0 
#define   MW1_CLK       PORTBbits.RB1        // microwire clock to 18Cxxx
#define   MW1_CLK_TRIS  TRISBbits.TRISB1

#elif defined(MWIRE_IO_V3)

#define   MW1_DO        PORTCbits.RC7        // microwire DO to 18Cxxx
#define   MW1_DO_TRIS   TRISCbits.TRISC7
#define   MW1_DI        PORTBbits.RB5        // microwire DI to 18Cxxx
#define   MW1_DI_TRIS   TRISBbits.TRISB5 
#define   MW1_CLK       PORTBbits.RB4        // microwire clock to 18Cxxx
#define   MW1_CLK_TRIS  TRISBbits.TRISB4

#elif defined(MWIRE_IO_V4)

#define   MW1_DO        PORTBbits.RB0        // microwire DO to 18Cxxx
#define   MW1_DO_TRIS   TRISBbits.TRISB0
#define   MW1_DI        PORTBbits.RB1        // microwire DI to 18Cxxx
#define   MW1_DI_TRIS   TRISBbits.TRISB1 
#define   MW1_CLK       PORTBbits.RB2        // microwire clock to 18Cxxx
#define   MW1_CLK_TRIS  TRISBbits.TRISB2

#else

#define   MW1_DO        PORTCbits.RC5        // microwire DO to 18Cxxx
#define   MW1_DO_TRIS   TRISCbits.TRISC5
#define   MW1_DI        PORTCbits.RC4        // microwire DI to 18Cxxx
#define   MW1_DI_TRIS   TRISCbits.TRISC4 
#define   MW1_CLK       PORTCbits.RC3        // microwire clock to 18Cxxx
#define   MW1_CLK_TRIS  TRISCbits.TRISC3

#endif

#if defined(MWIRE_IO_V2)

#define   MW2_DO        PORTDbits.RD2        // microwire DO to 18Cxxx
#define   MW2_DO_TRIS   TRISDbits.TRISD2
#define   MW2_DI        PORTDbits.RD1        // microwire DI to 18Cxxx
#define   MW2_DI_TRIS   TRISDbits.TRISD1 
#define   MW2_CLK       PORTDbits.RD0        // microwire clock to 18Cxxx
#define   MW2_CLK_TRIS  TRISDbits.TRISD0


#else

#define   MW2_DO        PORTDbits.RD4        // microwire DO to 18Cxxx
#define   MW2_DO_TRIS   TRISDbits.TRISD4
#define   MW2_DI        PORTDbits.RD5        // microwire DI to 18Cxxx
#define   MW2_DI_TRIS   TRISDbits.TRISD5 
#define   MW2_CLK       PORTDbits.RD6        // microwire clock to 18Cxxx
#define   MW2_CLK_TRIS  TRISDbits.TRISD6

#endif

/************************************************************************
Macro              :	DataRdyMwire1() 

Include            : 	mwire.h 

Description     : 	This Macro Indicate whether the Microwirex device has completed the internal write cycle.
                     
Arguments       : 	None 

Remarks           : 	1 if the Microwirex device is ready
			0 if the internal write cycle is not complete or a bus error occurred
*************************************************************************/
#define  DataRdyMwire1()    (MW1_DI)

/************************************************************************
Macro              :	DataRdyMwire2() 

Include            : 	mwire.h 

Description     : 	This Macro Indicate whether the Microwirex device has completed the internal write cycle.
                     
Arguments       : 	None

Return Value       : 	1 if the Microwirex device is ready
			0 if the internal write cycle is not complete or a bus error occurred

Remarks           : 	None
*************************************************************************/
#define  DataRdyMwire2()    (MW2_DI)

/************************************************************************
Macro              :	DataRdyMwire() 

Include            : 	mwire.h 

Description     : 	This Macro Indicate whether the Microwirex device has completed the internal write cycle.
                     
Arguments       : 	None

Return Value       : 	1 if the Microwirex device is ready
			0 if the internal write cycle is not complete or a bus error occurred

Remarks           : 	None
*************************************************************************/
#define  DataRdyMwire()       (MW1_DI)




//************* FUNCTION PROTOTYPES *********************************************

#if defined	(MWIRE_V2) || defined (MWIRE_V4) || defined (MWIRE_V5)

/*********************************************************************
Macro  :  		CloseMwire1()

Include            : 	mwire.h 

Description        : 	This Macro disable the SSPx module

Arguments          : 	None 

Return Value       : 	None 

Remarks            : 	Pin I/O returns under control of the TRISC  and LATC register settings.
*********************************************************************/
#define  CloseMwire1() SSP1CON1&=0xDF
#define  CloseMwire CloseMwire1

void OpenMwire1(  unsigned char sync_mode );
#define OpenMwire OpenMwire1

unsigned char ReadMwire1(  unsigned char high_byte,
                           unsigned char low_byte );
#define ReadMwire ReadMwire1

/**************************************************************************
Macro       : getcMwire1

Description : macro is identical to ReadMwire1,#define to ReadMwire1 in mwire.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  getcMwire1  ReadMwire1
#define  getcMwire getcMwire1

unsigned char WriteMwire1(  unsigned char data_out );
#define WriteMwire WriteMwire1

/**************************************************************************
Macro       : putcMwire1

Description : macro is identical to WriteMwire1,#define to WriteMwire1 in mwire.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  putcMwire1  WriteMwire1
#define  putcMwire putcMwire1

void getsMwire1(  unsigned char *rdptr,  unsigned char length );
#define getsMwire getsMwire1

#endif

/* ***** mwire2 ***** */
#if defined	(MWIRE_V2) || defined (MWIRE_V4)

/*********************************************************************
Macro  :  		CloseMwire2()

Include            : 	mwire.h 

Description        : 	This Macro disable the SSPx module

Arguments          : 	None 

Return Value       : 	None 

Remarks            : 	Pin I/O returns under control of the TRISC  and LATC register settings.
*********************************************************************/
#define  CloseMwire2() SSP2CON1&=0xDF

void OpenMwire2(  unsigned char sync_mode );

unsigned char ReadMwire2(  unsigned char high_byte,
                           unsigned char low_byte );

/**************************************************************************
Macro       : getcMwire2

Description : macro is identical to ReadMwire2,#define to ReadMwire2 in mwire.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  getcMwire2  ReadMwire2

unsigned char WriteMwire2(  unsigned char data_out );

/**************************************************************************
Macro       : putcMwire2

Description : macro is identical to WriteMwire2,#define to WriteMwire2 in mwire.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  putcMwire2  WriteMwire2

void getsMwire2(  unsigned char *rdptr,  unsigned char length );

#endif


#if defined	(MWIRE_V1) || defined (MWIRE_V3)

#if defined	(MWIRE_V3)
/*********************************************************************
Macro  :  		CloseMwire()

Include            : 	mwire.h 

Description        : 	This Macro disable the SSPx module

Arguments          : 	None 

Return Value       : 	None 

Remarks            : 	Pin I/O returns under control of the TRISC  and LATC register settings.
*********************************************************************/
#define  CloseMwire() SSPCON&=0xDF
#else
/*********************************************************************
Macro  :  		CloseMwire()

Include            : 	mwire.h 

Description        : 	This Macro disable the SSPx module

Arguments          : 	None 

Return Value       : 	None 

Remarks            : 	Pin I/O returns under control of the TRISC  and LATC register settings.
*********************************************************************/
#define  CloseMwire() SSPCON1&=0xDF
#endif

void OpenMwire(  unsigned char sync_mode );

unsigned char ReadMwire(  unsigned char high_byte,
                          unsigned char low_byte );

/**************************************************************************
Macro       : getcMwire

Description : macro is identical to ReadMwire,#define to ReadMwire in mwire.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  getcMwire  ReadMwire

unsigned char WriteMwire(  unsigned char data_out );

/**************************************************************************
Macro       : putcMwire

Description : macro is identical to WriteMwire,#define to WriteMwire in mwire.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define  putcMwire  WriteMwire

void getsMwire(  unsigned char *rdptr,  unsigned char length );
#endif
#endif
