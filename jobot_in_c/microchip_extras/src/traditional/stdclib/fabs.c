//-----------------------------------------------------------------------
// fabs.c: Floating-point absolute value function.
//
//-----------------------------------------------------------------------
#include "math.h"
//-----------------------------------------------------------------------

//-----------------------------------------------------------------------
//
// float fabs(float x)
//
//       Single-precision absolute value function.
//
// Input:
//
//       Floating-point number x
//
// Output:
//
//       Floating-point number |x|
//
// Description:
//
//       Computes the absolute value of the argument x.
//       
//-----------------------------------------------------------------------

float fabs(float x)
{ 
    char *pB = 3+(char *)&x;
    *pB &= 0x7F;
    return x;
}

