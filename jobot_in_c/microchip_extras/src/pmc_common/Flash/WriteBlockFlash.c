#include "flash.h"

#if defined (FLASH_V1_1) || defined (FLASH_V1_2) || defined (FLASH_V1_3) || defined (FLASH_V1_4) \
	|| defined (FLASH_V1_5) || defined (FLASH_V1_6) || defined (FLASH_V2_1)

 /*********************************************************************
 Function:        	void WriteBlockFlash(unsigned long startaddr, unsigned char num_block, unsigned char *flash_array)

 PreCondition:    	None
                  
 Input:           	startaddr - Strating address from which flash has to be written
			num_blocks - Number of blocks of flash to be written
			*flash_array - Pointer to array contents of which has to be written to flash
			
 Output:          	None
 
 Side Effects:    	Flash will be written in blocks of number of bytes specified in device data sheet
 
 Overview:        	The function writes flash from starting address in terms of number of bytes specified in device data sheet
			till end address or nearest multiple of number of bytes specified in device data sheet. If number of bytes between strating 
			and end address in not in multiples of number of bytes specified in device data sheet, write begins from address that is previous 
			nearest multiple of number of bytes specified in device data sheet 
                   
 Note:            	1. Necessary to erase flash block (number of bytes specified in device data sheet) exclusively in application before writing 
			   if application had written data into to this block of flash(after erasing followed by programming).
			2. Necessary to write interms block of number of bytes specified in device data sheet
			3. Writing can be done directly without erase if these bytes of flash is being written for the first time after programming
 ********************************************************************/
void WriteBlockFlash(unsigned long startaddr, unsigned char num_blocks, unsigned char *flash_array)
{
unsigned char write_byte=0,flag=0;
DWORD_VAL flash_addr;
		
		startaddr /=  FLASH_WRITE_BLOCK ;	//Allign the starting address block
		startaddr *= FLASH_WRITE_BLOCK ;
		
		flash_addr.Val = startaddr;
		
		while(num_blocks--)
		{
				TBLPTRU = flash_addr.byte.UB;						//Load the address to Address pointer registers
				TBLPTRH = flash_addr.byte.HB;	
				TBLPTRL	= flash_addr.byte.LB;
				
				write_byte = FLASH_WRITE_BLOCK;
				while(write_byte--)
				{
					TABLAT = *(flash_array++);
					_asm  TBLWTPOSTINC 	_endasm
				
				}	

				TBLPTRU = flash_addr.byte.UB;						//Load the address to Address pointer registers
				TBLPTRH = flash_addr.byte.HB;	
				TBLPTRL	= flash_addr.byte.LB;
			  //*********** Flash write sequence ***********************************
			  EECON1bits.WREN = 1;
			  if(INTCONbits.GIE)
			  {
				INTCONbits.GIE = 0;
				flag=1;
			  }		  
			  EECON2 = 0x55;
			  EECON2 = 0xAA;
			  EECON1bits.WR =1;
			  EECON1bits.WREN = 0 ; 
			  if(flag)
			  {
				INTCONbits.GIE = 1;	
				flag=0;
			  }
	  
			 flash_addr.Val = flash_addr.Val + FLASH_WRITE_BLOCK;									//increment to one block of 64 bytes
		}
		
}


#endif

