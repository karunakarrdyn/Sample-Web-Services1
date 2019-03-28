# README #
# Sample WEB Services

### What is this repository for? ###

This has sample WebServices developed by using SpringBoot,Java8,MySql,Hibernate,Redis Technologies. 

### How do I get set up? ###

## Summary of set up

Install redis,mysql,java8 and do standard needed configurations.

Start the mysql server,redis server.

Download this project and import into STS as maven project.

Create the database with the name test and import the mysqldump test.sql available under src/main/resources

Update the database url,username and password in application.properties.

Update the server port in application.properties (default 8080).

Update the redis server url,port in application.properties.

Now, To execute the test cases and generate .jar file run the following command in the terminal from the project home folder
```maven
mvn install

OR one can do the same from STS by right-clicking on project name->Run As->Maven Install.

### Deploy the services.

Now, Start the services by using generated jar file with the following command in terminal
```shell
nohup java -jar SMSTask-0.0.1-SNAPSHOT.jar &

OR one can start the services in the STS by running the configured run configuration.

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

Author:Karunakar Reddy
Contact:karunakar.rdyn@gmail.com