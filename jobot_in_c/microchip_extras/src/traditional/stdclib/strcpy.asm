; $Id: strcpy.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcpy
;The {\bf strcpy} function copies the string pointed to by {\bf s2}
;(including the terminating null character) into the array pointed to
;by {\bf s1}. If copying takes place between objects that overlap,
;the behaviour is undefined.''
;Stack usage: 6 bytes. Re-entrant.
;@param s1 pointer to destination
;@param s2 pointer to source
;@return ``The {\bf strcpy} function returns the value of {\bf s1}.''
;
; char *strcpy (char *dest, const char *src);


STRING CODE
strcpy
  global strcpy

; Proceedure: Use FSR0 for 'dest' and FSR2 for 'src'.

  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 'src' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -4,FSR0L

  ; make a copy for the return value

	  movff FSR0L, PRODL
	  movff FSR0H, PRODH


  ; Copy source to destination
jLoop:
          movff     POSTINC2, INDF0

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L
          return

  end
