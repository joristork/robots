; $Id: sstrpp.asm,v 1.2 2008/04/29 07:37:04 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strstrpgm
;``The {\bf strstrpgm} function locates the first occurrence in the string
;pointed to by {\bf s1} of the sequence of characters (excluding the
;null terminator) in the string pointed to by {\bf s2}.''
;Stack usage: 8 bytes. Re-entrant.
;@param s1 pointer to the string to search
;@param s2 pointer to sequence to search for
;@return ``The {\bf strstrpgm} function returns a pointer to the located
;string, or a null pointer if the string is not found. If {\bf s2}
;points to a string with zero length, the function returns {\bf s1}.''
;
; rom char *strstrpgm (const rom char *s1, const rom char *s2);

    extern __AARGB2
STRING CODE
strstrpgm
  global strstrpgm

#ifdef __SMALL__
 messg "strstrpgm - SMALL"
ps1  equ -7
ps2  equ -9
Ptr1 equ -5
Ptr2 equ -3
chr  equ -1

; Proceedure: Allocate on stack temporary pointers for Ptr1 and Ptr2.


  ; Allocate space for scratch pointer for s1 (Ptr1) on stack. (-5)
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Allocate space for scratch pointer for s2 (Ptr2) on stack. (-3)
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Add scratch (chr) to hold char from s1 and set FSR0 to point to it (-1)
          clrf      POSTINC1,ACCESS
          StkSetPtrToOffset FSR0L,-1     ; set FSR0 to point to scratch byte

jOuterLoop:

  ; Load TBLPTR with the 's1' pointer
          Stk2CpyToReg ps1,TBLPTRL

  ; Save TBLPTR to the scratch pointer pointer Ptr1
          Stk2CpyFromReg TBLPTRL,Ptr1

  ; Load TABPTR with the 's2' pointer
          Stk2CpyToReg ps2,TBLPTRL

  ; Save TBLPTR to the scratch pointer pointer Ptr2
          Stk2CpyFromReg TBLPTRL,Ptr2

jInnerLoop:
  ; Load TBLPTR with the Ptr1 pointer
          Stk2CpyToReg Ptr1,TBLPTRL

  ; Save char from Ptr1 to chr
          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF0

  ; Save incremented TBLPTR to the scratch pointer pointer Ptr1
          Stk2CpyFromReg TBLPTRL,Ptr1

  ; Load TBLPTR with the Ptr2 pointer
          Stk2CpyToReg Ptr2,TBLPTRL

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jStrMatch           ; found end of 's2' without mismatch

  ; Compare char to chr
          subwf     INDF0, W, ACCESS
          bnz       jNoMatch
  ; Match

  ; Save incremented TBLPTR to the scratch pointer pointer Ptr2
          Stk2CpyFromReg TBLPTRL,Ptr2

  ; Since Chars matched. and s2 char != 0 => s1 char also != 0
          bra       jInnerLoop

jNoMatch
  ; Have we reached the end of s1 without a match
          movf      INDF0, F, ACCESS
          bz        jNull               ; return a Null

  ; Increment s1
          Stk2Inc   ps1
          bra       jOuterLoop

jNull:
  ; copy Null to s1
          StkSetPtrToOffset FSR0L,ps1
          clrf      POSTINC0,ACCESS
          clrf      POSTINC0,ACCESS

jStrMatch
  ; Found match.  s1 contains pointer to be returned.
          StkAddStackPtr -5             ; Reset stack.

          Stk2CpyToReg -2,PRODL

          return

#else
#ifdef __LARGE__
 messg "strstrpgm - LARGE"
ps1  equ -10
ps2  equ -13
Ptr1 equ -7
Ptr2 equ -4
chr  equ -1

; Proceedure: Use FSR0 as 's1' pointer and FSR2 as 'SubStr' pointer.
;         Allocate on stack temporary pointer to hold location of beginning
;         of match.


  ; Allocate space for scratch pointer for s1 (Ptr1) on stack.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Allocate space for scratch pointer for s2 (Ptr2) on stack.
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS
          clrf      POSTINC1,ACCESS

  ; Add scratch (chr) to hold char from s1 and set FSR0 to point to it (-1)
          clrf      POSTINC1,ACCESS
          StkSetPtrToOffset FSR0L,-1     ; set FSR0 to point to scratch byte

jOuterLoop:

  ; Load TBLPTR with the 's1' pointer
          Stk3CpyToReg ps1,TBLPTRL

  ; Save TBLPTR to the scratch pointer pointer Ptr1
          Stk3CpyFromReg TBLPTRL,Ptr1

  ; Load TABPTR with the 's2' pointer
          Stk3CpyToReg ps2,TBLPTRL

  ; Save TBLPTR to the scratch pointer pointer Ptr2
          Stk3CpyFromReg TBLPTRL,Ptr2

jInnerLoop:
  ; Load TBLPTR with the Ptr1 pointer
          Stk3CpyToReg Ptr1,TBLPTRL

  ; Save char from Ptr1 to chr
          tblrd     *+                  ; read & increment
          movff     TABLAT, INDF0

  ; Save incremented TBLPTR to the scratch pointer pointer Ptr1
          Stk3CpyFromReg TBLPTRL,Ptr1

  ; Load TBLPTR with the Ptr2 pointer
          Stk3CpyToReg Ptr2,TBLPTRL

  ; Test for '\0' in 's2'
          tblrd     *+                  ; read & increment
          movf      TABLAT, W, ACCESS
          bz        jStrMatch           ; found end of 's2' without mismatch

  ; Compare char to chr
          subwf     INDF0, W, ACCESS
          bnz       jNoMatch
  ; Match

  ; Save incremented TBLPTR to the scratch pointer pointer Ptr2
          Stk3CpyFromReg TBLPTRL,Ptr2

  ; Since Chars matched. and s2 char != 0 => s1 char also != 0
          bra       jInnerLoop

jNoMatch
  ; Have we reached the end of s1 without a match
          movf      INDF0, F, ACCESS
          bz        jNull               ; return a Null

  ; Increment s1
          Stk3Inc   ps1
          bra       jOuterLoop

jNull:
  ; copy Null to s1
          StkSetPtrToOffset FSR0L,ps1
          clrf      POSTINC0,ACCESS
          clrf      POSTINC0,ACCESS

jStrMatch
  ; Found match.  s1 contains pointer to be returned.
          StkAddStackPtr -7             ; Reset stack.

  ; Set AARGB2 to s1
           Stk3CpyToReg -3,__AARGB2
          return


#else
 error "No Model Specified"
#endif
#endif
  end

