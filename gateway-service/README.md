# gateway-service

**Compatible java version 17**

This application is working as a Gateway for this micr-oservice architecture. Through this gateway, we are providing single a single entry point for all the micro-services.

This gateway is also registered itself with the registry service when it is started by calling below **Eureka Server** end point, after that the gateway will be aware of all the services registered with the service registry.

`eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/`

Below technology stacks are used for this application

1. Maven `apache-maven-3.9.9`
2. JDK `17`

Steps to run the application

- Option 1: The application will be automatically started by the script `run.bat` present in the parent folder of this module.
- Option 2: You can run the application by executing `gateway-service.bat`.
- Option 3: You can run the application by running below java command

	java -jar gateway-service-0.0.1-SNAPSHOT.jar

 

 