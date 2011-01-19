#include <stdio.h>

#ifdef __EXTENDED18__
#pragma config XINST=ON
#else
#pragma config XINST=OFF
#endif

void main (void)
{
  int j = 0;
  int i;

  for (i = 0; i < 10; i++)
    {
      printf ("%d:\t", i);

      if (i % 2)
        {
          printf ("ODD");
          j += i;
        }
      else
        {
          printf ("EVEN");
          j += i;
        }

      printf ("\tj = %d\n", j);
    }

  while (1)
    ;
}
