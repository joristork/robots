#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; FLO1632U:
; FLO1632S:
;
; Description:
;
;         Two byte integer to Float conversion
;
; Input:
;         FLO1632U: Two byte unsigned integer in iA0,iA1
;         FLO1632S: Two byte signed integer in iA0,iA1
;
; Output:
;
;         IEEE 754 format float fA
;            
;----------------------------------------------------------------------------------------------

          GLOBAL FLO1632U
          GLOBAL FLO1632S

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


FLO1632U:
          bcf       fFlg,FLG_SIGN_ABW   ;; Flag as unsigned
          bra       jI2F16Beg

FLO1632S:
          bcf       fFlg,FLG_SIGN_ABW
          braClr    iA0,7,jI2F16Beg

          ;; Negate
          Neg2      iA1,iA0
          bsf       fFlg,FLG_SIGN_ABW

jI2F16Beg:

          ;; Copy integer to fW
          movff     iA0,fW0
          movff     iA1,fW1
          clrf      fW2
          clrf      fW3

          movlw     0x8E
          movwf     fWexp
          clrf      fWexpHi

;; Sign is Flg.FLG_SIGN_ABW
          goto      __FpPack


          END
