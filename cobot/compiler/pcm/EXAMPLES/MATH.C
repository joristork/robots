////        (C) Copyright 1996,1997 Custom Computer Services            ////
//// This source code may only be used by licensed users of the CCS C   ////
//// compiler.  This source code may only be distributed to other       ////
//// licensed users of the CCS C compiler.  No other use, reproduction  ////
//// or distribution is permitted without written permission.           ////
//// Derivative programs created using this software in object code     ////
//// form are not restricted in any way.                                ////
////////////////////////////////////////////////////////////////////////////

#include <string.h>

struct long32 {unsigned long lo;  long hi;};

void atol32 (char *s, struct long32 *result)
{
   signed long hi, add_to_hi;
   long lo;
   int sign, base, ptr, sub, strlength, i;
   char c;
   
   ptr=0;
   sign = 0;
   base = 10;
   lo = 0;
   hi = 0;
   sub = 0;
   strlength = strlen (s);

   //skip begining non-alphanumeric
   do {
      c=s[ptr++];
   } while ((c<'0'||c>'9') && c!='+' && c!='-');

   while ((c>='0' && c<='9') || c=='+' || c=='-') {
      if (c == '-') {
         sign = 1;
         c = s[ptr++];
      }

      if (c == '+')
         c = s[ptr++];

      if (c == '0' && (s[ptr] == 'x' || s[ptr] == 'X')) {
         base = 16;
	 ptr++;
         c = s[ptr++];
      }

      if (base == 10)
         while (c >= '0' && c <= '9') {
            while ((lo>(6553+((int)(5+sub)/10)))||
               ((lo==6553+((int)(5+sub)/10))&&((c-'0')>((5+sub)%10)))) {
               lo -= 6553;

               add_to_hi = 1;
               for (i=0;i<(strlength-ptr);i++) {
                  add_to_hi *= 10;
               }
               hi += add_to_hi;
               sub += 6;

	       if (sub>10) {
                  lo -= (int)sub/10;
                  sub = sub % 10;   
               }
            }
           
            lo = (10*lo) + (c - '0');
            lo-=sub;
            c = s[ptr++];
            sub = 0;
         }

      if (base == 16)
         while ((c>='0'&&c<='9')||(c>='a'&&c<='f')||(c>='A'&&c<='F')) {
            for (i=0; i<4; i++)
               rotate_left (&hi,2);

            for (i=12; i<16; i++)
               if (bit_test (lo,i))
                   bit_set (hi,i-12);

            for (i=0; i<4; i++)
               rotate_left (&lo,2);

            if (c >= '0' && c <= '9') 
               lo |= c - '0';

            c = TOUPPER(c);

            if (c >= 'A' && c <= 'F') 
               lo |= c - 'A' + 10;

            c = s[ptr++];

         }
   }

   if (base == 16 && sign == 1)
      hi |= 0x8000;
   if (base == 10 && sign == 1)
      hi |= 0x8000;

   result->hi = hi;
   result->lo = lo;
}

void add32(struct long32 *a, struct long32 *b) {
                       // a = a + b
   byte r[4];

   r[0] = *(&b->lo);
   r[1] = *(&b->lo + 1);
   r[2] = *(&b->lo + 2);
   r[3] = *(&b->lo + 3);

#asm
   movf    a,w
   movwf   4
   movf    r[0],w
   addwf   0,f
   movf    r[1],w
   incf    4,f
   btfsc   3,0
   incfsz  r[1],w
   addwf   0,f
   movf    r[2],w
   incf    4,f
   btfsc   3,0
   incfsz  r[2],w
   addwf   0,f
   movf    r[3],w
   incf    4,f
   btfsc   3,0
   incfsz  r[3],w
   addwf   0,f
   #endasm
}


void sub32(struct long32 *a, struct long32 *b) {
                       // a = a - b
   byte r[4];

   r[0] = *(&b->lo);
   r[1] = *(&b->lo + 1);
   r[2] = *(&b->lo + 2);
   r[3] = *(&b->lo + 3);

#asm
   movf    a,w
   movwf   4
   movf    r[0],w
   subwf   0,f
   movf    r[1],w
   incf    4,f
   btfss   3,0
   incfsz  r[1],w
   subwf   0,f
   movf    r[2],w
   incf    4,f
   btfss   3,0
   incfsz  r[2],w
   subwf   0,f
   movf    r[3],w
   incf    4,f
   btfss   3,0
   incfsz  r[3],w
   subwf   0,f
   #endasm
}

