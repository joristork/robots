; $Id: scspnpr.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcspnpgmram
;The {\bf strcspnpgmram} function performs a {\bf strcspn}.
;@param s1 pointer to string in program memory
;@param s2 pointer to string in data memory
;
;sizerom_t strcspnpgmram(const rom char *s1, const char *s2);

extern __RETVAL0

STRING CODE
strcspnpgmram   FUSTART
  global strcspnpgmram


#ifdef __SMALL__
 messg "strcspnpgmram - SMALL"


; Proceedure: Use TBLPTR as 's1' pointer and FSR0 as 's2' pointer.

  ; Load TBLPTR with the 's1' pointer
          Stk2CpyToReg -2,TBLPTRL

  ; Use location of 's1' to store count.  Zero it.
          movlw     -2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          movlw     -1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS

  ; Add extra byte to stack so we can hold three byte count
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
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
          bz        jInner1              ; found end of 's2'

  ; Compare 's2' character with 's1' character at FSR0
          subwf     TABLAT, W, ACCESS
          bz        jMatch               ; match
          bra       jInnerLoop

jInner1:
  ; count match
          Stk3Inc   -2-1
          bra       jOuterLoop

jNull:
jMatch:
  ; Found match or reached end of 's1'.  Return FSR0 pointing to count.

  ; Must move three bytes down by 1
          StkSetPtrToOffset FSR0L,-2-1
          movf      POSTDEC0, W, ACCESS
          movwf     POSTINC0, ACCESS
          movf      PREINC0, W, ACCESS
          movf      POSTDEC0, F, ACCESS
          movwf     POSTINC0, ACCESS
          movf      PREINC0, W, ACCESS
          movf      POSTDEC0, F, ACCESS
          movwf     POSTINC0, ACCESS

          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1, F, ACCESS ; discard scratch
          StkSetPtrToOffset FSR0L,-2-1
          FUEND
          return

#else
#ifdef __LARGE__
 messg "strcspnpgmram - LARGE"

; Proceedure: Use TBLPTR as 's1' pointer and FSR0 as 's2' pointer.

  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg -3,TBLPTRL

  ; Use location of 's1' to store count.  Zero it.
          movlw     -3
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          movlw     -2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          movlw     -1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
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
          bz        jInner1              ; found end of 's2'

  ; Compare 's2' character with 's1' character at FSR0
          subwf     TABLAT, W, ACCESS
          bz        jMatch               ; match
          bra       jInnerLoop

jInner1:
  ; count match
          Stk3Inc   -3
          bra       jOuterLoop

jNull:
jMatch:
  ; Found match or reached end of 's1'.  Return __RETVAL0 pointing to count.
          Stk3CpyToReg -3,__RETVAL0
          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end

