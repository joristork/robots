//----------------------------------------------------------------------------------------------
// float log(float x)
//
// Description:
//
//  Natural log
//
// Input:
//  float x
//
// Output:
//  ln(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define A0          -0.5527074855E+0f
#define B0          -0.6632718214E+1f

#define C0          0.70710678118654752440f             /* sqrt(0.5) */
#define C1          (355.0f/512.0f)
#define C2          -2.121944400546905827679E-4f

float log(float x)
{
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;
    int n;
    float w, z, znum, zden;
    float A, B;

    type = _UnpkMath(*(tUfloat32 *)&x, &xIEEE);
    if(type == _NANTYPE || (xIEEE.sgn && type != _ZEROTYPE) )
        {
        errno = EDOM;
        Ret.l = NaN;
        }
    else if(type == _INFTYPE)
        {
        // return as is
        Ret.f = x;
        }
    else if(type == _ZEROTYPE)
        {
        errno = EDOM;
        Ret.l = NEGINF;
        }
    else
        {
        x = frexp(x, &n);
        if(x > C0)
            {
            znum = (x - 0.5f) - 0.5f;
            zden = x *0.5f + 0.5f;
            }
        else
            {
            n--;
            znum = x - 0.5f;
            zden = znum *0.5f + 0.5f;
            }
        z = znum / zden;
        w = z *z;
        A = A0;
        B = w + B0;
        Ret.f = w *A / B;
        Ret.f = z + z *Ret.f;
        Ret.f = C1 *n + (C2 *n + Ret.f);
        }
    return Ret.f;
}

