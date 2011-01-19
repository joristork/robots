#ifndef __ADC_H
#define __ADC_H
/******************************************************************************
 *                  ADC PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		adc.h
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


/***********************************************************************************
Macro       : ADC_INT_ENABLE()
Overview : Macro enables the ADC interrupt
Parameters   : None
Remarks     : Interrupt is configured as low priority interrupt
***********************************************************************************/
#define ADC_INT_ENABLE()     (PIR1bits.ADIF=0,INTCONbits.PEIE=1,PIE1bits.ADIE=1) 

/***********************************************************************************
Macro       : ADC_INT_DISABLE()
Overview : Macro disables the ADC interrupt
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_INT_DISABLE()    (PIE1bits.ADIE=0)


#if	defined (ADC_V1) || defined (ADC_V2) || defined (ADC_V3) || defined (ADC_V4) ||\
    defined (ADC_V5) || defined (ADC_V6) || defined (ADC_V8) || defined (ADC_V9) ||\
	defined (ADC_V10) || defined (ADC_V11) || defined (ADC_V11_1) || defined (ADC_V12)\
	|| defined (ADC_V13) || defined (ADC_V13_1) || defined (ADC_V13_2) ||\
	defined (ADC_V13_3) || defined (ADC_V14) || defined (ADC_V14_1)

#ifndef USE_OR_MASKS
//*************** A/D Conversion Clock Selection *****************************
#define ADC_FOSC_2       0b10001111 			//A/D conversion clock source is Fosc/2
#define ADC_FOSC_4       0b11001111 			//A/D conversion clock source is Fosc/4
#define ADC_FOSC_8       0b10011111 			//A/D conversion clock source is Fosc/8
#define ADC_FOSC_16      0b11011111 			//A/D conversion clock source is Fosc/16
#define ADC_FOSC_32      0b10101111 			//A/D conversion clock source is Fosc/32
#define ADC_FOSC_64      0b11101111 			//A/D conversion clock source is Fosc/64
#define ADC_FOSC_RC      0b11111111 			//A/D conversion clock source is Internal RC OSC

//************** A/D Acquisition Time Selection *******************************
#define ADC_0_TAD        0b11110001				//A/D Acquisition Time is 0 TAD
#define ADC_2_TAD        0b11110011				//A/D Acquisition Time is 2 TAD
#define ADC_4_TAD        0b11110101				//A/D Acquisition Time is 4 TAD
#define ADC_6_TAD        0b11110111				//A/D Acquisition Time is 6 TAD
#define ADC_8_TAD        0b11111001				//A/D Acquisition Time is 8 TAD
#define ADC_12_TAD       0b11111011				//A/D Acquisition Time is 12 TAD
#define ADC_16_TAD       0b11111101				//A/D Acquisition Time is 16 TAD
#define ADC_20_TAD       0b11111111				//A/D Acquisition Time is 20 TAD

//*************** ADC Interrupt Enable/Disable *******************************
#define ADC_INT_ON       0b11111111				//A/D Interrupt Enable
#define ADC_INT_OFF      0b01111111				//A/D Interrupt Disable

#else // USE_OR_MASKS
//*************** A/D Conversion Clock Select *****************************
#define ADC_FOSC_2       0b00000000 			//A/D conversion clock source is Fosc/2
#define ADC_FOSC_4       0b01000000 			//A/D conversion clock source is Fosc/4
#define ADC_FOSC_8       0b00010000 			//A/D conversion clock source is Fosc/8
#define ADC_FOSC_16      0b01010000 			//A/D conversion clock source is Fosc/16
#define ADC_FOSC_32      0b00100000 			//A/D conversion clock source is Fosc/32
#define ADC_FOSC_64      0b01100000 			//A/D conversion clock source is Fosc/64
#define ADC_FOSC_RC      0b01110000 			//A/D conversion clock source is Internal RC OSC
#define ADC_FOSC_MASK	 (~ADC_FOSC_RC)			//Mask A/D conversion clock source bits

//************** A/D Acquisition Time Selection *******************************
#define ADC_0_TAD        0b00000000				//A/D Acquisition Time is 0 TAD
#define ADC_2_TAD        0b00000010				//A/D Acquisition Time is 2 TAD
#define ADC_4_TAD        0b00000100				//A/D Acquisition Time is 4 TAD
#define ADC_6_TAD        0b00000110				//A/D Acquisition Time is 6 TAD
#define ADC_8_TAD        0b00001000				//A/D Acquisition Time is 8 TAD
#define ADC_12_TAD       0b00001010				//A/D Acquisition Time is 12 TAD
#define ADC_16_TAD       0b00001100				//A/D Acquisition Time is 16 TAD
#define ADC_20_TAD       0b00001110				//A/D Acquisition Time is 20 TAD
#define ADC_TAD_MASK	(~ADC_20_TAD)			//Mask A/D Acquisition Time bits

//*************** ADC Interrupt Enable/Disable *******************************
#define ADC_INT_ON       0b10000000				//A/D Interrupt Enable
#define ADC_INT_OFF      0b00000000				//A/D Interrupt Disable
#define ADC_INT_MASK     (~ADC_INT_ON)			//Mask A/D Interrupt
#endif	// USE_OR_MASKS

#endif	// End of versions


#if defined (ADC_V2) || defined (ADC_V3) || defined (ADC_V4) || defined (ADC_V5) ||\
    defined (ADC_V6) || defined (ADC_V8) || defined (ADC_V9) || defined (ADC_V11)|| defined (ADC_V11_1)||\
	defined (ADC_V12)

#ifndef USE_OR_MASKS
//************** Voltage Reference Configuration ****************************** 
#define ADC_REF_VDD_VREFMINUS   	0b11111110 	// ADC voltage source VREF+ = VDD and VREF- = ext.source at VREF-
#define ADC_REF_VREFPLUS_VREFMINUS  0b11111111 	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = ext.source at VREF-
#define ADC_REF_VREFPLUS_VSS		0b11111101 	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = VSS
#define	ADC_REF_VDD_VSS  			0b11111100 	// ADC voltage source VREF+ = VDD and VREF- = VSS
#else // USE_OR_MASKS	
//************** Voltage Reference Configuration ****************************** 
#define ADC_REF_VDD_VREFMINUS   	0b00000010 	// ADC voltage source VREF+ = VDD and VREF- = ext.source at VREF-
#define ADC_REF_VREFPLUS_VREFMINUS  0b00000011 	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = ext.source at VREF-
#define ADC_REF_VREFPLUS_VSS		0b00000001 	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = VSS
#define	ADC_REF_VDD_VSS  		    0b00000000 	// ADC voltage source VREF+ = VDD and VREF- = VSS
#define ADC_REF_MASK	            (~ADC_REF_VREFPLUS_VREFMINUS)	//Mask ADC Voltage Source
#endif	// USE_OR_MASKS

#endif	// End of versions


#ifndef	USE_OR_MASKS
//*************** A/D Result Format Select ***********************************
#define ADC_RIGHT_JUST   0b11111111 			// Right justify A/D result
#define ADC_LEFT_JUST    0b01111111 			// Left justify A/D result
#else // USE_OR_MASKS
//*************** A/D Result Format Select ***********************************
#define ADC_RIGHT_JUST   0b10000000 			// Right justify A/D result
#define ADC_LEFT_JUST    0b00000000 			// Left justify A/D result
#define ADC_RESULT_MASK	 (~ADC_RIGHT_JUST)		//Mask ADC Result adjust bit
#endif // USE_OR_MASKS


#if defined (ADC_V1)

#ifndef	USE_OR_MASKS
//***************A/D Port Configuration Control *******************************
#define ADC_8ANA_0REF    0b11110000				// VREF+=VDD,VREF-=VSS: all analog channels   (8 analog channels/0 Voltage Reference)
#define ADC_7ANA_1REF    0b11110001				// AN3=VREF+:  all analog channels except AN3 (7 analog channels/1 Voltage Reference)
#define ADC_5ANA_0REF    0b11110010 			// VREF+=VDD,VREF-=VSS: DIG-AN7,6,5 : ANG-AN4,3,2,1,0  (5 analog channels/0 Voltage Reference)
#define ADC_4ANA_1REF    0b11110011 			// AN3=VREF+:  DIG- AN7,6,5 : ANG- AN4,2,1,0  (4 analog channels/1 Voltage Reference)
#define ADC_3ANA_0REF    0b11110100 			// VREF+=VDD,VREF-=VSS: DIG- AN7,6,5,4,2 : ANG- AN3,1,0   (3 analog channels/0 Voltage Reference)
#define ADC_2ANA_1REF    0b11110101 			// AN3=VREF+:  DIG- AN7,6,5,4,2 : ANG- AN1,0   (2 analog channels/1 Voltage Reference)
#define ADC_0ANA_0REF    0b11110111 			// ALL DIGITAL Channels  (0 analog channels/0 Voltage Reference)
#define ADC_6ANA_2REF    0b11111000 			// AN3=VREF+,AN2=VREF-:  ANG- AN7,6,5,4,1,0  (6 analog channels/2 Voltage Reference)
#define ADC_6ANA_0REF    0b11111001 			// VREF+=VDD,VREF-=VSS:  DIG- AN7,6 : ANG- AN5,4,3,2,1,0  (6 analog channels/0 Voltage Reference)
#define ADC_5ANA_1REF    0b11111010 			// AN3=VREF+,VREF-=VSS:  DIG- AN7,6 : ANG- AN5,4,2,1,0  (5 analog channels/1 Voltage Reference)
#define ADC_4ANA_2REF    0b11111011 			// AN3=VREF+ AN2=VREF-:   DIG- AN7,6 : ANG- AN5,4,1,0 (4 analog channels/2 Voltage Reference)
#define ADC_3ANA_2REF    0b11111100 			// AN3=VREF+ AN2=VREF-:  DIG- AN7,6,5 : ANG- AN4,1,0  (3 analog channels/2 Voltage Reference)
#define ADC_2ANA_2REF    0b11111101 			// AN3=VREF+ AN2=VREF-:   DIG- AN7,6,5,4 : ANG- AN1,0  (2 analog channels/2 Voltage Reference)
#define ADC_1ANA_0REF    0b11111110 			// AN0 is the only analog input  (1 analog channels/0 Voltage Reference)
#define ADC_1ANA_2REF    0b11111111 			// AN3=VREF+ AN2=VREF-:   DIG- AN7,6,5,4,1 : ANG- AN0   (1 analog channels/2 Voltage Reference)
#else // USE_OR_MASKS
//***************A/D Port Configuration Control *******************************
#define ADC_8ANA_0REF    0b00000000				// VREF+=VDD,VREF-=VSS: all analog channels   (8 analog channels/0 Voltage Reference)
#define ADC_7ANA_1REF    0b00000001				// AN3=VREF+:  all analog channels except AN3 (7 analog channels/1 Voltage Reference)
#define ADC_5ANA_0REF    0b00000010 			// VREF+=VDD,VREF-=VSS: DIG-AN7,6,5 : ANG-AN4,3,2,1,0  (5 analog channels/0 Voltage Reference)
#define ADC_4ANA_1REF    0b00000011 			// AN3=VREF+:  DIG- AN7,6,5 : ANG- AN4,2,1,0  (4 analog channels/1 Voltage Reference)
#define ADC_3ANA_0REF    0b00000100 			// VREF+=VDD,VREF-=VSS: DIG- AN7,6,5,4,2 : ANG- AN3,1,0   (3 analog channels/0 Voltage Reference)
#define ADC_2ANA_1REF    0b00000101 			// AN3=VREF+:  DIG- AN7,6,5,4,2 : ANG- AN1,0   (2 analog channels/1 Voltage Reference)
#define ADC_0ANA_0REF    0b00000111 			// ALL DIGITAL Channels  (0 analog channels/0 Voltage Reference)
#define ADC_6ANA_2REF    0b00001000 			// AN3=VREF+,AN2=VREF-:  ANG- AN7,6,5,4,1,0  (6 analog channels/2 Voltage Reference)
#define ADC_6ANA_0REF    0b00001001 			// VREF+=VDD,VREF-=VSS:  DIG- AN7,6 : ANG- AN5,4,3,2,1,0  (6 analog channels/0 Voltage Reference)
#define ADC_5ANA_1REF    0b00001010 			// AN3=VREF+,VREF-=VSS:  DIG- AN7,6 : ANG- AN5,4,2,1,0  (5 analog channels/1 Voltage Reference)
#define ADC_4ANA_2REF    0b00001011 			// AN3=VREF+ AN2=VREF-:   DIG- AN7,6 : ANG- AN5,4,1,0 (4 analog channels/2 Voltage Reference)
#define ADC_3ANA_2REF    0b00001100 			// AN3=VREF+ AN2=VREF-:  DIG- AN7,6,5 : ANG- AN4,1,0  (3 analog channels/2 Voltage Reference)
#define ADC_2ANA_2REF    0b00001101 			// AN3=VREF+ AN2=VREF-:   DIG- AN7,6,5,4 : ANG- AN1,0  (2 analog channels/2 Voltage Reference)
#define ADC_1ANA_0REF    0b00001110 			// AN0 is the only analog input  (1 analog channels/0 Voltage Reference)
#define ADC_1ANA_2REF    0b00001111 			// AN3=VREF+ AN2=VREF-:   DIG- AN7,6,5,4,1 : ANG- AN0   (1 analog channels/2 Voltage Reference)
#define ADC_CONFIG_MASK	 (~ADC_1ANA_2REF)		//Mask ADC port configuration bits
#endif // USE_OR_MASKS

#elif defined (ADC_V2) || defined (ADC_V5) || defined (ADC_V6) || defined (ADC_V12) 

#ifndef USE_OR_MASKS
//***************A/D Port Configuration Control *******************************
#define ADC_0ANA   0b11111111 					// All channels are digital
#define ADC_1ANA   0b11111110 					// analog: AN0 and remaining channels are digital
#define ADC_2ANA   0b11111101 					// analog: AN0->1 and remaining channels are digital
#define ADC_3ANA   0b11111100 					// analog: AN0->2 and remaining channels are digital
#define ADC_4ANA   0b11111011 					// analog: AN0->3 and remaining channels are digital
#define ADC_5ANA   0b11111010 					// analog: AN0->4 and remaining channels are digital
#define ADC_6ANA   0b11111001 					// analog: AN0->5 and remaining channels are digital
#define ADC_7ANA   0b11111000 					// analog: AN0->6 and remaining channels are digital
#define ADC_8ANA   0b11110111 					// analog: AN0->7 and remaining channels are digital
#define ADC_9ANA   0b11110110 					// analog: AN0->8 and remaining channels are digital
#define ADC_10ANA  0b11110101 					// analog: AN0->9 and remaining channels are digital
#define ADC_11ANA  0b11110100 					// analog: AN0->10 and remaining channels are digital
#ifndef ADC_V12
#define ADC_12ANA  0b11110011 					// analog: AN0->11 and remaining channels are digital
#define ADC_13ANA  0b11110010 					// analog: AN0->12 and remaining channels are digital
#define ADC_14ANA  0b11110001 					// analog: AN0->13 and remaining channels are digital
#define ADC_15ANA  0b11110000 					// All channels are analog
#endif
#else // USE_OR_MASKS
//***************A/D Port Configuration Control *******************************
#define ADC_0ANA   0b00001111 					// All channels are digital
#define ADC_1ANA   0b00001110 					// analog: AN0  and remaining channels are digital
#define ADC_2ANA   0b00001101 					// analog: AN0->1 and remaining channels are digital
#define ADC_3ANA   0b00001100 					// analog: AN0->2 and remaining channels are digital
#define ADC_4ANA   0b00001011 					// analog: AN0->3 and remaining channels are digital
#define ADC_5ANA   0b00001010 					// analog: AN0->4 and remaining channels are digital
#define ADC_6ANA   0b00001001 					// analog: AN0->5 and remaining channels are digital
#define ADC_7ANA   0b00001000 					// analog: AN0->6 and remaining channels are digital
#define ADC_8ANA   0b00000111 					// analog: AN0->7 and remaining channels are digital
#define ADC_9ANA   0b00000110 					// analog: AN0->8 and remaining channels are digital
#define ADC_10ANA  0b00000101 					// analog: AN0->9 and remaining channels are digital
#define ADC_11ANA  0b00000100 					// analog: AN0->10 and remaining channels are digital
#ifndef ADC_V12
#define ADC_12ANA  0b00000011 					// analog: AN0->11 and remaining channels are digital
#define ADC_13ANA  0b00000010 					// analog: AN0->12 and remaining channels are digital
#define ADC_14ANA  0b00000001 					// analog: AN0->13 and remaining channels are digital
#define ADC_15ANA  0b00000000 					// All channels are analog
#endif
#define ADC_CONFIG_MASK  (~ADC_0ANA)			//Mask ADC port configuration bits
#endif // USE_OR_MASKS

#elif defined (ADC_V3)

//***************A/D Port Configuration Control *******************************
#define ADC_0ANA   0b11111111 					// All channels are digital
#define ADC_1ANA   0b11111110	 				// analog: AN0  and remaining channels are digital
#define ADC_2ANA   0b11111100 					// analog: AN0->1 and remaining channels are digital
#define ADC_3ANA   0b11111000 					// analog: AN0->2 and remaining channels are digital
#define ADC_4ANA   0b11110000 					// analog: AN0->3  and remaining channels are digital
#define ADC_5ANA   0b11100000 					// analog: AN0->4  and remaining channels are digital
#define ADC_6ANA   0b11000000 					// analog: AN0->5  and remaining channels are digital
#define ADC_7ANA   0b10000000 					// analog: AN0->6  and remaining channels are digital

#elif defined (ADC_V4)

#ifndef USE_OR_MASKS
//***************A/D Port Configuration Control *******************************
#define ADC_0ANA   0b11111111 					// All channels are digital
#define ADC_1ANA   0b11111110	 				// analog: AN0  and remaining channels are digital
#define ADC_2ANA   0b11111100 					// analog: AN0->1 and remaining channels are digital
#define ADC_3ANA   0b11111000 					// analog: AN0->2 and remaining channels are digital
#define ADC_4ANA   0b11110000 					// analog: AN0->3  and remaining channels are digital
#else // USE_OR_MASKS
//***************A/D Port Configuration Control *******************************
#define ADC_0ANA   0b00001111 					// All channels are digital
#define ADC_1ANA   0b00001110	 				// analog: AN0  and remaining channels are digital
#define ADC_2ANA   0b00001100 					// analog: AN0->1 and remaining channels are digital
#define ADC_3ANA   0b00001000 					// analog: AN0->2 and remaining channels are digital
#define ADC_4ANA   0b00000000 					// analog: AN0->3  and remaining channels are digital
#define ADC_CONFIG_MASK  (~ADC_0ANA)			//Mask ADC port configuration bits
#endif // USE_OR_MASKS


#elif defined (ADC_V9) 

//***************A/D Port Configuration Control *******************************
#define ADC_0ANA      0b1111111111111111 		// All channels are digital
#define ADC_1ANA      0b1111111111111110	 	// analog: AN0 and remaining channels are digital
#define ADC_2ANA      0b1111111111111100 		// analog: AN0-AN1 and remaining channels are digital
#define ADC_3ANA      0b1111111111111000 		// analog: AN0-AN2 and remaining channels are digital
#define ADC_4ANA      0b1111111111110000 		// analog: AN0-AN3 and remaining channels are digital
#define ADC_5ANA      0b1111111111100000		// analog: AN0-AN4 and remaining channels are digital
#define ADC_6ANA      0b1111111111000000	 	// analog: AN0-AN5 and remaining channels are digital
#define ADC_7ANA      0b1111111110000000 		// analog: AN0-AN6 and remaining channels are digital
#define ADC_8ANA      0b1111111100000000 		// analog: AN0-AN7 and remaining channels are digital
#define ADC_9ANA      0b1111111000000000 		// analog: AN0-AN8 and remaining channels are digital
#define ADC_10ANA     0b1111110000000000 		// analog: AN0-AN9 and remaining channels are digital
#define ADC_11ANA     0b1111100000000000	 	// analog: AN0-AN10 and remaining channels are digital
#define ADC_12ANA     0b1111000000000000 		// analog: AN0-AN11 and remaining channels are digital
#define ADC_13ANA     0b1110000000000000 		// analog: AN0-AN12 and remaining channels are digital
#define ADC_14ANA     0b1100000000000000	 	// analog: AN0-AN13 and remaining channels are digital
#define ADC_15ANA     0b1000000000000000 		// analog: AN0-AN14 and remaining channels are digital
#define ADC_16ANA     0b0000000000000000 		// All channels are analog


#elif defined (ADC_V8) || defined (ADC_V10) 

//***************A/D Port Configuration Control *******************************
#define ADC_0ANA      0b0000000000000000 		// All channels are digital
#define ADC_1ANA      0b0000000000000001	 	// analog: AN0 and remaining channels are digital
#define ADC_2ANA      0b0000000000000011 		// analog: AN0-AN1 and remaining channels are digital
#define ADC_3ANA      0b0000000000000111 		// analog: AN0-AN2 and remaining channels are digital
#define ADC_4ANA      0b0000000000001111 		// analog: AN0-AN3 and remaining channels are digital
#define ADC_5ANA      0b0000000000011111		// analog: AN0-AN4 and remaining channels are digital
#define ADC_6ANA      0b0000000000111111	 	// analog: AN0-AN5 and remaining channels are digital
#define ADC_7ANA      0b0000000001111111 		// analog: AN0-AN6 and remaining channels are digital
#define ADC_8ANA      0b0000000011111111 		// analog: AN0-AN7 and remaining channels are digital
#define ADC_9ANA      0b0000000111111111 		// analog: AN0-AN8 and remaining channels are digital
#define ADC_10ANA     0b0000001111111111 		// analog: AN0-An9 and remaining channels are digital
#define ADC_11ANA     0b0000011111111111	 	// analog: AN0-AN10 and remaining channels are digital
#define ADC_12ANA     0b0000111111111111 		// analog: AN0-AN11 and remaining channels are digital
#define ADC_13ANA     0b0001111111111111 		// All channels are analog

#elif defined (ADC_V11) || defined (ADC_V11_1) 

//***************A/D Port Configuration Control *******************************
#define ADC_0ANA      0b0001111111111111 		// All channels are digital
#define ADC_1ANA      0b0001111111111110	 	// analog: AN0 and remaining channels are digital
#define ADC_2ANA      0b0001111111111100 		// analog: AN0-AN1 and remaining channels are digital
#define ADC_3ANA      0b0001111111111000 		// analog: AN0-AN2 and remaining channels are digital
#define ADC_4ANA      0b0001111111110000 		// analog: AN0-AN3 and remaining channels are digital
#define ADC_5ANA      0b0001111111100000		// analog: AN0-AN4 and remaining channels are digital
#define ADC_6ANA      0b0001111111000000	 	// analog: AN0-AN5 and remaining channels are digital
#define ADC_7ANA      0b0001111110000000 		// analog: AN0-AN6 and remaining channels are digital
#define ADC_8ANA      0b0001111100000000 		// analog: AN0-AN7 and remaining channels are digital
#define ADC_9ANA      0b0001111000000000 		// analog: AN0-AN8 and remaining channels are digital
#define ADC_10ANA     0b0001110000000000 		// analog: AN0-An9 and remaining channels are digital
#define ADC_11ANA     0b0001100000000000	 	// analog: AN0-AN10 and remaining channels are digital
#define ADC_12ANA     0b0001000000000000 		// analog: AN0-AN11 and remaining channels are digital
#define ADC_13ANA     0b0000000000000000 		// All channels are analog

#if defined (ADC_V11) || defined (ADC_V11_1) 
//************** Band Gap selection *******************************************
#define ADC_VBG_ON    0b1000000000000000      	//VBG output of Band Gap module is enabled
#define ADC_VBG_OFF   0b0000000000000000     	//VBG output of Band Gap module is disabled 
#endif

#endif		// End of versions


#if defined (ADC_V7) || defined (ADC_V7_1)

#ifndef USE_OR_MASKS
//************** A/D conversion type *******************************************
#define	ADC_CONV_CONTINUOUS			0b11111111	//Auto-Conversion Continuous Loop Enabled
#define	ADC_CONV_SINGLE_SHOT		0b11110111	//Single shot mode enabled

//************* A/D conversion mode *******************************************
#define	ADC_MODE_MULTI_CH			0b11111111	//Multi-Channel mode enabled
#define	ADC_MODE_SINGLE_CH			0b11111011	//Single-Channel mode enabled

//************** A/D conversion mode sequence select *****************************
#define	ADC_CONV_SEQ_SEQM1			0b11111100	//Sequential Mode 1 (SEQM1); two samples are taken in sequence
#define	ADC_CONV_SEQ_SEQM2			0b11111101	//Sequential Mode 2 (SEQM2); four samples are taken in sequence
#define	ADC_CONV_SEQ_STNM1			0b11111110	//Simultaneous Mode 1 (STNM1); two samples are taken simultaneously
#define	ADC_CONV_SEQ_STNM2			0b11111111	//Simultaneous Mode 2 (STNM2); two samples are taken simultaneously
#define	ADC_CONV_SEQ_SCM1			0b11111100	//Single Channel Mode 1 (SCM1); Group A is taken and converted
#define	ADC_CONV_SEQ_SCM2			0b11111101	//Single Channel Mode 2 (SCM2); Group B is taken and converted
#define	ADC_CONV_SEQ_SCM3			0b11111110	//Single Channel Mode 3 (SCM3); Group C is taken and converted
#define	ADC_CONV_SEQ_SCM4			0b11111111	//Single Channel Mode 4 (SCM4); Group D is taken and converted

//************** A/D Vref selection *********************************************
#define	ADC_REF_VDD_VREFMINUS		0b10111111	// ADC voltage source VREF+ = VDD and VREF- = ext.source at VREF-
#define	ADC_REF_VREFPLUS_VSS		0b01111111	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = VSS
#define	ADC_REF_VREFPLUS_VREFMINUS	0b11111111	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = ext.source at VREF-
#define	ADC_REF_VDD_VSS				0b00111111	// ADC voltage source VREF+ = VDD and VREF- = VSS

//************** A/D FIFO buffer control ****************************************
#define	ADC_FIFO_EN					0b11111111	//FIFO Buffer Enable
#define	ADC_FIFO_DIS				0b11011111	//FIFO Buffer Disable

//************** A/D Buffer depth interrupt control  *******************************
#define	INT_EACH_WR_BUF				0b00111111	//Interrupt is generated when each word is written to the buffer
#define	INT_2_4_WR_BUF				0b01111111	//Interrupt is generated when the 2nd and 4th words are written to the buffer
#define	INT_4_WR_BUF				0b10111111	//Interrupt is generated when the 4th word is written to the buffer

//************** A/D trigger source *********************************************
#define	ADC_TRIG_EXT_INT0			0b11100001	//External interrupt RC3/INT0 starts A/D sequence
#define	ADC_TRIG_TMR_5				0b11100010	//Timer5 starts A/D sequence
#define	ADC_TRIG_INP_CAP			0b11100100	//Input Capture 1 (IC1) starts A/D sequence
#define	ADC_TRIG_CCP2_COM			0b11101000	//CCP2 compare match starts A/D sequence
#define	ADC_TRIG_PCPWM				0b11110000	//Power Control PWM module rising edge starts A/D sequence

//************** A/D Acquisition Time Selection *******************************
#define	ADC_0_TAD					0b10000111	//A/D Acquisition Time is 0 TAD
#define	ADC_2_TAD					0b10001111	//A/D Acquisition Time is 2 TAD
#define	ADC_4_TAD					0b10010111	//A/D Acquisition Time is 4 TAD
#define	ADC_6_TAD					0b10011111	//A/D Acquisition Time is 6 TAD
#define	ADC_8_TAD					0b10100111	//A/D Acquisition Time is 8 TAD
#define	ADC_10_TAD					0b10101111	//A/D Acquisition Time is 10 TAD
#define	ADC_12_TAD					0b10110111	//A/D Acquisition Time is 12 TAD
#define	ADC_16_TAD					0b10111111	//A/D Acquisition Time is 16 TAD
#define	ADC_20_TAD					0b11000111	//A/D Acquisition Time is 20 TAD
#define	ADC_24_TAD					0b11001111	//A/D Acquisition Time is 24 TAD
#define	ADC_28_TAD					0b11010111	//A/D Acquisition Time is 28 TAD
#define	ADC_32_TAD					0b11011111	//A/D Acquisition Time is 32 TAD
#define	ADC_36_TAD					0b11100111	//A/D Acquisition Time is 36 TAD
#define	ADC_40_TAD					0b11101111	//A/D Acquisition Time is 40 TAD
#define	ADC_48_TAD					0b11110111	//A/D Acquisition Time is 48 TAD
#define	ADC_64_TAD					0b11111111	//A/D Acquisition Time is 64 TAD

//*************** A/D Interrupt Enable/Disable *******************************
#define ADC_INT_ON       			0b11111111	//A/D Interrupt Enable
#define ADC_INT_OFF      			0b11011111	//A/D Interrupt Disable

//*************** A/D Conversion Clock Selection *****************************
#define ADC_FOSC_2      		 	0b11111000 	//A/D conversion clock source is Fosc/2
#define ADC_FOSC_4      			0b11111100 	//A/D conversion clock source is Fosc/4
#define ADC_FRC_4        			0b11111011 	//A/D conversion clock source is FRC/4
#define ADC_FOSC_8       			0b11111001 	//A/D conversion clock source is Fosc/8
#define ADC_FOSC_16      			0b11111101 	//A/D conversion clock source is Fosc/16
#define ADC_FOSC_32      			0b11111010 	//A/D conversion clock source is Fosc/32
#define ADC_FOSC_64      			0b11111110 	//A/D conversion clock source is Fosc/64
#define ADC_FOSC_RC      			0b11111111 	//A/D conversion clock source is Internal RC OSC

#else // USE_OR_MASKS

//************** A/D conversion type *******************************************
#define	ADC_CONV_CONTINUOUS			0b00001000	//Auto-Conversion Continuous Loop Enabled
#define	ADC_CONV_SINGLE_SHOT		0b00000000	//Single shot mode enabled
#define ADC_CONV_TYPE_MASK			(~ADC_CONV_CONTINUOUS)	//Mask ADC conversion type selection bit

//************* A/D conversion mode *******************************************
#define	ADC_MODE_MULTI_CH			0b00000100	//Multi-Channel mode enabled
#define	ADC_MODE_SINGLE_CH			0b00000000	//Single-Channel mode enabled
#define ADC_MODE_MASK				(~ADC_MODE_MULTI_CH)	//Mask ADC conversion mode selection bit

//************** A/D conversion mode sequence select *****************************
#define	ADC_CONV_SEQ_SEQM1			0b00000000	//Sequential Mode 1 (SEQM1); two samples are taken in sequence
#define	ADC_CONV_SEQ_SEQM2			0b00000001	//Sequential Mode 2 (SEQM2); four samples are taken in sequence
#define	ADC_CONV_SEQ_STNM1			0b00000010	//Simultaneous Mode 1 (STNM1); two samples are taken simultaneously
#define	ADC_CONV_SEQ_STNM2			0b00000011	//Simultaneous Mode 2 (STNM2); two samples are taken simultaneously
#define	ADC_CONV_SEQ_SCM1			0b00000000	//Single Channel Mode 1 (SCM1); Group A is taken and converted
#define	ADC_CONV_SEQ_SCM2			0b00000001	//Single Channel Mode 2 (SCM2); Group B is taken and converted
#define	ADC_CONV_SEQ_SCM3			0b00000010	//Single Channel Mode 3 (SCM3); Group C is taken and converted
#define	ADC_CONV_SEQ_SCM4			0b00000011	//Single Channel Mode 4 (SCM4); Group D is taken and converted
#define ADC_CONV_SEQ_MASK			(~ADC_CONV_SEQ_STNM2)	//Mask ADC conversion mode sequence select bits

//************** A/D Vref selection *********************************************
#define	ADC_REF_VDD_VREFMINUS		0b10000000	// ADC voltage source VREF+ = VDD and VREF- = ext.source at VREF-
#define	ADC_REF_VREFPLUS_VSS		0b01000000	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = VSS
#define	ADC_REF_VREFPLUS_VREFMINUS	0b11000000	// ADC voltage source VREF+ = ext.source at VREF+ and VREF- = ext.source at VREF-
#define	ADC_REF_VDD_VSS				0b00000000	// ADC voltage source VREF+ = VDD and VREF- = VSS
#define ADC_REF_MASK	            (~ADC_REF_VREFPLUS_VREFMINUS)	//Mask ADC Vref selection bits

//************** A/D FIFO buffer control ****************************************
#define	ADC_FIFO_EN					0b00100000	//FIFO Buffer Enable
#define	ADC_FIFO_DIS				0b00000000	//FIFO Buffer Disable
#define ADC_FIFO_MASK				(~ADC_FIFO_EN)	//Mask ADC FIFO buffer control bit

//************** A/D Buffer depth interrupt control  *******************************
#define	INT_EACH_WR_BUF				0b00000000	//Interrupt is generated when each word is written to the buffer
#define	INT_2_4_WR_BUF				0b01000000	//Interrupt is generated when the 2nd and 4th words are written to the buffer
#define	INT_4_WR_BUF				0b10000000	//Interrupt is generated when the 4th word is written to the buffer
#define	INT_MASK				    (0b00111111)	//Mask ADC Buffer depth interrupt control bits

//************** A/D trigger source *********************************************
#define	ADC_TRIG_EXT_INT0			0b00000001	//External interrupt RC3/INT0 starts A/D sequence
#define	ADC_TRIG_TMR_5				0b00000010	//Timer5 starts A/D sequence
#define	ADC_TRIG_INP_CAP			0b00000100	//Input Capture 1 (IC1) starts A/D sequence
#define	ADC_TRIG_CCP2_COM			0b00001000	//CCP2 compare match starts A/D sequence	
#define	ADC_TRIG_PCPWM				0b00010000	//Power Control PWM module rising edge starts A/D sequence
#define ADC_TRIG_MASK				(0b11100000)	//Mask ADC  trigger source bits 

//************** A/D Acquisition Time Selection *******************************
#define	ADC_0_TAD					0b00000000	//A/D Acquisition Time is 0 TAD
#define	ADC_2_TAD					0b00001000	//A/D Acquisition Time is 2 TAD
#define	ADC_4_TAD					0b00010000	//A/D Acquisition Time is 4 TAD
#define	ADC_6_TAD					0b00011000	//A/D Acquisition Time is 6 TAD
#define	ADC_8_TAD					0b00100000	//A/D Acquisition Time is 8 TAD
#define	ADC_10_TAD					0b00101000	//A/D Acquisition Time is 10 TAD
#define	ADC_12_TAD					0b00110000	//A/D Acquisition Time is 12 TAD
#define	ADC_16_TAD					0b00111000	//A/D Acquisition Time is 16 TAD
#define	ADC_20_TAD					0b01000000	//A/D Acquisition Time is 20 TAD
#define	ADC_24_TAD					0b01001000	//A/D Acquisition Time is 24 TAD
#define	ADC_28_TAD					0b01010000	//A/D Acquisition Time is 28 TAD
#define	ADC_32_TAD					0b01011000	//A/D Acquisition Time is 32 TAD
#define	ADC_36_TAD					0b01100000	//A/D Acquisition Time is 36 TAD
#define	ADC_40_TAD					0b01101000	//A/D Acquisition Time is 40 TAD
#define	ADC_48_TAD					0b01110000	//A/D Acquisition Time is 48 TAD
#define	ADC_64_TAD					0b01111000	//A/D Acquisition Time is 64 TAD
#define ADC_TAD_MASK				(~ADC_64_TAD)	//Mask ADC Acquisition Time Selection bits

//*************** A/D Interrupt Enable/Disable *******************************
#define ADC_INT_ON                  0b00100000	//A/D Interrupt Enable
#define ADC_INT_OFF                 0b00000000	//A/D Interrupt Disable
#define ADC_INT_MASK                ~ADC_INT_ON	//Mask ADC Interrupt Enable/Disable

//*************** A/D Conversion Clock Selection *****************************
#define ADC_FOSC_2      		 	0b00000000 	//A/D conversion clock source is Fosc/2
#define ADC_FOSC_4      			0b00000100 	//A/D conversion clock source is Fosc/4
#define ADC_FRC_4        			0b00000011 	//A/D conversion clock source is FRC/4
#define ADC_FOSC_8       			0b00000001 	//A/D conversion clock source is Fosc/8
#define ADC_FOSC_16      			0b00000101 	//A/D conversion clock source is Fosc/16
#define ADC_FOSC_32      			0b00000010 	//A/D conversion clock source is Fosc/32
#define ADC_FOSC_64      			0b00000110 	//A/D conversion clock source is Fosc/64
#define ADC_FOSC_RC      			0b00000111 	//A/D conversion clock source is Internal RC OSC
#define ADC_FOSC_MASK				(~ADC_FOSC_RC)	//Mask ADC Conversion Clock Selection bits
#endif // USE_OR_MASKS

#endif	// End of versions

#if	defined (ADC_V1) || defined (ADC_V2) || defined (ADC_V3) || defined (ADC_V4) ||\
    defined (ADC_V5) || defined (ADC_V6) || defined (ADC_V8) || defined (ADC_V9) ||\
	defined (ADC_V10) || defined (ADC_V11) || defined (ADC_V12) || defined (ADC_V11_1) 
#ifndef USE_OR_MASKS
//**************** channel selection ******************************************
#define ADC_CH0          0b10000111  			//Select Channel 0
#define ADC_CH1          0b10001111  			//Select Channel 1
#define ADC_CH2          0b10010111  			//Select Channel 2
#define ADC_CH3          0b10011111  			//Select Channel 3
#define ADC_CH4          0b10100111  			//Select Channel 4
#define ADC_CH5          0b10101111  			//Select Channel 5
#define ADC_CH6          0b10110111  			//Select Channel 6
#define ADC_CH7          0b10111111  			//Select Channel 7
#define ADC_CH8          0b11000111  			//Select Channel 8
#define ADC_CH9          0b11001111  			//Select Channel 9
#define ADC_CH10         0b11010111  			//Select Channel 10
#define ADC_CH11         0b11011111  			//Select Channel 11
#define ADC_CH12         0b11100111  			//Select Channel 12
#if defined (ADC_V11) || defined (ADC_V11_1) 
#define ADC_CH_CTMU      0b11101111  			// All analog inputs are off
#define ADC_CH_VDDCORE   0b11110111  			// VDDCORESelect Channel
#define ADC_CH_VBG       0b11111111  			// Voltage Band gapSelect Channel
#else
#define ADC_CH13         0b11101111  			//Select Channel 13
#define ADC_CH14         0b11110111  			//Select Channel 14
#define ADC_CH15         0b11111111  			//Select Channel 15
#endif

#else // USE_OR_MASKS

//**************** channel selection ******************************************
#define ADC_CH0          0b00000000  			//Select Channel 0
#define ADC_CH1          0b00001000  			//Select Channel 1
#define ADC_CH2          0b00010000  			//Select Channel 2
#define ADC_CH3          0b00011000  			//Select Channel 3
#define ADC_CH4          0b00100000  			//Select Channel 4
#define ADC_CH5          0b00101000  			//Select Channel 5
#define ADC_CH6          0b00110000  			//Select Channel 6
#define ADC_CH7          0b00111000  			//Select Channel 7
#define ADC_CH8          0b01000000  			//Select Channel 8
#define ADC_CH9          0b01001000  			//Select Channel 9
#define ADC_CH10         0b01010000  			//Select Channel 10
#define ADC_CH11         0b01011000  			//Select Channel  11
#define ADC_CH12         0b01100000  			//Select Channel 12
#if defined (ADC_V11) || defined (ADC_V11_1) 
#define ADC_CH_CTMU      0b01101000  			// All analog inputs are off - CTMU
#define ADC_CH_VDDCORE   0b01110000  			// VDDCORESelect Channel
#define ADC_CH_VBG       0b01111000  			// Voltage Band gapSelect Channel
#define ADC_CH_MASK		 (~ADC_CH_VBG)			//Mask ADC channel selection bits
#elif defined (ADC_V10)
#define ADC_CH_MASK		 (~FVR1)			//Mask ADC channel selection bits
#else
#define ADC_CH13         0b01101000  			//Select Channel 13
#define ADC_CH14         0b01110000  			//Select Channel 14
#define ADC_CH15         0b01111000  			//Select Channel 15
#define ADC_CH_MASK		 (~ADC_CH15)			//Mask ADC channel selection bits
#endif

#endif // USE_OR_MASKS
#endif

#if defined (ADC_V10)

#ifndef USE_OR_MASKS
//**************** channel selection ******************************************
#define ADC_CH3          0b10011111  		//Select Channel 3
#define ADC_CH4          0b10100111  		//Select Channel 4
#define ADC_CH5          0b10101111  		//Select Channel 5
#define ADC_CH6          0b10110111  		//Select Channel 6
#define ADC_CH7          0b10111111  		//Select Channel 7
#define ADC_CH8          0b11000111  		//Select Channel 8
#define ADC_CH9          0b11001111  		//Select Channel 9
#define ADC_CH10         0b11010111  		//Select Channel 10
#define ADC_CH11         0b11011111  		//Select Channel 11
#define DAC1			 0b11110111  		// Digital to Analog convertor
#define FVR1			 0b11111111  		// Fixed Voltage Regulator

//************** Positive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VDD			0b11110011 	// ADC voltage source VREF+ = AVDD
#define ADC_REF_VDD_VREFPLUS 	0b11110111 	// ADC voltage source VREF+ = ext. source at VREF+
#define ADC_REF_VDD_FVREF		0b11111011 	// ADC voltage source VREF+ = FVREF+

//************** Negetive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VSS			0b11111100 	// ADC voltage source VREF- = AVSS
#define ADC_REF_VDD_VREFMINUS	0b11111101 	// ADC voltage source VREF- = ext. source at VREF-

//------------------------------------------------
#else //USE_OR_MASKS
//**************** channel selection ******************************************
#define ADC_CH3          0b00011000  		//Select Channel 3
#define ADC_CH4          0b00100000  		//Select Channel 4
#define ADC_CH5          0b00101000  		//Select Channel 5
#define ADC_CH6          0b00110000  		//Select Channel 6
#define ADC_CH7          0b00111000  		//Select Channel 7
#define ADC_CH8          0b01000000  		//Select Channel 8
#define ADC_CH9          0b01001000  		//Select Channel 9
#define ADC_CH10         0b01010000  		//Select Channel 10
#define ADC_CH11         0b01011000  		//Select Channel  11
#define DAC1			 0b01110000  		// Digital to Analog convertor
#define FVR1			 0b01111000  		// Fixed Voltage Regulator


//************** Positive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VDD			0b00000000 	// ADC voltage source VREF+ = AVDD
#define ADC_REF_VDD_VREFPLUS	0b00000100 	// ADC voltage source VREF+ = ext. source at VREF+
#define ADC_REF_VDD_FVREF		0b00001000 	// ADC voltage source VREF+ = FVREF+

//************** Negetive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VSS		    0b00000000 	// ADC voltage source VREF- = AVSS
#define ADC_REF_VDD_VREFMINUS	0b00000001 	// ADC voltage source VREF- = ext. source at VREF-
#endif //USE_OR_MASKS

#endif  // End of version ADC_V10

#if defined (ADC_V12) 

#ifndef USE_OR_MASKS
//**************** Special Trigger Select bit *********************************
#define ADC_TRIG_CTMU           0b11111111 	//Special trigger from the CTMU
#define ADC_TRIG_CCP2           0b01111111 	//Special trigger from CCP2
#else //USE_OR_MASKS
//**************** Special Trigger Select bit *********************************
#define ADC_TRIG_CTMU           0b10000000 	//Special trigger from the CTMU
#define ADC_TRIG_CCP2           0b00000000 	//Special trigger from CCP2
#define ADC_TRIG_MASK           (~ADC_TRIG_CTMU)	//Mask ADC Special Trigger Select bit
#endif //USE_OR_MASKS

#endif  // End of version ADC_V12


#if defined (ADC_V13) || defined (ADC_V13_1) || defined (ADC_V11_1) ||\
	defined (ADC_V13_2) || defined (ADC_V13_3)
#ifndef USE_OR_MASKS
//**************** Special Trigger Select bit *********************************
#define ADC_TRIG_RTCC           0b11111111 	//Special trigger from the RTCC
#define ADC_TRIG_TIMER1         0b10111111 	//Special trigger from TIMER1
#define ADC_TRIG_CTMU           0b01111111 	//Special trigger from the CTMU
#define ADC_TRIG_CCP2           0b00111111 	//Special trigger from CCP2
#else //USE_OR_MASKS
//**************** Special Trigger Select bit *********************************
#define ADC_TRIG_RTCC           0b11000000 	//Special trigger from the RTCC
#define ADC_TRIG_TIMER1         0b10000000 	//Special trigger from TIMER1
#define ADC_TRIG_CTMU           0b01000000 	//Special trigger from the CTMU
#define ADC_TRIG_CCP2           0b00000000 	//Special trigger from CCP2
#define ADC_TRIG_MASK           (~ADC_TRIG_RTCC)	//Mask ADC Special Trigger Select bit
#endif //USE_OR_MASKS
#endif


#if defined (ADC_V13) || defined (ADC_V13_1) || defined (ADC_V13_2)\
	 || defined (ADC_V13_3)

#ifndef USE_OR_MASKS
//**************** channel selection ******************************************
#define ADC_CH0          0b10000011  			//Select Channel 0
#define ADC_CH1          0b10000111  			//Select Channel 1
#define ADC_CH2          0b10001011  			//Select Channel 2
#define ADC_CH3          0b10001111  			//Select Channel 3
#define ADC_CH4          0b10010011  			//Select Channel 4
#if defined (ADC_V13) || defined (ADC_V13_1)  || defined (ADC_V13_3)
#define ADC_CH5          0b10010111  			//Select Channel 5
#define ADC_CH6          0b10011011  			//Select Channel 6
#define ADC_CH7          0b10011111  			//Select Channel 7
#define ADC_CH9          0b10100111  			//Select Channel 9
#define ADC_CH10         0b10101011  			//Select Channel 10
#endif
#define ADC_CH8          0b10100011  			//Select Channel 8
#if defined (ADC_V13) || defined (ADC_V13_1) 
#define ADC_CH11         0b10101111  			//Select Channel  11
#endif
#if defined (ADC_V13) || defined (ADC_V13_1) 
#if defined (ADC_V13)
#define ADC_CH12         0b10110011  			//Select Channel 12
#define ADC_CH13         0b10110111  			//Select Channel 13
#define ADC_CH14         0b10111011  			//Select Channel 14
#define ADC_CH15         0b10111111  			//Select Channel 15
#endif
#define ADC_CH16         0b11000011  			//Select Channel 16
#define ADC_CH17         0b11000111  			//Select Channel  17
#define ADC_CH18         0b11001011  			//Select Channel 18
#define ADC_CH19         0b11001111  			//Select Channel 19
#if defined (ADC_V13)
#define ADC_CH20         0b11010011  			//Select Channel 20
#define ADC_CH21         0b11010111  			//Select Channel 21
#define ADC_CH22         0b11011011  			//Select Channel 22
#define ADC_CH23         0b11011111  			//Select Channel 23
#define ADC_CH28         0b11110011  			//Select Channel 28 for CTMU
#define ADC_CH29         0b11110111  			//Select Channel 29 for Temperature Sensor
#endif
#define ADC_CH30         0b11111011  			//Select Channel 30
#define ADC_CH31         0b11111111  			//Select Channel 31
#endif
#else // USE_OR_MASKS

//**************** channel selection ******************************************
#define ADC_CH0          0b00000000  			//Select Channel 0
#define ADC_CH1          0b00000100  			//Select Channel 1
#define ADC_CH2          0b00001000  			//Select Channel 2
#define ADC_CH3          0b00001100  			//Select Channel 3
#define ADC_CH4          0b00010000  			//Select Channel 4
#if defined (ADC_V13) || defined (ADC_V13_1)  || defined (ADC_V13_3)
#define ADC_CH5          0b00010100  			//Select Channel 5
#define ADC_CH6          0b00011000  			//Select Channel 6
#define ADC_CH7          0b00011100  			//Select Channel 7
#define ADC_CH9          0b00100100  			//Select Channel 9
#define ADC_CH10         0b00101000  			//Select Channel 10
#endif
#define ADC_CH8          0b00100000  			//Select Channel 8
#if defined (ADC_V13) || defined (ADC_V13_1) 
#define ADC_CH11         0b00101100  			//Select Channel  11
#endif
#if defined (ADC_V13) || defined (ADC_V13_1) 
#if defined (ADC_V13)
#define ADC_CH12         0b00110000  			//Select Channel 12
#define ADC_CH13         0b00110100  			//Select Channel 13
#define ADC_CH14         0b00111000  			//Select Channel 14
#define ADC_CH15         0b00111100  			//Select Channel 15
#endif
#define ADC_CH16         0b01000000  			//Select Channel 16
#define ADC_CH17         0b01000100  			//Select Channel  17
#define ADC_CH18         0b01001000  			//Select Channel 18
#define ADC_CH19         0b01001100  			//Select Channel 19
#if defined (ADC_V13)
#define ADC_CH20         0b01010000  			//Select Channel 20
#define ADC_CH21         0b01010100  			//Select Channel 21
#define ADC_CH22         0b01011000  			//Select Channel 22
#define ADC_CH23         0b01011100  			//Select Channel 23
#define ADC_CH28         0b01110000  			//Select Channel 28 for CTMU
#define ADC_CH29         0b01110100  			//Select Channel 29 for Temperature Sensor
#endif
#define ADC_CH30         0b01111000  			//Select Channel 30
#define ADC_CH31         0b01111100  			//Select Channel 31
#endif
#define ADC_CH_MASK		 (~0b01111100)			//Mask ADC channel selection bits

#endif // USE_OR_MASKS


#ifndef USE_OR_MASKS
//**************** Analog Negetive Channel Select *********************************
#define ADC_NEG_CH0          0b11111000  			//Select Analog Negetive Channel 0
#define ADC_NEG_CH1          0b11111001  			//Select Analog Negetive  Channel 1
#define ADC_NEG_CH2          0b11111010  			//Select Analog Negetive  Channel 2
#define ADC_NEG_CH3          0b11111011  			//Select Analog Negetive  Channel 3
#define ADC_NEG_CH4          0b11111100  			//Select Analog Negetive  Channel 4
#define ADC_NEG_CH5          0b11111101  			//Select Analog Negetive  Channel 5
#define ADC_NEG_CH6          0b11111110  			//Select Analog Negetive  Channel 6
#if defined (ADC_V13) || defined (ADC_V13_1)
#define ADC_NEG_CH7          0b11111111  			//Select Analog Negetive  Channel 7
#endif
#else //USE_OR_MASKS
//**************** Analog Negetive Channel Select *********************************
#define ADC_NEG_CH0          0b00000000  			//Select Analog Negetive Channel 0
#define ADC_NEG_CH1          0b00000001  			//Select Analog Negetive  Channel 1
#define ADC_NEG_CH2          0b00000010  			//Select Analog Negetive  Channel 2
#define ADC_NEG_CH3          0b00000011  			//Select Analog Negetive  Channel 3
#define ADC_NEG_CH4          0b00000100  			//Select Analog Negetive  Channel 4
#define ADC_NEG_CH5          0b00000101  			//Select Analog Negetive  Channel 5
#define ADC_NEG_CH6          0b00000110  			//Select Analog Negetive  Channel 6
#if defined (ADC_V13) || defined (ADC_V13_1)
#define ADC_NEG_CH7          0b00000111  			//Select Analog Negetive  Channel 7
#endif
#define ADC_NEG_CH_MASK		 (~0b00000111)			//Mask ADC Negetive channel selection bits
#endif //USE_OR_MASKS

#ifndef USE_OR_MASKS
//************** Positive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VDD			0b11001111 	// ADC voltage positive source VREF+ = AVDD
#define ADC_REF_VDD_VREFPLUS	0b11011111 	// ADC voltage positive source VREF+ = ext. source at VREF+
#define ADC_REF_VDD_INT_VREF_2	0b11101111 	// ADC voltage positive source VREF+ = Inetrnal VREF of 2.048V
#define ADC_REF_VDD_INT_VREF_4	0b11111111 	// ADC voltage positive source VREF+ = Inetrnal VREF of 4.096V
//************** Negetive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VSS		    0b11110111 	// ADC voltage negetive source VREF- = AVSS
#define ADC_REF_VDD_VREFMINUS	0b11111111 	// ADC voltage negetive source VREF- = ext. source at VREF-
#else //USE_OR_MASKS
//************** Positive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VDD			0b00000000 	// ADC voltage positive source VREF+ = AVDD
#define ADC_REF_VDD_VREFPLUS	0b00010000 	// ADC voltage positive source VREF+ = ext. source at VREF+
#define ADC_REF_VDD_INT_VREF_2	0b00100000 	// ADC voltage positive source VREF+ = Inetrnal VREF of 2.048V
#define ADC_REF_VDD_INT_VREF_4	0b00110000 	// ADC voltage positive source VREF+ = Inetrnal VREF of 4.096V
#define ADC_REF_POS_MASK		(~ADC_REF_VDD_INT_VREF_4)			//Mask ADC positive voltage source selection bits
//************** Negetive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VSS		    0b00000000 	// ADC voltage negetive source VREF- = AVSS
#define ADC_REF_VDD_VREFMINUS	0b00001000 	// ADC voltage negetive source VREF- = ext. source at VREF-
#define ADC_REF_NEG_MASK		(~ADC_REF_VDD_VREFMINUS)			//Mask ADC negetive voltage source selection bits
#endif //USE_OR_MASKS

/*ADC Port Configuration register definition*/
#define ENABLE_AN0_DIG()              ANCON0bits.PCFG0=1 /*Enable AN0 in digital mode */
#define ENABLE_AN0_ANA()              ANCON0bits.PCFG0=0 /*Enable AN0 in analog mode */
#define ENABLE_AN1_DIG()              ANCON0bits.PCFG1=1 /*Enable AN1 in digital mode */
#define ENABLE_AN1_ANA()              ANCON0bits.PCFG1=0 /*Enable AN1 in analog mode */
#define ENABLE_AN2_DIG()              ANCON0bits.PCFG2=1 /*Enable AN2 in digital mode */
#define ENABLE_AN2_ANA()              ANCON0bits.PCFG2=0 /*Enable AN2 in analog mode */
#define ENABLE_AN3_DIG()              ANCON0bits.PCFG3=1 /*Enable AN3 in digital mode */
#define ENABLE_AN3_ANA()              ANCON0bits.PCFG3=0 /*Enable AN3 in analog mode */
#define ENABLE_AN4_DIG()              ANCON0bits.PCFG4=1 /*Enable AN4 in digital mode */
#define ENABLE_AN4_ANA()              ANCON0bits.PCFG4=0 /*Enable AN4 in analog mode */
#if defined (ADC_V13) || defined (ADC_V13_1)  || defined (ADC_V13_3)
#define ENABLE_AN5_DIG()              ANCON0bits.PCFG5=1 /*Enable AN5 in digital mode */
#define ENABLE_AN5_ANA()              ANCON0bits.PCFG5=0 /*Enable AN5 in analog mode */
#define ENABLE_AN6_DIG()              ANCON0bits.PCFG6=1 /*Enable AN6 in digital mode */
#define ENABLE_AN6_ANA()              ANCON0bits.PCFG6=0 /*Enable AN6 in analog mode */
#define ENABLE_AN7_DIG()              ANCON0bits.PCFG7=1 /*Enable AN7 in digital mode */
#define ENABLE_AN7_ANA()              ANCON0bits.PCFG7=0 /*Enable AN7 in analog mode */
#define ENABLE_ALL_ANA_0_7()          ANCON0=0x00 /*Enable AN0-AN7 in analog mode */
#define ENABLE_ALL_DIG_0_7()          ANCON0=0xFF /*Enable AN0-AN7 in Digital mode */
#endif
#define ENABLE_AN8_DIG()              ANCON1bits.PCFG8=1 /*Enable AN8 in digital mode */
#define ENABLE_AN8_ANA()              ANCON1bits.PCFG8=0 /*Enable AN8 in analog mode */
#if defined (ADC_V13) || defined (ADC_V13_1)  || defined (ADC_V13_3)
#define ENABLE_AN9_DIG()              ANCON1bits.PCFG9=1 /*Enable AN9 in digital mode */
#define ENABLE_AN9_ANA()              ANCON1bits.PCFG9=0 /*Enable AN9 in analog mode */
#define ENABLE_AN10_DIG()             ANCON1bits.PCFG10=1 /*Enable AN10 in digital mode */
#define ENABLE_AN10_ANA()             ANCON1bits.PCFG10=0 /*Enable AN10 in analog mode */
#endif
#if defined (ADC_V13) || defined (ADC_V13_1) 
#define ENABLE_AN11_DIG()             ANCON1bits.PCFG11=1 /*Enable AN11 in digital mode */
#define ENABLE_AN11_ANA()             ANCON1bits.PCFG11=0 /*Enable AN11 in analog mode */

