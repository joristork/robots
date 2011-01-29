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

        data_previous = data_current;
        data_current = 0;
    }

    data_received = data_previous;
}
