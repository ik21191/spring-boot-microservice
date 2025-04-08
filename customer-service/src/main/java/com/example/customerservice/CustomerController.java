package com.example.customerservice;

import java.util.Arrays;
import java.util.List;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

  private OrderClient orderClient;

  public CustomerController(OrderClient orderClient) {
    this.orderClient = orderClient;
  }

  private List<Customer> customers =
      Arrays.asList(new Customer(1, "Joe Bloggs"), new Customer(2, "Jane Doe"));

  @GetMapping
  public List<Customer> getAllCustomers() {
    return customers;
  }

  @GetMapping("/{id}")
  public Customer getCustomerById(@PathVariable int id) {
    return customers.stream().filter(customer -> customer.getId() == id).findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  @GetMapping("/call-order-service/{id}")
  public Object getOrdersForCustomer(@PathVariable int id) {
    String username = "test";
    String password = "test";

    byte[] encodedBytes = Base64Utils.encode((username + ":" + password).getBytes());

    String authHeader = "Basic " + new String(encodedBytes);
    return orderClient.getOrdersForCustomer(authHeader, id);
  }
}
