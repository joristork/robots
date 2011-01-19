; RCS Header $Id: mul16.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.3 $

          include <P18CXXX.INC>     ; general Golden Gate definitions
          include <CMATH18.INC>     ; Math library definitions

;-------------------------------------------------------------------------
;
; 16-bit integer multiplication
;
; Prototype:  int __mul16 (int A, int B);
;
; Stack after setup:
;  FSR1 --> [10] Unused slot
;         > [ 9] Previous FSR2H
;         > [ 8] Previous FSR2L
;         > [ 7] Temporary location 4
;         > [ 6] Temporary location 3
;         > [ 5] Temporary location 2
;         > [ 4] Temporary location 1
;         > [ 3] Parameter A byte 1
;         > [ 2] Parameter A byte 0
;         > [ 1] Parameter B byte 1
;  FSR2 --> [ 0] Parameter B byte 0
;
; Result:     16-bit integer located on the stack (A*B)
;  FSR1 --> Unused slot
;         > Return value byte 1
;         > Return value byte 0
;-------------------------------------------------------------------------

PROG      CODE

__mul16   FUSTART
          GLOBAL    __mul16

          ; Allocate temporary locations
          stkadj    4                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 4

          ; Save current value of FSR2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 9 is 4 bytes of parameters plus 4 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 10

          ; Move parameter B into temporary locations.  This allows
          ; the return value to be placed in the correct location.
          MOVSS     [0], [4]
          MOVSS     [1], [5]

          MOVSS     [2], [6]     

          MOVF      [2], W
          MULWF     [4]
          MOVF      PRODH, W
          MOVWF     [1]
          MOVF      PRODL, W
          MOVWF     [0]
          
          MOVF      [3], W
          MOVWF     [7]
          MULWF     [5]
          MOVF      PRODH, W
          MOVWF     [3]
          MOVF      PRODL, W
          MOVWF     [2]

          MOVF      [7], W
          MULWF     [4]
          MOVF      PRODL, W
          ADDWF     [1], F
          MOVF      PRODH, W
          ADDWFC    [2], F
          CLRF      WREG
          ADDWFC    [3], F

          MOVF      [6], W
          MULWF     [5]
          MOVF      PRODL, W
          ADDWF     [1], F
          MOVF      PRODH, W
          ADDWFC    [2], F
          CLRF      WREG
          ADDWFC    [3], F
          
          ; Restore FSR2
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVF      POSTDEC1, 1, 0
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     POSTDEC1, FSR2H
          MOVFF     INDF1, FSR2L

          ; Deallocate parameter and temporary stack locations.
          ; NOTE: Return value locations are not deallocated here.
          stkadj    -6                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          SUBFSR    1, 6

          FUEND
          RETLW     0x00

          END
