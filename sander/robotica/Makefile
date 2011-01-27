

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
	ant -DremoteId=0014.4F01.0000.395A deploy

send-b:
	ant -DremoteId=0014.4F01.0000.3D00 deploy

init:
	ant disableota
	ant info
	ant startbasestation
