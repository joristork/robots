//----------------------------------------------------------------------------------------------
//float acos(float x)
//
// Description:
//
//         inverse cosine
//
// Input:
//         float x
//
// Output:
//         inverse cosine of x
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float acos(float x)
{
    return(_asinacos(x, 1));
}


