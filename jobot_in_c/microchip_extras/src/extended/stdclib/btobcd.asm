; RCS Header $Id: btobcd.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

;********************************************************************
;                  Binary To BCD Conversion Routine
;      This routine converts a 8 Bit binary Number to a 3 Digit BCD Number.
;       The 8 bit binary number is Value
;       The 3 digit BCD number is returned in BCD[2] with BCD[1] containing 
;       the LSD in its right most nibble.
;
;*******************************************************************;

; void btoBCD( unsigned char Value, char *BCD )

; Stack Offsets from top:
#define OffCount -1  ; Count
#define OffVal   -2  ; Value

STDLIB CODE

btoBCD FUSTART
  global btoBCD

          Stk2CpyToReg -3,FSR0L         ; pointer to BCD

     ;; Create Count on stack and load it with 8
          movlw     8
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movwf     POSTINC1,ACCESS

          clrf      POSTINC0,ACCESS     ; BCD[0]
          clrf      INDF0,ACCESS        ; BCD[1]

          bcf       STATUS,C,ACCESS     ; clear the carry bit
jLoop:
          movlw     OffVal              ; Value 
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          rlcf      PLUSW1,F,ACCESS

          rlcf      POSTDEC0,F,ACCESS   ; BCD[1]
          rlcf      INDF0,F,ACCESS      ; BCD[0]

          movlw     OffCount
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          decf      PLUSW1,F,ACCESS
          bz        jEnd

          call      OneByte             ; BCD[0]
          movf      POSTINC0,F,ACCESS   ; point FSR0 to  bBCD[1]
          call      OneByte             ; BCD[1]
;
          bra       jLoop

jEnd:
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movwf     POSTDEC1,ACCESS     ; fixup stack
          FUEND
          return    


OneByte   movlw     3
          addwf     INDF0,W,ACCESS
          btfsc     WREG,3,ACCESS    ; test if result > 7
          movwf     INDF0,ACCESS
          movlw     0x30
          addwf     INDF0,W,ACCESS
          btfsc     WREG,7,ACCESS    ; test if result > 7
          movwf     INDF0,ACCESS
          retlw     0x00

          end
