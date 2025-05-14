package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	  System.out.println("::::::::::::::::::::::::: Creating RouteLocator.........................");
        return builder.routes()
            .route("customer-service", customerService -> customerService    //Adding Customer service
                .path("/customers/**")
                .uri("lb://customer-service"))
            .route("order-service", ordersService -> ordersService  //Adding order service
                .path("/orders/**")
                .uri("lb://order-service"))
            .build();
    }
}

