//----------------------------------------------------------------------------------------------
//float exp(float x)
//
// Description:
//
//         exponential function
//
// Input:
//         float x
//
// Output:
//         e^x
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define INVLN2  1.4426950408889634074f
#define C1  (355.0f / 512.0f)
#define C2  (-2.121944400546905827679E-4f)
/*
** BIGX: Largest machine number less than ln(XMAX)
*/
#define BIGX    8.872283E+1f    /* 88.72283905206835305365817656031f */
/* 0x42B17217 */
/*
** SMALLX: Samllest machine number greater than ln(XMIN)
*/
#define SMALLX  -1.0327899E+2f  /* -1.0327892990343185110316758609727E+2f */
/* 0xC2CE8ED8 */

#define p0  0.24999999950E+0f
#define p1  0.41602886268E-2f
#define q0  0.50000000000E+0f
#define q1  0.49987178778E-1f

float
exp(float x)
{
    FTYPE type;
    tUfloat32 Ret;
    tUfloat32 eps;
    tIEEE xIEEE;

    Ret.f = x;

    // exp(x) = 1.0 for |x| < EPS
    // EPS = [2^(-p)]/2 = 2^-(p+1)          (0x33800000)
    eps.l = 0x33800000;

    type = _UnpkMath(Ret, &xIEEE);
    if(type == _NANTYPE)
        {
        // QNaN
        Ret.l = NaN;
        }
    else if(type == _ZEROTYPE)
        {
        // Exact result: exp(0) = 1
        Ret.f = 1.0;
        }
    else if(type == _INFTYPE)
        {
        if(xIEEE.sgn)
            // Exact result: exp(-Infinity) = 0
            Ret.f = 0.0;
        // Else return as is: Exact result: exp(+Infinity) = +Infinity
        }
    else if(x > BIGX)
        {
        // Overflow
        errno = ERANGE;
        Ret.l =  POSINF;
        }
    else if(x < SMALLX)
        {
        // Underflow
        errno = ERANGE;
        Ret.f = 0.0;
        }
    else if(fabs(x) < eps.f)
        {
        Ret.f = 1.0;
        }
    else
        {
        float gP, Q;
        float g;
        int n;
        float xn;
        float z;
        // n = intrnd(x/ln(2))
        xn = x *INVLN2;
        if(xn < 0)
            n = (int)(xn - 0.5f);
        else
            n = (int)(xn + 0.5f);

        // xn = float(xn)
        xn = - (float) n;

        // Determine g

        g = (x + xn *C1) + xn *C2;

        // |g| <= ln(2)/2 at this point
        z = g *g;
        gP = ((z *p1) + p0) *g;
        Q = (z *q1) + q0;
        Ret.f = 0.5f + gP / (Q - gP);
        Ret.f = ldexp( Ret.f, n + 1);
        }
    return(Ret.f);
}

