////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#ifndef _STDLIB
#define _STDLIB

//---------------------------------------------------------------------------
// Definitions and types
//---------------------------------------------------------------------------

#ifndef RAND_MAX
#define RAND_MAX  32767    // The value of which is the maximum value
                           // ... returned by the rand function
#endif

typedef struct {
   signed int quot;
   signed int rem;
} div_t;

typedef struct {
   signed long quot;
   signed long rem;
} ldiv_t;

#include <stddef.h>

//---------------------------------------------------------------------------
// String conversion functions
//---------------------------------------------------------------------------

/* Standard template: float atof(char * s)
 * converts the initial portion of the string s to a float.
 * returns the converted value if any, 0 otherwise
 */
float atof(char * s);

/* Standard template: signed int  atoi(char * s)
 * converts the initial portion of the string s to a signed int
 * returns the converted value if any, 0 otherwise
 */
signed int atoi(char *s);

/* Syntax: signed int32  atoi32(char * s)
   converts the initial portion of the string s to a signed int32
   returns the converted value if any, 0 otherwise*/
signed int32 atoi32(char *s);

/* Standard template: signed long  atol(char * s)
 * converts the initial portion of the string s to a signed long
 * returns the converted value if any, 0 otherwise
 */
signed long atol(char *s);

/* Standard template: float strtol(char * s,char *endptr)
 * converts the initial portion of the string s to a float
 * returns the converted value if any, 0 otherwise
 * the final string is returned in the endptr, if endptr is not null
 */
float strtod(char *s,char *endptr);

/* Standard template: long strtoul(char * s,char *endptr,signed int base)
 * converts the initial portion of the string s, represented as an
 * integral value of radix base  to a signed long.
 * Returns the converted value if any, 0 otherwise
 * the final string is returned in the endptr, if endptr is not null
 */
signed long strtol(char *s,char *endptr,signed int base);

/* Standard template: long strtoul(char * s,char *endptr,signed int base)
 * converts the initial portion of the string s, represented as an
 * integral value of radix base to a unsigned long.
 * returns the converted value if any, 0 otherwise
 * the final string is returned in the endptr, if endptr is not null
 */
long strtoul(char *s,char *endptr,signed int base);

//---------------------------------------------------------------------------
// Pseudo-random sequence generation functions
//---------------------------------------------------------------------------

/* The rand function computes a sequence of pseudo-random integers in
 * the range 0 to RAND_MAX
 *
 * Parameters:
 *       (none)
 *
 * Returns:
 *       The pseudo-random integer
 */
int32 rand(void);

/* The srand function uses the argument as a seed for a new sequence of
 * pseudo-random numbers to be returned by subsequent calls to rand.
 *
 * Parameters:
 *       [in] seed: The seed value to start from. You might need to pass
 *
 * Returns:
 *       (none)
 *
 * Remarks
 *          The srand function sets the starting point for generating
 *       a series of pseudorandom integers. To reinitialize the
 *       generator, use 1 as the seed argument. Any other value for
 *       seed sets the generator to a random starting point. rand
 *       retrieves the pseudorandom numbers that are generated.
 *       Calling rand before any call to srand generates the same
 *       sequence as calling srand with seed passed as 1.
 *          Usually, you need to pass a time here from outer source
 *       so that the numbers will be different every time you run.
 */
void srand(unsigned int32 seed);

//---------------------------------------------------------------------------
// Memory management functions
//---------------------------------------------------------------------------

// Comming soon

//---------------------------------------------------------------------------
// Communication with the environment
//---------------------------------------------------------------------------

/* The function returns 0 always
 */
signed int system(char *string);

//---------------------------------------------------------------------------
// Searching and sorting utilities
//---------------------------------------------------------------------------

/* Performs a binary search of a sorted array..
 *
 * Parameters:
 *       [in] key: Object to search for
 *       [in] base: Pointer to base of search data
 *       [in] num: Number of elements
 *       [in] width: Width of elements
 *       [in] compare: Function that compares two elements
 *
 * Returns:
 *       bsearch returns a pointer to an occurrence of key in the array pointed
 *       to by base. If key is not found, the function returns NULL. If the
 *       array is not in order or contains duplicate records with identical keys,
 *       the result is unpredictable.
 */
//void *bsearch(const void *key, const void *base, size_t num, size_t width,
//              int (*compare)(const void *, const void *));

