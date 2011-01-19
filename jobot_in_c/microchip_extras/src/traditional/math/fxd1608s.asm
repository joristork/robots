;	RCS Header $Id: fxd1608s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations
	
;**********************************************************************************************
;**********************************************************************************************

;       16/8 Bit Signed Fixed Point Divide 16/08 -> 16.08

;       Input:  16 bit fixed point dividend in AARGB0, AARGB1
;               8 bit fixed point divisor in BARGB0

;       Use:    CALL    FXD1608S

;       Output: 16 bit fixed point quotient in AARGB0, AARGB1
;               8 bit fixed point remainder in REMB0

;       Result: AARG, REM  <--  AARG / BARG


;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	21	80	70.14	clks

;       PM: 29             DM: 6

PROG	CODE

		EXTERN		FXD1608U

FXD1608S
		GLOBAL		FXD1608S

		CLRF		SIGN		; clear sign

        BTFSS       __BARGB0,MSB
        GOTO        DA1608S

        NEGF        __BARGB0		; if BARG<0, negate and toggle sign
		COMF		SIGN,F

DA1608S
		BTFSS       __AARGB0,MSB
        GOTO        C1608S

        NEGF        __AARGB1		; if AARG<0, negate and toggle sign
        COMF        __AARGB0,F
		CLRF		WREG
        ADDWFC		__AARGB0,F
		COMF		SIGN,F

C1608S
		CALL		FXD1608U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D1608SX

D1608SOK
		BTFSS       SIGN,MSB
		RETLW		0x00

D1608SNEG
		NEGF        __AARGB1		; negate if result negative
        COMF        __AARGB0,F
		CLRF		WREG
        ADDWFC		__AARGB0,F

        NEGF        __REMB0

        RETLW       0x00

D1608SX
		BTFSC		SIGN,MSB
		GOTO		D1608SNEG
		BSF			__FPFLAGS,NAN	; NAN exception if AARG=0x8000
						; with positive sign
		RETLW		0xFF

		END
