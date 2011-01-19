#ifndef __TIMERS_H
#define __TIMERS_H
/******************************************************************************
 // *                  TIMERS PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		timers.h
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

/* PIC18 timers peripheral library. */

/* used to hold 16-bit timer value */
union Timers
{
  unsigned int lt;
  char bt[2];
};


/* Interrupt bit mask to be 'anded' with the other configuration masks and
 * passed as the 'config' parameter to the 'open' routines. */

#ifndef USE_OR_MASKS
#define TIMER_INT_OFF  0b01111111  //Disable TIMER Interrupt
#define TIMER_INT_ON   0b11111111  //Enable TIMER Interrupt

/* ***** TIMER0 ***** */
/* TIMER0 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */
 
#define T0_16BIT       0b10111111  //Timer0 is configured as an 16-bit timer/counter
#define T0_8BIT        0b11111111  //Timer0 is configured as an 8-bit timer/counter

#define T0_SOURCE_INT  0b11011111  //Internal instruction cycle clock (CLKO) acts as source of clock
#define T0_SOURCE_EXT  0b11111111  //Transition on TxCKI pin acts as source of clock

#define T0_EDGE_RISE   0b11101111  //Increment on low-to-high transition on TxCKI pin
#define T0_EDGE_FALL   0b11111111  //Increment on high-to-low transition on TxCKI pin

#define T0_PS_1_1      0b11111111  //1:1 Prescale value (NO Prescaler)
#define T0_PS_1_2      0b11110000  //1:2 Prescale value
#define T0_PS_1_4      0b11110001  //1:4 Prescale value
#define T0_PS_1_8      0b11110010  //1:8 Prescale value
#define T0_PS_1_16     0b11110011  //1:16 Prescale value
#define T0_PS_1_32     0b11110100  //1:32 Prescale value
#define T0_PS_1_64     0b11110101  //1:64 Prescale value
#define T0_PS_1_128    0b11110110  //1:128 Prescale value
#define T0_PS_1_256    0b11110111  //1:256 Prescale value

#else //!USE_OR_MASKS

#define TIMER_INT_OFF  		0b00000000  //Disable TIMER Interrupt
#define TIMER_INT_ON   		0b10000000  //Enable TIMER Interrupt
#define TIMER_INT_MASK		(~TIMER_INT_ON)	//Mask Enable/Disable Timer Interrupt selection bit

/* ***** TIMER0 ***** */
/* TIMER0 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */
 
#define T0_16BIT       		0b00000000  //Timer0 is configured as an 16-bit timer/counter
#define T0_8BIT       		0b01000000  //Timer0 is configured as an 8-bit timer/counter
#define T0_BIT_MASK			(~T0_8BIT)	//Mask Timer0 8-Bit/16-Bit Control bit

#define T0_SOURCE_INT  		0b00000000  //Internal instruction cycle clock (CLKO) acts as source of clock
#define T0_SOURCE_EXT  		0b00100000  //Transition on TxCKI pin acts as source of clock
#define T0_SOURCE_MASK		(~T0_SOURCE_EXT)	//Mask Timer0 Clock Source Select bit

#define T0_EDGE_RISE   		0b00000000  //Increment on low-to-high transition on TxCKI pin
#define T0_EDGE_FALL   		0b00010000  //Increment on high-to-low transition on TxCKI pin
#define T0_EDGE_MASK		(~T0_EDGE_FALL)		//Mask Timer0 Source Edge Select bit

#define T0_PS_1_1      		0b00001000  //1:1 Prescale value (NO Prescaler)
#define	NO_T0_PS_MASK		(~T0_PS_1_1)		//Mask Timer0 Prescaler Assignment bit
		
#define T0_PS_1_2      		0b00000000  //1:2 Prescale value
#define T0_PS_1_4      		0b00000001  //1:4 Prescale value
#define T0_PS_1_8      		0b00000010  //1:8 Prescale value
#define T0_PS_1_16     		0b00000011  //1:16 Prescale value
#define T0_PS_1_32     		0b00000100  //1:32 Prescale value
#define T0_PS_1_64     		0b00000101  //1:64 Prescale value
#define T0_PS_1_128    		0b00000110  //1:128 Prescale value
#define T0_PS_1_256    		0b00000111  //1:256 Prescale value
#define T0_PS_MASK			(~T0_PS_1_256)	//Mask Timer0 Prescaler Select bits

#endif//USE_OR_MASKS

void OpenTimer0 ( unsigned char config);
void CloseTimer0 (void);
unsigned int ReadTimer0 (void);
void WriteTimer0 ( unsigned int timer0);

/* ***** TIMER1 ***** */

/* TIMER1 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */
 
#if defined (TMR_V6) || defined (TMR_V7) || defined (TMR_V7_1) || defined (TMR_V7_2)\
 || defined (TMR_V7_3)

#ifndef USE_OR_MASKS

#define T1_SOURCE_PINOSC 	0b11011111  // Clock source T1OSCEN = 0 Ext clock, T1OSCEN=1 Crystal osc
#define T1_SOURCE_FOSC_4 	0b10011111  //Clock source is instruction clock (FOSC/4)
#define T1_SOURCE_FOSC   	0b10111111  //Closck source is system clock (FOSC)

#define T1_PS_1_1        	0b11100111  // 1:1 prescale value
#define T1_PS_1_2        	0b11101111  // 1:2 prescale value
#define T1_PS_1_4        	0b11110111  // 1:4 prescale value
#define T1_PS_1_8        	0b11111111  // 1:8 prescale value

#define T1_OSC1EN_OFF    	0b11111011  // Timer 1 oscilator enable off
#define T1_OSC1EN_ON     	0b11111111  // Timer 1 oscilator enable on

#define T1_SYNC_EXT_ON      0b11111101  // Synchronize external clock input
#define T1_SYNC_EXT_OFF     0b11111111  // Do not synchronize external clock input

#define T1_8BIT_RW          0b11111110  //Enables register read/write of Timer1 in two 8-bit operations
#define T1_16BIT_RW         0b11111111  //Enables register read/write of Timer1 in one 16-bit operation

#else

#define T1_SOURCE_PINOSC 	0b01000000  // Clock source T1OSCEN = 0 Ext clock, T1OSCEN=1 Crystal osc
#define T1_SOURCE_FOSC_4 	0b00000000  //Clock source is instruction clock (FOSC/4)
#define T1_SOURCE_FOSC   	0b00100000  //Clock source is system clock (FOSC)
#define	T1_SOURCE_MASK	 	(~T1_SOURCE_FOSC)	//Mask Timer1 Clock Source Select bits

#define T1_PS_1_1        	0b00000000  // 1:1 prescale value
#define T1_PS_1_2        	0b00001000  // 1:2 prescale value
#define T1_PS_1_4        	0b00010000  // 1:4 prescale value
#define T1_PS_1_8        	0b00011000  // 1:8 prescale value
#define T1_PS_MASK		 	(~T1_PS_1_8)	//Mask Timer1 Input Clock Prescale Select bits

#define T1_OSC1EN_OFF    	0b00000000  // Timer 1 oscilator enable off
#define T1_OSC1EN_ON     	0b00000100  // Timer 1 oscilator enable on
#define	T1_OSC_MASK		 	(~T1_OSC1EN_ON)		//Mask Timer1 Oscillator Enable bit

#define T1_SYNC_EXT_ON      0b00000000  // Synchronize external clock input
#define T1_SYNC_EXT_OFF     0b00000010  // Do not synchronize external clock input
#define T1_SYNC_MASK	    (~T1_SYNC_EXT_OFF)	// Mask Timer1 External Clock Input Synchronization Select bit

#define T1_8BIT_RW          0b00000000  //Enables register read/write of Timer1 in two 8-bit operations
#define T1_16BIT_RW         0b00000001  //Enables register read/write of Timer1 in one 16-bit operation
#define T1_BIT_RW_MASK	    (~T1_16BIT_RW)		// Mask Timer1 16-Bit Read/Write Mode Enable bit

