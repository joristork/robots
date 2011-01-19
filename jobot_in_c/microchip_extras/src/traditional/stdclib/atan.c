//----------------------------------------------------------------------------------------------
// float atan(float x)
//
// Description:
//
//         inverse tangent
//
// Input:
//         float x
//
// Output:
//         atan(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define PI      3.1415926535f
#define PIOVER2 1.57079632679489662f
#define PIOVER3 1.04719755119659775f
#define PIOVER6 0.523598775598298873f
#define SQRT3m1     0.73205080756887729f
#define SQRT3       1.73205080756887729f
#define TWOmSQRT3   0.267949192431122706f
#define ATANEPS     0.000244140625f
#define ATANP0      -0.4708325141e+0f
#define ATANP1      -0.5090958253e-1f
#define ATANQ0      +0.1412500740e+1f

float atan(float x)
{       
    tUfloat32 Ret;
    tIEEE xIEEE;
    FTYPE xType;
    unsigned char n;
    Ret.f = x;
    xType = _UnpkMath(Ret, &xIEEE);
    if(xType == _NANTYPE)
        {
        xIEEE.sgn = 0;
        Ret.l = NaN;
        }
    else if(xType == _ZEROTYPE)
        {
        // return zero
        Ret.l = 0;
        }
    else if(xType == _INFTYPE)
        {
        Ret.f = PI / 2;
        }
    else 
        {
        Ret.f = fabs(Ret.f);
        if(Ret.f > 1.0f)
            {
            Ret.f = 1.0f / Ret.f;
            n = 2;
            }
        else 
            {
            n = 0;
            }
        if(Ret.f > TWOmSQRT3)
            {
            Ret.f = ((SQRT3m1 *Ret.f - 1.0f) + Ret.f) / (SQRT3 + Ret.f);
            n++;
            }
        if(fabs(Ret.f) >= ATANEPS)
            {
            float g;
            float P, Q, R;
            g = Ret.f *Ret.f;
            P = ATANP1 *g + ATANP0;
            Q = g + ATANQ0;
            R = g *P / Q;
            Ret.f += Ret.f *R;
            }
        if(n == 1)
            {
            Ret.f = PIOVER6 + Ret.f;
            }
        else if(n == 2)
            {
            Ret.f = PIOVER2 - Ret.f;
            }
        else if(n == 3)
            {
            Ret.f = PIOVER3 - Ret.f;
            }
        }

    if(xIEEE.sgn)
        Ret.f = -Ret.f;

    return Ret.f;
}

