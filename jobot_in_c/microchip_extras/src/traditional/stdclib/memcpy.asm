; $Id: memcpy.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memcpy
;``The {\bf memcpy} funciton copies {\bf n} characters from the object
;pointed to by {\bf s2} into the object pointed to by {\bf s1}. If
;copying takes place between objects that overlap, the behaviour is
;undefined.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to destination
;@param s2 pointer to source
;@param n count of bytes to copy
;@return ``The {\bf memcpy} function returns the value of {\bf s1}.''
;
; void *memcpy (void *s1, const void *s2, size_t n);


STRING CODE
memcpy
  global memcpy

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 'dest' and FSR2 for 'src' 


  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 'src' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -4,FSR0L

  ; Make a copy for the return value
	  movff FSR0L, PRODL
	  movff FSR0H, PRODH


jLoop:

  ; Test n for zero

          Stk2TestForZero -8,jEnd

  ; Decrement n 

          Stk2Dec   -8

          movff     POSTINC2, POSTINC0
          bra       jLoop

jEnd

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L
          return
  end
