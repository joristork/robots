This is a simple application to demonstrate the use of the InfraRed 
capabilities on the new rev8 Sun SPOT. Turns your SPOT into a remote
volume control for your tv. It will not work on older SPOTs or in the
Emulator.

An IR command is sent each time one of the switches is clicked (i.e. 
pressed & released). By default SW1 sends a MINUS (reduce volume) and SW2
sends a PLUS (increase volume). The IR LED is located just below SW1. Be
careful not to block it with your finger when pressing the switches.

Note: When switch 2 is pressed no IR signal can be sent, so we wait until
the switch is released to send the command.

The LEDs display a local "volume" setting and if another SPOT or tv remote
sends PLUS or MINUS commands the volume level will be modified appropriately.
Volume commands from an Apple IR Remote will also be recognized. The IR
Detector is located just below the H0 & H1 pins.


Author: Ron Goldman
Date:   August 27, 2010
