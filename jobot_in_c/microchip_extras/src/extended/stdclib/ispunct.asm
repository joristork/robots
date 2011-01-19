#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name ispunct
;The {\bf ispunct} function tests the value of {\bf c} determine if it
;is a punctuation character. A punctuation character is defined as any character
;for which {\bf isprint} is true and {\bf isalnum} is not true and is not a space.
;
; int ispunct (int c);

CTYPE CODE
ispunct  FUSTART
  global ispunct

; Proceedure: Use FSR0 as pointer to 'c'
;         Return with result in PROD

  ; Clear out result
          clrf      PRODL, ACCESS
	  clrf      PRODH, ACCESS

  ; Set FSR0 = FSR1 - 2 ( location of 'c')
          StkSetPtrToOffset FSR0L,-1

          tstfsz    POSTDEC0,ACCESS
          bra       jNoMatch            ; upper byte must be 0 to match

; Proceedure: Use FSR0 as pointer to 'c'
;         Return with FSR0 pointing to 'c'.

  ; Set FSR0 = FSR1 - 2 ( location of 'c')

          StkSetPtrToOffset FSR0L,-1

          tstfsz    POSTDEC0,ACCESS
          bra       jNoMatch            ; upper byte must be 0 to match

  ; Test for condition

  ; isprint conditions and not space
          jmpFleL   INDF0,ACCESS,0x20,jNoMatch
          jmpFgeL   INDF0,ACCESS,0x7E+1,jNoMatch

  ; !isalnum conditions
  ; isdigit conditions
          jmpFleL   INDF0,ACCESS,'0'-1,jMatch
          jmpFleL   INDF0,ACCESS,'9',jNoMatch

  ; isalpha conditions
          jmpFleL   INDF0,ACCESS,'A'-1,jMatch
          jmpFleL   INDF0,ACCESS,'Z',jNoMatch
          jmpFleL   INDF0,ACCESS,'a'-1,jMatch
          jmpFleL   INDF0,ACCESS,'z',jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          FUEND
          return
  end

