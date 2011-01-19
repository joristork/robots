; $Id: mcmpp2p.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memcmppgm
;The {\bf memcmppgm} function performs a {\bf memcmp} where both
;{\bf s1} and {\bf s2} point to program memory.
;@param s1 pointer to string in program memory
;@param s2 pointer to string in program memory
;@param n number of characters to compare
;
; signed char memcmppgm (const rom void *s1, const rom void *s2, sizerom_t n);


STRING CODE
memcmppgm
  global memcmppgm


#ifdef __SMALL__

 messg "memcmppgm - SMALL"

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it.

; signed char memcmppgm (const rom void *s1, const rom void *s2, sizerom_t n);

jLoop:

  ; Test 3 byte n for zero

          Stk3TestForZero -7,jEnd     ; WREG == 0 if branch

  ; Decrement 3 byte n 

          Stk3Dec   -7


          Stk2CpyToReg -4,TBLPTRL     ; source
          tblrd     *
          Stk2CpyToReg -2,TBLPTRL     ; dest

          Stk2Inc   -4                ; increment source pointer
          Stk2Inc   -2                ; increment dest pointer

          movf      TABLAT,W,ACCESS
          tblrd     *

          subwf     TABLAT, W, ACCESS
          bz        jLoop

  ; Found unequal byte - difference in WREG

jEnd

          return

#else
#ifdef __LARGE__

 messg "memcmppgm - LARGE"

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it.

; signed char memcmppgm (const rom void *s1, const rom void *s2, sizerom_t n);

jLoop:

  ; Test 3 byte n for zero

          Stk3TestForZero -9,jEnd     ; WREG == 0 if branch

  ; Decrement 3 byte n 

          Stk3Dec   -9


          Stk3CpyToReg -6,TBLPTRL     ; source
          tblrd     *
          Stk3CpyToReg -3,TBLPTRL     ; dest

          Stk3Inc   -6                ; increment source pointer
          Stk3Inc   -3                ; increment dest pointer

          movf      TABLAT,W,ACCESS
          tblrd     *

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
