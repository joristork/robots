void pow2x8 (void)
{
  /* exponent in wreg, leave result in PRODL */
  _asm
    clrf PRODL, 0
    incf PRODL, 1, 0
    andlw 0x0f
    bz 3
    rlncf PRODL, 1, 0
    decf WREG, 0, 0
    bnz -3
  _endasm
}
