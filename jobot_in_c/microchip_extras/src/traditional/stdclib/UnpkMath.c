//----------------------------------------------------------------------------------------------
// _UnpkMath(tUfloat32 X, tIEEE *pIEEE)
//
// Input: X is afloating point number to be unpacked
//
// Output: X is unpacked into *pIEEE
//      Special condition flags are returned as FTYPE
//
// Description: _UnpkMath is a support routine for math library
//      X is analysed for special condition flags
//      If X is an NaN, either signalling or quiet, errno is set to EDOM
//          and a quiet NaN is returned in *pIEEE.
//
//      X is unpacked into *pIEEE such that
//          sig: significant with leading 1 restored
//          exp: biased exponent,, i.e. bias not removed
//          sgn: if X has sign bit set => sgn = 1 else sgn = 0
//
//----------------------------------------------------------------------------------------------

#include <errno.h>
#include <math.h>
#include "_mathlib.h"

FTYPE _UnpkMath(tUfloat32 X, tIEEE *pIEEE)
{
    FTYPE ftype;
    pIEEE->sig = X.l & 0x007FFFFF;
    pIEEE->sgn = X.ub[3]& 0x80
                 ? 1
                 : 0;
    X.us[1]  = X.us[1] << 1;
    pIEEE->exp = X.ub[3];

    if (X.ub[3] == 255)
        {
        if (pIEEE->sig != 0)
            {
            errno = EDOM;
            ftype = _NANTYPE;
            }
        else
            ftype = _INFTYPE;
        }
    else if(X.ub[3] == 0)
        {
        // Treat subnormals as zero
        pIEEE->sig = 0;
        ftype = _ZEROTYPE;
        }
    else
        {
        ftype = _FINITETYPE;
        pIEEE->sig |= 0x00800000;      // restore implicit leading 1
        }

    return(ftype);
}


