#ifndef __PCPWM_H
#define __PCPWM_H
/******************************************************************************
 // *                   PCPWM PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		pcpwm.h
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


#if defined (PCPWM_V1) || defined (PCPWM_V2) || defined (PCPWM_V3)

/***********************************************************************************
Macro       : PCPWM_TMR_INT_EN

Include     : pcpwm.h

Description : Macro enables PCPWM Timer Interrupt
 
Arguments   : None
 
Remarks     : Clears the Timer Interrupt flag and enables the Interrupt
***********************************************************************************/
#define PCPWM_TMR_INT_EN 	{  \
							PIR3bits.PTIF = 0; \
							PIE3bits.PTIE = 1;}

/***********************************************************************************
Macro       : PCPWM_TMR_INT_DIS

Include     : pcpwm.h

Description : Macro disables PCPWM Timer Interrupt
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/
#define PCPWM_TMR_INT_DIS {PIE3bits.PTIE = 0;}

#endif

//---------pcpwm.h----------------------

// DTCON register
// DTCON<7:6> 
//Dead-Time Unit A Prescale Select bits
#ifndef USE_OR_MASKS
#define DT_CLK_SRC_FOSC_16		0b11111111		//Clock source for dead-time unit is FOSC/16
#define DT_CLK_SRC_FOSC_8	    0b10111111		//Clock source for dead-time unit is FOSC/8
#define DT_CLK_SRC_FOSC_4		0b01111111		//Clock source for dead-time unit is FOSC/4
#define DT_CLK_SRC_FOSC_2		0b00111111		//Clock source for dead-time unit is FOSC/2
#else
#define DT_CLK_SRC_FOSC_16		0b11000000		//Clock source for dead-time unit is FOSC/16
#define DT_CLK_SRC_FOSC_8	    0b10000000		//Clock source for dead-time unit is FOSC/8
#define DT_CLK_SRC_FOSC_4		0b01000000		//Clock source for dead-time unit is FOSC/4
#define DT_CLK_SRC_FOSC_2		0b00000000		//Clock source for dead-time unit is FOSC/2
#define DT_CLK_SRC_MASK			(~DT_CLK_SRC_FOSC_16)	//Mask Dead-Time Unit A Prescale Select bits
#endif

// PTCON0 register
// PTCON0<7:4>

#ifndef USE_OR_MASKS
#define PT_POS_1_1				0b00001111 		//1:1 Time Base Output Postscale
#define PT_POS_1_2				0b00011111 		//1:2 Time Base Output Postscale
#define PT_POS_1_3				0b00101111 		//1:3 Time Base Output Postscale
#define PT_POS_1_4				0b00111111 		//1:4 Time Base Output Postscale
#define PT_POS_1_5				0b01001111 		//1:5 Time Base Output Postscale
#define PT_POS_1_6				0b01011111 		//1:6 Time Base Output Postscale
#define PT_POS_1_7				0b01101111 		//1:7 Time Base Output Postscale
#define PT_POS_1_8				0b01111111 		//1:8 Time Base Output Postscale
#define PT_POS_1_9				0b10001111 		//1:9 Time Base Output Postscale
#define PT_POS_1_10				0b10011111 		//1:10 Time Base Output Postscale
#define PT_POS_1_11				0b10101111 		//1:11 Time Base Output Postscale
#define PT_POS_1_12				0b10111111 		//1:12 Time Base Output Postscale
#define PT_POS_1_13				0b11001111 		//1:13 Time Base Output Postscale
#define PT_POS_1_14				0b11011111 		//1:14 Time Base Output Postscale
#define PT_POS_1_15				0b11101111 		//1:15 Time Base Output Postscale
#define PT_POS_1_16				0b11111111 		//1:16 Time Base Output Postscale
#else 
#define PT_POS_1_1				0b00000000 		//1:1 Time Base Output Postscale
#define PT_POS_1_2				0b00010000 		//1:2 Time Base Output Postscale
#define PT_POS_1_3				0b00100000 		//1:3 Time Base Output Postscale
#define PT_POS_1_4				0b00110000 		//1:4 Time Base Output Postscale
#define PT_POS_1_5				0b01000000 		//1:5 Time Base Output Postscale
#define PT_POS_1_6				0b01010000 		//1:6 Time Base Output Postscale
#define PT_POS_1_7				0b01100000 		//1:7 Time Base Output Postscale
#define PT_POS_1_8				0b01110000 		//1:8 Time Base Output Postscale
#define PT_POS_1_9				0b10000000 		//1:9 Time Base Output Postscale
#define PT_POS_1_10				0b10010000 		//1:10 Time Base Output Postscale
#define PT_POS_1_11				0b10100000 		//1:11 Time Base Output Postscale
#define PT_POS_1_12				0b10110000 		//1:12 Time Base Output Postscale
#define PT_POS_1_13				0b11000000 		//1:13 Time Base Output Postscale
#define PT_POS_1_14				0b11010000 		//1:14 Time Base Output Postscale
#define PT_POS_1_15				0b11100000 		//1:15 Time Base Output Postscale
#define PT_POS_1_16				0b11110000 		//1:16 Time Base Output Postscale
#define PT_POS_MASK				(~PT_POS_1_16)	//Masks all Time Base Output Postscaler bits
#endif

// PTCON0<3:2>
#ifndef USE_OR_MASKS
#define PT_PRS_1_1				0b11110011		// PWM time base input clock is FOSC/4
#define PT_PRS_1_4				0b11110111		// PWM time base input clock is FOSC/16
#define PT_PRS_1_16				0b11111011		// PWM time base input clock is FOSC/64
#define PT_PRS_1_64				0b11111111		// PWM time base input clock is FOSC/256
#else
#define PT_PRS_1_1				0b00000000		// PWM time base input clock is FOSC/4
#define PT_PRS_1_4				0b00000100		// PWM time base input clock is FOSC/16
#define PT_PRS_1_16				0b00001000		// PWM time base input clock is FOSC/64
#define PT_PRS_1_64				0b00001100		// PWM time base input clock is FOSC/256
#define	PT_PRS_MASK				(~PT_PRS_1_64)	// Masks all clock prescale bits 
#endif


// PTCON0<1:0>
#ifndef USE_OR_MASKS
#define PT_MOD_CNT_UPDN_INT		0b11111111		// PWM time base operates in a Continuous Up/Down Count mode with interrupts for double PWM updates
#define PT_MOD_CNT_UPDN			0b11111110		// PWM time base operates in a Continuous Up/Down Count mode
#define PT_MOD_SNGL_SHOT		0b11111101		// PWM time base configured for Single-Shot mode
#define PT_MOD_FREE_RUN			0b11111100		// PWM time base operates in a Free-Running mode
#else
#define PT_MOD_CNT_UPDN_INT		0b00000011		// PWM time base operates in a Continuous Up/Down Count mode with interrupts for double PWM updates
#define PT_MOD_CNT_UPDN			0b00000010		// PWM time base operates in a Continuous Up/Down Count mode
#define PT_MOD_SNGL_SHOT		0b00000001		// PWM time base configured for Single-Shot mode
#define PT_MOD_FREE_RUN			0b00000000		// PWM time base operates in a Free-Running mode
#define	PT_MOD_MASK				(~PT_MOD_CNT_UPDN_INT)	// Masks Time base mode select bits 
#endif

// PTCON1 register
// PTCON1<7>
#ifndef USE_OR_MASKS
#define PT_ENABLE				0b11111111		// PWM time base is on
#define PT_DISABLE				0b01111111		// PWM time base is off
#else
#define PT_ENABLE				0b10000000		// PWM time base is on
#define PT_DISABLE				0b00000000		// PWM time base is off
#define PT_MASK					(~PT_ENABLE)	// Masks Time Base timer enable bit
#endif


// PTCON1<6>
#ifndef USE_OR_MASKS
#define PT_CNT_UP				0b10111111		// PWM time base counts down
#define PT_CNT_DWN				0b11111111		// PWM time base counts up
#else
#define PT_CNT_UP				0b00000000		// PWM time base counts down
#define PT_CNT_DWN				0b01000000		// PWM time base counts up
#define PT_CNT_MASK				(~PT_CNT_DWN)	// Masks Time Base count direction bit 
#endif


// PWMCON0 register
// PWMCON0<6:4>

#ifndef USE_OR_MASKS
#define PWM_IO_ALL_ODD			0b01111111		//All odd PWM I/O pins enabled for PWM output
#define PWM_IO_1AND3			0b01101111		//PWM1, PWM3 pins enabled for PWM output
#define PWM_IO_ALL				0b01011111		//All PWM I/O pins enabled for PWM output
#define PWM_IO_0TO5				0b01001111		// All PWM I/O pins enabled for PWM output
#define PWM_IO_0TO3				0b00111111		// PWM0, PWM1, PWM2 and PWM3 I/O pins enabled for PWM output
#define PWM_IO_0AND1			0b00101111		// PWM0 and PWM1 pins enabled for PWM output
#define PWM_IO_1				0b00011111		// PWM1 pin is enabled for PWM output
#define PWM_DISABLE				0b00001111		// PWM module disabled; all PWM I/O pins are general purpose I/O
#else
#define PWM_IO_ALL_ODD			0b01110000		//All odd PWM I/O pins enabled for PWM output
#define PWM_IO_1AND3			0b01100000		//PWM1, PWM3 pins enabled for PWM output
#define PWM_IO_ALL				0b01010000		//All PWM I/O pins enabled for PWM output
#define PWM_IO_0TO5				0b01000000		// All PWM I/O pins enabled for PWM output
#define PWM_IO_0TO3				0b00110000		// PWM0, PWM1, PWM2 and PWM3 I/O pins enabled for PWM output
#define PWM_IO_0AND1			0b00100000		// PWM0 and PWM1 pins enabled for PWM output
#define PWM_IO_1				0b00010000		// PWM1 pin is enabled for PWM output
#define PWM_DISABLE				0b00000000		// PWM module disabled; all PWM I/O pins are general purpose I/O
#define	PWM_IO_MASK				(~PWM_IO_ALL_ODD)	// Mask all PWM Module Enable bits
#endif

// PWMCON0<3:0>

#ifndef USE_OR_MASKS
#define PWM_0AND1_INDPEN		0b11111111		// PWM I/O pin pair (PWM0, PWM1) is in the Independent mode
#define PWM_0AND1_COMPLI		0b11111110		// PWM I/O pin pair (PWM0, PWM1) is in the Complementary mode
#else 
#define PWM_0AND1_INDPEN		0b00000001		// PWM I/O pin pair (PWM0, PWM1) is in the Independent mode
#define PWM_0AND1_COMPLI		0b00000000		// PWM I/O pin pair (PWM0, PWM1) is in the Complementary mode
#define PWM_0AND1_MASK			(~PWM_0AND1_INDPEN)	// Masks PWM Output Pair Mode bits
#endif

#ifndef USE_OR_MASKS
#define PWM_2AND3_INDPEN		0b11111111		// PWM I/O pin pair (PWM2, PWM3) is in the Independent mode
#define PWM_2AND3_COMPLI		0b11111101		// PWM I/O pin pair (PWM2, PWM3) is in the Complementary mode
#else 
#define PWM_2AND3_INDPEN		0b00000010		// PWM I/O pin pair (PWM2, PWM3) is in the Independent mode
#define PWM_2AND3_COMPLI		0b00000000		// PWM I/O pin pair (PWM2, PWM3) is in the Complementary mode
#define PWM_2AND3_MASK			(~PWM_2AND3_INDPEN)	// Masks PWM Output Pair Mode bits
#endif

#ifndef USE_OR_MASKS
#define PWM_4AND5_INDPEN		0b11111111		// PWM I/O pin pair (PWM4, PWM5) is in the Independent mode
#define PWM_4AND5_COMPLI		0b11111011		// PWM I/O pin pair (PWM4, PWM5) is in the Complementary mode
#else 
#define PWM_4AND5_INDPEN		0b00000100		// PWM I/O pin pair (PWM4, PWM5) is in the Independent mode
#define PWM_4AND5_COMPLI		0b00000000		// PWM I/O pin pair (PWM4, PWM5) is in the Complementary mode
#define PWM_4AND5_MASK			(~PWM_4AND5_INDPEN)	// Masks PWM Output Pair Mode bits
#endif


#if defined (PCPWM_V1) || defined (PCPWM_V2)
#ifndef USE_OR_MASKS
#define PWM_6AND7_INDPEN		0b11111111		// PWM I/O pin pair (PWM6, PWM7) is in the Independent mode
#define PWM_6AND7_COMPLI		0b11110111		// PWM I/O pin pair (PWM6, PWM7) is in the Complementary mode
#else 
#define PWM_6AND7_INDPEN		0b00001000		// PWM I/O pin pair (PWM6, PWM7) is in the Independent mode
#define PWM_6AND7_COMPLI		0b00000000		// PWM I/O pin pair (PWM6, PWM7) is in the Complementary mode
#define PWM_6AND7_MASK			(~PWM_6AND7_INDPEN)	// Masks PWM Output Pair Mode bits
#endif
#endif

// PWMCON1 register
// PWMCON1<7:4>
#ifndef USE_OR_MASKS
#define PW_SEVT_POS_1_1				0b00001111 		//Special Event Trigger 1:1 Postscale
#define PW_SEVT_POS_1_2				0b00011111 		//Special Event Trigger 1:2 Postscale
#define PW_SEVT_POS_1_3				0b00101111 		//Special Event Trigger 1:3 Postscale
#define PW_SEVT_POS_1_4				0b00111111 		//Special Event Trigger 1:4 Postscale
#define PW_SEVT_POS_1_5				0b01001111 		//Special Event Trigger 1:5 Postscale
#define PW_SEVT_POS_1_6				0b01011111 		//Special Event Trigger 1:6 Postscale
#define PW_SEVT_POS_1_7				0b01101111 		//Special Event Trigger 1:7 Postscale
#define PW_SEVT_POS_1_8				0b01111111 		//Special Event Trigger 1:8 Postscale
#define PW_SEVT_POS_1_9				0b10001111 		//Special Event Trigger 1:9 Postscale
#define PW_SEVT_POS_1_10			0b10011111 		//Special Event Trigger 1:10 Postscale
#define PW_SEVT_POS_1_11			0b10101111 		//Special Event Trigger 1:11 Postscale
#define PW_SEVT_POS_1_12			0b10111111 		//Special Event Trigger 1:12 Postscale
#define PW_SEVT_POS_1_13			0b11001111 		//Special Event Trigger 1:13 Postscale
#define PW_SEVT_POS_1_14			0b11011111 		//Special Event Trigger 1:14 Postscale
#define PW_SEVT_POS_1_15			0b11101111 		//Special Event Trigger 1:15 Postscale
#define PW_SEVT_POS_1_16			0b11111111 		//Special Event Trigger 1:16 Postscale
#else
#define PW_SEVT_POS_1_1				0b00000000 		//Special Event Trigger 1:1 Postscale
#define PW_SEVT_POS_1_2				0b00010000 		//Special Event Trigger 1:2 Postscale
#define PW_SEVT_POS_1_3				0b00100000 		//Special Event Trigger 1:3 Postscale
#define PW_SEVT_POS_1_4				0b00110000 		//Special Event Trigger 1:4 Postscale
#define PW_SEVT_POS_1_5				0b01000000 		//Special Event Trigger 1:5 Postscale
#define PW_SEVT_POS_1_6				0b01010000 		//Special Event Trigger 1:6 Postscale
#define PW_SEVT_POS_1_7				0b01100000 		//Special Event Trigger 1:7 Postscale
#define PW_SEVT_POS_1_8				0b01110000 		//Special Event Trigger 1:8 Postscale
#define PW_SEVT_POS_1_9				0b10000000 		//Special Event Trigger 1:9 Postscale
#define PW_SEVT_POS_1_10			0b10010000 		//Special Event Trigger 1:10 Postscale
#define PW_SEVT_POS_1_11			0b10100000 		//Special Event Trigger 1:11 Postscale
#define PW_SEVT_POS_1_12			0b10110000 		//Special Event Trigger 1:12 Postscale
#define PW_SEVT_POS_1_13			0b11000000 		//Special Event Trigger 1:13 Postscale
#define PW_SEVT_POS_1_14			0b11010000 		//Special Event Trigger 1:14 Postscale
#define PW_SEVT_POS_1_15			0b11100000 		//Special Event Trigger 1:15 Postscale
#define PW_SEVT_POS_1_16			0b11110000 		//Special Event Trigger 1:16 Postscale
#define PW_SEVT_POS_MASK			(~PW_SEVT_POS_1_16)		//Masks Special Event Trigger Postscale bits
#endif


// PWMCON1<3>
#ifndef USE_OR_MASKS
#define PW_SEVT_DIR_UP				0b11110111		// A Special Event Trigger will occur when the PWM time base is counting upwards
#define PW_SEVT_DIR_DWN				0b11111111		// A Special Event Trigger will occur when the PWM time base is counting downwards
#else
#define PW_SEVT_DIR_UP				0b00000000		// A Special Event Trigger will occur when the PWM time base is counting upwards
#define PW_SEVT_DIR_DWN				0b00001000		// A Special Event Trigger will occur when the PWM time base is counting downwards
#define PW_SEVT_DIR_MASK			(~PW_SEVT_DIR_DWN)	// PMasks Direction bit
#endif

// PWMCON1<0>
#ifndef USE_OR_MASKS
#define PW_OP_SYNC					0b11111111		// Output overrides via the OVDCON register are synchronized to the PWM time base
#define PW_OP_ASYNC					0b11111110		// Output overrides via the OVDCON register are asynchronous			
#else 
#define PW_OP_SYNC					0b00000001		// Output overrides via the OVDCON register are synchronized to the PWM time base
#define PW_OP_ASYNC					0b00000000		// Output overrides via the OVDCON register are asynchronous
#define PW_OP_SYNC_MASK				(~PW_OP_SYNC)	// Masks override sync bit 
#endif

// FLTCONFIG
// FLTCONFIG<7>

/***********************************************************************************
Macro       : BRK_FLT_EN

Include     : pcpwm.h

Description : Macro Enables Fault condition on a breakpoint
 
Arguments   : None
 
Remarks     : This feature is enabled only when PWMPIN = 1
***********************************************************************************/
#define BRK_FLT_EN					FLTCONFIGbits.BRFEN = 1;

