////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#define TWOBYPI 0.6366197724;

static int pibytwo[6] = {0x92, 0x1F, 0xB5, 0x44, 0x42, 0xD1};

/* pibytwo[6] = {146.0, 31.0, 181.0, 68.0, 66.0, 209.0} */

static float s[5] = {2.75573192E-6, -1.98412698E-4, 8.33333333E-3,
                    -1.66666667E-1, 1.00000000};
static float c[5] = {2.48015873E-5, -1.38888889E-3, 4.16666667E-2,
                    -5.00000000E-1, 1.00000000};

/* coefficients in series expansion for sine and cosine in
   reverse order */

float SIN_COS(float x, unsigned int shift)
{
   float f, p, r, rs, *a;
   long n;
   int i, j;

   f = x*TWOBYPI;
   n = (long)(f > 0 ? f+0.5 : f-0.5);
   f = (float)n;
   r = x - f;

   i = 0;
   p = (float)pibytwo[0];
   f = f/256.0;
   r -= f*p;

   i = 1;
   p = (float)pibytwo[1];
   f = f/256.0;
   r -= f*p;

   i = 2;
   p = (float)pibytwo[2];
   f = f/256.0;
   r -= f*p;

   i = 3;
   p = (float)pibytwo[3];
   f = f/256.0;
   r -= f*p;

   i = 4;
   p = (float)pibytwo[4];
   f = f/256.0;
   r -= f*p;

   shift += (unsigned int)(n & 0x3);
   
   if (shift & 0x1)
      a = &c[0];
   else
      a = &s[0];

   for (rs = *a, j = 1; j < 5; j++)
   {
      a++;
      rs = rs*r*r + *a;		
   }
   
   if (!(shift & 0x1))
      r = rs*r;        // r = sin(r)
   else
      r = rs;          // r = cos(r)

   return((shift & 0x2) ? -r : r);
}


float sin(float x)
{
   return(SIN_COS(x, 0));
}


float cos(float x)
{
   return(SIN_COS(x, 1));
}


float tan(float x)
{
   float c, s;

   c = SIN_COS(x, 1);
   s = SIN_COS(x, 0);
   if (c != 0.0)
      return(s/c);
   return(1.0e+36);
}

