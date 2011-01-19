; $Id: msetpgm.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memsetpgm
;The {\bf memsetpgm} function performs a {\bf memset} where {\bf s}
;points to program memory.
;@param s pointer to object in program memory
;@param c character to copy into object
;@param n number of characters of object to copy {\bf c} into
;
;rom void *memsetpgm (rom void *s, unsigned char c, sizerom_t n);
  
  extern __AARGB2
STRING CODE
memsetpgm
  global memsetpgm

#ifdef __SMALL__
 messg "memsetpgm - SMALL"

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use TBLPTR for 's'.


  ; Load TBLPTRU with the 's' pointer

          Stk2CpyToReg -2,TBLPTRL

  ; Make a copy for the return value
	  movff TBLPTRL, PRODL
  	  movff TBLPTRH, PRODH

jLoop:

  ; Test n for zero

          Stk3TestForZero -6,jEnd

  ; Decrement n

          Stk3Dec   -6

  ; get 'c'
          movlw     -3
          movf      PLUSW1, W, ACCESS

  ; Store it in 's'
          movwf     TABLAT, ACCESS
          tblwt     *+                  ; write and increment
          bra       jLoop

jEnd

          return

#else
#ifdef __LARGE__
 messg "memsetpgm - LARGE"

; Proceedure: Use offset from top of stack to test 'n' for zero and decrement
;         it. Use TBLPTR for 's'.


  ; Load TBLPTRU with the 's' pointer

          Stk3CpyToReg -3,TBLPTRL

jLoop:

  ; Test n for zero

          Stk3TestForZero -7,jEnd

  ; Decrement n

          Stk3Dec   -7

  ; get 'c'
          movlw     -4
          movf      PLUSW1, W, ACCESS

  ; Store it in 's'
          movwf     TABLAT, ACCESS
          tblwt     *+                  ; write and increment
          bra       jLoop

jEnd

  ; Set AARGB2 to point to 's' for return
          Stk3CpyToReg -3,__AARGB2
          return


#else
 error "No Model Specified"
#endif
#endif
  end

