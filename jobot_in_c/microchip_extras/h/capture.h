#ifndef __CAPTURE_H
#define __CAPTURE_H
/******************************************************************************
 // *                  CAPTURE  PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		capture.h
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


/* PIC18 capture peripheral routines. */

/* There are five library modules, corresponding to register names:
 *  CAPTURE1  (CCPCON1)
 *  CAPTURE2  (CCPCON2)
 *  CAPTURE3  (CCPCON3)
 *  CAPTURE4  (CCPCON4)
 *  CAPTURE5  (CCPCON5)
 *  ECAPTURE1  (ECCPCON1)
 *  Each module is defined only for those devices for which the register
 *  name is defined.
 *  Note that the 'E' prefix indicates PWM enhanced capability (in ECCPCON1,
 *  for example); however, the absence of the 'E' prefix does not imply
 *  lack of enhanced capability.  The usage of the 'E' prefix is indicative 
 *  of the register naming convention in the datasheets, not the
 *  functionality.  Note also that these modules deal with capture
 *  functionality only; for PWM functionality, see the header 'pwm.h'.
 */

 /* For each module, there are three routines: an 'open' routine,
  * a 'read' routine, and a 'close' routine.  The 'open' routine
  * configures the edge for capture as well as optionally enables the
  * interrupt.  The 'read' routine returns the 16-bit captured
  * value, and sets the overflow status bit if required.  The 'close'
  * routine disables the module, including the interrupt.
  */

/* For each module, a bit in this byte holds the overflow status.
 * Following a read, the bit will be set to indicate overflow. */
union capstatus
{
#if defined (CC_V1) || defined (CC_V2) || defined (CC_V3) || defined (CC_V4) ||\
	defined (CC_V5) || defined (CC_V6) || defined (CC_V7) || defined (CC_V8) || defined (CC_V8_1)\
	|| defined (ECC_V8) || defined (ECC_V8_1) || defined (ECC_V8_2) ||\
	defined (CC_V8_2) || defined (ECC_V9) || defined (ECC_V9_1) || defined (CC_V9_1)\
	 || defined (CC_V9)

  struct
  {
#if defined (CC_V1) || defined (CC_V2) || defined (CC_V3) || defined (CC_V4) ||\
	defined (CC_V5) || defined (CC_V6) || defined (CC_V7) 
  unsigned Cap1OVF:1; /* CAPTURE1 overflow status. Following a read, the bit will be set to indicate overflow */
#endif

#if defined (CC_V2) || defined (CC_V3) || defined (CC_V4) || defined (CC_V6) ||\
	defined (CC_V7) || defined (CC_V8_2)
	unsigned Cap2OVF:1; /* CAPTURE2 overflow status. Following a read, the bit will be set to indicate overflow*/
#endif

#if defined (CC_V3) || defined (CC_V4) || defined (CC_V8_2)
    unsigned Cap3OVF:1; /* CAPTURE3 overflow status. Following a read, the bit will be set to indicate overflow*/
#endif

#if defined (CC_V4) || defined (CC_V8) || defined (CC_V8_1) || defined (CC_V8_2)\
	 || defined (CC_V9)  || defined (CC_V9_1)
    unsigned Cap4OVF:1; /* CAPTURE4 overflow status. Following a read, the bit will be set to indicate overflow */
    unsigned Cap5OVF:1; /* CAPTURE5 overflow status. Following a read, the bit will be set to indicate overflow*/
#endif

#if defined (CC_V8) || defined (CC_V8_1)
    unsigned Cap6OVF:1; /* CAPTURE6 overflow status. Following a read, the bit will be set to indicate overflow */
    unsigned Cap7OVF:1; /* CAPTURE7 overflow status. Following a read, the bit will be set to indicate overflow*/
    unsigned Cap8OVF:1; /* CAPTURE8 overflow status. Following a read, the bit will be set to indicate overflow */
#if defined (CC_V8)	
    unsigned Cap9OVF:1; /* CAPTURE9 overflow status. Following a read, the bit will be set to indicate overflow*/
    unsigned Cap10OVF:1; /* CAPTURE10 overflow status. Following a read, the bit will be set to indicate overflow */
#endif	
#endif

#if defined (ECC_V5) || defined (ECC_V8) ||  defined (ECC_V8_1) || defined (ECC_V8_2)\
	 || defined (ECC_V9) || defined (ECC_V9_1)
    unsigned ECap1OVF:1; /* ECAPTURE1 overflow status. Following a read, the bit will be set to indicate overflow*/
#endif

#if defined (ECC_V8) ||  defined (ECC_V8_1)  || defined (ECC_V9) || defined (ECC_V9_1)
    unsigned ECap2OVF:1; /* ECAPTURE2 overflow status. Following a read, the bit will be set to indicate overflow*/
	unsigned ECap3OVF:1; /* ECAPTURE3 overflow status. Following a read, the bit will be set to indicate overflow*/
#endif

  };
#endif
  unsigned :8;

};

