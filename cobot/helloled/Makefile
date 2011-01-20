
PORT=/dev/ttyUSB0
TYPE=18f452
PICPROG_CHIP=18f4520
PROG=helloled

pk2cmd=pk2cmd/pk2cmd
pk2dat=pk2cmd/

All: ${PROG}.hex

${PROG}.hex: ${PROG}.o
	gplink -c -s ${TYPE}.lkr -o $@ -m $^ -I /usr/share/sdcc/lib/pic16 \
		libio${TYPE}.lib libdev${TYPE}.lib libsdcc.lib crt0.o

${PROG}.o: ${PROG}.asm
	gpasm -c $<

${PROG}.asm: ${PROG}.c
	sdcc -S -mpic16 -p${TYPE} $<

upload: ${PROG}.hex
	picp -c ${PORT} ${TYPE} -wp $^
	#${pk2cmd} -B${pk2dat} -PPIC${TYPE} -F${PROG}.hex -M
	#picprog --device pic${PICPROG_CHIP} --pic-serial-port ${PORT} \
	#	--burn --input-hexfile $^

clean:
	rm -vf ${PROG}.o ${PROG}.cod ${PROG}.cof ${PROG}.lst ${PROG}.map \
		${PROG}.hex ${PROG}.asm
