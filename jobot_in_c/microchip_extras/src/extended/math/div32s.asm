; RCS Header $Id: div32s.asm,v 1.5 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.5 $

          include <P18CXXX.INC>     ; general Golden Gate definitions
          include <CMATH18.INC>     ; Math library definitions


;-------------------------------------------------------------------------
;
; 32-bit signed integer division / modulus
;
; Prototype:  long __div32s (long A, long B);
;             long __mod32s (long A, long B);
;
; Stack after setup:
;  FSR1 --> [15] Unused slot          INDF1 is used as a counter
;         > [14] Previous FSR2H
;         > [13] Previous FSR2L
;         > [12] Temporary location 5 (FLAGS
;                                      7:SIGN
;                                      1:Early exit
;                                      0:Division)
;         > [11] Temporary location 4 (REM byte 3)
;         > [10] Temporary location 3 (REM byte 2)
;         > [ 9] Temporary location 2 (REM byte 1)
;         > [ 8] Temporary location 1 (REM byte 0)
;         > [ 7] Parameter A byte 3
;         > [ 6] Parameter A byte 2
;         > [ 5] Parameter A byte 1
;         > [ 4] Parameter A byte 0
;         > [ 3] Parameter B byte 3
;         > [ 2] Parameter B byte 2
;         > [ 1] Parameter B byte 1
;  FSR2 --> [ 0] Parameter B byte 0
;
; Result:     32-bit integer located on the stack (A/B or A%B)
;  FSR1 --> Unused slot
;         > Return value byte 3
;         > Return value byte 2
;         > Return value byte 1
;         > Return value byte 0
;
;*** Optimized: Dec. 21, 2000
;***        by: Daniel R. Madill, Quanser Consulting Inc.
;***       for: Saved (for the worst case) at least 8*32 = 256 instruction
;***            cycles over the code supplied with MCC18 v1.00.12
;
;-------------------------------------------------------------------------

PROG      CODE

          EXTERN     _early32uexit

SETUPSTK
          ; Allocate temporary locations
          ;stkadj    5                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 5

          ; Save current value of FSR2
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 15 is 8 bytes of parameters plus 5 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 15

          CLRF      [12]

          RETURN    0

__div32s  FUSTART
          GLOBAL    __div32s
          stkadj    7                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [12], LSB           ; set division flag
          RELOCTLMERGE SETSIGN
          FUEND
          BRA       SETSIGN

__mod32s  FUSTART
          GLOBAL    __mod32s
          stkadj    7                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK

SETSIGN
          CLRF      WREG

          BTFSS     [3], MSB
          GOTO      DA3232S

          NEGF      [0]                 ; if B < 0, negate
          COMF      [1], F
          COMF      [2], F
          COMF      [3], F
          ADDWFC    [1], F
          ADDWFC    [2], F
          ADDWFC    [3], F
          BTFSC     [12], LSB           ; toggle sign if doing division
          BTG       [12], MSB

DA3232S
          BTFSS     [7], MSB
          GOTO      D3232S

          NEGF      [4]                 ; if A < 0, negate and toggle sign
          COMF      [5], F
          COMF      [6], F
          COMF      [7], F
          ADDWFC    [5], F
          ADDWFC    [6], F
          ADDWFC    [7], F
          BTG       [12], MSB

D3232S
          CALL      _early32uexit

          BTFSC     [7], MSB            ; test for exception
          GOTO      D3232SX

D3232SOK
          BTFSS     [12], MSB
          BRA       D3232SPOS
D3232SNEG
          NEGF      [4]                 ; negate if result negative
          COMF      [5], F
          COMF      [6], F
          COMF      [7], F
          CLRF      WREG
          ADDWFC    [5], F
          ADDWFC    [6], F
          ADDWFC    [7], F

          NEGF      [8]
          COMF      [9], F
          COMF      [10], F
          COMF      [11], F
          ADDWFC    [9], F
          ADDWFC    [10], F
          ADDWFC    [11], F

D3232SPOS
          BTFSC     [12], LSB
          BRA       RETQUOTIENT

          MOVSS     [8], [0]            ; Doing modulus.  Return remainder.
          MOVSS     [9], [1]
          MOVSS     [10], [2]
          MOVSS     [11], [3]

          BTFSS     [12], LSB
          BRA       RESTORE

RETQUOTIENT
          MOVSS     [4], [0]            ; Doing division.  Return quotient.
          MOVSS     [5], [1]
          MOVSS     [6], [2]
          MOVSS     [7], [3]

RESTORE
          ; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVF      POSTDEC1, 1, 0
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     POSTDEC1, FSR2H
          MOVFF     INDF1, FSR2L

          ; Deallocate parameter and temporary stack locations.
          ; NOTE: Return value locations are not deallocated here.
          stkadj    -9                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          SUBFSR    1, 9

          RETLW     0x00

D3232SX
          BTFSC     [12], MSB
          GOTO      D3232SNEG
          RCALL     D3232SPOS
          FUEND
          RETLW     0xFF

          END
