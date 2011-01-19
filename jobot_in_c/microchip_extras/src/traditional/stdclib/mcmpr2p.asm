; $Id: mcmpr2p.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memcmpram2pgm
;The {\bf memcmpram2pgm} function performs a {\bf memcmp} where {\bf s1} 
;points to program memory and {\bf s2} point to data memory.
;@param s1 pointer to string in program memory
;@param s2 pointer to string in data memory
;@param n number of characters to compare
;
; signed char memcmpram2pgm (const rom void *s1, const void *s2, sizeram_t n);


STRING CODE
memcmpram2pgm
  global memcmpram2pgm


#ifdef __SMALL__
 messg "memcmpram2pgm - SMALL"

; Proceedure: Two byte pointers and two byte n.
;         Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's2' and TBLPTR for 's1' 
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 's1' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Load FSR0 with the 's2' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:

  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n 

          Stk2Dec    -6

          tblrd     *+                  ; read & increment
          movf      POSTINC0, W, ACCESS

          subwf     TABLAT, W, ACCESS
          bz        jLoop

  ; Found unequal byte - difference in WREG which will be put in PRODL

jEnd

	movwf PRODL, ACCESS

          return

#else
#ifdef __LARGE__
 messg "memcmpram2pgm - LARGE"

; Proceedure: Three byte rom pointer.
;         Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's2' and TBLPTR for 's1' 

; signed char memcmpram2pgm (const rom void *s1, const void *s2, sizeram_t n);

  ; Load TBLPTR with the 's1' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Load FSR0 with the 's2' pointer

          Stk2CpyToReg -5,FSR0L

jLoop:

  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n 

          Stk2Dec    -7

          tblrd     *+                  ; read & increment
          movf      POSTINC0, W, ACCESS

          subwf     TABLAT, W, ACCESS
          bz        jLoop

  ; Found unequal byte - difference in WREG

jEnd

          return


#else
 error "No Model Specified"
#endif
#endif
  end


