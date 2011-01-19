; $Id: strcmp.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcmp
;``The {\bf strcmp} function compares the string pointed to by {\bf s1} to
;the string pointed to by {\bf s2}.''
;Stack usage: 6 bytes. Re-entrant.
;@param s1 pointer to string one
;@param s2 pointer to string two
;@return ``The {\bf strcmp} function returns a signed char greater than, equal
;to, or less than zero, accordingly as the string pointed to by {\bf s1} is
;greater than, equal to, or less than the string pointed to by {\bf s2}.''
;
; signed char strcmp (const char *s1, const char *s2);


STRING CODE
strcmp
  global strcmp

; Proceedure: Use FSR0 for 's1' and FSR2 for 's2'.

  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 's2' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -4,FSR0L

  ; Compare s1 to s2
jLoop:
          movf      POSTINC2, W, ACCESS
          subwf     INDF0, W, ACCESS
          bnz       jEnd                ; unequal - diff in WREG 

  ; Here only if bytes are equal. Need to test for '\0' on only one byte.
          tstfsz    POSTINC0,ACCESS
          bra       jLoop


  ; Falls through with WREG = 0

jEnd

  ; Restore FSR2.  trashes WREG.
          movwf PRODL,ACCESS
          Stk2PopToReg FSR2L
          movf PRODL,W,ACCESS
          return
  end
