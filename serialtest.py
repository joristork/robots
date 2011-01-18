from serial import Serial, PARITY_NONE, EIGHTBITS, STOPBITS_ONE
from time import sleep

class HemissionException(Exception):
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)

class Hemisson:
    def __init__(self):
        """
        Initialise the serial connection to the Hemisson robot.
        """

        self.serial = Serial(0)
        self.serial.setParity(PARITY_NONE)
        self.serial.setByteSize(EIGHTBITS)
        self.serial.setStopbits(STOPBITS_ONE)
        self.serial.setBaudrate(115200)

        print 'i Initialised serial connection "%s".' % self.serial.portstr
        print '< %s\r' % self.serial.readline()
        print '< %s\r' % self.serial.readline()
        print '< %s\r' % self.serial.readline()
        self.serial.write(chr(254)+'\r')

    def __delete__(self):
        """
        Disconnect the serial connnection to the Hemisson robot.
        """

        print 'i Destroying serial connection "%s".' % self.serial.portstr
        self.write(chr(8))
        self.serial.close()

    def beep(self, state):
        """
        Generates a continuous beep, depending on state (0 = Off, 1 = On).
        """

        if state not in (0, 1):
            raise HemissionException('Beep state should be 0 (Off) or 1 (On).')

        self.write('H,%d' % state)
        print '< %s' % self.serial.readline()

    def drive(self):
        """
        Drive forward (setting both wheel's drive speed to '2').
        """

        self.set_speed(2)

    def get_switches(self):
        """
        Read the current status of the four top switches. Possible values are 0
        (= robot's right handside) and 1 (= robot's left handside). The first
        value is the value of the first switch from the front of the robot.
        """

        self.write('I')
        print '< %s' % self.serial.readline()

    def set_speed(self, left, right=None):
        """
        Set driving speed of left and right wheel. If only the left wheel drive
        speed is given, the right wheel's drive speed is set to the left
        wheel's drive speed.
        """

        if right == None:
            right = left

        if -9 < left or left > 9 or -9 < right or right > 9:
            raise HemissonException(
                'Keep the wheel drive speed value between -9 and 9.')

        self.write('D,%d,%d' % (left, right))
        print '< %s' % self.serial.readline()

    def stop(self):
        """
        Stop moving forward (setting both wheel's drive speed to zero).
        """

        self.write('D,0,0')
        print '< %s' % self.serial.readline()

    def remote_version(self):
        """
        Display version of the HemiOS running on the connected Hemisson robot.
        """

        self.write('B')
        print '< %s' % self.serial.readline()

    def reset(self):
        """
        Reset the robot's processor as if the On/Off switch is cycled.
        """

        self.serial.write('Z')
        print '< %s' % self.serial.readline()

    def write(self, msg):
        """
        Write a message through the serial connection to the Hemisson robot.
        """

        print '> %s\\r' % msg
        self.serial.write(msg+'\r\n')

if __name__ == '__main__':
    robot = Hemisson()
    robot.remote_version()
    robot.get_switches()
    robot.drive()
    sleep(2)
    robot.stop()
    robot.beep(1)
    sleep(2)
    robot.beep(0)
    del robot

