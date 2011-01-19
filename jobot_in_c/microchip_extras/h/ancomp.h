#ifndef __ANCOMP_H
#define __ANCOMP_H
/******************************************************************************
 // *                  ANALOG COMPARATOR PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		ancomp.h
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

/********************************************************************/
#if defined ( ANCOM_V1) || defined (ANCOM_V2) || defined (ANCOM_V3) || defined (ANCOM_V4)

void Close_ancomp( void );
void Open_ancomp(unsigned char config);

#ifndef USE_OR_MASKS
//******************* Comparator Output Inversion Selection ************************************
#define COMP_1_2_OP_INV			0b11111111		//comparator 1,2  with OP invert
#define COMP_1_OP_INV			0b11011111		//comparator 1  with OP invert
#define COMP_2_OP_INV			0b11101111		//comparator 2  with OP invert
#define COMP_OP_INV_NONE 		0b11001111		//comparator 1,2  without OP invert

//******************* Comparator Mode Selection **********************************************
#define COMP_1_2_INDP			0b11111010		//two independent comparator
#define	COMP_1_2_INDP_OP		0b11111011		//two independent comparator with OPS
#define COMP_1_2_COMN_REF		0b11111100		//two common ref comparator
#define	COMP_1_2_COMN_REF_OP	0b11111101		//two common ref comparator with OPS
#define COMP_1_INDP_OP			0b11111001		//one independent comparator with OP
#define	COMP_INT_REF_SAME_IP	0b11110110		//comparators with int ref and default IPS
#define	COMP_INT_REF_MUX_IP		0b11111110		//comparators with int ref and cross IPS

//*************** Comparator Interrupts ******************************************************
#define COMP_INT_EN				0b11111111		//comparator interrupt enable
#define COMP_INT_DIS			0b01111111		//comparator interrupt disable

#else

//******************* Comparator Output Inversion Selection ************************************
#define COMP_1_2_OP_INV			0b00110000		//comparator 1,2  with OP invert
#define COMP_1_OP_INV			0b00010000		//comparator 1  with OP invert
#define COMP_2_OP_INV			0b00100000		//comparator 2  with OP invert
#define COMP_OP_INV_NONE 		0b00000000		//comparator 1,2  without OP invert
#define COMP_OP_MASK			(~COMP_1_2_OP_INV)	//Mask Comparator Output Inversion bits

//******************* Comparator Mode Selection **********************************************
#define COMP_1_2_INDP			0b00000010		//two independent comparator
#define	COMP_1_2_INDP_OP		0b00000011		//two independent comparator with OPS
#define COMP_1_2_COMN_REF		0b00000100		//two common ref comparator
#define	COMP_1_2_COMN_REF_OP	0b00000101		//two common ref comparator with OPS
#define COMP_1_INDP_OP			0b00000001		//one independent comparator with OP
#define	COMP_INT_REF_SAME_IP	0b00000110		//comparators with int ref and default IPS
#define	COMP_INT_REF_MUX_IP		0b00001110		//comparators with int ref and cross IPS
#define COMP_SELECT_MASK		(0b11110000)	//Mask Comparator Mode Selection bits

//*************** Comparator Interrupts ******************************************************
#define COMP_INT_EN				0b10000000		//comparator interrupt enable
#define COMP_INT_DIS			0b00000000		//comparator interrupt disable
#define COMP_INT_MASK			(~COMP_INT_EN)	//Mask Comparator Interrupt bits

#endif


#elif defined (ANCOM_V5) || defined (ANCOM_V8)

void Close_ancomp1( void );
void Open_ancomp1(unsigned char config);
void Close_ancomp2( void );
void Open_ancomp2(unsigned char config);

#ifndef USE_OR_MASKS
#if defined (ANCOM_V5)
//************* Comparator Ref (CxVREF )select ****************
#define COMP_REF_FVR		0b11111111			//CxVREF input connects to FVR (fixed 1.2V)
#define	COMP_REF_CVREF		0b10111111			//CxVREF input connects to CVREF
#elif defined (ANCOM_V8)
//************* Comparator Ref (CxVREF )select ****************
#define COMP_REF_FVR		0b11111111			//CxVREF input connects to FVR (fixed 1.2V)
#define	COMP_REF_DAC		0b10111111			//CxVREF input connects to DAC
#endif
//*************Comparator Enable/Disable ************************
#define COMP_INT_EN			0b11111111			//Enable Comparator
#define	COMP_INT_DIS		0b01111111			//Disable comparator

