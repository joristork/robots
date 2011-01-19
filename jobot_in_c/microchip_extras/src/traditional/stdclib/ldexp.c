//----------------------------------------------------------------------------------------------
// float ldexp(float x, int n)
//
// Description:
//
//         Load exponent function.
//
// Input:
//         float x
//         int   n
//
// Output:
//         x scaled by 2^n
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float ldexp(float x, int n)
{
    tIEEE xIEEE;
    FTYPE type;
    tUfloat32 Ret;

    Ret.f = x;
    type = _UnpkMath(Ret, &xIEEE);
    if( (type & (_NANTYPE | _INFTYPE)) )
        return Ret.f;

    if( type == _ZEROTYPE )
        Ret.l = xIEEE.sgn ? NEGZERO : POSZERO;
    else
        {
        xIEEE.exp += n;
        _PackMath(&xIEEE, &Ret);
        }
    return Ret.f;
}

