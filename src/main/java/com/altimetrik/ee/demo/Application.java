package com.altimetrik.ee.demo;

import com.altimetrik.ee.demo.service.ComponentDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication()
public class Application {

  private static ConfigurableApplicationContext context;

  public static void main(String[] args) {
    context = SpringApplication.run(Application.class, args);
    context
        .getBean(ComponentDetailsService.class)
        .createComponentDetails(context.getEnvironment().getProperty("spring.application.name"));
  }
}
