#
# This Makefile was used to automate the compilation / linking / programming
# process of the microcontroller chip 18F452. Unfortunately, SDCC (Small Device
# C Compiler) did not produce the write assembly code. Therefore, the CCS C
# compiler (which runs only on Windows) is used for compilation (see also:
# compile.py).
#
# This Makefile is public domain; thus you can do anything with it you want.
#

PORT=/dev/ttyUSB0
TYPE=18f452
PICPROG_CHIP=18f4520
PIKLAB_CHIP=18F452
PIKLAB_ARGS=--port usb -p icd2 -d ${PIKLAB_CHIP}
PROG=ex_led

pk2cmd=pk2cmd/pk2cmd
pk2dat=pk2cmd/

All: upload
	# ${PROG}.hex upload

#${PROG}.hex: ${PROG}.o
#	gplink -c -s ${TYPE}.lkr -o $@ -m $^ -I /usr/share/sdcc/lib/pic16 \
#		-I /usr/share/sdcc/include/pic16 \
#		libio${TYPE}.lib libdev${TYPE}.lib libsdcc.lib crt0.o \
#		/usr/share/sdcc/lib/pic16/libc18f.lib
#
#${PROG}.o: ${PROG}.asm
#	gpasm -c $<
#
#${PROG}.asm: ${PROG}.c
#	sdcc -S -mpic16 -p${TYPE} $<

upload: ${PROG}.hex
	#picp -c ${PORT} ${TYPE} -wp $^
	#${pk2cmd} -B${pk2dat} -PPIC${TYPE} -F${PROG}.hex -M
	#picprog --device pic${PICPROG_CHIP} --pic-serial-port ${PORT} \
	#	--burn --input-hexfile $^
	/usr/bin/sudo piklab-prog ${PIKLAB_ARGS} -c connect
	/usr/bin/sudo piklab-prog ${PIKLAB_ARGS} -c program $^
	#sudo piklab-prog ${PIKLAB_ARGS} -c verify $^

clean:
	rm -vf ${PROG}.o ${PROG}.cod ${PROG}.cof ${PROG}.lst ${PROG}.map \
		${PROG}.hex ${PROG}.asm
