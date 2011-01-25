////////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////


#define SQRT2 1.41421356
#define TWOBYPI 0.6366197724

float const ps[4] = {5.9304945, 21.125224, 8.9403076, 0.29730279};
float const qs[4] = {1.0000000, 15.035723, 17.764134, 2.4934718};

#define PIDIVBYTWO 1.57079633

float const pas[3] = {0.49559947, -4.6145309, 5.6036290};
float const qas[3] = {1.0000000,  -5.5484666, 5.6036290};

int const pibytwo[6] = {0x92, 0x1F, 0xB5, 0x44, 0x42, 0xD1};

float const sintable[5] = {2.75573192E-6, -1.98412698E-4, 8.33333333E-3,
                    -1.66666667E-1, 1.00000000};
float const costable[5] = {2.48015873E-5, -1.38888889E-3, 4.16666667E-2,
                    -5.00000000E-1, 1.00000000};

/* coefficients in series expansion for sine and cosine in
   reverse order */

float sqrt(float x)
{
   float y, res, r;
   int n;

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

/***********************************************************/
float SIN_COS(float x, unsigned int shift)
{
   float f, p, r, rs, *a;
   long n;
   int i, j;

   short neg = 0;

   if (x < 0.0)
   {
      x = -x;
      if (!shift)
      	 neg = 1;
   }

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

   if (shift & 0x1) {
      for (j = 0; j < 4; j++)
      {
         rs = costable[j];
         rs = rs*r*r + costable[j+1];
      }
   }
   else {
      for (j = 0; j < 4; j++)
      {
         rs = sintable[j];
         rs = rs*r*r + sintable[j+1];
      }
   }

   if (!(shift & 0x1))
      r = rs*r;        // r = sin(r)
   else
      r = rs;          // r = cos(r)

   f = ((shift & 0x2) ? -r : r);

   if (neg)
      return -f;

   else
   	return f;
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


float ASIN_COS(float x, int n)
{
   float y, res, r;
   int s;

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
      res = PIDIVBYTWO - 2.0*res;
   if (s)
      res = -res;
   if (n & 1)           // take arccos
      res = PIDIVBYTWO - res;

   return(res);
}


float asin(float x)
{
   float r;

   r = ASIN_COS(x, 0);
   return(r);
}


float acos(float x)
{
   float r;

   r = ASIN_COS(x, 1);
   return(r);
}

/************************************************************/

float const pat[4] = {0.17630401, 5.6710795, 22.376096, 19.818457};
float const qat[4] = {1.0000000,  11.368190, 28.982246, 19.818457};


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
      res = PIDIVBYTWO - res;
   if (s)
      res = -res;

   return(res);
}

/***********************************************************/

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


float floor(float x)
{
   float r;
   r = CEIL_FLOOR(x, 0);
}


float ceil(float x)
{
   float r;
   r = CEIL_FLOOR(x, 1);
}

/***********************************************************/

#define LN2 0.6931471806

float const pe[6] = {0.000207455774, 0.00127100575, 0.00965065093,
                     0.0554965651,  0.240227138,  0.693147172};

float exp(float x)
{

   float y, res, r;
   signed int n;
   int s;

   n = (signed int)(x/LN2);
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

float log(float x)
{
   float y, res, r;
   signed n;

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

float log10(float x)
{
   float r;

   r = log(x);
   r = r/LN10;
   return(r);
}

