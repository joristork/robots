; RCS Header $Id: atoi.asm,v 1.1 2003/12/09 22:53:19 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; The {\bf atoi} function converts the string pointed to by {\bf s} into a 
; {\bf int} integer representation. Characters are recognized in the 
; following order: An optional string of tabs and spaces, An optional sign, 
; A string of digits. The first unrecognized character ends the conversion. 
; Overflow results are undefined.
; /@param s pointer to the string to convert
; @return The {\bf atob} function returns the converted value as an int
;
; int atoi (const char *s);

STDLIB CODE
atoi
  global atoi

; Proceedure: Use FSR0 for 's'.
; Stack Offsets from top:
#define OffSign    -1  ; Sign Flag 
#define OffScratch -3  ; Scratch
;                  -5  ; Save FSR2 
#define OffResult  -7  ; Results to be return


  ; Load FSR0 with the 's' pointer
          Stk2PopToReg FSR0L

  ; Push zero onto stack for return value.  Put it here so we don't 
  ; have to move it later.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Save FSR2 on the stack. 
          Stk2PushFromReg FSR2L

  ; Load FSR2 with pointer to OffResult
          StkSetPtrToOffset FSR2L,-4

  ; Push zero onto stack for scratch
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Push zero onto stack for sign flag
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


  ; Multiply Result value by 10 and move it to Scratch
          Stk2ShiftLeft       OffResult
          Stk2CpyFromFSR2     OffScratch
          Stk2ShiftLeft       OffResult
          Stk2ShiftLeft       OffResult
          Stk2AddToFSR2       OffScratch


  ; Convert ASCII character to binary and add to Result
          movlw     '0'
          subwf     POSTINC0,W,ACCESS
          addwf     POSTINC2,F,ACCESS
          movlw     0
          addwfc    POSTDEC2,F,ACCESS
          bra       jLoop

jEndLoop:
  ; Get Sign flag and fixup stack
          Stk1CpyToReg OffSign,WREG
          jmpWeqZ   jEnd

  ; Negative sign.  Negate result.
          Stk2Negate OffResult

jEnd:
  ; Adjust stack
          movf      POSTDEC1,F,ACCESS
          movf      POSTDEC1,F,ACCESS
          movf      POSTDEC1,F,ACCESS

  ; Restore FSR2 from stack. 
          Stk2PopToReg FSR2L

  ; Set FSR0 to point to results
          movff     FSR1L,FSR0L
          movff     FSR1H,FSR0H
          movf      POSTDEC0,F,ACCESS
          movf      POSTDEC0,F,ACCESS
	  
  ; FIXME - temporary solution for change in calling convention
  ; return value in PRODL instead of *FSR0
	  movff     POSTINC0, PRODL
	  movff     INDF0, PRODH
	 
          return
  end

