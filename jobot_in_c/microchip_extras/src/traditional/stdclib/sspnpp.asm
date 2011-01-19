; $Id: sspnpp.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strspnpgm
;``The {\bf strspnpgm} function computes the length of the maximum initial
;segment of the string pointed to by {\bf s1} which consists entirely
;of characters from the string pointed to by {\bf s2}.''
;@param s1 pointer to string to span
;@param s2 pointer to set of characters
;@return ``The {\bf strspnpgm} function returns the length of the segment.''
;
; sizerom_t strspnpgm (const rom char *s1, const rom char *s2);

  extern __AARGB2
STRING CODE
strspnpgm
  global strspnpgm


#ifdef __SMALL__
 messg "strspnpgm - SMALL"

; Proceedure: Use three extra bytes as counter location and one byte as scratch
; Assumes TBLPTRU = 0

  ; Add three extra bytes to stack so we can hold three byte count. Zero it.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
  ; Add scratch to hold char from s1
          clrf      POSTINC1,ACCESS

          StkSetPtrToOffset FSR0L,-1     ; set FSR0 to point to scratch byte

jOuterLoop:
  ; Load TBLPTR with the 's1' pointer
          Stk2CpyToReg -2-4,TBLPTRL

  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'
          movwf     INDF0, ACCESS       ; save in scratch

  ; Save TBLPTR to the 's1' pointer
          Stk2CpyFromReg TBLPTRL,-2-4

  ; Load TABPTR with the 's2' pointer
          Stk2CpyToReg -4-4,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNoMatch            ; found end of 's2'

  ; Compare 's2' character with 's1' character in scratch
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop

  ; count match
          Stk3Inc   -4
          bra       jOuterLoop

jNull:
jNoMatch:
  ; Found mismatch or reached end of 's1'.  Return FSR0 pointing to count.

          movf      POSTDEC1, F, ACCESS ; discard scratch

  ; Move count down to s2
          StkSetPtrToOffset FSR0L,-2-3

          movlw     0xFF
          movff     PLUSW1,POSTDEC0
          movf      POSTDEC1,F,ACCESS

          movlw     0xFF
          movff     PLUSW1,POSTDEC0
          movf      POSTDEC1,F,ACCESS

          movlw     0xFF
          movff     PLUSW1,INDF0        ; leave FSR0 pointing at base of count
          movf      POSTDEC1,F,ACCESS
          return

#else
#ifdef __LARGE__
 messg "strspnpgm - LARGE"

; Proceedure: Use three extra bytes as counter location and one byte as scratch

  ; Add three extra bytes to stack so we can hold three byte count. Zero it.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
  ; Add scratch to hold char from s1
          clrf      POSTINC1,ACCESS

          StkSetPtrToOffset FSR0L,-1     ; set FSR0 to point to scratch byte

jOuterLoop:
  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg -3-4,TBLPTRL

  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'
          movwf     INDF0, ACCESS       ; save in scratch

  ; Save TBLPTR to the 's1' pointer
          Stk3CpyFromReg TBLPTRL,-3-4

  ; Load TABPTR with the 's2' pointer
          Stk3CpyToReg -6-4,TBLPTRL

jInnerLoop:

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNoMatch            ; found end of 's2'

  ; Compare 's2' character with 's1' character in scratch
          subwf     INDF0, W, ACCESS
          bnz       jInnerLoop

  ; count match
          Stk3Inc   -4
          bra       jOuterLoop

jNull:
jNoMatch:
  ; Found mismatch or reached end of 's1'.  Return FSR0 pointing to count.

          movf      POSTDEC1, F, ACCESS ; discard scratch

  ; Copy three bytes from stack frame at offset to __AARGB2
           Stk3CpyToReg -3,__AARGB2

          movlw     0xFF
          movff     PLUSW1,POSTDEC0
          movf      POSTDEC1,F,ACCESS

          movlw     0xFF
          movff     PLUSW1,POSTDEC0
          movf      POSTDEC1,F,ACCESS

          movlw     0xFF
          movff     PLUSW1,INDF0        ; leave FSR0 pointing at base of count
          movf      POSTDEC1,F,ACCESS
          return
#else
 error "No Model Specified"
#endif
#endif
  end

