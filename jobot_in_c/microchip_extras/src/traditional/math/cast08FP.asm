#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; FLO0832U:
; FLO0832S:
;
; Description:
;
;         One byte integer to Float conversion
;
; Input:
;         FLO0832U: One byte unsigned integer in iA0
;         FLO0832S: One byte signed integer in iA0
;
; Output:
;
;         IEEE 754 format float fA
;            
;----------------------------------------------------------------------------------------------

          GLOBAL FLO0832U
          GLOBAL FLO0832S

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


FLO0832U:
          bcf       fFlg,FLG_SIGN_ABW   ;; Flag as unsigned
          bra       jI2F08Beg

FLO0832S:
          bcf       fFlg,FLG_SIGN_ABW
          braClr    iA0,7,jI2F08Beg

          ;; Negate
          negf      iA0
          bsf       fFlg,FLG_SIGN_ABW

jI2F08Beg:

          ;; Copy integer to fW
          movff     iA0,fW0
          clrf      fW1
          clrf      fW2
          clrf      fW3

          movlw     0x86
          movwf     fWexp
          clrf      fWexpHi

;; Sign is Flg.FLG_SIGN_ABW
          goto      __FpPack


          END
