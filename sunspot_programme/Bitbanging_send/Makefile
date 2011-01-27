

all: compile

clean:
	ant clean

compile:
	ant compile

deploy:
	ant codeploy

simulate:
	ant jar-app
	ant solarium

send-a:
	ant -DremoteId=0014.4F01.0000.2423 deploy

send-b:
	ant -DremoteId=0014.4F01.0000.37EE deploy

init:
	ant disableota
	ant info
	ant startbasestation
