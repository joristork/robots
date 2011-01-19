; $Id: mcpyp2p.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memcpypgm
;The {\bf memcpypgm} function performs a {\bf memcpy} where both
;{\bf s1} and {\bf s2} point to program memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in program memory
;@param n number of characters to copy
; rom void *memcpypgm (rom void *s1, const rom void *s2, sizerom_t n);

STRING CODE
memcpypgm
  global memcpypgm


#ifdef __SMALL__
 messg "memcpypgm - SMALL"


; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Make copy of s1 on stack to increment. Save original s1 for return.

; signed char memcmppgm (const rom void *s1, const rom void *s2, sizerom_t n);

	; copy s1 into PROD for return

	Stk2CpyToReg -2, PRODL

jLoop:

  ; Test 3 byte n for zero

          Stk3TestForZero -7,jEnd     ; WREG == 0 if branch

  ; Decrement 3 byte n

          Stk3Dec   -7

          Stk2CpyToReg -4,TBLPTRL     ; source
          tblrd     *
          Stk2CpyToReg -2,TBLPTRL     ; dest
          tblwt     *

          Stk2Inc   -4                ; increment source pointer
          Stk2Inc   -2                ; increment copy of dest pointer
          bra       jLoop

jEnd

          return

#else
#ifdef __LARGE__
 messg "memcpypgm - LARGE"

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Make copy of s1 on stack to increment. Save original s1 for return.

; signed char memcmppgm (const rom void *s1, const rom void *s2, sizerom_t n);

  ; Set FSR0 to s1
          StkSetPtrToOffset FSR0L,-3

  ; Push s1 onto stack. 
          Stk3PushFromFSR0              ; Leaves FSR0 unchanged

jLoop:

  ; Test 3 byte n for zero

          Stk3TestForZero -12,jEnd     ; WREG == 0 if branch

  ; Decrement 3 byte n

          Stk3Dec   -12

          Stk3CpyToReg -9,TBLPTRL     ; source
          tblrd     *
          Stk3CpyToReg -3,TBLPTRL     ; dest
          tblwt     *

          Stk2Inc   -9                ; increment source pointer
          Stk2Inc   -3                ; increment copy of dest pointer
          bra       jLoop

jEnd

   ; restore stack
          movf      POSTDEC1,W,ACCESS  
          movf      POSTDEC1,W,ACCESS  
          movf      POSTDEC1,W,ACCESS  
          return

#else
 error "No Model Specified"
#endif
#endif
  end
