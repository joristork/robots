/*** Unsigned Integer Division: 8-bit by 8-bit
 *** 
 *** Optimized: Dec. 21, 2000
 ***        by: Daniel R. Madill, Quanser Consulting Inc.
 ***       for: Saved (for the worst case) at least 4*8 = 32 instruction cycles
 ***            over the code supplied with MCC18 v1.00.12
 ***/

void FXD0808U (/*unsigned char arg0, unsigned char arg1*/)
{
  // use INDF1 for the counter...

    _asm

    // REM = 0
    clrf __REMB0, 0

    // INDF1 = 8
    movlw 8
    movwf INDF1, 0

    // clear the carry
    bcf STATUS, 0, 0

loop:

	//AARGB0 <<= 1; Carry is always clear at this point.
	rlcf __AARGB0, 1, 0

    //PROD = (PROD << 1) | (ARGB0 >> 16);
	rlcf __REMB0, 1, 0

	//if (REMB0 >= BARGB0)
	movf __BARGB0, 0, 0
	subwf __REMB0, 0, 0
	bnc endloop
    //{
	    //REMB0 -= BARGB0;
	    movwf __REMB0, 0
	    //++AARGB0;
	    incf __AARGB0, 1, 0   // Carry is cleared by incf since overflow not possible
    //}

endloop:
    decfsz INDF1, 1, 0    // does not affect carry
    bra loop

      _endasm

  /* result in AARG already... */
}
