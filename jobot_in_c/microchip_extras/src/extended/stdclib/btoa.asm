; RCS Header $Id: btoa.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name btoa
;The {\bf btoa} function converts the {\bf signed char} value {\bf value} to 
;a radix 10 string representation, storing the resultant string into
;the location pointed to by {\bf s}.
; * The {\bf btoa} function is an MPLAB-Cxx extension to the ANSI required
;libraries and may not be present in other implementations.
;@param value value to convert
;@param s pointer to destination string object
;@return The {\bf btoa} function returns the value of {\bf s}.
;
;  char *btoa (signed char value, char *s);

STDLIB CODE
btoa     FUSTART
  global btoa
  extern btoBCD

; Proceedure: Use FSR2 for 's'. Call btoBCD to convert ASCII to BCD.
; Stack Offsets from top:
#define OffValue   -3  ; Value

  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 's' pointer
          Stk2CpyToReg -5,FSR2L

  ; Test for sign
          movlw     LOW(OffValue)
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      PLUSW1, F, ACCESS   ; sets N flag
          bnn       jTstSign1

  ; Value is negative.  Store minus sign and negate Value
          movlw     '-'
          movwf     POSTINC2,ACCESS
          Stk1Negate OffValue

jTstSign1:

  ; Convert number to BCD
  ; void btoBCD( unsigned char Value, unsigned char *pBCD )

  ; Allocate space for BCD[2} on stack
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTINC1, F, ACCESS           ; make space for BCD
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      POSTINC1, F, ACCESS           ; make space for BCD
          StkPushPtrToOffset -2                   ; ptr to BCD
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          Stk1CpyToReg OffValue-4,POSTINC1       ; push Value on stack
          call      btoBCD

  ; Set FSR0 == pBCD
          StkSetPtrToOffset FSR0L,-5

          call      TstLower            ; lower nibble
          bnz       jDo3
          call      TstUpper            ; upper nibble
          bnz       jDo2 
          bra       jDo1                ; must have at least one digit

jDo3:     call      ProcLower           ; lower nibble
jDo2:     call      ProcUpper           ; upper nibble
jDo1:     call      ProcLower           ; lower nibble

          clrf      POSTINC2,ACCESS     ; terminate string

  ; fixup stack
          StkAddStackPtr -5

  ; Restore FSR2 from stack. 
          Stk2PopToReg FSR2L

  ; Set FSR0 to point to *s
          StkSetPtrToOffset FSR0L,-3
	  
  ; FIXME - temporary solution for change in calling convention
  ; return value in PROD instead of *FSR0
	  movff     POSTINC0, PRODL
	  movff     INDF0, PRODH
	  FUEND 
          return

; Test for zero nibble
TstUpper  swapf     INDF0,W,ACCESS      ; upper nibble
          andlw     0x0F         
          return


; Test for zero nibble
TstLower  movf      INDF0,W,ACCESS      ; lower nibble
          andlw     0x0F  
          bnz       jTstLowZ1
          movf      POSTINC0,F,ACCESS   ; increment pointer
          bsf       STATUS,Z            ; indicate zero nibble   
jTstLowZ1 return


; Convert nibble to ASCII character
ProcUpper swapf     INDF0,W,ACCESS      ; upper nibble
          andlw     0x0F  
          addlw     0x30                ; make into ASCII
          movwf     POSTINC2,ACCESS     ; put it into string
          return

; Convert nibble to ASCII character
ProcLower movf      POSTINC0,W,ACCESS   ; lower nibble
          andlw     0x0F  
          addlw     0x30                ; make into ASCII
          movwf     POSTINC2,ACCESS     ; put it into string
          return

  end

