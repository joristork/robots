; $Id: mcpyr2p.asm,v 1.2 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memcpyram2pgm
;The {\bf memcpyram2pgm} function performs a {\bf memcpy} where
;{\bf s1} points to program memory and {\bf s2} points to data
;memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in data memory
;@param n number of characters to copy

; rom void *memcpyram2pgm (rom void *s1, const void *s2, sizeram_t n);


STRING CODE
memcpyram2pgm   FUSTART
  global memcpyram2pgm


#ifdef __SMALL__
 messg "memcpyram2pgm - SMALL"


; Proceedure: Two byte pointers and two byte n.
;         Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 'src' and TBLPTR for 'dest'
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'dest' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Make a copy for the return value
	movff TBLPTRL, PRODL
	movff TBLPTRH, PRODH

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -4,FSR0L

jLoop:

  ; Test 2 byte n for zero

          Stk2TestForZero -6,jEnd

  ; Decrement n

          Stk2Dec   -6

          movff     POSTINC0,TABLAT
          tblwt     *+                  ; write & increment

          bra       jLoop

jEnd


          FUEND
          return

#else
#ifdef __LARGE__
 messg "memcpyram2pgm - LARGE"


; Proceedure: Three byte src pointer and two byte n.
;         Use offset from top of stack to test 'n' for zero and decrement
;         it. Use FSR0 for 'src' and TBLPTR for 'dest'
;         Assumes TBLPTRU = 0.

  ; Load TBLPTR with the 'dest' pointer

          Stk3CpyToReg -3,TBLPTRL

  ; Load FSR0 with the 'src' pointer

          Stk2CpyToReg -5,FSR0L

jLoop:

  ; Test 2 byte n for zero

          Stk2TestForZero -7,jEnd

  ; Decrement n

          Stk2Dec   -7

          movff     POSTINC0,TABLAT
          tblwt     *+                  ; write & increment

          bra       jLoop

jEnd

  ; Return value: Set FSR0 to 'dest'.
          StkSetPtrToOffset FSR0L,-3

          FUEND
          return
#else
 error "No Model Specified"
#endif
#endif
  end
