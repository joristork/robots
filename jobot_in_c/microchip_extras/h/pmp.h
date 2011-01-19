#ifndef PMP_H
#define PMP_H

/******************************************************************************
 *
 *                  PMP PERIPHERAL LIBRARY HEADER FILE
 *
 ******************************************************************************
 * FileName:        pmp.h
 * Dependencies:    See include below
 * Processor:       PIC18
 * Compiler:        MCC18
 * Company:         Microchip Technology, Inc.
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
 
#include "GenericTypeDefs.h"
#include "pconfig.h"

//This preprocessor conditional statement is to avoid unintended linking for unsuppported devices.
#if defined (pmp_v1_1) || defined (pmp_v1_2) || defined (pmp_v1_3) 

/*****************************************************************************/
/*Section : ENUMERATIONS */
/*****************************************************************************/
//Store result
typedef enum 
{
    buffer0, //buffer 0
    buffer1, //buffer 1
    buffer2, //buffer 2
    buffer3 //buffer 3
}BUFFER ;


/******************************************************************************
 * HOWTO Use the PIC18PMPLib Bit Masks
 *
 * The configuration of the PMP peripheral is highly dependent on its use in
 * the target application and should be configured appropriately to interface
 * correctly to the external device.  Setting these bits are typically done only
 * once and seldom changed afterwards.
 * To help simplify the process of configuring these registers, this header file
 * includes "configuration" bit masks below which are designed to be bitwise "|"
 * (OR'd) together to form the desired configuration mask for each register.
 * These OR'd masks can then be written directly to the PMCON, PMMODE and PMPEN
 * registers or used as arguments to the PMPOpen() library function.
 * See examples in the "configuration bit mask section" below.
 *
 * A second method of configuring the PMP peripheral registers is to use the PMP
 * register structures typedefs provided at the begining of this header file.
 * Each register structure can be popluated with the desired values and passed 
 * as an argument to the PMPOpen() library function.
 */
 


 #if defined (pmp_v1_1) || defined (pmp_v1_2) || defined (pmp_v1_3) 
/*****************************************************************************/
/* PMCON Port Control Register Configuration Bit Definitions*/
/*****************************************************************************/
/* PMPEN BIT configuration bit mask defines */
#define BIT_PMP_ON                  0x8000 /* Configure PMP enabled */
#define BIT_PMP_OFF                 0x0000 /* Configure PMP disabled */
#define BIT_PMP_MASK                (~BIT_PMP_ON)	//Mask PMP enable/disable bit

 #if defined (pmp_v1_1) || defined (pmp_v1_2)
/* STOP DURING IDLE BIT configuration bit mask defines */
#define BIT_SIDL_ON                 0x2000 /* Configure Stop in IDLE mode ON */
#define BIT_SIDL_OFF                0x0000 /* Configure Stop in IDLE mode OFF */
#define BIT_SIDL_MASK               (~BIT_SIDL_ON)	//Mask Stop in Idle Mode bit
#endif

/* ADDRESS MULTIPLEXING BITS configuration bit mask defines */
/* note: the value 0x1800 is reserved */
#define BIT_ADDR_MUX_16_8           0x1000 /* Configure 10 = All 16-bits addrs muxed on 8-bit data */
#define BIT_ADDR_MUX_8_8            0x0800 /* Configure 01 = Lower 8-bits addrs muxed on 8-bit data */
#define BIT_ADDR_MUX_NONE           0x0000 /* Configure 00 = Addrs and data on separate pins */
#define BIT_ADDR_MUX_MASK           (~(BIT_ADDR_MUX_16_8 | BIT_ADDR_MUX_8_8))	//Mask Address/Data Multiplexing Selection bits


/* BYTE ENABLE BIT configuration bit mask defines */
#define BIT_BE_ON                   0x0400 /* Configure ByteEnable port bit ON */
#define BIT_BE_OFF                  0x0000 /* Configure ByteEnable port bit OFF */
#define BIT_BE_MASK                 (~BIT_BE_ON)	//Mask Byte Enable Port Enable bit

/* RD and WR STROBE FUNCTION BIT configuration bit mask defines */
#define BIT_WR_ON                   0x0200 /* Configure WR, WR/ENB strobe = ON */
#define BIT_WR_OFF                  0x0000 /* Configure WR, WR/ENB strobe = ON */