/***********************************************************************************
Macro       : BRK_FLT_DIS

Include     : pcpwm.h

Description : Macro Disables Fault condition on a breakpoint
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/	
#define BRK_FLT_DIS					FLTCONFIGbits.BRFEN = 0;


#if defined (PCPWM_V1) || defined (PCPWM_V2)
// FLTCONFIG<5>

/***********************************************************************************
Macro       : FLT_B_CY_CY

Include     : pcpwm.h

Description : Macro enables Cycle-by-Cycle fault B mode
 
Arguments   : None
 
Remarks     : Pins are inactive for the remainder of the current PWM period or until FLTB
		is deasserted; FLTBS is cleared automatically when FLTB is inactive (no Fault present)
***********************************************************************************/	
#define FLT_B_CY_CY					FLTCONFIGbits.FLTBMOD = 1;

/***********************************************************************************
Macro       : FLT_B_CATAS

Include     : pcpwm.h

Description : Macro Disables from Cycle-by-Cycle to inactive mode in fault B mode
 
Arguments   : None
 
Remarks     : Pins are deactivated (catastrophic failure) until FLTB is deasserted and FLTBS is
		cleared by the user only
***********************************************************************************/	
#define FLT_B_CATAS					FLTCONFIGbits.FLTBMOD = 0;	

