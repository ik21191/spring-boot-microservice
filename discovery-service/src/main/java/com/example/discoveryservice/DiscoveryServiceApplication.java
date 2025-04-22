package com.example.discoveryservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@EnableScheduling
public class DiscoveryServiceApplication {

  @Autowired
  private DiscoveryClient discoveryClient;

  public static void main(String[] args) {
    SpringApplication.run(DiscoveryServiceApplication.class, args);
  }

  @Scheduled(fixedRate = 10000) // Check every 60 seconds
  public void checkServiceHealth() {
    System.out.println("checkServiceHealth() is called.");
    List<String> instances = discoveryClient.getServices();
    System.out.println(instances);
    instances.forEach(instance -> displayRegisteredServiceDetails(instance));
  }

  private void displayRegisteredServiceDetails(String serviceInstance) {
    // incase a service is running on multiple nodes, then we will get the list of all nodes
    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceInstance);
    serviceInstances.forEach(instanceDetail -> {
      System.out.println("\n:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
      System.out.println("Instance id: " + instanceDetail.getInstanceId());
      System.out.println("host: " + instanceDetail.getHost());
      System.out.println("port: " + instanceDetail.getPort());
      System.out.println("scheme: " + instanceDetail.getScheme());
      System.out.println("\n:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    });
  }

}
