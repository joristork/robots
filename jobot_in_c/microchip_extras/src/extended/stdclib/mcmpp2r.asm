; $Id: mcmpp2r.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strcpypgm2ram
;The {\bf strcpypgm2ram} function performs a {\bf strcpy} where
;{\bf s1} points to data memory and {\bf s2} points to program
;memory.
;@param s1 pointer to destination in data memory
;@param s2 pointer to source in program memory
;
; signed char memcmppgm2ram (const void *s1, const rom void *s2, sizeram_t n);


STRING CODE
memcmppgm2ram   FUSTART
  global memcmppgm2ram


#ifdef __SMALL__
 messg "memcmppgm2ram - SMALL"

; Proceedure: Two byte pointers and two byte n.
;         Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's1' and TBLPTR for 's2' 
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 's2' pointer

          Stk2CpyToReg -4,TBLPTRL

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -2,FSR0L

jLoop:

  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n 

          Stk2Dec    -6

          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS

          subwf     POSTINC0, W, ACCESS
          bz        jLoop

  ; Found unequal byte - difference in WREG which will be put in PRODL

jEnd

	movwf PRODL, ACCESS

          FUEND
          return

#else
#ifdef __LARGE__
 messg "memcmppgm2ram - LARGE"

; Proceedure: Three byte rom pointer.
;         Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 's1' and TBLPTR for 's2' 

; signed char memcmppgm2ram (const void *s1, const rom void *s2, sizeram_t n);

  ; Load TBLPTR with the 's2' pointer

          Stk3CpyToReg -5,TBLPTRL

  ; Load FSR0 with the 's1' pointer

          Stk2CpyToReg -2,FSR0L

jLoop:

  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n 

          Stk2Dec    -7

          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS

          subwf     POSTINC0, W, ACCESS
          bz        jLoop

  ; Found unequal byte - difference in WREG

jEnd

          FUEND
          return


#else
 error "No Model Specified"
#endif
#endif
  end

