package com.example.customerservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

public class OrderServiceConfiguration {

  @Autowired
  private DiscoveryClient discoveryClient;

  @Bean
  @Primary
  ServiceInstanceListSupplier serviceInstanceListSupplier() {
    return serviceInstanceListSupplier("order-service");
  }

  @Bean
  public ServiceInstanceListSupplier serviceInstanceListSupplier(String serviceName) {

    ServiceInstanceListSupplier supplier = new ServiceInstanceListSupplier() {
      @Override
      public Flux<List<ServiceInstance>> get() {
        List<ServiceInstance> instances = discoveryClient.getInstances(getServiceId());
        return Flux.just(instances);
      }

      @Override
      public String getServiceId() {
        return serviceName;
      }
    };
    return supplier;
  }
}
