if EXIST 18motor.out del 18motor.out
..\..\bin\mcc18 -i..\..\h 18motor.c
..\..\bin\mplink 18motor.o ..\..\lkr\18c452.lkr -l..\..\lib -o 18motor.out
