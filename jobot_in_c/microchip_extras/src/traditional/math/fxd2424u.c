/*** Unsigned Integer Division: 24-bit by 24-bit
 *** 
 *** Optimized: Dec. 21, 2000
 ***        by: Daniel R. Madill, Quanser Consulting Inc.
 ***       for: Saved (for the worst case) at least 7*24 = 168 instruction cycles
 ***            over the code supplied with MCC18 v1.00.12
 ***/

void FXD2424U (void/* ushlong aarg, ushlong barg */)
{
    // use INDF1 for the counter...

      _asm

        // Clear the remainder
        clrf __REMB0, 0
        clrf __REMB1, 0
        clrf __REMB2, 0

        // Set the counter to 24
        movlw 24
        movwf INDF1, 0

        // Clear the carry flag
        bcf STATUS, 0, 0

loop:
	    //AARG24 <<= 1; The carry is always clear at the top of the loop.
	    rlcf __AARGB2, 1, 0
	    rlcf __AARGB1, 1, 0
	    rlcf __AARGB0, 1, 0

        // REM24 = (REM24 << 1) | (AARG24 >> 24)
	    rlcf __REMB2, 1, 0
	    rlcf __REMB1, 1, 0
	    rlcf __REMB0, 1, 0

        //if (PROD >= BARG24)
	    movf __BARGB2, 0, 0
	    subwf __REMB2, 0, 0
	    movf __BARGB1, 0, 0
	    subwfb __REMB1, 0, 0
	    movf __BARGB0, 0, 0
	    subwfb __REMB0, 0, 0
	    bnc _false
        //{
	        //REM24-= BARG24;
	        movf __BARGB2, 0, 0
	        subwf __REMB2, 1, 0
	        movf __BARGB1, 0, 0
	        subwfb __REMB1, 1, 0
	        movf __BARGB0, 0, 0
	        subwfb __REMB0, 1, 0

			// AARG++; Note that since we shifted AARG above, we can add
			//         one simply by setting the least significant bit!
            //         Use the incf operation to do this so that the carry
            //         flag is cleared. Thus, the carry flag will always be
            //         zero at the top of the loop.

			incf __AARGB2, 1, 0
        //}

        _false:

        decfsz INDF1, 1, 0  // if (--count != 0) then loop. Does not affect carry flag.
        bra loop

        _endasm

  /* result in AARG already... */
}
