//----------------------------------------------------------------------------------------------
//float asin(float x)
//
// Description:
//
//         inverse sine
//
// Input:
//         float x
//
// Output:
//         inverse sine of x
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float asin(float x)
{
    return(_asinacos(x, 0));
}


