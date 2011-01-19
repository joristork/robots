; $Id: sncatr2p.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncatram2pgm
;The {\bf strncatram2pgm} function performs a {\bf strncat} where {\bf s1}
;points to program memory and {\bf s2} point to data memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in data memory
;@param n maximum number of characters to copy
;
;rom char *strncatram2pgm (rom char *s1, const char *s2, sizeram_t n);

  extern __RETVAL0

STRING CODE
strncatram2pgm   FUSTART
  global strncatram2pgm


#ifdef __SMALL__
  messg "strncatram2pgm - SMALL"

  ; Procedure: Use FSR0 for 'src' and TBLPTR for 'dest'
  ;         Assumes TBLPTRU = 0.
  ; Load TBLPTR with the 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; make a copy for the return value

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
  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n

          Stk2Dec    -6
 
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
  messg "strncatram2pgm - LARGE"

  ; Procedure: Use FSR0 for 'src' and TBLPTR for 'dest'
  ; Load TBLPTR with the 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; make a copy for the return value

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
  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n

          Stk2Dec    -7

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
