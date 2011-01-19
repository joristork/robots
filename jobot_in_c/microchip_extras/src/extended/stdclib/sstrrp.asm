; $Id: sstrrp.asm,v 1.4 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strstrrampgm
;``The {\bf strstrrampgm} function locates the first occurrence in the string
;pointed to by {\bf s1} of the sequence of characters (excluding the
;null terminator) in the string pointed to by {\bf s2}.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to the string to search
;@param s2 pointer to sequence to search for
;@return ``The {\bf strstrrampgm} function returns a pointer to the located
;string, or a null pointer if the string is not found. If {\bf s2}
;points to a string with zero length, the function returns {\bf s1}.''
;
; char *strstrrampgm (const char *s1, const rom char *s2);

STRING CODE
strstrrampgm   FUSTART
  global strstrrampgm

#ifdef __SMALL__
 messg "strstrrampgm - SMALL"

; Procedure: Use TBLPTR for s2 and FSR0 for s1.

  ; Load PROD with the 's1' pointer
          Stk2CpyToReg -2,PRODL

jOuterLoop:

  ; Load TBLPTR with the 's2' pointer
          Stk2CpyToReg -4,TBLPTRL

  ; load FSR0 with the 's1' pointer
	  movff PRODL, FSR0L
	  movff PRODH, FSR0H

jInnerLoop:
  ; Load char from s2 to TABLAT
          tblrd     *+                  ; read & increment

  ; Test for '\0' in 's2'
          movf      TABLAT, W, ACCESS
          bz        jStrMatch           ; found end of 's2' without mismatch

  ; Compare char from s1 to char from s2
          movf      TABLAT, W, ACCESS
          subwf     INDF0, W, ACCESS
          bnz       jNoMatch  

  ; Match
  ; Since Chars matched. and s2 char != 0 => s1 char also != 0
          movf      POSTINC0, F, ACCESS ; increment FSR0
          bra       jInnerLoop
jNoMatch:
  ; No Match
  ; Have we reached the end of s1 without a match
          movf      INDF0, F, ACCESS
          bz        jNull               ; return a Null

  ; Increment s1
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS
          bra       jOuterLoop

jNull:
  ; copy Null to PROD
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS

jStrMatch
  ; Found match.

          FUEND
          return

#else
#ifdef __LARGE__
 messg "strstrrampgm - LARGE"
; Procedure: Use TBLPTR for s2 and FSR0 for s1.

  ; Load PROD with the 's1' pointer
          Stk2CpyToReg -2,PRODL

jOuterLoop:

  ; Load TBLPTR with the 's2' pointer
          Stk3CpyToReg -5,TBLPTRL

  ; load FSR0 with the 's1' pointer
	  movff PRODL, FSR0L
	  movff PRODH, FSR0H

jInnerLoop:
  ; Load char from s2 to TABLAT
          tblrd     *+                  ; read & increment

  ; Test for '\0' in 's2'
          movf      TABLAT, W, ACCESS
          bz        jStrMatch           ; found end of 's2' without mismatch

  ; Compare char from s1 to char from s2
          movf      TABLAT, W, ACCESS
          subwf     INDF0, W, ACCESS
          bnz       jNoMatch  

  ; Match
  ; Since Chars matched. and s2 char != 0 => s1 char also != 0
          movf      POSTINC0, F, ACCESS ; increment FSR0
          bra       jInnerLoop
jNoMatch:
  ; No Match
  ; Have we reached the end of s1 without a match
          movf      INDF0, F, ACCESS
          bz        jNull               ; return a Null

  ; Increment s1
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS
          bra       jOuterLoop

jNull:
  ; copy Null to PROD
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS

jStrMatch
  ; Found match.

          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end

