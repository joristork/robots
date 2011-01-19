#ifndef __CTMU_H
#define __CTMU_H
/******************************************************************************
 // *                   CHARGE TIME MEASUREMENT UNIT PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		ctmu.h
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
 
#include "pconfig.h"

/* PIC18 Charge Time Measurement Unit (CTMU) peripheral library. */

#if	defined (CTMU_V1)
/*CTMUCON Control Register*/
 #define CTMU_ENABLE       			   0b10000000 /*CTMU is Enabled*/
 #define CTMU_DISABLE      			   0b00000000 /*CTMU is Disabled*/
 #define CTMU_ENBL_DISBL_MASK          (~CTMU_ENABLE)	//Mask CTMU Enable/Disable bit

 #define CTMU_IDLE_STOP                0b00100000 /*CTMU discontinue module operation in Idle mode*/
 #define CTMU_IDLE_CONTINUE            0b00000000 /*CTMU Operate in Idle mode*/
 #define CTMU_IDLE_MASK                (~CTMU_IDLE_STOP)	//Mask CTMU Idle mode selection bit

 #define CTMU_TIME_GEN_ENABLE          0b00010000 /*CTMU enables edge delay generation*/
 #define CTMU_TIME_GEN_DISABLE         0b00000000 /*CTMU disables edge delay generation*/
 #define CTMU_TIME_GEN_MASK            (~CTMU_TIME_GEN_ENABLE)	//Mask CTMU Time generation selection bit

 #define CTMU_EDGE_ENABLE              0b00001000 /*CTMU edges are not blocked*/
 #define CTMU_EDGE_DISABLE             0b00000000 /*CTMU edges are blocked*/
 #define CTMU_EDGE_MASK                (~CTMU_EDGE_ENABLE)	//Mask CTMU Edge enable/disable bit

 #define CTMU_EDGE_SEQUENCE_ON         0b00000100 /*Edge1 event must occur before edge2 event can occur*/
 #define CTMU_EDGE_SEQUENCE_OFF        0b00000000 /*No edge sequence os needed*/
 #define CTMU_EDGE_SEQUENCE_MASK       (~CTMU_EDGE_SEQUENCE)	//Mask CTMU edge sequence selection bit

 #define CTMU_ANA_CURR_SOURCE_GND      0b00000010 /*CTMU Analog current source output is grounded*/
 #define CTMU_ANA_CURR_SOURCE_NOT_GND  0b00000000 /*CTMU Analog current source output is not grounded*/
 #define CTMU_ANA_CURR_SOURCE_MASK     (~CTMU_ANA_CURR_SOURCE_GND)	//Mask CTMU current source grounding bit

 #define CTMU_TRIG_OUTPUT_ENABLE       0b00000001 /*Trigger output is enabled*/
 #define CTMU_TRIG_OUTPUT_DISABLE      0b00000000 /*Trigger output is disabled*/
 #define CTMU_TRIG_OUTPUT_MASK         (~CTMU_TRIG_OUTPUT)	//Mask CTMU trigger output enable/disable bit

 #define CTMU_EDGE1_POLARITY_POS       0b00010000 /*Edge 1 programmed for a positive edge response*/
 #define CTMU_EDGE1_POLARITY_NEG       0b00000000 /*Edge 1 programmed for a negative edge response*/
 #define CTMU_EDGE1_POLARITY_MASK      (~CTMU_EDGE2_POS_POLARITY)	//Mask CTMU edge1 polarity selection bit

 #define CTMU_EDGE1_SOURCE_CTED1       0b00001100 /*CTED1 is a source select for Edge1*/
 #define CTMU_EDGE1_SOURCE_CTED2       0b00001000 /*CTED2 is a source select for Edge1*/
 #define CTMU_EDGE1_SOURCE_OC1         0b00000100 /*OC1 is a source select for Edge1*/
 #define CTMU_EDGE1_SOURCE_TIMER1      0b00000000 /*TIMER1 is a source select for Edge1*/
 #define CTMU_EDGE1_SOURCE_MASK        (~CTMU_EDGE2_SOURCE_CTED1)	//Mask CTMU trigger edge source1 selection bit

 #define CTMU_EDGE2_POLARITY_POS       0b10000000 /*Edge 2 programmed for a positive edge response*/
 #define CTMU_EDGE2_POLARITY_NEG       0b00000000 /*Edge 2 programmed for a negative edge response*/
 #define CTMU_EDGE2_POLARITY_MASK      (~CTMU_EDGE2_POS_POLARITY)	//Mask CTMU edge2 polarity selection bit

 #define CTMU_EDGE2_SOURCE_CTED1       0b01100000 /*CTED1 is a source select for Edge2*/
 #define CTMU_EDGE2_SOURCE_CTED2       0b01000000 /*CTED2 is a source select for Edge2*/
 #define CTMU_EDGE2_SOURCE_OC1         0b00100000 /*OC1 is a source select for Edge2*/
 #define CTMU_EDGE2_SOURCE_TIMER1      0b00000000 /*TIMER1 is a source select for Edge2*/
 #define CTMU_EDGE2_SOURCE_MASK        (~CTMU_EDGE2_SOURCE_CTED1)	//Mask CTMU trigger edge source2 selection bit

 /*CTMU1CON Current Control register*/
 #define CTMU_POS_CURR_TRIM_62         0b01111100 /*62% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_60         0b01111000 /*60% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_58         0b01110100 /*58% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_56         0b01110000 /*56% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_54         0b01101100 /*54% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_52         0b01101000 /*52% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_50         0b01100100 /*50% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_48         0b01100000 /*48% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_46         0b01011100 /*46% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_44         0b01011000 /*44% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_42         0b01010100 /*42% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_40         0b01010000 /*40% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_38         0b01001100 /*38% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_36         0b01001000 /*36% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_34         0b01000100 /*34% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_32         0b01000000 /*32% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_30         0b00111100 /*30% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_28         0b00111000 /*28% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_26         0b00110100 /*26% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_24         0b00110000 /*24% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_22         0b00101100 /*22% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_20         0b00101000 /*20% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_18         0b00100100 /*18% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_16         0b00100000 /*16% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_14         0b00011100 /*14% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_12         0b00011000 /*12% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_10         0b00010100 /*10% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_8          0b00010000 /*8% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_6          0b00001100 /*6% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_4          0b00001000 /*4% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_2          0b00000100 /*2% of Positive change from nominal current*/
 #define CTMU_POS_CURR_TRIM_MASK       (~CTMU_POS_CURR_TRIM_62)	//Mask Positive change from nominal current selection bits

 #define CTMU_NOMINAL_CURRENT          0x0000 /*Nominal Current output specified by IRNG1:IRNG0*/

 #define CTMU_NEG_CURR_TRIM_2          0b11111100 /*2% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_4          0b11111000 /*4% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_6          0b11110100 /*6% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_8          0b11110000 /*8% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_10         0b11101100 /*10% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_12         0b11101000 /*12% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_14         0b11100100 /*14% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_16         0b11100000 /*16% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_18         0b11011100 /*18% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_20         0b11011000 /*20% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_22         0b11010100 /*22% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_24         0b11010000 /*24% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_26         0b11001100 /*26% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_28         0b11001000 /*28% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_30         0b11000100 /*30% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_32         0b11000000 /*32% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_34         0b10111100 /*34% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_36         0b10111000 /*36% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_38         0b10110100 /*38% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_40         0b10110000 /*40% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_42         0b10101100 /*42% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_44         0b10101000 /*44% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_46         0b10100100 /*46% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_48         0b10100000 /*48% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_50         0b10011100 /*50% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_52         0b10011000 /*52% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_54         0b10010100 /*54% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_56         0b10010000 /*56% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_58         0b10001100 /*58% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_60         0b10001000 /*60% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_62         0b10000100 /*62% of Negative change from nominal current*/
 #define CTMU_NEG_CURR_TRIM_MASK       (~CTMU_NEG_CURR_TRIM_2)	//Mask Negative change from nominal current selection bits

 #define CTMU_CURR_RANGE_100_BASE_CURR 0b00000011 /*Current source Range is 100*Base current (55uA) */
 #define CTMU_CURR_RANGE_10_BASE_CURR  0b00000010 /*Current source Range is 10*Base current (5.5uA)*/
 #define CTMU_CURR_RANGE_BASE_CURR     0b00000001 /*Current source Range is Base current (0.55uA)*/
 #define CTUM_CURR_SOURCE_DISABLE      0b00000000 /*Current source disabled*/
 #define CTMU_CURR_RANGE_MASK          (~CTMU_CURR_RANGE_100_BASE_CURR)	//Mask CTMU current source range selection bit

 #define CTMU_INT_ON                   0b01000000 /* Enable CTMU interrupts */
 #define CTMU_INT_OFF                  0b00000000 /* Disable CTMU interrupts */
 #define CTMU_INT_MASK                 (~CTMU_INT_ON)	//Mask CTMU Interrupt bit