#define BIT_RD_ON                   0x0100 /* Configure RD, RD/WR strobe = ON */
#define BIT_RD_OFF                  0x0000 /* Configure RD, RD/WR strobe = ON */

#define BIT_RD_WR_ON                0x0300 /* Configure RD, RD/WR strobe = ON; WR, WR/ENB strobe = ON */
#define BIT_RD_WR_OFF               0x0000 /* Configure RD, RD/WR strobe = OFF; WR, WR/ENB strobe = OFF */
#define BIT_RD_WR_MASK              (~BIT_RD_WR_ON)	//Mask Read/Write Enable Strobe Port Enable bit

/* CS FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_USE_CS2_CS1             0x0080 /* Configure 10 = CS1 and CS2 are chip selects */
#define BIT_USE_CS_OFF              0x0000 /* Configure 00 = CS2 = A15, CS1 = A14 */
#define BIT_CS_FUNC_MASK            (~(0x00C0))	//Mask Chip Select Function bits


/* AL BIT configuration bit mask defines */
#define BIT_LATCH_HI                0x0020 /* Configure PMALL, PMALH = active high */
#define BIT_LATCH_LO                0x0000 /* Configure PMALL, PMALH = active low */
#define BIT_LATCH_MASK              (~BIT_LATCH_HI)	//Mask Address Latch Polarity bit

#if defined (pmp_v1_1)
#define BIT_CS2_HI                  0x0010 /* Configure CS2 polarity = active high */
#define BIT_CS2_LO                  0x0000 /* Configure CS2 polarity = active low */
#define BIT_CS2_MASK               (~BIT_CS2_HI)	//Mask Chip Select Polarity bit
#endif

/* CS POLARITY SELECTION BITS configuration bit mask defines */
#define BIT_CS1_HI                  0x0008 /* Configure CS1 polarity = active high */
#define BIT_CS1_LO                  0x0000 /* Configure CS1 polarity = active low */
#define BIT_CS1_MASK                (~BIT_CS1_HI)	//Mask Chip Select Polarity bit

/* BE POLARITY BIT configuration bit mask defines */
#define BIT_BE_HI                   0x0004 /* Configure Byte enable polarity = active high */
#define BIT_BE_LO                   0x0000 /* Configure Byte enable polarity = active low */
#define BIT_BE_POLAR_MASK           (~BIT_BE_HI)	//Mask Byte Enable Polarity bit

/* RD/RW/WR/ENB POLARITY BIT configuration bit mask defines */
#define BIT_WR_HI                   0x0002 /* Configure Write/ENB strobe polarity = active high */
#define BIT_WR_LO                   0x0000 /* Configure Write/ENB strobe polarity = active low */
#define BIT_WR_MASK                 (~BIT_WR_HI)	//Mask Write Strobe Polarity bit

#define BIT_RD_HI                   0x0001 /* Configure Read/RW strobe polarity = active high */
#define BIT_RD_LO                   0x0000 /* Configure Read/RW strobe polarity = active low */
#define BIT_RD_MASK                 (~BIT_RD_HI)	//Mask Read Strobe Polarity bit

#define BIT_RD_WR_HI                0x0003 /* Configure Write/ENB and Read/RW polarity = active high */
#define BIT_RD_WR_LO                0x0000 /* Configure Write/ENB and Read/RW polarity = active low */
#define BIT_RD_WR_POLAR_MASK        (~BIT_RD_WR_HI)	//Mask Read/Write Strobe Polarity bit

// #define BIT_ALL_POLAR_MASK          (~(BIT_LATCH_HI | BIT_CS1_HI | BIT_BE_HI | BIT_WR_HI | BIT_RD_HI))

/*****************************************************************************/
/* PMMODE Port Mode Register Configuration Bit Definitions */
/*****************************************************************************/
/* IRQM FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_IRQ_BUF                 0x6000 /* Configure Interrupt generated on R/W buffer 3 */
#define BIT_IRQ_RW                  0x2000 /* Configure Interrupt at end of R/W cycle */
#define BIT_IRQ_NONE                0x0000 /* Configure No interrupt generated */
#define BIT_IRQ_MASK                (~BIT_IRQ_BUF)	//Mask Interrupt Request Mode bits