void mul32(struct long32 *a, struct long32 *b) {
                       // a = a*b
   byte r[4], c[4], sign, count;

   r[0] = *(&a->lo);
   r[1] = *(&a->lo + 1);
   r[2] = *(&a->lo + 2);
   r[3] = *(&a->lo + 3);

   c[0] = *(&b->lo);
   c[1] = *(&b->lo + 1);
   c[2] = *(&b->lo + 2);
   c[3] = *(&b->lo + 3);

   a->lo = 0;
   a->hi = 0;

#asm
   movlw    31
   movwf    count

   movf     r[3],w
   movwf    sign
   movf     c[3],w
   xorwf    sign,f

   btfss    r[3],7
   goto     chkb
   comf     r[0],f
   comf     r[1],f
   comf     r[2],f
   comf     r[3],f
   incfsz   r[0],f
   goto     chkb
   incfsz   r[1],f
   goto     chkb
   incfsz   r[2],f
   goto     chkb
   incf     r[3],f

chkb:
   btfss    c[3],7
   goto     loop
   comf     c[0],f
   comf     c[1],f
   comf     c[2],f
   comf     c[3],f
   incfsz   c[0],f
   goto     loop
   incfsz   c[1],f
   goto     loop
   incfsz   c[2],f
   goto     loop
   incf     c[3],f

loop:
   btfss    r[0],0
   goto     shft
   movf     a,w
   movwf    4
   movf     c[0],w
   addwf    0,f
   movf     c[1],w
   incf     4,f
   btfsc    3,0
   incfsz   c[1],w
   addwf    0,f
   movf     c[2],w
   incf     4,f
   btfsc    3,0
   incfsz   c[2],w
   addwf    0,f
   movf     c[3],w
   incf     4,f
   btfsc    3,0
   incfsz   c[3],w
   addwf    0,f

shft:
   bcf      3,0
   rrf      r[3],f
   rrf      r[2],f
   rrf      r[1],f
   rrf      r[0],f

   bcf      3,0
   rlf      c[0],f
   rlf      c[1],f
   rlf      c[2],f
   rlf      c[3],f
   decfsz   count,f
   goto     loop

   movf     a,w
   addlw    3
   movwf    4
   bcf      0,7
   btfss    sign,7
   goto     end

   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     end
   incf     4,f
   incfsz   0,f
   goto     end
   incf     4,f
   incfsz   0,f
   goto     end
   incf     4,f
   incf     0,f
end:
   nop
#endasm
}

