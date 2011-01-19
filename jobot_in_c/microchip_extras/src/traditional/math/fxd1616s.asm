;	RCS Header $Id: fxd1616s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions
	
	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       16/16 Bit Signed Fixed Point Divide 16/16 -> 16.16

;       Input:  16 bit fixed point dividend in AARGB0, AARGB1
;               16 bit fixed point divisor in BARGB0, BARGB1

;       Use:    CALL    FXD1616S

;       Output: 16 bit fixed point quotient in AARGB0, AARGB1
;               16 bit fixed point remainder in REMB0, REMB1

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	28	149	85.17	clks

;       PM: 33            DM: 8

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN	FXD1616U

FXD1616S
		GLOBAL		FXD1616S

		CLRF		SIGN		; clear sign

        BTFSS   	__BARGB0,MSB
        GOTO    	DA1616S

        NEGF    	__BARGB1		; if BARG<0, negate and toggle sign
        COMF    	__BARGB0,F
        CLRF		WREG
		ADDWFC		__BARGB0,F
		COMF		SIGN,F

DA1616S
		BTFSS       __AARGB0,MSB
        GOTO        D1616S

        NEGF        __AARGB1		; if AARG<0, negate and toggle sign
        COMF        __AARGB0,F
        CLRF		WREG
		ADDWFC		__AARGB0,F
		COMF		SIGN,F

D1616S
		CALL		FXD1616U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D1616SX

D1616SOK
		BTFSS       SIGN,MSB
		RETLW		0x00
D1616SNEG
        NEGF        __AARGB1		; negate if result negative
        COMF        __AARGB0,F
        CLRF		WREG
		ADDWFC		__AARGB0,F

		NEGF        __REMB1
        COMF        __REMB0,F
        CLRF		WREG
		ADDWFC		__REMB0,F

        RETLW       0x00

D1616SX
		BTFSC		SIGN,MSB
		GOTO		D1616SNEG
		BSF		__FPFLAGS,NAN	; NAN exception if AARG=0x8000
								; with positive sign
		RETLW		0xFF

		END
