; $Id: sspnpr.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strspnpgmram
;``The {\bf strspnpgmram} function computes the length of the maximum initial
;segment of the string pointed to by {\bf s1} which consists entirely
;of characters from the string pointed to by {\bf s2}.''
;@param s1 pointer to string to span
;@param s2 pointer to set of characters
;@return ``The {\bf strspnpgmram} function returns the length of the segment.''
;
; sizerom_t strspnpgmram (const rom char *s1, const char *s2);

  extern __AARGB2
STRING CODE
strspnpgmram
  global strspnpgmram


#ifdef __SMALL__
 messg "strspnpgmram - SMALL"


; Proceedure: Use TBLPTR as 's1' pointer and FSR0 as 's2' pointer.

  ; Load TBLPTR with the 's1' pointer
          Stk2CpyToReg -2,TBLPTRL

  ; Use location of 's1' to store count.  Zero it.
          movlw     -2
          clrf      PLUSW1,ACCESS
          movlw     -1
          clrf      PLUSW1,ACCESS

  ; Add extra byte to stack so we can hold three byte count
          clrf      POSTINC1,ACCESS


jOuterLoop:

  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'

  ; Load FSR0 with the 's2' pointer

          Stk2CpyToReg -4-1,FSR0L

jInnerLoop:

  ; Test for '\0' in 's2'
          movf      POSTINC0, W, ACCESS
          bz        jNoMatch             ; found end of 's2'

  ; Compare 's2' character with 's1' character at FSR0
          subwf     TABLAT, W, ACCESS
          bnz       jInnerLoop

  ; count match
          Stk3Inc   -2-1
          bra       jOuterLoop

jNull:
jNoMatch:
  ; Found mismatch or reached end of 's1'.  Return FSR0 pointing to count.

          movf      POSTDEC1, F, ACCESS ; discard scratch

          StkSetPtrToOffset FSR0L,-2
          return
#else
#ifdef __LARGE__
 messg "strspnpgmram - LARGE"

; Proceedure: Use TBLPTR as 's1' pointer and FSR0 as 's2' pointer.

  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg -3,TBLPTRL

  ; Use location of 's1' to store count.  Zero it.
          movlw     -3
          clrf      PLUSW1,ACCESS
          movlw     -2
          clrf      PLUSW1,ACCESS
          movlw     -1
          clrf      PLUSW1,ACCESS

jOuterLoop:

  ; Test for '\0' in 's1'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jNull               ; found end of 's1'

  ; Load FSR0 with the 's2' pointer

          Stk2CpyToReg -5,FSR0L

jInnerLoop:

  ; Test for '\0' in 's2'
          movf      POSTINC0, W, ACCESS
          bz        jNoMatch             ; found end of 's2'

  ; Compare 's2' character with 's1' character at FSR0
          subwf     TABLAT, W, ACCESS
          bnz       jInnerLoop

  ; count match
          Stk3Inc   -3
          bra       jOuterLoop

jNull:
jNoMatch:
  ; Found mismatch or reached end of 's1'. 
  ; Copy three bytes from stack frame at offset to __AARGB2
           Stk3CpyToReg -3,__AARGB2
          return

#else
 error "No Model Specified"
#endif
#endif
  end

