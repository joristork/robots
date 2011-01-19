; RCS Header $Id: ltobcd.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

;********************************************************************
;                  Binary To BCD Conversion Routine
;      This routine converts a 32 Bit binary Number to a 10 Digit BCD Number.
;
;       The 32 bit binary number is Value
;       The 10 digit BCD number is returned in BCD[5] with BCD[4] containing 
;       the LSD in its right most nibble.
;
;*******************************************************************;

; void ltoBCD( long Value, char *BCD)

; Stack Offsets from top:
#define OffCount -1  ; Count
#define OffValL  -5  ; Value (low  byte)
#define OffValH  -2  ; Value (high byte)

STDLIB CODE
ltoBCD   FUSTART
  global ltoBCD

          Stk2CpyToReg -6,FSR0L         ; pointer to BCD

     ;; Create Count on stack and load it with 32
          movlw     32
          movwf     POSTINC1,ACCESS

          clrf      POSTINC0,ACCESS     ; BCD[0]
          clrf      POSTINC0,ACCESS     ; BCD[1]
          clrf      POSTINC0,ACCESS     ; BCD[2]
          clrf      POSTINC0,ACCESS     ; BCD[3]
          clrf      INDF0,ACCESS        ; BCD[4]

          bcf       STATUS,C,ACCESS     ; clear the carry bit
jLoop:
          movlw     OffValL             ; Value (low  byte)
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          rlcf      PLUSW1,F,ACCESS
          movlw     OffValL+1           ; Value (1st byte)
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          rlcf      PLUSW1,F,ACCESS
          movlw     OffValL+2           ; Value (2nd byte)
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          rlcf      PLUSW1,F,ACCESS
          movlw     OffValH             ; Value (high  byte)
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          rlcf      PLUSW1,F,ACCESS

          rlcf      POSTDEC0,F,ACCESS   ; BCD[4]
          rlcf      POSTDEC0,F,ACCESS   ; BCD[3]
          rlcf      POSTDEC0,F,ACCESS   ; BCD[2]
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
          movf      POSTINC0,F,ACCESS   ; point FSR0 to  bBCD[2]
          call      OneByte             ; BCD[1]
;
          movf      POSTINC0,F,ACCESS   ; point FSR0 to  bBCD[3]
          call      OneByte             ; BCD[1]
;
          movf      POSTINC0,F,ACCESS   ; point FSR0 to  bBCD[4]
          call      OneByte             ; BCD[0]
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
