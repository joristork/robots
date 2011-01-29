/*
 * Team America's Cobot
 *
 * This is a Microcontroller 18F452 program, which controls a Jobot containing
 * three servo's and a communication channel between the microcontroller and a
 * SunSpot. This channel uses the microcontroller pins D4, D5, D6 and D7. There
 * are four motions supported by this program: forward, backward, rotate left
 * and rotate right. The servo's are controlled by pulses emitted on the
 * microcontroller pins D1, D2 and D3. A short pulse (= 0.6 ms) will rotate a
 * servo clock wise and a long pulse (= 2.0 ms) will rotate a servo counter
 * clock wise.
 *
 * Last modified: Jan 26, 2011
 * Created by:    Sander van Veen <sandervv@gmail.com>,
 *                Lucas Swartsenburg <luuk@noregular.com>
 *
 * This source code is released as public domain.
 */

// Microcontroller configuration
#if defined(__PCH__)
// Unfortunately, this include directive is Windows specific, since the PCH
// compiler runs only on Windows.
#include "C:\pch\Devices\18f452.h"
#fuses HS,PUT,PROTECT,NOBROWNOUT,LVP,NOWDT
#use delay(clock=20000000)
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7)  // Jumpers: 8 to 11, 7 to 12
#endif

#define DEBUG_MODE 0

#define PIN_ACK        PIN_D4
#define PIN_RST        PIN_D5
#define PIN_SENDER_ACK PIN_D6
#define PIN_SENDER_DAT PIN_D7

// Signal time spans (used to control the servo's)
int pulse_cw = 6;   // = 0.6 ms
int pulse_ccw = 20; // = 2.0 ms
int period = 200;   // = 20 ms

void wait(char count) {
    char i;
    for(i = 0; i < count && !kbhit(); i++) {
        delay_us(100);
    }
}

#define STATE_FORWARD  0
#define STATE_BACKWARD 1
#define STATE_LEFT     2
#define STATE_RIGHT    3
#define STATE_STOPPED  4

void backward() {
    output_high(PIN_D1);
    wait(pulse_cw);

    output_high(PIN_D2);
    output_low(PIN_D1);
    wait(pulse_ccw);

    output_low(PIN_D2);

    wait(period - pulse_cw - pulse_ccw);
}

void forward() {
    output_high(PIN_D2);
    wait(pulse_cw);

    output_high(PIN_D1);
    output_low(PIN_D2);
    wait(pulse_ccw);

    output_low(PIN_D1);

    wait(period - pulse_cw - pulse_ccw);
}

void rotateRight() {
    output_high(PIN_D1);
    output_high(PIN_D2);
    output_high(PIN_D3);

    wait(pulse_cw);

    output_low(PIN_D1);
    output_low(PIN_D2);
    output_low(PIN_D3);

    wait(period - pulse_cw);
}

void rotateLeft() {
    output_high(PIN_D1);
    output_high(PIN_D2);
    output_high(PIN_D3);

    wait(pulse_ccw);

    output_low(PIN_D1);
    output_low(PIN_D2);
    output_low(PIN_D3);

    wait(period - pulse_ccw);
}

void stop() {
    wait(period);
}

#ifndef DEBUG_MODE
#define debug printf
#else
void debug(char *str) { }
#endif

int get_sender_ack() {
    return INPUT(PIN_SENDER_ACK);
}

int get_sender_data() {
    return INPUT(PIN_SENDER_DAT);
}

void get_input() {
    if( INPUT(PIN_SENDER_ACK) )
        debug("Input D6 (ACK): 1\n");
    else
        debug("Input D6 (ACK): 0\n");

    if( INPUT(PIN_SENDER_DAT) )
        debug("Input D7 (DAT): 1\n");
    else
        debug("Input D7 (DAT): 0\n");
}

