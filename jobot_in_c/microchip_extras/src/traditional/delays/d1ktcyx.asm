#include "p18cxxx.inc"

;/********************************************************************
;*   Function Name: Delay1KTCYx                                      *
;*   Return Value:  void                                             *
;*   Parameters:    unit:                                            *
;*   Description:   This routine delays for multiples of 1000        *
;*                  instruction cycles that is specified in unit.    *
;*   Special Note:  This routine provides multiples of 1000 Tcy      *
;*                  A value of 1 to 255 to provides 1000 to          *
;*                  255000 Tcy delay.  A value of 0 will provide     *
;*                  a delay of 256000 Tcy.                           *
;********************************************************************/
    EXTERN  DelayCounter1

D1KTCYXCODE CODE

Delay1KTCYx
    movlw   0xff
    movf    PLUSW1,0
    movwf   DelayCounter1
    movlw   0x48
    bra     D1K_1

D1Kx
    movlw   0x4c
D1K_1
    movwf   INDF1
    decfsz  INDF1,1
    bra     $-2

    clrf    INDF1
    decfsz  INDF1,1
    bra     $-2

    decfsz  DelayCounter1,1
    bra     D1Kx
    nop
    return

    GLOBAL  Delay1KTCYx
    END

