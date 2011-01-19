;	RCS Header $Id: fxm1616s.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

	include <TEMPARG.INC>		; TEMPARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       16x16 Bit Signed Fixed Point Multiply 16 x 16 -> 32

;       Input:  16 bit signed fixed point multiplicand in AARGB0, AARGB1
;               16 bit signed fixed point multiplier in BARGB0, BARGB1

;       Use:    CALL    FXM1616S

;       Output: 32 bit signed fixed point product in AARGB0, AARGB1,
;               AARGB2, AARGB3

;       Result: AARG  <--  AARG * BARG

;       Max Timing:     39 clks

;       Min Timing:     31 clks

;       PM: 38              DM: 8

PROG	CODE

FXM1616S
		GLOBAL	FXM1616S

		MOVFF	__AARGB0,__TEMPB0
		MOVFF	__AARGB1,__TEMPB1	

		MOVF	__AARGB1,W
		MULWF	__BARGB1
		MOVFF	PRODH,__AARGB2
		MOVFF	PRODL,__AARGB3
		
		MOVF	__AARGB0,W
		MULWF	__BARGB0
		MOVFF	PRODH,__AARGB0
		MOVFF	PRODL,__AARGB1

		MULWF	__BARGB1
		MOVF	PRODL,W
		ADDWF	__AARGB2,F
		MOVF	PRODH,W
		ADDWFC	__AARGB1,F
		CLRF	WREG
		ADDWFC	__AARGB0,F

		MOVF	__TEMPB1,W
		MULWF	__BARGB0
		MOVF	PRODL,W
		ADDWF	__AARGB2,F
		MOVF	PRODH,W
		ADDWFC	__AARGB1,F
		CLRF	WREG
		ADDWFC	__AARGB0,F

		BTFSS	__BARGB0,MSB
		GOTO	TSIGN1616A
		MOVF	__TEMPB1,W
		SUBWF	__AARGB1,F
		MOVF	__TEMPB0,W
		SUBWFB	__AARGB0,F

TSIGN1616A
		BTFSS	__TEMPB0,MSB
		RETLW	0x00
		MOVF	__BARGB1,W
		SUBWF	__AARGB1,F
		MOVF	__BARGB0,W
		SUBWFB	__AARGB0,F
		
		RETLW	0x00

		END
