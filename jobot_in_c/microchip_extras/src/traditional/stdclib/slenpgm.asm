; $Id: slenpgm.asm,v 1.6 2009/05/14 08:27:21 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strlenpgm
;``The {\bf strlenpgm} function computes the length of the string pointed
;to by {\bf s}.''
;@param s pointer to the string
;@return ``The {\bf strlenpgm} function returns the number of characters
;that precede the terminating null character.''
;
; sizerom_t strlenpgm (const rom char *s);


  extern __AARGB2

STRING CODE
strlenpgm
  global strlenpgm

#ifdef __SMALL__
 messg "strlenpgm - SMALL"

; Procedure: Use TBLPTR for 's' and create 'n' on stack.  Return 'n' in
; __AARGB0:__AARGB1:__AARGB2.

  ; Load TBLPTR by POPing 's' off stack.

          Stk2PopToReg TBLPTRL
          clrf      POSTINC1,ACCESS    ; provide extra byte below stack

  ; Push 3 bytes of 0 onto stack. This is initial value of 'n'.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Scan for '\0' while counting.
jLoop:

  ; Test for '\0'
          tblrd     *+                  ; read & increment
          movf      TABLAT, F, ACCESS
          bz        jEnd

  ; Count it

          Stk3Inc   -3
          bra       jLoop

jEnd

          Stk3CpyToReg -3, __AARGB2

  ; adjust the stack pointer (FSR1) back to the position where it was when it entered the function
          clrf      POSTDEC1,ACCESS
          clrf      POSTDEC1,ACCESS

          return
#else
#ifdef __LARGE__
 messg "strlenpgm - LARGE"

; Procedure: Use TBLPTR for 's' and create 'n' on stack.  Return 'n' in
; __AARGB0:__AARGB1:__AARGB2.

  ; Load TBLPTR by POPing 's' off stack.

          Stk3PopToReg TBLPTRL

  ; Push 3 bytes of 0 onto stack. This is initial value of 'n'.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Scan for '\0' while counting.
jLoop:

  ; Test for '\0'
          tblrd     *+                  ; read & increment
          movf      TABLAT, F, ACCESS
          bz        jEnd

  ; Count it

          Stk3Inc   -3
          bra       jLoop

jEnd

          Stk3CpyToReg -3, __AARGB2
          return

#else
 error "No Model Specified"
#endif
#endif
  end