/* INCM FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_INC_ADDR_AUTO           0x1800 /* Configure 11 = SLAVE R/W buffers auto increment */
#define BIT_INC_ADDR_DEC            0x1000 /* Configure 10 = Addrs decrements on every R/W cycle */
#define BIT_INC_ADDR_INC            0x0800 /* Configure 01 = Addrs increments on every R/W cycle */
#define BIT_INC_ADDR_NONE           0x0000 /* Configure 00 = No addrs increment/decrement */
#define BIT_INC_MASK                (~BIT_INC_ADDR_AUTO)	//Mask Increment Mode bits

/* MODE16 FUNCTION BITS configuration bit mask defines */
#define BIT_DATA_16                 0x0400 /* Configure 16-bit data mode */
#define BIT_DATA_8                  0x0000 /* Configure 8-bit data mode */
#define BIT_DATA_MASK               (~BIT_DATA_16)	//Mask 8/16-Bit Mode bit

/* MODE FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_MODE_MASTER_1           0x0300 /* Configure MASTER mode 1 */
#define BIT_MODE_MASTER_2           0x0200 /* Configure MASTER modw 2 */
#define BIT_MODE_SLAVE_ENH          0x0100 /* Configure SLAVE enhanced mode */
#define BIT_MODE_SLAVE              0x0000 /* Configure SLAVE mode */
#define BIT_MODE_MASK               (~BIT_MODE_MASTER_1)	//Mask Parallel Port Mode Select bits

/* WAITB FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_WAITB_4_TCY             0x00C0 /* Configure 4 Tcy WAIT */
#define BIT_WAITB_3_TCY             0x0080 /* Configure 3 Tcy WAIT */
#define BIT_WAITB_2_TCY             0x0040 /* Configure 2 Tcy WAIT */
#define BIT_WAITB_1_TCY             0x0000 /* Configure 1 Tcy WAIT */
#define BIT_WAITB_MASK              (~BIT_WAITB_4_TCY)	//Mask Data Setup to Read/Write Wait State Configuration bits

/* WAITM FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_WAITM_15_TCY            0x003C /* Configure 15 Tcy WAIT */
#define BIT_WAITM_14_TCY            0x0038 /* Configure 14 Tcy WAIT */
#define BIT_WAITM_13_TCY            0x0034 /* Configure 13 Tcy WAIT */
#define BIT_WAITM_12_TCY            0x0030 /* Configure 12 Tcy WAIT */
#define BIT_WAITM_11_TCY            0x002C /* Configure 11 Tcy WAIT */
#define BIT_WAITM_10_TCY            0x0028 /* Configure 10 Tcy WAIT */
#define BIT_WAITM_9_TCY             0x0024 /* Configure 9 Tcy WAIT */
#define BIT_WAITM_8_TCY             0x0020 /* Configure 8 Tcy WAIT */
#define BIT_WAITM_7_TCY             0x001C /* Configure 7 Tcy WAIT */
#define BIT_WAITM_6_TCY             0x0018 /* Configure 6 Tcy WAIT */
#define BIT_WAITM_5_TCY             0x0014 /* Configure 5 Tcy WAIT */
#define BIT_WAITM_4_TCY             0x0010 /* Configure 4 Tcy WAIT */
#define BIT_WAITM_3_TCY             0x000C /* Configure 3 Tcy WAIT */
#define BIT_WAITM_2_TCY             0x0008 /* Configure 2 Tcy WAIT */
#define BIT_WAITM_1_TCY             0x0004 /* Configure 1 Tcy WAIT */
#define BIT_WAITM_0_TCY             0x0000 /* Configure 0 Tcy WAIT */
#define BIT_WAITM_MASK              (~BIT_WAITM_15_TCY)	//Mask Read to Byte Enable Strobe Wait State Configuration bits

/* WAITE FUNCTION SELECTION BITS configuration bit mask defines */
#define BIT_WAITE_4_TCY             0x0003 /* Configure 4 Tcy WAIT */
#define BIT_WAITE_3_TCY             0x0002 /* Configure 3 Tcy WAIT */
#define BIT_WAITE_2_TCY             0x0001 /* Configure 2 Tcy WAIT */
#define BIT_WAITE_1_TCY             0x0000 /* Configure 1 Tcy WAIT */
#define BIT_WAITE_MASK              (~BIT_WAITE_4_TCY)	//Mask Data Hold After Strobe Wait State Configuration bits

