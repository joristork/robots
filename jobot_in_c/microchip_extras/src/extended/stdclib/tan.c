//----------------------------------------------------------------------------------------------
//float tan(float x)
//
// Description:
//
//         tan
//
// Input:
//         float x
//
// Output:
//         tan(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

#define YMAX        6433.0f
#define TWO_OVER_PI 0.63661977236758134308f
#define TANC1       (201.0f/128.0f)
#define TANC2       4.83826794897e-4f

#define TANEPS      0.000244140625f

#define TANP0       +0.100000000e+1f
#define TANP1       -0.958017723e-1f
#define TANQ0       +0.100000000e+1f
#define TANQ1       -0.429135777e+0f
#define TANQ2       +0.971685835e-2f

float tan(float x)
{
    int n;
    float xn;
    float f, g;
    float xnum, xden;
    FTYPE type;
    tUfloat32 Ret;
    tIEEE xIEEE;

    type = _UnpkMath(*(tUfloat32 *)&x, &xIEEE);
    if(type &( _NANTYPE | _INFTYPE) )
        {
        errno = EDOM;
        Ret.l = NaN;
        return Ret.f;
        }


    n = (long)(x *TWO_OVER_PI + (x < 0
                                     ? - 0.5f
                                     : 0.5f));
    xn = (float) n;
    f = (x - xn *TANC1) - xn *TANC2;
    if(fabs(f) < TANEPS)
        {
        xnum = f;
        xden = 1.0f;
        }
    else
        {
        g = f *f;
        xnum = (TANP1 *g) *f + f;
        xden = (TANQ2 *g + TANQ1) *g + TANQ0;
        }
    if((n & 1) == 0)
        {
        Ret.f = xnum / xden;
        }
    else
        {
        Ret.f = xden / (-xnum);
        }
    return(Ret.f);
}

