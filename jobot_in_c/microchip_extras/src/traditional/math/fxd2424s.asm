;	RCS Header $Id: fxd2424s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       24/24 Bit Signed Fixed Point Divide 24/24 -> 24.24

;       Input:  24 bit fixed point dividend in AARGB0, AARGB1, AARGB2
;               24 bit fixed point divisor in BARGB0, BARGB1

;       Use:    CALL    FXD2424S

;       Output: 24 bit fixed point quotient in AARGB0, AARGB1, AARGB2
;               24 bit fixed point remainder in REMB0, REMB1

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	30	316	110.05	clks

;       PM: 42             DM: 11

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD2424U

FXD2424S
		GLOBAL		FXD2424S

		CLRF		SIGN		; clear sign

        BTFSS       __BARGB0,MSB
        GOTO        DA2424S

        NEGF        __BARGB2		; if BARG<0, negate and toggle sign
        COMF        __BARGB1,F
        COMF        __BARGB0,F
        CLRF        WREG
        ADDWFC      __BARGB1,F
        ADDWFC      __BARGB0,F
		COMF		SIGN,F

DA2424S
		BTFSS       __AARGB0,MSB	; if AARG<0, negate and toggle sign
		GOTO        D2424S

		NEGF        __AARGB2
		COMF        __AARGB1,F
		COMF        __AARGB0,F
		CLRF		WREG
		ADDWFC      __AARGB1,F
		ADDWFC      __AARGB0,F
		COMF		SIGN,F

D2424S
		CALL		FXD2424U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D2424SX

D2424SOK
		BTFSS       SIGN,MSB
        RETLW       0x00
D2424SNEG
        NEGF        __AARGB2		; negate if result negative
        COMF        __AARGB1,F
        COMF        __AARGB0,F
        CLRF        WREG
        ADDWFC      __AARGB1,F
        ADDWFC      __AARGB0,F

        NEGF        __REMB2
        COMF        __REMB1,F
        COMF        __REMB0,F
        ADDWFC      __REMB1,F
        ADDWFC      __REMB0,F

        RETLW       0x00

D2424SX
		BTFSC		SIGN,MSB
		GOTO		D2424SNEG
		BSF			__FPFLAGS,NAN	; NAN exception if AARG=0x800000
		RETLW		0xFF		; with positive sign

		END
