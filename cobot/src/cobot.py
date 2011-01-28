#!/usr/bin/env python
from serial import Serial, PARITY_NONE, EIGHTBITS, STOPBITS_ONE
from time import sleep

# This script is used to read and write data from/to the COM-to-USB port.
# Created by Sander van Veen <sandervv@gmail.com>, Jan 27, 2011.
# This script is public domain; feel free to do anything with it you want.
#
# Dependencies of this script:
#  - Python 2.x
#  - python-serial
#
# The python-serial package does not recognise the USB-to-COM port properly. To
# fix this, you should change the python source code line containing the string
# "/dev/tty%d" to "/dev/ttyUSB%d".
#
# I prefer the ipython console, because it has better auto completion than the
# default python console. However, this script should work with the default
# python console as well.
#
# Example invocation: $ ipython -i -nobanner -noconfirm_exit cobot.py
#
# To enter read-only mode, type in the python console: read(robot)

class CobotException(Exception):
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)

class Cobot:
    """
    Class used to read and write data from/to the COM-to-USB port. This class is
    used in the Cobot project (= a project consisting of writing a driver in C
    for the Jobot robot and implementing a communication channel between a
    SunSpot and the microcontroller of the Jobot).
    """

    def __init__(self):
        """
        Initialise the serial connection to the Cobot robot.
        """

        self.serial = Serial(0)
        self.serial.setParity(PARITY_NONE)
        self.serial.setByteSize(EIGHTBITS)
        self.serial.setStopbits(STOPBITS_ONE)
        self.serial.setBaudrate(9600)

        self.msg_count = 0

        print 'i Initialised serial connection "%s".' % self.serial.portstr

        # Read welcome header
        self.readline()

        # Enable debug mode. This is useful if the Jobot sends additional
        # information about the input/output pin states. This additional
        # information is sent, when the Jobot's source code is compiled with
        # DEBUG_MODE directive is enabled. See also the "DEBUG_MODE" directive
        # in the source file "ex_led.c".
        self.debug_mode = 0

        # Read handshake status information or, if DEBUG_MODE is enabled in
        # the Jobot's source code, input/output pin states.
        self.readline()
        self.readline()
        self.readline()
        self.readline()

    def __delete__(self):
        """
        Disconnect the serial connnection to the Jobot.
        """

        print 'i Destroying serial connection "%s".' % self.serial.portstr
        self.serial.close()

    def readline(self):
        """
        Read a single newline terminated line from the serial connection.
        """

        print '%d < %s' % (self.msg_count, self.serial.readline()[:-1])
        self.msg_count += 1

    def write(self, msg):
        """
        Write a message through the serial connection to the Jobot.
        """

        print '%d > %s' % (self.msg_count, msg)
        self.serial.write(msg)
        self.msg_count += 1

    def set_output(self, state):
        """
        Set output pin states (PIN_D4 and PIN_D5) of the Jobot.
        """
        if not state in (0, 1, 2, 3):
            raise CobotException('Output pin state should be in {0, 1, 2, 3}.')

        # Set output pin states
        self.write(str(state))

        # Read both output pin states
        self.readline()
        self.readline()

        # Read both input pin states
        self.readline()
        self.readline()

if __name__ == '__main__':
    robot = Cobot()
    # robot.debug_mode = 1

    def read(robot):
        from time import sleep

        try:
            while(1):
                robot.readline()
        except KeyboardInterrupt:
            pass

        del robot

    #for i in range(12):
    #    robot.set_output(1)
    #    robot.set_output(0)
