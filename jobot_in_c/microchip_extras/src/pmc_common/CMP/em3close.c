#include <p18cxxx.h>
#include <compare.h>


/********************************************************************
*    Function Name:   CloseECompare3                               *
*    Return Value:    void                                          *
*    Parameters:      void                                          *
*    Description:     This routine disables the capture interrupt.  *
********************************************************************/
#if defined (ECC_V8) || defined (ECC_V8_1)|| defined (ECC_V9)|| defined (ECC_V9_1)

void CloseECompare3(void)
{
   PIE4bits.CCP3IE = 0;    // Disable the interrupt
   CCP3CON=0x00;           // Reset the CCP module
}

#endif
