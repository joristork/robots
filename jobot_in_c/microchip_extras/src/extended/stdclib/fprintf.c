#include <stdio.h>

/* fprintf() is a wrapper function that calls vfprintf(). 
   EOF is returned on error, else the number of characters output. */
int
fprintf (FILE *fptr, const rom char *fmt, ...)
{
  va_list ap;
  int n;
  va_start (ap, fmt);
  n = vfprintf (fptr, fmt, ap);
  va_end (ap);
  return n;
}
