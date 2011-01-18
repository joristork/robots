/////////////////////////////////////////////////////////////////////////
//
// Filename     :    Test876.c                                            
// Revision     :    1.0                                                   
// Created      :    19-3-2001 
// Revised    :     26-11-2003 by Benb       
                                   
// Project      :    Pidac876                                              
// Device        :    PIC16F876                                          
// Development    :    MPLAB / CCS PCM                                                        
// Author        :    E. Steffens
// Department    :    Faculty of science
// Copyright    :    Universiteit van Amsterdam
//Description    :    Testing serial connection with PC                        
/////////////////////////////////////////////////////////////////////////

#include <C:\Program Files\PICC\Devices\16F876.H>
#include <C:\Program Files\PICC\Drivers\CTYPE.H>

// Inform the compiler the clock frequency is 8 MHz
#use delay(clock=8000000) 

// Setup the RS232 communication
#use rs232(baud=9600, xmit=PIN_C6, rcv=PIN_C7,  bits=8)

//Setup the I2C bus
#use I2C (Master, SDA=PIN_C4, SCL=PIN_C3, SLOW)

int main(){
    int Getal = 0;
    int Getal_1 = 0;
    printf("Assignment 4 \n\r");

    //Voorbeeld: Byte van Master ? Slave met adres 1

    while(1){
        //Voorbeeld: Byte van Slave met adres 0 ? Master 
        i2c_start();
        i2c_write( 0x41 );//write address (01000001)
        Getal = i2c_read(0); // acknowledge
        i2c_stop();        

        i2c_start();
        i2c_write( 0x42 );//write address (01000010)
        i2c_write( Getal );    //write byte
        i2c_stop();
        if(Getal!=Getal_1){
               printf("%d \n\r", Getal);
               Getal_1 = Getal;
        }
    }
    return 0;
}
