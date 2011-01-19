#ifndef __USART_H
#define __USART_H
/******************************************************************************
 // *                  USART PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		usart.h
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


/* PIC18 USART peripheral libraries. */

/* There are three library modules, corresponding to register names:
 *  USART1  (TXSTA1, RCSTA1, etc.)
 *  USART2  (TXSTA2, RCSTA2, etc.)
 *  USART  (TXSTA, RCSTA, etc.)
 *  Each module is defined only for those devices for which the register
 *  names are defined.
 */

 /* Corresponding to each module, there are several routines: 
  *
  * The 'open' routine takes two parameters:
  *   - 'config' is the bitwise 'and' of the appropriate configuration
  *     bit masks (defined below);
  *   - 'spbrg' is the baud rate.
  * The registers associated with the USART module are set according to these
  * parameters; then, the transmitter and receiver are enabled.
  *
  * The 'datardy' routine returns 1 if data has been received, 0 otherwise.
  *
  * The 'read' routine returns the received byte.  It also sets the framing
  * and overrun error status bits (FRAME_ERROR & OVERRUN_ERROR) if necessary; 
  * also, the status receive bit 8 (RX_NINE) is significant if 9-bit mode 
  * is enabled.
  * (See status bit structure definition below).
  *
  * The 'write' routine accepts the byte to transmit.  If 9-bit mode is 
  * enabled, the status trasmit bit 8 (TX_NINE) is also trasmitted.
  *
  * The 'gets' routine accepts a buffer and the buffer length in bytes as
  * parameters.  It fills the buffer with bytes as they are received; it will
  * wait for data if necessary in order to fill the entire buffer.
  *
  * The 'puts' routine accepts a null-terminated byte string.  All bytes
  * are transmitted, including the null character.  It will wait until the
  * USART is not busy in order to transmit all the bytes.
  *
  * The 'putrs' routine is identical to 'puts', except the byte string
  * resides in ROM.
  *
  * The 'close' routine disables the receiver, the transmitter, and the 
  * interrupts for both.
  *
  * The 'busy' routine returns 1 if the transmit shift register is not empty;
  * otherwise, it returns 0.
  *
  * For devices with enhanced USART capability, an additional 'baud'
  * routine is provided.  This routine takes a 'config' parameter, which
  * is a bitwise 'and' of the baud configuration bit masks (see below).
  * The BAUDCON (a.k.a. BAUDCTL) register is configured appropriately.
  */

/* Change this to near if building small memory model versions of
 * the libraries. */
#define MEM_MODEL far

/* Configuration bit masks to be 'anded' together and passed as the 'config'
 * parameter to the 'open' routine. */
//-----------AND OR MASK-------------------------------------------------
#ifndef USE_OR_MASKS

#define USART_TX_INT_ON   0b11111111  // Transmit interrupt on
#define USART_TX_INT_OFF  0b01111111  // Transmit interrupt off
#define USART_RX_INT_ON   0b11111111  // Receive interrupt on
#define USART_RX_INT_OFF  0b10111111  // Receive interrupt off
#define USART_BRGH_HIGH   0b11111111  // High baud rate
#define USART_BRGH_LOW    0b11101111  // Low baud rate
#define USART_CONT_RX     0b11111111  // Continuous reception
#define USART_SINGLE_RX   0b11110111  // Single reception
#define USART_SYNC_MASTER 0b11111111  // Synchrounous master mode
#define USART_SYNC_SLAVE  0b11111011  // Synchrounous slave mode
#define USART_NINE_BIT    0b11111111  // 9-bit data
#define USART_EIGHT_BIT   0b11111101  // 8-bit data
#define USART_SYNCH_MODE  0b11111111  // Synchronous mode
#define USART_ASYNCH_MODE 0b11111110  // Asynchronous mode
#define USART_ADDEN_ON    0b11111111  // Enables address detection
#define USART_ADDEN_OFF   0b11011111  // Disables address detection


//------------AND MASK------------------------------------------------
#else
#define USART_TX_INT_ON   		0b10000000  // Transmit interrupt on
#define USART_TX_INT_OFF  		0b00000000  // Transmit interrupt off
#define USART_TX_INT_MASK		(~USART_TX_INT_ON)	//Mask Trnasmit Interrupt select bit

#define USART_RX_INT_ON   		0b01000000  // Receive interrupt on
#define USART_RX_INT_OFF  		0b00000000  // Receive interrupt off
#define USART_RX_INT_MASK		(~USART_RX_INT_ON)	//Mask Receive Interrupt select bit

