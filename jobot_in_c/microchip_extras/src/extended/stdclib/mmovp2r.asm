; $Id: mmovp2r.asm,v 1.1 2003/12/09 22:43:29 GrosbaJ Exp $

#include "P18CXXX.INC"
#include "P18MACRO.INC"

; @name memmovepgm2ram
;The {\bf memmovepgm2ram} function performs a {\bf memmove} where
;{\bf s1} points to data memory and {\bf s2} points to program
;memory.
;@param s1 pointer to destination in data memory
;@param s2 pointer to source in program memory
;@param n number of characters to copy
;
; void *memmovepgm2ram (void *s1, const rom void *s2, sizeram_t n);


STRING CODE
memmovepgm2ram
  global memmovepgm2ram
  extern memcpypgm2ram

 messg "memmovepgm2ram - Uses memcpypgm2ram"

; Proceedure: Since it is not possible for memory overlap for a pgm to ram copy
; we will use the memcpypgm2ram code unchanged.

          goto      memcpypgm2ram

  end
