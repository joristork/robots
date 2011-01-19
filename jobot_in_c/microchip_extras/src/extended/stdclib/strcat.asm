; $Id: strcat.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcat
;``The {\bf strcat} function appends a copy of the string pointed to
;by {\bf s2} (including the terminating null character) to the end
;of the string pointed to by {\bf s1}. The initial character of
;{\bf s2} overwrites the null character at the end of {\bf s1}. If
;copying takes place between objects that overlap, the behaviour is
;undefined.''
;Stack usage: 6 bytes. Re-entrant.
;@param s1 pointer to destination
;@param s2 pointer to source
;@return ``The {\bf strcat} function returns the value of {\bf s1}.''
;
; char *strcat (char *dest, const char *src);


STRING CODE
strcat   FUSTART
  global strcat

; Proceedure: Use FSR0 for 'dest' and FSR2 for 'src'.

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

  ; Copy source to destination
jLoop:
  ; Copy byte
          movff     INDF2, POSTINC0

  ; Test for '\0'
          tstfsz    POSTINC2,ACCESS
          bra       jLoop

  ; Restore stack frame for return: restore FSR2
          Stk2PopToReg FSR2L
          FUEND
          return

  end