#define USART_ADDEN_ON    		0b00100000  // Enables address detection
#define USART_ADDEN_OFF   		0b00000000  // Disables address detection
#define USART_ADDEN_MASK		(~USART_ADDEN_ON)	//Mask address detection select bit

#define USART_BRGH_HIGH   		0b00010000  // High baud rate
#define USART_BRGH_LOW    		0b00000000  // Low baud rate
#define USART_BRGH_MASK			(~USART_BRGH_HIGH)	//Mask baud rate select bit

#define USART_CONT_RX     		0b00001000  // Continuous reception
#define USART_SINGLE_RX   		0b00000000  // Single reception
#define USART_CONT_RX_MASK		(~USART_CONT_RX) 	//Mask Continuous Reception select bit

#define USART_SYNC_MASTER 		0b00000100  // Synchrounous master mode
#define USART_SYNC_SLAVE  		0b00000000  // Synchrounous slave mode
#define USART_SYNC_MASK			(~USART_SYNC_MASTER)	//Mask usart mode select bit

#define USART_NINE_BIT    		0b00000010  // 9-bit data
#define USART_EIGHT_BIT   		0b00000000  // 8-bit data
#define USART_BIT_MASK 			(~USART_NINE_BIT)		//Mask 9 bit transmit  select bit

#define USART_SYNCH_MODE  		0b00000001  // Synchronous mode
#define USART_ASYNCH_MODE 		0b00000000  // Asynchronous mode
#define USART_MODE_MASK			(~USART_SYNCH_MODE) 	//Mask sync/async mode select bit

#endif

/* These devices have enhanced USARTs. */
#if defined (EAUSART_V3 ) ||defined (EAUSART_V4 ) ||defined (EAUSART_V5 )||\
    defined (EAUSART_V6 ) ||defined (EAUSART_V7 ) ||defined (EAUSART_V8 ) ||\
	defined (EAUSART_V9 ) ||defined (EAUSART_V10 ) || defined (EAUSART_V11)

/* The baud configuration bit masks to be 'anded' together and passed to
 * the 'baud' routine. */
//--------------AND OR MASK------------------------------
#ifndef USE_OR_MASKS
#define BAUD_IDLE_CLK_HIGH  0b11111111  // idle state for clock is a high level
#define BAUD_IDLE_CLK_LOW   0b11101111  // idle state for clock is a low level
#define BAUD_16_BIT_RATE    0b11111111  // 16-bit baud generation rate
#define BAUD_8_BIT_RATE     0b11110111  // 8-bit baud generation rate
#define BAUD_WAKEUP_ON      0b11111111  // RX pin monitored
#define BAUD_WAKEUP_OFF     0b11111101  // RX pin not monitored
#define BAUD_AUTO_ON        0b11111111  // auto baud rate measurement enabled
#define BAUD_AUTO_OFF       0b11111110  // auto baud rate measurement disabled
//-----------------AND MASK-------------------------------
#else 
#define BAUD_IDLE_CLK_HIGH  0b00010000  // idle state for clock is a high level
#define BAUD_IDLE_CLK_LOW   0b00000000  // idle state for clock is a low level
#define BAUD_IDLE_CLK_MASK	(~BAUD_IDLE_CLK_HIGH)	//Mask Idle Clock State bit

#define BAUD_16_BIT_RATE    0b00001000  // 16-bit baud generation rate
#define BAUD_8_BIT_RATE     0b00000000  // 8-bit baud generation rate
#define BAUD_BIT_RATE       (~BAUD_16_BIT_RATE)		//Mask 16 bit baud rate select bit

#define BAUD_WAKEUP_ON      0b00000010  // RX pin monitored
#define BAUD_WAKEUP_OFF     0b00000000  // RX pin not monitored
#define BAUD_WAKEUP_MASK	(~BAUD_WAKEUP_ON)		//Mask Wake-up Enable bit

#define BAUD_AUTO_ON        0b00000001  // auto baud rate measurement enabled
#define BAUD_AUTO_OFF       0b00000000  // auto baud rate measurement disabled
#define BAUD_AUTO_MASK		(~BAUD_AUTO_ON)			//Mask Auto-Baud Detect Enable bit

#endif

#endif


/* Only these devices have two usart modules: USART1 & USART2. */
#if defined (AUSART_V2) ||defined (EAUSART_V6) ||defined (EAUSART_V7) ||\
    defined (EAUSART_V8) ||defined (EAUSART_V9)  || defined (EAUSART_V10) ||\
	defined (EAUSART_V11)

/* ***** USART1 ***** */