//************* Comparator Output Enable/Disable **************
#define COMP_OP_EN			0b11111111			//Comparator Output Enabled on CxOUT
#define COMP_OP_DIS			0b11011111			//Comparator Output Disabled on CxOUT

//******************* Comparator Output Inversion Selection *******
#define	COMP_OP_INV			0b11111111			//comparator  with OP invert
#define	COMP_OP_NINV		0b11101111			//comparator  with OP Non invert

//************** Comparator Speed/Power Selection *************
#define	COMP_HSPEED			0b11111111			//Comparator operates in normal power and  higher speed mode
#define	COMP_LSPEED			0b11110111			//Comparator operates in low power and  low speed mode
       
//************* Comparator Reference Selection ****************
#if defined (ANCOM_V5)
#define COMP_VINP_PIN		0b11111011			//CxVIN+ connects to CxVREF output
#define COMP_VINP_VREF		0b11111111			//CxVIN+ connects to CxIN+ pin
#elif defined (ANCOM_V8)
#define COMP_C1VP_VREF      0b11111111			//CxVP connects to CxVREF output
#define COMP_C1VP_PIN       0b11111011			//CxVIN+ connects to CxIN+ pin
#endif

#if defined (ANCOM_V5)
#define COMP_VINM_IN0		0b11111100			//CxyIN0 neg pin of Comparator connects to CxVIN negetive terminal
#elif defined (ANCOM_V8)
#define COMP_VINM_GND		0b11111100			//CxyIN0 neg pin of Comparator connects to GND
#endif
#define COMP_VINM_IN1		0b11111101			//CxyIN1 neg pin of Comparator connects to CxVIN negetive terminal
#define COMP_VINM_IN2		0b11111110			//CxyIN2 neg pin of Comparator connects to CxVIN negetive terminal
#define COMP_VINM_IN3		0b11111111			//CxyIN3 neg pin of Comparator connects to CxVIN negetive terminal
//---------------AND mask-----------------
#else

#if defined (ANCOM_V5)
//************* Comparator Ref (CxVREF )select ****************
#define COMP_REF_FVR		0b01000000			//CxVREF input connects to FVR (fixed 1.2V)
#define	COMP_REF_CVREF		0b00000000			//CxVREF input connects to CVREF
#define	COMP_REF_MASK		(~COMP_REF_FVR)		//Mask Comparator Ref (CxVREF )selection bits
#elif defined (ANCOM_V8)
//************* Comparator Ref (CxVREF )select ****************
#define COMP_REF_FVR		0b01000000			//CxVREF input connects to FVR (fixed 1.2V)
#define	COMP_REF_DAC		0b00000000			//CxVREF input connects to DAC
#define COMP_REF_MASK   	(~COMP_REF_FVR)		//Mask Comparator Ref (CxVREF )selection bits
#endif


//*************Comparator Enable/Disable ************************
#define COMP_INT_EN			0b10000000			//Enable Comparator
#define	COMP_INT_DIS		0b00000000			//Disable Comparator
#define COMP_INT_MASK		(~COMP_INT_EN)		//Mask Enable/Disable Comparator bit

//************* Comparator Output Enable/Disable **************
#define COMP_OP_EN			0b00100000			//Comparator Output Enabled on CxOUT
#define COMP_OP_DIS			0b00000000			//Comparator Output Disabled on CxOUT
#define COMP_OP_EN_MASK		(~COMP_OP_EN)		//Mask Comparator Output Enable/Disable
#define COMP_OP_MASK		COMP_OP_EN_MASK

//******************* Comparator Output Inversion Selection *******
#define	COMP_OP_INV			0b00010000			//comparator  with OP invert
#define	COMP_OP_NINV		0b00000000			//comparator  with OP non invert
#define	COMP_OP_INV_MASK	(~COMP_OP_INV)		//Mask Comparator Output Inversion Selection bit