#define BIT_WAIT_ALL_MASK           (BIT_WAITB_MASK & BIT_WAITM_MASK & BIT_WAITE_MASK)	//Mask All wait state configuration bits

/*****************************************************************************/
/* PMADDR Parallel Port Address Register Configuration Bit Definitions */
/*****************************************************************************/



/* CS ENABLE BITS configuration bit mask defines */
#define BIT_CS1_ON                  0x4000 /* Configure CS1 enabled as chip select */
#define BIT_CS1_OFF                 0x0000 /* Configure CS1 disabled as chip select */
#define BIT_CS1_POL_MASK            (~BIT_CS1_ON)	//Maks PMCS1 Port Enable bits

#define BIT_A13                     0x2000 /*Bit Position 13*/
#define BIT_A12                     0x1000 /*Bit Position 12*/
#define BIT_A11                     0x0800 /*Bit Position 11*/
#define BIT_A10                     0x0400 /*Bit Position 10*/
#define BIT_A9                      0x0200 /*Bit Position 9*/
#define BIT_A8                      0x0100 /*Bit Position 8*/
#define BIT_A7                      0x0080 /*Bit Position 7*/
#define BIT_A6                      0x0040 /*Bit Position 6*/
#define BIT_A5                      0x0020 /*Bit Position 5*/
#define BIT_A4                      0x0010 /*Bit Position 4*/
#define BIT_A3                      0x0008 /*Bit Position 3*/
#define BIT_A2                      0x0004 /*Bit Position 2*/
#define BIT_A1                      0x0002 /*Bit Position 1*/
#define BIT_A0                      0x0000 /*Bit Position 0*/



/*****************************************************************************/
/* PMAEN Parallel Port Enable Register Configuration Bit Definitions */
/*****************************************************************************/
/* PTEN ENABLE SELECTION BITS configuration bit mask defines */
#if defined (pmp_v1_2) || defined (pmp_v1_3)
#define BIT_P15                     0x8000 /* Configure PTEN15 as address*/
#define BIT_P13                     0x2000 /* Configure PTEN13 as address*/
#define BIT_P12                     0x1000 /* Configure PTEN12 as address*/
#define BIT_P11                     0x0800 /* Configure PTEN11 as address*/
#define BIT_P10                     0x0400 /* Configure PTEN10 as address*/
#define BIT_P9                      0x0200 /* Configure PTEN9 as address*/
#define BIT_P8                      0x0100 /* Configure PTEN8 as address*/
#endif

#define BIT_P_ALL                   0xFFFF /* Configure All PMA and PMCS as address*/ 

#define BIT_P14                     0x4000 /* Configure PTEN14 as address*/

#define BIT_P7                      0x0080 /* Configure PTEN7 as address*/
#define BIT_P6                      0x0040 /* Configure PTEN6 as address*/
#define BIT_P5                      0x0020 /* Configure PTEN5 as address*/
#define BIT_P4                      0x0010 /* Configure PTEN4 as address*/
#define BIT_P3                      0x0008 /* Configure PTEN3 as address*/
#define BIT_P2                      0x0004 /* Configure PTEN2 as address*/
#define BIT_P1                      0x0002 /* Configure PTEN1 as address*/
#define BIT_P0                      0x0001 /* Configure PTEN0 as address*/
#define BIT_P_NONE                  0x0000 /* Configure ALL PTEN and PMCS as I0 */

/*****************************************************************************/
/* IEC2 Parallel Port Interrupt Enable Register Configuration Bit Definitions */
/*****************************************************************************/
/* INTERRUPT ENABLE SELECTION BITS configuration bit mask defines */ 
#define BIT_INT_ON                  0x0002/* Configure PMPIE enabled */  
#define BIT_INT_OFF                 0x0000/* Configure PMPIE disabled */
#define BIT_INT_MASK                (~BIT_INT_ON)	//Mask PMP Interrupt enable/disable bit

