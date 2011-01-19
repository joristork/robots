; $Id: scatr2p.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcatram2pgm
;The {\bf strcatram2pgm} function performs a {\bf strcat} where {\bf s1}
;points to program memory and {\bf s2} point to data memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in data memory
;
;rom char *strcatram2pgm (rom char *s1, const char *s2);

  extern __RETVAL0

STRING CODE
strcatram2pgm   FUSTART
  global strcatram2pgm


#ifdef __SMALL__
  messg "strcatram2pgm - SMALL"

  ; Procedure: Use FSR0 for 'src' and TBLPTR for 'dest'
  ;         Assumes TBLPTRU = 0.
  ; Load TBLPTR with the 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Make a copy for the return value

	  movff TBLPTRL, PRODL
	  movff TBLPTRH, PRODH

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -4,FSR0L

  ; Find end of 'dest'
jNullLoop:
  ; Test for '\0'
          tblrd     *+                  ; read & increment
          tstfsz    TABLAT,ACCESS
          bra       jNullLoop

  ; Backup over '\0'
          tblrd     *-                  ; read & decrement

jLoop:
          movff     POSTINC0,TABLAT
          tblwt     *+                  ; write & increment

  ; Test for '\0'
          tstfsz    TABLAT,ACCESS
          bra       jLoop

jEnd

          FUEND
          return

#else
#ifdef __LARGE__
  messg "strcatram2pgm - LARGE"

  ; Procedure: Use FSR0 for 'src' and TBLPTR for 'dest'
  ; Load TBLPTR with the 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Make a copy for the return value

          Stk3CpyToReg -3, __RETVAL0

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -5,FSR0L

  ; Find end of 'dest'
jNullLoop:
  ; Test for '\0'
          tblrd     *+                  ; read & increment
          tstfsz    TABLAT,ACCESS
          bra       jNullLoop

  ; Backup over '\0'
          tblrd     *-                  ; read & decrement

jLoop:
          movff     POSTINC0,TABLAT
          tblwt     *+                  ; write & increment

  ; Test for '\0'
          tstfsz    TABLAT,ACCESS
          bra       jLoop

jEnd

          FUEND
          return


#else
 error "No Model Specified"
#endif
#endif
  end
