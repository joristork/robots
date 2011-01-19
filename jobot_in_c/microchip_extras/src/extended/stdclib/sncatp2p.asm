; $Id: sncatp2p.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncatpgm
;The {\bf strncatpgm} function performs a {\bf strncat} where both
;{\bf s1} and {\bf s2} point to program memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in program memory
;@param n maximum number of characters to copy
;
;rom char *strncatpgm (rom char *s1, const rom char *s2, sizerom_t n);

extern __RETVAL0

STRING CODE
strncatpgm   FUSTART
  global strncatpgm


#ifdef __SMALL__
 messg "strncatpgm - SMALL"

; Proceedure: Use temporary copy of dest pointer to increment.
;         Assumes TBLPTRU = 0.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset FSR0L,-2

  ; make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

  ; Load TBLPTR with the temporary 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Find end of 'dest'
jNullLoop:
  ; Test for '\0'
          tblrd     *+                  ; read & increment
          tstfsz    TABLAT,ACCESS
          bra       jNullLoop

  ; Backup over '\0'
          tblrd     *-                  ; read & decrement

  ; Write TBLPTR to the temporary 'dest' pointer

          Stk2CpyFromReg TBLPTRL,-2

jLoop:
  ; Test 3 byte n for zero

          Stk3TestForZero -7,jEnd

  ; Decrement n

          Stk3Dec    -7

  ; Load TBLPTR with the 'src' pointer

          Stk2CpyToReg -4,TBLPTRL

          tblrd     *                   ; read

  ; Load TBLPTR with the temporary 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

          tblwt     *                   ; write

          Stk2Inc   -4                ; increment source pointer
          Stk2Inc   -2                ; increment copy of dest pointer

  ; Test for '\0'
          tstfsz    TABLAT,ACCESS
          bra       jLoop

jEnd

  ; Return value: PRODL already set to 'dest'.
          FUEND
          return

#else
#ifdef __LARGE__
 messg "strncatpgm - LARGE"

; Proceedure: Use temporary copy of dest pointer to increment.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset FSR0L,-3

  ; Push a copy of dest onto stack to use for temporary pointer
          Stk3PushFromFSR0

  ; Load TBLPTR with the temporary 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Find end of 'dest'
jNullLoop:
  ; Test for '\0'
          tblrd     *+                  ; read & increment
          tstfsz    TABLAT,ACCESS
          bra       jNullLoop

  ; Backup over '\0'
          tblrd     *-                  ; read & decrement

  ; Write TBLPTR to the temporary 'dest' pointer

          Stk3CpyFromReg TBLPTRL,-3

jLoop:
  ; Test 3 byte n for zero

          Stk3TestForZero -12,jEnd

  ; Decrement n

          Stk2Dec    -12


  ; Load TBLPTR with the 'src' pointer

          Stk3CpyToReg -9,TBLPTRL

          tblrd     *                   ; read

  ; Load TBLPTR with the temporary 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

          tblwt     *                   ; write

          Stk3Inc   -9                ; increment source pointer
          Stk3Inc   -3                ; increment copy of dest pointer

  ; Test for '\0'
          tstfsz    TABLAT,ACCESS
          bra       jLoop

jEnd
   ; restore stack
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,W,ACCESS
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,W,ACCESS
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,W,ACCESS

  ; Return value: set __RETVAL0 to 'dest'.
          Stk3CpyToReg -3,__RETVAL0
          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end
