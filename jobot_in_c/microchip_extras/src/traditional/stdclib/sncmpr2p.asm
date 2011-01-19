; $Id: sncmpr2p.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncmpram2pgm
;The {\bf strncmpram2pgm} function performs a {\bf strncmp} where {\bf s1}
;points to program memory and {\bf s2} point to data memory.
;@param s1 pointer to string in program memory
;@param s2 pointer to string in data memory
;@param n number of characters to compare
;
;signed char strncmpram2pgm (rom char *s1, const char *s2, sizeram_t n);


STRING CODE
strncmpram2pgm
  global strncmpram2pgm


#ifdef __SMALL__
 messg "strncmpram2pgm - SMALL"


; Proceedure: Use FSR0 for 'src' and TBLPTR for 'dest'
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n

          Stk2Dec    -6

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
 messg "strncmpram2pgm - LARGE"


  ; Load TBLPTR with the 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -5,FSR0L

jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n

          Stk2Dec    -7

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
