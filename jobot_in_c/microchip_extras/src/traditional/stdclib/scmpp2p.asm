#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcmppgm
; The {\bf strcmppgm} function performs a {\bf strcmp} where both
; {\bf s1} and {\bf s2} point to program memory.
; @param s1 pointer to string in program memory
; @param s2 pointer to string in program memory
; signed char strcmppgm(const rom char *s1, const rom char *s2);

STRING CODE
strcmppgm
  global strcmppgm


#ifdef __SMALL__
 messg "strcmppgm - SMALL"


;         Assumes TBLPTRU = 0.

          clrf      INDF1,ACCESS

jLoop:

  ; Load TBLPTR with the 's1' pointer

          Stk2CpyToReg -2,TBLPTRL

          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF1       ; store in scratch

          Stk2CpyFromReg TBLPTRL,-2   ; copy incremented pointer back to stack

  ; Load TBLPTR with the 's2' pointer

          Stk2CpyToReg -4,TBLPTRL

          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS

          subwf     INDF1, F, ACCESS    ; s1 - s2
          bnz       jEnd                ; unequal - diff in WREG

  ; Here only if bytes are equal. Need to test for '\0' on only one byte.
          andlw     0xFF                ; test s2 == 0
          bz        jEnd

          Stk2CpyFromReg TBLPTRL,-4   ; copy incremented pointer back to stack
          bra       jLoop

jEnd

  ; Retrieve difference from scratch byte and move it down by one byte.
	  movf INDF1, W,ACCESS
          return

#else
#ifdef __LARGE__
 messg "strcmppgm - LARGE"

          clrf      INDF1,ACCESS

jLoop:

  ; Load TBLPTR with the 's1' pointer

          Stk3CpyToReg -3,TBLPTRL

          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF1       ; store in scratch

          Stk3CpyFromReg TBLPTRL,-3   ; copy incremented pointer back to stack

  ; Load TBLPTR with the 's2' pointer

          Stk3CpyToReg -6,TBLPTRL

          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS

          subwf     INDF1, F, ACCESS    ; s1 - s2
          bnz       jEnd                ; unequal - diff in WREG

  ; Here only if bytes are equal. Need to test for '\0' on only one byte.
          andlw     0xFF                ; test s2 == 0
          bz        jEnd

          Stk3CpyFromReg TBLPTRL,-6   ; copy incremented pointer back to stack
          bra       jLoop

jEnd

  ; Retrieve difference from scratch byte
	  movf INDF1, W,ACCESS

          return


#else
 error "No Model Specified"
#endif
#endif
  end
