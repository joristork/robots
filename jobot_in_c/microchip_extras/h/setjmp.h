/* $Id: setjmp.h,v 1.1 2003/12/09 22:54:19 GrosbaJ Exp $ */
#ifndef __SETJMP_H
#define __SETJMP_h

extern unsigned near FSR2;
extern unsigned near FSR1;
extern unsigned near FSR0;
extern volatile unsigned char near STKPTR;
extern unsigned near PROD;
extern unsigned char near TOSL;
extern unsigned char near TOSH;
extern unsigned char near TOSU;



typedef struct {
  unsigned SP;
  unsigned FP;
  unsigned char STKPTR;
  unsigned long short TOS;
} jmp_buf;

void _longjmp (void);
void _setjmp  (void);

#define setjmp(b) (FSR0=(unsigned)&(b),_setjmp(),PROD)
#define longjmp(b,v) {PROD=(unsigned)(v);FSR0=(unsigned)&(b);_longjmp();}

#endif
