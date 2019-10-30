########## Invia Automation##########

Steps to run tests:
	1)	Import solution in Eclipse
	2)  	Automation solution for Invia web application testing has been developed using Selenium WebDriver + TestNG + Maven
	3)  	Solution folder structure paths as below: 
		Automation tests : 'ROOT_PATH:\src\test\java\com\invia\tests\
		Properties file : 'ROOT_PATH:\src\main\resources\InviaProp.properties'
		TestNg XMl file : 'ROOT_PATH:\testng.xml'
		Execution logs : 'ROOT_PATH: \logs\app.log'	
	4)	TO run the automation solution right click on the solution and select 'Run As --> Maven clean' 
		and then again right click on solution and select 'Run As --> Maven install'
			OR
		Right click on solution and select 'Run As --> Maven build...' and in Goals type 'clean install' and hit 'Run'
			OR
		Right click anywhere in the testng.xml file and select 'Run As --> 1 TestNG Suite'
	5)	Maven build will start and after the completion of execution, we can find execution TestNg report under following path “ROOT_PATH:\test-output\emailable-report.html”
	6)	Execution logs will be generated under folder: "ROOT_PATH:\logs\app.log"
	
(Please note: New arrival date has been changed '13.11.2019'(asked in task) to '14.11.2019' to highlight test case 5 failure(Verify that Flight timings are within selected range))