#include <stdio.h>

/* puts() is a wrapper function which calls fputs() with stdout as the 
   destination stream and adds a newline. EOF is returned on error, else
   non-negative. */
int
puts (const rom char *s)
{
  int n;
  n = fputs (s, stdout);
  putc ('\n', stdout);
  return n;
}
