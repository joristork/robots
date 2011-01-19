//----------------------------------------------------------------------------------------------
//float sinh(float x)
//
// Description:
//
//         hyperbolic sine function
//
// Input:
//         float x
//
// Output:
//         sinh(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define SINH_EPS    0.000244140625f
#define SINH_P0     -0.713793159e+1f
#define SINH_P1     -0.190333399e+0f
#define SINH_Q0     -0.428277109e+2f
float sinh(float x)
{       
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;
    float y;

    Ret.f = x;

    type = _UnpkMath(Ret, &xIEEE);
    if(type == _NANTYPE)
        {
        // QNaN
        Ret.l = NaN;
        }
    else if(type & (_INFTYPE | _ZEROTYPE) )
        {
        // Return as is
        }
    else
        {
        y = fabs(x);
        if(y > 1)
            {
            Ret.f = _sinhcosh(y, 0);
            if(xIEEE.sgn)
                Ret.f = - Ret.f;
            }
        else 
            {
            if(y >= SINH_EPS)
                {
                float g;
                float P, Q, R;
                g = x *x;
                P = SINH_P1 *g + SINH_P0;
                Q = g + SINH_Q0;
                R = g *P / Q;
                Ret.f = x + x *R;
                }
            }
        }
    return(Ret.f);
}

