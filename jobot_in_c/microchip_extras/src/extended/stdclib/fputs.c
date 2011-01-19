#include <stdio.h>

/* fputs() outputs characters from the program memory string one at a time
   via putc(), up to but not including the null terminator. 
   Zero is returned on success, EOF on error. */
int
fputs (const rom char *s, FILE * f)
{
  char c;
  while ((c = *s) != '\0')
    {
      if (putc (c, f) == EOF)
        return EOF;
      s++;
    }
  return 0;
}
