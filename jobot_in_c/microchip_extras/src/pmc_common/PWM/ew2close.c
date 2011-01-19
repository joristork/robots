#include <p18cxxx.h>
#include <pwm.h>


/********************************************************************
*    Function Name:  CloseEPWM2                                     *
*    Return Value:   void                                           *
*    Parameters:     void                                           *
*    Description:    This routine turns off PWM2.                   *
********************************************************************/

#if defined (EPWM_V14) || defined (EPWM_V14_1)|| defined (EPWM_V15) ||defined (EPWM_V15_1)

void CloseEPWM2(void)
{
 
 	ECCP2CON = 0x0;           // Turn off PWM
 
}
#endif
