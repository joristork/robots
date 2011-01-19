#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
;**********************************************************************************************
; __cast08uFP:
; __cast08sFP:
;
; Description:
;
;         One byte integer to Float conversion
;
; Input:
;         __cast08uFP: One byte unsigned integer
;         __cast08sFP: One byte signed integer
;
;            FSR1  --> [1] Unused slot
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

          GLOBAL __cast08uFP
          GLOBAL __cast08sFP

          EXTERN __FpPack

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__cast08uFP: FUSTART
          bcf       STATUS,N            ; Flag as unsigned
          RELOCTLMERGE jBeg
          FUEND
          bra       jBeg

__cast08sFP:
          FUSTART
          bsf       STATUS,N            ; Flag as signed

jBeg:
          ;; SetupStack doesn't effect STATUS bits
          SetupStack 1,LARGE_STK        ; 1 already allocated
          bcf       [fFlg],FLG_SIGN_ABW ; Flag as positive
          bnn       jNotNeg             ; Unsigned entry

          ;; Signed entry. If negative => negate and set flag
          braClr    [iLo3],7,jNotNeg

          ;; Negate
          negf      [iLo3]
          bsf       [fFlg],FLG_SIGN_ABW ; Flag as negative

jNotNeg:
          ;; Copy integer to fW
          movss     [iLo3],[fW0]
          clrf      [fW1]
          clrf      [fW2]
          clrf      [fW3]

          movlw     0x86
          movwf     [fWexp]
          clrf      [fWexpHi]

          ;; Sign is Flg.FLG_SIGN_ABW
          FUEND
          goto      __FpPack

          END
