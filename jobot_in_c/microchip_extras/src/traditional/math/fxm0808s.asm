;	RCS Header $Id: fxm0808s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       8x8 Bit Signed Fixed Point Multiply 08 x 08 -> 16

;       Input:  8 bit signed fixed point multiplicand in AARGB0
;               8 bit signed fixed point multiplier in BARGB0

;       Use:    CALL    FXM0808S

;       Output: 16 bit signed fixed point product in AARGB0, AARGB1

;       Result: AARG  <--  AARG * BARG

;       Max Timing:     11 clks

;       Min Timing:     11 clks

;       PM: 10              DM: 3

PROG	CODE

FXM0808S
		GLOBAL	FXM0808S

		MOVF	__AARGB0,W
		MULWF	__BARGB0
		BTFSC	__BARGB0,MSB
		SUBWF	PRODH,F
		MOVF	__BARGB0,W
		BTFSC	__AARGB0,MSB
		SUBWF	PRODH,F
		MOVFF	PRODH,__AARGB0
		MOVFF	PRODL,__AARGB1

		RETLW	0x00

		END
