#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; FPD32
;
; Description:
;
;         Division  fA/fB for floats fA and fB
;
; Input:
;         IEEE 754 format floats fAexp...fA2, fBexp...fB2.
;
; Output:
;
;         IEEE 754 format float fA = fA/fB
;            
;----------------------------------------------------------------------------------------------

          GLOBAL FPD32

          EXTERN __FpUnPackA, __FpUnPackB, __FpPack
          EXTERN __FpReturnNaN
          EXTERN __FpReturnPosZero,__FpReturnNegZero,__FpReturnSignZero     
          EXTERN __FpReturnPosInf, __FpReturnNegInf, __FpReturnSignInf

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


FPD32

;------ Unpack the operands
          clrf      fFlg
          call      __FpUnPackA
          call      __FpUnPackB

          gotoSet   fFlg,FLG_NaN_AorB,__FpReturnNaN

          braClr    fFlg,FLG_INF_A,jANotInf

;------ A is Infinity

          ;; Is b also infinite?
          gotoSet   fFlg,FLG_INF_B,__FpReturnNaN

          ;; B finite, A infinite => return infinite with FLG_SIGN_ABW sign
          goto      __FpReturnSignInf

jANotInf

          ;; B infinite, A finite => return Zero
          gotoSet   fFlg,FLG_INF_B,__FpReturnSignZero

;------ Both operands are finite: check for zeros

          braClr    fFlg,FLG_ZERO_A,jANotZero

jSignedZero:
          ;; A == 0
          gotoSet   fFlg,FLG_ZERO_B,__FpReturnNaN     ; B == 0 => 0/0        
          goto      __FpReturnSignZero

jANotZero
          gotoSet   fFlg,FLG_ZERO_B,__FpReturnSignInf ; B == 0 => Finite/0

;-----------------------------------------------------------------------;
;       Both operands are finite and non-zero
;-----------------------------------------------------------------------;

          ;; Calculate resulting exponent (two byte resultant)
          ;; ExpW = (ExpA+127) - (ExpB+127) + 127

          clrf      fWexpHi

          movlw     FLT_BIAS
          addwf     fAexp,W
          movwf     fWexp
          clrf      WREG
          addwfc    fWexpHi,F

          movf      fBexp,W
          subwf     fWexp,F
          clrf      WREG
          subwfb    fWexpHi,F


;------ Form the quotient and remainder

          movlw     26                  ; quotient width
          movwf     fT1                 ; loop counter

          clrf      fAexp

          ;; Init quotient
          Clear4    fW3, fW2, fW1, fW0

jLoop:
          ;; fAsig -= fBsig 
          Sub43     fA2,fA1,fA0,fAexp, fB2,fB1,fB0
          
          bnn       jNoAdjust           ; fAsig >= 0

          ;; fAsig += fBsig 
          Add43     fA2,fA1,fA0,fAexp, fB2,fB1,fB0
          bra       jNext            ; Next quotient bit is zero ...

jNoAdjust:

          ;; Insert quotient bit. Location picked so quotient will be 
          ;; left-justified in fW0
          bsf       fW3,5               

jNext:
          ShiftL4   fA2,fA1,fA0,fAexp
          ShiftL4   fW3,fW2,fW1,fW0
          decfsz    fT1,F
          bra       jLoop

;;------- Sticky bits
;;
;; Quotient consists of 26 bits left-justified in fW0->fW3
;; Add sticky bit in position bit 5 of fW3 which is just to right of the 
;; Round bit.
          movf      fA0,W
          iorwf     fA1,W
          iorwf     fA2,W
          btfss     STATUS,Z
          bsf       fW3,5

;; Sign is Flg.FLG_SIGN_ABW
          goto      __FpPack

          END
