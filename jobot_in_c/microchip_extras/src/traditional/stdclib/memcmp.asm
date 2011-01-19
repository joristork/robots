; $Id: memcmp.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memcmp
;``The {\bf memcmp} function compares the first {\bf n} characters of the
;object pointed to by {\bf s1} to the first {\bf n} characters pointed
;to by {\bf s2}.''
;Stack usage: 6 bytes. Re-entrant.
;@param s1 pointer to object one
;@param s2 pointer to object two
;@param n count of characters to compare
;@return ``The {\bf memcmp} function returns an signed char greater than, equal
;to, or less than zero, accordingly as the object pointed to by {\bf s1} is
;greater than, equal to, or less than the object pointed to by {\bf s2}.''
;
; signed char memcmp (const void *s1, const void *s1, size_t n);


STRING CODE
memcmp
  global memcmp

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's1' and FSR2 for 's2' 


  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 's2' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:

  ; Test n for zero

          Stk2TestForZero -8,jEnd        ; returns 0 in WREG if branch

  ; Decrement n 

          Stk2Dec -8

          movf      POSTINC2, W, ACCESS
          subwf     POSTINC0, W, ACCESS
          bz        jLoop

  ; Found unequal byte - difference in WREG which will be put on stack

jEnd
  ; Stash return value in PRODL for safekeeping

	  movwf PRODL, ACCESS

  ; Restore FSR2.  Trashes WREG.
          Stk2PopToReg FSR2L
          movf PRODL,W,ACCESS
          return
  end