/************************************************************************
Macro       : Enbl_CTMUEdge1
Overview    : By setting edge1 status bit current source enables
Parameters  : None
Remarks     : None.
**************************************************************************/
#define Enbl_CTMUEdge1  				(CTMUCONLbits.EDG1STAT = 1)

/************************************************************************
Macro       : Enbl_CTMUEdge2
Overview    : By setting edge2 status bit current source enables
Parameters  : None
Remarks     : None.
**************************************************************************/
#define Enbl_CTMUEdge2 				(CTMUCONLbits.EDG2STAT = 1)

/************************************************************************
Macro        :Disbl_CTMUEdge1
Overview    : By clearing edge1 status bit disable the current source
Parameters  : None
Remarks     : None.
**************************************************************************/
#define Disbl_CTMUEdge1 				(CTMUCONLbits.EDG1STAT = 0)

/************************************************************************
Macro       :Disbl_CTMUEdge2
Overview    : By clearing edge2 status bit disable the current source
Parameters  : None
Remarks     : None.
**************************************************************************/
#define Disbl_CTMUEdge2				(CTMUCONLbits.EDG2STAT = 0)


/************************************************************************
Macro       : CTMUEdge1_Status
Overview    : Returns the status of CTMU edge1
Parameters  : None
Remarks     : None.
**************************************************************************/
#define CTMUEdge1_Status      		CTMUCONLbits.EDG1STAT

