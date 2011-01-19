; $Id: memset.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memset
;``The {\bf memset} function copies the value of {\bf c} [...] into
;each of the first {\bf n} characters of the object pointed to by
;{\bf s}.''
; * The MPLAB-C18 version of the {\bf memset} function differs from the ANSI
;specified function in that {\bf c} is defined as an {\bf unsigned char}
;parameter rather than an {\bf int} parameter.
;Stack usage: 5 bytes. Re-entrant.
;@param s pointer to object
;@param c character to copy into object
;@param n number of bytes of object to copy {\bf c} into
;@return ``The {\bf memset} function returns the value of {\bf s}.''
;
; void *memset (void *s, unsigned char c, size_t n);


STRING CODE
memset
  global memset

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's'.


  ; Load FSR0 with the 's' pointer

          Stk2CpyToReg -2,FSR0L

  ; Make a copy for the return value
	  movff FSR0L, PRODL
    	  movff FSR0H, PRODH

jLoop:

  ; Test n for zero

          Stk2TestForZero -5,jEnd

  ; Decrement n 

          Stk2Dec   -5

  ; get 'c'
          movlw     -3
          movf      PLUSW1, W, ACCESS

  ; Store it in 's'
          movwf     POSTINC0, ACCESS
          bra       jLoop

jEnd

          return
  end
