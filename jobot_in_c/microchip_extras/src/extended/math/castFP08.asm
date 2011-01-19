#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
; __castFP08
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
;         IEEE 754 format float 
;
;               FSR1 --> [4] Unused slot
;                      > [3] Arg   fBexp
;                      > [2] Arg   fB0  
;                      > [1] Arg   fB1  
;                      > [0] Arg   fB2  
;
; Output:
;         One byte signed integer
;
;               FSR1  --> Unused slot
;                       > Return iLo3
;            
;----------------------------------------------------------------------------------------------

          GLOBAL __castFP08

          EXTERN __FpUnPackB

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************


__castFP08: FUSTART

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
          movwf     [iLo3]
          bra       jExit

jMaxNeg:
          movlw     0x80
          movwf     [iLo3]
          bra       jExit

jZero:
          clrf      [iLo3]
          bra       jExit

jNotInf:
          braSet    [fFlg],FLG_ZERO_B,jZero

          braFgeL   [fBexp],0x87,jMax         ;; exponent too large
          braFleL   [fBexp],0x7E,jZero        ;; exponent too small

jLoop:
          braFeqL   [fBexp],0x86,jLoopExit    ;; shift complete
          
          bcf       STATUS,C            
          rrcf      [fB0],F
          incf      [fBexp],F
          bra       jLoop

jLoopExit:

          braClr    [fFlg],FLG_SIGN_B,jDone

          negf      [fB0]
          braClr    [fB0],7,jZero             ;; too large

jDone:
          movss     [fB0],[iLo3]
jExit:
          ReleaseStack 1,SHORT_STK      ; leave 1 allocated
          FUEND
          return

          END
