#include "p18cxxx.inc"

;/**********************************************************************
;*      Function Name:  Delay100TCYx                                   *
;*      Return Value:   void                                           *
;*      Parameters:     unit:                                          *
;*      Description:    This routine delays for multiples of 100       *
;*                      instruction cycles that is specified in unit.  *
;*      Special Note:   This routine provides multiples of 100 Tcy.    *
;*                      A value of 1 to 255 provides 100 to 25500 Tcy  *
;*                      delay.  A value of 0 will provide a delay of   *
;*                      25600 Tcy.                                     *
;**********************************************************************/
        EXTERN  DelayCounter1

D100TCYXCODE    CODE

Delay100TCYx
        movlw   0xff
        movf    PLUSW1,0
        movwf   DelayCounter1
        movlw   0x1b
	bra     $+2
        bra     D100_1

D100x
        movlw   0x20
D100_1
        movwf   INDF1
        decfsz  INDF1,1
        bra     $-2
        decfsz  DelayCounter1,1
        bra     D100x
        bra     $+2
        return

        GLOBAL  Delay100TCYx

        END
