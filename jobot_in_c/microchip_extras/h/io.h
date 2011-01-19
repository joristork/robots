/******************************************************************************
 // *                   IO PERIPHERAL LIBRARY HEADER FILE
 ******************************************************************************
 * FileName:        		io.h
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
/*THIS IO.H FILE WILL THE ATTACHED TO PCONFIG.H FILE */

#ifndef _IO_H
#define _IO_H

#if !defined (__18C601) && !defined (__18C801)
#define BOR_V1
#endif

#if !defined (__18F24J10) && !defined (__18F25J10) && !defined (__18F44J10) &&\
    !defined (__18F45J10) && !defined (__18F65J10) && !defined (__18F65J15) &&\
    !defined (__18F66J10) && !defined (__18F66J15) && !defined (__18F66J60) &&\
	!defined (__18F66J65) && !defined (__18F67J10) && !defined (__18F67J60) &&\
    !defined (__18F85J10) && !defined (__18F85J15) && !defined (__18F86J10) &&\
	!defined (__18F86J15) && !defined (__18F86J60) && !defined (__18F86J65) &&\
    !defined (__18F87J10) && !defined (__18F87J60) && !defined (__18F96J60) &&\
	!defined (__18F96J65) && !defined (__18F97J60) && !defined (__18F96J65) &&\
    !defined (__18LF13K50)&& !defined (__18LF14K50)&& !defined (__18F13K50) &&\
	!defined (__18F14K50) && !defined (__18F13K22) && !defined (__18F14K22) &&\
	!defined (__18LF13K22)&& !defined (__18LF14K22) && !defined (__MCV20USB)
#define LVD_V1
#endif	

#if defined(__18C242) || defined(__18C252) || defined(__18C442) ||\
    defined(__18C452) || defined(__18C658)  || defined(__18C858)||\
    defined(__18F1220) || defined(__18F1320) || defined(__18F242)||\
	defined(__18F2439) || defined(__18F2455) || defined(__18F248)  ||\
    defined(__18F252)  || defined(__18F2539) || defined(__18F2550) ||\
	defined(__18F258)  || defined(__18F442)  || defined(__18F4439) ||\
    defined(__18F4455) || defined(__18F448)  || defined(__18F452)  ||\
	defined(__18F4539) || defined(__18F4550) || defined(__18F458)  ||\
	defined(__18F2458) || defined(__18F2553) || defined(__18F4458) ||\
	defined(__18F4553) || defined(__18F24J50) || defined(__18F25J50) ||\
	defined(__18F26J50) || defined(__18F44J50) || defined(__18F45J50) ||\
	defined(__18F46J50) || defined(__18F25J11) || defined(__18F24J11) ||\
	defined(__18F26J11) || defined(__18F45J11) || defined(__18F44J11) ||\
    defined(__18F46J11) || defined(__18LF24J50) || defined(__18LF25J50) ||\
	defined(__18LF26J50) || defined(__18LF44J50) || defined(__18LF45J50)||\
	defined(__18LF46J50) || defined(__18LF25J11) || defined(__18LF24J11)||\
	defined(__18LF26J11) || defined(__18LF45J11) || defined(__18LF44J11)||\
	defined(__18LF46J11) || defined(__18F66J90) || defined(__18F67J90)||\
	defined(__18F86J90) || defined(__18F87J90) || defined(__18F66J93) || \
    defined(__18F67J93)|| defined(__18F86J93) || defined(__18F87J93) || \
    defined(__18F86J72)|| defined(__18F87J72)
#define STKOVF STKFUL
    #if defined(__18F45J11) || defined(__18F44J11) ||defined(__18F46J11) || \
	defined(__18LF24J50) || defined(__18LF25J50) ||\
	defined(__18LF26J50) || defined(__18LF44J50) || defined(__18LF45J50)||\
	defined(__18LF46J50)|| defined(__18LF25J11) || defined(__18LF24J11)||\
	defined(__18LF26J11) || defined(__18LF45J11) || defined(__18LF44J11)||\
	defined(__18LF46J11)
		#define I2C_SFR_V1
	#endif
#endif

#if defined (__18F1220) || defined (__18F1320)
#define  USART_IO_V1
#define  USART_SFR_V1
#define  SW_I2C_IO_V1
#define  CC1_IO_V1
#endif

#if defined(__18F1230) || defined(__18F1330) 
#define  USART_IO_V2
#define  SW_I2C_IO_V1
#endif

