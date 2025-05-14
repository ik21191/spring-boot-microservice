package com.example.customerservice;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

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

  /***
   * Rate Limit Demo
   * @param id
   * @return
   */
  @GetMapping("/call-order-service/{id}")
  @RateLimiter(name = "getMessageRateLimit", fallbackMethod = "getMessageFallBack")
  public ResponseWrapper<String> getOrdersForCustomer(@PathVariable int id) {
    return callUsingWebClient(String.valueOf(id));
  }
  
  //fallback method for rateLimit
  public ResponseWrapper<String> getMessageFallBack(int id, RequestNotPermitted exception) {
    System.out.println("Rate limit has applied, So no further calls are getting accepted");
    String response = ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .body("Too many requests : No further request will be accepted. Please try after sometime").toString();
    return new ResponseWrapper<>(environment, response); 
  }
  
  /***retry demo
   * @param id
   * @return
   */
  @GetMapping("/retry-demo/{id}")
  @Retry(name = "getMessageForRetry", fallbackMethod = "retryFallBack")
  public ResponseWrapper<String> retryDemo(@PathVariable int id) {
    System.out.println("retryDemo method is called.");
    return callUsingWebClient(String.valueOf(id));
  }
  
  //fallback method for retryDemo
  public ResponseWrapper<String> retryFallBack(int id, Exception exception) {
    System.out.println("retry is applied");
    String response = ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("Service is down, please re-try after sometime !!!").toString();
    return new ResponseWrapper<>(environment, response); 
  }
  
  
  /***Circuit Breaker Demo
   * 
   * @param id
   * @return
   */
  @GetMapping("/cb-demo/{id}")
  @CircuitBreaker(name = "getMessageForCircuitBreaker", fallbackMethod = "circuitBreakerFallBack")
  public ResponseWrapper<String> circuitBreakerDemo(@PathVariable int id) {
    System.out.println("circuitBreakerDemo method is called.");
    return callUsingWebClient(String.valueOf(id));
  }
  
  //fallback method for Circuit Breaker
  public ResponseWrapper<String> circuitBreakerFallBack(int id, Exception exception) {
    System.out.println("circuit breaker is applied");
    String response = ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("Microservices commnunication issue. !!!").toString();
    return new ResponseWrapper<>(environment, response); 
  }
  
  
  /***
   * Time limiter demo
   * @return
   */
  @GetMapping("/time-limiter-demo")
  @TimeLimiter(name = "getMessageTL")
  public CompletableFuture<String> timeLimiterDemo() {
    System.out.println("timeLimiterDemo method is called.");
     return CompletableFuture.supplyAsync(this::getResponse);
  }

  private String getResponse() {
    if (Math.random() < 0.4) { // Expected to fail 40% of the time
      return "Executing Within the time Limit...";
    } else {
      try {
        System.out.println("Getting Delayed Execution");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return "Exception due to Request Timeout.";
  }
  
  public ResponseWrapper<String> callUsingWebClient(String resource) {
    // String username = "test";
    // String password = "test";
    //dGVzdDp0ZXN0 is base64 encoded of test:test
    String authHeader = "Basic " + "dGVzdDp0ZXN0";
    String response = webClientBuilder.build()
        .get().uri("http://order-service/orders/" + resource)
        .header("Authorization", authHeader)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    return new ResponseWrapper<>(environment, response);
  }
}
