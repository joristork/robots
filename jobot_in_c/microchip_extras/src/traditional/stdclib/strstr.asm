; $Id: strstr.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strstr
;``The {\bf strstr} function locates the first occurrence in the string 
;pointed to by {\bf s1} of the sequence of characters (excluding the
;null terminator) in the string pointed to by {\bf s2}.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to the string to search
;@param s2 pointer to sequence to search for
;@return ``The {\bf strstr} function returns a pointer to the located
;string, or a null pointer if the string is not found. If {\bf s2}
;points to a string with zero length, the function returns {\bf s1}.''
;
; char *strstr (const char *s1, const char *SubStr)

STRING CODE
strstr
  global strstr

; Proceedure: Use FSR0 as 's1' pointer and FSR2 as 'SubStr' pointer.
;         Use PROD to hold location of beginning of match. 

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

  ; Put start of next comparision in PRODL
          movff     FSR0L, PRODL
          movff     FSR0H, PRODH

jInnerLoop:

  ; Test for '\0' in 's2'
          movf      POSTINC2, W, ACCESS
          bz        jMatch               ; found end of 's2'

  ; Compare 'SubStr' character with 's1' character at FSR0
          subwf     INDF0, W, ACCESS
          bnz       jNoMatch
          movf      POSTINC0, W, ACCESS  ; matches so far - look at next char
          bra       jInnerLoop

jNoMatch:

  ; No match. Restore 's1' pointer and increment to next char
          movff     PRODL, FSR0L
          movff     PRODH, FSR0H
          movf      POSTINC0, W, ACCESS
          bra       jOuterLoop

jNull:
  ; discard temporary pointer and load PROD with NULL 
          clrf      PRODL, ACCESS
          clrf      PRODH, ACCESS

jMatch:


jReturn:
  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L

          return
  end


