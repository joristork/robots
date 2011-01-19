#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
;**********************************************************************************************
;    __ltFP
;
; Description:
;
;         Test if fA < fB
;
; Input:
;         IEEE 754 format floats [fAexp]...[fA2], [fBexp]...[fB2].
;
;               FSR1 --> [ 8] Unused slot
;                      > [ 7] Arg     fAexp
;                      > [ 6] Arg     fA0  
;                      > [ 5] Arg     fA1  
;                      > [ 4] Arg     fA2  
;                      > [ 3] Arg     fBexp
;                      > [ 2] Arg     fB0  
;                      > [ 1] Arg     fB1  
;               FSR2 --> [ 0] Arg     fB2  
;
; Output:
;         WREG = 1 if fA < fB 
;         WREG = 0 if fA >= fB or either fA or Fb is a NaN
;
;               FSR1 --> Unused slot
;            
;----------------------------------------------------------------------------------------------

          GLOBAL __ltFP

          EXTERN __FpCompare

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************

__ltFP:   FUSTART
          ;; Make comparisons, allocate and deallocate stack
          call      __FpCompare

          ;; WREG set as follows:
          ;; FLG_NaN_AorB        => NaN
          ;; FLG_ZERO_A          => A == B
          ;; FLG_SIGN_A          => A < B

          btfsc     WREG,FLG_NaN_AorB
          retlw     0                   ; NaN detected

          btfsc     WREG,FLG_ZERO_A
          retlw     0                   ; fA == fB

          btfss     WREG,FLG_SIGN_A
          retlw     0                   ; fA > fB
          FUEND
          retlw     1                   ; fA < fB

          END