/* Performs the shell-metzner sort (not the quick sort algorithm). The contents
 * of the array are sorted into ascending order according to a comparison
 * function pointed to by compar.
 *
 * Parameters:
 *       [in] base: Pointer to base of search data
 *       [in] num: Number of elements
 *       [in] width: Width of elements
 *       [in] compare: Function that compares two elements
 *
 * Returns:
 *       (none)
 */
//void *qsort(const void *base, size_t num, size_t width,
//              int (*compare)(const void *, const void *));

//---------------------------------------------------------------------------
// Integer arithmetic functions
//---------------------------------------------------------------------------

#define labs abs

div_t div(signed int numer,signed int denom);
ldiv_t ldiv(signed long numer,signed long denom);

//---------------------------------------------------------------------------
// Multibyte character functions
//---------------------------------------------------------------------------

// Not supported

//---------------------------------------------------------------------------
// Multibyte string functions
//---------------------------------------------------------------------------

// Not supported


//---------------------------------------------------------------------------
// Internal implementation
//---------------------------------------------------------------------------

#include <stddef.h>
#include <string.h>

div_t div(signed int numer,signed int denom)
{
   div_t val;
   val.quot = numer / denom;
   val.rem = numer - (denom * val.quot);
   return (val);
}

ldiv_t ldiv(signed long numer,signed long denom)
{
   ldiv_t val;
   val.quot = numer / denom;
   val.rem = numer - (denom * val.quot);
   return (val);
}

float atof(char * s)
{
   float pow10 = 1.0;
   float result = 0.0;
   int sign = 0, point = 0;
   char c;
   int ptr = 0;

   if(s)
   {
      c = s[ptr++];
   }

   while ((c>='0' && c<='9') || c=='+' || c=='-' || c=='.') {
      if(c == '-') {
         sign = 1;
         c = s[ptr++];
      }

      while((c >= '0' && c <= '9') && point == 0) {
         result = 10*result + c - '0';
         c = s[ptr++];
      }

      if (c == '.') {
         point = 1;
         c = s[ptr++];
      }

      while((c >= '0' && c <= '9') && point == 1) {
         pow10 = pow10*10;
         result += (c - '0')/pow10;
         c = s[ptr++];
      }
   }

   if (sign == 1)
      result = -1*result;
   return(result);
}

signed int atoi(char *s)
{
   signed int result;
   int sign, base, index;
   char c;

   index = 0;
   sign = 0;
   base = 10;
   result = 0;

   // Omit all preceeding alpha characters
   if(s)
      c = s[index++];

   // increase index if either positive or negative sign is detected
   if (c == '-')
   {
      sign = 1;         // Set the sign to negative
      c = s[index++];
   }
   else if (c == '+')
   {
      c = s[index++];
   }

   if (c >= '0' && c <= '9')
   {

      // Check for hexa number
      if (c == '0' && (s[index] == 'x' || s[index] == 'X'))
      {
         base = 16;
         index++;
         c = s[index++];
      }

      // The number is a decimal number
      if (base == 10)
      {
         while (c >= '0' && c <= '9')
         {
            result = 10*result + (c - '0');
            c = s[index++];
         }
      }
      else if (base == 16)    // The number is a hexa number
      {
         while (c = toupper(c), (c >= '0' && c <= '9') || (c >= 'A' && c<='F'))
         {
            if (c >= '0' && c <= '9')
               result = (result << 4) + (c - '0');
            else
               result = (result << 4) + (c - 'A' + 10);

            c = s[index++];
         }
      }
   }

   if (sign == 1 && base == 10)
       result = -result;

   return(result);
}

signed long atol(char *s)
{
   signed long result;
   int sign, base, index;
   char c;

   index = 0;
   sign = 0;
   base = 10;
   result = 0;

   if(s)
      c = s[index++];

   // increase index if either positive or negative sign is detected
   if (c == '-')
   {
      sign = 1;         // Set the sign to negative
      c = s[index++];
   }
   else if (c == '+')
   {
      c = s[index++];
   }

   if (c >= '0' && c <= '9')
   {
      if (c == '0' && (s[index] == 'x' || s[index] == 'X'))
      {
         base = 16;
         index++;
         c = s[index++];
      }

      // The number is a decimal number
      if (base == 10)
      {
         while (c >= '0' && c <= '9')
         {
            result = 10*result + (c - '0');
            c = s[index++];
         }
      }
      else if (base == 16)    // The number is a hexa number
      {
         while (c = toupper(c), (c >= '0' && c <= '9') || (c >= 'A' && c <='F'))
         {
            if (c >= '0' && c <= '9')
               result = (result << 4) + (c - '0');
            else
               result = (result << 4) + (c - 'A' + 10);

            c = s[index++];
         }
      }
   }

   if (base == 10 && sign == 1)
      result = -result;

   return(result);
}

