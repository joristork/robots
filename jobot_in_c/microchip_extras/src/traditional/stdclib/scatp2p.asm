; $Id: scatp2p.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcatpgm
;The {\bf strcatpgm} function performs a {\bf strcat} where both
;{\bf s1} and {\bf s2} point to program memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in program memory
;
;rom char *strcatpgm (rom char *s1, const rom char *s2);

  extern __AARGB2
STRING CODE
strcatpgm
  global strcatpgm


#ifdef __SMALL__
 messg "strcatpgm - SMALL"

; Proceedure: Use temporary copy of dest pointer to increment.
;         Assumes TBLPTRU = 0.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset FSR0L,-2

  ; Make a copy for the return value
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

          return

#else
#ifdef __LARGE__
 messg "strcatpgm - LARGE"

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
   ; Return value: set AARGB2 to 'dest'. 
     Stk3CpyToReg -6,__AARGB2   
   ; restore stack
          movf      POSTDEC1,W,ACCESS
          movf      POSTDEC1,W,ACCESS
          movf      POSTDEC1,W,ACCESS

          return

#else
 error "No Model Specified"
#endif
#endif
  end
