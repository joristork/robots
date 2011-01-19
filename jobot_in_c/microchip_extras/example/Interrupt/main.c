//=============================================================================
// Software License Agreement
//
// The software supplied herewith by Microchip Technology Incorporated 
// (the "Company") for its PIC® Microcontroller is intended and 
// supplied to you, the Company’s customer, for use solely and 
// exclusively on Microchip PIC Microcontroller products. The 
// software is owned by the Company and/or its supplier, and is 
// protected under applicable copyright laws. All rights are reserved. 
// Any use in violation of the foregoing restrictions may subject the 
// user to criminal sanctions under applicable laws, as well as to 
// civil liability for the breach of the terms and conditions of this 
// license.
//
// THIS SOFTWARE IS PROVIDED IN AN "AS IS" CONDITION. NO WARRANTIES, 
// WHETHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT NOT LIMITED 
// TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A 
// PARTICULAR PURPOSE APPLY TO THIS SOFTWARE. THE COMPANY SHALL NOT, 
// IN ANY CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL OR 
// CONSEQUENTIAL DAMAGES, FOR ANY REASON WHATSOEVER.
//
//=============================================================================
// Filename: MAIN.C
//=============================================================================
// Author:   Mike Garbutt
// Company:  Microchip Technology Inc.
// Revision: 1.00
// Date:   04/26/2001
//=============================================================================
// Compiled using MPLAB-C18 V1.00.31
// Include Files: P18C452.H V1.14.2.2
//=============================================================================
//
// Example code to generate a TMR0 interrupt and toggle LEDs on pins RB0 and
// RB7. Toggles RB0 in the interrupt routine and sets RB7 to match RB0 in the
// main routine. This demonstrates that code is executing in both routines.
//
//=============================================================================

//----------------------------------------------------------------------------

#include <p18C452.h>

//----------------------------------------------------------------------------

void main (void);
void InterruptHandlerHigh (void);

union
{
  struct
  {
    unsigned Timeout:1;         //flag to indicate a TMR0 timeout
    unsigned None:7;
  } Bit;
  unsigned char Byte;
} Flags;

//----------------------------------------------------------------------------
// Main routine

void
main ()
{
  Flags.Byte = 0;
  INTCON = 0x20;                //disable global and enable TMR0 interrupt
  INTCON2 = 0x84;               //TMR0 high priority
  RCONbits.IPEN = 1;            //enable priority levels
  TMR0H = 0;                    //clear timer
  TMR0L = 0;                    //clear timer
  T0CON = 0x82;                 //set up timer0 - prescaler 1:8
  INTCONbits.GIEH = 1;          //enable interrupts
  TRISB = 0;

  while (1)
    {
      if (Flags.Bit.Timeout == 1)
        {                                  //timeout?
          Flags.Bit.Timeout = 0;           //clear timeout indicor
          LATBbits.LATB7 = LATBbits.LATB0; //copy LED state from RB0 to RB7
        }
    }
}

//----------------------------------------------------------------------------
// High priority interrupt vector

#pragma code InterruptVectorHigh = 0x08
void
InterruptVectorHigh (void)
{
  _asm
    goto InterruptHandlerHigh //jump to interrupt routine
  _endasm
}

//----------------------------------------------------------------------------
// High priority interrupt routine

#pragma code
#pragma interrupt InterruptHandlerHigh

void
InterruptHandlerHigh ()
{
  if (INTCONbits.TMR0IF)
    {                                   //check for TMR0 overflow
      INTCONbits.TMR0IF = 0;            //clear interrupt flag
      Flags.Bit.Timeout = 1;            //indicate timeout
      LATBbits.LATB0 = !LATBbits.LATB0; //toggle LED on RB0
    }
}

//----------------------------------------------------------------------------
