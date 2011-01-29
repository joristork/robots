// Device configuration: 20 Mhz processor and enable rs232,
// which is the COM port. The COM port uses a baudrate of 9600.
#if defined(__PCH__)
#include "18f452.h"
#fuses HS,NOPROTECT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)
#endif

void main() {
    // Set B pins to 'output' mode
    set_tris_b(0);

    // Blink two LEDs (located on the microcontroller board).
    // Wait for 2 ms between toggling the pins' value.
    while(1) {
        output_high(PIN_B1);
        delay_us(2000);
        output_low(PIN_B1);

        output_high(PIN_B3);
        delay_us(2000);
        output_low(PIN_B3);
    }
}
