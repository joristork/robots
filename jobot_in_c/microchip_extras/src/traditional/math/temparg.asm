;	RCS Header $Id: temparg.asm,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;       CMATH18 DATA DEFINITION FILE

;*******************************************************************************
;   VARIABLE ALLOCATION - Core math library routines
;*******************************************************************************

MATH_DATA      UDATA_ACS

__TEMPB3		RES 1
__TEMPB2		RES 1
__TEMPB1		RES 1
__TEMP
__TEMPB0		RES 1       ; temporary storage

        GLOBAL  __TEMPB3, __TEMPB2, __TEMPB1, __TEMPB0, __TEMP
	
        END
