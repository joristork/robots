//----------------------------------------------------------------------------------------------
//float floor(float x)
//
// Description:
//
//         floor finds the largest integer not greater than x.
//
// Input:
//         float x
//
// Output:
//         floor(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float floor( float x )
{
    FTYPE type;
    tUfloat32 Ret;
    unsigned char Chopped;

    Ret = _fchop( *(tUfloat32 *)&x, 0, &Chopped);

    if(Chopped && (Ret.ub[3]&0x80))
        Ret.f -= 1.0;

    return(Ret.f);

}
