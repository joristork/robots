; $Id: mmovp2p.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

;The {\bf memmovepgm} function performs a {\bf memmove} where both
;{\bf s1} and {\bf s2} point to program memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in program memory
;@param n number of characters to copy
;
;rom void *memmovepgm (rom void *s1, const rom void *s2, sizerom_t n);
;

STRING CODE
memmovepgm
  global memmovepgm
  extern memcpypgm

#ifdef __SMALL__
 messg "memmovepgm - SMALL"


; Proceedure: If 'dest' > 'src' then must begin copy at end of 'src' to avoid
;         possible memory overwrite.  If 'dest' <= 'scr' use memcpy code.
;         Assume upper byte of n can be ignored -> use 2 byte arithmetic

  ; Save FSR2 on the stack.

          Stk2PushFromReg FSR2L

  ; Set FSR2 to point to source pointer
          StkSetPtrToOffset FSR2L,-6

  ; Set FSR0 to point to dest pointer
          StkSetPtrToOffset FSR0L,-4

  ; Make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

  ; If dest > scr then must start copy at end of string.
  ; Preserve FSR0 and FSR2
          movf      POSTINC0,W
          subwf     POSTINC2,W
          movf      POSTDEC0,W
          subwfb    POSTDEC2,W
          bnc       jReverse            ; reverse copy order

  ; Restore stack
          Stk2PopToReg FSR2L

  ; Use ordinary copy routine
          goto      memcpypgm

jReverse
  ; Test 'n' for zero
          Stk2TestForZero -9,jEnd

  ; n -> n-1
          Stk2Dec   -9

  ; Calc end of 'src' by adding 'n'-1.
          Stk2AddToFSR2 -9

  ; Calc end of 'dest' by adding 'n'-1.
          Stk2AddToFSR0 -9


  ; s2 & s1 are now at end of strings.  Begin copy of Cnt char to 'dest'.
  ; Remember that n has already been decremented by 1
jRevCpyLoop:

          Stk2CpyToReg -6,TBLPTRL     ; source
          tblrd     *
          Stk2CpyToReg -4,TBLPTRL     ; dest
          tblwt     *

  ; Test 2 byte n for zero
          Stk2TestForZero -9,jEnd

  ; Decrement 2 byte n
          Stk2Dec   -9

          Stk2Dec   -6                ; decrement source pointer
          Stk2Dec   -4                ; decrement dest pointer
          bra       jRevCpyLoop
jEnd

   ; restore FSR2
          Stk2PopToReg FSR2L

          return

#else
#ifdef __LARGE__
 messg "memmovepgm - LARGE"


; Proceedure: If 'dest' > 'src' then must begin copy at end of 'src' to avoid
;         possible memory overwrite.  If 'dest' <= 'scr' use memcpy code.
; Use 3 byte n and three byte pointers.

  ; Save FSR2 on the stack.

          Stk2PushFromReg FSR2L

  ; Set FSR2 to point to source pointer
          StkSetPtrToOffset FSR2L,-8

  ; Set FSR0 to point to dest pointer
          StkSetPtrToOffset FSR0L,-5

  ; If dest > scr then must start copy at end of string.
  ; Preserve FSR0 and FSR2
          movf      POSTINC0,W,ACCESS
          subwf     POSTINC2,W,ACCESS
          movf      POSTINC0,W,ACCESS
          subwfb    POSTINC2,W,ACCESS
          movf      POSTDEC0,W,ACCESS
          subwfb    POSTDEC2,W,ACCESS
  ; Restore FSR0 and FSR2
          movf      POSTDEC0,W,ACCESS
          movf      POSTDEC2,W,ACCESS
          bnc       jReverse            ; reverse copy order

  ; Restore stack
          Stk2PopToReg FSR2L

  ; Use ordinary copy routine
          goto      memcpypgm

jReverse
  ; Test 'n' for zero
          Stk3TestForZero -11,jEnd

  ; n -> n-1
          Stk3Dec   -11

  ; Calc end of 'src' by adding 'n'-1.
          Stk3AddToFSR2 -11

  ; Calc end of 'dest' by adding 'n'-1.
          Stk3AddToFSR0 -11


  ; s2 & s1 are now at end of strings.  Begin copy of Cnt char to 'dest'.
  ; Remember that n has already been decremented by 1
jRevCpyLoop:

          Stk3CpyToReg -8,TBLPTRL     ; source
          tblrd     *
          Stk3CpyToReg -5,TBLPTRL     ; dest
          tblwt     *

  ; Test 3 byte n for zero
          Stk3TestForZero -11,jEnd

  ; Decrement 3 byte n
          Stk3Dec   -11

          Stk2Dec   -8                ; decrement source pointer
          Stk2Dec   -5                ; decrement dest pointer
          bra       jRevCpyLoop
jEnd

   ; restore FSR2
          Stk2PopToReg FSR2L

   ; Point FSR0 to s1 which has been decrement back to it original value
          StkSetPtrToOffset FSR0L,-3

          return

#else
 error "No Model Specified"
#endif
#endif
  end

