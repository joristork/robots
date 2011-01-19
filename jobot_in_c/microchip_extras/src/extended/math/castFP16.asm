#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
; __castFP16
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
;         IEEE 754 format float 
;
;               FSR1 --> [4] Unused slot
;                      > [3] Arg   fBexp
;                      > [2] Arg   fB0  
;                      > [1] Arg   fB1  
;                      > [0] Arg   fB2  
;
; Output:
;         Two byte signed integer
;
;               FSR1  --> Unused slot
;                       > Return i2
;                       > Return iLo3
;            
;----------------------------------------------------------------------------------------------

          GLOBAL __castFP16

          EXTERN __FpUnPackB

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__castFP16: FUSTART

          SetupStack 4,SHORT_STK        ; 4 already allocated

;------ Unpack the operand
          clrf      [fFlg]
          call      __FpUnPackB

          gotoSet   [fFlg],FLG_NaN_AorB,jMaxPos
          braClr    [fFlg],FLG_INF_B,jNotInf

jMax:
          braSet    [fFlg],FLG_SIGN_B,jMaxNeg

jMaxPos:
          movlw     0x7F
          movwf     [i2]
          movlw     0xFF
          movwf     [iLo3]
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     [i2]
          clrf      [iLo3]
          bra       jExit

jZero:
          clrf      [i2]
          clrf      [iLo3]
          bra       jExit

jNotInf:
          braSet    [fFlg],FLG_ZERO_B,jZero

          braFgeL   [fBexp],0x8F,jMax         ;; exponent too large
          braFleL   [fBexp],0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   [fBexp],0x8E,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          ShiftR2C  [fB1], [fB0]
          incf      [fBexp],F
          bra       jLoop

jLoopExit:

          braClr    [fFlg],FLG_SIGN_B,jDone

          Neg2      [fB1],[fB0]
          braClr    [fB0],7,jZero             ;; too large

jDone:
          movss     [fB1],[iLo3]
          movss     [fB0],[i2]
jExit:
          ReleaseStack 2,SHORT_STK      ; leave 2 allocated
          FUEND
          return

          END
