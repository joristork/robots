////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#ifndef _stdlib_

#define _stdlib_ true

float atof(char * s) {
	float pow10;
        float result;
	int sign, point;
	char c;
        int ptr;

        ptr=0;
   	sign = 0;
	point = 0;
	pow10 = 1.0;
   	result = 0.0;

	do
          c=s[ptr++];
        while ((c<'0'||c>'9') && c!='+' && c!='-' && c!='.');

	while((c>='0' && c<='9') || c=='+' || c=='-' || c=='.') {
           if(c == '-') {
              sign = 1;
              c = s[ptr++];
           }

           while((c >= '0' && c <= '9') && point == 0) {
              result = 10*result + c - '0';
              c = s[ptr++];
           }

           if (c == '.') {
              point = 1;
              c = s[ptr++];
           }

	   while((c >= '0' && c <= '9') && point == 1) {
	       pow10 = pow10*10;
               result += (c - '0')/pow10;
               c = s[ptr++];
           }
        }

	if (sign == 1)
   	  result = -1*result;

	return(result);
}

int abs(signed int i)
{
   int r;

   r = (i < 0) ? -i : i;
   return(r);
}

long labs(signed long l)
{
   long r;

   r = (l < 0) ? -l : l;
   return(r);
}

/************************************************************/

signed int atoi(char *s)
{
   signed int result;
	int sign, base, ptr;
	char c;

   ptr=0;
  	sign = 0;
	base = 10;
	result = 0;

	do
      c=s[ptr++];
   while ((c<'0'||c>'9') && c!='+' && c!='-');

	while ((c>='0' && c<='9') || c=='+' || c=='-') {
      if (c == '-') {
         sign = 1;
         c = s[ptr++];
      }

      if (c == '0' && (s[ptr] == 'x' || s[ptr] == 'X')) {
         base = 16;
         c = s[ptr+1];
         ptr+=2;
      }

      if (base == 10)
         while (c >= '0' && c <= '9') {
            result = 10*result + c - '0';
            c = s[ptr++];
         }

      if (base == 16)
         while ((c>='0'&&c<='9')||(c>='a'&&c<='f')||(c>='A'&&c<='F')) {
            c = TOUPPER(c);

            if (c >= '0' && c <= '9')
               result = 16*result + c - '0';

            if (c >= 'A' && c <= 'F')
               result = 16*result + c - 'A' + 10;

            c = s[ptr++];
         }
   }

   if (sign == 1 && base == 10)
       result = -result;

   return(result);
}



signed long atol(char *s)
{
   signed long result;
	unsigned long ur;
   int sign, base, ptr;
   char c;

   ptr=0;
  	sign = 0;
	base = 10;
	result = 0;
   ur = 0;

	do
      c=s[ptr++];
   while ((c<'0'||c>'9') && c!='+' && c!='-');

	while ((c>='0' && c<='9') || c=='+' || c=='-') {
      if (c == '-') {
         sign = 1;
         c = s[ptr++];
      }

      if (c == '+')
         c = s[ptr++];

      if (c == '0' && (s[ptr] == 'x' || s[ptr] == 'X')) {
         base = 16;
	      ptr += 1;
         c = s[ptr++];
      }

      if (base == 10)
         while (c >= '0' && c <= '9') {
            result = 10*result + c - '0';
            c = s[ptr++];
         }

      if (base == 16)
         while ((c>='0'&&c<='9')||(c>='a'&&c<='f')||(c>='A'&&c<='F')) {
            if (c >= '0' && c <= '9') {
               ur = 16*ur + c - '0';
               c = s[ptr++];
	         }

     	      c = TOUPPER(c);
            if (c >= 'A' && c <= 'F') {
               ur = 16*ur + c - 'A' + 10;
               c = s[ptr++];
            }
         }
   }

   if (base == 10 && sign == 1)
      result = -result;
   if (base == 16)
      result = (ur <= 32767) ? ur : -(~(ur) + 1);

   return(result);
}

#endif
