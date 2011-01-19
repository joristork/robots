#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"

;**********************************************************************************************
;**********************************************************************************************
; INT3216:
;
; Description:
;
;         Float to two byte integer conversion. Designed for both signed
;         and unsigned use.  
;         Results for special cases:   
;         In                  Out
;         NaN                 0x7FFF
;         +Infinity           0x7FFF
;         -Infinity           0x8000
;         65535->32768        0xFFFF=>0x8000
;         <-32768             0
;         
; Input:
;         IEEE 754 format float fA
;
; Output:
;         INT3216U: Two byte signed integer in iA0,iA1
;
;            
;----------------------------------------------------------------------------------------------

          GLOBAL INT3216

          EXTERN __FpUnPackA

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


INT3216:

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
          movwf     fA1
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     fA0
          clrf      fA1
          bra       jExit

jZero:
          clrf      fA0
          clrf      fA1
          bra       jExit

jNotInf:
          braSet    fFlg,FLG_ZERO_A,jZero

          braFgeL   fAexp,0x8F,jMax         ;; exponent too large
          braFleL   fAexp,0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   fAexp,0x8E,jLoopExit    ;; shift complete
          ;;braFeqL   fAexp,0x8F,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          ShiftR2C  fA1, fA0
          incf      fAexp,F
          bra       jLoop

jLoopExit:

          braClr    fFlg,FLG_SIGN_A,jExit

          Neg2      fA1,fA0
          braClr    fA0,7,jZero    ;; too negative for signed & unsigned illegal

jExit:
          return

          END
