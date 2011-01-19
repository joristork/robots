;	RCS Header $Id: fxd3224s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       32/24 Bit Signed Fixed Point Divide 32/24 -> 32.24

;       Input:  32 bit fixed point dividend in AARGB0, AARGB1, AARGB2
;               24 bit fixed point divisor in BARGB0, BARGB1

;       Use:    CALL    FXD3224S

;       Output: 32 bit fixed point quotient in AARGB0, AARGB1, AARGB2
;               24 bit fixed point remainder in REMB0, REMB1

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	49	421	324.05	clks

;       PM: 46             DM: 12

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD3224U

FXD3224S
		GLOBAL		FXD3224S

		CLRF		SIGN		; clear sign
		CLRF		WREG

        BTFSS       __BARGB0,MSB
        GOTO        DA3224S

        NEGF        __BARGB2		; if BARG<0, negate and toggle sign
        COMF        __BARGB1,F
        COMF        __BARGB0,F
		ADDWFC		__BARGB1,F
		ADDWFC		__BARGB0,F
		COMF		SIGN,F

DA3224S
		BTFSS       __AARGB0,MSB
		GOTO        D3224S

		NEGF        __AARGB3		; if AARG<0, negate and toggle sign
		COMF        __AARGB2,F
		COMF        __AARGB1,F
		COMF        __AARGB0,F
		ADDWFC      __AARGB2,F
		ADDWFC      __AARGB1,F
		ADDWFC      __AARGB0,F
		COMF		SIGN,F

D3224S
		CALL		FXD3224U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D3224SX

D3224SOK
		BTFSS		SIGN,MSB
        RETLW		0x00
D3224SNEG
		NEGF        __AARGB3		; negate if result negative
		COMF        __AARGB2,F
		COMF        __AARGB1,F
		COMF        __AARGB0,F
		CLRF        WREG
		ADDWFC      __AARGB2,F
		ADDWFC      __AARGB1,F
		ADDWFC      __AARGB0,F

		NEGF        __REMB2
		COMF        __REMB1,F
		COMF        __REMB0,F
		ADDWFC      __REMB1,F
		ADDWFC      __REMB0,F

		RETLW       0x00

D3224SX
		BTFSC		SIGN,MSB
		GOTO		D3224SNEG
		BSF			__FPFLAGS,NAN	; NAN exception if AARG=0x800000
		RETLW		0xFF		; with positive sign

		END
