;	RCS Header $Id: fxd2416s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       24/16 Bit Signed Fixed Point Divide 24/16 -> 24.16

;       Input:  24 bit fixed point dividend in AARGB0, AARGB1, AARGB2
;               16 bit fixed point divisor in BARGB0, BARGB1

;       Use:    CALL    FXD2416S

;       Output: 24 bit fixed point quotient in AARGB0, AARGB1, AARGB2
;               16 bit fixed point remainder in REMB0, REMB1

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	54	310	282.08	clks

;       PM: 38             DM: 9

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD2416U

FXD2416S
		GLOBAL		FXD2416S

		CLRF		SIGN		; clear sign

        BTFSS       __BARGB0,MSB
        GOTO        DA2416S

        NEGF        __BARGB1		; if BARG<0, negate and toggle sign
        COMF        __BARGB0,F
        CLRF		WREG
        ADDWFC		__BARGB0,F
		COMF		SIGN,F

DA2416S
		BTFSS       __AARGB0,MSB
        GOTO        D2416S

        NEGF        __AARGB2		; if AARG<0, negate and toggle sign
        COMF        __AARGB1,F
        COMF        __AARGB0,F
        CLRF		WREG
        ADDWFC      __AARGB1,F
        ADDWFC      __AARGB0,F
		COMF		SIGN,F

D2416S
		CALL		FXD2416U

		BTFSC		__AARGB0,MSB	; test for exception
		GOTO		D2416SX

D2416SOK
		BTFSS       SIGN,MSB
        RETLW       0x00
D2416SNEG
        NEGF        __AARGB2		; negate if result negative
        COMF        __AARGB1,F
        COMF        __AARGB0,F
        CLRF        WREG
        ADDWFC      __AARGB1,F
        ADDWFC      __AARGB0,F

        NEGF        __REMB1
        COMF        __REMB0,F
        ADDWFC      __REMB0,F

        RETLW       0x00

D2416SX
		BTFSC		SIGN,MSB
		GOTO		D2416SNEG
		BSF			__FPFLAGS,NAN	; NAN exception if AARG=0x800000
		RETLW		0xFF		; with positive sign

		END