extern union capstatus CapStatus;
/* used to hold the 16-bit captured value */
union CapResult
{
 unsigned int lc;	// holds the 16-bit captured value
 char bc[2];		// holds the 16-bit captured value
};


/* storage class of library routine parameters; pre-built with auto;
 * do not change unless you rebuild the libraries with the new storage class */ 


/* Interrupt bit mask to be 'ORed' with the edge mask and passed as
 * the 'config' parameter to the 'open' routines. */

#ifndef USE_OR_MASKS
//***************** Capture Interrupt Enable/Diable ***********************************
#define CAPTURE_INT_ON   0b11111111  			/* Enable Capture interrupt */
#define CAPTURE_INT_OFF  0b01111111  			/* Disable Capture interrupt */
#else
#define CAPTURE_INT_ON   0b10000000  			/* Enable Capture interrupt */
#define CAPTURE_INT_OFF  0b00000000  			/* Disable Capture interrupt */
#define CAPTURE_INT_MASK	(~CAPTURE_INT_ON)	//Mask Capture interrupt bit
#endif

/* ***** CAPTURE1 (CCP1CON) ***** */

#ifndef USE_OR_MASKS

//***************** Capture mode selection ********************************************
#define CAP_EVERY_FALL_EDGE     0b10000100  	/* Capture on every falling edge*/
#define CAP_EVERY_RISE_EDGE     0b10000101  	/* Capture on every rising edge*/
#define CAP_EVERY_4_RISE_EDGE   0b10000110  	/* Capture on every 4th rising edge*/
#define CAP_EVERY_16_RISE_EDGE  0b10000111  	/* Capture on every 16th rising edge  */
#else

//***************** Capture mode selection ********************************************
#define CAP_EVERY_FALL_EDGE     0b00000100  	/* Capture on every falling edge*/
#define CAP_EVERY_RISE_EDGE     0b00000101  	/* Capture on every rising edge*/
#define CAP_EVERY_4_RISE_EDGE   0b00000110  	/* Capture on every 4th rising edge*/
#define CAP_EVERY_16_RISE_EDGE  0b00000111  	/* Capture on every 16th rising edge*/
#define CAP_MODE_MASK			(0b11110000)	//Mask Capture mode selection bits
#endif

#if defined (CC_V8) || defined (CC_V8_1) 
#ifndef USE_OR_MASKS

//***************** CCP source - TIMER selection ********************************************
//*************CCP4**************
#define CCP_4_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_4_SEL_TMR34		    0b11011111  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_4_SEL_TMR36		    0b11101111  	//CCP selects TIMER3 for Capture & Compare and TIMER6 for PWM

//*************CCP5**************
#define CCP_5_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_5_SEL_TMR54		    0b11011111  	//CCP selects TIMER5 for Capture & Compare and TIMER4 for PWM

//*************CCP6**************
#define CCP_6_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_6_SEL_TMR52		    0b11011111  	//CCP selects TIMER5 for Capture & Compare and TIMER2 for PWM

//*************CCP7**************
#define CCP_7_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_7_SEL_TMR54		    0b11011111  	//CCP selects TIMER5 for Capture & Compare and TIMER4 for PWM
#define CCP_7_SEL_TMR56		    0b11101111  	//CCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define CCP_7_SEL_TMR58		    0b11111111  	//CCP selects TIMER5 for Capture & Compare and TIMER8 for PWM

#if defined (CC_V8)
//*************CCP8**************
#define CCP_8_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_8_SEL_TMR74		    0b11011111  	//CCP selects TIMER7 for Capture & Compare and TIMER4 for PWM
#define CCP_8_SEL_TMR76		    0b11101111  	//CCP selects TIMER7 for Capture & Compare and TIMER6 for PWM
#elif defined (CC_V8_1)
//*************CCP8**************
#define CCP_8_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_8_SEL_TMR14		    0b11011111  	//CCP selects TIMER1 for Capture & Compare and TIMER4 for PWM
#define CCP_8_SEL_TMR16		    0b11101111  	//CCP selects TIMER1 for Capture & Compare and TIMER6 for PWM
#endif

