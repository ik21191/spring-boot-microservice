package com.example.customerservice;

import java.util.Arrays;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final Environment environment;
  
  private WebClient.Builder webClientBuilder;
  private List<Customer> customers =
      Arrays.asList(new Customer(1, "Imran Khan"), new Customer(2, "Raj Kumar"));

  public CustomerController(WebClient.Builder webClientBuilder, final Environment environment) {
    this.webClientBuilder = webClientBuilder;
    this.environment = environment;
  }

  @GetMapping("/ping")
  public ResponseWrapper<String> ping() {
    return new ResponseWrapper<>(environment, "Hello Customer !");
  }
  
  @GetMapping("/")
  public ResponseWrapper<List<Customer>> getAllCustomers() {
    return new ResponseWrapper<>(environment, customers) ;
  }

  @GetMapping("/{id}")
  public ResponseWrapper<Customer> getCustomerById(@PathVariable int id) {
    return new ResponseWrapper<>(environment, 
        customers.stream().filter(customer -> customer.getId() == id).findFirst()
        .orElseThrow(IllegalArgumentException::new)) ;
  }

  @GetMapping("/call-order-service/{id}")
  public ResponseWrapper<String> getOrdersForCustomer(@PathVariable int id) {
    // String username = "test";
    // String password = "test";
    //dGVzdDp0ZXN0 is base64 encoded of test:test
    String authHeader = "Basic " + "dGVzdDp0ZXN0";
    String response = webClientBuilder.build().get().uri("http://order-service/orders/" + id)
        .header("Authorization", authHeader).retrieve().bodyToMono(String.class).block();
    return new ResponseWrapper<>(environment, response);
  }
}