#if defined (ADC_V13)
#define ENABLE_AN12_DIG()             ANCON1bits.PCFG12=1 /*Enable AN12 in digital mode */
#define ENABLE_AN12_ANA()             ANCON1bits.PCFG12=0 /*Enable AN12 in analog mode */
#define ENABLE_AN13_DIG()             ANCON1bits.PCFG13=1 /*Enable AN13 in digital mode */
#define ENABLE_AN13_ANA()             ANCON1bits.PCFG13=0 /*Enable AN13 in analog mode */
#define ENABLE_AN14_DIG()             ANCON1bits.PCFG14=1 /*Enable AN14 in digital mode */
#define ENABLE_AN14_ANA()             ANCON1bits.PCFG14=0 /*Enable AN14 in analog mode */
#define ENABLE_AN15_DIG()             ANCON1bits.PCFG15=1 /*Enable AN15 in digital mode */
#define ENABLE_AN15_ANA()             ANCON1bits.PCFG15=0 /*Enable AN15 in analog mode */
#endif
#define ENABLE_ALL_ANA_8_15()         ANCON1=0x00 /*Enable AN8-AN15 in analog mode */
#define ENABLE_ALL_DIG_8_15()         ANCON1=0xFF /*Enable AN8-AN15 in Digital mode */

