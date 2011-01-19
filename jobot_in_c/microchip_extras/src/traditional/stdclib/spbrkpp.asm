; $Id: spbrkpp.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strpbrkpgm
;``The {\bf strpbrkpgm} function locates the first occurrence in the string
;pointed to by {\bf s1} of any character from the string pointed to by
;{\bf s2}.''
;@param s1 pointer to string to search
;@param s2 pointer to set of characters
;@return ``The {\bf strpbrkpgm} function returns a pointer to the character,
;or a null-pointer if no character from {\bf s2} occurs in {\bf s1}.''
;
; rom char *strpbrkpgm (const rom char *s1, const rom char *s2);

    extern __AARGB2
STRING CODE
strpbrkpgm
  global strpbrkpgm

#ifdef __SMALL__
 messg "strpbrkpgm - SMALL"

; Assumes TBLPTRU = 0

  ; Add scratch to hold char from s1
          clrf      INDF1,ACCESS

jOuterLoop:
  ; Load TBLPTR with the 's1' pointer
          Stk2CpyToReg -2,TBLPTRL

  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'
          movwf     INDF1, ACCESS       ; save in scratch

  ; Save TBLPTR to the 's1' pointer
          Stk2CpyFromReg TBLPTRL,-2

  ; Load TABPTR with the 's2' pointer
          Stk2CpyToReg -4,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jOuterLoop          ; found end of 's2'

  ; Compare 's2' character with 's1' character in scratch
          subwf     INDF1, W, ACCESS
          bnz       jInnerLoop          ; no match

jMatch:
  ; Found match.  Back up pointer at s1 by 1.
          Stk2Dec -2

jEnd:
  ; Set FSR0 to s1
          StkSetPtrToOffset PRODL,-2

          return

jNull:
          clrf      PRODL, ACCESS
          clrf      PRODH, ACCESS
          bra       jEnd

#else
#ifdef __LARGE__
 messg "strpbrkpgm - LARGE"

; Proceedure: Use FSR0 to hold pointer to scratch byte that contains current
;         char from s1.

  ; Add scratch to hold char from s1
          clrf      POSTINC1,ACCESS

          StkSetPtrToOffset FSR0L,-1     ; set FSR0 to point to scratch byte

jOuterLoop:
  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg -3-1,TBLPTRL

  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'
          movwf     INDF0, ACCESS       ; save in scratch

  ; Save TBLPTR to the 's1' pointer
          Stk3CpyFromReg TBLPTRL,-3-1

  ; Load TABPTR with the 's2' pointer
          Stk3CpyToReg -6-1,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jOuterLoop          ; found end of 's2'

  ; Compare 's2' character with 's1' character in scratch
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop          ; no match

  ; Found match.  Back up pointer at s1 by 1.
          Stk3Dec -3-1

jEnd:
  ; Found match. 

  ; Set AARGB2 to s1
          Stk3CpyToReg -3-1,__AARGB2

          movf      POSTDEC1, F, ACCESS ; discard scratch
          return

jNull:
  ; Reached end of 's1' without match.  Load Null to s1 and return pointer to it.
          movlw     -2
          clrf      PLUSW1, ACCESS
          movlw     -3
          clrf      PLUSW1, ACCESS
          movlw     -4
          clrf      PLUSW1, ACCESS
          bra       jEnd


#else
 error "No Model Specified"
#endif
#endif
  end
