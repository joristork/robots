; $Id: strcspn.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcspn
;``The {\bf strcspn} function computes the length of the maximum initial
;segment of the string pointed to by {\bf s1} which consists entirely
;of characters {\it not} from the string pointed to by {\bf s2}.''
;Stack usage: 6 bytes. Re-entrant.
;@param s1 pointer to string to span
;@param s2 pointer to set of characters
;@return ``The {\bf strcspn} function returns the length of the segment.''
;
; size_t strcspn (const char *s1, const char *s2);

STRING CODE
strcspn
  global strcspn

; Proceedure: Use FSR0 as 's1' pointer and FSR2 as 's2' pointer.

  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -4,FSR0L


jOuterLoop:

  ; Test for '\0' in 's1'
          movf      INDF0, W, ACCESS
          bz        jNull               ; found end of 's1'

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

jNull:
jMatch:
  ; Found match or reached end of 's1'.  Return FSR0 - 's1'

  ; Subtract FSR0 - 's1'
          movlw     -4
          movf      PLUSW1, W, ACCESS
          subwf     FSR0L, F, ACCESS
          movlw     -3
          movf      PLUSW1, W, ACCESS   ; doesn't effect C
          subwfb    FSR0H, F, ACCESS

  ; Put result into PROD
	  movff     FSR0L, PRODL
	  movff     FSR0H, PRODH
  
jEnd:

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L

          return
  end


