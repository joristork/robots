; $Id: strncmp.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strncmp
;``The {\bf strncmp} function compares not more than {\bf n} characters
;(characters that follow a null character are not compared) from the 
;array pointed to by {\bf s1} to the array pointed to by {\bf s2}.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to string one
;@param s2 pointer to string two
;@param n count of characters to compare
;@return ``The {\bf strncmp} function returns an signed char greater than,
;equal to, or less than zero, accordingly as the possibly null-terminated
;array pointed to by {\bf s1} is greater than, equal to, or less than the
;possibly null-terminated array pointed to by {\bf s2}.''
;
; signed char strncmp (const char *s1, const char *s2, size_t n);


STRING CODE
strncmp  FUSTART
  global strncmp

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's1' and FSR2 for 's2' 


  ; Save FSR2 on the stack. 

          Stk2PushFromReg FSR2L

  ; Load FSR2 with the 's2' pointer

          Stk2CpyToReg -6,FSR2L

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:

  ; Test n for zero

          Stk2TestForZero -8,jEnd        ; WREG = 0 if branch

  ; Decrement n 

          Stk2Dec   -8

          movf      POSTINC2, W, ACCESS
          subwf     INDF0, W, ACCESS
          bnz       jEnd                ; unequal - diff in WREG 

  ; Here only if bytes are equal. Need to test for '\0' on only one byte.
          tstfsz    POSTINC0,ACCESS
          bra       jLoop


  ; Falls through with WREG = 0

jEnd

  ; Restore FSR2.  Trashes WREG.
          movwf PRODL,ACCESS
          Stk2PopToReg FSR2L
          movf PRODL,W,ACCESS
          FUEND
          return
  end