/* A fast routine to multiply by 10
 */
signed int32 mult_with10(int32 num)
{
   return ( (num << 1) + (num << 3) );
}

signed int32 atoi32(char *s)
{
   signed int32 result;
   int sign, base, index;
   char c;

   index = 0;
   sign = 0;
   base = 10;
   result = 0;

   if(s)
      c = s[index++];

   // increase index if either positive or negative sign is detected
   if (c == '-')
   {
      sign = 1;         // Set the sign to negative
      c = s[index++];
   }
   else if (c == '+')
   {
      c = s[index++];
   }

   if (c >= '0' && c <= '9')
   {
      if (c == '0' && (s[index] == 'x' || s[index] == 'X'))
      {
         base = 16;
         index++;
         c = s[index++];
      }

      // The number is a decimal number
      if (base == 10)
      {
         while (c >= '0' && c <= '9') {
            result = (result << 1) + (result << 3);  // result *= 10;
            result += (c - '0');
            c = s[index++];
         }
      }
      else if (base == 16)    // The number is a hexa number
      {
         while (c = toupper(c), (c >= '0' && c <= '9') || (c >= 'A' && c <='F'))
         {
            if (c >= '0' && c <= '9')
               result = (result << 4) + (c - '0');
            else
               result = (result << 4) + (c - 'A' + 10);

            c = s[index++];
         }
      }
   }

   if (base == 10 && sign == 1)
      result = -result;

   return(result);
}

float strtod(char *s,char *endptr) {
   float pow10 = 1.0;
   float result = 0.0;
   int sign = 0, point = 0;
   char c;
   int ptr = 0;

   if(s)
   {
      c=s[ptr++];
   }

   while((c>='0' && c<='9') || c=='+' || c=='-' || c=='.') {
      if(c == '-') {
         sign = 1;
         c = s[ptr++];
      }

      while((c >= '0' && c <= '9') && point == 0) {
         result = 10*result + c - '0';
         c = s[ptr++];
      }

      if (c == '.') {
         point = 1;
         c = s[ptr++];
      }

      while((c >= '0' && c <= '9') && point == 1) {
         pow10 = pow10*10;
         result += (c - '0')/pow10;
         c = s[ptr++];
      }
   }

   if (sign == 1)
      result = -1*result;
   if(endptr)
   {
      if (ptr) {
         ptr--;
         *((char *)endptr)=s+ptr;
      }
      else
         *((char *)endptr)=s;
   }

   return(result);
}

long strtoul(char *s,char *endptr,signed int base)
{
   char *sc,*s1,*s2,*sd;
   unsigned long x=0;
   char sign;
   char digits[]="0123456789abcdefghijklmnopqstuvwxyz";
   for(sc=s;isspace(*sc);++sc);
   sign=*sc=='-'||*sc=='+'?*sc++:'+';
   if(sign=='-')
   {
      if (endptr)
      {
        *((char *)endptr)=s;
      }
      return 0;
   }

   if (base <0 || base ==1|| base >36) // invalid base
   {
      if (endptr)
      {
        *((char *)endptr)=s;
      }
      return 0;
   }
   else if (base)
   {
      if(base==16 && *sc =='0'&&(sc[1]=='x' || sc[1]=='X'))
         sc+=2;
      if(base==8 && *sc =='0')
         sc+=1;
      if(base==2 && *sc =='0'&&sc[1]=='b')
         sc+=2;

   }
   else if(*sc!='0') // base is 0, find base
      base=10;
   else if (sc[1]=='x' || sc[1]=='X')
      base =16,sc+=2;
   else if(sc[1]=='b')
      base=2,sc+=2;
   else
      base=8;
   for (s1=sc;*sc=='0';++sc);// skip leading zeroes
   for(s2=sc;(sd=memchr(digits,tolower(*sc),base))!=0;++sc)
   {
      x=x*base+(sd-digits);
   }
   if(s1==sc)
   {
      if (endptr)
      {
        *((char *)endptr)=s;
      }
   return 0;
   }
   if (endptr)
        *((char *)endptr)=sc;
   return x;
}

