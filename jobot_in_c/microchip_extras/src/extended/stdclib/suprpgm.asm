; $Id: suprpgm.asm,v 1.3 2009/02/17 08:16:03 guptan Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name struprpgm
;The {\bf struprpgm} function performs a {\bf strupr} where {\bf s}
;points to program memory.
;@param s pointer to object in program memory
;
;rom char *struprpgm (rom char *s);

extern __RETVAL0

STRING CODE
struprpgm   FUSTART
  global struprpgm

#ifdef __SMALL__
 messg "struprpgm - SMALL"

; Proceedure: Use TBLPTR for 's'.

          Stk2CpyToReg -2,TBLPTRL
         
  ; Save off pointer for return value 
	  movff     TBLPTRL, PRODL
	  movff     TBLPTRH, PRODH
	  
  ; Scan for '\0' while converting case
jLoop:
          tblrd     *
          movlw     'a'
          subwf     TABLAT,W,ACCESS
          bn        jSkip

          movlw     'z'+1
          subwf     TABLAT,W,ACCESS
          bnn       jSkip

  ; Convert to uppercase

          movlw     0xDF
          andwf     TABLAT,F,ACCESS
          tblwt     *

jSkip
          tblrd     *+                  ; increment

  ; Test for '\0'
          movf      TABLAT,F,ACCESS   ; set Z flag
          bnz       jLoop

jEnd
          FUEND
          return

#else
#ifdef __LARGE__
 messg "struprpgm - LARGE"

; Proceedure: Use TBLPTR for 's'.

          Stk3CpyToReg -3,TBLPTRL

  ; Scan for '\0' while converting case
jLoop:
          tblrd     *
          movlw     'a'
          subwf     TABLAT,W,ACCESS
          bn        jSkip

          movlw     'z'+1
          subwf     TABLAT,W,ACCESS
          bnn       jSkip

  ; Convert to uppercase

          movlw     0xDF
          andwf     TABLAT,F,ACCESS
          tblwt     *

jSkip
          tblrd     *+                  ; increment

  ; Test for '\0'
          movf      TABLAT,F,ACCESS   ; set Z flag
          bnz       jLoop

jEnd

  ; Set __RETVAL0 = 's'.

          Stk3CpyToReg -3,__RETVAL0
          FUEND
          return

#else
 error "No Model Specified"
#endif
#endif
  end