#if defined (CC_V8)
//*************CCP9**************
#define CCP_9_SEL_TMR12		    0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_9_SEL_TMR74		    0b11011111  	//CCP selects TIMER7 for Capture & Compare and TIMER4 for PWM

//*************CCP10**************
#define CCP_10_SEL_TMR12		 0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_10_SEL_TMR72		 0b11011111  	//CCP selects TIMER7 for Capture & Compare and TIMER2 for PWM
#endif

#else	//USE_OR_MASKS

//***************** CCP source - TIMER selection ********************************************
//*************CCP4**************
#define CCP_4_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_4_SEL_TMR34		    0b00010000  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_4_SEL_TMR36		    0b00100000  	//CCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define CCP_4_SEL_TMR_MASK		(~0b00110000)  	//Maks CCP TIMER Source selection bits

//*************CCP5**************
#define CCP_5_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_5_SEL_TMR54		    0b00010000  	//CCP selects TIMER5 for Capture & Compare and TIMER4 for PWM
#define CCP_5_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits

//*************CCP6**************
#define CCP_6_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_6_SEL_TMR52		    0b00010000  	//CCP selects TIMER5 for Capture & Compare and TIMER2 for PWM
#define CCP_6_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits

//*************CCP7**************
#define CCP_7_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_7_SEL_TMR54		    0b00010000  	//CCP selects TIMER5 for Capture & Compare and TIMER4 for PWM
#define CCP_7_SEL_TMR56		    0b00100000  	//CCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define CCP_7_SEL_TMR58		    0b00110000  	//CCP selects TIMER5 for Capture & Compare and TIMER8 for PWM
#define CCP_7_SEL_TMR_MASK		(~0b00110000)  	//Maks CCP TIMER Source selection bits

#if defined (CC_V8)
//*************CCP8**************
#define CCP_8_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_8_SEL_TMR74		    0b00010000  	//CCP selects TIMER7 for Capture & Compare and TIMER4 for PWM
#define CCP_8_SEL_TMR76		    0b00100000  	//CCP selects TIMER7 for Capture & Compare and TIMER6 for PWM
#define CCP_8_SEL_TMR_MASK		(~0b00110000)  	//Maks CCP TIMER Source selection bits
#elif defined (CC_V8_1)
//*************CCP8**************
#define CCP_8_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_8_SEL_TMR14		    0b00010000  	//CCP selects TIMER1 for Capture & Compare and TIMER4 for PWM
#define CCP_8_SEL_TMR16		    0b00100000  	//CCP selects TIMER1 for Capture & Compare and TIMER6 for PWM
#define CCP_8_SEL_TMR_MASK		(~0b00110000)  	//Maks CCP TIMER Source selection bits
#endif

#if defined (CC_V8)
//*************CCP9**************
#define CCP_9_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_9_SEL_TMR74		    0b00010000  	//CCP selects TIMER7 for Capture & Compare and TIMER4 for PWM
#define CCP_9_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits

//*************CCP10**************
#define CCP_10_SEL_TMR12		 0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_10_SEL_TMR72		 0b00010000  	//CCP selects TIMER7 for Capture & Compare and TIMER2 for PWM
#define CCP_10_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits
#endif

#endif	//USE_OR_MASKS
#endif	//main version 

#if defined (CC_V8_2) || defined (ECC_V8_2)
#ifndef USE_OR_MASKS
#define ECCP_1_SEL_TMR12		0b11101111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_1_SEL_TMR34		0b11111111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM

#define CCP_2_SEL_TMR12		    0b11101111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_2_SEL_TMR34		    0b11111111  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM

#define CCP_3_SEL_TMR12		    0b11101111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_3_SEL_TMR34		    0b11111111  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM

#define CCP_4_SEL_TMR12		    0b11101111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_4_SEL_TMR34		    0b11111111  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM

#define CCP_5_SEL_TMR12		    0b11101111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_5_SEL_TMR34		    0b11111111  	//CCP selects TIMER5 for Capture & Compare and TIMER4 for PWM

#else
#define ECCP_1_SEL_TMR12	    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_1_SEL_TMR34	    0b00010000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_1_SEL_TMR_MASK		(~0b00010000)  	//Maks ECCP TIMER Source selection bits

