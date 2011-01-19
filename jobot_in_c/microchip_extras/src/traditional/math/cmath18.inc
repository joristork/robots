;	RCS Header $Id: cmath18.inc,v 1.3 2006/01/12 23:16:38 rhinec Exp $

;	$Revision: 1.3 $

    LIST
; CMATH18.INC  v1.0  Microchip Technology, Inc. / F.J. Testa
    NOLIST

; This file should be included at the top of all math library routines except
; CMATH18x.ASM and at the top of user code.

;       CMATH18 INCLUDE FILE


;*******************************************************************************
;   Variables declared in cmath18.asm
;*******************************************************************************

	EXTERN	SIGN, __FPFLAGS
	
;*******************************************************************************
;   General Literal Constants
;*******************************************************************************

MSB     equ 7
LSB     equ 0

;*******************************************************************************
;   Commonly Used Bits
;*******************************************************************************

#define _C      STATUS,0
#define _DC     STATUS,1
#define _Z      STATUS,2
#define _OV     STATUS,3

;*******************************************************************************
;   Floating Point Specific Constants
;*******************************************************************************

EXPBIAS         equ     D'127'

;*******************************************************************************
;   Floating Point Exception Flags in Variable FPFLAGS
;*******************************************************************************

IOV	equ     0       ; bit0 = integer overflow flag
FOV	equ     1       ; bit1 = floating point overflow flag
FUN	equ     2       ; bit2 = floating point underflow flag
FDZ	equ     3       ; bit3 = floating point divide by zero flag
NAN     equ	4   	; bit4 = not-a-number exception flag
DOM     equ	5   	; bit5 = domain error flag
RND	equ     6       ; bit6 = floating point rounding flag, 0 = truncation
                                ; 1 = unbiased rounding to nearest LSB
SAT	equ     7       ; bit7 = floating point saturate flag, 0 = terminate on
                                ; exception without saturation, 1 = terminate on
                                ; exception with saturation to appropriate value


        LIST