#define ENABLE_AN16_DIG()             ANCON2bits.PCFG16=1 /*Enable AN16 in digital mode */
#define ENABLE_AN16_ANA()             ANCON2bits.PCFG16=0 /*Enable AN16 in analog mode */
#define ENABLE_AN17_DIG()             ANCON2bits.PCFG17=1 /*Enable AN17 in digital mode */
#define ENABLE_AN17_ANA()             ANCON2bits.PCFG17=0 /*Enable AN17 in analog mode */
#define ENABLE_AN18_DIG()             ANCON2bits.PCFG18=1 /*Enable AN18 in digital mode */
#define ENABLE_AN18_ANA()             ANCON2bits.PCFG18=0 /*Enable AN18 in analog mode */
#define ENABLE_AN19_DIG()             ANCON2bits.PCFG19=1 /*Enable AN19 in digital mode */
#define ENABLE_AN19_ANA()             ANCON2bits.PCFG19=0 /*Enable AN19 in analog mode */
#if defined (ADC_V13)
#define ENABLE_AN20_DIG()             ANCON2bits.PCFG20=1 /*Enable AN20 in digital mode */
#define ENABLE_AN20_ANA()             ANCON2bits.PCFG20=0 /*Enable AN20 in analog mode */
#define ENABLE_AN21_DIG()             ANCON2bits.PCFG21=1 /*Enable AN21 in digital mode */
#define ENABLE_AN21_ANA()             ANCON2bits.PCFG21=0 /*Enable AN21 in analog mode */
#define ENABLE_AN22_DIG()             ANCON2bits.PCFG22=1 /*Enable AN22 in digital mode */
#define ENABLE_AN22_ANA()             ANCON2bits.PCFG22=0 /*Enable AN22 in analog mode */
#define ENABLE_AN23_DIG()             ANCON2bits.PCFG23=1 /*Enable AN23 in digital mode */
#define ENABLE_AN23_ANA()             ANCON2bits.PCFG23=0 /*Enable AN23 in analog mode */
#endif
#define ENABLE_ALL_ANA_16_23()        ANCON2=0x00 /*Enable AN16-AN23 in analog mode */
#define ENABLE_ALL_DIG_16_23()        ANCON2=0xFF /*Enable AN16-AN23 in Digital mode */
#endif

