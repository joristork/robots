#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "EnhMacFP.inc"

;***********************************************************************
;***********************************************************************
;-----------------------------------------------------------------------;
;
; __FpCompare
;
; Description:
;
;       Compares fA to fB
;
; Input:
;         IEEE 754 format float in [fAexp]...[fA2].
;         Same for [fBexp]...[fB2].
;
; Output:
;         WREG bits set as follows:
;         FLG_NaN_AorB        => NaN
;         FLG_ZERO_A          => A == B
;         FLG_SIGN_A          => A < B
;
; Notes:
;         Flags must be checked in above order!
;
;         If TEST_FOR_SUBNORMALS is defined differences will be tested
;         for subnormals and treated as equal.
;
;----------------------------------------------------------------------------------------------
#define TEST_FOR_SUBNORMALS

          GLOBAL __FpCompare
          EXTERN __FpUnPackA, __FpUnPackB

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************

__FpCompare FUSTART

          SetupStack 8,MEDIUM_STK       ; 6 already allocated

;------ Unpack the operand
          clrf      [fFlg]
          call      __FpUnPackA
          call      __FpUnPackB

          btfsc     [fFlg],FLG_NaN_AorB
          bra       jExit

          braClr    [fFlg],FLG_SIGN_ABW,jSignsEQ

          ;; Signs differ thus:
          ;; If both are (+/-0) then they are equal by IEEE
          ;;        if A >= 0 => A > B else A < B

          braClr    [fFlg],FLG_ZERO_A,jBothNotZero
          braClr    [fFlg],FLG_ZERO_B,jBothNotZero

          ;; Both are zero
          bsf       [fFlg],FLG_ZERO_A
          bra       jExit

jBothNotZero:
          bcf       [fFlg],FLG_ZERO_A
          bra       jExit

#ifdef TEST_FOR_SUBNORMALS

jSignsEQ:
          movf      [fBexp],W
          subwf     [fAexp],W
          bnz       jNE

          movf      [fB0],W
          subwf     [fA0],W
          bnz       jNE

          movf      [fB1],W
          subwf     [fA1],W
          bnz       jNE

          movf      [fB2],W
          subwf     [fA2],W
          bnz       jNE

jEQ:
          ;; fA == fB
          bsf       [fFlg],FLG_ZERO_A
          bra       jExit

jNE:
          ;; Preserve order. Set ABW flag if fA >= fB.
          ;; Note that ABW flag must be 0 to be here

          braClr    STATUS,C,jExch
          bsf       [fFlg],FLG_SIGN_ABW
          bra       jNoExch

jExch:
          ;; exchange so that |fA| >= |fB|
          Exchange  [fAexp],[fBexp]
          Exchange  [fA0],  [fB0]
          Exchange  [fA1],  [fB1]
          Exchange  [fA2],  [fB2]

jNoExch:
          movf      [fBexp],W
          subwf     [fAexp],W
          bz        jExpEQ

          ;; Exp not equal. Do they differ by more than a subnormal amount?
          addlw     0xFF
          bz        jShift              ; fAexp = fBexp + 1

          ;; Exponents differ by more than 1 thus fA != fB

jFinal:
          bcf       [fFlg],FLG_ZERO_A
          braSet    [fFlg],FLG_SIGN_A,jAisNeg

          ;; fA & fB are not negative

          btfss     [fFlg],FLG_SIGN_ABW
          bsf       [fFlg],FLG_SIGN_A   ;; fA < fB
          bra       jExit

jAisNeg: 
          ;; fA & fB are negative

          btfss     [fFlg],FLG_SIGN_ABW
          bcf       [fFlg],FLG_SIGN_A   ;; fA > fB
          bra       jExit

jShift:
          ;; fAexp = fBexp + 1
          ;; Adjust to have same exponent
          ;; Increment fAexp and shift significant to right
          incf      [fBexp],F
          bcf       STATUS,C
          ShiftR3C  [fB2],[fB1],[fB0]

jExpEQ:
          ;; exponents equal and |fA| > |fB|
          ;; Determine they differ by more than a subnormal amount
          Sub3      [fA2],[fA1],[fA0],[fB2],[fB1],[fB0]

          ;; fA now contains the positive difference. Note that fA != 0.
          ;; Normalize while watching exponent

jRotate:
          braSet    [fA0],7,jFinal    ;; normalized
    
          bcf       STATUS,C
          ShiftL3C  [fA2],[fA1],[fA0]
          decfsz    [fAexp],F
          bra       jRotate

         ;; Difference is at subnormal level => consider equal
          bra       jEQ;
#else

jSignsEQ:
          movf      [fBexp],W
          subwf     [fAexp],W
          bnz       jAneB

          movf      [fB0],W
          subwf     [fA0],W
          bnz       jAneB

          movf      [fB1],W
          subwf     [fA1],W
          bnz       jAneB

          movf      [fB2],W
          subwf     [fA2],W
          bnz       jAneB

          ;; fA == fB
          bsf       [fFlg],FLG_ZERO_A
          bra       jExit

jAneB:
          bcf       [fFlg],FLG_ZERO_A
          braSet    [fFlg],FLG_SIGN_A,jAisNeg

          ;; fA & fB are not negative

          btfss     STATUS,C
          bsf       [fFlg],FLG_SIGN_A   ;; fA < fB
          bra       jExit

jAisNeg: 
          ;; fA & fB are negative

          btfss     STATUS,C
          bcf       [fFlg],FLG_SIGN_A   ;; fA > fB

#endif

jExit:
          ;; Load flags to WREG
          movf      [fFlg],W

          ReleaseStack 0,MEDIUM_STK      ; leave 0 allocated

          FUEND
          return

          END
