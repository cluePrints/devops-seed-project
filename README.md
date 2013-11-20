Devops-seed-backend app
=========

Releasing schema:
---------
	mvn db-migration:migrate
	
Building web archive to deploy:
---------
	# result will be in dos-server/target/devops-seed-backend.war
	cd devops-parent
	mvn package

Adjusting connection details:
---------
	pom.xml / jdbc.* properties

You can verify app is working by issuing an HTTP GET request against /service/page/, e.g. 

	curl http://localhost:8080/<context-path>/service/page


Starting client:
----------
	java -jar ../dos-seed-client/target/devops-seed-client-jar-with-dependencies.jar http://localhost:8080/devops-seed-backend/service

Server errors will be logged at:
----------
	tomcat/logs/catalina.2013-11-20.log	
