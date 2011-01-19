//----------------------------------------------------------------------------------------------
//float cosh(float x)
//
// Description:
//
//         hyperbolic cosine function
//
// Input:
//         float x
//
// Output:
//         cosh(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float cosh(float x)
{       
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;

    float y;

    Ret.f = x;

    type = _UnpkMath(Ret, &xIEEE);
    if(type == _NANTYPE)
        {
        // QNaN
        Ret.l = NaN;
        }
    else if(type == _INFTYPE )
        {
        // +Infinity
        Ret.l = POSINF;
        }
    else
        {
        y = fabs(x);
        Ret.f = _sinhcosh(y, 1);
        }
    return(Ret.f);
}

