;	RCS Header $Id: fxd3216s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       32/16 Bit Signed Fixed Point Divide 32/16 -> 32.16

;       Input:  32 bit fixed point dividend in AARGB0, AARGB1, AARGB2
;               16 bit fixed point divisor in BARGB0, BARGB1

;       Use:    CALL    FXD3216S

;       Output: 32 bit fixed point quotient in AARGB0, AARGB1, AARGB2
;               16 bit fixed point remainder in REMB0, REMB1

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	84	421	376.20	clks

;       PM: 42             DM: 10

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD3216U

FXD3216S
		GLOBAL		FXD3216S

		CLRF		SIGN		; clear sign
		CLRF		WREG

        BTFSS       __BARGB0,MSB
        GOTO        DA3216S

        NEGF        __BARGB1		; if BARG<0, negate and toggle sign
        COMF        __BARGB0,F
        CLRF		WREG
        ADDWFC		__BARGB0,F
		COMF		SIGN,F

DA3216S
		BTFSS       __AARGB0,MSB
		GOTO        D3216S

		NEGF        __AARGB3		; if AARG<0, negate and toggle sign
		COMF        __AARGB2,F
		COMF        __AARGB1,F
		COMF        __AARGB0,F
		CLRF		WREG
		ADDWFC      __AARGB2,F
		ADDWFC      __AARGB1,F
		ADDWFC      __AARGB0,F
		COMF		SIGN,F

D3216S
		CALL		FXD3216U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D3216SX

D3216SOK
		BTFSS       SIGN,MSB
        RETLW       0x00
D3216SNEG
		NEGF        __AARGB3		; negate if result negative
        COMF        __AARGB2,F
        COMF        __AARGB1,F
        COMF        __AARGB0,F
        CLRF        WREG
        ADDWFC      __AARGB2,F
        ADDWFC      __AARGB1,F
        ADDWFC      __AARGB0,F

        NEGF        __REMB1
        COMF        __REMB0,F
        ADDWFC      __REMB0,F

        RETLW       0x00

D3216SX
		BTFSC		SIGN,MSB
		GOTO		D3216SNEG
		BSF			__FPFLAGS,NAN	; NAN exception if AARG=0x800000
		RETLW		0xFF		; with positive sign

		END
