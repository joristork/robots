#include "flash.h"

#if defined (FLASH_V1_1) || defined (FLASH_V1_2) || defined (FLASH_V1_3) || defined (FLASH_V1_4) \
	|| defined (FLASH_V1_5) || defined (FLASH_V1_6) || defined (FLASH_V2_1)

 /*********************************************************************
 Function:        	void WriteBytesFlash(unsigned long startaddr, unsigned long num_bytes, unsigned char *flash_array)

 PreCondition:    	None
                  
 Input:           	startaddr - Strating address from which flash has to be written
			num_bytes - Number of bytes of flash to be written
			*flash_array - Pointer to array contents of which has to be written to flash
			
 Output:          	None
 
 Side Effects:    	None
 
 Overview:        	The function writes flash from starting address specified
			till number of bytes specified
                   
 Note:            	1. Necessary to erase flash block (number of bytes specified in device data sheet) exclusively in application before writing 
			   if application had written data into to this block of flash(after erasing followed by programming).
			2. Writing can be done directly without erase if these bytes of flash is being written for the first time after programming
			
 ********************************************************************/
void WriteBytesFlash(unsigned long startaddr, unsigned int num_bytes, unsigned char *flash_array)
{
unsigned char write_byte=0,flag=0;
DWORD_VAL flash_addr;
		
		flash_addr.Val = startaddr;

		startaddr /= FLASH_WRITE_BLOCK ;	//Allign the starting address block
		startaddr *= FLASH_WRITE_BLOCK ;
		startaddr += FLASH_WRITE_BLOCK ;
		
		write_byte = startaddr - flash_addr.Val;
		
		while(num_bytes)
		{
				TBLPTRU = flash_addr.byte.UB;						//Load the address to Address pointer registers
				TBLPTRH = flash_addr.byte.HB;	
				TBLPTRL	= flash_addr.byte.LB;
				
				
				while(write_byte--)
				{
					TABLAT = *flash_array++;
					_asm  TBLWTPOSTINC 	_endasm
					if(--num_bytes==0)	break;
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
			 write_byte = FLASH_WRITE_BLOCK;
			 flash_addr.Val = flash_addr.Val + FLASH_WRITE_BLOCK;									//increment to one block of 64 bytes
		}
		
}


#endif

