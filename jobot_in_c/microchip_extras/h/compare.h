#ifndef __COMPARE_H
#define __COMPARE_H
/******************************************************************************
 // *                   COMPARE PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		compare.h
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
//*************** Compare unit enable/disable **********************************************
#define COM_INT_ON   0b11111111  			/*Enable Compare unit interrupt */
#define COM_INT_OFF  0b01111111 			/*Disable Compare unit interrupt */

//************** CCPx Module Mode Selection **********************************************
#define COM_TOGG_MATCH   0b10000010			/*Compare mode, toggle output on match */
#define COM_HI_MATCH     0b10001000  		/*Compare mode: initialize CCPx pin low; on compare match, force CCPx pin high*/
#define COM_LO_MATCH     0b10001001  		/*Compare mode: initialize CCPx pin high; on compare match, force CCPx pin low*/
#define COM_UNCHG_MATCH  0b10001010  		/*Compare mode: generate software interrupt on compare match*/
#define COM_TRIG_SEVNT	 0b10001011	 		/*Compare mode: Special Event Trigger; reset timer; start A/D conversion on CCPx match*/

//**************ECCPx Module Mode Selection **********************************************
#define ECOM_TOGG_MATCH   0b10000010  		/*Compare mode, toggle output on match */
#define ECOM_HI_MATCH     0b10001000  		/*Compare mode: initialize ECCPx pin low; on compare match, force ECCPx pin high*/
#define ECOM_LO_MATCH     0b10001001  		/*Compare mode: initialize ECCPx pin high; on compare match, force CCPx pin low*/
#define ECOM_UNCHG_MATCH  0b10001010  		/*Compare mode: generate software interrupt on compare match*/
#define ECOM_TRIG_SEVNT	  0b10001011  		/*Compare mode: Special Event Trigger; reset timer; start A/D conversion on ECCPx match*/

#else

//*************** Compare unit enable/disable **********************************************
#define COM_INT_ON   0b10000000  			/*Enable Compare unit interrupt */
#define COM_INT_OFF  0b00000000  			/*Disable Compare unit interrupt */
#define COM_INT_MASK	(~COM_INT_ON)		//Mask Compare unit interrupt enable/Diable bits

//************** CCPx Module Mode Selection **********************************************
#define COM_TOGG_MATCH   0b00000010			/*Compare mode, toggle output on match */
#define COM_HI_MATCH     0b00001000  		/*Compare mode: initialize CCPx pin low; on compare match, force CCPx pin high*/
#define COM_LO_MATCH     0b00001001  		/*Compare mode: initialize CCPx pin high; on compare match, force CCPx pin low*/
#define COM_UNCHG_MATCH  0b00001010  		/*Compare mode: generate software interrupt on compare match*/
#define COM_TRIG_SEVNT	 0b00001011	 		/*Compare mode: Special Event Trigger; reset timer; start A/D conversion on CCPx match*/
#define COM_MODE_MASK	(0b11110000) 		//Mask  CCPx Module Mode Selection bits

//**************ECCPx Module Mode Selection **********************************************
#define ECOM_TOGG_MATCH   0b00000010  		/*Compare mode, toggle output on match */
#define ECOM_HI_MATCH     0b00001000  		/*Compare mode: initialize ECCPx pin low; on compare match, force ECCPx pin high*/
#define ECOM_LO_MATCH     0b00001001  		/*Compare mode: initialize ECCPx pin high; on compare match, force CCPx pin low*/
#define ECOM_UNCHG_MATCH  0b00001010  		/*Compare mode: generate software interrupt on compare match*/
#define ECOM_TRIG_SEVNT	  0b00001011  		/*Compare mode: Special Event Trigger; reset timer; start A/D conversion on ECCPx match*/
#define ECOM_MODE_MASK	  (0b11110000)		//Mask  ECCPx Module Mode Selection bits

#endif //USE_OR_MASKS


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


#if defined (CC_V1) || defined (CC_V2) || defined (CC_V3) || defined (CC_V4) || defined (CC_V5) || defined (CC_V6) || defined (CC_V7)
void OpenCompare1(unsigned char config,unsigned int period);
void CloseCompare1(void);
#endif


#if defined (CC_V2 ) || defined ( CC_V3 ) || defined ( CC_V4 ) ||\
	defined (CC_V6) || defined (CC_V7) || defined (CC_V8_2)
void OpenCompare2(unsigned char config, unsigned int period);
void CloseCompare2(void);
#endif

#if defined (CC_V3 ) || defined (CC_V4) || defined (CC_V8_2)
void OpenCompare3(unsigned char config,unsigned int period);
void CloseCompare3(void);
#endif

#if defined (CC_V4) || defined (CC_V8) || defined (CC_V8_1) || defined (CC_V8_2)\
	|| defined (CC_V9) || defined (CC_V9_1)
