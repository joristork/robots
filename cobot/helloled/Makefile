
PORT=/dev/ttyUSB0
TYPE=18f452
PICPROG_CHIP=18f4520
PIKLAB_CHIP=18F452
PIKLAB_ARGS= --port usb -p icd2 -d ${PIKLAB_CHIP} # --max-debug
GPASM_ARGS=--extended
PROG=helloled

pk2cmd=pk2cmd/pk2cmd
pk2dat=pk2cmd/

All: ${PROG}.hex upload

${PROG}.hex: ${PROG}.o
	gplink -c -s ${TYPE}.lkr -o $@ -m $^ -I /usr/share/sdcc/lib/pic16 \
		-I /usr/share/sdcc/include/pic16 \
		libio${TYPE}.lib libdev${TYPE}.lib libsdcc.lib crt0.o \
		/usr/share/sdcc/lib/pic16/libc18f.lib

${PROG}.o: ${PROG}.asm
	gpasm ${GPASM_ARGS} -c $<

${PROG}.asm: ${PROG}.c
	#sdcc -S -mpic16 -p${TYPE} $<
	/usr/hitech/picc-18/std/9.52/bin/picc18 -S --chip=${TYPE} $<

upload: ${PROG}.hex
	#picp -c ${PORT} ${TYPE} -wp $^
	#${pk2cmd} -B${pk2dat} -PPIC${TYPE} -F${PROG}.hex -M
	#picprog --device pic${PICPROG_CHIP} --pic-serial-port ${PORT} \
	#	--burn --input-hexfile $^
	sudo piklab-prog ${PIKLAB_ARGS} -c connect
	sudo piklab-prog ${PIKLAB_ARGS} -c program $^
	sudo piklab-prog ${PIKLAB_ARGS} -c verify $^

clean:
	rm -vf ${PROG}.o ${PROG}.cod ${PROG}.cof ${PROG}.lst ${PROG}.map \
		${PROG}.hex ${PROG}.asm
