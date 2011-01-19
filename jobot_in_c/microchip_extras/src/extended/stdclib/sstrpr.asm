; $Id: sstrpr.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strstrpgmram
;``The {\bf strstrpgmram} function locates the first occurrence in the string
;pointed to by {\bf s1} of the sequence of characters (excluding the
;null terminator) in the string pointed to by {\bf s2}.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to the string to search
;@param s2 pointer to sequence to search for
;@return ``The {\bf strstrpgmram} function returns a pointer to the located
;string, or a null pointer if the string is not found. If {\bf s2}
;points to a string with zero length, the function returns {\bf s1}.''
;
; rom char *strstrpgmram (const rom char *s1, const char *s2);

extern __RETVAL0

STRING CODE
strstrpgmram   FUSTART
  global strstrpgmram

#ifdef __SMALL__
 messg "strstrpgmram - SMALL"

; Proceedure: Use TBLPTR for s1 and FSR0 for s2.

  ; Load PROD with the 's1' pointer
          Stk2CpyToReg -2,PRODL

jOuterLoop:
  ; Load TBLPTR with 's1'

	  movff PRODL, TBLPTRL
	  movff PRODH, TBLPTRH

  ; Load FSR0 with the 's2' pointer
          Stk2CpyToReg -4,FSR0L

jInnerLoop:
  ; Load char from s1 to TABLAT
          tblrd     *+                  ; read & increment

  ; Test for '\0' in 's2'
          movf      INDF0, W, ACCESS
          bz        jStrMatch           ; found end of 's2' without mismatch

  ; Compare char from s1 to char from s2
          movf      TABLAT, W, ACCESS
          subwf     POSTINC0, W, ACCESS
          bz        jInnerLoop  ; Since Chars matched. and s2 char != 0 => s1 char also != 0

  ; No Match
  ; Have we reached the end of s1 without a match
          movf      TABLAT, F, ACCESS
          bz        jNull               ; return a Null

  ; Increment s1
	  infsnz    PRODL, W, ACCESS
	  incf      PRODH, W, ACCESS
          bra       jOuterLoop

jNull:
  ; copy Null to s1
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS

jStrMatch
  ; Found match.

          FUEND
          return

#else
#ifdef __LARGE__
 messg "strstrpgmram - LARGE"
; Proceedure: Use TBLPTR for s1 and FSR0 for s2.

jOuterLoop:

  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg -3,TBLPTRL

  ; Load FSR0 with the 's2' pointer
          Stk2CpyToReg -5,FSR0L

jInnerLoop:
  ; Load char from s1 to TABLAT
          tblrd     *+                  ; read & increment

  ; Test for '\0' in 's2'
          movf      INDF0, W, ACCESS
          bz        jStrMatch           ; found end of 's2' without mismatch

  ; Compare char from s1 to char from s2
          movf      TABLAT, W, ACCESS
          subwf     POSTINC0, W, ACCESS
          bz        jInnerLoop  ; Since Chars matched. and s2 char != 0 => s1 char also != 0

  ; No Match
  ; Have we reached the end of s1 without a match
          movf      TABLAT, F, ACCESS
          bz        jNull               ; return a Null

  ; Increment s1
          Stk3Inc   -3
          bra       jOuterLoop

jNull:
  ; copy Null to s1
          StkSetPtrToOffset FSR0L,-3
          clrf      POSTINC0,ACCESS
          clrf      POSTINC0,ACCESS
          clrf      POSTINC0,ACCESS

jStrMatch
  ; Found match.  s1 contains pointer to be returned.

  ; Set __RETVAL0 to s1
          Stk3CpyToReg -3,__RETVAL0
          FUEND
          return


#else
 error "No Model Specified"
#endif
#endif
  end