/*****************************************************************************/
/* IPC11 Parallel Port Interrupt Priority Register Configuration Bit Definitions */
/*****************************************************************************/
/* INTERRUPT PRIORITY SELECTION BITS configuration bit mask defines */
#define BIT_INT_PRI_HIGH               0x0001 /* Configure PMPIP = 7 */
#define BIT_INT_PRI_LOW                0x0000 /* Configure PMPIP = 6 */
#define BIT_INT_PRI_MASK            (~BIT_INT_PRI_HIGH)	//Mask Interrupt priority selection


/******************************************************************************/
/* MACRO DEFINITIONS */
/*****************************************************************************/
/******************************************************************************
 * Macro:           mPMPIsBufferFull
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        Returns state of PMSTAT.IBF (input buffer full bit)
 *                  
 * Input:           None
 *
 * Output:          TRUE/FALSE
 *
 * Note:            Use in any SLAVE mode
 *****************************************************************************/
#define mPMPIsBufferFull    PMSTATHbits.IBF

/******************************************************************************
 * Macro:           mPMPIsBufferOverflow
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        Returns state of PMSTAT.IBOV (input buffer overflow bit)
 *                  
 * Input:           None
 *
 * Output:          TRUE/FALSE
 *
 * Note:            Use in any SLAVE mode
 *****************************************************************************/
#define mPMPIsBufferOverflow  PMSTATHbits.IBOV 

 /******************************************************************************
 * Macro:           mPMPClearBufferOverflow
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        Clears PMSTAT.IBOV (input buffer overflow bit)
 *
 * Input:           None
 *
 * Output:          None
 *
 * Note:            Use in any SLAVE mode         
 *****************************************************************************/
#define mPMPClearBufferOverflow PMSTATHbits.IBOV = 0

/******************************************************************************
 * Macro:           mPMPClearBufferUnderflow
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        Clears PMSTAT.OBUF (output buffer underflow bit)*
 *
 * Input:           None
 *
 * Output:          None
 *                  
 * Note:            Use in any SLAVE mode          
 *****************************************************************************/
#define mPMPClearBufferUnderflow  PMSTATLbits.OBUF = 0

/******************************************************************************
 * Macro:           mPMPIsBufferEmpty
 *
 * PreCondition:    None
 * 
 * Side Effects:    None
 *
 * Overview:        Returns state of PMSTAT.OBE (output buffer empty bit)
 *
 * Input:           None     
 *
 * Output:          TRUE/FALSE
 *
 * Note:            Use in any SLAVE mode
 *****************************************************************************/
#define mPMPIsBufferEmpty PMSTATLbits.OBE

/******************************************************************************
 * Macro:           mPMPIsBufferUnderFlow
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        Returns state of PMSTAT.OBUF (output buffer underflow bit)
 *                  
 * Input:           None
 *
 * Output:          TRUE/FALSE
 *
 * Note:            Use in any SLAVE mode          
 *****************************************************************************/
#define mPMPIsBufferUnderflow PMSTATLbits.OBUF 

/******************************************************************************
 * Macro:           mPMPSetAddrIncMode(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP address inc/dec mode
 *
 * Input:           mode - 0= no inc/dec, 1= inc, 2= dec, 3= PSP buffered
 *
 * Output:          None
 *                  
 * Note:            
 *****************************************************************************/
#define mPMPSetAddrIncMode(mode)    (PMMODEH | (((UINT8)mode)&0x07) )

/******************************************************************************
 * Macro:           mPMPSetAddrMux(mux)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        selects PMP addrs/data multiplexing mode
 *
 * Input:           mux - 0= no mux, 1= 8addr/8data muxed, 2= 16addr/8data muxed
 *
 * Output:          None 
 *                  
 * Note:            
 *****************************************************************************/
#define mPMPSetAddrMux(mux)     (PMCONH | ( (((UINT8)mux)&0x03) <<3))

/******************************************************************************
 * Macro:           mPMPSetByteEnableEPolarity(polarity)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP byte enable pin polarity
 *
 * Input:           polarity - 0= active lo, 1= active hi
 *
 * Output:          None
 * 
 * Note:            
 *****************************************************************************/
#define mPMPSetByteEnablePolarity(polarity) PMCONLbits.BEP = polarity