#if defined (__18C601) || defined (__18C801)
#define  PWM2_IO_V7
#define  CC2_IO_V1
#define  I2C_IO_V1
#define  SPI_IO_V1
#endif

#if defined (__18C658) || defined (__18C858) 
#define  PWM2_IO_V8
#define  I2C_IO_V1
#define  SPI_IO_V1
#endif

#if defined (__18C242) || defined (__18C252) || defined (__18C442) ||\
    defined (__18C452) || defined (__18F242) || defined (__18F252) ||\
    defined (__18F442) || defined (__18F452) || defined (__18F2220) ||\
    defined (__18F2221) || defined (__18F2320)|| defined (__18F2321)||\
	defined (__18F2410) ||defined (__18F2420) || defined (__18F2423) ||\
	defined (__18F2510) || defined (__18F2515)|| defined (__18F2520) ||\
	defined (__18F2523) || defined (__18F2525)|| defined (__18F2610) ||\
	defined (__18F2620) 	 
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V1
#define  SPI_IO_V1
#endif

#if defined (__18F2331) || defined (__18F2431) || defined (__18F4331) || \
    defined (__18F4431) 
#define  CC2_IO_V0
#define  I2C_IO_V2
#define  SPI_IO_V5
#endif

#if defined(__18F2455) || defined(__18F2458) || defined(__18F2550) ||\
    defined(__18F2553)
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V3
#define  SPI_IO_V3
#define  MWIRE_IO_V1 
#define  SW_I2C_IO_V2
#endif

#if defined (__18F24J10) ||  defined (__18F25J10)
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V5
#define  SPI_IO_V1
#endif

#if defined (__18F248) || defined (__18F258) || defined (__18F448) ||\
    defined (__18F458) || defined (__18F2480) ||defined (__18F2580) ||\
	defined (__18F2585)|| defined (__18F2680) ||defined (__18F2682) ||\
	defined (__18F2685)|| defined (__18F4480) ||defined (__18F4580) ||\
	defined (__18F4585)|| defined (__18F4680) ||defined (__18F4682) ||\
	defined (__18F4685)|| defined (__18F2439) || defined (__18F2539) ||\
	defined (__18F4439) ||defined (__18F4539) 
#define  I2C_IO_V1 
#define  SPI_IO_V1
#if defined (__18F2439) || defined (__18F2539) || defined (__18F4439) ||\
	defined (__18F4539)
#define PROMPT_V1
#endif
#endif

#if defined(__18F24J50) || defined(__18F25J50) || defined(__18F26J50) ||\
    defined(__18F44J50) || defined(__18F45J50) || defined(__18F46J50) || \
	defined(__18LF24J50) || defined(__18LF25J50) || defined(__18LF26J50) ||\
    defined(__18LF44J50) || defined(__18LF45J50) || defined(__18LF46J50) || \
	defined(__18F26J53) || defined(__18F27J53) || defined(__18F46J53) || defined(__18F47J53) ||\
	defined(__18LF26J53) || defined(__18LF27J53) || defined(__18LF46J53) || defined(__18LF47J53) ||\
	defined(__18F26J13) || defined(__18F27J13) || defined(__18F46J13) || defined(__18F47J13) ||\
	defined(__18LF26J13) || defined(__18LF27J13) || defined(__18LF46J13) || defined(__18LF47J13)	
#define SW_I2C_IO_V3
#define MWIRE_IO_V3
#define MWIRE_IO_V4
#if defined(__18F44J50) || defined(__18F45J50) || defined(__18F46J50) ||\
	defined(__18F26J53) || defined(__18F27J53) || defined(__18F46J53) || defined(__18F47J53) ||\
	defined(__18LF26J53) || defined(__18LF27J53) || defined(__18LF46J53) || defined(__18LF47J53) ||\
	defined(__18F26J13) || defined(__18F27J13) || defined(__18F46J13) || defined(__18F47J13) ||\
	defined(__18LF26J13) || defined(__18LF27J13) || defined(__18LF46J13) || defined(__18LF47J13)	
#define I2C_SFR_V1
#endif
#endif

#if defined(__18F26J53) || defined(__18F27J53) || defined(__18F46J53) || defined(__18F47J53) ||\
	defined(__18LF26J53) || defined(__18LF27J53) || defined(__18LF46J53) || defined(__18LF47J53)||\
	defined(__18F26J13) || defined(__18F27J13) || defined(__18F46J13) || defined(__18F47J13) ||\
	defined(__18LF26J13) || defined(__18LF27J13) || defined(__18LF46J13) || defined(__18LF47J13)	
