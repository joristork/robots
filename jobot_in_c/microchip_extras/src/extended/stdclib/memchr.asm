; $Id: memchr.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memchr
;``The {\bf memchr} function locates the first occurrence of {\bf c} [...]
;in the initial {\bf n} characters [...] of the object pointed to by
;{\bf s}.
; * The MPLAB-C18 version of the {\bf memchr} function differs from the ANSI
;specified function in that {\bf c} is defined as an {\bf unsigned char}
;parameter rather than an {\bf int} parameter.''
;Stack usage: 5 bytes. Re-entrant.
;@param s pointer to object to search
;@param c character to search for
;@param n maximum number of chararacters to search
;@return ``The {\bf memchr} function returns a pointer to the located character,
;or a null pointer if the character does not occur in the object.''
;
; void *memchr (const void *s, unsigned char c, size_t n);


STRING CODE
memchr   FUSTART
  global memchr

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's'.


  ; Load FSR0 with the 's' pointer

          Stk2CpyToReg -2,FSR0L

jLoop:

  ; Test n for zero

          Stk2TestForZero -5,jNoMatch

  ; Decrement n 

          Stk2Dec   -5

  ; get 'c'
          movlw     -3
          stkadj    1                    ; added STKADJ PSEUDO-INSTRUCTION for stack analysis
          movf      PLUSW1, W, ACCESS

  ; Compare with character in 's'
          subwf     POSTINC0, W, ACCESS
          bnz       jLoop               ; no match

  ; Found match - Load PROD with current value of FSR0-1 which points to
  ; match character.  
  
          movf      POSTDEC0, F, ACCESS
	  movff     FSR0L, PRODL
	  movff     FSR0H, PRODH
          bra       jEnd

jNoMatch
  ; Load NULL to PROD
          clrf      PRODL,ACCESS
          clrf      PRODH,ACCESS
  
jEnd

          FUEND
          return
  end
