; $Id: sncpyr2p.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncpyram2pgm
;The {\bf strncpyram2pgm} function performs a {\bf strncpy} where
;{\bf s1} points to program memory and {\bf s2} points to data memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in data memory
;@param n maximum number of characters to copy
;
; rom char *strncpyram2pgm (rom char *s1, const char *s2, sizeram_t n);

  extern __AARGB2
STRING CODE
strncpyram2pgm
  global strncpyram2pgm


#ifdef __SMALL__
 messg "strncpyram2pgm - SMALL"


; Proceedure: Use FSR0 for 'src' and TBLPTR for 'dest'
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; make a copy for the return value

	  movff TBLPTRL, PRODL
	  movff TBLPTRH, PRODH

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n

          Stk2Dec    -6

          movff     INDF0,TABLAT
          tblwt     *+                  ; write & increment

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          movf      POSTDEC0, W, ACCESS ; decrement
          bra       jLoop

jEnd

          return

#else
#ifdef __LARGE__
 messg "strncpyram2pgm - LARGE"


; Proceedure: Use FSR0 for 'src' and TBLPTR for 'dest'

  ; Load TBLPTR with the 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -5,FSR0L

jLoop:
  ; Test 3 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n

          Stk2Dec    -7

          movff     INDF0,TABLAT
          tblwt     *+                  ; write & increment

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          movf      POSTDEC0, W, ACCESS ; decrement
          bra       jLoop

jEnd

  ; Copy three bytes from stack frame at offset to __AARGB2
          Stk3CpyToReg -3,__AARGB2

          return

#else
 error "No Model Specified"
#endif
#endif
  end
