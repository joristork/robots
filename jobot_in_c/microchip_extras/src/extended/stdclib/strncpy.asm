; $Id: strncpy.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncpy
;``The {\bf strncpy} function copies not more than {\bf n} characters
;(characters that follow a null character are not copied) from the
;array pointed to by {\bf s2} to the array pointed to by {\bf s1}.
;If a null character is encountered copied ceases after it has been copied.
;If {\bf n} characters are copied and no null character is found then
;{\bf s1} will not be terminated.
;If copying takes place between objects that overlap, the behaviour
;is undefined.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to destination
;@param s2 pointer to source
;@param n count of maximum characters to copy
;@return ``The {\bf strncpy} function returns the value of {\bf s1}.''
;
; char *strncpy (char *dest, const char *src, size_t n);


STRING CODE
strncpy  FUSTART
  global strncpy

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 'dest' and FSR2 for 'src' 


  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 'src' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 'dest' pointer

          Stk2CpyToReg -4,FSR0L

  ; make a copy for the return value

          movff FSR0L, PRODL
	  movff FSR0H, PRODH

jLoop:

  ; Test n for zero

          Stk2TestForZero -8,jEnd

  ; Decrement n 

          Stk2Dec   -8

  ; Copy byte
          movff     POSTINC2, INDF0

  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jLoop

  ; Move pointer back to terminating null in order to pad with nulls
          movf      POSTDEC2, F, ACCESS
          bra       jLoop

jEnd

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L
          FUEND
          return

  end
