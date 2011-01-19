;	RCS Header $Id: fxd2408u.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

	include <TEMPARG.INC>		; TEMPARG declarations
	
;**********************************************************************************************
;**********************************************************************************************

;       24/08 Bit Unsigned Fixed Point Divide 24/08 -> 24.08

;       Input:  24 bit unsigned fixed point dividend in AARGB0, AARGB1, AARGB2
;               8 bit unsigned fixed point divisor in BARGB0

;       Use:    CALL    FXD2408U

;       Output: 24 bit unsigned fixed point quotient in AARGB0, AARGB1, AARGB2
;               8 bit unsigned fixed point remainder in REMB0

;       Result: AARG, REM  <--  AARG / BARG

;	Testing from 100000 trials:

;		min	max	mean
;	Timing:	30	126	116.12	clks

;       PM: 13               DM: 8

;----------------------------------------------------------------------------------------------

PROG	CODE

		EXTERN		FXD1608U

FXD2408U
		GLOBAL		FXD2408U

		MOVFF		__AARGB0,__TEMPB0
		MOVFF		__AARGB1,__TEMPB1
		MOVFF		__AARGB2,__TEMPB2

		CALL		FXD1608U

		MOVFF		__AARGB0,__TEMPB0
		MOVFF		__AARGB1,__TEMPB1

		MOVFF		__TEMPB2,__AARGB1
		MOVFF		__REMB0,__AARGB0

		CALL		FXD1608U

		MOVFF		__AARGB1,__AARGB2
		MOVFF		__TEMPB1,__AARGB1
		MOVFF		__TEMPB0,__AARGB0

		RETLW		0x00

		END