#endif


void OpenTimer1 ( unsigned char config,  unsigned char config1);
void CloseTimer1 (void);
unsigned int ReadTimer1 (void);
void WriteTimer1 ( unsigned int timer1);

#else 


#ifndef	USE_OR_MASKS

#define T1_8BIT_RW       0b10111111  //Enables register read/write of Timer1 in two 8-bit operations
#define T1_16BIT_RW      0b11111111  //Enables register read/write of Timer1 in one 16-bit operation
#define T1_PS_1_1        0b11001111  // 1:1 prescale value
#define T1_PS_1_2        0b11011111  // 1:2 prescale value
#define T1_PS_1_4        0b11101111  // 1:4 prescale value
#define T1_PS_1_8        0b11111111  // 1:8 prescale value
#define T1_OSC1EN_OFF    0b11110111  // Timer 1 oscilator enable off
#define T1_OSC1EN_ON     0b11111111  // Timer 1 oscilator enable on
#define T1_SYNC_EXT_ON   0b11111011  // Synchronize external clock input
#define T1_SYNC_EXT_OFF  0b11111111  // Do not synchronize external clock input
#define T1_SOURCE_INT    0b11111101  //Internal instruction cycle clock (CLKO) acts as source of clock
#define T1_SOURCE_EXT    0b11111111  //Transition on TxCKI pin acts as source of clock

#else //!USE_OR_MASKS

#define T1_8BIT_RW       0b00000000  //Enables register read/write of Timer1 in two 8-bit operations
#define T1_16BIT_RW      0b01000000  //Enables register read/write of Timer1 in one 16-bit operation
#define T1_BIT_RW_MASK	 (~T1_16BIT_RW)		// Mask Timer1 16-Bit Read/Write Mode Enable bit

#define T1_PS_1_1        0b00000000  // 1:1 prescale value
#define T1_PS_1_2        0b00010000  // 1:2 prescale value
#define T1_PS_1_4        0b00100000  // 1:4 prescale value
#define T1_PS_1_8        0b00110000  // 1:8 prescale value
#define T1_PS_MASK		 (~T1_PS_1_8)		//Mask Timer1 Input Clock Prescale Select bits
	
#define T1_OSC1EN_OFF    0b00000000  // Timer 1 oscilator enable off
#define T1_OSC1EN_ON     0b00001000  // Timer 1 oscilator enable on
#define	T1_OSC_MASK		 (~T1_OSC1EN_ON)	//Mask Timer1 Oscillator Enable bit

#define T1_SYNC_EXT_ON   0b00000000  // Synchronize external clock input
#define T1_SYNC_EXT_OFF  0b00000100  // Do not synchronize external clock input
#define T1_SYNC_MASK	 (~T1_SYNC_EXT_OFF)	// Mask Timer1 External Clock Input Synchronization Select bit

#define T1_SOURCE_INT    0b00000000  //Internal instruction cycle clock (CLKO) acts as source of clock
#define T1_SOURCE_EXT	 0b00000010  //Transition on TxCKI pin acts as source of clock
#define	T1_SOURCE_MASK	 (~T1_SOURCE_EXT)	//Mask Timer1 Clock Source Select bits



#endif //USE_OR_MASKS

void OpenTimer1 ( unsigned char config);
void CloseTimer1 (void);
unsigned int ReadTimer1 (void);
void WriteTimer1 ( unsigned int timer1);
#endif


/* ***** TIMER2 ***** */
#if defined (TMR_V2) || defined (TMR_V3) || defined (TMR_V4) ||\
    defined (TMR_V5) || defined (TMR_V6) || defined (TMR_V7) || defined (TMR_V7_1) ||\
	defined (TMR_V7_2) || defined (TMR_V7_3)
/* TIMER2 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */
//-----------------AND OR MASK----------------------
#ifndef USE_OR_MASKS

#define T2_POST_1_1    0b10000111  //Timer2 Postscaler 1:1
#define T2_POST_1_2    0b10001111  //Timer2 Postscaler 1:2
#define T2_POST_1_3    0b10010111  //Timer2 Postscaler 1:3
#define T2_POST_1_4    0b10011111  //Timer2 Postscaler 1:4
#define T2_POST_1_5    0b10100111  //Timer2 Postscaler 1:5
#define T2_POST_1_6    0b10101111  //Timer2 Postscaler 1:6
#define T2_POST_1_7    0b10110111  //Timer2 Postscaler 1:7
#define T2_POST_1_8    0b10111111  //Timer2 Postscaler 1:8
#define T2_POST_1_9    0b11000111  //Timer2 Postscaler 1:9
#define T2_POST_1_10   0b11001111  //Timer2 Postscaler 1:10
#define T2_POST_1_11   0b11010111  //Timer2 Postscaler 1:11
#define T2_POST_1_12   0b11011111  //Timer2 Postscaler 1:12
#define T2_POST_1_13   0b11100111  //Timer2 Postscaler 1:13
#define T2_POST_1_14   0b11101111  //Timer2 Postscaler 1:14
#define T2_POST_1_15   0b11110111  //Timer2 Postscaler 1:15
#define T2_POST_1_16   0b11111111  //Timer2 Postscaler 1:16
#define T2_PS_1_1      0b11111100  //Timer2 Prescale 1:1
#define T2_PS_1_4      0b11111101  //Timer2 Prescale 1:4
#define T2_PS_1_16     0b11111110  //Timer2 Prescale 1:16

#else //!USE_OR_MASKS

#define T2_POST_1_1    0b00000000  //Timer2 Postscaler 1:1
#define T2_POST_1_2    0b00001000  //Timer2 Postscaler 1:2
#define T2_POST_1_3    0b00010000  //Timer2 Postscaler 1:3
#define T2_POST_1_4    0b00011000  //Timer2 Postscaler 1:4
#define T2_POST_1_5    0b00100000  //Timer2 Postscaler 1:5
#define T2_POST_1_6    0b00101000  //Timer2 Postscaler 1:6
#define T2_POST_1_7    0b00110000  //Timer2 Postscaler 1:7
#define T2_POST_1_8    0b00111000  //Timer2 Postscaler 1:8
#define T2_POST_1_9    0b01000000  //Timer2 Postscaler 1:9
#define T2_POST_1_10   0b01001000  //Timer2 Postscaler 1:10
#define T2_POST_1_11   0b01010000  //Timer2 Postscaler 1:11
#define T2_POST_1_12   0b01011000  //Timer2 Postscaler 1:12
#define T2_POST_1_13   0b01100000  //Timer2 Postscaler 1:13
#define T2_POST_1_14   0b01101000  //Timer2 Postscaler 1:14
#define T2_POST_1_15   0b01110000  //Timer2 Postscaler 1:15
#define T2_POST_1_16   0b01111000  //Timer2 Postscaler 1:16
#define	T2_POST_MASK	(~T2_POST_1_16)	//Mask Timer2 Postscale Selection bits

#define T2_PS_1_1      0b00000000  //Timer2 Prescale 1:1
#define T2_PS_1_4      0b00000001  //Timer2 Prescale 1:4
#define T2_PS_1_16     0b00000011  //Timer2 Prescale 1:16
#define T2_PS_MASK 		(~T2_PS_1_16)	//Mask Timer2 Input Clock Prescale Select bits

#endif //!USE_OR_MASKS

/***********************************************************************************
Macro       : WriteTimer2(timer2)

Include     : timers.h

Description : Macro writes the parameter passed to TIMER2 count register TMR2
 
Arguments   : timer2 : value to be written to TIMER2 count register TMR2
 
Remarks     : None
***********************************************************************************/
#define WriteTimer2(timer2) TMR2 = (timer2)

/***********************************************************************************
Macro       : ReadTimer2()

Include     : timers.h

Description : Macro returns the TMR2 register value of TIMER2
 
Arguments   : None
 
Returns     : TMR2 value
***********************************************************************************/
#define ReadTimer2()	    TMR2

