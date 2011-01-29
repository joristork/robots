colorize=pygmentize -O -style=colorfull -o

all: highlight

highlight: ex_led.tex

%.tex: %.c
	${colorize} $@ $^

cobot.tex: cobot.py
	${colorize} $@ $^

clear:
	rm -vf *.tex
