#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isupper
;The {\bf isupper} function tests the value of {\bf c} determine if it
;is a upper-case character. A upper case letter is defined as any character
;in the set ['A','Z'].
;
; int isupper (int c);


CTYPE CODE
isupper
  global isupper

; Proceedure: Use FSR0 as pointer to 'c'
;         Return with result in PROD

  ; Clear out result
          clrf      PRODL, ACCESS
	  clrf      PRODH, ACCESS

  ; Set FSR0 = FSR1 - 2 ( location of 'c')
          StkSetPtrToOffset FSR0L,-1

          tstfsz    POSTDEC0,ACCESS
          bra       jNoMatch            ; upper byte must be 0 to match

  ; Test for lower case condition
          jmpFleL   INDF0,ACCESS,'A'-1,jNoMatch
          jmpFgeL   INDF0,ACCESS,'Z'+1,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          return
  end
