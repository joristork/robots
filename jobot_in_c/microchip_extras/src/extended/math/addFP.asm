#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;**********************************************************************************************
;**********************************************************************************************
; __addFP
; __subFP
;
; Description:
;
;         Addition     fA+fB for floats fA and fB
;         Subtraction  fA-fB for floats fA and fB
;
;               FSR1 --> [ 8] Unused slot
;                      > [ 7] Arg     fAexp
;                      > [ 6] Arg     fA0  
;                      > [ 5] Arg     fA1  
;                      > [ 4] Arg     fA2  
;                      > [ 3] Arg     fBexp
;                      > [ 2] Arg     fB0  
;                      > [ 1] Arg     fB1  
;               FSR2 --> [ 0] Arg     fB2  
;
; Input:
;         IEEE 754 format floats [fAexp]...[fA2], [fBexp]...[fB2].
;
; Output:
;
;         IEEE 754 format float fB = fA+fB or fB = fA-fB
;
;            FSR1 --> [4] Unused slot
;                   > [3] Return fBexp
;                   > [2] Return fB0  
;                   > [1] Return fB1  
;                   > [0] Return fB2  
;            
;----------------------------------------------------------------------------------------------

          GLOBAL __subFP, __addFP

          EXTERN __FpUnPackA, __FpUnPackB, __FpPack
          EXTERN __FpReturnW
          EXTERN __FpReturnNaN
          EXTERN __FpReturnPosZero,__FpReturnNegZero,__FpReturnSignZero     
          EXTERN __FpReturnPosInf, __FpReturnNegInf, __FpReturnSignInf

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************

__subFP   FUSTART
          bsf       STATUS,N            ; Flag as subtract
          RELOCTLMERGE jBeg
          FUEND 
          bra       jBeg

__addFP   FUSTART
          bcf       STATUS,N            ; Flag as add

jBeg:
          ;; SetupStack doesn't effect STATUS bits
          SetupStack 8,LARGE_STK     ; 8 already allocated

          btfsc     STATUS,N          
          btg       [fBexp],7           ; toggle sign for subtract

; After setup:
;
;  FSR1 --> [20] Unused slot
;         > [19] Previous FSR2H
;         > [18] Previous FSR2L
;         > [17] temp    fA3
;         > [16] temp    fT0
;         > [15] temp    fT1
;         > [14] temp    fWexpHi
;         > [13] temp    fEexp
;         > [12] temp    fW0
;         > [11] temp    fW1
;         > [10] temp    fW2
;         > [ 9] temp    fW3
;         > [ 8] temp    fFlg
;         > [ 7] Arg     fAexp
;         > [ 6] Arg     fA0  
;         > [ 5] Arg     fA1  
;         > [ 4] Arg     fA2  
;         > [ 3] Arg     fBexp
;         > [ 2] Arg     fB0  
;         > [ 1] Arg     fB1  
;  FSR2 --> [ 0] Arg     fB2  

;------ Unpack the operands
          clrf      [fFlg]
          call      __FpUnPackA
          call      __FpUnPackB

          gotoSet   [fFlg],FLG_NaN_AorB,__FpReturnNaN

          braClr    [fFlg],FLG_INF_A,jANotInf

;------ A is Infinite

          braClr    [fFlg],FLG_INF_B,jBANotInf

;------ A and B Infinite

          gotoSet   [fFlg],FLG_SIGN_ABW,__FpReturnNaN ; Inf - Inf => return NaN

jBANotInf:

;------ A infinite and B not infinite or both infinite with same sign
          gotoClr   [fFlg],FLG_SIGN_A,__FpReturnPosInf
          goto      __FpReturnNegInf

jANotInf:
          braClr    [fFlg],FLG_INF_B,jBNotInf

          ;; A finite, B infinite => return signed infinite
          gotoClr   [fFlg],FLG_SIGN_B,__FpReturnPosInf
          goto      __FpReturnNegInf

jBNotInf:

;-----------------------------------------------------------------------;
;       Both operands are finite
;-----------------------------------------------------------------------;

          braClr    [fFlg],FLG_ZERO_A,jNotBothZero
          braClr    [fFlg],FLG_ZERO_B,jNotBothZero

          ;; The cases of (+/-0)+(+/-0) must be trapped since IEEE requires:
          ;;        (+0)+(+0), (+0)+(-0) and (-0)+(+0) to = (+0)
          ;;        (-0)+(-0) = (-0)

          braSet    [fFlg],FLG_SIGN_ABW,jDifOrPosSign
          braClr    [fFlg],FLG_SIGN_A,jDifOrPosSign
          goto      __FpReturnNegZero   ;; Return (-0)

