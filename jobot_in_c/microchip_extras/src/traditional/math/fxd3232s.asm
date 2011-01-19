;	RCS Header $Id: fxd3232s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       32/32 Bit Signed Fixed Point Divide 32/32 -> 32.32

;       Input:  32 bit fixed point dividend in AARGB0, AARGB1, AARGB2
;               32 bit fixed point divisor in BARGB0, BARGB1

;       Use:    CALL    FXD3232S

;       Output: 32 bit fixed point quotient in AARGB0, AARGB1, AARGB2
;               32 bit fixed point remainder in REMB0, REMB1

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	32	410	127.22	clks

;       PM: 50             DM: 12

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD3232U

FXD3232S
		GLOBAL		FXD3232S

		CLRF		SIGN		; clear sign
		CLRF		WREG

		BTFSS		__BARGB0,MSB
		GOTO 		DA3232S

		NEGF 		__BARGB3		; if BARG<0, negate and toggle sign
		COMF 		__BARGB2,F
		COMF 		__BARGB1,F
		COMF 		__BARGB0,F
		ADDWFC		__BARGB2,F
		ADDWFC		__BARGB1,F
		ADDWFC		__BARGB0,F
		COMF		SIGN,F

DA3232S
		BTFSS       __AARGB0,MSB
		GOTO        D3232S

		NEGF        __AARGB3		; if AARG<0, negate and toggle sign
		COMF        __AARGB2,F
		COMF        __AARGB1,F
		COMF        __AARGB0,F
		ADDWFC      __AARGB2,F
		ADDWFC      __AARGB1,F
		ADDWFC      __AARGB0,F
		COMF		SIGN,F

D3232S
		CALL		FXD3232U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D3232SX

D3232SOK
		BTFSS       SIGN,MSB
        RETLW       0x00
D3232SNEG
		NEGF  		__AARGB3		; negate if result negative
		COMF  		__AARGB2,F
		COMF  		__AARGB1,F
		COMF  		__AARGB0,F
		CLRF  		WREG
		ADDWFC		__AARGB2,F
		ADDWFC		__AARGB1,F
		ADDWFC		__AARGB0,F

		NEGF  		__REMB3
		COMF  		__REMB2,F
		COMF  		__REMB1,F
		COMF  		__REMB0,F
		ADDWFC		__REMB2,F
		ADDWFC		__REMB1,F
		ADDWFC		__REMB0,F

		RETLW 		0x00

D3232SX
		BTFSC		SIGN,MSB
		GOTO		D3232SNEG
		BSF			__FPFLAGS,NAN	; NAN exception if AARG=0x800000
		RETLW		0xFF		; with positive sign

		END
