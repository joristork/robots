; $Id: strlen.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strlen
;``The {\bf strlen} function computes the length of the string pointed
;to by {\bf s}.''
;Stack usage: 2 bytes. Re-entrant.
;@param s pointer to the string
;@return ``The {\bf strlen} function returns the number of characters
;that precede the terminating null character.''
;
; size_t strlen (const char *s);


STRING CODE
strlen
  global strlen

; Proceedure: Use FSR0 for 's' and use PROD for 'n'.

  ; Load FSR0 by copying 's' off stack into FSR0.

          Stk2CpyToReg -2,FSR0L

  ; Clear PROD
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS

  ; Scan for '\0' while counting.
jLoop:

  ; Test for '\0'
          movf      POSTINC0,F,ACCESS   ; set Z flag
          bz        jEnd

  ; Count it 
	  infsnz    PRODL, F, ACCESS
	  incf      PRODH, F, ACCESS

          bra       jLoop

jEnd

          return
  end
