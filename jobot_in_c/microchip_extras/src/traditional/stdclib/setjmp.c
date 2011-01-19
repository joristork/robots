/* $Id: setjmp.c,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $ */
#include <setjmp.h>

void _setjmp (void)
{
  POSTINC0 = FSR1L;
  POSTINC0 = FSR1H;
  POSTINC0 = FSR2L;
  POSTINC0 = FSR2H;
  POSTINC0 = STKPTR;
  POSTINC0 = TOSL;
  POSTINC0 = TOSH;
  POSTINC0 = TOSU;
  PROD = 0;
}

void _longjmp (void)
{
  if (PROD==0)
    PRODL++;
  FSR1L = POSTINC0;
  FSR1H = POSTINC0;
  FSR2L = POSTINC0;
  FSR2H = POSTINC0;
  STKPTR = POSTINC0;
  TOSL = POSTINC0;
  TOSH = POSTINC0;
  TOSU = POSTINC0;
}
