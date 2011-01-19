;	RCS Header $Id: fxm0808u.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       8x8 Bit Unsigned Fixed Point Multiply 08 x 08 -> 16

;       Input:  8 bit unsigned fixed point multiplicand in AARGB0
;               8 bit unsigned fixed point multiplier in BARGB0

;       Use:    CALL    FXM0808U

;       Output: 16 bit unsigned fixed point product in AARGB0, AARGB1

;       Result: AARG  <--  AARG * BARG

;       Max Timing:     6 clks

;       Min Timing:     6 clks

;       PM: 5              DM: 3


PROG	CODE

FXM0808U
	GLOBAL	FXM0808U	

	MOVF	__BARGB0,W
	MULWF	__AARGB0
	MOVFF	PRODH,__AARGB0
	MOVFF	PRODL,__AARGB1
	RETLW	0x00

	END