#define CCP_2_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_2_SEL_TMR34		    0b00010000  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_2_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits

#define CCP_3_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_3_SEL_TMR34		    0b00010000  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_3_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits

#define CCP_4_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_4_SEL_TMR34		    0b00010000  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_4_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits

#define CCP_5_SEL_TMR12		    0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_5_SEL_TMR34		    0b00010000  	//CCP selects TIMER5 for Capture & Compare and TIMER4 for PWM
#define CCP_5_SEL_TMR_MASK		(~0b00010000)  	//Maks CCP TIMER Source selection bits
#endif
#endif


#if defined (ECC_V8) || defined (ECC_V8_1) 
#ifndef USE_OR_MASKS
//***************** ECCP source - TIMER selection ********************************************
//*************ECCP1**************
#define ECCP_1_SEL_TMR12		    0b10001111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_1_SEL_TMR34		    0b10011111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_1_SEL_TMR36		    0b10101111  	//ECCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define ECCP_1_SEL_TMR38		    0b10111111  	//ECCP selects TIMER3 for Capture & Compare and TIMER8 for PWM
#if defined (ECC_V8)
#define ECCP_1_SEL_TMR310		    0b11001111  	//ECCP selects TIMER3 for Capture & Compare and TIMER10 for PWM
#define ECCP_1_SEL_TMR312		    0b11011111  	//ECCP selects TIMER3 for Capture & Compare and TIMER12 for PWM
#endif

//*************ECCP2**************
#define ECCP_2_SEL_TMR12		    0b10001111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_2_SEL_TMR34		    0b10011111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_2_SEL_TMR36		    0b10101111  	//ECCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define ECCP_2_SEL_TMR38		    0b10111111  	//ECCP selects TIMER3 for Capture & Compare and TIMER8 for PWM
#if defined (ECC_V8)
#define ECCP_2_SEL_TMR310		    0b11001111  	//ECCP selects TIMER3 for Capture & Compare and TIMER10 for PWM
#endif

//*************ECCP3**************
#define ECCP_3_SEL_TMR12		    0b11001111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_3_SEL_TMR34		    0b11011111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_3_SEL_TMR36		    0b11101111  	//ECCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define ECCP_3_SEL_TMR38		    0b11111111  	//ECCP selects TIMER3 for Capture & Compare and TIMER8 for PWM


#else	//USE_OR_MASKS

//***************** ECCP source - TIMER selection ********************************************
//*************ECCP1**************
#define ECCP_1_SEL_TMR12		    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_1_SEL_TMR34		    0b00010000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM

#define ECCP_1_SEL_TMR36		    0b00100000  	//ECCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define ECCP_1_SEL_TMR38		    0b00110000  	//ECCP selects TIMER3 for Capture & Compare and TIMER8 for PWM
#if defined (ECC_V8)
#define ECCP_1_SEL_TMR310		    0b01000000  	//ECCP selects TIMER3 for Capture & Compare and TIMER10 for PWM
#define ECCP_1_SEL_TMR312		    0b01010000  	//ECCP selects TIMER3 for Capture & Compare and TIMER12 for PWM
#endif
#define ECCP_1_SEL_TMR_MASK			(~0b01110000)  	//Maks ECCP TIMER Source selection bits

//*************ECCP2**************
#define ECCP_2_SEL_TMR12		    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_2_SEL_TMR34		    0b00010000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_2_SEL_TMR36		    0b00100000  	//ECCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define ECCP_2_SEL_TMR38		    0b00110000  	//ECCP selects TIMER3 for Capture & Compare and TIMER8 for PWM
#if defined (ECC_V8)
#define ECCP_2_SEL_TMR310		    0b01000000  	//ECCP selects TIMER3 for Capture & Compare and TIMER10 for PWM
#endif
#define ECCP_2_SEL_TMR_MASK			(~0b01110000)  	//Maks ECCP TIMER Source selection bits

//*************ECCP3**************
#define ECCP_3_SEL_TMR12		    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_3_SEL_TMR34		    0b00010000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_3_SEL_TMR36		    0b00100000  	//ECCP selects TIMER3 for Capture & Compare and TIMER6 for PWM
#define ECCP_3_SEL_TMR38		    0b00110000  	//ECCP selects TIMER3 for Capture & Compare and TIMER8 for PWM
#define ECCP_3_SEL_TMR_MASK			(~0b00110000)  	//Maks ECCP TIMER Source selection bits