signed long strtol(char *s,char *endptr,signed int base)
{
   char *sc,*s1,*s2,*sd;
   signed long x=0;
   char sign;
   char digits[]="0123456789abcdefghijklmnopqstuvwxyz";
   for(sc=s;isspace(*sc);++sc);
   sign=*sc=='-'||*sc=='+'?*sc++:'+';
   if (base <0 || base ==1|| base >36) // invalid base
   {
      if (endptr)
      {
        *((char *)endptr)=s;
      }
      return 0;
   }
   else if (base)
   {
      if(base==16 && *sc =='0'&&(sc[1]=='x' || sc[1]=='X'))
         sc+=2;
      if(base==8 && *sc =='0')
         sc+=1;
      if(base==2 && *sc =='0'&&sc[1]=='b')
         sc+=2;

   }
   else if(*sc!='0') // base is 0, find base
      base=10;
   else if (sc[1]=='x' || sc[1]=='X')
      base =16,sc+=2;
   else if(sc[1]=='b')
      base=2,sc+=2;
   else
      base=8;
   for (s1=sc;*sc=='0';++sc);// skip leading zeroes
   for(s2=sc;(sd=memchr(digits,tolower(*sc),base))!=0;++sc)
   {
      x=x*base+(sd-digits);
   }
   if(s1==sc)
   {
      if (endptr)
      {
        *((char *)endptr)=s;
      }
   return 0;
   }
   if(sign=='-')
      x  =-x;
   if (endptr)
        *((char *)endptr)=sc;
   return x;
}

signed int system(char *string)
{
   return 0;
}

int mblen(char *s,size_t n)
{
   return strlen(s);
}

int mbtowc(wchar_t *pwc,char *s,size_t n)
{
   *pwc=*s;
   return 1;
}

int wctomb(char *s,wchar_t wchar)
{
   *s=wchar;
   return 1;
}

size_t mbstowcs(wchar_t *pwcs,char *s,size_t n)
{
   strncpy(pwcs,s,n);
   return strlen(pwcs);
}

size_t wcstombs(char *s,wchar_t *pwcs,size_t n)
{
   strncpy(s,pwcs,n);
   return strlen(s);
}

//---------------------------------------------------------------------------
// The random number implementation
//---------------------------------------------------------------------------

unsigned int32 _Randseed = 1;

long rand(void)
{
   _Randseed = _Randseed * 1103515245 + 12345;
   return ((unsigned long)(_Randseed >> 16) % RAND_MAX);
}

void srand(unsigned int32 seed)
{
   _Randseed = seed;
}

//---------------------------------------------------------------------------
// Searching and sorting utilities implementation
//---------------------------------------------------------------------------
/*
void *bsearch(const void *key, const void *base, size_t num, size_t width,
              int (*compare)(const void *, const void *))
{
   char *p, *q;
   size_t n;
   size_t pivot;
   int val;

   p = base;
   n = num;

   while (n > 0)
   {
      pivot = n >> 1;
      q = p + size * pivot;

      val = compare(key, q);

      if (val < 0)
         n = pivot;
      else if (val == 0)
         return ((void *)q);
      else {
         p = q + size;
         n -= pivot + 1;
      }
   }

   return NULL;      // There's no match
}

void *qsort(const void *base, size_t num, size_t width,
              int (*compare)(const void *, const void *))
{
   char *p, *q, *pend, tmp;
   size_t pivot, i;
   short bDone;
   int val;

   pend = base + num * width ;
   pivot = num;

   while (pivot > 1) {
      pivot >>= 1;

      do {
         bDone = 1;
         p = base;
         q = p + width * pivot;
         while (q < pend) {
            val = compare(p, q);

            if (val > 0) {
               // swap *q and *p
               for (i = 0; i < width; i++) {
                  tmp = *p;
                  *p = *q;
                  *q = tmp'

                  p++; q++;
               }

               bDone = 0;
            }

            if (bDone) {
               p += width;
               q += width;
            }
         }
      } while (!bDone);
   }
}
*/

#endif
