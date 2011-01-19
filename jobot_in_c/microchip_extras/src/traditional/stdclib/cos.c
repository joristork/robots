//----------------------------------------------------------------------------------------------
//float cos(float x)
//
// Description:
//
//         cos
//
// Input:
//         float x
//
// Output:
//         cos(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float cos(float x)
{
    return(_sincos(x, 0));
}


