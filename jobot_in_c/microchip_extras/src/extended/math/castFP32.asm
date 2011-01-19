#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
; __castFP32
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
;         IEEE 754 format float 
;
;               FSR1 --> [4] Unused slot
;                      > [3] Arg   fBexp
;                      > [2] Arg   fB0  
;                      > [1] Arg   fB1  
;                      > [0] Arg   fB2  
;
; Output:
;         Four byte signed integer
;
;               FSR1 --> [4] Unused slot
;                      > [3] Return iHi0  
;                      > [2] Return i1
;                      > [1] Return i2
;                      > [0] Return iLo3
;                              
;----------------------------------------------------------------------------------------------

          GLOBAL __castFP32

          EXTERN __FpUnPackB

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__castFP32: FUSTART

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
          movwf     [iHi0]
          movlw     0xFF
jWr123
          movwf     [i1]
          movwf     [i2]
          movwf     [iLo3]
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     [iHi0]
          bra       jWrZ123

jZero:
          clrf      [iHi0]
jWrZ123:
          movlw     0
          bra       jWr123

jNotInf:
          braSet    [fFlg],FLG_ZERO_B,jZero
          clrf      [fTemp]

          braFgeL   [fBexp],0x9F,jMax         ;; exponent too large
          braFleL   [fBexp],0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   [fBexp],0x9E,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          ShiftR4C  [fTemp], [fB2], [fB1], [fB0]
          incf      [fBexp],F
          bra       jLoop

jLoopExit:

          braClr    [fFlg],FLG_SIGN_B,jDone

          Neg4      [fTemp],[fB2],[fB1],[fB0]
          braClr    [fB0],7,jZero             ;; too large

jDone:
          ;; Copy to correct locations
          movss     [fB0],[iHi0]
          movss     [fB1],[i1]
          movss     [fB2],[i2]
          movss     [fTemp],[iLo3]
jExit:
          ReleaseStack 4,SHORT_STK      ; leave 4 allocated
          FUEND
          return

          END
