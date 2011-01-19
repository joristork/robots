#include <p18cxxx.h>
#include <EEP.h>

/********************************************************************
*     Function Name:    Write_b_eep                                 *
*     Return Value:     None										*
*     Parameters:       unsigned int add, unsigned char data        *
*     Description:      Write single byte to Internal EEP           *
********************************************************************/
#if defined (EEP_V1)		//	128 byte EEP
void Write_b_eep( unsigned int badd,unsigned char bdata )
{
	EEADR = (badd & 0x07f);
	EEDATA = bdata;
  	EECON1bits.EEPGD = 0;
	EECON1bits.CFGS = 0;
	EECON1bits.WREN = 1;
	INTCONbits.GIE = 0;
	EECON2 = 0x55;
	EECON2 = 0xAA;
	EECON1bits.WR = 1;
	while(EECON1bits.WR);				//Wait till the write completion
	INTCONbits.GIE = 1;
	EECON1bits.WREN = 0;
}

#elif defined (EEP_V2)	//	256 byte EEP
void Write_b_eep( unsigned int badd,unsigned char bdata )
{
	EEADR = (badd & 0x0ff);
  	EEDATA = bdata;
  	EECON1bits.EEPGD = 0;
	EECON1bits.CFGS = 0;
	EECON1bits.WREN = 1;
	INTCONbits.GIE = 0;
	EECON2 = 0x55;
	EECON2 = 0xAA;
	EECON1bits.WR = 1;
	while(EECON1bits.WR);				//Wait till the write completion
	INTCONbits.GIE = 1;
	EECON1bits.WREN = 0;
}

#elif defined (EEP_V3)				// 1024 byte EEP	
void Write_b_eep( unsigned int badd,unsigned char bdata )
{
	EEADRH = (badd >> 8) & 0x03;
	EEADR = (badd & 0x0ff);
	EEDATA = bdata;
  	EECON1bits.EEPGD = 0;
	EECON1bits.CFGS = 0;
	EECON1bits.WREN = 1;
	INTCONbits.GIE = 0;
	EECON2 = 0x55;
	EECON2 = 0xAA;
	EECON1bits.WR = 1;
	while(EECON1bits.WR);				//Wait till the write completion
	INTCONbits.GIE = 1;
	EECON1bits.WREN = 0;
}
#endif
