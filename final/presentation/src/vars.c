
void wait(int time) {
    while(time--) delay_ns(100); // = 0.1 micro second
}

void main(void) {
    int period = 200;     // = 20.0 milli seconds
    int pulse  = 8;       // =  0.8 milli seconds

    OUTPUT_HIGH(PIN_D1);
    wait(pulse);          // Emit pulse to engine

    OUTPUT_LOW(PIN_D1);
    wait(period - pulse); // Complete the period
}
