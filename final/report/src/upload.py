#!/usr/bin/env python
import os
import sys
from time import sleep, gmtime, strftime

# This script is used program an hex file on the 16-bit microcontroller 18F452.
# This script will monitor an hex file (defined below) for modification (by
# comparing the time of last modification) and, if it is modified, upload the
# source file to the microcontroller.
#
# Created by Sander van Veen <sandervv@gmail.com>, Jan 27, 2011.
# This script is public domain; feel free to do anything with it you want.
#
# Example invocation: python upload.py
#
# See also the included Makefile for information about the software used to
# program the microcontroller.

# Hex file to monitor
hex_file = 'ex_led.HEX'

# Log files used during compilation/uploading
build_log = 'ex_led.log'

old_mtime = 0

while True:
    mtime = os.stat(hex_file)[8]

    # Only upload newer versions of the hex file
    if mtime > old_mtime:
        f = open(build_log, 'a')
        f.write('Upload: %s (started)\n' % strftime('%X %x'))
        f.close()

        # Run the programmer (see Makefile)
        exit_code = os.system('make')

        f = open(build_log, 'a')

        if exit_code:
            f.write(('Upload: %s (failed; check your USB/COM cable and' \
                    + ' programming switch)\n' ) % strftime('%X %x'))
        else:
            old_mtime = mtime
            f.write('Upload: %s (success)\n' % strftime('%X %x'))

        f.write('-----\n')
        f.close()
    sleep(1)