//************** Comparator Speed/Power Selection *************
#define	COMP_HSPEED			0b00001000			//Comparator operates in normal power and  higher speed mode
#define	COMP_LSPEED			0b00000000			//Comparator operates in low power and  low speed mode
#define	COMP_HSPEED_MASK	(~COMP_HSPEED)		//Mask Comparator Speed/Power Selection bit

//************* Comparator Reference Selection ****************
#if defined (ANCOM_V5)
#define COMP_VINP_PIN		0b00000000			//CxVIN+ connects to CxVREF output
#define COMP_VINP_VREF		0b00000100			//CxVIN+ connects to CxIN+ pin
#define COMP_VINP_MASK		(~COMP_VINP_VREF)	//Mask Comparator positive terminal input selection bit
#elif defined (ANCOM_V8)
#define COMP_C1VP_VREF      0b00000100			//CxVIN+ connects to CxVREF output
#define COMP_C1VP_PIN       0b00000000			//CxVIN+ connects to CxIN+ pin
#define COMP_C1VP_MASK 		(~COMP_C1VP_VREF)	//Mask Comparator positive terminal input selection bit
#endif

#if defined (ANCOM_V5)
#define COMP_VINM_IN0		0b00000000			//CxyIN0 neg pin of Comparator connects to CxVIN negetive terminal
#elif defined (ANCOM_V8)
#define COMP_VINM_GND		0b00000000			//CxyIN0 neg pin of Comparator connects to GND
#endif
#define COMP_VINM_IN1		0b00000001			//CxyIN1 neg pin of Comparator connects to CxVIN negetive terminal
#define COMP_VINM_IN2		0b00000010			//CxyIN2 neg pin of Comparator connects to CxVIN negetive terminal
#define COMP_VINM_IN3		0b00000011			//CxyIN3 neg pin of Comparator connects to CxVIN negetive terminal
#define COMP_VINM_MASK		(~COMP_VINM_IN3)	//Mask Comparator negetive terminal input selection bit



#endif 

#elif  defined (ANCOM_V6) || defined (ANCOM_V7) || defined (ANCOM_V9) || defined (ANCOM_V10) || defined (ANCOM_V10_1) ||\
		defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
void Close_ancomp1( void );
void Open_ancomp1(unsigned char config);
void Close_ancomp2( void );
void Open_ancomp2(unsigned char config);

#if defined (ANCOM_V10) || defined (ANCOM_V10_1) || defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
void Close_ancomp3( void );
void Open_ancomp3(unsigned char config);

/**************************************************************************
Macro   :  Config_CVREF(config)

Overview   : This macro configures CVRCON register

Parameters   : config - This contains the parameters to be configured in the CVRCON   
                         Register as defined below
						 
			Comparator Voltage Reference Enable bit
			*      CMP_CVREF_ENABLE
			*      CMP_CVREF_DISABLE
			*      CMP_CVREF_MASK

			Comparator VREF Output Enable bit	
			*      CMP_VREF_OUTPUT_ENABLE
			*      CMP_VREF_OUTPUT_DISABLE
			*      CMP_VREF_OUTPUT_MASK

			Comparator VREF Source Selection bit
			*      CMP_VRSRC_VREF_VREF
			*      CMP_VRSRC_AVDD_AVSS
			*      CMP_VRSRC_MASK

			Comparator VREF Value Selection
			*      CMP_CVREF_OP_SEL_0
			*      CMP_CVREF_OP_SEL_1
			*      CMP_CVREF_OP_SEL_2
			*      CMP_CVREF_OP_SEL_3
			*      CMP_CVREF_OP_SEL_4
			*      CMP_CVREF_OP_SEL_5
			*      CMP_CVREF_OP_SEL_6
			*      CMP_CVREF_OP_SEL_7
			*      CMP_CVREF_OP_SEL_8
			*      CMP_CVREF_OP_SEL_9
			*      CMP_CVREF_OP_SEL_10
			*      CMP_CVREF_OP_SEL_11
			*      CMP_CVREF_OP_SEL_12
			*      CMP_CVREF_OP_SEL_13
			*      CMP_CVREF_OP_SEL_14
			*      CMP_CVREF_OP_SEL_15
			*      CMP_CVREF_OP_SEL_16
			*      CMP_CVREF_OP_SEL_17
			*      CMP_CVREF_OP_SEL_18
			*      CMP_CVREF_OP_SEL_19
			*      CMP_CVREF_OP_SEL_20
			*      CMP_CVREF_OP_SEL_21
			*      CMP_CVREF_OP_SEL_22
			*      CMP_CVREF_OP_SEL_23
			*      CMP_CVREF_OP_SEL_24
			*      CMP_CVREF_OP_SEL_25
			*      CMP_CVREF_OP_SEL_26
			*      CMP_CVREF_OP_SEL_27
			*      CMP_CVREF_OP_SEL_28
			*      CMP_CVREF_OP_SEL_29
			*      CMP_CVREF_OP_SEL_30
			*      CMP_CVREF_OP_SEL_31
			*      CMP_CVREF_OP_SEL_MASK
Returns      : None					 

Remarks      : None                                             
**************************************************************************/
#define Config_CVREF(config)     (CVRCON = config)

