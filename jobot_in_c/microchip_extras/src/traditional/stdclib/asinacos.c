/*----------------------------------------------------------------------*/
/* asinfacosf.s: arcsine/arccosine elementary support function.         */
/*----------------------------------------------------------------------*/
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define ASINEPS  0.000244140625f
#define ASINP1  +0.933935835e+0f
#define ASINP2  -0.504400557E+0f
#define ASINQ0  +0.560363004E+1f
#define ASINQ1  -0.554846723E+1f
#define PIOVER2 +1.57079632679489662f
#define PI      +3.14159265358979324f
#define NEGTWO  -2.0f

float _asinacos(float x, unsigned char flag)
{       
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;
    unsigned char flg;
    float P, Q, R;
    float y;
    float g;
    Ret.f = x;
    type = _UnpkMath(Ret, &xIEEE);
    if(type & (_NANTYPE | _INFTYPE))
        {
        errno = EDOM;
        Ret.l = NaN;
        }
    y = fabs(x);
    if(y < ASINEPS)
        {
        flg = flag;
        Ret.f = y;
        }
    else 
        {
        if(y > 1.0f)
            {
            Ret.l = POSNAN;
            errno = EDOM;
            return(Ret.f);
            }
        else if(y > 0.5f)
            {
            flg = 1 - flag;
            g = 0.5f *(1.0f - y);
            y = NEGTWO *sqrt(g);
            }
        else 
            {
            flg = flag;
            g = y *y;
            }
        Q = (g + ASINQ1) *g + ASINQ0;
        P = ASINP2 *g + ASINP1;
        R = g *P / Q;
        Ret.f = y + y *R;
        }
    if(flag == 0)
        {
        // asin(x)
        if(flg)
            {
            Ret.f = Ret.f + PIOVER2;
            }
        if(xIEEE.sgn)
            {
            Ret.f = - Ret.f;
            }
        }
    else 
        {
        // acos(x)
        if(xIEEE.sgn)
            {
            if(flg)
                {
                Ret.f = Ret.f + PIOVER2;
                }
            else 
                {
                Ret.f = Ret.f + PI;
                }
            }
        else 
            {
            Ret.f = - Ret.f;
            if(flg)
                {
                Ret.f = Ret.f + PIOVER2;
                }
            }
        }
    return(Ret.f);
}
