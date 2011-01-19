/* $Id: stokrp.c,v 1.1 2003/12/09 22:43:29 GrosbaJ Exp $ */

#include "string.h"

/** @name strtokrampgm
 * The {\bf strtokrampgm} function performs a {\bf strtok} where {\bf s1}
 * points to program memory and {\bf s2} point to data memory.
 * @param s1 pointer to destination in program memory
 * @param s2 pointer to source in data memory
 */
//rom char *strtokrampgm (rom char *s1, const char *s2);

static rom char *PreStr = NULL;

rom char *strtokrampgm (rom char *s1, const char *s2)
{
   rom char *Token;

   if (s1 == NULL)
      s1 = PreStr;

   // Skip over leading delimiters
   s1 += strspnpgmram ((const rom char *)s1, s2);
   if (*s1 == '\0')
      return NULL;

   // Find the end of the Token.
   Token = s1;
   s1 = strpbrkpgmram ((const rom char *)Token, s2);
   if (s1 == NULL)
      // Point to null at end of string
      PreStr = strchrpgm ((const rom char *)Token, '\0');
   else
      {
      // Terminate Token. Set PreStr to next character
      *s1 = '\0';
      PreStr = s1 + 1;
      }
   return Token;
}