void OpenTimer2 ( unsigned char config);
void CloseTimer2 (void);
//unsigned char ReadTimer2 (void);
#endif
/* ***** TIMER3 ***** */

#if defined(TMR_V1)  || defined(TMR_V2)  || defined(TMR_V4) 

/* TIMER3 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */
 
#ifndef USE_OR_MASKS

#define T3_8BIT_RW      0b11111110 //Enables register read/write of Timer3 in two 8-bit operations
#define T3_16BIT_RW     0b11111111 //Enables register read/write of Timer3 in two 16-bit operations
#define T3_PS_1_1       0b11001111 //Timer3 1:1 prescale value
#define T3_PS_1_2       0b11011111 //Timer3 1:2 prescale value
#define T3_PS_1_4       0b11101111 //Timer3 1:4 prescale value
#define T3_PS_1_8       0b11111111 //Timer3 1:8 prescale value
#define T3_SYNC_EXT_ON  0b11111011 // Synchronize external clock input
#define T3_SYNC_EXT_OFF 0b11111111 // Do not synchronize external clock input
#define T3_SOURCE_INT   0b11111101 //Internal instruction cycle clock (CLKO) acts as source of clock
#define T3_SOURCE_EXT   0b11111111 //Transition on TxCKI pin acts as source of clock

#else //!USE_OR_MASKS

#define T3_8BIT_RW      0b00000000 //Enables register read/write of Timer3 in two 8-bit operations
#define T3_16BIT_RW     0b00000001 //Enables register read/write of Timer3 in two 16-bit operations
#define T3_BIT_MASK		(~T3_16BIT_RW)	// Mask Timer3 16-Bit Read/Write Mode Enable bit

#define T3_PS_1_1       0b00000000 //Timer3 1:1 prescale value
#define T3_PS_1_2       0b00010000 //Timer3 1:2 prescale value
#define T3_PS_1_4       0b00100000 //Timer3 1:4 prescale value
#define T3_PS_1_8       0b00110000 //Timer3 1:8 prescale value
#define T3_PS_MASK		(~T3_PS_1_8)	//Mask Timer3 Input Clock Prescale Select bits

#define T3_SYNC_EXT_ON  0b00000000 // Synchronize external clock input
#define T3_SYNC_EXT_OFF 0b00000100 // Do not synchronize external clock input
#define T3_SYNC_MASK	(~T3_SYNC_EXT_OFF)	// Mask Timer3 External Clock Input Synchronization Select bit

#define T3_SOURCE_INT   0b00000000 //Internal instruction cycle clock (CLKO) acts as source of clock
#define T3_SOURCE_EXT   0b00000010 //Transition on TxCKI pin acts as source of clock
#define T3_SOURCE_MASK	(~T3_SOURCE_EXT)	//Mask Timer3 Clock Source Select bits


#endif //USE_OR_MASKS

//-----------------------------------------------------------------------------------------------------------
/************************************************************************
Macro        : T3_OSC1EN_ON
Overview    : sets the T1OSCEN bit in the T1CON 
Parameters  : None 
Remarks     : None.
**************************************************************************/ 
#define T3_OSC1EN_ON()   T1CONbits.T1OSCEN=1

/************************************************************************
Macro        : T3_OSC1EN_OFF
Overview    : Clears the T1OSCEN bit in the T1CON 
Parameters  : None 
Remarks     : None.
**************************************************************************/ 
#define T3_OSC1EN_OFF()   T1CONbits.T1OSCEN=0



void OpenTimer3 ( unsigned char config);
void CloseTimer3 (void);
unsigned int ReadTimer3 (void);
void WriteTimer3 ( unsigned int timer3);

#elif defined (TMR_V6) || defined (TMR_V7) || defined (TMR_V7_1) ||\
 defined (TMR_V7_2) || defined (TMR_V7_3)

#ifndef USE_OR_MASKS

#define T3_SOURCE_PINOSC 	0b11011111  // Clock source T1OSCEN = 0 Ext clock, T1OSCEN=1 Crystal osc
#define T3_SOURCE_FOSC_4 	0b10011111  //Clock source is instruction clock (FOSC/4)
#define T3_SOURCE_FOSC   	0b10111111  //Closck source is system clock (FOSC)

#define T3_PS_1_1        	0b11100111  //Timer3 1:1 prescale value
#define T3_PS_1_2        	0b11101111  //Timer3 1:2 prescale value
#define T3_PS_1_4        	0b11110111  //Timer3 1:4 prescale value
#define T3_PS_1_8        	0b11111111  //Timer3 1:8 prescale value

#define T3_OSC1EN_OFF    	0b11111011  // Timer1  oscilator disabled which is used by Timer3
#define T3_OSC1EN_ON     	0b11111111  // Timer1  oscilator enabled which is used by Timer3

#define T3_SYNC_EXT_ON      0b11111101  // Synchronize external clock input
#define T3_SYNC_EXT_OFF     0b11111111  // Do not synchronize external clock input

#define T3_8BIT_RW          0b11111110  //Enables register read/write of Timer3 in two 8-bit operations
#define T3_16BIT_RW         0b11111111  //Enables register read/write of Timer3 in two 16-bit operations

#else

#define T3_SOURCE_PINOSC 	0b01000000  // Clock source T1OSCEN = 0 Ext clock, T1OSCEN=1 Crystal osc
#define T3_SOURCE_FOSC_4 	0b00000000  //Clock source is instruction clock (FOSC/4)
#define T3_SOURCE_FOSC   	0b00100000  //Closck source is system clock (FOSC)
#define	T3_SOURCE_MASK	 	(~T3_SOURCE_FOSC)	//Mask Timer3 Clock Source Select bits

#define T3_PS_1_1        	0b00000000  //Timer3 1:1 prescale value
#define T3_PS_1_2        	0b00001000  //Timer3 1:2 prescale value
#define T3_PS_1_4        	0b00010000  //Timer3 1:4 prescale value
#define T3_PS_1_8        	0b00011000  //Timer3 1:8 prescale value
#define T3_PS_MASK		 	(~T3_PS_1_8)		//Mask Timer3 Input Clock Prescale Select bits

#define T3_OSC1EN_OFF    	0b00000000  // Timer1  oscilator disabled which is used by Timer3
#define T3_OSC1EN_ON     	0b00000100  // Timer1  oscilator enabled which is used by Timer3
#define	T3_OSC_MASK		 	(~T3_OSC1EN_ON)		//Mask Timer3 Oscillator Source Select bit

#define T3_SYNC_EXT_ON      0b00000000  // Synchronize external clock input
#define T3_SYNC_EXT_OFF     0b00000010  // Do not synchronize external clock input
#define T3_SYNC_MASK	    (~T3_SYNC_EXT_OFF)	// Mask Timer3 External Clock Input Synchronization Select bit

#define T3_8BIT_RW          0b00000000  //Enables register read/write of Timer3 in two 8-bit operations
#define T3_16BIT_RW         0b00000001  //Enables register read/write of Timer3 in two 16-bit operations
#define T3_BIT_RW_MASK	    (~T3_16BIT_RW)		// Mask Timer3 16-Bit Read/Write Mode Enable bit

#endif

void OpenTimer3 ( unsigned char config,  unsigned char config1);
void CloseTimer3 (void);
unsigned int ReadTimer3 (void);
void WriteTimer3 ( unsigned int timer3);
#endif

/* ***** TIMER4 ***** */

#if defined(TMR_V4) || defined(TMR_V6) || defined (TMR_V7) || defined (TMR_V7_1)\
 ||defined (TMR_V7_2) || defined (TMR_V7_3)

/* TIMER4 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */

#ifndef USE_OR_MASKS

