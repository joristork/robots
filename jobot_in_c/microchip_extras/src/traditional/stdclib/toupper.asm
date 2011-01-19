#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name toupper
;The {\bf toupper} function converts a lower case character to the
;corresponding upper case character.
;@return If {\bf islower} is true for the value of {\bf c}, the corresponding
;upper case equivalent is returned, else the value of {\bf c} is returned
;unchanged.
;
; int toupper (int c);

CTYPE CODE
toupper
  global toupper

; Proceedure: Use FSR0 as pointer to 'c'
;         Return with result in PROD

  ; Set FSR0 = FSR1 - 2 ( location of 'c') after checking upper byte for 0

          StkSetPtrToOffset FSR0L,-1
	  
  ; Copy 'c' into target location
	  movff POSTDEC0, PRODH
	  movff INDF0, PRODL

          tstfsz    PRODH,ACCESS
          bra       jNoMatch            ; upper byte must be 0

  ; Test for lower case condition
          jmpFleL   PRODL,ACCESS,'a'-1,jNoMatch
          jmpFgeL   PRODL,ACCESS,'z'+1,jNoMatch

  ; Convert char from lower to upper by clearing bit 5
          bcf       PRODL,5,ACCESS

jNoMatch:
          return
  end
