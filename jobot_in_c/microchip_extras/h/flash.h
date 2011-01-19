#ifndef __FLASH_H
#define __FLASH_H

/******************************************************************************
 *
 *                  FLASH PERIPHERAL LIBRARY HEADER FILE
 *
 ******************************************************************************
 * FileName:        flash.h
 * Dependencies:    See include below
 * Processor:       PIC18
 * Compiler:        MPLAB C18
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
#include "pconfig.h"
#include "GenericTypeDefs.h"

#if defined (FLASH_V1_1)

#define FLASH_WRITE_BLOCK 	8
#define FLASH_ERASE_BLOCK 	64

#elif defined (FLASH_V1_2)

#define FLASH_WRITE_BLOCK 	32
#define FLASH_ERASE_BLOCK 	64

#elif defined (FLASH_V1_3)

#define FLASH_WRITE_BLOCK 	16
#define FLASH_ERASE_BLOCK 	64

#elif defined (FLASH_V1_4)

#define FLASH_WRITE_BLOCK 	64
#define FLASH_ERASE_BLOCK 	64

#elif defined (FLASH_V1_5) || defined (FLASH_V2_1)

#define FLASH_WRITE_BLOCK 	64
#define FLASH_ERASE_BLOCK 	1024

#elif defined (FLASH_V1_6)

#define FLASH_WRITE_BLOCK 	128
#define FLASH_ERASE_BLOCK 	128

#endif


/*********************************************************************
 Macro       : LoadFlashAddr(addr)

 Include     : flash.h
 
 Description : This macro loads the table pointer with address of flash
 
 Arguments   :  addr - 24 byte flash address to be loaded into table pointer
 
 Remarks     :  24 bytes of address is passed as unsigned long
**********************************************************************/   
#define LoadFlashAddr(addr)					TBLPTRU = ((unsigned char)(addr>>16)),\
											TBLPTRH = (unsigned char)(((unsigned int)addr)>>8),\
											TBLPTRL	= ((unsigned char)addr)

/*********************************************************************
 Macro       : TableRead(data)

 Include     : flash.h
 
 Description : This macro does table read operation from flash
 
 Arguments   :  data - Byte of data to be read from flash
 
 Returns:	Byte of data read from flash from the specified location
 
 Remarks     : This macro has to be preceeded by loading flash address to TBLPTRx registers using LoadFlashAddr(addr) macro
**********************************************************************/   
#define TableRead(data)						_asm TBLRD _endasm; data=TABLAT




/*************** FUNCTION PROTOTYPES ***************************/

#if defined (FLASH_V1_1) || defined (FLASH_V1_2) || defined (FLASH_V1_3) || defined (FLASH_V1_4) \
	|| defined (FLASH_V1_5) || defined (FLASH_V1_6) || defined (FLASH_V2_1) || defined (FLASH_V3_1)

extern void ReadFlash(unsigned long startaddr, unsigned int num_bytes, unsigned char *flash_array);

#endif

#if defined (FLASH_V1_1) || defined (FLASH_V1_2) || defined (FLASH_V1_3) || defined (FLASH_V1_4) \
	|| defined (FLASH_V1_5) || defined (FLASH_V1_6) || defined (FLASH_V2_1)

extern void EraseFlash(unsigned long startaddr, unsigned long endaddr);

extern void WriteBlockFlash(unsigned long startaddr, unsigned char num_blocks, unsigned char *flash_array);

extern void WriteBytesFlash(unsigned long startaddr, unsigned int num_bytes, unsigned char *flash_array);

#if defined (FLASH_V2_1)
extern void WriteWordFlash(unsigned long startaddr, unsigned int data);
#endif

#endif


#endif /* __FLASH_H */
