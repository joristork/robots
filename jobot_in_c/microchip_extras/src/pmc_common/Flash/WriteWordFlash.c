#include "flash.h"

#if defined (FLASH_V2_1) 
 /*********************************************************************
 Function:        	void WriteWordFlash(unsigned long startaddr, unsigned int data)

 PreCondition:    	None
                  
 Input:           	startaddr - Strating address from which flash has to be written
			data - Data to be written into flash
						
 Output:          	None
 
 Side Effects:    	None
 
 Overview:        	The function writes word to flash 
                   
 Note:            	1. Necessary to erase flash block (number of bytes specified in device data sheet) exclusively in application before writing 
			   if application had written data into to this block of flash(after erasing followed by programming).
			2. Starting address has to be an even address else boundary mismatch will occur 
			3. Writing can be done directly without erase if these bytes of flash is being written for the first time after programming
 ********************************************************************/
void WriteWordFlash(unsigned long startaddr, unsigned int data)
{

unsigned char flag=0;
DWORD_VAL flash_addr;
WORD_VAL flash_data;

			flash_addr.Val = startaddr;
			flash_data.Val = data;
			
			TBLPTRU = flash_addr.byte.UB;						//Load the address to Address pointer registers
			TBLPTRH = flash_addr.byte.HB;	
			TBLPTRL	= flash_addr.byte.LB;
				
			TABLAT = flash_data.byte.LB;
			_asm  TBLWTPOSTINC 	_endasm

			TABLAT =flash_data.byte.HB;
			_asm  TBLWT 	_endasm
	
			TBLPTRU = flash_addr.byte.UB;						//Load the address to Address pointer registers
			TBLPTRH = flash_addr.byte.HB;	
			TBLPTRL	= flash_addr.byte.LB;
		  
		  //*********** Flash write sequence ***********************************
			EECON1bits.WPROG = 1;	
			EECON1bits.WREN = 1;
			if(INTCONbits.GIE)
			{
				INTCONbits.GIE = 0;
				flag=1;
			}
			EECON2  = 0x55;
			EECON2  = 0xAA;
			EECON1bits.WR = 1;
			if(flag)
				INTCONbits.GIE = 1;
		
}


#endif

