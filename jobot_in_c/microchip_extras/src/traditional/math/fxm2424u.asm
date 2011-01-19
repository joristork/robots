;	RCS Header $Id: fxm2424u.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

	include <P18CXXX.INC>		; general Golden Gate definitions

	include <CMATH18.INC>		; Math library definitions

	include <AARG.INC>		; AARG declarations
	
	include <BARG.INC>		; BARG declarations

	include <TEMPARG.INC>		; TEMPARG declarations

;**********************************************************************************************
;**********************************************************************************************

;       24x24 Bit Unsigned Fixed Point Multiply 24 x 24 -> 48

;       Input:  24 bit unsigned fixed point multiplicand in AARGB0, AARGB1,
;               AARGB2
;               24 bit unsigned fixed point multiplier in BARGB0, BARGB1,
;               BARGB2

;       Use:    CALL    FXM2424U

;       Output: 48 bit unsigned fixed point product in AARGB0, AARGB1, 
;               AARGB2, AARGB3, AARGB4, AARGB5

;       Result: AARG  <--  AARG * BARG

;       Max Timing:     65 clks

;       Min Timing:     65 clks

;       PM: 64              DM: 12

PROG	CODE

FXM2424U
		GLOBAL	FXM2424U

		MOVFF	__AARGB0,__TEMPB0	
		MOVFF	__AARGB1,__TEMPB1	
		MOVFF	__AARGB2,__TEMPB2	

		MOVF	__AARGB2,W
		MULWF	__BARGB2
		MOVFF	PRODH,__AARGB4
		MOVFF	PRODL,__AARGB5
		
		MOVF	__AARGB1,W
		MULWF	__BARGB1
		MOVFF	PRODH,__AARGB2
		MOVFF	PRODL,__AARGB3

		MULWF	__BARGB2
		MOVF	PRODL,W
		ADDWF	__AARGB4,F
		MOVF	PRODH,W
		ADDWFC	__AARGB3,F
		CLRF	WREG
		ADDWFC	__AARGB2,F

		MOVF	__TEMPB2,W
		MULWF	__BARGB1
		MOVF	PRODL,W
		ADDWF	__AARGB4,F
		MOVF	PRODH,W
		ADDWFC	__AARGB3,F
		CLRF	WREG
		ADDWFC	__AARGB2,F
	
		MOVF	__AARGB0,W
		MULWF	__BARGB2
		MOVF	PRODL,W
		ADDWF	__AARGB3,F
		MOVF	PRODH,W
		ADDWFC	__AARGB2,F
		MOVF	__AARGB0,W
		MULWF	__BARGB1
		CLRF	__AARGB1
		CLRF	WREG
		ADDWFC	__AARGB1,F
		MOVF	PRODL,W
		ADDWF	__AARGB2,F
		MOVF	PRODH,W
		ADDWFC	__AARGB1,F

		MOVF	__TEMPB2,W
		MULWF	__BARGB0
		MOVF	PRODL,W
		ADDWF	__AARGB3,F
		MOVF	PRODH,W
		ADDWFC	__AARGB2,F
		CLRF	__AARGB0
		CLRF	WREG
		ADDWFC	__AARGB1,F
		ADDWFC	__AARGB0,F

		MOVF	__TEMPB1,W
		MULWF	__BARGB0
		MOVF	PRODL,W
		ADDWF	__AARGB2,F
		MOVF	PRODH,W
		ADDWFC	__AARGB1,F
		CLRF	WREG
		ADDWFC	__AARGB0,F

		MOVF	__TEMPB0,W
		MULWF	__BARGB0
		MOVF	PRODL,W
		ADDWF	__AARGB1,F
		MOVF	PRODH,W
		ADDWFC	__AARGB0,F

		RETLW	0x00

		END
