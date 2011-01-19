; $Id: strrchr.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strrchr
;``The {\bf strrchr} function locates the last occurrence of {\bf c} [...]
;in the string pointed to by {\bf s}. The terminating null character is 
;considered to be part of the string.''
; * The MPLAB-C18 version of the {\bf strrchr} function differs from the ANSI
;specified function in that {\bf c} is defined as an {\bf unsigned char}
;parameter rather than an {\bf int} parameter.
;Stack usage: 3 bytes. Re-entrant.
;@param s pointer to string to search
;@param c character to search for
;@return ``The {\bf strrchr} function returns a pointer to the character,
;or a null pointer if {\bf c} does not occur in the string.''
;
; char *strrchr (const char *s, unsigned char c);

STRING CODE
strrchr
  global strrchr

; Proceedure: Use FSR0 for 's'.


  ; Load FSR0 with the 's' pointer

          Stk2CpyToReg -2,FSR0L

  ; Preload NULL to PROD in case there is no match.
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS
jLoop:

  ; get 'c'
          movlw     -3
          movf      PLUSW1, W, ACCESS

  ; Compare with character in 's'
          subwf     INDF0, W, ACCESS
          bnz       jLoop1

  ; Match: save ponter in 's'

	  movff     FSR0L, PRODL
	  movff     FSR0H, PRODH

jLoop1:
  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop


  ; Done.  The last match is in PROD or, if no match, then PROD contains NULL.

          return
  end

