; RCS Header $Id: atolfix.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"


;The {\bf atolfix} function converts a string of the form:
;     whitespace +/- digits DecimalPt digits
;into a long integer, value, and the power of ten, Decpt, such that
;value * 10 ^ Decpt represents the number contained in the string.
;The function returns a pointer to 1st character past the end of the
;digits.
;
; char *atolfix( const char *s, char *decpt, long *value )


STDLIB CODE
atolfix  FUSTART
  global atolfix

; Proceedure: Use FSR0 for 's'.
; Stack Offsets from top after automatics allocated:
#define OffFlags   -1  ; Flags:
#define FlgBitSign  0  ; Minus sign found
#define FlgBitDecPt 1  ; Decimal pt found 

#define OffDecCnt  -2  ; Decimal point count
#define OffLongA   -6  ; Long scratch A
#define OffLongB   -d'10' ; Long scratch B

#define OffSavFSR2 -d'12' ; Save FSR2
#define OffStrPtr  -d'14' ; Pointer to string returned by function (& original s)
#define Offdecpt   -d'16' ; Location of decpt pointer
#define Offvalue   -d'18' ; Location of value pointer

  ; Save FSR2 on the stack. 
          Stk2PushFromReg FSR2L

  ; Allocate on stack 1 byte for decpt, 4 bytes for value, 1 byte for flags
  ; Push zeros onto stack
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS

  ; Load FSR0 with the 's' pointer
          Stk2CpyToReg  OffStrPtr, FSR0L

jSkipWS:
  ; Skip over white space, i.e. spaces and tabs

  ; Compare with space
          movlw     ' '
          subwf     INDF0, W, ACCESS
          bz        jSkipWS1            ; match

  ; Compare with tab
          movlw     '\t'
          subwf     INDF0, W, ACCESS
          bz        jSkipWS1            ; match

  ; Test for '\0'
          tstfsz    INDF0,ACCESS
          bra       jTstSign
          bra       jEnd                ; found terminator

jSkipWS1:
  ; next character
          movf      POSTINC0, W, ACCESS ; increment pointer
          bra       jSkipWS

jTstSign:
  ; Test for sign
          movlw     '-'
          subwf     INDF0, W, ACCESS
          bnz       jTstSign1

  ; Found minus sign.  
          movlw     OffFlags
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          bsf       PLUSW1,FlgBitSign,ACCESS  ; record it in Flags
          bra       jTstSign2

jTstSign1:
          movlw     '+'
          subwf     INDF0, W, ACCESS
          bnz       jTstSign3

  ; Skip plus sign

jTstSign2:
  ; Skip character
          movf      POSTINC0, W, ACCESS

jTstSign3:

  ; Load FSR2 with pointer to LongA
          StkSetPtrToOffset FSR2L,OffLongA

jLoop:
  ; If the next character is a decimal point begin counting digits
          jmpFneL   INDF0,ACCESS,'.',jLoop1

  ; Decimal point: Set DecPt found flag and skip char
          movlw     OffFlags
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          bsf       PLUSW1,FlgBitDecPt,ACCESS     ; record it in Flags
          movf      POSTINC0,F,ACCESS             ; increment string pointer
          bra       jLoop

jLoop1:
  ; If the next character is not 0 ->9 then terminate
          jmpFleL   INDF0,ACCESS,'0'-1,jEndLoop
          jmpFgeL   INDF0,ACCESS,'9'+1,jEndLoop


  ; Multiply LongA value by 10 and move it to LongB
          Stk4ShiftLeft       OffLongA
          Stk4CpyFromFSR2     OffLongB
          Stk4ShiftLeft       OffLongA
          Stk4ShiftLeft       OffLongA
          Stk4AddToFSR2       OffLongB


  ; Convert ASCII character to binary and add to Result
          movlw     '0'
          subwf     POSTINC0,W,ACCESS
          addwf     POSTINC2,F,ACCESS
          movlw     0
          addwfc    POSTINC2,F,ACCESS
          addwfc    POSTINC2,F,ACCESS
          addwfc    POSTDEC2,F,ACCESS
          movf      POSTDEC2,F,ACCESS
          movf      POSTDEC2,F,ACCESS

          movlw     OffFlags
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          jmpClr    PLUSW1,FlgBitDecPt,ACCESS,jLoop

  ; Count digits after decimal point by decrementing decpt
          movlw     OffDecCnt
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          decf      PLUSW1,F,ACCESS
          bra       jLoop

jEndLoop:
  ; Get Sign flag 
          movlw     OffFlags
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          jmpClr    PLUSW1,FlgBitSign,ACCESS,jEnd

  ; Negative sign.  Negate result.
          Stk4Negate OffLongA

jEnd:
  ; Copy results to pointer locations passed as arguments

  ; Current value of string pointer is in FSR0
          Stk2CpyFromReg  FSR0L,OffStrPtr

  ; get pointer to "value" arg
          Stk2CpyToReg  Offvalue,FSR2L

  ; copy LongA to "value" location
          Stk4CpyToFSR2  OffLongA

  ; get pointer to "decpt" arg
          Stk2CpyToReg  Offdecpt,FSR2L

  ; copy DecCnt to "decpt" location
          Stk1CpyToFSR2  OffDecCnt

  ; Set FSR0 equal to location of returned value
          StkSetPtrToOffset FSR0L,OffStrPtr

  ; Free automatics
          StkAddStackPtr -d'10'

  ; Restore FSR2 from stack. 
          Stk2PopToReg FSR2L
	  
  ; FIXME - temporary solution for change in calling convention
  ; return value in PRODL instead of *FSR0
	  movff     POSTINC0, PRODL
	  movff     INDF0, PRODH
	  FUEND 
          return
  end


