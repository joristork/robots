/*----------------------------------------------------------------------*/
/* sinfcosf.c: sine/cosine elementary support function.         */
/*----------------------------------------------------------------------*/
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define PIOVER2         1.57079632679489661923f

#define TWOPI  6.28318530717958648f
//#define PI_C1  201.0f/64.0f
#define PI_C1  3.140625
#define PI_C2   9.67653589793E-4f
#define SINEPS  0.000244140625f
#define YMAX    12868.0f
#define INV_PI  0.31830988618379067154f
#define R1      -0.1666665668E+0f
#define R2       0.8333025139E-2f
#define R3      -0.1980741872E-3f
#define R4       0.2601903036E-5f

float _sincos(float x, unsigned char flag)
{
    int n;
    float y;
    float xn;
    float f;
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;

    type = _UnpkMath(*(tUfloat32 *)&x, &xIEEE);
    if(type & (_NANTYPE | _INFTYPE))
        {
        errno = EDOM;
        Ret.l = NaN;
        return Ret.f;
        }
    y = fabs(x);
    if(y >= YMAX)
        {
        // y = fmod(y, TWOPI);
        xn = y/TWOPI;
        xn = (long)xn;
        y = y - xn*TWOPI;
        }
    if(flag)
        {
        if(type == _ZEROTYPE)
            {
            return(x);
            }
        n = (int)(y *INV_PI + 0.5f);
        xn = (float) n;
        }
    else
        {
        if(type == _ZEROTYPE)
            {
            Ret.f = 1.0;
            return Ret.f;
//            return(1.0f);
            }
        xIEEE.sgn = 0;
        n = (int)(y *INV_PI + 1.0f);
        xn = (float) n;
        xn -= 0.5f;
        }
    if(n & 1)
        {
        xIEEE.sgn = xIEEE.sgn ? 0 : 1;
        }
    f = (y - xn *PI_C1) - xn *PI_C2;
    if(fabs(f) < SINEPS)
        {
        y = f;
        }
    else
        {
        float g, RR;
        g = f *f;
        RR = (((R4 *g + R3) *g + R2) *g + R1) *g;
        y = f + f *RR;
        }
    return((xIEEE.sgn)
               ? - y
               : y);
}
