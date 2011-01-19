#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; INT3224:
;
; Description:
;
;         Float to three byte integer conversion. Designed for both signed
;         and unsigned use.  
;         Results for special cases:   
;         In                  Out
;         NaN                 0x7FFFFF
;         +Infinity           0x7FFFFF
;         -Infinity           0x800000
;         2^23-1->2^22        0xFFFFFF=>0x800000
;         <-2^22              0
;
; Input:
;         IEEE 754 format float fA

; Output:
;         INT3224U: Three byte signed integer in iA0,iA1,iA2
;
;            
;----------------------------------------------------------------------------------------------

          GLOBAL INT3224

          EXTERN __FpUnPackA

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


INT3224:

;------ Unpack the operand
          clrf      fFlg
          call      __FpUnPackA

          gotoSet   fFlg,FLG_NaN_AorB,jMaxPos
          braClr    fFlg,FLG_INF_A,jNotInf

jMax:
          braSet    fFlg,FLG_SIGN_A,jMaxNeg

jMaxPos:
          movlw     0x7F
          movwf     fA0
          movlw     0xFF
jWr123
          movwf     fA1
          movwf     fA2
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     fA0
          bra       jWrZ123

jZero:
          clrf      fA0
jWrZ123:
          movlw     0
          bra       jWr123

jNotInf:
          braSet    fFlg,FLG_ZERO_A,jZero

          braFgeL   fAexp,0x97,jMax         ;; exponent too large
          braFleL   fAexp,0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   fAexp,0x96,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          ShiftR3C  fA2, fA1, fA0
          incf      fAexp,F
          bra       jLoop

jLoopExit:

          braClr    fFlg,FLG_SIGN_A,jExit
          Neg3      fA2,fA1,fA0
          braClr    fA0,7,jZero             ;; too large

jExit:
          return

          END
