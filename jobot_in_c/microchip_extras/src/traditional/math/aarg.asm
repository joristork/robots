;	RCS Header $Id: aarg.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;       CMATH18 DATA DEFINITION FILE

;*******************************************************************************
;   VARIABLE ALLOCATION - Core math library routines
;*******************************************************************************

MATH_DATA      UDATA_ACS

__REMB3
__AARGB7		RES 1
__REMB2
__AARGB6		RES 1
__REMB1
__AARGB5		RES 1
__REMB0
__AARGB4		RES 1
__AARGB3		RES 1
__AARGB2		RES 1
__AARGB1		RES 1
__AARGB0		RES 1

__AEXP		RES 1       ; 8 bit biased exponent for argument A

	GLOBAL	__AARGB0, __AARGB1, __AARGB2, __AARGB3
        GLOBAL  __AARGB4, __AARGB5, __AARGB6, __AARGB7
	GLOBAL  __AEXP
	GLOBAL  __REMB3, __REMB2, __REMB1, __REMB0

        END
