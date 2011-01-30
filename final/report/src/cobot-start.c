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
    // Get microcontroller input pin states
    set_output(0);
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
