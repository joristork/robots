; $Id: scspnrp.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcspnrampgm
;The {\bf strcspnrampgm} function performs a {\bf strcspn}.
;@param s1 pointer to string in data memory
;@param s2 pointer to string in program memory
;
;sizeram_t strcspnrampgm(const char *s1, const rom char *s2);

STRING CODE
strcspnrampgm
  global strcspnrampgm


#ifdef __SMALL__
 messg "strcspnrampgm - SMALL"

; Proceedure: Use FSR0 as 's1' pointer and TBLPTR as 's2' pointer.
; Assumes TBLPTRU = 0

  ; Load FSR0 with the 's1' pointer
          Stk2CpyToReg -2,FSR0L

  ; Use PROD to store count. zero it.
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS

  ; Test for '\0' in 's1'
          movf      INDF0, W, ACCESS
          bz        jNull               ; found end of 's1'

jOuterLoop:
  ; Load TABPTR with the 's2' pointer
          Stk2CpyToReg -4,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jInner1              ; found end of 's2'

  ; Compare 's2' character with 's1' character at TABLAT
          subwf     INDF0, W, ACCESS
          bz        jMatch               ; match
          bra       jInnerLoop

jInner1:
  ; count match
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS

          movf      PREINC0, W, ACCESS
          bnz       jOuterLoop

jNull:
jMatch:

          return

#else
#ifdef __LARGE__
 messg "strcspnrampgm - LARGE"

; Proceedure: Use FSR0 as 's1' pointer and TBLPTR as 's2' pointer.

  ; Load FSR0 with the 's1' pointer
          Stk2CpyToReg -2,FSR0L

  ; Use PROD to store count.  Zero it.
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS

  ; Test for '\0' in 's1'
          movf      INDF0, W, ACCESS
          bz        jNull               ; found end of 's1'

jOuterLoop:
  ; Load TABPTR with the 's2' pointer
          Stk3CpyToReg -5,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jInner1              ; found end of 's2'

  ; Compare 's2' character with 's1' character at TABLAT
          subwf     INDF0, W, ACCESS
          bz        jMatch               ; match
          bra       jInnerLoop

jInner1:
  ; count match
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS

          movf      PREINC0, W, ACCESS
          bnz       jOuterLoop

jNull:
jMatch:
          return

#else
 error "No Model Specified"
#endif
#endif
  end

