Reports radio traffic in an audible manner like a Geiger counter.

A modified version of the packet sniffer that clicks the speaker (if 
present) & blinks the LEDs for each packet received by the radio.

There are two audio modes:  [mode shown in LED1]

 1. [green] click for each packet: 
		high   = ACK [red]
		medium = data packet [green]
 2. [red]   click for type of data packet:
               high   = radiogram [red]
               medium = radiostream [blue]
               low    = routing protocol packet [green]
               lowest = fragmented packet [white]

Use SW1 to toggle between them.

Currently there are 4 LED display modes:

 1. [white] number of packets received in last 10 seconds
 2. [green] number of SPOTs transmitting packets
 3. [blue]  number of SPOTs originating packets
 4. [red]   RSSI over last N seconds

Use the SW2 to change LED display modes.

This application requires that the SPOT running it does not start up any
services that use the radio, e.g. OTA mode must be disabled.  It also
must be run on a SPOT, not as a host app, since it needs to access the
physical layer of the radio stack.

Note: while in promiscuous listening mode the radio chip will not send
out any ACKs, so any Spot sending us a packet will never be notified it
was delivered---the sender will get a No ACK exception.

Author: Ron Goldman
