////////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,2001 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////
////
//// History:
////		* 9/20/2001	: Improvments are made to sin/cos code. The code
////						  now is smaller, much faster and more accurate.
////
////////////////////////////////////////////////////////////////////////////

#ifndef MATH_H
#define MATH_H


#undef PI
#define PI	3.141592654


#define SQRT2 				1.41421356

float const ps[4] = {5.9304945, 21.125224, 8.9403076, 0.29730279};
float const qs[4] = {1.0000000, 15.035723, 17.764134, 2.4934718};

///////////////////////////// Round Functions //////////////////////////////

float CEIL_FLOOR(float x, int n)
{
   float y, res;
   long l;
   int s;

   s = 0;
   y = x;

   if (x < 0)
   {
      s = 1;
      y = -y;
   }

   if (y <= 32768.0)
  res = (float)(long)y;

 else if (y < 10000000.0)
   {
  l = (long)(y/32768.0);
      y = 32768.0*(y/32768.0 - (float)l);
  res = 32768.0*(float)l;
  res += (float)(long)y;
 }

 else
  res = y;

 y = y - (float)(long)y;

 if (s)
  res = -res;

 if (y != 0)
 {
  if (s == 1 && n == 0)
   res -= 1.0;

  if (s == 0 && n == 1)
   res += 1.0;
 }
 if (x == 0)
    res = 0;

 return (res);
}

////////////////////////////////////////////////////////////////////////////
//	float floor(float x)
////////////////////////////////////////////////////////////////////////////
// Description : rounds down the number x.
// Date : N/A
//
float floor(float x)
{
   float r;
   r = CEIL_FLOOR(x, 0);
}

////////////////////////////////////////////////////////////////////////////
//	float ceil(float x)
////////////////////////////////////////////////////////////////////////////
// Description : rounds up the number x.
// Date : N/A
//
float ceil(float x)
{
   float r;
   r = CEIL_FLOOR(x, 1);
}

 ////////////////////////////////////////////////////////////////////////////
//	float fabs(float x)
////////////////////////////////////////////////////////////////////////////
// Description : Computes the absolute value of floating point number x
// Returns : returns the absolute value of x
// Date : N/A
//
#define fabs abs

////////////////////////////////////////////////////////////////////////////
//	float fmod(float x)
////////////////////////////////////////////////////////////////////////////
// Description : Computes the floating point remainder of x/y
// Returns : returns the value of x= i*y, for some integer i such that, if y
// is non zero, the result has the same isgn of x na dmagnitude less than the
// magnitude of y. If y is zero then a domain error occurs.
// Date : N/A
//

float fmod(float x,float y)
{
   float i;
   if (y!=0.0)
   {
      i=(x/y < 0.0)? ceil(x/y): floor(x/y);
      return(x-(i*y));
   }
   else
   {
   #ifdef _ERRNO
   {
      errno=EDOM;
   }
   #endif
   }
}

//////////////////// Exponential and logarithmic functions ////////////////////

#define LN2 0.6931471806

float const pe[6] = {0.000207455774, 0.00127100575, 0.00965065093,
                     0.0554965651,  0.240227138,  0.693147172};

////////////////////////////////////////////////////////////////////////////
//	float exp(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the value (e^x)
// Date : N/A
//
float exp(float x)
{

   float y, res, r;
   signed int n;
   int s;
   #ifdef _ERRNO
   if(x > 88.722838)
   {
      errno=ERANGE;
      return(0);
   }
   #endif
   n = (signed long)(x/LN2);
   s = 0;
   y = x;

   if (x < 0)
   {
      s = 1;
      n = -n;
      y = -y;
   }

   res = 0.0;
   *(&res) = n + 0x7F;

   y = y/LN2 - (float)n;

   r = pe[0]*y + pe[1];
   r = r*y + pe[2];
   r = r*y + pe[3];
   r = r*y + pe[4];
   r = r*y + pe[5];

   res = res*(1.0 + y*r);

   if (s)
      res = 1.0/res;
   return(res);
}

/************************************************************/