#endif  // End of version ADC_V13 & ADC_V13_1

#if defined (ADC_V14) || defined (ADC_V14_1)

#ifndef	USE_OR_MASKS
//*************** A/D Result Format Select ***********************************
#define ADC_RIGHT_JUST   0b11111111 			// Right justify A/D result
#define ADC_LEFT_JUST    0b01111111 			// Left justify A/D result
#else // USE_OR_MASKS
//*************** A/D Result Format Select ***********************************
#define ADC_RIGHT_JUST   0b10000000 			// Right justify A/D result
#define ADC_LEFT_JUST    0b00000000 			// Left justify A/D result
#define ADC_RESULT_MASK	 (~ADC_RIGHT_JUST)		//Mask ADC Result adjust bit
#endif // USE_OR_MASKS

#ifndef USE_OR_MASKS
//************** Positive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VDD			0b11110011 	// ADC voltage positive source VREF+ = AVDD
#define ADC_REF_VDD_VREFPLUS	0b11110111 	// ADC voltage positive source VREF+ = ext. source at VREF+
#define ADC_REF_FVR_BUF			0b11111011 	// ADC voltage positive source VREF+ = FVR BUF
//************** Negetive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VSS		    0b11111100 	// ADC voltage negetive source VREF- = AVSS
#define ADC_REF_VDD_VREFMINUS	0b11111101 	// ADC voltage negetive source VREF- = ext. source at VREF-
#else //USE_OR_MASKS
//************** Positive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VDD			0b00000000 	// ADC voltage positive source VREF+ = AVDD
#define ADC_REF_VDD_VREFPLUS	0b00000100 	// ADC voltage positive source VREF+ = ext. source at VREF+
#define ADC_REF_FVR_BUF			0b00001000 	// ADC voltage positive source VREF+ = FVR BUF
#define ADC_REF_POS_MASK		(~0b00001100)			//Mask ADC positive voltage source selection bits
//************** Negetive Voltage Reference Configuration *************************
#define ADC_REF_VDD_VSS		    0b00000000 	// ADC voltage negetive source VREF- = AVSS
#define ADC_REF_VDD_VREFMINUS	0b00001001 	// ADC voltage negetive source VREF- = ext. source at VREF-
#define ADC_REF_NEG_MASK		(~0b00000011)			//Mask ADC negetive voltage source selection bits
#endif //USE_OR_MASKS

