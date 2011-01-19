//#include <p18cxxx.h>
#include <ctmu.h>

#ifdef CTMU_V1

/***********************************************************************************************
Function:    void CloseCTMU (void)

Overview:    This function turns off the CTMU module and disables the CTMU interrupts.

Parameters:  None

Returns:     None

Remarks:     This function first disables the CTMU interrupt and then turns off the CTMU
                   module.The Interrupt Flag bit is also cleared.
***********************************************************************************************/

void CloseCTMU(void)
{
    /* disable CTMU interrupt */
    PIE3bits.CTMUIE = 0;

    /* turn off CTMU */
    CTMUCONHbits.CTMUEN = 0;

    /*turn off current source*/
    CTMUCONLbits.EDG1STAT = 0;
    CTMUCONLbits.EDG2STAT = 0;

    /* clear CTMUIF bit */
    PIR3bits.CTMUIF = 0;
	PIE3bits.CTMUIE = 0;
}

#endif