float const pl[4] = {0.45145214, -9.0558803, 26.940971, -19.860189};
float const ql[4] = {1.0000000,  -8.1354259, 16.780517, -9.9300943};

////////////////////////////////////////////////////////////////////////////
//	float log(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the the natural log of x
// Date : N/A
//
float log(float x)
{
   float y, res, r;
   signed n;
   #ifdef _ERRNO
   if(x <0)
   {
      errno=EDOM;
   }
   if(x ==0)
   {
      errno=ERANGE;
      return(0);
   }
   #endif
   y = x;
   if (y != 1.0)
   {
      *(&y) = 0x7E;

      y = (y - 1.0)/(y + 1.0);

      res = pl[0]*y*y + pl[1];
      res = res*y*y + pl[2];
      res = res*y*y + pl[3];

      r = ql[0]*y*y + ql[1];
      r = r*y*y + ql[2];
      r = r*y*y + ql[3];

      res = y*res/r;

      n = *(&x) - 0x7E;

      if (n<0)
         r = -(float)-n;
      else
         r = (float)n;

      res += r*LN2;
   }

   else
      res = 0.0;

   return(res);
}

#define LN10 2.30258509

////////////////////////////////////////////////////////////////////////////
//	float log10(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the the log base 10 of x
// Date : N/A
//
float log10(float x)
{
   float r;

   r = log(x);
   r = r/LN10;
   return(r);
}

////////////////////////////////////////////////////////////////////////////
//	float modf(float x)
////////////////////////////////////////////////////////////////////////////
// Description :breaks the argument value int integral and fractional parts,
// ach of which have the same sign as the argument.  It stores the integral part
// as a float in the object pointed to by the iptr
// Returns : returns the signed fractional part of value.
// Date : N/A
//

float modf(float value,float *iptr)
{
   *iptr=(value < 0.0)? ceil(value): floor(value);
   return(value - *iptr);
}


////////////////////////////////////////////////////////////////////////////
//	float pwr(float x,float y)
////////////////////////////////////////////////////////////////////////////
// Description : returns the value (x^y)
// Date : N/A
//
float pwr(float x,float y)
{
   if(x>=0)
     return(  exp(y*log(x)) );
   else
     return(  -exp(y*log(-x)) );
}


//////////////////// Power functions ////////////////////

////////////////////////////////////////////////////////////////////////////
//	float pow(float x,float y)
////////////////////////////////////////////////////////////////////////////
// Description : returns the value (x^y)
// Date : N/A
//
float pow(float x,float y)
{
   if(x>=0)
     return(  exp(y*log(x)) );
   else
     return(  -exp(y*log(-x)) );
}

////////////////////////////////////////////////////////////////////////////
//	float sqrt(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the square root of x
// Date : N/A
//
float sqrt(float x)
{
   float y, res, r;
   int n;
   #ifdef _ERRNO
   if( x < 0)
   {
      errno=EDOM;
   }
   #endif
   y = x;
   *(&y) = 0x7E;
   if (y >= 0.9994)
      res = 0.5 + 0.5*y;
   else
   {
      res = ps[0]*y + ps[1];
      res = res*y + ps[2];
      res = res*y + ps[3];

      r = qs[0]*y + qs[1];
      r = r*y + qs[2];
      r = r*y + qs[3];

      res = res/r;
   }

   n = *(&x);
   *(&res) = (n + 1)/2 + 63;
   if (n & 1)
      res = res/SQRT2;

   return(res);
}






