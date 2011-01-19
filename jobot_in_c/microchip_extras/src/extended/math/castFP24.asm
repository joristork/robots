#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
; __castFP24
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
;         IEEE 754 format float 
;
;               FSR1 --> [4] Unused slot
;                      > [3] Arg   fBexp
;                      > [2] Arg   fB0  
;                      > [1] Arg   fB1  
;                      > [0] Arg   fB2  
;
; Output:
;         Three byte signed integer
;
;               FSR1  --> Unused slot
;                       > Return i1
;                       > Return i2
;                       > Return iLo3
;            
;----------------------------------------------------------------------------------------------

          GLOBAL __castFP24

          EXTERN __FpUnPackB

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__castFP24: FUSTART

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
          movwf     [i1]
          movlw     0xFF
jWr123
          movwf     [i2]
          movwf     [iLo3]
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     [i1]
          bra       jWrZ123

jZero:
          clrf      [i1]
jWrZ123:
          movlw     0
          bra       jWr123

jNotInf:
          braSet    [fFlg],FLG_ZERO_B,jZero

          braFgeL   [fBexp],0x97,jMax         ;; exponent too large
          braFleL   [fBexp],0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   [fBexp],0x96,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          ShiftR3C  [fB2], [fB1], [fB0]
          incf      [fBexp],F
          bra       jLoop

jLoopExit:

          braClr    [fFlg],FLG_SIGN_B,jDone

          Neg3      [fB2],[fB1],[fB0]
          braClr    [fB0],7,jZero             ;; too large

jDone:

jExit:
          ReleaseStack 3,SHORT_STK      ; leave 3 allocated
          FUEND
          return

          END
