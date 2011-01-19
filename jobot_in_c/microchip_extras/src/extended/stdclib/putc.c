#include <stdio.h>

int _user_putc (char c);
int _usart_putc (char c);

int
putc (char c, FILE * f)
{
  /* if the handle > 0 then it's a pointer to a string buffer address.
     specifically, a pointer to a pointer referencing the next available
     byte in the buffer, to which the character will be stored. */
  if ((int) f >= 0)
    return (unsigned char) (*(*(char **) f)++ = c);

  if ((signed char) f == (signed char) _H_USART)
    {
      /* the invocation of the USART function is direct, as it's our
         default value and we want it to work without needing any
         direct initialization from the user */
      return _usart_putc (c);
    }
  else
    {
      /* the invocation of the user function is direct, not via a
         function pointer. We just put an empty definition of the
         function in the library and if the user specifies their own
         version it will be found first and the library definition will 
         be ignored. We do it this way because it's generally better to 
         spend 2 bytes of program memory for the RETURN instruction of
         the empty function than three bytes of data memory for the
         function pointer. */
      return _user_putc (c);
    }
}
