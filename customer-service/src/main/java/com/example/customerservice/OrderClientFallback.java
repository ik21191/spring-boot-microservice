package com.example.customerservice;

import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public class OrderClientFallback implements OrderClient {
  @Override
  public Object getOrdersForCustomer(@RequestHeader("Authorization") String header, int customerId) {
    System.out.println(
        "#############order-service not available, returning empty list ########################");
    return Collections.emptyList();
  }
}
