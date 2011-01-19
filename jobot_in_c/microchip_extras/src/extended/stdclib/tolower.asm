#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name tolower
;The {\bf tolower} function converts an upper case character to the
;corresponding lower case character.
;@return If {\bf isupper} is true for the value of {\bf c}, the corresponding
;lower case equivalent is returned, else the value of {\bf c} is returned
;unchanged.
;
; int tolower (int c);

CTYPE CODE
tolower  FUSTART
  global tolower

; Proceedure: Use FSR0 as pointer to 'c'
;         Return with result in PROD

  ; Set FSR0 = FSR1 - 2 ( location of 'c') after checking upper byte for 0

          StkSetPtrToOffset FSR0L,-1

  ; Copy 'c' into target location
	  movff POSTDEC0, PRODH
	  movff INDF0, PRODL

          tstfsz    PRODH,ACCESS
          bra       jNoMatch            ; upper byte must be 0

  ; Test for upper case condition
          jmpFleL   PRODL,ACCESS,'A'-1,jNoMatch
          jmpFgeL   PRODL,ACCESS,'Z'+1,jNoMatch

  ; Convert char from upper to lower by setting bit 5
          bsf       PRODL,5,ACCESS

jNoMatch:
          FUEND
          return
  end
