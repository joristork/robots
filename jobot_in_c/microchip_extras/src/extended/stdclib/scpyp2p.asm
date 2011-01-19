; $Id: scpyp2p.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcpypgm
;The {\bf strcpypgm} function performs a {\bf strcpy} where {\bf s1}
;points to program memory and {\bf s2} point to program memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in program memory
;
;rom char *strcpypgm (rom char *s1, const rom char *s2);

extern __RETVAL0

STRING CODE
strcpypgm   FUSTART
  global strcpypgm


#ifdef __SMALL__
 messg "strcpypgm - SMALL"


; Proceedure: Use temporary copy of dest pointer to increment.
;         Assumes TBLPTRU = 0.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset PRODL,-2

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

   ; restore stack
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,W,ACCESS
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,W,ACCESS

  ; Return value: PROD already set to 'dest'.
          FUEND
          return

#else
#ifdef __LARGE__
 messg "strcpypgm - LARGE"

; Proceedure: Use temporary copy of dest pointer to increment.

  ; Set FSR0 to point to destination. Leaves FSR0 unchanged.
          StkSetPtrToOffset FSR0L,-3

  ; Push a copy of dest onto stack to use for temporary pointer
          Stk3PushFromFSR0 

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
