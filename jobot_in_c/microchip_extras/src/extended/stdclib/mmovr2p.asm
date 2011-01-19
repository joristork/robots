; $Id: mmovr2p.asm,v 1.1 2003/12/09 22:43:29 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memmoveram2pgm
;The {\bf memmoveram2pgm} function performs a {\bf memmove} where {\bf s1}
;points to program memory and {\bf s2} point to data memory.
;@param s1 pointer to destination in program memory
;@param s2 pointer to source in data memory
;@param n number of characters to copy
;
;rom void *memmoveram2pgm (rom void *s1, const void *s2, sizeram_t n);


STRING CODE
memmoveram2pgm
  global memmoveram2pgm
  extern memcpyram2pgm

 messg "memmoveram2pgm - Uses memcpyram2pgm"

; Proceedure: Since it is not possible for memory overlap for a pgm to ram copy
; we will use the memcpyram2pgm code unchanged.

          goto      memcpyram2pgm

  end


