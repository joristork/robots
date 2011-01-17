#!/bin/bash

# Bash script to automatically restart the host app if it crashes.
# In my experiments, the host app often crashes with the following
# error message:
#
#     [java] [radiogram] Adding: Server on port 65
#     [java] java.io.IOException: Device not configured in readArray
#     [java] 	at gnu.io.RXTXPort.readArray(Native Method)
#     [java] 	at gnu.io.RXTXPort$SerialInputStream.read(RXTXPort.java:1398)
#     [java] 	at java.io.BufferedInputStream.fill(BufferedInputStream.java:218)
#     [java] 	at java.io.BufferedInputStream.read(BufferedInputStream.java:237)
#     [java] 	at com.sun.spot.peripheral.radio.HostSerialPipe.readByte(HostSerialPipe.java:76)
#     [java] 	at com.sun.spot.peripheral.radio.HostSerialPipe.receive(HostSerialPipe.java:51)
#     [java] 	at com.sun.spot.peripheral.radio.ProxyMACCommandExecutor.run(ProxyMACCommandExecutor.java:55)
#     [java] Experimental:  JNI_OnLoad called.
#
# Instead of starting the host app as:
#
#    % ant host-run
#
# start it as follows:
#
#    % /bin/bash watchdog.sh
#
# SLEEP_TIME determines how many seconds to wait before restarting the server
#
SLEEP_TIME=5
HOST_APP="/usr/bin/ant host-run"

until $HOST_APP; do
	echo "SensorDotNetwork-onDesktop crashed, with exit code $?. Respawing É " >&2
	sleep $SLEEP_TIME
done