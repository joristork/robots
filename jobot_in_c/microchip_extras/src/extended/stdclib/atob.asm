; RCS Header $Id: atob.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name atob
;The {\bf atob} function converts the string pointed to by {\bf s} into a 
;{\bf signed char} integer representation. Characters are recognized in the 
;following order: An optional string of tabs and spaces, An optional sign, 
;A string of digits. The first unrecognized character ends the conversion. 
;Overflow results are undefined.
;The {\bf atob} function is an MPLAB-Cxx extension to the ANSI required
;libraries and may not be present in other implementations.
;@param s pointer to the string to convert
;@return The {\bf atob} function returns the converted value
;
; signed char atob (const char *s);

STDLIB CODE
atob FUSTART
  global atob

; Proceedure: Use FSR0 for 's'.
; Stack Offsets from top:
#define OffSign    -1  ; Sign Flag 
#define OffScratch -2  ; Scratch
;                  -4  ; Save FSR2 
#define OffResult  -5  ; Results to be return


  ; Load FSR0 with the 's' pointer
          Stk2PopToReg FSR0L

  ; Push zero onto stack for return value.  Put it here so we don't 
  ; have to move it later.
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS

  ; Save FSR2 on the stack. 
          Stk2PushFromReg FSR2L

  ; Load FSR2 with pointer to OffResult
          StkSetPtrToOffset FSR2L,-3

  ; Push zero onto stack for scratch
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS

  ; Push zero onto stack for sign flag
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          clrf      POSTINC1,ACCESS
  
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
          Stk1Inc    -1       ; record it in sign flag
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
jLoop:

  ; If the next character is not 0 ->9 then terminate
          jmpFleL   INDF0,ACCESS,'0'-1,jEndLoop
          jmpFgeL   INDF0,ACCESS,'9'+1,jEndLoop


  ; Multiply return value by 10
          Stk1ShiftLeft       OffResult
          Stk1CpyFromFSR2     OffScratch
          Stk1ShiftLeft       OffResult
          Stk1ShiftLeft       OffResult
          Stk1AddToFSR2       OffScratch


  ; Convert ASCII character to binary and add to Result
          movlw     '0'
          subwf     POSTINC0,W,ACCESS
          addwf     INDF2,F,ACCESS
          bra       jLoop

jEndLoop:
  ; Get Sign flag and fixup stack
          Stk1CpyToReg OffSign,WREG
          jmpWeqZ   jEnd

  ; Negative sign.  Negate result.
          Stk1NegateFSR2

jEnd:
  ; Adjust stack
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,F,ACCESS
          stkadj    -1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTDEC1,F,ACCESS

  ; Restore FSR2 from stack. 
          Stk2PopToReg FSR2L

  ; Move result to W
          Stk1CpyToReg -1,WREG
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTINC1,F,ACCESS
          FUEND
          return
  end

