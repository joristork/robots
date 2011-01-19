; $Id: strchr.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strchr
;``The {\bf strchr} function locates the first occurence of {\bf c} [...]
;in the string pointed to by {\bf s}. The terminating null character is
;considered to be part of the string.''
; * The MPLAB-C18 version of the {\bf strchr} function differs from the ANSI
;specified function in that {\bf c} is defined as an {\bf unsigned char}
;parameter rather than an {\bf int} parameter.
;Stack usage: 3 bytes. Re-entrant.
;@param s pointer to string to search
;@param c character to search for
;@return ``The {\bf strchr} function returns a pointer to the located character,
;or a null pointer if the character does not occur in the string.''
;
; char *strchr (const char *s, unsigned char c);

STRING CODE
strchr   FUSTART
  global strchr

; Proceedure: Use FSR0 for 's'.


  ; Load FSR0 with the 's' pointer

          Stk2CpyToReg -2,FSR0L

jLoop:

  ; get 'c'
          movlw     -3
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      PLUSW1, W, ACCESS

  ; Compare with character in 's'
          subwf     INDF0, W, ACCESS
          bz        jMatch               ; match

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop


  ; No match - Load NULL to 's'
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS
          bra       jEnd

jMatch
  ; Found match - Load PROD with current value of FSR0 which points to
  ; match character.  
  
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH
  
jEnd

          FUEND
          return
  end

