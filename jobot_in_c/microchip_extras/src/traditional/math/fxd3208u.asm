;	RCS Header $Id: fxd3208u.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

	include <TEMPARG.INC>		; TEMPARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       32/08 Bit Unsigned Fixed Point Divide 32/08 -> 32.08

;       Input:  32 bit unsigned fixed point dividend in AARGB0, AARGB1, AARGB2, AARGB3
;               8 bit unsigned fixed point divisor in BARGB0

;       Use:    CALL    FXD3208U

;       Output: 32 bit unsigned fixed point quotient in AARGB0, AARGB1, AARGB2, AARGB3
;               8 bit unsigned fixed point remainder in REMB0

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	44	188	172.77	clks

;       PM: 19               DM: 10

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD1608U

FXD3208U
		GLOBAL		FXD3208U

		MOVFF		__AARGB0,__TEMPB0
		MOVFF		__AARGB1,__TEMPB1
		MOVFF		__AARGB2,__TEMPB2
		MOVFF		__AARGB3,__TEMPB3

		CALL		FXD1608U

		MOVFF		__AARGB0,__TEMPB0
		MOVFF		__AARGB1,__TEMPB1

		MOVFF		__TEMPB2,__AARGB1
		MOVFF		__REMB0,__AARGB0

		CALL		FXD1608U

		MOVFF		__AARGB1,__TEMPB2
		MOVFF		__TEMPB3,__AARGB1
		MOVFF		__REMB0,__AARGB0

		CALL		FXD1608U

		MOVFF		__AARGB1,__AARGB3
		MOVFF		__TEMPB0,__AARGB0
		MOVFF		__TEMPB1,__AARGB1
		MOVFF		__TEMPB2,__AARGB2

		RETLW		0x00

		END
