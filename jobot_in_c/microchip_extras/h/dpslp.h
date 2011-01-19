#ifndef __DPSLP_H
#define __DPSLP_H
 /******************************************************************************
 *
 *                  DEEP SLEEP PERIPHERAL LIBRARY HEADER FILE
 *
 ******************************************************************************
 * FileName:        		dpslp.h
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
 *
 *****************************************************************************/

#include "pconfig.h"
#include "pps.h"



#if defined(DPSLP_V1_1) 

typedef enum _BOOL { FALSE = 0, TRUE } BOOL;	// Undefined size

//Status of deep sleep wake up
typedef union 
{ 
    struct
    {
        unsigned char    DS_POR:1;      //wake up source is Power on reset 
        unsigned char    DS_MCLR:1;		//wake up source is master reset pin set to low
        unsigned char    DS_RTC:1;		//wake up source is rtcc alarm
        unsigned char    DS_WDT:1;		//wake up source is deep sleep watch dog timer expiry
		unsigned char    DS_FLT:1;		//wake up due to fault in deep sleep configuration corruption
		unsigned char    DS_INT0:1;		//wake up source is external interrupt
		unsigned char    DS_BOR:1;		//Brown out occured during deep sleep
		unsigned char    DS_ULP:1;		//wake up source is ultra low power wake up.
    }WK_SRC;                     		// wake up source
	
    unsigned char        WKSRC;       	 // wake up source

}SRC;

//context to be saved or read in deep sleep
typedef struct 
{
		unsigned char	Reg0;			//Stores the DSGPR0 register contents
		unsigned char	Reg1;			//Stores the DSGPR0 register contents
}CONTEXT;

#define DPSLP_RTCC_WAKEUP_ENABLE        0x0000 /*RTCC wake up enabled */
#define DPSLP_RTCC_WAKEUP_DISABLE       0x0001 /*RTCC wake up disabled */

#define DPSLP_ULPWU_ENABLE		        0x0002 /*Enable Ultra Low Power Wake Up in deep sleep*/
#define DPSLP_ULPWU_DISABLE	       		0x0000 /*Disable Ultra Low Power Wake Up in deep sleep*/
	
	
/**********************************************************************
 Macro       : Write_DSGPR(data1,data2)

 Include     : dpslp.h
 
 Description : This macro saves the context into DSGPRx registers
 
 Arguments   : integer values to be saved in DSGPRx registers.
 
 Remarks     : None
**********************************************************************/                        
#define Write_DSGPR(data1,data2)          DSGPR0=data1;DSGPR1=data2

/**********************************************************************
 Macro       : ReleaseDeepSleep()

 Include     : dpslp.h
 
 Description : This macro clears the RELEASE bit in DSCON thus rleasing device from deep sleep 
 
 Arguments   : None.
 
 Remarks     : None
**********************************************************************/                        
#define ReleaseDeepSleep()          DSCONLbits.RELEASE=0;Nop();Nop()

extern void DeepSleepWakeUpSource(SRC* ptr);
extern void GotoDeepSleep( unsigned int config);
extern unsigned char IsResetFromDeepSleep( void );
extern void ReadDSGPR( CONTEXT* ptr );
#endif

 #if defined(DPSLP_V1_1)|| defined(DPSLP_V1_2)
extern void ULPWakeUpEnable( void );
//#else		//This preprocessor conditional statement is to avoid unintended linking for unsuppported devices.
//#error "Selected device does not support this Module"
#endif

#endif /* __DPSLP_H */

