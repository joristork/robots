; $Id: strlwr.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strlwr
;The {\bf strlwr} function converts each upper case character in the
;string pointed to by {\bf s} to the corresponding lower case character.
;Stack usage: 2 bytes. Re-entrant.
;@param s pointer to string
;@return The {\bf strlwr} function returns the value of {\bf s}.
;
; char *strlwr (char *s);

STRING CODE
strlwr
  global strlwr

; Proceedure: Use FSR0 for 's'.

  ; Load FSR0 with the 's' pointer

          Stk2CpyToReg -2,FSR0L

  ; Make a copy for the return value

	  movff FSR0L, PRODL
	  movff FSR0H, PRODH

  ; Scan for '\0' while converting case
jLoop:
          movlw     'A'
          subwf     INDF0,W,ACCESS
          bn        jSkip

          movlw     'Z'+1
          subwf     INDF0,W,ACCESS
          bnn       jSkip

  ; Convert to lowercase

          movlw     0x20
          iorwf     INDF0,F,ACCESS

jSkip

  ; Test for '\0'
          movf      POSTINC0,F,ACCESS   ; set Z flag
          bnz       jLoop

jEnd

          return
  end