#ifndef USE_OR_MASKS
//**************** Special Trigger Select bit *********************************
#define ADC_TRIG_CTMU           0b11111111 	//Special trigger from the CTMU
#define ADC_TRIG_CCP5           0b01111111 	//Special trigger from CCP5
#else //USE_OR_MASKS
//**************** Special Trigger Select bit *********************************
#define ADC_TRIG_CTMU           0b10000000 	//Special trigger from the CTMU
#define ADC_TRIG_CCP5           0b00000000 	//Special trigger from CCP5
#define ADC_TRIG_MASK           (~ADC_TRIG_CTMU)	//Mask ADC Special Trigger Select bit
#endif //USE_OR_MASKS

#ifndef USE_OR_MASKS
#define ADC_CH0          0b10000011  			//Select Channel 0
#define ADC_CH1          0b10000111  			//Select Channel 1
#define ADC_CH2          0b10001011  			//Select Channel 2
#define ADC_CH3          0b10001111  			//Select Channel 3
#define ADC_CH4          0b10010011  			//Select Channel 4
#if defined (ADC_V14)
#define ADC_CH5          0b10010111  			//Select Channel 5
#define ADC_CH6          0b10011011  			//Select Channel 6
#define ADC_CH7          0b10011111  			//Select Channel 7
#endif
#define ADC_CH8          0b10100011  			//Select Channel 8
#define ADC_CH9          0b10100111  			//Select Channel 9
#define ADC_CH10         0b10101011  			//Select Channel 10
#define ADC_CH11         0b10101111  			//Select Channel  11
#define ADC_CH12         0b10110011  			//Select Channel 12
#define ADC_CH13         0b10110111  			//Select Channel 13
#define ADC_CH14         0b10111011  			//Select Channel 14
#define ADC_CH15         0b10111111  			//Select Channel 15
#define ADC_CH16         0b11000011  			//Select Channel 16
#define ADC_CH17         0b11000111  			//Select Channel  17
#define ADC_CH18         0b11001011  			//Select Channel 18
#define ADC_CH19         0b11001111  			//Select Channel 19
#define ADC_CH20         0b11010011  			//Select Channel 20
#if defined (ADC_V14)
#define ADC_CH21         0b11010111  			//Select Channel 21
#define ADC_CH22         0b11011011  			//Select Channel 22
#define ADC_CH23         0b11011111  			//Select Channel 23
#define ADC_CH24         0b11100011  			//Select Channel 24
#define ADC_CH25         0b11100111  			//Select Channel 25
#define ADC_CH26         0b11101011  			//Select Channel 26
#define ADC_CH27         0b11101111  			//Select Channel 27
#endif
#define ADC_CH_CTMU      0b11110111  			//Select Channel 29 for CTMU
#define ADC_CH_DAC       0b11111011  			//Select Channel 30 for DAC
#define ADC_CH_FRV       0b11111111  			//Select Channel 31 for FRV
#else
#define ADC_CH0          0b00000000  			//Select Channel 0
#define ADC_CH1          0b00000100  			//Select Channel 1
#define ADC_CH2          0b00001000  			//Select Channel 2
#define ADC_CH3          0b00001100  			//Select Channel 3
#define ADC_CH4          0b00010000  			//Select Channel 4
#if defined (ADC_V14)
#define ADC_CH5          0b00010100  			//Select Channel 5
#define ADC_CH6          0b00011000  			//Select Channel 6
#define ADC_CH7          0b00011100  			//Select Channel 7
#endif
#define ADC_CH8          0b00100000  			//Select Channel 8
#define ADC_CH9          0b00100100  			//Select Channel 9
#define ADC_CH10         0b00101000  			//Select Channel 10
#define ADC_CH11         0b00101100  			//Select Channel  11
#define ADC_CH12         0b00110000  			//Select Channel 12
#define ADC_CH13         0b00110100  			//Select Channel 13
#define ADC_CH14         0b00111000  			//Select Channel 14
#define ADC_CH15         0b00111100  			//Select Channel 15
#define ADC_CH16         0b01000000  			//Select Channel 16
#define ADC_CH17         0b01000100  			//Select Channel  17
#define ADC_CH18         0b01001000  			//Select Channel 18
#define ADC_CH19         0b01001100  			//Select Channel 19
#define ADC_CH20         0b01010000  			//Select Channel 20
#if defined (ADC_V14)
#define ADC_CH21         0b01010100  			//Select Channel 21
#define ADC_CH22         0b01011000  			//Select Channel 22
#define ADC_CH23         0b01011100  			//Select Channel 23
#define ADC_CH24         0b01100000  			//Select Channel 24
#define ADC_CH25         0b01100100  			//Select Channel 25
#define ADC_CH26         0b01101000  			//Select Channel 26
#define ADC_CH27         0b01101100  			//Select Channel 27
#endif
#define ADC_CH_CTMU      0b01110100  			//Select Channel 29 for CTMU
#define ADC_CH_DAC       0b01111000  			//Select Channel 30 for DAC
#define ADC_CH_FRV       0b01111100  			//Select Channel 31 for FRV
#define ADC_CH_MASK		 (~0b01111100)			//Mask ADC channel selection bits
#endif

