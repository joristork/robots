#include "p18cxxx.inc"

;/********************************************************************
;*   Function Name: Delay10TCYx                                      *
;*   Return Value:  void                                             *
;*   Parameters:    unit:                                            *
;*   Description:   This routine delays for multiples of 10          *
;*                  instruction cycles that is specified in unit.    *
;*   Special Note:  This routine provides multiples of 10 Tcy.       *
;*                  A value of 1 to 255 provides 10 to 2550   Tcy    *
;*                  delay.  A value of 0 will provide a delay of     *
;*                  2560 Tcy.                                        *
;********************************************************************/
D10TCYXCODE CODE

Delay10TCYx
    movlw   0xff
    movf    PLUSW1,0
    dcfsnz  WREG,1
    return

D10x
    nop
    bra     $+2
    bra     $+2
    bra     $+2
D10_1
    decfsz  WREG,1
    bra     D10x
    return

    GLOBAL  Delay10TCYx
    END
