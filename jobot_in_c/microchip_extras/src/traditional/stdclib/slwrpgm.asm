; $Id: slwrpgm.asm,v 1.1 2003/12/09 22:53:20 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name strlwrpgm
;The {\bf strlwrpgm} function performs a {\bf strlwr} where {\bf s}
;points to program memory.
;@param s pointer to object in program memory
;
;rom char *strlwrpgm (rom char *s);

STRING CODE
strlwrpgm
  global strlwrpgm

#ifdef __SMALL__
 messg "strlwrpgm - SMALL"

; Proceedure: Use TBLPTR for 's'.

          Stk2CpyToReg -2,TBLPTRL

; make a copy for the return value
	  movff TBLPTRL, PRODL
	  movff TBLPTRH, PRODH

  ; Scan for '\0' while converting case
jLoop:
          tblrd     *
          movlw     'A'
          subwf     TABLAT,W,ACCESS
          bn        jSkip

          movlw     'Z'+1
          subwf     TABLAT,W,ACCESS
          bnn       jSkip

  ; Convert to lowercase

          movlw     0x20
          iorwf     TABLAT,F,ACCESS
          tblwt     *

jSkip
          tblrd     *+                  ; increment

  ; Test for '\0'
          movf      TABLAT,F,ACCESS   ; set Z flag
          bnz       jLoop

jEnd

          return

#else
#ifdef __LARGE__
 messg "strlwrpgm - LARGE"

; Proceedure: Use TBLPTR for 's'.

          Stk3CpyToReg -3,TBLPTRL

  ; Scan for '\0' while converting case
jLoop:
          tblrd     *
          movlw     'A'
          subwf     TABLAT,W,ACCESS
          bn        jSkip

          movlw     'Z'+1
          subwf     TABLAT,W,ACCESS
          bnn       jSkip

  ; Convert to lowercase

          movlw     0x20
          iorwf     TABLAT,F,ACCESS
          tblwt     *

jSkip
          tblrd     *+                  ; increment

  ; Test for '\0'
          movf      TABLAT,F,ACCESS   ; set Z flag
          bnz       jLoop

jEnd

  ; Set FSR0 = 's'.

          StkSetPtrToOffset FSR0L,-3
          return

#else
 error "No Model Specified"
#endif
#endif
  end


