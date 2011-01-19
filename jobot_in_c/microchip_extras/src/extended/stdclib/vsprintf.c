#include <stdio.h>

/* vsprintf() is a wrapper to vfprintf() with the destination buffer as
   the output destination. EOF is returned on error, else the number of
   characters output. */
int
vsprintf (char *buf, const rom char *f, va_list ap)
{
  int n;
  n = vfprintf ((FILE *) & buf, f, ap);
  *buf = '\0';
  return n;
}
