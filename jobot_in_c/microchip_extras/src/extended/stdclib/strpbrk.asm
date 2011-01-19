; $Id: strpbrk.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strpbrk
;``The {\bf strpbrk} function locates the first occurrence in the string
;pointed to by {\bf s1} of any character from the string pointed to by
;{\bf s2}.''
;Stack usage: 6 bytes. Re-entrant.
;@param s1 pointer to string to search
;@param s2 pointer to set of characters
;@return ``The {\bf strpbrk} function returns a pointer to the character,
;or a null-pointer if no character from {\bf s2} occurs in {\bf s1}.''
;
; char *strpbrk (const char *s1, const char *s2);

STRING CODE
strpbrk  FUSTART
  global strpbrk

; Proceedure: Use FSR0 as 's1' pointer and FSR2 as 's2' pointer.

  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -4,FSR0L


jOuterLoop:

  ; Test for '\0' in 's1'
          movf      INDF0, W, ACCESS
          bz        jNull                ; found end of 's1'

  ; Load FSR2 with the 's2' pointer

          Stk2CpyToReg -6,FSR2L

jInnerLoop:

  ; Test for '\0' in 's2'
          movf      POSTINC2, W, ACCESS
          bz        jInner1             ; found end of 's2'

  ; Compare 's2' character with 's1' character at FSR0
          subwf     INDF0, W, ACCESS
          bz        jMatch               ; match
          bra       jInnerLoop

jInner1:
  ; Increment 's1' pointer
          movf      POSTINC0, W, ACCESS
          bra       jOuterLoop

jNull

  ; Reached end of 's1' without match.  Return NULL.
          clrf      PRODL, ACCESS
          clrf      PRODH, ACCESS
          bra       jEnd
          

jMatch
  ; Found match.  Return FSR0.

  ; Put FSR0 into PROD
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH
  
jEnd

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L

          FUEND
          return
  end