#endif

#ifndef USE_OR_MASKS
//*************Comparator Enable/Disable ************************
#define COMP_INT_EN			0b11111111			//Enable Comparator
#define	COMP_INT_DIS		0b01111111			//Disable Comparator

//************* Comparator Output Enable/Disable **************
#define COMP_OP_EN			0b11111111			//Comparator Output Enabled on CxOUT
#define COMP_OP_DIS			0b10111111			//Comparator Output Disabled on CxOUT

//******************* Comparator Output Inversion Selection *******
#define	COMP_OP_INV			0b11111111			//comparator  with OP invert
#define	COMP_OP_NINV		0b11011111			//comparator  with OP non invert

//****************** Comparator  Interrupt generation settings ******
#define	COMP_INT_ALL_EDGE	0b11111111			//Interrupt generation on any change of the output
#define	COMP_INT_FALL_EDGE	0b11110111			//Interrupt generation only on high-to-low transition of the output
#define	COMP_INT_RISE_EDGE	0b11101111			//Interrupt generation only on low-to-high transition of the output
#define	COMP_INT_NOGEN		0b11100111			//Interrupt generation is disabled

//************* Comparator Reference Selection ****************
#define COMP_REF_CVREF		0b11111111			//Non-inverting input connects to internal CVREF voltage
#define COMP_REF_CINA		0b11111011			//Non-inverting input connects to CxINA pin

#if  defined (ANCOM_V6) || defined (ANCOM_V7) 
#define COMP_VINM_VIRV		0b11111111			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CIND		0b11111110			//Inverting input of comparator connects to CxIND pin
#define COMP_VINM_CINC		0b11111101			//Inverting input of comparator connects to CxINC pin
#define COMP_VINM_CINB		0b11111100			//Inverting input of comparator connects to CxIN pin
#elif defined (ANCOM_V9)
#define COMP_VINM_VBG		0b11111111			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CM1_C2INB	0b11111110			//Inverting input of comparator1 connects to C1INB and that of comparator2 connected to GND
#define COMP_VINM_CM2_GND	0b11111110			//Inverting input of comparator2 connects to GND
#define COMP_VINM_GND		0b11111101			//Inverting input of comparator connects to GND
#define COMP_VINM_CINB		0b11111100			//Inverting input of comparator connects to CxINB pin
#elif defined (ANCOM_V10) || defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
#define COMP_VINM_VBG				0b11111111			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CM1_CM3_C2INB		0b11111110			//Inverting input of comparator1 & Comparaor3 connects to C2INB
#define COMP_VINM_CM2_C2IND			0b11111110			//Inverting input of comparator2 connects to C2IND
#define COMP_VINM_CINC				0b11111101			//Inverting input of comparator connects to CxINC
#define COMP_VINM_CINB				0b11111100			//Inverting input of comparator connects to CxINB pin
#elif defined (ANCOM_V10_1)
#define COMP_VINM_VIRV				0b11111111			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CIND				0b11111110			//Inverting input of comparator connects to C1IND
#define COMP_VINM_CINC				0b11111101			//Inverting input of comparator connects to C1INC
#define COMP_VINM_CINB				0b11111100			//Inverting input of comparator connects to CxINB pin
#endif

