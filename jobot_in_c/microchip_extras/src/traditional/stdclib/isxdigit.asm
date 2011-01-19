#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isxdigit
;The {\bf isxdigit} function tests the value of {\bf c} to determine if it
;is a hex-digit character. A hex-digit character is defined as any
;hexadecimal digit character (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e, f,
;A, B, C, D, E, F).
;
; int isxdigit (int c);

CTYPE CODE
isxdigit
  global isxdigit

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
          jmpFleL   INDF0,ACCESS,'9',jMatch
          bcf       INDF0,5,ACCESS      ; change lowercase to uppercase
          jmpFleL   INDF0,ACCESS,'A'-1,jNoMatch
          jmpFgeL   INDF0,ACCESS,'F'+1,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          return
  end

