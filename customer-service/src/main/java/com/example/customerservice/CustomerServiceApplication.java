package com.example.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
@SpringBootApplication annotation is used to set up the auto configuration feature of Spring Boot along with
 invoking the component scanning mechanism for the respective components to be scanned.
@EnableEurekaClient annotation specifying that this service is a client service that will be registered 
with the Eureka Server for the service discovery process.
@EnableFeignClients annotation is used here to have the provision of calling another microservice's endpoint 
which is registered with the respective Eureka Server.
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
