; $Id: scpyp2r.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcpypgm2ram
;The {\bf strcpypgm2ram} function performs a {\bf strcpy} where
;{\bf s1} points to data memory and {\bf s2} points to program
;memory.
;@param s1 pointer to destination in data memory
;@param s2 pointer to source in program memory
;
; char *strcpypgm2ram (char *s1, const rom char *s2);

STRING CODE
strcpypgm2ram   FUSTART
  global strcpypgm2ram


#ifdef __SMALL__
 messg "strcpypgm2ram - SMALL"


; Proceedure: Use FSR0 for 'dest' and TBLPTR for 'src' 
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'src' pointer

          Stk2CpyToReg -4,TBLPTRL

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -2,FSR0L

  ; Make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

jLoop:
          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF0

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

jEnd

          FUEND
          return

#else
#ifdef __LARGE__
 messg "strcpypgm2ram - LARGE"


; Proceedure: Three byte rom pointer.
;         Use FSR0 for 'dest' and TBLPTR for 'src' 

  ; Load TBLPTR with the 'src' pointer

          Stk3CpyToReg -5,TBLPTRL

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -2,FSR0L

  ; Make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

jLoop:
          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF0

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

jEnd
          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end
