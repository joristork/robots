; $Id: mchrpgm.asm,v 1.4 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memchrpgm
; The {\bf memchrpgm} function performs a {\bf memchr} where {\bf s}
; points to program memory.
; @param s pointer to object to search
; @param c character to search for
; @param n maximum number of chararacters to search
;
; rom char  *memchrpgm(const rom char *s, const unsigned char c, sizerom_t n);

extern __RETVAL0

STRING CODE
memchrpgm  FUSTART
  global memchrpgm

#ifdef __SMALL__
 messg "memchrpgm - SMALL"


; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use TBLPTR for 's'.  Use FSR0 to point to 'c'.
;         Assumes TBLPTRU = 0.


  ; Load TBLPTR with the 's' pointer
          Stk2CpyToReg -2,TBLPTRL

  ; Set FSR0 to point to 'c'.
          StkSetPtrToOffset FSR0L,-3

jLoop:

  ; Test n for zero.  Note that n is three bytes

          Stk3TestForZero -6,jNoMatch

  ; Decrement n

          Stk3Dec   -6

          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS

  ; Compare with 'c'
          subwf     INDF0, W, ACCESS
          bnz       jLoop               ; no match

  ; Found match - Put TBLPTR-1 (which points to match character) in PROD
  ; since as of 1.0.5 return values of 16bits or less are returned in prod.
          tblrd     *-
          ; Stk2CpyFromReg TBLPTRL,-3
	movff TBLPTRL, PRODL
	movff TBLPTRH, PRODH
          return

jNoMatch
  ; Load 2 byte NULL to stack at FSR0
          movlw     -3
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          movlw     -2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          FUEND
          return

#else
#ifdef __LARGE__
 messg "memchrpgm - LARGE"


; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use TBLPTR for 's'.  Use FSR0 to point to 'c'.
;         Three byte rom pointer.


  ; Load TBLPTR with the 's' pointer
          Stk3CpyToReg -3,TBLPTRL

  ; Set FSR0 to point to 'c'.
          StkSetPtrToOffset FSR0L,-4

jLoop:

  ; Test n for zero.  Note that n is three bytes

          Stk3TestForZero -7,jNoMatch

  ; Decrement n

          Stk3Dec   -7

          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS

  ; Compare with 'c'
          subwf     INDF0, W, ACCESS
          bnz       jLoop               ; no match

  ; Found match - Put TBLPTR-1 (which points to match character) in stack at 'c'.
          tblrd     *-
          Stk3CpyFromReg TBLPTRL,-4
  ; Copy three bytes from stack frame at offset to __RETVAL0 
          Stk3CpyToReg -4,__RETVAL0 
          return

jNoMatch
  ; Load 3 byte NULL to stack at FSR0
          movlw     -4
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          movlw     -3
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
          movlw     -2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      PLUSW1,ACCESS
  ; Copy the three NULL Bytes to __RETVAL0 before retuning to the caller.
          Stk3CpyToReg -4,__RETVAL0
          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end

