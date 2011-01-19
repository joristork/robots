#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; FLO2432U:
; FLO2432S:
;
; Description:
;
;         Three byte integer to Float conversion
;
; Input:
;         FLO2432U: Three byte unsigned integer in iA0,iA1,iA2
;         FLO2432S: Three byte signed integer in iA0,iA1,iA2
;
; Output:
;
;         IEEE 754 format float fA
;            
;----------------------------------------------------------------------------------------------

          GLOBAL FLO2432U
          GLOBAL FLO2432S

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


FLO2432U:
          bcf       fFlg,FLG_SIGN_ABW   ;; Flag as unsigned
          bra       jI2F24Beg

FLO2432S:
          bcf       fFlg,FLG_SIGN_ABW
          braClr    iA0,7,jI2F24Beg

          ;; Negate
          Neg3      iA2,iA1,iA0
          bsf       fFlg,FLG_SIGN_ABW

jI2F24Beg:

          ;; Copy integer to fW
          movff     iA0,fW0
          movff     iA1,fW1
          movff     iA2,fW2
          clrf      fW3

          movlw     0x96
          movwf     fWexp
          clrf      fWexpHi

;; Sign is Flg.FLG_SIGN_ABW
          goto      __FpPack


          END