void OpenCompare4(unsigned char config,unsigned int period);
void CloseCompare4(void);

void OpenCompare5(unsigned char config,unsigned int period);
void CloseCompare5(void);
#endif

#if defined (CC_V8) || defined (CC_V8_1)
void OpenCompare6(unsigned char config,unsigned int period);
void CloseCompare6(void);

void OpenCompare7(unsigned char config,unsigned int period);
void CloseCompare7(void);

void OpenCompare8(unsigned char config,unsigned int period);
void CloseCompare8(void);

#if defined (CC_V8)
void OpenCompare9(unsigned char config,unsigned int period);
void CloseCompare9(void);

void OpenCompare10(unsigned char config,unsigned int period);
void CloseCompare10(void);
#endif

#endif

#if defined (ECC_V5) || defined (ECC_V8) || defined (ECC_V8_1) ||\
 defined (ECC_V8_2) || defined (ECC_V9) || defined (ECC_V9_1)
void OpenECompare1(unsigned char config,unsigned int period);
void CloseECompare1(void);
#endif

#if defined (ECC_V8) || defined (ECC_V8_1) || defined (ECC_V9) || defined (ECC_V9_1)
void OpenECompare2(unsigned char config,unsigned int period);
void CloseECompare2(void);

void OpenECompare3(unsigned char config,unsigned int period);
void CloseECompare3(void);
#endif

#if defined CC1_IO_V1	
#define CM1_TRIS	TRISBbits.TRISB3 
#elif defined CC1_IO_V2
#define CM1_TRIS	TRISCbits.TRISC5 
#elif defined CC8_IO_V1
#define CM1_TRIS    TRISBbits.TRISB4
#elif defined CC8_IO_V2
#define CM1_TRIS    TRISDbits.TRISD4
#elif defined CC9_IO_V1
#define CM1_TRIS    TRISCbits.TRISC2
#else
#define	CM1_TRIS	TRISCbits.TRISC2 
#endif

#if defined CC8_IO_V1
#define CM2_TRIS    TRISCbits.TRISC2
#elif defined CC8_IO_V2
#define CM2_TRIS    TRISCbits.TRISC2
#elif defined CC9_IO_V1
#define CM2_TRIS    TRISBbits.TRISB3
#else
#define CM2_TRIS    TRISEbits.TRISE7
#endif

#if defined CC3_IO_V1	
#define CM3_TRIS	TRISDbits.TRISD1 
#elif defined CC8_IO_V1
#define CM3_TRIS    TRISCbits.TRISC6
#elif defined CC8_IO_V2
#define CM3_TRIS    TRISCbits.TRISC6
#elif defined CC9_IO_V1
#define CM3_TRIS    TRISBbits.TRISB5
#else	
#define CM3_TRIS	TRISGbits.TRISG0 
#endif

#if defined CC4_IO_V1
#define CM4_TRIS    TRISDbits.TRISD2
#elif defined CC4_IO_V2
#define CM4_TRIS    TRISBbits.TRISB4
#elif defined CC8_IO_V1
#define CM4_TRIS    TRISCbits.TRISC7
#elif defined CC8_IO_V2
#define CM4_TRIS    TRISCbits.TRISC7
#elif defined CC9_IO_V1
#define CM4_TRIS    TRISBbits.TRISB0
#else
#define CM4_TRIS    TRISGbits.TRISG3
#endif

#if defined CC4_IO_V2	
#define CM5_TRIS    TRISBbits.TRISB5
#elif defined CC8_IO_V1
#define CM5_TRIS    TRISBbits.TRISB5
#elif defined CC8_IO_V2
#define CM5_TRIS    TRISBbits.TRISB5
#elif defined CC9_IO_V1
#define CM5_TRIS    TRISAbits.TRISA4
#else	
#define CM5_TRIS    TRISGbits.TRISG4
#endif

#if defined CC4_IO_V2	
#define CM6_TRIS    TRISBbits.TRISB6
#else	
#define CM6_TRIS    TRISEbits.TRISE6
#endif

#if defined CC4_IO_V2	
#define CM7_TRIS    TRISBbits.TRISB7
#else	
#define CM7_TRIS    TRISEbits.TRISE5
#endif

#if defined CC4_IO_V2	
#define CM8_TRIS    TRISCbits.TRISC1
#else	
#define CM8_TRIS    TRISEbits.TRISE4
#endif

#if defined CC4_IO_V2	
#define CM9_TRIS    TRISCbits.TRISC6
#else	
#define CM9_TRIS    TRISEbits.TRISE3
#endif

#if defined CC4_IO_V2	
#define CM10_TRIS    TRISCbits.TRISC7
#else	
#define CM10_TRIS   TRISEbits.TRISE2
#endif


#endif
