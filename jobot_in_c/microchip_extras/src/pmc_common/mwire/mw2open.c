#include <p18cxxx.h>
#include <mwire.h>
#include "pps.h"
#if defined (MWIRE_V2) || defined (MWIRE_V4)
/********************************************************************
*   Function Name:  OpenMwire2                                      *
*   Return Value:   void                                            *
*   Parameters:     SSP2 peripheral setup byte                      *
*   Description:    This function sets up the SSP2 module on a      * 
*                   PIC18CXXX core device for use with a Microchip  *
*                   Microwire EEPROM device.                        *
********************************************************************/
void OpenMwire2( unsigned char sync_mode )
{

#if defined (MWIRE_V4)
	PPSUnLock();					
	PPSInput(PPS_SDI2,PPS_RP4);		//PPS assignment of SSP functins on to PIN
	PPSOutput(PPS_RP3,PPS_SDO2);	//PPS assignment of SSP functins on to PIN	
	PPSOutput(PPS_RP5,PPS_SCK2);	//PPS assignment of SSP functins on to PIN
	PPSLock();
#endif

  SSP2STAT &= 0x3F;                // power on state 
  SSP2CON1 = 0x00;                 // power on state
  SSP2CON1 |= sync_mode;           // select serial mode 

  MW2_DO_TRIS = 0;            // define SDO2 as output 
  MW2_CLK_TRIS = 0;            // define clock as output   
  MW2_DI_TRIS = 1;            // define SDI2 as input 
 
  SSP2CON1 |= SSPENB;              // enable synchronous serial port 
}
#endif
