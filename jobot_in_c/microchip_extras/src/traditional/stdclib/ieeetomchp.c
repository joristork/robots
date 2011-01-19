#include <p18cxxx.h>
/*
                      +-----------+-----------+-----------+-----------+
                      |    eb     |    f0     |    f1     |    f2     |
   +------------------+-----------+-----------+-----------+-----------+
   | IEEE754 32-bit   | sxxx xxxx | yxxx xxxx | xxxx xxxx | xxxx xxxx |
   +------------------+-----------+-----------+-----------+-----------+
   | Microchip 32-bit | xxxx xxxx | sxxx xxxx | xxxx xxxx | xxxx xxxx |
   +------------------+-----------+-----------+-----------+-----------+

   The conversion from MCHP <--> IEEE754 requires the upper two bytes
   be rotated to move the sign bit around.
 */
unsigned long ieeetomchp (float v)
{
  FSR0 = ((unsigned)&v) + 2;
  _asm
    bcf STATUS, 0, 0
    rlcf POSTINC0, 1, 0
    rlcf POSTDEC0, 1, 0
    rrcf INDF0, 1, 0
  _endasm
  return *(unsigned long *)&v;
}
