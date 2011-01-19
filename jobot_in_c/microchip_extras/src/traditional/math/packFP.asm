#include <P18CXXX.INC>           ; general Golden Gate definitions
#include "fpmacros.inc"


          GLOBAL __FpPack

          GLOBAL __FpReturnW
          GLOBAL __FpReturnNaN
          GLOBAL __FpReturnPosZero,__FpReturnNegZero,__FpReturnSignZero     
          GLOBAL __FpReturnPosInf, __FpReturnNegInf, __FpReturnSignInf
          GLOBAL __FpExit


PROG    CODE

;***********************************************************************
;************ C O M M O M    E N T R I E S *****************************
;***********************************************************************

__FpReturnNaN:     
          ;; return NaN
          movlw     0x7F
          movwf     fAexp
          movlw     0xFF
          movwf     fA0
          movwf     fA1
          movwf     fA2
__FpExit:
          return

__FpReturnSignInf:     
          ;; return infinite with sign in FLG_SIGN_ABW
          movlw     0x7F
          btfsc     fFlg,FLG_SIGN_ABW
__FpReturnNegInf:            ;; return negative infinite
          movlw     0xFF
          movwf     fAexp
          bra       jReturnPosInf0

__FpReturnPosInf:
          ;; return positive infinite
          movlw     0x7F
          movwf     fAexp

jReturnPosInf0:
          movlw     0x80
          movwf     fA0

jZero1and2: 
          clrf      fA1
          clrf      fA2
          bra       __FpExit

__FpReturnSignZero:     
          ;; return Zero with sign in FLG_SIGN_ABW
          braClr    fFlg,FLG_SIGN_ABW,__FpReturnPosZero       

__FpReturnNegZero:       
          movlw     0x80
          movwf     fAexp
          clrf      fA0
          bra       jZero1and2 

__FpReturnPosZero:       
          ;; return zero
          clrf      fAexp
          clrf      fA0
          bra       jZero1and2 

;***********************************************************************
;***********************************************************************
;-----------------------------------------------------------------------;
;
; __Fppack:
;
; Description:
;
;       Rounds and packs a float
;
; Input:
;       Assumes significant, round bit and sticky bits in fW0 -> fW3. 
;       Biased exponent in fWexp,fWexpHi.  
;       Exponent must be adjusted for significant left-justified in fW0.
;       Sign in FLAG_SIGN_ABW.  
;
; Output:
;
;       Packed, signed float in fAexp...fA2
;
; Notes:
;
;
;----------------------------------------------------------------------------------------------

__FpPack

          movf      fW0,W
          iorwf     fW1,W
          iorwf     fW2,W
          iorwf     fW3,W
          bz        __FpReturnPosZero             ;; Pure zero
          braSet    fWexpHi,7,__FpReturnSignZero  ;; Subnormal. Treat as signed zero
          
jPkLoop:
          braSet    fW0,7,jPkShiftDone            ;; Significant left-justified

          ;; if( fWexp == 0 && fWexpHi == 0 )goto __FpReturnZero;

          movf      fWexp,W
          iorwf     fWexpHi,W
          bz        __FpReturnSignZero            ;; Subnormal

          bcf       STATUS,C
          ShiftL4C  fW3,fW2,fW1,fW0
          Dec2      fWexp,fWexpHi                 ;; corrected exponent
          bra       jPkLoop

jPkShiftDone:

          ;; if( fWexpHi ) goto __FpReturnSignInf
          movf      fWexpHi,W
          bnz       __FpReturnSignInf

          ;; if( fWexp == 0xFF ) goto __FpReturnSignInf
          incf      fWexp,W
          bz        __FpReturnSignInf

          ;;------ Round up if (round && (sticky || (significand & 1)))

          ;; if( !(fW3 & 0x80)) goto jPkRoundDone; 

          braClr    fW3,7,jPkRoundDone  ;; No rounding required ...

          ;; if(  fW3 & 0x7F)   goto jPkRoundIt;   

          movlw     0x7F
          andwf     fW3,W
          bnz       jPkRoundIt          ;; Rounding required ...

          ;; if( !(fW2 & 0x01)) goto jPkRoundDone;

          braClr    fW2,0,jPkRoundDone  ;; No rounding required ...

jPkRoundIt:

          ;; ---------- Do Rounding ----------------

          Inc3      fW2,fW1,fW0         ;; Significand++
          bnc       jPkRoundDone        ;; No need to adjust exponent

          ;; Renormalize
          bsf       STATUS,C
          ShiftR3C  fW2,fW1,fW0
          incf      fWexp,F

          ;; if( fWexp == 0xFF ) goto __FpReturnSignInf
          incf      fWexp,W
          bz        __FpReturnSignInf

jPkRoundDone:

__FpReturnW:
          movf      fWexp,W
          bz        __FpReturnSignZero            ;; Subnormal

          movff     fW2,fA2
          movff     fW1,fA1

          rlcf      fW0,W
          movwf     fA0

          rrcf      fWexp,W
          movwf     fAexp
          rrcf      fA0,F

          bcf       fAexp,7
          btfsc     fFlg,FLG_SIGN_ABW
          bsf       fAexp,7
          goto      __FpExit


          END