jDifOrPosSign:
          goto      __FpReturnPosZero   ;; Return (+0)

jNotBothZero:

          clrf      [fW3]
          clrf      [fWexpHi]

          ;; [fT0] = [fAexp] - [fBexp]
          movf      [fBexp],W
          subwf     [fAexp],W
          movwf     [fT0]

          bnc       jAltBexp

          ;; [fAexp] >= [fBexp]   => copy fA -> fW and fB -> fA
          movss     [fA2],[fW2]
          movss     [fA1],[fW1]
          movss     [fA0],[fW0]

          ;; save largest exponent
          movss     [fAexp],[fT1]

          movss     [fB2],[fA2]
          movss     [fB1],[fA1]
          movss     [fB0],[fA0]

          ;; Exchange A & B flags.
          swapf     [fFlg],F  
          bra       jAandWsetup

jAltBexp:
          ;; [fAexp] < [fBexp] copy fB -> fW
          movss     [fB2],[fW2]
          movss     [fB1],[fW1]
          movss     [fB0],[fW0]

          ;; save largest exponent
          movss     [fBexp],[fT1]

          ;; make [fT0] absolute value
          negf      [fT0]

jAandWsetup:


          ;; At this point Flg's for A refer to fA and Flg's for B refer to fW.
          ;; Since flag nibbles may have been swapped FLG_NaN_AorB and
          ;;        FLG_SIGN_ABW may be incorrect.


          ;; Must set FLG_SIGN_ABW and [fWexp] in case ReturnW is used.


          ;; Flg.FLG_SIGN_ABW = Flg.FLG_SIGN_B

          bcf       [fFlg],FLG_SIGN_ABW
          btfsc     [fFlg],FLG_SIGN_B
          bsf       [fFlg],FLG_SIGN_ABW

          movss     [fT1],[fWexp]           ;; largest exponent

          ;; if fA contains zero => return fW
          gotoSet   [fFlg],FLG_ZERO_A,__FpReturnW   


          ;; fW contains the value with the larger exponent
          ;; [fT0] contains the absolute difference between the exponents

          gotoFgeL  [fT0],26,__FpReturnW  ;; fA too small to contribute


          ;; Past here [fAexp] and [fWexp] are used as the upper byte of Significants.
          ;; The upper byte is required both for negation and because the 
          ;; results can exceed [fA0] or [fW0], i.e. 0.1xx + 0.1xx => 1.xxx
          clrf      [fWexp]
          clrf      [fAexp]

          ;; Switch from sign and absolute value representation to
          ;; signed representation

          braClr    [fFlg],FLG_SIGN_A,jANotNeg

          ;; Negate fA
          Neg4      [fA2],[fA1],[fA0],[fAexp]

jANotNeg:

          braClr    [fFlg],FLG_SIGN_B,jWNotNeg

          ;; Negate fW
          Neg4      [fW2],[fW1],[fW0],[fWexp]

jWNotNeg:


          ;; Have to add additional lower byte, [fA3], to capture 
          ;; roundoff & sticky bits
          clrf      [fA3]

          braFeqZ   [fT0],jShiftDone   ;; exponents already equal

          ;; Shift fA right with sign extension until exponents equal
jShiftLoop:

          rlcf      [fAexp],W             ;; Set C for sign extension
          ShiftR4C  [fA3],[fA2],[fA1],[fA0]

          decfsz    [fT0],F
          bra       jShiftLoop

jShiftDone:

          ;; Finally we can add.  
          ;; Note that [fA3] will not be changed by Add

          Add4      [fW2],[fW1],[fW0],[fWexp], [fA2],[fA1],[fA0],[fAexp]
          movss     [fA3],[fW3]

          ;; Switch back to Sign,absolute value representation
          bcf       [fFlg],FLG_SIGN_ABW
          braClr    [fWexp],7,jSignDone

          ;; Result is negative.  Negate and record sign
          Neg5      [fW3],[fW2],[fW1],[fW0],[fWexp]
          bsf       [fFlg],FLG_SIGN_ABW

jSignDone:

          ;; If results has form 1.xxx must shift to right before going
          ;; to FpPack

          braClr    [fWexp],0,jFinalSign

          bsf       STATUS,C
          ShiftR4C  [fW3],[fW2],[fW1],[fW0]
          incf      [fT1],F                         ;; Note: this can't exceed 255

jFinalSign:

          ;; load exponent for pack
          movss     [fT1],[fWexp]

          ;; Sign is FLG_SIGN_ABW
          FUEND
          goto __FpPack

          END

