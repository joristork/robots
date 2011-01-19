#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; INT3232:
;
; Description:
;
;         Float to four byte integer conversion. Designed for both signed
;         and unsigned use.  
;         Results for special cases:   
;         In                  Out
;         NaN                 0x7FFFFFFF
;         +Infinity           0x7FFFFFFF
;         -Infinity           0x80000000
;         2^31-1->2^30        0xFFFFFFFF=>0x80000000
;         <-2^30              0
;
; Input:
;         IEEE 754 format float fA

; Output:
;         INT3232U: Four byte signed integer in iA0,iA1,iA2,iA3
;
;            
;----------------------------------------------------------------------------------------------

          GLOBAL INT3232

          EXTERN __FpUnPackA

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


INT3232:

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
          movwf     fA3
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
          clrf      fA3

          braFgeL   fAexp,0x9F,jMax         ;; exponent too large
          braFleL   fAexp,0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   fAexp,0x9E,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          ShiftR4C  fA3, fA2, fA1, fA0
          incf      fAexp,F
          bra       jLoop

jLoopExit:

          braClr    fFlg,FLG_SIGN_A,jExit

          Neg4      fA3,fA2,fA1,fA0
          braClr    fA0,7,jZero             ;; too large

jExit:
          return

          END
