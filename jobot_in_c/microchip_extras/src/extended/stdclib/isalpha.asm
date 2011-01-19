#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isalpha
;The {\bf isalpha} function tests the value of {\bf c} to determine if it
;is an alphabetic character. An alphabetic character is defined as any
;value for which {\bf isupper} or {\bf islower} is true.
;
; int isalpha (int c);

CTYPE CODE
isalpha  FUSTART
  global isalpha

; Proceedure: Use FSR0 as pointer to 'c'
;         Return result in PROD

  ; Clear out result
          clrf      PRODL, ACCESS
	  clrf      PRODH, ACCESS
	  
  ; Set FSR0 = FSR1 - 2 ( location of 'c')
          StkSetPtrToOffset FSR0L,-1

          tstfsz    POSTDEC0,ACCESS
          bra       jNoMatch            ; upper byte must be 0 to match

  ; Test for condition
          jmpFleL   INDF0,ACCESS,'A'-1,jNoMatch
          jmpFleL   INDF0,ACCESS,'Z',jMatch
          jmpFleL   INDF0,ACCESS,'a'-1,jNoMatch
          jmpFgeL   INDF0,ACCESS,'z'+1,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          FUEND
          return
  end

