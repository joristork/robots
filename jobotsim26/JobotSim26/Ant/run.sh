#!/bin/bash 

cd /data/javaBot/src
rm * -R
svn checkout http://oege.ie.hva.nl/svnproj/mse_sp/ /data/javaBot/src --username slangenh --password BUGslangenh 

export ANT_HOME=/usr/share/ant/
cd /data/javaBot/build
rm -rf * -R
cd /data/javaBot
ant cobertura-report -lib src/JobotSim/lib