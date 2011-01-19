/*----------------------------------------------------------------------*/
/* sinhcoshf.c: Floating-point hyperbolic sine/cosine functions.       */
/*                                                                      */
/*----------------------------------------------------------------------*/
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define SINH_YBAR   88.72283905206835305365817656031f
#define SINH_LNV    0.69316101074218750000E+0f

static float sinh_ybar = SINH_YBAR;
static float sinh_lnv = SINH_LNV;
static float SINH_Vo2m1 = 0.0000138302778796019f;
static float SINH_YHAT = 89.4159862326282983631f;
float _sinhcosh(float y, unsigned char flag)
{       
    tUfloat32 Ret;
    if(y > sinh_ybar)
        {
        float w, z;
        if(y > SINH_YHAT)
            {
            errno = ERANGE;
            Ret.l = POSINF;
            }
        else 
            {
            w = y - sinh_lnv;
            z = exp(w);
            Ret.f = z + SINH_Vo2m1 *z;
            }
        }
    else 
        {
        float z;
        Ret.f = exp(y);
        z = - 1.0 / Ret.f;
        if(flag)
            z = - z;

        Ret.f = (Ret.f + z) *0.5;
        }
    return(Ret.f);
}

