compile: presentation.pdf

%.pdf: %.tex
	@if [ -f ~/.vim/script/latex-errorfilter ]; then \
		pdflatex $^ | ~/.vim/script/latex-errorfilter; \
	else \
		pdflatex $^; \
	fi

clean:
	rm -v *.aux *.log *.pdf *.toc