////////////////////////////// Trig Functions //////////////////////////////
#undef PI_DIV_BY_TWO
#define PI_DIV_BY_TWO	1.570796326794896
#undef TWOBYPI
#define TWOBYPI 			0.6366197724
////////////////////////////////////////////////////////////////////////////
//	float cos(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the cosine value of the angle x, which is in radian
// Date : 9/20/2001
//
float cos(float x)
{
	float y, t, t2 = 1.0;
	int quad, i;
	float frac;
	float p[4] = {
		-0.499999993585,
		 0.041666636258,
		-0.0013888361399,
		 0.00002476016134
	};

	if (x < 0) x = -x; 				// absolute value of input

	quad = (int)(x / PI_DIV_BY_TWO);		// quadrant
	frac = (x / PI_DIV_BY_TWO) - quad;  // fractional part of input
	quad = quad % 4;							// quadrant (0 to 3)

	if (quad == 0 || quad == 2)
		t = frac * PI_DIV_BY_TWO;
	else if (quad == 1)
		t = (1-frac) * PI_DIV_BY_TWO;
	else // should be 3
		t = (frac-1) * PI_DIV_BY_TWO;

	y = 0.999999999781;
	t = t * t;
	for (i = 0; i <= 3; i++)
	{
		t2 = t2 * t;
		y = y + p[i] * t2;
	}

	if (quad == 2 || quad == 1)
		y = -y;  // correct sign

	return (y);
}

////////////////////////////////////////////////////////////////////////////
//	float sin(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the sine value of the angle x, which is in radian
// Date : 9/20/2001
//
float sin(float x)
{
	return cos(x - PI_DIV_BY_TWO);
}

////////////////////////////////////////////////////////////////////////////
//	float tan(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the tangent value of the angle x, which is in radian
// Date : 9/20/2001
//
float tan(float x)
{
   float c, s;

   c = cos(x);
	if (c == 0.0)
	   return (1.0e+36);

   s = sin(x);
   return(s/c);
}



float const pas[3] = {0.49559947, -4.6145309, 5.6036290};
float const qas[3] = {1.0000000,  -5.5484666, 5.6036290};

float ASIN_COS(float x, int n)
{
   float y, res, r;
   int s;
   #ifdef _ERRNO
   if(x <-1 || x > 1)
   {
      errno=EDOM;
   }
   #endif
   s = 0;
   y = x;

   if (x < 0)
   {
      s = 1;
      y = -y;
   }

   if (y > 0.5)
   {
      y = sqrt((1.0 - y)/2.0);
      n += 2;
   }

   res = pas[0]*y*y + pas[1];
   res = res*y*y + pas[2];

   r = qas[0]*y*y + qas[1];
   r = r*y*y + qas[2];

   res = y*res/r;

   if (n & 2)     // |x| > 0.5
      res = PI_DIV_BY_TWO - 2.0*res;
   if (s)
      res = -res;
   if (n & 1)           // take arccos
      res = PI_DIV_BY_TWO - res;

   return(res);
}


////////////////////////////////////////////////////////////////////////////
//	float asin(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the arcsine value of the value x.
// Date : N/A
//
float asin(float x)
{
   float r;

   r = ASIN_COS(x, 0);
   return(r);
}


////////////////////////////////////////////////////////////////////////////
//	float acos(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the arccosine value of the value x.
// Date : N/A
//
float acos(float x)
{
   float r;

   r = ASIN_COS(x, 1);
   return(r);
}

float const pat[4] = {0.17630401, 5.6710795, 22.376096, 19.818457};
float const qat[4] = {1.0000000,  11.368190, 28.982246, 19.818457};

////////////////////////////////////////////////////////////////////////////
//	float atan(float x)
////////////////////////////////////////////////////////////////////////////
// Description : returns the arctangent value of the value x.
// Date : N/A
//
float atan(float x)
{
   float y, res, r;
   int s, flag;

   s = 0;
   flag = 0;
   y = x;

   if (x < 0)
   {
      s = 1;
      y = -y;
   }

   if (y > 1.0)
   {
      y = 1.0/y;
      flag = 1;
   }

   res = pat[0]*y*y + pat[1];
   res = res*y*y + pat[2];
   res = res*y*y + pat[3];

   r = qat[0]*y*y + qat[1];
   r = r*y*y + qat[2];
   r = r*y*y + qat[3];

   res = y*res/r;


   if (flag)                              // for |x| > 1
      res = PI_DIV_BY_TWO - res;
   if (s)
      res = -res;

   return(res);
}

