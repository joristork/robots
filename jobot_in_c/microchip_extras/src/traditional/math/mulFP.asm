#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;******************************************************************************
;******************************************************************************
; FPM32
;
; Description:
;
;         Mutiplication  fA*fB for floats fA and fB
;
; Input:
;         IEEE 754 format floats fAexp...fA2, fBexp...fB2.
;
; Output:
;
;         IEEE 754 format float fA = fA*fB
;            
;------------------------------------------------------------------------------

          GLOBAL FPM32

          EXTERN __FpUnPackA, __FpUnPackB, __FpPack
          EXTERN __FpReturnNaN
          EXTERN __FpReturnPosZero,__FpReturnNegZero,__FpReturnSignZero     
          EXTERN __FpReturnPosInf, __FpReturnNegInf, __FpReturnSignInf

PROG    CODE

;******************************************************************************
;******************************************************************************


FPM32

;------ Unpack the operands
          clrf      fFlg
          call      __FpUnPackA
          call      __FpUnPackB

          gotoSet   fFlg,FLG_NaN_AorB,__FpReturnNaN

          braClr    fFlg,FLG_INF_A,jANotInf

;------ A is Infinity

          ;; Is b zero
          gotoSet   fFlg,FLG_ZERO_B,__FpReturnNaN  ; Inf * Zero => return NaN

          ;; B finite, A infinite => return infinite with FLG_SIGN_ABW sign
          goto      __FpReturnSignInf

jANotInf:
          ;; A not infinite
          braClr    fFlg,FLG_INF_B,jBothFinite

          ;; A finite, B infinite
          gotoSet   fFlg,FLG_ZERO_A,__FpReturnNaN  ; Zero * Inf => return NaN
          goto      __FpReturnSignInf

jBothFinite:

;------ Both operands are finite: check for zeros

          braSet    fFlg,FLG_ZERO_A,jHaveZero
          braClr    fFlg,FLG_ZERO_B,jBothNotZero

jHaveZero:
          ;; A or B is zero
          ;; Return zero with sign of product
          goto      __FpReturnSignZero

jBothNotZero:


;-----------------------------------------------------------------------;
;       Both operands are finite and non-zero
;-----------------------------------------------------------------------;

          ;; Calculate resulting exponent (two byte resultant)
          ;; ExpW = (ExpA+127) + (ExpB+127) - 127 + 1

          clrf      fWexpHi

          movf      fBexp,W
          addwf     fAexp,W
          movwf     fWexp
          clrf      WREG
          addwfc    fWexpHi,F

          movlw     FLT_BIAS-1
          subwf     fWexp,F
          clrf      WREG
          subwfb    fWexpHi,F

;; Not all products are calculated and not all products are included
;; This will have a slight effect on the rounding and could cause 
;; a round toward zero when a round up is required by IEEE754 
;; round to nearest mode.  Effectively the round up threshold is changed 
;; from 0.5 to about 0.5001
;; 
;; a0b0    a0b0                    not     not  
;;         a1b0    a1b0            added   added
;;         a0b1    a0b1             |       |
;;                 a2b0    a2b0     V       V
;;                 a1b1    a1b1
;;                 a0b2    a0b2
;;                         a1b2    a1b2
;;                         a2b1    a2b1
;;                                 a2b2    a2b2
;; added to get
;; 
;; fW0      fW1     fW2     fW3    NA      NA
;; 
;; If msb of fW0   is 0 then the results will have to be left shifted
;; which will move the msb of fA3 into fA2. After this is done then
;; the msb of fW3 becomes the Round bit and the rest of the less significant
;; bits combine to form the Sticky bit.  Since the full product is not
;; calculated fW3 could be a maximum of 2 too small. Since bits 0 -> 6 of
;; fW3 are used only for rounding the effect is a very small failure to 
;; round up.

          Clear4    fW3, fW2, fW1, fW0
          
          Mul2      fA0,fB0
          movff     PRODH,fW0
          movff     PRODL,fW1
          Mul2      fA0,fB1
          Add32     fW2,fW1,fW0, PRODL, PRODH
          Mul2      fA1,fB0
          Add32     fW2,fW1,fW0, PRODL, PRODH
          Mul2      fA0,fB2
          Add42     fW3,fW2,fW1,fW0, PRODL, PRODH
          Mul2      fA1,fB1
          Add42     fW3,fW2,fW1,fW0, PRODL, PRODH
          Mul2      fA2,fB0
          Add42     fW3,fW2,fW1,fW0, PRODL, PRODH
          Mul2      fA1,fB2
          Add41     fW3,fW2,fW1,fW0, PRODH
          Mul2      fA2,fB1
          Add41     fW3,fW2,fW1,fW0, PRODH

;; Start with form 0.f * 0.f
;; Results can be fW0 = 11xxxxxx -> 01xxxxxx
;; Round and partial Sticky bits in fW3. 
;; One bit of signficant in fW3 in the 01xxxxxx case

;; Sign is Flg.FLG_SIGN_ABW
          goto      __FpPack


          END
