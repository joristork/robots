#include <p18Cxxx.h>
#include <capture.h>

#if defined (CC_V8) || defined (CC_V8_1)
/*******************************************************************************
Function Prototype : unsigned int ReadCapture6(void)
 
Include            : capture.h
 
Description        : This function reads the pending Input Capture buffer.
 
Arguments          : None
 
Return Value       : This routine reads the CAxL and CAxH into the union Cap of type CapResult 
			that is defined in global data  space. The int result is then returned. 
 
Remarks            : This function reads the pending Input Capture buffer
*******************************************************************************/
unsigned int ReadCapture6(void)
{
  union CapResult Cap;

  CapStatus.Cap6OVF = 0;   // Clear the overflow flag in the
                           // status variable

  if(PIR4bits.CCP6IF)      // If the overflow flag is set
    CapStatus.Cap6OVF = 1; // Set the overflow flag

  Cap.bc[0] = CCPR6L;      // Read CAxL into the lower byte
  Cap.bc[1] = CCPR6H;      // Read CAxH into the high byte

  return (Cap.lc);         // Return the int
}
#endif