#endif

/* Stores the result of an A/D conversion. */
union ADCResult
{
	int lr;			//holds the 10-bit ADC Conversion value as integer
 	char br[2];		//holds the 10-bit ADC Conversion value as two byte values
};

char BusyADC (void);

void ConvertADC (void);

int ReadADC(void);

void CloseADC(void);


#if defined (ADC_V1) || defined (ADC_V2)

void OpenADC ( unsigned char ,
               unsigned char );

#elif defined (ADC_V3) || defined (ADC_V4) || defined (ADC_V5) || defined (ADC_V6) ||\
      defined (ADC_V7) || defined (ADC_V7_1)|| defined (ADC_V12) || defined (ADC_V13)\
	  || defined (ADC_V13_1) || defined (ADC_V13_2) || defined (ADC_V13_3) || \
	  defined (ADC_V14) || defined (ADC_V14_1)

void OpenADC ( unsigned char ,
               unsigned char ,
               unsigned char );

#elif defined (ADC_V8) || defined (ADC_V9) 

void OpenADC ( unsigned char ,
               unsigned char ,
               unsigned int );

#elif defined (ADC_V10) || defined (ADC_V11_1) 

void OpenADC ( unsigned char ,
               unsigned char ,
               unsigned char ,
               unsigned int );
			  
