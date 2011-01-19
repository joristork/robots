#include <p18Cxxx.h>
#include <capture.h>

#if defined (CC_V8) || defined (CC_V8_1)
/*******************************************************************************
Function Prototype : void OpenCapture8(unsigned char config)
 
Include            : capture.h
 
Description        : This function configures the Input Capture module.
 
Arguments          : config - This contains the parameters to be configured in the
                              CCPxCON register as defined below
				Enable CCP Interrupts:
				    * CAPTURE_INT_ON        
				    * CAPTURE_INT_OFF  
				    * CAPTURE_INT_MASK
				Capture configuration
				     * CAP_EVERY_FALL_EDGE
				     * CAP_EVERY_RISE_EDGE 
				     * CAP_EVERY_4_RISE_EDGE  
				     * CAP_EVERY_16_RISE_EDGE
				     * CAP_MODE_MASK					
 
Return Value       : None
 
Remarks            : This function configures the input capture for idle mode, clock select,
                      capture per interrupt and mode select
********************************************************************************/
void OpenCapture8(unsigned char config)
{
  CCP8CON = config&0x0F; // Configure capture

  //configure timer source for CCP
  CCPTMRS2 &= 0b11111100;
  CCPTMRS2 |= ((config&0b00110000)>>4);  
  
  if(config&0x80)
  {
    PIR4bits.CCP8IF = 0;  // Clear the interrupt flag
    PIE4bits.CCP8IE = 1;  // Enable the interrupt
  }

  	CapStatus.Cap8OVF = 0;  // Clear the capture overflow status flag
	
	CP8_TRIS = 1;
}
#endif
