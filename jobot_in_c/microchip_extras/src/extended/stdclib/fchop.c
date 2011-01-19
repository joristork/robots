#include <errno.h>
#include <math.h>
#include "_mathlib.h"

tUfloat32 _fchop(tUfloat32 x, int k, unsigned char *pChopped)
{
    FTYPE type;
    tIEEE xIEEE;
    tUfloat32 Ret;
    unsigned char ChopFlg = 0;

    Ret = x;
    type = _UnpkMath(Ret, &xIEEE);

    if(type == _FINITETYPE)
        {
        int d;
        d = xIEEE.exp - k - FLT_BIAS;
        if(d < 0)
            {
            ChopFlg = 1;
            Ret.l = xIEEE.sgn ? NEGZERO : POSZERO;
            }
        else if(d < 23)
            {
            unsigned long mask;
            mask = 0x007FFFFFul >> d;
            ChopFlg = (xIEEE.sig & mask) != 0;
            if(ChopFlg)
                {
                xIEEE.sig &= 0x00800000ul - (mask + 1);
                _PackMath( &xIEEE, &Ret);
                }
            }
        }
    else if(type == _ZEROTYPE)
        Ret.l = xIEEE.sgn ? NEGZERO : POSZERO;

    *pChopped = ChopFlg;
    return Ret;
}

