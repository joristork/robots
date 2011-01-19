;	RCS Header $Id: barg.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;       CMATH18 DATA DEFINITION FILE

;*******************************************************************************
;   VARIABLE ALLOCATION - Core math library routines
;*******************************************************************************

MATH_DATA      UDATA_ACS

__BARGB3		RES 1
__BARGB2		RES 1
__BARGB1		RES 1
__BARGB0		RES 1       ; most significant byte of argument B

__BEXP		RES 1       ; 8 bit biased exponent for argument B

        GLOBAL  __BARGB3, __BARGB2, __BARGB1, __BARGB0
        GLOBAL  __BEXP

        END
