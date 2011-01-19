//----------------------------------------------------------------------------------------------
// float sqrt(float x)
//
// Description:
//
//         square root
//
// Input:
//         float x
//
// Output:
//         square root of x
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float sqrt(float x)
{       
    int n;
    tUfloat32 Ret;
    tIEEE xIEEE;
    FTYPE type;

    Ret.f = x;
    type = _UnpkMath(Ret, &xIEEE);

    if(type == _NANTYPE)
        Ret.l = NaN;

    else if(Ret.f < 0)
        {
        errno = EDOM;
        Ret.l = NaN;
        }

    else if(type & (_ZEROTYPE | _INFTYPE) )
        return Ret.f;

    else 
        {
        int i;
        int round;
        int sticky;
        unsigned long q;
        unsigned long r;
        unsigned long b;
        n = xIEEE.exp + (2 - FLT_BIAS);
        r = xIEEE.sig;
        if(n & 1)
            {
            n--;
            r <<= 1;
            }
        n /= 2;
        q = 0;
        b = 0x04000000ul >> 2;
        for(i = 1; i < 26; ++i)
            {
            long t;
            r = r << 1;
            t = r - ( (q<<1) + b);
            if(t > 0)
                {
                r = t;
                q |= b;
                }
            b >>= 1;
            }
        round = q & 1;
        sticky = r;
        q >>= 1;
        if(round && (sticky || (q & 1)))
            {
            q++;
            }
        xIEEE.sgn = 0;
        xIEEE.sig = q;
        xIEEE.exp = - 1 + FLT_BIAS;
        _PackMath(&xIEEE, &Ret);
        Ret.f = ldexp( Ret.f, n);
        }
    return(Ret.f);
}