#endif	//USE_OR_MASKS
#endif	//main version 


#if defined (CC_V9) || defined (ECC_V9) || defined (CC_V9_1) || defined (ECC_V9_1)
#ifndef USE_OR_MASKS
#define ECCP_1_SEL_TMR12		0b11100111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_1_SEL_TMR34		0b11101111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_1_SEL_TMR56		0b11110111  	//ECCP selects TIMER5 for Capture & Compare and TIMER6 for PWM

#define ECCP_2_SEL_TMR12		0b00111111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_2_SEL_TMR34		0b01111111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_2_SEL_TMR56		0b10111111  	//ECCP selects TIMER5 for Capture & Compare and TIMER6 for PWM

#define ECCP_3_SEL_TMR12		0b11001111  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_3_SEL_TMR34		0b11011111  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_3_SEL_TMR56		0b11101111  	//ECCP selects TIMER5 for Capture & Compare and TIMER6 for PWM

#define CCP_4_SEL_TMR12			0b11001111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_4_SEL_TMR34			0b11011111  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_4_SEL_TMR56			0b11101111  	//CCP selects TIMER5 for Capture & Compare and TIMER6 for PWM

#define CCP_5_SEL_TMR12			0b00111111  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_5_SEL_TMR34			0b01111111  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_5_SEL_TMR56			0b10111111  	//CCP selects TIMER5 for Capture & Compare and TIMER6 for PWM

#else
#define ECCP_1_SEL_TMR12	    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_1_SEL_TMR34	    0b00001000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_1_SEL_TMR56	    0b00010000  	//ECCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define ECCP_1_SEL_TMR_MASK		(~0b00011000)  	//Maks ECCP TIMER Source selection bits

#define ECCP_2_SEL_TMR12	    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_2_SEL_TMR34	    0b01000000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_2_SEL_TMR56	    0b10000000  	//ECCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define ECCP_2_SEL_TMR_MASK		(~0b11000000)  	//Maks ECCP TIMER Source selection bits

#define ECCP_3_SEL_TMR12	    0b00000000  	//ECCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define ECCP_3_SEL_TMR34	    0b00010000  	//ECCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define ECCP_3_SEL_TMR56	    0b00100000  	//ECCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define ECCP_3_SEL_TMR_MASK		(~0b00110000)  	//Maks ECCP TIMER Source selection bits

#define CCP_4_SEL_TMR12	    	0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_4_SEL_TMR34	    	0b00010000  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_4_SEL_TMR56	    	0b00100000  	//CCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define CCP_4_SEL_TMR_MASK		(~0b00110000)  	//Maks CCP TIMER Source selection bits

#define CCP_5_SEL_TMR12	    	0b00000000  	//CCP selects TIMER1 for Capture & Compare and TIMER2 for PWM
#define CCP_5_SEL_TMR34	    	0b01000000  	//CCP selects TIMER3 for Capture & Compare and TIMER4 for PWM
#define CCP_5_SEL_TMR56	    	0b10000000  	//CCP selects TIMER5 for Capture & Compare and TIMER6 for PWM
#define CCP_5_SEL_TMR_MASK		(~0b11000000)  	//Maks CCP TIMER Source selection bits
#endif
#endif



#if defined (CC_V1) || defined (CC_V2) || defined (CC_V3) || defined (CC_V4) ||\
	defined (CC_V5) || defined (CC_V6) || defined (CC_V7) 
void OpenCapture1 ( unsigned char config);
unsigned int ReadCapture1 (void);
void CloseCapture1 (void);
#endif

/* ***** CAPTURE2 (CCP2CON) ***** */

#if defined (CC_V2) || defined (CC_V3) || defined (CC_V4) || defined (CC_V6)\
	|| defined (CC_V7) || defined (CC_V8_2)

void OpenCapture2 ( unsigned char config);
unsigned int ReadCapture2 (void);
void CloseCapture2 (void);

#endif


/* ***** CAPTURE3 (CCP3CON) ***** */

#if defined (CC_V3) || defined (CC_V4) || defined (CC_V8_2)

void OpenCapture3 ( unsigned char config);
unsigned int ReadCapture3 (void);
void CloseCapture3 (void);

#endif


/* ***** CAPTURE4 (CCP4CON) ***** */

