#include <p18cxxx.h>
#include <pwm.h>


/********************************************************************
*    Function Name:  ClosePWM8                                     *
*    Return Value:   void                                           *
*    Parameters:     void                                           *
*    Description:    This routine turns off PWM8.                   *
********************************************************************/
#if defined (PWM_V14) || defined (PWM_V14_1)
void ClosePWM8(void)
{
  
	CCP8CON=0;            // Turn off PWM8

}
#endif
