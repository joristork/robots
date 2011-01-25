////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////
#include <CTYPE.H>

byte gethex1() {
   char digit;

   digit = getch();

   putchar(digit);

   if(digit<='9')
     return(digit-'0');
   else
     return((toupper(digit)-'A')+10);
}

byte gethex() {
   int lo,hi;

   hi = gethex1();
   lo = gethex1();
   if(lo==0xdd)
     return(hi);
   else
     return( hi*16+lo );
}


void get_string(char * s,int max) {
   int len;
   char c;

   max--;
   len=0;
   do {
     c=getc();
     if(c==8) {  // Backspace
        if(len>0) {
          len--;
          putc(c);
          putc(' ');
          putc(c);
        }
     } else if ((c>=' ')&&(c<='~'))
       if(len<max) {
         s[len++]=c;
         putc(c);
       }
   } while(c!=13);
   s[len]=0;
}

#ifdef _stdlib_

signed int get_int() {
  char s[5];
  signed int i;

  get_string(s, 5);

  i=atoi(s);
  return(i);
}



signed long get_long() {
  char s[7];
  signed long l;

  get_string(s, 7);
  l=atol(s);
  return(l);
}

#endif