#if defined (CC_V4) || defined (CC_V8) || defined (CC_V8_1) || defined (CC_V8_2)\
	|| defined (CC_V9)|| defined (CC_V9_1)

void OpenCapture4 ( unsigned char config);
unsigned int ReadCapture4 (void);
void CloseCapture4 (void);

void OpenCapture5 ( unsigned char config);
unsigned int ReadCapture5 (void);
void CloseCapture5 (void);

#endif


#if defined (CC_V8) || defined (CC_V8_1)

void OpenCapture6 ( unsigned char config);
unsigned int ReadCapture6 (void);
void CloseCapture6 (void);

void OpenCapture7 ( unsigned char config);
unsigned int ReadCapture7 (void);
void CloseCapture7 (void);

void OpenCapture8 ( unsigned char config);
unsigned int ReadCapture8 (void);
void CloseCapture8 (void);

#if defined (CC_V8)
void OpenCapture9 ( unsigned char config);
unsigned int ReadCapture9 (void);
void CloseCapture9 (void);

void OpenCapture10 ( unsigned char config);
unsigned int ReadCapture10 (void);
void CloseCapture10 (void);
#endif

#endif


/* ***** ECAPTURE1 (ECCP1CON) ***** */

#if defined (ECC_V5) || defined (ECC_V8) || defined (ECC_V8_1) || defined (ECC_V8_2) \
	|| defined (ECC_V9)|| defined (ECC_V9_1)


#ifndef	USE_OR_MASKS
//***************** Capture mode selection ********************************************
#define ECAP_EVERY_FALL_EDGE     0b10000100			/* Capture on every falling edge*/
#define ECAP_EVERY_RISE_EDGE     0b10000101			/* Capture on every rising edge*/
#define ECAP_EVERY_4_RISE_EDGE   0b10000110			/* Capture on every 4th rising edge*/
#define ECAP_EVERY_16_RISE_EDGE  0b10000111			/* Capture on every 16th rising edge*/

#else

//***************** Capture mode selection ********************************************
#define ECAP_EVERY_FALL_EDGE     0b00000100			/* Capture on every falling edge*/
#define ECAP_EVERY_RISE_EDGE     0b00000101			/* Capture on every rising edge*/
#define ECAP_EVERY_4_RISE_EDGE   0b00000110			/* Capture on every 4th rising edge*/
#define ECAP_EVERY_16_RISE_EDGE  0b00000111			/* Capture on every 16th rising edge*/
#define ECAP_MODE_MASK			(0b11110000)		//Mask Capture mode selection bits	
#endif

void OpenECapture1 ( unsigned char config);
unsigned int ReadECapture1 (void);
void CloseECapture1 (void);

#if defined (ECC_V8) || defined (ECC_V8_1) || defined (ECC_V9)|| defined (ECC_V9_1)
void OpenECapture2 ( unsigned char config);
unsigned int ReadECapture2 (void);
void CloseECapture2 (void);

void OpenECapture3 ( unsigned char config);
unsigned int ReadECapture3 (void);
void CloseECapture3 (void);
#endif

#endif

/*Macros for backward compatibility*/

#define	C1_EVERY_FALL_EDGE			CAP_EVERY_FALL_EDGE
#define	C1_EVERY_RISE_EDGE			CAP_EVERY_RISE_EDGE
#define	C1_EVERY_4_RISE_EDGE		CAP_EVERY_4_RISE_EDGE
#define	C1_EVERY_16_RISE_EDGE		CAP_EVERY_16_RISE_EDGE
#define	C2_EVERY_FALL_EDGE			CAP_EVERY_FALL_EDGE
#define	C2_EVERY_RISE_EDGE			CAP_EVERY_RISE_EDGE
#define	C2_EVERY_4_RISE_EDGE		CAP_EVERY_4_RISE_EDGE
#define	C2_EVERY_16_RISE_EDGE		CAP_EVERY_16_RISE_EDGE
#define	C3_EVERY_FALL_EDGE			CAP_EVERY_FALL_EDGE
#define	C3_EVERY_RISE_EDGE			CAP_EVERY_RISE_EDGE
#define	C3_EVERY_4_RISE_EDGE		CAP_EVERY_4_RISE_EDGE
#define	C3_EVERY_16_RISE_EDGE		CAP_EVERY_16_RISE_EDGE
#define	C4_EVERY_FALL_EDGE			CAP_EVERY_FALL_EDGE
#define	C4_EVERY_RISE_EDGE			CAP_EVERY_RISE_EDGE
#define	C4_EVERY_4_RISE_EDGE		CAP_EVERY_4_RISE_EDGE
#define	C4_EVERY_16_RISE_EDGE		CAP_EVERY_16_RISE_EDGE
#define	C5_EVERY_FALL_EDGE			CAP_EVERY_FALL_EDGE
#define	C5_EVERY_RISE_EDGE			CAP_EVERY_RISE_EDGE
#define	C5_EVERY_4_RISE_EDGE		CAP_EVERY_4_RISE_EDGE
#define	C5_EVERY_16_RISE_EDGE		CAP_EVERY_16_RISE_EDGE
#define	EC1_EVERY_FALL_EDGE			ECAP_EVERY_FALL_EDGE
#define	EC1_EVERY_RISE_EDGE			ECAP_EVERY_RISE_EDGE
#define	EC1_EVERY_4_RISE_EDGE		ECAP_EVERY_4_RISE_EDGE
#define	EC1_EVERY_16_RISE_EDGE		ECAP_EVERY_16_RISE_EDGE

