#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isprint
;The {\bf isprint} function tests the value of {\bf c} determine if it
;is a printable character. A printable character is defined as any character
;in the set [0x20,0x7e].
;
; int isprint (int c);

CTYPE CODE
isprint
  global isprint

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
          jmpFleL   INDF0,ACCESS,0x20-1,jNoMatch
          jmpFgeL   INDF0,ACCESS,0x7E+1,jNoMatch

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          return
  end

