compile: report.pdf

report.pdf: report.tex
	make -C src/
	pdflatex $^
	pdflatex $^

clean:
	make -C src/ clean
	rm -vf *.aux *.log *.out *.pdf *.toc
