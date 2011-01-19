; $Id: sncmpp2p.asm,v 1.2 2009/12/16 16:48:10 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncmppgm
; The {\bf strncmppgm} function performs a {\bf strncmp} where both
; {\bf s1} and {\bf s2} point to program memory.
; @param s1 pointer to string in program memory
; @param s2 pointer to string in program memory
; @param n number of characters to compare
;
; signed char strncmppgm (const rom char *s1, const rom char *s2, sizerom_t n);

STRING CODE
strncmppgm
  global strncmppgm


#ifdef __SMALL__
 messg "strncmppgm - SMALL"


; Proceedure: Use FSR0 for 'dest' and TBLPTR for 'src'
;         Assumes TBLPTRU = 0.

          clrf      INDF1,ACCESS

jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n

          Stk2Dec    -6


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

          movf      INDF1,W,ACCESS
          return

#else
#ifdef __LARGE__
 messg "strncmppgm - LARGE"

; Proceedure: Three byte rom pointer.
;         Use FSR0 for 'dest' and TBLPTR for 'src'

          clrf      INDF1,ACCESS

jLoop:
  ; Test 3 byte n for zero

          Stk3TestForZero -9,jEnd

  ; Decrement n

          Stk2Dec    -9


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

	  movf INDF1, W,ACCESS

          return


#else
 error "No Model Specified"
#endif
#endif
  end
