package com.altimetrik.ee.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(apiInfo())
        .addSecurityItem(new SecurityRequirement().addList("Authorization"))
        .components(
            new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(
                    "Authorization",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .name("Authorization")
                        .in(In.HEADER)));
  }

  private Info apiInfo() {
    return new Info()
        .title("Demo Application")
        .description("Demo API Services")
        .version("2.0")
        .contact(
            new Contact()
                .name("Playground")
                .url("https://playground.altimetrik.com")
                .email("pg-mgr1@altimetrik.com"))
        .termsOfService("https://www.altimetrik.com/privacy-policy/")
        .license(
            new io.swagger.v3.oas.models.info.License()
                .name("Apache License Version 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0"));
  }
}
