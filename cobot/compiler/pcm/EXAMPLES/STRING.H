////////////////////////////////////////////////////////////////////////////
////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <ctype.h>

/* standard template: char *strcat(char *s1, const char *s2) */

char strcat(char *s1, char *s2)
{
   char *s;

   for (s = s1; *s != '\0'; s++);
   while ((*s = *s2) != '\0')
   {
       s++;
       s2++;
   }
   return(s1);
}

/***********************************************************/

/* standard template: char *strchr(const char *s, int c).
   Finds first occurrence of c in s1 */

char strchr(char *s, int c)
{
   for (; *s != c; s++)
      if (*s == '\0')
         return(0);
   return(s);
}

/* standard template: char *strrchr(const char *s, int c).
   Finds last occurrence of c in s1 */

char strrchr(char *s, int c)
{
   char *p;

   for (p = 0; ; s++)
   {
      if (*s == c)
         p = s;
      if (*s == '\0')
         return(p);
   }
}

/*****************************************************************/

/* standard template: int strcmp(const char *s1, const char *s2).
   Compares s1 & s2; returns -1 if s1<s2, 0 if s1=s2, 1 if s1>s2 */

int strcmp(char *s1, char *s2)
{
   for (; *s1 == *s2; s1++, s2++)
      if (*s1 == '\0')
         return(0);
   return((*s1 < *s2) ? -1: 1);
}

/* standard template:
   int strncmp(const char *s1, const char *s2, size_t n).
   Compares max of n characters (not following 0) from s1 to s2;
   returns same as strcmp */

int strncmp(char *s1, char *s2, int n)
{
   for (; n > 0; s1++, s2++, n--)
      if (*s1 != *s2)
         return((*s1 <*s2) ? -1: 1);
      else if (*s1 == '\0')
         return(0);
   return(0);
}

/* standard template: size_t stricmp(const char *s1, const char *s2).
   Compares s1 to s2 ignoring case (upper vs. lower) */

int stricmp(char *s1, char *s2)
{
 for(; *s1==*s2||(ISALPHA(*s1)&&ISALPHA(*s2)&&(*s1==*s2+32||*s2==*s1+32));
    s1++, s2++)
    if (*s1 == '\0')
       return(0);
 return((*s1 < *s2) ? -1: 1);
}

/***************************************************************/

/* compiler ignored the name 'strcpy()'; perhaps, it's reserved?
   Standard template: char *strcpy(char *s1, const char *s2) */

char strcopy(char *s1, char *s2)
{
  char *s;

  for (s = s1; *s2 != 0; s++, s2++)
	  *s = *s2;
  return(s1);
}

/* standard template:
   char *strncpy(char *s1, const char *s2, size_t n).
   Copies max of n characters (not following ending '\0')
   from s2 in s1; if s2 has less than n characters, appends 0 */

char strncpy(char *s1, char *s2, int n)
{
  char *s;

  for (s = s1; n > 0 && *s2 != '\0'; n--)
     *s++ = *s2++;
  for (; n > 0; n--)
     *s++ = '\0';

  return(s1);
}

/***************************************************************/

/* standard template:
   size_t strcspn(const char *s1, const char *s2).
   Computes length of max initial segment of s1 that
   consists entirely of characters NOT from s2*/

int strcspn(char *s1, char *s2)
{
   char *sc1, *sc2;

   for (sc1 = s1; *sc1 != 0; sc1++)
      for (sc2 = s2; *sc2 != 0; sc2++)
         if (*sc1 == *sc2)
            return(sc1 - s1);
   return(sc1 - s1);
}

/* computes length of max initial segment of s1 consisting
   entirely of characters from s2 */

int strspn(char *s1, char *s2)
{
   char *sc1, *sc2;

   for (sc1 = s1; *sc1 != 0; sc1++)
      for (sc2 = s2; ; sc2++)
	 if (*sc2 == '\0')
	    return(sc1 - s1);
         else if (*sc1 == *sc2)
            break;
   return(sc1 - s1);
}

/***************************************************************/

/* standard template: size_t strlen(const char *s).
   Computes length of s1 (preceding terminating 0) */

int strlen(char *s)
{
   char *sc;

   for (sc = s; *sc != 0; sc++);
   return(sc - s);
}

/***************************************************************/

/* standard template: char *strlwr(char *s).
   Replaces uppercase letters by lowercase;
   returns pointer to new string s */

char strlwr(char *s)
{
   char *p;

   for (p = s; *p != '\0'; p++)
      if (*p >= 'A' && *p <='Z')
         *p += 'a' - 'A';
   return(s);
}

/****************************************************************/

/* standard template:
   char *strpbrk(const char *s1, const char *s2).
   Locates first occurence of any character from s2 in s1;
   returns s1 if s2 is empty string */

char strpbrk(char *s1, char *s2)
{
   char *sc1, *sc2;

   for (sc1 = s1; *sc1 != 0; sc1++)
      for (sc2 = s2; *sc2 != 0; sc2++)
         if (*sc1 == *sc2)
            return(sc1);
   return(0);
}

/****************************************************************/

/* standard template:
   char *strstr(const char *s1, const char *s2);
   Locates first occurence of character sequence s2 in s1;
   returns 0 if s2 is empty string */

char strstr(char *s1, char *s2)
{
   char *sc1, *sc2;

   if (*s2 == 0)
         return(s1);
   for (; s1 = strchr(s1, *s2); s1++)
   {
      for (sc1 = s1, sc2 = s2; ; sc1++, sc2++)
         if (*sc2 == 0)
	    return(s1);
         else if (*sc1 != *sc2)
            break;
   }
   return(0);
}

/************************************************************/

/* standard template: char *strtok(char *s1, const char *s2).

   Finds next token in s1 delimited by a character from separator
   string s2 (which can be different from call to call).  First call
   starts at beginning of s1 searching for first character NOT
   contained in s2; returns 0 if none is found.
   If one is found, it is the start of first token (return value).
   Function then searches from there for a character contained in s2.
   If none is found, current token extends to end of s1, and subsequent
   searches for a token will return 0.  If one is found, it is
   overwritten by '\0', which terminates current token.  Function saves
   pointer to following character from which next search will start.
   Each subsequent call, with 0 as first argument, starts searching
   from saved pointer */

char strtok(char *s1, char *s2)
{
   char *beg, *end;
   static char *save;

   beg = (s1)? s1: save;
   beg += strspn(beg, s2);
   if (*beg == '\0')
   {
      *save = ' ';
      return(0);
   }
   end = strpbrk(beg, s2);
   if (*end != '\0')
   {
      *end = '\0';
      end++;
   }
   save = end;
   return(beg);
}

