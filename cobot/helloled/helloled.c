// helloled.c
//
// simple minimal 'hello, world' program that blinks a LED


// ------------------------------------------------
// configuration
// change these if you're wiring the LED to a pin other than RA1

//#ifndef LED_TRIS
//#define LED_TRIS  TRISAbits.TRISA1
//#endif
//
//#ifndef LED_PIN
//#define LED_PIN   PORTAbits.RA1
//#endif

#define LED_PIN  LATBbits.LATB1   // Define LEDPin as PORT D Pin 1
#define LED_TRIS TRISBbits.TRISB1 // Define LEDTris as TRISD Pin 1

// ------------------------------------------------
// this #include pulls in the correct processor-specific registers
// definition file

//#include "pic18fregs.h"
//#include "stdio.h"
//#include "ctype.h"

// Inform the compiler the clock frequency is 20 MHz
#use delay(clock=20000000)

// Setup the RS232 communication
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7,  bits=8)

//#define FOSC 20000000L

//typedef unsigned int config;
//config __at 0x2007 __CONFIG = _CP_OFF &  _WDT_OFF &  _PWRTE_OFF &  _XT_OSC;

/*
 * Delay function.
 */
void delay_ms(long ms) {
    long i;

    while (ms--)
        for (i=0; i < 330; i++)
            ;
}

/*
 * Main entry point.
 */
void main() {
    int i;
    // Set LED Pin data direction to OUTPUT
    //LED_TRIS = 0;

    //LED_PIN = 1;
    //delay_ms(3000);

    TRISAbits.TRISA0 = 1;
    TRISAbits.TRISA1 = 1;
    TRISAbits.TRISA2 = 1;
    TRISAbits.TRISA3 = 1;
    TRISAbits.TRISA4 = 1;
    TRISAbits.TRISA5 = 1;
    TRISAbits.TRISA6 = 1;

    TRISBbits.TRISB0 = 1;
    TRISBbits.TRISB1 = 1;
    TRISBbits.TRISB2 = 1;
    TRISBbits.TRISB3 = 1;
    TRISBbits.TRISB4 = 1;
    TRISBbits.TRISB5 = 1;
    TRISBbits.TRISB6 = 1;

    TRISCbits.TRISC0 = 1;
    TRISCbits.TRISC1 = 1;
    TRISCbits.TRISC2 = 1;
    TRISCbits.TRISC3 = 1;
    TRISCbits.TRISC4 = 1;
    TRISCbits.TRISC5 = 1;
    TRISCbits.TRISC6 = 1;

    TRISDbits.TRISD0 = 1;
    TRISDbits.TRISD1 = 1;
    TRISDbits.TRISD2 = 1;
    TRISDbits.TRISD3 = 1;
    TRISDbits.TRISD4 = 1;
    TRISDbits.TRISD5 = 1;
    TRISDbits.TRISD6 = 1;

    // sit in an endless loop blinking the led
    for (;;) {
    //    //LED_PIN = 0;
    //    //delay_ms(250);
    //    //LED_PIN = 1;

        PORTAbits.RA0 = 0;
        PORTAbits.RA1 = 0;
        PORTAbits.RA2 = 0;
        PORTAbits.RA3 = 0;
        PORTAbits.RA4 = 0;
        PORTAbits.RA5 = 0;
        PORTAbits.RA6 = 0;

        PORTBbits.RB0 = 0;
        PORTBbits.RB1 = 0;
        PORTBbits.RB2 = 0;
        PORTBbits.RB3 = 0;
        PORTBbits.RB4 = 0;
        PORTBbits.RB5 = 0;
        PORTBbits.RB6 = 0;

        PORTCbits.RC0 = 0;
        PORTCbits.RC1 = 0;
        PORTCbits.RC2 = 0;
        PORTCbits.RC3 = 0;
        PORTCbits.RC4 = 0;
        PORTCbits.RC5 = 0;
        PORTCbits.RC6 = 0;

        PORTDbits.RD0 = 0;
        PORTDbits.RD1 = 0;
        PORTDbits.RD2 = 0;
        PORTDbits.RD3 = 0;
        PORTDbits.RD4 = 0;
        PORTDbits.RD5 = 0;
        PORTDbits.RD6 = 0;

        for (i=0; i < 100; i++);

        PORTAbits.RA0 = 1;
        PORTAbits.RA1 = 1;
        PORTAbits.RA2 = 1;
        PORTAbits.RA3 = 1;
        PORTAbits.RA4 = 1;
        PORTAbits.RA5 = 1;
        PORTAbits.RA6 = 1;

        PORTBbits.RB0 = 1;
        PORTBbits.RB1 = 1;
        PORTBbits.RB2 = 1;
        PORTBbits.RB3 = 1;
        PORTBbits.RB4 = 1;
        PORTBbits.RB5 = 1;
        PORTBbits.RB6 = 1;

        PORTCbits.RC0 = 1;
        PORTCbits.RC1 = 1;
        PORTCbits.RC2 = 1;
        PORTCbits.RC3 = 1;
        PORTCbits.RC4 = 1;
        PORTCbits.RC5 = 1;
        PORTCbits.RC6 = 1;

        PORTDbits.RD0 = 1;
        PORTDbits.RD1 = 1;
        PORTDbits.RD2 = 1;
        PORTDbits.RD3 = 1;
        PORTDbits.RD4 = 1;
        PORTDbits.RD5 = 1;
        PORTDbits.RD6 = 1;

        delay_ms(20);
        printf("LED loop\n\r");
    }
}
