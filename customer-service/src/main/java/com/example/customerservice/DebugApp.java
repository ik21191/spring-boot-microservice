package com.example.customerservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class DebugApp {
  private final Environment environment;
  
  public DebugApp(Environment environment) {
    this.environment = environment;
  }
  
  @GetMapping("/env/{property}")
  public ResponseWrapper<String> getPropertyValue(@PathVariable String property) {
    System.out.println("getPropertyValue is called " + property);
    return new ResponseWrapper<>(environment, property + " = " + environment.getProperty(property));
  }
}
