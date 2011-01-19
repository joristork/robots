//----------------------------------------------------------------------------------------------
//float ceil(float x)
//
// Description:
//
//      ceil finds the smallest integer not less than x.
//
// Input:
//         float x
//
// Output:
//         ceil(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float ceil( float x )
{
    FTYPE type;
    tUfloat32 Ret;
    unsigned char Chopped;


    Ret = _fchop( *(tUfloat32 *)&x, 0, &Chopped);

    if(Chopped && !(Ret.ub[3]&0x80))
        Ret.f += 1.0;

    return(Ret.f);
}