void div32(struct long32 *a,struct long32  *b) {
                       // a = a/b
   byte c[4], d[4], sign, count, negb;

   c[0] = *(&a->lo);
   c[1] = *(&a->lo + 1);
   c[2] = *(&a->lo + 2);
   c[3] = *(&a->lo + 3);

   d[0] = 0;
   d[1] = 0;
   d[2] = 0;
   d[3] = 0;

   a->lo = 0;
   a->hi = 0;
   count = 32;
   negb = 0;

#asm
   movf     c[3],w
   movwf    sign
   movf     b,w
   addlw    3
   movwf    4
   movf     0,w
   xorwf    sign,f

   btfss    c[3],7
   goto     chkb
   comf     c[0],f
   comf     c[1],f
   comf     c[2],f
   comf     c[3],f
   incfsz   c[0],f
   goto     chkb
   incfsz   c[1],f
   goto     chkb
   incfsz   c[2],f
   goto     chkb
   incf     c[3],f

chkb:
   movf     b,w
   addlw    3
   movwf    4
   btfss    0,7
   goto     loop
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     loop
   incf     4,f
   incfsz   0,f
   goto     loop
   incf     4,f
   incfsz   0,f
   goto     loop
   incf     4,f
   incf     0,f
   bsf      negb,0

loop:
   bcf      3,0
   rlf      c[0],f
   rlf      c[1],f
   rlf      c[2],f
   rlf      c[3],f
   rlf      d[0],f
   rlf      d[1],f
   rlf      d[2],f
   rlf      d[3],f

   movf     b,w
   addlw    3
   movwf    4
   movf     0,w
   subwf    d[3],w
   btfss    3,2
   goto     chk
   decf     4,f
   movf     0,w
   subwf    d[2],w
   btfss    3,2
   goto     chk
   decf     4,f
   movf     0,w
   subwf    d[1],w
   btfss    3,2
   goto     chk
   decf     4,f
   movf     0,w
   subwf    d[0],w

chk:
   btfss    3,0
   goto     skip
   movf     b,w
   movwf    4
   movf     0,w
   subwf    d[0],f
   incf     4,f
   movf     0,w
   btfss    3,0
   incfsz   0,w
   subwf    d[1],f
   incf     4,f
   movf     0,w
   btfss    3,0
   incfsz   0,w
   subwf    d[2],f
   incf     4,f
   movf     0,w
   btfss    3,0
   incfsz   0,w
   subwf    d[3],f
   bsf      3,0

skip:
   movf     a,w
   movwf    4
   rlf      0,f
   incf     4,f
   rlf      0,f
   incf     4,f
   rlf      0,f
   incf     4,f
   rlf      0,f
   decfsz   count,f
   goto     loop

   movf     a,w
   addlw    3
   movwf    4
   bcf      0,7
   btfss    sign,7
   goto     retb

   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     retb
   incf     4,f
   incfsz   0,f
   goto     retb
   incf     4,f
   incfsz   0,f
   goto     retb
   incf     4,f
   incf     0,f

retb:
   btfss    negb,0
   goto     end
   movf     b,w
   addlw    3
   movwf    4
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     end
   incf     4,f
   incfsz   0,f
   goto     end
   incf     4,f
   incfsz   0,f
   goto     end
   incf     4,f
   incf     0,f
end:
   nop
#endasm
}

void rem32(struct long32 *a,struct long32  *b,struct long32  *r) {
                       // a = a/b
                       // r = a%b
   byte c[4], d[4], sign, count, negb;

   c[0] = *(&a->lo);
   c[1] = *(&a->lo + 1);
   c[2] = *(&a->lo + 2);
   c[3] = *(&a->lo + 3);

   d[0] = 0;
   d[1] = 0;
   d[2] = 0;
   d[3] = 0;

   a->lo = 0;
   a->hi = 0;
   count = 32;
   negb = 0;

#asm
   movf     c[3],w
   movwf    sign
   movf     b,w
   addlw    3
   movwf    4
   movf     0,w
   xorwf    sign,f

   btfss    c[3],7
   goto     chkb
   comf     c[0],f
   comf     c[1],f
   comf     c[2],f
   comf     c[3],f
   incfsz   c[0],f
   goto     chkb
   incfsz   c[1],f
   goto     chkb
   incfsz   c[2],f
   goto     chkb
   incf     c[3],f

chkb:
   movf     b,w
   addlw    3
   movwf    4
   btfss    0,7
   goto     loop
   bsf      negb,0
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     loop
   incf     4,f
   incfsz   0,f
   goto     loop
   incf     4,f
   incfsz   0,f
   goto     loop
   incf     4,f
   incf     0,f

loop:
   bcf      3,0
   rlf      c[0],f
   rlf      c[1],f
   rlf      c[2],f
   rlf      c[3],f
   rlf      d[0],f
   rlf      d[1],f
   rlf      d[2],f
   rlf      d[3],f

   movf     b,w
   addlw    3
   movwf    4
   movf     0,w
   subwf    d[3],w
   btfss    3,2
   goto     chk
   decf     4,f
   movf     0,w
   subwf    d[2],w
   btfss    3,2
   goto     chk
   decf     4,f
   movf     0,w
   subwf    d[1],w
   btfss    3,2
   goto     chk
   decf     4,f
   movf     0,w
   subwf    d[0],w

chk:
   btfss    3,0
   goto     skip
   movf     b,w
   movwf    4
   movf     0,w
   subwf    d[0],f
   incf     4,f
   movf     0,w
   btfss    3,0
   incfsz   0,w
   subwf    d[1],f
   incf     4,f
   movf     0,w
   btfss    3,0
   incfsz   0,w
   subwf    d[2],f
   incf     4,f
   movf     0,w
   btfss    3,0
   incfsz   0,w
   subwf    d[3],f
   bsf      3,0

skip:
   movf     a,w
   movwf    4
   rlf      0,f
   incf     4,f
   rlf      0,f
   incf     4,f
   rlf      0,f
   incf     4,f
   rlf      0,f
   decfsz   count,f
   goto     loop

   movf     a,w
   addlw    3
   movwf    4
   bcf      0,7
   btfss    sign,7
   goto     retb

   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     retb
   incf     4,f
   incfsz   0,f
   goto     retb
   incf     4,f
   incfsz   0,f
   goto     retb
   incf     4,f
   incf     0,f

retb:
   btfss    negb,0
   goto     negr
   movf     b,w
   addlw    3
   movwf    4
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   decf     4,f
   comf     0,f
   incfsz   0,f
   goto     negr
   incf     4,f
   incfsz   0,f
   goto     negr
   incf     4,f
   incfsz   0,f
   goto     negr
   incf     4,f
   incf     0,f

negr:
   movf     b,w
   addlw    3
   movwf    4
   movf     0,w
   xorwf    sign,f
   btfss    sign,7
   goto     end
   comf     d[0],f
   comf     d[1],f
   comf     d[2],f
   comf     d[3],f
   incfsz   d[0],f
   goto     end
   incfsz   d[1],f
   goto     end
   incfsz   d[2],f
   goto     end
   incf     d[3],f

end:
   nop
#endasm

   *r = d[0];
   *(r + 1) = d[1];
   *(r + 2) = d[2];
   *(r + 3) = d[3];
}

