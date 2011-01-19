/*
 * The following sample application prompts the user (via HyperTerminal) to
 * enter a digit between 0 and 9.  Upon receiving a character from the USART,
 * the program will then either output a string from an array of data or if the
 * character received is not between 0 and 9, output an error string.
 * The command line used to build this application is:
 * 
 * mcc18 -p 18f452 -I c:\mcc18\h example2.c
 * 
 * where c:\mcc18 is the directory in which the compiler is installed.
 * This sample application is designed for use with the MPLAB ICD2, the
 * PICDEM 2 Plus demo board, and the PIC18F452 device.  This sample covers the
 * following items:
 * 
 * 1.  Creating large data objects
 * 2.  Reading from and writing to the USART. 
 * 3.  Interrupt handling (#pragma interrupt, interrupt vectors, and
 *     interrupt service routines)
 * 4.  System header files
 * 5.  Processor-specific header files
 * 6.  #pragma sectiontype
 * 7.  Inline assembly
 */
#include <p18cxxx.h>
#include <usart.h>

void rx_handler (void);

#define BUF_SIZE 25

/*
 * Step #1  The data is allocated into its own section.
 */
#pragma idata bigdata
char data[11][BUF_SIZE+1] = {
  { "String #0\n\r" },
  { "String #1\n\r" },
  { "String #2\n\r" },
  { "String #3\n\r" },
  { "String #4\n\r" },
  { "String #5\n\r" },
  { "String #6\n\r" },
  { "String #7\n\r" },
  { "String #8\n\r" },
  { "String #9\n\r" },
  { "Invalid key (0-9 only)\n\r" }
};
#pragma idata

#pragma code rx_interrupt = 0x8
void rx_int (void)
{
  _asm goto rx_handler _endasm
}
#pragma code

#pragma interrupt rx_handler
void rx_handler (void)
{
  unsigned char c;

  /* Get the character received from the USART */
  c = ReadUSART();
  if (c >= '0' && c <= '9')
    {
      c -= '0';
      /* Display value received on LEDs */
      PORTB = c;

      /*
       * Step #2  This example did not need an additional
       * pointer to access the large memory because of the
       * multi-dimension array.
       *
       * Display the string located at the array offset
       * of the character received
       */
      putsUSART (data[c]);
    }
  else
    {
      /*
       * Step #2  This example did not need an additional
       * pointer to access the large memory because of the
       * multi-dimension array.
       *
       * Invalid character received from USART.
       * Display error string.
       */
      putsUSART (data[10]);

      /* Display value received on LEDs */
      PORTB = c;
    }

    /* Clear the interrupt flag */
    PIR1bits.RCIF = 0;
}

void main (void)
{
  /* Configure all PORTB pins for output */
  TRISB = 0;

  /*
   * Open the USART configured as
   * 8N1, 2400 baud, in polled mode
   */
  OpenUSART (USART_TX_INT_OFF &
             USART_RX_INT_ON &
             USART_ASYNCH_MODE &
             USART_EIGHT_BIT &
             USART_CONT_RX &
             USART_BRGH_HIGH, 103);

  /* Display a prompt to the USART */
  putrsUSART (
    (const far rom char *)"\n\rEnter a digit 0-9!\n\r");

  /* Enable interrupt priority */
  RCONbits.IPEN = 1;

  /* Make receive interrupt high priority */
  IPR1bits.RCIP = 1;

  /* Enable all high priority interrupts */
  INTCONbits.GIEH = 1;

  /* Loop forever */
  while (1)
    ;
}
