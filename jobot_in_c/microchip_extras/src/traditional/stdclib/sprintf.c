#include <stdio.h>

/* sprintf() is a wrapper function which calls vfprintf() with the address
   of the destination buffer as the output stream. EOF is returned on
   error, else the number of characters output. */
int
sprintf (char *buf, const rom char *f, ...)
{
  int n;
  va_list ap;
  va_start (ap, f);
  n = vfprintf ((FILE *) & buf, f, ap);
  va_end (ap);
  *buf = '\0';
  return n;
}
