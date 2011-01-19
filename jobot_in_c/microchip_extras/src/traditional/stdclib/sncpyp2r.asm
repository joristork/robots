; $Id: sncpyp2r.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncpypgm2ram
;The {\bf strncpypgm2ram} function performs a {\bf strncpy} where
;{\bf s1} points to data memory and {\bf s2} points to program
;memory.
;@param s1 pointer to destination in data memory
;@param s2 pointer to source in program memory
;@param n maximum number of characters to copy
;
; char *strncpypgm2ram (char *s1, const rom char *s2, sizeram_t n);

STRING CODE
strncpypgm2ram
  global strncpypgm2ram


#ifdef __SMALL__
 messg "strncpypgm2ram - SMALL"


; Proceedure: Use FSR0 for 'dest' and TBLPTR for 'src' 
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'src' pointer

          Stk2CpyToReg -4,TBLPTRL

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -2,FSR0L

  ; make a copy for the retyrn value

	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n 

          Stk2Dec    -6

          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF0

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          tblrd     *-                  ; decrement
          bra       jLoop

jEnd

          return

#else
#ifdef __LARGE__
 messg "strncpypgm2ram - LARGE"


; Proceedure: Three byte rom pointer.
;         Use FSR0 for 'dest' and TBLPTR for 'src' 

  ; Load TBLPTR with the 'src' pointer

          Stk3CpyToReg -5,TBLPTRL

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -2,FSR0L

  ; make a copy for the retyrn value

	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

jLoop:
  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n 

          Stk2Dec    -7

          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF0

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          tblrd     *-                  ; decrement
          bra       jLoop

jEnd

          return

#else
 error "No Model Specified"
#endif
#endif
  end
