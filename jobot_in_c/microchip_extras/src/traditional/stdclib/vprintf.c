#include <stdio.h>

/* vprintf() is a wrapper to vfprintf() with stdout as the destination
   stream. EOF is returned on error, else the number of characters
   output. */
int
vprintf (const rom char *fmt, va_list ap)
{
  return vfprintf (stdout, fmt, ap);
}
