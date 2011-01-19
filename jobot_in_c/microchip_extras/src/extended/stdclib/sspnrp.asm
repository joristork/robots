; $Id: sspnrp.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strspnrampgm
;``The {\bf strspnrampgm} function computes the length of the maximum initial
;segment of the string pointed to by {\bf s1} which consists entirely
;of characters from the string pointed to by {\bf s2}.''
;@param s1 pointer to string to span
;@param s2 pointer to set of characters
;@return ``The {\bf strspnrampgm} function returns the length of the segment.''
;
; sizeram_t strspnrampgm (const char *s1, const rom char *s2);

STRING CODE
strspnrampgm   FUSTART
  global strspnrampgm


#ifdef __SMALL__
 messg "strspnrampgm - SMALL"

; Proceedure: Use FSR0 as 's1' pointer and TBLPTR as 's2' pointer.
; Assumes TBLPTRU = 0

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
          Stk2CpyToReg -4,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNoMatch             ; found end of 's2'

  ; Compare 's2' character with 's1' character at TABLAT
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop

  ; count match
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS
          movf      PREINC0, W, ACCESS
          bnz       jOuterLoop

jNull:
jNoMatch:
  ; Found mismatch or reached end of 's1'.

          FUEND
          return

#else
#ifdef __LARGE__
 messg "strspnrampgm - LARGE"

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
          bz        jNoMatch            ; found end of 's2'

  ; Compare 's2' character with 's1' character at TABLAT
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop

jInner1:
  ; count match
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS
          movf      PREINC0, W, ACCESS
          bnz       jOuterLoop
jNull:
jNoMatch:
  ; Found mismatch or reached end of 's1'.  Return FSR0 pointing to count.
          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end


