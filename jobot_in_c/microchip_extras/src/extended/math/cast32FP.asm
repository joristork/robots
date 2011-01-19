#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
;**********************************************************************************************
; __cast32uFP:
; __cast32sFP:
;
; Description:
;
;         Four byte integer to Float conversion
;
; Input:
;         __cast32uFP: Four byte unsigned integer
;         __cast32sFP: Four byte signed integer
;
;            FSR1  --> [4] Unused slot
;                    > [3] Arg   iHi0  
;                    > [2] Arg   i1
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

          GLOBAL __cast32uFP
          GLOBAL __cast32sFP

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__cast32uFP: FUSTART
          bcf       STATUS,N            ; Flag as unsigned
          RELOCTLMERGE jBeg
          FUEND
          bra       jBeg

__cast32sFP: FUSTART
          bsf       STATUS,N            ; Flag as signed

jBeg:
          ;; SetupStack doesn't effect STATUS bits
          SetupStack 4,LARGE_STK       ; 4 already allocated

          bcf       [fFlg],FLG_SIGN_ABW ; Flag as positive
          bnn       jNotNeg             ; Unsigned entry

          ;; Signed entry. If negative => negate and set flag
          braClr    [iHi0],7,jNotNeg

          ;; Negate
          Neg4      [iLo3],[i2],[i1],[iHi0]
          bsf       [fFlg],FLG_SIGN_ABW ; Flag as negative

jNotNeg:
          ;; Copy integer to fW
          movss     [iHi0],[fW0]
          movss     [i1],[fW1]
          movss     [i2],[fW2]
          movss     [iLo3],[fW3]

          movlw     0x9E
          movwf     [fWexp]
          clrf      [fWexpHi]

          ;; Sign is Flg.FLG_SIGN_ABW
          FUEND
          goto      __FpPack


          END
