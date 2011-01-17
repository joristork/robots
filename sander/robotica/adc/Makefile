<<<<<<< HEAD
compile:
	pdflatex report.tex
=======
compile: report.pdf journal_Stork_Swartsenburg_vVeen.pdf

%.pdf: %.tex
	@if [ -f ~/.vim/script/latex-errorfilter ]; then \
		pdflatex $^ | ~/.vim/script/latex-errorfilter; \
	else \
		pdflatex $^; \
	fi
>>>>>>> 7e084c249d15a60e9b7d24625a4a09c46e3a1c97

clean:
	rm -v *.aux *.log *.pdf *.toc