#define T4_POST_1_1    0b10000111  //Timer4 Postscaler 1:1
#define T4_POST_1_2    0b10001111  //Timer4 Postscaler 1:2
#define T4_POST_1_3    0b10010111  //Timer4 Postscaler 1:3
#define T4_POST_1_4    0b10011111  //Timer4 Postscaler 1:4
#define T4_POST_1_5    0b10100111  //Timer4 Postscaler 1:5
#define T4_POST_1_6    0b10101111  //Timer4 Postscaler 1:6
#define T4_POST_1_7    0b10110111  //Timer4 Postscaler 1:7
#define T4_POST_1_8    0b10111111  //Timer4 Postscaler 1:8
#define T4_POST_1_9    0b11000111  //Timer4 Postscaler 1:9
#define T4_POST_1_10   0b11001111  //Timer4 Postscaler 1:10
#define T4_POST_1_11   0b11010111  //Timer4 Postscaler 1:11
#define T4_POST_1_12   0b11011111  //Timer4 Postscaler 1:12
#define T4_POST_1_13   0b11100111  //Timer4 Postscaler 1:13
#define T4_POST_1_14   0b11101111  //Timer4 Postscaler 1:14
#define T4_POST_1_15   0b11110111  //Timer4 Postscaler 1:15
#define T4_POST_1_16   0b11111111  //Timer4 Postscaler 1:16
#define T4_PS_1_1      0b11111100  //Timer4 Prescale 1:1
#define T4_PS_1_4      0b11111101  //Timer4 Prescale 1:4
#define T4_PS_1_16     0b11111111  //Timer4 Prescale 1:16

#else //!USE_OR_MASKS

#define T4_POST_1_1    0b00000000  // Timer4 Postscaler 1:1
#define T4_POST_1_2    0b00001000  // Timer4 Postscaler 1:2
#define T4_POST_1_3    0b00010000  // Timer4 Postscaler 1:3
#define T4_POST_1_4    0b00011000  // Timer4 Postscaler 1:4
#define T4_POST_1_5    0b00100000  // Timer4 Postscaler 1:5
#define T4_POST_1_6    0b00101000  // Timer4 Postscaler 1:6
#define T4_POST_1_7    0b00110000  // Timer4 Postscaler 1:7
#define T4_POST_1_8    0b00111000  // Timer4 Postscaler 1:8
#define T4_POST_1_9    0b01000000  // Timer4 Postscaler 1:9
#define T4_POST_1_10   0b01001000  // Timer4 Postscaler 1:10
#define T4_POST_1_11   0b01010000  // Timer4 Postscaler 1:11
#define T4_POST_1_12   0b01011000  // Timer4 Postscaler 1:12
#define T4_POST_1_13   0b01100000  // Timer4 Postscaler 1:13
#define T4_POST_1_14   0b01101000  // Timer4 Postscaler 1:14
#define T4_POST_1_15   0b01110000  // Timer4 Postscaler 1:15
#define T4_POST_1_16   0b01111000  // Timer4 Postscaler 1:16
#define T4_POST_MASK	(~T4_POST_1_16)		//Mask Timer4 Postscale Select bits

#define T4_PS_1_1      0b00000000  //Timer4 Prescale 1:1
#define T4_PS_1_4      0b00000001  //Timer4 Prescale 1:4
#define T4_PS_1_16     0b00000011  //Timer4 Prescale 1:16
#define T4_PS_MASK		(~T4_PS_1_16)		//Mask Timer4 Input Clock Prescale Select bits

#endif //USE_OR_MASKS

/***********************************************************************************
Macro       : WriteTimer4(timer4)

Include     : timers.h

Description : Macro writes the parameter passed to TIMER4 count register TMR4
 
Arguments   : timer4 : value to be written to TIMER4 count register TMR4
 
Remarks     : None
***********************************************************************************/
#define WriteTimer4(timer4) TMR4 = (timer4)

/***********************************************************************************
Macro       : ReadTimer4()

Include     : timers.h

Description : Macro returns the TMR4 register value of TIMER4
 
Arguments   : None
 
Returns     : TMR4 value
***********************************************************************************/
#define ReadTimer4()	    TMR4

void OpenTimer4 ( unsigned char config);
void CloseTimer4 (void);
//unsigned char ReadTimer4 (void);

#endif

//-------- TIMER 5 DECLARATION ---------------------
#if defined (TMR_V5)

#ifndef USE_OR_MASKS

#define T5_SLP_EN       	0b11111111   //Timer5 Enable during sleep
#define T5_SLP_DIS      	0b01111111   //Timer5 Disable during sleep 
#define T5_SP_EVNT_REN  	0b10111111   //Timer5 special event reset enable
#define T5_SP_EVNT_RDIS 	0b11111111   //Timer5 special event reset disable
#define T5_MD_SNGL_SHOT  	0b11111111   //Timer5 SINGLE SHOT MODE
#define T5_MD_CONT_COUNT 	0b11011111   //Timer5 CONTINOUS COUNT MODE
#define T5_PS_1_1      		0b11100111   //Timer5 Prescale 1:1
#define T5_PS_1_2      		0b11101111   //Timer5 Prescale 1:2
#define T5_PS_1_4      		0b11110111   //Timer5 Prescale 1:4
#define T5_PS_1_8      		0b11111111   //Timer5 Prescale 1:8
#define T5_EX_CLK_SYNC   	0b11111011   // Synchronize external clock input
#define T5_EX_CLK_NOSYNC 	0b11111111   // Do not synchronize external clock input
#define T5_CLK_EXTRN  		0b11111111   //Transition on TxCKI pin acts as source of clock
#define T5_CLK_INT	  		0b11111101   //Internal instruction cycle clock (CLKO) acts as source of clock

#else //!USE_OR_MASKS

#define T5_SLP_EN       	0b10000000   //Timer5 Enable during sleep
#define T5_SLP_DIS      	0b00000000   //Timer5 Disable during sleep
#define T5_SLP_MASK			(~T5_SLP_EN)	//Mask Timer5 Sleep Enable bit

#define T5_SP_EVNT_REN  	0b00000000   //Timer5 special event reset enable
#define T5_SP_EVNT_RDIS 	0b01000000   //Timer5 special event reset disable
#define T5_SP_ENNT_MASK		(~T5_SP_EVNT_RDIS)	//Mask TIMER5 Special Event Trigger Reset Enable bit

#define T5_MD_SNGL_SHOT  	0b00100000   //Timer5 SINGLE SHOT MODE
#define T5_MD_CONT_COUNT 	0b00000000   //Timer5 CONTINOUS COUNT MODE
#define T5_MD_MASK			(~T5_MD_SNGL_SHOT)	//Mask Timer5 Mode bit

#define T5_PS_1_1      		0b00000000   //Timer5 Prescale 1:1
#define T5_PS_1_2      		0b00001000   //Timer5 Prescale 1:2
#define T5_PS_1_4      		0b00010000   //Timer5 Prescale 1:4
#define T5_PS_1_8      		0b00011000   //Timer5 Prescale 1:8
#define T5_PS_MASK			(~T5_PS_1_8)	//Mask Timer5 Input Clock Prescale Select bits

#define T5_EX_CLK_SYNC   	0b00000000   // Synchronize external clock input
#define T5_EX_CLK_NOSYNC 	0b00000100   // Do not synchronize external clock input
#define T5_EX_CLK_SYNC_MASK	(~T5_EX_CLK_NOSYNC)		// Mask Timer5 External Clock Input Synchronization Select bit

#define T5_CLK_EXTRN  		0b00000010   //Transition on TxCKI pin acts as source of clock
#define T5_CLK_INT	  		0b00000000   //Internal instruction cycle clock (CLKO) acts as source of clock
#define T5_CLK_SOURCE_MASK	(~T5_CLK_EXTRN)		//Mask Timer5 Clock Source Select bits

#endif	//USE_OR_MASKS

void CloseTimer5(void);
void OpenTimer5( unsigned char,  unsigned int);
unsigned int ReadTimer5(void);
void WriteTimer5( unsigned int);