/* status bits */
union USART1
{
  unsigned char val;
  struct
  {
    unsigned RX_NINE:1;         // Receive Bit 8 if 9-bit mode is enabled
    unsigned TX_NINE:1;         // Transmit Bit 8 if 9-bit mode is enabled
    unsigned FRAME_ERROR:1;     // Framing Error for usart
    unsigned OVERRUN_ERROR:1;   // Overrun Error for usart
    unsigned fill:4;
  };
};
extern union USART1 USART1_Status;

void Open1USART ( unsigned char config,  unsigned int spbrg);

#if defined (EAUSART_V7 ) ||defined (EAUSART_V8 )
/***********************************************************************************
Macro       : DataRdy1USART()

Include     : usart.h

Description : Macro returns if data available in the read buffer
 
Arguments   : None
 
Returns     : Returns the status of Receive interrupt flag bit status
***********************************************************************************/
#define DataRdy1USART( ) (PIR1bits.RCIF)
#else
/***********************************************************************************
Macro       : DataRdy1USART()

Include     : usart.h

Description : Macro returns if data available in the read buffer
 
Arguments   : None
 
Returns     : Returns the status of Receive interrupt flag bit status
***********************************************************************************/
#define DataRdy1USART( ) (PIR1bits.RC1IF)
#endif

char Read1USART (void);
void Write1USART ( char data);
void gets1USART ( char *buffer,  unsigned char len);
void puts1USART ( char *data);
void putrs1USART ( const MEM_MODEL rom char *data);

/**************************************************************************
Macro       : getc1USART

Description : macro is identical to Read1USART, #define to Read1USART in usart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define getc1USART Read1USART

/**************************************************************************
Macro       : putc1USART

Description : macro is identical to Write1USART, #define to Write1USART in usart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define putc1USART Write1USART

/***********************************************************************************
Macro       : Close1USART()

Include     : usart.h

Description : Macro Disable the specified usart
 
Arguments   : None
 
Returns     : disables the interrupts, transmitter and receiver for the specified usart
***********************************************************************************/
#define Close1USART( ) RCSTA1&=0b01001111,TXSTA1bits.TXEN=0,PIE1&=0b11001111

/***********************************************************************************
Macro       : Busy1USART()

Include     : usart.h

Description : Macro returns if the usart transmitting or not
 
Arguments   : None
 
Returns     : Returns a value indicating if the usart transmitter is currently busy. 
		This Macro should be used prior to commencing a new transmission
***********************************************************************************/
#define Busy1USART( )  (!TXSTA1bits.TRMT)
#endif

/* ***** USART2 ***** */
#if defined (AUSART_V2 ) ||defined (EAUSART_V6 ) ||defined (EAUSART_V7 ) ||\
    defined (EAUSART_V8 ) ||defined (EAUSART_V9 ) || defined (EAUSART_V11)
/* status bits */
union USART2
{
  unsigned char val;
  struct
  {
    unsigned RX_NINE:1;         // Receive Bit 8 if 9-bit mode is enabled
    unsigned TX_NINE:1;         // Transmit Bit 8 if 9-bit mode is enabled
    unsigned FRAME_ERROR:1;     // Framing Error for usart
    unsigned OVERRUN_ERROR:1;   // Overrun Error for usart
    unsigned fill:4;
  };
};
extern union USART2 USART2_Status;
void Open2USART ( unsigned char config,  unsigned int spbrg);

/***********************************************************************************
Macro       : DataRdy2USART()

Include     : usart.h

Description : Macro returns if data available in the read buffer
 
Arguments   : None
 
Returns     : Returns the status of Receive interrupt flag bit status
***********************************************************************************/
#define DataRdy2USART( ) (PIR3bits.RC2IF)

char Read2USART (void);
void Write2USART ( char data);
void gets2USART ( char *buffer,  unsigned char len);
void puts2USART ( char *data);
void putrs2USART ( const MEM_MODEL rom char *data);

/**************************************************************************
Macro       : getc2USART

Description : macro is identical to Read2USART, #define to Read2USART in usart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define getc2USART Read2USART

/**************************************************************************
Macro       : putc2USART

Description : macro is identical to Write2USART, #define to Write2USART in usart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define putc2USART Write2USART

/***********************************************************************************
Macro       : Close2USART()

Include     : usart.h

Description : Macro Disable the specified usart
 
Arguments   : None
 
Returns     : disables the interrupts, transmitter and receiver for the specified usart
***********************************************************************************/
#define Close2USART( ) RCSTA2&=0b01001111,TXSTA2bits.TXEN=0,PIE3&=0b11001111

