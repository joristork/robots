#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name islower
;The {\bf islower} function tests the value of {\bf c} determine if it
;is a lower-case character. A lower case letter is defined as any character
;in the set ['a','z'].
;
; int islower (int c);

CTYPE CODE
islower  FUSTART
  global islower

; Proceedure: Use FSR0 as pointer to 'c'
;         Return with result in PROD

  ; Clear out result
          clrf      PRODL, ACCESS
	  clrf      PRODH, ACCESS

  ; Set FSR0 = FSR1 - 2 ( location of 'c')

          StkSetPtrToOffset FSR0L,-1

          tstfsz    POSTDEC0,ACCESS
          bra       jNoMatch            ; upper byte must be 0 to match

  ; Test for condition
          jmpFleL   INDF0,ACCESS,'a'-1,jNoMatch
          jmpFgeL   INDF0,ACCESS,'z'+1,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          FUEND
          return
  end

