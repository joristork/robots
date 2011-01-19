/*** Unsigned Integer Division: 32-bit by 32-bit
 *** 
 *** Optimized: Dec. 21, 2000
 ***        by: Daniel R. Madill, Quanser Consulting Inc.
 ***       for: Saved (for the worst case) at least 8*32 = 256 instruction cycles
 ***            over the code supplied with MCC18 v1.00.12
 ***/

void  FXD3232U(void/* ulong aarg, ulong barg */)
{
  // use INDF1 for the counter...

    _asm

    // REM = 0
    clrf __REMB0, 0
    clrf __REMB1, 0
    clrf __REMB2, 0
    clrf __REMB3, 0

    // INDF1 = 32
    movlw 32
    movwf INDF1, 0

    // Clear the carry
    bcf STATUS, 0, 0

loop:

	//AARG32 <<= 1; The carry is always clear at the top of the loop.
	rlcf __AARGB3, 1, 0
	rlcf __AARGB2, 1, 0
	rlcf __AARGB1, 1, 0
	rlcf __AARGB0, 1, 0

	//REM32 = (REM32 << 1) | (AARG32 >> 32)
	rlcf __REMB3, 1, 0
	rlcf __REMB2, 1, 0
	rlcf __REMB1, 1, 0
	rlcf __REMB0, 1, 0
	
	//if (PROD >= BARG32)
	movf __BARGB3, 0, 0
	subwf __REMB3, 0, 0
	movf __BARGB2, 0, 0
	subwfb __REMB2, 0, 0
	movf __BARGB1, 0, 0
	subwfb __REMB1, 0, 0
	movf __BARGB0, 0, 0
	subwfb __REMB0, 0, 0
	bnc _false
    //{
	    //REM32-= BARG32;
	    movf __BARGB3, 0, 0
	    subwf __REMB3, 1, 0
	    movf __BARGB2, 0, 0
	    subwfb __REMB2, 1, 0
	    movf __BARGB1, 0, 0
	    subwfb __REMB1, 1, 0
	    movf __BARGB0, 0, 0
	    subwfb __REMB0, 1, 0

	    //++AARG32; Since AARG32 was shift to the left above, we only need to set
        //          the lowest bit. Use incf so that the carry flag will also be cleared.
        //          Thus, the carry will always be clear at the top of the loop.
	    incf __AARGB3, 1, 0
    //}
_false:

    decfsz INDF1, 1, 0    // does not affect the carry bit
    bra loop

  /* result in AARG already... */
  _endasm
}
