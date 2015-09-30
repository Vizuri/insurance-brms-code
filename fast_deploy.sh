#!/bin/sh

echo "do: $1"

if [ "$1" = "copy" ]; then
	# script to copy static content to jboss expanded war folder
    cp -r /Users/bvanderwalt/Ben/Projects/git/insurance_demo/vizuri_brms-insurance-code/insurance-web-app/src/main/webapp/ /usr/local/jboss/jboss-eap-6.4.brms-6.1.0/jboss-eap-6.4/standalone/deployments/insurance-web-app.war
	echo "copied"
elif [ "$1" = "deploy" ]; then

	cp -r /Users/bvanderwalt/Ben/Projects/git/insurance_demo/vizuri_brms-insurance-code/insurance-web-app/target/insurance-web-app/WEB-INF/ /usr/local/jboss/jboss-eap-6.4.brms-6.1.0/jboss-eap-6.4/standalone/deployments/insurance-web-app.war/WEB-INF
	# to redeploy everything after static copy (use this if you modified any rest services or other java code
	touch /usr/local/jboss/jboss-eap-6.4.brms-6.1.0/jboss-eap-6.4/standalone/deployments/insurance-web-app.war.dodeploy
	echo "deployed" 
fi



