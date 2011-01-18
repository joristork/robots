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
    int Getal = 0, success =0, state = 0;
    unsigned long int count = 0;
    unsigned long int check_count = 0;
    printf("Servo \n\r");
    while(1){
        check_count++;
        count++;

        // Receives the data (current position) from the I2C. 
        if((count >= 0) && (count <= 4)){
            // Start the SAR
            OUTPUT_HIGH(PIN_A3);
        }
        else if (count >= 7){
            count = 0;
            i2c_start();
            i2c_write( 0x41 );//write address (01000001)
            Getal = i2c_read(0); // acknowledge
            i2c_stop();

            i2c_start();
            i2c_write( 0x42 );//write address (01000010)
            i2c_write( Getal );    //write byte
            i2c_stop();

            OUTPUT_LOW(PIN_A3);
        }else {
            i2c_start();
            i2c_write( 0x41 );//write address (01000001)
            Getal = i2c_read(0); // acknowledge
            i2c_stop();

            i2c_start();
            i2c_write( 0x42 );//write address (01000010)
            i2c_write( Getal );    //write byte
            i2c_stop();
        }
         
        // DEBUG: Print the count so that we can find out what is happening.
        printf("count %ld \n\r", count);

        // Case position 0 (B1: 0 and B2: 0)
        if (INPUT(PIN_B1)==0 && INPUT(PIN_B2)==0){
            if(state!=0){
                check_count = 0;
                state = 0;
            }
            success = 0;
            // Postion has been reached, turn off the servo
            if (count > 5 && Getal > 0 && Getal <=2){
                success = 1;
                check_count = 0;             
                OUTPUT_LOW(PIN_A1);
                OUTPUT_LOW(PIN_A2);
            }
            // If "Getal" is smaller than this position requires, make sure 
            // servo turns right.
            else if((count > 5) && (Getal < 1)){      
                OUTPUT_LOW(PIN_A1);
                OUTPUT_HIGH(PIN_A2);
            }
            // If "Getal" is bigger than this position requires, make sure 
            // servo turns left.
            else if ((count > 5) && (Getal > 1)){
                success = 2;
                OUTPUT_LOW(PIN_A2);
                OUTPUT_HIGH(PIN_A1);
            }
           
        }
        // Case position 1 (B1: 1 and B2: 0)
        if (INPUT(PIN_B1)==1 && INPUT(PIN_B2)==0){
            if(state!=1){
                check_count = 0;
                state = 1;
            }
            success = 0;
            // Postion has been reached, turn off the servo
            if (count > 5 && Getal > 8 && Getal <=12){
                success = 1;
                check_count = 0;
                OUTPUT_LOW(PIN_A1);
                OUTPUT_LOW(PIN_A2);
            }
            // If "Getal" is smaller than this position requires, make sure 
            // servo turns right.
            else if((count > 5) && (Getal < 10)){
                OUTPUT_LOW(PIN_A1);
                OUTPUT_HIGH(PIN_A2);
            }
            // If "Getal" is bigger than this position requires, make sure 
            // servo turns left.
            else if ((count > 5) && (Getal > 10)){
                OUTPUT_LOW(PIN_A2);
                OUTPUT_HIGH(PIN_A1);
            }
        }
        // Case position 2 (B1: 0 and B2: 1)
        if (INPUT(PIN_B1)==0 && INPUT(PIN_B2)==1){
            if(state!=2){
                check_count = 0;
                state = 2;
            }
            
            success = 0;
            // Postion has been reached, turn off the servo
            if (count > 5 && Getal > 35 && Getal <=55){
                success = 1;
                check_count = 0;
                OUTPUT_LOW(PIN_A1);
                OUTPUT_LOW(PIN_A2);
            }
            // If "Getal" is smaller than this position requires, make sure 
            // servo turns right.
            else if((count > 5) && (Getal < 40)){
                OUTPUT_LOW(PIN_A1);
                OUTPUT_HIGH(PIN_A2);
            }
            // If "Getal" is bigger than this position requires, make sure 
            // servo turns left.
            else if ((count > 5) && (Getal > 40)){
                check_count = 0;
                OUTPUT_LOW(PIN_A2);
                OUTPUT_HIGH(PIN_A1);
            }
        }
        // Case position 3 (B1: 1 and B2: 1)
        if (INPUT(PIN_B1)==1 && INPUT(PIN_B2)==1){
            if(state!=3){
                check_count = 0;
                state = 3;
            }
            success = 0;
            // Postion has been reached, turn off the servo
            if (count > 5 && Getal > 80 && Getal <=110){
                success = 1;
                check_count = 0;
                printf("%ld \n\r", check_count);
                OUTPUT_LOW(PIN_A1);
                OUTPUT_LOW(PIN_A2);
            }
            // If "Getal" is smaller than this position requires, make sure 
            // servo turns right.
            else if((count > 5) && (Getal < 100)){
                OUTPUT_LOW(PIN_A1);
                OUTPUT_HIGH(PIN_A2);
            }
            // If "Getal" is bigger than this position requires, make sure 
            // servo turns left.
            else if ((count > 5) && (Getal > 100)){
                OUTPUT_LOW(PIN_A2);
                OUTPUT_HIGH(PIN_A1);
            }
        }

        /* 
         * If the state hasn't changed or if a destination position hasn't
         * been reached in withing 600 counts, shut the program down.
         */
        if(check_count > 600){
           printf("ERROR: motor stuck\n\r");
           OUTPUT_LOW(PIN_A1);
           OUTPUT_LOW(PIN_A2);
           break;
        }
        printf("Getal %d \n\r", Getal);
    }
    return 0;
}
