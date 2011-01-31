#!/usr/bin/env python
import os
import sys
import subprocess
from time import sleep, gmtime, strftime

# This script is used compile a source file with the CCS C command line compiler
# (PCH) for the 16-bit microcontroller 18F452. This script will monitor a source
# file (defined below) for modification (by comparing the time of last
# modification) and, if it is modified, compile the source file.
#
# Created by Sander van Veen <sandervv@gmail.com>, Jan 27, 2011.
# This script is public domain; feel free to do anything with it you want.
#
# Example invocation: python compile.py
#
# Unfortunately, this script is only useful for Windows, since the used CCS C
# compiler is only available for Windows. This script automates the compilation
# while running the compiler in a Virtual Machine with Windows. This way, we
# were no longer required to run Windows actively (you can minimize the VM) and
# could use our decent OS (Linux / Mac) to develop the Jobot's source code ;-).

# Source file to monitor
source_file = 'ex_led.c'

# Log files used during compilation
build_log   = 'ex_led.log'
error_log   = 'ex_led.err'

# Previous modification time of source file
old_mtime = 0

while True:
    try:
        # Check modification date of source file
        mtime = os.stat(source_file)[8]
        if mtime > old_mtime:
            # Append log info to build log
            log = open(build_log, 'a')
            log.write('Compile: %s (started)\n' % strftime('%X %x'))

            # Compile source file
            os.system('ccsc +FH ' + source_file)

            # Append error log to build log
            err = open(error_log, 'r')
            last_line = ''
            while 1:
                line = err.readline()
                if not line:
                    break
                log.write(line)
                last_line = line
            err.close()

            # Exit code of ccsc or Windows is not set properly.
            # Therefore, check the error log for errors.
            if last_line == 'No Errors\n':
                log.write('Compile: %s (finished)\n' % strftime('%X %x'))
            else:
                log.write('Compile: %s (failed; check your source file)\n' \
                        % strftime('%X %x'))

            # Update cached modification date
            old_mtime = mtime
            log.close()
        sleep(0.5)
    except WindowsError:
        sleep(1)
