#include <p18cxxx.h>
#include <pwm.h>


/********************************************************************
*    Function Name:  CloseEPWM1                                     *
*    Return Value:   void                                           *
*    Parameters:     void                                           *
*    Description:    This routine turns off PWM1.                   *
********************************************************************/

#if defined (EPWM_V7) || defined (EPWM_V14) || defined (EPWM_V14_1)\
 || defined (EPWM_V14_2)|| defined (EPWM_V15) ||defined (EPWM_V15_1)

void CloseEPWM1(void)
{
 
 	ECCP1CON = 0x0;           // Turn off PWM
 
}
#endif