#elif defined (TMR_V7) || defined (TMR_V7_1) || defined (TMR_V7_3)

#ifndef USE_OR_MASKS

#define T5_SOURCE_PINOSC 	0b11011111  // Clock source SOSCEN = 0 Ext clock, SOSCEN=1 Crystal osc
#define T5_SOURCE_FOSC_4 	0b10011111  //Clock source is instruction clock (FOSC/4)
#define T5_SOURCE_FOSC   	0b10111111  //Closck source is system clock (FOSC)

#define T5_PS_1_1        	0b11100111  //Timer5 1:1 prescale value
#define T5_PS_1_2        	0b11101111  //Timer5 1:2 prescale value
#define T5_PS_1_4        	0b11110111  //Timer5 1:4 prescale value
#define T5_PS_1_8        	0b11111111  //Timer5 1:8 prescale value

#define T5_OSC1EN_OFF    	0b11111011  // Timer1  oscilator disabled which is used by Timer5
#define T5_OSC1EN_ON     	0b11111111  // Timer1  oscilator enabled which is used by Timer5

#define T5_SYNC_EXT_ON      0b11111101  // Synchronize external clock input
#define T5_SYNC_EXT_OFF     0b11111111  // Do not synchronize external clock input

#define T5_8BIT_RW          0b11111110  //Enables register read/write of Timer5 in two 8-bit operations
#define T5_16BIT_RW         0b11111111  //Enables register read/write of Timer5 in two 16-bit operations

#else

#define T5_SOURCE_PINOSC 	0b01000000  // Clock source SOSCEN = 0 Ext clock, SOSCEN=1 Crystal osc
#define T5_SOURCE_FOSC_4 	0b00000000  //Clock source is instruction clock (FOSC/4)
#define T5_SOURCE_FOSC   	0b00100000  //Closck source is system clock (FOSC)
#define	T5_SOURCE_MASK	 	(~T5_SOURCE_FOSC)	//Mask Timer5 Clock Source Select bits

#define T5_PS_1_1        	0b00000000  //Timer5 1:1 prescale value
#define T5_PS_1_2        	0b00001000  //Timer5 1:2 prescale value
#define T5_PS_1_4        	0b00010000  //Timer5 1:4 prescale value
#define T5_PS_1_8        	0b00011000  //Timer5 1:8 prescale value
#define T5_PS_MASK		 	(~T5_PS_1_8)		//Mask Timer5 Input Clock Prescale Select bits

#define T5_OSC1EN_OFF    	0b00000000  // Timer1  oscilator disabled which is used by Timer5
#define T5_OSC1EN_ON     	0b00000100  // Timer1  oscilator enabled which is used by Timer5
#define	T5_OSC_MASK		 	(~T5_OSC1EN_ON)		//Mask Timer5 Oscillator Source Select bit

#define T5_SYNC_EXT_ON      0b00000000  // Synchronize external clock input
#define T5_SYNC_EXT_OFF     0b00000010  // Do not synchronize external clock input
#define T5_SYNC_MASK	    (~T5_SYNC_EXT_OFF)	// Mask Timer5 External Clock Input Synchronization Select bit

#define T5_8BIT_RW          0b00000000  //Enables register read/write of Timer5 in two 8-bit operations
#define T5_16BIT_RW         0b00000001  //Enables register read/write of Timer5 in two 16-bit operations
#define T5_BIT_RW_MASK	    (~T5_16BIT_RW)		// Mask Timer5 16-Bit Read/Write Mode Enable bit

#endif

void OpenTimer5 ( unsigned char config,  unsigned char config1);
void CloseTimer5 (void);
unsigned int ReadTimer5 (void);
void WriteTimer5 ( unsigned int Timer5);

#endif



#if defined (TMR_V7) || defined (TMR_V7_1) || defined (TMR_V7_3)

/* TIMER6 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */

#ifndef USE_OR_MASKS

#define T6_POST_1_1    0b10000111  //Timer6 Postscaler 1:1
#define T6_POST_1_2    0b10001111  //Timer6 Postscaler 1:2
#define T6_POST_1_3    0b10010111  //Timer6 Postscaler 1:3
#define T6_POST_1_4    0b10011111  //Timer6 Postscaler 1:4
#define T6_POST_1_5    0b10100111  //Timer6 Postscaler 1:5
#define T6_POST_1_6    0b10101111  //Timer6 Postscaler 1:6
#define T6_POST_1_7    0b10110111  //Timer6 Postscaler 1:7
#define T6_POST_1_8    0b10111111  //Timer6 Postscaler 1:8
#define T6_POST_1_9    0b11000111  //Timer6 Postscaler 1:9
#define T6_POST_1_10   0b11001111  //Timer6 Postscaler 1:10
#define T6_POST_1_11   0b11010111  //Timer6 Postscaler 1:11
#define T6_POST_1_12   0b11011111  //Timer6 Postscaler 1:12
#define T6_POST_1_13   0b11100111  //Timer6 Postscaler 1:13
#define T6_POST_1_14   0b11101111  //Timer6 Postscaler 1:14
#define T6_POST_1_15   0b11110111  //Timer6 Postscaler 1:15
#define T6_POST_1_16   0b11111111  //Timer6 Postscaler 1:16
#define T6_PS_1_1      0b11111100  //Timer6 Prescale 1:1
#define T6_PS_1_4      0b11111101  //Timer6 Prescale 1:4
#define T6_PS_1_16     0b11111111  //Timer6 Prescale 1:16

#else //!USE_OR_MASKS

#define T6_POST_1_1    0b00000000  // Timer6 Postscaler 1:1
#define T6_POST_1_2    0b00001000  // Timer6 Postscaler 1:2
#define T6_POST_1_3    0b00010000  // Timer6 Postscaler 1:3
#define T6_POST_1_4    0b00011000  // Timer6 Postscaler 1:4
#define T6_POST_1_5    0b00100000  // Timer6 Postscaler 1:5
#define T6_POST_1_6    0b00101000  // Timer6 Postscaler 1:6
#define T6_POST_1_7    0b00110000  // Timer6 Postscaler 1:7
#define T6_POST_1_8    0b00111000  // Timer6 Postscaler 1:8
#define T6_POST_1_9    0b01000000  // Timer6 Postscaler 1:9
#define T6_POST_1_10   0b01001000  // Timer6 Postscaler 1:10
#define T6_POST_1_11   0b01010000  // Timer6 Postscaler 1:11
#define T6_POST_1_12   0b01011000  // Timer6 Postscaler 1:12
#define T6_POST_1_13   0b01100000  // Timer6 Postscaler 1:13
#define T6_POST_1_14   0b01101000  // Timer6 Postscaler 1:14
#define T6_POST_1_15   0b01110000  // Timer6 Postscaler 1:15
#define T6_POST_1_16   0b01111000  // Timer6 Postscaler 1:16
#define T6_POST_MASK	(~T6_POST_1_16)		//Mask Timer6 Postscale Select bits

#define T6_PS_1_1      0b00000000  //Timer6 Prescale 1:1
#define T6_PS_1_4      0b00000001  //Timer6 Prescale 1:4
#define T6_PS_1_16     0b00000011  //Timer6 Prescale 1:16
#define T6_PS_MASK		(~T6_PS_1_16)		//Mask Timer6 Input Clock Prescale Select bits

#endif //USE_OR_MASKS

/***********************************************************************************
Macro       : WriteTimer6(Timer6)

Include     : timers.h

Description : Macro writes the parameter passed to Timer6 count register TMR6
 
Arguments   : Timer6 : value to be written to Timer6 count register TMR6
 
Remarks     : None
***********************************************************************************/
#define WriteTimer6(Timer6) 	TMR6 = (Timer6)

/***********************************************************************************
Macro       : ReadTimer6()

Include     : timers.h

Description : Macro returns the TMR6 register value of Timer6
 
Arguments   : None
 
Returns     : TMR6 value
***********************************************************************************/
#define ReadTimer6()	    TMR6

