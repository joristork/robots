#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name iscntrl
;The {\bf iscntrl} function tests the value of {\bf c} to determine if it
;is a control character. A control character is defined as any character
;in the set [0,1f] or the character 7f.
;
; int iscntrl (int c);

CTYPE CODE
iscntrl  FUSTART
  global iscntrl

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
          jmpFeqL   INDF0,ACCESS,0x7F,jMatch
          jmpFgeL   INDF0,ACCESS,0x20,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          FUEND
          return
  end

