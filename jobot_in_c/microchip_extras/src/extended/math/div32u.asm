; RCS Header $Id: div32u.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.3 $

                include <P18CXXX.INC>   ; general Golden Gate definitions
                include <CMATH18.INC>   ; Math library definitions

;-------------------------------------------------------------------------
;
; 32-bit unsigned integer division / modulus
;
; Prototype:  unsigned long __div32u (unsigned long A, unsigned long B);
;             unsigned long __mod32u (unsigned long A, unsigned long B);
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

__div32u  FUSTART
          GLOBAL    __div32u
          stkadj    7                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [12], LSB
          RELOCTLMERGE divmod32u
          FUEND
          BRA       divmod32u

__mod32u  FUSTART
          GLOBAL    __mod32u
          stkadj    7                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function          
          RCALL     SETUPSTK
          BRA       divmod32u

_early32uexit
          GLOBAL    _early32uexit

          BSF       [12], 1

divmod32u
          CLRF      [11]                ; REM = 0
          CLRF      [10]
          CLRF      [9]
          CLRF      [8]

          MOVLW     32                  ; INDF1 = 32
          MOVWF     INDF1, 0

          BCF       STATUS, 0, 0        ; Clear the carry

loop:
          ; The carry is always clear at the top of the loop.
          RLCF      [4], F              ; A <<= 1;
          RLCF      [5], F
          RLCF      [6], F
          RLCF      [7], F

          RLCF      [8], F              ; REM = (REM << 1) | (A >> 32)
          RLCF      [9], F
          RLCF      [10], F
          RLCF      [11], F
          
          MOVF      [0], W              ; if (REM >= B)
          SUBWF     [8], W
          MOVF      [1], W
          SUBWFB    [9], W
          MOVF      [2], W
          SUBWFB    [10], W
          MOVF      [3], W
          SUBWFB    [11], W
          BNC       _false              ; {
          MOVF      [0], W              ;   REM -= B;
          SUBWF     [8], F
          MOVF      [1], W
          SUBWFB    [9], F
          MOVF      [2], W
          SUBWFB    [10], F
          MOVF      [3], W
          SUBWFB    [11], F

          ; Since A was shift to the left above, we only need
          ; to set the lowest bit. Use incf so that the carry flag
          ; will also be cleared.  Thus, the carry will always be
          ; clear at the top of the loop.
          INCF [4], F                   ;   ++A;
_false:                                 ; }

          DECFSZ    INDF1, F            ; does not affect the carry bit
          BRA       loop

          ; If called from signed routine, want to exit without
          ; restoring the stack.
          BTFSC     [12], 1
          RETURN    0

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

          FUEND
          RETURN    0

          END
