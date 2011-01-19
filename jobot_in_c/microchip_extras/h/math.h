/* $Id: math.h,v 1.4 2006/05/09 22:30:05 nairnj Exp $ */
#ifndef __MATH_H
#define __MATH_H

typedef float float_t;
typedef float double_t;

#define HUGE_VAL 6.3e38
#define HUGE_VALL 6.3e38
#define HUGE_VALF 6.3e38

float fabs (auto float x);
float ldexp (auto float x, auto int n);
float exp (auto float f);
float sqrt (auto float x);
float asin (auto float x);
float acos (auto float x);
float atan2 (auto float y, auto float x);
float atan (auto float x);
float sin (auto float x);
float cos (auto float x);
float tan (auto float x);
float sinh (auto float x);
float cosh (auto float x);
float tanh (auto float x);
float frexp (auto float x, auto int *pexp);
float log10 (auto float x);
float log (auto float x);
float pow (auto float x, auto float y);
float ceil (auto float x);
float floor (auto float x);
float modf (auto float x, auto float *ipart);
float fmod (auto float x, auto float y);

float mchptoieee (auto unsigned long v);
unsigned long ieeetomchp (auto float v);

#endif
