package com.altimetrik.ee.demo;

import com.altimetrik.ee.demo.service.ComponentDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    context
        .getBean(ComponentDetailsService.class)
        .createComponentDetails(context.getEnvironment().getProperty("spring.application.name"));
  }
}
