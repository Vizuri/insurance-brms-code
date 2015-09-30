#!/bin/sh
#set -x #echo on

BASEDIR=$(dirname $0)
echo $BASEDIR
curdir=$PWD/$BASEDIR
now="$(date +'%d-%m-%Y-%H-%M-%S')"
DIRNAME=`dirname "$0"`
PROGNAME=`basename "$0"`
#JBOSS_HOME=/Users/ashakya/Documents/magnaworkspace/jboss-eap-6.2-brms-GA-delivery/jboss-eap-6.2-brms
echo "jboss home :  ${JBOSS_HOME}"
localrepo=vizuribrms-insurance-rules
remotreponame=vizuri_brms-insurance-rules
function checkEnviroment(){
	if [ "x$JBOSS_HOME" = "x" ]; then
		echo "JBOSS_HOME is not set"
    	exit -1;
	fi
}

function gitConfig(){
	git config user.email "jbrms-test@mcarta.com"
	git config user.name  "jbrms-test"
}

function stageLocal(){
	cd $BASEDIR
	#mv HomeownerRepo HomeownerRepo$now
	mv $localrepo $MagnaRuleRepo$now
	gitConfig
	echo "****Please enter business-central password when prompted***"
	git clone ssh://rulesuser@localhost:8001/$localrepo
	
  	
	#read -p "Enter Jboss Directory : " confirm_value
	#git clone $JBOSS_HOME/.niogit/HomeownerRepo.git
	
	cd $localrepo
	
	echo "****Now adding unfuddled remote use unfuddled credentials ***"
	git remote add unfuddle https://vizuri.unfuddle.com/git/$remotreponame/
	git fetch unfuddle
	git checkout -b unfuddlemaster unfuddle/master
	git pull unfuddle master
	git merge master
	
	git checkout master
	
	git merge unfuddlemaster
	
	echo "****Please enter business-central password when prompted***"
	git push origin master
	git checkout unfuddlemaster
	gitConfig
	echo "****Now pushing to unfuddled remote use the unfuddled credentials ***"
	git push unfuddle master

}

function deleteGitStage(){
	cd $curdir
	rm -rf Home*
	rm -rf $localrepo*
	
}
#checkEnviroment
stageLocal
#sleep 3
deleteGitStage
#rm -rf Home*
