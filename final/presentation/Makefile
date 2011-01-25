compile: presentation.pdf

%.pdf: %.tex
	@if [ -f ~/.vim/script/latex-errorfilter ]; then \
		pdflatex $^ | ~/.vim/script/latex-errorfilter; \
	else \
		pdflatex $^; \
	fi

presentation.tex: presentation.yaml
	python yml2tex.py $^ > $@

clean:
	rm -vf *.aux *.log *.nav *.out *.vrb *.pdf *.tex *.toc