unsigned long FTOunsL(int *p)
{
   int i[2], t;
   unsigned long long_i;

#asm
      MOVLW    142
      MOVWF    t
      MOVF     p,W
      MOVWF    4
      MOVF     0,W
      SUBWF    t,F

      INCF     4,F
      MOVF     0,W
      MOVWF    i[1]

      INCF     4,F
      MOVF     0,W
      MOVWF    i[0]

      BSF      i[1],7
      MOVF     t,F
      BTFSC    3,2
      GOTO     end
step: BCF      3,0
      MOVF     i[1],F
      BTFSS    3,2
      GOTO     cont
      MOVF     i[0],F
      BTFSC    3,2
      GOTO     end
cont: RRF      i[1],F
      RRF      i[0],F
      DECFSZ   t,F
      GOTO     step

end:
#endasm

    long_i=((unsigned long)i[1]<<8)+i[0];
    return(long_i);
}

void unsLTOF(unsigned long int ul, int *p)
{
   unsigned int h, l;
   l = *(&ul);
   h = *(&ul + 1);
 #asm
      MOVF     p,W
      MOVWF    4
      MOVLW    0x8E
      MOVWF    0

      MOVLW    1
      ADDWF    p,W
      MOVWF    4
      MOVF     h,W
      MOVWF    0

      MOVLW    2
      ADDWF    p,W
      MOVWF    4
      MOVF     l,W
      MOVWF    0

      MOVLW    3
      ADDWF    p,W
      MOVWF    4
      CLRF     0

 beg: MOVLW    1
      ADDWF    p,W
      MOVWF    4
      MOVF     0,F
      BTFSS    3,2
      GOTO     set

      INCF     4,F
      MOVF     0,W
      CLRF     0
      DECF     4,F
      MOVWF    0

      DECF     4,F
      MOVLW    8
      SUBWF    0,F

      INCF     4,F
      MOVF     0,F
      BTFSS    3,2
      GOTO     set
      DECF     4,F
      CLRF     0
      GOTO     end

 set: DECF     4,F
 nrm: BCF      3,0
      INCF     4,F
      BTFSC    0,7
      GOTO     sig
      INCF     4,F
      RLF      0,F
      DECF     4,F
      RLF      0,F
      DECF     4,F
      DECF     0,F
      GOTO     nrm

 sig: BCF      0,7
 end: nop
#endasm
}
