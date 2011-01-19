; RCS Header $Id: div24u.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.3 $

                include <P18CXXX.INC>   ; general Golden Gate definitions
                include <CMATH18.INC>   ; Math library definitions

;-------------------------------------------------------------------------
;
; 24-bit unsigned integer division / modulus
;
; Prototype:  unsigned short long __div24u (unsigned short long A,
;                                           unsigned short long B);
;             unsigned short long __mod24u (unsigned short long A,
;                                           unsigned short long B);
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

__div24u  FUSTART
          GLOBAL    __div24u
          stkadj    6                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function
          RCALL     SETUPSTK
          BSF       [9], LSB
          RELOCTLMERGE divmod24u
          FUEND
          BRA       divmod24u

__mod24u  FUSTART
          GLOBAL    __mod24u
          stkadj    6                   ; added STKADJ PSEUDO-INSTRUCTION for stack analysis. This is for the stack
                                        ; adjusts done in SETUPSTK function          
          RCALL     SETUPSTK
          BRA       divmod24u

_early24uexit
          GLOBAL    _early24uexit

          BSF       [9], 1

divmod24u

          CLRF      [8]                 ; Clear the remainder
          CLRF      [7]
          CLRF      [6]

          MOVLW     24                  ; Set the counter to 24
          MOVWF     INDF1, 0

          BCF       STATUS, 0, 0        ; Clear the carry flag

loop:
          ; The carry is always clear at the top of the loop.
          RLCF      [3], F              ; A <<= 1;
          RLCF      [4], F
          RLCF      [5], F

          RLCF      [6], F              ; REM = (REM << 1) | (A >> 24);
          RLCF      [7], F
          RLCF      [8], F

          MOVF      [0], W              ; if (REM >= B)
          SUBWF     [6], W
          MOVF      [1], W
          SUBWFB    [7], W
          MOVF      [2], W
          SUBWFB    [8], W
          BNC       _false              ; {
          MOVF      [0], W              ;   REM -= B;
          SUBWF     [6], F
          MOVF      [1], W
          SUBWFB    [7], F
          MOVF      [2], W
          SUBWFB    [8], F

          ; Note that since we shifted A above, we can add one
          ; simply by setting the least significant bit!  Use the
          ; incf operation to do this so that the carry flag is
          ; cleared. Thus, the carry flag will always be zero at
          ; the top of the loop.
          INCF      [3], F              ;   A++;


_false:                                 ; }

          DECFSZ    INDF1, F            ; if (--count != 0) then loop.
                                        ; Does not affect carry flag.
          BRA       loop

          ; If called from signed routine, want to exit without
          ; restoring the stack.
          BTFSC     [9], 1
          RETURN    0

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

          FUEND
          RETURN    0

          END
