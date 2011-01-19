; $Id: scmpr2p.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcmpram2pgm
;The {\bf strcmpram2pgm} function performs a {\bf strcmp} where {\bf s1}
;points to program memory and {\bf s2} point to data memory.
;@param s1 pointer to string in program memory
;@param s2 pointer to string in data memory
;
;signed char strcmpram2pgm (const rom char *s1, const char *s2);


STRING CODE
strcmpram2pgm
  global strcmpram2pgm


#ifdef __SMALL__
 messg "strcmpram2pgm - SMALL"


; Proceedure: Use FSR0 for 'src' and TBLPTR for 'dest'
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:
          movf      POSTINC0, W, ACCESS
          tblrd     *+                  ; read & increment

          subwf     TABLAT, W, ACCESS
          bnz       jEnd                ; unequal - diff in WREG

  ; Here only if bytes are equal. Need to test for '\0' on only one byte.
          tstfsz    TABLAT,ACCESS
          bra       jLoop


  ; Falls through with WREG = 0

jEnd

          return

#else
#ifdef __LARGE__
 messg "strcmpram2pgm - LARGE"


; Proceedure: Three byte rom pointer.
;         Use FSR0 for 'scr' and TBLPTR for 'dest'

  ; Load TBLPTR with the 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -5,FSR0L

jLoop:
          movf      POSTINC0, W, ACCESS
          tblrd     *+                  ; read & increment

          subwf     TABLAT, W, ACCESS
          bnz       jEnd                ; unequal - diff in WREG

  ; Here only if bytes are equal. Need to test for '\0' on only one byte.
          tstfsz    TABLAT,ACCESS
          bra       jLoop

  ; Falls through with WREG = 0

jEnd

          return

#else
 error "No Model Specified"
#endif
#endif
  end
