/* a signed multiply is only different from an unsigned multiply
 * if the upper bytes of the result matter, which for us they
 * don't.
 */

#define PRODH	(PRODL + 1)

extern near unsigned char __AARGB3, __BARGB3;
extern near unsigned char __AARGB7;
void FXM3232 (void)
{
  _asm
    movf __AARGB3, 0, 0
    mulwf __BARGB3, 0
    // low byte of both operands, so result adds into the low order
    // result bytes
    movff PRODL, __AARGB7
    movff PRODH, __AARGB7 + 1
    // W still contains AARGB3
    mulwf __BARGB3 + 1, 0
    // BARGB3[1], so result adds into AARGB7[1,2]
    movf PRODL, 0, 0
    addwf __AARGB7 + 1, 1, 0
    movlw 0
    addwfc PRODH, 0, 0
    movwf __AARGB7 + 2, 0
    // reload AARGB3 to continue
    movf __AARGB3, 0, 0
    mulwf __BARGB3 + 2, 0
    // BARGB3[2], so result adds into AARGB7[2,3]
    movf PRODL, 0, 0
    addwf __AARGB7 + 2, 1, 0
    movlw 0
    addwfc PRODH, 0, 0
    movwf __AARGB7 + 3, 0
    // reload AARGB3 to continue
    movf __AARGB3, 0, 0
    mulwf __BARGB3 + 3, 0
    // BARGB3[3], so result adds into AARGB7[3]. 
    // we don't care about result bytes above AARGB7[3], ignore prodh here.
    movf PRODL, 0, 0
    addwf __AARGB7 + 3, 1, 0
    // that's the end of all terms involving AARGB3[0].
    // load AARGB3[1] to continue
    movf __AARGB3 + 1, 0, 0
    mulwf __BARGB3, 0
    // AARGB3[1], so result adds into AARGB7[1,2]
    movf PRODL, 0, 0
    addwf __AARGB7 + 1, 1, 0
    movf PRODH, 0, 0
    addwfc __AARGB7 + 2, 1, 0
    movlw 0
    addwfc __AARGB7 + 3, 1, 0
    // reload AARGB3[1] to continue
    movf __AARGB3 + 1, 0, 0
    mulwf __BARGB3 + 1, 0
    // AARGB3[1] and BARGB3[1], so result adds into AARGB7[2,3]
    movf PRODL, 0, 0
    addwf __AARGB7 + 2, 1, 0
    movf PRODH, 0, 0
    addwfc __AARGB7 + 3, 1, 0
    // reload AARGB3[1] to continue
    movf __AARGB3 + 1, 0, 0
    mulwf __BARGB3 + 2, 0
    // AARGB3[1] and BARGB3[2], so result adds into AARGB7[3]
    // we don't care about result bytes above AARGB7[3], ignore prodh here.
    movf PRODL, 0, 0
    addwf __AARGB7 + 3, 1, 0
    // all bytes of the term from the product of AARGB3[1] and BARGB3[3] are
    // above our 32-bit result, don't even need to bother calculating
    // that term.
    // load AARGB3[2] to continue
    movf __AARGB3 + 2, 0, 0
    mulwf __BARGB3, 0
    // AARGB3[2] and BARGB3[0], so result adds into AARGB7[2,3]
    movf PRODL, 0, 0
    addwf __AARGB7 + 2, 1, 0
    movf PRODH, 0, 0
    addwfc __AARGB7 + 3, 1, 0
    // reload AARGB3[2] to continue
    movf __AARGB3 + 2, 0, 0
    mulwf __BARGB3 + 1, 0
    // AARGB3[2] and BARGB3[1], so result adds into AARGB7[3]
    // we don't care about result bytes above AARGB7[3], ignore prodh here.
    movf PRODL, 0, 0
    addwf __AARGB7 + 3, 1, 0
    // all bytes of the termsfrom the products of AARGB3[2] and BARGB3[2,3] are
    // above our 32-bit result, don't even need to bother calculating
    // those terms.
    // load AARGB3[3] to continue
    movf __AARGB3 + 3, 0, 0
    mulwf __BARGB3, 0
    // AARGB3[3] and BARGB3[0], so result adds into AARGB7[3]
    movf PRODL, 0, 0
    addwf __AARGB7 + 3, 1, 0

    _endasm;
}