#define CC4_IO_V2
#endif

#if defined(__18F25J11) || defined(__18F24J11) || defined(__18F26J11) ||\
	defined(__18F45J11) || defined(__18F44J11) || defined(__18F46J11) ||\
	defined(__18LF25J11) || defined(__18LF24J11)|| defined(__18LF26J11) ||\
	defined(__18LF45J11) || defined(__18LF44J11)|| defined(__18LF46J11)
	#define MWIRE_IO_V4
#endif	
	
#if defined(__18F2450) || defined(__18F4450) 
#define  SW_I2C_IO_V2
#endif

#if defined (__18F4220) || defined (__18F4221) || defined (__18F4320) ||\
    defined (__18F4321) || defined (__18F4410) || defined (__18F4420) ||\
	defined (__18F4423) || defined (__18F4510) || defined (__18F4515) ||\
	defined (__18F4520) || defined (__18F4523) || defined (__18F4525) ||\
	defined (__18F4610) || defined (__18F4620) 	
#define  PWM1_IO_V1
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V1 
#define  SPI_IO_V1
#endif

#if defined(__18F4455) || defined(__18F4458) || defined(__18F4550) ||\
    defined(__18F4553)	
#define  PWM1_IO_V1
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V3
#define  SPI_IO_V3
#define	 MWIRE_IO_V1 
#define  SW_I2C_IO_V2
#endif

#if defined(__18F44J10)|| defined(__18F45J10) 
#define  PWM1_IO_V1
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V5
#define  I2C_IO_V6
#define  SPI_IO_V1
#define  SPI_IO_V8
#define	 MWIRE_IO_V2 
#endif

#if	defined(__18F6310) || defined(__18F6410) 
#define PWM2_IO_V2
#define PWM_CONFIG3L_V1  
#define CC2_IO_V4
#define CC_CONFIG3L_V1 
#define I2C_IO_V1 
#define SPI_IO_V2
#endif

#if defined (__18F6525) ||defined (__18F6621)
#define  PWM1_IO_V2
#define  PWM2_IO_V5
#define  PWM3_IO_V2
#define  CC2_IO_V4
#define  CC_CONFIG3L_V1
#define  I2C_IO_V1
#define  SPI_IO_V2
#endif

#if	defined(__18F6527) || defined(__18F6622) || defined(__18F6627) ||\
    defined(__18F6722) 
#define  PWM1_IO_V2
#define  PWM2_IO_V5
#define  PWM3_IO_V2
#define  CC2_IO_V4
#define  CC_CONFIG3L_V1
#define  I2C_IO_V5
#define  I2C_IO_V7
#define  SPI_IO_V2
#define  SPI_IO_V7
#endif 

#if defined (__18F6585) || defined (__18F6680) 
#define  PWM1_IO_V2
#define  PWM2_IO_V2
#define  CC2_IO_V4
#define  I2C_IO_V1
#define  SPI_IO_V2 
#endif 

#if   defined (__18F63J11) || defined (__18F64J11) || defined (__18F65J11)     
#define  PWM2_IO_V2
#define  CC2_IO_V4
#define  I2C_IO_V1 
#define  SPI_IO_V4
#endif

#if defined (__18F65J10) || defined (__18F65J15) ||	defined (__18F66J10) ||\
    defined (__18F66J15) || defined (__18F67J10) || defined(__18F65J50) ||\
	defined(__18F66J50) || defined(__18F66J55) || defined(__18F67J50) ||\
	defined(__18F66J11) || defined(__18F66J16) || defined(__18F67J11)
#define  PWM1_IO_V2
#define  PWM2_IO_V5
#define  PWM3_IO_V2
#define  CC2_IO_V4
#define  CC_CONFIG3L_V2
#define  I2C_IO_V5
#define  I2C_IO_V7
#define  SPI_IO_V2
#define  SPI_IO_V7
#if defined(__18F66J11) || defined(__18F66J16) || defined(__18F67J11) 
#define  ANCOMP_IO_V1
#endif
#if defined(__18F65J50) || defined(__18F66J50) || defined(__18F66J55)||\
    defined(__18F67J50)
#define  ANCOMP_IO_V2
#endif
#endif

#if defined (__18F66J60) || defined (__18F66J65) || defined (__18F67J60)
#define  PWM1_IO_V3
#define  PWM2_IO_V3
#define  PWM3_IO_V1
#define  PWM4_IO_V1
#define  CC2_IO_V0
#define  CC3_IO_V1
#define  CC4_IO_V1
#define  CC_CONFIG3L_V2
#define  I2C_IO_V5
#define  SPI_IO_V2
#endif

