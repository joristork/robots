; RCS Header $Id: div16u.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.3 $

                include <P18CXXX.INC>   ; general Golden Gate definitions
                include <CMATH18.INC>   ; Math library definitions

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
;
;*** Optimized: Dec. 21, 2000
;***        by: Daniel R. Madill, Quanser Consulting Inc.
;***       for: Saved (for the worst case) at least 5*16 = 80 instruction
;***            cycles over the code supplied with MCC18 v1.00.12
;
;-------------------------------------------------------------------------

PROG      CODE

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

__div16u  FUSTART
          GLOBAL    __div16u
          stkadj    5                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [6], LSB
          RELOCTLMERGE divmod16u
          FUEND
          BRA       divmod16u

__mod16u  FUSTART
          GLOBAL    __mod16u
          stkadj    5                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function          
          RCALL     SETUPSTK
          FUEND
          BRA       divmod16u

_early16uexit
          GLOBAL    _early16uexit

          BSF       [6], 1

divmod16u
          CLRF      [5]                 ; REM = 0
          CLRF      [4]
          
          MOVLW     16                  ; INDF1 = 16
          MOVWF     INDF1, 0

          BCF       STATUS, 0, 0        ; Clear the carry

loop
          ; Carry is always clear at this point.
          RLCF      [2], F              ; A <<= 1;
          RLCF      [3], F

          RLCF      [4], F              ; REM = (REM << 1) | (A >> 16)
          RLCF      [5], F
          
          MOVF      [0], W              ; if (REM >= B)
          SUBWF     [4], W
          MOVF      [1], W
          SUBWFB    [5], W
          BNC       endloop             ; {
          MOVF      [0], W              ;   REM -= B;
          SUBWF     [4], F
          MOVF      [1], W
          SUBWFB    [5], F

          ; Since A was shift to the left above, the increment will
          ; simply set the LSbit. Using incf also clears the carry, which
          ; means we don't have to clear the carry at the top of the loop.
          INCF      [2], F              ;   ++A;

endloop                                 ; }
          DECFSZ    INDF1, F            ; does not affect the carry bit
          BRA       loop

          ; If called from signed routine, want to exit without
          ; restoring the stack.
          BTFSC     [6], 1
          RETURN    0

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

          FUEND
          RETURN    0

          END
