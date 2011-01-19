; $Id: strncat.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncat
;``The {\bf strncat} function appends not more than {\bf n} characters
;(a null character and characters that follow it are not appended)
;from the array pointed to by {\bf s2} to the end of the string
;pointed to by {\bf s1}. The initial character of {\bf s2} overwrites
;the null character at the end of {\bf s1}. A terminating null 
;character is always appended to the result. If copying takes place
;between objects that overlap, the behaviour is undefined.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to destination
;@param s2 pointer to source
;@param n count of maximum characters to copy
;@return ``The {\bf strncat} function returns the value of {\bf s1}.''
;
; char *strncat (char *dest, const char *src, size_t n);


STRING CODE
strncat  FUSTART
  global strncat

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

  ; Find end of 'dest'
jNullLoop:
  ; Test for '\0'
          tstfsz    POSTINC0,ACCESS
          bra       jNullLoop

  ; Backup over '\0'
          movf      POSTDEC0,F,ACCESS

jLoop:

  ; Test n for zero

          Stk2TestForZero -8,jTerm

  ; Decrement n 

          Stk2Dec   -8

  ; Copy byte
          movff     INDF2, POSTINC0

  ; Test for '\0'
          tstfsz    POSTINC2,ACCESS
          bra       jLoop
          bra       jEnd


jTerm
  ; Be certain there is a terminating null
          clrf      INDF0,ACCESS

jEnd

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L
          FUEND
          return

  end
