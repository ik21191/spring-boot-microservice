package com.example.customerservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/***
 @FeignClient annotation is used to annotate this Feign interface as the interface that will be used to provide 
 the functionality of calling the Order Service's getOrderById() method through its endpoint /{id}
 */

//Here order-service is another REST application
@FeignClient(name = "order-service", fallback = OrderClientFallback.class, configuration = FeignConfig.class)
public interface OrderClient {
    @GetMapping("/")
    Object getOrdersForCustomer(@RequestHeader("Authorization") String header, @RequestParam int customerId);
}