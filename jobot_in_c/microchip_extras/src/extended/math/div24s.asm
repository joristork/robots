; RCS Header $Id: div24s.asm,v 1.5 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.5 $

          include <P18CXXX.INC>     ; general Golden Gate definitions
          include <CMATH18.INC>     ; Math library definitions

;-------------------------------------------------------------------------
;
; 24-bit signed integer division / modulus
;
; Prototype:  short long __div24u (short long A, short long B);
;             short long __mod24u (short long A, short long B);
;
; Stack after setup:
;  FSR1 --> [12] Unused slot          INDF1 is used as a counter
;         > [11] Previous FSR2H
;         > [10] Previous FSR2L
;         > [ 9] Temporary location 4 (FLAGS
;                                      7:SIGN
;                                      1:Early exit
;                                      0:Division)
;         > [ 8] Temporary location 3 (REM byte 2)
;         > [ 7] Temporary location 2 (REM byte 1)
;         > [ 6] Temporary location 1 (REM byte 0)
;         > [ 5] Parameter A byte 2
;         > [ 4] Parameter A byte 1
;         > [ 3] Parameter A byte 0
;         > [ 2] Parameter B byte 2
;         > [ 1] Parameter B byte 1
;  FSR2 --> [ 0] Parameter B byte 0
;
; Result:     24-bit integer located on the stack (A/B or A%B)
;  FSR1 --> Unused slot
;         > Return value byte 2
;         > Return value byte 1
;         > Return value byte 0
;
;*** Optimized: Dec. 21, 2000
;***        by: Daniel R. Madill, Quanser Consulting Inc.
;***       for: Saved (for the worst case) at least 7*24 = 168 instruction
;***            cycles over the code supplied with MCC18 v1.00.12
;
;-------------------------------------------------------------------------

PROG      CODE

          EXTERN    _early24uexit

SETUPSTK
          ; Allocate temporary locations
          ;stkadj    4                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 4

          ; Save current value of FSR2
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          ;stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 12 is 6 bytes of parameters plus 4 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 12

          CLRF      [9]

          RETURN    0

__div24s  FUSTART
          GLOBAL    __div24s
          stkadj    6                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [9], LSB            ; set division flag
          RELOCTLMERGE SETSIGN
          FUEND
          BRA       SETSIGN

__mod24s  FUSTART
          GLOBAL    __mod24s
          stkadj    6                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK

SETSIGN
          BTFSS     [2], MSB
          GOTO      DA2424S

          NEGF      [0]                 ; if B < 0, negate
          COMF      [1], F
          COMF      [2], F
          CLRF      WREG
          ADDWFC    [1], F
          ADDWFC    [2], F
          BTFSC     [9], LSB
          BTG       [9], MSB            ; toggle sign if doing division

DA2424S
          BTFSS     [5], MSB            ; if A < 0, negate and toggle sign
          GOTO      D2424S

          NEGF      [3]
          COMF      [4], F
          COMF      [5], F
          CLRF      WREG
          ADDWFC    [4], F
          ADDWFC    [5], F
          BTG       [9], MSB

D2424S
          CALL      _early24uexit

          BTFSC     [5], MSB            ; test for exception
          GOTO      D2424SX

D2424SOK
          BTFSS     [9], MSB
          BRA       D2424SPOS
D2424SNEG
          NEGF      [3]                 ; negate if result negative
          COMF      [4], F
          COMF      [5], F
          CLRF      WREG
          ADDWFC    [4], F
          ADDWFC    [5], F

          NEGF      [6]
          COMF      [7], F
          COMF      [8], F
          ADDWFC    [7], F
          ADDWFC    [8], F

D2424SPOS
          BTFSC     [9], LSB
          BRA       RETQUOTIENT

          MOVSS     [6], [0]            ; Doing modulus.  Return remainder.
          MOVSS     [7], [1]
          MOVSS     [8], [2]

          BTFSS     [9], LSB
          BRA       RESTORE

RETQUOTIENT
          MOVSS     [3], [0]            ; Doing division.  Return quotient.
          MOVSS     [4], [1]
          MOVSS     [5], [2]

RESTORE
          ; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVF      POSTDEC1, 1, 0
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     POSTDEC1, FSR2H
          MOVFF     INDF1, FSR2L

          ; Deallocate parameter and temporary stack locations.
          ; NOTE: Return value locations are not deallocated here.
          stkadj    -7                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          SUBFSR    1, 7

          RETLW     0x00

D2424SX
          BTFSC     [9], MSB
          GOTO      D2424SNEG
          RCALL     D2424SPOS
          FUEND
          RETLW     0xFF

          END
