colorize=pygmentize -O -style=colorfull -f latex

all: highlight

highlight: hemisson.tex ex_led.tex data-receiver.tex sending.tex \
	measuring.tex loops.tex cobot-start.tex upload.tex compile.tex \
	receive.tex send.tex cobot.tex

%.tex: %.c
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\21#' > $@

compile.tex: compile.py
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

upload.tex: upload.py
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

hemisson.tex: hemisson.py
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

measuring.tex: measuring.java
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

send.tex: send.java
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

receive.tex: receive.java
	${colorize} $^ | sed 's#\(\\PY{l+s+sd}{\)\( \+\)#\2\1#' > $@

clear:
	rm -vf *.tex
