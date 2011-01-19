#include <p18cxxx.h>
#include <pwm.h>


/********************************************************************
*    Function Name:  SetDCPWM10                                     *
*    Return Value:   void                                           *
*    Parameters:     dutycycle: holds 10-bit duty cycle value       *
*    Description:    This routine writes the 10-bit value from      *
*                    dutycycle into the PWMX duty cycle registers   *
*                    CCPRXL and CCPXCON.                            *
********************************************************************/
#if defined (PWM_V14)
void SetDCPWM10(unsigned int dutycycle)
{
  union PWMDC DCycle;

  // Save the dutycycle value in the union
  DCycle.lpwm = dutycycle << 6;
  
  // Write the high byte into CCPR5L
  CCPR10L = DCycle.bpwm[1];

  // Write the low byte into CCPxCON5:4
  CCP10CON = (CCP10CON & 0xCF) | ((DCycle.bpwm[0] >> 2) & 0x30);
}
#endif