void OpenTimer6 ( unsigned char config);
void CloseTimer6 (void);


#endif


#if defined (TMR_V7)

#ifndef USE_OR_MASKS

#define T7_SOURCE_PINOSC 	0b11011111  // Clock source SOSCEN = 0 Ext clock, SOSCEN=1 Crystal osc
#define T7_SOURCE_FOSC_4 	0b10011111  //Clock source is instruction clock (FOSC/4)
#define T7_SOURCE_FOSC   	0b10111111  //Closck source is system clock (FOSC)

#define T7_PS_1_1        	0b11100111  //Timer7 1:1 prescale value
#define T7_PS_1_2        	0b11101111  //Timer7 1:2 prescale value
#define T7_PS_1_4        	0b11110111  //Timer7 1:4 prescale value
#define T7_PS_1_8        	0b11111111  //Timer7 1:8 prescale value

#define T7_OSC1EN_OFF    	0b11111011  // Timer1  oscilator disabled which is used by Timer7
#define T7_OSC1EN_ON     	0b11111111  // Timer1  oscilator enabled which is used by Timer7

#define T7_SYNC_EXT_ON      0b11111101  // Synchronize external clock input
#define T7_SYNC_EXT_OFF     0b11111111  // Do not synchronize external clock input

#define T7_8BIT_RW          0b11111110  //Enables register read/write of Timer7 in two 8-bit operations
#define T7_16BIT_RW         0b11111111  //Enables register read/write of Timer7 in two 16-bit operations

#else

#define T7_SOURCE_PINOSC 	0b01000000  // Clock source SOSCEN = 0 Ext clock, SOSCEN=1 Crystal osc
#define T7_SOURCE_FOSC_4 	0b00000000  //Clock source is instruction clock (FOSC/4)
#define T7_SOURCE_FOSC   	0b00100000  //Closck source is system clock (FOSC)
#define	T7_SOURCE_MASK	 	(~T7_SOURCE_FOSC)	//Mask Timer7 Clock Source Select bits

#define T7_PS_1_1        	0b00000000  //Timer7 1:1 prescale value
#define T7_PS_1_2        	0b00001000  //Timer7 1:2 prescale value
#define T7_PS_1_4        	0b00010000  //Timer7 1:4 prescale value
#define T7_PS_1_8        	0b00011000  //Timer7 1:8 prescale value
#define T7_PS_MASK		 	(~T7_PS_1_8)		//Mask Timer7 Input Clock Prescale Select bits

#define T7_OSC1EN_OFF    	0b00000000  // Timer1  oscilator disabled which is used by Timer7
#define T7_OSC1EN_ON     	0b00000100  // Timer1  oscilator enabled which is used by Timer7
#define	T7_OSC_MASK		 	(~T7_OSC1EN_ON)		//Mask Timer7 Oscillator Source Select bit

#define T7_SYNC_EXT_ON      0b00000000  // Synchronize external clock input
#define T7_SYNC_EXT_OFF     0b00000010  // Do not synchronize external clock input
#define T7_SYNC_MASK	    (~T7_SYNC_EXT_OFF)	// Mask Timer7 External Clock Input Synchronization Select bit

#define T7_8BIT_RW          0b00000000  //Enables register read/write of Timer7 in two 8-bit operations
#define T7_16BIT_RW         0b00000001  //Enables register read/write of Timer7 in two 16-bit operations
#define T7_BIT_RW_MASK	    (~T7_16BIT_RW)		// Mask Timer7 16-Bit Read/Write Mode Enable bit

#endif

void OpenTimer7 ( unsigned char config,  unsigned char config1);
void CloseTimer7 (void);
unsigned int ReadTimer7 (void);
void WriteTimer7 ( unsigned int Timer7);

#endif




#if defined (TMR_V7) || defined (TMR_V7_1)

/* Timer8 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */

#ifndef USE_OR_MASKS

#define T8_POST_1_1    0b10000111  //Timer8 Postscaler 1:1
#define T8_POST_1_2    0b10001111  //Timer8 Postscaler 1:2
#define T8_POST_1_3    0b10010111  //Timer8 Postscaler 1:3
#define T8_POST_1_4    0b10011111  //Timer8 Postscaler 1:4
#define T8_POST_1_5    0b10100111  //Timer8 Postscaler 1:5
#define T8_POST_1_6    0b10101111  //Timer8 Postscaler 1:6
#define T8_POST_1_7    0b10110111  //Timer8 Postscaler 1:7
#define T8_POST_1_8    0b10111111  //Timer8 Postscaler 1:8
#define T8_POST_1_9    0b11000111  //Timer8 Postscaler 1:9
#define T8_POST_1_10   0b11001111  //Timer8 Postscaler 1:10
#define T8_POST_1_11   0b11010111  //Timer8 Postscaler 1:11
#define T8_POST_1_12   0b11011111  //Timer8 Postscaler 1:12
#define T8_POST_1_13   0b11100111  //Timer8 Postscaler 1:13
#define T8_POST_1_14   0b11101111  //Timer8 Postscaler 1:14
#define T8_POST_1_15   0b11110111  //Timer8 Postscaler 1:15
#define T8_POST_1_16   0b11111111  //Timer8 Postscaler 1:16
#define T8_PS_1_1      0b11111100  //Timer8 Prescale 1:1
#define T8_PS_1_4      0b11111101  //Timer8 Prescale 1:4
#define T8_PS_1_16     0b11111111  //Timer8 Prescale 1:16

#else //!USE_OR_MASKS

#define T8_POST_1_1    0b00000000  // Timer8 Postscaler 1:1
#define T8_POST_1_2    0b00001000  // Timer8 Postscaler 1:2
#define T8_POST_1_3    0b00010000  // Timer8 Postscaler 1:3
#define T8_POST_1_4    0b00011000  // Timer8 Postscaler 1:4
#define T8_POST_1_5    0b00100000  // Timer8 Postscaler 1:5
#define T8_POST_1_6    0b00101000  // Timer8 Postscaler 1:6
#define T8_POST_1_7    0b00110000  // Timer8 Postscaler 1:7
#define T8_POST_1_8    0b00111000  // Timer8 Postscaler 1:8
#define T8_POST_1_9    0b01000000  // Timer8 Postscaler 1:9
#define T8_POST_1_10   0b01001000  // Timer8 Postscaler 1:10
#define T8_POST_1_11   0b01010000  // Timer8 Postscaler 1:11
#define T8_POST_1_12   0b01011000  // Timer8 Postscaler 1:12
#define T8_POST_1_13   0b01100000  // Timer8 Postscaler 1:13
#define T8_POST_1_14   0b01101000  // Timer8 Postscaler 1:14
#define T8_POST_1_15   0b01110000  // Timer8 Postscaler 1:15
#define T8_POST_1_16   0b01111000  // Timer8 Postscaler 1:16
#define T8_POST_MASK	(~T8_POST_1_16)		//Mask Timer8 Postscale Select bits

#define T8_PS_1_1      0b00000000  //Timer8 Prescale 1:1
#define T8_PS_1_4      0b00000001  //Timer8 Prescale 1:4
#define T8_PS_1_16     0b00000011  //Timer8 Prescale 1:16
#define T8_PS_MASK		(~T8_PS_1_16)		//Mask Timer8 Input Clock Prescale Select bits

#endif //USE_OR_MASKS

/***********************************************************************************
Macro       : WriteTimer8(Timer8)

Include     : timers.h

Description : Macro writes the parameter passed to Timer8 count register TMR8
 
Arguments   : Timer8 : value to be written to Timer8 count register TMR8
 
Remarks     : None
***********************************************************************************/
#define WriteTimer8(Timer8) 	TMR8 = (Timer8)

/***********************************************************************************
Macro       : ReadTimer8()

Include     : timers.h

Description : Macro returns the TMR8 register value of Timer8
 
Arguments   : None
 
Returns     : TMR8 value
***********************************************************************************/
#define ReadTimer8()	    TMR8

