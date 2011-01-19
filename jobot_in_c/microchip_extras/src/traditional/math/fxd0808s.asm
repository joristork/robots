;	RCS Header $Id: fxd0808s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions
	
	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       8/8 Bit Signed Fixed Point Divide 08/08 -> 08.08

;       Input:  8 bit fixed point dividend in AARGB0
;               8 bit fixed point divisor in BARGB0

;       Use:    CALL    FXD0808S

;       Output: 8 bit fixed point quotient in AARGB0
;               8 bit fixed point remainder in REMB0

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	21	52	38.80	clks

;       PM: 11+31+13+257 = 312        DM: 6

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD0808U

FXD0808S
		GLOBAL		FXD0808S

		CLRF		SIGN		; clear sign

        BTFSS       __BARGB0,MSB
        GOTO        DA0808S

        NEGF        __BARGB0		; if BARG<0, negate and toggle sign
		COMF		SIGN,F

DA0808S
	 	BTFSS       __AARGB0,MSB
        GOTO        D0808S

        NEGF        __AARGB0		; if AARG<0, negate and toggle sign
		COMF		SIGN,F

D0808S
		CALL		FXD0808U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D0808SX

D0808SOK
	    BTFSS		SIGN,MSB
        RETLW		0x00
D0808SNEG
        NEGF        __AARGB0		; negate if result negative
        NEGF        __REMB0

        RETLW       0x00

D0808SX
		BTFSC		SIGN,MSB
		GOTO		D0808SNEG
		BSF		__FPFLAGS,NAN	; NAN exception if AARG=0x80
						; with positive sign
		RETLW		0xFF

		END