// FLTCONFIG<4>

/***********************************************************************************
Macro       : FLT_B_EN

Include     : pcpwm.h

Description : Macro enables FAULT B MODE
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/	
#define FLT_B_EN					FLTCONFIGbits.FLTBEN = 1;

/***********************************************************************************
Macro       : FLT_B_DIS

Include     : pcpwm.h

Description : Macro disables FAULT B MODE
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/			
#define FLT_B_DIS					FLTCONFIGbits.FLTBEN = 0;

// FLTCONFIG<3>

/***********************************************************************************
Macro       : FLT_AB_DEACT_ALL

Include     : pcpwm.h

Description : FLTA, FLTB or both deactivate all PWM outputs
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/	
#define FLT_AB_DEACT_ALL				FLTCONFIGbits.FLTCON = 1;

/***********************************************************************************
Macro       : FLT_AB_DEACT_0TO5

Include     : pcpwm.h

Description : FLTA or FLTB deactivates PWM<5:0>
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/	
#define FLT_AB_DEACT_0TO5				FLTCONFIGbits.FLTCON = 0;
#endif


// FLTCONFIG<1>

/***********************************************************************************
Macro       : FLT_A_CY_CY

Include     : pcpwm.h

Description : Macro enables Cycle-by-Cycle fault A mode
 
Arguments   : None
 
Remarks     : Pins are inactive for the remainder of the current PWM period or until FLTA
		is deasserted; FLTAS is cleared automatically when FLTA is inactive (no Fault present)
***********************************************************************************/	
#define FLT_A_CY_CY					FLTCONFIGbits.FLTAMOD = 1;

