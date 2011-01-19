; RCS Header $Id: mul24.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $
; $Revision: 1.3 $

          include <P18CXXX.INC>     ; general Golden Gate definitions
          include <CMATH18.INC>     ; Math library definitions

;-------------------------------------------------------------------------
;
; 24-bit integer multiplication
;
; Prototype:  short long __mul24 (short long A, short long B);
;
; Stack after setup:
;  FSR1 --> [14] Unused slot
;         > [13] Previous FSR2H
;         > [12] Previous FSR2L
;         > [11] Temporary location 6
;         > [10] Temporary location 5
;         > [ 9] Temporary location 4
;         > [ 8] Temporary location 3
;         > [ 7] Temporary location 2
;         > [ 6] Temporary location 1
;         > [ 5] Parameter A byte 2
;         > [ 4] Parameter A byte 1
;         > [ 3] Parameter A byte 0
;         > [ 2] Parameter B byte 2
;         > [ 1] Parameter B byte 1
;  FSR2 --> [ 0] Parameter B byte 0
;
; Result:     24-bit integer located on the stack (A*B)
;  FSR1 --> Unused slot
;         > Return value byte 2
;         > Return value byte 1
;         > Return value byte 0
;
;-------------------------------------------------------------------------

PROG     CODE

__mul24   FUSTART
          GLOBAL    __mul24

          ; Allocate temporary locations
          stkadj    6                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          ADDFSR    1, 6

          ; Save current value of FSR2
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2L, POSTINC1
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          MOVFF     FSR2H, POSTINC1

          ; Set up FSR2 to point to the first parameter on the stack.
          ; The 14 is 6 bytes of parameters plus 6 byte for temporaries
          ; plus 2 bytes for the previous value of FSR2
          MOVFF     FSR1L, FSR2L
          MOVFF     FSR1H, FSR2H
          SUBFSR    2, 14

          ; Move parameter B into temporary locations.  This allows
          ; the return value to be placed in the correct location.
          MOVSS     [0], [6]
          MOVSS     [1], [7]
          MOVSS     [2], [8]

          MOVSS     [5], [11]     
          MOVSS     [4], [10]     
          MOVSS     [3], [9]     

          MOVF      [3], W
          MULWF     [6]
          MOVF      PRODH, W
          MOVWF     [1]
          MOVF      PRODL, W
          MOVWF     [0]
          
          MOVF      [4], W
          MULWF     [7]
          MOVF      PRODH, W
          MOVWF     [3]
          MOVF      PRODL, W
          MOVWF     [2]

          MOVF      [4], W
          MULWF     [6]
          MOVF      PRODL, W
          ADDWF     [1], F
          MOVF      PRODH, W
          ADDWFC    [2], F
          CLRF      WREG
          ADDWFC    [3], F

          MOVF      [9], W
          MULWF     [7]
          MOVF      PRODL, W
          ADDWF     [1], F
          MOVF      PRODH, W
          ADDWFC    [2], F
          CLRF      WREG
          ADDWFC    [3], F
          
          MOVF      [5], W
          MULWF     [6]
          MOVF      PRODL, W
          ADDWF     [2], F
          MOVF      PRODH, W
          ADDWFC    [3], F
          MOVF      [5], W
          MULWF     [7]
          CLRF      [4]
          CLRF      WREG
          ADDWFC    [4], F
          MOVF      PRODL, W
          ADDWF     [3], F
          MOVF      PRODH, W
          ADDWFC    [4], F

          MOVF      [9], W
          MULWF     [8]
          MOVF      PRODL, W
          ADDWF     [2], F
          MOVF      PRODH, W
          ADDWFC    [3], F
          CLRF      [5]
          CLRF      WREG
          ADDWFC    [4], F
          ADDWFC    [5], F

          MOVF      [10], W
          MULWF     [8]
          MOVF      PRODL, W
          ADDWF     [3], F
          MOVF      PRODH, W
          ADDWFC    [4], F
          CLRF      WREG
          ADDWFC    [5], F

          MOVF      [11], W
          MULWF     [8]
          MOVF      PRODL, W
          ADDWF     [4], F
          MOVF      PRODH, W
          ADDWFC    [5], F

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
          RETLW     0x00

          END
