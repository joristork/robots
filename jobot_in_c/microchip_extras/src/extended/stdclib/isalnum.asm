#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isalnum
;The {\bf isalnum} function tests the value of {\bf c} to determine if
;it is an alpha-numeric character. An alphanumeric character is defined
;as any value for which {\bf isalpha} or {\bf isdigit} is true.
;
; int isalnum (int c);

CTYPE CODE
isalnum   FUSTART
  global isalnum

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
  ; isdigit conditions
          jmpFleL   INDF0,ACCESS,'0'-1,jNoMatch
          jmpFleL   INDF0,ACCESS,'9',jMatch

  ; isalpha conditions
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

