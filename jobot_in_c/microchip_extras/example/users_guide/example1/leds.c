/*
 * The following sample application will flash LEDs connected to PORTB of a
 * PIC18F452 microcontroller. The command line used to build this application
 * is
 * 
 * mcc18 -p 18f452 -I c:\mcc18\h leds.c
 * 
 * where c:\mcc18 is the directory in which the compiler is installed.
 * This sample application was designed for use with a PICDEM 2 demo board. 
 * This sample has also been tested using an MPLAB ICD 2 and a PICDEM 2 Plus
 * demo board.  This sample covers the following items:
 * 
 * 1. Interrupt handling (#pragma interruptlow, interrupt vectors,
    * interrupt service routines)
 * 2. System header files
 * 3. Processor-specific header files
 * 4. #pragma sectiontype
 * 5. Inline assembly
 */

/*
 * Includes the generic processor header file. The correct processor is
 * selected via the -p command-line option.
 */
#include <p18cxxx.h>
#include <timers.h>

#define NUMBER_OF_LEDS 8

void timer_isr (void);

static unsigned char s_count = 0;

/*
 * For PIC18xxxx devices, the low interrupt vector is found at 000000018h.
 * Change the default code section to the absolute code section named
 * low_vector located at address 0x18.
 */
#pragma code low_vector=0x18
void low_interrupt (void)
{
  /*
   * Inline assembly that will jump to the ISR.
   */
  _asm GOTO timer_isr _endasm
}

/*
 * Returns the compiler to the default code section.
 */
#pragma code

/*
 * Specifies the function timer_isr as a low-priority interrupt service
 * routine. This is required in order for the compiler to generate a
 * RETFIE instruction instead of a RETURN instruction for the timer_isr
 * function.
 */
#pragma interruptlow timer_isr

/*
 * Define the timer_isr function. Notice that it does not take any
 * parameters, and does not return anything (as required by ISRs). 
 */
void
timer_isr (void)
{
  static unsigned char led_display = 0;

  /*
   * Clears the TMR0 interrupt flag to stop the program from processing the
   * same interrupt multiple times.
   */
  INTCONbits.TMR0IF = 0;

  s_count = s_count % (NUMBER_OF_LEDS + 1);

  led_display = (1 << s_count++) - 1;

  /*
   * Sets the special function register PORTB to the value of led_display.
   */
  PORTB = led_display;
}

void
main (void)
{
  /*
   * Initialize the special function registers TRISB and PORTB.
   */
  TRISB = 0;
  PORTB = 0;

  /*
   * Enable the TMR0 interrupt, setting up the timer as an internal
   * 16-bit clock.
   */
  OpenTimer0 (TIMER_INT_ON & T0_SOURCE_INT & T0_16BIT);
  
  /*
   * Enable global interrupts.
   */
  INTCONbits.GIE = 1;

  while (1)
    {
    }
}
