; RCS Header $Id: div08s.asm,v 1.5 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.5 $

          include <P18CXXX.INC>         ; general Golden Gate definitions
          include <CMATH18.INC>         ; Math library definitions

;-------------------------------------------------------------------------
;
; 8-bit signed division / modulus
;
; Prototype:  char __div08s (char A, char B);
;             char __mod08s (char A, char B);
;
; Stack after setup:
;  FSR1 --> [6] Unused slot          INDF1 is used as a counter
;         > [5] Previous FSR2H
;         > [4] Previous FSR2L
;         > [3] Temporary location 2 (FLAGS
;                                     7:SIGN
;                                     1:Early exit
;                                     0:Division)
;         > [2] Temporary location 1 (REMAINDER)
;         > [1] Parameter A byte 0
;  FSR2 --> [0] Parameter B byte 0
;
; Result:     8-bit integer located on the stack (A/B or A%B)
;  FSR1 --> Unused slot
;         > Return value byte 0
;
;-------------------------------------------------------------------------

PROG      CODE

          EXTERN    _early08uexit

SETUPSTK
          ; Allocate temporary locations
          ;stkadj    2                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 2

          ; Save current value of FSR2
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 6 is 2 bytes of parameters plus 2 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 6

          CLRF      [3]                 ; clear flags

          RETURN    0

__div08s  FUSTART
          GLOBAL    __div08s

          stkadj    4                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [3], LSB            ; set division flag
          RELOCTLMERGE SETSIGN
          FUEND
          BRA       SETSIGN

__mod08s  FUSTART
          GLOBAL    __mod08s
          stkadj    4                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK

SETSIGN
          BTFSS     [0], MSB
          BRA       DA0808S

          NEGF      [0]                 ; if B < 0, negate
          BTFSC     [3], LSB
          BTG       [3], MSB            ; toggle sign if doing division

DA0808S
          BTFSS     [1], MSB
          BRA       D0808S

          NEGF      [1]                 ; if A < 0, negate and toggle sign
          BTG       [3], MSB

D0808S
          CALL      _early08uexit

          BTFSC     [1], MSB            ; test for exception
          GOTO      D0808SX

D0808SOK
          BTFSS     [3], MSB
          BRA       RESTORE

D0808SNEG
          NEGF      [1]
          NEGF      [2]

RESTORE
          BTFSC     [3], LSB
          MOVSS     [1], [0]            ; Doing division.  Return quotient.
          BTFSS     [3], LSB
          MOVSS     [2], [0]            ; Doing modulus.  Return remainder.

          ; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVF      POSTDEC1, 1, 0
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     POSTDEC1, FSR2H
          MOVFF     INDF1, FSR2L

          ; Deallocate parameter and temporary stack locations.
          ; NOTE: Return value location is not deallocated here.
          stkadj    -3                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          SUBFSR    1, 3

          RETLW     0x00

D0808SX
          BTFSC     [3], MSB
          BRA       D0808SNEG
          RCALL     RESTORE
          FUEND
          RETLW     0xFF

          END
