#include <p18cxxx.h>

#pragma config WDT = OFF

void main (void)
{
  /* Make all bits on the Port B (LEDs) output bits.
   * If bit is cleared, then the bit is an output bit.
   */
  TRISB = 0;

  /* Reset the LEDs */
  PORTB = 0;

  /* Light the LEDs */
  PORTB = 0x5A;

  while (1)
    ;
}
