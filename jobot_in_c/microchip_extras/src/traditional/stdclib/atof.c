// RCS Header $Id: atof.c,v 1.1 2003/12/09 22:53:19 GrosbaJ Exp $
// $Revision: 1.1 $

#include "stdlib.h"

typedef unsigned char BYTE;

/** @name atof
 * The {\bf atof} function converts the initial portion of the string pointed
 * to by {\bf s} into a floating-point {\bf double} representation.
 * @param s pointer to the string to convert
 * @return The {\bf atof} function returns the converted value
 */
// double atof (const char *s);

char *atolfix( const char *s, char *decpt, long *value );

double atof (const char *s)
{
char *pS;
long Lng;
char Decpt;
double dbl;
double f;
unsigned char Neg = 0;

   // Convert characters precceding exponent to a long integer and the power
   // of ten such that Lng * 10 ^ Decpt == original string before exponent.
   pS = atolfix(s, &Decpt, &Lng );


   // Get exponent if present
   if( (*pS & 0xDF) == 'E' )
      {
      pS++;    
      Decpt += atoi( (const char *)pS);
      }

   dbl = (double)Lng;

   if( Decpt == 0 )
      return dbl;
   else if( Decpt < 0 )
      {
      // Treat negative power of ten as positive and divide later
      Neg = 1;
      Decpt = -Decpt;
      }

   // Convert power of ten to floating point number
   f = 1.0;
   if( Decpt >= 20 )
      {
      f = f * 1.0E20;
      Decpt -= 20;
      }
   if( Decpt >= 10 )
      {
      f = f * 1.0E10;
      Decpt -= 10;
      }
   if( Decpt >= 5 )
      {
      f = f * 1.0E5;
      Decpt -= 5;
      }
   while( Decpt >= 2 )
      {
      f = f * 1.0E2;
      Decpt -= 2;
      }
   if( Decpt >= 1 )
      {
      f = f * 10;
      Decpt -= 1;
      }

   // Combine power of ten and long.
   if( Neg )
      dbl = dbl/f;
   else
      dbl = dbl * f;

   return dbl;
}


