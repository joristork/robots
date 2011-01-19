#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; INT3208:
;
; Description:
;
;         Float to one byte integer conversion. Designed for both signed
;         and unsigned use.  
;         Results for special cases:   
;         In                  Out
;         NaN                 0x7F
;         +Infinity           0x7F
;         -Infinity           0x80
;         255->128            0xFF=>0x80
;         <-128               0
;
; Input:
;         IEEE 754 format float fA

; Output:
;         INT3208U: One byte signed integer in iA0
;
;            
;----------------------------------------------------------------------------------------------

          GLOBAL INT3208

          EXTERN __FpUnPackA

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


INT3208:

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
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     fA0
          bra       jExit

jZero:
          clrf      fA0
          bra       jExit

jNotInf:
          braSet    fFlg,FLG_ZERO_A,jZero

          braFgeL   fAexp,0x87,jMax         ;; exponent too large
          braFleL   fAexp,0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   fAexp,0x86,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          rrcf      fA0,F
          incf      fAexp,F
          bra       jLoop

jLoopExit:

          braClr    fFlg,FLG_SIGN_A,jExit
          negf      fA0
          braClr    fA0,7,jZero             ;; too large

jExit:
          return

          END
