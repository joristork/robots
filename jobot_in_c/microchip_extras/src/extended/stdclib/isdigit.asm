#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isdigit
;The {\bf isdigit} function tests the value of {\bf c} to determine if it
;is a digit character. A digit character is defined as any decimal digit
;character (0, 1, 2, 3, 4, 5, 6, 7, 8, 9).
;
; int isdigit (int c);

CTYPE CODE
isdigit  FUSTART
  global isdigit

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

          jmpFleL   INDF0,ACCESS,'0'-1,jNoMatch
          jmpFgeL   INDF0,ACCESS,'9'+1,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          FUEND
          return
  end