/***********************************************************************************
Macro       : FLT_A_CATAS

Include     : pcpwm.h

Description : Macro Disables from Cycle-by-Cycle to inactive mode in fault A mode
 
Arguments   : None
 
Remarks     : Pins are deactivated (catastrophic failure) until FLTA is deasserted and FLTAS is
		cleared by the user only
***********************************************************************************/		
#define FLT_A_CATAS					FLTCONFIGbits.FLTAMOD = 0;

// FLTCONFIG<0>

/***********************************************************************************
Macro       : FLT_A_EN

Include     : pcpwm.h

Description : Macro enables FAULT A MODE
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/	
#define FLT_A_EN					FLTCONFIGbits.FLTAEN = 1;

/***********************************************************************************
Macro       : FLT_A_DIS

Include     : pcpwm.h

Description : Macro disables FAULT A MODE
 
Arguments   : None
 
Remarks     : None
***********************************************************************************/
#define FLT_A_DIS					FLTCONFIGbits.FLTAEN = 0;	


#if defined (PCPWM_V1) 

void Openpcpwm(unsigned char ,unsigned char ,unsigned char ,unsigned char,unsigned int ,unsigned int );

void Setdc0pcpwm(unsigned int );

void Setdc1pcpwm(unsigned int );

void Setdc2pcpwm(unsigned int );

