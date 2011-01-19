//----------------------------------------------------------------------------------------------
//float tanh(float x)
//
// Description:
//
//         hyperbolic tangent function
//
// Input:
//         float x
//
// Output:
//         tanh(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define TANH_XBIG   9.01091334728f
#define TANH_EPS    0.000244140625f
#define TANH_C0     0.549306144334f
#define TANH_P0     -0.8237728127e+0f
#define TANH_P1     -0.3831010665e-2f
#define TANH_Q0     +0.2471319654e+1f

float tanh(float x)
{       
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;
    Ret.f = x;
    type = _UnpkMath(Ret, &xIEEE);
    if(type == _NANTYPE)
        {
        // QNaN
        Ret.l = NaN;
        }
    else if(type == _INFTYPE)
        {
        Ret.f = xIEEE.sgn
                ? - 1.0
                : 1.0;
        }
    else if(type != _ZEROTYPE)
        {
        float f;
        f = fabs(x);
        if(f > TANH_XBIG)
            {
            Ret.f = 1.0;
            }
        else if(f > TANH_C0)
            {
            Ret.f = 0.5 - 1.0 / (1.0f + exp(f + f));
            Ret.f += Ret.f;
            }
        else if(f < TANH_EPS)
            {
            Ret.f = f;
            }
        else 
            {
            float g;
            float P, Q, R;
            g = f * f;
            P = TANH_P1 *g + TANH_P0;
            Q = g + TANH_Q0;
            R = g * P / Q;
            Ret.f = f + f * R;
            }
        if(xIEEE.sgn)
            {
            Ret.f = - Ret.f;
            }
        }
    // return x unchanged if x = +/-0
    return(Ret.f);
}