/************************************************************************
Macro       : CTMUEdge2_Status
Overview    : Returns the status of CTMU edge2
Parameters  : None
Remarks     : None.
**************************************************************************/
#define CTMUEdge2_Status  	 		CTMUCONLbits.EDG2STAT

/***********************************************************************
Macro      : EnableIntCTMU()
Overview   : This macro enables the CTMU interrupt.
Parameters : None
Remarks    : This macro sets CTMU Interrupt Enable bit of Interrupt
                  Enable Control Register.
************************************************************************/
#define CTMU_INT_ENABLE()                {PIR3bits.CTMUIF = 0; INTCONbits.PEIE = 1; PIE3bits.CTMUIE = 1; }

/***********************************************************************
Macro      : DisableIntCTMU()
Overview   : This macro disables the CTMU interrupt.
Parameters : None
Remarks    : This macro clears CTMU Interrupt Enable bit of Interrupt
             Enable Control register.
************************************************************************/
#define CTMU_INT_DISABLE()               (PIE3bits.CTMUIE = 0)

/*******************************************************************
Macro       : CTMU_Clear_Intr_Status_Bit
Overview    : Macro to Clear CTMU Interrupt Status bit
Parameters  : None
Remarks     : None
*******************************************************************/
#define CTMU_Clear_Intr_Status_Bit    (PIR3bits.CTMUIF = 0)

/*******************************************************************
Macro       : CTMU_Intr_Status
Overview    : Macro to return CTMU Interrupt Status
Parameters  : None
Remarks     : None
*******************************************************************/
#define CTMU_Intr_Status    (PIR3bits.CTMUIF)

void OpenCTMU(unsigned char config1,unsigned char config2,unsigned char config3);
void CurrentControlCTMU(unsigned char config);
void CloseCTMU(void);

#endif

#endif