/***********************************************************************************
Macro       : Busy2USART()

Include     : usart.h

Description : Macro returns if the usart transmitting or not
 
Arguments   : None
 
Returns     : Returns a value indicating if the usart transmitter is currently busy. 
		This Macro should be used prior to commencing a new transmission
***********************************************************************************/
#define Busy2USART( ) (!TXSTA2bits.TRMT)

#endif

#if defined (AUSART_V1) || defined (EAUSART_V3) || defined (EAUSART_V4) ||\
    defined (EAUSART_V5 )
/* ***** usart (TXSTA, RCSTA, etc.) ***** */
/* status bits */
union USART
{
  unsigned char val;
  struct
  {
    unsigned RX_NINE:1;         // Receive Bit 8 if 9-bit mode is enabled
    unsigned TX_NINE:1;         // Transmit Bit 8 if 9-bit mode is enabled
    unsigned FRAME_ERROR:1;     // Framing Error for usart
    unsigned OVERRUN_ERROR:1;   // Overrun Error for usart
    unsigned fill:4;
  };
};
extern union USART USART_Status;
void OpenUSART ( unsigned char config,  unsigned spbrg);

/***********************************************************************************
Macro       : DataRdyUSART()

Include     : usart.h

Description : Macro returns if data available in the read buffer
 
Arguments   : None
 
Returns     : Returns the status of Receive interrupt flag bit status
***********************************************************************************/
#define DataRdyUSART( ) (PIR1bits.RCIF)

char ReadUSART (void);
void WriteUSART ( char data);
void getsUSART ( char *buffer,  unsigned char len);
void putsUSART ( char *data);
void putrsUSART ( const MEM_MODEL rom char *data);

/**************************************************************************
Macro       : getcUSART

Description : macro is identical to ReadUSART, #define to ReadUSART in usart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define getcUSART ReadUSART

/**************************************************************************
Macro       : putcUSART

Description : macro is identical to WriteUSART, #define to WriteUSART in usart.h
 
Arguments   : None
 
Remarks     : None 
***************************************************************************/
#define putcUSART WriteUSART

/***********************************************************************************
Macro       : CloseUSART()

Include     : usart.h

Description : Macro Disable the specified usart
 
Arguments   : None
 
Returns     : disables the interrupts, transmitter and receiver for the specified usart
***********************************************************************************/
#define CloseUSART( ) RCSTA&=0b01001111,TXSTAbits.TXEN=0,PIE1&=0b11001111

/***********************************************************************************
Macro       : BusyUSART()

Include     : usart.h

Description : Macro returns if the usart transmitting or not
 
Arguments   : None
 
Returns     : Returns a value indicating if the usart transmitter is currently busy. 
		This Macro should be used prior to commencing a new transmission
***********************************************************************************/
#define BusyUSART( ) (!TXSTAbits.TRMT)

#endif

#if defined (EAUSART_V4 ) ||defined (EAUSART_V3 ) ||defined (EAUSART_V5 )
void baudUSART ( unsigned char baudconfig);
#endif


#if defined (EAUSART_V6 )  || defined (EAUSART_V10)
void baud1USART ( unsigned char baudconfig);
#endif

#if defined (EAUSART_V7) ||defined (EAUSART_V8) ||defined (EAUSART_V9) ||\
    defined (EAUSART_V11)
void baud1USART ( unsigned char baudconfig);
void baud2USART ( unsigned char baudconfig);
#endif

/*Macros for backward compatibility*/
#ifndef USE_OR_MASKS
	
#define BAUD_IDLE_RX_PIN_STATE_HIGH    0b11011111  // idle state for RX pin is high level
#define BAUD_IDLE_RX_PIN_STATE_LOW     0b11111111  // idle state for RX pin is low level
#define BAUD_IDLE_TX_PIN_STATE_HIGH    0b11101111  // idle state for TX pin is high level
#define BAUD_IDLE_TX_PIN_STATE_LOW     0b11111111  // idle state for TX pin is low level	
	
#else
#define BAUD_IDLE_RX_PIN_STATE_HIGH    0b00100000  // idle state for RX pin is high level 
#define BAUD_IDLE_RX_PIN_STATE_LOW     0b00000000  // idle state for RX pin is low level
#define BAUD_IDLE_TX_PIN_STATE_HIGH    0b00010000  // idle state for TX pin is high level
#define BAUD_IDLE_TX_PIN_STATE_LOW     0b00000000  // idle state for TX pin is low level
#define BAUD_IDLE_TX_RX_PIN_STATE_MASK (~0b00110000)	//Mask idle tx rx pin state select bit
#endif

#if defined(USART_SFR_V1)
#define BAUDCON BAUDCTL
#endif

#endif
