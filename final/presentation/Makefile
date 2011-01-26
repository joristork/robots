compile: presentation.pdf

%.pdf: %.tex
	@if [ -f ~/.vim/script/latex-errorfilter ]; then \
		cat /dev/null | pdflatex $^ | ~/.vim/script/latex-errorfilter ; \
		cat /dev/null | pdflatex $^ > /dev/null; \
	else \
		cat /dev/null pdflatex $^ ; \
		cat /dev/null pdflatex $^ > /dev/null; \
	fi

presentation.tex: presentation.yaml
	python yml2tex.py $^ > $@ || rm -v presentation.tex

clean:
	rm -vf *.aux *.log *.nav *.out *.vrb *.pdf *.tex *.toc
