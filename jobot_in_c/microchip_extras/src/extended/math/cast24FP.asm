#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
;**********************************************************************************************
; __cast24uFP:
; __cast24sFP:
;
; Description:
;
;         Three byte integer to Float conversion
;
; Input:
;         __cast24uFP: Three byte unsigned integer
;         __cast24sFP: Three byte signed integer
;
;            FSR1  --> [3] Unused slot
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

          GLOBAL __cast24uFP
          GLOBAL __cast24sFP

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__cast24uFP: FUSTART
          bcf       STATUS,N            ; Flag as unsignedQ
          RELOCTLMERGE jBeg
          FUEND
          bra       jBeg

__cast24sFP: FUSTART
          bsf       STATUS,N            ; Flag as signed

jBeg:
          ;; SetupStack doesn't effect STATUS bits
          SetupStack 3,LARGE_STK       ; 3 already allocated

          bcf       [fFlg],FLG_SIGN_ABW ; Flag as positive
          bnn       jNotNeg             ; Unsigned entry

          ;; Signed entry. If negative => negate and set flag
          braClr    [i1],7,jNotNeg

          ;; Negate
          Neg3      [iLo3],[i2],[i1]
          bsf       [fFlg],FLG_SIGN_ABW ; Flag as negative

jNotNeg:
          ;; Copy integer to fW
          movss     [i1],[fW0]
          movss     [i2],[fW1]
          movss     [iLo3],[fW2]
          clrf      [fW3]

          movlw     0x96
          movwf     [fWexp]
          clrf      [fWexpHi]

          ;; Sign is Flg.FLG_SIGN_ABW
          FUEND
          goto      __FpPack

          END
