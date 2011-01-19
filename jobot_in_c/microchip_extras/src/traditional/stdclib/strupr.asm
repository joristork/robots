; $Id: strupr.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strupr
;The {\bf strupr} function converts each lower case character in the
;string pointed to by {\bf s} to the corresponding upper case character.
;Stack usage: 2 bytes. Re-entrant.
;@param s pointer to string
;@return The {\bf strupr} function returns the value of {\bf s}.
;
; char *strupr (char *s);

STRING CODE
strupr
  global strupr

; Proceedure: Use FSR0 for 's'.

  ; Load FSR0 with the 's' pointer

          Stk2CpyToReg -2,FSR0L
	  
  ; Load return value with pointer to string
          movff     FSR0L, PRODL
	  movff     FSR0H, PRODH
	  
  ; Scan for '\0' while converting case
jLoop:
          movlw     'a'
          subwf     INDF0,W,ACCESS
          bn        jSkip

          movlw     'z'+1
          subwf     INDF0,W,ACCESS
          bnn       jSkip

  ; Convert to uppercase

          movlw     0xDF
          andwf     INDF0,F,ACCESS

jSkip

  ; Test for '\0'
          movf      POSTINC0,F,ACCESS   ; set Z flag
          bnz       jLoop

jEnd
          return

  end