////////////////////////////////////////////////////////////////////////////
//	float atan2(float y, float x)
////////////////////////////////////////////////////////////////////////////
// Description :computes the principal value o arc tangent of y/x, using the
// signs of both the arguments to determine the quadrant of the return value
// Returns : returns the arc tangent of y/x.
// Date : N/A
//


float atan2(float y,float x)
{
   float z;
   int sign,quad;
   sign=0;
   quad=0; //quadrant
   quad=((y<=0.0)?((x<=0.0)?3:4):((x<0.0)?2:1));
   if(y<0.0)
   {
      sign=1;
      y=-y;
   }
   if(x<0.0)
   {
      x=-x;
   }
   if (x==0.0)
   {
      if(y==0.0)
      {
      #ifdef _ERRNO
      {
         errno=EDOM;
      }
      #endif
      }
      else
      {
         if(sign)
         {
         return (-(PI_DIV_BY_TWO));
         }
         else
         {
         return (PI_DIV_BY_TWO);
         }
      }
   }
   else
   {
      z=y/x;
      switch(quad)
      {
         case 1:
         {
            return atan(z);
            break;
         }
         case 2:
         {
            return (atan(z)+PI_DIV_BY_TWO);
            break;
         }
         case 3:
         {
            return (atan(z)-PI);
            break;
         }
         case 4:
         {
            return (-atan(z));
            break;
         }
      }
   }
}

//////////////////// Hyperbolic functions ////////////////////

////////////////////////////////////////////////////////////////////////////
//	float cosh(float x)
////////////////////////////////////////////////////////////////////////////
// Description : Computes the hyperbolic cosine value of x
// Returns : returns the hyperbolic cosine value of x
// Date : N/A
//



float cosh(float x)
{
   return ((exp(x)+exp(-x))/2);
}

////////////////////////////////////////////////////////////////////////////
//	float sinh(float x)
////////////////////////////////////////////////////////////////////////////
// Description : Computes the hyperbolic sine value of x
// Returns : returns the hyperbolic sine value of x
// Date : N/A
//

float sinh(float x)
{

   return ((exp(x) - exp(-x))/2);
}

////////////////////////////////////////////////////////////////////////////
//	float tanh(float x)
////////////////////////////////////////////////////////////////////////////
// Description : Computes the hyperbolic tangent value of x
// Returns : returns the hyperbolic tangent value of x
// Date : N/A
//

float tanh(float x)
{
   return(sinh(x)/cosh(x));
}

////////////////////////////////////////////////////////////////////////////
//	float frexp(float x, signed int *exp)
////////////////////////////////////////////////////////////////////////////
// Description : breaks a floating point number into a normalized fraction and an integral
// power of 2. It stores the integer in the signed int object pointed to by exp.
// Returns : returns the value x, such that x is a double with magnitude in the interval
// [1/2,1) or zero, and value equals x times 2 raised to the power *exp.If value is zero,
// both parts of the result are zero.
// Date : N/A
//



#define LOG2 .30102999566398119521
float frexp(float x, signed int *exp)
{
   float res,f;
   int sign=0;
   int i,i1;
   if(x==0.0)
   {
      *exp=0;
      return (0.0);
   }
   if(x< 0.0)
   {
     x=-x;
     sign=1;
   }
   if (x >1.0)
   {
      *exp=(ceil(log10(x)/LOG2));
      res=x/(pow(2,*exp));
      if (res ==1)
      {
         *exp=*exp+1;
          res=.5;
      }
   }
   else
   {
      if(x<.5)
      {
         *exp=-1;
         res=x*2;
      }
      else
      {
         *exp=0;
          res=x;
      }
   }
   if(sign)
   {
      res=-res;
   }
   return res;
}

////////////////////////////////////////////////////////////////////////////
//	float ldexp(float x, signed int *exp)
////////////////////////////////////////////////////////////////////////////
// Description : multiplies a floating point number by an integral power of 2.
// Returns : returns the value of x times 2 raised to the power exp.
// Date : N/A
//

float ldexp(float value, signed int exp)
{
   return (value * pow(2,exp));
}
#endif
