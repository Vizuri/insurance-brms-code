# insurance-brms-code
Code and installer Documentation for BRMS Insurance demonstration

## Prequisites

	* Jdk 1.6 or higher
	* Apache Maven 3.1 or higher

## Install Red Hat EAP 6.4.0 and BRMS 6.1.0

	*	Red Hat JBoss Enterprise Application Platform 6.4 from www.jboss.org
	*	Red Hat JBoss BRMS 6.1.GA plus patch/update 5 from www.jboss.org
	
## OR

## Install Red Hat EAP 6.4.5 and BRMS 6.2.0

	*	Red Hat JBoss Enterprise Application Platform 6.4.0 plus patch/update 5 from www.jboss.org
	*	Red Hat JBoss BRMS 6.2.GA from www.jboss.org
	
## Please note the different library dependancy versions in the pom.xml, depending on whether you want to use BRMS 6.1.x or 6.2.0	 

    * BRMS 6.1.x
        * Use drools-version: 6.2.0.Final-redhat-11 for BRMS 6.1.x
        * Use version.jboss.bom.eap: 6.3.2.GA for BRMS 6.1.x
    
    * BRMS 6.2
        * Use drools-version: 6.3.0.Final-redhat-5 for BRMS 6.2
        * Use version.jboss.bom.eap: 6.4.0.GA for BRMS 6.2
        
    * Please note that a similar change has to be made in the BRMS rules project depending on which version of BRMS you are running. Notes were added to the pom of the rules project to indicate which changes to make.
    
    
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

    * Open the pom.xml and verify that the correct dependencies are selected for the given version of BRMS
       
        * BRMS 6.1.x
            * Use version: 6.0.3-redhat-6 for BRMS 6.1.x
          
        * BRMS 6.2
            * Use version: 6.3.0.Final-redhat-5 for BRMS 6.2
        
    
	* Build and Deploy insurance-web-app
		*	mvn -pl insurance-web-app clean install jboss-as:deploy
		* 	Navigate to http://localhost:8080/insurance-web-app/
	

