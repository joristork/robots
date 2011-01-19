; $Id: spbrkpr.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strpbrkpgmram
;``The {\bf strpbrkpgmram} function locates the first occurrence in the string
;pointed to by {\bf s1} of any character from the string pointed to by
;{\bf s2}.''
;@param s1 pointer to string to search
;@param s2 pointer to set of characters
;@return ``The {\bf strpbrkpgmram} function returns a pointer to the character,
;or a null-pointer if no character from {\bf s2} occurs in {\bf s1}.''
;
; rom char *strpbrkpgmram (const rom char *s1, const char *s2);

  extern __RETVAL0

STRING CODE
strpbrkpgmram   FUSTART
  global strpbrkpgmram

#ifdef __SMALL__
  messg "strpbrkpgmram - SMALL"

  ; Procedure: Use FSR0 for s2.
  ; Assumes TBLPTRU = 0
  ; Load TBLPTR with the 's1' pointer
          Stk2CpyToReg -2,TBLPTRL


jOuterLoop:
  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'

  ; Set FSR0 to s2
          Stk2CpyToReg -4,FSR0L

jInnerLoop:

  ; Test for '\0' in 's2'
          movf      INDF0, W, ACCESS
          bz        jOuterLoop          ; found end of 's2'

  ; Compare 's2' character with 's1' character in TABLAT
          movf      TABLAT, W, ACCESS
          subwf     POSTINC0, W, ACCESS
          bnz       jInnerLoop          ; no match

  ; Found match.  Back up s1 pointer by 1.
          tblrd     *-                  ; decrement


  ; Save TBLPTR to PRODL
	  movff TBLPTRL, PRODL
	  movff TBLPTRH, PRODH

jEnd:

          return

jNull:
  ; Reached end of 's1' without match.  Load Null to s1 and return pointer to it.
          clrf      PRODL, ACCESS
          clrf      PRODH, ACCESS
          FUEND
          bra       jEnd

#else
#ifdef __LARGE__
  messg "strpbrkpgmram - LARGE"

  ; Procedure: Use FSR0 for s2.
  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg -3,TBLPTRL

jOuterLoop:
  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'

  ; Set FSR0 to s2
          Stk2CpyToReg -5,FSR0L

jInnerLoop:

  ; Test for '\0' in 's2'
          movf      INDF0, W, ACCESS
          bz        jOuterLoop          ; found end of 's2'

  ; Compare 's2' character with 's1' character in TABLAT
          movf      TABLAT, W, ACCESS
          subwf     POSTINC0, W, ACCESS
          bnz       jInnerLoop          ; no match

  ; Found match.  Back up s1 pointer by 1.
          tblrd     *-                  ; decrement

  ; Save TBLPTR to the 's1' pointer
          Stk3CpyFromReg TBLPTRL,-3

jEnd:
  ; Set __RETVAL2:__RETVAL1:__RETVAL0 to s1
          Stk3CpyToReg -3, __RETVAL0
          return

jNull:
  ; Reached end of 's1' without match.  Load Null to s1 and return pointer to it.
			
          movlw     -1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1, ACCESS
          movlw     -2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1, ACCESS
          movlw     -3
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1, ACCESS
          FUEND
          bra       jEnd

#else
 error "No Model Specified"
#endif
#endif
  end
