//----------------------------------------------------------------------------------------------
// fmod: float fmod(float x, float y)
//
// Description:
//   fmod calculates x modulo y (the remainder f, where x = ay + f for some
//     integer a, and 0 < f < y).
//
// Input:
//      float x
//      float y
//
// Output:
//      fmod(float x, float y
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float fmod(float x, float y)
{
    FTYPE aType, bType;
    tUfloat32 Ret;
    tIEEE aIEEE, bIEEE;
    int Ed, Er;
    long B, P;
    unsigned char i;

    aType = _UnpkMath(*(tUfloat32 *)& x, &aIEEE);
    bType = _UnpkMath(*(tUfloat32 *)& y, &bIEEE);
    Ret.f = x;
    if(aType == _NANTYPE || bType == _NANTYPE)
        {
        Ret.l = NaN;
        goto jExit;
        }
    if(bType == _ZEROTYPE || aType == _INFTYPE)
        {
        errno = EDOM;
        Ret.l = NaN;
        goto jExit;
        }
    if(aType == _ZEROTYPE || bType == _INFTYPE)
        {
        // return x
        goto jExit;
        }
//-----------------------------------------------------------------------;
//       Arguments are finite and non-zero
//-----------------------------------------------------------------------;
    Ed = aIEEE.exp - bIEEE.exp;
    if(Ed < 0)
        {
        // return x
        goto jExit;
        }
    B = bIEEE.sig;
    P = aIEEE.sig;
    i = 0;
    for(; ; )
        {
        P -= B;
        if(P < 0)
            P += B;

        i++;
        if(i > Ed)
            {
            break;
            }
        P <<= 1;
        }

    if(P == 0)
        {
        Ret.l = 0;
        goto jSign;
        }

    Er = bIEEE.exp - FLT_BIAS;

    while((P & 0x00800000) == 0)
        {
        P <<= 1;
        Er--;
        }
    if(Er < FLT_EMIN )
        {
        // Subnormal: replace with 0
        Ret.l = 0;
        }
    else
        {
        bIEEE.sgn = 0;
        bIEEE.exp = FLT_BIAS + Er;
        bIEEE.sig = P;
        _PackMath( &bIEEE, &Ret);
        }

jSign:
    if(aIEEE.sgn)
        Ret.ub[3] |= 0x80;

jExit:
   return(Ret.f);
}

