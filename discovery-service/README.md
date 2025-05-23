# discovery-service

**Compatible java version 17**

This application is working as a registry service(Eureka Server) where individual Microservice register itself, so that it can be discovered by other services.

So, whenever a micro-service is started, it will call below endpoint to register with this discovery service(Eureka Server).

`eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/`

Below technology stacks are used for this application

1. Maven `apache-maven-3.9.9`
2. JDK `17`

Steps to run the application

- Option 1: The application will be automatically started by the script `run.bat` present in the parent folder of this module.
- Option 2: You can run the application by executing `discovery-service.bat`.
- Option 3: You can run the application by running below java command

	java -jar discovery-service-0.0.1-SNAPSHOT.jar
	
Once you run the application using once of the above command then you can access this application using [app-url](http://localhost:8761)

 

 