void Closepcpwm(void);

void pcpwm_dt_clk_source(unsigned char );

void pcpwm_dt_assignment(unsigned char );

void pcpwm_OVD_CTRL(unsigned char );

void pcpwm_OVD_IO_STA(unsigned char );

#endif

#if defined (PCPWM_V2)

void Openpcpwm(unsigned char ,unsigned char ,unsigned char ,unsigned char,unsigned int ,unsigned int );

void Setdc0pcpwm(unsigned int );

void Setdc1pcpwm(unsigned int );

void Setdc2pcpwm(unsigned int );

void Setdc3pcpwm(unsigned int );

void Closepcpwm(void);

void pcpwm_dt_clk_source(unsigned char );

void pcpwm_dt_assignment(unsigned char );

void pcpwm_OVD_CTRL(unsigned char );

void pcpwm_OVD_IO_STA(unsigned char );

#endif


#if defined (PCPWM_V3) 

void Openpcpwm(unsigned char ,unsigned char ,unsigned char ,unsigned char,unsigned int ,unsigned int );

void Setdc0pcpwm(unsigned int );

void Setdc1pcpwm(unsigned int );

void Setdc2pcpwm(unsigned int );

void Closepcpwm(void);

void pcpwm_dt_clk_source(unsigned char );

void pcpwm_dt_assignment(unsigned char );

void pcpwm_OVD_CTRL(unsigned char );

void pcpwm_OVD_IO_STA(unsigned char );

#endif

#endif // __PCPWM_H

