//----------------------------------------------------------------------------------------------
//float sin(float x)
//
// Description:
//
//         sin
//
// Input:
//         float x
//
// Output:
//         sin(x)
//
//----------------------------------------------------------------------------------------------
#include <errno.h>
#include <math.h>
#include "_mathlib.h"

float sin(float x)
{
    return(_sincos(x, 1));
}


