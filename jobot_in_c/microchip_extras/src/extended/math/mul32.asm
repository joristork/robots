; RCS Header $Id: mul32.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.3 $

          include <P18CXXX.INC>     ; general Golden Gate definitions
          include <CMATH18.INC>     ; Math library definitions

;-------------------------------------------------------------------------
;
; 32-bit integer multiplication
;
; Prototype:  long __mul32 (long A, long B);
;
; Stack after setup:
;  FSR1 --> [14] Unused slot
;         > [13] Previous FSR2H
;         > [12] Previous FSR2L
;         > [11] Temporary location 4
;         > [10] Temporary location 3
;         > [ 9] Temporary location 2
;         > [ 8] Temporary location 1
;         > [ 7] Parameter A byte 3
;         > [ 6] Parameter A byte 2
;         > [ 5] Parameter A byte 1
;         > [ 4] Parameter A byte 0
;         > [ 3] Parameter B byte 3
;         > [ 2] Parameter B byte 2
;         > [ 1] Parameter B byte 1
;  FSR2 --> [ 0] Parameter B byte 0
;
; Result:     32-bit integer located on the stack (A*B)
;  FSR1 --> Unused slot
;         > Return value byte 3
;         > Return value byte 2
;         > Return value byte 1
;         > Return value byte 0
;
;-------------------------------------------------------------------------

PROG      CODE

__mul32   FUSTART
          GLOBAL    __mul32

          ; Allocate temporary locations
          stkadj    4                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 4

          ; Save current value of FSR2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 14 is 8 bytes of parameters plus 4 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 14

          ; Move parameter B into temporary locations.  This allows
          ; the return value to be placed in the correct location.
          MOVSS     [0], [8]
          MOVSS     [1], [9]
          MOVSS     [2], [10]
          MOVSS     [3], [11]
          
          MOVF      [4], W
          MULWF     [8]

          ; low byte of both operands, so result adds into the low order
          ; result bytes
          MOVF      PRODL, W
          MOVWF     [0]
          MOVF      PRODH, W
          MOVWF     [1]

          ; reload WREG with [4] to continue
          MOVF      [4], W
          MULWF     [9]

          ; [9], so result adds into [2][1]
          MOVF      PRODL, W
          ADDWF     [1], F
          MOVLW     0
          ADDWFC    PRODH, W
          MOVWF     [2]

          ; reload WREG with [4] to continue
          MOVF      [4], W
          MULWF     [10]

          ; [10], so result adds into [3][2]
          MOVF      PRODL, W
          ADDWF     [2], F
          MOVLW     0
          ADDWFC    PRODH, W
          MOVWF     [3]

          ; reload WREG with [4] to continue
          MOVF      [4], W
          MULWF     [11]

          ; [11], so result adds into [3]. 
          ; we don't care about result bytes above [3], ignore PRODH here.
          MOVF      PRODL, W
          ADDWF     [3], F

          ; that's the end of all terms involving [4].
          ; load [5] to continue
          MOVF      [5], W
          MULWF     [8]

          ; [5], so result adds into [3][2][1]
          MOVF      PRODL, W
          ADDWF     [1], F
          MOVF      PRODH, W
          ADDWFC    [2], F
          MOVLW     0
          ADDWFC    [3], F

          ; reload WREG with [5] to continue
          MOVF      [5], W
          MULWF     [9]

          ; [5] and [9], so result adds into [3][2]
          MOVF      PRODL, W
          ADDWF     [2], F
          MOVF      PRODH, W
          ADDWFC    [3], F

          ; reload WREG with [5] to continue
          MOVF      [5], W
          MULWF     [10]

          ; [5] and [10], so result adds into [3]
          ; we don't care about result bytes above [3], ignore PRODH here.
          MOVF      PRODL, W
          ADDWF     [3], F

          ; all bytes of the term from the product of [5] and [11]
          ; are above our 32-bit result, don't even need to bother calculating
          ; that term.
          ; load [6] to continue
          MOVF      [6], W
          MULWF     [8]

          ; [6] and [8], so result adds into [3][2]
          MOVF      PRODL, W
          ADDWF     [2], F
          MOVF      PRODH, W
          ADDWFC    [3], F

          ; reload WREG with [6] to continue
          MOVF      [6], W
          MULWF     [9]

          ; we don't care about result bytes above [3], ignore PRODH here.
          MOVF      PRODL, W
          ADDWF     [3], F

          ; all bytes of the terms from the products of [6] and
          ; [10][11] are above our 32-bit result, don't even need to bother
          ; calculating those terms.
          ; load [7] to continue
          MOVF      [7], W
          MULWF     [8]

          ; [7] and [8], so result adds into [3]
          MOVF      PRODL, W
          ADDWF     [3], F

          ; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVF      POSTDEC1, 1, 0
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     POSTDEC1, FSR2H
          MOVFF     INDF1, FSR2L

          ; Deallocate parameter and temporary stack locations.
          ; NOTE: Return value locations are not deallocated here.
          stkadj    -8                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          SUBFSR    1, 8
          FUEND
          RETLW     0x00

          END
