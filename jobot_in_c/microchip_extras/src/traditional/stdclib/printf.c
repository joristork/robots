#include <stdio.h>

/* printf() is a wrapper function which calls vfprintf() with stdout as the 
   destination output stream. EOF is returned on error, else the number 
   of characters output. */
int
printf (const rom char *fmt, ...)
{
  va_list ap;
  int n;
  va_start (ap, fmt);
  n = vfprintf (stdout, fmt, ap);
  va_end (ap);
  return n;
}