void set_output(int state) {
    get_input();

    switch(state) {
        case 0:
            OUTPUT_LOW(PIN_ACK);
            OUTPUT_LOW(PIN_RST);
            debug("Output D4 (ACK): 0\n");
            debug("Output D5 (DAT): 0\n");
        break;
        case 1:
            OUTPUT_HIGH(PIN_ACK);
            OUTPUT_LOW(PIN_RST);
            debug("Output D4 (ACK): 1\n");
            debug("Output D5 (DAT): 0\n");
        break;
        case 2:
            OUTPUT_LOW(PIN_ACK);
            OUTPUT_HIGH(PIN_RST);
            debug("Output D4 (ACK): 0\n");
            debug("Output D5 (DAT): 1\n");
        break;
        case 3:
            OUTPUT_HIGH(PIN_ACK);
            OUTPUT_HIGH(PIN_RST);
            debug("Output D4 (ACK): 1\n");
            debug("Output D5 (DAT): 1\n");
        break;
    }
}

char input_char;

int i;

// Buffer for data receiving
int data_previous, data_current, data_received, receive_round;
int b, bit_count = 3;

void receive_data() {
    // Clear data buffers
    data_received = data_previous = data_current = 0;

    // Receive two times (= "rounds") three bits. Most significant bits are
    // transmitted first (thus from left to right)
    for( receive_round = 0; receive_round < 2; receive_round++ ) {
        for(b = 0; b < bit_count; b++) {
            //printf("round: %d, b: %d\n", receive_round, bit_count - b);
            debug("get_sender_ack() first\n");
            while(!get_sender_ack())
                wait(1);

            debug("get_sender_data()\n");
            data_current |= get_sender_data() << (bit_count-b-1);

            // First round is simply storing the data and emits an acknowledge
            // signal (= 1). The second round is used for error checking. The
            // second round will emit an acknowledge signal when the data is
            // properly transmitted / received. The second round will send an
            // acknowledge and reset signal (= 3), when the current data bit is
            // not matching data of the previous round.
            if(receive_round == 0
                    || (data_previous & (1<<(bit_count-b-1)))
                            == (data_current & (1<<(bit_count-b-1))))
                set_output(1);
            else {
                debug("Error during transmission\n");
                set_output(3);

                // Wait until the sender sees the acknowledge and reset signal.
                // When the sender emits his acknowledge signal, the receive
                // procedure is restarted.
                while(get_sender_ack())
                    wait(1);
                set_output(0);
                return receive_data();
            }

            debug("get_sender_ack() last\n");
            while(get_sender_ack())
                wait(1);

            set_output(0);
        }

        //printf("R%d data: %d\n-----\n", receive_round, data_current);

        data_previous = data_current;
        data_current = 0;
    }

    data_received = data_previous;
}

void start_handshake() {
    printf("Hello sender (start handshake)\n");
    set_output(3);

    printf("Waiting for sender handshake.\n");
    while(!get_sender_ack() || !get_sender_data())
        wait(1);

    printf("Waiting for sender to finish the handshake.\n");
    while(get_sender_ack() || get_sender_data())
        wait(1);

    printf("Handshake completed\n");
    set_output(0);
}

void main() {
    // Be nice, say hello to the user
    printf("Hello, this is 18f452\n");

    // Set microcontroller output pin states
    set_output(0);

    // Get microcontroller input pin states
    get_input();

    // Start handshake with the sender. This call is blocking, which will ensure
    // the sender and this microcontroller do not send or receive data while the
    // other is booting / resetting.
    start_handshake();

    for(;;) {
        receive_data();
        printf("Received data: %d\n", data_received);

        // Continue the movement until the sender emits an acknowledge signal,
        // which means the sender is trying to send new data.
        while(!get_sender_ack()) {
            switch(data_received) {
                case STATE_FORWARD:  forward();     break;
                case STATE_BACKWARD: backward();    break;
                case STATE_LEFT:     rotateLeft();  break;
                case STATE_RIGHT:    rotateRight(); break;
                case STATE_STOPPED:  stop();        break;
            }
        }
    }
}
