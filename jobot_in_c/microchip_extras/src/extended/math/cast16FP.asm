#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
;**********************************************************************************************
; __cast16uFP:
; __cast16sFP:
;
; Description:
;
;         Two byte integer to Float conversion
;
; Input:
;         __cast16uFP: Two byte unsigned integer
;         __cast16sFP: Two byte signed integer
;
;            FSR1  --> [2] Unused slot
;                    > [1] Arg   i2
;                    > [0] Arg   iLo3
; Output:
;                             
;         IEEE 754 format float 
;            
;            FSR1 --> [4] Unused slot
;                   > [3] Return fBexp
;                   > [2] Return fB0  
;                   > [1] Return fB1  
;                   > [0] Return fB2  
;                   
;----------------------------------------------------------------------------------------------

          GLOBAL __cast16uFP
          GLOBAL __cast16sFP

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__cast16uFP: FUSTART
          bcf       STATUS,N            ; Flag as unsigned
          RELOCTLMERGE jBeg
          FUEND
          bra       jBeg

__cast16sFP:  FUSTART
          bsf       STATUS,N            ; Flag as signed

jBeg:
          ;; SetupStack doesn't effect STATUS bits
          SetupStack 2,LARGE_STK       ; 2 already allocated

          bcf       [fFlg],FLG_SIGN_ABW ; Flag as positive
          bnn       jNotNeg             ; Unsigned entry

          ;; Signed entry. If negative => negate and set flag
          braClr    [i2],7,jNotNeg

          ;; Negate
          Neg2      [iLo3],[i2]
          bsf       [fFlg],FLG_SIGN_ABW ; Flag as negative
jNotNeg:

          ;; Copy integer to fW
          movss     [i2],[fW0]
          movss     [iLo3],[fW1]
          clrf      [fW2]
          clrf      [fW3]

          movlw     0x8E
          movwf     [fWexp]
          clrf      [fWexpHi]

          ;; Sign is Flg.FLG_SIGN_ABW
          FUEND
          goto      __FpPack

          END
