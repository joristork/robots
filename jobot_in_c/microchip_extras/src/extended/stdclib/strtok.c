/* $Id: strtok.c,v 1.1 2003/12/09 22:43:29 GrosbaJ Exp $
 * $Revision: 1.1 $
 */

#include <string.h>

/** @name strtok
 * ``A sequence of calls to the {\bf strtok} function breaks the
 * string pointed to by {\bf s1} into a sequence of tokens, each of
 * which is delimited by a character from the string pointed to by
 * {\bf s2}. The first call in the sequence has {\bf s1} as its
 * first argument, and is followed by calls with a null pointer
 * as their first argument. The separator string pointed to by {\bf s2}
 * may be different from call to call.
 *
 * ``The first call in the sequence searches the string pointed to
 * by {\bf s1} for the first character that is {\it not} contained in
 * the current separator string {\bf s2}. If no such character is found,
 * then there are no tokens in the string pointed to by {\bf s1} and the
 * {\bf strtok} function returns a null pointer. If such a character is
 * found, it is the start of the first token.
 *
 * ``The {\bf strtok} function then searches from there for a character
 * that {\it is} contained in the current separator string. If no such
 * character is found, the current token extends to the end of the
 * string pointed to by {\bf s1}, and subsequent searches for a token
 * will return a null pointer. If such a character is found, it is
 * overwritten by a null character, which terminates the current token.
 * The {\bf strtok} function saves a pointer to the following character,
 * from which the next search for a token will start.
 *
 * ``Each subsequent call, with a null pointer as the first argument, 
 * starts searching from the saved pointer and behaves as described
 * above.
 *
 * ``The implmentation shall behave as if no library function calls the
 * {\bf strtok} function.''
 * @param s1 pointer to a string to begin searching, or null to continue
 * searching a prior string
 * @param s2 pointer to a string containing the set of separator characters
 * @return ``The {\bf strtok} function returns a pointer to the first
 * character of a token, or a null pointer if there is no token.'' 
 */


static char *PreStr = NULL;

char *strtok (char *s1, const char *s2)
{
   char *Token;

   if (s1 == NULL)
      s1 = PreStr;

   // Skip over leading delimiters
   s1 += strspn ((const char *)s1, s2);
   if (*s1 == '\0')
      return NULL;

   // Find the end of the Token.
   Token = s1;
   s1 = strpbrk ((const char *)Token, s2);
   if (s1 == NULL)
      // Point to null at end of string
      PreStr = strchr ((const char *)Token, '\0');
   else
      {
      // Terminate Token. Set PreStr to next character
      *s1 = '\0';
      PreStr = s1 + 1;
      }
   return Token;
}