void OpenTimer8 ( unsigned char config);
void CloseTimer8 (void);


#endif


#if defined (TMR_V7)

/* Timer10 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */

#ifndef USE_OR_MASKS

#define T10_POST_1_1    0b10000111  //Timer10 Postscaler 1:1
#define T10_POST_1_2    0b10001111  //Timer10 Postscaler 1:2
#define T10_POST_1_3    0b10010111  //Timer10 Postscaler 1:3
#define T10_POST_1_4    0b10011111  //Timer10 Postscaler 1:4
#define T10_POST_1_5    0b10100111  //Timer10 Postscaler 1:5
#define T10_POST_1_6    0b10101111  //Timer10 Postscaler 1:6
#define T10_POST_1_7    0b10110111  //Timer10 Postscaler 1:7
#define T10_POST_1_8    0b10111111  //Timer10 Postscaler 1:8
#define T10_POST_1_9    0b11000111  //Timer10 Postscaler 1:9
#define T10_POST_1_10   0b11001111  //Timer10 Postscaler 1:10
#define T10_POST_1_11   0b11010111  //Timer10 Postscaler 1:11
#define T10_POST_1_12   0b11011111  //Timer10 Postscaler 1:12
#define T10_POST_1_13   0b11100111  //Timer10 Postscaler 1:13
#define T10_POST_1_14   0b11101111  //Timer10 Postscaler 1:14
#define T10_POST_1_15   0b11110111  //Timer10 Postscaler 1:15
#define T10_POST_1_16   0b11111111  //Timer10 Postscaler 1:16
#define T10_PS_1_1      0b11111100  //Timer10 Prescale 1:1
#define T10_PS_1_4      0b11111101  //Timer10 Prescale 1:4
#define T10_PS_1_16     0b11111111  //Timer10 Prescale 1:16

#else //!USE_OR_MASKS

#define T10_POST_1_1    0b00000000  // Timer10 Postscaler 1:1
#define T10_POST_1_2    0b00001000  // Timer10 Postscaler 1:2
#define T10_POST_1_3    0b00010000  // Timer10 Postscaler 1:3
#define T10_POST_1_4    0b00011000  // Timer10 Postscaler 1:4
#define T10_POST_1_5    0b00100000  // Timer10 Postscaler 1:5
#define T10_POST_1_6    0b00101000  // Timer10 Postscaler 1:6
#define T10_POST_1_7    0b00110000  // Timer10 Postscaler 1:7
#define T10_POST_1_8    0b00111000  // Timer10 Postscaler 1:8
#define T10_POST_1_9    0b01000000  // Timer10 Postscaler 1:9
#define T10_POST_1_10   0b01001000  // Timer10 Postscaler 1:10
#define T10_POST_1_11   0b01010000  // Timer10 Postscaler 1:11
#define T10_POST_1_12   0b01011000  // Timer10 Postscaler 1:12
#define T10_POST_1_13   0b01100000  // Timer10 Postscaler 1:13
#define T10_POST_1_14   0b01101000  // Timer10 Postscaler 1:14
#define T10_POST_1_15   0b01110000  // Timer10 Postscaler 1:15
#define T10_POST_1_16   0b01111000  // Timer10 Postscaler 1:16
#define T10_POST_MASK	(~T10_POST_1_16)		//Mask Timer10 Postscale Select bits

#define T10_PS_1_1      0b00000000  //Timer10 Prescale 1:1
#define T10_PS_1_4      0b00000001  //Timer10 Prescale 1:4
#define T10_PS_1_16     0b00000011  //Timer10 Prescale 1:16
#define T10_PS_MASK		(~T10_PS_1_16)		//Mask Timer10 Input Clock Prescale Select bits

#endif //USE_OR_MASKS

/***********************************************************************************
Macro       : WriteTimer10(Timer10)

Include     : timers.h

Description : Macro writes the parameter passed to Timer10 count register TMR10
 
Arguments   : Timer10 : value to be written to Timer10 count register TMR10
 
Remarks     : None
***********************************************************************************/
#define WriteTimer10(Timer10) 	TMR10 = (Timer10)

/***********************************************************************************
Macro       : ReadTimer10()

Include     : timers.h

Description : Macro returns the TMR10 register value of Timer10
 
Arguments   : None
 
Returns     : TMR10 value
***********************************************************************************/
#define ReadTimer10()	    TMR10

void OpenTimer10 ( unsigned char config);
void CloseTimer10 (void);


#endif



#if defined (TMR_V7)

/* Timer12 configuration masks -- to be 'anded' together and passed to the
 * 'open' routine. */

#ifndef USE_OR_MASKS

#define T12_POST_1_1    0b10000111  //Timer12 Postscaler 1:1
#define T12_POST_1_2    0b10001111  //Timer12 Postscaler 1:2
#define T12_POST_1_3    0b10010111  //Timer12 Postscaler 1:3
#define T12_POST_1_4    0b10011111  //Timer12 Postscaler 1:4
#define T12_POST_1_5    0b10100111  //Timer12 Postscaler 1:5
#define T12_POST_1_6    0b10101111  //Timer12 Postscaler 1:6
#define T12_POST_1_7    0b10110111  //Timer12 Postscaler 1:7
#define T12_POST_1_8    0b10111111  //Timer12 Postscaler 1:8
#define T12_POST_1_9    0b11000111  //Timer12 Postscaler 1:9
#define T12_POST_1_10   0b11001111  //Timer12 Postscaler 1:10
#define T12_POST_1_11   0b11010111  //Timer12 Postscaler 1:11
#define T12_POST_1_12   0b11011111  //Timer12 Postscaler 1:12
#define T12_POST_1_13   0b11100111  //Timer12 Postscaler 1:13
#define T12_POST_1_14   0b11101111  //Timer12 Postscaler 1:14
#define T12_POST_1_15   0b11110111  //Timer12 Postscaler 1:15
#define T12_POST_1_16   0b11111111  //Timer12 Postscaler 1:16
#define T12_PS_1_1      0b11111100  //Timer12 Prescale 1:1
#define T12_PS_1_4      0b11111101  //Timer12 Prescale 1:4
#define T12_PS_1_16     0b11111111  //Timer12 Prescale 1:16

#else //!USE_OR_MASKS

#define T12_POST_1_1    0b00000000  // Timer12 Postscaler 1:1
#define T12_POST_1_2    0b00001000  // Timer12 Postscaler 1:2
#define T12_POST_1_3    0b00010000  // Timer12 Postscaler 1:3
#define T12_POST_1_4    0b00011000  // Timer12 Postscaler 1:4
#define T12_POST_1_5    0b00100000  // Timer12 Postscaler 1:5
#define T12_POST_1_6    0b00101000  // Timer12 Postscaler 1:6
#define T12_POST_1_7    0b00110000  // Timer12 Postscaler 1:7
#define T12_POST_1_8    0b00111000  // Timer12 Postscaler 1:8
#define T12_POST_1_9    0b01000000  // Timer12 Postscaler 1:9
#define T12_POST_1_10   0b01001000  // Timer12 Postscaler 1:10
#define T12_POST_1_11   0b01010000  // Timer12 Postscaler 1:11
#define T12_POST_1_12   0b01011000  // Timer12 Postscaler 1:12
#define T12_POST_1_13   0b01100000  // Timer12 Postscaler 1:13
#define T12_POST_1_14   0b01101000  // Timer12 Postscaler 1:14
#define T12_POST_1_15   0b01110000  // Timer12 Postscaler 1:15
#define T12_POST_1_16   0b01111000  // Timer12 Postscaler 1:16
#define T12_POST_MASK	(~T12_POST_1_16)		//Mask Timer12 Postscale Select bits