/*CVRCON register definition*/
#if defined (ANCOM_V10) || defined (ANCOM_V10_1) || defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
#define CMP_CVREF_ENABLE                 0xFF /* CVREF circuit powered on */
#define CMP_CVREF_DISABLE                0xEF /* CVREF circuit powered down */

#define CMP_VREF_OUTPUT_ENABLE           0xFF /* CVREF voltage level is output on CVREF pin */
#define CMP_VREF_OUTPUT_DISABLE          0xBF /* CVREF voltage level is disconnected from CVREF pin */

#define CMP_VRSRC_VREF_VREF        		 0xFF /* Comparator reference source CVRSRC = VREF+ – VREF- */
#define CMP_VRSRC_AVDD_AVSS        		 0xDF /* Comparator reference source CVRSRC = AVDD – AVSS */

#define CMP_CVREF_OP_SEL_0			     0xE0 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_1			     0xE1 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_2			     0xE2 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_3			     0xE3 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_4			     0xE4 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_5			     0xE5 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_6			     0xE6 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_7			     0xE7 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_8			     0xE8 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_9			     0xE9 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_10			     0xEA /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_11			     0xEB /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_12			     0xEC /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_13			     0xED /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_14			     0xEE /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_15			     0xEF /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_16			     0xF0 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_17			     0xF1 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_18			     0xF2 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_19			     0xF3 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_20			     0xF4 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_21			     0xF5 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_22			     0xF6 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_23			     0xF7 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_24			     0xF8 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_25			     0xF9 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_26			     0xFA /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_27			     0xFB /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_28			     0xFC /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_29			     0xFD /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_30			     0xFE /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_31			     0xFF /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#endif

#else

//*************Comparator Enable/Disable ************************
#define COMP_INT_EN			0b10000000			//Enable Comparator
#define	COMP_INT_DIS		0b00000000			//Disable Comparator
#define COMP_INT_MASK		(~COMP_INT_EN)		//Mask Enable/Disable Comparator bit

//************* Comparator Output Enable/Disable **************
#define COMP_OP_EN			0b01000000			//Comparator Output Enabled on CxOUT
#define COMP_OP_DIS			0b00000000			//Comparator Output Disabled on CxOUT
#define COMP_OP_MASK		(~COMP_OP_EN)		//Mask Comparator Output Enable/Disable

//******************* Comparator Output Inversion Selection *******
#define	COMP_OP_INV			0b00100000			//comparator  with OP invert
#define	COMP_OP_NINV		0b00000000			//comparator  with OP non invert
#define	COMP_OP_INV_MASK	(~COMP_OP_INV)		//Mask Comparator Output Inversion Selection bit

//****************** Comparator  Interrupt generation settings ******	
#define	COMP_INT_ALL_EDGE	0b00011000			//Interrupt generation on any change of the output
#define	COMP_INT_FALL_EDGE	0b00010000			//Interrupt generation only on high-to-low transition of the output
#define	COMP_INT_RISE_EDGE	0b00001000			//Interrupt generation only on low-to-high transition of the output
#define	COMP_INT_NOGEN		0b00000000			//Interrupt generation is disabled
#define	COMP_INT_EDGE_MASK	(~COMP_INT_ALL_EDGE)	//Mask Comparator  Interrupt generation selection bits	

//************* Comparator Reference Selection ****************
#define COMP_REF_CVREF		0b00000100			//Non-inverting input connects to internal CVREF voltage
#define COMP_REF_CINA		0b00000000			//Non-inverting input connects to CxINA pin
#define COMP_REF_MASK		(~COMP_REF_CVREF)	//Mask Comparator Ref selection bits

