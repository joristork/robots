// helloled.c
//
// simple minimal 'hello, world' program that blinks a LED


// ------------------------------------------------
// configuration
// change these if you're wiring the LED to a pin other than RA1

#ifndef LED_TRIS
#define LED_TRIS  TRISAbits.TRISA1
#endif

#ifndef LED_PIN
#define LED_PIN   PORTAbits.RA1
#endif

// ------------------------------------------------
// this #include pulls in the correct processor-specific registers
// definition file

#include "pic18fregs.h"

// ------------------------------------------------
// a simple delay function

void delay_ms(long ms)
{
    long i;

    while (ms--)
        for (i=0; i < 330; i++)
            ;
}

// --------------------------------------------------
// and our main entry point

void main()
{
    // set pin to output
    LED_TRIS = 0;

    // sit in an endless loop blinking the led
    for (;;)
    {
        LED_PIN = 0;
        delay_ms(250);
        LED_PIN = 1;
        delay_ms(250);
    }
}
