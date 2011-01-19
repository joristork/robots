/*
* The following sample application will rotate the LEDs of the
* PIC18Fxx20 64/80L TQFP demo board, which are attached to PORTD.  The initial
* direction to rotate the LEDs is read from EEDATA.  When the lower left button
* is pushed, the direction that the LEDs are rotating will reverse, and the
* updated direction variable is written to EEDATA.  Analog Channel 0 (AN0) for
* the ADC is attached to the POT, which can be used to control the speed of the
* rotating LEDs. The command line used to build this application is:
* 
* mcc18 -p 18f8720 -I c:\mcc18\h example3.c
* 
* where c:\mcc18 is the directory in which the compiler is installed.
* This application is designed for use with the MPLAB ICD2, the
* PIC18Fxx20 64/80L TQFP demo board, and the PIC18F8720 device.   This sample
* covers the following items:
* 1.  Reading from and writing to EEDATA
* 2.  Mixing interrupt driven and polling peripheral access
* 3.  The use of interrupt priority (#pragma interrupt, #pragma interruptlow,
*     interrupt vectors, and interrupt service routines)
* 4.  Setting configuration bits via C code
* 5.  #pragma sectiontype
* 6.  Inline assembly
*/

#include <p18cxxx.h>
#include <delays.h>


#pragma config OSC = HS, OSCS = OFF
#pragma config PWRT = OFF
#pragma config BOR = OFF
#pragma config WDT = OFF
#pragma config CCP2MUX = OFF
#pragma config LVP = OFF

void tmr2 (void);
void button (void);

#pragma code high_vector_section=0x8
void
high_vector (void)
{
  _asm GOTO button _endasm
}
#pragma code low_vector_section=0x18
void
low_vector (void)
{
  _asm GOTO tmr2 _endasm
}
#pragma code

volatile unsigned current_ad_value;
int count = 0;
volatile enum { DIR_LEFT = 0, DIR_RIGHT } direction;

#pragma interruptlow tmr2
void
tmr2 (void)
{
  /* clear the timer interrupt flag */
  PIR1bits.TMR2IF = 0;

  /*
   * if we have reached the repeat count,
   * update the LEDs
   */
  if (count++ < current_ad_value)
    return;
  else
    count = 0;

  /*
   * Based on the direction, rotate the LEDs
   */
  if (direction == DIR_LEFT)
    {
      _asm RLNCF PORTD, 1, 0 _endasm
    }
  else
    {
      _asm RRNCF PORTD, 1, 0 _endasm
    }
}


#pragma interrupt button
void
button (void)
{
  direction = !direction;

  /*
   * Store the new direction in EEDATA.
   * Note that since we are already
   * in the high priority interrupt, we do
   * not need to explicitly enable/disable
   * interrupts around the write cycle
   */
  EECON1bits.EEPGD = 0;  /* WRITE step #1 */
  EECON1bits.WREN = 1;   /* WRITE step #2 */
  EEADR = 0;             /* WRITE step #3 */
  EEDATA = direction;    /* WRITE step #4 */
  EECON2 = 0x55;         /* WRITE step #5 */
  EECON2 = 0xaa;         /* WRITE step #6 */
  EECON1bits.WR = 1;     /* WRITE step #7 */
  while (!PIR2bits.EEIF) /* WRITE step #8 */
    ;
  PIR2bits.EEIF = 0;     /* WRITE step #9 */

  /* clear the interrupt flag */
  INTCONbits.INT0IF = 0;
}


void
main (void)
{
  /*
   * The first thing to do is to read
   * the start direction from data EEPROM.
   */
  EECON1bits.EEPGD = 0;  /* READ step #1 */
  EEADR = 0;             /* READ step #2 */
  EECON1bits.RD = 1;     /* READ step #3 */
  direction = EEDATA;    /* READ step #4 */

  /*
   * Make all bits on the Port D output
   * bits for the LEDs
   */
  TRISD = 0;

  /*
   * Make PORTA RA0 input, for the A/D
   * converter
   */
  TRISAbits.TRISA0 = 1;

  /* PORTB RB0 input for the button */
  TRISBbits.TRISB0 = 1;

  /* Reset Port D. Set just one bit to on. */
  PORTD = 1;

  /* Enable interrupt priority */
  RCONbits.IPEN = 1;

  /* Clear the peripheral interrupt flags */
  PIR1 = 0;

  /* Enable the timer interrupt */
  PIE1bits.TMR2IE = 1;
  IPR1bits.TMR2IP = 0;

  /*
   * Set the button on RB0 to trigger an
   * interrupt.  It is always high priority
   */
  INTCONbits.INT0IE = 1;


  /* Configure the ADC, most of this is the
   * default settings:
   * Fosc/32
   * AN0 Analog,
   * AN1-15 Digital Channel zero Interrupt
   *   disabled
   * Internal voltage references
   */

  /* FOSC/32 clock select */
  ADCON2bits.ADCS0 = 1;
  ADCON2bits.ADCS1 = 1;
  ADCON2bits.ADCS2 = 1;
  ADCON2bits.ADCS2 = 1;

  /* AN0-15, VREF */
  ADCON1 = 0b00001110;

  /* Enable interrupts */
  INTCONbits.GIEH = 1;
  INTCONbits.GIEL = 1;

  /* Turn on the ADC */
  ADCON0bits.ADON = 1;

  /* Enable the timer */
  T2CONbits.TMR2ON = 1;

  /* Start the ADC conversion */
  while (1)
    {
      /* Give the ADC time to get ready. */
      Delay100TCYx (2);

      /* start the ADC conversion */
      ADCON0bits.GO = 1;
      while (ADCON0bits.GO)
        ;
      current_ad_value = ADRES;
    }
}
