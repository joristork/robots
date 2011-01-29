colorize=pygmentize -O -style=colorfull -f latex

all: highlight

highlight: hemisson.tex ex_led.tex data-receiver.tex

%.tex: %.c
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\21#' > $@

hemisson.tex: hemisson.py
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

cobot.tex: cobot.py
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

clear:
	rm -vf *.tex
