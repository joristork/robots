#ifndef __PORTB_H
#define __PORTB_H
/******************************************************************************
 *                  PORTB PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		portb.h
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

#ifndef USE_OR_MASKS

#define PORTB_CHANGE_INT_ON   	0b11111111  /* PORTB interrupts on  */
#define PORTB_CHANGE_INT_OFF 	0b01111111  /* PORTB interrupts off */
#define PORTB_PULLUPS_ON      	0b11111110  /* PORTB pullups on     */
#define PORTB_PULLUPS_OFF     	0b11111111  /* PORTB pullups off    */
#define RISING_EDGE_INT  		0b11111111 	/*Interrupt is set by a rising edge signal  */
#define FALLING_EDGE_INT 		0b11111101 	/*Interrupt is set by a falling edge sginal */
#define PORTB_INT_PRIO_HIGH     0b11111111 	/*Interrupt priority set to high*/
#define PORTB_INT_PRIO_LOW      0b10111111 	/*Interrupt priority set to low*/

#else

#define PORTB_CHANGE_INT_ON   	0b10000000  /* PORTB interrupts on  */
#define PORTB_CHANGE_INT_OFF  	0b00000000  /* PORTB interrupts off */
#define PORTB_CHANGE_INT_MASK	(~PORTB_CHANGE_INT_ON)	//Mask PORTB Interrupt bit

#define PORTB_PULLUPS_ON      	0b00000000  /* PORTB pullups on     */
#define PORTB_PULLUPS_OFF     	0b00000001  /* PORTB pullups off    */
#define PORTB_PULLUPS_MASK		(~PORTB_PULLUPS_OFF)	//Mask PORTB pullup setting bits

#define RISING_EDGE_INT  		0b00000010 /*Interrupt is set by a rising edge signal */
#define FALLING_EDGE_INT 		0b00000000 /*Interrupt is set by a falling edge signal */
#define EDGE_INT_MASK    		(~RISING_EDGE_INT)		//Mask Interrupt on edge selection bits

#define PORTB_INT_PRIO_HIGH     0b01000000 /*Interrupt priority set to high*/
#define PORTB_INT_PRIO_LOW      0b00000000 /*Interrupt priority set to low*/
#define PORTB_INT_PRIO_MASK     (~PORTB_INT_PRIO_HIGH) 	//Mask interrupt priority selelction bit

#endif


#if defined (PTB_V5)
//TODO  remove once V5 header files are changed
//#define RABIE RBIE


/******************************************************************
Macro       : EnablePullups()
 
Include     : portb.h
 
Description : This enables the pull-up resistors for PORTB Change Notification pins.
               
Arguments   : None
 
Remarks     : None 
********************************************************************/
#define EnablePullups() INTCON2bits.RABPU=0

/******************************************************************
Macro       : DisablePullups()
 
Include     : portb.h
 
Description : This disables the pull-up resistors for PORTB Change Notification pins.
               
Arguments   : None
 
Remarks     : None 
********************************************************************/
#define DisablePullups() INTCON2bits.RABPU=1

#else

/******************************************************************
Macro       : EnablePullups()
 
Include     : portb.h
 
Description : This enables the pull-up resistors for PORTB Change Notification pins.
               
Arguments   : None
 
Remarks     : None 
********************************************************************/
#define EnablePullups() INTCON2bits.RBPU=0

/******************************************************************
Macro       : DisablePullups()
 
Include     : portb.h
 
Description : This disables the pull-up resistors for PORTB Change Notification pins.
               
Arguments   : None
 
Remarks     : None 
********************************************************************/
#define DisablePullups() INTCON2bits.RBPU=1
#endif

void OpenPORTB( unsigned char config);

#if defined (PTB_V5)

/****************************************************************************
Macro 	:	ClosePORTB()
 
Include        : 	portb.h
 
Description : 	This Macro disables the CN interrupt and disables pullups
 
Arguments   : 	None
 
Remarks      : 	None
*******************************************************************************/
#define ClosePORTB() INTCONbits.RABIE=0, DisablePullups()

#else


/****************************************************************************
Macro 	:	ClosePORTB()
 
Include        : 	portb.h
 
Description : 	This Macro disables the CN interrupt and disables pullups
 
Arguments   : 	None
 
Remarks      : 	None
*******************************************************************************/
#define ClosePORTB() INTCONbits.RBIE=0, DisablePullups()

#endif


void OpenRB0INT( unsigned char config);

/****************************************************************************
Macro	 :  	CloseRB0INT()
 
Include	: 	portb.h
 
Description : 	This Macro disables the external interrupt on INT pin.
 
Arguments   : 	None
 
Return Value: 	None
 
Remarks     : 	This Macro disables the interrupt on INT pin.
*******************************************************************************/
#define CloseRB0INT() INTCONbits.INT0IE=0


void OpenRB1INT( unsigned char config);

/****************************************************************************
Macro	 :  	CloseRB1INT()
 
Include	: 	portb.h
 
Description : 	This Macro disables the external interrupt on INT pin.
 
Arguments   : 	None
 
Return Value: 	None
 
Remarks     : 	This Macro disables the interrupt on INT pin.
*******************************************************************************/
#define CloseRB1INT() INTCON3bits.INT1IE=0

void OpenRB2INT( unsigned char config);

/****************************************************************************
Macro	 :  	CloseRB2INT()
 
Include	: 	portb.h
 
Description : 	This Macro disables the external interrupt on INT pin.
 
Arguments   : 	None
 
Return Value: 	None
 
Remarks     : 	This Macro disables the interrupt on INT pin.
*******************************************************************************/
#define CloseRB2INT() INTCON3bits.INT2IE=0

#if defined PTB_V2 || defined PTB_V3 || defined PTB_V6

void OpenRB3INT( unsigned char config);

/****************************************************************************
Macro	 :  	CloseRB3INT()
 
Include	: 	portb.h
 
Description : 	This Macro disables the external interrupt on INT pin.
 
Arguments   : 	None
 
Return Value: 	None
 
Remarks     : 	This Macro disables the interrupt on INT pin.
*******************************************************************************/
#define CloseRB3INT() INTCON3bits.INT3IE=0

#endif

#endif