#if  defined (ANCOM_V6) || defined (ANCOM_V7) 
#define COMP_VINM_VIRV		0b00000011			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CIND		0b00000010			//Inverting input of comparator connects to CxIND pin
#define COMP_VINM_CINC		0b00000001			//Inverting input of comparator connects to CxINC pin
#define COMP_VINM_CINB		0b00000000			//Inverting input of comparator connects to CxIN pin
#define COMP_VINM_MASK		(~COMP_VINM_VIRV)	//Mask Comparator negetive input Ref selection bits
#elif defined (ANCOM_V9)
#define COMP_VINM_VBG		0b00000011			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CM1_C2INB	0b00000010			//Inverting input of comparator1 connects to C1INB and that of comparator2 connected to GND
#define COMP_VINM_CM2_GND	0b00000010			//Inverting input of comparator2 connects to GND
#define COMP_VINM_GND		0b00000001			//Inverting input of comparator connects to GND
#define COMP_VINM_CINB		0b00000000			//Inverting input of comparator connects to CxINB pin
#define COMP_VINM_MASK		(~COMP_VINM_VBG)	//Mask Comparator negetive input Ref selection bits
#elif defined (ANCOM_V10) || defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
#define COMP_VINM_VBG				0b00000011			//Inverting input of comparator connects to VIRV
#define COMP_VINM_CM1_CM3_C2INB		0b00000010			//Inverting input of comparator1 & Comparator2 connects to C2INB
#define COMP_VINM_CM2_C2IND			0b00000010			//Inverting input of comparator2 connects to C2IND
#define COMP_VINM_CINC				0b00000001			//Inverting input of comparator connects to CxINC pin
#define COMP_VINM_CINB				0b00000000			//Inverting input of comparator connects to CxINB pin
#define COMP_VINM_MASK				(~COMP_VINM_VBG)	//Mask Comparator negetive input Ref selection bits
#elif defined (ANCOM_V10_1) 
#define COMP_VINM_VIRV				0b00000011			//Inverting input of comparator connects to VIRV
#define COMP_VINM_C1IND				0b00000010			//Inverting input of comparator2 connects to C1IND
#define COMP_VINM_C1INC				0b00000001			//Inverting input of comparator connects to C1INC pin
#define COMP_VINM_CINB				0b00000000			//Inverting input of comparator connects to CxINB pin
#define COMP_VINM_MASK				(~COMP_VINM_VBG)	//Mask Comparator negetive input Ref selection bits
#endif

/*CVRCON register definition*/
#if defined (ANCOM_V10) || defined (ANCOM_V10_1) || defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
#define CMP_CVREF_ENABLE                  0x80 /* CVREF circuit powered on */
#define CMP_CVREF_DISABLE                 0x00 /* CVREF circuit powered down */
#define CMP_CVREF_MASK                   (~CMP_CVREF_ENABLE)	//Mask CVREF enable bit

#define CMP_VREF_OUTPUT_ENABLE           0x40 /* CVREF voltage level is output on CVREF pin */
#define CMP_VREF_OUTPUT_DISABLE          0x00 /* CVREF voltage level is disconnected from CVREF pin */
#define CMP_VREF_OUTPUT_MASK             (~CMP_VREF_OUTPUT_ENABLE)	//Mask  CVREF voltage level on output bit

#define CMP_VRSRC_VREF_VREF        		 0x20 /* Comparator reference source CVRSRC = VREF+ – VREF- */
#define CMP_VRSRC_AVDD_AVSS        		 0x00 /* Comparator reference source CVRSRC = AVDD – AVSS */
#define CMP_VRSRC_MASK                   (~CMP_VRSRC_VREF_VREF)	//Mask reference source selection bit

#define CMP_CVREF_OP_SEL_0			     0x00 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_1			     0x01 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_2			     0x02 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_3			     0x03 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_4			     0x04 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_5			     0x05 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_6			     0x06 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_7			     0x07 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_8			     0x08 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_9			     0x09 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_10			     0x0A /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_11			     0x0B /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_12			     0x0C /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_13			     0x0D /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_14			     0x0E /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_15			     0x0F /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_16			     0x10 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_17			     0x11 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_18			     0x12 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_19			     0x13 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_20			     0x14 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_21			     0x15 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_22			     0x16 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_23			     0x17 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_24			     0x18 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_25			     0x19 /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_26			     0x1A /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_27			     0x1B /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_28			     0x1C /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_29			     0x1D /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_30			     0x1E /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_31			     0x1F /* Selects CVREF voltage = CVREF = (VREF-) + (CVR[4:0]/ 32) • (VREF+ - VREF-) OR CVREF = (AVSS) + (CVR[4:0]/ 32) • (AVDD - AVSS) */
#define CMP_CVREF_OP_SEL_MASK             (~CMP_CVREF_OP_SEL_31) //Mask CVREF level sel bits
#endif

