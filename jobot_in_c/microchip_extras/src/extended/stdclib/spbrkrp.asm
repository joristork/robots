; $Id: spbrkrp.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strpbrkrampgm
;``The {\bf strpbrkrampgm} function locates the first occurrence in the string
;pointed to by {\bf s1} of any character from the string pointed to by
;{\bf s2}.''
;@param s1 pointer to string to search
;@param s2 pointer to set of characters
;@return ``The {\bf strpbrkrampgm} function returns a pointer to the character,
;or a null-pointer if no character from {\bf s2} occurs in {\bf s1}.''
;
; char *strpbrkrampgm (const char *s1, const rom char *s2);

STRING CODE
strpbrkrampgm   FUSTART
  global strpbrkrampgm

#ifdef __SMALL__
  messg "strpbrkrampgm - SMALL"

  ; Procedure: Use FSR0 as s1 pointer and TBLPTR as s2 pointer.
  ; Assumes TBLPTRU = 0

          Stk2CpyToReg -2,FSR0L         ; set FSR0 to point to s1

  ; Test for '\0' in 's1'
          movf      INDF0, W, ACCESS
          bz        jNull               ; found end of 's1'

jOuterLoop:
  ; Load TBLPTR with the 's2' pointer
          Stk2CpyToReg -4,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jInner1             ; found end of 's2'

  ; Compare 's2' character with 's1' character in scratch
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop          ; no match
          bra       jMatch

jInner1:
          movf      PREINC0, W, ACCESS
          bz        jNull               ; found end of 's1'
          bra       jOuterLoop

jMatch:
  ; Found match. Copy FSR0 into PROD.
          movff     FSR0L, PRODL
          movff     FSR0H, PRODH

jEnd:

          return

jNull:
  ; Reached end of 's1' without match.  Load Null to s1 and return pointer to it.
          clrf      PRODL, ACCESS
          clrf      PRODH, ACCESS
;          bra       jEnd
          FUEND
	  return

#else
#ifdef __LARGE__
  messg "strpbrkrampgm - LARGE"

  ; Procedure: Use FSR0 as s1 pointer and TBLPTR as s2 pointer.

          Stk2CpyToReg -2,FSR0L         ; set FSR0 to point to s1

  ; Test for '\0' in 's1'
          movf      INDF0, W, ACCESS
          bz        jNull               ; found end of 's1'

jOuterLoop:
  ; Load TBLPTR with the 's2' pointer
          Stk3CpyToReg -5,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jInner1             ; found end of 's2'

  ; Compare 's2' character with 's1' character in scratch
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop          ; no match
          bra       jMatch

jInner1:
          movf      PREINC0, W, ACCESS
          bz        jNull               ; found end of 's1'
          bra       jOuterLoop

jMatch:
  ; Found match. Copy FSR0 into PROD.
          movff     FSR0L, PRODL
          movff     FSR0H, PRODH

jEnd:

          return

jNull:
  ; Reached end of 's1' without match.  Load Null to s1 and return pointer to it.
          clrf      PRODL, ACCESS
          clrf      PRODH, ACCESS
;          bra       jEnd
          FUEND
	  return

#else
 error "No Model Specified"
#endif
#endif
  end