/******************************************************************************
 * Macro:           mPMPSetByteEnable(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP byte enable bit
 *
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None
 *                  
 * Note:            
 *****************************************************************************/
#define mPMPSetByteEnable(mode) PMCONHbits.PTBEEN =  mode

/******************************************************************************
 * Macro:           mPMPSetChipSelectMode(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP CS function as a chip select or address line
 *
 * Input:           mode - 0= CS2 as A15,CS1 as A14; 1= CS2 as CS2, CS1 as A14;
 *                  2= CS2 as CS2, CS1 as CS1; 3= reserved
 *
 * Output:          None 
 *                  
 * Note:            
 *****************************************************************************/ 
#define mPMPSetChipSelectMode(mode) (PMCONL |((((UINT8)mode)&0x03)<<6))

/******************************************************************************
 * Macro:           mPMPSetChipSelect1Enable(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP chip select 1 pin polarities
 *
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None
 *                  
 * Note:            
 *****************************************************************************/
 #define mPMPSetChipSelect1Enable(mode) PMADDRHbits.CS1 = mode
 
/******************************************************************************
 * Macro:           mPMPSetChipSelect1Polarity(polarity)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP chip select pin polarities
 *
 * Input:           polarity - 0= active lo, 1= active hi
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetChipSelect1Polarity(polarity) PMCONLbits.CS1P = polarity


 #if  defined (pmp_v1_1)
 
/******************************************************************************
 * Macro:           mPMPSetChipSelect2Enable(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP chip select 2 pin
 *
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None 
 *                  
 * Note:            
 *****************************************************************************/
 #define mPMPSetChipSelect2Enable(mode) PMADDRHbits.CS2 = mode

/******************************************************************************
 * Macro:           mPMPSetChipSelect2Polarity(polarity)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP chip select pin polarities
 *                  
 * Input:           polarity - 0= active lo, 1= active hi
 *
 * Output:          None
 * 
 * Note:            
 *****************************************************************************/
#define mPMPSetChipSelect2Polarity(polarity) PMCONLbits.CS2P = polarity

#endif

/******************************************************************************
 * Macro:           mPMPSetDataMode(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP data width to 8 or 16 bit
 *                      
 * Input:           mode - 0=8-bit, 1=16-bit
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetDataMode(mode)   PMMODEHbits.MODE16 = mode

/******************************************************************************
 * Macro:           mPMPSetInterruptEnable(mode)
 *
 * PreCondition:    
 *
 * Side Effects:    None
 *
 * Overview:        sets/clears PMP interrupt enable bit
 *                  
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetInterruptEnable(mode) PIE1bits.PMPIE = mode

/******************************************************************************
 * Macro:           mPMPSetInterruptPriority(polarity)
 *
 * PreCondition:    
 *
 * Side Effects:    None
 *
 * Overview:        sets/clears PMP interrupt priority bits
 *
 * Input:           priority - 0..7
 *
 * Output:          None
 *                  
 * Note:            
 *****************************************************************************/
#define mPMPSetInterruptPriority(priority) IPR1bits.PMPIP = priority

