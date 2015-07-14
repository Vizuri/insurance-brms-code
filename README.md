# insurance-brms-code
Code and installer Documentation for BRMS Insurance demonstration

## Prequisites

	* Jdk 1.6 or higher
	* Apache Maven 3.1 or higher






## Install Red Hat EAP and BRMS

	*	Red Hat JBoss Enterprise Application Platform 6.4 from www.jboss.org
	*	Red Hat JBoss BRMS 6.1.GA from www.jboss.org	 

## Getting Started

	* Build Domain Model:
		* mvn -pl domain clean install
	
	* Add application user 
		*	run EAP_HOME/bin/add-user.sh or EAP_HOME/bin/add-user.bat
		* 	Create a Application User  called rulesuser with the role admin

	* Start EAP 
		*  run EAP_HOME/bin/standalone.sh or EAP_HOME/bin/standalone.bat
	
	* Navigate to http://localhost:8080/business-central
	* Login with the above created user	

	* Go to Authoring > Administration
	* Go to Organization Unit > Manage Organization Unit
	* Click Add , and provide the following
		*  Name 				: DemoOrg 
		*  Default Group ID 	: DemoOrg
		*  Owner 				: rulesuser
	* Go to Repositories > Clone repository
	* In the Clone Repository Box, Provide following
		* Repository Name 		: DemoRepo
		* Orgnizational Unit 	: Select DemoOrg
		* Git URL 				: https://github.com/Vizuri/insurance-brms-rules.git

		* Click Clone
	
	* Go to Authoring > Project Authoring
		* In Project Explorer 
			* Choose Orgnizational unit 	: DemoOrg
			* Repository 					: DemoRepo 
			* Project 						: Insurance
			* Click On Open Project Editor
			* Click Build > Build & Deploy in right panel

	* Build and Deploy insurance-web-app
		*	mvn -pl insurance-web-app clean install jboss-as:deploy
		* 	Navigate to http://localhost:8080/insurance-web-app/
	

