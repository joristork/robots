#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; TAEQB32
;
; Description:
;
;         Test if fA == fB
;
; Input:
;         IEEE 754 format floats fAexp...fA2, fBexp...fB2.
;
; Output:
;         WREG = 1 if fA == fB 
;         WREG = 0 if fA != fB or either fA or Fb is a NaN
;
;            
;----------------------------------------------------------------------------------------------

          GLOBAL TAEQB32

          EXTERN __FpCompare

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************

TAEQB32:

;------ Make comparison
;         fFlgs set as follows:
;         FLG_NaN_AorB        => NaN
;         FLG_ZERO_A          => A == B
;         FLG_SIGN_A          => A < B

          call      __FpCompare

          btfsc     fFlg,FLG_NaN_AorB
          retlw     0                   ; NaN detected

          btfsc     fFlg,FLG_ZERO_A
          retlw     1                   ; fA == fB

          retlw     0                   ; fA != fB

          END