#if defined CC1_IO_V1	
#define CP1_TRIS	TRISBbits.TRISB3 
#elif defined CC1_IO_V2
#define CP1_TRIS	TRISCbits.TRISC5 
#elif defined CC8_IO_V1
#define CP1_TRIS    TRISBbits.TRISB4
#elif defined CC8_IO_V2
#define CP1_TRIS    TRISDbits.TRISD4
#elif defined CC9_IO_V1
#define CP1_TRIS    TRISCbits.TRISC2
#else
#define	CP1_TRIS	TRISCbits.TRISC2 
#endif

#if defined CC8_IO_V1
#define CP2_TRIS    TRISCbits.TRISC2
#elif defined CC8_IO_V2
#define CP2_TRIS    TRISCbits.TRISC2
#elif defined CC9_IO_V1
#define CP2_TRIS    TRISBbits.TRISB3
#else
#define CP2_TRIS    TRISEbits.TRISE7
#endif

#if defined CC3_IO_V1	
#define CP3_TRIS	TRISDbits.TRISD1 
#elif defined CC8_IO_V1
#define CP3_TRIS    TRISCbits.TRISC6
#elif defined CC8_IO_V2
#define CP3_TRIS    TRISCbits.TRISC6
#elif defined CC9_IO_V1
#define CP3_TRIS    TRISBbits.TRISB5
#else	
#define CP3_TRIS	TRISGbits.TRISG0 
#endif

#if defined CC4_IO_V1
#define CP4_TRIS    TRISDbits.TRISD2
#elif defined CC4_IO_V2
#define CP4_TRIS    TRISBbits.TRISB4
#elif defined CC8_IO_V1
#define CP4_TRIS    TRISCbits.TRISC7
#elif defined CC8_IO_V2
#define CP4_TRIS    TRISCbits.TRISC7
#elif defined CC9_IO_V1
#define CP4_TRIS    TRISBbits.TRISB0
#else
#define CP4_TRIS    TRISGbits.TRISG3
#endif

#if defined CC4_IO_V2	
#define CP5_TRIS    TRISBbits.TRISB5
#elif defined CC8_IO_V1
#define CP5_TRIS    TRISBbits.TRISB5
#elif defined CC8_IO_V2
#define CP5_TRIS    TRISBbits.TRISB5
#elif defined CC9_IO_V1
#define CP5_TRIS    TRISAbits.TRISA4
#else	
#define CP5_TRIS    TRISGbits.TRISG4
#endif

#if defined CC4_IO_V2	
#define CP6_TRIS    TRISBbits.TRISB6
#else	
#define CP6_TRIS    TRISEbits.TRISE6
#endif

#if defined CC4_IO_V2	
#define CP7_TRIS    TRISBbits.TRISB7
#else	
#define CP7_TRIS    TRISEbits.TRISE5
#endif

#if defined CC4_IO_V2	
#define CP8_TRIS    TRISCbits.TRISC1
#else	
#define CP8_TRIS    TRISEbits.TRISE4
#endif

#if defined CC4_IO_V2	
#define CP9_TRIS    TRISCbits.TRISC6
#else	
#define CP9_TRIS    TRISEbits.TRISE3
#endif

#if defined CC4_IO_V2	
#define CP10_TRIS    TRISCbits.TRISC7
#else	
#define CP10_TRIS   TRISEbits.TRISE2
#endif


#endif
