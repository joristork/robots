; RCS Header $Id: itobcd.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

;********************************************************************
;                  Binary To BCD Conversion Routine
;      This routine converts a 16 Bit binary Number to a 5 Digit BCD Number.
;
;       The 16 bit binary number is Value
;       The 5 digit BCD number is returned in BCD[3] with BCD[2] containing 
;       the LSD in its right most nibble.
;
;*******************************************************************;

; void itoBCD( int Value, char *BCD)

; Stack Offsets from top:
#define OffCount -1  ; Count
#define OffValL  -3  ; Value (low  byte)
#define OffValH  -2  ; Value (high byte)

STDLIB CODE
itoBCD
  global itoBCD

          Stk2CpyToReg -4,FSR0L         ; pointer to BCD

     ;; Create Count on stack and load it with 16
          movlw     0x10 
          movwf     POSTINC1,ACCESS

          clrf      POSTINC0,ACCESS     ; BCD[0]
          clrf      POSTINC0,ACCESS     ; BCD[1]
          clrf      INDF0,ACCESS        ; BCD[2]

          bcf       STATUS,C,ACCESS     ; clear the carry bit
jLoop:
          movlw     OffValL             ; Value (low  byte)
          rlcf      PLUSW1,F,ACCESS
          movlw     OffValH             ; Value (high  byte)
          rlcf      PLUSW1,F,ACCESS

          rlcf      POSTDEC0,F,ACCESS   ; BCD[2]
          rlcf      POSTDEC0,F,ACCESS   ; BCD[1]
          rlcf      INDF0,F,ACCESS      ; BCD[0]

          movlw     OffCount
          decf      PLUSW1,F,ACCESS
          bz        jEnd

          call      OneByte             ; BCD[0]
          movf      POSTINC0,F,ACCESS   ; point FSR0 to  bBCD[1]
          call      OneByte             ; BCD[1]
;
          movf      POSTINC0,F,ACCESS   ; point FSR0 to  bBCD[2]
          call      OneByte             ; BCD[0]
;
          bra       jLoop

jEnd:
          movwf     POSTDEC1,ACCESS     ; fixup stack
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
