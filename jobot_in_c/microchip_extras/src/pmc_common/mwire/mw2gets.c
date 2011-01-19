#include <p18cxxx.h>
#include <mwire.h>

#if defined (MWIRE_V2) || defined (MWIRE_V4)
/********************************************************************
*     Function Name:    getsMwire2                                  *
*     Return Value:     void                                        *
*     Parameters:       address of read string storage location and *
*                       length of string bytes to read              *
*     Description:      This routine reads a string from the        *
*                       Microwire2 device. User must first issue    *
*                       start bit, opcode and address before reading*
*                       a string. Function WriteMWire2() or         *
*                       putcMWire2() can be called 2 times to       *
*                       accomplish the byte write sequence.         *
********************************************************************/
void getsMwire2( unsigned char *rdptr, unsigned char length )
{
  SSP2CON1bits.CKP = 0;           // ensure clock idle state is 0 for read
  while ( length )                // stay in loop until length = 0
  {
    SSP2BUF = 0x00;               // initiate bus cycle
    while( !SSP2STATbits.BF );    // wait until byte has been received
    *rdptr++ = SSP2BUF;           // save byte
    length--;                     // reduce string byte count by 1
  }  
  SSP2CON1bits.CKP = 1;           // ensure clock idle state is 1 for transmission
}
#endif