#define T12_PS_1_1      0b00000000  //Timer12 Prescale 1:1
#define T12_PS_1_4      0b00000001  //Timer12 Prescale 1:4
#define T12_PS_1_16     0b00000011  //Timer12 Prescale 1:16
#define T12_PS_MASK		(~T12_PS_1_16)		//Mask Timer12 Input Clock Prescale Select bits

#endif //USE_OR_MASKS

/***********************************************************************************
Macro       : WriteTimer12(Timer12)

Include     : timers.h

Description : Macro writes the parameter passed to Timer12 count register TMR12
 
Arguments   : Timer12 : value to be written to Timer12 count register TMR12
 
Remarks     : None
***********************************************************************************/
#define WriteTimer12(Timer12) 	TMR12 = (Timer12)

/***********************************************************************************
Macro       : ReadTimer12()

Include     : timers.h

Description : Macro returns the TMR12 register value of Timer12
 
Arguments   : None
 
Returns     : TMR12 value
***********************************************************************************/
#define ReadTimer12()	    TMR12

void OpenTimer12 ( unsigned char config);
void CloseTimer12 (void);


#endif





#if defined (TMR_V6)

#ifndef USE_OR_MASKS

#define TIMER_GATE_ON 	        0b11111111  //Timer1 counting is controlled by Timer1 gate function
#define TIMER_GATE_OFF 	        0b01111111  //Timer1 counts regardless of Timer1 gate function
#define TIMER_GATE_POL_HI     	0b11111111  //Gate  is active-high
#define TIMER_GATE_POL_LO   	0b10111111  //Gate is active-low
#define TIMER_GATE_TOGGLE_ON 	0b11111111  //Gate Toggle mode is enabled
#define TIMER_GATE_TOGGLE_OFF 	0b11011111  //Gate Toggle mode is disabled
#define TIMER_GATE_1SHOT_ON     0b11111111  //Gate one shot is enabled
#define TIMER_GATE_1SHOT_OFF    0b11101111  //Gate one shot is disabled
#define TIMER_GATE_SRC_T1GPIN   0b11111100  //Timer1 gate pin
#define TIMER_GATE_SRC_T0       0b11111101  //Timer0 overflow output
#define TIMER_GATE_SRC_T2       0b11111110  //Timer2 match PR2  output
#define TIMER_GATE_INT_OFF      0b11111011  //Interrupts disabled
#define TIMER_GATE_INT_ON       0b11111111  //Interrupts enabled

#else

#define TIMER_GATE_ON 	        0b10000000  //Timer1 counting is controlled by Timer1 gate function
#define TIMER_GATE_OFF 	        0b00000000  //Timer1 is always counting 
#define TIMER_GATE_MASK         (~TIMER_GATE_ON)	//Mask Timer1 Gate Enable bit

#define TIMER_GATE_POL_HI     	0b01000000  //Gate  is active-high
#define TIMER_GATE_POL_LO   	0b00000000  //Gate is active-low
#define	TIMER_GATE_POL_MASK	    (~TIMER_GATE_POL_HI)	//Mask Timer1 Gate Polarity bit

#define TIMER_GATE_TOGGLE_ON 	0b00100000  //Gate Toggle mode is enabled
#define TIMER_GATE_TOGGLE_OFF 	0b00000000  //Gate Toggle mode is disabled
#define TIMER_GATE_TOGGLE_MASK  (~TIMER_GATE_TOGGLE_ON)	//Mask Timer1 Gate Toggle Mode bit

#define TIMER_GATE_1SHOT_ON     0b00010000  //Gate one shot is enabled
#define TIMER_GATE_1SHOT_OFF    0b00000000  //Gate one shot is disabled
#define TIMER_GATE_1SHOT_MASK   (~TIMER_GATE_1SHOT_MASK)	//Mask Timer1 Gate Single Pulse Mode bit

#define TIMER_GATE_SRC_T1GPIN   0b00000000  //Timer1 gate pin
#define TIMER_GATE_SRC_T0       0b00000001  //Timer0 overflow output
#define TIMER_GATE_SRC_T2       0b00000010  //Timer2 match PR2  output
#define TIMER_GATE_SRC_MASK     (~TIMER_GATE_SRC_T2) 	//Mask Timer1 Gate Source Select bits

#define TIMER_GATE_INT_OFF      0b00000000  //Interrupts disabled
#define TIMER_GATE_INT_ON       0b00000100  //Interrupts enabled
#define TIMER_GATE_INT_MASK		(~TIMER_GATE_INT_ON) 	//Mask Timer1 Gate Interrupt bit

#endif
#endif

#if defined (TMR_V4)
#ifndef USE_OR_MASKS
#define T34_SOURCE_CCP   		  	0b11111111		// T3 and T4 are sources for CCP1 thru CCP5
#define T12_CCP12_T34_CCP345		0b11110111		// T1 and T2 are sources for CCP1 and CCP2 and T3 and T4 are sources for CCP3 thru CCP5
#define T12_CCP1_T34_CCP2345		0b10111111		// T1 and T2 are sources for CCP1 and T3 and T4 are sources for CCP2 thru CCP5
#define T12_SOURCE_CCP   		  	0b10110111		// T1 and T2 are sources for CCP1 thru CCP5

#else  //!USE_OR_MASKS

#define T34_SOURCE_CCP   		  	0b01001000		// T3 and T4 are sources for CCP1 thru CCP5
#define T12_CCP12_T34_CCP345		0b01000000		// T1 and T2 are sources for CCP1 and CCP2 and T3 and T4 are sources for CCP3 thru CCP5
#define T12_CCP1_T34_CCP2345		0b00001000		// T1 and T2 are sources for CCP1 and T3 and T4 are sources for CCP2 thru CCP5
#define T12_SOURCE_CCP   		  	0b00000000		// T1 and T2 are sources for CCP1 thru CCP5
#define TMR_SOURCE_CCP_MASK			(~T34_SOURCE_CCP)	//Mask Timer1 and Timer2 to CCPx Enable bits
#endif //USE_OR_MASKS

#elif defined (TMR_V2)//USE_OR_MASKS

#ifndef USE_OR_MASKS
#define T3_SOURCE_CCP           0b11111111			// T3 is source for CCP
#define T1_CCP1_T3_CCP2         0b10111111			// T1 is source for CCP1 and T3 is source for CCP2
#define T1_SOURCE_CCP           0b10110111			// T1 is source for CCP
#else //!USE_OR_MASKS
#define T3_SOURCE_CCP        	0b01001000			// T3 is source for CCP
#define T1_CCP1_T3_CCP2       	0b00001000			// T1 is source for CCP1 and T3 is source for CCP2
#define T1_SOURCE_CCP        	0b00000000			// T1 is source for CCP
#define TMR_SOURCE_CCP_MASK		(~T3_SOURCE_CCP)	//Mask Timer1 and Timer3 to CCPx Enable bits
#endif //USE_OR_MASKS

#elif defined (TMR_V6)
#ifndef USE_OR_MASKS
#define T34_SOURCE_CCP12        0b11111111		// T3 and T4 are sources for CCP1 and CCP2 
#define T12_CCP1_T34_CCP2       0b11111101		// T1 and T2 are sources for CCP1 and T3 and T4 are sources for CCP2 
#define T12_SOURCE_CCP        	0b11111100		// T1  and T2 is source for CCP1 and CCP2

#else

#define T34_SOURCE_CCP12        0b00000011		// T3 and T4 are sources for CCP1 and CCP2 
#define T12_CCP1_T34_CCP2       0b00000001		// T1 and T2 are sources for CCP1 and T3 and T4 are sources for CCP2 
#define T12_SOURCE_CCP        	0b00000000		// T1  and T2 is source for CCP1 and CCP2
#define TMR_SOURCE_CCP_MASK		(T34_CCP12)		//Mask Timer as source to CCPx  bits
#endif
#endif

#if defined (TMR_V4) || defined (TMR_V2) || defined (TMR_V6)
void SetTmrCCPSrc(  unsigned char );
#endif

#endif//__TIMERS_H

