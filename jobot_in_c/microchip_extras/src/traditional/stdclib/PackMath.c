#include <errno.h>
#include <math.h>
#include "_mathlib.h"

void _PackMath( tIEEE *pIEEE, tUfloat32 *pX)
{
    if(pIEEE->exp > 0xFE )
        {
        // Overflow - return +/- infinity
        errno = ERANGE;
        if(pIEEE->sgn)
            pX->l = NEGINF;
        else
            pX->l = POSINF;
        }
    else if(pIEEE->exp < 1 )
        {
        // Underflow - return +/- Zero
        errno = ERANGE;
        if(pIEEE->sgn)
            pX->l = NEGZERO;
        else
            pX->l = POSZERO;
        }
    else
        {
        pX->l = pIEEE->sig;
        if(pIEEE->exp & 1)
            pX->ub[2] |= 0x80;
        else
            pX->ub[2] &= 0x7F;

        pX->ub[3] = pIEEE->exp >> 1;
        if(pIEEE->sgn)
            pX->ub[3] |= 0x80;
        }
}


