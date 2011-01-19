#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"


;**********************************************************************************************
;**********************************************************************************************
; __FpUnPackA
; __FpUnPackB
;
; Description:
;
;         Unpack floats fA and fB
;
; Input:
;         IEEE 754 format float in fAexp...fA2.
;         Same for fBexp...fB2.
;         Requires flags to be zero on entry.    
;
; Output:
;
;         Unpacks exponent into fAexp and Significant into fA0,fA1,fA2.
;         Sets .0f => .1f, i.e. makes msb of Significant explict
;         Sets Zero, Infinite and NaN flags.
;
;
; On entry: IEEE 754 format                   Address increasing down
;       f2     D7  D6  D5  D4  D3  D2  D1  D0              |
;       f1     D15 D14 D13 D12 D11 D10 D9  D8              |
;       f0     E0  D22 D21 D20 D19 D18 D17 D16             |
;       fexp   Sn  E7  E6  E5  E4  E3  E2  E1              V
;            
;            
;            
; On exit    
;       f2     D7  D6  D5  D4  D3  D2  D1  D0 
;       f1     D15 D14 D13 D12 D11 D10 D9  D8
;       f0     1   D22 D21 D20 D19 D18 D17 D16
;       fexp   E7  E6  E5  E4  E3  E2  E1  E0
;            
;----------------------------------------------------------------------------------------------

          GLOBAL __FpUnPackA, __FpUnPackB

PROG    CODE

;**********************************************************************************************
;**********************************************************************************************

__FpUnPackA:

          rlcf      fA0,F
          rlcf      fAexp,F
          bnc       jUnpkA1

          ;; Negative
          bsf       fFlg,FLG_SIGN_A    ; Signal negative
          btg       fFlg,FLG_SIGN_ABW  ; Toggle combined sign

jUnpkA1:
          bnz       jUnpkANZ

          ;; Zero exponent => Zero value or subnormal
          bsf       fFlg,FLG_ZERO_A    ; Signal Zero

          ;; for safety force all bytes to zero since subnormals not supported
          clrf      fAexp
          clrf      fA0
          clrf      fA1
          clrf      fA2
          return

jUnpkANZ:
          ;; Test for NaN or infinity
          incf      fAexp,W
          bz        jUnpkA2            ; == 0xFF => either NaN or infinite

          ;; set msb of Significand to get 1.f
          bsf       STATUS,C
          rrcf      fA0,F
          return

jUnpkA2:
          ;; either NaN or infinity
          bcf       STATUS,C
          rrcf      fA0,F
          bnz       jUnpkNaN           ; NaN

          ;; infinity
          bsf       fFlg,FLG_INF_A      ; A: is infinity
          return

jUnpkNaN:
          ;; either A or B is a NaN
          bsf       fFlg,FLG_NaN_AorB   ; Signal NaN for A or B
          return

;**********************************************************************************************
;**********************************************************************************************
          
__FpUnPackB

          rlcf      fB0,F
          rlcf      fBexp,F
          bnc       jUnpkB1

          ;; Negative
          bsf       fFlg,FLG_SIGN_B       ; Signal negative
          btg       fFlg,FLG_SIGN_ABW     ; Toggle combined sign
jUnpkB1:
          bnz       jUnpkBNZ

          ;; Zero exponent => Zero value or subnormal
          bsf       fFlg,FLG_ZERO_B       ; Signal Zero

          ;; for safety force all bytes to zero since subnormals not supported
          clrf      fBexp
          clrf      fB0
          clrf      fB1
          clrf      fB2
          return

jUnpkBNZ:
          ;; Test for NaN or infinity
          incf      fBexp,W
          bz        jUnpkB2            ; == 0xFF => either NaN or infinite

          ;; set msb of Significand to get 1.f
          bsf       STATUS,C
          rrcf      fB0,F
          return

jUnpkB2:
          ;; either NaN or infinity
          bcf       STATUS,C
          rrcf      fB0,F
          bnz       jUnpkNaN           ; NaN

          ;; infinity
          bsf       fFlg,FLG_INF_B      ; B: is infinity
          return

          END

