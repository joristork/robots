; RCS Header $Id: div16s.asm,v 1.5 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.5 $

          include <P18CXXX.INC>     ; general Golden Gate definitions
          include <CMATH18.INC>     ; Math library definitions
          

;-------------------------------------------------------------------------
;
; 16-bit unsigned integer division / modulus
;
; Prototype:  unsigned int __div16u (unsigned int A, unsigned int B);
;             unsigned int __mod16u (unsigned int A, unsigned int B);
;
; Stack after setup:
;  FSR1 --> [9] Unused slot          INDF1 is used as a counter
;         > [8] Previous FSR2H
;         > [7] Previous FSR2L
;         > [6] Temporary location 3 (FLAGS
;                                     7:SIGN
;                                     1:Early exit
;                                     0:Division)
;         > [5] Temporary location 2 (REM byte 1)
;         > [4] Temporary location 1 (REM byte 0)
;         > [3] Parameter A byte 1
;         > [2] Parameter A byte 0
;         > [1] Parameter B byte 1
;  FSR2 --> [0] Parameter B byte 0
;
; Result:     16-bit integer located on the stack (A/B or A%B)
;  FSR1 --> Unused slot
;         > Return value byte 1
;         > Return value byte 0
;-------------------------------------------------------------------------

PROG      CODE

          EXTERN    _early16uexit

SETUPSTK
          ; Allocate temporary locations
          ;stkadj    3                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 3

          ; Save current value of FSR2
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 9 is 4 bytes of parameters plus 3 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 9

          CLRF      [6]

          RETURN    0

__div16s  FUSTART
          GLOBAL    __div16s
          stkadj    5                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [6], LSB            ; set division flag
          RELOCTLMERGE SETSIGN
          FUEND
          BRA       SETSIGN

__mod16s  FUSTART
          GLOBAL    __mod16s
          stkadj    5                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK

SETSIGN
          BTFSS     [1], MSB
          GOTO      DA1616S

          NEGF      [0]                 ; if B < 0, negate
          COMF      [1], F
          CLRF      WREG
          ADDWFC    [1], F
          BTFSC     [6], LSB
          BTG       [6], MSB            ; toggle sign if doing division

DA1616S
          BTFSS     [3], MSB
          GOTO      D1616S

          NEGF      [2]                 ; if A < 0, negate and toggle sign
          COMF      [3], F
          CLRF      WREG
          ADDWFC    [3], F
          BTG       [6], MSB

D1616S
          CALL      _early16uexit

          BTFSC     [3], MSB            ; test for exception
          GOTO      D1616SX

D1616SOK
          BTFSS     [6], MSB
          BRA       D1616SPOS
D1616SNEG
          NEGF      [2]                 ; negate if result negative
          COMF      [3], F
          CLRF      WREG
          ADDWFC    [3], F

          NEGF      [4]
          COMF      [5], F
          CLRF      WREG
          ADDWFC    [5], F

D1616SPOS
          BTFSC     [6], LSB
          BRA       RETQUOTIENT

          MOVSS     [4], [0]            ; Doing modulus.  Return remainder.
          MOVSS     [5], [1]

          BTFSS     [6], LSB
          BRA       RESTORE

RETQUOTIENT
          MOVSS     [2], [0]            ; Doing division.  Return quotient.
          MOVSS     [3], [1]

RESTORE
          ; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVF      POSTDEC1, 1, 0
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     POSTDEC1, FSR2H
          MOVFF     INDF1, FSR2L

          ; Deallocate parameter and temporary stack locations.
          ; NOTE: Return value locations are not deallocated here.
          stkadj    -5                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          SUBFSR    1, 5

          RETLW     0x00

D1616SX
          BTFSC     [6], MSB
          GOTO      D1616SNEG
          RCALL     D1616SPOS
          FUEND
          RETLW     0xFF

          END
