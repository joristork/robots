compile: report.pdf journal_Stork_Swartsenburg_vVeen.pdf

%.pdf: %.tex
	@if [ -f ~/.vim/script/latex-errorfilter ]; then \
		pdflatex $^ | ~/.vim/script/latex-errorfilter; \
	else \
		pdflatex $^; \
	fi

clean:
	rm -v *.aux *.log *.pdf *.toc