#if defined (__18F6393)|| defined (__18F6493) || defined (__18F8393) ||\
    defined (__18F8493)|| defined (__18F67J90)|| defined (__18F66J90)  	
#define  PWM2_IO_V2
#define  I2C_IO_V1 
#endif	

#if defined (__18F6390) || defined (__18F6490) || defined (__18F6520) || \
    defined (__18F6620) || defined (__18F6720) || defined (__18F8390) || \
	defined (__18F8490) || defined (__18F63J90) || defined (__18F64J90) ||\
	defined (__18F65J90)|| defined (__18F83J90) || defined (__18F84J90) ||\
	defined (__18F85J90)|| defined (__18F67J90)|| defined (__18F66J90)||\
	defined (__18F87J90) || defined (__18F86J90)|| defined (__18F67J93)|| \
    defined (__18F66J93)|| defined (__18F87J93) || defined (__18F86J93) || \
    defined (__18F86J72)|| defined (__18F87J72)
#define  PWM2_IO_V2
#define  I2C_IO_V1 	
#define  CC2_IO_V4
#define  SPI_IO_V2
#endif

#if defined (__18F6628)  || defined (__18F6723) || defined (__18F8628) ||\
    defined (__18F8723)
#define I2C_IO_V5
#define I2C_IO_V7
#endif	

#if defined (__18F8520) || defined (__18F8620) || defined (__18F8720)||\
    defined(__18F8310) || defined(__18F8410)     
#define  PWM2_IO_V4
#define  CC2_IO_V5
#define  I2C_IO_V1 
#define  SPI_IO_V2
#if defined(__18F8310) || defined(__18F8410) 
#define  CC_CONFIG3L_V1 
#define  PWM_CONFIG3L_V1 
#endif
#endif

#if defined(__18F8527) || defined(__18F8622) || defined(__18F8627) ||\
    defined(__18F8722) || defined (__18F8525) ||defined (__18F8621)
#define  PWM1_IO_V4
#define  PWM2_IO_V6
#define  PWM3_IO_V3
#define  CC_CONFIG3L_V1 
#define  CC2_IO_V5
#define  SPI_IO_V2
#if defined (__18F8525) ||defined (__18F8621) 
#define  I2C_IO_V1
#endif
#if defined(__18F8527) || defined(__18F8622) || defined(__18F8627) ||\
    defined(__18F8722)
#define  I2C_IO_V5
#define  I2C_IO_V7
#define  SPI_IO_V7
#endif
#endif	

#if defined (__18F85J10) || defined (__18F85J15) || defined (__18F85J50) || \
    defined (__18F86J10) || defined (__18F86J11) || defined (__18F86J15) || \
	defined (__18F86J16) || defined (__18F86J50) || defined (__18F86J55) ||\
	defined (__18F87J10) || defined (__18F87J11) || defined (__18F87J50) ||\
	defined (__18F97J60) 	
#define  PWM1_IO_V4
#define  PWM2_IO_V6
#define  PWM3_IO_V3
#define  CC2_IO_V5
#define  CC_CONFIG3L_V2
#define  I2C_IO_V5
#define  I2C_IO_V7
#define  SPI_IO_V2
#define  SPI_IO_V7
#endif

#if defined (__18F8585) || defined (__18F8680)
#define  PWM1_IO_V4
#define  PWM2_IO_V4
#define  CC2_IO_V5
#define  I2C_IO_V1
#define  SPI_IO_V2 
#endif

#if defined(__18F83J11) || defined(__18F84J11) || defined(__18F85J11)
#define  PWM2_IO_V4
#define  PWM_CONFIG3L_V2
#define  CC2_IO_V5
#define  CC_CONFIG3L_V2
#define  I2C_IO_V1 
#define  SPI_IO_V4
#endif

#if defined (__18F86J60) || defined (__18F86J65) || defined (__18F87J60)
#define  PWM1_IO_V4 
#define  PWM2_IO_V5
#define  PWM3_IO_V3
#define  CC2_IO_V4
#define  CC_CONFIG3L_V2
#define  I2C_IO_V5
#define  SPI_IO_V2
#endif

#if defined (__18F96J60) ||defined (__18F96J65)
#define  PWM1_IO_V4
#define  PWM2_IO_V6
#define  PWM3_IO_V3
#define  CC2_IO_V5
#define  CC_CONFIG3L_V2
#define  I2C_IO_V5
#define  I2C_IO_V7
#define  SPI_IO_V2
#define  SPI_IO_V7
#endif

