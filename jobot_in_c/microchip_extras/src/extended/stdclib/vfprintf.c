#include <stdio.h>
#include <ctype.h>
#include <string.h>

#define _FLAG_MINUS 0x1
#define _FLAG_PLUS  0x2
#define _FLAG_SPACE 0x4
#define _FLAG_OCTO  0x8
#define _FLAG_ZERO  0x10
#define _FLAG_SIGNED 0x80

static const rom char s_digits[] = "0123456789abcdef";

static unsigned char
s_put_n_chars (FILE * handle, unsigned char n, char c)
{
  while (n--)
    if (putc (c, handle) == EOF)
      return 1;
  return 0;
}

#define _FMT_UNSPECIFIED 0
#define _FMT_LONG 1
#define _FMT_SHLONG 2
#define _FMT_BYTE   3
int
vfprintf (auto FILE *handle, auto const rom char *f, auto va_list ap)
{
  unsigned char c;
  int count = 0;

  for (c = *f; c; c = *++f)
    {
      if (c == '%')
        {
          unsigned char flags = 0;
          unsigned char width = 0;
          unsigned char precision = 0;
          unsigned char have_precision = 0;
          unsigned char size = 0;
          unsigned char space_cnt;
          unsigned char cval;
          unsigned long larg;
          far rom char *romstring;
          char *ramstring;
          int n;
          /* get the first character of the conversion specifier */
          c = *++f;

          /**  The conversion specification has, after the '%' character:
              7.9.6.1
             * Zero or more flags (in any order) that modify the meaning of the
               conversion specification.
             * An optional minimum field width. If the converted value has 
               fewer characters than the field width, it will be padded with
               spaces (by default) on the left (or right, if the left
               adjustment flag, described later, has been given) to the field
               width. The field width takes the form of an asterisk * 
               (described later) or a decimal integer.
             * An optional precision that gives the minimum number of digits
               to appear for the d, i, o, u, x, and X conversions, the
               maximum number of digits to appear after the decimal point
               character for e, E, and f conversions, the maximum number
               of significant digits for the g and G conversions, or the 
               maximum number of characters to be written from a string in
               s conversion. The precision takes the form of a period (.)
               followed by an asterisk * (described later) or by an optional
               decimal integer; if only the period is specified, the precision
               is taken as zero. If a precision appears with any other 
               conversion specifier, the behaviour is undefined.
             * An optional h specifying that a following d, i, o, u, x, or X
               conversion specifier applies to a short int or unsigned short
               int argument (the argument will have been promoted according
               to the integer promotions, and its value shall be converted
               to short int or unsigned short int before printing); an
               optional h specifying that a following n conversion specifier
               applies to a pointer to a short int argument; an optional l 
               (ell) specifying that a following d, i, o, u, x, or X 
               conversion specifier applies to a long int or unsigned long int 
               argument; or an optional L specifying that a following e, E, 
               f, g, or G conversion specifier applies to a long double 
               argument. If an h, l, or L appears with any other conversion 
               specifier, the behaviour is undefined.
             * A character that specifies the type of conversion to be applied.
            */

          /** check for a flag.
             7.9.6.1
             The flag characters and their meanings are:

             - The result of the conversion will be left-justified within
               the field. (It will be right justified if this flag is not
               specified.)
             + The result of a signed conversion will always begin with a 
               plus or a minus sign. (It will begin with a sign only when
               a negative value is converted if this flag is not specified.)
             space  If the first character of a signed conversion is not a
               sign, or if a signed conversion results in no characters, a
               space will be prefixed to the result. If the space and + flags
               both appear, the space flag will be ignored.
             # The result is to be converted to an "alternate form." For o
               conversion, it increases the precision to force the first digit
               of the result to be a zero. For x (or X) conversion, a nonzero
               result will have 0x (or 0X) prefixed to it. For e, E, f, g, 
               and G conversions, the result will always contain a decimal-
               point character, even if no digits follow it. (Normally, a
               decimal point character appears in the result of these
               conversions only if a digit follows it.) For g and G
               conversions, trailing zeros will not be removed from the 
               result. For other conversions the behaviour is undefined.
             0 For d, i, o, u, x, X, e, E, f, g, and G conversions, leading
               zeros (following any indication of sign or base) are used to
               pad to the field width; no space padding is performed. If
               the 0 and - flags both appear, the 0 flag will be ignored.
               For other conversions, the behaviour is undefined.
            */
          while (c == '-' || c == '+' || c == ' ' || c == '#'
                 || c == '0')
            {
              switch (c)
                {
                case '-':
                  flags |= _FLAG_MINUS;
                  break;
                case '+':
                  flags |= _FLAG_PLUS;
                  break;
                case ' ':
                  flags |= _FLAG_SPACE;
                  break;
                case '#':
                  flags |= _FLAG_OCTO;
                  break;
                case '0':
                  flags |= _FLAG_ZERO;
                  break;
                }
              c = *++f;
            }
          /* the optional width field is next */
          if (c == '*')
            {
              n = va_arg (ap, int);
              if (n < 0)
                {
                  flags |= _FLAG_MINUS;
                  width = -n;
                }
              else
                width = n;
              c = *++f;
            }
          else
            {
              cval = 0;
              while ((unsigned char) isdigit (c))
                {
                  cval = cval * 10 + c - '0';
                  c = *++f;
                }
              width = cval;
            }

          /* if '-' is specified, '0' is ignored */
          if (flags & _FLAG_MINUS)
            flags &= ~_FLAG_ZERO;

          /* the optional precision field is next */
          if (c == '.')
            {
              c = *++f;
              if (c == '*')
                {
                  n = va_arg (ap, int);
                  if (n >= 0)
                    {
                      precision = n;
                      have_precision = 1;
                    }
                  c = *++f;
                }
              else
                {
                  cval = 0;
                  while ((unsigned char) isdigit (c))
                    {
                      cval = cval * 10 + c - '0';
                      c = *++f;
                    }
                  precision = cval;
                  have_precision = 1;
                }
            }

          /* the optional 'h' specifier. since int and short int are
             the same size for MPLAB C18, this is a NOP for us. */
          if (c == 'h')
            {
              c = *++f;
              /* if 'c' is another 'h' character, this is an 'hh'
                 specifier and the size is 8 bits */
              if (c == 'h')
                {
                  size = _FMT_BYTE;
                  c = *++f;
                }
            }
          /* 'z' and 't' are both 16-bit and so are NOPs */
          else if (c == 't' || c == 'z')
            c = *++f;
          /* the 'H' specifier tells us to deal with 24-bit integers.
             the 'T' and 'Z' specifiers are also 24 bit */
          else if (c == 'H' || c == 'T' || c == 'Z')
            {
              size = _FMT_SHLONG;
              c = *++f;
            }
          /* the 'l' specifier tells us to deal with 32-bit integers
             'j' specifies intmax_t (long in our case). */
          else if (c == 'l' || c == 'j')
            {
              size = _FMT_LONG;
              c = *++f;
            }

          switch (c)
            {
            case '\0':
              /* this is undefined behaviour. we have a trailing '%' character
                 in the string, perhaps with some flags, width, precision
                 stuff as well, but no format specifier. We'll, arbitrarily,
                 back up a character so that the loop will terminate 
                 properly when it loops back and we'll output a '%'
                 character. */
              --f;
              /* fallthrough */
            case '%':
              if (putc ('%', handle) == EOF)
                return EOF;
              ++count;
              break;
            case 'c':
              space_cnt = 0;
              if (width > 1)
                {
                  space_cnt = width - 1;
                  count += space_cnt;
                }
              if (space_cnt && !(flags & _FLAG_MINUS))
                {
                  if (s_put_n_chars (handle, space_cnt, ' '))
                    return EOF;
                  space_cnt = 0;
                }
              c = va_arg (ap, int);
              if (putc (c, handle) == EOF)
                return EOF;
              ++count;
              if (s_put_n_chars (handle, space_cnt, ' '))
                return EOF;
              break;
            case 'S':
              if (size == _FMT_SHLONG)
                romstring = va_arg (ap, rom far char *);
              else
                romstring = (far rom char*)va_arg (ap, rom near char *);
              n = strlenpgm (romstring);
              /* Normalize the width based on the length of the actual 
                 string and the precision. */
              if (have_precision && precision < (unsigned char) n)
                n = precision;
              if (width < (unsigned char) n)
                width = n;
              space_cnt = width - (unsigned char) n;
              count += space_cnt;
              /* we've already calculated the space count that the width
                 will require. now we want the width field to have the
                 number of character to display from the string itself,
                 limited by the length of the actual string and the
                 specified precision. */
              if (have_precision && precision < width)
                width = precision;
              /* if right justified, we print the spaces before the
                 string */
              if (!(flags & _FLAG_MINUS))
                {
                  if (s_put_n_chars (handle, space_cnt, ' '))
                    return EOF;
                  space_cnt = 0;
                }
              cval = 0;
              for (c = *romstring; c && cval < width; c = *++romstring)
                {
                  if (putc (c, handle) == EOF)
                    return EOF;
                  ++count;
                  ++cval;
                }
              /* If there are spaces left, it's left justified. 
                 Either way, calling the function unconditionally 
                 is smaller code. */
              if (s_put_n_chars (handle, space_cnt, ' '))
                return EOF;
              break;
            case 's':
              ramstring = va_arg (ap, char *);
              n = strlen (ramstring);
              /* Normalize the width based on the length of the actual 
                 string and the precision. */
              if (have_precision && precision < (unsigned char) n)
                n = precision;
              if (width < (unsigned char) n)
                width = n;
              space_cnt = width - (unsigned char) n;
              count += space_cnt;
              /* we've already calculated the space count that the width
                 will require. now we want the width field to have the
                 number of character to display from the string itself,
                 limited by the length of the actual string and the
                 specified precision. */
              if (have_precision && precision < width)
                width = precision;
              /* if right justified, we print the spaces before the
                 string */
              if (!(flags & _FLAG_MINUS))
                {
                  if (s_put_n_chars (handle, space_cnt, ' '))
                    return EOF;
                  space_cnt = 0;
                }
              cval = 0;
              for (c = *ramstring; c && cval < width; c = *++ramstring)
                {
                  if (putc (c, handle) == EOF)
                    return EOF;
                  ++count;
                  ++cval;
                }
              /* If there are spaces left, it's left justified. 
                 Either way, calling the function unconditionally 
                 is smaller code. */
              if (s_put_n_chars (handle, space_cnt, ' '))
                return EOF;
              break;
            case 'd':
            case 'i':
              flags |= _FLAG_SIGNED;
              /* fall through */
            case 'o':
            case 'u':
            case 'x':
            case 'X':
            case 'b':
            case 'B':
              /* This is a bit of a sneaky trick. The 'l' and 'hh' size
                 specifiers are valid only for the integer conversions,
                 not the 'p' or 'P' conversions, and are ignored for the
                 latter. By jumping over the additional size specifier
                 checks here we get the best code size since we can
                 limit the size checks in the remaining code. */
              if (size == _FMT_LONG)
                {
                  larg = va_arg (ap, long int);
                  goto _do_integer_conversion;
                }
              else if (size == _FMT_BYTE)
                {
                  if (flags & _FLAG_SIGNED)
                    larg = (signed char) va_arg (ap, int);
                  else
                    larg = (unsigned char) va_arg (ap, unsigned int);
                  goto _do_integer_conversion;
                }
              /* fall through */
            case 'p':
            case 'P':
              if (size == _FMT_SHLONG)
                {
                  if (flags & _FLAG_SIGNED)
                    larg = va_arg (ap, short long int);
                  else
                    larg = va_arg (ap, unsigned short long int);
                }
              else if (flags & _FLAG_SIGNED)
                larg = va_arg (ap, int);
              else
                larg = va_arg (ap, unsigned int);
            _do_integer_conversion:
              /* default precision is 1 */
              if (!have_precision)
                precision = 1;

              {
                unsigned char digit_cnt = 0;
                unsigned char prefix_cnt = 0;
                unsigned char sign_char;
                /* A 32 bit number will require at most 32 digits in the
                   string representation (binary format). */
                char buf[33];
                /* Start storing digits least-significant first */
                char *q = &buf[31];
                /* null terminate the string */
                buf[32] = '\0';

                space_cnt = 0;
                size = 10;

                switch (c)
                  {
                  case 'b':
                  case 'B':
                    size = 2;
                    break;
                  case 'o':
                    size = 8;
                    break;
                  case 'p':
                  case 'P':
                    /* from here on out, treat 'p' conversions just
                       like 'x' conversions. */
                    c += 'x' - 'p';
                    /* fall through */
                  case 'x':
                  case 'X':
                    size = 16;
                    break;
                  }

                /* if it's an unsigned conversion, we should ignore the
                   ' ' and '+' flags */
                if (!(flags & _FLAG_SIGNED))
                  flags &= ~(_FLAG_PLUS | _FLAG_SPACE);

                /* if it's a negative value, we need to negate the
                   unsigned version before we convert to text. Using
                   unsigned for this allows us to (ab)use the 2's
                   complement system to avoid overflow and be able to
                   adequately handle LONG_MIN.

                   We'll figure out what sign character to print, if
                   any, here as well. */
                if (flags & _FLAG_SIGNED && ((long) larg < 0))
                  {
                    larg = -(long) larg;
                    sign_char = '-';
                    ++digit_cnt;
                  }
                else if (flags & _FLAG_PLUS)
                  {
                    sign_char = '+';
                    ++digit_cnt;
                  }
                else if (flags & _FLAG_SPACE)
                  {
                    sign_char = ' ';
                    ++digit_cnt;
                  }
                else
                  sign_char = '\0';
                /* get the digits for the actual number. If the
                   precision is zero and the value is zero, the result
                   is no characters. */
                if (precision || larg)
                  {
                    do
                      {
                        cval = s_digits[larg % size];
                        if (c == 'X' && cval >= 'a')
                          cval -= 'a' - 'A';
                        larg /= size;
                        *q-- = cval;
                        ++digit_cnt;
                      }
                    while (larg);
                    /* if the '#' flag was specified and we're dealing
                       with an 'o', 'b', 'B', 'x', or 'X' conversion,
                       we need a bit more. */
                    if (flags & _FLAG_OCTO)
                      {
                        if (c == 'o')
                          {
                            /* per the standard, for octal, the '#' flag
                               makes the precision be at least one more
                               than the number of digits in the number */
                            if (precision <= digit_cnt)
                              precision = digit_cnt + 1;
                          }
                        else if (c == 'x'
                                 || c == 'X' || c == 'b' || c == 'B')
                          prefix_cnt = 2;
                      }
                  }
                else
                  digit_cnt = 0;

                /* The leading zero count depends on whether the '0'
                   flag was specified or not. If it was not, then the
                   count is the difference between the specified
                   precision and the number of digits (including the
                   sign character, if any) to be printed; otherwise,
                   it's as if the precision were equal to the max of
                   the specified precision and the field width. If a
                   precision was specified, the '0' flag is ignored,
                   however. */
                if ((flags & _FLAG_ZERO) && (width > precision)
                    && !have_precision)
                  precision = width;
                /* for the rest of the processing, precision contains
                   the leading zero count for the conversion. */
                if (precision > digit_cnt)
                  precision -= digit_cnt;
                else
                  precision = 0;
                /* the space count is the difference between the field
                   width and the digit count plus the leading zero
                   count. If the width is less than the digit count
                   plus the leading zero count, the space count is
                   zero. */
                if (width > precision + digit_cnt + prefix_cnt)
                  space_cnt =
                    width - precision - digit_cnt - prefix_cnt;

                /* for output, we check the justification, if it's
                   right justified and the space count is positive, we
                   emit the space characters first. */
                if (!(flags & _FLAG_MINUS) && space_cnt)
                  {
                    if (s_put_n_chars (handle, space_cnt, ' '))
                      return EOF;
                    count += space_cnt;
                    space_cnt = 0;
                  }
                /* if we have a sign character to print, that comes
                   next */
                if (sign_char)
                  if (putc (sign_char, handle) == EOF)
                    return EOF;
                /* if we have a prefix (0b, 0B, 0x or 0X), that's next */
                if (prefix_cnt)
                  {
                    if (putc ('0', handle) == EOF)
                      return EOF;
                    if (putc (c, handle) == EOF)
                      return EOF;
                  }
                /* if we have leading zeros, they follow. the prefix, if any
                   is included in the number of digits when determining how
                   many leading zeroes are needed. */
                if (precision > prefix_cnt)
                  precision -= prefix_cnt;
                if (s_put_n_chars (handle, precision, '0'))
                  return EOF;
                /* print the actual number */
                for (cval = *++q; cval; cval = *++q)
                  if (putc (cval, handle) == EOF)
                    return EOF;
                /* if there are any spaces left, they go to right-pad
                   the field */
                if (s_put_n_chars (handle, space_cnt, ' '))
                  return EOF;

                count += precision + digit_cnt + space_cnt + prefix_cnt;
              }
              break;
            case 'n':
              switch (size)
                {
                case _FMT_LONG:
                  *(long *) va_arg (ap, long *) = count;
                  break;
                case _FMT_SHLONG:
                  *(short long *) va_arg (ap, short long *) = count;
                  break;
                case _FMT_BYTE:
                  *(signed char *) va_arg (ap, signed char *) = count;
                  break;
                default:
                  *(int *) va_arg (ap, int *) = count;
                  break;
                }
              break;
            default:
              /* undefined behaviour. we do nothing */
              break;
            }
        }
      else
        {
          if (putc (c, handle) == EOF)
            return EOF;
          ++count;
        }
    }

  return count;
}
