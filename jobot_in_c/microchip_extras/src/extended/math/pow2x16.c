void __pow2x16 (void)
{
  /* exponent in wreg, leave result in PROD */
  _asm
    clrf PRODL, 0
    clrf PRODL+1, 0
    incf PRODL, 1, 0
    andlw 0x0f
    bz 5
    bcf STATUS, 0, 0
    rlcf PRODL, 1, 0
    rlcf PRODL+1, 1, 0
    decf WREG, 0, 0
    bnz -5
  _endasm
}
