//----------------------------------------------------------------------------------------------
// float modf(float x, float *ipart)
//
// Description:
//
//  modf breaks the float x into two parts: the integer and the fraction.
//  modf stores the integer in ipart and returns the fraction.
//
// Input:
//      float x
//      float *ipart     Pointer to integer part
//
// Output:
//      modf(x,&ipart)  Fractional part
//      ipart           Integer part
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float modf(float x, float *ipart)
{
    FTYPE type;
    tUfloat32 Frac, Integ;
    unsigned char Chopped;

    Integ = _fchop( *(tUfloat32 *)&x, 0, &Chopped);

    if( Chopped )
        Frac.f = x - Integ.f;
    else
        {
        FTYPE type;
        tIEEE xIEEE;
        type = _UnpkMath(*(tUfloat32 *)&x, &xIEEE);

        if(type == _NANTYPE)
            Frac.f = x;
        else
            {
            Frac.f = 0.0;
            if( xIEEE.sgn )
                Frac.ub[3] |= 0x80;
            }
        }
    *ipart = Integ.f;
    return(Frac.f);
}