/*******************************************************************
Macro       : mPMP_Clear_Intr_Status_Bit

Include     : pmp.h 

Description : Macro to Clear PMP Interrupt Status bit 

Arguments   : None 

Remarks     : None 
*******************************************************************/
#define mPMP_Clear_Intr_Status_Bit     (PIR1bits.PMPIF = 0)

 #if defined (pmp_v1_1) || defined (pmp_v1_2)
 /******************************************************************************
 * Macro:           mPMPSetIdle(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets/clears PMP "STOP IN IDLE" mode bit
 *                  
 * Input:           mode - 0=ENABLED, 1=DISABLED 
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetIdle(mode) PMCONHbits.PSIDL = mode
#endif

/******************************************************************************
 * Macro:           mPMPSetInterruptMode(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        selects PMP interrupt mode
 *                  
 * Input:           mode - 0=no INT, 1=INT on R/W, 2=INT on buffer full, 3= reserved
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetInterruptMode(mode)  (PMMODEH |((((UINT8)mode)&0x03)<<5))

/******************************************************************************
 * Macro:           mPMPSetAddrLatchPolarity(polarity)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP address latch pin polarity
 *                  
 * Input:           polarity - 0= active lo, 1= active hi
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetAddrLatchPolarity(polarity)  PMCONLbits.ALP = polarity

/******************************************************************************
 * Macro:          mPMPSetPortEnable(mode)
 *
 * PreCondition:    
 *
 * Side Effects:    None
 *
 * Overview:        sets/clears PMP module enable bit
 *                  
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetPortEnable(mode) PMCONHbits.PMPEN = mode

/******************************************************************************
 * Macro:           mPMPSetPortMode(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP mode of operation as a slave or master
 *                  
 * Input:           mode - 0= PSP legacy, 1= PSP enhanced, 2= Master2, 3= Master1
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetPortMode(mode) (PMMODEH | (((UINT8)mode)&0x03))

/******************************************************************************
 * Macro:           mPMPSetPortPins(Value)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP addrs, latch and CS pins as either functional or I/O
 *                  
 * Input:           value - 0-FFFFh
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetPortPins(value)  (PMEH = (0xFF&value),PMEL = ((0xFF00&value)>>8) )

/******************************************************************************
 * Macro:           mPMPSetReadStrobeEnable(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP read pin polarity
 *                  
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
 #define mPMPSetReadStrobeEnable(mode) PMCONHbits.PTRDEN = mode;
 
/******************************************************************************
 * Macro:           mPMPSetReadStrobePolarity(polarity)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP read pin polarity
 *
 * Input:           polarity - 0= active lo, 1= active hi
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetReadStrobePolarity(polarity) PMCONLbits.RDSP = polarity

/******************************************************************************
 * Macro:           mPMPSetWaitBegin(wait)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP beginning phase wait time
 *                  
 * Input:           wait - 0..3 cycles
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetWaitBegin(wait)  PMMODELbits.WAITB = wait

/******************************************************************************
 * Macro:           mPMPSetWaitMiddle(wait)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP middle phase wait time
 *                  
 * Input:           wait - 0..15 cycles
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetWaitMiddle(wait) PMMODELbits.WAITM = wait

/******************************************************************************
 * Macro:           mPMPSetWaitEnd(wait)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP end phase wait time
 *                  
 * Input:           wait - 0..3 cycles
 *
 * Output:          None
 * 
 * Note:            
 *****************************************************************************/
#define mPMPSetWaitEnd(wait)    PMMODELbits.WAITE = wait

/******************************************************************************
 * Macro:           mPMPSetWriteStrobeEnable(mode)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP read pin polarity
 *                  
 * Input:           mode - 0=DISABLED, 1=ENABLED
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
 #define mPMPSetWriteStrobeEnable(mode) PMCONHbits.PTWREN = mode;
 
/******************************************************************************
 * Macro:           mPMPSetWriteStrobePolarity(polarity)
 *
 * PreCondition:    None
 *
 * Side Effects:    None
 *
 * Overview:        sets PMP write pin polarity
 *                  
 * Input:           polarity - 0= active lo, 1= active hi
 *
 * Output:          None
 *
 * Note:            
 *****************************************************************************/
#define mPMPSetWriteStrobePolarity(polarity)    PMCONLbits.WRSP = polarity

/******************************************************************************/
/* FUNCTION PROTOTYPES */
/*****************************************************************************/
extern void PMPClose(void) ;

extern BOOL PMPIsBufferNEmpty(BUFFER buf) ;

extern BOOL PMPIsBufferNFull(BUFFER buf) ;

extern void PMPOpen(UINT control, UINT mode, UINT port, UINT addrs, BYTE interrupt) ;

extern WORD PMPMasterRead(void) ;

extern void PMPMasterWrite(WORD value) ;

extern void PMPSetAddress(WORD addrs) ;

extern void PMPSlaveReadBuffers(BYTE* ref) ;

extern BYTE PMPSlaveReadBufferN(BUFFER buf) ;

extern BOOL PMPSlaveWriteBuffers(BYTE* ref) ;

extern BOOL PMPSlaveWriteBufferN(BUFFER buf, BYTE value) ;

#endif 

//#else		//This preprocessor conditional statement is to avoid unintended linking for unsuppported devices.
//#warning "Selected device does not support this Module"
#endif

#endif	//_PMP_H
