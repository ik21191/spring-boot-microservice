package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableWebSecurity
public class OrderServiceApplication extends WebSecurityConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(OrderServiceApplication.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
    .disable()
    .authorizeRequests()
    .anyRequest()
    .authenticated()
    .and()
    .httpBasic();

    /* restrict the number of times a user can be logged in concurrently
    http.authorizeRequests()
    .anyRequest()
    .authenticated()
    .and()
    .formLogin()
    .and()  
    .sessionManagement()
    .maximumSessions(1)
    .expiredUrl("/login?expired");
    */
  }
}