#endif


#endif


#if defined (ANCOM_V10_1)

#define C1INA_PIN	TRISAbits.TRISA0
#define C1INB_PIN	TRISAbits.TRISA3
#define C1INC_PIN	TRISAbits.TRISA5
#define C1IND_PIN	TRISAbits.TRISA2

#define C2INA_PIN	TRISAbits.TRISA1
#define C2INB_PIN	TRISAbits.TRISA2
#define C2INC_PIN	TRISBbits.TRISB2
#define C2IND_PIN	TRISCbits.TRISC2

#define C3INA_PIN	TRISBbits.TRISB3	
#define C3INB_PIN	TRISAbits.TRISA2
#define C3INC_PIN	TRISBbits.TRISB1
#define C3IND_PIN	TRISBbits.TRISB0

#define C1OUT_PIN
#define C2OUT_PIN
#define C3OUT_PIN

#elif defined (ANCOM_V10_2)

#define C1INA_PIN	TRISDbits.TRISD0
#define C1INB_PIN	TRISDbits.TRISD1
#define C1INC_PIN	TRISAbits.TRISA1


#define C2INA_PIN	TRISDbits.TRISD2
#define C2INB_PIN	TRISDbits.TRISD3
#define C2INC_PIN	TRISAbits.TRISA2

#define C1OUT_PIN	TRISEbits.TRISE1
#define C2OUT_PIN	TRISEbits.TRISE2

#elif defined (ANCOM_V10_3) 

#define C1INA_PIN	TRISBbits.TRISB0
#define C1INB_PIN	TRISBbits.TRISB1
#define C1INC_PIN	TRISAbits.TRISA5


#define C2INA_PIN	TRISBbits.TRISB4
#define C2INB_PIN	TRISAbits.TRISA5
#define C2INC_PIN	TRISAbits.TRISA2

#define C1OUT_PIN	TRISBbits.TRISB2
#define C2OUT_PIN	TRISBbits.TRISB3

#endif
//---------------------------------------

#if defined (ANCOM_V10) 
#define CMP1_IF		PIR6bits.CMP1IF
#define CMP1_IE		PIE6bits.CMP1IE
#define CMP1_IP		IPR6bits.CMP1IP

#define CMP2_IF		PIR6bits.CMP2IF
#define CMP2_IE		PIE6bits.CMP2IE
#define CMP2_IP		IPR6bits.CMP2IP

#define CMP3_IF		PIR6bits.CMP3IF
#define CMP3_IE		PIE6bits.CMP3IE
#define CMP3_IP		IPR6bits.CMP3IP
#elif defined (ANCOM_V10_2) || defined (ANCOM_V10_3)
#define CMP1_IF		PIR4bits.CMP1IF
#define CMP1_IE		PIE4bits.CMP1IE
#define CMP1_IP		IPR4bits.CMP1IP

#define CMP2_IF		PIR4bits.CMP2IF
#define CMP2_IE		PIE4bits.CMP2IE
#define CMP2_IP		IPR4bits.CMP2IP
#endif

//TODO fix the following in P18FxxJxx.h
#if defined (ANCOM_V6) || defined (ANCOM_V7)

#define CM1CON	CM1CON1
#define CM2CON	CM2CON1


#define CM1CONbits	CM1CON1bits
#define CM2CONbits	CM2CON1bits
#endif

#if defined (ANCOM_V6)
#define TRISFbits DDRFbits

#define TRISF1  RF1
#define TRISF2  RF2
#define TRISF3  RF3
#define TRISF4  RF4
#define TRISF5  RF5
#define TRISF6  RF6
#define TRISF7  RF7
// / fix in p18FxxJxx.h
#endif
//---------------------------------------
#endif
