//----------------------------------------------------------------------------------------------
// float atan2(float y, float x)
//
// Description:
//
//         inverse tangent
//
// Input:
//         float x, float y
//
// Output:
//         atan2(y,x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define PI  3.1415926535f

float atan2(float y, float x)
{       
    tUfloat32 Ret;
    tIEEE xIEEE, yIEEE;
    FTYPE xType, yType;
    xType = _UnpkMath(*(tUfloat32 *)& x, &xIEEE);
    yType = _UnpkMath(*(tUfloat32 *)& y, &yIEEE);

    if( ( (xType | yType) & _NANTYPE ) ||
        ( (xType == _INFTYPE) && (yType == _INFTYPE) ) ||
        ((xType == _ZEROTYPE) && (yType == _ZEROTYPE)) )
        {
        errno = EDOM;
        Ret.l = NaN;
        }
    else 
        {
        Ret.f = atan(fabs(y / x));
        if( xIEEE.sgn)
            {
            Ret.f = PI - Ret.f;
            }
        if( yIEEE.sgn)
            {
            Ret.f = - Ret.f;
            }
        }
    return Ret.f;
}