#elif defined (ADC_V11)

void OpenADC ( unsigned char ,
               unsigned char ,
               unsigned int );
#endif

 
#if defined (ADC_V1) || defined (ADC_V2) || defined (ADC_V3) ||\
    defined (ADC_V4) || defined ( ADC_V5 ) || defined ( ADC_V6 )||\
	defined ( ADC_V8 ) || defined ( ADC_V9) || defined ( ADC_V10)||\
	defined ( ADC_V11) || defined (ADC_V12) || defined (ADC_V13) || \
	defined (ADC_V13_1) || defined ( ADC_V11_1) || defined (ADC_V13_2)\
	|| defined (ADC_V13_3) || defined (ADC_V14) || defined (ADC_V14_1)
	
void SetChanADC(unsigned char );
#endif

void SelChanConvADC( unsigned char );




#if defined (ADC_V4)

/***********************************************************************************
Macro       : ADC_SEVT_ENABLE()
Overview : Macro enables Special Event Trigger from Power Control PWM module
Parameters   : None
Remarks     : Functions in coordination with PCPWM Module
***********************************************************************************/
#define ADC_SEVT_ENABLE()    (ADCON0bits.SEVTEN=1)

/***********************************************************************************
Macro       : ADC_SEVT_DISABLE()
Overview : Macro disables Special Event Trigger from Power Control PWM module
Parameters   : None
Remarks     : Functions in coordination with PCPWM Module
***********************************************************************************/
#define ADC_SEVT_DISABLE()   (ADCON0bits.SEVTEN=0)

