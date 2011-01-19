#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; FLO3232U:
; FLO3232S:
;
; Description:
;
;         Four byte integer to Float conversion
;
; Input:
;         FLO3232U: Four byte unsigned integer in iA0,iA1,iA2,iA3
;         FLO3232S: Four byte signed integer in iA0,iA1,iA2,iA3
;
; Output:
;
;         IEEE 754 format float fA
;            
;----------------------------------------------------------------------------------------------

          GLOBAL FLO3232U
          GLOBAL FLO3232S

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


FLO3232U:
          bcf       fFlg,FLG_SIGN_ABW   ;; Flag as unsigned
          bra       jI2F32Beg

FLO3232S:
          bcf       fFlg,FLG_SIGN_ABW
          braClr    iA0,7,jI2F32Beg

          ;; Negate
          Neg4      iA3,iA2,iA1,iA0
          bsf       fFlg,FLG_SIGN_ABW

jI2F32Beg:

          ;; Copy integer to fW
          movff     iA0,fW0
          movff     iA1,fW1
          movff     iA2,fW2
          movff     iA3,fW3

          movlw     0x9E
          movwf     fWexp
          clrf      fWexpHi

;; Sign is Flg.FLG_SIGN_ABW
          goto      __FpPack


          END
