/////////////////////////////////////////////////////////////////////////
//
// Filename     :   Test876.c
// Revision     :   1.0
// Created      :   19-3-2001
// Revised  :   26-11-2003 by Benb
// Project      :   Pidac876
// Device       :   PIC16F876
// Development  :   MPLAB / CCS PCM
// Author       :   E. Steffens
// Department   :   Faculty of science
// Copyright    :   Universiteit van Amsterdam
//Description   :   Testing serial connection with PC
/////////////////////////////////////////////////////////////////////////

#include <18f452.h>
#include <ctype.h>

// Inform the compiler the clock frequency is 8 MHz
#use delay(clock=8000000)

// Setup the RS232 communication
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7,  bits=8)

int main(){
    char in_char;
    delay_ms(10); // Initialisation
    printf("Hello World\n\r");
    for(;;) {                 // Do forever
        in_char = getc() & 0x7F;       // Receive char
        printf(" %c %x\n\r", in_char, in_char); // Echo back received character
    }
    return 0;
}

