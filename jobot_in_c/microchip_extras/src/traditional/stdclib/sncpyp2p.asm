; $Id: sncpyp2p.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncpypgm
;The {\bf strncpypgm} function performs a {\bf strncpy} where
;{\bf s1} points to program memory and {\bf s2} points to program memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in program memory
;@param n maximum number of characters to copy
;
; rom char *strncpypgm (rom char *s1, const rom char *s2, sizerom_t n);

  extern __AARGB2
STRING CODE
strncpypgm
  global strncpypgm


#ifdef __SMALL__
 messg "strncpypgm - SMALL"

; Proceedure: Use temporary copy of dest pointer to increment.
;         Assumes TBLPTRU = 0.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset FSR0L,-2

  ; make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH


jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n

          Stk2Dec    -9

  ; Load TBLPTR with the 'src' pointer

          Stk2CpyToReg -4,TBLPTRL

          tblrd     *                   ; read

  ; Load TBLPTR with the temporary 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

          tblwt     *                   ; write

          Stk2Inc   -4                ; increment source pointer
          Stk2Inc   -2                ; increment copy of dest pointer

  ; Test for '\0'
          tstfsz    TABLAT,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          Stk2Dec   -4                ; decrement source pointer
          bra       jLoop

jEnd

  ; Return value: PROD already set to 'dest'.
          return


#else
#ifdef __LARGE__
 messg "strncpypgm - LARGE"


; Proceedure: Use temporary copy of dest pointer to increment.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset FSR0L,-3

  ; Push a copy of dest onto stack to use for temporary pointer
          Stk3PushFromFSR0

jLoop:
  ; Test 3 byte n for zero

          Stk3TestForZero -12,jEnd

  ; Decrement n

          Stk3Dec    -12

  ; Load TBLPTR with the 'src' pointer

          Stk3CpyToReg -9,TBLPTRL

          tblrd     *                   ; read

  ; Load TBLPTR with the temporary 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

          tblwt     *                   ; write

          Stk3Inc   -9                ; increment source pointer
          Stk3Inc   -3                ; increment copy of dest pointer

  ; Test for '\0'
          tstfsz    TABLAT,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          Stk3Dec   -9                ; decrement source pointer
          bra       jLoop

jEnd
   ; restore stack
          movf      POSTDEC1,W,ACCESS
          movf      POSTDEC1,W,ACCESS
          movf      POSTDEC1,W,ACCESS

  ; Copy three bytes from stack frame at offset to __AARGB2
           Stk3CpyToReg -3,__AARGB2
          return


#else
 error "No Model Specified"
#endif
#endif
  end