#if defined (__18F13K22) || defined (__18F14K22) || defined (__18LF13K22) ||\
    defined (__18LF14K22) || defined (__18F13K50) || defined (__18F14K50) ||\
	defined (__18LF13K50) || defined (__18LF14K50) || defined (__MCV20USB)
#define  PWM1_IO_V6
#define  CC1_IO_V2
#define  CC2_IO_V3
#define  I2C_IO_V4	
#define  SPI_IO_V9
#define  SW_SPI_IO_V1
#define  USART_IO_V3
#if defined (__18F13K50) || defined (__18F14K50) ||defined (__18LF13K50) ||\
    defined (__18LF14K50) || defined (__MCV20USB)
#define  USART_SFR_V1
#endif
#endif

#if defined (__18F23K20) || defined (__18F24K20) || defined (__18F25K20) ||\
    defined (__18F26K20) || defined (__18F43K20) || defined (__18F44K20) ||\
    defined (__18F45K20) || defined (__18F46K20)
#define  PWM1_IO_V5
#define  PWM2_IO_V1
#define  CC2_IO_V3
#define  I2C_IO_V1 
#define  SPI_IO_V6
#endif

#if defined (__18F23K22) || defined (__18F24K22) || defined (__18F25K22) ||\
    defined (__18F26K22) || defined (__18F43K22) || defined (__18F44K22) ||\
    defined (__18F45K22) || defined (__18F46K22) ||\
	defined (__18LF23K22) || defined (__18LF24K22) || defined (__18LF25K22) ||\
    defined (__18LF26K22) || defined (__18LF43K22) || defined (__18LF44K22) ||\
    defined (__18LF45K22) || defined (__18LF46K22)
#define  TMR_INT_V7_3
#define  CC9_IO_V1
#define  ECCP1CON	CCP1CON
#define  ECCP2CON	CCP2CON
#endif

#if defined (__18F87K90) || defined (__18F86K95) || defined (__18F86K90) ||\
    defined (__18F85K90) || defined (__18F87K22) || defined (__18F86K27) || defined (__18F86K22) ||\
    defined (__18F85K22)
#define  PWM_V14_IO_V1
#define I2C_SFR_V1
#define RTCC_SFR_V1
#define ANCOM_V10_IO_V1
#endif

#if defined(__18F26J53) || defined(__18F27J53) || defined(__18F46J53) || defined(__18F47J53) ||\
	defined(__18LF26J53) || defined(__18LF27J53) || defined(__18LF46J53) || defined(__18LF47J53)||\
	defined(__18F26J13) || defined(__18F27J13) || defined(__18F46J13) || defined(__18F47J13) ||\
	defined(__18LF26J13) || defined(__18LF27J13) || defined(__18LF46J13) || defined(__18LF47J13)	
#define  TMR_V7_1_INT_V1
#define  STK_SFR_V1
#endif

#if defined (__18F67K90) || defined (__18F66K95) ||\
    defined (__18F66K90) || defined (__18F65K90) || defined (__18F67K22) || defined (__18F66K27) ||\
    defined (__18F66K22) || defined (__18F65K22)
#define  PWM_V14_IO_V2
#define I2C_SFR_V1
#define RTCC_SFR_V1
#endif	

#if defined (__18F66K80) || defined (__18F65K80) ||\
    defined (__18LF66K80) || defined (__18LF65K80) || defined (__18F46K80) || defined (__18LF46K80) ||\
    defined (__18F45K80) || defined (__18LF45K80) || defined (__18F26K80) || defined (__18LF26K80) \
	|| defined (__18F25K80) || defined (__18LF25K80)
	
#define 	INTCONbits	INTCON1bits
#define 	INTCON		INTCON1

#define  I2C_IO_V1
#define  SPI_IO_V1

#if defined (__18F66K80) || defined (__18F65K80) ||\
    defined (__18LF66K80) || defined (__18LF65K80) ||\
	defined (__18F46K80) || defined (__18LF46K80) ||\
    defined (__18F45K80) || defined (__18LF45K80)
#define CC8_IO_V2
#define PWM14_2_IO_V2
#elif defined (__18F26K80) || defined (__18LF26K80) \
		|| defined (__18F25K80) || defined (__18LF25K80)
#define CC8_IO_V1	
#define PWM14_2_IO_V1	
#endif
#endif	

#endif
