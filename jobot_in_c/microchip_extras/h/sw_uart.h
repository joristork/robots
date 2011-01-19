#ifndef __SW_UART_H
#define __SW_UART_H
/******************************************************************************
 // *                   SW_UART PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		sw_uart.h
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


/* PIC 18 Software UART library header
 *
 * The I/O pins must be specified for the software UART. The
 * corresponding TRIS bits must also be specified. The notation is:
 *     PORTx,y  where x is A,B,C etc. and y is the bit 0-7
 *     TRISx,y  where x is A,B,C etc. and y is the bit 0-7
 * This is in assembly language notation because assembly is
 * used to initialize the software UART.
 */

/* Function Prototypes */

void OpenUART(void);

char ReadUART(void);

void WriteUART( char);

void getsUART( char *,  unsigned char);

void putsUART( char *);

/**************************************************************************
Macro       : getcUART

Description : macro is identical to ReadUART,#define to ReadUART in sw_uart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define getcUART ReadUART

/**************************************************************************
Macro       : putcUART

Description : macro is identical to WriteUART,#define to WriteUART in sw_uart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define putcUART WriteUART

#endif /* __SW_UART_H */
