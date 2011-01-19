/*** Unsigned Integer Division: 16-bit by 16-bit
 *** 
 *** Optimized: Dec. 21, 2000
 ***        by: Daniel R. Madill, Quanser Consulting Inc.
 ***       for: Saved (for the worst case) at least 5*16 = 80 instruction cycles
 ***            over the code supplied with MCC18 v1.00.12
 ***/

void FXD1616U (/*unsigned arg0, unsigned arg1*/)
{
  // use INDF1 for the counter...

      _asm

    // REM = 0
    clrf __REMB0, 0
    clrf __REMB1, 0
    
    // INDF1 = 16
    movlw 16
    movwf INDF1, 0

    // Clear the carry
	bcf STATUS, 0, 0

loop:

	//AARG16 <<= 1; Carry is always clear at this point.
	rlcf __AARGB1, 1, 0
	rlcf __AARGB0, 1, 0

	//PROD = (PROD << 1) | (AARG16 >> 16)
	rlcf __REMB1, 1, 0
	rlcf __REMB0, 1, 0
	
	//if (PROD >= BARG16)
	movf __BARGB1, 0, 0
	subwf __REMB1, 0, 0
	movf __BARGB0, 0, 0
	subwfb __REMB0, 0, 0
	bnc endloop
    //{
	    //PROD -= BARG16;
	    movf __BARGB1, 0, 0
	    subwf __REMB1, 1, 0
	    movf __BARGB0, 0, 0
	    subwfb __REMB0, 1, 0

	    //++AARG16; Since AARG16 was shift to the left above, the increment will
        //          simply set the LSbit. Using incf also clears the carry, which
        //          means we don't have to clear the carry at the top of the loop.
        incf __AARGB1, 1, 0
    //}

endloop:
    decfsz INDF1, 1, 0    // does not affect the carry bit
    bra loop

  _endasm

  /* result in AARG already... */
}
