; $Id: memmove.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memmove
;``The {\bf memmove} function copies {\bf n} characters from the object
;pointed to by {\bf s2} into the object pointed to by {\bf s1}. Copying
;takes place as if the {\bf n} characters from the object pointed to
;by {\bf s2} are first copied into a temporary array of {\bf n}
;characters that does not overlap the objects pointed to by {\bf s1}
;and {\bf s2}, and then the {\bf n} characters from the temporary array
;are copied into the object pointed to by {\bf s1}.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to destination
;@param s2 pointer to source
;@param n count of bytes to copy
;@return ``The {\bf memmove} function returns the value of {\bf s1}.''
;
; void *memmove (void *s1, const void *s2, size_t n);


STRING CODE
memmove
  global memmove

; Proceedure: If 'dest' > 'src' then must begin copy at end of 'src' to avoid
;         possible memory overwrite.  If 'dest' <= 'scr' use memcpy code.


  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 'src' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -4,FSR0L

  ; Make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

  ; If dest > src then must start copy at end of string.

          movf      FSR0L,W
          subwf     FSR2L,W
          movf      FSR0H,W
          subwfb    FSR2H,W
          bnc       jReverse

jLoop:
  ; Test n for zero

          Stk2TestForZero -8,jEnd

  ; Decrement n 

          Stk2Dec   -8

          movff     POSTINC2, POSTINC0
          bra       jLoop

jReverse
  ; Calc end of 'src' by adding 'n'-1.
          movlw     -8
          movf      PLUSW1,W,ACCESS          
          addwf     FSR2L,F,ACCESS
          movlw     -7
          movf      PLUSW1,W,ACCESS
          addwfc    FSR2H,F,ACCESS
          movf      POSTDEC2,F,ACCESS   ; n -> n-1

  ; Calc end of 'dest' by adding 'n'-1.
          movlw     -8
          movf      PLUSW1,W,ACCESS
          addwf     FSR0L,F,ACCESS
          movlw     -7
          movf      PLUSW1,W,ACCESS
          addwfc    FSR0H,F,ACCESS
          movf      POSTDEC0,F,ACCESS   ; n -> n-1

  ; Now at end of 'src' string.  Begin copy of Cnt char to 'dest'.


jRevCpyLoop:
  ; Test 'n' for zero

          Stk2TestForZero -8,jEnd

  ; Decrement 'n' 

          Stk2Dec   -8

          movff     POSTDEC2, POSTDEC0
          bra       jRevCpyLoop

jEnd

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L
          return
  end
