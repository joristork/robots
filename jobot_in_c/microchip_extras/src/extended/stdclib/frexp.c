//----------------------------------------------------------------------------------------------
// float frexp(float x, int *pexp)
//
// Description:
//
//  Splits a number into mantissa and exponent.
//  Calculates the mantissa m (a float greater than or equal to 0.5
//  and less than 1) and the integer value n such that x equals
//  m * 2n. frexp stores n in the integer that pexp points to.
//
// Input:
//  float x
//
// Output:
//  frexp = mantissa.  Returns x if NaN, Zero or Infinity
//  *pexp = exponent.  Returns 0 if NaN, Zero or Infinity
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float frexp(float x, int *pexp)
{
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;

    Ret.f = x;
    type = _UnpkMath(Ret, &xIEEE);

    if( type & (_NANTYPE | _INFTYPE | _ZEROTYPE) )
        *pexp = 0;
    else
        {
        // Normalized
        *pexp = (1-FLT_BIAS) + xIEEE.exp;
        xIEEE.exp = (-1+FLT_BIAS);
        _PackMath( &xIEEE, &Ret);
        }
    return Ret.f;
}
