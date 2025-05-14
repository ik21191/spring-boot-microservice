package com.example.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/***
@SpringBootApplication annotation is used to set up the auto configuration feature of Spring Boot along with
 invoking the component scanning mechanism for the respective components to be scanned.
@EnableDiscoveryClient allows microservices to dynamically register themselves and discover other services 
within a microservices architecture. 
*/

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(CustomerServiceApplication.class, args);
  }
}
