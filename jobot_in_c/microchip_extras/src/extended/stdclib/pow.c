//----------------------------------------------------------------------------------------------
//float pow(float x, float y)
//
// Description:
//
//         power function
//
// Input:
//         float x
//         float y
//
// Output:
//         x^y
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define MMAX            128
#define NMAX            32767                   /* max integer */
#define LN2             (0.69314718055994530941723212145817658f)
#define SQRTHALF        (0.70710678118654752440084436210484905f)
#define _INT            1
#define _EVEN           2
#define _ODD            4

float pow(float x, float y)
{
    FTYPE xType, yType;
    tUfloat32 Ret;
    tIEEE xIEEE, yIEEE;

    float f;
    unsigned char sgn;
    unsigned char yChopped, yIntegral;


    Ret.f = x;
    xType = _UnpkMath(*(tUfloat32 *)& x, &xIEEE);
    yType = _UnpkMath(*(tUfloat32 *)& y, &yIEEE);
    f = fabs(x);

    _fchop(*(tUfloat32 *)&y, 0, &yChopped);

    if(yChopped)
        {
        yIntegral = 0;
        }
    else
        {
        _fchop(*(tUfloat32 *)&y, 1, &yChopped);
        if(yChopped == 0)
            {
            yIntegral = _INT | _EVEN;
            }
        else
            {
            yIntegral = _INT | _ODD;
            }
        }
    if((yType == _ZEROTYPE) || (x == 1.0f))
        {
        Ret.f = 1.0f;
        }
    else if((xType == _NANTYPE) || (yType == _NANTYPE))
        {
        Ret.l = NaN;
        }
    else if(xType == _ZEROTYPE)
        {
        if(yIEEE.sgn)
            {
            Ret.l = POSINF;
            errno = EDOM;
            }
        else
            {
            Ret.f = 0.0f;
            }
        goto signit;
        }
    else if(xType == _INFTYPE)
        {
        if(yIEEE.sgn)
            {
            Ret.f = 0.0f;
            }
        else
            {
            Ret.l = POSINF;
            }
signit:
        if(yIntegral & _ODD)
            {
            if(xIEEE.sgn)
                {
                Ret.f = - Ret.f;
                }
            }
        }
    else if(yType == _INFTYPE)
        {
        /*
                ** x is finite
                */
        if(f == 1.0f)
            {
            Ret.f = 1.0f;
            }
        else if(yIEEE.sgn)
            {
            if(f < 1.0f)
                {
                Ret.l = POSINF;
                }
            else
                {
                Ret.f = 0.0f;
                }
            }
        else
            {
            if(f < 1.0f)
                {
                Ret.f = 0.0f;
                }
            else
                {
                Ret.l = POSINF;
                }
            }
        }
    else if((xIEEE.sgn) && !(yIntegral & _INT))
        {
        errno = EDOM;
        Ret.l = NaN;
        }
    else if(y == 1.0f)
        {
        Ret.f = x;
        }
    else
        {
        int k;
        long ml, nl;
        float kf;
        float z, w;
        tUfloat32 p;
        f = frexp(x, &k);
        if(!xIEEE.sgn)
            {
            sgn = 0;
            }
        else
            {
            /*
                        ** f < 0
                        */
            f = - f;
            /*
                        ** negate Ret.f for y an odd integer
                        */
            sgn = yIntegral & _ODD;
            }
        if(f < SQRTHALF)
            {
            /*
                        ** sqrt(.5) <= f <= sqrt(2)
                        */
            f = ldexp(f, 1);    /* f = f * 2 */
            --k;
            }
        ml = (long) y;
        kf = (float) k;
        nl = (long)(kf * y);
        if((nl < - NMAX) || (nl > NMAX))
            {
            p.l = 0;
            }
        else
            {
            int i;
            int m, n;
            float g, h,y2;
            tUfloat32 y1;
            /*
                        ** compute f^m * e^w * 2^n
                        */

            if(ml < - MMAX)
                {
                n = k < 0
                    ? MMAX
                    : k == 0
                    ? 0
                    : - MMAX;
                m = 0;
                w = 0;
                }
            else if(MMAX < ml)
                {
                n = k < 0
                    ? - MMAX
                    : k == 0
                    ? 0
                    : MMAX;
                m = 0;
                w = 0;
                }
            else
                {
                m = (int) ml;
                n = (int) nl;
                i = yIEEE.exp - FLT_BIAS - 8;
                y1 = _fchop(*(tUfloat32 *)&y, i, &yChopped);
                y2 = y - y1.f;
                h = (kf *y1.f - n) + kf *y2;
                w = h *LN2;
                }
            g = y - m;
            w += g *log(f);
            if(m < 0)
                {
                m = - m;
                }
            z = 1.0f;
            while(m)
                {
                if((m & 1) != 0)
                    {
                    z *= f;
                    }
                m >>= 1;
                f *= f;
                }
            if(yIEEE.sgn)
                {
                z = 1.0 / z;
                }
            p.f = ldexp(z *exp(w), n);
            }

        // using xType & xIEEE for p
        xType = _UnpkMath(p, &xIEEE);
        if( xType & (_INFTYPE | _NANTYPE | _ZEROTYPE) )
            {
            errno = ERANGE;
            if(((fabs(x) < 1) && (yIEEE.sgn))
               ||
               ((fabs(x) > 1) && (!yIEEE.sgn)))
                {
                p.l = POSINF;
                }
            else
                {
                p.f = 0.0f;
                }
            }
        Ret.f = (sgn != 0
                      ? - p.f
                      : p.f);
        }
    return(Ret.f);
}

