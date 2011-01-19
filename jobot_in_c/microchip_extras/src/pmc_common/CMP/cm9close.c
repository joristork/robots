#include <p18cxxx.h>
#include <compare.h>


/********************************************************************
*    Function Name:    CloseCapture9                                *
*    Return Value:     void                                         *
*    Parameters:       void                                         *
*    Description:      This routine disables the capture interrupt. *
********************************************************************/
#if defined (CC_V8)
void CloseCompare9(void)
{
   PIE4bits.CCP9IE = 0; // Disable the interrupt
   CCP9CON=0x00;        // Reset the CCP module
}

#endif
