#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name isgraph
;The {\bf isgraph} function tests the value of {\bf c} determine if it
;is a graphical character. A graphical character is defined as any
;printing character except space (' ').
;
; int isgraph (int c);

CTYPE CODE
isgraph
  global isgraph

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
          jmpFeqL   INDF0,ACCESS,' ',jNoMatch     ; space

jMatch:
          incf      PRODL,F,ACCESS
jNoMatch:
          return
  end