#endif


#if defined (ADC_V6) || defined (ADC_V12)

/***********************************************************************************
Macro       : ADC_CALIB()
Overview : Macro performs the Calibration on next A/D conversion
Parameters   : None
Remarks     : None
***********************************************************************************/
#define	ADC_CALIB()	    (ADCON0bits.ADCAL=1)

/***********************************************************************************
Macro       : ADC_NO_CALIB()
Overview : Macro performs the Normal A/D converter operation with no Calibration
Parameters   : None
Remarks     : None
***********************************************************************************/
#define	ADC_NO_CALIB()	(ADCON0bits.ADCAL=0)


#elif defined (ADC_V9)

/***********************************************************************************
Macro       : ADC_CALIB()
Overview : Macro performs the Calibration on next A/D conversion
Parameters   : None
Remarks     : None
***********************************************************************************/
#define	ADC_CALIB()	    (WDTCONbits.DEVCFG=0, ADCON1bits.ADCAL=1)

/***********************************************************************************
Macro       : ADC_NO_CALIB()
Overview : Macro performs the Normal A/D converter operation with no Calibration
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_NO_CALIB()	(WDTCONbits.DEVCFG=0, ADCON1bits.ADCAL=0)

#elif defined (ADC_V11)

/***********************************************************************************
Macro       : ADC_CALIB()
Overview : Macro performs the Calibration on next A/D conversion
Parameters   : None
Remarks     : None
***********************************************************************************/
#define	ADC_CALIB()	    (ADCON1bits.ADCAL=1)

/***********************************************************************************
Macro       : ADC_NO_CALIB()
Overview : Macro performs the Normal A/D converter operation with no Calibration
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_NO_CALIB()	(ADCON1bits.ADCAL=0)

#endif

//---------------------------------/
#if defined (ADC_V7) || defined (ADC_V7_1)
/******* A/D channel from Gruop A *******/
#define ADC_CH_GRA_SEL_AN0   0
/******* A/D channel from Gruop A *******/
#define ADC_CH_GRA_SEL_AN4   4
/******* A/D channel from Gruop B *******/
#define ADC_CH_GRB_SEL_AN1   1
/******* A/D channel from Gruop C *******/
#define ADC_CH_GRC_SEL_AN2   2
/******* A/D channel from Gruop D *******/
#define ADC_CH_GRD_SEL_AN3   3

/***********************************************************************************
Macro       : ADC_CH_GRA_AN0()
Overview : Macro  select AN0 as analog in Group A
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_CH_GRA_AN0()	{ANSEL0|=0b00000001; ADCHS|=0b00000011; ADCHS&=0b11111100;}

/***********************************************************************************
Macro       : ADC_CH_GRA_AN4()
Overview : Macro  select AN4 as analog in Group A
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_CH_GRA_AN4()	{ANSEL0|=0b00010000; ADCHS|=0b00000011; ADCHS&=0b11111101;}

/***********************************************************************************
Macro       : ADC_CH_GRB_AN1()
Overview : Macro  select AN1 as analog in Group B
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_CH_GRB_AN1()	{ANSEL0|=0b00000010; ADCHS|=0b00110000; ADCHS&=0b11001111;}

/***********************************************************************************
Macro       : ADC_CH_GRC_AN2()
Overview : Macro  select AN2 as analog in Group C
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_CH_GRC_AN2()	{ANSEL0|=0b00000100; ADCHS|=0b00001100; ADCHS&=0b11110011;}

/***********************************************************************************
Macro       : ADC_CH_GRD_AN3()
Overview : Macro  select AN3 as analog in Group D
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_CH_GRD_AN3()	{ANSEL0|=0b00001000; ADCHS|=0b11000000; ADCHS&=0b00111111;}

#endif

#if defined (ADC_V7)
/****** A/D channel from Gruop B ******/
#define ADC_CH_GRB_SEL_AN5   5
/****** A/D channel from Gruop C ******/
#define ADC_CH_GRC_SEL_AN6   6
/****** A/D channel from Gruop D ******/
#define ADC_CH_GRD_SEL_AN7   7
/****** A/D channel from Gruop A ******/	
#define ADC_CH_GRA_SEL_AN8   8

/***********************************************************************************
Macro       : ADC_CH_GRB_AN5()
Overview : Macro  select AN5 as analog in Group B
Parameters   : None
Remarks     : None
***********************************************************************************/	
#define ADC_CH_GRB_AN5()	{ANSEL0|=0b00100000; ADCHS|=0b00110000; ADCHS&=0b11011111;}

/***********************************************************************************
Macro       : ADC_CH_GRC_AN6()
Overview : Macro  select AN6 as analog in Group C
Parameters   : None
Remarks     : None
***********************************************************************************/
#define ADC_CH_GRC_AN6()	{ANSEL0|=0b01000000; ADCHS|=0b00001100; ADCHS&=0b11110111;}

/***********************************************************************************
Macro       : ADC_CH_GRD_AN7()
Overview : Macro  select AN7 as analog in Group D
Parameters   : None
Remarks     : None
***********************************************************************************/			
#define ADC_CH_GRD_AN7()	{ANSEL0|=0b10000000; ADCHS|=0b11000000; ADCHS&=0b01111111;}

/***********************************************************************************
Macro       : ADC_CH_GRA_AN8()
Overview : Macro  select AN8 as analog in Group A
Parameters   : None
Remarks     : None
***********************************************************************************/										
#define ADC_CH_GRA_AN8()	{ANSEL1|=0b00000001; ADCHS|=0b00000011; ADCHS&=0b11111110;}

/***********************************************************************************
Macro       : ALL_CH_DIGITAL()
Overview : Macro  configures all channels as Digital
Parameters   : None
Remarks     : None
***********************************************************************************/	
#define ALL_CH_DIGITAL()	{ANSEL0=0; ANSEL1=0;}

#endif


/*Macros for backward compatibility*/ 
#if defined (ADC_V8)

#define ADC_CHAN0   0b1111111111111110  // AN0
#define ADC_CHAN1   0b1111111111111101  // AN1
#define ADC_CHAN2   0b1111111111111011  // AN2
#define ADC_CHAN3   0b1111111111110111  // AN3
#define ADC_CHAN4   0b1111111111101111  // AN4
#define ADC_CHAN5   0b1111111111011111  // AN5
#define ADC_CHAN6   0b1111111110111111  // AN6
#define ADC_CHAN7   0b1111111101111111  // AN7
#define ADC_CHAN8   0b1111111011111111  // AN8
#define ADC_CHAN9   0b1111110111111111  // AN9
#define ADC_CHAN10  0b1111101111111111  // AN10
#define ADC_CHAN11  0b1111011111111111  // AN11
#define ADC_CHAN12  0b1110111111111111  // AN12
#define ADC_CHAN13  0b1101111111111111  // AN13
#define ADC_CHAN14  0b1011111111111111  // AN14
#define ADC_CHAN15  0b0111111111111111  // AN15

#endif

#if defined ( ADC_V2 ) || defined (ADC_V3) || defined (ADC_V4 ) ||\
    defined ( ADC_V5 ) || defined ( ADC_V6 ) || defined (ADC_V7) ||\
	defined (ADC_V7_1) || defined ( ADC_V8 ) || defined (ADC_V9) ||\
	defined (ADC_V11) || defined (ADC_V12)
#define ADC_VREFPLUS_VDD	ADC_REF_VDD_VREFMINUS      	// VREF+ = AVDD
#define ADC_VREFPLUS_EXT	ADC_REF_VREFPLUS_VREFMINUS 	// VREF+ = external
#define ADC_VREFMINUS_VSS	ADC_REF_VREFPLUS_VSS       	// VREF- = AVSS
#define ADC_VREFMINUS_EXT	ADC_REF_VREFPLUS_VREFMINUS 	// VREF- = external

#elif defined (ADC_V10)
#define ADC_VREFPLUS_VDD	ADC_REF_VDD_VDD      		// VREF+ = AVDD
#define ADC_VREFPLUS_EXT	ADC_REF_VDD_VREFPLUS 		// VREF+ = external
#define ADC_VREFMINUS_VSS	ADC_REF_VDD_VSS       		// VREF- = AVSS
#define ADC_VREFMINUS_EXT	ADC_REF_VDD_VREFMINUS 		// VREF- = external

#endif

#endif
