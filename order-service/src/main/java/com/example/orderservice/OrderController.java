package com.example.orderservice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private final List<Order> orders =
    Arrays.asList(
      new Order(1, 1, "Product A"), new Order(2, 1, "Product B"),
      new Order(3, 2, "Product C"), new Order(4, 1, "Product D"), 
      new Order(5, 2, "Product E"));

  private final Environment environment;

  @Autowired
  public OrderController(final Environment environment) {
    this.environment = environment;
  }

  @GetMapping("/ping")
  public ResponseWrapper<String> ping() {
    return new ResponseWrapper<String>(environment, "Hello Orders !");
  }

  @GetMapping("/")
  public ResponseWrapper<List<Order>>  getAllOrders() {
    return new ResponseWrapper<List<Order>>(environment, orders);
  }

  //* Below is the modified version of this method
  /*
  @GetMapping
  public List<Order> getAllOrders(@RequestParam(required = false) Integer customerId) {
    if (customerId != null) {
      return orders.stream().filter(order -> customerId.equals(order.getCustomerId()))
          .collect(Collectors.toList());
    }

    return orders;
  }
  */

  @GetMapping
  public ResponseWrapper<List<Order>> getAllOrders(
      @RequestParam(required = false) Integer customerId) {
    if (customerId != null) {
      return new ResponseWrapper<>(environment, orders.stream()
          .filter(order -> customerId.equals(order.getCustomerId())).collect(Collectors.toList()));
    }
    return new ResponseWrapper<>(environment, orders);
  }

  @GetMapping("/{id}")
  public ResponseWrapper<Order> getOrderById(@PathVariable int id) {
    return new ResponseWrapper<>(environment, 
        orders.stream().filter(order -> order.getId() == id).findFirst()
        .orElseThrow(IllegalArgumentException::new) 
    );
  }
}
