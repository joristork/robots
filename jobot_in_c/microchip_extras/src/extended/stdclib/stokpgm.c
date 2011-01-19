/* $Id: stokpgm.c,v 1.1 2003/12/09 22:43:29 GrosbaJ Exp $ */

#include "string.h"

/** @name strtokpgm
 * The {\bf strtokpgm} function performs a {\bf strtok} where both
 * {\bf s1} and {\bf s2} point to program memory.
 * @param s1 pointer to destination in program memory
 * @param s2 pointer to source in program memory
 */
// rom char *strtokpgm (rom char *s1, const rom char *s2);

static rom char *PreStr = NULL;

rom char *strtokpgm (rom char *s1, const rom char *s2)
{
   rom char *Token;

   if (s1 == NULL)
      s1 = PreStr;

   // Skip over leading delimiters
   s1 += strspnpgm ((const rom char *)s1, s2);
   if (*s1 == '\0')
      return NULL;

   // Find the end of the Token.
   Token = s1;
   s1 = strpbrkpgm ((const rom char *)Token, s2);
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
