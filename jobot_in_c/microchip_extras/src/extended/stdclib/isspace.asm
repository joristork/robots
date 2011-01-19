#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isspace
;The {\bf isspace} function tests the value of {\bf c} determine if it
;is a white-space character. A white-space character is defined as a
;character value of space, form feed, new-line, carriage-return,
;horizontal tag, or vertical tab.
;
; int isspace (int c);

CTYPE CODE
isspace  FUSTART
  global isspace

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
          jmpFeqL   INDF0,ACCESS,' ',jMatch  ; space
  ; FF=12,LF=10,CR=13,HT=9,VT=11 hence check for match of 9 to 13
          jmpFleL   INDF0,ACCESS,8,  jNoMatch
          jmpFgeL   INDF0,ACCESS,14, jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          FUEND
          return
  end

