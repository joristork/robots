;	RCS Header $Id: cmath18.asm,v 1.5 2006/01/12 23:16:38 rhinec Exp $

;       CMATH18 DATA DEFINITION FILE

;*******************************************************************************
;   VARIABLE ALLOCATION - Core math library routines
;*******************************************************************************

MATH_DATA      UDATA_ACS

SIGN		RES 1       ; save location for sign in MSB
__FPFLAGSbits
__FPFLAGS		RES 1       ; floating point library exception flags

        GLOBAL  SIGN, __FPFLAGS, __FPFLAGSbits
	
        END
